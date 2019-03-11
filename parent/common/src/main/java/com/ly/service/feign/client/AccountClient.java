package com.ly.service.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ly.service.context.Response;
import com.ly.service.feign.client.fallback.DefaultAccountClient;


@FeignClient(name="prescription-service", fallback=DefaultAccountClient.class)
public interface AccountClient {

	@RequestMapping(value = "/internal/create", method = RequestMethod.POST)
	public Response create(
			@RequestParam(name = "storeid") Integer storeid);
	
}
