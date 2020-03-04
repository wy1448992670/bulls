package com.goochou.p2b.constant.project;

/**
 * 月龄范围
 * 
 * @ClassName: ProjectAgeEnum
 * @author zj
 * @date 2019-07-16 16:31
 */
public enum ProjectAgeEnum {

	ONE("0-6个月"), TWO("6-12个月"), THREE("12-24个月"), FOUR("24个月以上");

	/**
	 * @Title: WeightEnum
	 * @param scope
	 * @param desc
	 * @param count
	 * @author zj
	 * @date 2019-07-16 16:22
	 */
	private ProjectAgeEnum(String desc) {
		this.desc = desc;
	}

	public String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
