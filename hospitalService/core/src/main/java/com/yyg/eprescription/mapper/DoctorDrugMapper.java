package com.yyg.eprescription.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yyg.eprescription.BaseMapper;
import com.yyg.eprescription.entity.DoctorDrug;
import com.yyg.eprescription.entity.ShortDrugInfo;

public interface DoctorDrugMapper extends BaseMapper<DoctorDrug>{

	List<ShortDrugInfo> getDrugsByDoctor(@Param(value="doctorid")Integer doctorid);
    
	List<ShortDrugInfo> getZyDrugsByDoctor(@Param(value="doctorid")Integer doctorid);
    
}
