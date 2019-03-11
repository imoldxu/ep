package com.ly.service.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.Store;
import com.ly.service.service.StoreService;
import com.ly.service.utils.JSONUtils;
import com.ly.service.utils.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(description = "商户接口")
@RequestMapping("/store")
public class StoreController {

	@Autowired
	StoreService storeService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ApiOperation(value = "注册药店", notes = "管理员接口")
	public Response register(@ApiParam(name = "name", value = "药店名") @RequestParam(name = "name") String name,
			@ApiParam(name = "address", value = "地址") @RequestParam(name = "address")String address,
			@ApiParam(name = "email", value = "邮箱") @RequestParam(name = "email")String email,
			@ApiParam(name = "longitude", value = "经度") @RequestParam(name = "longitude")double longitude,
			@ApiParam(name = "latitude", value = "纬度") @RequestParam(name = "latitude")double latitude,
			@ApiParam(name = "password", value = "密码") @RequestParam(name = "password") String password,
			@ApiParam(name = "rate", value = "费率") @RequestParam(name = "rate") double rate,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			SessionUtil.getManagerId(request);
			
			Store store = storeService.create(name,address,email,longitude,latitude,password,rate);
 		
			return Response.OK(store);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			return Response.SystemError();
		}
			
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	@ApiOperation(value = "修改药店", notes = "由管理员调用")
	public Response modify(@ApiParam(name = "jsstore", value = "store的js对象") @RequestParam(name = "jsstore") String jsstore,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			SessionUtil.getManagerId(request);
			
			Store store = JSONUtils.getObjectByJson(jsstore, Store.class);
			
			store = storeService.modify(store);
 		
			return Response.OK(store);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			return Response.SystemError();
		}
			
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
	@ApiOperation(value = "重置密码", notes = "由管理员调用")
	public Response resetPwd(@ApiParam(name = "storeid", value = "药房id") @RequestParam(name = "storeid") Integer storeid,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			SessionUtil.getManagerId(request);
			
			storeService.resetPwd(storeid);
 		
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			return Response.SystemError();
		}
			
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(value = "登录", notes = "由药房自己调用登录")
	public Response login(@ApiParam(name = "email", value = "邮箱") @RequestParam(name = "email")String email,
			@ApiParam(name = "password", value = "密码需进过md5加密上传") @RequestParam(name = "password") String password,
			HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Methods", "GET, POST");
		
		try{
			Store store = storeService.login(email, password);
 		
			SessionUtil.setStoreId(request, store.getId());
			return Response.OK(store);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
			
	}

	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/modifyPwd", method = RequestMethod.POST)
	@ApiOperation(value = "修改密码", notes = "由药房自己调用")
	public Response modifyPwd(@ApiParam(name = "oldPassword", value = "旧密码") @RequestParam(name = "oldPassword") String oldPassword,
			@ApiParam(name = "newPassword", value = "新密码") @RequestParam(name = "newPassword") String newPassword,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			Integer storeid = SessionUtil.getStoreId(request);
			
			storeService.modifyPwd(storeid, oldPassword, newPassword);
 		
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			return Response.SystemError();
		}
			
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ApiOperation(value = "登出", notes = "由药房自己调用")
	public Response logout(HttpServletRequest request, HttpServletResponse response){
		
		try{
			SessionUtil.setStoreId(request, null);
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			return Response.SystemError();
		}
			
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getStoreByDrug", method = RequestMethod.GET)
	@ApiOperation(value = "根据单个药品获取附近的药店", notes = "由用户调用")
	public Response getStoreByDrug(@ApiParam(name = "drugid", value = "药品id") @RequestParam(name = "drugid") Integer drugid,
			@ApiParam(name = "latitude", value = "纬度") @RequestParam(name = "latitude") Double latitude,
			@ApiParam(name = "longitude", value = "经度") @RequestParam(name = "longitude") Double longitude,
			HttpServletRequest request, HttpServletResponse response){
		try{
			List<Store> list = storeService.getStoreByDrug(drugid, latitude, longitude);
			return Response.OK(list);	
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
		
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getStoreByGPS", method = RequestMethod.GET)
	@ApiOperation(value = "根据药品获取附近的药店", notes = "由用户调用")
	public Response getStoreByGPS(@ApiParam(name = "drugListStr", value = "药品清单") @RequestParam(name = "drugListStr") String drugListStr,
			@ApiParam(name = "latitude", value = "纬度") @RequestParam(name = "latitude") Double latitude,
			@ApiParam(name = "longitude", value = "经度") @RequestParam(name = "longitude") Double longitude,
			@ApiParam(name = "size", value = "获取数量") @RequestParam(name = "size") int size,
			HttpServletRequest request, HttpServletResponse response){
		
		
		List<Integer> drugList = null;
		try{
			drugList  = JSONUtils.getObjectListByJson(drugListStr, Integer.class);
		}catch (Exception e) {
			return Response.Error(ErrorCode.ARG_ERROR, "参数错误");
		}
		
		try{
			List<Store> list = storeService.getStoreByGPS(latitude, longitude, drugList, size);
			return Response.OK(list);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getStoresByName", method = RequestMethod.GET)
	@ApiOperation(value = "获取所有的药店", notes = "管理接口")
	public Response getStoresByName(@ApiParam(name = "name", value = "药房名称,传入空串则表示搜索所有药店") @RequestParam(name = "name") String name,
			@ApiParam(name = "pageIndex", value = "页码1-n") @RequestParam(name = "pageIndex") int pageIndex,
			@ApiParam(name = "pageSize", value = "每页最大数量") @RequestParam(name = "pageSize") int pageSize,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			List<Store> list = storeService.getStoreByName(name, pageIndex, pageSize);
			return Response.OK(list);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
}
