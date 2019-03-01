package com.ly.service.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_diagnosis")
public class Diagnosis {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer id;
	
	@Column(name = "diagnosis")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String diagnosis;
	
	@Column(name = "fullkeys")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String fullkeys;

	@Column(name = "shortkeys")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String shortkeys;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getFullkeys() {
		return fullkeys;
	}

	public void setFullkeys(String fullkeys) {
		this.fullkeys = fullkeys;
	}

	public String getShortkeys() {
		return shortkeys;
	}

	public void setShortkeys(String shortkeys) {
		this.shortkeys = shortkeys;
	}

}
