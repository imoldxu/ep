package com.ly.service.service;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.entity.Hospital;
import com.ly.service.mapper.HospitalMapper;
import com.ly.service.utils.PasswordUtil;

import tk.mybatis.mapper.entity.Example;


@Service
public class HospitalService {

	@Autowired
	HospitalMapper hospitalMapper;
	
	public Hospital getHospitalById(int hid) {
		Hospital h = hospitalMapper.selectByPrimaryKey(hid);
		return h;
	}

	public Hospital addHospital(String name, String address, String managerEmail, String password, Double latitude, Double longitude) {
		Hospital h = new Hospital();
		h.setEmail(managerEmail);
		h.setName(name);
		h.setAddress(address);
		
		String nonce = PasswordUtil.generateNonce();
		h.setPwdnonce(nonce);
		String newPwd = PasswordUtil.generatePwd(password, nonce);
		h.setPassword(newPwd);
		h.setLatitude(latitude);
		h.setLongitude(longitude);
		
		String secretkey = UUID.randomUUID().toString();
		h.setSecretkey(secretkey);
		
		hospitalMapper.insertUseGeneratedKeys(h);
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

	public List<Hospital> getAllHospital(Integer pageIndex, Integer pageSize) {
		Example ex = new Example(Hospital.class);
		ex.setOrderByClause("id DESC");
		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize,pageSize);
		List<Hospital> ret = hospitalMapper.selectByExampleAndRowBounds(ex, rowBounds);
		return ret;
	}

	public Hospital modifyHospital(Hospital hospital) {
		if(hospital.getId() == null){
			throw new HandleException(ErrorCode.ARG_ERROR, "参数错误");
		}
		hospital.setPassword(null);//不能修改密码
		hospitalMapper.updateByPrimaryKeySelective(hospital);
		return hospital;
	}

	public Hospital getHospital(Integer hid) {
		Hospital ret = hospitalMapper.selectByPrimaryKey(hid);
		return ret;
	}

	public List<Hospital> getAllHospital() {
		Example ex = new Example(Hospital.class);
		ex.setOrderByClause("id DESC");
		List<Hospital> ret = hospitalMapper.selectByExample(ex);
		return ret;
	
	}

	public List<Hospital> getHospitalsByName(String name, Integer pageIndex, Integer pageSize) {
		Example ex = new Example(Hospital.class);
		ex.createCriteria().andLike("name", "%"+name+"%");
		ex.setOrderByClause("id DESC");
		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize,pageSize);
		List<Hospital> ret = hospitalMapper.selectByExampleAndRowBounds(ex, rowBounds);
		return ret;
	}

	public void resetPwd(Integer hospitalid) {
		Hospital hospital = hospitalMapper.selectByPrimaryKey(hospitalid);
		
		String nonce = hospital.fetchPwdnonce();
		
		
		String clientPwd = PasswordUtil.generateClientPwd("123456");
		String newPwd = PasswordUtil.generatePwd(clientPwd, nonce);
		hospital.setPassword(newPwd);
		
		hospitalMapper.updateByPrimaryKey(hospital);
	}
}
