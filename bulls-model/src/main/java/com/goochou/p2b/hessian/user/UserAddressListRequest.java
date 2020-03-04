package com.goochou.p2b.hessian.user;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Request;

public class UserAddressListRequest extends Request{
	private static final long serialVersionUID = -6817049802044051068L;
	@FieldMeta(description = "用户ID", have = true)
	private Integer userId;
	@FieldMeta(description = "页码", have = true)
	private Integer page;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	 
}
