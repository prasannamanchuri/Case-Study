package com.capgemeni.railwayAuthentication.Entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user-data")
public class Userinfo {
	private String username;
	private String password;
	private String contact;
	private String address ;
	private String usertype;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public Userinfo() {}
	public Userinfo(String string, String string2, String string3, String string4, String string5) {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
}
