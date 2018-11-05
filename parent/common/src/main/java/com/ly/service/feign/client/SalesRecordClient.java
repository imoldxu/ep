package com.ly.service.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ly.service.context.Response;
import com.ly.service.entity.Order;
import com.ly.service.feign.client.fallback.DefaultSalesRecordClient;

@FeignClient(name="salesrecord-service", fallback=DefaultSalesRecordClient.class )
public interface SalesRecordClient {

	@RequestMapping(value = "/internal/createByOnlinePay", method = RequestMethod.POST)
	public Response createByOnlinePay(@RequestParam(name = "orderStr") String orderStr);
	
	@RequestMapping(value = "/internal/createByStore", method = RequestMethod.POST)
	public Response createByStore(@RequestParam(name = "storeid") int storeid,
			@RequestParam(name = "orderStr") String orderStr);
}
