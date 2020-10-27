package com.ly.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import com.ly.BaseMapper;
import com.ly.service.entity.Article;

import tk.mybatis.mapper.provider.SpecialProvider;

public interface ArticleMapper extends BaseMapper<Article>{

	public List<Article> getArticleList(@Param(value="offset")int offset, @Param(value="size")int size);
	
	public List<Article> getArticleListByTitle(@Param(value="title")String title, @Param(value="offset")int offset, @Param(value="size")int size);

	 @Options(useGeneratedKeys = true, keyProperty = "articleid")
	 @InsertProvider(type = SpecialProvider.class, method = "dynamicSQL")
	 int insertUseGeneratedKeys(Article record);
}
