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

import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.SellerAccountRecord;
import com.ly.service.entity.StoreAccountRecord;
import com.ly.service.service.AccountService;
import com.ly.service.utils.SessionUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class AccountController {

	@Autowired
	AccountService accountService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getSellerBalance", method = RequestMethod.GET)
	@ApiOperation(value = "获取销售的余额", notes = "获取销售的余额")
	public Response getSellerBalance(HttpServletRequest request, HttpServletResponse resp) {
		try{
			int sellerid = SessionUtil.getSellerId(request);
			int balance = accountService.getSellerBalance(sellerid);
			return Response.OK(balance);
		} catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Response.Error(-1, "系统异常");
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getSellerAccountRecord", method = RequestMethod.GET)
	@ApiOperation(value = "获取销售的余额", notes = "获取销售的余额")
	public Response getSellerAccountRecord(@ApiParam(name="pageIndex", value="页码") @RequestParam(name="pageIndex") int pageIndex,
			@ApiParam(name="pageSize", value="每页数量") @RequestParam(name="pageSize") int pageSize,
			HttpServletRequest request, HttpServletResponse resp) {
		try{
			int sellerid = SessionUtil.getSellerId(request);
			List<SellerAccountRecord> list = accountService.getSellerAccountRecord(sellerid, pageIndex, pageSize);
			return Response.OK(list);
		} catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Response.Error(-1, "系统异常");
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getStoreBalance", method = RequestMethod.GET)
	@ApiOperation(value = "获取销售的余额", notes = "获取销售的余额")
	public Response getStoreBalance(HttpServletRequest request, HttpServletResponse resp) {
		try{
			int id = SessionUtil.getStoreId(request);
			int balance = accountService.getStoreBalance(id);
			return Response.OK(balance);
		} catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Response.Error(-1, "系统异常");
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getStoreAccountRecord", method = RequestMethod.GET)
	@ApiOperation(value = "获取店铺账户记录", notes = "获取店铺账户记录")
	public Response getStoreAccountRecord(@ApiParam(name="pageIndex", value="页码") @RequestParam(name="pageIndex") int pageIndex,
			@ApiParam(name="pageSize", value="每页数量") @RequestParam(name="pageSize") int pageSize,
			HttpServletRequest request, HttpServletResponse resp) {
		try{
			int storeid = SessionUtil.getStoreId(request);
			List<StoreAccountRecord> list = accountService.getStoreAccountRecord(storeid, pageIndex, pageSize);
			return Response.OK(list);
		} catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Response.Error(-1, "系统异常");
		}
	}
}
