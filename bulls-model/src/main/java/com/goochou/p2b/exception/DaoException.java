package com.goochou.p2b.exception;
/**
 * DAO 异常类
 * 
 * @author mike
 * @update by liuboen 
 */
public class DaoException extends Exception {

	/*** serial id */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 空构造
	 */
	public DaoException(){
		super("DaoException 异常");
	}
	
	/**
	 * 
	 * 自定义错误日志
	 * @param e
	 */
	public DaoException(String e){
		super(e);
	}
	
	/**
	 * 只抛错误信息
	 * @param e
	 */
	public DaoException(Throwable e){
		super(e);
	}
	/**
	 * 两者皆抛
	 * @param er
	 * @param e
	 */
	public DaoException(String er,Throwable e){
		super(er, e);
	}
}
