 package com.goochou.p2b.constant;

 /**
 * @author sxy
 * @date 2019/06/05
 */
public enum expressCompanyEnum {
    
    EMS("1","EMS","EMS快递"),
    SHUNFENG("2","shunfeng","顺丰快递"),
    SHENTONG("3","shentong","申通快递"),
    YUANTONG("4","yuantong","圆通快递"),
    ZHONGTONG("5","zhongtong","中通快递"),
    YUNDA("6","yunda","韵达快递"),
    BAISHIHUITONG("7","baishihuitong","百世汇通快递"),
    TIANTIAN("8","tiantian","天天快递"),
    ZHAIJISONG("9","zhaijisong","宅急送快递"),
    LUODIPEI("10","luodipei","落地配"),
    DAN_NIAO("11","DAN_NIAO","丹鸟"),
    DAN_MA("12","DAN_MA","丹马宽容"),
    AN_XIAN_DA("13","AN_XIAN_DA","安鲜达");
	

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
    expressCompanyEnum(String featureType, String featureName, String description){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;
    }
    
    public static expressCompanyEnum getValueByType(int featureType){
        for (expressCompanyEnum enums : values()) {
            if (enums.getFeatureType().equals(featureType)) {
                return enums;
            }
        }
        return null;
    }
    public static expressCompanyEnum getValueByName(String featureName){
        for (expressCompanyEnum enums : values()) {
            //不区分大小写返回
            if (enums.getFeatureName().equalsIgnoreCase(featureName)) {
                return enums;
            }
        }
        return null;
    }
    
    public static expressCompanyEnum getValueByDesc(String description){
        for (expressCompanyEnum enums : values()) {
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
