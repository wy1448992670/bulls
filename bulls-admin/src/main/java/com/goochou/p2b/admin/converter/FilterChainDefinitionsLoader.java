package com.goochou.p2b.admin.converter;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.stereotype.Component;

import com.goochou.p2b.admin.AdminApp;
import com.goochou.p2b.model.Resources;
import com.goochou.p2b.service.ResourcesService;

@Component
public class FilterChainDefinitionsLoader {
	private static final Logger logger = Logger.getLogger(FilterChainDefinitionsLoader.class);
	
    @Resource
    private ResourcesService resourcesService;

    /**
     * 初始化从数据库中读取所有的权限列表
     *
     * @return
     */
    public String loadDefinitions() {
        List<Resources> list = resourcesService.findAllResource();
        StringBuilder sb = new StringBuilder();
        for (Resources r : list) {
            sb.append(r.getUrl() + "=" + "authc,perms[" + r.getPermission() + "]\r\n");
        }
        logger.info("============加载所有的权限===============" + sb.toString() + "===========================");
        return sb.toString();
    }

    public void changeFilters() {
        DefaultFilterChainManager manager = getDefaultFilterChainManager();
        manager.getFilterChains().clear();
        List<Resources> list = resourcesService.findAllResource();
        for (Resources r : list) {
            manager.createChain(r.getUrl(), "authc,perms[" + r.getPermission() + "]");
        }
    }

    /**
     * 获取默认的filterchain的实例
     *
     * @return
     */
    private static DefaultFilterChainManager getDefaultFilterChainManager() {
        AbstractShiroFilter r = AdminApp.SPRING_CONTEXT.getBean(AbstractShiroFilter.class);
        PathMatchingFilterChainResolver res =
                (PathMatchingFilterChainResolver) r.getFilterChainResolver();
        return (DefaultFilterChainManager) res.getFilterChainManager();
    }
}
