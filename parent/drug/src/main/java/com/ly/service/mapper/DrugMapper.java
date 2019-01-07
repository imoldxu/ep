package com.ly.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ly.BaseMapper;
import com.ly.service.context.SimpleDrugInfo;
import com.ly.service.entity.Drug;


@Repository("drugMapper")
//@Mapper
public interface DrugMapper extends BaseMapper<Drug> {

    List<SimpleDrugInfo> getDrugsByKeys(@Param(value="mykeys")String mykeys);
 
    List<SimpleDrugInfo> getZyDrugsByKeys(@Param(value="mykeys")String mykeys);
    
    List<SimpleDrugInfo> getDrugsByKeysWithoutID(@Param(value="myid")Integer myid, @Param(value="mykeys")String mykeys);
    
    List<SimpleDrugInfo> getZyDrugsByKeysWithoutID(@Param(value="myid")Integer myid, @Param(value="mykeys")String mykeys);
    
//    List<ShortDrugInfo> getCommonDrugByDoctor(@Param(value="doctorname")String doctorname, @Param(value="department")String department);

//    List<ShortDrugInfo> getCommonZyDrugByDoctor(@Param(value="doctorname")String doctorname, @Param(value="department")String department);

    List<SimpleDrugInfo> getDrugByTag(@Param(value="tag")String tag);

    List<SimpleDrugInfo> getZyDrugByTag(@Param(value="tag")String tag);

}
