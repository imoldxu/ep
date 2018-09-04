package com.ly.service.feign.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ly.service.context.TransactionDrug;
import com.ly.service.entity.Order;
import com.ly.service.feign.client.fallback.DefaultOrderClient;

@FeignClient(name="order-service", fallback=DefaultOrderClient.class )
public interface OrderClient {

	@RequestMapping(value = "/internal/create", method = RequestMethod.POST)
	public Order create(@RequestParam(name = "uid") int uid, 
			@RequestParam(name = "amount") int amount,
			@RequestParam(name = "transactionList") List<TransactionDrug> transactionList);
	
	@RequestMapping(value = "/internal/createByStore", method = RequestMethod.POST)
	public Order createByStore(@RequestParam(name = "storeid") int storeid, 
			@RequestParam(name="uid") int uid,
			@RequestParam(name = "transactionList") List<TransactionDrug> transactionList);
}
