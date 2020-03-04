package com.goochou.p2b.constant.pasture;

/**
 * @Auther: huangsj
 * @Date: 2019/5/17 10:15
 * @Description:
 */
public enum InvestmentStateEnum {

    no_buy(0,"未饲养","代付款"),
	buyed(1,"饲养期","已成团"),
	saled(2,"已卖牛","已结算"),
	caceled(3,"已取消","已取消"),
    waiting(4,"待成交","待成团"),
    ;

    private int code;

    private String description;

    private String pinDescription;

    InvestmentStateEnum(int code,String description,String pinDescription){
        this.code=code;
        this.description=description;
        this.pinDescription=pinDescription;
    }

    public static InvestmentStateEnum getValueByCode(int code){
        for (InvestmentStateEnum enums : values()) {
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

    public String getPinDescription() {
        return pinDescription;
    }

    public void setPinDescription(String pinDescription) {
        this.pinDescription = pinDescription;
    }
}
