package com.goochou.p2b.hessian;

import com.goochou.p2b.annotationin.FieldMeta;


public class InterFaceRequest extends Request {

	/**
	 * <p>Discription:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = 8509718139541397723L;

	@FieldMeta(description="接口名称")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
