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

import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.context.SimpleDrugInfo;
import com.ly.service.entity.DoctorDrug;
import com.ly.service.entity.Drug;
import com.ly.service.service.DoctorDrugService;
import com.ly.service.utils.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(description="医生的药品管理")
@RequestMapping("/doctor")
public class DoctorDrugController {

	@Autowired
	DoctorDrugService doctorDrugService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getDrugsByDoctor", method = RequestMethod.GET)
	@ApiOperation(value = "根据医生获取其常用药品", notes = "面向医院的通用的接口")
	public Response getDrugsByDoctor(
			@ApiParam(name = "hid", value = "医院ID") @RequestParam(name = "hid") Integer hid,
			@ApiParam(name = "doctorid", value = "医生ID") @RequestParam(name = "doctorid") Integer doctorid,
			@ApiParam(name = "type", value = "西药为1，中药为2") @RequestParam(name = "type") int type,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			List<SimpleDrugInfo> ret = doctorDrugService.getDrugsByDoctor(hid, doctorid, type);
			
			Response resp = Response.OK(ret);
			return resp;
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getDrugListByDoctor", method = RequestMethod.GET)
	@ApiOperation(value = "根据医生获取其常用药品", notes = "面向医生的接口")
	public Response getDrugListByDoctor(
			@ApiParam(name = "type", value = "西药为1，中药为2") @RequestParam(name = "type") int type,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			Integer doctorid = SessionUtil.getDoctorId(request);
			
			List<Drug> ret = doctorDrugService.getDrugListByDoctor(doctorid, type);
			
			Response resp = Response.OK(ret);
			return resp;
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/addDoctorDrug", method = RequestMethod.POST)
	@ApiOperation(value = "添加医生的药品", notes = "面向医生的接口")
	public Response addDoctorDrug(
			@ApiParam(name = "drugid", value = "药品id列表") @RequestParam(name = "drugid")Integer drugid,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			Integer doctorid = SessionUtil.getDoctorId(request);
			
			DoctorDrug ret = doctorDrugService.add(doctorid, drugid);
			
			Response resp = Response.OK(ret);
			return resp;
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/delDoctorDrug", method = RequestMethod.POST)
	@ApiOperation(value = "添加医生的药品", notes = "面向医生的接口")
	public Response delDoctorDrug(
			@ApiParam(name = "drugid", value = "药品id列表") @RequestParam(name = "drugid")Integer drugid,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			Integer doctorid = SessionUtil.getDoctorId(request);
			
			doctorDrugService.del(doctorid, drugid);
			
			Response resp = Response.OK(null);
			return resp;
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
}
