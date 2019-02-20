package com.ly.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.service.utils.RedissonUtil;
import com.ly.service.utils.WxUtil;

@Component
public class WxTask {

	@Autowired
	RedissonUtil redisson;
	
	//每隔一个小时刷新一次wx的token和jsapi_ticket
	@Scheduled(fixedRate=3600000)
	public void refreshAccessToken() {
		String token = WxUtil.getToken();
		redisson.set("wechat_access_token", token, 7200000L);
		
		String jsapi_ticket = WxUtil.getTicket(token);
		redisson.set("wechat_access_token", jsapi_ticket, 7200000L);
	}
}
