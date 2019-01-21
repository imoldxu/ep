package com.yyg.eprescription.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_diagnosismsg")
@ApiModel(value = "diagnosismsg", description = "诊断描述")
public class DiagnosisMsg {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.INTEGER)
	@ApiModelProperty(value = "id")
	private Integer id;
	
	@Column(name = "diagnosis")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "diagnosis")
	private String diagnosis;
	
	@Column(name = "fullkeys")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "fullkeys")
	private String fullkeys;

	@Column(name = "shortkeys")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "shortkeys")
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

	public String getShortkeys() {
		return shortkeys;
	}

	public void setShortkeys(String shortkeys) {
		this.shortkeys = shortkeys;
	}
	
	public String getFullkeys() {
		return fullkeys;
	}

	public void setFullkeys(String fullkeys) {
		this.fullkeys = fullkeys;
	}
}
