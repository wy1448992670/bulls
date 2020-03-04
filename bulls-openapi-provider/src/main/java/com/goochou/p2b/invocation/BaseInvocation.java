package com.goochou.p2b.invocation;

import java.util.Map;

import com.goochou.p2b.hessian.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.adapter.CommunicateException;
import com.goochou.p2b.adapter.IRemote;
import com.goochou.p2b.adapter.StringTeletext;
import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.utils.JSONAlibabaUtil;

/**
 * Created on 2014-8-28
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [实现类基类]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class BaseInvocation {
	
    protected final Logger logger = Logger.getLogger(this.getClass());
    
    @Autowired
    protected IRemote remote;
    
//    @Autowired
//    protected IMemcachedManager cache;


    /**
     * 发送请求报文
     * @param request
     * @param method
     * @param communicator
     * @return
     * @throws CommunicateException
     * @throws Exception
     */
    protected Response getRsp(Request request, String method, String communicator) throws CommunicateException, Exception {
        Response rspJson = remote.callRemote2(new StringTeletext(request,method, communicator));
        logger.debug(" ======接收报文 ====== \n" + rspJson);
        return rspJson;
    }
    
    /**
     * 发送请求报文
     * @param accessId
     * @param params
     * @param template 报文模板
     * @return
     * @throws CommunicateException
     * @throws Exception
     */
    protected String getRspXML(Request request, String method, String communicator) throws CommunicateException, Exception {
        String reqStr;
        String rspJson = "";
        reqStr = toJSONString(request);
        if (null != reqStr && !"".equals(reqStr)) {
            rspJson = (String) remote.callRemote(new StringTeletext(reqStr,method, communicator));
            logger.debug(" ======接收报文 ====== \n" + rspJson);
        }
        return rspJson;
    }
    
    protected String getJSONString(Map<String, Object> map) {
        return JSONArray.toJSONString(map);
    }
    
    protected JSONObject parseJSONString(String jsonString) {
        return JSONAlibabaUtil.parseString(jsonString);
    }
    
    protected String toJSONString(Request request) {
    	String jsonStr = "";
    	try {
    		if (request != null){
    			jsonStr = JSON.toJSONString(request);
    		}
		} catch (Exception e) {
			 logger.error("BaseInvocation => toJSONString : " + e);
		}
    	return jsonStr;
    }
    
    /**
	 * 
	 *  Created on 2015年12月3日 
	 * <p>Discription:[获取字典值]</p>
	 * @author:[李旭东]
	 * @update:[日期2015年12月3日] [李旭东]
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
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @return String .
     */
//    public String getCacheKeyValue(String key){
//        String val = null;
//        if(null != getCacheValue(key)){
//            Map<String, String> map = (HashMap<String, String>)getCacheValue(key);
//            Set<String> set = map.keySet();
//            val = map.get(set.toArray()[0]);
//        }
//        return val;
//    }
    
}
