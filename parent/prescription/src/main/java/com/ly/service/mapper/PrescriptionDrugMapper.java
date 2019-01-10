package com.ly.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ly.BaseMapper;
import com.ly.service.entity.PrescriptionDrug;

public interface PrescriptionDrugMapper extends BaseMapper<PrescriptionDrug> {
   
	List<PrescriptionDrug> getPrescriptDrugsByStore(@Param(value="storeID")Integer storeID,
			@Param(value="pid")Long pid);
}
