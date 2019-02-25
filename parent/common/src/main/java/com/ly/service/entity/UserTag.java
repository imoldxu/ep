package com.ly.service.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_user_tag")
public class UserTag {

	@Column(name = "uid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer uid;
	
	@Column(name = "tagid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer tagid;
	
	@Transient
	private String tarname;

	public String getTarname() {
		return tarname;
	}

	public void setTarname(String tarname) {
		this.tarname = tarname;
	}

	public Integer getTagid() {
		return tagid;
	}

	public void setTagid(Integer tagid) {
		this.tagid = tagid;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
}
