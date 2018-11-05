package com.ly.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.StoreDrug;
import com.ly.service.feign.client.StoreClient;
import com.ly.service.service.StoreDrugService;
import com.ly.service.utils.JSONUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/internal")
@Api("商户内部接口")
public class StoreClientImpl implements StoreClient{
	
	@Autowired
	StoreDrugService storeDrugService;
	
	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping("/getDrugsInStore")
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
			String retStr = JSONUtils.getJsonString(ret);
			return Response.OK(retStr);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}

	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping("/getDrugByStore")
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
}
