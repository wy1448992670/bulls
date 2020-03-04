package com.goochou.p2b.constant.project;

/**
 * Created on 2017-11-22
 * <p>Title:       [标的列表TAB分类]</p>
 * <p>Company:     鑫聚财线上交易平台</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * <p>Department:  研发中心</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public enum ProjectTabEnum {
    //标识，name，解释，是否选中（有且仅有一个true）
	LINGHUOBAO("10003", "yueyueying", "智投", true, "http://m2.xinjucai.com/pages/capital/monthly_gain_newlist.html?r=2018091915315901"),
//	LINGHUOBAO("10003", "yueyueying", "智投", true, "http://test.xinjucai.com:9999/pages/capital/monthly_gain_newlist.html?r=201809171122001"),
    DINGQI("10001", "dingqi", "散标", false, ""),
    ZHAIZHUAN("10002", "zhaizhuan", "债转", false, "");
    
    /**
     * 类型
     */
    private String featureType;
    /**
     * 名称(数据库featrues存储名)
     */
    public String featureName;
    /**
     * 描叙
     */
    private String description;

    private Boolean flag;

    private String url;

    /**
     * 初始化
     * @param featureType
     * @param featureName
     * @param description
     */
    ProjectTabEnum(String featureType, String featureName, String description, Boolean flag){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;
        this.flag=flag;
    }

    ProjectTabEnum(String featureType, String featureName, String description, Boolean flag, String url) {
        this.featureType = featureType;
        this.featureName = featureName;
        this.description = description;
        this.flag = flag;
        this.url = url;
    }

    public static ProjectTabEnum getValueByType(int featureType){
        for (ProjectTabEnum enums : values()) {
            if (enums.getFeatureType().equals(featureType)) {
                return enums;
            }
        }
        return null;
    }
    public static ProjectTabEnum getValueByName(String featureName){
        for (ProjectTabEnum enums : values()) {
            //不区分大小写返回
            if (enums.getFeatureName().equalsIgnoreCase(featureName)) {
                return enums;
            }
        }
        return null;
    }

    public static ProjectTabEnum getValueByDesc(String description){
        for (ProjectTabEnum enums : values()) {
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

    public Boolean getFlag() {
        return flag;
    }

    public String getUrl() {
        return url;
    }
}
