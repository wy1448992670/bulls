package com.goochou.p2b.constant;

/**
 * @author shuys
 * @since 2019/6/4 16:58
 */
public enum ApplyRefundStatusEnum {

    SUBMIT(0,"提交申请"),
    IN_AUDIT(1,"审核中"),
    PASS(2,"通过"),
    NO_PASS(3,"打回"),
    ;

    private int code;

    private String description;

    ApplyRefundStatusEnum(int code,String description){
        this.code=code;
        this.description=description;
    }

    public static ApplyRefundStatusEnum getValueByCode(Integer code){
        for (ApplyRefundStatusEnum enums : values()) {
            if (enums.getCode()==code) {
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
