package com.goochou.p2b.model.pay.allinpay.xstruct.stdagr;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2018年6月11日
 **/
@SuppressWarnings({"rawtypes", "unchecked"})
public class QAGRRSP {

	private List details = new ArrayList();

	public List getDetails() {
		return details;
	}

	public void setDetails(List details) {
		this.details = details;
	}
	
	public void addDtl(QAGRDETAIL dtl) {
		if (details == null)
			details = new ArrayList();
		details.add(dtl);
	}
	
}
