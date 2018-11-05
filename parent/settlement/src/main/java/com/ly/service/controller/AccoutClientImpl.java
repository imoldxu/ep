package com.ly.service.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.ws.rs.WebApplicationException;

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
import com.ly.service.context.InternalErrorMessage;
import com.ly.service.context.Response;
import com.ly.service.entity.SalesRecord;
import com.ly.service.entity.SellerAccount;
import com.ly.service.entity.StoreAccount;
import com.ly.service.feign.client.AccountClient;
import com.ly.service.service.AccountService;
import com.ly.service.utils.JSONUtils;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/internal")
public class AccoutClientImpl implements AccountClient{

	@Autowired
	AccountService accountService;
	
	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/addSellerAccount", method = RequestMethod.POST)
	@ApiOperation(value = "增加销售人员的余额", notes = "增加销售人员的余额")
	public Response addSellerAccount(@ApiParam(name="sellerid", value="销售人员id") @RequestParam(name="sellerid") int sellerid,
			@ApiParam(name="amount", value="金额") @RequestParam(name="amount") int amount,
			@ApiParam(name="msg", value="记录信息") @RequestParam(name="msg") String msg) {
		try{
			SellerAccount ret = accountService.addSellerAccount(sellerid, amount, msg);
			return Response.OK(ret); 
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}

	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/reduceSellerAccount", method = RequestMethod.POST)
	@ApiOperation(value = "减少销售人员的余额", notes = "减少销售人员的余额")
	public Response reduceSellerAccount(@ApiParam(name="sellerid", value="销售人员id") @RequestParam(name="sellerid") int sellerid,
			@ApiParam(name="amount", value="金额") @RequestParam(name="amount") int amount,
			@ApiParam(name="msg", value="记录信息") @RequestParam(name="msg") String msg) {
		try{
			SellerAccount ret = accountService.reduceSellerAccount(sellerid, amount, msg);
			return Response.OK(ret); 
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}

	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/addStoreAccount", method = RequestMethod.POST)
	@ApiOperation(value = "增加店铺的余额", notes = "增加店铺的余额")
	public Response addStoreAccount(@ApiParam(name="storeid", value="药店id") @RequestParam(name="storeid") int storeid,
			@ApiParam(name="amount", value="金额") @RequestParam(name="amount") int amount,
			@ApiParam(name="msg", value="记录信息") @RequestParam(name="msg") String msg) { 
		try{
			StoreAccount ret = accountService.addStoreAccount(storeid, amount, msg);
			return Response.OK(ret); 
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}

	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/reduceStoreAccount", method = RequestMethod.POST)
	@ApiOperation(value = "减少店铺的余额", notes = "减少店铺的余额")
	public Response reduceStoreAccount(@ApiParam(name="storeid", value="药店id") @RequestParam(name="storeid") int storeid,
			@ApiParam(name="amount", value="金额") @RequestParam(name="amount") int amount,
			@ApiParam(name="msg", value="记录信息") @RequestParam(name="msg") String msg) {
		try{
			StoreAccount ret = accountService.reduceStoreAccount(storeid, amount, msg);
			return Response.OK(ret); 
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}

	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/settleSalesRecords", method = RequestMethod.POST)
	@ApiOperation(value = "结算交易记录", notes = "结算交易记录")
	public Response settleSalesRecords(@ApiParam(name="salesRecordsStr", value="销售记录") @RequestParam(name="salesRecordsStr") String salesRecordsStr) {
		
		List<SalesRecord> salesRecords;
		try {
			salesRecords = JSONUtils.getObjectListByJson(salesRecordsStr, SalesRecord.class);
		} catch (Exception e) {
			return Response.Error(ErrorCode.DATA_ERROR, "内部数据异常");
		} 
		
		try{
			accountService.settleSalesRecords(salesRecords);
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
}
