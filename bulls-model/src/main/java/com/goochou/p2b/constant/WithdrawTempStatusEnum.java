package com.goochou.p2b.constant;


/**
 * 交易报文状态
 * @author WuYJ
 *
 */
public enum WithdrawTempStatusEnum {

    WEIWANCHENG("1001","weiwancheng","未完成"),
    YIWANCHENG("1002","yiwancheng","已完成"),
    SHIBAI("1003","shibai","失败");
    
    
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
    WithdrawTempStatusEnum(String featureType,String featureName,String description){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;
    }
    
    public static WithdrawTempStatusEnum getValueByType(String featureType){
        for (WithdrawTempStatusEnum enums : values()) {
            if (enums.getFeatureType()==featureType) {
                return enums;
            }
        }
        return null;
    }
    public static WithdrawTempStatusEnum getValueByName(String featureName){
        for (WithdrawTempStatusEnum enums : values()) {
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
