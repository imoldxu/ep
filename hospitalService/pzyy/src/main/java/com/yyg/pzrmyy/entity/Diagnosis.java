package com.yyg.pzrmyy.entity;

import java.io.Serializable;

public class Diagnosis implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 5896329768547867452L;

	private String num;
	
	private String doctorname;
	
	private String department;
	
	private String patientname;
	
	private String patientage;
	
	private String patientsex;
	
	private String patientphone;
	
//	private String diagnosis;

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

	public String getPatientname() {
		return patientname;
	}

	public void setPatientname(String patientname) {
		this.patientname = patientname;
	}

	public String getPatientage() {
		return patientage;
	}

	public void setPatientage(String patientage) {
		this.patientage = patientage;
	}

	public String getPatientsex() {
		return patientsex;
	}

	public void setPatientsex(String patientsex) {
		this.patientsex = patientsex;
	}

	public String getPatientphone() {
		return patientphone;
	}

	public void setPatientphone(String patientphone) {
		this.patientphone = patientphone;
	}

//	public String getDiagnosis() {
//		return diagnosis;
//	}
//
//	public void setDiagnosis(String diagnosis) {
//		this.diagnosis = diagnosis;
//	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
}
