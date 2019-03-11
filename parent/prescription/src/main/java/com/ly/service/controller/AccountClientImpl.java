package com.ly.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.StoreAccount;
import com.ly.service.feign.client.AccountClient;
import com.ly.service.service.AccountService;
import com.ly.service.utils.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api("账户服务")
@RequestMapping("/internal")
public class AccountClientImpl implements AccountClient{

	@Autowired
	AccountService accountService;
	
	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ApiOperation(value = "获取所有药店的账户", notes = "管理接口")
	public Response create(@ApiParam(name = "storeid", value = "药房id") @RequestParam(name = "storeid") Integer storeid) {
		try{
		
			StoreAccount ret = accountService.generateAccount(storeid);
			return Response.OK(ret);
		} catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}	
	}

}
