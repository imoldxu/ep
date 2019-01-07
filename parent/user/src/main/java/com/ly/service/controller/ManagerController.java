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
import com.ly.service.entity.ManagerUser;
import com.ly.service.service.ManagerService;
import com.ly.service.utils.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(description = "管理员接口")
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	ManagerService managerService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(value = "登录", notes = "登录")
	public Response login(@ApiParam(name = "phone", value = "手机号") @RequestParam(name = "phone")String phone,
			@ApiParam(name = "password", value = "密码") @RequestParam(name = "password") String password,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			ManagerUser manager = managerService.login(phone, password);
 		
			SessionUtil.setManagerId(request, manager.getId());
			return Response.OK(manager);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			return Response.Error(-1, "系统异常");
		}
			
	}

	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ApiOperation(value = "登录", notes = "登录")
	public Response logout(HttpServletRequest request, HttpServletResponse response){
		
		try{
			SessionUtil.setManagerId(request, null);
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			return Response.Error(-1, "系统异常");
		}
			
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/modifyPwd", method = RequestMethod.POST)
	@ApiOperation(value = "修改登录密码", notes = "修改登录密码")
	public Response modifyPwd(@ApiParam(name = "oldPassword", value = "密码") @RequestParam(name = "oldPassword") String oldPassword,
			@ApiParam(name = "newPassword", value = "密码") @RequestParam(name = "newPassword") String newPassword,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			Integer managerid = SessionUtil.getManagerId(request);
			
			managerService.modifyPwd(managerid, oldPassword, newPassword);
 		
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			return Response.Error(-1, "系统异常");
		}
			
	}
	
}
