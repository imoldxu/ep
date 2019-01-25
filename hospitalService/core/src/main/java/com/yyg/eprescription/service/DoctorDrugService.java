package com.yyg.eprescription.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyg.eprescription.entity.DoctorDrug;
import com.yyg.eprescription.mapper.DoctorDrugMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class DoctorDrugService {

	@Autowired
	DoctorDrugMapper doctorDrugsMapper;
	
	public void add(Integer doctorid, Integer drugid){
		//加入医生开药信息
		Example dex = new Example(DoctorDrug.class);
		dex.createCriteria().andEqualTo("drugid", drugid).andEqualTo("doctorid", doctorid);
		List<DoctorDrug> doctorDrugs = doctorDrugsMapper.selectByExample(dex);
		if(doctorDrugs.isEmpty()){
			DoctorDrug doctorDrug = new DoctorDrug();
			doctorDrug.setDoctorid(doctorid);
			doctorDrug.setDrugid(drugid);
			doctorDrugsMapper.insert(doctorDrug);
		}
	}
	
}
