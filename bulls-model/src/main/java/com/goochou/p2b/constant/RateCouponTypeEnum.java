package com.goochou.p2b.constant;

public enum RateCouponTypeEnum {
    UN_LIMITED(10001,"unlimited","无限制加息券"),
    LIMITED(10002,"limited","有限制加息券");



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
    RateCouponTypeEnum(int featureType,String featureName,String description){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;
    }

    public static RateCouponTypeEnum getValueByType(int featureType){
        for (RateCouponTypeEnum enums : values()) {
            if (enums.getFeatureType()==featureType) {
                return enums;
            }
        }
        return null;
    }
    public static RateCouponTypeEnum getValueByName(String featureName){
        for (RateCouponTypeEnum enums : values()) {
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
