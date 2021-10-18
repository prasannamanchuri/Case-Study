package com.capgemeni.railwayBooking.Entity;

import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "booking-info")
public class Bookinginfo {
	private String username;
	private String trainname;
	private int noofadult;
	private int noofchildren;
	private String typeofclass;
	private Double price;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTrainname() {
		return trainname;
	}
	public void setTrainname(String trainname) {
		this.trainname = trainname;
	}
	public int getNoofadult() {
		return noofadult;
	}
	public void setNoofadult(int noofadult) {
		this.noofadult = noofadult;
	}
	public int getNoofchildren() {
		return noofchildren;
	}
	public void setNoofchildren(int noofchildren) {
		this.noofchildren = noofchildren;
	}
	public String getTypeofclass() {
		return typeofclass;
	}
	public void setTypeofclass(String typeofclass) {
		this.typeofclass = typeofclass;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Bookinginfo [username=" + username + ", trainname=" + trainname + ", noofadult=" + noofadult
				+ ", noofchildren=" + noofchildren + ", typeofclass=" + typeofclass + "]";
	}
	public Bookinginfo(String username, String trainname, int noofadult, int noofchildren, String typeofclass) {
		super();
		this.username= username;
		this.trainname = trainname;
		this.noofadult = noofadult;
		this.noofchildren = noofchildren;
		this.typeofclass = typeofclass;
	}
	public Bookinginfo()  {
		
	}
	 

}
