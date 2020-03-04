package com.goochou.p2b.model.pay.allinpay.xstruct.accttransfer;

import java.util.List;

public class BacctTransferReq {
	private BSum BSUM;
	private List DTLS;
	public BSum getBSUM() {
		return BSUM;
	}
	public void setBSUM(BSum bSUM) {
		BSUM = bSUM;
	}
	public List getDTLS() {
		return DTLS;
	}
	public void setDTLS(List dTLS) {
		DTLS = dTLS;
	}

	
	
}
