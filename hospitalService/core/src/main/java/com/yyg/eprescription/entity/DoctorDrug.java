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

@Table(name="t_doctordrug")
@ApiModel(value = "doctordrugs", description = "医生药品库")
public class DoctorDrug {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.BIGINT)
	@ApiModelProperty(value = "id")
	private Long id;
	
	@Column(name = "drugid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	@ApiModelProperty(value = "drugid")
	private Integer drugid;
	
	@Column(name = "doctorid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	@ApiModelProperty(value = "科室")
	private Integer doctorid;//科室

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDrugid() {
		return drugid;
	}

	public void setDrugid(Integer drugid) {
		this.drugid = drugid;
	}

	public Integer getDoctorid() {
		return doctorid;
	}

	public void setDoctorid(Integer doctorid) {
		this.doctorid = doctorid;
	}

}
