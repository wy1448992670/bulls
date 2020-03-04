package com.goochou.p2b.constant.client;

import com.goochou.p2b.constant.ConfigHelper;


public abstract class ClientConstants {

	public static String getLableValue(String key) {
		return ConfigHelper.getDefault().getString(key);
	}
	
	public final static String OPENAPI_IP = ClientConstants.getLableValue("openapi.ip");
	public final static String OPENAPI_PORT = ClientConstants.getLableValue("openapi.port");
	public final static String OPENAPI_MODULE = ClientConstants.getLableValue("openapi.module");
	public final static String OPENAPI_USER = ClientConstants.getLableValue("openapi.user");
	public final static String OPENAPI_PWD = ClientConstants.getLableValue("openapi.pwd");
	
	public final static String USERCENTER_IP = ClientConstants.getLableValue("usercenter.ip");
	public final static String USERCENTER_PORT = ClientConstants.getLableValue("usercenter.port");
	public final static String USERCENTER_MODULE = ClientConstants.getLableValue("usercenter.module");
	public final static String USERCENTER_USER = ClientConstants.getLableValue("usercenter.user");
	public final static String USERCENTER_PWD = ClientConstants.getLableValue("usercenter.pwd");
	
	public final static String TRANSACTION_IP = ClientConstants.getLableValue("transaction.ip");
	public final static String TRANSACTION_PORT = ClientConstants.getLableValue("transaction.port");
	public final static String TRANSACTION_MODULE = ClientConstants.getLableValue("transaction.module");
	public final static String TRANSACTION_USER = ClientConstants.getLableValue("transaction.user");
	public final static String TRANSACTION_PWD = ClientConstants.getLableValue("transaction.pwd");
	
	public final static String APP_ROOT = ClientConstants.getLableValue("app.root");
	/**
	 * 上传路径外网URL
	 */
	public final static String ALIBABA_PATH = ClientConstants.getLableValue("upload.url");
	/**
	 * H5域名
	 */
	public final static String H5_URL = ClientConstants.getLableValue("h5.url");
}
