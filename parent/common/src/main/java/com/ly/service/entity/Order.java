package com.ly.service.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_order")
public class Order {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.BIGINT)
	private Long id;
	
	public static final int TAGET_USER = 1;
	public static final int TAGET_STORE = 2;
	public static final int TAGET_SELLER = 3;
	
	@Column(name = "tagettype")
	@ColumnType(jdbcType = JdbcType.TINYINT)
	private int tagettype;  //用户1 或 店铺2 销售 3
	
	@Column(name = "tagetid")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int tagetid;  //用户id或店铺id

	public static final int CODE_TRANS = 1;
	public static final int CODE_CHARGE = 2;
	public static final int CODE_PAYOUT = 3;
	
	@Column(name = "transcode")
	@ColumnType(jdbcType = JdbcType.TINYINT)
	private int transcode;
	
	public int getTranscode() {
		return transcode;
	}

	public void setTranscode(int transcode) {
		this.transcode = transcode;
	}

	@Column(name = "info")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String info;  //交易清单
	
	@Column(name = "amount")
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private int amount;  //交易金额
	
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
	private int state;
	
	public final static int PAY_OFFLINESTORE = 1;
	public final static int PAY_WX = 2;
	
	@Column(name = "payway")
	@ColumnType(jdbcType = JdbcType.TINYINT)
	private int payway;//支付订单id
	
	public int getPayway() {
		return payway;
	}

	public void setPayway(int payway) {
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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public int getTagettype() {
		return tagettype;
	}

	public void setTagettype(int tagettype) {
		this.tagettype = tagettype;
	}

	public int getTagetid() {
		return tagetid;
	}

	public void setTagetid(int tagetid) {
		this.tagetid = tagetid;
	}
}
