package com.ly.service.feign.client.fallback;

import org.springframework.stereotype.Component;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.Response;
import com.ly.service.feign.client.SellerClient;

@Component
public class DefaultSellerClient implements SellerClient{

	@Override
	public Response getHospitalDrug(int drugid, int hospitalid) {
		return Response.Error(ErrorCode.MODULE_ERROR, "网络异常");
	}


}
