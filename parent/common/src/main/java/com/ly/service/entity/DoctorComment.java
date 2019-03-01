package com.ly.service.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name="t_doctor_comment")
public class DoctorComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="commentid")
	@ColumnType(jdbcType= JdbcType.BIGINT)
	private Long commentid;
	
	@Column(name ="content")
	@ColumnType(jdbcType= JdbcType.VARCHAR)
	private String content;

	@Column(name ="uid")
	@ColumnType(jdbcType= JdbcType.INTEGER)
	private Integer uid;
	
	@Transient
	private String userNick;

	@Transient
	private String userHeadImgUrl;
	
	@Column(name ="doctorid")
	@ColumnType(jdbcType= JdbcType.INTEGER)
	private Integer doctorid;
	
	@Column(name ="star")
	@ColumnType(jdbcType= JdbcType.TINYINT)
	private Integer star;
	
	@Column(name ="createtime")
	@ColumnType(jdbcType= JdbcType.TIMESTAMP)
	private Date createtime;

	public Long getCommentid() {
		return commentid;
	}

	public void setCommentid(Long commentid) {
		this.commentid = commentid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getDoctorid() {
		return doctorid;
	}

	public void setDoctorid(Integer doctorid) {
		this.doctorid = doctorid;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getUserHeadImgUrl() {
		return userHeadImgUrl;
	}

	public void setUserHeadImgUrl(String userHeadImgUrl) {
		this.userHeadImgUrl = userHeadImgUrl;
	}
	
}
