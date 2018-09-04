package com.ly.service.feign.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ly.service.entity.StoreDrug;
import com.ly.service.feign.client.fallback.DefaultStoreClient;


@FeignClient(name="store-service", fallback=DefaultStoreClient.class )
public interface StoreClient {

	@RequestMapping("/internal/getDrugsInStore")
	public List<StoreDrug> getDrugsInStore(@RequestParam(name = "storeid")Integer storeid,
			@RequestParam(name = "drugList") List<Integer> drugList);
	
	@RequestMapping("/internal/getDrugsInStore")
	public StoreDrug getDrugByStore(@RequestParam(name = "storeid")Integer storeid,
			@RequestParam(name = "drugid") Integer drugid);
	
}
