package com.goochou.p2b.model.vo;

public class BondPayVO {
	private String date;
	
	private double totalAmount;
	
	private String deadline;
	
	private double annualized;
	
	private double limitDay;
	
	public double getLimitDay() {
		return limitDay;
	}

	public void setLimitDay(double limitDay) {
		this.limitDay = limitDay;
	}

	private double interestAmount;
	private double capitalAmount;
	private int hasDividended;
	private double fff;
	private double capInterAmount;

	public double getCapInterAmount() {
		return capInterAmount;
	}

	public void setCapInterAmount(double capInterAmount) {
		this.capInterAmount = capInterAmount;
	}

	public double getFff() {
		return fff;
	}

	public void setFff(double fff) {
		this.fff = fff;
	}

	public int getHasDividended() {
		return hasDividended;
	}

	public void setHasDividended(int hasDividended) {
		this.hasDividended = hasDividended;
	}

	public double getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(double interestAmount) {
		this.interestAmount = interestAmount;
	}

	public double getCapitalAmount() {
		return capitalAmount;
	}

	public void setCapitalAmount(double capitalAmount) {
		this.capitalAmount = capitalAmount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public double getAnnualized() {
		return annualized;
	}

	public void setAnnualized(double annualized) {
		this.annualized = annualized;
	}
}
