package com.ly.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ly.BaseMapper;
import com.ly.service.entity.SalesRecord;

public interface SalesRecordMapper extends BaseMapper<SalesRecord>{

	List<SalesRecord> getSalesRecordForRefund(@Param(value="storeid") Integer storeid, @Param(value="drugid") Integer drugid, @Param(value="pid") Long pid);
}
