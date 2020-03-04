package com.goochou.p2b.constant;

/**
 * 
 * <p>[描述信息：优惠券类型]</p>
 *
 * @author lxfeng 
 * @version 1.0 Created on 2018-6-21 上午10:12:25
 */
public enum CouponTypeEnum {
	
	INVEST_RED(10001,"0","投资红包"),
	RATE_COUPON(10002,"1","加息券"),
	CASH_RED(10003,"2","现金红包");
	
    
	/**
     * 类型
     */
    private int featureType;
    
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
     * @param memo
     */
    CouponTypeEnum(int featureType,String featureName,String description){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;
    }
    
    public static CouponTypeEnum getValueByType(int featureType){
        for (CouponTypeEnum enums : values()) {
            if (enums.getFeatureType()==featureType) {
                return enums;
            }
        }
        return null;
    }
    public static CouponTypeEnum getValueByName(String featureName){
        for (CouponTypeEnum enums : values()) {
            if (enums.getFeatureName().equalsIgnoreCase(featureName)) {
                return enums;
            }
        }
        return null;
    }

    /**
     * @return the featureType
     */
    public int getFeatureType() {
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
