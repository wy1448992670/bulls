package com.goochou.p2b.model;

public class AllAssetsExcel {
	private Integer num_id;
	private String username;
	private String trueName;
	private Double rechargeAmount;
	private Double withdrawAmount;
	private Double capitalAmount;
	private Double frozenAmount;
	private Double totalInvestmentAmount;
	private Double totalIncome;
	private Double uncollectCapital;
	private Double uncollectInterest;
	private Double availableBalance;
	private Double totalMoney;
	private Double hongbao;
	

	public Integer getNum_id() {
		return num_id;
	}
	public void setNum_id(Integer num_id) {
		this.num_id = num_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public Double getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(Double rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public Double getWithdrawAmount() {
		if(withdrawAmount == null)
		return 0d;
		else
		return withdrawAmount;
	}
	public void setWithdrawAmount(Double withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}
	public Double getCapitalAmount() {
		if(capitalAmount == null)
		return 0d;
		else
		return capitalAmount;
	}
	public void setCapitalAmount(Double capitalAmount) {
		this.capitalAmount = capitalAmount;
	}
	public Double getFrozenAmount() {
		return frozenAmount;
	}
	public void setFrozenAmount(Double frozenAmount) {
		this.frozenAmount = frozenAmount;
	}
	public Double getTotalInvestmentAmount() {
		return totalInvestmentAmount;
	}
	public void setTotalInvestmentAmount(Double totalInvestmentAmount) {
		this.totalInvestmentAmount = totalInvestmentAmount;
	}
	public Double getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(Double totalIncome) {
		this.totalIncome = totalIncome;
	}
	public Double getUncollectCapital() {
		return uncollectCapital;
	}
	public void setUncollectCapital(Double uncollectCapital) {
		this.uncollectCapital = uncollectCapital;
	}
	public Double getUncollectInterest() {
		return uncollectInterest;
	}
	public void setUncollectInterest(Double uncollectInterest) {
		this.uncollectInterest = uncollectInterest;
	}
	public Double getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}
	public Double getTotalMoney() {
		return this.availableBalance+this.uncollectCapital+this.uncollectInterest+this.frozenAmount;
	}
	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
	public Double getHongbao() {
		return 0d;
	}
	public void setHongbao(Double hongbao) {
		this.hongbao = hongbao;
	}
	
	
	

}
