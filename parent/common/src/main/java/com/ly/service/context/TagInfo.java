package com.ly.service.context;

public class TagInfo {

	private Long id;

	private Integer targetid;
	
	private Integer tagid;
	
	private String tagname;

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tarname) {
		this.tagname = tarname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getTargetid() {
		return targetid;
	}

	public void setTargetid(Integer targetid) {
		this.targetid = targetid;
	}

	public Integer getTagid() {
		return tagid;
	}

	public void setTagid(Integer tagid) {
		this.tagid = tagid;
	}
}
