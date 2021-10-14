package com.capgemeni.railwayAuthentication.Entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user-data")
public class Userinfo {
	private Long Id;
	private String Name;
	private String UserName;
	private String Password;
	private int Contact;
	private String Address ;
	private String Usertype;
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public int getContact() {
		return Contact;
	}
	public void setContact(int contact) {
		this.Contact = contact;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	
	public String getUsertype() {
		return Usertype;
	}
	public void setUsertype(String usertype) {
		Usertype = usertype;
	}
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Userinfo() {}
	@Override
	public String toString() {
		return "Userinfo [userId=" + Id + ", Name=" + Name + ", UserName=" + UserName + ", Password=" + Password
				+ ", Contact=" + Contact + ", Address=" + Address + ", Usertype=" + Usertype + "]";
	}
	
	
	
	
}
