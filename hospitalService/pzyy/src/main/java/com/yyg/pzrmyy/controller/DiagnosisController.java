package com.yyg.pzrmyy.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yyg.pzrmyy.context.Response;
import com.yyg.pzrmyy.entity.Diagnosis;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/diagnosis")
@Api(description = "医院诊断信息接口")
public class DiagnosisController {
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getDiagnosisByNumber", method = RequestMethod.GET)
	@ApiOperation(value = "获取诊断信息", notes = "获取诊断信息")
	public Response getDiagnosisByNumber(
			@ApiParam(name = "number", value = "诊断号") @RequestParam(name = "number") String number,
			HttpServletRequest request, HttpServletResponse respons) {
		respons.setHeader("Access-Control-Allow-Origin", "*");
		respons.setHeader("Access-Control-Allow-Methods", "GET");
		Response resp = null;
		Diagnosis diagnosis = null;
		try{
		    diagnosis = getDInfo(number);
		    resp = new Response(Response.SUCCESS, diagnosis, "成功");
		}catch (Exception e) {
			e.printStackTrace();
			resp = new Response(Response.ERROR, null, e.getMessage());
		}
		return resp;
	}
		
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/getPatientByNumber", method = RequestMethod.GET)
	@ApiOperation(value = "根据就诊卡获取患者信息", notes = "根据就诊卡获取患者信息")
	public Response getPatientByNumber(
			@ApiParam(name = "number", value = "就诊卡号") @RequestParam(name = "number") String number,
			HttpServletRequest request, HttpServletResponse respons) {
		respons.setHeader("Access-Control-Allow-Origin", "*");
		respons.setHeader("Access-Control-Allow-Methods", "GET");
		Response resp = null;
		Diagnosis diagnosis = null;
		try{
		    diagnosis = getPInfo(number);
		    resp = new Response(Response.SUCCESS, diagnosis, "成功");
		}catch (Exception e) {
			e.printStackTrace();
			resp = new Response(Response.ERROR, null, e.getMessage());
		}
		
		return resp;
	}
	
	
	private Diagnosis getDInfo(String number) throws Exception{
		Diagnosis diagnosis = null;
				
		Connection ct=null;
		Statement sm=null;
		try
		{
			//1.加载驱动(把需要的驱动程序加入内存）
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			//2.得到连接(指定连接到那个数据源
			Properties info = new Properties(); 
	        info.setProperty("charSet","GB2312"); 
	        info.setProperty("user", "_system"); 
	        info.setProperty("password", "sys");
			ct=DriverManager.getConnection("jdbc:odbc:yaofang", info);//("jdbc:odbc:yaofang","_system","sys");
			//3.创建statement或者preparedstatement
			//statement：用来发送sql语句
			sm=ct.createStatement();
			//4.执行，包括crud等任何操作
			//演示，添加一条数据到dept表 
			//executeupdate可以增删改，返回值为影响条数
			//彭州中医院、彭州人民医院的数据库sql
			String sql ="select PAADM_PAPMI_DR->papmi_name as pname ,PAADM_PAPMI_DR->PAPMI_Sex_DR->CTSEX_Desc as psex, PAADM_PAPMI_DR->PAPMI_RowId1->PAPER_AgeYr as page,PAADM_PAPMI_DR->PAPMI_RowId1->PAPER_TelH as pphone,PAADM_DepCode_DR->ctloc_desc as department,PAADM_AdmDocCodeDR->ctpcp_desc as doctorname,PAADM_ADMNo as dnum from pa_adm where PAADM_ADMNo='"+number+"'";
			ResultSet result = sm.executeQuery(sql);
			if(result.next()){
				diagnosis = new Diagnosis();
				
				diagnosis.setPatientname(result.getString(1));
				diagnosis.setPatientsex(result.getString(2));
				diagnosis.setPatientage(result.getString(3));
				diagnosis.setPatientphone(result.getString(4));
				diagnosis.setDepartment(result.getString(5));
				diagnosis.setDoctorname(result.getString(6));
				diagnosis.setNum(result.getString(7));
				
				return diagnosis;
			}else{
				throw new Exception("诊断号错误或不存在,请检查诊断号");
			}
		}catch (Exception e)		{
			e.printStackTrace();
			throw new Exception("医院HIS链接错误");
		}
		finally	{
		//关闭资源,，谁后创建，谁先关闭
			try
			{
				if(sm!=null)
				{
					sm.close();
				}
				if(ct!=null)
				{
					ct.close();
				}
			}catch (SQLException e1){
				e1.printStackTrace();
				throw new Exception("系统异常,请联系管理员");
			}
		}
	}
	
	private Diagnosis getPInfo(String number) throws Exception{
		
		
		Diagnosis diagnosis = null;
		
		diagnosis = new Diagnosis();
		
		//diagnosis.setPatientname(result.getString(1));
		//diagnosis.setPatientsex(result.getString(2));
		//diagnosis.setPatientage(result.getString(3));
		//diagnosis.setPatientphone(result.getString(4));
		diagnosis.setPatientname("老徐");
		diagnosis.setPatientsex("男");
		diagnosis.setPatientage("23");
		diagnosis.setPatientphone("13120312301");
		
		return diagnosis;
		
//		Connection ct=null;
//		Statement sm=null;
//		try
//		{
//			//1.加载驱动(把需要的驱动程序加入内存）
//			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//			//2.得到连接(指定连接到那个数据源
//			Properties info = new Properties(); 
//	        info.setProperty("charSet","GB2312"); 
//	        info.setProperty("user", "_system"); 
//	        info.setProperty("password", "sys"); 
//			 ct=DriverManager.getConnection("jdbc:odbc:yaofang", info);//("jdbc:odbc:yaofang","_system","sys");
//			//3.创建statement或者preparedstatement
//			//statement：用来发送sql语句
//			sm=ct.createStatement();
//			//4.执行，包括crud等任何操作
//			//演示，添加一条数据到dept表 
//			//executeupdate可以增删改，返回值为影响条数
//			String sql ="select PAADM_PAPMI_DR->papmi_name as pname ,PAADM_PAPMI_DR->PAPMI_Sex_DR->CTSEX_Desc as psex, PAADM_PAPMI_DR->PAPMI_RowId1->PAPER_AgeYr as page,PAADM_PAPMI_DR->PAPMI_RowId1->PAPER_TelH as pphone,PAADM_DepCode_DR->ctloc_desc as department,PAADM_AdmDocCodeDR->ctpcp_desc as doctorname,PAADM_ADMNo as dnum from pa_adm where PAADM_ADMNo='"+number+"'";
//			ResultSet result = sm.executeQuery(sql);
//			if(result.next()){
//				diagnosis = new Diagnosis();
//				
//				diagnosis.setPatientname(result.getString(1));
//				diagnosis.setPatientsex(result.getString(2));
//				diagnosis.setPatientage(result.getString(3));
//				diagnosis.setPatientphone(result.getString(4));
//				
//				return diagnosis;
//			}else{
//				throw new Exception("就诊卡号错误或不存在，请检查");
//			}
//		}catch (Exception e)		{
//			e.printStackTrace();
//			throw new Exception("系统异常,请联系管理员");
//		}
//		finally	{
//		//关闭资源,，谁后创建，谁先关闭
//			try
//			{
//				if(sm!=null)
//				{
//					sm.close();
//				}
//				if(ct!=null)
//				{
//					ct.close();
//				}
//			}catch (SQLException e1){
//				e1.printStackTrace();
//				throw new Exception("系统异常,请联系管理员");
//			}
//		}
	}
//	private String asciiToString(String value)
//    {
//        StringBuffer sbu = new StringBuffer();
//        String[] chars = value.split(",");
//        for (int i = 0; i < chars.length; i++) {
//            sbu.append((char) Integer.parseInt(chars[i]));
//        }
//        return sbu.toString();
//    }
	
//	public static String unicodeToString(String unicode) {
//
//        String str = unicode.replace("0x", "\\");
//
//        StringBuffer string = new StringBuffer();
//        String[] hex = str.split("\\\\u");
//        for (int i = 1; i < hex.length; i++) {
//            int data = Integer.parseInt(hex[i], 16);
//            string.append((char) data);
//        }
//        return string.toString();
//    }
	
//	public static String gb2312ToUtf8(String str) {
//
//        String urlEncode = "" ;
//
//        try {
//
//            urlEncode = URLEncoder.encode (str, "UTF-8" );
//
//        } catch (UnsupportedEncodingException e) {
//
//            e.printStackTrace();
//
//        }
//
//        return urlEncode;
//
//	}
}
