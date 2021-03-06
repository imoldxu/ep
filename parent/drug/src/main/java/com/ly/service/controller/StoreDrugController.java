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

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.context.StoreAndDrugInfo;
import com.ly.service.entity.StoreDrug;
import com.ly.service.service.StoreDrugService;
import com.ly.service.utils.JSONUtils;
import com.ly.service.utils.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(description="药店药品管理")
@RequestMapping("/store")
public class StoreDrugController {

	@Autowired
	StoreDrugService drugService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ApiOperation(value = "为药店分配药品", notes = "管理接口")
	public Response add(@ApiParam(name = "storeid", value = "药店编号") @RequestParam(name = "storeid") int storeid,
			@ApiParam(name = "drugid", value = "药品id") @RequestParam(name = "drugid") int drugid,
			@ApiParam(name = "drugName", value = "药品名称") @RequestParam(name = "drugName") String drugName,
			@ApiParam(name = "standard", value = "药品规格") @RequestParam(name = "standard") String standard,
			@ApiParam(name = "company", value = "药品厂商") @RequestParam(name = "company") String company,
			@ApiParam(name = "price", value = "药品价格，单位为分") @RequestParam(name = "price") int price,
			@ApiParam(name = "settlementPrice", value = "药品给平台结算费用，单位为分") @RequestParam(name = "settlementPrice") int settlementPrice,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			SessionUtil.getManagerId(request);
			
			StoreDrug storeDrug = drugService.add(storeid, drugid, drugName, standard, company, price, settlementPrice);
			return Response.OK(storeDrug);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ApiOperation(value = "为药店分配药品", notes = "管理接口")
	public Response del(@ApiParam(name = "storeid", value = "药店编号") @RequestParam(name = "storeid") int storeid,
			@ApiParam(name = "drugid", value = "药品id") @RequestParam(name = "drugid") int drugid,
			@ApiParam(name = "storeDrugId", value = "药店药品编号") @RequestParam(name = "storeDrugId") int storeDrugId,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			SessionUtil.getManagerId(request);
			
			drugService.del(storeid, drugid, storeDrugId);
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/upDrug", method = RequestMethod.POST)
	@ApiOperation(value = "上药", notes = "药房接口")
	public Response upDrug(@ApiParam(name = "storedrugId", value = "药店药品id") @RequestParam(name = "storedrugId") Long storedrugId,
			HttpServletRequest request, HttpServletResponse response){
		
		try{
			Integer storeid = SessionUtil.getStoreId(request);
			StoreDrug ret = drugService.upDrug(storeid, storedrugId);
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/downDrug", method = RequestMethod.POST)
	@ApiOperation(value = "下药", notes = "药房接口")
	public Response downDrug(@ApiParam(name = "storedrugId", value = "药店药品id") @RequestParam(name = "storedrugId") Long storedrugId,
			HttpServletRequest request, HttpServletResponse response){
		try{
			Integer storeid = SessionUtil.getStoreId(request);
			StoreDrug ret = drugService.downDrug(storeid, storedrugId);			
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/modifyPrice", method = RequestMethod.POST)
	@ApiOperation(value = "修改售价", notes = "药房接口")
	public Response modifyPrice(@ApiParam(name = "storedrugId", value = "药店药品id") @RequestParam(name = "storedrugId") Long storedrugId,
			@ApiParam(name = "price", value = "售价") @RequestParam(name = "price") Integer price,
			HttpServletRequest request, HttpServletResponse response){
		try{
			Integer storeid = SessionUtil.getStoreId(request);
			StoreDrug ret = drugService.modifyPrice(storeid, price, storedrugId);			
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getStoreDrugListByStore", method = RequestMethod.GET)
	@ApiOperation(value = "获取所有药房的药品清单", notes = "管理接口")
	public Response getStoreDrugListByStore(@ApiParam(name = "storeid", value = "商户id") @RequestParam(name = "storeid") Integer storeid,
			@ApiParam(name = "key", value = "关键字") @RequestParam(name = "key") String key,
			@ApiParam(name = "pageIndex", value = "页码") @RequestParam(name = "pageIndex") int pageIndex,
			@ApiParam(name = "pageSize", value = "每页数量") @RequestParam(name = "pageSize")int pageSize,
			HttpServletRequest request, HttpServletResponse response){
		try{
			SessionUtil.getManagerId(request);
			List<StoreDrug> list= drugService.getStoreDrugList(storeid, key, 0, pageIndex, pageSize);//默认不关心状态
			return Response.OK(list);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getStoreDrugList", method = RequestMethod.GET)
	@ApiOperation(value = "获取药房的药品清单", notes = "药房接口")
	public Response getStoreDrugList(@ApiParam(name = "key", value = "关键字") @RequestParam(name = "key") String key,
			@ApiParam(name = "state", value = "状态") @RequestParam(name = "state") int state,
			@ApiParam(name = "pageIndex", value = "页码") @RequestParam(name = "pageIndex") int pageIndex,
			@ApiParam(name = "pageSize", value = "每页数量") @RequestParam(name = "pageSize")int pageSize,
			HttpServletRequest request, HttpServletResponse response){
		try{
			Integer storeid = SessionUtil.getStoreId(request);
			List<StoreDrug> list= drugService.getStoreDrugList(storeid, key, state, pageIndex, pageSize);
			return Response.OK(list);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getStoresByDrugs", method = RequestMethod.GET)
	@ApiOperation(value = "根据药品清单获取药房信息", notes = "通用接口")
	public Response getStoresByDrugs(@ApiParam(name = "drugidListStr", value = "id数组[1,2,3]") @RequestParam(name = "drugidListStr") String drugidListStr,
			@ApiParam(name = "latitude", value = "纬度") @RequestParam(name = "latitude") double latitude,
			@ApiParam(name = "longitude", value = "经度") @RequestParam(name = "longitude") double longitude,
			@ApiParam(name = "size", value = "数量") @RequestParam(name = "size")int size,
			HttpServletRequest request, HttpServletResponse response){
		
		List<Integer> drugidList = null;
		try{
			drugidList = JSONUtils.getObjectListByJson(drugidListStr, Integer.class);
		}catch (Exception e) {
			e.printStackTrace();
			return Response.Error(ErrorCode.ARG_ERROR, "参数错误");
		}
		
		try{
			List<StoreAndDrugInfo> list= drugService.getStoreByDrugs(drugidList, latitude, longitude, size);
			return Response.OK(list);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
}
