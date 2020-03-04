package com.goochou.p2b.model.pay.allinpay.xstruct.trans;

import java.io.Serializable;

/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2018年10月22日
 **/
public class JointDtlDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4887301814818340888L;

	private String MERCHANT_ID;
    private String AMOUNT;;
	
	
	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANT_ID) {
		MERCHANT_ID = mERCHANT_ID;
	}
	public String getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
