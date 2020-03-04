package com.goochou.p2b.constant.validate;

/**
 * 
 * Created on 2016-6-25
 * <p>Title:       中投融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [描述该类概要功能介绍]</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * <p>Company:     中投融线上交易平台</p>
 * <p>Department:  研发中心</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public enum ValidateEnum {
    
	AUTH(10001,"AUTH","没有权限"),//没有权限
	FAILED(10002,"FAILED","认证失败"),//认证失败
    SUCCESS(10003,"SUCCESS","认证成功"),//认证成功
    TIMES(10004,"TIMES","次数限制");//次数限制
    
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
    ValidateEnum(int featureType,String featureName,String description){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;
    }
    
    public static ValidateEnum getValueByType(int featureType){
        for (ValidateEnum enums : values()) {
            if (enums.getFeatureType()==featureType) {
                return enums;
            }
        }
        return null;
    }
    public static ValidateEnum getValueByName(String featureName){
        for (ValidateEnum enums : values()) {
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
