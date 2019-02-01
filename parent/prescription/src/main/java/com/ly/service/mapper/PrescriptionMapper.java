package com.ly.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ly.BaseMapper;
import com.ly.service.entity.Prescription;


public interface PrescriptionMapper extends BaseMapper<Prescription> {

	Prescription getStorePrescriptByPid(@Param(value="storeId")Integer storeId,
			@Param(value="pid")Long pid);
	
	List<Prescription> getStorePrescripts(@Param(value="storeId")Integer storeId,
			@Param(value="startDate")String startDate,
			@Param(value="endDate")String endDate,
			@Param(value="offset")Integer offset,
			@Param(value="pageSize")Integer pageSize);
	
	List<Prescription> getStorePrescriptsWithPatient(@Param(value="storeId")Integer storeId,
			@Param(value="patientName")String patientName,
			@Param(value="startDate")String startDate,
			@Param(value="endDate")String endDate,
			@Param(value="offset")Integer offset,
			@Param(value="pageSize")Integer pageSize);
}
