package com.goochou.p2b.model.pay;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fuiou.util.MD5;
import com.goochou.p2b.constant.pay.BankEnum;
import com.goochou.p2b.constant.pay.BaseOutPay;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.utils.DateTimeUtil;
import com.goochou.p2b.utils.Money;

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
public class FuiouOutPay extends BaseOutPay implements Serializable
{
    
    /**
	 * <p>Discription:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = 6224108545690458893L;

	@Override
    public String getPayBackClassName()
    {
        return "com.goochou.p2b.model.pay.FuiouPayBack";
    }
    
    public FuiouOutPay() {
        
    }
    public FuiouOutPay(String orderNo, Double amount, String returnUrl,
            String adviteUrl,  String defaultBankNum, String submitUrl)
    {
        super(orderNo, amount, returnUrl, adviteUrl, defaultBankNum, submitUrl);
    }


    /**
     * 储蓄卡编号集合
     */
    @SuppressWarnings("serial")
    private static Map<String, String> cxkBankMap = new HashMap<String, String>(){{
        put(BankEnum.ICBC.getFeatureName(), "0801020000");
        put(BankEnum.ABC.getFeatureName(), "0801030000");
        put(BankEnum.BOC.getFeatureName(), "0801040000");
        put(BankEnum.CCB.getFeatureName(), "0801050000");
        put(BankEnum.CMB.getFeatureName(), "0803080000");
        put(BankEnum.SPDB.getFeatureName(), "0803100000");
        put(BankEnum.CGB.getFeatureName(), "0803060000");
        put(BankEnum.COMM.getFeatureName(), "0803010000");
        put(BankEnum.PSBC.getFeatureName(), "0801000000");
        put(BankEnum.CITIC.getFeatureName(), "0803020000");
        put(BankEnum.CMBC.getFeatureName(), "0803050000");
        put(BankEnum.CEB.getFeatureName(), "0803030000");
        put(BankEnum.HXB.getFeatureName(), "0803040000");
        put(BankEnum.CIB.getFeatureName(), "0803090000");
        put(BankEnum.BOS.getFeatureName(), "080401001C");
        put(BankEnum.PAB.getFeatureName(), "0804105840");
        put(BankEnum.BCCB.getFeatureName(), "0804031000");
    }};
    
    @Override
    public String getSignKey()
    {
        return PayConstants.FUIOU_B2C_MCHNT_KEY;
    }

    @Override
    public String getInfoCiphertext()
    {
        return MD5.MD5Encode(this.getSignInfo());
    }
    @Override
    public String getSignInfo()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(PayConstants.FUIOU_B2C_MCHNT_CD);
        sb.append("|");
        sb.append(getOrderNo());
        sb.append("|");
        sb.append(new Money(getAmount()).getCent());
        sb.append("|");
        sb.append("B2C");
        sb.append("|");
        sb.append(getReturnUrl()+"?orderno="+getOrderNo());
        sb.append("|");
        sb.append(getAdviteUrl());
        sb.append("|");
        sb.append("10m");
        sb.append("|");
        sb.append(getDefaultBankNum());
        sb.append("|");
        sb.append("recharge");
        sb.append("|");
        sb.append("http://www.xinjucai.com");
        sb.append("|");
        sb.append("备注");
        sb.append("|");
        sb.append("1.0.1");
        sb.append("|");
        sb.append(PayConstants.FUIOU_B2C_MCHNT_KEY);
        System.out.println(sb.toString());
        return sb.toString();
    }
    
    @Override
    public String getJumpParams(){
        return super.jumpParams;
    }
    
    @Override
    public BaseOutPay setSubOutPay(String payOutId, Double amount, String bankCode)
    {
        BaseOutPay baseOutPay = new FuiouOutPay(
        		payOutId, amount,
        		PayConstants.RETURN_URL,PayConstants.ADVITE_URL,
               null, PayConstants.FUIOU_SUBMIT);
        Long formatAmount = new Money(baseOutPay.getAmount()).getCent();
        baseOutPay.setDefaultBankNum(bankCode);
        StringBuffer sb = new StringBuffer();
        sb.append("正在跳转..");
        sb.append("<form id=\"payFrom\" name=\"payFrom\" action=\""+baseOutPay.getSubmitUrl()+"\" method=\"post\" />");
        sb.append("<input type=\"hidden\" value='"+baseOutPay.getInfoCiphertext()+"' name=\"md5\"/>");
        sb.append("<input type=\"hidden\" value='"+getMerNo()+"' name=\"mchnt_cd\"/>");
        sb.append("<input type=\"hidden\" value='"+baseOutPay.getOrderNo()+"' name=\"order_id\"/>");
        sb.append("<input type=\"hidden\" value='"+formatAmount+"' name=\"order_amt\"/>");
        sb.append("<input type=\"hidden\" value='B2C' name=\"order_pay_type\"/>");
        sb.append("<input type=\"hidden\" value='"+PayConstants.RETURN_URL+"?orderno="+payOutId+"' name=\"page_notify_url\"/>");
        sb.append("<input type=\"hidden\" value='"+PayConstants.ADVITE_URL+"' name=\"back_notify_url\"/>");
        sb.append("<input type=\"hidden\" value='10m' name=\"order_valid_time\"/>");
        sb.append("<input type=\"hidden\" value='"+baseOutPay.getDefaultBankNum()+"' name=\"iss_ins_cd\"/>");
        sb.append("<input type=\"hidden\" value='recharge' name=\"goods_name\"/>");
        sb.append("<input type=\"hidden\" value='http://www.xinjucai.com' name=\"goods_display_url\"/>");
        sb.append("<input type=\"hidden\" value='备注' name=\"rem\"/>");
        sb.append("<input type=\"hidden\" value='1.0.1' name=\"ver\"/>");
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
        String order_id = getOrderNo();
        String order_pay_error = "成功";
        String order_pay_code = "0000";
        String mchnt_cd = PayConstants.FUIOU_QUICK_MCHNT_CD;
        String order_date = DateTimeUtil.getTodayChar14();
        String order_st = "";
        String fy_ssn = DateTimeUtil.getTodayChar14();
        String order_amt = new Money(getAmount()).getCent()+"";
        String resv1 = "";
        String signPain = new StringBuffer().append(mchnt_cd).append("|").append(order_id).append("|").append(order_date)
                .append("|").append(order_amt).append("|").append(order_st).append("|").append(order_pay_code).append("|")
                .append(order_pay_error).append("|").append(resv1).append("|").append(fy_ssn).append("|").append(PayConstants.FUIOU_B2C_MCHNT_KEY).toString();
        StringBuffer sb = new StringBuffer();
        sb.append("<form id=\"payFrom\" name=\"payFrom\" action=\""+baseOutPay.getAdviteUrl()+"\" method=\"post\" />");
        sb.append("<input type=\"hidden\" name=\"md5\" value=\""+MD5.MD5Encode(signPain)+"\" />");
        sb.append("<input type=\"hidden\" name=\"resv1\" value=\"\" />");
        sb.append("<input type=\"hidden\" name=\"order_amt\" value=\""+order_amt+"\" />");
        sb.append("<input type=\"hidden\" name=\"fy_ssn\" value=\""+fy_ssn+"\" />");
        sb.append("<input type=\"hidden\" name=\"order_st\" value=\""+order_st+"\" />");
        sb.append("<input type=\"hidden\" name=\"order_date\" value=\""+order_date+"\" />");
        sb.append("<input type=\"hidden\" name=\"mchnt_cd\" value=\""+mchnt_cd+"\" />");
        sb.append("<input type=\"hidden\" name=\"order_pay_code\" value=\""+order_pay_code+"\" />");
        sb.append("<input type=\"hidden\" name=\"order_pay_error\" value=\""+order_pay_error+"\" />");
        sb.append("<input type=\"hidden\" name=\"order_id\" value=\""+order_id+"\" />");
        sb.append("</form>");
        sb.append("<script>document.forms['payFrom'].submit();</script>");
        super.jumpParams = sb.toString();
        return super.jumpParams;
    }


    @Override
    protected String getMerNo()
    {
        return PayConstants.FUIOU_B2C_MCHNT_CD;
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
        return OutPayEnum.FUIOU;
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
