package com.yyg.eprescription.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yyg.eprescription.context.HandleException;
import com.yyg.eprescription.context.Response;
import com.yyg.eprescription.service.DiagnosisService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/diagnosis")
@Api(description="诊断信息")
public class DiagnosisController {

	@Autowired
	DiagnosisService diagnosisService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getDiagnosisByKeys", method = RequestMethod.GET)
	@ApiOperation(value = "获取诊断信息", notes = "获取诊断信息")
	public Response getDiagnosisByKeys(
			@ApiParam(name = "keys", value = "keys") @RequestParam(name = "keys") String keys,
			HttpServletRequest request, HttpServletResponse respons) {
		respons.setHeader("Access-Control-Allow-Methods", "GET");
		try{			
			List<String> ret = diagnosisService.getDiagnosisByKey(keys);
			
			Response resp = new Response(Response.SUCCESS, ret, Response.SUCCESS_MSG);
			return resp;
		}catch(HandleException e){	
			return new Response(e.getErrorCode(), null, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
}
