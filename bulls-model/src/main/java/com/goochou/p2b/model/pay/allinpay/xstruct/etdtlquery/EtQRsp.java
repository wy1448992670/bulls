package com.goochou.p2b.model.pay.allinpay.xstruct.etdtlquery;

import java.util.ArrayList;
import java.util.List;

public class EtQRsp {
	
	private EtSum ETSUM = new EtSum();
	private List details = new ArrayList();

	public List getDetails() {
		return details;
	}
	public void setDetails(List details) {
		this.details = details;
	}
	public void addDtl(EtDtl dtl)
	{
		if(details==null) details=new ArrayList();
		details.add(dtl);
	}
	
	public EtSum getETSUM() {
		return ETSUM;
	}
	public void setETSUM(EtSum eTSUM) {
		ETSUM = eTSUM;
	}
	
}
