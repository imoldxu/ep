package com.ly.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.Drug;
import com.ly.service.entity.HospitalDrug;
import com.ly.service.entity.StoreDrug;
import com.ly.service.feign.client.DrugClient;
import com.ly.service.service.DrugService;
import com.ly.service.service.HospitalDrugService;
import com.ly.service.service.StoreDrugService;
import com.ly.service.utils.JSONUtils;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/internal")
public class DrugClientImpl implements DrugClient {

	@Autowired
	DrugService drugService;
	@Autowired
	HospitalDrugService hospitaldrugService;
	@Autowired
	StoreDrugService storeDrugService;
	
	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/getHospitalDrug", method = RequestMethod.GET)
	@ApiOperation(value = "获取销售员id", notes = "获取销售员id")
	public Response getHospitalDrug(@ApiParam(name = "drugid", value = "姓名") @RequestParam(name="drugid") int drugid,
			@ApiParam(name = "hospitalid", value = "医院id") @RequestParam(name="hospitalid") int hospitalid) {
		try{
			HospitalDrug ret = hospitaldrugService.getHospitalDrug(drugid, hospitalid);
			return Response.OK(ret);
		}catch(HandleException e){
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}

	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/getDrugsInStore", method = RequestMethod.GET)
	@ApiOperation(value = "获取该药店与处方匹配的药品", notes = "获取该药店与处方匹配的药品")
	public Response getDrugsInStore(@ApiParam(name = "storeid", value = "邮箱") @RequestParam(name = "storeid")Integer storeid,
			@ApiParam(name = "drugListStr", value = "药品清单") @RequestParam(name = "drugListStr") String drugListStr){
		List<Integer> drugList = null;
		try{
			drugList = JSONUtils.getObjectListByJson(drugListStr, Integer.class);
		}catch(Exception e){
			return Response.Error(ErrorCode.DATA_ERROR, "内部数据错误");
		}
		
		try{
			List<StoreDrug> ret = storeDrugService.getDrugsInStore(storeid, drugList);
			//String retStr = JSONUtils.getJsonString(ret);
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}

	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/getDrugByStore", method = RequestMethod.GET)
	@ApiOperation(value = "获取该药店对应的药品", notes = "获取该药店对应的药品")
	public Response getDrugByStore(@ApiParam(name = "storeid", value = "药房id") @RequestParam(name = "storeid") Integer storeid,
			@ApiParam(name = "drugid", value = "药品id") @RequestParam(name = "drugid") Integer drugid) {
		try{
			StoreDrug ret = storeDrugService.getDrugByStore(storeid, drugid);
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}

	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/getDrugsByKeys", method = RequestMethod.GET)
	@ApiOperation(value = "根据关键字获取药品", notes = "根据关键字获取药品")
	public Response getDrugsByKeys(@ApiParam(name = "keys", value = "关键字") @RequestParam(name = "keys")String keys,
			@ApiParam(name = "type", value = "类型，1为西药，2为中药") @RequestParam(name = "type")int type) {
		try{
			List<Drug> ret = drugService.getDrugListByKeys(keys);
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
}
