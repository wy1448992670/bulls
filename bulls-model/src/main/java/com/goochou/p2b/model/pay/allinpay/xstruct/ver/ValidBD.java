/**
 * 
 */
package com.goochou.p2b.model.pay.allinpay.xstruct.ver;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者：胡东强
 * @功能说明：
 * @创建时间: 2011-12-31下午01:49:47
 * @版本：V1.0
 * 
 */
@SuppressWarnings("rawtypes")
public class ValidBD {
	 public List details = new ArrayList();

	public List getDetails() {
		return details;
	}

	public void setDetails(List details) {
		this.details = details;
	}
	 
	@SuppressWarnings("unchecked")
	public void addDTL(VbDetail dtl){
		if(details == null) details = new ArrayList();
		details.add(dtl);
	}
}
