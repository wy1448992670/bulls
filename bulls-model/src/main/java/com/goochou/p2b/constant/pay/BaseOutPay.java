package com.goochou.p2b.constant.pay;

import java.util.Map;
/**
 * 
 * Created on 2014年8月23日
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [外部支付对象基类,非数据库实体类]</p>
 * <p>Copyright:   Copyright (c) 2011</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [杜成] [196168@qq.com]
 * @version        1.0
 */
public abstract class BaseOutPay 
{
    
    /**
     * 平台支付单号
     */
    protected String orderNo;

	/**
     * 支付金额
     */
    protected Double amount;
    /**
     * 页面通知url
     */
    protected String returnUrl;
    /**
     * 服务器通知Url
     */
    protected String adviteUrl;


    
    /**
     * 默认跳转银行编号，根据
     */
    protected String defaultBankNum;
    
    /**
     * 跳转参数
     */
    protected String jumpParams;
    
    /**
     * 跳转地址
     */
    protected String submitUrl;
    
    /**
     * 用户ID
     */
    protected String payUserId;
    
    public String getPayUserId() {
		return payUserId;
	}

	public void setPayUserId(String payUserId) {
		this.payUserId = payUserId;
	}

	/**
     * 费率
     */
    public  abstract Double getRate();

    public String getSubmitUrl()
    {
        return submitUrl;
    }

    public void setSubmitUrl(String submitUrl)
    {
        this.submitUrl = submitUrl;
    }

    public BaseOutPay(){
        
    }
    
    public BaseOutPay( String orderNo, Double amount,
            String returnUrl, String adviteUrl,
            String defaultBankNum, String submitUrl)
    {
        super();
        this.orderNo = orderNo;
        this.amount = amount;
        this.returnUrl = returnUrl;
        this.adviteUrl = adviteUrl;
        this.defaultBankNum = defaultBankNum;
        this.submitUrl = submitUrl;
    }

    /**
     * 
     *  Created on 2014年8月24日 
     * <p>Discription:[要求子类一定要实现生成具体实]</p>
     * @author:[杜成]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @return Object .
     */
    public abstract  BaseOutPay setSubOutPay(String payOutId, Double amount, String bankCode);

    /**
     * 
     *  Created on 2014年8月25日 
     * <p>Discription:[外部支付单号生成器，根据不同平台要求生成]</p>
     * @author:[杜成]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @return String .
     */
    public abstract  String outPayIdFactory();
    
    /**
     * 
     *  Created on 2014年8月23日 
     * <p>Discription:[解密配置文件对应的第三方key]</p>
     * @author:[杜成]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @return String .
     */
    public abstract String getSignKey ();
    
    /**
     * 
     *  Created on 2014年8月24日 
     * <p>Discription:[获取商户号]</p>
     * @author:[杜成]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @return String .
     */
    protected abstract String getMerNo();
    
     /**
      * 
      *  Created on 2014年8月23日 
      * <p>Discription:[得到加密后的签名内容]</p>
      * @author:[杜成]
      * @update:[日期YYYY-MM-DD] [更改人姓名]
      * @return String .
      */
    public  abstract String getInfoCiphertext();
    
    /**
     * 
     *  Created on 2014年8月26日 
     * <p>Discription:[得到子对象对应支付枚举</p>
     * @author:[杜成]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @return OutPayEnum .
     */
    public  abstract OutPayEnum getOutPayEnum();
    /**
     * 
     *  Created on 2014年8月25日 
     * <p>Discription:[得到签名前内容]</p>
     * @author:[杜成]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @return String .
     */
    public  abstract String getSignInfo();
    /**
     *  Created on 2015-3-10 
     * <p>Discription:[三方支付支持直接跳转的获取支持银行参数(储蓄卡)]</p>
     * @author:[叶东平]
     * @update:[日期2015-3-10] [叶东平]
     * @return Map<String,String> .
     */
    public  abstract Map<String,String> getCxkBankMap();
    
    /**
     * 代付银行编码
     * @return
     */
    public  abstract Map<String,String> getWithdrawBankMap();

    public Double getAmount()
    {
        return amount;
    }

    public void setAmount(Double amount)
    {
        this.amount = amount;
    }

    public String getReturnUrl()
    {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl)
    {
        this.returnUrl = returnUrl;
    }

    public String getAdviteUrl()
    {
        return adviteUrl;
    }

    public void setAdviteUrl(String adviteUrl)
    {
        this.adviteUrl = adviteUrl;
    }

    public String getDefaultBankNum()
    {
        return defaultBankNum;
    }

    public void setDefaultBankNum(String defaultBankNum)
    {
        this.defaultBankNum = defaultBankNum;
    }
    
    public String getJumpParams()
    {
        return jumpParams;
    }

    public void setJumpParams(String jumpParams)
    {
        this.jumpParams = jumpParams;
    }
    
    /**
     * 测试跳转
     */
    public String testJumpParams;
    
    public String getTestJumpParams(BaseOutPay baseOutPay)
    {
        return testJumpParams;
    }

    public void setTestJumpParams(String testJumpParams)
    {
        this.testJumpParams = testJumpParams;
    }
    
    public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
    
	public abstract  String getPayBackClassName();
}
