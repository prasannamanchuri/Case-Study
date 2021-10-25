package com.capgemeni.railwayBooking.Entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "booking-info")
public class Bookinginfo {
	private String _id;
	private String ticketNo;
	//@DBRef(lazy = true)
	private Userinfo userinfo;
	//@DBRef(lazy = true)
	private Traininfo trainInfo;
	private int noofadult;
	private int noofchildren;
	private String typeofclass;
	private Double price;
	private String status;
	
	public Bookinginfo()  {
		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Userinfo getUserinfo() {
		return userinfo;
	}


	public void setUserinfo(Userinfo userinfo) {
		this.userinfo = userinfo;
	}
	public Traininfo getTrainInfo() {
		return trainInfo;
	}

	public void setTrainInfo(Traininfo trainInfo) {
		this.trainInfo = trainInfo;
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

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	

	public Bookinginfo(String _id, String ticketNo, Userinfo userinfo, Traininfo trainInfo, int noofadult,
			int noofchildren, String typeofclass, Double price, String status) {
		super();
		this._id = _id;
		this.ticketNo = ticketNo;
		this.userinfo = userinfo;
		this.trainInfo = trainInfo;
		this.noofadult = noofadult;
		this.noofchildren = noofchildren;
		this.typeofclass = typeofclass;
		this.price = price;
		this.status = status;
	}

	@Override
	public String toString() {
		return "Bookinginfo [_id=" + _id + ", ticketNo=" + ticketNo + ", userinfo=" + userinfo + ", trainInfo="
				+ trainInfo + ", noofadult=" + noofadult + ", noofchildren=" + noofchildren + ", typeofclass="
				+ typeofclass + ", price=" + price + ", status=" + status + "]";
	}

	

}
