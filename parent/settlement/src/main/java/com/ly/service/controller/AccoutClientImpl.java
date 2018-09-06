package com.ly.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ly.service.entity.SalesRecord;
import com.ly.service.entity.SellerAccount;
import com.ly.service.entity.StoreAccount;
import com.ly.service.feign.client.AccountClient;
import com.ly.service.service.AccountService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/internal")
public class AccoutClientImpl implements AccountClient{

	@Autowired
	AccountService accountService;
	
	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/internal/addSellerAccount", method = RequestMethod.POST)
	@ApiOperation(value = "增加销售人员的余额", notes = "增加销售人员的余额")
	public SellerAccount addSellerAccount(@ApiParam(name="sellerid", value="销售人员id") @RequestParam(name="sellerid") int sellerid,
			@ApiParam(name="amount", value="金额") @RequestParam(name="amount") int amount,
			@ApiParam(name="msg", value="记录信息") @RequestParam(name="msg") String msg) {
		return accountService.addSellerAccount(sellerid, amount, msg);
	}

	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/internal/reduceSellerAccount", method = RequestMethod.POST)
	@ApiOperation(value = "减少销售人员的余额", notes = "减少销售人员的余额")
	public SellerAccount reduceSellerAccount(@ApiParam(name="sellerid", value="销售人员id") @RequestParam(name="sellerid") int sellerid,
			@ApiParam(name="amount", value="金额") @RequestParam(name="amount") int amount,
			@ApiParam(name="msg", value="记录信息") @RequestParam(name="msg") String msg) {
		return accountService.reduceSellerAccount(sellerid, amount, msg);
	}

	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/internal/addStoreAccount", method = RequestMethod.POST)
	@ApiOperation(value = "增加店铺的余额", notes = "增加店铺的余额")
	public StoreAccount addStoreAccount(@ApiParam(name="storeid", value="药店id") @RequestParam(name="storeid") int storeid,
			@ApiParam(name="amount", value="金额") @RequestParam(name="amount") int amount,
			@ApiParam(name="msg", value="记录信息") @RequestParam(name="msg") String msg) {
		return accountService.addStoreAccount(storeid, amount, msg);
	}

	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/internal/reduceSellerAccount", method = RequestMethod.POST)
	@ApiOperation(value = "减少店铺的余额", notes = "减少店铺的余额")
	public StoreAccount reduceStoreAccount(@ApiParam(name="storeid", value="药店id") @RequestParam(name="storeid") int storeid,
			@ApiParam(name="amount", value="金额") @RequestParam(name="amount") int amount,
			@ApiParam(name="msg", value="记录信息") @RequestParam(name="msg") String msg) {
		return accountService.reduceStoreAccount(storeid, amount, msg);
	}

	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/internal/settleSalesRecords", method = RequestMethod.POST)
	@ApiOperation(value = "结算交易记录", notes = "结算交易记录")
	public void settleSalesRecords(@ApiParam(name="salesRecords", value="销售记录") @RequestParam(name="salesRecords") List<SalesRecord> salesRecords) {
		accountService.settleSalesRecords(salesRecords);
	}
	
	
	
}
