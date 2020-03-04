package com.goochou.p2b.hessian.user;

import java.util.List;

import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.model.UserAddress;

public class UserAddressListResponse extends Response{
	private static final long serialVersionUID = 342308197525010173L;
	
	private List<UserAddress> list;
	private Integer count;
	public List<UserAddress> getList() {
		return list;
	}

	public void setList(List<UserAddress> list) {
		this.list = list;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
