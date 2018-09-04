package com.ly.service.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;


@Table(name="t_selleraccountrecord")
public class SellerAccountRecord {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int id;
	
	@Column(name = "sellerid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int sellerid;
	
	public static final int TYPE_INCOME = 1;
	public static final int TYPE_PAYOUT = 2;
	
	@Column(name = "type")
	@ColumnType(jdbcType = JdbcType.TINYINT)
	private int type;
	
	@Column(name = "code")
	@ColumnType(jdbcType = JdbcType.TINYINT)
	private int code;
	
	@Column(name = "amount")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int amount;

	@Column(name = "msg")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String msg;

	@Column(name = "createdate")
	@ColumnType(jdbcType = JdbcType.TIMESTAMP)
	private Date createdate;
	
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSellerid() {
		return sellerid;
	}

	public void setSellerid(int sellerid) {
		this.sellerid = sellerid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
