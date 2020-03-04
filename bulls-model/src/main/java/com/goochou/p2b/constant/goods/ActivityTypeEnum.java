package com.goochou.p2b.constant.goods;

/**
 * @author: huangsj
 * @Date: 2019/12/17 18:35
 * @Description:
 */
public enum ActivityTypeEnum {
    SECOND_KILL(1,"秒杀"),
    ;

    private int code;

    private String description;

    ActivityTypeEnum(int code,String description){
        this.code=code;
        this.description=description;
    }

    public static ActivityTypeEnum getValueByCode(int code){
        for (ActivityTypeEnum enums : values()) {
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
