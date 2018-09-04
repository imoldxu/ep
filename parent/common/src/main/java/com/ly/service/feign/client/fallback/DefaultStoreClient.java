package com.ly.service.feign.client.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ly.service.context.HandleException;
import com.ly.service.entity.StoreDrug;
import com.ly.service.feign.client.StoreClient;

@Component
public class DefaultStoreClient implements StoreClient{

	@Override
	public List<StoreDrug> getDrugsInStore(Integer storeid, List<Integer> drugList) {
		throw new HandleException(-1, "网络故障");
	}

	@Override
	public StoreDrug getDrugByStore(Integer storeid, Integer drugid) {
		throw new HandleException(-1, "网络故障");
	}

}
