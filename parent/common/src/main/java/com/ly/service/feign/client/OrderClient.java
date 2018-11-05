package com.ly.service.feign.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ly.service.context.Response;
import com.ly.service.feign.client.fallback.DefaultOrderClient;

@FeignClient(name="order-service", fallback=DefaultOrderClient.class)
public interface OrderClient {

	@RequestMapping(value = "/internal/create", method = RequestMethod.POST)
	public Response create(@RequestParam(name = "uid") int uid, 
			@RequestParam(name = "amount") int amount,
			@RequestParam(name = "transactionListStr") String transactionListStr);
	
	@RequestMapping(value = "/internal/createByStore", method = RequestMethod.POST)
	public Response createByStore(@RequestParam(name = "storeid") int storeid, 
			@RequestParam(name="uid") int uid,
			@RequestParam(name = "transactionListStr") String transactionListStr);
}
