package com.goochou.p2b.model.pay;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.fuiou.util.MD5;
import com.goochou.p2b.constant.pay.BankEnum;
import com.goochou.p2b.constant.pay.BaseOutPay;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.model.pay.allinpay.AllinGateWayUtil;
import com.goochou.p2b.utils.Money;

/**
 * 易宝支付
 * @author ydp
 * @date 2019/08/29
 */
public class YeeOutPay extends BaseOutPay implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = -3351688158779752060L;

    @Override
    public String getPayBackClassName()
    {
        return "com.goochou.p2b.model.pay.YeeOutBack";
    }
    
    public YeeOutPay() {
        
    }
    public YeeOutPay(String orderNo, Double amount, String returnUrl,
            String adviteUrl,  String defaultBankNum, String submitUrl)
    {
        super(orderNo, amount, returnUrl, adviteUrl, defaultBankNum, submitUrl);
    }


    /**
     * 储蓄卡编号集合
     */
    @SuppressWarnings("serial")
    private static Map<String, String> cxkBankMap = new HashMap<String, String>(){{
        put(BankEnum.ICBC.getFeatureName(), BankEnum.ICBC.getFeatureName());
        put(BankEnum.ABC.getFeatureName(), BankEnum.ABC.getFeatureName());
        put(BankEnum.BOC.getFeatureName(), BankEnum.BOC.getFeatureName());
        put(BankEnum.CCB.getFeatureName(), BankEnum.CCB.getFeatureName());
        put("CMBCHINA", BankEnum.CMB.getFeatureName());
        put(BankEnum.SPDB.getFeatureName(), BankEnum.SPDB.getFeatureName());
        put(BankEnum.CGB.getFeatureName(), BankEnum.CGB.getFeatureName());
        put("BOCO", BankEnum.COMM.getFeatureName());
        put(BankEnum.PSBC.getFeatureName(), BankEnum.PSBC.getFeatureName());
        put("ECITIC", BankEnum.CITIC.getFeatureName());
        put(BankEnum.CMBC.getFeatureName(), BankEnum.CMBC.getFeatureName());
        put(BankEnum.CEB.getFeatureName(), BankEnum.CEB.getFeatureName());
        put(BankEnum.HXB.getFeatureName(), BankEnum.HXB.getFeatureName());
        put(BankEnum.CIB.getFeatureName(), BankEnum.CIB.getFeatureName());
        put("SHYH", BankEnum.BOS.getFeatureName());
        put("SDB", BankEnum.PAB.getFeatureName());
        put(BankEnum.BCCB.getFeatureName(), BankEnum.BCCB.getFeatureName());
    }};
    
    @Override
    public String getSignKey()
    {
        return PayConstants.YEEPAY_APP_KEY;
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
    public BaseOutPay setSubOutPay(String orderNo, Double amount, String bankCode)
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
        return null;
    }


    @Override
    protected String getMerNo()
    {
        return PayConstants.YEEPAY_MERCHANTNO;
    }

    @Override
    public String outPayIdFactory()
    {
    	return "";
    }
    @Override
    public OutPayEnum getOutPayEnum()
    {
        return OutPayEnum.YEEPAY;
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

    @Override
    public Map<String, String> getWithdrawBankMap() {
         return null;
    }
    
    public static String getCxkBank(String bankCode) {
        return cxkBankMap.get(bankCode);
    }
}
