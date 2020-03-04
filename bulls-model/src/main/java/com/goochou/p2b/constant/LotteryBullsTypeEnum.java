package com.goochou.p2b.constant;

public enum LotteryBullsTypeEnum {
	HEALTH_BULLS(1,"health_bulls", "健康牛","JK"),
	HAPPY_BULLS(2, "happy_bulls", "快乐牛","KL"),
	SAFETY_BULLS(3, "safety_bulls", "平安牛","PA"),
	LUCKY_BULLS(4,"lucky_bulls", "吉祥牛","XY"),
	BENFU_BULLS(5,  "benfu_bulls", "奔富牛","BF"),
	GREATE_BULLS(6, "greate_bulls", "牛气冲天","NQCT"),
	NOTHING(7, "nothing", "未命中","NT");
	
	LotteryBullsTypeEnum(int type, String name, String description, String code){
		this.type = type;
		this.name = name;
		this.description = description;
		this.code = code;
	}
	
	private int type; 
	private String name;
	private String description;
	private String code;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public static LotteryBullsTypeEnum getValueByType(int type){
		for (LotteryBullsTypeEnum enums : values()) {
			if (enums.getType() == type) {
				return enums;
			}
		}
		return null;
	}
}
