package com.goochou.p2b.model.pay.allinpay.xstruct.stdagr;
/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2018年6月11日
 **/
public class AGRCFMRSP {

	private String AGRMNO;
	private String RET_CODE;
	private String ERR_MSG;
	
	public String getAGRMNO() {
		return AGRMNO;
	}
	public void setAGRMNO(String aGRMNO) {
		AGRMNO = aGRMNO;
	}
	public String getRET_CODE() {
		return RET_CODE;
	}
	public void setRET_CODE(String rET_CODE) {
		RET_CODE = rET_CODE;
	}
	public String getERR_MSG() {
		return ERR_MSG;
	}
	public void setERR_MSG(String eRR_MSG) {
		ERR_MSG = eRR_MSG;
	}
	
}
