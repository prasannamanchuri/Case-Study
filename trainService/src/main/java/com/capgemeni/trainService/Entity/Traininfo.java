package com.capgemeni.trainService.Entity;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "train-info")

public class Traininfo {
	private String trainname;
	private String from;
	private String to;
	private Date date;
	private String status;
	private int class_a_seats;
	private int class_b_seats;
	private int class_c_seats;
	private double class_a_amount;
	private double class_b_amount;
	private double class_c_amount;
	public String getTrainname() {
		return trainname;
	}
	public void setTrainname(String trainname) {
		this.trainname = trainname;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getClass_a_seats() {
		return class_a_seats;
	}
	public void setClass_a_seats(int class_a_seats) {
		this.class_a_seats = class_a_seats;
	}
	public int getClass_b_seats() {
		return class_b_seats;
	}
	public void setClass_b_seats(int class_b_seats) {
		this.class_b_seats = class_b_seats;
	}
	public int getClass_c_seats() {
		return class_c_seats;
	}
	public void setClass_c_seats(int class_c_seats) {
		this.class_c_seats = class_c_seats;
	}
	public double getClass_a_amount() {
		return class_a_amount;
	}
	public void setClass_a_amount(double class_a_amount) {
		this.class_a_amount = class_a_amount;
	}
	public double getClass_b_amount() {
		return class_b_amount;
	}
	public void setClass_b_amount(double class_b_amount) {
		this.class_b_amount = class_b_amount;
	}
	public double getClass_c_amount() {
		return class_c_amount;
	}
	public void setClass_c_amount(double class_c_amount) {
		this.class_c_amount = class_c_amount;
	}
	@Override
	public String toString() {
		return "traininfo [trainname=" + trainname + ", from=" + from + ", to=" + to + ", date=" + date + ", status="
				+ status + ", class_a_seats=" + class_a_seats + ", class_b_seats=" + class_b_seats + ", class_c_seats="
				+ class_c_seats + ", class_a_amount=" + class_a_amount + ", class_b_amount=" + class_b_amount
				+ ", class_c_amount=" + class_c_amount + "]";
	}
	public Traininfo(String trainname, String from, String to, Date date, String status, int class_a_seats,
			int class_b_seats, int class_c_seats, double class_a_amount, double class_b_amount, double class_c_amount) {
		super();
		this.trainname = trainname;
		this.from = from;
		this.to = to;
		this.date = date;
		this.status = status;
		this.class_a_seats = class_a_seats;
		this.class_b_seats = class_b_seats;
		this.class_c_seats = class_c_seats;
		this.class_a_amount = class_a_amount;
		this.class_b_amount = class_b_amount;
		this.class_c_amount = class_c_amount;
	}
	
	
}
