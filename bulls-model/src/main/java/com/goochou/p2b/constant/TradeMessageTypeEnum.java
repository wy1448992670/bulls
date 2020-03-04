package com.goochou.p2b.constant;



/**
 * 外部报文日志类别
 * @author WuYJ
 *
 */
public enum TradeMessageTypeEnum {
    SEND("10000","send","发送"),
    BACK("10001","back","回调");
    
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
    TradeMessageTypeEnum(String featureType, String featureName, String description){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;
    }
    
    public static TradeMessageTypeEnum getValueByType(int featureType){
        for (TradeMessageTypeEnum enums : values()) {
            if (enums.getFeatureType().equals(featureType)) {
                return enums;
            }
        }
        return null;
    }
    public static TradeMessageTypeEnum getValueByName(String featureName){
        for (TradeMessageTypeEnum enums : values()) {
            //不区分大小写返回
            if (enums.getFeatureName().equalsIgnoreCase(featureName)) {
                return enums;
            }
        }
        return null;
    }
    
    public static TradeMessageTypeEnum getValueByDesc(String description){
        for (TradeMessageTypeEnum enums : values()) {
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
