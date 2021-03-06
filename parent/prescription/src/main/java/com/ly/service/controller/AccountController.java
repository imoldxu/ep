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
import com.ly.service.entity.StoreAccount;
import com.ly.service.entity.StoreAccountRecord;
import com.ly.service.service.AccountService;
import com.ly.service.utils.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api("账户结算系统")
@RequestMapping("/account")
public class AccountController {

	@Autowired
	AccountService accountService;
	
//	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
//	@RequestMapping(value = "/getAllSellerAccount", method = RequestMethod.GET)
//	@ApiOperation(value = "获取所有销售的账户", notes = "管理接口")
//	public Response getAllSellerAccount(@ApiParam(name = "pageIndex", value = "页码") @RequestParam(name = "pageIndex") int pageIndex,
//			@ApiParam(name = "pageSize", value = "最大数") @RequestParam(name = "pageSize") int pageSize,
//			HttpServletRequest request, HttpServletResponse resp) {
//		try{
//			SessionUtil.getManagerId(request);
//			
//			List<SellerAccount> ret = accountService.getAllSellerAccount(pageIndex, pageSize);
//			return Response.OK(ret);
//		} catch (HandleException e) {
//			return Response.Error(e.getErrorCode(), e.getMessage());
//		} catch (Exception e) {
//			e.printStackTrace();
//			return Response.SystemError();
//		}
//	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getAllStoreAccount", method = RequestMethod.GET)
	@ApiOperation(value = "获取所有药店的账户", notes = "管理接口")
	public Response getAllStoreAccount(@ApiParam(name = "name", value = "药房名称") @RequestParam(name = "name") String name,
			@ApiParam(name = "pageIndex", value = "页码") @RequestParam(name = "pageIndex") int pageIndex,
			@ApiParam(name = "pageSize", value = "最大数") @RequestParam(name = "pageSize") int pageSize,
			HttpServletRequest request, HttpServletResponse resp) {
		try{
			SessionUtil.getManagerId(request);
			
			List<StoreAccount> ret = accountService.getAllStoreAccount(name, pageIndex, pageSize);
			return Response.OK(ret);
		} catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
//	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
//	@RequestMapping(value = "/getSellerBalance", method = RequestMethod.GET)
//	@ApiOperation(value = "获取销售的余额", notes = "销售人员接口")
//	public Response getSellerBalance(HttpServletRequest request, HttpServletResponse resp) {
//		try{
//			int sellerid = SessionUtil.getSellerId(request);
//			int balance = accountService.getSellerBalance(sellerid);
//			return Response.OK(balance);
//		} catch (HandleException e) {
//			return Response.Error(e.getErrorCode(), e.getMessage());
//		} catch (Exception e) {
//			e.printStackTrace();
//			return Response.SystemError();
//		}
//	}
	
//	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
//	@RequestMapping(value = "/getSellerAccountRecord", method = RequestMethod.GET)
//	@ApiOperation(value = "获取销售的记录", notes = "销售人员接口")
//	public Response getSellerAccountRecord(@ApiParam(name="startDate", value="开始日期") @RequestParam(name="startDate") String startDate,
//			@ApiParam(name="endDate", value="结束日期") @RequestParam(name="endDate") String endDate,
//			@ApiParam(name="pageIndex", value="页码") @RequestParam(name="pageIndex") int pageIndex,
//			@ApiParam(name="pageSize", value="每页数量") @RequestParam(name="pageSize") int pageSize,
//			HttpServletRequest request, HttpServletResponse resp) {
//		try{
//			int sellerid = SessionUtil.getSellerId(request);
//			List<SellerAccountRecord> list = accountService.getSellerAccountRecord(sellerid, startDate, endDate, pageIndex, pageSize);
//			return Response.OK(list);
//		} catch (HandleException e) {
//			return Response.Error(e.getErrorCode(), e.getMessage());
//		} catch (Exception e) {
//			e.printStackTrace();
//			return Response.SystemError();
//		}
//	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getStoreBalance", method = RequestMethod.GET)
	@ApiOperation(value = "获取销售的余额", notes = "药房接口")
	public Response getStoreBalance(HttpServletRequest request, HttpServletResponse resp) {
		try{
			int id = SessionUtil.getStoreId(request);
			int balance = accountService.getStoreBalance(id);
			return Response.OK(balance);
		} catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getStoreAccountRecord", method = RequestMethod.GET)
	@ApiOperation(value = "获取店铺账户记录", notes = "药房接口")
	public Response getStoreAccountRecord(@ApiParam(name="startDate", value="开始日期") @RequestParam(name="startDate") String startDate,
			@ApiParam(name="endDate", value="结束日期") @RequestParam(name="endDate") String endDate,
			@ApiParam(name="pageIndex", value="页码") @RequestParam(name="pageIndex") int pageIndex,
			@ApiParam(name="pageSize", value="每页数量") @RequestParam(name="pageSize") int pageSize,
			HttpServletRequest request, HttpServletResponse resp) {
		try{
			int storeid = SessionUtil.getStoreId(request);
			List<StoreAccountRecord> list = accountService.getStoreAccountRecord(storeid, startDate, endDate, pageIndex, pageSize);
			return Response.OK(list);
		} catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getAccountRecordByStore", method = RequestMethod.GET)
	@ApiOperation(value = "获取店铺账户记录", notes = "管理接口")
	public Response getAccountRecordByStore(@ApiParam(name="storeid", value="药房id") @RequestParam(name="storeid") Integer storeid,
			@ApiParam(name="startDate", value="开始日期") @RequestParam(name="startDate") String startDate,
			@ApiParam(name="endDate", value="结束日期") @RequestParam(name="endDate") String endDate,
			@ApiParam(name="pageIndex", value="页码") @RequestParam(name="pageIndex") int pageIndex,
			@ApiParam(name="pageSize", value="每页数量") @RequestParam(name="pageSize") int pageSize,
			HttpServletRequest request, HttpServletResponse resp) {
		try{
			SessionUtil.getManagerId(request);
			
			List<StoreAccountRecord> list = accountService.getStoreAccountRecord(storeid, startDate, endDate, pageIndex, pageSize);
			return Response.OK(list);
		} catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
//	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
//	@RequestMapping(value = "/updateSellerBalance", method = RequestMethod.POST)
//	@ApiOperation(value = "调整销售的账户", notes = "管理接口")
//	public Response updateSellerBalance(@ApiParam(name = "sellerid", value = "销售的id") @RequestParam(name = "sellerid") int sellerid,
//			@ApiParam(name = "type", value = "1、add, 2、reduce") @RequestParam(name = "type") int type,
//			@ApiParam(name = "amount", value = "调整金额") @RequestParam(name = "amount") int amount,
//			HttpServletRequest request, HttpServletResponse resp) {
//		try{
//			SessionUtil.getManagerId(request);
//			
//			SellerAccount ret = accountService.updateSellerBalance(sellerid, type, amount);
//			return Response.OK(ret);
//		} catch (HandleException e) {
//			return Response.Error(e.getErrorCode(), e.getMessage());
//		} catch (Exception e) {
//			e.printStackTrace();
//			return Response.SystemError();
//		}
//	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/updateStoreBalance", method = RequestMethod.POST)
	@ApiOperation(value = "调整药房的账户", notes = "管理接口")
	public Response updateStoreBalance(@ApiParam(name = "storeid", value = "药房id") @RequestParam(name = "storeid") int storeid,
			@ApiParam(name = "type", value = "1、add, 2、reduce") @RequestParam(name = "type") int type,
			@ApiParam(name = "amount", value = "调整金额") @RequestParam(name = "amount") int amount,
			HttpServletRequest request, HttpServletResponse resp) {
		try{
			SessionUtil.getManagerId(request);
			
			StoreAccount ret = accountService.updateStoreBalance(storeid, type, amount);
			return Response.OK(ret);
		} catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
}
