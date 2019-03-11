package com.ly.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ly.BaseMapper;
import com.ly.service.entity.StoreAccount;

public interface StoreAccountMapper extends BaseMapper<StoreAccount> {

	public List<StoreAccount> getStoreAccountByName(@Param(value="name") String name,
			@Param(value="offset") Integer offset,
			@Param(value="size") Integer size);
}
