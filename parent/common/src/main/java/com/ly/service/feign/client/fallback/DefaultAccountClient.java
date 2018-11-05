package com.ly.service.feign.client.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.SalesRecord;
import com.ly.service.entity.SellerAccount;
import com.ly.service.entity.StoreAccount;
import com.ly.service.feign.client.AccountClient;

@Component
public class DefaultAccountClient implements AccountClient{

	@Override
	public Response addSellerAccount(int sellerid, int amount, String msg) {
		return Response.Error(ErrorCode.MODULE_ERROR, "结算服务异常");
	}

	@Override
	public Response reduceSellerAccount(int sellerid, int amount, String msg) {
		return Response.Error(ErrorCode.MODULE_ERROR, "结算服务异常");
	}

	@Override
	public Response addStoreAccount(int storeid, int amount, String msg) {
		return Response.Error(ErrorCode.MODULE_ERROR, "结算服务异常");
	}

	@Override
	public Response reduceStoreAccount(int storeid, int amount, String msg) {
		return Response.Error(ErrorCode.MODULE_ERROR, "结算服务异常");
	}

	@Override
	public Response settleSalesRecords(String salesRecordsStr) {
		return Response.Error(ErrorCode.MODULE_ERROR, "结算服务异常");
	}

}
