package com.goochou.p2b.constant;

/***
 * 打款记录枚举
 * @author fangjun
 *
 */
public enum PayMoneyPlanStatusEnum {
	
	CHANNEL_WEIDAKUAN("weidakuan","渠道方未打款"),
	CHANNEL_YIDAKUAN("yidakuan","渠道方已打款"),
	PLATFORM_WEIDAKUAN("weishoukuan","平台未收款"),
	PLATFORM_YISHOUKUAN("yishoukuan","平台已收款");
	
	/**
     * (数据库channel_status,platform_status字段存储值)
     */
    private String featureName;
    /**
     * 描叙
     */
    private String description;
    
    /***
     * 初始化
     * @param featureName
     * @param description
     */
    PayMoneyPlanStatusEnum(String featureName,String description){
    	  this.featureName=featureName;
          this.description=description;
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
