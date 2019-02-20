package com.ly.service.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.utils.RedissonUtil;
import com.ly.service.utils.WxUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/wx")
@Api("微信接口")
public class WxController {

	@Autowired
	RedissonUtil redisson;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/config", method = RequestMethod.GET)
	@ApiOperation(value = "微信初始化", notes = "通用接口")
	public Response config(@ApiParam(name="url", value="签名地址") @RequestParam(name="url")String url,
			HttpServletRequest request, HttpServletResponse response){
		try{
			String jsapi_ticket = (String)redisson.get("wechat_jsapi_ticket");
			Map<String, String> config = WxUtil.sign(jsapi_ticket, url);
			return Response.OK(config);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
	}
}
