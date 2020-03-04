package com.goochou.p2b.constant;



/**
 * Created on 2019年5月13日
 * <p>Title:       [订单节点枚举]</p>
 * <p>Company:     奔富畜牧</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * <p>Department:  研发中心</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public enum OrderDoneEnum {

    SUBMIT("1001","submit","下单"),
    PAY("1002","pay","完成支付"),//支付成功
    CANNEL("1003","cannel","订单取消"),
    INTEREST("1004","interest","派息"),
    SUCCESS("1005","success","订单完成"),//卖牛
    SHIPPED("1006","shipped","已发货"),
    RECEIVED("1007","received","已收货"),
    PAYORDER("1008","payorder","支付订单生成"),
    REFUND_APPLY("1009","refund_apply","订单退款申请"),
    REFUND_FINISH("1010","refund_finish","订单退款完成"),
    REFUND_REJECT("1011","refund_reject","订单退款驳回"),
    DUE("1012","due","投资到期"),
    CREATEPROJECT("1020","CREATEPROJECT","生成续购标"),
    UPDATEPROJECT("1022","UPDATEPROJECT","更新续购标"),
    TRADE_COMPLETE("1030","TRADE_COMPLETE","成交"),//所有满标,成交
    ;

    /**
     * 类型
     */
    private String featureType;
    /**
     * 名称(数据库featrues存储名)
     */
    private String featureName;
    /**
     * 描叙
     */
    private String description;

    /**
     * 初始化
     * @param featureType
     * @param featureName
     * @param description
     */
    OrderDoneEnum(String featureType, String featureName, String description){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;
    }

    public static OrderDoneEnum getValueByType(int featureType){
        for (OrderDoneEnum enums : values()) {
            if (enums.getFeatureType().equals(featureType)) {
                return enums;
            }
        }
        return null;
    }
    public static OrderDoneEnum getValueByName(String featureName){
        for (OrderDoneEnum enums : values()) {
            //不区分大小写返回
            if (enums.getFeatureName().equalsIgnoreCase(featureName)) {
                return enums;
            }
        }
        return null;
    }

    public static OrderDoneEnum getValueByDesc(String description){
        for (OrderDoneEnum enums : values()) {
            //不区分大小写返回
            if (enums.getDescription().indexOf(description) > -1) {
                return enums;
            }
        }
        return null;
    }

    /**
     * @return the featureType
     */
    public String getFeatureType() {
        return featureType;
    }

    /**
     * @return the featureName
     */
    public String getFeatureName() {
        return featureName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
}
