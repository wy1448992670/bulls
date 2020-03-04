package com.goochou.p2b.model.pay;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fuiou.util.MD5;
import com.goochou.p2b.constant.pay.BankEnum;
import com.goochou.p2b.constant.pay.BaseOutPay;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.constant.pay.PayConstants;
/**
 * 通联支付
 * @author ydp
 * @date 2019/06/25
 */
public class AllinPayOutPay extends BaseOutPay implements Serializable
{

	/**
     *
     */
    private static final long serialVersionUID = -5853583733227605500L;

    @Override
    public String getPayBackClassName()
    {
        return "com.goochou.p2b.model.pay.AllinPayBack";
    }
    
    public AllinPayOutPay() {
        
    }
    public AllinPayOutPay(String orderNo, Double amount, String returnUrl,
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
    
    private static Map<String, String> withdrawBankMap = new HashMap<String, String>(){{
        put(BankEnum.ICBC.getFeatureName(), "0102");
        put(BankEnum.ABC.getFeatureName(), "0103");
        put(BankEnum.BOC.getFeatureName(), "0104");
        put(BankEnum.CCB.getFeatureName(), "0105");
        put(BankEnum.CMB.getFeatureName(), "0308");
        put(BankEnum.SPDB.getFeatureName(), "0310");
        put(BankEnum.CGB.getFeatureName(), "0306");
        put(BankEnum.COMM.getFeatureName(), "0301");
        put(BankEnum.PSBC.getFeatureName(), "0403");
        put(BankEnum.CITIC.getFeatureName(), "0302");
        put(BankEnum.CMBC.getFeatureName(), "0305");
        put(BankEnum.CEB.getFeatureName(), "0303");
        put(BankEnum.HXB.getFeatureName(), "0304");
        put(BankEnum.CIB.getFeatureName(), "0309");
        put(BankEnum.BOS.getFeatureName(), "04012900");
        put(BankEnum.PAB.getFeatureName(), "0307");
        put(BankEnum.BCCB.getFeatureName(), "04031000");
    }};
    
    @Override
    public String getSignKey()
    {
        return PayConstants.ALLINPAY_MCHNT_KEY;
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
        return PayConstants.ALLINPAY_MCHNT_CD;
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
        return OutPayEnum.ALLINPAY;
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
	
	public String getAppId()
    {
        return PayConstants.ALLINPAY_APP_ID;
    }

    /* (non-Javadoc)
     * @see com.goochou.p2b.constant.pay.BaseOutPay#getWithdrawBankMap()
     */
    @Override
    public Map<String, String> getWithdrawBankMap() {
         return withdrawBankMap;
    }
}
