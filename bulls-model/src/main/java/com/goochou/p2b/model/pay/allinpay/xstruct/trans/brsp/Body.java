package com.goochou.p2b.model.pay.allinpay.xstruct.trans.brsp;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Body {
	
	private List details = new ArrayList();
	public List getDetails() {
		return details;
	}
	public void setDetails(List details) {
		this.details = details;
	}
	
	public void addDetail(Ret_Detail detail) {
		details.add(detail);
	}
}
