package com.ly.service.feign.client.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.TransactionDrug;
import com.ly.service.entity.Order;
import com.ly.service.feign.client.OrderClient;

@Component
public class DefaultOrderClient implements OrderClient {

	@Override
	public Order create(int uid, int amount, List<TransactionDrug> transactionList) {
		throw new HandleException(ErrorCode.MODULE_ERROR, "订单模块异常");
	}

	@Override
	public Order createByStore(int storeid, int uid, List<TransactionDrug> transactionList) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
