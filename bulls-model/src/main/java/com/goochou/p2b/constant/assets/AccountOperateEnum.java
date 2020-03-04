package com.goochou.p2b.constant.assets;

import static com.goochou.p2b.constant.assets.AccountTypeEnum.*;
import static com.goochou.p2b.constant.assets.AccountOperateTypeEnum.*;

/**
 * Created on 2019年5月8日
 * <p>
 * Title: [描述该类概要功能介绍]
 * </p>
 * <p>
 * Company: 奔富畜牧
 * </p>
 * <p>
 * Copyright: Copyright (c) 2017
 * </p>
 * <p>
 * Department: 研发中心
 * </p>
 *
 * 更新此枚举请同步更新account_operate表
 *
 * @author [叶东平] [58294114@qq.com]
 * @version 1.0
 */
public enum AccountOperateEnum {
	/**
	 * 命名规则 例: 提现
	 * WITHDRAW_BALANCE_FREEZE("10116","withdraw_balance_freeze","提现", "提现",BALANCE,FREEZE)
	 * 枚举名:业务名(WITHDRAW)_AccountType.name(BALANCE)_AccountOperateType.name(FREEZE)
	 * featureType:业务编号(101)+AccountType.id(1)+AccountOperateType.id(6)
	 * description:业务中文名
	 * appDescription:app端显示的业务名
	 */
	//WITHDRAW 101
	WITHDRAW_BALANCE_FREEZE("10116", "withdraw_balance_freeze", "提现", "提现", BALANCE, FREEZE),
	WITHDRAW_BALANCE_FROZEN_SUBTRACT("10114", "withdraw_balance_frozen_subtract", "提现", "提现", BALANCE, FROZEN_SUBTRACT), // 提现
	WITHDRAW_BALANCE_UNFREEZE("10115", "withdraw_balance_unfreeze", "提现失败", "提现失败", BALANCE, UNFREEZE), // 提现
	WITHDRAW_CASH_ADD("10101", "withdraw_cash_add", "提现", "提现", CASH, ADD), // 提现

	//USE_CASH_HONGBAO 102
	USE_CASH_HONGBAO_BALANCE_ADD("10211", "use_cash_hongbao_balance_add","现金红包","现金红包", BALANCE, ADD), // 使用现金红包

	//RECHARGE 103
	RECHARGE_CASH_SUBTRACT("10302","recharge_cash_subtract","充值", "充值",CASH,SUBTRACT),//充值
	RECHARGE_BALANCE_ADD("10311","recharge_balance_add","充值", "充值",BALANCE,ADD),//充值

	//----------------------------------------投资 investment
	//INVEST 201
	INVEST_CASH_SUBTRACT("20102", "invest_cash_subtract", "购买畜牧", "领养", CASH, SUBTRACT), // 购买畜牧,现金支付
	INVEST_BALANCE_SUBTRACT("20112", "invest_balance_subtract", "购买畜牧", "领养", BALANCE, SUBTRACT), // 购买畜牧,余额支付
	INVEST_BALANCE_FREEZE("20116", "invest_balance_freeze", "购买畜牧", "领养", BALANCE, FREEZE), // 购买畜牧,余额冻结
	INVEST_BALANCE_FROZEN_SUBTRACT("20114", "invest_balance_frozen_subtract", "购买畜牧", "领养", BALANCE, FROZEN_SUBTRACT), // 购买成功,减冻结金额
	INVEST_CREDIT_ADD("20121", "invest_credit_add", "购买畜牧", "领养", CREDIT, ADD),//购买畜牧,授信增加

	//INVEST_CLOSE 202
	INVEST_CLOSE_BALANCE_UNFREEZE("20215", "invest_close_balance_unfreeze", "取消购买畜牧", "领养订单取消", BALANCE, UNFREEZE), // 取消购买畜牧,余额解冻
	//INVEST_REFUND 203
	INVEST_REFUND_BALANCE_ADD("20311", "invest_refund_balance_add", "畜牧退单", "领养订单退单", BALANCE, ADD), // 畜牧退单,退款 !没有此业务

	//INVEST_BUYBACK_INTEREST 204
	INVEST_BUYBACK_INTEREST_CREDIT_SUBTRACT("20422", "invest_buyback_interest_credit_subtract", "利息兑付", "出售(授信减少)", CREDIT, SUBTRACT), // 利息兑付,授信减少
	INVEST_BUYBACK_INTEREST_BALANCE_ADD("20411", "invest_buyback_interest_balance_add", "利息兑付", "出售(冻结利润)", BALANCE, ADD), // 利息兑付,余额增加

	INVEST_GIVEOUT_INTEREST_CREDIT_SUBTRACT("20425", "invest_giveout_interest_credit_subtract", "vip利息发放", "vip利息发放(授信减少)", CREDIT, SUBTRACT), // 利息兑付,授信减少
	INVEST_GIVEOUT_INTEREST_BALANCE_ADD("20415", "invest_giveout_interest_balance_add", "vip利息发放", "vip利息发放(余额增加)", BALANCE, ADD), // 利息兑付,余额增加

	//INVEST_BUYBACK_PRINCIPAL 205
	INVEST_BUYBACK_PRINCIPAL_BALANCE_ADD("20511", "invest_buyback_principal_balance_add", "本金兑付", "出售(出售款项)", BALANCE, ADD), // 赎回本金,增加余额

	//----------------------------------------商城 t_goods_order
	//GOODSORDER 301
	GOODSORDER_CASH_SUBTRACT("30102", "goodsorder_cash_subtract", "购买商品", "购买", CASH, SUBTRACT), // 购买畜牧,现金支付
	GOODSORDER_BALANCE_SUBTRACT("30112", "goodsorder_balance_subtract", "购买商品", "购买", BALANCE, SUBTRACT),//购买商品,余额支付
	GOODSORDER_BALANCE_FREEZE("30116", "goodsorder_balance_freeze", "购买商品", "购买", BALANCE, FREEZE),//购买商品,冻结余额
	GOODSORDER_BALANCE_FROZEN_SUBTRACT("30114", "goodsorder_balance_frozen_subtract", "购买商品", "购买", BALANCE, FROZEN_SUBTRACT), // 购买成功,减冻结金额
	GOODSORDER_CREDIT_SUBTRACT("30122", "goodsorder_credit_subtract", "购买商品", "购买", CREDIT, SUBTRACT),
	GOODSORDER_CREDIT_FREEZE("30126", "goodsorder_credit_freeze", "购买商品", "购买", CREDIT, FREEZE),
	GOODSORDER_CREDIT_FROZEN_SUBTRACT("30124", "goodsorder_credit_frozen_subtract", "购买商品", "购买", CREDIT, FROZEN_SUBTRACT), // 购买成功,减授信

	//GOODSORDER_CLOSE 302
	GOODSORDER_CLOSE_BALANCE_UNFREEZE("30215", "goodsorder_close_balance_unfreeze", "关闭商品订单", "商城订单取消", BALANCE, UNFREEZE),
	GOODSORDER_CLOSE_CREDIT_UNFREEZE("30225", "goodsorder_close_credit_unfreeze", "关闭商品订单", "商城订单取消", CREDIT, UNFREEZE),

	//GOODSORDER_REFUND 303
	GOODSORDER_REFUND_CASH_ADD("30301", "goodsorder_refund_cash_add", "商品退单", "商城订单退款", CASH, ADD),
	GOODSORDER_REFUND_BALANCE_ADD("30311", "goodsorder_refund_balance_add", "商品退单", "商城订单退款", BALANCE, ADD),
	GOODSORDER_REFUND_CREDIT_ADD("30321", "goodsorder_refund_credit_add", "商品退单", "商城订单退款", CREDIT, ADD),
	
	// VIP dividend vip用户利息发放
	VIPDIVIDEND_CREDIT_SUBTRACT("40122", "vip_dividend_credit_subtract", "发放授信减少", "VIP冻结减少", CREDIT, SUBTRACT),//发放授信减少
	VIPDIVIDEND_BALANCE_ADD("40111", "vip_dividend_balance_add", "授信发放余额增加", "VIP冻结发放", BALANCE, ADD),//发放余额增加

	MIGRATION_BALANCE_ADD("40211", "migration_balance_add", "迁移用户,余额迁移", "迁移用户,余额迁移", BALANCE, ADD),
	MIGRATION_INTEREST_BALANCE_ADD("40311", "migration_interest_balance_add", "迁移用户,回款利息", "迁移用户,回款利息", BALANCE, ADD),
	MIGRATION_PRINCIPAL_BALANCE_ADD("40411", "migration_principal_balance_add", "迁移用户,回款本金", "迁移用户,回款本金", BALANCE, ADD),
	;

	/**
	 * 类型
	 */
	private String featureType;
	/**
	 * 名称(数据库featrues存储名)
	 */
	private String featureName;
	/**
	 * 描叙
	 */
	private String description;

	/**
	 *  app 描叙
	 */
	private String appDescription;


	/**
	 * 账户 余额,信用额度
	 */
	private AccountTypeEnum accountType;

	/**
	 * 账户操作类型
	 */
	private AccountOperateTypeEnum accountOperateType;

	/**
	 * 初始化
	 *
	 * @param featureType
	 * @param featureName
	 * @param description
	 */
	AccountOperateEnum(String featureType, String featureName, String description, String appDescription, AccountTypeEnum accountType,
			AccountOperateTypeEnum accountOperateType) {
		this.featureType = featureType;
		this.featureName = featureName;
		this.description = description;
		this.appDescription = appDescription;
		this.accountType = accountType;
		this.accountOperateType = accountOperateType;

	}

	AccountOperateEnum(String featureType, String featureName, String description, AccountTypeEnum accountType,
					   AccountOperateTypeEnum accountOperateType) {
		this.featureType = featureType;
		this.featureName = featureName;
		this.description = description;
		this.accountType = accountType;
		this.accountOperateType = accountOperateType;

	}

	public static AccountOperateEnum getValueByType(String featureType) {
		for (AccountOperateEnum enums : values()) {
			if (enums.getFeatureType() == featureType) {
				return enums;
			}
		}
		return null;
	}

	public static AccountOperateEnum getValueByName(String featureName) {
		for (AccountOperateEnum enums : values()) {
			// 不区分大小写返回
			if (enums.getFeatureName().equalsIgnoreCase(featureName)) {
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

	public String getAppDescription() {
		return appDescription;
	}

	public AccountTypeEnum getAccountType() {
		return accountType;
	}

	public AccountOperateTypeEnum getAccountOperateType() {
		return accountOperateType;
	}

	public String show(Double amount) {
		return this.description + " " + accountType.getDescription() + " " + accountOperateType.getSignSymbolStr()
				+ amount;
	}
}
