package com.goochou.p2b.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ProjectDebtRateEnum {
    DEBT_RATE_5(1, "0.5","true", 0.005),
    DEBT_RATE_6(1, "0.6", "false", 0.006),
    DEBT_RATE_7(1, "0.7", "false", 0.007),
    DEBT_RATE_8(1, "0.8", "false", 0.008),
    DEBT_RATE_9(1, "0.9", "false", 0.009),
    DEBT_RATE_10(1, "1", "false", 0.01),
    DEBT_RATE_11(1, "1.1", "false", 0.011),
    DEBT_RATE_12(1, "1.2", "false", 0.012),
    DEBT_RATE_13(1, "1.3", "false", 0.013),
    DEBT_RATE_14(1, "1.4", "false", 0.014),
    DEBT_RATE_15(1, "1.5", "false", 0.015),
    DEBT_RATE_16(1, "1.6", "false", 0.016),
    DEBT_RATE_17(1, "1.7", "false", 0.017),
    DEBT_RATE_18(1, "1.8", "false", 0.018),
    DEBT_RATE_19(1, "1.9", "false", 0.019),
    DEBT_RATE_20(1, "2.0", "false", 0.020);

    /**
     * 是否显示
     */
    private Integer featureType;
    /**
     * 折扣率
     */
    private String featureName;
    /**
     * 默认值
     */
    private String description;
    
    /**
     * 折扣率折算
     */
    private Double value;

    ProjectDebtRateEnum(Integer featureType, String featureName, String description, Double value) {
        this.featureType = featureType;
        this.featureName = featureName;
        this.description = description;
        this.value = value;
    }
    public static  List<Enum<ProjectDebtRateEnum>> getEnumList(){
        List<Enum<ProjectDebtRateEnum>> list = new ArrayList<>();
        list.addAll(Arrays.asList(ProjectDebtRateEnum.values()));
        return list;
    }
    public static List<Map<String,Object>> enumParseMap() {
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map;
        for (ProjectDebtRateEnum e : ProjectDebtRateEnum.values()) {
            if(e.featureType == 1){
                map = new HashMap<>();
                map.put("discount",e.featureName);
                map.put("flag",e.description);
                list.add(map);
            }
        }
        return list;
    }
    
    public static ProjectDebtRateEnum getValueByValue(Double value){
        for (ProjectDebtRateEnum enums : values()) {
            //不区分大小写返回
            if (enums.getValue().doubleValue() == value.doubleValue()) {
                return enums;
            }
        }
        return null;
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

    public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public static void main(String[] args) {
        String s="123%s6%s79";
        String a= String.format(s,"5","9");
        System.out.println(s);
        System.out.println(a);
        
        //System.out.println(ProjectDebtRateEnum.getValueByValue(0.011));
    }
}
