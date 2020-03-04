/**
 * 
 */
package com.goochou.p2b.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 养牛、拼牛
 * @author ydp
 * @date 2019/12/05
 */
public enum ProjectTypeEnum {
	YANGNIU(0,"yangniu","养牛"),
	PINNIU(1,"pinniu","拼牛"),
	;
	
    
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
    ProjectTypeEnum(int featureType,String featureName,String description){
        this.featureType=featureType;
        this.featureName=featureName;
        this.description=description;
    }
    
    public static ProjectTypeEnum getValueByType(int featureType){
        for (ProjectTypeEnum enums : values()) {
            if (enums.getFeatureType()==featureType) {
                return enums;
            }
        }
        return null;
    }
    public static ProjectTypeEnum getValueByName(String featureName){
        for (ProjectTypeEnum enums : values()) {
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
    
    public static List<Enum<ProjectTypeEnum>> getEnumList() {
        List<Enum<ProjectTypeEnum>> list = new ArrayList<>();
        list.addAll(Arrays.asList(ProjectTypeEnum.values()));
        return list;
    }
    
}
