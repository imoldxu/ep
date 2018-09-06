package com.ly.service.feign.client.fallback;

import com.ly.service.entity.SalesRecord;
import com.ly.service.entity.SellerAccount;
import com.ly.service.entity.StoreAccount;
import com.ly.service.feign.client.AccountClient;

public class DefaultAccountClient implements AccountClient{

	@Override
	public SellerAccount addSellerAccount(int sellerid, int amount, String msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SellerAccount reduceSellerAccount(int sellerid, int amount, String msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoreAccount addStoreAccount(int storeid, int amount, String msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoreAccount reduceStoreAccount(int storeid, int amount, String msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void settleSalesRecord(SalesRecord salesRecord) {
		// TODO Auto-generated method stub
		
	}

}
