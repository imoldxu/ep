package com.ly.service.feign.client.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.entity.SalesRecord;
import com.ly.service.entity.SellerAccount;
import com.ly.service.entity.StoreAccount;
import com.ly.service.feign.client.AccountClient;

@Component
public class DefaultAccountClient implements AccountClient{

	@Override
	public SellerAccount addSellerAccount(int sellerid, int amount, String msg) {
		throw new HandleException(ErrorCode.MODULE_ERROR, "结算服务异常");
	}

	@Override
	public SellerAccount reduceSellerAccount(int sellerid, int amount, String msg) {
		throw new HandleException(ErrorCode.MODULE_ERROR, "结算服务异常");
	}

	@Override
	public StoreAccount addStoreAccount(int storeid, int amount, String msg) {
		throw new HandleException(ErrorCode.MODULE_ERROR, "结算服务异常");
	}

	@Override
	public StoreAccount reduceStoreAccount(int storeid, int amount, String msg) {
		throw new HandleException(ErrorCode.MODULE_ERROR, "结算服务异常");
	}

	@Override
	public void settleSalesRecords(List<SalesRecord> salesRecord) {
		throw new HandleException(ErrorCode.MODULE_ERROR, "结算服务异常");
	}

}
