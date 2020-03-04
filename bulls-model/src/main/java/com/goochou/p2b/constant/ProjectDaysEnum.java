package com.goochou.p2b.constant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public enum ProjectDaysEnum {
    PROJECT_DAYS_30(30, "30天", "1",BigDecimal.valueOf(7.8d)),
    PROJECT_DAYS_60(60, "60天", "1",BigDecimal.valueOf(8.2d)),
    PROJECT_DAYS_90(90, "90天", "1",BigDecimal.valueOf(8.6d)),
    PROJECT_DAYS_180(180, "180天", "1",BigDecimal.valueOf(9.6d)),
//    PROJECT_DAYS_270(270, "270天", "1"),
    PROJECT_DAYS_360(360, "360天", "1",BigDecimal.valueOf(10.8d));

    /**
     * 天数
     */
    private Integer featureType;
    /**
     * 几个月
     */
    private String featureName;
    /**
     * 是否显示 0不显示 1显示
     */
    private String description;

    private BigDecimal rate;


    ProjectDaysEnum(Integer featureType, String featureName, String description, BigDecimal rate) {
        this.featureType = featureType;
        this.featureName = featureName;
        this.description = description;
        this.rate = rate;
    }

    public static ProjectDaysEnum getValueByType(int featureType){
        for (ProjectDaysEnum enums : values()) {
            if (enums.getFeatureType().equals(featureType)) {
                return enums;
            }
        }
        return null;
    }


    public static List<Enum<ProjectDaysEnum>> getEnumList() {
        List<Enum<ProjectDaysEnum>> list = new ArrayList<>();
        list.addAll(Arrays.asList(ProjectDaysEnum.values()));
        return list;
    }

    public static List<Map<String, Object>> enumParseMap() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;
        for (ProjectDaysEnum e : ProjectDaysEnum.values()) {
            if (e.description.equals("1")) {
                map = new HashMap<>();
                map.put("days", e.featureType.toString());
                map.put("daysName", e.featureName);
                list.add(map);
            }
        }
        return list;
    }
    
    /**
     * 拼接外部集合
    * @Title: enumParseMap 
    * @param list  需要拼接的集合
    * @return List<Map<String,Object>>
    * @author zj
    * @date 2019-06-26 16:39
     */
    public static List<Map<String, Object>> enumParseMap(List<Map<String, Object>> list) {
    	
    	List<Map<String, Object>> bigList = new ArrayList<>();
    	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			bigList.add(map);
		}
    	Map<String, Object> map;
    	for (ProjectDaysEnum e : ProjectDaysEnum.values()) {
    		if (e.description.equals("1")) {
    			map = new HashMap<>();
    			map.put("days", e.featureType.toString());
    			map.put("daysName", e.featureName);
    			bigList.add(map);
    		}
    	}
    	return bigList;
    }

    public Integer getFeatureType() {
        return featureType;
    }

    public void setFeatureType(Integer featureType) {
        this.featureType = featureType;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * 拼接外部集合（新需求）
    * @Title: enumParseMap 
    * @param list  需要拼接的集合
    * @return List<Map<String,Object>>
    * @author zj
    * @date 2019-06-26 16:39
     */
    public static List<Map<String, Object>> enumParseMapNew(List<Map<String, Object>> list) {
    	
    	List<Map<String, Object>> bigList = new ArrayList<>();
    	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			bigList.add(map);
		}
    	Map<String, Object> map;
    	for (ProjectDaysEnum e : ProjectDaysEnum.values()) {
    		if (e.description.equals("1")) {
    			map = new HashMap<>();
    			map.put("id", e.featureType.toString());
    			map.put("subTitle", e.featureName);
    			bigList.add(map);
    		}
    	}
    	return bigList;
    }
    
    public static void main(String[] args) {
        System.out.println(enumParseMap());

    }
}
