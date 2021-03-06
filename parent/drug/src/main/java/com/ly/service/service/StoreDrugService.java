package com.ly.service.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.StoreAndDrugInfo;
import com.ly.service.entity.Store;
import com.ly.service.entity.StoreDrug;
import com.ly.service.feign.client.UserClient;
import com.ly.service.mapper.StoreDrugMapper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class StoreDrugService {

	@Autowired
	StoreDrugMapper storeDrugMapper;
	@Autowired
	UserClient userClient;
	
	public StoreDrug upDrug(int storeid, Long storedrugId){
		StoreDrug drug= storeDrugMapper.selectByPrimaryKey(storedrugId);
		if(drug == null){
			throw new HandleException(ErrorCode.ARG_ERROR, "参数错误，对应的药品不存在");
		}else{
			if(drug.getStoreid() != storeid){
				throw new HandleException(ErrorCode.DOMAIN_ERROR, "你无权进行此操作");
			}
			drug.setState(StoreDrug.STATE_UP);
			storeDrugMapper.updateByPrimaryKey(drug);
		}
		return drug;
	}
	
	public StoreDrug downDrug(int storeid, Long storedrugId){		
		StoreDrug drug = storeDrugMapper.selectByPrimaryKey(storedrugId);
		if(drug == null){
			throw new HandleException(ErrorCode.ARG_ERROR, "所选择的药品不存在");
		}else{
			if(drug.getStoreid() != storeid){
				throw new HandleException(ErrorCode.DOMAIN_ERROR, "你无权进行此操作");
			}
			drug.setState(StoreDrug.STATE_DOWN);
			storeDrugMapper.updateByPrimaryKey(drug);
		}
		return drug;
	}
	
	public List<StoreDrug> getStoreDrugList(int storeid, String key, int state, int pageIndex, int pageSize){
//		Example ex = new Example(StoreDrug.class);
//		Criteria c = ex.createCriteria().andEqualTo("storeid", storeid);
//		if(key != null && !key.isEmpty()){
//			c = c.andLike("drugname", "%"+key+"%");
//		}
//		if(state!=0){
//			c = c.andEqualTo("state", state);
//		}
//		ex.setOrderByClause("id DESC");
//		//分页处理
//		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize, pageSize);
//		List<StoreDrug> list= storeDrugMapper.selectByExampleAndRowBounds(ex, rowBounds);
//		
		List<StoreDrug> list = null;
		int offset = (pageIndex-1)*pageSize;
		key = "%"+key+"%";
		if(state==0) {
			list = storeDrugMapper.getStoreDrugsByKeys(storeid, key, offset, pageSize);
		}else {
			list = storeDrugMapper.getStoreDrugsByKeysAndState(storeid, key, state, offset, pageSize);
		}
		return list;
	}
	
	public List<StoreDrug> getDrugsInStore(int storeid, List<Integer> drugList){
		Example ex = new Example(StoreDrug.class);
		ex.createCriteria().andEqualTo("storeid", storeid).andIn("drugid", drugList);
		List<StoreDrug> list = storeDrugMapper.selectByExample(ex);
		return list;
	}

	public StoreDrug getDrugByStore(Integer storeid, Integer drugid) {
		Example ex = new Example(StoreDrug.class);
		ex.createCriteria().andEqualTo("storeid", storeid).andEqualTo("drugid", drugid);
		StoreDrug storeDrug = storeDrugMapper.selectOneByExample(ex);
		
		return storeDrug;
	}

	public StoreDrug add(int storeid, int drugid, String drugName, String standard ,String company, int price, int settlementPrice) {
		StoreDrug ret = getDrugByStore(storeid, drugid);
		if(ret != null){
			throw new HandleException(ErrorCode.NORMAL_ERROR, "该药品已经配置,无需重复配置");
		}
		
		ret = new StoreDrug();
		ret.setDrugcompany(company);
		ret.setDrugid(drugid);
		ret.setDrugname(drugName);
		ret.setDrugstandard(standard);
		ret.setPrice(price);
	
		Store store = userClient.getStore(storeid).fetchOKData(Store.class);
		
		ret.setSettlementprice(getInt(store.getRate()*price));//根据药品售价以及药店的费率计算服务费
		ret.setState(StoreDrug.STATE_DOWN);
		ret.setStoreid(storeid);
		
		storeDrugMapper.insertUseGeneratedKeys(ret);
		
		return ret;
	}

	private int getInt(double number){
	    BigDecimal bd=new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
	    return Integer.parseInt(bd.toString()); 
	} 
	
	public List<StoreDrug> getAllStoreDrugList(int pageIndex, int pageSize) {
		Example ex = new Example(StoreDrug.class);
		ex.setOrderByClause("id DESC");
		//分页处理
		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize, pageSize);
		List<StoreDrug> list= storeDrugMapper.selectByExampleAndRowBounds(ex, rowBounds);
		
		return list;
	}

	public StoreDrug modifyPrice(Integer storeid, Integer price, Long storedrugId) {
		StoreDrug storeDrug = storeDrugMapper.selectByPrimaryKey(storedrugId);
		if(storeDrug.getStoreid() != storeid){
			throw new HandleException(ErrorCode.DOMAIN_ERROR, "你无权进行此操作");
		}
		storeDrug.setPrice(price);
		Store store = userClient.getStore(storeid).fetchOKData(Store.class);
		storeDrug.setSettlementprice(getInt(store.getRate()*price));//根据药品售价以及药店的费率计算服务费
		storeDrugMapper.updateByPrimaryKey(storeDrug);
		
		return storeDrug;
	}
	
	public List<StoreAndDrugInfo> getStoreByDrugs(List<Integer> drugidList, double lat, double lon, int size){
		int moreSize = size+1;
		StringBuffer idsBuffer = new StringBuffer();
		idsBuffer.append("(");
		for(int i=0; i<drugidList.size(); i++){
			if(i!=0){
				idsBuffer.append(",");
			}
			idsBuffer.append(drugidList.get(i));
		}
		idsBuffer.append(")");
		
		String drugids = idsBuffer.toString();
		List<StoreAndDrugInfo> ret = storeDrugMapper.getStoreByDrugs(drugids, lat, lon, moreSize);
		if(!ret.isEmpty()){
			if(ret.get(0).getId() != null && ret.size() > size){
				ret.remove(ret.size()-1); //若所有药品都找到对应的药店,则移除多搜索的一个结果
			}
		}
		return ret;
		
		
	}

	public void del(int storeid, int drugid, int storeDrugId) {
		Example ex = new Example(StoreDrug.class);
		ex.createCriteria().andEqualTo("storeid", storeid).andEqualTo("drugid", drugid).andEqualTo("id", storeDrugId);
		int ret = storeDrugMapper.deleteByExample(ex);
		if(ret!=1) {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "删除失败");
		}
	}
}
