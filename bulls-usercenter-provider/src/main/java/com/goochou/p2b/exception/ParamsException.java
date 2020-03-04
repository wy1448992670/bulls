package com.goochou.p2b.exception;


public class ParamsException extends Exception{

	/**
	 * <p>Discription:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = -6368823336810056987L;
	
	public ParamsException() {
	}

	public ParamsException(Exception e) {
		super(e);
	}

	public ParamsException(String s) {
		super(s);
	}

}
