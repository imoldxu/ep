package com.yyg.eprescription.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_prescriptiondrugs")
@ApiModel(value = "prescriptiondrugs", description = "处方中开具的药品")
public class PrescriptionDrugs implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1346530373832969652L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.BIGINT)
	@ApiModelProperty(value = "id")
	private Long id;
	
	@Column(name = "prescriptionid")
	@ColumnType(jdbcType = JdbcType.BIGINT)
	@ApiModelProperty(value = "处方编号")
	private Long prescriptionid;//处方编号
	
	@Column(name = "drugid")
	@ColumnType(jdbcType = JdbcType.BIGINT)
	@ApiModelProperty(value = "药品编号")
	private Long drugid;//处方编号
	
	
	public Long getDrugid() {
		return drugid;
	}

	public void setDrugid(Long drugid) {
		this.drugid = drugid;
	}

	@Column(name = "drugname")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "名称")
	private String drugname;//名称
	
	@Column(name = "standard")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "规格")
	private String standard;//规格
	
	@Column(name = "category")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "规格")
	private String category;//分类
	
	@Column(name = "unit")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "单位")
	private String unit;//单位：盒、瓶
	
	@Column(name = "number")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	@ApiModelProperty(value = "数量")
	private int number;//数量
	
	@Column(name = "singledose")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "单次剂量")
	private String singledose; //单次剂量
	
	@Column(name = "myusage")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "用法")
	private String myusage;//用法
	
	@Column(name = "frequency")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "频次")
	private String frequency;//频次

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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
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
}
