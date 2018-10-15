package com.ly.service.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.Doctor;
import com.ly.service.service.DoctorService;
import com.ly.service.utils.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api("医生人员系统")
public class DoctorController {

	@Autowired
	DoctorService doctorService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/loginByWx", method = RequestMethod.POST)
	@ApiOperation(value = "微信登录", notes = "微信登录")
	public Response LoginByWx(@ApiParam(name="wxCode", value="微信授权码") @RequestParam(name="wxCode") String wxCode,
			HttpServletRequest request,
			HttpServletResponse response){
		
		try {
			Doctor doctor = doctorService.loginByWx(wxCode);
			SessionUtil.setDoctorId(request, doctor.getId());
			return Response.OK(doctor);
		} catch (IOException e) {
			return Response.Error(ErrorCode.LOGIN_ERROR, "微信登录网络故障");
		} catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
		
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/login", method = RequestMethod.GET)
	@ApiOperation(value = "账号登录", notes = "账号登录")
	public Response Login(@ApiParam(name="phone", value="微信授权码") @RequestParam(name="phone") String phone,
			@ApiParam(name="password", value="密码") @RequestParam(name="password") String password, HttpServletRequest request,
			HttpServletResponse response){
		try{
			Doctor doctor = doctorService.login(phone, password);
			SessionUtil.setDoctorId(request, doctor.getId());
			return Response.OK(doctor);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@RequestMapping(path="/register", method = RequestMethod.POST)
	public Response register(@ApiParam(name="phone", value="微信授权码") @RequestParam(name="phone") String phone,
			@ApiParam(name="password", value="密码") @RequestParam(name="password") String password){
		return null;
	}
	
	@RequestMapping(path="/bind", method = RequestMethod.POST)
	public Response bind(String phone, String password, String wxcode){
		return null;
	}
}
