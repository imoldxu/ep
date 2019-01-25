package com.ly.service.feign.client.fallback;

import org.springframework.stereotype.Component;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.Response;
import com.ly.service.feign.client.DrugClient;

@Component
public class DefaultDrugClient implements DrugClient{

	@Override
	public Response getDrugsByKeys(String keys, int type) {
		return Response.Error(ErrorCode.MODULE_ERROR, "药品模块异常");
	}

	@Override
	public Response getHospitalDrug(int drugid, int hospitalid) {
		return Response.Error(ErrorCode.MODULE_ERROR, "药品模块异常");
	}
	
	@Override
	public Response getDrugsInStore(Integer storeid, String drugListStr) {
		return Response.Error(ErrorCode.MODULE_ERROR, "药品模块异常");
	}

	@Override
	public Response getDrugByStore(Integer storeid, Integer drugid) {
		return Response.Error(ErrorCode.MODULE_ERROR, "药品模块异常");
	}

	@Override
	public Response getStoresByDrugs(String drugidListStr, double latitude, double longitude, int size) {
		return Response.Error(ErrorCode.MODULE_ERROR, "药品模块异常");
	}

	@Override
	public Response addDoctorDrugs(String drugidListStr, Integer doctorid) {
		return Response.OK(null);//此处即使不成功，也不影响整个业务
	}

}
