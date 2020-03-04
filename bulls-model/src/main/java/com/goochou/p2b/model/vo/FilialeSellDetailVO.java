package com.goochou.p2b.model.vo;

import java.math.BigDecimal;
import java.util.Date;

public class FilialeSellDetailVO {
	private Integer userId;
	private Integer investId;
	private String title;
	private Integer rowNum;
	private String mobile;
	private String realName;
	private Date createDate;
	private Integer number;
	private Integer limitDays;
	private BigDecimal amount;
	private Integer isExpire; // 1是 0否
	private String isExpireStr; // 1是 0否
	public Integer getRowNum() {
		return rowNum;
	}
	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getLimitDays() {
		return limitDays;
	}
	public void setLimitDays(Integer limitDays) {
		this.limitDays = limitDays;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getIsExpire() {
		return isExpire;
	}
	public void setIsExpire(Integer isExpire) {
		this.isExpire = isExpire;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getIsExpireStr() {
		return isExpireStr;
	}
	public void setIsExpireStr(String isExpireStr) {
		this.isExpireStr = isExpireStr;
	}
	public Integer getInvestId() {
		return investId;
	}
	public void setInvestId(Integer investId) {
		this.investId = investId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
