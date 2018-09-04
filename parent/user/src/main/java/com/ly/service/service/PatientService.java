package com.ly.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ly.service.entity.Patient;
import com.ly.service.mapper.PatientMapper;
import com.ly.service.utils.IdCardUtil;

import tk.mybatis.mapper.entity.Example;

@Service
public class PatientService {

	@Autowired
	PatientMapper patientMapper;
	
	public Patient add(int uid, String name, String sex, String phone, int idcardtype, String idcardnum, String birthday){
		Example ex = new Example(Patient.class);
		ex.createCriteria().andEqualTo("userid", uid).andEqualTo("name", name);
		Patient p = patientMapper.selectOneByExample(ex);
		if(p != null){
			//患者已经存在则不做任何处理
			return p;
		}
		
		p = new Patient();
		p.setUserid(uid);
		p.setName(name);
		p.setPhone(phone);
		p.setIdcardnum(idcardnum);
		p.setIdcardtype(idcardtype);
		if(idcardtype==Patient.TYPE_IDCARD){
			p.setBirthday(IdCardUtil.getBirthday(idcardnum));
		}else{
			p.setBirthday(birthday);
		}
		
		patientMapper.insert(p);
		return p;
	}
	
	public int del(int uid, int pid){
		Example ex = new Example(Patient.class);
		ex.createCriteria().andEqualTo("userid", uid).andEqualTo("id", pid);
		int ret = patientMapper.deleteByExample(ex);
		return ret;
	}
	
	public int update(int uid, Patient p){
		int ret = patientMapper.updateByPrimaryKey(p);
		return ret;
	}
	
	public List<Patient> getMyPatientList(int uid){
		Example ex = new Example(Patient.class);
		ex.createCriteria().andEqualTo("userid", uid);
		List<Patient> ret = patientMapper.selectByExample(ex);
		return ret;
	}
}
