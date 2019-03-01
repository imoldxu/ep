package com.ly.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.Doctor;
import com.ly.service.entity.Hospital;
import com.ly.service.entity.Store;
import com.ly.service.feign.client.UserClient;
import com.ly.service.service.DoctorCommentService;
import com.ly.service.service.DoctorService;
import com.ly.service.service.HospitalService;
import com.ly.service.service.PatientService;
import com.ly.service.service.StoreService;
import com.ly.service.utils.JSONUtils;

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
	StoreService storeService;
	@Autowired
	DoctorCommentService doctorCommentService;
	
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
			return Response.OK(null);
			//return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			//return Response.SystemError();
			return Response.OK(null);
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
	
//	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
//	@RequestMapping(value = "/getSeller", method = RequestMethod.GET)
//	@ApiOperation(value = "获取销售信息", notes = "内部接口")
//	public Response getSeller(@ApiParam(name = "sellerid", value = "销售编号") @RequestParam(name = "sellerid") Integer sellerid) {
//		try{
//			Seller ret = sellerService.getSeller(sellerid);
//			return Response.OK(ret);
//		}catch (HandleException e) {
//			return Response.Error(e.getErrorCode(), e.getMessage());
//		}catch (Exception e) {
//			e.printStackTrace();
//			return Response.SystemError();
//		}
//	}
	
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
	
	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getStoreByGPS", method = RequestMethod.GET)
	@ApiOperation(value = "根据药品获取附近的药店", notes = "内部接口")
	public Response getStoreByGPS(@ApiParam(name = "drugListStr", value = "药品清单") @RequestParam(name = "drugListStr") String drugListStr,
			@ApiParam(name = "latitude", value = "纬度") @RequestParam(name = "latitude") Double latitude,
			@ApiParam(name = "longitude", value = "经度") @RequestParam(name = "longitude") Double longitude,
			@ApiParam(name = "size", value = "数量") @RequestParam(name = "size") int size){
		
		List<Integer> drugList = null;
		try{
			drugList  = JSONUtils.getObjectListByJson(drugListStr, Integer.class);
		}catch (Exception e) {
			return Response.Error(ErrorCode.ARG_ERROR, "参数错误");
		}
		
		try{
			List<Store> list = storeService.getStoreByGPS(latitude, longitude, drugList,size);
			return Response.OK(list);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/commitDoctorComment", method = RequestMethod.POST)
	@ApiOperation(value = "提交医生评论", notes = "内部接口")
	public Response commitDoctorComment(@ApiParam(name = "doctorid", value = "医生id") @RequestParam(name = "doctorid") Integer doctorid,
			@ApiParam(name = "uid", value = "评论用户id") @RequestParam(name = "uid") Integer uid,
			@ApiParam(name = "content", value = "内容") @RequestParam(name = "content") String content,
			@ApiParam(name = "star", value = "评分") @RequestParam(name = "star") Integer star){
		
		try{
			doctorCommentService.commitService(content, uid, doctorid, star);
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
}
