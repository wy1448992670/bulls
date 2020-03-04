package com.goochou.p2b.hessian.user;

import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.model.User;

public class UserResponse extends Response{
	private static final long serialVersionUID = 5114347663950152180L;
	
	private User user;
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
