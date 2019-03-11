package com.ly.service.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

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
import com.ly.service.utils.JSONUtils;
import com.ly.service.utils.SessionUtil;
import com.ly.service.utils.ValidDataUtil;

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
	public Response register(@ApiParam(name="phone", value="手机号") @RequestParam(name="phone") String phone,
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
	@RequestMapping(path="/modifyPwd", method = RequestMethod.POST)
	@ApiOperation(value = "修改密码", notes = "医生调用")
	public Response modifyPwd(@ApiParam(name="oldPwd", value="旧密码") @RequestParam(name="oldPwd") String oldPwd,
			@ApiParam(name="newPwd", value="新密码") @RequestParam(name="newPwd") String newPwd,
			HttpServletRequest request, HttpServletResponse response){
		try{
			Integer doctorid = SessionUtil.getDoctorId(request);
			
			doctorService.modifyPwd(doctorid, oldPwd, newPwd);
			return Response.OK(null);
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
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/verifyCode", method = RequestMethod.POST)
	@ApiOperation(value = "验证手机验证码", notes = "医生接口")
	public Response verifyCode(@ApiParam(name="phone", value="登陆电话号码") @RequestParam(name="phone") String phone,
			@ApiParam(name="code", value="验证码") @RequestParam(name="code") String code,
			HttpServletRequest request, HttpServletResponse response){
		try{
			String authCode = doctorService.verifyCode(phone, code);
			
			return Response.OK(authCode);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/getVerifyCode", method = RequestMethod.GET)
	@ApiOperation(value = "获取身份验证码", notes = "医生接口，忘记密码，修改手机号可以调用")
	public Response getVerifyCode(@ApiParam(name="phone", value="登陆电话号码") @RequestParam(name="phone") String phone,
			HttpServletRequest request, HttpServletResponse response){
//		if(!ValidDataUtil.isPhone(phone)) {
//			return Response.Error(ErrorCode.ARG_ERROR, "手机号格式不正确");
//		}
		
		try{
			
			doctorService.getVerifyCode(phone);
			
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/resetPwd", method = RequestMethod.POST)
	@ApiOperation(value = "重置密码", notes = "医生接口")
	public Response resetPwd(@ApiParam(name="phone", value="登陆电话号码") @RequestParam(name="phone") String phone,
			@ApiParam(name="code", value="授权码") @RequestParam(name="code") String code,
			@ApiParam(name="newPwd", value="新密码") @RequestParam(name="newPwd") String newPwd,
			HttpServletRequest request, HttpServletResponse response){
		try{
			
			doctorService.resetPwd(phone, code, newPwd);
			
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/modifyPhone", method = RequestMethod.POST)
	@ApiOperation(value = "修改手机号", notes = "医生接口")
	public Response modifyPhone(@ApiParam(name="phone", value="登陆电话号码") @RequestParam(name="phone") String phone,
			@ApiParam(name="pwd", value="登陆密码") @RequestParam(name="pwd") String pwd,
			HttpServletRequest request, HttpServletResponse response){
//		if(!ValidDataUtil.isPhone(phone)) {
//			return Response.Error(ErrorCode.ARG_ERROR, "手机号格式不正确");
//		}
		
		try{
			Integer doctorid = SessionUtil.getDoctorId(request);
			
			Doctor doctor = doctorService.modifyPhone(doctorid, phone, pwd);
			
			return Response.OK(doctor);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/getDoctors", method = RequestMethod.GET)
	@ApiOperation(value = "查询医生", notes = "管理接口")
	public Response getDoctors(@ApiParam(name="phone", value="电话号码") @RequestParam(name="phone") String phone,
			@ApiParam(name="name", value="姓名") @RequestParam(name="name") String name,
			@ApiParam(name="hospitalid", value="医院id") @RequestParam(name="hospitalid") Integer hospitalid,
			@ApiParam(name="pageIndex", value="页码") @RequestParam(name="pageIndex") Integer pageIndex,
			@ApiParam(name="pageSize", value="每页最大数量") @RequestParam(name="pageSize") Integer pageSize,
			HttpServletRequest request, HttpServletResponse response){		
		try{
			SessionUtil.getManagerId(request);
			
			List<Doctor> doctors = doctorService.getDoctors(phone, name, hospitalid, pageIndex, pageSize);
			
			return Response.OK(doctors);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/add", method = RequestMethod.POST)
	@ApiOperation(value = "添加医生", notes = "管理接口")
	public Response getDoctors(@ApiParam(name="phone", value="电话号码") @RequestParam(name="phone") String phone,
			@ApiParam(name="name", value="姓名") @RequestParam(name="name") String name,
			@ApiParam(name="department", value="科室") @RequestParam(name="department") String department,
			@ApiParam(name="hospitalid", value="医院id") @RequestParam(name="hospitalid") Integer hospitalid,
			@ApiParam(name="password", value="密码") @RequestParam(name="password") String password,
			HttpServletRequest request, HttpServletResponse response){		
		try{
			SessionUtil.getManagerId(request);
			
			Doctor doctor = doctorService.add(phone, password, name, department, hospitalid);
			
			return Response.OK(doctor);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/modify", method = RequestMethod.POST)
	@ApiOperation(value = "编辑医生", notes = "编辑接口")
	public Response modify(@ApiParam(name="jsdoctor", value="医生jsonStr") @RequestParam(name="jsdoctor") String jsdoctor,
			HttpServletRequest request, HttpServletResponse response){		
		try{
			SessionUtil.getManagerId(request);
			
			Doctor doctor = JSONUtils.getObjectByJson(jsdoctor, Doctor.class);
			
			doctor = doctorService.modify(doctor);
			
			return Response.OK(doctor);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/del", method = RequestMethod.POST)
	@ApiOperation(value = "删除医生", notes = "删除接口")
	public Response del(@ApiParam(name="doctorid", value="医生id") @RequestParam(name="doctorid") Integer doctorid,
			HttpServletRequest request, HttpServletResponse response){		
		try{
			SessionUtil.getManagerId(request);
			
			doctorService.del(doctorid);
			
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
}
