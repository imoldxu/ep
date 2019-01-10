package com.ly.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ly.BaseMapper;
import com.ly.service.entity.Prescription;


public interface PrescriptionMapper extends BaseMapper<Prescription> {

	List<Prescription> getStorePrescripts(@Param(value="storeId")Integer storeId,
			@Param(value="offset")Integer offset,
			@Param(value="pageSize")Integer pageSize);
	
}
