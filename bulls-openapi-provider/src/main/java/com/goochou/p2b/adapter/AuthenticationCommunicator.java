package com.goochou.p2b.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.Response;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.utils.JSONAlibabaUtil;
import com.goochou.p2b.utils.pyzx.bean.QueryCondition;
import com.goochou.p2b.utils.pyzx.bean.QueryConditions;
import com.goochou.p2b.utils.pyzx.config.PyConfig;
import com.goochou.p2b.utils.pyzx.util.HttpUtils;
import com.goochou.p2b.utils.pyzx.util.PyUtils;

/**
 * Created on 2019年5月8日
 * <p>Title:       [描述该类概要功能介绍]</p>
 * <p>Company:     奔富畜牧</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * <p>Department:  研发中心</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class AuthenticationCommunicator implements ICommunicator {

	private static final Logger logger = Logger
			.getLogger(AuthenticationCommunicator.class);

	@Override
	public Response httSend2(Request request, String method) throws CommunicateException {
		return null;
	}

	@Override
	public String httSend(String data, String method, String requestType)
			throws CommunicateException {
		JSONObject jsonParam = JSONAlibabaUtil.parseString(data);
		String username = jsonParam.getString("username");
		String idNo = jsonParam.getString("idNo");
		String res = "";
		try {
			String result = requestApi(PyConfig.HOST, PyConfig.PATH_ZIP, username, idNo);
			logger.info("host:"+PyConfig.HOST);
			logger.info("api result = "+result);
			// 对压缩文本做进一步处理
	        Map<String, Object> resultMap = JSON.parseObject(result);
	        String status = (String) resultMap.get("status");
	        if ("1".equals(status)) {
	            String returnValue = (String) resultMap.get("returnValue");
	            returnValue = PyUtils.decodeAndDecompress(returnValue, "UTF-8");
	            logger.info("returnValue:" + returnValue);
	            JSONObject obj = JSONAlibabaUtil.parseString(returnValue);
	            JSONArray jsonArray = obj.getJSONArray("cisReport");
	            for(int i = 0;i < jsonArray.size();i ++){
	            	JSONObject json = (JSONObject) jsonArray.get(i);
	            	String policeCheckInfo = json.getString("policeCheckInfo");
	            	JSONObject policeCheckInfoObj = JSONAlibabaUtil.parseString(policeCheckInfo);
	            	String item = policeCheckInfoObj.getString("item");
	            	JSONObject itemObj = JSONAlibabaUtil.parseString(item);
	            	res = itemObj.getString("result");
	            	logger.info(res);
	            }
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public static String requestApi(String host, String path, String username, String idNo) throws Exception {

        // https双向认证使用,配合PySSLContextUtil中的DefaultSSLContext
//        System.setProperty("javax.net.debug", "all");
//        System.setProperty("javax.net.ssl.keyStore", PyConfig.KEYSTORE_FILE);
//        System.setProperty("javax.net.ssl.keyStorePassword", PyConfig.KEYSTORE_PASSWORD);
//        System.setProperty("javax.net.ssl.trustStore", PyConfig.TRUSTSTORE_FILE);
//        System.setProperty("javax.net.ssl.trustStorePassword", PyConfig.TRUSTSTORE_PASSWORD);

        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> querys = null;
        Map<String, String> bodys = new HashMap<String, String>();
		//默认请求条件是JSON格式.如果请求条件是xml，需要指定格式。
		//bodys.put("format","xml")
        bodys.put("userID", PyConfig.USERID);
        bodys.put("password", PyConfig.PASSWORD);
        bodys.put("queryCondition", getQueryCondition(username, idNo));
        HttpResponse response = HttpUtils.doPost(host, path, "POST", headers, querys, bodys);
        String result = EntityUtils.toString(response.getEntity());
        return result;
    }

    private static String getQueryCondition(String username, String idNo) throws Exception {
    	// 使用JavaBean/Map方式（正式使用,仅供参考）
        QueryConditions queryConditions = new QueryConditions();
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        QueryCondition queryCondition = new QueryCondition();
        // 查询类型
        queryCondition.setQueryType("25160");
        List<QueryCondition.Item> items = new ArrayList<QueryCondition.Item>();
        // 收费子报告
        items.add(new QueryCondition.Item("subreportIDs", "10602"));
        // 查询原因
        items.add(new QueryCondition.Item("queryReasonID", "701"));
        // 业务流水号
        items.add(new QueryCondition.Item("refID", ""));
        // 具体查询条件
        items.add(new QueryCondition.Item("corpName", "内蒙古奔富畜牧业发展有限公司"));
        //姓名
        items.add(new QueryCondition.Item("name", username));
        //身份证
        items.add(new QueryCondition.Item("documentNo", idNo));

        queryCondition.setItems(items);
        conditions.add(queryCondition);
        queryConditions.setConditions(conditions);
        return JSON.toJSONString(queryConditions);
    }
    
    public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir")+"/src/main/resources/cert/");
	}
}