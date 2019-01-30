package com.ly.service.context;

public class SimpleStoreDrug {
	
	private String drugname;
	
	private String standard;
	
	private Integer price;
	
	private Integer state;

	public SimpleStoreDrug() {
		
	}
	
	public String getDrugname() {
		return drugname;
	}

	public void setDrugname(String drugname) {
		this.drugname = drugname;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
