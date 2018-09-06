package com.ly.service.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.expression.ReloadableResourceBundleExpressionSource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.Order;
import com.ly.service.service.OrderService;
import com.ly.service.utils.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api("订单系统")
public class OrderController{

	@Autowired
	OrderService orderService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getPayToken", method = RequestMethod.POST)
	@ApiOperation(value = "获取支付凭证", notes = "获取支付凭证")
	public Response getPayToken(@ApiParam(name="payWay", value = "支付方式") @RequestParam(name="payWay") int payWay,
			@ApiParam(name="oid", value = "订单id") @RequestParam(name="oid") Long oid) {
		try{
			orderService.getPayToken(payWay, oid);
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.Error(Response.ERROR, "系统异常");		
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/notify", method = RequestMethod.POST)
	@ApiOperation(value = "创建支付通知", notes = "创建支付通知")
	public void notify(@ApiParam(name="oid", value = "订单id") @RequestParam(name="oid") Long oid) {
		try{
			orderService.payOver(oid);
		}catch (HandleException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/charge", method = RequestMethod.POST)
	@ApiOperation(value = "店铺充值", notes = "店铺充值")
	public Response charge(@ApiParam(name="amount", value = "金额") @RequestParam(name="amount") int amount,
			HttpServletRequest request, HttpServletResponse reps) {
		try{
			int storeid = SessionUtil.getStoreId(request);
			Order order = orderService.createChargeOrder(storeid, amount);
			return Response.OK(order);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.Error(Response.ERROR, "系统异常");	
		}
	}
}
