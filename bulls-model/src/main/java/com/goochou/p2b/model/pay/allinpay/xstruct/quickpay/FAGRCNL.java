package com.goochou.p2b.model.pay.allinpay.xstruct.quickpay;
/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2017年9月15日
 **/
public class FAGRCNL {
	private String MERCHANT_ID;   //商户ID
	private String ACCOUNT_NO;    //卡号
	private String AGRMNO;
	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANT_ID) {
		MERCHANT_ID = mERCHANT_ID;
	}
	public String getACCOUNT_NO() {
		return ACCOUNT_NO;
	}
	public void setACCOUNT_NO(String aCCOUNT_NO) {
		ACCOUNT_NO = aCCOUNT_NO;
	}
	public String getAGRMNO() {
		return AGRMNO;
	}
	public void setAGRMNO(String aGRMNO) {
		AGRMNO = aGRMNO;
	}
	
}
