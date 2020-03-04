package com.goochou.p2b.constant;

public enum CompanyEnum {
	WEN_ZHOU(1,"温州"),
	NING_DE(2,"宁德"),
	ZAO_ZHUANG(3,"枣庄"),
	SHANG_HAI(4,"上海");
	   
	/**
     * code
     */
    private int code;
    /**
     * 名称
     */
    private String name;
    
    CompanyEnum(int code, String name){
    	this.code = code;
    	this.name = name;
    }
    
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
