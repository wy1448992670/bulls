/**
 * 
 */
package com.goochou.p2b.constant;


/**
 * 业务渠道枚举
 * Created on 2017年6月13日
 * <p>Title:       DEC集团系统_[子系统统名]_[模块名]/p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * <p>Company:     澳钜（上海）教育科技有限公司</p>
 * <p>Department:  研发中心</p>
 * @version        1.0
 */
public enum InterestHasDividendedEnum {
	WEIPAIXI(0,"weipaixi","未派息"),
	YIPAIXI(1,"yipaixi","已派息"),
	YIJISUAN(2,"yijisuan","已计算（未实际发放）");
    
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
    InterestHasDividendedEnum(int featureType,String featureName,String description){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;
    }
    
    public static InterestHasDividendedEnum getValueByType(int featureType){
        for (InterestHasDividendedEnum enums : values()) {
            if (enums.getFeatureType()==featureType) {
                return enums;
            }
        }
        return null;
    }
    public static InterestHasDividendedEnum getValueByName(String featureName){
        for (InterestHasDividendedEnum enums : values()) {
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
