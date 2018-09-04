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

@Table(name="t_patient")
public class Patient {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.BIGINT)
	private Long id;
	
	@Column(name = "userid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int userid;

	@Column(name = "name")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String name;
	
	@Column(name = "sex")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String sex;
	
	public static final int TYPE_IDCARD = 1;//身份证
	public static final int TYPE_JG = 2;//军官证
	
	@Column(name = "idcardtype")
	@ColumnType(jdbcType = JdbcType.TINYINT)
	private int idcardtype;
	
	@Column(name = "idcardnum")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String idcardnum;
	
	@Column(name = "birthday")
	@ColumnType(jdbcType = JdbcType.DATE)
	private String birthday;
		
	@Transient
	private Date age;
	

	@Column(name = "phone")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String phone;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public int getIdcardtype() {
		return idcardtype;
	}


	public void setIdcardtype(int idcardtype) {
		this.idcardtype = idcardtype;
	}


	public String getIdcardnum() {
		return idcardnum;
	}


	public void setIdcardnum(String idcardnum) {
		this.idcardnum = idcardnum;
	}


	public String getBirthday() {
		return birthday;
	}


	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


	public Date getAge() {
		return age;
	}


	public void setAge(Date age) {
		this.age = age;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public int getUserid() {
		return userid;
	}


	public void setUserid(int userid) {
		this.userid = userid;
	}
}
