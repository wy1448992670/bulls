package com.goochou.p2b.constant.farmcloud;


/**
 * @Auther: huangsj
 * @Date: 2019/6/28 16:46
 * @Description:
 */
public enum CompanyEnum {


    DRUIDTECH("","druidtech","四川德鲁伊科技-畜牧精细化管理"),
    ;

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
    CompanyEnum(String featureType,String featureName,String description){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;
    }

    public static CompanyEnum getValueByType(String featureType){
        for (CompanyEnum enums : values()) {
            if (enums.getFeatureType()==featureType) {
                return enums;
            }
        }
        return null;
    }
    public static CompanyEnum getValueByName(String featureName){
        for (CompanyEnum enums : values()) {
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
