package com.ly.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ly.BaseMapper;
import com.ly.service.context.TagInfo;
import com.ly.service.entity.TagMap;

public interface TagMapMapper extends BaseMapper<TagMap>{

	List<TagInfo> getTagsByTargetId(@Param(value="targetid")Integer targetid);
    
}
