package com.ly.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ly.BaseMapper;
import com.ly.service.entity.DoctorComment;

public interface DoctorCommentMapper extends BaseMapper<DoctorComment>{
	
	Double getAvgStar(@Param(value="doctorid") Integer doctorid);

	List<DoctorComment> getCommentsByDoctor(@Param(value="doctorid") Integer doctorid,
			@Param(value="offset") Integer offset,
			@Param(value="size") Integer size);
}
