package com.goochou.p2b.constant;

/**
 * 使用规则枚举
 * @author WuYJ
 *
 */
public enum UseRuleEnum {
	PROJECTVALIDMINDAY("projectValidMinDay","适用项目期限(最小天数)","仅限xxx天及以上散标使用"),
	PROJECTVALIDMAXDAY("projectValidMaxDay","适用项目期限(最大天数)","仅限xxx天及以下散标使用"),
	PROJECTVALIDDAY("projectValidDay", "项目期限(天)","仅限xxx天散标使用；"),
	PROJECTVALIDMINAMOUNT("projectValidMinAmount", "适用项目金额(最小金额)","单笔出借满xxx元可用");
	
	/**
	 * key
	 */
	private String key;
	/**
	 * 值
	 */
	private String value;

	private String description;

	
    public static UseRuleEnum getValueByKey(String key){
        for (UseRuleEnum enums : values()) {
            //不区分大小写返回
            if (enums.getKey().equalsIgnoreCase(key)) {
                return enums;
            }
        }
        return null;
    }
	
	private UseRuleEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}

	UseRuleEnum(String key, String value, String description) {
		this.key = key;
		this.value = value;
		this.description = description;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
