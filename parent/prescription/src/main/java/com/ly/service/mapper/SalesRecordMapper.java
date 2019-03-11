package com.ly.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ly.BaseMapper;
import com.ly.service.context.PieData;
import com.ly.service.entity.SalesRecord;

public interface SalesRecordMapper extends BaseMapper<SalesRecord>{

	List<SalesRecord> getSalesRecordForRefund(@Param(value="storeid") Integer storeid, @Param(value="drugid") Integer drugid, @Param(value="pid") Long pid);

	Integer getIncomeByDay(@Param(value="day") String day);
	
	Integer getIncomeByMonth(@Param(value="month") Integer month, @Param(value="year") Integer year);

	List<PieData> getSalesByDrug(@Param(value="startTime")String startTime,
			@Param(value="endTime")String endTime,
			@Param(value="size")Integer size);
	
	List<PieData> getSalesByStore(@Param(value="startTime")String startTime,
			@Param(value="endTime")String endTime,
			@Param(value="size")Integer size);
}
