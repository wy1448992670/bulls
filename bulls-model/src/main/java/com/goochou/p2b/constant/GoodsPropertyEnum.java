package com.goochou.p2b.constant;

public enum GoodsPropertyEnum {
	SPECIFICATION(1,"规格");
	
	GoodsPropertyEnum(int code, String propertyName){
		this.code = code;
		this.propertyName = propertyName;
	}
	private String propertyName;
	
	private int code;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
