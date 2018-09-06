package com.ly.service.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ly.service.entity.Hospital;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class HospitalController {

	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/getHospitalById", method = RequestMethod.GET)
	@ApiOperation(value = "微信登录", notes = "微信登录")
	public Hospital getHospitalById(@ApiParam(name="hid", value="医院id") @RequestParam(name="hid") int hid){
		
		return null;
	}
}
