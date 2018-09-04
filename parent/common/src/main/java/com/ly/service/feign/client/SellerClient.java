package com.ly.service.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ly.service.entity.HospitalDrug;
import com.ly.service.feign.client.fallback.DefaultSellerClient;


@FeignClient(name="seller-service", fallback=DefaultSellerClient.class )
public interface SellerClient {

	@RequestMapping(value = "/internal/getHospitalDrug", method = RequestMethod.GET)
	public HospitalDrug getHospitalDrug(@RequestParam(name = "drugid") int drugid, 
			@RequestParam(name = "hospitalid") int hospitalid);
}
