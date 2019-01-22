package com.ly.service.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_store")
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id")
	@ColumnType(jdbcType= JdbcType.INTEGER)
	private Integer id;
	
	@Column(name ="name")
	@ColumnType(jdbcType= JdbcType.VARCHAR)
	private String name;

	@Column(name ="address")
	@ColumnType(jdbcType= JdbcType.VARCHAR)
	private String address;
	
	@Column(name ="email")
	@ColumnType(jdbcType= JdbcType.VARCHAR)
	private String email;
	
	@Column(name ="password")
	@ColumnType(jdbcType= JdbcType.VARCHAR)
	private String password;

	@Column(name = "pwdnonce")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String pwdnonce;
	
	@Column(name ="longitude")
	@ColumnType(jdbcType= JdbcType.DOUBLE)
	private Double longitude;
	
	@Column(name ="latitude")
	@ColumnType(jdbcType= JdbcType.DOUBLE)
	private Double latitude;

	@Transient
	private int distance;

	@Transient
	private List<StoreDrug> drugList;
	
	public List<StoreDrug> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<StoreDrug> drugList) {
		this.drugList = drugList;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
	
	
}
