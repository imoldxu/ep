package com.ly.service.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_manager_user")
public class ManagerUser {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnType(jdbcType = JdbcType.INTEGER)
	private Integer id;
	
	@Column(name = "phone")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String phone;
	
	@Column(name = "password")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String password;
	
	@Column(name = "pwdnonce")
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	private String pwdnonce;
	
	public static final int STATE_VALID = 1;
	public static final int STATE_INVALID = 0;
	
	@Column(name = "state")
	@ColumnType(jdbcType = JdbcType.TINYINT)
	private int state;
	
	@Column(name = "createtime")
	@ColumnType(jdbcType = JdbcType.TIMESTAMP)
	private Date createtime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String fetchPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String fetchPwdnonce() {
		return pwdnonce;
	}

	public void setPwdnonce(String pwdnonce) {
		this.pwdnonce = pwdnonce;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
