package com.info121.model;

public class Country_Model {
	
	public String countrycode;
	public String country;
	
	public Country_Model(String x, String y){
		countrycode = x; country = y;
	}
	
	public String getCountrycode() {
		return countrycode;
	}
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	

}
