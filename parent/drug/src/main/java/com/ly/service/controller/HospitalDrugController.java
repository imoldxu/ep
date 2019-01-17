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

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.context.SimpleDrugInfo;
import com.ly.service.entity.HospitalDrug;
import com.ly.service.service.HospitalDrugService;
import com.ly.service.utils.JSONUtils;
import com.ly.service.utils.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(description="医院的药品管理")
@RequestMapping("/hospital")
public class HospitalDrugController {

	@Autowired
	HospitalDrugService drugService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/add", method = RequestMethod.POST)
	@ApiOperation(value = "设置医院销售的药品", notes = "管理接口")
	public Response add(@ApiParam(name="sellerid", value="销售编号") @RequestParam(name="sellerid") int sellerid,
			@ApiParam(name="sellername", value="销售名称") @RequestParam(name="sellername") String sellername,
			@ApiParam(name="drugid", value="药名id") @RequestParam(name="drugid") int drugid,
			@ApiParam(name="drugname", value="药品名称") @RequestParam(name="drugname") String drugname,
			@ApiParam(name="standard", value="药品规格") @RequestParam(name="standard") String standard,
			@ApiParam(name="company", value="药品厂商") @RequestParam(name="company") String company,
			@ApiParam(name="hospitalid", value="医院id") @RequestParam(name="hospitalid") int hospitalid,
			@ApiParam(name="hospitalname", value="医院名称") @RequestParam(name="hospitalname") String hospitalname,
			@ApiParam(name="sellfee", value="销售提成") @RequestParam(name="sellfee") int sellfee,
			HttpServletRequest request, HttpServletResponse response){
		try{
			SessionUtil.getManagerId(request);
			
			HospitalDrug ret = drugService.setHospitalDrug(sellerid, sellername, drugid, drugname, standard, company, hospitalid, hospitalname, sellfee);
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();		
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/modify", method = RequestMethod.POST)
	@ApiOperation(value = "更新医院的药品", notes = "管理接口")
	public Response modify(@RequestParam(name="drugStr") String drugStr,
			HttpServletRequest request, HttpServletResponse response){
		
		HospitalDrug hospitalDrug;
		try{
			hospitalDrug = JSONUtils.getObjectByJson(drugStr, HospitalDrug.class);
		}catch (Exception e) {
			return Response.Error(ErrorCode.ARG_ERROR, "参数异常");
		}
		
		try{
			SessionUtil.getManagerId(request);
			
			drugService.modifyHospitalDrug(hospitalDrug);
			return Response.OK(hospitalDrug);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();		
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/del", method = RequestMethod.POST)
	@ApiOperation(value = "更新销售的药品", notes = "管理接口")
	public Response del(@RequestParam(name="hdid") Long hdid,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			SessionUtil.getManagerId(request);
			
			drugService.del(hdid);
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();		
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getDrugsByKeys", method = RequestMethod.GET)
	@ApiOperation(value = "根据药品的拼音首字母缩写或药品名称搜索药品", notes = "面向医院的通用的接口")
	public Response getDrugsByKeys(
			@ApiParam(name = "hid", value = "医院ID") @RequestParam(name = "hid") Integer hid,
			@ApiParam(name = "keys", value = "拼音首字母索引或药品名称") @RequestParam(name = "keys") String keys,
			@ApiParam(name = "type", value = "西药为1，中药为2") @RequestParam(name = "type") int type,
			HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		try{
			List<SimpleDrugInfo> ret = drugService.getSimpleDrugListByKeys(hid, type, keys);
			
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
	@RequestMapping(value = "/getDrugListByTag", method = RequestMethod.GET)
	@ApiOperation(value = "根据标签搜索药品", notes = "面向医院的通用接口")
	public Response getDrugListByTag(
			@ApiParam(name = "hid", value = "医院ID") @RequestParam(name = "hid") Integer hid,
			@ApiParam(name = "tag", value = "标签") @RequestParam(name = "tag") String tag,
			@ApiParam(name = "type", value = "西药为1，中药为2") @RequestParam(name = "type") int type,
			HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		try{
			List<SimpleDrugInfo> ret = drugService.getSimpleDrugListByTag(hid, type, tag);
			
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
	@RequestMapping(value = "/getDrugListBySeller", method = RequestMethod.GET)
	@ApiOperation(value = "根据标签搜索药品", notes = "面向销售的接口")
	public Response getDrugListBySeller(
			@ApiParam(name = "key", value = "药品关键字") @RequestParam(name = "key") String key,
			@ApiParam(name = "pageIndex", value = "页码1-n") @RequestParam(name = "pageIndex") int pageIndex,
			@ApiParam(name = "pageSize", value = "每页最大数量") @RequestParam(name = "pageSize") int pageSize,
			HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		try{
			Integer sellerid = SessionUtil.getSellerId(request);
			
			List<HospitalDrug> ret = drugService.getHospitalDrugListBySeller(sellerid, key, pageIndex, pageSize);
			
			Response resp = Response.OK(ret);
			return resp;
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
}
