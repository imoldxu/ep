package com.yyg.eprescription.proxy;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.yyg.eprescription.context.Response;
import com.yyg.eprescription.entity.Doctor;
import com.yyg.eprescription.util.HttpClientUtil;
import com.yyg.eprescription.util.JSONUtils;

/**
 * 平台服务代理
 * @author oldxu
 *
 */
public class PlatformProxy {

	private static final String URL = "http://127.0.0.1:9201";
	private static final String URL2 = "http://127.0.0.1:9202";
	private static final String URL3 = "http://127.0.0.1:9203";
	
	public static Doctor login(String phone, String pwd) throws Exception {
		HttpClientUtil h = new HttpClientUtil();
		Doctor ret = null;
		try {
			h.open(URL+"/doctor/login", "post");
			h.addParameter("phone", phone);
			h.addParameter("password", pwd);
			//h.setRequestHeader("Cookie", "Language=zh_CN;UserAgent=PC");
			int status = h.send();
			if(status==200){
				String resp = h.getResponseBodyAsString("utf-8");
				JsonNode respNode = JSONUtils.getJsonObject(resp);
				JsonNode codeNode = respNode.get("code");
				int code = codeNode.asInt();
				if(code == Response.SUCCESS){
					JsonNode dataNode = respNode.get("data");
					String dataStr = dataNode.toString();
					ret = JSONUtils.getObjectByJson(dataStr, Doctor.class);
				}else{
					JsonNode msgNode = respNode.get("msg");
					String msg = msgNode.asText();
					throw new Exception(msg);
				}
			}else{
				throw new Exception("网络请求异常,status="+status);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("系统异常，请联系管理员");
		} finally {
			h.close();
		}
		return ret;
    }
	
	public static String commit2Server(Integer doctorid, Integer hospitalid, String prescriptionInfo, String drugListStr) throws Exception {
		HttpClientUtil h = new HttpClientUtil();
		try {
			h.open(URL3+"/prescription/commitByHospital", "post");
			h.addParameter("doctorid", doctorid.toString());
			h.addParameter("hospitalid", hospitalid.toString());
			h.addParameter("perscription", prescriptionInfo);
			h.addParameter("drugList", drugListStr);
			//h.setRequestHeader("Cookie", "Language=zh_CN;UserAgent=PC");
			int status = h.send();
			if(status==200){
				String resp = h.getResponseBodyAsString("utf-8");
				JsonNode respNode = JSONUtils.getJsonObject(resp);
				JsonNode codeNode = respNode.get("code");
				int code = codeNode.asInt();
				if(code == Response.SUCCESS){
					JsonNode dataNode = respNode.get("data");
					String dataStr = dataNode.toString();
					return dataStr;
				}else{
					JsonNode msgNode = respNode.get("msg");
					String msg = msgNode.asText();
					throw new Exception(msg);
				}
			}else{
				throw new Exception("网络请求异常,status="+status);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("系统异常，请联系管理员");
		} finally {
			h.close();
		}
	}

}
