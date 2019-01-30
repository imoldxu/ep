//package com.ly.service.entity;
//
//import java.util.Date;
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
//
//@Table(name="t_seller_account_record")
//public class SellerAccountRecord {
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
//	public static final int TYPE_INCOME = 1;
//	public static final int TYPE_PAYOUT = 2;
//	
//	@Column(name = "type")
//	@ColumnType(jdbcType = JdbcType.TINYINT)
//	private Integer type;
//	
//	@Column(name = "code")
//	@ColumnType(jdbcType = JdbcType.TINYINT)
//	private Integer code;
//	
//	@Column(name = "amount")
//	@ColumnType(jdbcType = JdbcType.INTEGER)
//	private Integer amount;
//
//	@Column(name = "msg")
//	@ColumnType(jdbcType = JdbcType.VARCHAR)
//	private String msg;
//
//	@Column(name = "createtime")
//	@ColumnType(jdbcType = JdbcType.TIMESTAMP)
//	private Date createtime;
//	
//	public Date getCreatetime() {
//		return createtime;
//	}
//
//	public void setCreatetime(Date createtime) {
//		this.createtime = createtime;
//	}
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
//	public Integer getType() {
//		return type;
//	}
//
//	public void setType(Integer type) {
//		this.type = type;
//	}
//
//	public Integer getCode() {
//		return code;
//	}
//
//	public void setCode(Integer code) {
//		this.code = code;
//	}
//
//	public Integer getAmount() {
//		return amount;
//	}
//
//	public void setAmount(Integer amount) {
//		this.amount = amount;
//	}
//
//	public String getMsg() {
//		return msg;
//	}
//
//	public void setMsg(String msg) {
//		this.msg = msg;
//	}
//}
