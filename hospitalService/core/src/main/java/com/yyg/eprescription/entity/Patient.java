package com.yyg.eprescription.entity;

public class Patient {

	private Long id;
	
	private Integer userid;

	private String name;
	
	private String sex;
	
	public static final int TYPE_IDCARD = 1;//身份证
	public static final int TYPE_JG = 2;//军官证
	
	private Integer idcardtype;
	
	private String idcardnum;
	
	private String birthday;
		
	private int age;

	private String phone;

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


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
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
