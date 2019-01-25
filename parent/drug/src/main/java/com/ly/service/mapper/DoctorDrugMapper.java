package com.ly.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ly.BaseMapper;
import com.ly.service.context.SimpleDrugInfo;
import com.ly.service.entity.DoctorDrug;

public interface DoctorDrugMapper extends BaseMapper<DoctorDrug>{

	 List<SimpleDrugInfo> getDrugsByDoctor(@Param(value="hid")Integer hid, @Param(value="doctorid")Integer doctorid);
	 
	 List<SimpleDrugInfo> getZyDrugsByDoctor(@Param(value="hid")Integer hid, @Param(value="doctorid")Integer doctorid);
	    
}
