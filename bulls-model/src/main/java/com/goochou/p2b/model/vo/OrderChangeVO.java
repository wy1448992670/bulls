package com.goochou.p2b.model.vo;

import java.util.Date;

public class OrderChangeVO {
	private int id;
	private String title;
	private int syDay;
	private Date sendTime;
	private double sendAmount;
	private String sellUser;
	private String buyUser;
	private Date buyTime;
	private double buyAmount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getSyDay() {
		return syDay;
	}
	public void setSyDay(int syDay) {
		this.syDay = syDay;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public double getSendAmount() {
		return sendAmount;
	}
	public void setSendAmount(double sendAmount) {
		this.sendAmount = sendAmount;
	}
	public String getSellUser() {
		return sellUser;
	}
	public void setSellUser(String sellUser) {
		this.sellUser = sellUser;
	}
	public String getBuyUser() {
		return buyUser;
	}
	public void setBuyUser(String buyUser) {
		this.buyUser = buyUser;
	}
	public Date getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}
	public double getBuyAmount() {
		return buyAmount;
	}
	public void setBuyAmount(double buyAmount) {
		this.buyAmount = buyAmount;
	}
}
