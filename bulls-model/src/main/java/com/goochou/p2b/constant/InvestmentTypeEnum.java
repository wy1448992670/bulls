/**
 * 
 */
package com.goochou.p2b.constant;


/**
 * 0定期1活期2新手标3超级标4VIP标5资产包产品6利息复投7本金复投 8:月月盈产品
 * @author xueqi
 *
 */
public enum InvestmentTypeEnum {
	DINGQI(0,"dingqi","定期"),
	HUOQI(1,"huoqi","活期"),
	XINSHOU(2,"xinshou","新手标"),
	CHAOJI(3,"chaoji","超级标"),
	VIP(4,"vip","VIP标"),
	ZICHANBAO(5,"zichanbao","资产包产品"),
	LIXIFUTOU(6,"lixifutou","利息复投"),
	BENJINFUTOU(7,"benjinfutou","本金复投"),
	YUEYUEYING(8,"yueyueying","月月盈产品"),
	YUEYUEYINGFUTOU(9,"yueyueyingfutou","月月盈复投");
	
    
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
    InvestmentTypeEnum(int featureType,String featureName,String description){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;
    }
    
    public static InvestmentTypeEnum getValueByType(int featureType){
        for (InvestmentTypeEnum enums : values()) {
            if (enums.getFeatureType()==featureType) {
                return enums;
            }
        }
        return null;
    }
    public static InvestmentTypeEnum getValueByName(String featureName){
        for (InvestmentTypeEnum enums : values()) {
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
