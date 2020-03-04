package com.goochou.p2b.model.pay;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.fuiou.util.MD5;
import com.goochou.p2b.constant.pay.BaseOutPay;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.model.pay.allinpay.AllinGateWayUtil;
import com.goochou.p2b.utils.Money;

/**
 * 通联网关
 * @author ydp
 * @date 2019/08/01
 */
public class AllinGateWayPay extends BaseOutPay implements Serializable
{
	/**
     *
     */
    private static final long serialVersionUID = 6962814530088451441L;

    @Override
    public String getPayBackClassName()
    {
        return "com.goochou.p2b.model.pay.AllinGateWayBack";
    }
    
    public AllinGateWayPay() {
        
    }
    public AllinGateWayPay(String orderNo, Double amount, String returnUrl,
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
    public BaseOutPay setSubOutPay(String orderNo, Double amount, String bankCode)
    {
        BaseOutPay baseOutPay = new AllinGateWayPay(
            orderNo, amount,
        		PayConstants.RETURN_URL,PayConstants.ADVITE_URL,
               null, PayConstants.ALLINPAY_GATEWAY+"/pay");
        Long formatAmount = new Money(baseOutPay.getAmount()).getCent();
        //baseOutPay.setDefaultBankNum(bankCode);
        String randomstr = String.valueOf(System.currentTimeMillis()) ;
        TreeMap<String,String> params = new TreeMap<String,String>();
        params.put("appid", PayConstants.ALLINPAY_APP_ID);
        params.put("cusid", getMerNo());
        params.put("charset", "UTF-8");
        params.put("trxamt", String.valueOf(formatAmount));
        params.put("orderid", baseOutPay.getOrderNo());
        params.put("paytype", "");
        params.put("randomstr", randomstr);
        params.put("goodsinf", "");
        params.put("goodsid", "");
        params.put("limitpay", "no_credit");
        params.put("validtime", "30");
        params.put("returl", PayConstants.RETURN_URL+"?orderNo="+baseOutPay.getOrderNo());
        params.put("notifyurl", PayConstants.ADVITE_URL);
        String sign  = AllinGateWayUtil.sign(params,PayConstants.ALLINPAY_MCHNT_KEY);
        StringBuffer sb = new StringBuffer();
        sb.append("正在跳转..");
        sb.append("<form id=\"payFrom\" name=\"payFrom\" action=\""+baseOutPay.getSubmitUrl()+"\" method=\"post\" />");
        sb.append("<input type=\"hidden\" name=\"appid\" id=\"appid\" value=\""+PayConstants.ALLINPAY_APP_ID+"\" />");
        sb.append("<input type=\"hidden\" name=\"cusid\" id=\"cusid\" value=\""+getMerNo()+"\" />");
        sb.append("<input type=\"hidden\" name=\"charset\" id=\"charset\" value=\"UTF-8\" />");
        sb.append("<input type=\"hidden\" name=\"trxamt\" id=\"trxamt\" value=\""+formatAmount+"\" />");
        sb.append("<input type=\"hidden\" name=\"orderid\" id=\"orderid\" value=\""+baseOutPay.getOrderNo()+"\" />");
        sb.append("<input type=\"hidden\" name=\"randomstr\" id=\"randomstr\" value=\""+randomstr+"\" />");
        sb.append("<input type=\"hidden\" name=\"goodsinf\" id=\"goodsinf\" value=\"\" />");
        sb.append("<input type=\"hidden\" name=\"goodsid\" id=\"goodsid\" value=\"\" />");
        sb.append("<input type=\"hidden\" name=\"limitpay\" id=\"limitpay\" value=\"no_credit\" />");
        sb.append("<input type=\"hidden\" name=\"validtime\" id=\"validtime\" value=\"30\" />");
        sb.append("<input type=\"hidden\" name=\"returl\" id=\"returl\" value=\""+PayConstants.RETURN_URL+"?orderNo="+baseOutPay.getOrderNo()+"\" />");
        sb.append("<input type=\"hidden\" name=\"notifyurl\" id=\"notifyurl\" value=\""+PayConstants.ADVITE_URL+"\" />");
        sb.append("<input type=\"hidden\" name=\"sign\" id=\"sign\" value=\""+sign+"\" />");
        sb.append("</form>");
        sb.append("<script>document.forms['payFrom'].submit();</script>");
        baseOutPay.setJumpParams(sb.toString());
        return baseOutPay;
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
        return OutPayEnum.ALLINPAY_GATEWAY;
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
