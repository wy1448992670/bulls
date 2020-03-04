package com.goochou.p2b.model.pay.allinpay.xstruct.quickpay;
/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2017年9月15日
 **/
public class FAGRA {
	private String MERCHANT_ID;   //商户ID
	private String BANK_CODE;   //银行代码
	private String ACCOUNT_TYPE;   //00银行卡，01存折，02信用卡。不填默认为银行卡00。
	private String ACCOUNT_NO;   //银行卡或存折号码
	private String ACCOUNT_NAME;   //银行卡或存折上的所有人姓名
	private String ACCOUNT_PROP;   //0私人，1公司。不填时，默认为私人0
	private String ID_TYPE;   //0：身份证,1: 户口簿，2：护照,3.军官证,4.士兵证，5. 港澳居民来往内地通行证,6. 台湾同胞来往内地通行证,7. 临时身份证,8. 外国人居留证,9. 警官证, X.其他证件
	private String ID;   //证件号
	private String TEL;   //手机号/小灵通
	private String MERREM;   //商户保留信息
	private String REMARK;   //备注
	private String CVV2;
	private String VALIDDATE;//第二版更正
	private String VAILDDATE;//第一版错误拼写
	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANT_ID) {
		MERCHANT_ID = mERCHANT_ID;
	}
	public String getBANK_CODE() {
		return BANK_CODE;
	}
	public void setBANK_CODE(String bANK_CODE) {
		BANK_CODE = bANK_CODE;
	}
	public String getACCOUNT_TYPE() {
		return ACCOUNT_TYPE;
	}
	public void setACCOUNT_TYPE(String aCCOUNT_TYPE) {
		ACCOUNT_TYPE = aCCOUNT_TYPE;
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
	public String getACCOUNT_PROP() {
		return ACCOUNT_PROP;
	}
	public void setACCOUNT_PROP(String aCCOUNT_PROP) {
		ACCOUNT_PROP = aCCOUNT_PROP;
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
	public String getMERREM() {
		return MERREM;
	}
	public void setMERREM(String mERREM) {
		MERREM = mERREM;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getCVV2() {
		return CVV2;
	}
	public void setCVV2(String cVV2) {
		CVV2 = cVV2;
	}
	public String getVALIDDATE() {
		return VALIDDATE;
	}
	public void setVALIDDATE(String vALIDDATE) {
		VALIDDATE = vALIDDATE;
	}
	public String getVAILDDATE() {
		return VAILDDATE;
	}
	public void setVAILDDATE(String vAILDDATE) {
		VAILDDATE = vAILDDATE;
	}
	
}
