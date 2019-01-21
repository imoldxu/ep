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

@Table(name="t_doctordrugs")
@ApiModel(value = "doctordrugs", description = "医生药品库")
public class DoctorDrugs {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.INTEGER)
	@ApiModelProperty(value = "id")
	private Integer id;
	
	@Column(name = "drugid")
	@ColumnType(jdbcType = JdbcType.BIGINT)
	@ApiModelProperty(value = "drugid")
	private Long drugid;
	
	@Column(name = "doctorid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	@ApiModelProperty(value = "科室")
	private Integer doctorid;//科室

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getDrugid() {
		return drugid;
	}

	public void setDrugid(Long drugid) {
		this.drugid = drugid;
	}

	public Integer getDoctorid() {
		return doctorid;
	}

	public void setDoctorid(Integer doctorid) {
		this.doctorid = doctorid;
	}

}
