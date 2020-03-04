package com.goochou.p2b.constant.pay;

/**
 * @author shuys
 * @since 2019/5/22 10:04
 */
public interface ChannelConstants {

    // 返回用户支付方式 map 对象 key 常量
    String CHANNEL_KEY = "code";
    String CHANNEL_NAME = "name";
    String CHANNEL_URL = "url";
    String CHANNEL_CHOOSE = "choose";
    String CHANNEL_ALLOW_CHOOSE = "allow";
    String CHANNEL_JUMP_TEXT = "jumpText";
    String CHANNEL_JUMP_KEY = "jumpKey";
    String CHANNEL_JUMP_URL = "jumpUrl";
    String CHANNEL_JUMP_TEXT_COLOR = "jumpTextColor";

    // 支付渠道 类型 0充值1提现
    Integer PAY_TUNNEL_TYPE_0 = 0;
    Integer PAY_TUNNEL_TYPE_1 = 1;

    // 支付渠道 状态 0激活1屏蔽
    Integer PAY_TUNNEL_STATUS_0 = 0;
    Integer PAY_TUNNEL_STATUS_1 = 1;



}
