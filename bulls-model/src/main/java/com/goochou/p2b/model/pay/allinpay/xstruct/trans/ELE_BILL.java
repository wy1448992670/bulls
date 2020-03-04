package com.goochou.p2b.model.pay.allinpay.xstruct.trans;
/**
 * @Description 电子回单下载
 * @Author meixf@allinpay.com
 * @Date 2018年7月12日
 **/
public class ELE_BILL {
	
	private String MERCHANT_ID; //商户号
	private String FILENAME; //文件名
	private String FSN; //序号
	
	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANT_ID) {
		MERCHANT_ID = mERCHANT_ID;
	}
	public String getFILENAME() {
		return FILENAME;
	}
	public void setFILENAME(String fILENAME) {
		FILENAME = fILENAME;
	}
	public String getFSN() {
		return FSN;
	}
	public void setFSN(String fSN) {
		FSN = fSN;
	}
}
