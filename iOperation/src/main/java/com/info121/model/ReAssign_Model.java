package com.info121.model;

public class ReAssign_Model {
	
	public String username;
	public String telno;
	
	public ReAssign_Model(String x, String y){
		username = x; telno = y;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

}
