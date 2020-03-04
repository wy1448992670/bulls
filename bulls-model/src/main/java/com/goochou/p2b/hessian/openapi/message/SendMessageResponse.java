package com.goochou.p2b.hessian.openapi.message;

import java.util.List;

import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.model.message.ChanzorBack;


public class SendMessageResponse extends Response {

	/**
	 * <p>Discription:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = 7192617257489356963L;
	
	private List<ChanzorBack> result;

	public List<ChanzorBack> getResult() {
		return result;
	}

	public void setResult(List<ChanzorBack> result) {
		this.result = result;
	}
	
}
