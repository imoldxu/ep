package com.ly.service.feign.client.fallback;

import org.springframework.stereotype.Component;

import com.ly.service.feign.client.UserClient;

@Component
public class DefaultUserClient implements UserClient {

	@Override
	public void addPatient(Integer uid, String name, String sex, String phone, int idcardtype, String idcardnum, String birthday) {		
		return;
	}

}
