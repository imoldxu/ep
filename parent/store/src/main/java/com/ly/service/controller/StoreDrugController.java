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
import com.ly.service.entity.StoreDrug;
import com.ly.service.service.StoreDrugService;
import com.ly.service.utils.SessionUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class StoreDrugController {

	@Autowired
	StoreDrugService drugService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/upDrug", method = RequestMethod.POST)
	@ApiOperation(value = "上药", notes = "上药")
	public Response upDrug(@ApiParam(name = "drugid", value = "药品id") @RequestParam(name = "drugid") int drugid,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			Integer storeid = SessionUtil.getStoreId(request);
			drugService.upDrug(storeid, drugid);
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			return Response.Error(-1, "系统异常");
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/downDrug", method = RequestMethod.POST)
	@ApiOperation(value = "下药", notes = "下药")
	public Response downDrug(@ApiParam(name = "drugid", value = "药品id") @RequestParam(name = "drugid") int drugid,
			HttpServletRequest request, HttpServletResponse response){
		try{
			Integer storeid = SessionUtil.getStoreId(request);
			drugService.downDrug(storeid, drugid);			
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			return Response.Error(-1, "系统异常");
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getStoreDrugList", method = RequestMethod.GET)
	@ApiOperation(value = "获取药房的药品清单", notes = "获取药房的药品清单")
	public Response getStoreDrugList(@ApiParam(name = "pageIndex", value = "页码") @RequestParam(name = "pageIndex") int pageIndex,
			@ApiParam(name = "pageSize", value = "每页数量") @RequestParam(name = "pageSize")int pageSize,
			HttpServletRequest request, HttpServletResponse response){
		try{
			Integer storeid = SessionUtil.getStoreId(request);
			List<StoreDrug> list= drugService.getStoreDrugList(storeid, pageIndex, pageSize);
			return Response.OK(list);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			return Response.Error(-1, "系统异常");
		}
	}	
	
}
