package com.ly.service.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ly.service.context.Response;
import com.ly.service.feign.client.fallback.DefaultDrugClient;


@FeignClient(name="drug-service", fallback=DefaultDrugClient.class )
public interface DrugClient {
	
	@RequestMapping(value = "/internal/getDrugsByKeys", method = RequestMethod.GET)
	public Response getDrugsByKeys(
			 @RequestParam(name = "keys") String keys,
			 @RequestParam(name = "type") int type) ;
	
	@RequestMapping(value = "/internal/getHospitalDrug", method = RequestMethod.GET)
	public Response getHospitalDrug(@RequestParam(name = "drugid") int drugid, 
			@RequestParam(name = "hospitalid") int hospitalid);
	
	@RequestMapping(value="/internal/getDrugsInStore", method = RequestMethod.GET)
	public Response getDrugsInStore(@RequestParam(name = "storeid")Integer storeid,
			@RequestParam(name = "drugListStr") String drugListStr);
	
	@RequestMapping(value="/internal/getDrugByStore", method = RequestMethod.GET)
	public Response getDrugByStore(@RequestParam(name = "storeid")Integer storeid,
			@RequestParam(name = "drugid") Integer drugid);
	
	@RequestMapping(value = "/internal/getStoresByDrugs", method = RequestMethod.GET)
	public Response getStoresByDrugs(@RequestParam(name = "drugidListStr") String drugidListStr,
			@RequestParam(name = "latitude") double latitude,
			@RequestParam(name = "longitude") double longitude,
			@RequestParam(name = "size")int size);
	
	@RequestMapping(value = "/internal/addDoctorDrugs", method = RequestMethod.POST)
	public Response addDoctorDrugs(@RequestParam(name = "drugidListStr") String drugidListStr,
			@RequestParam(name = "doctorid") Integer doctorid);
}
