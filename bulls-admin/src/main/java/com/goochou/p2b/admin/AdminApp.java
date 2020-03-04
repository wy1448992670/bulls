package com.goochou.p2b.admin;

import org.springframework.context.ApplicationContext;

import com.goochou.p2b.exception.IException;

public class AdminApp {
	/**
	 * 编码
	 */
	public static final String CHARSET = "UTF-8";

	/**
	 * 项目路径
	 */
	public static String APP_PATH = "";

	/**
	 * SPRING容器
	 */
	public static ApplicationContext SPRING_CONTEXT = null;
	
	public static IException EXCEPTION = null;
}
