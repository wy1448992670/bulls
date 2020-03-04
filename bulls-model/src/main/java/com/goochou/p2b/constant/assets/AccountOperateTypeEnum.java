package com.goochou.p2b.constant.assets;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author 张琼麒
 * @version 创建时间：2019年6月13日 上午10:42:02
 * 对用户资金账户的操作类型枚举
 */
public enum AccountOperateTypeEnum {
	//											signSymbol	str	mainAccount		frozenAccount		wholeAccount
	ADD(1, "add", "增加", 1, "+", true, false, true),
	SUBTRACT(2, "subtract", "减少", -1, "-", true, false, true),
	FROZEN_ADD(3, "frozen_add", "解冻返回", 1, "+", false, true, true),
	FROZEN_SUBTRACT(4, "frozen_subtract", "解冻扣除", -1, "-", false, true, true),
	UNFREEZE(5, "unfreeze", "解冻", 1, "+", true, true, false),
	FREEZE(6, "freeze", "冻结", -1, "-", true, true, false);

	/**
	 * id
	 */
	private int featureType;
	/**
	 *
	 */
	private String featureName;
	/**
	 * 描叙
	 */
	private String description;
	/**
	 * 操作符号
	 */
	private Integer signSymbol;
	/**
	 * 显示操作符号
	 */
	private String signSymbolStr;
	/**
	 * 主账户相关
	 */
	private Boolean aboutMainAccount;
	/**
	 * 冻结账户相关
	 */
	private Boolean aboutFrozenAccount;
	/**
	 * 整体账户相关
	 */
	private Boolean aboutWholeAccount;

	AccountOperateTypeEnum(int featureType, String featureName, String description, Integer signSymbol,
			String signSymbolStr, Boolean aboutMainAccount, Boolean aboutFrozenAccount, Boolean aboutWholeAccount) {
		this.featureType = featureType;
		this.featureName = featureName;
		this.description = description;
		this.signSymbol=signSymbol;
		this.signSymbolStr=signSymbolStr;
		this.aboutMainAccount=aboutMainAccount;
		this.aboutFrozenAccount=aboutFrozenAccount;
		this.aboutWholeAccount=aboutWholeAccount;
	}

	public static AccountOperateTypeEnum getValueByType(int featureType) {
		for (AccountOperateTypeEnum enums : values()) {
			if (enums.getFeatureType() == featureType) {
				return enums;
			}
		}
		return null;
	}

	public static AccountOperateTypeEnum getValueByName(String featureName) {
		for (AccountOperateTypeEnum enums : values()) {
			// 不区分大小写返回
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
		for (AccountOperateTypeEnum e : AccountOperateTypeEnum.values()) {
			JSONObject object = new JSONObject();
			object.put("featureType", e.getFeatureType());
			object.put("featureName", e.getFeatureName());
			object.put("signSymbol", e.getSignSymbol());
			object.put("signSymbolStr", e.getSignSymbolStr());
			object.put("aboutMainAccount", e.getAboutMainAccount());
			object.put("aboutFrozenAccount", e.getAboutFrozenAccount());
			object.put("aboutWholeAccount", e.getAboutWholeAccount());
			jsonArray.add(object);
		}
		return jsonArray.toString();
	}

	public int getFeatureType() {
		return featureType;
	}

	public String getFeatureName() {
		return featureName;
	}

	public String getDescription() {
		return description;
	}

	public Integer getSignSymbol() {
		return signSymbol;
	}

	public String getSignSymbolStr() {
		return signSymbolStr;
	}

	public Boolean getAboutMainAccount() {
		return aboutMainAccount;
	}

	public Boolean getAboutFrozenAccount() {
		return aboutFrozenAccount;
	}

	public Boolean getAboutWholeAccount() {
		return aboutWholeAccount;
	}


}
