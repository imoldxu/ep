package com.ly.service.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_tagmap")
public class TagMap {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.BIGINT)
	private long id;

	@Column(name = "tagetid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	int tagetid;
	
	@Column(name = "tagid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	int tagid;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public int getTagetid() {
		return tagetid;
	}

	public void setTagetid(int tagetid) {
		this.tagetid = tagetid;
	}

	public int getTagid() {
		return tagid;
	}

	public void setTagid(int tagid) {
		this.tagid = tagid;
	}
}
