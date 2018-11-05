package com.ly.service.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ly.service.context.Response;
import com.ly.service.feign.client.fallback.DefaultStoreClient;


@FeignClient(name="store-service", fallback=DefaultStoreClient.class )
public interface StoreClient {

	@RequestMapping("/internal/getDrugsInStore")
	public Response getDrugsInStore(@RequestParam(name = "storeid")Integer storeid,
			@RequestParam(name = "drugListStr") String drugListStr);
	
	@RequestMapping("/internal/getDrugByStore")
	public Response getDrugByStore(@RequestParam(name = "storeid")Integer storeid,
			@RequestParam(name = "drugid") Integer drugid);
	
}
