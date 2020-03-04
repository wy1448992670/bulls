package com.goochou.p2b.exception;

public class ManagerParamException extends ManagerException { 

	/*** serial id*/
	private static final long serialVersionUID = 4109764123357142460L;

	/**
	 * 
	 * 空构造
	 */
	public ManagerParamException(){
		super("DaoException 异常");
	}
	
	/**
	 * 
	 * 自定义错误日志
	 * @param e
	 */
	public ManagerParamException(String e){
		super(e);
	}
	
	/**
	 * 只抛错误信息
	 * @param e
	 */
	public ManagerParamException(Throwable e){
		super(e);
	}
	/**
	 * 两者皆抛
	 * @param er
	 * @param e
	 */
	public ManagerParamException(String er,Throwable e){
		super(er, e);
	}

}
