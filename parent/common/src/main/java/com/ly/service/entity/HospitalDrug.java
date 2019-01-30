package com.ly.service.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_hospital_drug")
public class HospitalDrug {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.BIGINT)
	private Long id;

	@Column(name = "drugid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer drugid;//药品id
	
	@Column(name = "drugname")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String drugname;//药品名称
	
	@Column(name = "drugstandard")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String drugstandard;//药品规则
	
	@Column(name = "drugcompany")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String drugcompany;//药品厂商
	
	@Column(name = "exid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer exid;//第三方id
	
	@Column(name = "hospitalid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer hospitalid;//医院id

	@Column(name = "hospitalname")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String hospitalname;//医院名称
		
	public String getDrugname() {
		return drugname;
	}

	public void setDrugname(String drugname) {
		this.drugname = drugname;
	}

	public String getDrugstandard() {
		return drugstandard;
	}

	public void setDrugstandard(String drugstandard) {
		this.drugstandard = drugstandard;
	}

	public String getDrugcompany() {
		return drugcompany;
	}

	public void setDrugcompany(String drugcompany) {
		this.drugcompany = drugcompany;
	}

//	public String getSellername() {
//		return sellername;
//	}
//
//	public void setSellername(String sellername) {
//		this.sellername = sellername;
//	}

	public String getHospitalname() {
		return hospitalname;
	}

	public void setHospitalname(String hospitalname) {
		this.hospitalname = hospitalname;
	}

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

	public Integer getHospitalid() {
		return hospitalid;
	}

	public void setHospitalid(Integer hospitalid) {
		this.hospitalid = hospitalid;
	}

	public Integer getExid() {
		return exid;
	}

	public void setExid(Integer exid) {
		this.exid = exid;
	}
	
}
