package com.goochou.p2b.constant;

public enum ActivityTriggerTypeEnum {
	
	REGISTER(1,"register","注册"),
	INVESTMENT(2,"investment","投资"),
	BUYGOODS(3,"buygoods","消费"),
	LOGIN(4,"login","登录"),
	INVITE_FIRST_INVESTMENT(5,"invite_first_investment","邀请首投"),
	INVEST_BUY_BACK(6, "invest_buy_back","投资回购")
	;
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
     * @param featureType
     * @param featureName
     * @param description
     */
    ActivityTriggerTypeEnum(int featureType, String featureName, String description){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;
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
    
    public static ActivityTriggerTypeEnum getValueByType(int featureType) {
        for (ActivityTriggerTypeEnum enums : values()) {
            if (enums.getFeatureType() == featureType) {
                return enums;
            }
        }
        return null;
    }

    public static ActivityTriggerTypeEnum getValueByName(String featureName) {
        for (ActivityTriggerTypeEnum enums : values()) {
            if (enums.getFeatureName().equals(featureName)) {
                return enums;
            }
        }
        return null;
    }


}
