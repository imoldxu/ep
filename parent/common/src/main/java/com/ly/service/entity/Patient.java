package com.ly.service.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.ibatis.type.JdbcType;

import com.ly.service.utils.BarcodeUtil;

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
	private Integer userid;

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
	private Integer idcardtype;
	
	@Column(name = "idcardnum")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String idcardnum;
	
	@Column(name = "birthday")
	@ColumnType(jdbcType = JdbcType.DATE)
	private String birthday;
		
	@Transient
	private Integer age;

	@Column(name = "phone")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String phone;

	@Transient
	private String barCode;
	
	public String getBarCode() {
		return barCode;
	}


	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
		this.barCode = BarcodeUtil.generateBarcode(BarcodeUtil.TYPE_PATIENT, id);
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


	public Integer getIdcardtype() {
		return idcardtype;
	}


	public void setIdcardtype(Integer idcardtype) {
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


	public Integer getAge() {
		return age;
	}


	public void setAge(Integer age) {
		this.age = age;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Integer getUserid() {
		return userid;
	}


	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	
}
