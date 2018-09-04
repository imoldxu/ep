package com.ly.service.feign.client.fallback;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ly.service.entity.Drug;
import com.ly.service.feign.client.DrugClient;

@Component
public class DefaultDrugClient implements DrugClient{

	@Override
	public List<Drug> getDrugsByKeys(String keys, int type) {
		return new ArrayList<Drug>();
	}


}
