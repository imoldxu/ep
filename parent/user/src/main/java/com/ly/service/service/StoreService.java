package com.ly.service.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.Store;
import com.ly.service.entity.StoreDrug;
import com.ly.service.feign.client.DrugClient;
import com.ly.service.mapper.StoreMapper;
import com.ly.service.utils.JSONUtils;
import com.ly.service.utils.PasswordUtil;

import tk.mybatis.mapper.entity.Example;

@Service
public class StoreService {

	@Autowired
	StoreMapper storeMapper;
	@Autowired
	DrugClient drugClient;
	
	public Store login(String email, String password){
		Example ex = new Example(Store.class);
		ex.createCriteria().andEqualTo("email", email);
		Store store = storeMapper.selectOneByExample(ex);
		if(store == null){
			throw new HandleException(ErrorCode.NORMAL_ERROR, "用户不存在");
		}
		if(PasswordUtil.isEqual(store.fetchPassword(), password, store.fetchPwdnonce())){
			return store;
		}else{
			throw new HandleException(ErrorCode.NORMAL_ERROR, "密码错误");
		}
			
	}
			
	public List<Store> getStoreByDrug(int drugid, double lat,	double lon){
		
		List<Store> list = storeMapper.getStoreByDrug(drugid, lon, lat);
		
		return list;
	}
	
	public List<Store> getStoreByGPS(double lat, double lon, List<Integer> drugList){
		
		List<Store> list = storeMapper.getStoreByGPS(lon, lat);
		
		String drugListStr = JSONUtils.getJsonString(drugList);
		for(Store store: list){
			Response resp = drugClient.getDrugsInStore(store.getId(), drugListStr);
			ObjectMapper om = new ObjectMapper();
			List<StoreDrug> drugs = om.convertValue(resp.fetchOKData(), new TypeReference<List<StoreDrug>>() {});
			store.setDruglist(drugs);
		}
		return list;
	}

	public void modifyPwd(Integer storeid, String oldPassword, String newPassword) {
		Store store = storeMapper.selectByPrimaryKey(storeid);
		if(PasswordUtil.isEqual(store.fetchPassword(), oldPassword, store.fetchPwdnonce())){
			String newPwd = PasswordUtil.generatePwd(newPassword, store.fetchPwdnonce());
			store.setPassword(newPwd);
			storeMapper.updateByPrimaryKey(store);
		}else{
			throw new HandleException(ErrorCode.NORMAL_ERROR, "旧密码错误");
		}
	}

	public Store create(String name, String address, String email, double longitude, double latitude, String password) {

		Example ex = new Example(Store.class);
		ex.createCriteria().andEqualTo("email", email);
		Store store = storeMapper.selectOneByExample(ex);
		if(store != null){
			throw new HandleException(ErrorCode.NORMAL_ERROR, "该email账号已存在");
		}
		
		store = new Store();
		store.setAddress(address);
		store.setEmail(email);
		store.setLatitude(latitude);
		store.setLongitude(longitude);
		store.setName(name);
		
		String nonce = PasswordUtil.generateNonce();
		store.setPwdnonce(nonce);
		String newPwd = PasswordUtil.generatePwd(password, nonce);
		store.setPassword(newPwd);
		storeMapper.insertUseGeneratedKeys(store);
				
		return store;
	}

	public List<Store> getStoreByName(String name, int pageIndex, int pageSize) {
		Example ex = new Example(Store.class);
		if(name != null && !name.isEmpty()){
			ex.createCriteria().andLike("name", "%"+name+"%");
		}
		ex.setOrderByClause("id DESC");
			
		
		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize, pageSize);
		List<Store> ret = storeMapper.selectByExampleAndRowBounds(ex, rowBounds);
		
		return ret;
	}
}
