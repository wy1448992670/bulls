package com.goochou.p2b.exception;

public class RunningLowException extends Exception { 

	/*** serial id*/
	private static final long serialVersionUID = 4109764123357142460L;

	/**
	 * 
	 * 空构造
	 */
	public RunningLowException(){
		super("RunningLowException 异常");
	}
	
	/**
	 * 
	 * 自定义错误日志
	 * @param e
	 */
	public RunningLowException(String e){
		super(e);
	}
	
	/**
	 * 只抛错误信息
	 * @param e
	 */
	public RunningLowException(Throwable e){
		super(e);
	}
	/**
	 * 两者皆抛
	 * @param er
	 * @param e
	 */
	public RunningLowException(String er,Throwable e){
		super(er, e);
	}

}
