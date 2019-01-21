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

	@Override
	public Response getDoctor(Integer doctorid) {
		return Response.Error(ErrorCode.MODULE_ERROR, "用户模块服务异常");
	}

	@Override
	public Response getHospital(Integer hid) {
		return Response.Error(ErrorCode.MODULE_ERROR, "用户模块服务异常");
	}

	@Override
	public Response getSeller(Integer sellerid) {
		return Response.Error(ErrorCode.MODULE_ERROR, "用户模块服务异常");
	}

	@Override
	public Response getStore(Integer storeid) {
		return Response.Error(ErrorCode.MODULE_ERROR, "用户模块服务异常");
	}

	@Override
	public Response getStoreByGPS(String drugListStr, Double latitude, Double longitude) {
		return Response.Error(ErrorCode.MODULE_ERROR, "用户模块服务异常");
	}
}
