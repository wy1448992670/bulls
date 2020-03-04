package com.goochou.p2b.constant.goods;

/**
 * @Auther: huangsj
 * @Date: 2019/5/13 14:50
 * @Description:
 */
public enum GoodsOrderInvestRelationEnum {
    USING(0,"使用中"),
    SUCCESS(1,"使用成功"),
    REFUND(2,"退款")
    ;

    private int code;

    private String description;

    GoodsOrderInvestRelationEnum(int code,String description){
        this.code=code;
        this.description=description;
    }

    public static GoodsOrderInvestRelationEnum getValueByCode(int code){
        for (GoodsOrderInvestRelationEnum enums : values()) {
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
