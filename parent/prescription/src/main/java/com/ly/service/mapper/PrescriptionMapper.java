package com.ly.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ly.BaseMapper;
import com.ly.service.entity.Prescription;


public interface PrescriptionMapper extends BaseMapper<Prescription> {

	List<Prescription> getStorePrescripts(@Param(value="storeid")Integer storeid,
			@Param(value="offset")Integer offset,
			@Param(value="size")Integer size);
	
}
