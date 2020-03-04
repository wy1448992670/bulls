package com.goochou.p2b.constant;

public enum ActivityTargetTypeEnum {
	
	OPERATOR(1,"operator","操作人"),
	INVITEE(2,"invitee","被邀人"),
	INVITER(3,"inviter","邀请人"),
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
    ActivityTargetTypeEnum(int featureType, String featureName, String description){
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
    
    public static ActivityTargetTypeEnum getValueByType(int featureType) {
        for (ActivityTargetTypeEnum enums : values()) {
            if (enums.getFeatureType() == featureType) {
                return enums;
            }
        }
        return null;
    }

    public static ActivityTargetTypeEnum getValueByName(String featureName) {
        for (ActivityTargetTypeEnum enums : values()) {
            if (enums.getFeatureName().equals(featureName)) {
                return enums;
            }
        }
        return null;
    }


}
