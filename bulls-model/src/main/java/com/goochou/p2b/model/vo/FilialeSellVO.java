package com.goochou.p2b.model.vo;

import java.math.BigDecimal;

public class FilialeSellVO {
	private Integer rowNum;
	private Integer empId;
	private String company;
	private String realName; 
	private String mobile;
	private Integer limitDays30;
	private Integer limitDays90;
	private Integer limitDays180;
	private Integer limitDays360;
	private BigDecimal investTotalAmount30;
	private BigDecimal investTotalAmount90;
	private BigDecimal investTotalAmount180;
	private BigDecimal investTotalAmount360;
	private Integer totalNum;
	private BigDecimal totalInvest;
	
	public BigDecimal getInvestTotalAmount30() {
		return investTotalAmount30;
	}
	public void setInvestTotalAmount30(BigDecimal investTotalAmount30) {
		this.investTotalAmount30 = investTotalAmount30;
	}
	public BigDecimal getInvestTotalAmount90() {
		return investTotalAmount90;
	}
	public void setInvestTotalAmount90(BigDecimal investTotalAmount90) {
		this.investTotalAmount90 = investTotalAmount90;
	}
	public BigDecimal getInvestTotalAmount180() {
		return investTotalAmount180;
	}
	public void setInvestTotalAmount180(BigDecimal investTotalAmount180) {
		this.investTotalAmount180 = investTotalAmount180;
	}
	public BigDecimal getInvestTotalAmount360() {
		return investTotalAmount360;
	}
	public void setInvestTotalAmount360(BigDecimal investTotalAmount360) {
		this.investTotalAmount360 = investTotalAmount360;
	}
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Integer getLimitDays30() {
		return limitDays30;
	}
	public void setLimitDays30(Integer limitDays30) {
		this.limitDays30 = limitDays30;
	}
	public Integer getLimitDays90() {
		return limitDays90;
	}
	public void setLimitDays90(Integer limitDays90) {
		this.limitDays90 = limitDays90;
	}
	public Integer getLimitDays180() {
		return limitDays180;
	}
	public void setLimitDays180(Integer limitDays180) {
		this.limitDays180 = limitDays180;
	}
	public Integer getLimitDays360() {
		return limitDays360;
	}
	public void setLimitDays360(Integer limitDays360) {
		this.limitDays360 = limitDays360;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	 
	public Integer getRowNum() {
		return rowNum;
	}
	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public BigDecimal getTotalInvest() {
		return totalInvest;
	}
	public void setTotalInvest(BigDecimal totalInvest) {
		this.totalInvest = totalInvest;
	}
	 
}
