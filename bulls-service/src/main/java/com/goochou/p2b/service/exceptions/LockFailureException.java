package com.goochou.p2b.service.exceptions;

public class LockFailureException extends Exception{

	private static final long serialVersionUID = 7959375149775271617L;
	
	public LockFailureException() {
		super();
	}

	public LockFailureException(String message) {
		super(message);
	}
	
	@Override
	public String getMessage() {
		if(super.getMessage()==null) {
			return "操作的数据已被他人修改";
		}else {
			return super.getMessage();
		}
	}
}
