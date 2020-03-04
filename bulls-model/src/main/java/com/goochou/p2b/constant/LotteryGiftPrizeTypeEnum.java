/**
 * 
 */
package com.goochou.p2b.constant;


/**
 * 4
 * @author xueqi
 *
 */
public enum LotteryGiftPrizeTypeEnum {
	
	INVEST_HONGBAO(10001,"investHongBao","投资红包"),
	CASH_HONGBAO(10002,"cashHongBao","现金红包"),
	RATE_COUPON(10003,"rateCoupon","加息券"),
	EXPERIENCE(10004,"experience","体验金");
	
    
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
    LotteryGiftPrizeTypeEnum(int featureType,String featureName,String description){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;
    }
    
    public static LotteryGiftPrizeTypeEnum getValueByType(int featureType){
        for (LotteryGiftPrizeTypeEnum enums : values()) {
            if (enums.getFeatureType()==featureType) {
                return enums;
            }
        }
        return null;
    }
    public static LotteryGiftPrizeTypeEnum getValueByName(String featureName){
        for (LotteryGiftPrizeTypeEnum enums : values()) {
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
