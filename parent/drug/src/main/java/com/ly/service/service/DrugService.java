package com.ly.service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ly.service.mapper.DrugMapper;
import com.ly.service.mapper.TagMapMapper;
import com.ly.service.mapper.TagMapper;

import tk.mybatis.mapper.entity.Example;

import com.ly.service.context.SimpleDrugInfo;
import com.ly.service.context.DrugTagInfo;
import com.ly.service.entity.Drug;
import com.ly.service.entity.DrugTag;
import com.ly.service.entity.DrugTagMap;

@Service
public class DrugService {

	@Autowired
	DrugMapper drugMapper;
	@Autowired
	TagMapper tagMapper;
	@Autowired
	TagMapMapper tagMapMapper;
	
	public Drug getDrugById(Integer id){
		Drug drug = drugMapper.selectByPrimaryKey(id);
		List<DrugTagInfo> tags = getTagsByDrugId(drug.getId());
		drug.setTags(tags);
		return drug;
	}
	
	public int del(Integer id){
		int ret = drugMapper.deleteByPrimaryKey(id);
		//TODO 抛出删除药品事件
		return ret;
	}
	
	public List<Drug> getDrugListByKeys(String keys){
		List<Drug> ret = new ArrayList<Drug>();

		keys = keys.toUpperCase();
		
		Example ex = new Example(Drug.class);
		ex.or().andLike("drugname", "%"+keys+"%");
		ex.or().andLike("fullkeys", "%"+keys+"%");
		ex.or().andLike("shortnamekeys", "%"+keys+"%");
		ret = drugMapper.selectByExample(ex);
		
		return ret;
	}
	
	public List<SimpleDrugInfo> getSimpleDrugListByTag(int type, String tag){
		List<SimpleDrugInfo> ret = null;
		if(type==1){
			ret = drugMapper.getDrugByTag(tag);
		}else{
			ret = drugMapper.getZyDrugByTag(tag);	
		}
		//TODO 先根据tag获取tag标签id，再获取该标签下的药品id,再根据药品id获取药品信息，重新写联合查询的sql
		return ret;
	}
	
	public List<SimpleDrugInfo> getSimpleDrugListByKeys(int type, String keys){
		List<SimpleDrugInfo> ret = new ArrayList<SimpleDrugInfo>();
		
		if(type == 1){
			keys = keys.toUpperCase();
			List<SimpleDrugInfo> matchList = drugMapper.getDrugsByKeys(keys);
			if(matchList.isEmpty()){
				ret = drugMapper.getDrugsByKeys("%"+keys+"%");	
			}else{
				ret.addAll(matchList);
				Integer myid = matchList.get(0).getId();
				List<SimpleDrugInfo> druglist = drugMapper.getDrugsByKeysWithoutID(myid, "%"+keys+"%");
				ret.addAll(druglist);
			}
		}else{
			keys = keys.toUpperCase();
			List<SimpleDrugInfo> matchList = drugMapper.getZyDrugsByKeys(keys);
			if(matchList.isEmpty()){
				ret = drugMapper.getZyDrugsByKeys("%"+keys+"%");	
			}else{
				ret.addAll(matchList);
				Integer myid = matchList.get(0).getId();
				List<SimpleDrugInfo> druglist = drugMapper.getZyDrugsByKeysWithoutID(myid, "%"+keys+"%");
				ret.addAll(druglist);
			}
		}
		
		return ret;
	}
	
	public int modify(Drug drug){
		int opRet = drugMapper.updateByPrimaryKey(drug);
		//TODO:tag的更新处理，直接调用del和add，不在此处处理
		return opRet;
	}
	
	@Transactional
	public int uploadDrugList(List<Drug> drugList){
    	//drugMapper.insertDrugs(drugList);
		int opRet = drugMapper.insertList(drugList);
		return opRet;
	}

	public List<DrugTag> getTags() {
		return tagMapper.selectAll();
	}

	public void addTag(int drugid, int tagid, String tag) {
		if(tagid>0){
			DrugTagMap map = new DrugTagMap();
			map.setDrugid(drugid);
			map.setTagid(tagid);
			tagMapMapper.insert(map);
		}else{
			DrugTag t = insertTag(tag);
			
			DrugTagMap map = new DrugTagMap();
			map.setDrugid(drugid);
			map.setTagid(t.getId());
			tagMapMapper.insert(map);
		}
	}

	private DrugTag insertTag(String tag) {
		DrugTag t = getTagByName(tag);
		if(t==null){
		    t = new DrugTag();
			t.setTag(tag);
			tagMapper.insertUseGeneratedKeys(t);
		}
		return t;
	}
	
	private DrugTag getTagByName(String name){
		Example ex = new Example(DrugTag.class);
		ex.createCriteria().andEqualTo("tag", name);
		DrugTag tag = tagMapper.selectOneByExample(ex);
		return tag;
	}

	public void delTag(long tagMapid) {
		tagMapMapper.deleteByPrimaryKey(tagMapid);
	}
	
	public List<DrugTagInfo> getTagsByDrugId(int targetid){
		return tagMapMapper.getTagsByDrugId(targetid);
	}
}
