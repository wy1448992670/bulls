 package com.goochou.p2b.constant;

 /**
 * @author sxy
 * @date 2019/11/15
 */
public enum ExportApplyStatusEnum {

    IN_AUDIT(0,"审核中"),
    PASS(1,"通过"),
    NO_PASS(2,"拒绝"),
    ;

    private int code;

    private String description;

    ExportApplyStatusEnum(int code,String description){
        this.code=code;
        this.description=description;
    }

    public static ExportApplyStatusEnum getValueByCode(Integer code){
        for (ExportApplyStatusEnum enums : values()) {
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
