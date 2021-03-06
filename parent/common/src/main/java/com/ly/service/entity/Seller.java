//package com.ly.service.entity;
//
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.persistence.Transient;
//
//import org.apache.ibatis.type.JdbcType;
//
//import tk.mybatis.mapper.annotation.ColumnType;
//
//@Table(name="t_seller")
//public class Seller {
//
//	@Id
//	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@ColumnType(jdbcType = JdbcType.INTEGER)
//	private Integer id;
//	
//	@Column(name = "name")
//	@ColumnType(jdbcType = JdbcType.VARCHAR)
//	private String name;	
//	
//	@Column(name = "phone")
//	@ColumnType(jdbcType = JdbcType.VARCHAR)
//	private String phone;
//	
//
//	@Column(name = "password")
//	@ColumnType(jdbcType = JdbcType.VARCHAR)
//	private String password;
//	
//	@Column(name = "pwdnonce")
//	@ColumnType(jdbcType = JdbcType.VARCHAR)
//	private String pwdnonce;
//	
//	@Column(name = "wxunionid")
//	@ColumnType(jdbcType = JdbcType.VARCHAR)
//	private String wxunionid;
//	
//	@Column(name = "createtime")
//	@ColumnType(jdbcType = JdbcType.TIMESTAMP)
//	private Date createtime;
//	
//	@Column(name = "lastlogintime")
//	@ColumnType(jdbcType = JdbcType.TIMESTAMP)
//	private Date lastlogintime;
//
//	@Transient
//	private int subscribe;
//
//	@Transient
//	private String sessionID;
//	
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getPhone() {
//		return phone;
//	}
//
//	public void setPhone(String phone) {
//		this.phone = phone;
//	}
//
//	public String fetchPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public String fetchWxunionid() {
//		return wxunionid;
//	}
//
//	public void setWxunionid(String wxunionid) {
//		this.wxunionid = wxunionid;
//	}
//
//	public Date fetchCreatetime() {
//		return createtime;
//	}
//
//	public void setCreatetime(Date createtime) {
//		this.createtime = createtime;
//	}
//
//	public Date fetchLastlogintime() {
//		return lastlogintime;
//	}
//
//	public void setLastlogintime(Date lastlogintime) {
//		this.lastlogintime = lastlogintime;
//	}
//
//	public int getSubscribe() {
//		return subscribe;
//	}
//	
//	public void setSubscribe(int subscribe) {
//		this.subscribe = subscribe;
//	}
//
//	public String fetchPwdnonce() {
//		return pwdnonce;
//	}
//
//	public void setPwdnonce(String pwdnonce) {
//		this.pwdnonce = pwdnonce;
//	}
//
//	public String getSessionID() {
//		return sessionID;
//	}
//
//	public void setSessionID(String sessionID) {
//		this.sessionID = sessionID;
//	}
//	
//	
//}
