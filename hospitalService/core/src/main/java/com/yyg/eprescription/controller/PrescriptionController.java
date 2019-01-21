package com.yyg.eprescription.controller;



import java.io.IOException;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.yyg.eprescription.context.Response;
import com.yyg.eprescription.entity.CountPrescriptionInfo;
import com.yyg.eprescription.entity.DiagnosisMsg;
import com.yyg.eprescription.entity.Doctor;
import com.yyg.eprescription.entity.DoctorDrugs;
import com.yyg.eprescription.entity.Prescription;
import com.yyg.eprescription.entity.PrescriptionDrugs;
import com.yyg.eprescription.entity.PrescriptionInfo;
import com.yyg.eprescription.entity.PrescriptionNumber;
import com.yyg.eprescription.entity.SearchOption;
import com.yyg.eprescription.mapper.DiagnosisMsgMapper;
import com.yyg.eprescription.mapper.DoctorDrugsMapper;
import com.yyg.eprescription.mapper.PrescriptionDrugsMapper;
import com.yyg.eprescription.mapper.PrescriptionMapper;
import com.yyg.eprescription.mapper.PrescriptionNumberMapper;
import com.yyg.eprescription.proxy.WSAPIProxy;
import com.yyg.eprescription.service.DiagnosisService;
import com.yyg.eprescription.service.PrescriptionService;
import com.yyg.eprescription.util.ChineseCharacterUtil;
import com.yyg.eprescription.util.ExportUtil;
import com.yyg.eprescription.util.HttpClientUtil;
import com.yyg.eprescription.util.JSONUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/prescition")
@Api(description = "处方签接口")
public class PrescriptionController {
		
	@Autowired
	PrescriptionService prescriptionService;
	
	/**
	 * 从武胜医院的webservice获取信息
	 * @param number
	 * @return
	 * @throws Exception
	 */	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	@ApiOperation(value = "获取诊断信息", notes = "彭州人民医院、中医院专用")
	public Response initForPz(
			@ApiParam(name = "hospitalNum", value = "诊断号") @RequestParam(name = "hospitalNum") String hospitalNum,
			HttpServletRequest request, HttpServletResponse respons) {
		respons.setHeader("Access-Control-Allow-Methods", "GET");
		try{    
			Prescription ret = prescriptionService.init(hospitalNum);
			Response resp = new Response(Response.SUCCESS, ret, Response.SUCCESS_MSG);
			return resp;
		}catch(Exception e){
			e.printStackTrace();
			Response resp = new Response(Response.ERROR, null, e.getMessage());
			return resp;
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/initWithType", method = RequestMethod.GET)
	@ApiOperation(value = "获取诊断信息--武胜专用", notes = "获取处方信息--武胜专用")
	public Response initForWS(
			@ApiParam(name = "type", value = "门诊1，住院2") @RequestParam(name = "type") String type,
			@ApiParam(name = "hospitalNum", value = "诊断号") @RequestParam(name = "hospitalNum") String hospitalNum,
			HttpServletRequest request, HttpServletResponse respons) {
		respons.setHeader("Access-Control-Allow-Methods", "GET");
		try{
			Prescription ret = prescriptionService.init(type, hospitalNum);
			Response resp = new Response(Response.SUCCESS, ret, Response.SUCCESS_MSG);
			return resp;
		}catch (Exception e) {
			e.printStackTrace();
			Response resp = new Response(Response.ERROR, null, e.getMessage());
			return resp;
		}
	}
	
	
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getPrescriptionList", method = RequestMethod.GET)
	@ApiOperation(value = "获取处方列表", notes = "管理接口")
	public Response getPrescriptionList(
			@ApiParam(name = "option", value = "查询条件") @RequestParam(name = "option") String option,
			HttpServletRequest request, HttpServletResponse respons) {
		//respons.setHeader("Access-Control-Allow-Origin", "*");
		respons.setHeader("Access-Control-Allow-Methods", "GET");
		Response resp = null;
		try{
			SearchOption searchOption = JSONUtils.getObjectByJson(option, SearchOption.class);
			
			List<Prescription> plist = prescriptionService.getPresciptionList(searchOption);
			
			resp = new Response(Response.SUCCESS, plist, "成功");		
			return resp;
		}catch (Exception e) {
			System.out.println("arg option is====>"+option);
			e.printStackTrace();
			return new Response(Response.ERROR, null, "系统异常");		
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getPrescriptionByID", method = RequestMethod.GET)
	@ApiOperation(value = "获取处方详情", notes = "管理接口")
	public Response getPrescriptionByID(
			@ApiParam(name = "id", value = "处方id") @RequestParam(name = "id") Integer id,
			HttpServletRequest request, HttpServletResponse respons) {
		//respons.setHeader("Access-Control-Allow-Origin", "*");
		//respons.setHeader("Access-Control-Allow-Methods", "GET");
		
		try{
			Prescription ret = prescriptionService.getPrescriptionByID(id); 		
			return new Response(Response.SUCCESS, ret, "成功");
		}catch(Exception e){
			return new Response(Response.ERROR, null, e.getMessage());
		}
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/open", method = RequestMethod.POST)
	@ApiOperation(value = "开处方", notes = "开处方")
	public Response open(
			@ApiParam(name = "doctorid", value = "医生id") @RequestParam(name = "doctorid") Integer doctorid,
			@ApiParam(name = "hospitalid", value = "医院id") @RequestParam(name = "hospitalid") Integer hospitalid,
			@ApiParam(name = "prescriptionInfo", value = "处方信息") @RequestParam(name = "prescriptionInfo") String prescriptionInfo,
			@ApiParam(name = "drugList", value = "处方药列表") @RequestParam(name = "drugList") String drugList,
			HttpServletRequest request, HttpServletResponse respons) {
		//respons.setHeader("Access-Control-Allow-Origin", "*");
		//respons.setHeader("Access-Control-Allow-Methods", "POST");
		
		Response resp = null;
		try{
		
			Prescription p = JSONUtils.getObjectByJson(prescriptionInfo, Prescription.class);
						
			List<PrescriptionDrugs> drugs = JSONUtils.getObjectListByJson(drugList, PrescriptionDrugs.class);
			if (p==null || drugs==null) {
				resp = new Response(Response.ERROR, null, "请求参数错误");
				return resp;
			}else if(drugs.isEmpty()){
				resp = new Response(Response.ERROR, null, "请选择至少一种药品");
				return resp;
			}
			
			String ret = prescriptionService.open(doctorid, hospitalid, prescriptionInfo, drugList, p, drugs);
			
			resp = new Response(Response.SUCCESS, ret, Response.SUCCESS_MSG);
			return resp;
		}catch (Exception e) {
			System.out.println("prescription is ====>"+prescriptionInfo);
			System.out.println("drugLis is ====>"+ drugList);
			e.printStackTrace();
			resp = new Response(Response.ERROR, null, "系统异常");
			return resp;
		}
	}

	
	

//	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
//	@RequestMapping(value = "/over", method = RequestMethod.POST)
//	@ApiOperation(value = "已领药", notes = "已领药")
//	public Response over(
//			@ApiParam(name = "id", value = "处方id") @RequestParam(name = "id") Integer id,
//			HttpServletRequest request, HttpServletResponse respons) {
//		respons.setHeader("Access-Control-Allow-Origin", "*");
//		respons.setHeader("Access-Control-Allow-Methods", "POST");
//		
//		Response resp = null;
//		
//		Prescription p = prescriptionMapper.selectByPrimaryKey(id);
//		if (p==null) {
//			resp = new Response(Response.ERROR, null, "请求参数错误");
//			return resp;
//		}
//		prescriptionMapper.updateByPrimaryKey(p);
//		
//		resp = new Response(Response.SUCCESS, p, Response.SUCCESS_MSG);
//		return resp;
//	}
	
//	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
//	@RequestMapping(value = "/rollback", method = RequestMethod.POST)
//	@ApiOperation(value = "回退成未领药", notes = "回退成未领药")
//	public Response rollback(
//			@ApiParam(name = "id", value = "处方id") @RequestParam(name = "id") Integer id,
//			HttpServletRequest request, HttpServletResponse respons) {
//		respons.setHeader("Access-Control-Allow-Origin", "*");
//		respons.setHeader("Access-Control-Allow-Methods", "POST");
//		
//		Response resp = null;
//		
//		Prescription p = prescriptionMapper.selectByPrimaryKey(id);
//		if (p==null) {
//			resp = new Response(Response.ERROR, null, "请求参数错误");
//			return resp;
//		}
//		p.setState(Prescription.STATE_NEW);
//		prescriptionMapper.updateByPrimaryKey(p);
//		
//		resp = new Response(Response.SUCCESS, p, Response.SUCCESS_MSG);
//		return resp;
//	}
	


	//中医处方统计要和西医分开
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/downloadExcel", method = RequestMethod.GET)
	@ApiOperation(value = "导出处方签", notes = "导出处方签")
	public void downloadExcel(
			HttpServletRequest request, HttpServletResponse respons) {
		//respons.setHeader("Access-Control-Allow-Origin", "*");
		respons.setHeader("Access-Control-Allow-Methods", "GET");
		respons.setContentType("application/x-msdownload");  
        
		Date now = new Date();
		
		Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(now);
        int i = localCalendar.get(Calendar.MONTH);
        if(i==0){
        	int y = localCalendar.get(Calendar.YEAR);
        	localCalendar.set(Calendar.YEAR, y-1);
        	localCalendar.set(Calendar.MONTH, 11);
        }else{
        	localCalendar.set(Calendar.MONTH, i - 1);
        }
		Date lastMonth = localCalendar.getTime();
        
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM");
	    localSimpleDateFormat.setLenient(false);
	    String lastMonthStr = localSimpleDateFormat.format(lastMonth);
		
		List<CountPrescriptionInfo> infoList = prescriptionService.countPrescription(lastMonthStr);
		
		try {
			String fileName = "处方统计表"+lastMonthStr+".xlsx";  
            respons.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "utf-8"));
			ServletOutputStream outputStream = respons.getOutputStream();
			exportExcel(infoList, outputStream);		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void exportExcel(List<CountPrescriptionInfo> list, ServletOutputStream outputStream) {  
        // 创建一个workbook 对应一个excel应用文件  
        XSSFWorkbook workBook = new XSSFWorkbook();  
        // 在workbook中添加一个sheet,对应Excel文件中的sheet  
        XSSFSheet sheet = workBook.createSheet("统方信息");  
        ExportUtil exportUtil = new ExportUtil(workBook, sheet);  
        XSSFCellStyle headStyle = exportUtil.getHeadStyle();  
        XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();  
        // 构建表头  
        XSSFRow headRow = sheet.createRow(0);
        String[] titles = { "药品名", "医生名", "科室", "数量", "计价单位"}; 
        XSSFCell cell = null;  
        for (int i = 0; i < titles.length; i++)  
        {  
            cell = headRow.createCell(i);  
            cell.setCellStyle(headStyle);  
            cell.setCellValue(titles[i]);  
        }  
        // 构建表体数据  
        if (list != null && list.size() > 0)  
        {  
            for (int j = 0; j < list.size(); j++)  
            {  
                XSSFRow bodyRow = sheet.createRow(j + 1);  
                CountPrescriptionInfo info = list.get(j);  
  
                cell = bodyRow.createCell(0);  
                cell.setCellStyle(bodyStyle);
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(info.getDrugname());  
  
                cell = bodyRow.createCell(1);  
                cell.setCellStyle(bodyStyle);
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(info.getDoctorname());  
  
                cell = bodyRow.createCell(2);  
                cell.setCellStyle(bodyStyle);  
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(info.getDepartment());

                cell = bodyRow.createCell(3);  
                cell.setCellStyle(bodyStyle);
                cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                cell.setCellValue(info.getCountnumber());
                
                cell = bodyRow.createCell(4);  
                cell.setCellStyle(bodyStyle);
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(info.getDrugunit());
            }  
        }  
        try  
        {  
            workBook.write(outputStream);  
            outputStream.flush();  
            outputStream.close();  
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
        }  
        finally  
        {  
            try  
            {  
                outputStream.close();  
            }  
            catch (IOException e)  
            {  
                e.printStackTrace();  
            }  
        }  
  
    }
}
