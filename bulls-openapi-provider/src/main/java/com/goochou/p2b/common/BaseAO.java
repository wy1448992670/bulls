/**
 *
 */
package com.goochou.p2b.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;

import com.goochou.p2b.exception.IException;

/**
 * Ao基类
 * Created on 2014-8-11
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [描述该类概要功能介绍]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class BaseAO extends ApplicationObjectSupport {
	protected  final   Log log =LogFactory.getLog(this.getClass().getName());
	
	@Autowired
	protected IException exception;
	
	/**
     * Memcached
     */
//    @Autowired
//    protected MemcachedClient cache;
    
    /**
     *  Created on 2014-8-21 
     * <p>Discription:[获取缓存]</p>
     * @author:[叶东平]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @return Object .
     */
//    @SuppressWarnings("rawtypes")
//	public Object getCacheValue(String key) {
//		Object obj = null;
//		try {
//			Map map = cache.get(Constants.DICTS);
//			obj = map.get(key);
//		} catch (Exception e) {
//			log.error("缓存键值["+key+"]获取失败", e);
//		}
//		return obj;
//	}
	/**
	 * 
	 *  Created on 2015年11月9日 
	 * <p>Discription:[方法功能中文描述]</p>
	 * @author:[李旭东]
	 * @update:[日期2015年11月9日] [李旭东]
	 * @return Object .
	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public Object cacheValue(String key) {
//		Map cacheMap = (Map) getCacheValue(key);
//		if (cacheMap == null) return null;
//		Set<String> set = cacheMap.keySet();
//		return cacheMap.get(set.toArray()[0]);
//	}

	public IException getException() {
		return exception;
	}

	public void setException(IException exception) {
		this.exception = exception;
	}
	

//	public String getDictsValue(String key){
//        Map map = null;
//		try {
//			map = (Map) cache.get(Constants.DICTS);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//        if (map == null) return null;
//        
//        map = (Map)map.get(key);
//        if (map == null) return null;
//
//		Set set = map.keySet();
//        if (set == null) return null;
//        
//		return (String) map.get(set.toArray()[0]);
//    }
}
