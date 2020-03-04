package com.goochou.p2b.constant.project;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ProjectRiskEnum {
    PROJECT_RISK_0("weipinggu", 0, 0, "未评估", "-", 0, ""),
    PROJECT_RISK_A("baoshou", 10, 16, "保守型", "极低风险", 5, "10万"),
    PROJECT_RISK_B("wenjian", 17, 23, "稳健型", "较低风险", 4, "50万"),
    PROJECT_RISK_C("pingheng", 24, 30, "平衡型", "中等风险", 3, "100万"),
    PROJECT_RISK_D("chengzhang", 31, 37, "成长型", "中高风险", 2, "500万"),
    PROJECT_RISK_E("jinqu", 38, 100, "进取型", "高风险", 1, "5000万"); //目前maxScore最大43

    private String featureType;
    private Integer minScore;
    private Integer maxScore;
    private String featureName;
    private String description;
    private Integer star;
    private String limitAmount;

    ProjectRiskEnum(String featureType, Integer minScore, Integer maxScore, String featureName, String description, Integer star, String limitAmount) {
        this.featureType = featureType;
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.featureName = featureName;
        this.description = description;
        this.star = star;
        this.limitAmount = limitAmount;
    }

    /**
     * 根据评估分数判断属于哪一级别
     *
     * @param score
     * @return
     */
    public static ProjectRiskEnum getProjectRiskByScore(Integer score) {
    	if(score != null) {
			for (ProjectRiskEnum enums : values()) {
				if (score >= enums.getMinScore() && score <= enums.getMaxScore()) {
					return enums;
				}
			}
    	}
        return null;
    }

    public static List<Map<String, Object>> enumParseMap() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;
        for (ProjectRiskEnum e : values()) {
            map = new HashMap<>();
            map.put("featureType", e.featureType);
            map.put("minScore", e.minScore);
            map.put("maxScore", e.maxScore);
            map.put("featureName", e.featureName);
            map.put("description", e.description);
            map.put("star", e.star);
            map.put("limitAmount", e.limitAmount);
            list.add(map);
        }
        return list;
    }

    public static String getStarStringByNum(Integer starNum) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < starNum; i++) {
            sb.append("<font style='font-size:110%' color='#ff3214'>★</font>");
        }
        for (int j = 0; j < 5 - starNum; j++) {
            sb.append("<font style='font-size:110%' color='#C0C0C0'>☆</font>");
        }
        return String.valueOf(sb);
    }

    public static ProjectRiskEnum getValueByStar(Integer star) {
        for (ProjectRiskEnum enums : values()) {
            if (enums.getStar().equals(star)) {
                return enums;
            }
        }
        return null;
    }


    public static ProjectRiskEnum getValueByType(String featureType) {
        for (ProjectRiskEnum enums : values()) {
            if (enums.getFeatureType().equalsIgnoreCase(featureType)) {
                return enums;
            }
        }
        return null;
    }

    public static ProjectRiskEnum getValueByName(String featureName) {
        for (ProjectRiskEnum enums : values()) {
            if (enums.getFeatureName().equalsIgnoreCase(featureName)) {
                return enums;
            }
        }
        return null;
    }


    public String getFeatureType() {
        return featureType;
    }

    public void setFeatureType(String featureType) {
        this.featureType = featureType;
    }

    public Integer getMinScore() {
        return minScore;
    }

    public void setMinScore(Integer minScore) {
        this.minScore = minScore;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
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

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(String limitAmount) {
        this.limitAmount = limitAmount;
    }

    public static void main(String[] args) {
        System.out.println(getStarStringByNum(4));
    }

}
