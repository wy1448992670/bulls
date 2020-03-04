package com.goochou.p2b.constant.pay;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 * Created on 2014年8月25日
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [支付返回对象抽象基类]</p>
 * <p>Copyright:   Copyright (c) 2011</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [杜成] [196168@qq.com]
 * @version        1.0
 */
public abstract class BasePayBack
{
    
    public BasePayBack(){}

    public abstract String getOutPayId();
    
    public abstract String getAmount();
    
    public abstract String getBackInfo();
    
    public abstract String getStatus();
    
    public abstract String getStatusName();

    public abstract String getSign();
    
    /*
     * 支付结果描述
     */
    public abstract String getResultDesc();
    
    /*
     * 订单附加消息 
     */
    public abstract String getAdditionalInfo();
    
    /*
     * 支付完成时间
     */
    public abstract String getSuccTime();
    
    /*
     * 宝付认证api回调
     */
    public abstract String getDataContent();
    
    /**
     *  Created on 2014-9-3 
     * <p>Discription:[实现回参加密]</p>
     * @author:[叶东平]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @return String .
     */
    public abstract String getInfoCiphertext(String outPayId, String amount,
            String backInfo, String status, String signKey, String resultDesc, String additionalInfo, String succTime,
            String batchNo, String sn, String currency, String accountNo, String mobile, String resCode, String settAmount, String orderNo);
    
    /**
     *  Created on 2014-9-3
     * <p>Discription:[第三方返回金额是否是元，true 代表是， false 代表否]</p>
     * @author:[叶东平]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public abstract Boolean getCent();
    
    /**
     *  Created on 2015-4-1 
     * <p>Discription:[响应通知交易成功]</p>
     * @author:[叶东平]
     * @update:[日期2015-4-1] [叶东平]
     * @return String .
     */
    public abstract String getRes();
    
    /**
     * 
     *  Created on 2015-4-8 
     * <p>Discription:[请求方式:stream, post]</p>
     * @author:[叶东平]
     * @update:[日期2015-4-8] [叶东平]
     * @return String .
     */
    public abstract String getRequestType();
    
    //易联冗余参数
    public abstract String getBatchNo();
    public abstract String getSn();
    public abstract String getCurrency();
    public abstract String getAccountNo();
    public abstract String getMobile();
    public abstract String getResCode();
    public abstract String getSettAmount();
    public abstract String getOrderNo();
    
    public abstract Boolean checkSign(HttpServletRequest request, BaseOutPay baseOutPay);
    
}
