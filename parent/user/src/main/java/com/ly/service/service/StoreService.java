package com.ly.service.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.entity.Store;
import com.ly.service.entity.StoreAccount;
import com.ly.service.entity.StoreDrug;
import com.ly.service.feign.client.AccountClient;
import com.ly.service.feign.client.DrugClient;
import com.ly.service.mapper.StoreMapper;
import com.ly.service.utils.JSONUtils;
import com.ly.service.utils.PasswordUtil;
import com.ly.service.utils.ValidDataUtil;

import tk.mybatis.mapper.entity.Example;

@Service
public class StoreService {

	@Autowired
	StoreMapper storeMapper;
	@Autowired
	DrugClient drugClient;
	@Autowired
	AccountClient accountClient;
	
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
	
	public List<Store> getStoreByGPS(double lat, double lon, List<Integer> drugList, int size){
		
		List<Store> list = storeMapper.getStoreByGPS(lon, lat, size);
		
		String drugListStr = JSONUtils.getJsonString(drugList);
		for(int i=(list.size()-1); i>=0; i--){
			Store store = list.get(i);
			List<StoreDrug> drugs = drugClient.getDrugsInStore(store.getId(), drugListStr).fetchOKData(new TypeReference<List<StoreDrug>>() {});
			if(drugs.isEmpty()){
				list.remove(store);//若是药房没有镀银的药则屏蔽掉该药房
			}else{
				store.setDrugList(drugs);
			}
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

	@Transactional
	public Store create(String name, String address, String email, double longitude, double latitude, String password, double rate) {

		if(!ValidDataUtil.isEmail(email)) {
			throw new HandleException(ErrorCode.ARG_ERROR, "邮箱格式不正确");
		}
		
		Example ex = new Example(Store.class);
		ex.createCriteria().andEqualTo("email", email);
		Store store = storeMapper.selectOneByExample(ex);
		if(store != null){
			throw new HandleException(ErrorCode.NORMAL_ERROR, "该账号已存在");
		}
		
		store = new Store();
		store.setAddress(address);
		store.setEmail(email);
		store.setLatitude(latitude);
		store.setLongitude(longitude);
		store.setName(name);
		store.setRate(rate);
		
		String nonce = PasswordUtil.generateNonce();
		store.setPwdnonce(nonce);
		String newPwd = PasswordUtil.generatePwd(password, nonce);
		store.setPassword(newPwd);
		storeMapper.insertUseGeneratedKeys(store);

		accountClient.create(store.getId()).fetchOKData(StoreAccount.class);
		
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

	public Store getStore(Integer storeid) {
		Store ret = storeMapper.selectByPrimaryKey(storeid);
		if(ret == null){
			throw new HandleException(ErrorCode.ARG_ERROR, "该药店不存在");
		}
		return ret;
	}

	public Store modify(Store store) {
		if(store.fetchPassword()!=null) {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "更新失败，不允许直接更新密码");
		}
		int ret = storeMapper.updateByPrimaryKeySelective(store);
		if(ret == 1) {
			return store;
		}else {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "更新失败");
		}
	}

	public void resetPwd(Integer storeid) {
		Store store = storeMapper.selectByPrimaryKey(storeid);
		
		String nonce = store.fetchPwdnonce();
		
		
		String clientPwd = PasswordUtil.generateClientPwd("123456");
		String newPwd = PasswordUtil.generatePwd(clientPwd, nonce);
		store.setPassword(newPwd);
		
		storeMapper.updateByPrimaryKey(store);
	}
}
