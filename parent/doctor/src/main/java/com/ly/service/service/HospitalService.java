package com.ly.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ly.service.entity.Hospital;
import com.ly.service.mapper.HospitalMapper;

@Service
public class HospitalService {

	@Autowired
	HospitalMapper hospitalMapper;
	
	public Hospital getHospitalById(int hid) {
		Hospital h = hospitalMapper.selectByPrimaryKey(hid);
		return h;
	}

	public Hospital addHospital(String name, String managerEmail, String password) {
		Hospital h = new Hospital();
		h.setEmail(managerEmail);
		h.setName(name);
		h.setPassword(password);
		hospitalMapper.insert(h);
		return h;
	}
	
	public boolean delHospital(int hid) {
		int ret = hospitalMapper.deleteByPrimaryKey(hid);
		if(ret != 0){
			return true;
		}else{
			return false;
		}
	}
}
