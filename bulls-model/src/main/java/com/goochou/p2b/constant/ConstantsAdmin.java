package com.goochou.p2b.constant;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.goochou.p2b.model.SinaTrade;

public interface ConstantsAdmin {

	//分页设定
    public final static int PAGE_LIMIT2 = 20;
    public final static int PAGE_LIMIT5 = 50;

    //日期，天数，月，周
    public final static int TIME_SLOT_THREE_DAY = 3;
    public final static int TIME_SLOT_FIVE_DAY = 5;
    public final static int TIME_SLOT_FIFTEEN_DAY = 15;
    public final static int TIME_SLOT_TWENTY_DAY = 20;
    public final static int TIME_SLOT_A_WEEK = 7;
    public final static int TIME_SLOT_A_MONTH = 30;

    //
    public final static int BANK_RULE_TRIGGER = 2;


    /**
     * 银行卡 规则类型 ： 排除
     */
    public final static int RULE_TYPE_EXCLUDE = 0;
    /**
     * 银行卡 规则类型 ： 包含
     */
    public final static int RULE_TYPE_INCLUDE = 1;
    /**
     * 银行卡 规则类型 ： 触发
     */
    public final static int RULE_TYPE_TRIGGER = 2;

    //测试环境--------------------------------------------------------------------------------------
    //解绑银行卡URL
//    String UNBIND_CARD_URL = "http://test.xinjucai.com/new-project-1.0/app/user/change_bankCard.html";
//    //解除实名URL
//    String RELASE_AUTHENTICATION_URL = "http://test.xinjucai.com/new-project-1.0/app/user/change_trueName.html";
//    //月账单URL
//    String MONTH_ACCOUNT_LIST_URL = "http://test.xinjucai.com/new-project-1.0/app/user/month_bill.html";
//    //审核页面URL
//    String AUDIT_URL = "http://test.xinjucai.com/new-project-1.0/app/user/submit_success.html";
//    //新增地址URL
//    String EXCHANGE_ADDRESS_URL = "http://test.xinjucai.com/new-project-1.0/app/address/update_address.html";
//    //我的地址URL
//    String MY_ADDRESS_URL = "http://test.xinjucai.com/new-project-1.0/app/address/my_address.html";
//    //解绑说明
//    String UNBIND_DESCRIPTION_URL = "http://test.xinjucai.com/app/service_center/bank.html";
//
//    //债权转让详情下载url
//    String DOWNLOAD_CREDITOR_URL = "http://test.xinjucai.com/app/credit_agreement.html";
//    //债权详情查看url
//    String CREDITOR_DETAIL_URL = "http://test.xinjucai.com/app/credit_guarantee.html";
//    //测试环境图片地址
//    // String ALIBABA_PATH = "https://qmlc.oss-cn-hangzhou.aliyuncs.com/";//测试环境   test.xinjucai.com
//    String ALIBABA_PATH = "http://test.xinjucai.com:8282/";//测试环境   test.xinjucai.com
//    //友盟推送，测试环境
//    boolean PRODUCTION_MODE = false;
//    //友盟推送    Set 'production_mode' to 'false' if it's a test device.
//    //头条加密密匙
//    String KEY_JRTT = "f5adf9a0-a055-4ac7-86b5-f6f6d0b2b4ec";
//    // jedis
//    String JEDIS_URL = "test.xinjucai.com";
//    int JEDIS_PORT = 6379;
//    String JEDIS_PASSWORD = "aiwulin";
//    //红包使用规则    红包兑换规则
//    String HONGBAO_RULE = "http://test.xinjucai.com/app/hongbao/hongbaoUseRule.html";
//    String HONGBAO_REDEEM = "http://test.xinjucai.com/app/hongbao/hongbao_exchange.html";
//    //产品介绍
//    String PRODUCT_INTRO = "http://test.xinjucai.com/app/fixed-invest/product_introduction.html";
//    //投资记录
//    String INVEST_RECORD = "http://test.xinjucai.com/app/fixed-invest/investment_record.html";
//    //安全保障
//    String SAFE_PROTECT = "http://test.xinjucai.com/app/fixed-invest/project_guarantee.html";
//    //产品介绍--活期
//    String PRODUCT_INTRO_HUO = "http://test.xinjucai.com/app/huoqi/product_introduction.html";
//    //投资记录--活期
//    String INVEST_RECORD_HUO = "http://test.xinjucai.com/app/huoqi/investment_record.html";
//    //安全保障--活期
//    String SAFE_PROTECT_HUO = "http://test.xinjucai.com/app/huoqi/project_guarantee.html";
//    //借款协议
//    String BORROW_PROTOCOL = "http://test.xinjucai.com/app/loan_agreement.html";
//    //借款协议
//    String BORROW_PROTOCOL_NEW = "http://static.xinjucai.com/app/contract-loan-agreement.html";
//	//债转协议
//	String BORROW_PROTOCOL_OF_BOND = "http://test.xinjucai.com/app/transfer_agreement_of_bond.html";
//    //新手标  常见问题
//    String NOOB_PROBLEM = "http://test.xinjucai.com/app/fixed-invest/common_problem.html";
//    //佣金结算奖励规则
//    String REWARD_RULE = "http://test.xinjucai.com/app/rewardRule.html";
//    //微信邀请好友注册页面
//    String WX_QRCODE = "http://test.xinjucai.com:8088/wx/exp";
//    //快乐赚接收有效数据的接口
//    String BIND_HAPAY_MAKEMONEY = " http://www.lezhuan.com/reannal.php";
//    //快乐赚加密秘钥
//    String KEY_HAPAY_MAKEMONEY = "f9e93698b92262b0";
//    //活期债权转让协议
//    String BORROW_PROTOCOL_HUO = "http://test.xinjucai.com/app/transfer_agreement.html";
//    //超级投资人ID
//    Integer SUPER_ID = 57894;
//    //媒体报道  测试环境地址
//    String NEWS_URL = "http://test.xinjucai.com:8088/news?id=";
//    //运营数据
//    String OPERATION_URL = "http://test.xinjucai.com/app/operation_data.html";
//    //vip
//    String VIP_URL = "http://test.xinjucai.com/app/vipUser.html";
//    //返利么加密秘钥
//    String KEY_REBATE = "UsefzA2B3Keb4";
//    //权限IDs
//    Integer[] AUTHORITY_IDS = {1, 39, 40};
//    //新浪wap充值返回
//    String SINA_WAP_RECHARGE = "http://test.xinjucai.com:9999/pages/capital/recharge_result.html";
//    //新浪wap提现返回
//    String SINA_WAP_WITHDRAW = "http://test.xinjucai.com:9999/pages/capital/withdraw_record.html";
//    //新浪wapreturn
//    String SINA_WAP_RETURN = "http://test.xinjucai.com:9999/pages/capital/sinaReturn.html";
//
//    //-----5.2.0   新增
//    //安鑫赚  安鑫赚列表  什么是安鑫赚
//    String JUCAIBAO_PROBLEM = "http://test.xinjucai.com/app/jucaibao/jucaibao_index.html";
//    //安鑫赚  产品介绍
//    String JUCAIBAO_PRODUCT = "http://test.xinjucai.com/app/jucaibao/jucaibao_detail.html";
//    //安鑫赚  居间服务协议
//    String JUCAIBAO_JUJIAN = "http://test.xinjucai.com/app/jucaibao/middleman_agreement.html";
//    //安鑫赚  周期标协议
//    String JUCAIBAO_PROJECT = "http://test.xinjucai.com/app/jucaibao/loan_agreement.html";
//    //安鑫赚  周期标债转协议
//    String JUCAIBAO_CREDITOR = "http://test.xinjucai.com/app/jucaibao/transfer_agreement.html";
//
//    //呼叫系统的URL，USER_ID，COMPANYCODE
//    String IMPORTCUSTOMERS_URL = "";
//    Long IMPORTCUSTOMERS_USERID = 1231l;
//    String IMPORTCUSTOMERS_COMPANYCODE = "001231";
//    String QUERYUSERNOS_COMPANYCODE = "";
//    //6.0.0
//    //产品介绍
//    String NEW_PRODUCT_INTRO = "http://test.xinjucai.com/new-project-1.0/app/new-project/fixed_detail.html";
//    //安鑫赚  产品介绍
//    String NEW_JUCAIBAO_PRODUCT = "http://test.xinjucai.com/new-project-1.0/app/jucaibao/jucaibao_detail.html";
//    String NEW_JUCAIBAO_PROBLEM = "http://test.xinjucai.com/new-project-1.0/app/jucaibao/jucaibao_index.html";
//    String NEW_HONGBAO_RULE = "http://test.xinjucai.com//new-project-1.0/app/hongbao/hongbaoUseRule.html";
//    String NEW_REWARD_RULE = "http://test.xinjucai.com/new-project-1.0/app/rewardRule.html";
//    String NEW_HONGBAO_REDEEM = "http://test.xinjucai.com/new-project-1.0/app/hongbao/hongbao_exchange.html";
//    //产品介绍--活期
//    String NEW_PRODUCT_INTRO_HUO = "http://test.xinjucai.com/new-project-1.0/app/huoqi/product_introduction.html";
//    //安全保障--活期
//    String NEW_SAFE_PROTECT_HUO = "http://test.xinjucai.com/new-project-1.0/app/huoqi/project_guarantee.html";
//    String NEW_VIP_URL = "http://test.xinjucai.com/new-project-1.0/app/vipUser.html";
//    String CERTIFICATE_URL = "http://test.xinjucai.com/app/certificate.html";
//    //6.2.0
//    //投资成功--定期|债转|活期
//    String INVEST_RESULT_URL = "http://test.xinjucai.com:9999/pages/capital/investResult.html";

    //生产环境----------------------------------------------------------------------------------------
    //解绑银行卡URL
    String UNBIND_CARD_URL = "http://static.xinjucai.com/new-project-1.0/app/user/change_bankCard.html";
    //解除实名URL
    String RELASE_AUTHENTICATION_URL = "http://static.xinjucai.com/new-project-1.0/app/user/change_trueName.html";
    //月账单URL
    String MONTH_ACCOUNT_LIST_URL = "http://static.xinjucai.com/new-project-1.0/app/user/month_bill.html";
    //审核页面URL
    String AUDIT_URL = "http://static.xinjucai.com/app/user/submit_success.html";
    //新增地址URL
    String EXCHANGE_ADDRESS_URL = "http://static.xinjucai.com/new-project-1.0/app/address/update_address.html";
    //我的地址URL
    String MY_ADDRESS_URL = "http://static.xinjucai.com/new-project-1.0/app/address/my_address.html";
    //解绑说明
    String UNBIND_DESCRIPTION_URL = "http://static.xinjucai.com/app/service_center/bank.html";

    /*****************
     * 项目管理
     *******************/
    //债权转让详情下载url
    String DOWNLOAD_CREDITOR_URL = "/app/credit_agreement.html";
    //债权详情查看url
    String CREDITOR_DETAIL_URL = "http://static.xinjucai.com/app/credit_guarantee.html";
    //友盟推送    Set 'production_mode' to 'false' if it's a test device.
    boolean PRODUCTION_MODE = true;
    //图片链接地址  2016-06-13启用
    String ALIBABA_PATH = "https://qmlc.oss-cn-hangzhou.aliyuncs.com/";  // String ALIBABA_PATH ="http://qmlic.oss-cn-hangzhou.aliyuncs.com/";  //原正式环境地址
    // jedis
    String JEDIS_URL = "10.139.53.209";
    int JEDIS_PORT = 6379;
    String JEDIS_PASSWORD = "Xinjucai123";
    //红包使用规则    红包兑换规则
    String HONGBAO_RULE = "http://static.xinjucai.com/app/hongbao/hongbaoUseRule.html";
    String HONGBAO_REDEEM = "http://static.xinjucai.com/app/hongbao/hongbao_exchange.html";
    //奖励规则链接地址
    String REWARD_RULE = "http://static.xinjucai.com/app/rewardRule.html";
    //产品介绍
    String PRODUCT_INTRO = "http://static.xinjucai.com/app/fixed-invest/product_introduction.html";
    //投资记录
    String INVEST_RECORD = "http://static.xinjucai.com/app/fixed-invest/investment_record.html";
    //安全保障
    String SAFE_PROTECT = "http://static.xinjucai.com/app/fixed-invest/project_guarantee.html";
    //借款协议
    String BORROW_PROTOCOL = "/app/loan_agreement.html";
    String BORROW_PROTOCOL_OLD = "/app/loan_agreement_old.html";
    //借款协议
    String BORROW_PROTOCOL_NEW = "http://static.xinjucai.com/app/contract-loan-agreement.html";
    //债转协议
    String BORROW_PROTOCOL_OF_BOND = "http://static.xinjucai.com/app/transfer_agreement_of_bond.html";

    //债转协议 2018/07/03
    String CREDIT_AGREEMENT = "/app/credit_agreement.html";

    String CREDIT_AGREEMENT_OLD = "/app/credit_agreement_old.html";

    //智投债转协议
    String CREDIT_AGREEMENT_FOR_MONTHLY_GAIN = "/app/credit_agreement_for_monthly_gain.html";
    String CREDIT_AGREEMENT_OLD_FOR_MONTHLY_GAIN = "/app/credit_agreement_old_for_monthly_gain.html";
    String BORROW_PROTOCOL_FOR_MONTHLY_GAIN = "/app/loan_agreement_of_mothly_gain.html";


    //未生成合同提示页面
    String NOT_GENERATED_PROMPT = "/app/not_generated_prompt.html";

    //邀请好友链接
    String WX_QRCODE = "http://m.xinjucai.com/wx/exp";
    //新手标  常见问题
    String NOOB_PROBLEM = "http://static.xinjucai.com/app/fixed-invest/common_problem.html";
    //快乐赚接收有效数据的接口
    String BIND_HAPAY_MAKEMONEY = " http://www.lezhuan.com/reannal.php";
    //快乐赚加密秘钥
    String KEY_HAPAY_MAKEMONEY = "e51dc76f22100771";
    //头条加密密匙
    String KEY_JRTT = "c00e8c6f-f44b-4ba6-a2ce-491a84fdb985";
    //产品介绍--活期
    String PRODUCT_INTRO_HUO = "http://static.xinjucai.com/app/huoqi/product_introduction.html";
    //投资记录--活期
    String INVEST_RECORD_HUO = "http://static.xinjucai.com/app/huoqi/investment_record.html";
    //安全保障--活期
    String SAFE_PROTECT_HUO = "http://static.xinjucai.com/app/huoqi/project_guarantee.html";
    //活期债权转让协议
    String BORROW_PROTOCOL_HUO = "http://static.xinjucai.com/app/transfer_agreement.html";
    //超级投资人ID
    Integer SUPER_ID = 57894;
    //返利么加密秘钥
    String KEY_REBATE = "UsefzA2B3Keb4";
    //媒体报道  测试环境地址
    String NEWS_URL = "http://m.xinjucai.com/news?id=";
    //运营数据
    String OPERATION_URL = "http://static.xinjucai.com/app/operation_data.html";
    //vip
    String VIP_URL = "http://static.xinjucai.com/app/vipUser.html";
    //  导出手机号，姓名等权限IDs
    Integer[] AUTHORITY_IDS = {1};
    String SINA_WAP_RECHARGE = "http://m2.xinjucai.com/pages/capital/recharge_result.html";
    //新浪wap提现返回
    String SINA_WAP_WITHDRAW = "http://m2.xinjucai.com/pages/capital/withdraw_record.html";
    //新浪wapreturn
    String SINA_WAP_RETURN = "http://m2.xinjucai.com/pages/capital/sinaReturn.html";

    //-----5.2.0   新增
    //安鑫赚  安鑫赚列表  什么是聚财宝?
    String JUCAIBAO_PROBLEM = "http://static.xinjucai.com/app/jucaibao/jucaibao_index.html";
    //安鑫赚  产品介绍
    String JUCAIBAO_PRODUCT = "http://static.xinjucai.com/app/jucaibao/jucaibao_detail.html";
    //安鑫赚  居间服务协议
    String JUCAIBAO_JUJIAN = "http://static.xinjucai.com/app/jucaibao/middleman_agreement.html";
    //安鑫赚  周期标协议
    String JUCAIBAO_PROJECT = "http://static.xinjucai.com/app/jucaibao/loan_agreement.html";
    //安鑫赚  周期标债转协议
    String JUCAIBAO_CREDITOR = "http://static.xinjucai.com/app/jucaibao/transfer_agreement.html";
    String IMPORTCUSTOMERS_URL = "http://wenguan.3322.org:9091/backend/importCustomers";
    Long IMPORTCUSTOMERS_USERID = 10013l;
    String IMPORTCUSTOMERS_COMPANYCODE = "001510";
    String QUERYUSERNOS_COMPANYCODE = "http://wenguan.3322.org:9091/backend/queryUserNos";
    //    6.0.0
//    产品介绍
    String NEW_PRODUCT_INTRO = "http://static.xinjucai.com/new-project-1.0/app/new-project/fixed_detail.html";
    //安鑫赚  产品介绍
    String NEW_JUCAIBAO_PRODUCT = "http://static.xinjucai.com/new-project-1.0/app/jucaibao/jucaibao_detail.html";
    String NEW_JUCAIBAO_PROBLEM = "http://static.xinjucai.com/new-project-1.0/app/jucaibao/jucaibao_index.html";
    String NEW_HONGBAO_RULE = "http://static.xinjucai.com//new-project-1.0/app/hongbao/hongbaoUseRule.html";
    String NEW_REWARD_RULE = "http://static.xinjucai.com/new-project-1.0/app/rewardRule.html";
    String NEW_HONGBAO_REDEEM = "http://static.xinjucai.com/new-project-1.0/app/hongbao/hongbao_exchange.html";
    //产品介绍--活期
    String NEW_PRODUCT_INTRO_HUO = "http://static.xinjucai.com/new-project-1.0/app/huoqi/product_introduction.html";
    //安全保障--活期
    String NEW_SAFE_PROTECT_HUO = "http://static.xinjucai.com/new-project-1.0/app/huoqi/project_guarantee.html";
    String NEW_VIP_URL = "http://static.xinjucai.com/new-project-1.0/app/vipUser.html";
    String CERTIFICATE_URL = "http://static.xinjucai.com/app/certificate.html";
    //6.2.0
    //投资成功--定期|债转|活期
    String INVEST_RESULT_URL = "http://m2.xinjucai.com/pages/capital/investResult.html";

    //app 6.2.1 新链接
    // 定期详情——投资记录、产品介绍
    String NEW_PRODUCT_INTRO_V621 = "/pages/common/dingqi/detail.html";
    //投资成功
    String NEW_INVEST_RESULT_V621 = "/pages/common/investResult.html";
    //优惠券详情（投资红包、现金红包、加息券、体验金）
    String NEW_COUPON_DETAIL_V621 = "/pages/common/dingqi/couponDetail.html";
    //债权转让详情——转让详情、加入记录、回款记录
    String NEW_CREDITOR_DETAIL_V621 = "/pages/common/zhaizhuan/detail.html";
    //债转规则说明
    String NEW_CREDITOR_RULE_V621 = "/pages/common/zhaizhuan/zhaizhuanRule.html";
    //签到说明
    String NEW_SIGNED_RULE_V621 = "/pages/common/home/signRule.html";
    // 注册成功页面
    String REGISTER_SUCCESS = "/pages/user/registerSuccess.html";


    String MAX_PICTURE_SIZE = "max_picture_size"; // 最大图片大小
    String MAX_VIDEO_SIZE = "max_video_size"; // 最大图片大小
    String USER_PICTURE_PATH = "user_picture_path"; // 用户头像图片存储路径
    String PROJECT_PICTURE_PATH = "project_picture_path"; // 项目图片存储路径
    String PROJECT_VIDEO_PATH = "project_video_path"; // 牛只视频路径
    String PROJECT_VIDEO_COVER_PATH = "project_video_cover_path"; // 牛只视频路径
    String PROJECT_PICTURE_RENBAO_PATH = "project_picture_renbao_path"; // 项目人保水印图片存储路径
    String BANNER_PICTURE_PATH = "banner_picture_path"; // banner图片存储路径
    String SKIN_PICTURE_PATH = "skin_picture_path"; // skin图片存储路径
    String ENTERPRISE_PICTURE_PATH = "enterprise_picture_path"; // 企业图片存储路径
    String GUARANTEE_PICTURE_PATH = "guarantee_picture_path"; // 担保机构图片存储路径
    String FEEDBACK_PICTURE_PATH = "feedback_picture_path"; // 用户反馈图片存储路径
    String ACTIVITY_PICTURE_PATH = "activity_picture_path"; // 活动图片存储路径
    String ICON_PICTURE_PATH = "icon_picture_path"; // ICON图片存储路径
    String SPLASH_PICTURE_SCREEN = "splash_picture_screen"; // 闪屏图片或者安全保障图片存储路径
    String NEWS_PICTURE_PATH = "news_picture_path"; // 媒体报道
    String GOODS_PICTURE_PATH = "goods_picture_path"; // 商城图片
    String GOODS_COMMENT_PATH = "goods_comment_picture_path"; // 评论图片
    String SHARE_PICTURE_PATH = "share_picture_path"; // 分享图片
    String LIFE_PICTURE_PATH = "life_picture_path"; // 牛只生活照
    String ADVERTISEMENT_CHANNEL_PATH = "advertisement_channel_path"; // 渠道推广顶部图片
    String RECHARGE_VOUCHER_PICTURE_PATH = "recharge_voucher_picture_path"; //线下转账凭证图片
    
    String GOODS_CATEGORY_PATH = "goods_category_path"; //商品分类上传banner图片
    String PICTURE_TYPE = "picture_type"; // 支持的图片类型
    String VIDEO_TYPE = "video_type"; // 支持的视频类型
    String STATUS = "status";
    String SUCCESS = "success";
    String ERROR = "error";
    String MESSAGE = "message";
    String PATH = "path";
    String PICTURE_PATH = "picture_path";
    String ABSOLUTE_PATH = "absolute_path";
    String BASE_PATH = "base_path";
    String ID = "id";
    Double SIGNED_AMOUNT = 500d;//签到奖励总金额
    // ---------提现
    String W_SUCCESS = "success"; // 提现成功
    String W_FAILED = "failed"; // 提现失败
    String W_PENDING = "pending"; // 挂起，状态未知

    String W_HANDLING = "handling"; // 已接受，银行处理中
    // --------通道
    String T_YEEPAY = "yibao"; // 易宝
    String T_LIANLIAN = "lianlian"; // 连连
    String T_BAOFOO = "baofoo"; // 宝付
    String T_SINA = "sina"; // 新浪
    String T_FUIOU = "fuiou"; //富有
    //yes or no
    Integer NO = 0;

    Integer YES = 1;
    //数字常量
    Integer CONSTANT_ZERO = 0;
    Integer CONSTANT_ONE = 1;
    Integer CONSTANT_TWO = 2;
    Integer CONSTANT_THREE = 3;
    /**
     * 默认加息率
     */
    double RATE_DEFAULT_VALUE = 0.075d;

    float RATE_DEFAULT_VALUE_F = 0.075f;
    double RATE_DEFAULT_OLD_VERSION = 0.08d;

    Integer USER_UNBIND_CARD = 1;                   //解绑银行卡
    Integer USER_RELASE_AUTHENTICATION = 0;          //解除实名

    Integer USER_OPERATION_TYPE_BIND = 0;           //解绑卡记录类型  绑定银行卡
    Integer USER_OPERATION_TYPE_UNBIND = 1;         //解绑卡记录类型  解绑银行卡

    Integer USER_AUTHENTICATION_TYPE_ADD = 0;       //申请实名认证
    Integer USER_AUTHENTICATION_TYPE_RELASE = 1;    //解除实名认证

    Integer USER_STATUS_FAILED = 0;          //失败
    Integer USER_STATUS_SUCCESS = 1;         //成功
    Integer USER_STATUS_PROCESS = 2;         //进行中

    /**
     * 邀请好友返利活动开启时间
     */
    String INVITEFRIENDS_ACTIVE_STARTDATE = "2016-08-01";

    Queue<SinaTrade> sinaTradeQueue = new ConcurrentLinkedQueue<>();

    //排除渠道过来的双倍利息
    String[] codes = {"renrenli", "toujx", "fanlime", "hbzj", "stk", "mjf", "fbb", "jjsd", "yxdf1", "yxdf2",
            "yxdf3", "tmyh", "yxdf4", "yd1", "yhh", "cwyxljc", "ydhz"};
    /*排除status=3的测试用户,第十万个注册用户的id**/
    Integer REGISTER_NUM = 100531;

    //活期债权转让协议
    String COSTRACT_CREATING = "http://static.xinjucai.com/app/result.html";

}
