package com.goochou.p2b.constant;



/**
 * Created on 2019年5月13日
 * <p>Title:       [订单类型]</p>
 * <p>Company:     奔富畜牧</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * <p>Department:  研发中心</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public enum OrderTypeEnum {
	
    INVESTMENT("1001","investment","物权订单"),
    GOODS("1002","goods","商城订单"),
    RECHARGE("1003","recharge","充值单"),
    PROJECT("1004","project","续购标的");
    
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
    OrderTypeEnum(String featureType, String featureName, String description){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;
    }
    
    public static OrderTypeEnum getValueByType(int featureType){
        for (OrderTypeEnum enums : values()) {
            if (enums.getFeatureType().equals(featureType)) {
                return enums;
            }
        }
        return null;
    }
    public static OrderTypeEnum getValueByName(String featureName){
        for (OrderTypeEnum enums : values()) {
            //不区分大小写返回
            if (enums.getFeatureName().equalsIgnoreCase(featureName)) {
                return enums;
            }
        }
        return null;
    }
    
    public static OrderTypeEnum getValueByDesc(String description){
        for (OrderTypeEnum enums : values()) {
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
