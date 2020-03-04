package com.goochou.p2b.model.pay.allinpay.xstruct.quickpay;



/**
 * @Description
 * @Author linwx@allinpay.com
 * @Date 2018年9月5日
 **/
public class FasttrxDetail extends PGPayReqRecord{
	
	private String AGRMNO;
	private String CVV2;
	private String VALIDDATE;
	
	public String getAGRMNO() {
		return AGRMNO;
	}
	public void setAGRMNO(String aGRMNO) {
		AGRMNO = aGRMNO;
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
	
}
