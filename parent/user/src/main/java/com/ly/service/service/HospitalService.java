package com.ly.service.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.entity.Hospital;
import com.ly.service.mapper.HospitalMapper;

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
		h.setPassword(password);
		h.setLatitude(latitude);
		h.setLongitude(longitude);
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
		hospitalMapper.updateByPrimaryKeySelective(hospital);
		return hospital;
	}

	public Hospital getHospital(Integer hid) {
		Hospital ret = hospitalMapper.selectByPrimaryKey(hid);
		return ret;
	}
}
