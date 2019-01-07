package com.ly.service.feign.client.fallback;

import org.springframework.stereotype.Component;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.Response;
import com.ly.service.feign.client.UserClient;

@Component
public class DefaultUserClient implements UserClient {

	@Override
	public Response addPatient(Integer uid, String name, String sex, String phone, int idcardtype, String idcardnum, String birthday) {		
		return Response.OK(null);
	}

}
