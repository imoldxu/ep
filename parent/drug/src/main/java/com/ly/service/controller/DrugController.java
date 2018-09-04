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

import com.ly.service.context.Response;
import com.ly.service.entity.Drug;
import com.ly.service.entity.Tag;
import com.ly.service.service.DrugService;
import com.ly.service.context.SimpleDrugInfo;
import com.ly.service.utils.ExcelUtils;
import com.ly.service.utils.JSONUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(description = "药品接口")
public class DrugController {
	
	@Autowired
	DrugService drugService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getDrugsByKeys", method = RequestMethod.GET)
	@ApiOperation(value = "根据药品的拼音首字母缩写或药品名称搜索药品", notes = "根据药品的拼音首字母缩写或药品名称搜索药品")
	public Response getDrugsByKeys(
			@ApiParam(name = "keys", value = "拼音首字母索引或药品名称") @RequestParam(name = "keys") String keys,
			@ApiParam(name = "type", value = "西药为1，中药为2") @RequestParam(name = "type") int type,
			HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		
		List<SimpleDrugInfo> ret = drugService.getSimpleDrugListByKeys(type, keys);
		
		Response resp = new Response(Response.SUCCESS, ret, Response.SUCCESS_MSG);
		return resp;
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getDrugListByKeys", method = RequestMethod.GET)
	@ApiOperation(value = "根据药品的拼音缩写搜索药品", notes = "根据药品的拼音缩写搜索药品")
	public Response getDrugListByKeys(
			@ApiParam(name = "keys", value = "拼音首字母索引") @RequestParam(name = "keys") String keys,
			HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		
		List<Drug> ret = drugService.getDrugListByKeys(keys);
		
		Response resp = new Response(Response.SUCCESS, ret, Response.SUCCESS_MSG);
		return resp;
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getDrugListByTag", method = RequestMethod.GET)
	@ApiOperation(value = "根据分类搜索药品", notes = "根据分类搜索药品")
	public Response getDrugListByTag(
			@ApiParam(name = "tag", value = "标签") @RequestParam(name = "tag") String tag,
			@ApiParam(name = "type", value = "西药为1，中药为2") @RequestParam(name = "type") int type,
			HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		
		List<SimpleDrugInfo> ret = drugService.getSimpleDrugListByTag(type, tag);
		
		Response resp = new Response(Response.SUCCESS, ret, Response.SUCCESS_MSG);
		return resp;
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getDrugByID", method = RequestMethod.GET)
	@ApiOperation(value = "获取药品信息", notes = "获取药品信息")
	public Response getDrugByID(
			@ApiParam(name = "drugid", value = "药品id") @RequestParam(name = "drugid") Integer drugid,
			HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		
		Drug drug = drugService.getDrugById(drugid);
		Response resp = null;
		if(drug==null){
			resp = new Response(Response.ERROR, null, "请求的药品不存在或已下架");
		}else{
			resp = new Response(Response.SUCCESS, drug, Response.SUCCESS_MSG);
		}
		return resp;
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/uploadByExcel", method = RequestMethod.POST)
	@ApiOperation(value = "新增上传药品信息", notes = "新增上传药品信息")
	public Response uploadByExcel(@RequestPart(value="file") MultipartFile file,HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		
		if(file==null){
			return new Response(Response.ERROR, null, "请求参数异常");
		}
		
		//获取文件名
	    String name=file.getOriginalFilename();
	    //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
	    long size=file.getSize();
	    if(name == null || ("").equals(name) && size==0){
	    	return new Response(Response.ERROR, null, "文件不存在或没有内容");
	    }
	    //批量导入。参数：文件名，文件。
	    Response resp = null;
	    
	    try{
			ExcelUtils excelUtils = new ExcelUtils();
	    	List<Drug> drugList = excelUtils.getExcelInfo(name, file);
	    	drugService.uploadDrugList(drugList);
	    	resp = new Response(Response.SUCCESS, drugList, Response.SUCCESS_MSG);    
	    }catch(IOException ioe){
	    	 resp = new Response(Response.ERROR,null, ioe.getMessage());
	    }catch (Exception e) {
	    	e.printStackTrace();
	        resp = new Response(Response.ERROR,null, "导入失败");
	    }

		return resp;
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/modifyDrug", method = RequestMethod.POST)
	@ApiOperation(value = "修改药品信息", notes = "{     \"id\": 23,     \"drugname\": \"硫酸亚铁片\",     \"standard\": \"0.3g*60片\",     \"category\": \"OTC\",     \"price\": 38,     \"unit\": \"盒\",     \"form\": \"片剂\",     \"singledose\": \"1\",     \"doseunit\": \"片\",     \"defaultusage\": \"饭前\",     \"frequency\": \"一天三次\",     \"fullkeys\": \"LSYTP\",     \"shortnamekeys\": \"LSYTP\"   }")
	public Response modifyDrug(@RequestParam(value="drugInfo") String drugInfo,HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		
		Drug drug = null;
		try{
			drug = JSONUtils.getObjectByJson(drugInfo, Drug.class);
		}catch (Exception e) {
			return Response.Error(-1, "参数错误");
		}
		
		if(drug.getId() != null){
			int opRet = drugService.modify(drug);
			if(opRet == 0){
				return Response.Error(Response.ERROR, "修改失败");    
			}else{
				return Response.OK(drug);    
			}
		}else{
			return Response.Error(Response.ERROR, "参数错误");
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/delDrug", method = RequestMethod.POST)
	@ApiOperation(value = "删除药品信息", notes = "{     \"id\": 23,     \"drugname\": \"硫酸亚铁片\",     \"standard\": \"0.3g*60片\",     \"category\": \"OTC\",     \"price\": 38,     \"unit\": \"盒\",     \"form\": \"片剂\",     \"singledose\": \"1\",     \"doseunit\": \"片\",     \"defaultusage\": \"饭前\",     \"frequency\": \"一天三次\",     \"fullkeys\": \"LSYTP\",     \"shortnamekeys\": \"LSYTP\"   }")
	public Response delDrug(@RequestParam(value="drugid") int drugid, HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		
		Response resp = null;			
		int optRet = drugService.del(drugid);	
		if(optRet!=0){
			resp = new Response(Response.SUCCESS, null, "删除成功");
		}else{
			resp = new Response(Response.ERROR, null, "药品不存在");
		}
		return resp;
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
	@ApiOperation(value = "获取所有的tag", notes = "获取所有的tag")
	public Response getTags(){
		
		List<Tag> taglist = drugService.getTags();
		return Response.OK(taglist);
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/addTag", method = RequestMethod.POST)
	@ApiOperation(value = "上架药品信息", notes = "上架药品信息")
	public Response addTag(@ApiParam(name = "drugid", value = "药品id") @RequestParam(value="drugid") int drugid,
			@ApiParam(name="tagid", value="标签id") @RequestParam(value="tagid") int tagid,
			@ApiParam(name="tag", value="标签名称") @RequestParam(value="tag") String tag){
		
		drugService.addTag(drugid, tagid, tag);
		return Response.OK(null);
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/delTag", method = RequestMethod.POST)
	@ApiOperation(value = "上架药品信息", notes = "上架药品信息")
	public Response delTag(@ApiParam(name = "mapid", value = "药品id") @RequestParam(value="mapid") long mapid){
		
		drugService.delTag(mapid);
		return Response.OK(null);
	}
}
