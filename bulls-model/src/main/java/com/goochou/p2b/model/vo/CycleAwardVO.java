package com.goochou.p2b.model.vo;

import java.util.Date;

public class CycleAwardVO {
	private Integer id;
	private Double amount;
	private Date time;
	private Integer hasDays;
	private Integer stage;
	private Integer limitDays;
	private Integer userId;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getLimitDays() {
		return limitDays;
	}
	public void setLimitDays(Integer limitDays) {
		this.limitDays = limitDays;
	}
	public Integer getStage() {
		return stage;
	}
	public void setStage(Integer stage) {
		this.stage = stage;
	}
	public Integer getHasDays() {
		return hasDays;
	}
	public void setHasDays(Integer hasDays) {
		this.hasDays = hasDays;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}
