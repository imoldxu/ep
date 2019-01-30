package com.ly.service.context;

import java.util.List;

import com.ly.service.utils.JSONUtils;

/**药房，以及药房包含的药品*/
public class StoreAndDrugInfo {
	
	private Integer id;
	
	private String name;

	private String address;
		
	private Double longitude;
	
	private Double latitude;

	private Integer distance;

	private List<SimpleStoreDrug> drugList; //["",""]字符串数组
	
	private String drugListStr; //["",""]字符串数组

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public String fetchDrugListStr() {
		return drugListStr;
	}

	public void setDrugListStr(String drugListStr) {
		this.drugListStr = drugListStr;
		try {
			this.drugList = JSONUtils.getObjectListByJson(drugListStr, SimpleStoreDrug.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<SimpleStoreDrug> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<SimpleStoreDrug> drugList) {
		this.drugList = drugList;
	}


}
