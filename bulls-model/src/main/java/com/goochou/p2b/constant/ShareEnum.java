/**
 *
 */
package com.goochou.p2b.constant;


public enum ShareEnum {
    type_1(1, "1", "更多"),
    type_2(2, "2", ""),
    type_3(3, "3", "最新活动"),
    type_4(4, "4", "佣金"),
    type_10001(10001, "5", "邀请好友");


    /**
     * 类型
     */
    private int featureType;
    /**
     * 名称
     */
    private String featureName;
    /**
     * 描叙
     */
    private String description;

    /**
     * 初始化
     *
     * @param featureType
     * @param featureName
     * @param description
     */
    ShareEnum(int featureType, String featureName, String description) {
        this.featureType = featureType;
        this.featureName = featureName;
        this.description = description;
    }

    public static ShareEnum getValueByType(int featureType) {
        for (ShareEnum enums : values()) {
            if (enums.getFeatureType() == featureType) {
                return enums;
            }
        }
        return null;
    }

    public static ShareEnum getValueByName(String featureName) {
        for (ShareEnum enums : values()) {
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
