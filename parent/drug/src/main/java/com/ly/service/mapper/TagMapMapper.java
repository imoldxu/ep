package com.ly.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ly.BaseMapper;
import com.ly.service.context.DrugTagInfo;
import com.ly.service.entity.DrugTagMap;

public interface TagMapMapper extends BaseMapper<DrugTagMap>{

	List<DrugTagInfo> getTagsByDrugId(@Param(value="drugid")Integer drugid);
 
	List<DrugTagInfo> getAllTagsByDrugId(@Param(value="drugid")Integer drugid);
}
