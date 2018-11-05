package com.ly.service.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ly.service.context.Response;
import com.ly.service.entity.SellerAccount;
import com.ly.service.entity.StoreAccount;
import com.ly.service.feign.client.fallback.DefaultAccountClient;

@FeignClient(name="account-service", fallback=DefaultAccountClient.class)
public interface AccountClient {

	@RequestMapping(value = "/internal/addSellerAccount", method = RequestMethod.POST)
	public Response addSellerAccount(
			 @RequestParam(name = "sellerid") int sellerid,
			 @RequestParam(name = "amount") int amount,
			 @RequestParam(name = "msg") String msg) ;
	

	@RequestMapping(value = "/internal/reduceSellerAccount", method = RequestMethod.POST)
	public Response reduceSellerAccount(
			 @RequestParam(name = "sellerid") int sellerid,
			 @RequestParam(name = "amount") int amount,
			 @RequestParam(name = "msg") String msg) ;
	

	@RequestMapping(value = "/internal/addStoreAccount", method = RequestMethod.POST)
	public Response addStoreAccount(
			 @RequestParam(name = "storeid") int storeid,
			 @RequestParam(name = "amount") int amount,
			 @RequestParam(name = "msg") String msg) ;
	

	@RequestMapping(value = "/internal/reduceStoreAccount", method = RequestMethod.POST)
	public Response reduceStoreAccount(
			 @RequestParam(name = "storeid") int storeid,
			 @RequestParam(name = "amount") int amount,
			 @RequestParam(name = "msg") String msg) ;
	
	@RequestMapping(value = "/internal/settleSalesRecords", method = RequestMethod.POST)
	public Response settleSalesRecords(@RequestParam(name = "salesRecordsStr") String salesRecordsStr);
	
}
