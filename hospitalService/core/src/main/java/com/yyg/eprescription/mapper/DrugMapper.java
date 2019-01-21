package com.yyg.eprescription.mapper;

import java.util.List;

//import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yyg.eprescription.BaseMapper;
import com.yyg.eprescription.entity.Drug;
import com.yyg.eprescription.entity.ShortDrugInfo;

@Repository("drugMapper")
//@Mapper
public interface DrugMapper extends BaseMapper<Drug> {

    List<ShortDrugInfo> getDrugsByKeys(@Param(value="mykeys")String mykeys);
 
    List<ShortDrugInfo> getZyDrugsByKeys(@Param(value="mykeys")String mykeys);
    
    List<ShortDrugInfo> getDrugsByKeysWithoutID(@Param(value="myid")Integer myid, @Param(value="mykeys")String mykeys);
    
    List<ShortDrugInfo> getZyDrugsByKeysWithoutID(@Param(value="myid")Integer myid, @Param(value="mykeys")String mykeys);
    
//    List<ShortDrugInfo> getCommonDrugByDoctor(@Param(value="doctorname")String doctorname, @Param(value="department")String department);

//    List<ShortDrugInfo> getCommonZyDrugByDoctor(@Param(value="doctorname")String doctorname, @Param(value="department")String department);

    List<ShortDrugInfo> getDrugBySubCategory(@Param(value="category")String category);

    List<ShortDrugInfo> getZyDrugBySubCategory(@Param(value="category")String category);

}
