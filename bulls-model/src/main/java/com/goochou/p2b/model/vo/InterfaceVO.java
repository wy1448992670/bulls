package com.goochou.p2b.model.vo;

import java.io.Serializable;

import com.goochou.p2b.hessian.Service;


public class InterfaceVO implements Serializable {
	/**
	 * <p>Discription:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = -1456667053337440137L;
	private String name;
	private Service service;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	public InterfaceVO(){}
	public InterfaceVO(String name, Service service) {
		this.name = name;
		this.service = service;
	}
}
