package com.goochou.p2b.constant;

/**
 * @author shuys
 * @date 2019/6/20
*/
public enum MessageReceiverStatusEnum {

    UNREAD(0,"未读"),
    ALREADY_READ(1,"已读"),
    DELETE(2,"删除")
    ;

    private int code;

    private String description;

    MessageReceiverStatusEnum(int code, String description){
        this.code=code;
        this.description=description;
    }

    public static MessageReceiverStatusEnum getValueByCode(int code){
        for (MessageReceiverStatusEnum enums : values()) {
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
