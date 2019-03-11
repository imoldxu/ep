package com.ly.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ly.BaseMapper;
import com.ly.service.context.StoreAndDrugInfo;
import com.ly.service.entity.StoreDrug;

public interface StoreDrugMapper extends BaseMapper<StoreDrug> {

	List<StoreAndDrugInfo> getStoreByDrugs(@Param(value="drugids") String drugids, @Param(value="lat") double lat,
			@Param(value="lon") double lon, @Param(value="size") int size);
	
	List<StoreDrug> getStoreDrugsByKeys(@Param(value="storeid") Integer storeid, @Param(value="key") String key,
			@Param(value="offset") Integer offset, @Param(value="size") Integer size);
	
	List<StoreDrug> getStoreDrugsByKeysAndState(@Param(value="storeid") Integer storeid, @Param(value="key") String key,
			@Param(value="state") Integer state,
			@Param(value="offset") Integer offset, @Param(value="size") Integer size);
}
