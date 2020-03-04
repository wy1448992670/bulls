package com.goochou.p2b.adapter;

import java.util.UUID;

import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.Response;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dahantc.api.sms.json.JSONHttpClient;
import com.goochou.p2b.utils.JSONAlibabaUtil;

/**
 * Created on 2019年5月8日
 * <p>Title:       [描述该类概要功能介绍]</p>
 * <p>Company:     奔富畜牧</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * <p>Department:  研发中心</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class Dh3tCommunicator implements ICommunicator {

	private static final Logger logger = Logger
			.getLogger(Dh3tCommunicator.class);

	public static String account = "dh31339";// 用户名（必填）
	public static String password = "4nk3TqLC";// 密码（必填）
	public static String sign = "【奔富牧业】"; // 短信签名（必填）
	public static String subcode = ""; // 子号码（可选）
	public static String msgid = UUID.randomUUID().toString().replace("-", ""); // 短信id，查询短信状态报告时需要，（可选）
	public static String sendtime = ""; // 定时发送时间（可选）
	
	public final static Integer SEND_TYPE_SYSTEM = 1; // 系统短信
	public final static Integer SEND_TYPE_MARKETING = 2; // 营销短信

	@Override
	public String httSend(String data, String method, String requestType)
			throws CommunicateException {
		JSONObject jsonParam = JSONAlibabaUtil.parseString(data);
		String phones = jsonParam.getString("phones");
		String content = jsonParam.getString("content");
		boolean market = jsonParam.getBoolean("market");
		int result = 0;
		try {
		    if(market) {
		        send(phones, content, SEND_TYPE_MARKETING);
		    }else {
		        send(phones, content, SEND_TYPE_SYSTEM);
		    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Response httSend2(Request request, String method) throws CommunicateException {
		return null;
	}

	public static void send(String phone, String message, Integer sendType, Integer...retry){
		// 重发机制
		if(retry == null || retry.length == 0){
			retry = new Integer[]{1};
		}else{
			retry[0] += 1; 
		}
		
		if(retry[0] > 3){
			logger.info(String.format("大汉sms：【%s】,【%s】；发送失败，超过重试次数%s", phone, message, retry[0]));
			return;
		}
		
		if(SEND_TYPE_SYSTEM.equals(sendType)){
			account = "dh31339";
			password = "4nk3TqLC";
		}
		else if(SEND_TYPE_MARKETING.equals(sendType)){
			account = "dh31340";
			password = "KQo0o1g9";
		}
		
		try {
			JSONHttpClient jsonHttpClient = new JSONHttpClient("http://www.dh3t.com");
			jsonHttpClient.setRetryCount(1);
			JSONObject result = JSON.parseObject(jsonHttpClient.sendSms(account, password, phone, message, sign, subcode));
			
			logger.info(String.format("大汉【%s】sms：【%s】,【%s】；大汉结果【%s】", account, phone, message, result));
			if(!"0".equals(result.get("result"))){
				send(phone, message, sendType, retry[0]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(String.format("大汉sms：【%s】,【%s】；发送失败【%s】", phone, message, e.getMessage()));
		}
	}
	
	public static void main(String[] args) {
		send("15001824049", "这是一条营销短信", SEND_TYPE_MARKETING);
	}

}