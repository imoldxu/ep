package com.ly.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.entity.Patient;
import com.ly.service.entity.User;
import com.ly.service.mapper.PatientMapper;
import com.ly.service.utils.IdCardUtil;
import com.ly.service.utils.ValidDataUtil;

import tk.mybatis.mapper.entity.Example;

@Service
public class PatientService {

	@Autowired
	PatientMapper patientMapper;
	
	public Patient add(int uid, String name, String sex, String phone, int idcardtype, String idcardnum, String birthday){
		if(idcardtype != User.TYPE_IDCARD && idcardtype != User.TYPE_JG){
			throw new HandleException(ErrorCode.ARG_ERROR, "证件类型异常,请检查客户端");
		}
		if(idcardtype==User.TYPE_IDCARD){
			if(!IdCardUtil.isIDCard(idcardnum)){
				throw new HandleException(ErrorCode.NORMAL_ERROR, "身份证号有误,请检查");
			}
		}
		if(!ValidDataUtil.isPhone(phone)){
			throw new HandleException(ErrorCode.NORMAL_ERROR, "手机号有误,请检查"); 
		}
		
		Example ex = new Example(Patient.class);
		ex.createCriteria().andEqualTo("userid", uid).andEqualTo("name", name);
		Patient p = patientMapper.selectOneByExample(ex);
		if(p != null){
			//患者已经存在则不做任何处理
			throw new HandleException(ErrorCode.NORMAL_ERROR, "患者已存在,请直接编辑即可");
		}
		
		p = new Patient();
		p.setUserid(uid);
		p.setName(name);
		p.setPhone(phone);
		p.setIdcardnum(idcardnum);
		p.setIdcardtype(idcardtype);
		if(idcardtype==Patient.TYPE_IDCARD){
			p.setBirthday(IdCardUtil.getBirthday(idcardnum));
			p.setSex(IdCardUtil.getSex(idcardnum));
		}else{
			p.setBirthday(birthday);
			p.setSex(sex);
		}
		
		patientMapper.insertUseGeneratedKeys(p);
		return p;
	}
	
	public int del(int uid, int pid){
		Example ex = new Example(Patient.class);
		ex.createCriteria().andEqualTo("userid", uid).andEqualTo("id", pid);
		int ret = patientMapper.deleteByExample(ex);
		return ret;
	}
	
	public int update(int uid, Patient p){
		if(uid != p.getUserid()){
			throw new HandleException(ErrorCode.ARG_ERROR, "你无权操作此记录");
		}
		if(p.getIdcardtype() != User.TYPE_IDCARD && p.getIdcardtype() != User.TYPE_JG){
			throw new HandleException(ErrorCode.ARG_ERROR, "证件类型异常,请检查客户端");
		}
		if(p.getIdcardtype()==User.TYPE_IDCARD){
			if(!IdCardUtil.isIDCard(p.getIdcardnum())){
				throw new HandleException(ErrorCode.NORMAL_ERROR, "身份证号有误,请检查");
			}
		}
		if(!ValidDataUtil.isPhone(p.getPhone())){
			throw new HandleException(ErrorCode.NORMAL_ERROR, "手机号有误,请检查"); 
		}
		
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
