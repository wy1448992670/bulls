package com.goochou.p2b.model.pay.allinpay.xstruct.ver;

public class ValidTR {
	private String ACCOUNT_ATTRB;//账户类别  1一类账户  2二类账户  3三类账户
	private String MERCHANT_ID;	//商户代码
	private String SUBMIT_TIME;//	提交时间
	private String BINDID;	//协议ID
	private String RELATID;//	关联ID
	private String BANK_CODE; //银行代码
	private String ACCOUNT_TYPE;//	账号类型
	private String ACCOUNT_NO;//	账号
	private String ACCOUNT_NAME;//	账号名
	private String ACCOUNT_PROP;//	账号属性
	private String ID_TYPE;//	开户证件类型
	private String ID;//	证件号
	private String RELATEDCARD;//	关联卡号
	private String TEL;//	手机号/小灵通
	private String MERREM;//	商户保留信息
	private String REMARK;//	备注
	public String getACCOUNT_ATTRB() {
		return ACCOUNT_ATTRB;
	}

	public void setACCOUNT_ATTRB(String aCCOUNTATTRB) {
		ACCOUNT_ATTRB = aCCOUNTATTRB;
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

	public String getBINDID() {
		return BINDID;
	}

	public void setBINDID(String bINDID) {
		BINDID = bINDID;
	}

	public String getRELATID() {
		return RELATID;
	}

	public void setRELATID(String rELATID) {
		RELATID = rELATID;
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

	public String getRELATEDCARD() {
		return RELATEDCARD;
	}

	public void setRELATEDCARD(String rELATEDCARD) {
		RELATEDCARD = rELATEDCARD;
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
	
}
