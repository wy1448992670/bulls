package com.goochou.p2b.model.pay.allinpay.xstruct.transfer;

public class Transfer {
	private String MERCHANT_ID;// 商户号
	private String SUBMIT_TIME;// 提交时间
	private String ACCOUNT_NAME;// 持卡人姓名
	private String AMOUNT;// 还款金额，整数，单位分
	private String CURRENCY;// 人民币：CNY，默认为人民币
	private String BUSINESS_CODE;// 转出方业务代码，见附录A2业务代码
	private String FROM_ACCOUNT_NO;//转出方借记卡号码
	private String TO_ACCOUNT_NO;// 转入方信用卡号码
	private String REMARK;// 
	private String SUMMARY;// 
	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANT_ID) {
		MERCHANT_ID = mERCHANT_ID;
	}
	public String getSUBMIT_TIME() {
		return SUBMIT_TIME;
	}
	public void setSUBMIT_TIME(String sUBMIT_TIME) {
		SUBMIT_TIME = sUBMIT_TIME;
	}
	public String getACCOUNT_NAME() {
		return ACCOUNT_NAME;
	}
	public void setACCOUNT_NAME(String aCCOUNT_NAME) {
		ACCOUNT_NAME = aCCOUNT_NAME;
	}
	public String getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}
	public String getCURRENCY() {
		return CURRENCY;
	}
	public void setCURRENCY(String cURRENCY) {
		CURRENCY = cURRENCY;
	}
	public String getBUSINESS_CODE() {
		return BUSINESS_CODE;
	}
	public void setBUSINESS_CODE(String bUSINESS_CODE) {
		BUSINESS_CODE = bUSINESS_CODE;
	}
	public String getFROM_ACCOUNT_NO() {
		return FROM_ACCOUNT_NO;
	}
	public void setFROM_ACCOUNT_NO(String fROM_ACCOUNT_NO) {
		FROM_ACCOUNT_NO = fROM_ACCOUNT_NO;
	}
	public String getTO_ACCOUNT_NO() {
		return TO_ACCOUNT_NO;
	}
	public void setTO_ACCOUNT_NO(String tO_ACCOUNT_NO) {
		TO_ACCOUNT_NO = tO_ACCOUNT_NO;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getSUMMARY() {
		return SUMMARY;
	}
	public void setSUMMARY(String sUMMARY) {
		SUMMARY = sUMMARY;
	}

}
