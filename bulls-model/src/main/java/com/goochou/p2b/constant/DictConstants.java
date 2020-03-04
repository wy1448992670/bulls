package com.goochou.p2b.constant;

public class DictConstants {
    /**
     * 风险提示
     */
    public final static String RISK_PROMPT = "RISK_PROMPT";
    public final static String SHOW_CAPTIMG_PROMPT = "SHOW_CAPTIMG_PROMPT";    //app登录页显示图形验证码开关  yes，no
	/**
	 * 封停次数
	 */
	public final static String KILL_TIMES = "KILL_TIMES";
	/**
	 * 注册强IP校验开关(配合封停次数使用)
	 */
	public final static String REGISTER_IP_CHECK = "REGISTER_IP_CHECK"; //yes，no

	public final static String USER_RELEASE_CARD_COUNT = "USER_RELEASE_CARD_COUNT"; // 每人每天可更换银行卡次数

	public final static String SPLASH_SHOW_TIME = "SPLASH_SHOW_TIME"; //广告时间

	public final static String PAY_WAIT_TIME = "PAY_WAIT_TIME"; //支付等待时间

	public final static String BULLS_SHOW_IOS = "BULLS_SHOW_IOS"; //养牛是否显示IOS  yes，no
	public final static String BULLS_SHOW_ANDROID = "BULLS_SHOW_ANDROID"; //养牛是否显示android  yes，no
	public final static String BULLS_SHOW_WAP = "BULLS_SHOW_WAP"; //养牛是否显示WAP  yes，no

	//短信验证码
	public final static String VALIDATE_CODE = "验证码：{code}。15分钟有效期，仅用于验证手机，请勿告知他人。";
	//认养成功短信
	public final static String SUBMIT_VALIDATE_CODE = "您成功认养一头【{title}】，可在奔富牧业APP我的牧场页面进行查看，订单编号：{orderNo}。饲养利润{interestAmount}元已提前发放至您的账户，在饲养期未结束前您可用于商城消费。";
	//回购成功短信
	public final static String AUTO_BACK_VALIDATE_CODE = "您认养的【{title}】根据协议约定委托售卖完毕，领养期间饲养利润为¥{interestAmount}元（已提前发放），共卖出¥{totalAmount}元，余额到账¥{balanceAmount}元，订单编号：{orderNo}；为答谢您的支持，给您发送{redAmount}元牧场红包，已到账请查收。";
	//发货提醒短信
	public final static String SEND_GOODS_VALIDATE_CODE = "您购买的【{title}】平台已发货，订单编号:{orderNo}可在奔富牧业APP查看物流信息。";
	//饲养期结束
	//public final static String DEADLINE_VALIDATE_CODE = "您认养的【{title}】订单编号为：{orderNo}，协议约定饲养期已结束，您可登录APP【牧场主】-【我的牧场】-【饲养期】进行出售操作。";
	public final static String DEADLINE_VALIDATE_CODE = "您认养的{title}；订单编号为：{orderNo}的牛只饲养期已结束，请前往app进行出售操作；如您未进行出售操作，根据合同约定我们将在1日后帮您进行出售，出售的款项将进入您的账户余额中；感谢您一直以来的信赖与支持，我们会再接再厉给您带来最优质的服务。";
	//牛待付款5分钟之后（营销）
	public final static String BULLS_VALIDATE_CODE = "您即将认养的【{title}】预计饲养利润{interestAmount}元，请前往奔富牧业APP进行付款，{time}分钟后订单将自动取消。订单编号：{orderNo}。退订回T";
	//商品待付款5分钟之后（营销）
	public final static String GOODS_VALIDATE_CODE = "您想要购买的【{title}】，请尽快前往奔富牧场APP进行付款。{time}分钟后订单将自动取消，订单编号：{orderNo}。退订回T";
	//提现审核成功
    public final static String WITHDRAW_SUCCESS_VALIDATE_CODE = "提醒您，您的提现申请【提现单号：{orderNo}】已通过，款项提现至尾号{cardNo}的银行卡，请注意查收，因银行结算存在到账延时情况，以银行结算时间为准。";
    //提现审核拒绝
    public final static String WITHDRAW_REFUSE_VALIDATE_CODE = "【奔富牧业】提醒您，您的提现申请【提现单号：{orderNo}】被驳回，驳回原因：{auditRemark}。如有疑问请致电客服：021-64386337";
    //默认密码短信
  	public final static String DEFAULT_PASSWORD  = "您已成功注册奔富牧业APP，您的账号为：{phone}，默认密码为：{password}，为了您的账户安全，请尽快登录APP修改密码。";
	//唤醒渠道注册用户产生投资短信发送
	public final static String SEND_ADVERTISEMENT_CHANNEL_CODE  = "您有6个月{channelName}VIP会员待领取，点击{channelUrl}任意领养一头牛，即可领取！您账号初始密码：{password}，回复T退订。";
    // vip用户发放利润短信
    public final static String SEND_VIP_PROFIT_CODE  = "尊敬的{realName}您好，您的“VIP冻结发放通道”有最新动态，已发放{amount}，请登录APP进行查看。";
	//邀请首投活动_成功
	public final static String INVITE_FIRST_INVESTMENT_SUCCEED_CODE  = "恭喜您和您的好友各获得50元养牛补贴，请在奔富牧业App我的-优惠券-现金红包中进行查收。邀请越多，赚的越多！继续邀请可点击 {url} ，回复T退订";
	//邀请首投活动_邀请人待领养											
	public final static String INVITE_FIRST_INVESTMENT_WAIT_INVITER_CODE  = "您邀请的好友已认养一头牛，您完成认养任意一头牛，即可与好友各得50元养牛补贴，详情点击 https://wap.bfmuchang.com/#/bulls?qd=1 ，回复T退订";
	//邀请首投活动_被邀人待领养												 
	public final static String INVITE_FIRST_INVESTMENT_WAIT_INVITEE_CODE  = "您的好友已成功领养牛只，您完成认养任意一头牛，即可与好友各得50元养牛补贴，详情点击 {url} ,回复T退订";
		
	public final static String RECHARGE_SHOW_IOS = "RECHARGE_SHOW_IOS"; //充值是否显示IOS  yes，no
	public final static String RECHARGE_SHOW_ANDROID = "RECHARGE_SHOW_ANDROID"; //充值是否显示android  yes，no
	public final static String RECHARGE_SHOW_WAP = "RECHARGE_SHOW_WAP"; //充值是否显示WAP  yes，no
	public final static String NOTICE_SHOW_TIME = "NOTICE_SHOW_TIME"; //公告滚动时间

	public final static String BUY_BULLS_CHECK = "BUY_BULLS_CHECK"; //购牛协议默认勾选  yes，no

	public final static String LY_SHOW = "LY_SHOW"; //领养快讯  yes，no

	public final static String APP_CHANNEL = "APP_CHANNEL"; //特殊渠道上线时屏蔽的设置
	public final static String APP_CHANNEL_BALANCE = "APP_CHANNEL_BALANCE"; //特殊渠道上线时屏蔽金额的设置
	public final static String PARADISE_SHOW = "PARADISE_SHOW";// 是否显示奔富乐园入口 字典KEY
	public final static String DATA_SOURCE = "DATA_SOURCE"; //渠道关闭养牛
	public final static String PINGNIU_PROJECT_SHOW_NUM = "PINGNIU_PROJECT_SHOW_NUM"; //是否显示拼牛   yes，no

	public final static String BULLS_SHOW_IOS_V2_0 = "V2_0:BULLS_SHOW_IOS"; //app v2.0 养牛是否显示IOS  yes，no
	public final static String BULLS_SHOW_ANDROID_V2_0 = "V2_0:BULLS_SHOW_ANDROID"; //app v2.0 养牛是否显示android  yes，no
	public final static String BULLS_SHOW_WAP_V2_0 = "V2_0:BULLS_SHOW_WAP"; //app v2.0 养牛是否显示WAP  yes，no
	public final static String SHOP_SHOW_RECOMMEND_V2_0 = "V2_0:SHOP_SHOW_RECOMMEND"; //app v2.0 首页是否显示商城精选推荐  yes，no
	
}
