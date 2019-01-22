package com.ly.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ly.BaseMapper;
import com.ly.service.entity.Store;

public interface StoreMapper extends BaseMapper<Store>{

	public List<Store> getStoreByDrug(@Param(value="drugid")int drugid, @Param(value="lon")double lon, @Param(value="lat")double lat);

	public List<Store> getStoreByGPS(@Param(value="lon")double lon, @Param(value="lat")double lat, @Param(value="size") int size);
}
