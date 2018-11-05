package com.ly.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.HospitalDrug;
import com.ly.service.feign.client.SellerClient;
import com.ly.service.service.HospitalDrugService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/internal")
public class SellerClientImpl implements SellerClient {

	@Autowired
	HospitalDrugService drugService;
	
	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/getHospitalDrug", method = RequestMethod.GET)
	@ApiOperation(value = "获取销售员id", notes = "获取销售员id")
	public Response getHospitalDrug(@ApiParam(name = "drugid", value = "姓名") @RequestParam(name="drugid") int drugid,
			@ApiParam(name = "hospitalid", value = "医院id") @RequestParam(name="hospitalid") int hospitalid) {
		try{
			HospitalDrug ret = drugService.getHospitalDrug(drugid, hospitalid);
			return Response.OK(ret);
		}catch(HandleException e){
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}

}
