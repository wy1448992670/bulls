package com.goochou.p2b.model.pay.allinpay.xstruct.quickpay;
/**
 * @Description  C  combined 合并支付
 * @Author meixf@allinpay.com
 * @Date 2017年12月28日
 **/
public class FASTTRXRETC {
	private String RET_CODE;
	private String SETTLE_DAY;
	private String ERR_MSG;
	private String ACCT_SUFFIX; //卡号后四位
	private String AGRMNO;
	
	public String getRET_CODE() {
		return RET_CODE;
	}
	public void setRET_CODE(String rET_CODE) {
		RET_CODE = rET_CODE;
	}
	public String getSETTLE_DAY() {
		return SETTLE_DAY;
	}
	public void setSETTLE_DAY(String sETTLE_DAY) {
		SETTLE_DAY = sETTLE_DAY;
	}
	public String getERR_MSG() {
		return ERR_MSG;
	}
	public void setERR_MSG(String eRR_MSG) {
		ERR_MSG = eRR_MSG;
	}
	public String getACCT_SUFFIX() {
		return ACCT_SUFFIX;
	}
	public void setACCT_SUFFIX(String aCCT_SUFFIX) {
		ACCT_SUFFIX = aCCT_SUFFIX;
	}
	public String getAGRMNO() {
		return AGRMNO;
	}
	public void setAGRMNO(String aGRMNO) {
		AGRMNO = aGRMNO;
	}
	
}
