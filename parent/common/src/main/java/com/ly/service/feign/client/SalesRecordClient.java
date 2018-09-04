package com.ly.service.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ly.service.entity.Order;
import com.ly.service.feign.client.fallback.DefaultSalesRecordClient;

@FeignClient(name="soldrecord-service", fallback=DefaultSalesRecordClient.class )
public interface SalesRecordClient {

	@RequestMapping(value = "/internal/createByStore", method = RequestMethod.POST)
	public void createByOnlinePay(@RequestParam(name = "order") Order order);
	
	@RequestMapping(value = "/internal/createByStore", method = RequestMethod.POST)
	public void createByStore(@RequestParam(name = "storeid") int storeid,
			@RequestParam(name = "order") Order order);
}
