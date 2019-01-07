package com.ly.service.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_user_tag_map")
public class UserTagMap {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.BIGINT)
	private Long id;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
