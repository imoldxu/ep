package com.ly.service.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.Store;
import com.ly.service.feign.client.DrugClient;
import com.ly.service.service.StoreService;
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
	@ApiOperation(value = "注册药店", notes = "注册药店")
	public Response register(@ApiParam(name = "name", value = "药店名") @RequestParam(name = "name") String name,
			@ApiParam(name = "address", value = "地址") @RequestParam(name = "address")String address,
			@ApiParam(name = "email", value = "邮箱") @RequestParam(name = "email")String email,
			@ApiParam(name = "longitude", value = "经度") @RequestParam(name = "longitude")double longitude,
			@ApiParam(name = "latitude", value = "纬度") @RequestParam(name = "latitude")double latitude,
			@ApiParam(name = "password", value = "密码") @RequestParam(name = "password") String password,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			SessionUtil.getManagerId(request);
			
			Store store = storeService.create(name,address,email,longitude,latitude,password);
 		
			return Response.OK(store);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			return Response.Error(-1, "系统异常");
		}
			
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(value = "登录", notes = "登录")
	public Response login(@ApiParam(name = "email", value = "邮箱") @RequestParam(name = "email")String email,
			@ApiParam(name = "password", value = "密码") @RequestParam(name = "password") String password,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			Store store = storeService.login(email, password);
 		
			SessionUtil.setStoreId(request, store.getId());
			return Response.OK(store);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			return Response.Error(-1, "系统异常");
		}
			
	}

	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/modifyPwd", method = RequestMethod.POST)
	@ApiOperation(value = "登录", notes = "登录")
	public Response modifyPwd(@ApiParam(name = "oldPassword", value = "密码") @RequestParam(name = "oldPassword") String oldPassword,
			@ApiParam(name = "newPassword", value = "密码") @RequestParam(name = "newPassword") String newPassword,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			Integer storeid = SessionUtil.getStoreId(request);
			
			storeService.modifyPwd(storeid, oldPassword, newPassword);
 		
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			return Response.Error(-1, "系统异常");
		}
			
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ApiOperation(value = "登出", notes = "登出")
	public Response logout(HttpServletRequest request, HttpServletResponse response){
		
		try{
			SessionUtil.setStoreId(request, null);
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			return Response.Error(-1, "系统异常");
		}
			
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getStoreByDrug", method = RequestMethod.GET)
	@ApiOperation(value = "根据药品获取附近的药店", notes = "根据药品获取附近的药店")
	public Response getStoreByDrug(@ApiParam(name = "drugid", value = "药品id") @RequestParam(name = "drugid") int drugid,
			@ApiParam(name = "latitude", value = "纬度") @RequestParam(name = "latitude") int latitude,
			@ApiParam(name = "longitude", value = "经度") @RequestParam(name = "longitude") int longitude,
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
	@ApiOperation(value = "根据药品获取附近的药店", notes = "根据药品获取附近的药店")
	public Response getStoreByGPS(@ApiParam(name = "drugList", value = "药品清单") @RequestParam(name = "drugList") List<Integer> drugList,
			@ApiParam(name = "latitude", value = "纬度") @RequestParam(name = "latitude") int latitude,
			@ApiParam(name = "longitude", value = "经度") @RequestParam(name = "longitude") int longitude,
			HttpServletRequest request, HttpServletResponse response){
		try{
			List<Store> list = storeService.getStoreByGPS(latitude, longitude, drugList);
			return Response.OK(list);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
}
