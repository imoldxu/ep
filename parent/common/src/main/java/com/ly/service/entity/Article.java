package com.ly.service.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import com.alibaba.fastjson.JSONArray;
import com.ly.service.typehandler.JSONArrayHandler;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_article")
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="articleid")
	@ColumnType(jdbcType= JdbcType.INTEGER)
	private Integer articleid;
	
	@Column(name ="title")
	@ColumnType(jdbcType= JdbcType.VARCHAR)
	private String title;

	@Column(name ="url")
	@ColumnType(jdbcType= JdbcType.VARCHAR)
	private String url;
	
	@Column(name ="content")
	@ColumnType(jdbcType= JdbcType.VARCHAR)
	private String content;
	
	@Column(name ="piclist")
	@ColumnType(jdbcType= JdbcType.VARCHAR, typeHandler=JSONArrayHandler.class)
	private JSONArray piclist;
	
	@Column(name ="createtime")
	@ColumnType(jdbcType= JdbcType.TIMESTAMP)
	private Date createtime;

	@Column(name ="source")
	@ColumnType(jdbcType= JdbcType.VARCHAR)
	private String source;
	
	public static final Integer STATE_VALID = 1;
	public static final Integer STATE_INVALID = 0;
	
	@Column(name ="state")
	@ColumnType(jdbcType= JdbcType.TINYINT)
	private Integer state;
	
	public Integer getArticleid() {
		return articleid;
	}

	public void setArticleid(Integer articleid) {
		this.articleid = articleid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public JSONArray getPiclist() {
		return piclist;
	}

	public void setPiclist(JSONArray piclist) {
		this.piclist = piclist;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
