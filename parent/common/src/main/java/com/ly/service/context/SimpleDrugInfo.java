package com.ly.service.context;

import java.io.Serializable;

public class SimpleDrugInfo implements Serializable{

	private static final long serialVersionUID = 1963727049542945062L;

	private Integer id;
	
	private String showname;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShowname() {
		return showname;
	}

	public void setShowname(String showname) {
		this.showname = showname;
	}
}
