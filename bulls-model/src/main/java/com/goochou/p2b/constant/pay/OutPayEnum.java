package com.goochou.p2b.constant.pay;


/**
 *
 * Created on 2014年8月24日
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [接入的外部支付方枚举]</p>
 * <p>Copyright:   Copyright (c) 2011</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [杜成] [196168@qq.com]
 * @version        1.0
 */
public enum OutPayEnum {

    FUIOU("com.goochou.p2b.model.pay.FuiouOutPay","fuioupay","富友网关支付"),
    FUIOU_QUICK("com.goochou.p2b.model.pay.FuiouQuickOutPay","fuioupay_quick","富友快捷支付"),
    WXPAY("com.goochou.p2b.model.pay.FuiouQuickOutPay","wxpay","微信支付"),
    ALPAY("com.goochou.p2b.model.pay.FuiouQuickOutPay","alpay","支付宝支付"),
    ALLINPAY("com.goochou.p2b.model.pay.AllinPayOutPay","allinpay","通联支付"),
    CHANPAY("com.goochou.p2b.model.pay.ChanPayOutPay","chanpay","畅捷快捷支付"),
	OFFLINE(null,"offline","线下支付"),
    ALLINPAY_GATEWAY("com.goochou.p2b.model.pay.AllinGateWayPay","allinpay_gateway","通联网关"),
    YEEPAY("com.goochou.p2b.model.pay.YeeOutPay","yeepay","易宝支付");

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
    OutPayEnum(String featureType,String featureName,String description){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;

    }

    public static OutPayEnum getValueByType(String featureType){
        for (OutPayEnum enums : values()) {
            if (enums.getFeatureType()==featureType) {
                return enums;
            }
        }
        return null;
    }
    public static OutPayEnum getValueByName(String featureName){
        for (OutPayEnum enums : values()) {
            //不区分大小写返回
            if (enums.getFeatureName().equalsIgnoreCase(featureName)) {
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
