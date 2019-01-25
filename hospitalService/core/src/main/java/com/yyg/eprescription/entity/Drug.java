package com.yyg.eprescription.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_drug")
@ApiModel(value = "drug", description = "药品")
public class Drug implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6087060542192213246L;

	@Id
	@Column(name = "id")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.INTEGER)
	@ApiModelProperty(value = "id")
	private Integer id;
	
	@Column(name = "drugname")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "名称")
	private String drugname;//名称
	
	@Column(name = "shortname")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "简称")
	private String shortname;//简称
	
	@Column(name = "standard")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "规格")
	private String standard;//规格
	
	@Column(name = "category")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "类别")
	private String category;//分类：OTC、处方药、饮片、耗材、保健品、特殊膳食
	
	@Column(name = "unit")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "单位")
	private String unit;//单位：盒、瓶
	
	@Column(name = "form")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "剂型")
	private String form;//剂型,片剂,粉剂,搽剂
	
	@Column(name = "singledose")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "单次剂量")
	private String singledose; //单次剂量
	
	@Column(name = "doseunit")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "剂量单位")
	private String doseunit;//剂量单位
	
	@Column(name = "defaultusage")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "用法")
	private String defaultusage;//用法
	
	@Column(name = "company")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "药厂")
	private String company;//药厂
	
	@Column(name = "frequency")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "频次")
	private String frequency;//频次
	
	@Column(name = "fullkeys")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "药品拼音首字母索引")
	private String fullkeys;//药品拼音首字母索引
	
	public String getFullkeys() {
		return fullkeys;
	}

	public void setFullkeys(String fullkeys) {
		this.fullkeys = fullkeys;
	}

	@Column(name = "shortnamekeys")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "药品简称首字母索引")
	private String shortnamekeys;//药品拼音首字母索引
	
	public static final int STATE_OK = 1;//有货
	public static final int STATE_EMPTY = 0;//缺货
	
	@Column(name = "state")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	@ApiModelProperty(value = "药品状态，1表示有效，0表示无效")
	private int state;//药品拼音首字母索引
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

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
