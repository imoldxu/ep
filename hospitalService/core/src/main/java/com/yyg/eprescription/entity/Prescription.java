package com.yyg.eprescription.entity;

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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_prescription")
@ApiModel(value = "prescription", description = "处方")
public class Prescription implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3080928075066734357L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.BIGINT)
	@ApiModelProperty(value = "id")
	private Long id;
	
	@Column(name = "sn")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "门诊号")
	private String sn;//门诊号
	
	public static final int TYPE_XY = 1;//西药处方
	public static final int TYPE_ZY = 2;//中药处方
	
	@Column(name = "type")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	@ApiModelProperty(value = "处方类型")
	private int type;
	
	@Column(name = "department")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "科室")
	private String department;//科室
	
	@Column(name = "diagnosis")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "诊断")
	private String diagnosis;//诊断
	
	@Column(name = "patientname")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "患者姓名")
	private String patientname;//患者姓名
	
	@Column(name = "patientage")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "患者年龄")
	private Integer patientage;//患者年龄
	
	@Column(name = "patientsex")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "患者性别")
	private String patientsex;//患者性别
	
	@Column(name = "patientphone")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "患者电话")
	private String patientphone;//患者性别
	
	@Column(name = "doctorname")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "医生姓名")
	private String doctorname;//医生姓名
	
	@Column(name = "createtime")
	@ColumnType(jdbcType = JdbcType.TIMESTAMP)
	@ApiModelProperty(value = "开具日期")
	private Date createtime;//开具日期
	
	@Column(name = "zynum")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	@ApiModelProperty(value = "中药的用法")
	private int zynum;
	
	@Column(name = "zyusage")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "用法")
	private String zyusage;
	
	@Column(name = "zysingledose")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "一次用量")
	private String zysingledose;
	
	@Column(name = "zyfrequence")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "每日用量")
	private String zyfrequence;
	

	@Column(name = "zymode")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@ApiModelProperty(value = "方式")
	private String zymode;
	
	@Transient
	private List<PrescriptionDrugs> drugList;
	
	
	public List<PrescriptionDrugs> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<PrescriptionDrugs> drugList) {
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

	public Integer getPatientage() {
		return patientage;
	}

	public void setPatientage(Integer patientage) {
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public int getZynum() {
		return zynum;
	}

	public void setZynum(int zynum) {
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

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

}
