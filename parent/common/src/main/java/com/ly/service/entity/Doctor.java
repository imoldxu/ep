package com.ly.service.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_doctor")
public class Doctor {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer id;
	
	@Column(name = "name")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String name;//姓名
	
	@Column(name = "hospitalid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer hospitalid;//医院id
	
	@Column(name = "department")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String department;//部门
	
	@Column(name = "phone")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String phone;//电话

	@Column(name = "password")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String password;//密码
	
	@Column(name = "pwdnonce")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String pwdnonce;//密码
	
	@Column(name = "wxunionid")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String wxunionid;//微信唯一账号
	
	@Column(name = "createtime")
	@ColumnType(jdbcType = JdbcType.TIMESTAMP)
	private Date createtime;//创建时间
	
	@Column(name = "lastlogintime")
	@ColumnType(jdbcType = JdbcType.TIMESTAMP)
	private Date lastlogintime;//最后登录时间

	@Transient
	private int subscribe;//是否关注公众号
	
	@Transient
	private String sessionID;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getHospitalid() {
		return hospitalid;
	}

	public void setHospitalid(Integer hospitalid) {
		this.hospitalid = hospitalid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String fetchPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String fetchWxunionid() {
		return wxunionid;
	}

	public void setWxunionid(String wxunionid) {
		this.wxunionid = wxunionid;
	}

	public Date fetchCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date fetchLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public int getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}

	public String fetchPwdnonce() {
		return pwdnonce;
	}

	public void setPwdnonce(String pwdnonce) {
		this.pwdnonce = pwdnonce;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	
}
