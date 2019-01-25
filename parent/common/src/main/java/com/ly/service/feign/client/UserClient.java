package com.ly.service.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ly.service.context.Response;
import com.ly.service.feign.client.fallback.DefaultUserClient;

@FeignClient(name="user-service", fallback=DefaultUserClient.class)
public interface UserClient {

	@RequestMapping(value = "/internal/addPatient", method = RequestMethod.POST)
	public Response addPatient(
			@RequestParam(name = "uid") Integer uid,
			@RequestParam(name = "name") String name,
			@RequestParam(name = "sex") String sex,	
			@RequestParam(name = "phone") String phone,
			@RequestParam(name = "idcardtype") int idcardtype,
			@RequestParam(name = "idcardnum") String idcardnum,
			@RequestParam(name = "birthday") String birthday);
	
	@RequestMapping(value = "/internal/getDoctor", method = RequestMethod.GET)
	public Response getDoctor(@RequestParam(name = "doctorid") Integer doctorid);
	
	@RequestMapping(value = "/internal/getHospital", method = RequestMethod.GET)
	public Response getHospital(@RequestParam(name = "hid") Integer hid);

	@RequestMapping(value = "/internal/getSeller", method = RequestMethod.GET)
	public Response getSeller(@RequestParam(name = "sellerid") Integer sellerid);
	
	@RequestMapping(value = "/internal/getStore", method = RequestMethod.GET)
	public Response getStore(@RequestParam(name = "storeid") Integer storeid);

	@RequestMapping(value = "/internal/getStoreByGPS", method = RequestMethod.GET)
	Response getStoreByGPS(@RequestParam(name = "drugListStr") String drugListStr,
			@RequestParam(name = "latitude")Double latitude,
			@RequestParam(name = "longitude")Double longitude,
			@RequestParam(name = "size") int size);
}
