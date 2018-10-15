package com.ly.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ly.service.context.Response;
import com.ly.service.entity.Hospital;
import com.ly.service.service.HospitalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api("医院管理接口")
public class HospitalController {

	@Autowired
	HospitalService hospitalService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/getHospitalById", method = RequestMethod.GET)
	@ApiOperation(value = "根据id获取医院信息", notes = "根据id获取医院信息")
	public Hospital getHospitalById(@ApiParam(name="hid", value="医院id") @RequestParam(name="hid") int hid){
		Hospital h = hospitalService.getHospitalById(hid);
		return h;
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/addHospital", method = RequestMethod.POST)
	@ApiOperation(value = "添加医院", notes = "添加医院")
	public Response addHospital(@ApiParam(name="name", value="医院名称") @RequestParam(name="name") String name,
			@ApiParam(name="email", value="管理员email") @RequestParam(name="email") String email,
			@ApiParam(name="password", value="管理员密码") @RequestParam(name="password") String password){
		Hospital h = hospitalService.addHospital(name, email, password);
		return Response.OK(h);
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/delHospital", method = RequestMethod.POST)
	@ApiOperation(value = "删除医院", notes = "删除医院")
	public Response delHospital(@ApiParam(name="hid", value="医院id") @RequestParam(name="hid") int hid){
		boolean ret = hospitalService.delHospital(hid);
		return Response.OK(ret);
	}
}
