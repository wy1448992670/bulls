package com.goochou.p2b.constant.project;

public enum ProjectLimitAmountEnum {

    LIMIT_AMOUNT_100(100, "100", "普通项目起投金额"),
    LIMIT_AMOUNT_20000(20000, "20000", "vip项目起投金额");

    /**
     * 类型
     */
    private Integer featureType;
    /**
     * 名称(数据库featrues存储名)
     */
    public String featureName;
    /**
     * 描叙
     */
    private String description;

    ProjectLimitAmountEnum(Integer featureType, String featureName, String description) {
        this.featureType = featureType;
        this.featureName = featureName;
        this.description = description;
    }

    public static ProjectLimitAmountEnum getValueByType(int featureType) {
        for (ProjectLimitAmountEnum enums : values()) {
            if (enums.getFeatureType().equals(featureType)) {
                return enums;
            }
        }
        return null;
    }

    public static ProjectLimitAmountEnum getValueByName(String featureName) {
        for (ProjectLimitAmountEnum enums : values()) {
            //不区分大小写返回
            if (enums.getFeatureName().equalsIgnoreCase(featureName)) {
                return enums;
            }
        }
        return null;
    }

    public static ProjectLimitAmountEnum getValueByDesc(String description) {
        for (ProjectLimitAmountEnum enums : values()) {
            //不区分大小写返回
            if (enums.getDescription().contains(description)) {
                return enums;
            }
        }
        return null;
    }

    /**
     * @return the featureType
     */
    public Integer getFeatureType() {
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
