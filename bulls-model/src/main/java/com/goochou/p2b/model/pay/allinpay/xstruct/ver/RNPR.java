package com.goochou.p2b.model.pay.allinpay.xstruct.ver;

public class RNPR {
	private String MERCHANT_ID;//商户代码;
	private String SRCREQSN;//原请求流水
	
	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANTID) {
		MERCHANT_ID = mERCHANTID;
	}
	public String getSRCREQSN() {
		return SRCREQSN;
	}
	public void setSRCREQSN(String sRCREQSN) {
		SRCREQSN = sRCREQSN;
	}

}
