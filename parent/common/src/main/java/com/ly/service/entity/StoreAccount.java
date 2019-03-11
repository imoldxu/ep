package com.ly.service.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_store_account")
public class StoreAccount {
	
	@Id
	@Column(name = "storeid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer storeid;
	
	@Transient
	private String name;
	
	@Transient
	private String address;
	
	@Column(name = "balance")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer balance;

	public Integer getStoreid() {
		return storeid;
	}

	public void setStoreid(Integer storeid) {
		this.storeid = storeid;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
