package com.yyg.eprescription.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yyg.eprescription.context.HandleException;
import com.yyg.eprescription.context.Response;
import com.yyg.eprescription.entity.Doctor;
import com.yyg.eprescription.proxy.PlatformProxy;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(description="医生代理")
@RequestMapping(path="/doctor")
public class DoctorController {

	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(value = "医生登录", notes = "")
	public Response login(@ApiParam(name="phone", value="手机号") @RequestParam(name="phone") String phone,
			@ApiParam(name="password", value="密码") @RequestParam(name="password") String password,
			HttpServletRequest request,HttpServletResponse response) {
		
		try {
			Doctor doctor = PlatformProxy.login(phone, password);
			return new Response(Response.SUCCESS, doctor, Response.SUCCESS_MSG);
		}catch(HandleException e){	
			return new Response(e.getErrorCode(), null, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
		
	}
	
	
}
