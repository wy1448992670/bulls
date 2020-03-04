package com.goochou.p2b.model.pay.allinpay.xstruct.quickpay;
/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2018年7月17日
 **/
public class RET_DETAIL {
	private String SN;
	private String RESULT_CODE;
	private String RESULT_MSG;
	
	public String getSN() {
		return SN;
	}
	public void setSN(String sN) {
		SN = sN;
	}
	public String getRESULT_CODE() {
		return RESULT_CODE;
	}
	public void setRESULT_CODE(String rESULT_CODE) {
		RESULT_CODE = rESULT_CODE;
	}
	public String getRESULT_MSG() {
		return RESULT_MSG;
	}
	public void setRESULT_MSG(String rESULT_MSG) {
		RESULT_MSG = rESULT_MSG;
	}
}
