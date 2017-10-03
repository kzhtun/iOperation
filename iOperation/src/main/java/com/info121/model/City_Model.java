package com.info121.model;

public class City_Model {
	
	public String citycode;
	public String city;
	
	public City_Model(String x, String y){
		citycode = x; city = y;
	}
	
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	

}
