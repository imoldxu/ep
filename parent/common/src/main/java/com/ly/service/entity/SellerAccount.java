//package com.ly.service.entity;
//
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import org.apache.ibatis.type.JdbcType;
//
//import tk.mybatis.mapper.annotation.ColumnType;
//
//@Table(name="t_seller_account")
//public class SellerAccount {
//
//	@Id
//	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@ColumnType(jdbcType = JdbcType.INTEGER)
//	private Integer id;
//	
//	@Column(name = "sellerid")
//	@ColumnType(jdbcType = JdbcType.INTEGER)
//	private Integer sellerid;
//	
//	@Column(name = "balance")
//	@ColumnType(jdbcType = JdbcType.INTEGER)
//	private Integer balance;
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public Integer getSellerid() {
//		return sellerid;
//	}
//
//	public void setSellerid(Integer sellerid) {
//		this.sellerid = sellerid;
//	}
//
//	public Integer getBalance() {
//		return balance;
//	}
//
//	public void setBalance(Integer balance) {
//		this.balance = balance;
//	}
//	
//}
