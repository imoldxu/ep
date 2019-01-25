package com.ly.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ly.service.context.SimpleDrugInfo;
import com.ly.service.entity.DoctorDrug;
import com.ly.service.mapper.DoctorDrugMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class DoctorDrugService {

	@Autowired
	DoctorDrugMapper drugMapper;

	public void addDoctorDrugs(Integer doctorid, List<Integer> drugidList) {
		for(Integer drugid: drugidList){
			Example dex = new Example(DoctorDrug.class);
			dex.createCriteria().andEqualTo("drugid", drugid).andEqualTo("doctorid", doctorid);
			List<DoctorDrug> doctorDrugs = drugMapper.selectByExample(dex);
			if(doctorDrugs.isEmpty()){
				DoctorDrug doctorDrug = new DoctorDrug();
				doctorDrug.setDoctorid(doctorid);
				doctorDrug.setDrugid(drugid);
				drugMapper.insert(doctorDrug);
			}
		}
	}
	
	public List<SimpleDrugInfo> getDrugsByDoctor(Integer hid, Integer doctorid, int type) {
		List<SimpleDrugInfo> ret;
		if(type==1){
			ret = drugMapper.getDrugsByDoctor(hid, doctorid);
		}else{
			ret = drugMapper.getZyDrugsByDoctor(hid, doctorid);
		}
		return ret;
	}
	
}
