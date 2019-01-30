package com.ly.service.controller;

import java.io.IOException;
import java.util.Base64;

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
@RequestMapping("/doctor")
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
			String sessionID = request.getSession().getId();
			sessionID = Base64.getEncoder().encodeToString(sessionID.getBytes());
			doctor.setSessionID(sessionID);
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
	@RequestMapping(path="/login", method = RequestMethod.POST)
	@ApiOperation(value = "账号登录", notes = "账号登录")
	public Response Login(@ApiParam(name="phone", value="账号") @RequestParam(name="phone") String phone,
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
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/logout", method = RequestMethod.GET)
	@ApiOperation(value = "账号登出", notes = "账号登出")
	public Response Logout(HttpServletRequest request, HttpServletResponse response){
		try{
			SessionUtil.setDoctorId(request, null);
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/register", method = RequestMethod.POST)
	@ApiOperation(value = "注册账户", notes = "医生调用")
	public Response register(@ApiParam(name="phone", value="微信授权码") @RequestParam(name="phone") String phone,
			@ApiParam(name="password", value="密码") @RequestParam(name="password") String password,
			HttpServletRequest request, HttpServletResponse response){
		try{
			Doctor doctor = doctorService.register(phone, password);
			SessionUtil.setDoctorId(request, doctor.getId());
			return Response.OK(doctor);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/updateInfo", method = RequestMethod.POST)
	@ApiOperation(value = "更新医生信息", notes = "更新信息")
	public Response updateInfo(@ApiParam(name="hid", value="医院编号") @RequestParam(name="hid") Integer hid,
			@ApiParam(name="name", value="医生姓名") @RequestParam(name="name") String name,
			@ApiParam(name="department", value="医生科室") @RequestParam(name="department") String department,
			@ApiParam(name="signatureurl", value="医生签名") @RequestParam(name="signatureurl") String signatureurl,
			HttpServletRequest request, HttpServletResponse response){
		try{
			Integer doctorID = SessionUtil.getDoctorId(request);
			
			Doctor doctor = doctorService.updateInfo(doctorID, hid, name, department, signatureurl);
			
			SessionUtil.setDoctorId(request, doctor.getId());
			
			return Response.OK(doctor);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/bind", method = RequestMethod.POST)
	@ApiOperation(value = "绑定已有的账户", notes = "绑定已经有的账户,或者新设置一个账号和密码")
	public Response bind(@ApiParam(name="phone", value="账号") @RequestParam(name="phone") String phone,
			@ApiParam(name="password", value="密码") @RequestParam(name="password") String password,
			 HttpServletRequest request, HttpServletResponse response){
		try{
			Integer doctorID = SessionUtil.getDoctorId(request);
			
			Doctor doctor = doctorService.bind(doctorID, phone, password);
			
			SessionUtil.setDoctorId(request, doctor.getId());
			
			return Response.OK(doctor);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
}
