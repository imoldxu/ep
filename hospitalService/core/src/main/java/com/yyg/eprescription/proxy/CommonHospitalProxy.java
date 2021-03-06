package com.yyg.eprescription.proxy;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.yyg.eprescription.context.HandleException;
import com.yyg.eprescription.context.Response;
import com.yyg.eprescription.entity.Prescription;
import com.yyg.eprescription.util.HttpClientUtil;
import com.yyg.eprescription.util.JSONUtils;

public class CommonHospitalProxy {

	public static Prescription getHospitalInfo(String number) {
		HttpClientUtil h = new HttpClientUtil();
		Prescription ret = null;
		try {
			h.open("http://127.0.0.1:8867/diagnosis/getDiagnosisByNumber", "get");
			h.addParameter("number", number);

			h.setRequestHeader("Cookie", "Language=zh_CN;UserAgent=PC");
			int status = h.send();
			if(status==200){
				String resp = h.getResponseBodyAsString("utf-8");
				JsonNode respNode = JSONUtils.getJsonObject(resp);
				JsonNode codeNode = respNode.get("code");
				int code = codeNode.asInt();
				if(code == Response.SUCCESS){
					JsonNode dataNode = respNode.get("data");
					String dataStr = dataNode.toString();
					ret = JSONUtils.getObjectByJson(dataStr, Prescription.class);
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
