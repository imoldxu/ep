package com.ly.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ly.BaseMapper;
import com.ly.service.entity.Article;

public interface ArticleMapper extends BaseMapper<Article>{

	public List<Article> getArticleList(@Param(value="offset")int offset, @Param(value="size")int size);
	
}
