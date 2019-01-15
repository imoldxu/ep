package com.ly.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.Doctor;
import com.ly.service.entity.Hospital;
import com.ly.service.entity.Seller;
import com.ly.service.entity.Store;
import com.ly.service.feign.client.UserClient;
import com.ly.service.service.DoctorService;
import com.ly.service.service.HospitalService;
import com.ly.service.service.PatientService;
import com.ly.service.service.SellerService;
import com.ly.service.service.StoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/internal")
@Api("用户内部接口")
public class UserClientImpl implements UserClient{

	@Autowired
	PatientService patientService;
	@Autowired
	DoctorService doctorService;
	@Autowired
	HospitalService hospitalService;
	@Autowired
	SellerService sellerService;
	@Autowired
	StoreService storeService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/addPatient", method = RequestMethod.POST)
	@ApiOperation(value = "添加患者信息", notes = "添加患者信息")
	public Response addPatient(@ApiParam(name = "uid", value = "用户编号") @RequestParam(name = "uid") Integer uid,
			@ApiParam(name = "name", value = "姓名") @RequestParam(name = "name") String name,
			@ApiParam(name = "sex", value = "性别") @RequestParam(name = "sex") String sex,	
			@ApiParam(name = "phone", value = "电话") @RequestParam(name = "phone") String phone,
			@ApiParam(name = "idcardtype", value = "证件类型") @RequestParam(name = "idcardtype") int idcardtype,
			@ApiParam(name = "idcardnum", value = "证件号") @RequestParam(name = "idcardnum") String idcardnum,
			@ApiParam(name = "birthday", value = "出生日期") @RequestParam(name = "birthday") String birthday) {
		try{
			patientService.add(uid, name, sex, phone, idcardtype, idcardnum, birthday);
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}

	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getDoctor", method = RequestMethod.GET)
	@ApiOperation(value = "获取医生信息", notes = "获取医生信息")
	public Response getDoctor(@ApiParam(name = "doctorid", value = "医生编号") @RequestParam(name = "doctorid") Integer doctorid) {
		try{
			Doctor ret = doctorService.getDoctor(doctorid);
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getHospital", method = RequestMethod.GET)
	@ApiOperation(value = "获取医院信息", notes = "获取医院信息")
	public Response getHospital(@ApiParam(name = "hid", value = "医院编号") @RequestParam(name = "hid") Integer hid) {
		try{
			Hospital ret = hospitalService.getHospital(hid);
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getSeller", method = RequestMethod.GET)
	@ApiOperation(value = "获取销售信息", notes = "内部接口")
	public Response getSeller(@ApiParam(name = "sellerid", value = "销售编号") @RequestParam(name = "sellerid") Integer sellerid) {
		try{
			Seller ret = sellerService.getSeller(sellerid);
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getStore", method = RequestMethod.GET)
	@ApiOperation(value = "获取药房信息", notes = "内部接口")
	public Response getStore(@ApiParam(name = "storeid", value = "药房编号") @RequestParam(name = "storeid") Integer storeid) {
		try{
			Store ret = storeService.getStore(storeid);
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
}
