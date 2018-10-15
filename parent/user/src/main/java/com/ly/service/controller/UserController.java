package com.ly.service.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.Patient;
import com.ly.service.entity.User;
import com.ly.service.service.PatientService;
import com.ly.service.service.UserService;
import com.ly.service.utils.JSONUtils;
import com.ly.service.utils.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api("用户接口")
public class UserController{

	@Autowired
	UserService userService;
	@Autowired
	PatientService patientService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/loginByWx", method = RequestMethod.POST)
	@ApiOperation(value = "微信登录", notes = "微信登录")
	public Response loginByWx(
			@ApiParam(name = "wxCode", value = "微信授权码") @RequestParam(name = "wxCode") String wxCode,
			HttpServletRequest request, HttpServletResponse response) {
	
		if (StringUtils.isEmpty(wxCode)) {
			return Response.NormalError("未获得微信授权码");
		} else {
			try{
				User user = userService.loginByWx(wxCode);
				SessionUtil.setUserId(request, user.getId());
				return Response.OK(user);
			}catch (IOException e) {
				e.printStackTrace();
				return Response.NormalError(e.getMessage());
			}catch (Exception e){
				e.printStackTrace();
				return Response.SystemError();
			}
			
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ApiOperation(value = "注册", notes = "注册")
	public Response register(
			@ApiParam(name = "phone", value = "电话号码") @RequestParam(name = "phone") String phone,
			@ApiParam(name = "password", value = "密码") @RequestParam(name = "password") String password,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			userService.register(phone, password);
			
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(value = "账号登录", notes = "账号登录")
	public Response login(
			@ApiParam(name = "phone", value = "电话号码") @RequestParam(name = "phone") String phone,
			@ApiParam(name = "password", value = "密码") @RequestParam(name = "password") String password,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			User user = userService.login(phone, password);
			SessionUtil.setUserId(request, user.getId());
			return Response.OK(user);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
	@ApiOperation(value = "更新用户信息", notes = "更新用户信息")
	public Response updateInfo(
			@ApiParam(name = "name", value = "姓名") @RequestParam(name = "name") String name,
			@ApiParam(name = "phone", value = "电话") @RequestParam(name = "phone") String phone,
			@ApiParam(name = "idcardtype", value = "证件类型") @RequestParam(name = "idcardtype") int idcardtype,
			@ApiParam(name = "idcardnum", value = "证件号") @RequestParam(name = "idcardnum") String idcardnum,
			HttpServletRequest request, HttpServletResponse response) {
	
		try{
			int uid = SessionUtil.getUserId(request);
			
			userService.updateInfo(uid, name, phone, idcardtype, idcardnum);
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}

	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/addPatient", method = RequestMethod.POST)
	@ApiOperation(value = "添加患者信息", notes = "添加患者信息")
	public Response addPatient(
			@ApiParam(name = "name", value = "姓名") @RequestParam(name = "name") String name,
			@ApiParam(name = "sex", value = "性别") @RequestParam(name = "sex") String sex,	
			@ApiParam(name = "phone", value = "电话") @RequestParam(name = "phone") String phone,
			@ApiParam(name = "idcardtype", value = "证件类型") @RequestParam(name = "idcardtype") int idcardtype,
			@ApiParam(name = "idcardnum", value = "证件号") @RequestParam(name = "idcardnum") String idcardnum,
			@ApiParam(name = "birthday", value = "出生日期") @RequestParam(name = "birthday") String birthday,
			HttpServletRequest request, HttpServletResponse response) {
	
		try{
			int uid = SessionUtil.getUserId(request);
			Patient p = patientService.add(uid, name, sex, phone, idcardtype, idcardnum, birthday);
			return Response.OK(p);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/delPatient", method = RequestMethod.POST)
	@ApiOperation(value = "添加患者信息", notes = "添加患者信息")
	public Response delPatient(
			@ApiParam(name = "pid", value = "pid") @RequestParam(name = "pid") int pid,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			int uid = SessionUtil.getUserId(request);
			
			int ret = patientService.del(uid, pid);
			if(ret==0){
				return Response.NormalError("删除失败");	
			}else{
				return Response.OK(null);
			}
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/updatePatient", method = RequestMethod.POST)
	@ApiOperation(value = "添加患者信息", notes = "添加患者信息")
	public Response updatePatient(
			@ApiParam(name = "patient", value = "patient") @RequestParam(name = "patient") String patient,
			HttpServletRequest request, HttpServletResponse response) {
		Patient p;
		try{
			p = JSONUtils.getObjectByJson(patient, Patient.class);
		}catch (Exception e) {
			return Response.Error(ErrorCode.ARG_ERROR, "参数错误");
		}
		
		try{
			int uid = SessionUtil.getUserId(request);
			
			int ret = patientService.update(uid, p);
			if(ret==1){
				return Response.OK(patient);	
			}else{
				return Response.NormalError("更新失败");
			}
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getMyPatientList", method = RequestMethod.GET)
	@ApiOperation(value = "添加患者信息", notes = "添加患者信息")
	public Response getMyPatientList(
			HttpServletRequest request, HttpServletResponse response) {
		try{
			int uid = SessionUtil.getUserId(request);
			List<Patient> list = patientService.getMyPatientList(uid);
			return Response.OK(list);	
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}

}
