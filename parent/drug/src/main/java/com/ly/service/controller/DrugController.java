package com.ly.service.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.ly.service.context.DrugTagInfo;
import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.Drug;
import com.ly.service.entity.DrugTag;
import com.ly.service.service.DrugService;
import com.ly.service.context.SimpleDrugInfo;
import com.ly.service.utils.ExcelUtils;
import com.ly.service.utils.JSONUtils;
import com.ly.service.utils.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(description = "药品接口")
@RequestMapping("/drug")
public class DrugController {
	
	@Autowired
	DrugService drugService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getDrugsByKeys", method = RequestMethod.GET)
	@ApiOperation(value = "根据药品的拼音首字母缩写或药品名称搜索药品", notes = "通用接口")
	public Response getDrugsByKeys(
			@ApiParam(name = "keys", value = "拼音首字母索引或药品名称") @RequestParam(name = "keys") String keys,
			@ApiParam(name = "type", value = "西药为1，中药为2") @RequestParam(name = "type") int type,
			HttpServletRequest request, HttpServletResponse response) {
		//response.setHeader("Access-Control-Allow-Origin", "*");
		//response.setHeader("Access-Control-Allow-Methods", "GET");
		try{
			List<SimpleDrugInfo> ret = drugService.getSimpleDrugListByKeys(type, keys);
			
			Response resp = Response.OK(ret);
			return resp;
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getDrugListByKeys", method = RequestMethod.GET)
	@ApiOperation(value = "根据药品的拼音缩写搜索药品", notes = "通用接口")
	public Response getDrugListByKeys(
			@ApiParam(name = "keys", value = "拼音首字母索引") @RequestParam(name = "keys") String keys,
			HttpServletRequest request, HttpServletResponse response) {
		//response.setHeader("Access-Control-Allow-Origin", "*");
		//response.setHeader("Access-Control-Allow-Methods", "GET");
		try{
			List<Drug> ret = drugService.getDrugListByKeys(keys);
			
			Response resp = Response.OK(ret);
			return resp;
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getDrugListByTag", method = RequestMethod.GET)
	@ApiOperation(value = "根据标签搜索药品", notes = "通用接口")
	public Response getDrugListByTag(
			@ApiParam(name = "tag", value = "标签") @RequestParam(name = "tag") String tag,
			@ApiParam(name = "type", value = "西药为1，中药为2") @RequestParam(name = "type") int type,
			HttpServletRequest request, HttpServletResponse response) {
		//response.setHeader("Access-Control-Allow-Origin", "*");
		//response.setHeader("Access-Control-Allow-Methods", "GET");
		try{
			List<SimpleDrugInfo> ret = drugService.getSimpleDrugListByTag(type, tag);
			
			Response resp = Response.OK(ret);
			return resp;
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getDrugByID", method = RequestMethod.GET)
	@ApiOperation(value = "获取药品信息", notes = "通用接口")
	public Response getDrugByID(
			@ApiParam(name = "drugid", value = "药品id") @RequestParam(name = "drugid") Integer drugid,
			HttpServletRequest request, HttpServletResponse response) {
		//response.setHeader("Access-Control-Allow-Origin", "*");
		//response.setHeader("Access-Control-Allow-Methods", "GET");
		try{
			Drug drug = drugService.getDrugById(drugid);
			Response resp = null;
			if(drug==null){
				resp = Response.NormalError("请求的药品不存在");
			}else{
				resp = Response.OK(drug);
			}
			return resp;
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/uploadByExcel", method = RequestMethod.POST)
	@ApiOperation(value = "新增上传药品信息", notes = "管理接口")
	public Response uploadByExcel(@RequestPart(value="file") MultipartFile file,
			HttpServletRequest request,HttpServletResponse response) {
		//response.setHeader("Access-Control-Allow-Origin", "*");
		//response.setHeader("Access-Control-Allow-Methods", "POST");
		
		if(file==null){
			return Response.NormalError("请求参数异常");
		}
		
		//获取文件名
	    String name=file.getOriginalFilename();
	    //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
	    long size=file.getSize();
	    if(name == null || ("").equals(name) && size==0){
	    	return Response.NormalError("文件不存在或没有内容");
	    }
	    //批量导入。参数：文件名，文件。
	    Response resp = null;
	    
	    try{
	    	SessionUtil.getManagerId(request);
	    	
			ExcelUtils excelUtils = new ExcelUtils();
	    	List<Drug> drugList = excelUtils.getExcelInfo(name, file);
	    	drugService.uploadDrugList(drugList);
	    	resp = Response.OK(drugList);    
	    }catch(IOException ioe){
	    	 resp = Response.NormalError(ioe.getMessage());
	    }catch (Exception e) {
	    	e.printStackTrace();
	        resp = Response.NormalError("导入失败");
	    }

		return resp;
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/modifyDrug", method = RequestMethod.POST)
	@ApiOperation(value = "修改药品信息", notes = "管理接口")
	public Response modifyDrug(@ApiParam(name="jsdrug",value="{ \"id\": 23,     \"drugname\": \"硫酸亚铁片\",     \"standard\": \"0.3g*60片\",     \"category\": \"OTC\",     \"price\": 38,     \"unit\": \"盒\",     \"form\": \"片剂\",     \"singledose\": \"1\",     \"doseunit\": \"片\",     \"defaultusage\": \"饭前\",     \"frequency\": \"一天三次\",     \"fullkeys\": \"LSYTP\",     \"shortnamekeys\": \"LSYTP\"   }") @RequestParam(value="jsdrug") String jsdrug,
			HttpServletRequest request,
			HttpServletResponse response) {
		Drug drug = null;
		try{			
			drug = JSONUtils.getObjectByJson(jsdrug, Drug.class);
		}catch (Exception e) {
			return Response.Error(ErrorCode.ARG_ERROR, "参数错误");
		}
		
		try{
			SessionUtil.getManagerId(request);
			
			if(drug.getId() != null){
				int opRet = drugService.modify(drug);
				if(opRet == 0){
					return Response.NormalError("修改失败");    
				}else{
					return Response.OK(drug);    
				}
			}else{
				return Response.Error(ErrorCode.ARG_ERROR, "参数错误");
			}
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/delDrug", method = RequestMethod.POST)
	@ApiOperation(value = "删除药品信息", notes ="管理接口" )
	public Response delDrug(@RequestParam(value="drugid") int drugid, HttpServletRequest request,HttpServletResponse response) {
		//response.setHeader("Access-Control-Allow-Origin", "*");
		//response.setHeader("Access-Control-Allow-Methods", "POST");
		try{
			SessionUtil.getManagerId(request);
			
			Response resp = null;			
			int optRet = drugService.del(drugid);	
			if(optRet!=0){
				resp = Response.OK(null);
			}else{
				resp = Response.NormalError("药品不存在");
			}
			return resp;
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}		
	}
	
//	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
//	@RequestMapping(value = "/downDrug", method = RequestMethod.POST)
//	@ApiOperation(value = "下架药品", notes = "{     \"id\": 23,     \"drugname\": \"硫酸亚铁片\",     \"standard\": \"0.3g*60片\",     \"category\": \"OTC\",     \"price\": 38,     \"unit\": \"盒\",     \"form\": \"片剂\",     \"singledose\": \"1\",     \"doseunit\": \"片\",     \"defaultusage\": \"饭前\",     \"frequency\": \"一天三次\",     \"fullkeys\": \"LSYTP\",     \"shortnamekeys\": \"LSYTP\"   }")
//	public Response downDrug(@RequestParam(value="drugid") int drugid, HttpServletRequest request,HttpServletResponse response) {
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		response.setHeader("Access-Control-Allow-Methods", "POST");
//		
//		Response resp = null;	
//		//int optRet = drugMapper.deleteByPrimaryKey(drugid);
//		Drug drug = drugMapper.selectByPrimaryKey(drugid);
//		drug.setState(Drug.STATE_EMPTY);
//		int optRet = drugMapper.updateByPrimaryKey(drug);
//		if(optRet!=0){
//			resp = new Response(Response.SUCCESS, null, "删除成功");
//		}else{
//			resp = new Response(Response.ERROR, null, "药品不存在");
//		}
//		return resp;
//	}
//	
//	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
//	@RequestMapping(value = "/upDrug", method = RequestMethod.POST)
//	@ApiOperation(value = "上架药品信息", notes = "上架药品信息")
//	public Response upDrug(@RequestParam(value="drugid") int drugid, HttpServletRequest request,HttpServletResponse response) {
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		response.setHeader("Access-Control-Allow-Methods", "POST");
//		
//		Response resp = null;			
//		//int optRet = drugMapper.deleteByPrimaryKey(drugid);
//		Drug drug = drugMapper.selectByPrimaryKey(drugid);
//		drug.setState(Drug.STATE_OK);
//		int optRet = drugMapper.updateByPrimaryKey(drug);
//		if(optRet!=0){
//			resp = new Response(Response.SUCCESS, null, "删除成功");
//		}else{
//			resp = new Response(Response.ERROR, null, "药品不存在");
//		}
//		return resp;
//	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getTags", method = RequestMethod.GET)
	@ApiOperation(value = "获取所有的tag", notes = "通用接口")
	public Response getTags(){
		try{
			List<DrugTag> taglist = drugService.getTags();
			return Response.OK(taglist);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getAllTagsByDrug", method = RequestMethod.GET)
	@ApiOperation(value = "获取指定药品的所有的tag", notes = "通用接口")
	public Response getAllTagsByDrug(@ApiParam(name="drugid", value="药品id") @RequestParam(value="drugid") Integer drugid,
			HttpServletRequest request,
			HttpServletResponse response){
		try{
			List<DrugTagInfo> taglist = drugService.getAllTagsByDrug(drugid);
			return Response.OK(taglist);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/addTag", method = RequestMethod.POST)
	@ApiOperation(value = "添加药品标签", notes = "管理接口")
	public Response addTag(@ApiParam(name = "drugid", value = "药品id") @RequestParam(value="drugid") int drugid,
			@ApiParam(name="tagid", value="标签id") @RequestParam(value="tagid") int tagid,
			HttpServletRequest request,HttpServletResponse response){
		try{
			SessionUtil.getManagerId(request);
			
			drugService.addTag(drugid, tagid);
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}	
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/delTag", method = RequestMethod.POST)
	@ApiOperation(value = "删除药品标签", notes = "管理接口")
	public Response delTag(@ApiParam(name = "drugid", value = "药品id") @RequestParam(value="drugid") int drugid,
			@ApiParam(name="tagid", value="标签id") @RequestParam(value="tagid") int tagid,
			HttpServletRequest request,HttpServletResponse response){
		try{
			SessionUtil.getManagerId(request);
			
			drugService.delTag(drugid, tagid);
			return Response.OK(null);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return Response.SystemError();
		}	
	}
}
