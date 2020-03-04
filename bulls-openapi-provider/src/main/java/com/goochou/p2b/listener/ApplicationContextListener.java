package com.goochou.p2b.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.exception.IException;

/**
 * 
 * 容器启动初始化监听器
 * 
 * @author yeahdp
 * 
 */
public class ApplicationContextListener implements ServletContextListener {
	
	private final static Log logger = LogFactory.getLog(ApplicationContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		logger.info("servlet上下文销毁!");
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		logger.info("正在初始化上下文监听器");
		String realPath = servletContextEvent.getServletContext().getRealPath(
				"");
		if (!realPath.endsWith("/")) {
			realPath += "/";
		}
		OpenApiApp.APP_PATH = realPath;
		// 初始化SPRING_CONTEXT
		OpenApiApp.SPRING_CONTEXT = WebApplicationContextUtils
				.getWebApplicationContext(servletContextEvent
						.getServletContext());
		OpenApiApp.EXCEPTION=(IException)(OpenApiApp.SPRING_CONTEXT.getBean("exception"));
	}
}
