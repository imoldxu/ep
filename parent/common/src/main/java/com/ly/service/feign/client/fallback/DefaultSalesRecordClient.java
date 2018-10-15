package com.ly.service.feign.client.fallback;

import com.ly.service.entity.Order;

import org.springframework.stereotype.Component;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.feign.client.SalesRecordClient;

@Component
public class DefaultSalesRecordClient implements SalesRecordClient{

	
	@Override
	public void createByStore(int storeid, Order order) {
		throw new HandleException(ErrorCode.MODULE_ERROR, "网络故障");
	}

	@Override
	public void createByOnlinePay(Order order) {
		throw new HandleException(-1, "网络故障");
	}

}
