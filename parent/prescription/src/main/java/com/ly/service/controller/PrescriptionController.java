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

import com.ly.service.context.TransactionDrug;
import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.context.SearchOption;
import com.ly.service.entity.Order;
import com.ly.service.entity.Prescription;
import com.ly.service.entity.PrescriptionDrug;
import com.ly.service.service.PrescriptionService;
import com.ly.service.utils.JSONUtils;
import com.ly.service.utils.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(description = "处方接口")
@RequestMapping("/prescription")
public class PrescriptionController {
	

	@Autowired
	PrescriptionService prescriptionService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/open", method = RequestMethod.POST)
	@ApiOperation(value = "医生在线开处方", notes = "医生接口")
	public Response open(
			@ApiParam(name = "perscription", value = "拼音首字母索引或药品名称") @RequestParam(name = "perscription") String perscription,
			@ApiParam(name = "drugList", value = "药品清单") @RequestParam(name = "drugList") String drugList,
			HttpServletRequest request, HttpServletResponse response) {
		Prescription p = null;
		List<PrescriptionDrug> list = null;
		try{
			p = JSONUtils.getObjectByJson(perscription, Prescription.class);
			list = JSONUtils.getObjectListByJson(drugList, PrescriptionDrug.class);
		}catch (Exception e) {
			return Response.Error(ErrorCode.ARG_ERROR, "参数错误");
		}
			
		try{
			Integer doctorid = SessionUtil.getDoctorId(request);
			prescriptionService.open(doctorid, p, list);
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}
	}

	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/commitByHosiptal", method = RequestMethod.POST)
	@ApiOperation(value = "从医院处方系统传入处方", notes = "医院接口")
	public Response commit(@ApiParam(name = "doctorid", value = "医生") @RequestParam(name = "doctorid") int doctorid,
			@ApiParam(name = "hospitalid", value = "医院编号") @RequestParam(name = "hospitalid") int hospitalid,
			@ApiParam(name = "perscription", value = "拼音首字母索引或药品名称") @RequestParam(name = "perscription") String perscription,
			@ApiParam(name = "drugList", value = "药品清单") @RequestParam(name = "drugList") String drugList,
			HttpServletRequest request, HttpServletResponse response) {
		
		//TODO:此处应该有安全机制认证医院的身份
		Prescription p = null;
		List<PrescriptionDrug> list = null;
		try{
			p = JSONUtils.getObjectByJson(perscription, Prescription.class);
			list = JSONUtils.getObjectListByJson(drugList, PrescriptionDrug.class);
		}catch (Exception e) {
			return Response.Error(ErrorCode.ARG_ERROR, "参数错误");
		}
		try{
			prescriptionService.commit(doctorid, hospitalid, p, list);
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();		
		}
	}
	
	//用户接口
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/receive", method = RequestMethod.POST)
	@ApiOperation(value = "用户领取处方", notes = "用户接口")
	public Response receive(
			@ApiParam(name = "perscriptionid", value = "处方编号") @RequestParam(name = "perscriptionid") Long pid,
			HttpServletRequest request, HttpServletResponse response) {
		
		try{
			Integer userid = SessionUtil.getUserId(request);
			
			prescriptionService.receive(userid, pid);
	
			return Response.OK(null);
			
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();		
		}
	}
	
	//管理工具用
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getPrescriptionList", method = RequestMethod.GET)
	@ApiOperation(value = "获取处方列表", notes = "管理接口")
	public Response getPrescriptionList(
			@ApiParam(name = "option", value = "查询条件") @RequestParam(name = "option") String option,
			HttpServletRequest request, HttpServletResponse respons) {
		respons.setHeader("Access-Control-Allow-Origin", "*");
		respons.setHeader("Access-Control-Allow-Methods", "GET");
		SearchOption searchOption = null;
		try{
			searchOption = JSONUtils.getObjectByJson(option, SearchOption.class);
		}catch (Exception e) {
			Response.Error(-1, "参数错误");
		}
		try{
			SessionUtil.getManagerId(request);
			
			List<Prescription> plist =  prescriptionService.getPrescriptionListByOption(searchOption);
			return Response.OK(plist);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();		
		}
	}
	
	//管理工具用
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getPrescriptionByID", method = RequestMethod.GET)
	@ApiOperation(value = "获取处方详情", notes = "管理接口")
	public Response getPrescriptionByID(
			@ApiParam(name = "pid", value = "处方id") @RequestParam(name = "pid") Integer pid,
			HttpServletRequest request, HttpServletResponse respons) {
		respons.setHeader("Access-Control-Allow-Origin", "*");
		respons.setHeader("Access-Control-Allow-Methods", "GET");
		try{
			SessionUtil.getManagerId(request);
			
			Prescription detail = prescriptionService.getPrescriptionDetail(pid);		
			return Response.OK(detail);		
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}

	//用户
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getUserPrescriptionList", method = RequestMethod.GET)
	@ApiOperation(value = "获取处方详情", notes = "用户接口")
	public Response getUserPrescriptionList(
			@ApiParam(name = "pageIndex", value = "页码") @RequestParam(name = "pageIndex") int pageIndex,
			@ApiParam(name = "pageSize", value = "最大数") @RequestParam(name = "pageSize") int pageSize,
			HttpServletRequest request, HttpServletResponse respons) {
		respons.setHeader("Access-Control-Allow-Origin", "*");
		respons.setHeader("Access-Control-Allow-Methods", "GET");
		try{
			Integer uid = SessionUtil.getUserId(request);
		    List<Prescription> list = prescriptionService.getPrescriptionListByUser(uid, pageIndex, pageSize);		
		    return Response.OK(list);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getPrescriptionByIDFromStore", method = RequestMethod.GET)
	@ApiOperation(value = "药房获取处方详情,只提供药房有的药品信息", notes = "药房接口")
	public Response getPrescriptionByIDFromStore(
			@ApiParam(name = "pid", value = "处方id") @RequestParam(name = "pid") Long pid,
			HttpServletRequest request, HttpServletResponse respons) {
		respons.setHeader("Access-Control-Allow-Origin", "*");
		respons.setHeader("Access-Control-Allow-Methods", "GET");
		try{
			Integer storeid = SessionUtil.getStoreId(request);
		    Prescription detail = prescriptionService.getPrescriptionDetailByStore(storeid, pid);		
		    return Response.OK(detail);		
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/buy", method = RequestMethod.POST)
	@ApiOperation(value = "用户买药", notes = "用户接口")
	public Response buy(
			@ApiParam(name = "pid", value = "处方id") @RequestParam(name = "pid") Long pid,
			@ApiParam(name = "drugList", value = "药品清单") @RequestParam(name = "drugList") String drugList,
			HttpServletRequest request, HttpServletResponse respons) {
		List<TransactionDrug> transDrugList = null;
		try{
			transDrugList = JSONUtils.getObjectListByJson(drugList, TransactionDrug.class);
		}catch (Exception e) {
			return Response.Error(ErrorCode.ARG_ERROR, "参数错误");
		}
		try{
			Integer uid = SessionUtil.getUserId(request);
			
			Order order = prescriptionService.buy(uid, pid, transDrugList);		
			return Response.OK(order);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/buyFromStore", method = RequestMethod.POST)
	@ApiOperation(value = "买药", notes = "药房接口")
	public Response buyFromStore(
			@ApiParam(name = "pid", value = "处方id") @RequestParam(name = "pid") Long pid,
			@ApiParam(name = "drugList", value = "药品清单") @RequestParam(name = "drugList") String drugList,
			HttpServletRequest request, HttpServletResponse respons) {
		List<TransactionDrug> transDrugList = null;
		try{
			transDrugList = JSONUtils.getObjectListByJson(drugList, TransactionDrug.class);
		}catch (Exception e) {
			return Response.Error(ErrorCode.ARG_ERROR, "参数错误");
		}
		
		try{
			Integer storeid = SessionUtil.getStoreId(request);
			Order order = prescriptionService.buyFromStore(storeid, pid, transDrugList);		
			return Response.OK(order);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}

	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getStorePrescriptions", method = RequestMethod.GET)
	@ApiOperation(value = "获取药房已经销售的处方列表", notes = "药房接口")
	public Response getStorePrescriptions(@ApiParam(name="pageIndex", value="页码") @RequestParam(name="pageIndex") int pageIndex,
			@ApiParam(name="pageSize", value="每页数量") @RequestParam(name="pageSize") int pageSize,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			Integer storeid = SessionUtil.getStoreId(request);
			
			List<Prescription> list = prescriptionService.getStorePrescriptions(storeid, pageIndex, pageSize);
			return Response.OK(list);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();		
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getStorePrescriptionDetail", method = RequestMethod.GET)
	@ApiOperation(value = "获取药房已经销售的处方列表", notes = "获取接口")
	public Response getStorePrescriptionDetail(@ApiParam(name="pid", value="处方id") @RequestParam(name="pid") Long pid,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			Integer storeid = SessionUtil.getStoreId(request);
			
			Prescription ret = prescriptionService.getStorePrescriptionDetail(storeid, pid);
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();		
		}
	}
}
