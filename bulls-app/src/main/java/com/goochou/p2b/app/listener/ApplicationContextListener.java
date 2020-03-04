package com.goochou.p2b.app.listener;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.goochou.p2b.app.App;
import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.dao.AdminLogMapper;
import com.goochou.p2b.service.TmDictService;
import com.goochou.p2b.service.memcached.MemcachedManager;

/**
 * 
 * 容器启动初始化监听器
 * 
 * @author yeahdp
 * 
 */
public class ApplicationContextListener implements ServletContextListener {
	
	private static final Logger logger = Logger.getLogger(ApplicationContextListener.class);

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
		App.APP_PATH = realPath;
		// 初始化SPRING_CONTEXT
		App.SPRING_CONTEXT = WebApplicationContextUtils
				.getWebApplicationContext(servletContextEvent
						.getServletContext());
		initCache();
	}
	
	//统一初始化缓存
	private void initCache() {
		TmDictService tmDictService=(TmDictService) App.SPRING_CONTEXT.getBean("tmDictServiceImpl");
		tmDictService.doFulshCache();
		
		/*
		boolean flag = false;
		try {
			MemcachedManager memcachedManager = (MemcachedManager) App.SPRING_CONTEXT.getBean("memcachedManagerImpl");
			AdminLogMapper adminLogMapper = (AdminLogMapper) App.SPRING_CONTEXT.getBean("adminLogMapper");
            Map<String, Map<String,String>> map = new LinkedHashMap<String, Map<String,String>>();
            memcachedManager.delete(Constants.DICTS);
            List<Map<String, Object>> dicts = adminLogMapper.selectDicts();
            if(null != dicts && dicts.size()>0){
                for(Map<String, Object> dict : dicts){
                    Map<String,String> value = new LinkedHashMap<String, String>();
                	if(map.get(dict.get("t_key")+"")!=null){
                        map.get(dict.get("t_key")+"").put(dict.get("t_name")+"", dict.get("t_value")+"");
                    }else{
                        value.put(dict.get("t_name")+"", dict.get("t_value")+"");
                        map.put(dict.get("t_key")+"", value);
                    }
                }
            }
            flag = memcachedManager.add(Constants.DICTS,map,0);
            if(!flag){
            	System.out.println("初始化memcached.server1.host "+Constants.DICTS+" 失败");
	        }else{
	            System.out.println("初始化memcached.server1.host "+Constants.DICTS+" 成功..");
	        }
        }
        catch (Exception e)
        {
        	throw new RuntimeException("初始化缓存异常", e);
        }*/
    }
}
