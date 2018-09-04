package com.ly.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ly.service.entity.HospitalDrug;
import com.ly.service.mapper.HospitalDrugMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class HospitalDrugService {

	@Autowired
	HospitalDrugMapper drugMapper;
	
	public void setHospitalDrug(int sellerid, String sellername, int drugid, String drugname, String drugstandard, String drugcompany, int hospitalid, String hospitalname, int sellfee){
		Example ex = new Example(HospitalDrug.class);
		ex.createCriteria().andEqualTo("sellerid", sellerid).andEqualTo("drugid", drugid).andEqualTo("hospitalid", hospitalid);
		HospitalDrug sd = drugMapper.selectOneByExample(ex);
		if(sd ==  null){
			sd = new HospitalDrug();
			sd.setHospitalid(hospitalid);
			sd.setHospitalname(hospitalname);
			sd.setDrugid(drugid);
			sd.setDrugname(drugname);
			sd.setDrugstandard(drugstandard);
			sd.setDrugcompany(drugcompany);
			sd.setSellerid(sellerid);
			sd.setSellername(sellername);
			sd.setSellfee(sellfee);
			drugMapper.insert(sd);
		}else{
			sd.setHospitalid(hospitalid);
			sd.setHospitalname(hospitalname);
			sd.setDrugid(drugid);
			sd.setDrugname(drugname);
			sd.setDrugstandard(drugstandard);
			sd.setDrugcompany(drugcompany);
			sd.setSellerid(sellerid);
			sd.setSellername(sellername);
			sd.setSellfee(sellfee);
			drugMapper.updateByPrimaryKey(sd);
		}
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
}
