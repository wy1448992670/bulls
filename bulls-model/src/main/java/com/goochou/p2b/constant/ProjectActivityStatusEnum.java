package com.goochou.p2b.constant;



/**
 * 用户类型
 * @author xueqi
 *
 */
public enum ProjectActivityStatusEnum {
	
	WATING_BEGIN("1000","waitingBegin","即将开始"),
	
	WAITING_OPEN("1001","waitingOpen","待开奖"),
    
    SELLING("1002","selling","进行中"),
	
	OPENED("1003","opened","已开奖");
    
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
    ProjectActivityStatusEnum(String featureType, String featureName, String description){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;
    }
    
    public static ProjectActivityStatusEnum getValueByType(int featureType){
        for (ProjectActivityStatusEnum enums : values()) {
            if (enums.getFeatureType().equals(featureType)) {
                return enums;
            }
        }
        return null;
    }
    public static ProjectActivityStatusEnum getValueByName(String featureName){
        for (ProjectActivityStatusEnum enums : values()) {
            //不区分大小写返回
            if (enums.getFeatureName().equalsIgnoreCase(featureName)) {
                return enums;
            }
        }
        return null;
    }
    
    public static ProjectActivityStatusEnum getValueByDesc(String description){
        for (ProjectActivityStatusEnum enums : values()) {
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
