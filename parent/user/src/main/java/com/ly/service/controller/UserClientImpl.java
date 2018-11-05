package com.ly.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.feign.client.UserClient;
import com.ly.service.service.PatientService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/internal")
@Api("用户接口")
public class UserClientImpl implements UserClient{

	@Autowired
	PatientService patientService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/addPatient", method = RequestMethod.POST)
	@ApiOperation(value = "添加患者信息", notes = "添加患者信息")
	public Response addPatient(@ApiParam(name = "uid", value = "用户编号") @RequestParam(name = "uid") Integer uid,
			@ApiParam(name = "name", value = "姓名") @RequestParam(name = "name") String name,
			@ApiParam(name = "sex", value = "性别") @RequestParam(name = "sex") String sex,	
			@ApiParam(name = "phone", value = "电话") @RequestParam(name = "phone") String phone,
			@ApiParam(name = "idcardtype", value = "证件类型") @RequestParam(name = "idcardtype") int idcardtype,
			@ApiParam(name = "idcardnum", value = "证件号") @RequestParam(name = "idcardnum") String idcardnum,
			@ApiParam(name = "birthday", value = "出生日期") @RequestParam(name = "birthday") String birthday) {
		try{
			patientService.add(uid, name, sex, phone, idcardtype, idcardnum, birthday);
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}

}
