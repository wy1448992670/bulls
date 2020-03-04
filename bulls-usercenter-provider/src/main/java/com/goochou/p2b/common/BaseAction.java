/**
 * 
 */
package com.goochou.p2b.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.utils.CommonUtil;

/**
 * ACTION基类
 * Created on 2014-8-11
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [描述该类概要功能介绍]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class BaseAction {
	protected  final   Log log =LogFactory.getLog(this.getClass().getName());
    
    /**
     * Memcached
     */
//    @Autowired
//    protected IMemcachedManager cache;
    
    
    @Autowired
    protected MessageSource messageSource;
	/**
	 * 输出JOSN字符串
	 * @param response
	 * @param jsonStr
	 */
	protected void outResult(HttpServletResponse response, String jsonStr) {
		try {
			response.setContentType("text/plain;charset="+CommonUtil.CHARSET);
			response.getWriter().print(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出JOSN对象
	 * @param response
	 * @param jsonObj
	 */
	public void outResult(HttpServletResponse response, JSONObject jsonObj) {
		response.setContentType("text/plain;charset="+CommonUtil.CHARSET);
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.print(jsonObj.toString());
		out.flush();
		out.close();
	}

	/**
	 * 输出JSONArray
	 * @param response
	 * @param jsonObj
	 */
	public void outResult(HttpServletResponse response, JSONArray jsonObj) {
		response.setContentType("text/plain;charset="+CommonUtil.CHARSET);
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.print(jsonObj.toString());
		out.flush();
		out.close();
	}

	/**
	 * 输出RESULT基类
	 * @param response
	 * @param result
	 */
	public void outResult(HttpServletResponse response, Object result) {
		response.setContentType("text/plain;charset="+CommonUtil.CHARSET);
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String str = JSONArray.toJSONString(result);
		out.print(str);
		out.flush();
		out.close();
	}
	
	/**
     * 输出服务（接口）基类
     * @param response
     * @param result
     */
    public void outResult(HttpServletResponse response, Response result) {
        response.setContentType("text/plain;charset="+CommonUtil.CHARSET);
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = JSONArray.toJSONString(result);
        out.print(str);
        out.flush();
        out.close();
    }
    
    

    /**
     *  Created on 2014-8-21 
     * <p>Discription:[获取缓存]</p>
     * @author:[叶东平]
     * @update:[日期2016-6-15] [李旭东][空指针异常处理]
     * @return Object .
     */
//    public Object getCacheValue(String key){
//        Map map = (Map) cache.get(Constants.DICTS);
//        if (map == null) return null;
//        return map.get(key);
//    }
    
    /**
     * 
     *  Created on 2014年9月24日 
     * <p>Discription:[获取键值对形式的值]</p>
     * @author:[马宗飞]
     * @update:[日期2016-6-15] [李旭东][空指针异常处理]
     * @return String .
     */
//	public String getCacheKeyValue(String key) {
//
//		if (null == getCacheValue(key)) return null;
//
//		Map map = (Map) getCacheValue(key);
//		if (map == null) return null;
//		Set set = map.keySet();
//		return  (String) map.get(set.toArray()[0]);
//    }
}
