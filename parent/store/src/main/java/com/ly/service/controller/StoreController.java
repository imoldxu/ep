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
import com.ly.service.entity.StoreDrug;
import com.ly.service.service.StoreDrugService;
import com.ly.service.service.StoreService;
import com.ly.service.utils.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(description = "商户接口")
public class StoreController {

	@Autowired
	StoreService storeService;
	@Autowired
	StoreDrugService drugService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ApiOperation(value = "登录", notes = "登录")
	public Response login(@ApiParam(name = "email", value = "邮箱") @RequestParam(name = "email")String email,
			@ApiParam(name = "password", value = "密码") @RequestParam(name = "password") String password,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			Store store = storeService.login(email, password);
 		
			HttpSession session = request.getSession();
			session.setAttribute("STORE_ID", store.getId());
			return new Response(Response.SUCCESS, store, "登录成功");
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			return Response.Error(-1, "系统异常");
		}
			
	}
	
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
			return Response.Error(-1, "系统异常");
		}
	}
		
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getStoreByGPS", method = RequestMethod.GET)
	@ApiOperation(value = "根据药品获取附近的药店", notes = "根据药品获取附近的药店")
	public Response getStoreByGPS(@ApiParam(name = "druglist", value = "药品清单") @RequestParam(name = "druglist") List<Integer> druglist,
			@ApiParam(name = "latitude", value = "纬度") @RequestParam(name = "latitude") int latitude,
			@ApiParam(name = "longitude", value = "经度") @RequestParam(name = "longitude") int longitude,
			HttpServletRequest request, HttpServletResponse response){
		try{
			List<Store> list = storeService.getStoreByGPS(latitude, longitude);
			for(Store store: list){
				List<StoreDrug> drugs= drugService.getDrugsInStore(store.getId(), druglist);
				store.setDruglist(drugs);
			}
			return Response.OK(list);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			return Response.Error(-1, "系统异常");
		}
	}
}
