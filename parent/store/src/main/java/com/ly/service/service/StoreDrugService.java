package com.ly.service.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ly.service.context.HandleException;
import com.ly.service.entity.StoreDrug;
import com.ly.service.mapper.StoreDrugMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class StoreDrugService {

	@Autowired
	StoreDrugMapper storeDrugMapper;
		
	
	public void upDrug(int storeid, int drugid){
		Example ex = new Example(StoreDrug.class);
		ex.createCriteria().andEqualTo("drugid", drugid).andEqualTo("storeid", storeid);
		List<StoreDrug> list= storeDrugMapper.selectByExample(ex);
		if(list.isEmpty()){
			StoreDrug drug = new StoreDrug();
			drug.setDrugid(drugid);
			drug.setStoreid(storeid);
			drug.setState(1);
			storeDrugMapper.insert(drug);
		}else{
			StoreDrug drug = list.get(0);
			drug.setState(1);
			storeDrugMapper.updateByPrimaryKey(drug);
		}
		return ;
	}
	
	public void downDrug(int storeid, int drugid){		
		Example ex = new Example(StoreDrug.class);
		ex.createCriteria().andEqualTo("drugid", drugid).andEqualTo("storeid", storeid);
		List<StoreDrug> list= storeDrugMapper.selectByExample(ex);
		if(list.isEmpty()){
			throw new HandleException(-1, "药品不存在");
		}else{
			StoreDrug drug = list.get(0);
			drug.setState(0);
			storeDrugMapper.updateByPrimaryKey(drug);
		}
		return;
	}
	
	public List<StoreDrug> getStoreDrugList(int storeid, int pageIndex, int pageSize){
		Example ex = new Example(StoreDrug.class);
		ex.createCriteria().andEqualTo("storeid", storeid);
		//分页处理
		RowBounds rowBounds = new RowBounds(pageIndex*pageSize, pageSize);
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
}
