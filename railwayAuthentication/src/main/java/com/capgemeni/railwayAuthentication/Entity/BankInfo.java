package com.capgemeni.railwayAuthentication.Entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bankInfo")
public class BankInfo {
	private String _id;
	private Userinfo userinfo;
	private String bankname;
	private String cardNo;
	private String cvv ;
	private String expirydate;
	private float balance;
	public BankInfo() {}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public Userinfo getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(Userinfo userinfo) {
		this.userinfo = userinfo;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	public String getExpirydate() {
		return expirydate;
	}
	public void setExpirydate(String expirydate) {
		this.expirydate = expirydate;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public BankInfo(Userinfo userinfo, String bankname, String cardNo, String cvv, String expirydate, float balance) {
		super();
		this.userinfo = userinfo;
		this.bankname = bankname;
		this.cardNo = cardNo;
		this.cvv = cvv;
		this.expirydate = expirydate;
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "BankInfo [_id=" + _id + ", userinfo=" + userinfo + ", bankname=" + bankname + ", cardNo=" + cardNo
				+ ", cvv=" + cvv + ", expirydate=" + expirydate + ", balance=" + balance + "]";
	}
	
	
}
