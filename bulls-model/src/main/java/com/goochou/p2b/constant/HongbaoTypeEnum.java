package com.goochou.p2b.constant;

public enum HongbaoTypeEnum {

	CASH(1, "现金"),
	INVESTMENT(2, "投资红包"),
	COUPONS(3, "优惠券"),
	PIN_INVESTMENT(4, "拼牛投资红包"),
	;

    private int code;

    private String description;


    HongbaoTypeEnum(int code, String description){
        this.code=code;
        this.description=description;
    }

    public static HongbaoTypeEnum getValueByCode(int code) {
        for (HongbaoTypeEnum enums : values()) {
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
