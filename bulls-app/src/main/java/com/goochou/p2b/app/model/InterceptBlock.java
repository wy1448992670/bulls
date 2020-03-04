package com.goochou.p2b.app.model;

public class InterceptBlock {
	private boolean isIntercept;// 是否需要拦截
	private String title;
	private String msg;
	private String key;// app的key+参数
	
	public InterceptBlock() {
		this.isIntercept=false;
	}
	
	public boolean isIntercept() {
		return isIntercept;
	}
	public void setIntercept(boolean isIntercept) {
		this.isIntercept = isIntercept;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
}
