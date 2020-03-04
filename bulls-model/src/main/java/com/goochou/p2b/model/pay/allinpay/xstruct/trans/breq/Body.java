package com.goochou.p2b.model.pay.allinpay.xstruct.trans.breq;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Body {
	private Trans_Sum TRANS_SUM;
	private List details = new ArrayList( );
	
	public Trans_Sum getTRANS_SUM() {
		return TRANS_SUM;
	}
	public void setTRANS_SUM(Trans_Sum trans_sum) {
		TRANS_SUM = trans_sum;
	}
	public List getDetails() {
		return details;
	}
	public void setDetails(List details) {
		this.details = details;
	}
	
	public void addDetail(Trans_Detail detail) {
		details.add(detail);
	}
}
