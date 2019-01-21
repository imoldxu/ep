package com.yyg.eprescription.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yyg.eprescription.BaseMapper;
import com.yyg.eprescription.entity.DiagnosisMsg;

public interface DiagnosisMsgMapper extends BaseMapper<DiagnosisMsg> {
	
	 List<String> getMsgByKeys(@Param(value="mykeys")String mykeys);
}
