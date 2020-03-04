package com.goochou.p2b.constant.pay;

import com.goochou.p2b.constant.ConfigHelper;

/**
 * 
 * Created on 2014年8月24日
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [支付常量,系统启动时从配置文件初始化]</p>
 * <p>Copyright:   Copyright (c) 2011</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [杜成] [196168@qq.com]
 * @version        1.0
 */
//TODO   请实现配置文件
public class PayConstants
{   
	/**
     * PC 快捷支付
     */
    public static final String FUIOU_PAY_TYPE = "DIRPAY";
    /**
     * 响应吗：成功
     */
    public static final String RESP_CODE_SUCCESS = "0000";
    /**
     * 响应吗：已支付
     */
    public static final String RESP_CODE_PAIED = "5185";
	
	//平台外部支付接口统一页面回复URL
    public static String RETURN_URL =ConfigHelper.getDefault().getString("return_url");
    //平台外部支付接口统一后台回复URL
    public static String ADVITE_URL =ConfigHelper.getDefault().getString("advite_url");
    
    public static String FUIOU_B2C_MCHNT_CD=ConfigHelper.getDefault().getString("fuiou_b2c_mchnt_cd");
    public static String FUIOU_B2C_MCHNT_KEY=ConfigHelper.getDefault().getString("fuiou_b2c_mchnt_key");
    public static String FUIOU_SUBMIT=ConfigHelper.getDefault().getString("fuiou_submit");
    public static String FUIOU_QUICK_MCHNT_CD=ConfigHelper.getDefault().getString("fuiou_quick_mchnt_cd");
    public static String FUIOU_QUICK_MCHNT_KEY=ConfigHelper.getDefault().getString("fuiou_quick_mchnt_key");
    public static String WITHDRAW_MCHNT_CD=ConfigHelper.getDefault().getString("withdraw_mchnt_cd");
    public static String WITHDRAW_MCHNT_KEY=ConfigHelper.getDefault().getString("withdraw_mchnt_key");
    public static String FUIOU_API=ConfigHelper.getDefault().getString("fuiou.api");
    public static String FUIOU_PUB_KEY=ConfigHelper.getDefault().getString("fuiou_pub_key");
    public static String FUIOU_GATE_QUERY=ConfigHelper.getDefault().getString("fuiou_gate_query");
    public static String FUIOU_WEB_QUICK_PUB_KEY=ConfigHelper.getDefault().getString("fuiou_web_quick_pub_key");
    public static String FUIOU_WITHDRAW_API=ConfigHelper.getDefault().getString("fuiou.withdraw.api");
    
    public final static String METHOD_CREATE_PAY="apipay/orderAction.pay";
    public final static String METHOD_PAY="apipay/payAction.pay";
    public final static String METHOD_MESSAGE="apipay/messageAction.pay";
    public final static String METHOD_QUICK_QUERY_ORDER="findPay/queryOrderId.pay";
    public final static String METHOD_QUERY_ORDER="METHOD_QUERY_ORDER";
    public final static String METHOD_WITHDRAW="METHOD_WITHDRAW";
    public final static String METHOD_BIND_CARD="checkCard/checkCardDebit.pay";
    public final static String METHOD_BIND_CARD_CONFIRM="METHOD_BIND_CARD_CONFIRM";
    public final static String METHOD_BIND_CARD_MESSAGE="METHOD_BIND_CARD_MESSAGE";
    public final static String METHOD_REFUND="METHOD_REFUND";
    
    public static String ALLINPAY_MCHNT_CD=ConfigHelper.getDefault().getString("allinpay_mchnt_cd");
    public static String ALLINPAY_MCHNT_KEY=ConfigHelper.getDefault().getString("allinpay_mchnt_key");
    public static String ALLINPAY_APP_ID=ConfigHelper.getDefault().getString("allinpay_app_id");
    public static String ALLINPAY_API=ConfigHelper.getDefault().getString("allinpay_api");
    public static String ALLINPAY_GATEWAY=ConfigHelper.getDefault().getString("allinpay_gateway");
    //通联代付配置
    public static String ALLINPAY_PROVIDED_API=ConfigHelper.getDefault().getString("allinpay_provided_api");
    public static String ALLINPAY_PROVIDED_MCHNT_CD=ConfigHelper.getDefault().getString("allinpay_provided_mchnt_cd");
    public static String ALLINPAY_PROVIDED_USER=ConfigHelper.getDefault().getString("allinpay_provided_user");
    public static String ALLINPAY_PROVIDED_PWD=ConfigHelper.getDefault().getString("allinpay_provided_pwd");
    public static String ALLINPAY_PROVIDED_PFX_PATH=ConfigHelper.getDefault().getString("allinpay_provided_pfx_path");
    public static String ALLINPAY_PROVIDED_PFX_PWD=ConfigHelper.getDefault().getString("allinpay_provided_pfx_pwd");
    public static String ALLINPAY_PROVIDED_PUB_PATH=ConfigHelper.getDefault().getString("allinpay_provided_pub_path");
    
    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";
    
    //畅捷配置
    public static String CHAN_QUICK_PARTNERID=ConfigHelper.getDefault().getString("chan_quick_partnerid");
    public static String CHAN_MERCHANT_PUBLIC_KEY=ConfigHelper.getDefault().getString("chan_merchant_public_key");
    public static String CHAN_MERCHANT_PRIVATE_KEY=ConfigHelper.getDefault().getString("chan_merchant_private_key");
    public static String CHAN_GATEWAY_URL=ConfigHelper.getDefault().getString("chan_gateway_url");
    public static String CHAN_CJT_PARTNERID=ConfigHelper.getDefault().getString("chan_cjt_partnerid");
    
    //易宝支付配置
    public static String YEEPAY_PARENTMERCHANTNO=ConfigHelper.getDefault().getString("yeepay_parentmerchantno");
    public static String YEEPAY_MERCHANTNO=ConfigHelper.getDefault().getString("yeepay_merchantno");
    public static String YEEPAY_APP_KEY=ConfigHelper.getDefault().getString("yeepay_app_key");
    public static String YEEPAY_PRIVATE_KEY=ConfigHelper.getDefault().getString("yeepay_private_key");
    public static String YEEPAY_PUBLIC_KEY=ConfigHelper.getDefault().getString("yeepay_public_key");
    public static String YEEPAY_TRADEORDER_URI=ConfigHelper.getDefault().getString("yeepay_tradeorder_uri");
    public static String YEEPAY_CASHIER=ConfigHelper.getDefault().getString("yeepay_cashier");
    public static String YEEPAY_TRADEORDER_QUERY=ConfigHelper.getDefault().getString("yeepay_tradeorder_query");
    public static String YEEPAY_TRADEORDER_REFUND=ConfigHelper.getDefault().getString("yeepay_tradeorder_refund");
}
