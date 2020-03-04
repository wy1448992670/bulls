package com.goochou.p2b.model.pay.allinpay.xstruct.quickpay;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2018年7月17日
 **/
public class RET_DETAILS {
	List<RET_DETAIL> details;

	public List<RET_DETAIL> getDetails() {
		return details;
	}

	public void setDetails(List<RET_DETAIL> details) {
		this.details = details;
	}

	public void add(RET_DETAIL retdetail){
		if(details == null){
			details = new ArrayList<RET_DETAIL>();
		}
		details.add(retdetail);
	}
}
