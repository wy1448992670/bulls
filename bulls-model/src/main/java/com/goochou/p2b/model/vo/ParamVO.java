package com.goochou.p2b.model.vo;

import java.io.Serializable;


public class ParamVO implements Serializable {
	/**
	 * <p>Discription:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = -7805379085166183088L;
	private String name;
	private String tp;
	private String desc;
	private Boolean have;
	public boolean isHave() {
		return have;
	}
	public void setHave(boolean have) {
		this.have = have;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTp() {
		return tp;
	}
	public void setTp(String tp) {
		this.tp = tp;
	}
	public ParamVO(){}
	public ParamVO(String name, String tp, String desc, Boolean have) {
		this.name = name;
		this.tp = tp;
		this.desc = desc;
		this.have = have;
	}
}
