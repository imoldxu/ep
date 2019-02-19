package com.yyg.eprescription.proxy;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.yyg.eprescription.context.HandleException;
import com.yyg.eprescription.context.Response;
import com.yyg.eprescription.entity.Doctor;
import com.yyg.eprescription.entity.Drug;
import com.yyg.eprescription.entity.Patient;
import com.yyg.eprescription.entity.ShortDrugInfo;
import com.yyg.eprescription.util.HttpClientUtil;
import com.yyg.eprescription.util.JSONUtils;
import com.yyg.eprescription.util.SignUtil;

/**
 * 平台服务代理
 * @author oldxu
 *
 */
public class PlatformProxy {

	private static final String URL = "http://127.0.0.1:9201";
	private static final String URL2 = "http://127.0.0.1:9202";
	private static final String URL3 = "http://127.0.0.1:9203";
	private static final String signkey = "pzzyy";
	
	public static Doctor login(String phone, String pwd) {
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
					throw new HandleException(4, msg);
				}
			}else{
				throw new HandleException(4, "网络请求异常,status="+status);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new HandleException(4, "网络异常");
		} finally {
			h.close();
		}
		return ret;
    }
	
	public static List<ShortDrugInfo> getDrugsByKeys(Integer hid, String keys, Integer type) {
		HttpClientUtil h = new HttpClientUtil();
		List<ShortDrugInfo> ret = null;
		try {
			h.open(URL2+"/hospital/getDrugsByKeys", "get");
			h.addParameter("hid", hid.toString());
			h.addParameter("keys", URLEncoder.encode(keys, "UTF-8"));
			h.addParameter("type", type.toString());

			h.setRequestHeader("Content-Type", "application/json;charset=utf-8");
			
			int status = h.send();
			if(status==200){
				String resp = h.getResponseBodyAsString("utf-8");
				JsonNode respNode = JSONUtils.getJsonObject(resp);
				JsonNode codeNode = respNode.get("code");
				int code = codeNode.asInt();
				if(code == Response.SUCCESS){
					JsonNode dataNode = respNode.get("data");
					String dataStr = dataNode.toString();
					ret = JSONUtils.getObjectListByJson(dataStr, ShortDrugInfo.class);
				}else{
					JsonNode msgNode = respNode.get("msg");
					String msg = msgNode.asText();
					throw new HandleException(4, msg);
				}
			}else{
				throw new HandleException(4, "网络请求异常,status="+status);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new HandleException(4, "网络异常");
		} finally {
			h.close();
		}
		return ret;
    }
	
	public static List<ShortDrugInfo> getDrugsByTag(Integer hid, String tag, Integer type) {
		HttpClientUtil h = new HttpClientUtil();
		List<ShortDrugInfo> ret = null;
		try {
			h.open(URL2+"/hospital/getDrugListByTag", "get");
			h.addParameter("hid", hid.toString());
			h.addParameter("tag", URLEncoder.encode(tag, "UTF-8"));
			h.addParameter("type", type.toString());

			h.setRequestHeader("Content-Type", "application/json;charset=utf-8");
			
			int status = h.send();
			if(status==200){
				String resp = h.getResponseBodyAsString("utf-8");
				JsonNode respNode = JSONUtils.getJsonObject(resp);
				JsonNode codeNode = respNode.get("code");
				int code = codeNode.asInt();
				if(code == Response.SUCCESS){
					JsonNode dataNode = respNode.get("data");
					String dataStr = dataNode.toString();
					ret = JSONUtils.getObjectListByJson(dataStr, ShortDrugInfo.class);
				}else{
					JsonNode msgNode = respNode.get("msg");
					String msg = msgNode.asText();
					throw new HandleException(4, msg);
				}
			}else{
				throw new HandleException(4, "网络请求异常,status="+status);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new HandleException(4, "网络异常");
		} finally {
			h.close();
		}
		return ret;
    }
	
	public static Drug getDrugById(Integer drugid) {
		HttpClientUtil h = new HttpClientUtil();
		Drug ret = null;
		try {
			h.open(URL2+"/drug/getDrugByID", "get");
			h.addParameter("drugid", drugid.toString());

			h.setRequestHeader("Content-Type", "application/json;charset=utf-8");
			
			int status = h.send();
			if(status==200){
				String resp = h.getResponseBodyAsString("utf-8");
				JsonNode respNode = JSONUtils.getJsonObject(resp);
				JsonNode codeNode = respNode.get("code");
				int code = codeNode.asInt();
				if(code == Response.SUCCESS){
					JsonNode dataNode = respNode.get("data");
					String dataStr = dataNode.toString();
					ret = JSONUtils.getObjectByJson(dataStr, Drug.class);
				}else{
					JsonNode msgNode = respNode.get("msg");
					String msg = msgNode.asText();
					throw new HandleException(4, msg);
				}
			}else{
				throw new HandleException(4, "网络请求异常,status="+status);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new HandleException(4, "网络异常");
		} finally {
			h.close();
		}
		return ret;
    }
	
	public static String commit2Server(Integer doctorid, Integer hospitalid, String prescriptionInfo, String drugListStr) {
		HttpClientUtil h = new HttpClientUtil();
		try {
			Map<String, String> signMap = new HashMap<String, String>();
			
			h.open(URL3+"/prescription/commitByHospital", "post");
			h.addParameter("doctorid", doctorid.toString());
			h.addParameter("hospitalid", hospitalid.toString());
			h.addParameter("prescription", prescriptionInfo);
			h.addParameter("drugList", drugListStr);
			signMap.put("doctorid", doctorid.toString());
			signMap.put("hospitalid", hospitalid.toString());
			signMap.put("prescription", prescriptionInfo);
			signMap.put("drugList", drugListStr);
			String sign = SignUtil.generateSignature(signMap, signkey);
			h.addParameter("sign", sign);
			
			h.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
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
					throw new HandleException(4,msg);
				}
			}else{
				throw new HandleException(4,"网络请求异常,status="+status);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new HandleException(4, "网络异常");
		} finally {
			h.close();
		}
	}

	public static List<ShortDrugInfo> getDrugsByDoctor(Integer hid, Integer doctorid, Integer type) {
		HttpClientUtil h = new HttpClientUtil();
		List<ShortDrugInfo> ret = null;
		try {
			h.open(URL2+"/doctor/getDrugsByDoctor", "get");
			h.addParameter("hid", hid.toString());
			h.addParameter("doctorid", doctorid.toString());
			h.addParameter("type", type.toString());

			h.setRequestHeader("Content-Type", "application/json;charset=utf-8");
			
			int status = h.send();
			if(status==200){
				String resp = h.getResponseBodyAsString("utf-8");
				JsonNode respNode = JSONUtils.getJsonObject(resp);
				JsonNode codeNode = respNode.get("code");
				int code = codeNode.asInt();
				if(code == Response.SUCCESS){
					JsonNode dataNode = respNode.get("data");
					String dataStr = dataNode.toString();
					ret = JSONUtils.getObjectListByJson(dataStr, ShortDrugInfo.class);
				}else{
					JsonNode msgNode = respNode.get("msg");
					String msg = msgNode.asText();
					throw new HandleException(4, msg);
				}
			}else{
				throw new HandleException(4, "网络请求异常,status="+status);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new HandleException(4, "网络异常");
		} finally {
			h.close();
		}
		return ret;
	}
	
	public static Patient getPatient(String barcode) {
		HttpClientUtil h = new HttpClientUtil();
		Patient ret = null;
		try {
			h.open(URL2+"/user/getPatient", "get");
			h.addParameter("barcode", barcode);
			
			h.setRequestHeader("Content-Type", "application/json;charset=utf-8");
			
			int status = h.send();
			if(status==200){
				String resp = h.getResponseBodyAsString("utf-8");
				JsonNode respNode = JSONUtils.getJsonObject(resp);
				JsonNode codeNode = respNode.get("code");
				int code = codeNode.asInt();
				if(code == Response.SUCCESS){
					JsonNode dataNode = respNode.get("data");
					String dataStr = dataNode.toString();
					ret = JSONUtils.getObjectByJson(dataStr, Patient.class);
				}else{
					JsonNode msgNode = respNode.get("msg");
					String msg = msgNode.asText();
					throw new HandleException(4, msg);
				}
			}else{
				throw new HandleException(4, "网络请求异常,status="+status);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new HandleException(4, "网络异常");
		} finally {
			h.close();
		}
		return ret;
	}

}
