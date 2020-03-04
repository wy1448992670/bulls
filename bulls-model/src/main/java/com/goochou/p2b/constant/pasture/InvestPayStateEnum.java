package com.goochou.p2b.constant.pasture;


/**
 * @Auther: huangsj
 * @Date: 2019/5/22 16:27
 * @Description:
 */
public enum InvestPayStateEnum {
	NO_PAY(0, "未支付"),
	PAYING(1, "支付中"),
	PAYED(2, "已支付"),
	;

	private int code;

	private String description;

	InvestPayStateEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public static InvestPayStateEnum getValueByCode(int code) {
		for (InvestPayStateEnum enums : values()) {
			if (enums.getCode() == code) {
				return enums;
			}
		}
		return null;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
