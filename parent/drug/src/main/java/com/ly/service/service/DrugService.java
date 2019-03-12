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
import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
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
		if(ret!=1) {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "删除失败");
		}
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
		if(opRet!=1) {
			throw new HandleException(ErrorCode.NORMAL_ERROR,"更新失败");
		}
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

	public void addTag(int drugid, int tagid) {
		DrugTagMap map = new DrugTagMap();
		map.setDrugid(drugid);
		map.setTagid(tagid);
		tagMapMapper.insert(map);
		return;
	}

	public DrugTag insertTag(String tag) {
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

	public void delTag(Integer drugid, Integer tagid) {
		Example ex = new Example(DrugTagMap.class);
		ex.createCriteria().andEqualTo("drugid", drugid).andEqualTo("tagid", tagid);
		int opRet = tagMapMapper.deleteByExample(ex);
		if(opRet != 1) {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "删除失败");
		}
		return;
	}
	
	public List<DrugTagInfo> getTagsByDrugId(int targetid){
		return tagMapMapper.getTagsByDrugId(targetid);
	}

	public List<DrugTagInfo> getAllTagsByDrug(Integer drugid) {
		List<DrugTagInfo> ret = tagMapMapper.getAllTagsByDrugId(drugid);
		return ret;
	}
}
