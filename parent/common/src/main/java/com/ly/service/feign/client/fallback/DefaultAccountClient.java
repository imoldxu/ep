package com.ly.service.feign.client.fallback;

import org.springframework.stereotype.Component;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.Response;
import com.ly.service.feign.client.AccountClient;

@Component
public class DefaultAccountClient implements AccountClient {

	@Override
	public Response create(Integer storeid) {		
		return Response.Error(ErrorCode.MODULE_ERROR, "账户服务模块异常");
	}

}
