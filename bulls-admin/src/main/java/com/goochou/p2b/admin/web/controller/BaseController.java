package com.goochou.p2b.admin.web.controller;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import com.goochou.p2b.service.memcached.MemcachedManager;

public abstract class BaseController {

	private static final Logger logger = Logger.getLogger(BaseController.class);

    protected static final String USERNAME = "username"; // session 中使用
    protected static final String USERID = "userId";
    protected static final String AVATAR = "avatar";
    protected static final String STATUS = "status";
    protected static final String MESSAGE = "message";
    protected static final String SUCCESS = "success";
    protected static final String ERROR = "error";

    @ExceptionHandler
    public String exp(HttpServletRequest request, Exception e) {
        logger.error(e);
        e.printStackTrace();
        return "404";
    }

    // 获取状态使用
    @InitBinder
    protected void initBinder(ServletRequestDataBinder binder) {
        CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }


    public static Integer calcPage(Integer total, Integer limit) {
        return total == 0 ? 1 : (total / limit + ((total % limit) > 0 ? 1 : 0));
    }

    public static Long calcPage(Long total, Integer limit) {
        return total == 0 ? 1 : (total / limit + ((total % limit) > 0 ? 1 : 0));
    }

    public Double getFixed2(String obj) {
        return new BigDecimal(obj).setScale(2, BigDecimal.ROUND_FLOOR).doubleValue();
    }

    public String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    @Resource
    protected MemcachedManager memcachedManager;

	public MemcachedManager getCache() {
		return memcachedManager;
	}

	public void setCache(MemcachedManager memcachedManager) {
		this.memcachedManager = memcachedManager;
	}

	/**
	 *
	 * <p>获取缓存</p>
	 * @param key
	 * @return
	 */
    @SuppressWarnings("rawtypes")
	private Object getCacheValue(String key){
        Object obj = null;
        Map map = (Map) memcachedManager.get(com.goochou.p2b.constant.Constants.DICTS);
        obj = map.get(key);
        return obj;
    }


    /**
     *
     * <p>获取键值对形式的值</p>
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
	public String getCacheKeyValue(String key){
        String val = null;
        if(getCacheValue(key) != null){
            Map<String, String> map = (HashMap<String, String>)getCacheValue(key);
            Set<String> set = map.keySet();
            val = map.get(set.toArray()[0]);
        }
        return val;
    }


}
