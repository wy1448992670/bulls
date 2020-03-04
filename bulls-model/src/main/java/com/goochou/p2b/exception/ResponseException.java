package com.goochou.p2b.exception;

import com.goochou.p2b.hessian.Response;

public class ResponseException extends Exception { 

	private static final long serialVersionUID = -7591349015569628121L;
	private Response response;
	
	public ResponseException(){}
	
	public ResponseException(String errorCode, String errorMsg){
		response = new Response();
		response.setErrorCode(errorCode);
		response.setErrorMsg(errorMsg);
		response.setMsg(errorMsg);
	}
	public ResponseException(Response response){
		this.response = response;
	}

	@Override
	public String getMessage(){
		return response == null ? null : response.getMsg();
	}
	public ResponseException(String errorCode, String errorMsg, Throwable e){
		super(e);
		response = new Response();
		response.setErrorCode(errorCode);
		response.setErrorMsg(errorMsg);
		response.setMsg(errorMsg);
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

}
