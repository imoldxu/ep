package com.ly.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.SimpleDrugInfo;
import com.ly.service.entity.Doctor;
import com.ly.service.entity.DoctorDrug;
import com.ly.service.entity.Drug;
import com.ly.service.feign.client.UserClient;
import com.ly.service.mapper.DoctorDrugMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class DoctorDrugService {

	@Autowired
	DoctorDrugMapper drugMapper;
	@Autowired
	UserClient userClient;

	public void addDoctorDrugs(Integer doctorid, List<Integer> drugidList) {
		for(Integer drugid: drugidList){
			add(doctorid, drugid);
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

	public List<Drug> getDrugListByDoctor(Integer doctorid, int type) {
		List<Drug> ret;
		
		ObjectMapper om = new ObjectMapper();
		Doctor doctor = om.convertValue(userClient.getDoctor(doctorid).fetchOKData(),Doctor.class);
		
		if(type==1){
			ret = drugMapper.getDrugListByDoctor(doctor.getHospitalid(), doctorid);
		}else{
			ret = drugMapper.getZyDrugListByDoctor(doctor.getHospitalid(), doctorid);
		}
		return ret;
	}

	public DoctorDrug add(Integer doctorid, Integer drugid) {
		Example dex = new Example(DoctorDrug.class);
		dex.createCriteria().andEqualTo("drugid", drugid).andEqualTo("doctorid", doctorid);
		List<DoctorDrug> doctorDrugs = drugMapper.selectByExample(dex);
		if(doctorDrugs.isEmpty()){
			DoctorDrug doctorDrug = new DoctorDrug();
			doctorDrug.setDoctorid(doctorid);
			doctorDrug.setDrugid(drugid);
			drugMapper.insert(doctorDrug);
			return doctorDrug;
		}else {
			return null;
		}
	}

	public void del(Integer doctorid, Integer drugid) {
		Example dex = new Example(DoctorDrug.class);
		dex.createCriteria().andEqualTo("drugid", drugid).andEqualTo("doctorid", doctorid);
		int ret = drugMapper.deleteByExample(dex);
		if(ret == 0) {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "删除失败");
		}
		return;
	}
	
}
