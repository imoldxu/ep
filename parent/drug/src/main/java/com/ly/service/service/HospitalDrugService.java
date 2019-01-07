package com.ly.service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ly.service.context.SimpleDrugInfo;
import com.ly.service.entity.HospitalDrug;
import com.ly.service.mapper.HospitalDrugMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class HospitalDrugService {

	@Autowired
	HospitalDrugMapper drugMapper;
	
	public HospitalDrug setHospitalDrug(int sellerid, String sellername, int drugid, String drugname, String drugstandard, String drugcompany, int hospitalid, String hospitalname, int sellfee){
		Example ex = new Example(HospitalDrug.class);
		ex.createCriteria().andEqualTo("sellerid", sellerid).andEqualTo("drugid", drugid).andEqualTo("hospitalid", hospitalid);
		HospitalDrug hdrug = drugMapper.selectOneByExample(ex);
		if(hdrug ==  null){
			hdrug = new HospitalDrug();
			hdrug.setHospitalid(hospitalid);
			hdrug.setHospitalname(hospitalname);
			hdrug.setDrugid(drugid);
			hdrug.setDrugname(drugname);
			hdrug.setDrugstandard(drugstandard);
			hdrug.setDrugcompany(drugcompany);
			hdrug.setSellerid(sellerid);
			hdrug.setSellername(sellername);
			hdrug.setSellfee(sellfee);
			drugMapper.insertUseGeneratedKeys(hdrug);
		}else{
			hdrug.setHospitalid(hospitalid);
			hdrug.setHospitalname(hospitalname);
			hdrug.setDrugid(drugid);
			hdrug.setDrugname(drugname);
			hdrug.setDrugstandard(drugstandard);
			hdrug.setDrugcompany(drugcompany);
			hdrug.setSellerid(sellerid);
			hdrug.setSellername(sellername);
			hdrug.setSellfee(sellfee);
			drugMapper.updateByPrimaryKey(hdrug);
		}
		return hdrug;
	}
	
	public List<HospitalDrug> getMyDrugList(int sellerid){
		Example ex = new Example(HospitalDrug.class);
		ex.createCriteria().andEqualTo("sellerid", sellerid);
		List<HospitalDrug> list = drugMapper.selectByExample(ex);
		return list;
	}

	public HospitalDrug getHospitalDrug(int drugid, int hospitalid) {
		Example ex = new Example(HospitalDrug.class);
		ex.createCriteria().andEqualTo("drugid", drugid).andEqualTo("hospitalid", hospitalid);
		HospitalDrug sd = drugMapper.selectOneByExample(ex);
		return sd;
	}

	public void modifyHospitalDrug(HospitalDrug hospitalDrug) {
		drugMapper.updateByPrimaryKey(hospitalDrug);
	}

	public void del(Long hdid) {
		drugMapper.deleteByPrimaryKey(hdid);
	}

	public List<SimpleDrugInfo> getSimpleDrugListByKeys(int type, String keys) {
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

	public List<SimpleDrugInfo> getSimpleDrugListByTag(int type, String tag) {
		List<SimpleDrugInfo> ret = null;
		//先根据tag获取tag标签id，再获取该标签下的药品id,再根据药品id获取药品信息
		if(type==1){
			ret = drugMapper.getDrugByTag(tag);
		}else{
			ret = drugMapper.getDrugByTag(tag);	
		}
		return ret;
	}
}
