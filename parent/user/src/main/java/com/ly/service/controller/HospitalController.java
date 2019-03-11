package com.ly.service.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.Hospital;
import com.ly.service.service.HospitalService;
import com.ly.service.utils.JSONUtils;
import com.ly.service.utils.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api("医院管理接口")
@RequestMapping("/hospital")
public class HospitalController {

	@Autowired
	HospitalService hospitalService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/getHospitalById", method = RequestMethod.GET)
	@ApiOperation(value = "根据id获取医院信息", notes = "根据id获取医院信息")
	public Response getHospitalById(@ApiParam(name="hid", value="医院id") @RequestParam(name="hid") int hid,
			 HttpServletRequest request, HttpServletResponse response){
		try{
			Hospital h = hospitalService.getHospitalById(hid);
			return Response.OK(h);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/addHospital", method = RequestMethod.POST)
	@ApiOperation(value = "添加医院", notes = "添加医院")
	public Response addHospital(@ApiParam(name="name", value="医院名称") @RequestParam(name="name") String name,
			@ApiParam(name="address", value="医院地址") @RequestParam(name="address") String address,
			@ApiParam(name="email", value="管理员email") @RequestParam(name="email") String email,
			@ApiParam(name="password", value="管理员密码") @RequestParam(name="password") String password,
			@ApiParam(name="latitude", value="纬度") @RequestParam(name="latitude") Double latitude,
			@ApiParam(name="longitude", value="经度") @RequestParam(name="longitude") Double longitude,
			 HttpServletRequest request, HttpServletResponse response){
		try{
			SessionUtil.getManagerId(request);
			
			Hospital h = hospitalService.addHospital(name, address, email, password, latitude, longitude);
			return Response.OK(h);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/resetPwd", method = RequestMethod.POST)
	@ApiOperation(value = "添加医院", notes = "添加医院")
	public Response resetPwd(@ApiParam(name="hospitalid", value="医院名称") @RequestParam(name="hospitalid") Integer hospitalid,
			 HttpServletRequest request, HttpServletResponse response){
		try{
			SessionUtil.getManagerId(request);
			
			hospitalService.resetPwd(hospitalid);
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/delHospital", method = RequestMethod.POST)
	@ApiOperation(value = "删除医院", notes = "删除医院")
	public Response delHospital(@ApiParam(name="hid", value="医院id") @RequestParam(name="hid") int hid,
			 HttpServletRequest request, HttpServletResponse response){
		try{
			SessionUtil.getManagerId(request);//确认是由管理员在操作
			
			boolean ret = hospitalService.delHospital(hid);
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/getHospitalList", method = RequestMethod.GET)
	@ApiOperation(value = "获取所有医院", notes = "通用接口")
	public Response getAllHospital(HttpServletRequest request, HttpServletResponse response){
		try{
			List<Hospital> ret = hospitalService.getAllHospital();
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/getHospitalsByName", method = RequestMethod.GET)
	@ApiOperation(value = "获取所有医院", notes = "获取所有医院")
	public Response getHospitalsByName(@ApiParam(name="name", value="医院名称") @RequestParam(name="name") String name,
			@ApiParam(name="pageIndex", value="页码 1- n") @RequestParam(name="pageIndex") Integer pageIndex,
			@ApiParam(name="pageSize", value="每页大小") @RequestParam(name="pageSize") Integer pageSize,
			HttpServletRequest request, HttpServletResponse response){
		try{
			SessionUtil.getManagerId(request);//确认是由管理员在操作
			
			List<Hospital> ret = hospitalService.getHospitalsByName(name, pageIndex, pageSize);
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/modifyHospital", method = RequestMethod.POST)
	@ApiOperation(value = "修改医院信息", notes = "管理接口")
	public Response modifyHospital(@ApiParam(name="jshospital", value="医院信息") @RequestParam(name="jshospital") String jshospital,
			HttpServletRequest request, HttpServletResponse response){
		
		Hospital hospital = null;
		try{
			hospital = JSONUtils.getObjectByJson(jshospital, Hospital.class);
		}catch (Exception e) {
			throw new HandleException(ErrorCode.ARG_ERROR, "参数 错误");
		}
		
		try{
			SessionUtil.getManagerId(request);//确认是由管理员在操作
			
			Hospital ret = hospitalService.modifyHospital(hospital);
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
}
