package com.ly.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ly.BaseMapper;
import com.ly.service.context.SimpleDrugInfo;
import com.ly.service.entity.HospitalDrug;

public interface HospitalDrugMapper extends BaseMapper<HospitalDrug>{

	 List<SimpleDrugInfo> getDrugsByKeys(@Param(value="hid")Integer hid, @Param(value="mykeys")String mykeys);
	 
	    List<SimpleDrugInfo> getZyDrugsByKeys(@Param(value="hid")Integer hid, @Param(value="mykeys")String mykeys);
	    
	    List<SimpleDrugInfo> getDrugsByKeysWithoutID(@Param(value="hid")Integer hid, @Param(value="myid")Integer myid, @Param(value="mykeys")String mykeys);
	    
	    List<SimpleDrugInfo> getZyDrugsByKeysWithoutID(@Param(value="hid")Integer hid, @Param(value="myid")Integer myid, @Param(value="mykeys")String mykeys);
	    
	    List<SimpleDrugInfo> getDrugByTag(@Param(value="hid")Integer hid, @Param(value="tag")String tag);

	    List<SimpleDrugInfo> getZyDrugByTag(@Param(value="hid")Integer hid, @Param(value="tag")String tag);

	    List<HospitalDrug> getHospitalDrugsByKeys(@Param(value="hid")Integer hid, @Param(value="mykeys")String mykeys,
	    		@Param(value="offset")Integer offset, @Param(value="size")Integer size);
}
