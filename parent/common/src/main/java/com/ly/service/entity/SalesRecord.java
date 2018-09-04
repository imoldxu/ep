package com.ly.service.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_salesrecord")
public class SalesRecord {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.BIGINT)
	private long id;
	
	@Column(name = "orderid")
	@ColumnType(jdbcType = JdbcType.BIGINT)
	private Long orderid;//订单编号
	
	@Column(name = "prescriptionid")
	@ColumnType(jdbcType = JdbcType.BIGINT)
	private Long prescriptionid;//处方
	
	@Column(name = "drugid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int drugid;//药品编号
	
	@Column(name = "drugname")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String drugname;//药品编号
	

	@Column(name = "sellerid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int sellerid;//哪个销售的单子

	@Column(name = "sellername")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String sellername;//哪个销售的单子
	
	@Column(name = "storeid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int storeid;//哪个药店的单子
	
	@Column(name = "storename")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String storename;//哪个药店的单子
	
	@Column(name = "doctorid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int doctorid;//哪个医生的单子
	
	@Column(name = "doctorname")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String doctorname;//哪个医生的单子
	
	@Column(name = "hospitalid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int hospitalid;//哪个医院的单子
	
	@Column(name = "hospitalname")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String hospitalname;//哪个医院的单子
	
	@Column(name = "num")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int num;//销售数量
	
	@Column(name = "sellerfee")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int sellerfee;//推广费

	@Column(name = "settlementprice")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int settlementprice;//结算费

	@Column(name = "createtime")
	@ColumnType(jdbcType = JdbcType.TIMESTAMP)
	private Date createtime;//结算费
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
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

	public int getStoreid() {
		return storeid;
	}

	public void setStoreid(int storeid) {
		this.storeid = storeid;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public int getDoctorid() {
		return doctorid;
	}

	public void setDoctorid(int doctorid) {
		this.doctorid = doctorid;
	}

	public Long getPrescriptionid() {
		return prescriptionid;
	}

	public void setPrescriptionid(Long prescriptionid) {
		this.prescriptionid = prescriptionid;
	}

	public String getDrugname() {
		return drugname;
	}

	public void setDrugname(String drugname) {
		this.drugname = drugname;
	}

	public String getSellername() {
		return sellername;
	}

	public void setSellername(String sellername) {
		this.sellername = sellername;
	}

	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

	public String getDoctorname() {
		return doctorname;
	}

	public void setDoctorname(String doctorname) {
		this.doctorname = doctorname;
	}

	public int getHospitalid() {
		return hospitalid;
	}

	public void setHospitalid(int hospitalid) {
		this.hospitalid = hospitalid;
	}

	public String getHospitalname() {
		return hospitalname;
	}

	public void setHospitalname(String hospitalname) {
		this.hospitalname = hospitalname;
	}

	public int getSellerfee() {
		return sellerfee;
	}

	public void setSellerfee(int sellerfee) {
		this.sellerfee = sellerfee;
	}

	public int getSettlementprice() {
		return settlementprice;
	}

	public void setSettlementprice(int settlementprice) {
		this.settlementprice = settlementprice;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
