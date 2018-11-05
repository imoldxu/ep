package com.ly.service.feign.client.fallback;

import org.springframework.stereotype.Component;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.StoreDrug;
import com.ly.service.feign.client.StoreClient;

@Component
public class DefaultStoreClient implements StoreClient{

	@Override
	public Response getDrugsInStore(Integer storeid, String drugListStr) {
		return Response.Error(ErrorCode.MODULE_ERROR, "网络故障");
	}

	@Override
	public Response getDrugByStore(Integer storeid, Integer drugid) {
		return Response.Error(ErrorCode.MODULE_ERROR, "网络故障");
	}

}
