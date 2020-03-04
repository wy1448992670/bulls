package com.goochou.p2b.model.vo;

import java.util.Date;

public class AnXinZhuanOfflineDataVO {
	
	private Integer investmentId;
	
	private Integer userId;
	
	private String userName;
	
	private String trueName;
	
	private Double amount;
	
	private Date time;
	
	private Integer day;
	
	private Double money;
	
	private Double annualizedMin;

	public Integer getInvestmentId() {
		return investmentId;
	}

	public void setInvestmentId(Integer investmentId) {
		this.investmentId = investmentId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getAnnualizedMin() {
		return annualizedMin;
	}

	public void setAnnualizedMin(Double annualizedMin) {
		this.annualizedMin = annualizedMin;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "AnXinZhuanOfflineDataVO [investmentId=" + investmentId
				+ ", userId=" + userId + ", userName=" + userName
				+ ", trueName=" + trueName + ", amount="
				+ amount + ", time=" + time + ", day=" + day
				+ ", money=" + money + ", annualizedMin=" + annualizedMin + "]";
	}

	
	
}
