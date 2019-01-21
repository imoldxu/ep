package com.yyg.eprescription.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyg.eprescription.entity.DoctorDrugs;
import com.yyg.eprescription.mapper.DoctorDrugsMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class DoctorDrugService {

	@Autowired
	DoctorDrugsMapper doctorDrugsMapper;
	
	public void add(Integer doctorid, Long drugid){
		//加入医生开药信息
		Example dex = new Example(DoctorDrugs.class);
		dex.createCriteria().andEqualTo("drugid", drugid).andEqualTo("doctorid", doctorid);
		List<DoctorDrugs> doctorDrugs = doctorDrugsMapper.selectByExample(dex);
		if(doctorDrugs.isEmpty()){
			DoctorDrugs doctorDrug = new DoctorDrugs();
			doctorDrug.setDoctorid(doctorid);
			doctorDrug.setDrugid(drugid);
			doctorDrugsMapper.insert(doctorDrug);
		}
	}
	
}
