package com.goochou.p2b.hessian.user;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Request;

public class UserAddressDeleteRequest extends Request{
	private static final long serialVersionUID = -6817049802044051068L;
	@FieldMeta(description = "地址ID", have = true)
	private Integer addressId;
	@FieldMeta(description = "用户ID", have = true)
	private Integer userId;
	  
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	 
}
