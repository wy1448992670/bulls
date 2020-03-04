package com.goochou.p2b.hessian;

import java.util.List;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.model.vo.InterfaceVO;


public class InterFaceResponse extends Response {

	/**
	 * <p>Discription:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = 2545670024154614485L;

	@FieldMeta(description="接口列表")
	public List<InterfaceVO> interfaces;

	public List<InterfaceVO> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<InterfaceVO> interfaces) {
		this.interfaces = interfaces;
	}
}
