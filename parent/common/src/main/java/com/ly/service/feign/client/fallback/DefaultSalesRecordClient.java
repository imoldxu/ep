package com.ly.service.feign.client.fallback;

import com.ly.service.entity.Order;

import org.springframework.stereotype.Component;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.feign.client.SalesRecordClient;

@Component
public class DefaultSalesRecordClient implements SalesRecordClient{

	
	@Override
	public Response createByStore(int storeid, String order) {
		return Response.Error(ErrorCode.MODULE_ERROR, "销售记录模块故障");
	}

	@Override
	public Response createByOnlinePay(String order) {
		return Response.Error(ErrorCode.MODULE_ERROR, "销售记录模块故障");
	}

}
