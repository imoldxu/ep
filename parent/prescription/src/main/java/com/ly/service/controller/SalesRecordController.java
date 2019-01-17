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
import com.ly.service.entity.SalesRecord;
import com.ly.service.service.SalesRecordService;
import com.ly.service.utils.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api("取药记录")
@RequestMapping("/record")
public class SalesRecordController {

	@Autowired
	SalesRecordService recordService;
		
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getRecordByStore", method = RequestMethod.GET)
	@ApiOperation(value = "获取药房的销售记录", notes = "药房接口")
	public Response getRecordByStore(@ApiParam(name="pageIndex", value="页码") @RequestParam(name="pageIndex") int pageIndex,
			@ApiParam(name="pageSize", value="每页数量") @RequestParam(name="pageSize") int pageSize,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			Integer storeid = SessionUtil.getStoreId(request);
			
			List<SalesRecord> list = recordService.getRecordByStore(storeid, pageIndex, pageSize);
			return Response.OK(list);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();		
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getRecordBySeller", method = RequestMethod.GET)
	@ApiOperation(value = "获取销售的销售清单", notes = "销售接口")
	public Response getRecordBySeller(
			@ApiParam(name="doctorName", value="医生姓名") @RequestParam(name="doctorName") String doctorName,
			@ApiParam(name="startDate", value="起始日期") @RequestParam(name="startDate") String startDate,
			@ApiParam(name="endDate", value="结束日期") @RequestParam(name="endDate") String endDate,
			@ApiParam(name="pageIndex", value="页码") @RequestParam(name="pageIndex") int pageIndex,
			@ApiParam(name="pageSize", value="每页数量") @RequestParam(name="pageSize") int pageSize,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			Integer sellerid = SessionUtil.getSellerId(request);
			List<SalesRecord> list = recordService.getRecordBySeller(sellerid, doctorName, startDate, endDate, pageIndex, pageSize);
			return Response.OK(list);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();		
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getRecords", method = RequestMethod.GET)
	@ApiOperation(value = "获取所有的销售清单", notes = "管理接口")
	public Response getRecords(@ApiParam(name="pageIndex", value="页码") @RequestParam(name="pageIndex") int pageIndex,
			@ApiParam(name="pageSize", value="每页数量") @RequestParam(name="pageSize") int pageSize,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			SessionUtil.getManagerId(request);
			
			List<SalesRecord> list = recordService.getRecords(pageIndex, pageSize);
			return Response.OK(list);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();		
		}
	}
	
}
