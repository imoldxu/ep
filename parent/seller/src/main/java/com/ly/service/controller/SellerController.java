package com.ly.service.controller;

import java.io.IOException;

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
import com.ly.service.entity.Seller;
import com.ly.service.service.SellerService;
import com.ly.service.utils.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api("销售人员系统")
public class SellerController {

	@Autowired
	SellerService sellerService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/loginByWx", method = RequestMethod.POST)
	@ApiOperation(value = "微信登录", notes = "微信登录")
	public Response LoginByWx(@ApiParam(name="wxCode", value="微信授权码") @RequestParam(name="wxCode") String wxCode,
			HttpServletRequest request,
			HttpServletResponse response){
		
		try {
			Seller seller = sellerService.loginByWx(wxCode);
			SessionUtil.setSellerId(request, seller.getId());
			return Response.OK(seller);
		} catch (IOException e) {
			return Response.Error(Response.ERROR, "微信登录失败");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.Error(Response.ERROR, "系统异常");
		}
		
	}
	
	@RequestMapping(path="/login", method = RequestMethod.GET)
	public Response Login(@ApiParam(name="phone", value="微信授权码") @RequestParam(name="phone") String phone,
			@ApiParam(name="password", value="密码") @RequestParam(name="password") String password, HttpServletRequest request,
			HttpServletResponse response){
		try{
			Seller seller = sellerService.login(phone, password);
			SessionUtil.setSellerId(request, seller.getId());
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.Error(-1, "系统异常");
		}
	}
	
	@RequestMapping(path="/bind", method = RequestMethod.POST)
	public Response bind(String phone, String password, String wxcode){
		return null;
	}
}
