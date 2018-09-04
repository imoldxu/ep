package com.ly.service.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_hospital")
public class Hospital {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer id;

	@Column(name = "name")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String name;

	@Column(name = "email")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String email;

	@Column(name = "password")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String password;
}
