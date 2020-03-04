package com.goochou.p2b.adapter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.Response;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.model.message.ChanzorData;
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
public class ChanzorCommunicator implements ICommunicator {

	private static final Logger logger = Logger
			.getLogger(ChanzorCommunicator.class);

	private String remoteURL;
	
	private String account;
	
	private String password;

    @Override
    public Response httSend2(Request request, String method) throws CommunicateException {
        return null;
    }

    // 通过网络与服务器建立连接的超时时间
	private int connectionTimeout = 30000;

	// Socket读数据的超时时间
	private int soTimeout = 45000;

	@Override
	public String httSend(String data, String method, String requestType)
			throws CommunicateException {
		JSONObject jsonParam = JSONAlibabaUtil.parseString(data);
		String phones = jsonParam.getString("phones");
		String content = jsonParam.getString("content");
		String result = null;
		try {
			result = JSONArray.toJSONString(send(content, phones));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	private final static int MAX_SEND = 5000;
    private final static String SEND_TYPE = "send";

    /**
     * 发送信息
     *
     * @param content 短信内容
     * @param phones  电话字符串 多个电话字符串用英文逗号（","）隔开
     * @return
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     * @author
     * @date 2015年12月1日
     * @parameter
     */
    public List<ChanzorData> send(String content, String phones) throws URISyntaxException, ClientProtocolException, IOException {
    	List<ChanzorData> result = new ArrayList<ChanzorData>();
    	try {
    		List<String> phoneList = Arrays.asList(phones.split(","));
            List<String> list = new ArrayList<String>();
            if (phoneList.size() >= MAX_SEND) {
                int count = phoneList.size() / MAX_SEND;
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < count; i++) {
                    List<String> abc = phoneList.subList(i * MAX_SEND, (i + 1) * MAX_SEND);
                    for (String s : abc) {
                        if (buffer.length() > 0) {
                            buffer.append("," + s);
                        } else {
                            buffer.append(s);
                        }
                    }
                    list.add(buffer.toString());
                    buffer.delete(0, buffer.length());
                }
                if (phoneList.size() % MAX_SEND != 0) {
                    List<String> abc = phoneList.subList(count * MAX_SEND, phoneList.size());
                    for (String s : abc) {
                        if (buffer.length() > 0) {
                            buffer.append("," + s);
                        } else {
                            buffer.append(s);
                        }
                    }
                    list.add(buffer.toString());
                    buffer.delete(0, buffer.length());
                }
            } else {
                list.add(phones);
            }
            result = sendList(list, content);
		} catch (Exception e) {
			logger.error("发送短信失败!!", e);
		}
        return result;

//		String[] phoneList = phones.split(",");
//		List<String> list = new ArrayList<String>();
//		if(phoneList.length >= MAX_SEND){
//			StringBuffer buffer = new StringBuffer();
//			for(int i = 1; i < phoneList.length; i++){
//				if(i % MAX_SEND == 0 || i == phoneList.length-1){
//					if(buffer.length() > 0){
//						buffer.append(","+phoneList[i]);
//					}else{
//						buffer.append(phoneList[i]);
//					}
//					list.add(buffer.toString());
//					buffer.delete(0, buffer.length());
//				}else{
//					if(buffer.length() > 0){
//						buffer.append(","+phoneList[i]);
//					}else{
//						buffer.append(phoneList[i]);
//					}
//				}
//			}
//		}else{
//			list.add(phones);
//		}
//		sendList(list, content);
    }

    /**
     * 私有方法
     *
     * @param list
     * @param content
     * @return
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     * @author
     * @date 2015年12月11日
     * @parameter
     */
    private List<ChanzorData> sendList(List<String> list, String content) throws URISyntaxException, ClientProtocolException, IOException {
        List<ChanzorData> resList = new ArrayList<ChanzorData>();
        for (String str : list) {
            //httpPost方式发送，可以在请求体中添加跟多参数，使用时效率比get稍慢，但数量更多
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            HttpPost method = new HttpPost(this.remoteURL);
            CloseableHttpClient client = HttpClients.createDefault();
            NameValuePair pair = new BasicNameValuePair("account", this.account);
            NameValuePair pair2 = new BasicNameValuePair("password", this.password);
            NameValuePair pair3 = new BasicNameValuePair("mobile", str);
            NameValuePair pair4 = new BasicNameValuePair("content", content);
            NameValuePair pair5 = new BasicNameValuePair("action", SEND_TYPE);
            paramList.add(pair);
            paramList.add(pair2);
            paramList.add(pair3);
            paramList.add(pair4);
            paramList.add(pair5);
            method.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
            CloseableHttpResponse response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                str = EntityUtils.toString(entity);
                JSONObject obj = JSONAlibabaUtil.parseString(str);
                ChanzorData cd = (ChanzorData) JSONAlibabaUtil.toBean(obj, ChanzorData.class);
                resList.add(cd);
            }
            System.out.println(resList.toString());
            logger.info(" ======================> " + resList.toString() + " <===================== ");
            //HTTPGET方式发送，较少内容时使用
//			URIBuilder builder = new URIBuilder("http://sms.chanzor.com:8001/sms.aspx");
//			builder
//			.addParameter("account", "quanminlicai-yx")
//			.addParameter("password", "153472")
//			.addParameter("mobile", str)
//			.addParameter("content", content)
//			.addParameter("action", "send");
//			HttpClient httpclient = new DefaultHttpClient();
//			HttpGet get = new HttpGet(builder.build().toString());
//			HttpResponse response = httpclient.execute(get);
//			HttpEntity entity = response.getEntity();
//			String res = EntityUtils.toString(entity);
//			LogUtil.infoLogs("短信平台返回信息 Date:"+DateFormatTools.dateToStr1(new Date())+"\n" + res);

        }
        return resList;
    }

	public String getRemoteURL() {
		return remoteURL;
	}

	public void setRemoteURL(String remoteURL) {
		this.remoteURL = remoteURL;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getSoTimeout() {
		return soTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}
	
	public String getAccount() {
		return account;
	}


	public void setAccount(String account) {
		this.account = account;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

}