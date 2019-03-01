package com.ly.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ly.BaseMapper;
import com.ly.service.entity.Diagnosis;

public interface DiagnosisMapper extends BaseMapper<Diagnosis> {

	List<String> getMsgByKeys(@Param(value="mykeys")String mykeys);
	
}
