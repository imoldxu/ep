package com.ly.service.feign.client.fallback;

import org.springframework.stereotype.Component;

import com.ly.service.context.HandleException;
import com.ly.service.entity.HospitalDrug;
import com.ly.service.feign.client.SellerClient;

@Component
public class DefaultSellerClient implements SellerClient{

	@Override
	public HospitalDrug getHospitalDrug(int drugid, int hospitalid) {
		throw new HandleException(-1, "网络异常");
	}


}
