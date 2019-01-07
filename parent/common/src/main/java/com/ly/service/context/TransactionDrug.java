package com.ly.service.context;

import java.io.Serializable;

/**
 * 交易药品信息
 * @author oldxu
 *
 */
public class TransactionDrug implements Serializable{

	private static final long serialVersionUID = 584652859728361864L;

	private Long prescriptionid;//处方id
	
	private int drugid;//药品id
	
	private String drugname;//药品名称
	
	private int doctorid;//开药医生id

	private String doctorname;//开药医生姓名
	
	private int hospitalid; //来源医院id
	
	private String hospitalname;//医院名称
	
	private int sellerid;//关联的销售id
	
	private String sellername;//销售名字

	private int num;//购买数量
	
	private int sellerfee;//销售推广费


	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getDrugid() {
		return drugid;
	}

	public void setDrugid(int drugid) {
		this.drugid = drugid;
	}
	
	public int getSellerid() {
		return sellerid;
	}

	public void setSellerid(int sellerid) {
		this.sellerid = sellerid;
	}
	
	public int getDoctorid() {
		return doctorid;
	}

	public void setDoctorid(int doctorid) {
		this.doctorid = doctorid;
	}

	public int getHospitalid() {
		return hospitalid;
	}

	public void setHospitalid(int hospitalid) {
		this.hospitalid = hospitalid;
	}

	public String getSellername() {
		return sellername;
	}

	public void setSellername(String sellername) {
		this.sellername = sellername;
	}

	public String getHospitalname() {
		return hospitalname;
	}

	public void setHospitalname(String hospitalname) {
		this.hospitalname = hospitalname;
	}

	public String getDoctorname() {
		return doctorname;
	}

	public void setDoctorname(String doctorname) {
		this.doctorname = doctorname;
	}

	public String getDrugname() {
		return drugname;
	}

	public void setDrugname(String drugname) {
		this.drugname = drugname;
	}

	public Long getPrescriptionid() {
		return prescriptionid;
	}

	public void setPrescriptionid(Long prescriptionid) {
		this.prescriptionid = prescriptionid;
	}

	public int getSellerfee() {
		return sellerfee;
	}

	public void setSellerfee(int sellerfee) {
		this.sellerfee = sellerfee;
	}
}
