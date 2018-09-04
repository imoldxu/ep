package com.ly.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ly.service.entity.StoreDrug;
import com.ly.service.feign.client.StoreClient;
import com.ly.service.service.StoreDrugService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/internal")
@Api("商户内部接口")
public class StoreClientImpl implements StoreClient{
	
	@Autowired
	StoreDrugService storeDrugService;
	
	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping("/getDrugsInStore")
	@ApiOperation(value = "获取该药店与处方匹配的药品", notes = "获取该药店与处方匹配的药品")
	public List<StoreDrug> getDrugsInStore(@ApiParam(name = "storeid", value = "邮箱") @RequestParam(name = "storeid")Integer storeid,
			@ApiParam(name = "drugList", value = "药品清单") @RequestParam(name = "drugList") List<Integer> drugList){
		
		return  storeDrugService.getDrugsInStore(storeid, drugList);
	
	}

	@Override
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping("/getDrugByStore")
	@ApiOperation(value = "获取该药店对应的药品", notes = "获取该药店对应的药品")
	public StoreDrug getDrugByStore(@ApiParam(name = "storeid", value = "药房id") @RequestParam(name = "storeid") Integer storeid,
			@ApiParam(name = "drugid", value = "药品id") @RequestParam(name = "drugid") Integer drugid) {
		return storeDrugService.getDrugByStore(storeid, drugid);
	}
}
