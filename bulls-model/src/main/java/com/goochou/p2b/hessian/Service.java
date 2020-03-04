package com.goochou.p2b.hessian;

import java.io.Serializable;

public class Service implements Serializable {
	/**
	 * <p>Discription:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = 8467982857812833925L;
	protected String serviceImpl;
	protected String request;
	protected String response;
	protected String auth;
	protected String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getServiceImpl() {
		return serviceImpl;
	}
	public void setServiceImpl(String serviceImpl) {
		this.serviceImpl = serviceImpl;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public Service(){}
}
