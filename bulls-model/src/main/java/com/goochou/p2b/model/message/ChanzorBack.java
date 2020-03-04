package com.goochou.p2b.model.message;

import java.io.Serializable;

public class ChanzorBack implements Serializable {
	/**
	 * <p>Discription:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = -2014847240847947215L;
	private String phone;
	private Boolean flag;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
}
