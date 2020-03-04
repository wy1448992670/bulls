package com.goochou.p2b.constant.assets;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 用户账户类型枚举
 * @author WuYj
 *
 */
public enum AccountTypeEnum {
	CASH(0,"cash","现金", "银行卡"),
	BALANCE(1,"balance","余额", "余额"),
	//FROZEN(10002,"frozen","用户冻结账户"),
	CREDIT(2,"credit","授信余额", "授信余额");
	//CREDITFROZEN(10004,"creditFrozen","用户授信冻结账户");

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

    private String appDescription;
    /**
     * 初始化
     * @param featureType
     * @param featureName
     * @param description
     */
    AccountTypeEnum(int featureType,String featureName,String description,String appDescription){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;
        this.appDescription=appDescription;
    }

    public static AccountTypeEnum getValueByType(int featureType){
        for (AccountTypeEnum enums : values()) {
            if (enums.getFeatureType()==featureType) {
                return enums;
            }
        }
        return null;
    }
    public static AccountTypeEnum getValueByName(String featureName){
        for (AccountTypeEnum enums : values()) {
            //不区分大小写返回
            if (enums.getFeatureName().equalsIgnoreCase(featureName)) {
                return enums;
            }
        }
        return null;
    }

    /**
     * 转化成json
     * @return
     */
    public static String toJson(){
        JSONArray jsonArray = new JSONArray();
        for (AccountTypeEnum e : AccountTypeEnum.values()) {
            JSONObject object = new JSONObject();
            object.put("featureType", e.getFeatureType());
            object.put("featureName", e.getFeatureName());
            object.put("description", e.getDescription());
            jsonArray.add(object);
        }
        return jsonArray.toString();
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

    public String getAppDescription() {
        return appDescription;
    }
}
