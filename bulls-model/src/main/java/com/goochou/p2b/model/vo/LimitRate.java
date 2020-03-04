package com.goochou.p2b.model.vo;

public class LimitRate {
	private Integer limit_days;
	private Float rate;
	public Float getRate() {
		return rate;
	}
	public void setRate(Float rate) {
		this.rate = rate;
	}
	public Integer getLimit_days() {
		return limit_days;
	}
	public void setLimit_days(Integer limit_days) {
		this.limit_days = limit_days;
	}
}
