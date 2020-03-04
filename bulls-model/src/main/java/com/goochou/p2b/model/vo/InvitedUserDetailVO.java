package com.goochou.p2b.model.vo;

import com.goochou.p2b.utils.StringUtil;

/**
 * 
 * @author xueqi
 *
 */
public class InvitedUserDetailVO {
	
	private String registTime;
	
	private Integer status;
	
	private Integer bankId;
	
	private String phone;
	
	private Integer chargeCount;
	
	private String showStatus;

	public String getRegistTime() {
		return StringUtil.isNull(registTime) ? registTime : registTime.replaceAll("-", ".");
	}

	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getChargeCount() {
		return chargeCount;
	}

	public void setChargeCount(Integer chargeCount) {
		this.chargeCount = chargeCount;
	}

	public String getShowStatus() {
		
		showStatus = "";
		
		if(bankId == null || bankId == 0){
			showStatus = "未绑卡";
		}else if(chargeCount == null || chargeCount == 0){
			showStatus = "未充值";
		}else if(status == 0){
			showStatus = "未投资";
		}else if(status == 1){
			showStatus = "已投资";
		}
		
		return showStatus;
	}

	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}
}
