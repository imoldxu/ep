package com.yyg.eprescription.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yyg.eprescription.context.HandleException;
import com.yyg.eprescription.context.Response;
import com.yyg.eprescription.entity.Drug;
import com.yyg.eprescription.entity.ShortDrugInfo;
import com.yyg.eprescription.service.DoctorDrugService;
import com.yyg.eprescription.service.DrugService;
import com.yyg.eprescription.util.JSONUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/drug")
@Api(description = "药品接口")
public class DrugController {
	
	@Autowired
	DrugService drugService;
	@Autowired
	DoctorDrugService doctorDrugsService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getDrugsByKeys", method = RequestMethod.GET)
	@ApiOperation(value = "根据药品的拼音首字母缩写或药品名称搜索药品", notes = "根据药品的拼音首字母缩写或药品名称搜索药品")
	public Response getDrugsByKeys(
			@ApiParam(name = "keys", value = "拼音首字母索引或药品名称") @RequestParam(name = "keys") String keys,
			@ApiParam(name = "type", value = "西药为1，中药为2") @RequestParam(name = "type") int type,
			HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Methods", "GET");
		try{
			List<ShortDrugInfo> ret = drugService.getDrugsByKeys(keys, type);
			Response resp = new Response(Response.SUCCESS, ret, Response.SUCCESS_MSG);
			return resp;
		}catch(HandleException e){	
			return new Response(e.getErrorCode(), null, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getDrugInfoListByKeys", method = RequestMethod.GET)
	@ApiOperation(value = "根据药品的拼音缩写搜索药品", notes = "根据药品的拼音缩写搜索药品")
	public Response getDrugInfoListByKeys(
			@ApiParam(name = "keys", value = "拼音首字母索引") @RequestParam(name = "keys") String keys,
			HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Methods", "GET");
		try{
			List<Drug> ret = drugService.getDrugInfoListByKeys(keys);
			
			Response resp = new Response(Response.SUCCESS, ret, Response.SUCCESS_MSG);
			return resp;
		}catch(HandleException e){	
			return new Response(e.getErrorCode(), null, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getDrugInfoListByCategory", method = RequestMethod.GET)
	@ApiOperation(value = "根据分类搜索药品", notes = "根据分类搜索药品")
	public Response getDrugBySubCategory(
			@ApiParam(name = "category", value = "分类") @RequestParam(name = "category") String category,
			@ApiParam(name = "type", value = "西药为1，中药为2") @RequestParam(name = "type") int type,
			HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Methods", "GET");
		try{
			List<ShortDrugInfo> ret = drugService.getDrugBySubCategory(category, type);
			
			Response resp = new Response(Response.SUCCESS, ret, Response.SUCCESS_MSG);
			return resp;
		}catch(HandleException e){	
			return new Response(e.getErrorCode(), null, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getMyDrugInfoList", method = RequestMethod.GET)
	@ApiOperation(value = "获得我的常用药品", notes = "获得我的常用药品")
	public Response getMyDrugInfoList(
			@ApiParam(name = "doctorid", value = "医生编号") @RequestParam(name = "doctorid") Integer doctorid,
			@ApiParam(name = "type", value = "西药为1，中药为2") @RequestParam(name = "type") int type,
			HttpServletRequest request, HttpServletResponse response) {	
		response.setHeader("Access-Control-Allow-Methods", "GET");
		try{
			List<ShortDrugInfo> ret = drugService.getMyDrugInfoList(doctorid, type);
			
			Response resp = new Response(Response.SUCCESS, ret, Response.SUCCESS_MSG);
			return resp;
		}catch(HandleException e){	
			return new Response(e.getErrorCode(), null, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getDrugByID", method = RequestMethod.GET)
	@ApiOperation(value = "获取药品信息", notes = "获取药品信息")
	public Response getDrugByID(
			@ApiParam(name = "drugid", value = "药品id") @RequestParam(name = "drugid") Integer drugid,
			HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Methods", "GET");
		
		try{
			Drug drug = drugService.getDrugByID(drugid);
			return new Response(Response.SUCCESS, drug, Response.SUCCESS_MSG);
		}catch(HandleException e){	
			return new Response(e.getErrorCode(), null, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/uploadByExcel", method = RequestMethod.POST)
	@ApiOperation(value = "新增上传药品信息", notes = "新增上传药品信息")
	public Response uploadByExcel(@RequestPart(value="file") MultipartFile file,HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Methods", "POST");
		
		try{
	    	List<Drug> drugList =  drugService.uploadByExcel(file);
	    	return new Response(Response.SUCCESS, drugList, Response.SUCCESS_MSG);    
		}catch(HandleException e){	
			return new Response(e.getErrorCode(), null, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/modifyDrug", method = RequestMethod.POST)
	@ApiOperation(value = "修改药品信息", notes = "{     \"id\": 23,     \"drugname\": \"硫酸亚铁片\",     \"standard\": \"0.3g*60片\",     \"category\": \"OTC\",     \"price\": 38,     \"unit\": \"盒\",     \"form\": \"片剂\",     \"singledose\": \"1\",     \"doseunit\": \"片\",     \"defaultusage\": \"饭前\",     \"frequency\": \"一天三次\",     \"fullkeys\": \"LSYTP\",     \"shortnamekeys\": \"LSYTP\"   }")
	public Response modifyDrug(@RequestParam(value="drugInfo") String drugInfo,HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Methods", "POST");
		
		Response resp = null;
		
		Drug drug = JSONUtils.getObjectByJson(drugInfo, Drug.class);
		if (drug == null) {
			resp = new Response(Response.ERROR, null, "请求参数错误");
			return resp;
		}
		
		try{
			drugService.modifyDrug(drug);
			return new Response(Response.SUCCESS, drug, Response.SUCCESS_MSG);    
		}catch(HandleException e){	
			return new Response(e.getErrorCode(), null, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/delDrug", method = RequestMethod.POST)
	@ApiOperation(value = "删除药品信息", notes = "管理接口")
	public Response delDrug(@RequestParam(value="drugid") int drugid, HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Methods", "POST");
		
		try{
			drugService.delDrug(drugid);
			return new Response(Response.SUCCESS, null, "删除成功");
		}catch(HandleException e){	
			return new Response(e.getErrorCode(), null, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/downDrug", method = RequestMethod.POST)
	@ApiOperation(value = "下架药品", notes = "{     \"id\": 23,     \"drugname\": \"硫酸亚铁片\",     \"standard\": \"0.3g*60片\",     \"category\": \"OTC\",     \"price\": 38,     \"unit\": \"盒\",     \"form\": \"片剂\",     \"singledose\": \"1\",     \"doseunit\": \"片\",     \"defaultusage\": \"饭前\",     \"frequency\": \"一天三次\",     \"fullkeys\": \"LSYTP\",     \"shortnamekeys\": \"LSYTP\"   }")
	public Response downDrug(@RequestParam(value="drugid") int drugid, HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Methods", "POST");
		
		try{
			drugService.downDrug(drugid);
			return new Response(Response.SUCCESS, null, Response.SUCCESS_MSG);
		}catch(HandleException e){	
			return new Response(e.getErrorCode(), null, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/upDrug", method = RequestMethod.POST)
	@ApiOperation(value = "上架药品信息", notes = "上架药品信息")
	public Response upDrug(@RequestParam(value="drugid") int drugid, HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Methods", "POST");
		
		try{
			drugService.upDrug(drugid);
			return new Response(Response.SUCCESS, null, Response.SUCCESS_MSG);
		}catch(HandleException e){	
			return new Response(e.getErrorCode(), null, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
}
