package com.goochou.p2b.model.pay.allinpay.xstruct.trans;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2018年10月22日
 **/
public class JointDto  {

	private List list;

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public void addTrx(Object o) {
		if (o == null)
			return;
		if (list == null)
			list = new ArrayList();
		list.add(o);
	}
	
}
