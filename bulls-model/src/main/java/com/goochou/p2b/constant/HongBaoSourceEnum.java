package com.goochou.p2b.constant;

public enum HongBaoSourceEnum {
	
	PC(0,"PC","PC",""),
	APP(1,"APP","APP",""),
	WAP(2,"WAP","WAP",""),
	FIRST_INVEST(1001,"first_invest","首投奖","首投奖励"),
	//被邀请人与邀请人的首投奖励区别
	FIRST_INVESTEE(1004,"first_investee","首投奖","首投奖励"),
	COMMISION(1002,"commision","提成奖","提成奖"),
	CONNECTION(1003,"connection","人脉奖","人脉奖"),
	SURPRISE_GIFT(1005, "suprise_gift", "七月惊喜豪礼", "七月惊喜豪礼"),
	CHUQIU_GIFT(1006, "chuqiu_gift", "初秋大礼", "初秋大礼"),
	CYCLE_GIFT(1007, "cycle_gift", "智投周期奖励", "智投周期奖励"),
	CENTER_GIFT(1008, "center_gift", "领券中心", "领券中心");
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
     * 描叙2（主要用于用户短信发放中使用）
     */
    private String description2;
    
    /**
     * 初始化
     * @param featureType
     * @param featureName
     * @param description
     */
    HongBaoSourceEnum(int featureType, String featureName, String description,String description2){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;
        this.description2=description2;
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
    
    /**
     * @return the description2
     */
    public String getDescription2() {
        return description2;
    }


    public static HongBaoSourceEnum getValueByType(int featureType) {
        for (HongBaoSourceEnum enums : values()) {
            if (enums.getFeatureType() == featureType) {
                return enums;
            }
        }
        return null;
    }

    public static HongBaoSourceEnum getValueByName(String featureName) {
        for (HongBaoSourceEnum enums : values()) {
            if (enums.getFeatureName().equals(featureName)) {
                return enums;
            }
        }
        return null;
    }


}
