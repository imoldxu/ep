package com.yyg.eprescription.entity;

public class CountPrescriptionInfo {

	private String doctorname;

	private String department;
	
	private String drugname;
	
	private Integer countnumber;

	private String drugunit;
	
	public String getDoctorname() {
		return doctorname;
	}

	public void setDoctorname(String doctorname) {
		this.doctorname = doctorname;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDrugname() {
		return drugname;
	}

	public void setDrugname(String drugname) {
		this.drugname = drugname;
	}

	public Integer getCountnumber() {
		return countnumber;
	}

	public void setCountnumber(Integer countnumber) {
		this.countnumber = countnumber;
	}

	public String getDrugunit() {
		return drugunit;
	}

	public void setDrugunit(String drugunit) {
		this.drugunit = drugunit;
	}


}
