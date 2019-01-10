package com.ly.service.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ly.service.context.ErrorCode;
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
@RequestMapping("/seller")
public class SellerController {

	@Autowired
	SellerService sellerService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/loginByWx", method = RequestMethod.POST)
	@ApiOperation(value = "微信登录", notes = "由销售人员调用")
	public Response LoginByWx(@ApiParam(name="wxCode", value="微信授权码") @RequestParam(name="wxCode") String wxCode,
			HttpServletRequest request,
			HttpServletResponse response){
		
		try {
			Seller seller = sellerService.loginByWx(wxCode);
			String sessionID = request.getSession().getId();
			sessionID = Base64.getEncoder().encodeToString(sessionID.getBytes());
			seller.setSessionID(sessionID);
			
			SessionUtil.setSellerId(request, seller.getId());
			return Response.OK(seller);
		} catch (IOException e) {
			return Response.Error(ErrorCode.LOGIN_ERROR, "微信登录网络故障");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
		
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/login", method = RequestMethod.POST)
	@ApiOperation(value = "账号登录", notes = "销售人员调用")
	public Response Login(@ApiParam(name="phone", value="账号") @RequestParam(name="phone") String phone,
			@ApiParam(name="password", value="密码，密码需经过md5之后传上来") @RequestParam(name="password") String password, HttpServletRequest request,
			HttpServletResponse response){
		try{
			Seller seller = sellerService.login(phone, password);
			String sessionID = request.getSession().getId();
			sessionID = Base64.getEncoder().encodeToString(sessionID.getBytes());
			seller.setSessionID(sessionID);
			
			SessionUtil.setSellerId(request, seller.getId());
			return Response.OK(seller);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/register", method = RequestMethod.POST)
	@ApiOperation(value = "账号注册", notes = "由管理员调用")
	public Response register(@ApiParam(name="name", value="姓名") @RequestParam(name="name") String name,
			@ApiParam(name="phone", value="电话号码") @RequestParam(name="phone") String phone,
			@ApiParam(name="password", value="密码") @RequestParam(name="password") String password,
			HttpServletRequest request,	HttpServletResponse response){
		try{
			SessionUtil.getManagerId(request);
			
			sellerService.register(name, phone, password);
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/modifyPwd", method = RequestMethod.POST)
	@ApiOperation(value = "修改账号密码", notes = "销售人员调用")
	public Response modifyPwd(@ApiParam(name="oldPassword", value="老密码") @RequestParam(name="oldPassword")String oldPassword,
			@ApiParam(name="newPassword", value="新密码") @RequestParam(name="newPassword")String newPassword,
			HttpServletRequest request, HttpServletResponse response){
		try{
			Integer sellerId = SessionUtil.getSellerId(request);
			
			sellerService.modifypassword(sellerId, oldPassword, newPassword);
			
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/bind", method = RequestMethod.POST)
	@ApiOperation(value = "绑定账号", notes = "绑定账号")
	public Response bind(@ApiParam(name="password", value="密码") @RequestParam(name="password")String password,
			@ApiParam(name="phone", value="账号") @RequestParam(name="phone")String phone,
			HttpServletRequest request, HttpServletResponse response){
		try{
			Integer sellerId = SessionUtil.getSellerId(request);
			
			Seller seller = sellerService.bind(sellerId, phone, password);
			
			SessionUtil.setSellerId(request, seller.getId());
			
			return Response.OK(seller);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/getAllSeller", method = RequestMethod.GET)
	@ApiOperation(value = "获取搜索的销售人员", notes = "由管理员调用")
	public Response getAllSeller(@ApiParam(name="pageIndex", value="页码 1-n") @RequestParam(name="pageIndex")Integer pageIndex,
			@ApiParam(name="pageSize", value="每页最大数量") @RequestParam(name="pageSize")Integer pageSize,
			HttpServletRequest request, HttpServletResponse response){
		try{
			SessionUtil.getManagerId(request);
			
			List<Seller> sellers = sellerService.getAllSeller(pageIndex, pageSize);
			
			return Response.OK(sellers);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
}
