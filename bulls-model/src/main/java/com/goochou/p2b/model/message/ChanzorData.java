package com.goochou.p2b.model.message;

import java.io.Serializable;

public class ChanzorData implements Serializable {
	/**
	 * <p>Discription:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = 1321669607987637715L;
	//{"taskId":null,"overage":0,"mobileCount":0,"status":-107,"desc":"账号密码错误"}
	private String taskId;
	private int overage;
	private int mobileCount;
	private int status;
	private String desc;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public int getOverage() {
		return overage;
	}
	public void setOverage(int overage) {
		this.overage = overage;
	}
	public int getMobileCount() {
		return mobileCount;
	}
	public void setMobileCount(int mobileCount) {
		this.mobileCount = mobileCount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
