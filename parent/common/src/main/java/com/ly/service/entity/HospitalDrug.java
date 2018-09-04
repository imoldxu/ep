package com.ly.service.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_hospitaldrug")
public class HospitalDrug {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.BIGINT)
	private long id;

	@Column(name = "drugid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int drugid;//药品id
	
	@Column(name = "drugname")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String drugname;//药品名称
	
	@Column(name = "drugstandard")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String drugstandard;//药品规则
	
	@Column(name = "drugcompany")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String drugcompany;//药品厂商
	
	@Column(name = "sellerid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int sellerid;//销售人员id
	
	@Column(name = "sellername")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String sellername;//销售人员姓名
	
	@Column(name = "hospitalid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int hospitalid;//医院id

	@Column(name = "hospitalname")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String hospitalname;//医院名称
	
	@Column(name = "sellfee")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int sellfee;//推广费分 不同医院，不用药品的推广费不一样
	
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

	public String getSellername() {
		return sellername;
	}

	public void setSellername(String sellername) {
		this.sellername = sellername;
	}

	public String getHospitalname() {
		return hospitalname;
	}

	public void setHospitalname(String hospitalname) {
		this.hospitalname = hospitalname;
	}

	public int getSellfee() {
		return sellfee;
	}

	public void setSellfee(int sellfee) {
		this.sellfee = sellfee;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getDrugid() {
		return drugid;
	}

	public void setDrugid(int drugid) {
		this.drugid = drugid;
	}

	public int getSellerid() {
		return sellerid;
	}

	public void setSellerid(int sellerid) {
		this.sellerid = sellerid;
	}

	public int getHospitalid() {
		return hospitalid;
	}

	public void setHospitalid(int hospitalid) {
		this.hospitalid = hospitalid;
	}
	
}
