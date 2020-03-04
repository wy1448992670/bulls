package com.goochou.p2b.constant.pasture;


/**
 * @Auther: huangsj
 * @Date: 2019/5/13 16:35
 * @Description:
 */
public enum ProjectStatusEnum {

    BUILDED(0,"待上架"),
    ENABLE_SALE(1,"上架"),
    PAYING(2,"待付款"),
    SALED(3,"已出售"),
    BUYBACK(4,"已回购"),
    DEAD(5,"已死亡"),
    DELETE(6,"已删除"),
    ;

    private int code;

    private String description;

    ProjectStatusEnum(int code,String description){
        this.code=code;
        this.description=description;
    }

    public static ProjectStatusEnum getValueByCode(int code){
        for (ProjectStatusEnum enums : values()) {
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
