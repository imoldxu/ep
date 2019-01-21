package com.yyg.eprescription.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_prescriptionnum")
@ApiModel(value = "prescriptionNumber", description = "处方签号申请")
public class PrescriptionNumber {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.INTEGER)
	@ApiModelProperty(value = "id")
	private Integer id;
	
	@Column(name = "number")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	@ApiModelProperty(value = "处方号")
	private int number;//门诊号
	
	@Column(name = "opendate")
	@ColumnType(jdbcType = JdbcType.DATE)
	@ApiModelProperty(value = "处方号")
	private Date opendate;//门诊号

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Date getOpendate() {
		return opendate;
	}

	public void setOpendate(Date opendate) {
		this.opendate = opendate;
	}
	
}
