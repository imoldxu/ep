package com.ly.service.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.ibatis.type.JdbcType;

import com.ly.service.context.StoreAndDrugInfo;
import com.ly.service.utils.BarcodeUtil;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_prescription")
public class Prescription implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3080928075066734357L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.BIGINT)
	private Long id;
	
	@Column(name = "doctorid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer doctorid;//医生编号
	
	@Column(name = "hospitalid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer hospitalid;//医院号

	@Column(name = "hospitalname")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String hospitalname;//医院名称
	
	@Column(name = "userid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer userid;//用户号

	@Column(name = "sn")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String sn;//院内编号
	
	public static final int TYPE_XY = 1;//西药处方
	public static final int TYPE_ZY = 2;//中药处方
	
	@Column(name = "type")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer type;
	
	@Column(name = "department")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String department;//科室
	
	@Column(name = "diagnosis")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String diagnosis;//诊断
	
	@Column(name = "patientname")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String patientname;//患者姓名
	
	@Column(name = "patientage")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String patientage;//患者年龄
	
	@Column(name = "patientsex")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String patientsex;//患者性别
	
	@Column(name = "patientphone")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String patientphone;//患者性别
	
	@Column(name = "doctorname")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String doctorname;//医生姓名
	
	@Column(name = "createtime")
	@ColumnType(jdbcType = JdbcType.TIMESTAMP)
	private Date createtime;//开具日期
	
	@Column(name = "zynum")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer zynum;
	
	@Column(name = "zyusage")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String zyusage;//中药用法
	
	@Column(name = "zysingledose")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String zysingledose;//中药单次剂量
	
	@Column(name = "zyfrequence")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String zyfrequence;//中药的频次
	
	@Column(name = "zymode")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String zymode;//中药服用方式
	
	@Column(name = "signatureurl")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String signatureurl;
	
	@Column(name = "iscomment")
	@ColumnType(jdbcType = JdbcType.TINYINT)
	private int iscomment;
	
	public int getIscomment() {
		return iscomment;
	}

	public void setIscomment(int iscomment) {
		this.iscomment = iscomment;
	}

	@Transient
	private String barcode;//取药码
	
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	@Transient
	private List<PrescriptionDrug> drugList;
	
	@Transient
	private List<StoreAndDrugInfo> storeList;
	
	public List<StoreAndDrugInfo> getStoreList() {
		return storeList;
	}

	public void setStoreList(List<StoreAndDrugInfo> storeList) {
		this.storeList = storeList;
	}

	public List<PrescriptionDrug> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<PrescriptionDrug> drugList) {
		this.drugList = drugList;
	}

	public String getZymode() {
		return zymode;
	}

	public void setZymode(String zymode) {
		this.zymode = zymode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
		this.barcode = BarcodeUtil.generateBarcode(BarcodeUtil.TYPE_PRESCRIPTION,id);
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
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

	public String getDoctorname() {
		return doctorname;
	}

	public void setDoctorname(String doctorname) {
		this.doctorname = doctorname;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getZynum() {
		return zynum;
	}

	public void setZynum(Integer zynum) {
		this.zynum = zynum;
	}

	public String getZyusage() {
		return zyusage;
	}

	public void setZyusage(String zyusage) {
		this.zyusage = zyusage;
	}

	public String getZysingledose() {
		return zysingledose;
	}

	public void setZysingledose(String zysingledose) {
		this.zysingledose = zysingledose;
	}

	public String getZyfrequence() {
		return zyfrequence;
	}

	public void setZyfrequence(String zyfrequence) {
		this.zyfrequence = zyfrequence;
	}
	
	public Integer getDoctorid() {
		return doctorid;
	}

	public void setDoctorid(Integer doctorid) {
		this.doctorid = doctorid;
	}

	public Integer getHospitalid() {
		return hospitalid;
	}

	public void setHospitalid(Integer hospitalid) {
		this.hospitalid = hospitalid;
	}
	
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getHospitalname() {
		return hospitalname;
	}

	public void setHospitalname(String hospitalname) {
		this.hospitalname = hospitalname;
	}

	public String getSignatureurl() {
		return signatureurl;
	}

	public void setSignatureurl(String signatureurl) {
		this.signatureurl = signatureurl;
	}
	
}
