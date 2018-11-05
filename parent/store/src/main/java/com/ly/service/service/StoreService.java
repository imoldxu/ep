package com.ly.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.entity.Store;
import com.ly.service.mapper.StoreMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class StoreService {

	@Autowired
	StoreMapper storeMapper;
	
	public Store login(String email, String password){
		Example ex = new Example(Store.class);
		ex.createCriteria().andEqualTo("email", email);
		List<Store> storeList = storeMapper.selectByExample(ex);
		if(storeList.isEmpty()){
			throw new HandleException(ErrorCode.NORMAL_ERROR, "用户不存在");
		}
		Store store = storeList.get(0);
		if(store.getPassword().equals(password)){
			return store;
		}else{
			throw new HandleException(ErrorCode.NORMAL_ERROR, "密码错误");
		}
			
	}
			
	public List<Store> getStoreByDrug(int drugid, double lat,	double lon){
		
		List<Store> list = storeMapper.getStoreByDrug(drugid, lon, lat);
		
		return list;
	}
	
	public List<Store> getStoreByGPS(double lat,	double lon){
		
		List<Store> list = storeMapper.getStoreByGPS(lon, lat);
		
		return list;
	}
}
