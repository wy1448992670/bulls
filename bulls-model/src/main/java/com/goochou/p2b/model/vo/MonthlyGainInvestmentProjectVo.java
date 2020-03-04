package com.goochou.p2b.model.vo;

import java.util.Date;

public class MonthlyGainInvestmentProjectVo {
	
	private Integer investmentId;
	
	private String productName;
	
	private String productRate;
	
	private Double amount;
	
	private Integer holdDays;
	
	private Integer isQuit;
	
	private Date lastQuitDate;
	
	private String lastQuitDateStr;
	
	private Date applyQuitDate;
	
	private String applyQuitDateStr;
	
	private Date quitDate;
	
	private String quitDateStr;
	
	// 累计收益
	private Double totalEarnings;
	
	private Integer status;
	
	private Integer limitdays;

	public Integer getInvestmentId() {
		return investmentId;
	}

	public void setInvestmentId(Integer investmentId) {
		this.investmentId = investmentId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductRate() {
		return productRate;
	}

	public void setProductRate(String productRate) {
		this.productRate = productRate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getHoldDays() {
		return holdDays;
	}

	public void setHoldDays(Integer holdDays) {
		this.holdDays = holdDays;
	}

	public Integer getIsQuit() {
		return isQuit;
	}

	public void setIsQuit(Integer isQuit) {
		this.isQuit = isQuit;
	}

	public Date getLastQuitDate() {
		return lastQuitDate;
	}

	public void setLastQuitDate(Date lastQuitDate) {
		this.lastQuitDate = lastQuitDate;
	}

	public String getLastQuitDateStr() {
		return lastQuitDateStr;
	}

	public void setLastQuitDateStr(String lastQuitDateStr) {
		this.lastQuitDateStr = lastQuitDateStr;
	}

	public Date getApplyQuitDate() {
		return applyQuitDate;
	}

	public void setApplyQuitDate(Date applyQuitDate) {
		this.applyQuitDate = applyQuitDate;
	}

	public String getApplyQuitDateStr() {
		return applyQuitDateStr;
	}

	public void setApplyQuitDateStr(String applyQuitDateStr) {
		this.applyQuitDateStr = applyQuitDateStr;
	}

	public Date getQuitDate() {
		return quitDate;
	}

	public void setQuitDate(Date quitDate) {
		this.quitDate = quitDate;
	}

	public String getQuitDateStr() {
		return quitDateStr;
	}

	public void setQuitDateStr(String quitDateStr) {
		this.quitDateStr = quitDateStr;
	}

	public Double getTotalEarnings() {
		return totalEarnings;
	}

	public void setTotalEarnings(Double totalEarnings) {
		this.totalEarnings = totalEarnings;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getLimitdays() {
		return limitdays;
	}

	public void setLimitdays(Integer limitdays) {
		this.limitdays = limitdays;
	}
	
}
