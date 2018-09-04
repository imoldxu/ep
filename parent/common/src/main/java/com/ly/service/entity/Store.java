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
	private int id;
	
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
	
	@Column(name ="longitude")
	@ColumnType(jdbcType= JdbcType.VARCHAR)
	private String longitude;
	
	@Column(name ="latitude")
	@ColumnType(jdbcType= JdbcType.VARCHAR)
	private String latitude;

	@Transient
	private int distance;

	@Transient
	private List<StoreDrug> druglist;
	
	public List<StoreDrug> getDruglist() {
		return druglist;
	}

	public void setDruglist(List<StoreDrug> druglist) {
		this.druglist = druglist;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	
}
