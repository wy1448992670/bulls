package com.goochou.p2b.model.pay.allinpay.xstruct.quickpay;
/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2017年12月28日
 **/
public class FASTTRXRET {
	private String RET_CODE;
	private String SETTLE_DAY;
	private String ERR_MSG;
	private String ACCT_SUFFIX; //卡号后四位
	
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
}
