package com.goochou.p2b.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.goochou.p2b.hessian.Request;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fuiou.mpay.encrypt.DESCoderFUIOU;
import com.fuiou.mpay.encrypt.RSAUtils;
import com.fuiou.util.MD5;
import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.openapi.pay.FuiouBindCardResponse;
import com.goochou.p2b.hessian.openapi.pay.FuiouDataResponse;
import com.goochou.p2b.model.pay.fuiou.BankCardResqData;
import com.goochou.p2b.model.pay.fuiou.CheckCardReqData;
import com.goochou.p2b.model.pay.fuiou.OrderReqData;
import com.goochou.p2b.model.pay.fuiou.OrderRespData;
import com.goochou.p2b.model.pay.fuiou.PayOrderRespData;
import com.goochou.p2b.model.pay.fuiou.PayReqData;
import com.goochou.p2b.model.pay.fuiou.PayRespData;
import com.goochou.p2b.model.pay.fuiou.QuickPayOrderReqData;
import com.goochou.p2b.model.pay.fuiou.QuickPayOrderRespData;
import com.goochou.p2b.model.pay.fuiou.WithdrawResData;
import com.goochou.p2b.utils.JSONAlibabaUtil;
import com.goochou.p2b.utils.Money;
import com.goochou.p2b.utils.XmlBeanUtils;
import com.goochou.p2b.utils.http.HttpPostUtil;

/**
 * Created on 2019年5月8日
 * <p>Title:       [描述该类概要功能介绍]</p>
 * <p>Company:     奔富畜牧</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * <p>Department:  研发中心</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class FuiouCommunicator implements ICommunicator {

	private static final Logger logger = Logger
			.getLogger(FuiouCommunicator.class);

	@Override
	public Response httSend2(Request request, String method) throws CommunicateException {
		return null;
	}

	private String remoteURL;

	// 通过网络与服务器建立连接的超时时间
	private int connectionTimeout = 30000;

	// Socket读数据的超时时间
	private int soTimeout = 45000;

	@Override
	public String httSend(String data, String method, String requestType)
			throws CommunicateException {
		String result = "";
		String version = null;
		String type = null;
		String signPlain = null;
        String sign = null;
        String userId = null;
        String ip = null;
        String amt = null;
		String bankCard = null;
		String name = null;
		String idNo = null;
		String mobile = null;
		String orderId = null;
        String idType = null;
        String userIp = null;
        String cvn = null;
        String rem1 = null;
        String rem2 = null;
        String rem3 = null;
        // 发送内容
     	JSONObject jsonParam = JSONAlibabaUtil.parseString(data);
     	OrderReqData reqData = null;
		switch(method){
		case PayConstants.METHOD_MESSAGE:
			FuiouDataResponse fdr = new FuiouDataResponse();
			fdr.setSuccess(false);
			userId = jsonParam.getString("userId");
			amt = jsonParam.getString("amount");
			bankCard = jsonParam.getString("cardNo");
			name = jsonParam.getString("trueName");
			idNo = jsonParam.getString("identityCard");
			mobile = jsonParam.getString("phone");
			ip = jsonParam.getString("ip");
			orderId = jsonParam.getString("outOrderNo");
			
	        idType = "0";
	        cvn = "";
	        version = "2.3";
	        type = "03";
	        rem1 = "备注1";
	        rem2 = "备注2";
	        rem3 = "备注3";
	        signPlain = type + "|" + version + "|" + PayConstants.FUIOU_QUICK_MCHNT_CD + "|" + orderId + "|" + userId + "|"
	                + amt + "|" + bankCard + "|" + PayConstants.ADVITE_URL + "|" + name + "|" + idNo + "|" + idType + "|" + mobile
	                + "|" + ip + "|" + PayConstants.FUIOU_QUICK_MCHNT_KEY;
	        sign = MD5.MD5Encode(signPlain);
	        System.out.println("[签名明文:]" + signPlain);
	        reqData = new OrderReqData();
	        reqData.setVersion(version);
	        reqData.setMchntcd(PayConstants.FUIOU_QUICK_MCHNT_CD);
	        reqData.setType(type);
	        reqData.setOrderId(orderId);
	        reqData.setUserid(userId);
	        reqData.setAmt(amt);
	        reqData.setBankcard(bankCard);
	        reqData.setName(name);
	        reqData.setBackurl(PayConstants.ADVITE_URL);
	        reqData.setIdtype(idType);
	        reqData.setIdno(idNo);
	        reqData.setMobile(mobile);
	        reqData.setCvn(cvn);
	        reqData.setRem1(rem1);
	        reqData.setRem2(rem2);
	        reqData.setRem3(rem3);
	        reqData.setSigntp("md5");
	        reqData.setUserip(ip);
	        reqData.setSign(sign);
			try {
				String orderPlain = XmlBeanUtils.convertBean2Xml(reqData, "UTF-8", true);
		        System.out.println("[订单信息:]" + orderPlain);
				Map<String, String> param = new HashMap<String, String>();
		        param.put("MCHNTCD", PayConstants.FUIOU_QUICK_MCHNT_CD);
		        param.put("APIFMS",
		                DESCoderFUIOU.desEncrypt(orderPlain, DESCoderFUIOU.getKeyLength8(PayConstants.FUIOU_QUICK_MCHNT_KEY)));
		        System.out.println("[请求信息:]" + param);
				String respStr = HttpPostUtil.postForward(this.remoteURL+method, param);
		        respStr = DESCoderFUIOU.desDecrypt(respStr, DESCoderFUIOU.getKeyLength8(PayConstants.FUIOU_QUICK_MCHNT_KEY));
		        OrderRespData orderRespData = XmlBeanUtils.convertXml2Bean(respStr, OrderRespData.class);
		        System.out.println("返回数据：" + orderRespData);
				logger.info("<<<" + fdr);
				if(PayConstants.RESP_CODE_SUCCESS.equals(orderRespData.getResponsecode())){
					fdr.setSuccess(true);
					fdr.setOrderRespData(orderRespData);
				}else{
					fdr.setSuccess(false);
					fdr.setErrorMsg(orderRespData.getResponsemsg());
				}
			} catch (Exception e) {
				logger.error("失败", e);
			}
			result = JSONArray.toJSONString(fdr);
			break;
		case PayConstants.METHOD_PAY:
			Response res = new Response();
			res.setSuccess(false);
			version = "2.3";
	        type = "03";
	        PayReqData payReqData = new PayReqData();
	        String cardNo = jsonParam.getString("cardNo");
	        String orderNo = jsonParam.getString("orderNo");
	        String phone = jsonParam.getString("phone");
	        orderId = jsonParam.getString("orderId");
	        String signPay = jsonParam.getString("signPay");
	        userId = jsonParam.getString("userId");
	        String verifyCode = jsonParam.getString("verifyCode");
	        ip = jsonParam.getString("ip");
	        payReqData.setBankcard(cardNo);
	        payReqData.setMchntcd(PayConstants.FUIOU_QUICK_MCHNT_CD);
	        payReqData.setMchntorderid(orderNo);
	        payReqData.setMobile(phone);
	        payReqData.setOrderid(orderId);
	        payReqData.setSignpay(signPay);
	        payReqData.setSigntp("md5");
	        payReqData.setType(type);
	        payReqData.setUserid(userId);
	        payReqData.setUserip(ip);
	        payReqData.setVercd(verifyCode);
	        payReqData.setVersion(version);
	        signPlain = payReqData.getType() + "|" + payReqData.getVersion() + "|" + PayConstants.FUIOU_QUICK_MCHNT_CD + "|"
	                + payReqData.getOrderid() + "|" + payReqData.getMchntorderid() + "|" + payReqData.getUserid() + "|"
	                + payReqData.getBankcard() + "|" + payReqData.getVercd() + "|" + payReqData.getMobile() + "|"
	                + payReqData.getUserip() + "|" + PayConstants.FUIOU_QUICK_MCHNT_KEY;
	        sign = MD5.MD5Encode(signPlain);
	        payReqData.setSign(sign);
	        logger.info("[签名明文:]" + signPlain);
			try {
				String apiFms = XmlBeanUtils.convertBean2Xml(payReqData);
		        logger.info("[订单信息:]" + apiFms);
		        Map<String, String> param = new HashMap<String, String>();
		        param.put("MCHNTCD", PayConstants.FUIOU_QUICK_MCHNT_CD);
		        param.put("APIFMS", DESCoderFUIOU.desEncrypt(apiFms, DESCoderFUIOU.getKeyLength8(PayConstants.FUIOU_QUICK_MCHNT_KEY)));
		        logger.info("[请求信息:]" + param);
				String respStr = HttpPostUtil.postForward(this.remoteURL+method, param);
		        respStr = DESCoderFUIOU.desDecrypt(respStr, DESCoderFUIOU.getKeyLength8(PayConstants.FUIOU_QUICK_MCHNT_KEY));
		        logger.info("返回数据：" + respStr);
		        PayRespData payRespData = XmlBeanUtils.convertXml2Bean(respStr, PayRespData.class);
		        if (PayConstants.RESP_CODE_SUCCESS.equals(payRespData.getResponsecode())) {
		        	res.setSuccess(true);
	            }else{
	            	res.setSuccess(false);
	            	res.setErrorMsg(payRespData.getResponsemsg());
	            }
			} catch (Exception e) {
				logger.error("失败", e);
			}
			result = JSONArray.toJSONString(res);
			break;
		case PayConstants.METHOD_CREATE_PAY:
			FuiouDataResponse fdrCreate = new FuiouDataResponse();
			fdrCreate.setSuccess(false);
			userId = jsonParam.getString("userId");
			amt = jsonParam.getString("amount");
			bankCard = jsonParam.getString("cardNo");
			name = jsonParam.getString("trueName");
			idNo = jsonParam.getString("identityCard");
			mobile = jsonParam.getString("phone");
			ip = jsonParam.getString("ip");
			orderId = jsonParam.getString("orderNo");
	        idType = "0";
	        userIp = ip;
	        cvn = "2";
	        String indate = "123";
	        version = "2.3";
	        type = "03";
	        rem1 = "";
	        rem2 = "";
	        rem3 = "";
	        signPlain = type + "|" + version + "|" + PayConstants.FUIOU_QUICK_MCHNT_CD + "|" + orderId + "|" + userId + "|"
	                + amt + "|" + bankCard + "|" + PayConstants.ADVITE_URL + "|" + name + "|" + idNo + "|" + idType + "|" + mobile
	                + "|" + userIp + "|" + PayConstants.FUIOU_QUICK_MCHNT_KEY;
	        sign = MD5.MD5Encode(signPlain);
	        System.out.println("[签名明文:]" + signPlain);
	        reqData = new OrderReqData();
	        reqData.setVersion(version);
	        reqData.setMchntcd(PayConstants.FUIOU_QUICK_MCHNT_CD);
	        reqData.setType(type);
	        reqData.setMchntorderid(orderId);
	        reqData.setUserid(userId);
	        reqData.setAmt(amt);
	        reqData.setBankcard(bankCard);
	        reqData.setName(name);
	        reqData.setBackurl(PayConstants.ADVITE_URL);
	        reqData.setIdtype(idType);
	        reqData.setIdno(idNo);
	        reqData.setMobile(mobile);
	        reqData.setRem1(rem1);
	        reqData.setRem2(rem2);
	        reqData.setRem3(rem3);
	        reqData.setSigntp("md5");
	        reqData.setUserip(userIp);
	        reqData.setSign(sign);
	        try{
	        	String cvn2 = "";
		        if (cvn != null && !"".equals(cvn.trim()) && indate != null && "".equals(indate.trim())) {
		            cvn2 = RSAUtils.encryptByPublicKey(cvn + ";" + indate, PayConstants.FUIOU_PUB_KEY);
		        }
		        reqData.setCvn(cvn2);
	        	String orderPlain = XmlBeanUtils.convertBean2Xml(reqData, "UTF-8", true);
		        System.out.println("[订单信息:]" + orderPlain);
		        Map<String, String> param = new HashMap<String, String>();
		        param.put("MCHNTCD", PayConstants.FUIOU_QUICK_MCHNT_CD);
		        param.put("APIFMS",
		                DESCoderFUIOU.desEncrypt(orderPlain, DESCoderFUIOU.getKeyLength8(PayConstants.FUIOU_QUICK_MCHNT_KEY)));
		        System.out.println("[请求信息:]" + param);
	        	String respStr = HttpPostUtil.postForward(this.remoteURL+method, param);
		        System.out.println("返回数据：" + respStr);
		        respStr = DESCoderFUIOU.desDecrypt(respStr, DESCoderFUIOU.getKeyLength8(PayConstants.FUIOU_QUICK_MCHNT_KEY));
		        System.out.println("返回数据：" + respStr);
		        OrderRespData orderRespData = XmlBeanUtils.convertXml2Bean(respStr, OrderRespData.class);
		        System.out.println("===========" + orderRespData);
		        if(PayConstants.RESP_CODE_SUCCESS.equals(orderRespData.getResponsecode())){
		        	fdrCreate.setSuccess(true);
		        	fdrCreate.setOrderRespData(orderRespData);
				}else{
					fdrCreate.setSuccess(false);
					fdrCreate.setErrorMsg(orderRespData.getResponsemsg());
				}
	        }catch (Exception e) {
	        	logger.error("失败", e);
			}
	        result = JSONArray.toJSONString(fdrCreate);
			break;
		case PayConstants.METHOD_QUICK_QUERY_ORDER:
			result = queryQuickPayStatus(data, method);
			break;
		case PayConstants.METHOD_QUERY_ORDER:
			result = queryPayStatus(data, method);
			break;
		case PayConstants.METHOD_WITHDRAW:
			result = withdraw(data, method);
			break;
		case PayConstants.METHOD_BIND_CARD:
		    FuiouBindCardResponse resultBind = new FuiouBindCardResponse();
		    BankCardResqData resData = null;
	        String mno = jsonParam.getString("phoneNo");
	        String oCerNo = jsonParam.getString("identityCard");
	        String oCerTp = "0";
	        String onm = jsonParam.getString("trueName");
	        String ono = jsonParam.getString("bankCard");
	        String oSsn = jsonParam.getString("userId");
	        String ver = "1.30";

	        CheckCardReqData reqdata = new CheckCardReqData();
	        reqdata.setMno(mno);
	        reqdata.setOCerNo(oCerNo);
	        reqdata.setOCerTp(oCerTp);
	        reqdata.setOnm(onm);
	        reqdata.setOno(ono);
	        reqdata.setOSsn(oSsn);
	        reqdata.setVer(ver);
	        try {
    	        Map<String, String> param = new HashMap<String, String>();
    	        param.put("FM", reqdata.buildReqXml());
    	        System.out.println("请求消息：" + param);
    	        String respStr = HttpPostUtil.postForward(this.remoteURL+PayConstants.METHOD_BIND_CARD, param);
    	        System.out.println("商户支持卡Bin返回信息：" + respStr);
    	        if (respStr.length() > 0) {
    	            resData = XmlBeanUtils.convertXml2Bean(respStr, BankCardResqData.class);
    	            resultBind.setBankCardResqData(resData);
    	            resultBind.setSuccess(true);
    	        }
	        }catch (Exception e) {
	            e.printStackTrace();
            }
	        result = JSONArray.toJSONString(resultBind);
            break;
		default:
		    break;
		}
		return result;
	}

	private String withdraw(String data, String method) {
		String result = null;
		JSONObject jsonParam = JSONAlibabaUtil.parseString(data);
		String orderNo = jsonParam.getString("orderNo");
		String bankNo = jsonParam.getString("bankNo");
		String date = jsonParam.getString("date");
		String cardNo = jsonParam.getString("cardNo");
		String trueName = jsonParam.getString("trueName");
		String amount = jsonParam.getString("amount");
		String d = null;
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>" +
                "<payforreq>" +
                "<ver>1.00</ver>" +
                "<merdt>" + date + "</merdt>" +
                "<orderno>" + orderNo + "</orderno>" +
                "<bankno>" + bankNo + "</bankno>" +
                "<cityno>1000</cityno>" + //对私可以默认值1000
                //"<branchnm>中国银行股份有限公司北京西单支行</branchnm>"+
                "<accntno>" + cardNo + "</accntno>" +
                "<accntnm>" + trueName + "</accntnm>" +
                "<amt>" + new Money(amount).getCent() + "</amt>" +
                //"<txncd>05</txncd>"+
                //"<projectid>0002900F0345178_20160121_0222</projectid>"+
                //"<txncd></txncd>"+
                //"<projectid></projectid>"+
                "</payforreq>";
		logger.info(xml);
        String macSource = PayConstants.WITHDRAW_MCHNT_CD + "|" + PayConstants.WITHDRAW_MCHNT_KEY + "|" + "payforreq" + "|" + xml; //商户号+密钥+请求类型+xml
        String mac = MD5.MD5Encode(macSource).toUpperCase();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("merid", PayConstants.WITHDRAW_MCHNT_CD));
        params.add(new BasicNameValuePair("reqtype", "payforreq"));
        params.add(new BasicNameValuePair("xml", xml));
        params.add(new BasicNameValuePair("mac", mac));
        try{
	        String jsonStr = requestPost(PayConstants.FUIOU_WITHDRAW_API, params);
	        WithdrawResData wrd = XmlBeanUtils.convertXml2Bean(jsonStr, WithdrawResData.class);
	        result = JSONArray.toJSONString(wrd);
	        logger.info("===========提现返回==============" + result);
        }catch (Exception e) {
        	e.printStackTrace();
		}
		return result;
	}
	
	public static String requestPost(String url, List<NameValuePair> params) throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        CloseableHttpResponse response = httpclient.execute(httppost);
        System.out.println(response.toString());

        HttpEntity entity = response.getEntity();
        String jsonStr = EntityUtils.toString(entity, "utf-8");
        System.out.println(jsonStr);

        httppost.releaseConnection();
        return jsonStr;
    }

	/**
	 * 网关订单查询
	 * @param data
	 * @param method
	 * @return
	 */
	private String queryPayStatus(String data, String method) {
		String result = null;
		JSONObject jsonParam = JSONAlibabaUtil.parseString(data);
		String orderNo = jsonParam.getString("orderNo");
		String signPlain = PayConstants.FUIOU_B2C_MCHNT_CD + "|" + orderNo + "|" + PayConstants.FUIOU_B2C_MCHNT_KEY;
        String sign = MD5.MD5Encode(signPlain);
        System.out.println("[签名明文:]" + signPlain);
        try{
	        Map<String, String> param = new HashMap<String, String>();
	        param.put("md5", sign);
	        param.put("mchnt_cd", PayConstants.FUIOU_B2C_MCHNT_CD);
	        param.put("order_id", orderNo);
	        System.out.println("[请求信息:]" + param);
        	String respStr = HttpPostUtil.postForward(PayConstants.FUIOU_GATE_QUERY, param);
	        System.out.println("返回数据：" + respStr);
	        PayOrderRespData respData = XmlBeanUtils.convertXml2Bean(respStr, PayOrderRespData.class);
	        System.out.println("===========" + respData);
	        result = JSONArray.toJSONString(respData);
        }catch (Exception e) {
        	logger.error("失败", e);
		}
        return result;
	}

	/**
	 * 快捷订单查询
	 * @param data
	 * @param method
	 * @return
	 */
	private String queryQuickPayStatus(String data, String method) {
		String result = null;
		JSONObject jsonParam = JSONAlibabaUtil.parseString(data);
		String outOrderNo = jsonParam.getString("outOrderNo");
        String signPlain = PayConstants.FUIOU_QUICK_MCHNT_CD + "|" + outOrderNo + "|" + PayConstants.FUIOU_QUICK_MCHNT_KEY;
        String sign = MD5.MD5Encode(signPlain);
        System.out.println("[签名明文:]" + signPlain);
        QuickPayOrderReqData reqData = new QuickPayOrderReqData();
        reqData.setMchntcd(PayConstants.FUIOU_QUICK_MCHNT_CD);
        reqData.setOrderId(outOrderNo);
        reqData.setSign(sign);
        try{
        	String orderPlain = XmlBeanUtils.convertBean2Xml(reqData, "UTF-8", false);
	        System.out.println("[订单信息:]" + orderPlain);
	        Map<String, String> param = new HashMap<String, String>();
	        param.put("FM", orderPlain);
	        System.out.println("[请求信息:]" + param);
        	String respStr = HttpPostUtil.postForward(this.remoteURL+method, param);
	        System.out.println("返回数据：" + respStr);
	        QuickPayOrderRespData respData = XmlBeanUtils.convertXml2Bean(respStr, QuickPayOrderRespData.class);
	        System.out.println("===========" + respData);
	        result = JSONArray.toJSONString(respData);
        }catch (Exception e) {
        	logger.error("失败", e);
		}
		return result;
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
	
	public static void main(String[] args) throws CommunicateException {
		String str = PayConstants.FUIOU_B2C_MCHNT_CD + "|66441179181428435749|" + PayConstants.FUIOU_B2C_MCHNT_KEY;
		String md5 = MD5.MD5Encode(str);
		System.out.println(md5);
	}

}