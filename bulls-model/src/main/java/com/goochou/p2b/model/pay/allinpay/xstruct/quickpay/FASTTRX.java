package com.goochou.p2b.model.pay.allinpay.xstruct.quickpay;
/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2017年9月18日
 **/
public class FASTTRX {
	String BUSINESS_CODE;
	String MERCHANT_ID;
	String SUBMIT_TIME;
	String AGRMNO; //协议号   用于协议支付
	String ACCOUNT_NO;
	String ACCOUNT_NAME;
	String ACCOUNT_TYPE;//账户类型  00 银行卡  01 存折  02 信用卡
	String ACCOUNT_PROP; //账户属性   0 私人 1 公司
	String BANK_CODE;
	String AMOUNT;
	String CURRENCY;
	String ID_TYPE;
	String ID;
	String TEL;
	String CVV2;
	String VAILDDATE;
	String VALIDDATE; //第二版更正
	String CUST_USERID;
	String SUMMARY;
	String REMARK;
	String SRC_REQ_SN;  //原交易流水     用于直接支付
	String VER_CODE;  //验证码   用于直接支付
	String RESERVED; //保留字段
	private String NOTIFYURL;// 通知地址
	
	public String getBUSINESS_CODE() {
		return BUSINESS_CODE;
	}
	public void setBUSINESS_CODE(String bUSINESS_CODE) {
		BUSINESS_CODE = bUSINESS_CODE;
	}
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
	public String getAGRMNO() {
		return AGRMNO;
	}
	public void setAGRMNO(String aGRMNO) {
		AGRMNO = aGRMNO;
	}
	public String getBANK_CODE() {
		return BANK_CODE;
	}
	public void setBANK_CODE(String bANK_CODE) {
		BANK_CODE = bANK_CODE;
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
	public String getID_TYPE() {
		return ID_TYPE;
	}
	public void setID_TYPE(String iD_TYPE) {
		ID_TYPE = iD_TYPE;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getTEL() {
		return TEL;
	}
	public void setTEL(String tEL) {
		TEL = tEL;
	}
	public String getCVV2() {
		return CVV2;
	}
	public void setCVV2(String cVV2) {
		CVV2 = cVV2;
	}
	public String getVAILDDATE() {
		return VAILDDATE;
	}
	public void setVAILDDATE(String vAILDDATE) {
		VAILDDATE = vAILDDATE;
	}
	public String getCUST_USERID() {
		return CUST_USERID;
	}
	public void setCUST_USERID(String cUST_USERID) {
		CUST_USERID = cUST_USERID;
	}
	public String getSUMMARY() {
		return SUMMARY;
	}
	public void setSUMMARY(String sUMMARY) {
		SUMMARY = sUMMARY;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getACCOUNT_NO() {
		return ACCOUNT_NO;
	}
	public void setACCOUNT_NO(String aCCOUNT_NO) {
		ACCOUNT_NO = aCCOUNT_NO;
	}
	public String getACCOUNT_NAME() {
		return ACCOUNT_NAME;
	}
	public void setACCOUNT_NAME(String aCCOUNT_NAME) {
		ACCOUNT_NAME = aCCOUNT_NAME;
	}
	public String getSRC_REQ_SN() {
		return SRC_REQ_SN;
	}
	public void setSRC_REQ_SN(String sRC_REQ_SN) {
		SRC_REQ_SN = sRC_REQ_SN;
	}
	public String getVER_CODE() {
		return VER_CODE;
	}
	public void setVER_CODE(String vER_CODE) {
		VER_CODE = vER_CODE;
	}
	public String getACCOUNT_TYPE() {
		return ACCOUNT_TYPE;
	}
	public void setACCOUNT_TYPE(String aCCOUNT_TYPE) {
		ACCOUNT_TYPE = aCCOUNT_TYPE;
	}
	public String getRESERVED() {
		return RESERVED;
	}
	public void setRESERVED(String rESERVED) {
		RESERVED = rESERVED;
	}
	public String getVALIDDATE() {
		return VALIDDATE;
	}
	public void setVALIDDATE(String vALIDDATE) {
		VALIDDATE = vALIDDATE;
	}
	public String getACCOUNT_PROP() {
		return ACCOUNT_PROP;
	}
	public void setACCOUNT_PROP(String aCCOUNT_PROP) {
		ACCOUNT_PROP = aCCOUNT_PROP;
	}
	public String getNOTIFYURL() {
		return NOTIFYURL;
	}
	public void setNOTIFYURL(String nOTIFYURL) {
		NOTIFYURL = nOTIFYURL;
	}

}
