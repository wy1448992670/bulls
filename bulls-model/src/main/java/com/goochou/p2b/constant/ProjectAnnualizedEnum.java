package com.goochou.p2b.constant;


/**
 * 项目标地状态枚举
 */
public enum ProjectAnnualizedEnum {
    ANNUALIZED_1(10,2.0,"15"),        //新手标15天标的
    ANNUALIZED_8(8,0.5,"30"),      //30天标的
    ANNUALIZED_9(9, 1.0,"90"),      //90天标的
    ANNUALIZED_10(10,1.0, "180");   //180天标的

    /**
     * 年化收益
     */
    private Integer featureType;
    /**
     * 额外加息
     */
    private Double featureName;
    /**
     * 项目期限
     */
    private String description;

    /**
     * 初始化
     *
     * @param featureType
     * @param featureName
     * @param description
     */
    ProjectAnnualizedEnum(Integer featureType, Double featureName, String description) {
        this.featureType = featureType;
        this.featureName = featureName;
        this.description = description;
    }

    public static ProjectAnnualizedEnum getValueByType(Integer featureType) {
        for (ProjectAnnualizedEnum enums : values()) {
            if (enums.getFeatureType() == featureType) {
                return enums;
            }
        }
        return null;
    }

    public static ProjectAnnualizedEnum getValueByName(Double featureName) {
        for (ProjectAnnualizedEnum enums : values()) {
            //不区分大小写返回
            if (enums.getFeatureName() == featureName) {
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
    public Double getFeatureName() {
        return featureName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
}
