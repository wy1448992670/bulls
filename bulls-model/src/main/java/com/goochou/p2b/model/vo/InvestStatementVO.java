package com.goochou.p2b.model.vo;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.goochou.p2b.utils.BigDecimalUtil;

public class InvestStatementVO {
	private int totalCount;//认养数量（只）
	private int noob;// 0是 0-6个月的牛犊。 1：6个月以上的牛群
	private BigDecimal totalAmount; //认购总金额
	private BigDecimal totalManageFee; //总管理费
	private BigDecimal totalRaiseFee;//总饲养费
	private BigDecimal totalBalancePayMoney; //余额支付总金额
	private BigDecimal totalHongbaoMoney;//红包总金额
	private BigDecimal totalRemainAmount;//现金支付总金额
	
	private BigDecimal totalCowMoney;//购牛款
	
	private String noobString;
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	 
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getTotalManageFee() {
		return totalManageFee;
	}
	public void setTotalManageFee(BigDecimal totalManageFee) {
		this.totalManageFee = totalManageFee;
	}
	public BigDecimal getTotalRaiseFee() {
		return totalRaiseFee;
	}
	public void setTotalRaiseFee(BigDecimal totalRaiseFee) {
		this.totalRaiseFee = totalRaiseFee;
	}
	public BigDecimal getTotalBalancePayMoney() {
		return totalBalancePayMoney;
	}
	public void setTotalBalancePayMoney(BigDecimal totalBalancePayMoney) {
		this.totalBalancePayMoney = totalBalancePayMoney;
	}
	public BigDecimal getTotalHongbaoMoney() {
		return totalHongbaoMoney;
	}
	public void setTotalHongbaoMoney(BigDecimal totalHongbaoMoney) {
		this.totalHongbaoMoney = totalHongbaoMoney;
	}
	public BigDecimal getTotalRemainAmount() {
		return totalRemainAmount;
	}
	public void setTotalRemainAmount(BigDecimal totalRemainAmount) {
		this.totalRemainAmount = totalRemainAmount;
	}
	
	
	public BigDecimal getTotalCowMoney() {
		if(this.totalAmount.compareTo(BigDecimal.ZERO) > 0) {
			totalCowMoney = new BigDecimal(BigDecimalUtil.sub(totalAmount, totalRaiseFee, totalManageFee).toString());
		}
		return totalCowMoney;
	}
	public void setTotalCowMoney(BigDecimal totalCowMoney) {
		this.totalCowMoney = totalCowMoney;
	}
	 
	public String getNoobString() {
		if(!StringUtils.isEmpty(noobString)) {
			return noobString;
		}
		
		if(this.noob == 0) {
			noobString = "牛犊6个月以下包含6个月" ;
		} else if (this.noob == 1){
			noobString = "基础牛群6个月以上" ;
		} 
		return noobString;
	}
	public void setNoobString(String noobString) {
		
		this.noobString = noobString;
	}
	public int getNoob() {
		return noob;
	}
	public void setNoob(int noob) {
		this.noob = noob;
	}
	
	@Override
	public String toString() {
		return "InvestStatementVO [totalCount=" + totalCount + ", noob=" + noob + ", totalAmount=" + totalAmount
				+ ", totalManageFee=" + totalManageFee + ", totalRaiseFee=" + totalRaiseFee + ", totalBalancePayMoney="
				+ totalBalancePayMoney + ", totalHongbaoMoney=" + totalHongbaoMoney + ", totalRemainAmount="
				+ totalRemainAmount + ", totalCowMoney=" + totalCowMoney + ", noobString=" + noobString + "]";
	}
	
}
