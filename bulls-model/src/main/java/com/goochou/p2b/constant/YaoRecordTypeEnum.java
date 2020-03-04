package com.goochou.p2b.constant;

/**
 * @author shuys
 * @since 2019/6/6 10:13
 */
public enum YaoRecordTypeEnum {

    // 0没抽中 1现金红包2加息券3投资红包4提现券
    MISSED(0,"没抽中"),
    CASH_RED_ENVELOPE(1,"现金红包"),
    COUPON(2,"加息券"),
    INVESTMENT_BONUS(3,"投资红包"),
    CASH_COPONS(4,"提现券"),
    ;

    private Integer code;

    private String description;

    YaoRecordTypeEnum(Integer code,String description){
        this.code=code;
        this.description=description;
    }

    public static YaoRecordTypeEnum getValueByCode(Integer code){
        for (YaoRecordTypeEnum enums : values()) {
            if (enums.getCode()==code) {
                return enums;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
