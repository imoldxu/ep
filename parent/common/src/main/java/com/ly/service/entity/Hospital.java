package com.ly.service.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_hospital")
public class Hospital {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer id;

	@Column(name = "name")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String name;//医院名称

	@Column(name = "email")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String email;//医院登录账号

	@Column(name = "password")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String password;//医院登录密码
	
	@Column(name = "address")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String address;//医院地址

	@Column(name ="longitude")
	@ColumnType(jdbcType= JdbcType.DOUBLE)
	private Double longitude;
	
	@Column(name ="latitude")
	@ColumnType(jdbcType= JdbcType.DOUBLE)
	private Double latitude;

	@Column(name = "pwdnonce")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String pwdnonce;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String fetchPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String fetchPwdnonce() {
		return pwdnonce;
	}

	public void setPwdnonce(String pwdnonce) {
		this.pwdnonce = pwdnonce;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
