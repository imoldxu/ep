package com.ly.service.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_order")
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1026976489215761449L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.BIGINT)
	private Long id;
	
	public static final int TARGET_USER = 1;
	public static final int TARGET_STORE = 2;
	public static final int TARGET_SELLER = 3;
	
	@Column(name = "targettype")
	@ColumnType(jdbcType = JdbcType.TINYINT)
	private Integer targettype;  //用户1 或 店铺2 销售 3
	
	@Column(name = "targetid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer targetid;  //用户id或店铺id

	public static final int CODE_TRANS = 1;
	public static final int CODE_CHARGE = 2;
	public static final int CODE_PAYOUT = 3;
	
	@Column(name = "transcode")
	@ColumnType(jdbcType = JdbcType.TINYINT)
	private Integer transcode;
	
	public Integer getTranscode() {
		return transcode;
	}

	public void setTranscode(Integer transcode) {
		this.transcode = transcode;
	}

	@Column(name = "info")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String info;  //交易清单
	
	@Column(name = "amount")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer amount;  //交易金额
	
	@Column(name = "createtime")
	@ColumnType(jdbcType = JdbcType.TIMESTAMP)
	private Date createtime;  //创建时间
	
	@Column(name = "completetime")
	@ColumnType(jdbcType = JdbcType.TIMESTAMP)
	private Date completetime;  //完成时间

	public final static int STATE_NEW = 1;
	public final static int STATE_PAYING = 2;
	public final static int STATE_PAYED = 3;
	public final static int STATE_COMPLETE = 5;
	public final static int STATE_REFUND = 4;
	
	@Column(name = "state")
	@ColumnType(jdbcType = JdbcType.TINYINT)
	private Integer state;
	
	public final static int PAY_OFFLINESTORE = 1;
	public final static int PAY_WX = 2;
	
	@Column(name = "payway")
	@ColumnType(jdbcType = JdbcType.TINYINT)
	private Integer payway;//支付订单id
	
	public Integer getPayway() {
		return payway;
	}

	public void setPayway(Integer payway) {
		this.payway = payway;
	}

	@Column(name = "payid")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String payid;//支付订单id

	public String getPayid() {
		return payid;
	}

	public void setPayid(String payid) {
		this.payid = payid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getCompletetime() {
		return completetime;
	}

	public void setCompletetime(Date completetime) {
		this.completetime = completetime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	public Integer getTargettype() {
		return targettype;
	}

	public void setTargettype(Integer targettype) {
		this.targettype = targettype;
	}

	public Integer getTargetid() {
		return targetid;
	}

	public void setTargetid(Integer targetid) {
		this.targetid = targetid;
	}
}
