package com.ly.service.feign.client.fallback;

import org.springframework.stereotype.Component;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.Order;
import com.ly.service.feign.client.OrderClient;

@Component
public class DefaultOrderClient implements OrderClient {

	@Override
	public Response create(int uid, int amount, String transactionListStr) {
		return Response.Error(ErrorCode.MODULE_ERROR, "订单模块异常");
	}

	@Override
	public Response createByStore(int storeid, int uid, String transactionListStr) {
		return Response.Error(ErrorCode.MODULE_ERROR, "订单模块异常");
	}

	
}
