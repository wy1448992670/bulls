package com.goochou.p2b.admin.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.goochou.p2b.admin.AdminApp;
import com.goochou.p2b.service.TmDictService;

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
		AdminApp.APP_PATH = realPath;
		// 初始化SPRING_CONTEXT
		AdminApp.SPRING_CONTEXT = WebApplicationContextUtils
				.getWebApplicationContext(servletContextEvent
						.getServletContext());
		initCache();
	}
	
	//统一初始化缓存
	private void initCache() {
		
		TmDictService tmDictService=(TmDictService) AdminApp.SPRING_CONTEXT.getBean("tmDictServiceImpl");
		tmDictService.doFulshCache();
		/*
		boolean flag = false;
		try {
			MemcachedManager memcachedManager = (MemcachedManager) AdminApp.SPRING_CONTEXT.getBean("memcachedManagerImpl");
			AdminLogMapper adminLogMapper = (AdminLogMapper) AdminApp.SPRING_CONTEXT.getBean("adminLogMapper");
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
                        System.out.println(dict.get("t_key"));
                    }
                }
            }
            flag = memcachedManager.add(Constants.DICTS,map,0);
            if(!flag){
            	System.out.println("初始化memcached.server1.host "+Constants.DICTS+" 失败");
	        }else{
	            System.out.println("初始化memcached.server1.host "+Constants.DICTS+" 成功..");
	        }
            
            ActivityService activityService = (ActivityService) AdminApp.SPRING_CONTEXT.getBean("activityServiceImpl");
            activityService.flushCacheActionActivityDetailMap();
            System.out.println("初始化memcached.server1.host "+Constants.ACTIVITY_DETAIL_LIST_MAP+" 成功..");

        }
        catch (Exception e)
        {
        	throw new RuntimeException("初始化缓存异常", e);
        }*/
    }
}
