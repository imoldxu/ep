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
import com.ly.service.context.TransactionDrug;
import com.ly.service.entity.Order;
import com.ly.service.feign.client.OrderClient;
import com.ly.service.service.OrderService;
import com.ly.service.utils.JSONUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
@RequestMapping("/internal")
@Api("订单系统内部接口")
public class OrderClientImpl implements OrderClient{

	@Autowired
	OrderService orderService;
	
	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ApiOperation(value = "创建订单", notes = "创建订单")
	public Response create(@ApiParam(name="uid", value = "用户id") @RequestParam(name="uid") int uid,
			@ApiParam(name="amount", value = "订单金额") @RequestParam(name="amount") int amount,
			@ApiParam(name="transactionListStr", value = "交易清单") @RequestParam(name="transactionListStr") String transactionListStr) {
		
		List<TransactionDrug> transactionList = null;
		try{
			transactionList = JSONUtils.getObjectListByJson(transactionListStr, TransactionDrug.class);
		}catch (Exception e) {
			return Response.Error(ErrorCode.ARG_ERROR, "请求参数错误");
		}
		try{
			Order order = orderService.create(uid, amount, transactionList);
			return Response.OK(order);
		}catch(HandleException e){
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}

	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/createByStore", method = RequestMethod.POST)
	@ApiOperation(value = "创建订单", notes = "创建订单")
	public Response createByStore(@ApiParam(name="storeid", value = "店铺id") @RequestParam(name="storeid")int storeid,
			@ApiParam(name="uid", value = "用户id") @RequestParam(name="uid") int uid,
			@ApiParam(name="transactionListStr", value = "交易清单") @RequestParam(name="transactionListStr") String transactionListStr) {
		
		List<TransactionDrug> transactionList = null;
		try{
			transactionList = JSONUtils.getObjectListByJson(transactionListStr, TransactionDrug.class);
		}catch (Exception e) {
			return Response.Error(ErrorCode.DATA_ERROR, "内部数据错误");
		}
		try{
			Order order = orderService.createByStore(uid, storeid, transactionList);
			return Response.OK(order);
		}catch(HandleException e){
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
		
	}
}
