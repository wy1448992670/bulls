package com.goochou.p2b.constant;

/**
 * Created on 2019年5月16日
 * <p>Title:       [短信渠道]</p>
 * <p>Company:     奔富畜牧</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * <p>Department:  研发中心</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public enum MessageChannelEnum {
	
	CHANZOR(10001,"chanzor","畅卓"),
	DH3T(10002,"dh3t","大汉三通");
	
    
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
    MessageChannelEnum(int featureType,String featureName,String description){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;
    }
    
    public static MessageChannelEnum getValueByType(int featureType){
        for (MessageChannelEnum enums : values()) {
            if (enums.getFeatureType()==featureType) {
                return enums;
            }
        }
        return null;
    }
    public static MessageChannelEnum getValueByName(String featureName){
        for (MessageChannelEnum enums : values()) {
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
