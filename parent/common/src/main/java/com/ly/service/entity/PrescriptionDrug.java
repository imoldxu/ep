package com.ly.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_prescription_drug")
public class PrescriptionDrug implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1346530373832969652L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.BIGINT)
	private Long id;
	
	@Column(name = "prescriptionid")
	@ColumnType(jdbcType = JdbcType.BIGINT)
	private Long prescriptionid;//处方id
	
	@Column(name = "drugid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer drugid;//药品编号

	@Column(name = "exid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer exid;//扩展id
	
	@Column(name = "drugname")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String drugname;//名称
	
	@Column(name = "standard")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String standard;//规格
	
	@Column(name = "category")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String category;//分类

	@Column(name = "unit")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String unit;//单位：盒、瓶
	
	@Column(name = "number")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer number;//数量,在药房处方上则是表示药房的销售数量
	
	@Column(name = "soldnumber")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer soldnumber;//数量
	
	@Column(name = "singledose")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String singledose; //单次剂量
	
	@Column(name = "myusage")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String myusage;//用法
	
	@Column(name = "frequency")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String frequency;//频次

	@Transient
	private Integer price;//单价,来源于各个药店的信息
	
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPrescriptionid() {
		return prescriptionid;
	}

	public void setPrescriptionid(Long prescriptionid) {
		this.prescriptionid = prescriptionid;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

//	public Integer getPrice() {
//		return price;
//	}
//
//	public void setPrice(Integer price) {
//		this.price = price;
//	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getSingledose() {
		return singledose;
	}

	public void setSingledose(String singledose) {
		this.singledose = singledose;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	public String getDrugname() {
		return drugname;
	}

	public void setDrugname(String drugname) {
		this.drugname = drugname;
	}

	public String getMyusage() {
		return myusage;
	}

	public void setMyusage(String myusage) {
		this.myusage = myusage;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public Integer getSoldnumber() {
		return soldnumber;
	}

	public void setSoldnumber(Integer soldnumber) {
		this.soldnumber = soldnumber;
	}
	
	
	public Integer getDrugid() {
		return drugid;
	}

	public void setDrugid(Integer drugid) {
		this.drugid = drugid;
	}

	public Integer getExid() {
		return exid;
	}

	public void setExid(Integer exid) {
		this.exid = exid;
	}
	
}
