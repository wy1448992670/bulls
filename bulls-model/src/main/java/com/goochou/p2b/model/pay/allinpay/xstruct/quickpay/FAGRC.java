package com.goochou.p2b.model.pay.allinpay.xstruct.quickpay;
/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2017年9月15日
 **/
public class FAGRC {
	private String MERCHANT_ID;   //商户代码
	private String SRCREQSN;   //对应申请请求报文中的REQ_SN
	private String VERCODE;   //短信验证码
	
	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANT_ID) {
		MERCHANT_ID = mERCHANT_ID;
	}
	public String getSRCREQSN() {
		return SRCREQSN;
	}
	public void setSRCREQSN(String sRCREQSN) {
		SRCREQSN = sRCREQSN;
	}
	public String getVERCODE() {
		return VERCODE;
	}
	public void setVERCODE(String vERCODE) {
		VERCODE = vERCODE;
	}

}
