package com.ly.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_drug")
public class Drug implements Serializable{

	private static final long serialVersionUID = 6087060542192213246L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer id;
	
	@Column(name = "drugname")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String drugname;//名称
	
	@Column(name = "shortname")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String shortname;//简称
	
	@Column(name = "standard")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String standard;//规格
	
	@Column(name = "iszy")
	@ColumnType(jdbcType = JdbcType.TINYINT)
	private int iszy;//是否是中药
	
	@Column(name = "category")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String category;//分类：OTC、处方药、饮片、耗材、保健品、特殊膳食

	@Column(name = "unit")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String unit;//单位：盒、瓶
	
	@Column(name = "form")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String form;//剂型,片剂,粉剂,搽剂
	
	@Column(name = "singledose")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String singledose; //单次剂量
	
	@Column(name = "doseunit")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String doseunit;//剂量单位
	
	@Column(name = "defaultusage")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String defaultusage;//用法
	
	@Column(name = "company")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String company;//药厂
	
	@Column(name = "frequency")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String frequency;//频次
	
	@Column(name = "fullkeys")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String fullkeys;//药品拼音首字母索引
	
	public String getFullkeys() {
		return fullkeys;
	}

	public void setFullkeys(String fullkeys) {
		this.fullkeys = fullkeys;
	}

	@Column(name = "shortnamekeys")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String shortnamekeys;//药品拼音首字母索引
	
//	public static final int STATE_OK = 1;//有货
//	public static final int STATE_EMPTY = 0;//缺货
//	
//	@Column(name = "state")
//	@ColumnType(jdbcType = JdbcType.INTEGER)
//	private int state;//药品拼音首字母索引
	
//	public int getState() {
//		return state;
//	}
//
//	public void setState(int state) {
//		this.state = state;
//	}

	public String getShortnamekeys() {
		return shortnamekeys;
	}

	public void setShortnamekeys(String shortnamekeys) {
		this.shortnamekeys = shortnamekeys;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

//	public Double getPrice() {
//		return price;
//	}
//
//	public void setPrice(Double price) {
//		this.price = price;
//	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getSingledose() {
		return singledose;
	}

	public void setSingledose(String singledose) {
		this.singledose = singledose;
	}

	public String getDoseunit() {
		return doseunit;
	}

	public void setDoseunit(String doseunit) {
		this.doseunit = doseunit;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getDrugname() {
		return drugname;
	}

	public void setDrugname(String drugname) {
		this.drugname = drugname;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDefaultusage() {
		return defaultusage;
	}

	public void setDefaultusage(String defaultusage) {
		this.defaultusage = defaultusage;
	}

}
