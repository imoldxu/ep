package com.ly.service.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.entity.Article;
import com.ly.service.mapper.ArticleMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class ArticleService {

	@Autowired
	ArticleMapper mapper;
	
	public List<Article> getUserArticleList(Integer uid, int pageIndex, int pageSize) {
		//Example ex = new Example(Article.class);
		//ex.setOrderByClause("articleid DESC");
		//RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize, pageSize);
		//List<Article> ret = mapper.selectByExampleAndRowBounds(ex, rowBounds);
		List<Article> ret = mapper.getArticleList((pageIndex-1)*pageSize, pageSize);
		return ret;
	}

	public Article getArticleById(int articleId) {
		Article ret = mapper.selectByPrimaryKey(articleId);
		if(ret == null) {
			throw new HandleException(ErrorCode.ARG_ERROR, "你请求的文章不存在");
		}
		return ret;
	}

	public Article publish(String title, JSONArray piclist, String content) {
		Article ret = new Article();
		ret.setTitle(title);
		ret.setPiclist(piclist);
		ret.setCreatetime(new Date());
		ret.setContent(content);
		ret.setState(Article.STATE_VALID);
		mapper.insertUseGeneratedKeys(ret);
		return ret;
	}
	
	public List<Article> getArticleList(String title, int pageIndex, int pageSize) {
		//Example ex = new Example(Article.class);
		//ex.setOrderByClause("articleid DESC");
		//RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize, pageSize);
		//List<Article> ret = mapper.selectByExampleAndRowBounds(ex, rowBounds);
		List<Article> ret = mapper.getArticleListByTitle("%"+title+"%" ,(pageIndex-1)*pageSize, pageSize);
		return ret;
	}

	public Article up(Integer articleId) {
		Article article = mapper.selectByPrimaryKey(articleId);
		article.setState(Article.STATE_VALID);
		mapper.updateByPrimaryKey(article);
		return article;
	}
	
	public Article down(Integer articleId) {
		Article article = mapper.selectByPrimaryKey(articleId);
		article.setState(Article.STATE_INVALID);
		mapper.updateByPrimaryKey(article);
		return article;
	}

	public Article modify(Integer articleId, String title, JSONArray piclist, String content) {
		Article article = mapper.selectByPrimaryKey(articleId);
		article.setTitle(title);
		article.setPiclist(piclist);
		article.setContent(content);
		mapper.updateByPrimaryKey(article);
		return article;
	}
}
