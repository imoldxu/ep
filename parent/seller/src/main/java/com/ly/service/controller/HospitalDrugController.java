package com.ly.service.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.HospitalDrug;
import com.ly.service.service.HospitalDrugService;
import com.ly.service.utils.JSONUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api("销售的药品管理")
public class HospitalDrugController {

	@Autowired
	HospitalDrugService drugService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/setHospitalDrug", method = RequestMethod.POST)
	@ApiOperation(value = "设置医院销售的药品", notes = "设置医院销售的药品")
	public Response setSellerDrug(@RequestParam(name="sellerid") int sellerid,
			@RequestParam(name="sellername") String sellername,
			@RequestParam(name="drugid") int drugid,
			@RequestParam(name="drugname") String drugname,
			@RequestParam(name="standard") String standard,
			@RequestParam(name="company") String company,
			@RequestParam(name="hospitalid") int hospitalid,
			@RequestParam(name="hospitalname") String hospitalname,
			@RequestParam(name="sellfee") int sellfee,
			HttpServletRequest request, HttpServletResponse response){
		try{
			drugService.setHospitalDrug(sellerid, sellername, drugid, drugname, standard, company, hospitalid, hospitalname, sellfee);
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();		
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/modifyHospitalDrug", method = RequestMethod.POST)
	@ApiOperation(value = "更新销售的药品", notes = "更新销售的药品")
	public Response modifyHospitalDrug(@RequestParam(name="drugStr") String drugStr,
			HttpServletRequest request, HttpServletResponse response){
		
		HospitalDrug hospitalDrug;
		try{
			hospitalDrug = JSONUtils.getObjectByJson(drugStr, HospitalDrug.class);
		}catch (Exception e) {
			return Response.Error(-1, "参数异常");
		}
		
		try{
			drugService.modifyHospitalDrug(hospitalDrug);
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();		
		}
	}
	
}
