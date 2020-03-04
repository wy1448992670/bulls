package com.goochou.p2b.hessian.user;


import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.model.UserAddress;

public class UserAddressResponse extends Response{
	private static final long serialVersionUID = 342308197525010173L;
	
	private UserAddress address;

	public UserAddress getAddress() {
		return address;
	}

	public void setAddress(UserAddress address) {
		this.address = address;
	}
	 
}
