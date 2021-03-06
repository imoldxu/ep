package com.ly.service.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_store_drug")
public class StoreDrug {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.BIGINT)
	private Long id;
	
	@Column(name = "storeid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer storeid;//药房id
	
	@Column(name = "drugid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer drugid;//药品编号
	
	@Column(name = "drugname")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String drugname;//药品名称
	
	@Column(name = "drugstandard")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String drugstandard;//药品规格
	
	@Column(name = "drugcompany")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String drugcompany;//药品厂商
	
	@Column(name = "price")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer price;//售价
	
	@Column(name = "settlementprice")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer settlementprice;//药店给平台的费用, 各个地区的各个药店结算价格都不一致，结算价由管理员设置
	
	public static final int STATE_UP = 1;//有货
	public static final int STATE_DOWN = 2;//没货
		
	@Column(name = "state")
	@ColumnType(jdbcType = JdbcType.TINYINT)
	private Integer state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDrugname() {
		return drugname;
	}

	public void setDrugname(String drugname) {
		this.drugname = drugname;
	}

	public String getDrugcompany() {
		return drugcompany;
	}

	public void setDrugcompany(String drugcompany) {
		this.drugcompany = drugcompany;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getSettlementprice() {
		return settlementprice;
	}

	public void setSettlementprice(Integer settlementprice) {
		this.settlementprice = settlementprice;
	}
	
	public Integer getStoreid() {
		return storeid;
	}

	public void setStoreid(Integer storeid) {
		this.storeid = storeid;
	}

	public Integer getDrugid() {
		return drugid;
	}

	public void setDrugid(Integer drugid) {
		this.drugid = drugid;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getDrugstandard() {
		return drugstandard;
	}

	public void setDrugstandard(String drugstandard) {
		this.drugstandard = drugstandard;
	}
	
	
}
