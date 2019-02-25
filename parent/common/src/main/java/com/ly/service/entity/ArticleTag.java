package com.ly.service.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_article")
public class ArticleTag {

	@Column(name ="articleid")
	@ColumnType(jdbcType= JdbcType.INTEGER)
	private Integer articleid;
	
	@Column(name ="tagid")
	@ColumnType(jdbcType= JdbcType.INTEGER)
	private Integer tagid;

	public Integer getArticleid() {
		return articleid;
	}

	public void setArticleid(Integer articleid) {
		this.articleid = articleid;
	}

	public Integer getTagid() {
		return tagid;
	}

	public void setTagid(Integer tagid) {
		this.tagid = tagid;
	}


}
