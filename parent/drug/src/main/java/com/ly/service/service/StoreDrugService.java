package com.ly.service.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.entity.StoreDrug;
import com.ly.service.mapper.StoreDrugMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class StoreDrugService {

	@Autowired
	StoreDrugMapper storeDrugMapper;
		
	
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
	
	public List<StoreDrug> getStoreDrugList(int storeid, int pageIndex, int pageSize){
		Example ex = new Example(StoreDrug.class);
		ex.createCriteria().andEqualTo("storeid", storeid);
		ex.setOrderByClause("id DESC");
		//分页处理
		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize, pageSize);
		List<StoreDrug> list= storeDrugMapper.selectByExampleAndRowBounds(ex, rowBounds);
		
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
		StoreDrug store = storeDrugMapper.selectOneByExample(ex);
		
		return store;
	}

	public StoreDrug add(int storeid, int drugid, String drugName, String stander ,String company, int price, int settlementPrice) {
		StoreDrug ret = getDrugByStore(storeid, drugid);
		if(ret != null){
			throw new HandleException(ErrorCode.NORMAL_ERROR, "该药品已经配置,无需重复配置");
		}
		
		ret = new StoreDrug();
		ret.setDrugcompany(company);
		ret.setDrugid(drugid);
		ret.setDrugname(drugName);
		ret.setDrugstander(stander);
		ret.setPrice(price);
		ret.setSettlementprice(settlementPrice);
		ret.setState(StoreDrug.STATE_UP);
		ret.setStoreid(storeid);
		
		storeDrugMapper.insertUseGeneratedKeys(ret);
		
		return ret;
	}

	public List<StoreDrug> getAllStoreDrugList(int pageIndex, int pageSize) {
		Example ex = new Example(StoreDrug.class);
		ex.setOrderByClause("id DESC");
		//分页处理
		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize, pageSize);
		List<StoreDrug> list= storeDrugMapper.selectByExampleAndRowBounds(ex, rowBounds);
		
		return list;
	}
}
