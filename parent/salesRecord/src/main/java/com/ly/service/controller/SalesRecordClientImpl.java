package com.ly.service.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ly.service.entity.Order;
import com.ly.service.feign.client.SalesRecordClient;
import com.ly.service.service.SalesRecordService;

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
	public void createByStore(@ApiParam(name="storeid", value="药房id") @RequestParam(name="storeid") int storeid,
			@ApiParam(name="order", value="购买订单") @RequestParam(name="order") Order order) {
		
		recordService.createByStore(storeid, order);
		
		return;
	}

	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/createByOnlinePay", method = RequestMethod.POST)
	@ApiOperation(value = "创建销售记录", notes = "创建销售记录")
	public void createByOnlinePay(Order order) {
		
		recordService.createByOnlinePay(order);
		
	}

}
