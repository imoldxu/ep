package com.ly.service.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_storedrug")
public class StoreDrug {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.BIGINT)
	private long id;
	
	@Column(name = "storeid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int storeid;
	
	@Column(name = "drugid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int drugid;
	
	@Column(name = "drugname")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private int drugname;
	
	@Column(name = "drugstander")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private int drugstander;
	
	@Column(name = "drugcompany")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private int drugcompany;
	
	@Column(name = "price")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int price;//售价
	
	@Column(name = "settlementprice")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int settlementprice;//药店给平台的费用, 各个地区的各个药店结算价格都不一致，结算价由管理员设置
	
	public static final int STATE_UP = 1;//有货
	public static final int STATE_DOWN = 2;//没货
		
	@Column(name = "state")
	@ColumnType(jdbcType = JdbcType.TINYINT)
	private int state;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getDrugname() {
		return drugname;
	}

	public void setDrugname(int drugname) {
		this.drugname = drugname;
	}

	public int getDrugstander() {
		return drugstander;
	}

	public void setDrugstander(int drugstander) {
		this.drugstander = drugstander;
	}

	public int getDrugcompany() {
		return drugcompany;
	}

	public void setDrugcompany(int drugcompany) {
		this.drugcompany = drugcompany;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getSettlementprice() {
		return settlementprice;
	}

	public void setSettlementprice(int settlementprice) {
		this.settlementprice = settlementprice;
	}
	
	public int getStoreid() {
		return storeid;
	}

	public void setStoreid(int storeid) {
		this.storeid = storeid;
	}

	public int getDrugid() {
		return drugid;
	}

	public void setDrugid(int drugid) {
		this.drugid = drugid;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
