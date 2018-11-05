package com.ly.service.controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.Order;
import com.ly.service.feign.client.SalesRecordClient;
import com.ly.service.service.SalesRecordService;
import com.ly.service.utils.JSONUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api("销售记录内部接口")
@RequestMapping("/internal")
public class SalesRecordClientImpl implements SalesRecordClient{

	@Autowired
	SalesRecordService recordService;
	
	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/createByStore", method = RequestMethod.POST)
	@ApiOperation(value = "获取销售清单", notes = "获取销售清单")
	public Response createByStore(@ApiParam(name="storeid", value="药房id") @RequestParam(name="storeid") int storeid,
			@ApiParam(name="orderStr", value="购买订单") @RequestParam(name="orderStr") String orderStr) {
		
		Order order = null;
		try {
			order = JSONUtils.getObjectByJson(orderStr, Order.class);
		} catch (Exception e) {
			return Response.Error(ErrorCode.DATA_ERROR, "内部数据异常");
		}
		
		try{
			recordService.createByStore(storeid, order);
			return Response.OK(null);
		} catch(HandleException e){
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
		
	}

	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/createByOnlinePay", method = RequestMethod.POST)
	@ApiOperation(value = "创建销售记录", notes = "创建销售记录")
	public Response createByOnlinePay(String orderStr) {
		Order order = null;
		try {
			order = JSONUtils.getObjectByJson(orderStr, Order.class);
		} catch (Exception e) {
		    return Response.Error(ErrorCode.DATA_ERROR, "内部数据异常");
		}
		try{
			recordService.createByOnlinePay(order);
			return Response.OK(null);
		} catch(HandleException e){
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}

}
