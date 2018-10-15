package com.ly.service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ly.service.mapper.DrugMapper;
import com.ly.service.mapper.TagMapMapper;
import com.ly.service.mapper.TagMapper;

import tk.mybatis.mapper.entity.Example;

import com.ly.service.context.SimpleDrugInfo;
import com.ly.service.context.TagInfo;
import com.ly.service.entity.Drug;
import com.ly.service.entity.Tag;
import com.ly.service.entity.TagMap;

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
		List<TagInfo> tags = getTagsByTargetId(drug.getId());
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
			ret = drugMapper.getDrugByTag(tag);	
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
		return opRet;
	}
	
	public int uploadDrugList(List<Drug> drugList){
    	//drugMapper.insertDrugs(drugList);
		int opRet = drugMapper.insertList(drugList);
		return opRet;
	}

	public List<Tag> getTags() {
		return tagMapper.selectAll();
	}

	public void addTag(int drugid, int tagid, String tag) {
		if(tagid>0){
			TagMap map = new TagMap();
			map.setTargetid(drugid);
			map.setTagid(tagid);
			tagMapMapper.insert(map);
		}else{
			Tag t = insertTag(tag);
			
			TagMap map = new TagMap();
			map.setTargetid(drugid);
			map.setTagid(t.getId());
			tagMapMapper.insert(map);
		}
	}

	private Tag insertTag(String tag) {
		Tag t = getTagByName(tag);
		if(t==null){
		    t = new Tag();
			t.setTag(tag);
			tagMapper.insertUseGeneratedKeys(t);
		}
		return t;
	}
	
	private Tag getTagByName(String name){
		Example ex = new Example(Tag.class);
		ex.createCriteria().andEqualTo("tag", name);
		Tag tag = tagMapper.selectOneByExample(ex);
		return tag;
	}

	public void delTag(long tagMapid) {
		tagMapMapper.deleteByPrimaryKey(tagMapid);
	}
	
	public List<TagInfo> getTagsByTargetId(int targetid){
		return tagMapMapper.getTagsByTargetId(targetid);
	}
}
