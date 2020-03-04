package com.goochou.p2b.constant.goods;


/**
 * @Auther: huangsj
 * @Date: 2019/5/13 11:49
 * @Description:
 */
public enum  GoodsOrderStatusEnum {
    NO_PAY(0,"未支付"),
    PAYING(1,"支付中"),
    PAYED(2,"已支付"),
    PICKING(3,"拣货中"),
    SENDED(4,"已发货"),
    CANCEL(5,"订单取消"),
    REFUND(6,"订单退款"),
    COMPLETED(7,"交易完成"),
    REFUNDING(8,"退款中"),
    ;

    private int code;

    private String description;

    GoodsOrderStatusEnum(int code,String description){
        this.code=code;
        this.description=description;
    }

    public static GoodsOrderStatusEnum getValueByCode(int code){
        for (GoodsOrderStatusEnum enums : values()) {
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
