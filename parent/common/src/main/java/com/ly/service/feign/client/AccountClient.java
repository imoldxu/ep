package com.ly.service.feign.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ly.service.entity.SalesRecord;
import com.ly.service.entity.SellerAccount;
import com.ly.service.entity.StoreAccount;
import com.ly.service.feign.client.fallback.DefaultAccountClient;

@FeignClient(name="account-service", fallback=DefaultAccountClient.class )
public interface AccountClient {

	@RequestMapping(value = "/internal/addSellerAccount", method = RequestMethod.POST)
	public SellerAccount addSellerAccount(
			 @RequestParam(name = "sellerid") int sellerid,
			 @RequestParam(name = "amount") int amount,
			 @RequestParam(name = "msg") String msg) ;
	

	@RequestMapping(value = "/internal/reduceSellerAccount", method = RequestMethod.POST)
	public SellerAccount reduceSellerAccount(
			 @RequestParam(name = "sellerid") int sellerid,
			 @RequestParam(name = "amount") int amount,
			 @RequestParam(name = "msg") String msg) ;
	

	@RequestMapping(value = "/internal/addStoreAccount", method = RequestMethod.POST)
	public StoreAccount addStoreAccount(
			 @RequestParam(name = "storeid") int storeid,
			 @RequestParam(name = "amount") int amount,
			 @RequestParam(name = "msg") String msg) ;
	

	@RequestMapping(value = "/internal/reduceStoreAccount", method = RequestMethod.POST)
	public StoreAccount reduceStoreAccount(
			 @RequestParam(name = "storeid") int storeid,
			 @RequestParam(name = "amount") int amount,
			 @RequestParam(name = "msg") String msg) ;
	
	@RequestMapping(value = "/internal/settleSalesRecords", method = RequestMethod.POST)
	public void settleSalesRecords(@RequestParam(name = "salesRecords") List<SalesRecord> salesRecords);
}
