/**
 * 
 */
package com.goochou.p2b.constant;

public enum PushUrlTypeEnum {
	URL(10001,"url","url"),
	NATIVE(10002,"native","原生");


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
     */
    PushUrlTypeEnum(int featureType, String featureName, String description){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;
    }
    
    public static PushUrlTypeEnum getValueByType(int featureType){
        for (PushUrlTypeEnum enums : values()) {
            if (enums.getFeatureType()==featureType) {
                return enums;
            }
        }
        return null;
    }
    public static PushUrlTypeEnum getValueByName(String featureName){
        for (PushUrlTypeEnum enums : values()) {
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
