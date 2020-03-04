package com.goochou.p2b.model.pay.allinpay.xstruct.trans;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Ledgers {
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
