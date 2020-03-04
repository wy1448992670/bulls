package com.goochou.p2b.model.pay;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fuiou.util.MD5;
import com.goochou.p2b.constant.pay.BaseOutPay;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.constant.pay.PayConstants;
/**
 * 
 * Created on 2014年8月23日
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [汇潮支付交互实体]</p>
 * <p>Copyright:   Copyright (c) 2011</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [杜成] [196168@qq.com]
 * @version        1.0
 */
public class FuiouQuickOutPay extends BaseOutPay implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2871432793915788289L;

	@Override
    public String getPayBackClassName()
    {
        return "com.goochou.p2b.model.pay.FuiouQuickPayBack";
    }
    
    public FuiouQuickOutPay() {
        
    }
    public FuiouQuickOutPay(String orderNo, Double amount, String returnUrl,
            String adviteUrl,  String defaultBankNum, String submitUrl)
    {
        super(orderNo, amount, returnUrl, adviteUrl, defaultBankNum, submitUrl);
    }


    /**
     * 储蓄卡编号集合
     */
    @SuppressWarnings("serial")
    private static Map<String, String> cxkBankMap = new HashMap<String, String>(){{
        
    }};
    
    @Override
    public String getSignKey()
    {
        return PayConstants.FUIOU_QUICK_MCHNT_KEY;
    }

    @Override
    public String getInfoCiphertext()
    {
        return MD5.MD5Encode(this.getSignInfo());
    }
    @Override
    public String getSignInfo()
    {
        return null;
    }
    
    @Override
    public String getJumpParams(){
        return super.jumpParams;
    }
    
    @Override
    public BaseOutPay setSubOutPay(String payOutId, Double amount, String bankCode)
    {
        return null;
    }
    
    /**
     *  Created on 2014-8-27
     * <p>Discription:[模拟支付回调]</p>
     * @author:[叶东平]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public String getTestJumpParams(BaseOutPay baseOutPay){
        return super.jumpParams;
    }


    @Override
    protected String getMerNo()
    {
        return PayConstants.FUIOU_QUICK_MCHNT_CD;
    }

    @Override
    public String outPayIdFactory()
    {
        //return FuiouIdGenerator.getInstance().next();
    	return "";
    }
    @Override
    public OutPayEnum getOutPayEnum()
    {
        return OutPayEnum.FUIOU_QUICK;
    }
    @Override
    public Double getRate()
    {
        return 0.0025;
    }

	@Override
	public Map<String, String> getCxkBankMap() {
		return cxkBankMap;
	}

    /* (non-Javadoc)
     * @see com.goochou.p2b.constant.pay.BaseOutPay#getWithdrawBankMap()
     */
    @Override
    public Map<String, String> getWithdrawBankMap() {
        // TODO Auto-generated method stub
         return null;
    }
}
