package com.ly.service.service;

import org.springframework.stereotype.Service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;

@Service
public class SMSService {

	public void sendSMS(String phone, String code) {
        DefaultProfile profile = DefaultProfile.getProfile("default", "<accessKeyId>", "<accessSecret>");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "12323");
        request.putQueryParameter("TemplateCode", "SM_0000");
        request.putQueryParameter("TemplateParam", "{\"code\":"+code+"}");
        //request.putQueryParameter("SmsUpExtendCode", "123");
        //request.putQueryParameter("OutId", "22");
        
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
            throw new HandleException(ErrorCode.NORMAL_ERROR, "短信服务异常");
        } catch (ClientException e) {
            e.printStackTrace();
            throw new HandleException(ErrorCode.NORMAL_ERROR, "短信服务异常");
        }
    }
	
}
