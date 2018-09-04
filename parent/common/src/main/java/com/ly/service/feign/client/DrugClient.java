package com.ly.service.feign.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ly.service.entity.Drug;
import com.ly.service.feign.client.fallback.DefaultDrugClient;

@FeignClient(name="drug-service", fallback=DefaultDrugClient.class )
public interface DrugClient {
	
	@RequestMapping(value = "/internal/getDrugsByKeys", method = RequestMethod.GET)
	public List<Drug> getDrugsByKeys(
			 @RequestParam(name = "keys") String keys,
			 @RequestParam(name = "type") int type) ;
	
}
