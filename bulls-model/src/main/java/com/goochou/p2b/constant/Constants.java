package com.goochou.p2b.constant;

import java.math.BigDecimal;

import com.goochou.p2b.constant.client.ClientConstants;

/**
 * Created on 2014-8-21
 * <p>
 * Title: 上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>
 * Description: [全局常量]
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: 上海橙旗金融线上交易平台
 * </p>
 * <p>
 * Department: 网贷中心开发部
 * </p>
 *
 * @author [叶东平] [58294114@qq.com]
 * @version 1.0
 */
public class Constants {

	/***今日头条*/
	public static final String AD_CODE_TOUTIAO = "7";
	public static final String AD_CODE_TOUTIAO_2 = "26";
	public static final String AD_CODE_TOUTIAO_DESC = "TD";

	//月月盈合同开关  0: 关, 1:开
	public final static Integer YYY_CONTRACT_SWITCH = 0;
	/**
	 * 编码
	 */
	public final static String UTF8 = "utf-8";
	/**
	 * 字典缓存表示cache.get(DICTS);
	 */
	public final static String DICTS = "DICTS";

	/**
	 * 活动详情,缓存名
	 */
	public final static String ACTIVITY_DETAIL_LIST_MAP = "ACTIVITY_DETAIL_LIST_MAP";

	/**
	 * 牧场区域,缓存名
	 */
	public final static String PRAIRIE_AREA_TACTICS_CACHE = "PRAIRIE_AREA_TACTICS_CACHE";

	public final static String INTERFACES = "xjc.interfaces.get";
	/**
	 * DEBUG：调试模式，RELEASE：正式模式
	 */
	public final static String TEST_SWITCH = ConfigHelper.getDefault().getString("test.switch");
	// APP MD5_key
	public final static String APP_MD5_KEY = "51e4dc1269013ccd8f257164a79f465a";

	// 充值开关
	public final static String PAY_SWITCH = "PAY_SWITCH";

	// 记录有效
	public final static Boolean DATE_VALIT = true;
	// 记录无效
	public final static Boolean DATE_NULLITY = false;

	public final static String REQUEST_POST = "REQUEST_POST";
	public final static String REQUEST_STREAM = "REQUEST_STREAM";

	/**
	 * 接口ResultCode 成功标识
	 */
	public final static String SUCCESS = "1";

	/**
	 * 接口ResultCode 失败标识
	 */
	public final static String FAIL = "0";
	public final static String WEB = "WEB";

	public final static String TASK_TABLE_NAME_PREFIX = "tk_plan_wait";

	public final static String PROJECT_ACITIVITY_MESSAGE_TITLE = "三周年加息抽奖";

	/**
	 * 发起债转显示预计收益开关
	 */
	public final static String PREAMOUNT_SWITCH = "0";
	/***
	 * 注册送18888 体验金+888红包（2018年2月1日-2018年12月31日） 活动开始时间
	 */
	public final static String ACTIVITY_START_DATE = "2018-02-01";

	/***
	 * 风险评估有效天数
	 */
	public final static Integer RISK_EVALUATE_DAY = 180;
	public final static Integer LANDLORD_LEND_MAX_AMOUNT = 200000;

	public final static double FEE_TRANSACT = 0.25;  //平台服务费
	public final static double FEE_ASSETS = 0.01;   //推荐方服务费
	public final static String ISRATE = "0";    //折扣率开关
	public final static double FEE_START_AMOUNT = 100d;  //起投金额
	public final static double FEE_PIECE_AMOUNT = 100d;  //投资，债转每份金额

	public final static String RISK_EVALUATE_URL = "/pages/common/risk_assessment/risk_assessment.html";     //风险评测选择页
	public final static String RISK_EVALUATE_RESULT_URL = "/pages/common/risk_assessment/risk_assessment_result.html";  //风险评测结果页
	public final static String RISK_PROJECT_ASSET = "/pages/common/assetGrade.html";  //定期详情页增加资产等级说明
	public final static String BORROW_PROTOCOL = "/pages/common/loan_agreement.html"; //借款协议
	public final static String NET_BORROW_PROTOCOL = "/pages/common/prompt_agreement.html";     //网络借贷风险和禁止性行为提示书》《资金来源合法承诺书》
	public final static String INFORMATION_DIS = "/pages/common/informationDis.html";     //信息披露专栏
	public final static String INSURANCE_URL = "/pages/capital/insurance.html";     //信息披露（新的）
	public final static String SAFETY_URL = "/pages/common/dingqi/detail.html";     //安全保障（新的）
	public final static String UNTIEBANK_URL = "/pages/user/untieBank.html";     //解绑银行卡
	public final static String REGISTER_SUCCESS = "/pages/user/registerSuccess_app.html"; // 注册成功页面
	public final static String ONEBEAT_URL = "/app/oneBeat.html";    // 抢标夺券
	public final static String CREDITOR_RULE = "/app/tranfer_rules.html";    //债转规则说明
	public final static String REGISTER_SUCCESS_H5 = "/pages/user/registerSuccess.html"; // 注册成功页面
	public final static String STATIC_SERVICES_AGREEMENT = "/app/service_newagreement.html";
	public final static String TOTAL_ASSETS_URL = "/pages/user/total_assets.html"; // 总资产&累计收益h5 页面

	public final static String STATIC_SERVICES_RISK_WARNING = "/app/risk_newwarning.html";


	public final static String SHOW_CODE_SWITCH = "1"; //app登录页显示图形验证码开关  0: 关, 1:开
	public final static Integer NEW_CONTRACT_BEGIN_EFFECTIVE_PROJECT_ID = 9999999;
	public final static String CAP_IMG_CODE = "capImgCode"; //图形验证码前缀
	public final static String PROJECT_CLASS_LIST = "PROJECT_CLASS_LIST";    //项目一级列表

	/**
	 * 是
	 */
	public final static String YES = "yes";

	/**
	 * 否
	 */
	public final static String NO = "no";

	/**
	 * 投资送体验金(exp_money => type)
	 */
	public final static Integer EXP_MONEY_TYPE_14 = 14;

	/**
	 * 首页新手广告弹窗cachekey
	 */
	public final static String HOME_USER_ALERT_FLAG_CACHE_KEY_OF_NEW_USER = "home_user_alert_flag_cache_key_of_new_user";

	/**
	 * 首页普通广告弹窗cachekey
	 */
	public final static String HOME_USER_ALERT_FLAG_CACHE_KEY_OF_NORMAL = "home_user_alert_flag_cache_key_of_normal";

	/**
	 * 转让活动让利费率(平台出), 该利率加上转让人管理费利率 * 本金, 给到受让人
	 */
	public final static String PLATFORM_BOND_ACTIVITY_RATE = "PLATFORM_BOND_ACTIVITY_RATE";

	public final static String PUSH_OPEN_NATIVE_URL = "http://static.xinjucai.com/pushOpenNative.html";

	public final static String IS_SHOW_HINT_CACHE_KEY = "IS_SHOW_HINT_CACHE_KEY";

	/**
	 * 活转定活动投资期限对应返还红包比例
	 */
	public final static String CURRENT_TRANSFER_REGULAR_SCALE = "CURRENT_TRANSFER_REGULAR_SCALE";

	/**
	 * 新的首投奖励梯度规则
	 */
	public final static String FIRST_CAST_REWARD_RULE ="FIRST_CAST_REWARD_RULE";

	/**
	 * 新提成奖励梯度规则
	 */
	public final static String COMMISION_REWARD_RULE ="COMMISION_REWARD_RULE";

//	public final static String RAISE_FINISHED_MAIL_SEND_TARGET = "xueqi@xinjucai.com;qianjiawen@xinjucai.com;xueyanhui@xinjucai.com";

	public final static String RAISE_FINISHED_MAIL_SEND_TARGET = "liuyan@xinjucai.com;zhaoxiaoyan@xinjucai.com;yedongping@xinjucai.com";

//	public final static String RAISE_FINISHED_MAIL_SEND_TARGET = "lixianfeng@xinjucai.com;li_xianfeng@vip.sina.com;qianjiawen@xinjucai.com";

	/**
	 * 客服电话
	 */
	public final static String SERVICE_TEL = "4001688082";

	/**
	 * 客服电话
	 */
	public final static String SERVICE_TEL_STR = "400-168-8082";

	/**
	 * 充值页面显示的图片
	 */
	public final static String RECHARGE_IMG = "https://qmlc.oss-cn-hangzhou.aliyuncs.com/images/app/rechargePrompt.png";

	/**
	 * 分享的图片
	 */
	public final static String SHARE_IMG = "https://qmlc.oss-cn-hangzhou.aliyuncs.com/images/kuaizhao/sharePic2.jpg";

	/**
	 * 年化利率
	 */
	public final static double ANNUALIZED_INTEREST_RATE = 0.13d;

	/**
	 * 分享conetent
	 */
	public final static String DEFAULT_SHARE_CONTENT = "鑫聚财，专业住房租赁供应链金融服务平台";

	/**
	 * 分享title
	 */
	public final static String QR_TITLE = "88888新手礼包";

	/**
	 * 分享链接
	 */
	public final static String SHARE_LINK_URL = "http://m.xinjucai.com/wx/exp";

	/**
	 * 月月盈退出规则，需要在可退出日的17点前进行操作
	 */
	public final static Integer  PM_17 = 17;


	/**
	 * 散标发打钱开关 0:关 , 1:开
	 */
	public final static String PROJECT_TYPE_0_ADD_LENDER_BALANCE_SWITCH = "PROJECT_TYPE_0_ADD_LENDER_BALANCE_SWITCH";

	/**
	 * 智投发打钱开关 0:关 , 1:开
	 */
	public final static String PROJECT_TYPE_7_ADD_LENDER_BALANCE_SWITCH = "PROJECT_TYPE_7_ADD_LENDER_BALANCE_SWITCH";

	/**
	 * 初秋大礼奖励梯度规则
	 */
	public final static String CHUQIU_REWARD_RULE ="CHUQIU_REWARD_RULE";

	/**
	 * 智投周期奖励比例
	 */
	public final static String MONTHLY_CYCLE_REWARD_RULE = "MONTHLY_CYCLE_REWARD_RULE";

	/**
	 * 停止债权转让
	 */
	public final static String CREDIT_TRANSFER_SWITCH = "CREDIT_TRANSFER_SWITCH";

	/**
	 * 停止债权转让文案
	 */
	public final static String CREDIT_TRANSFER_SWITCH_MSG = "CREDIT_TRANSFER_SWITCH_MSG";

	/**
	 * t+1提现手续费为0.02%
	 */
	public final static String T1_FEE = "T1_fee";

	/**
	 * t+0提现手续费为0.03%
	 */
	public final static String T0_FEE = "T0_fee";

	public final static String HOLIDAY_WITHDRAW_ALERT = "国庆假期第三方支付银行系统结算通道关闭，暂不能提现，10月8日结算通道恢复，具体到账以银行为准，敬请理解！";

	/**
	 * 用户密码拼接字符
	 */
	public final static String PASSWORD_FEX = "xVuUD0FAMKkzYq05";

	/**
	 * 重发短信秒数限制
	 */
	public final static Integer ALLOW_SECOUND = 60;

	/**
	 * 风险提示
	 */
	public final static String FXTS = "风险提示：您在饲养过程中可能面临各种风险，包括不限于市场风险丶信用风险丶操作风险，流动性风险以及战争丶自然灾害等不可抗力导致的收益不稳定性。";
	/**
	 * 收益回报
	 */
	public final static String SYHB = "按饲养周期计算饲养利润，奔富牧业会提前将饲养利润发放至您的平台账户中，饲养周期未结束前，饲养利润金额仅可用于商城消费，饲养周期结束后您可进行出售操作，出售成功您可将全部认养金额（含提前发放的饲养利润）提现至您的银行结算账户中。";

	public final static String PROVICE_CITY = "PROVICE_CITY";
	public final static String AREA_KET = "AREA:LIST:ALL";
	public final static int AREA_EXPIRE = 7 * 24 * 60 * 60; // 七天

	/**
	 * 预支回报账单说明
	 */
	public final static String ADVANCE_DECLARE="剩余可用利润收益：账单根据周期提前预支利润金额，此金额只可用于购买商城物品，卖牛后可用利润自动解冻，到达余额且可提现";
	/**
	 * 预支回报账单注意事项
	 */
	public final static String ADVANCE_NOTICE="*已预支回报发放至账户预支回报中，饲养期满后选择卖牛转化为余额可供提现；成长回报与够牛款，用户选择卖牛后一并发放至余额。";

	public final static String HGGS_CONTENT = "未到最佳屠宰期的牛只或羊只只由内蒙木奔富畜牧业发展有限公司回购，已到最佳屠宰期由第三方屠宰场或食品加工场收购。";

	public final static String REMAINING_AVAILABLE_INCOME="剩余可用利润收益";
	public final static String REMAINING_AVAILABLE_PROFIT="剩余可用利润";

	public final static String USABLE_FREEZE_PROFIT="可用冻结利润";

	public final static String UNFREEZED_PROFIT="可用已解冻利润";
	public final static String ORDER_NO_NAME="编号";
	public final static String UNFREEZE_DESCRIPTION ="天后解冻";

	public final static  String FOSTER_TOTAL_PRICE ="认养总价(元)";

	public final static  String FOSTER_DATE ="认养日期";

	public final static  String REDBAG_DEDUCTION="红包抵扣";

	/**
	 * 首页缓存KEY
	 */
	public final static String BANNERS = "BANNERS";
	public final static String ICONS = "ICONS";

	/**
	 * 待产牛的数量
	 */
	public final static int EXPECTANTCOW_QUANTITY=200;
	public final static int EXPECTANTCOW_QUANTITY2=120;
	public final static int EXPECTANTCOW_QUANTITY3=100;
	public final static int EXPECTANTCOW_QUANTITY4=300;

	public final static int FARM2COUNT=480;
	public final static int FARM3COUNT=420;
	public final static int FARM4COUNT=600;


	public final static String SHOPPING_CART_LIST = "user:{userId}_SHOPPING_CART_LIST";
	public final static int SHOPPING_CART_EXPIRE_IN_SECONDS = 48 * 60 * 60; // 购物车缓存失效时间(两天)



//	public final static String IOS_APP_KEY = "5d1179ce4ca357e0de000417";
//	public final static String IOS_APP_KEY = "5d8340c8570df331da000d1d";
//	public final static String IOS_APP_KEY = "5d9dbb410cafb23f62000a51";//奔富牛业2019-10-10
	public final static String IOS_APP_KEY = "5dde54c14ca3578a9b000548";//奔富牧业  2019-11-27
	
//	public final static String IOS_APP_SECRET = "pnz91jxcb0uw47fntfnu6leh7bjjduuh";
//	public final static String IOS_APP_SECRET = "0xscjex0xhfyqrzklnvwrcikiefyyxsd";
//	public final static String IOS_APP_SECRET = "j3s9fdiyqfrlvsfoolwkhzgeohyrl01g";//奔富牛业2019-10-10
	public final static String IOS_APP_SECRET = "fvddpzaekdgojcxj0lhwlqldakve8pwe";//奔富牧业 2019-11-27
	public final static String ANDROID_APP_KEY = "5d117a9b570df322f600054f";
	public final static String ANDROID_APP_SECRET = "4qm3rdcn3n5nqjmmibqpspblf0vonijq";

	
	
	public final static String STATUS_URL = "https://msgapi.umeng.com/api/status";

	public final static String ADVERTISEMENT_CHANNEL_URL = ClientConstants.APP_ROOT+"/user/channel_regist/%s";
	
	
	public final static BigDecimal WITHDRAW_FEE = BigDecimal.valueOf(2);//提现手续费 2元

	public final static int MOBILE_VERIFY_CODE_EXPIRE = 15 * 60; // 手机验证码过期时间，15分钟过期
	public final static String NEW_YEAR_ACTIVITY_GET_CARD = "GET_CARD:%s"; // 新年活动-领取福卡验证key %s=user phone
	public final static String CHANNEL_REGISTER = "CHANNEL_REGISTER:%s"; // 渠道注册验证key %s=user phone


	//腾讯天气数据(内蒙古,呼和浩特)
//	public final static String WEATHER_URL = "https://wis.qq.com/weather/common?source=pc&weather_type=observe%7Cforecast_1h%7Cforecast_24h%7Cindex%7Calarm%7Climit%7Ctips%7Crise&province=%E5%86%85%E8%92%99%E5%8F%A4&city=%E5%91%BC%E5%92%8C%E6%B5%A9%E7%89%B9&county=&callback=jQuery1113009637072332639529_1577347353547&_=1577347353555";
	// 腾讯天气数据(内蒙古,锡林郭勒盟)
	public final static String WEATHER_URL = "https://wis.qq.com/weather/common?source=pc&weather_type=observe%7Cforecast_1h%7Cforecast_24h%7Cindex%7Calarm%7Climit%7Ctips%7Crise&province=%E5%86%85%E8%92%99%E5%8F%A4&city=%E9%94%A1%E6%9E%97%E9%83%AD%E5%8B%92%E7%9B%9F&county=&callback=jQuery1113009637072332639529_1577347353547&_=1577347353555";
	//腾讯天气空气质量(内蒙古,呼和浩特)
//	public final static String AIR_QUALITY_URL = "https://wis.qq.com/weather/common?source=pc&weather_type=air%7Crise&province=%E5%86%85%E8%92%99%E5%8F%A4&city=%E5%91%BC%E5%92%8C%E6%B5%A9%E7%89%B9&callback=jQuery1113009637072332639529_1577347353547&_=1577347353556";
    //腾讯天气空气质量(内蒙古,锡林郭勒盟)
    public final static String AIR_QUALITY_URL = "https://wis.qq.com/weather/common?source=pc&weather_type=air%7Crise&province=%E5%86%85%E8%92%99%E5%8F%A4&city=%E9%94%A1%E6%9E%97%E9%83%AD%E5%8B%92%E7%9B%9F&callback=jQuery1113009637072332639529_1577347353547&_=1577347353556";
	//天气图片
	public final static String WEATHER_PICTURE_URL = "https://mat1.gtimg.com/pingjs/ext2020/weather/pc/icon/weather/day/%s.png";
}
