package com.goochou.p2b.adapter;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.JAXBException;

import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.Response;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.model.pay.allinpay.AIPGException;
import com.goochou.p2b.model.pay.allinpay.HttpUtil;
import com.goochou.p2b.model.pay.allinpay.QpayUtil;
import com.goochou.p2b.model.pay.allinpay.xml.XmlParser;
import com.goochou.p2b.model.pay.allinpay.xstruct.common.AipgReq;
import com.goochou.p2b.model.pay.allinpay.xstruct.common.AipgRsp;
import com.goochou.p2b.model.pay.allinpay.xstruct.common.InfoReq;
import com.goochou.p2b.model.pay.allinpay.xstruct.common.InfoRsp;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.TransExt;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.TransRet;
import com.goochou.p2b.utils.JSONAlibabaUtil;
import com.goochou.p2b.utils.Money;
import com.goochou.p2b.utils.XmlBeanUtils;

/**
 * 通联支付路由
 * @author ydp
 * @date 2019/06/25
 */
public class AllinPayCommunicator implements ICommunicator {

	private static final Logger logger = Logger.getLogger(AllinPayCommunicator.class);
	
	public static Map<String, String> buildBasicMap(){
        TreeMap<String,String> params = new TreeMap<String,String>();
        params.put("appid", PayConstants.ALLINPAY_APP_ID);
        params.put("cusid", PayConstants.ALLINPAY_MCHNT_CD);
        params.put("version", "11");
        params.put("randomstr", System.currentTimeMillis()+"");
        return params;
    }

	@Override
	public Response httSend2(Request request, String method) throws CommunicateException {
		return null;
	}

	@Override
	public String httSend(String data, String method, String requestType)
			throws CommunicateException {
	    logger.info("api="+PayConstants.ALLINPAY_API);
	    String result = "";
	    Map<String, String> map = new HashMap<String, String>();
        // 发送内容
     	JSONObject jsonParam = JSONAlibabaUtil.parseString(data);
		switch(method){
		case PayConstants.METHOD_MESSAGE:
		    try {
                Map<String, String> params = buildBasicMap();
                params.put("orderid", jsonParam.getString("orderNo"));//payapply的单号
                params.put("agreeid",jsonParam.getString("agreeId"));//绑卡返回的agreeid
                params.put("thpinfo", jsonParam.getString("thpInfo"));
                map = QpayUtil.dorequest(PayConstants.ALLINPAY_API+"/paysmsagree", params,PayConstants.ALLINPAY_MCHNT_KEY);
            }catch (Exception e) {
                e.printStackTrace();
            }
            result = JSONArray.toJSONString(map);
			break;
		case PayConstants.METHOD_PAY:
		    try {
                Map<String, String> params = buildBasicMap();
                params.put("orderid", jsonParam.getString("orderNo"));//payapply的单号
                params.put("agreeid",jsonParam.getString("agreeId"));//绑卡返回的agreeid
                params.put("smscode", jsonParam.getString("code"));
                params.put("thpinfo", jsonParam.getString("thpInfo"));
                map = QpayUtil.dorequest(PayConstants.ALLINPAY_API+"/payagreeconfirm", params,PayConstants.ALLINPAY_MCHNT_KEY);
            }catch (Exception e) {
                e.printStackTrace();
            }
            result = JSONArray.toJSONString(map);
            break;
		case PayConstants.METHOD_CREATE_PAY:
		    try {
		        Map<String, String> params = buildBasicMap();
	            params.put("orderid", jsonParam.getString("orderNo"));
	            params.put("amount", jsonParam.getString("amount"));
	            params.put("subject", jsonParam.getString("subject"));
	            params.put("notifyurl", PayConstants.ADVITE_URL);
	            params.put("currency","CNY");
	            params.put("validtime","30");
	            params.put("agreeid",jsonParam.getString("agreeId"));//绑卡返回的agreeid
	            map = QpayUtil.dorequest(PayConstants.ALLINPAY_API+"/payapplyagree", params,PayConstants.ALLINPAY_MCHNT_KEY);
            }catch (Exception e) {
                e.printStackTrace();
            }
            result = JSONArray.toJSONString(map);
            break;
		case PayConstants.METHOD_QUICK_QUERY_ORDER:
		    try {
                Map<String, String> params = buildBasicMap();
                params.put("orderid", jsonParam.getString("orderNo"));
                map = QpayUtil.dorequest(PayConstants.ALLINPAY_API+"/query", params,PayConstants.ALLINPAY_MCHNT_KEY);
            }catch (Exception e) {
                e.printStackTrace();
            }
            result = JSONArray.toJSONString(map);
			break;
		case PayConstants.METHOD_REFUND:
		    try {
                Map<String, String> params = buildBasicMap();
                params.put("trxamt",jsonParam.getString("amount"));
                params.put("orderid","TK"+System.currentTimeMillis());
                params.put("oldorderid",jsonParam.getString("orderNo"));
                map = QpayUtil.dorequest(PayConstants.ALLINPAY_API+"/refund", params,PayConstants.ALLINPAY_MCHNT_KEY);
            }catch (Exception e) {
                e.printStackTrace();
            }
            result = JSONArray.toJSONString(map);
			break;
		case PayConstants.METHOD_BIND_CARD:
	        try {
	            Map<String, String> params = buildBasicMap();
	            params.put("meruserid",jsonParam.getString("userId"));
                params.put("accttype","00");
                params.put("acctno",jsonParam.getString("bankCard"));
                params.put("idno",jsonParam.getString("identityCard"));
                params.put("acctname",jsonParam.getString("trueName"));
                params.put("mobile",jsonParam.getString("phoneNo"));
	            params.put("cvv2", "");
	            params.put("validdate", "");
	            map = QpayUtil.dorequest(PayConstants.ALLINPAY_API+"/agreeapply", params,PayConstants.ALLINPAY_MCHNT_KEY);
	        }catch (Exception e) {
	            e.printStackTrace();
            }
	        result = JSONArray.toJSONString(map);
            break;
		case PayConstants.METHOD_BIND_CARD_CONFIRM:
            try {
                Map<String, String> params = buildBasicMap();
                params.put("meruserid",jsonParam.getString("userId"));
                params.put("accttype","00");
                params.put("acctno",jsonParam.getString("bankCard"));
                params.put("idno",jsonParam.getString("identityCard"));
                params.put("acctname",jsonParam.getString("trueName"));
                params.put("mobile",jsonParam.getString("phoneNo"));
                params.put("cvv2", "");
                params.put("validdate", "");
                params.put("thpinfo", jsonParam.getString("thpInfo"));
                params.put("smscode", jsonParam.getString("code"));
                map = QpayUtil.dorequest(PayConstants.ALLINPAY_API+"/agreeconfirm", params,PayConstants.ALLINPAY_MCHNT_KEY);
            }catch (Exception e) {
                e.printStackTrace();
            }
            result = JSONArray.toJSONString(map);
            break;
		case PayConstants.METHOD_BIND_CARD_MESSAGE:
            try {
                Map<String, String> params = buildBasicMap();
                params.put("meruserid",jsonParam.getString("userId"));
                params.put("accttype","00");
                params.put("acctno",jsonParam.getString("bankCard"));
                params.put("idno",jsonParam.getString("identityCard"));
                params.put("acctname",jsonParam.getString("trueName"));
                params.put("mobile",jsonParam.getString("phoneNo"));
                params.put("cvv2", "");
                params.put("validdate", "");
                params.put("thpinfo", jsonParam.getString("thpInfo"));
                map = QpayUtil.dorequest(PayConstants.ALLINPAY_API+"/agreesms", params,PayConstants.ALLINPAY_MCHNT_KEY);
            }catch (Exception e) {
                e.printStackTrace();
            }
            result = JSONArray.toJSONString(map);
            break;
		case PayConstants.METHOD_WITHDRAW:
		    InfoReq infoReq = QpayUtil.makeReq("100014");
	        
	        TransExt trans = new TransExt();
	        trans.setBUSINESS_CODE("09900");//必须使用业务人员提供的业务代码，否则返回“未开通业务类型”
	        trans.setMERCHANT_ID(PayConstants.ALLINPAY_PROVIDED_MCHNT_CD);
	        trans.setSUBMIT_TIME(QpayUtil.getNow());
	        trans.setACCOUNT_NAME(jsonParam.getString("trueName"));
	        trans.setACCOUNT_NO(jsonParam.getString("cardNo"));
	        trans.setACCOUNT_PROP("0");
	        trans.setAMOUNT(String.valueOf(new Money(jsonParam.getString("amount")).getCent()));
	        trans.setBANK_CODE(jsonParam.getString("bankNo"));
	        trans.setCURRENCY("CNY");
	        trans.setTEL("");
	        trans.setCUST_USERID(jsonParam.getString("idNo"));
	        trans.setSUMMARY("摘要");
	        trans.setREMARK(jsonParam.getString("orderNo"));
	        trans.setNOTIFYURL(PayConstants.ADVITE_URL.replace("/backRecharge", "/withdraw/allinpay/notify"));
	        AipgReq req = new AipgReq();
	        req.setINFO(infoReq);
	        req.addTrx(trans);
	        logger.info("req="+JSONArray.toJSONString(req));
	        try{
	            //step1 对象转xml
	            String xml = XmlParser.toXml(req);
	            logger.info("xml="+xml);
	            //step2 加签
	            String signedXml = QpayUtil.buildSignedXml(xml);
	            //step3 发往通联
	            String url = PayConstants.ALLINPAY_PROVIDED_API+"?MERCHANT_ID="+PayConstants.ALLINPAY_PROVIDED_MCHNT_CD+"&REQ_SN="+infoReq.getREQ_SN();
	            System.out.println("============================请求报文============================");
	            System.out.println(signedXml);
	            String respText = HttpUtil.post(signedXml, url);
	            System.out.println("============================响应报文============================");
	            System.out.println(respText);
	            //step4 验签
	            if(!QpayUtil.verifyXml(respText)){
	                System.out.println("====================================================>验签失败");
	                return "";
	            }
	            System.out.println("====================================================>验签成功");
	            //step5 xml转对象
	            AipgRsp rsp = XmlParser.parseRsp(respText);
	            InfoRsp infoRsp = rsp.getINFO();
	            System.out.println(infoRsp.getRET_CODE());
	            System.out.println(infoRsp.getERR_MSG());
	            if(PayConstants.RESP_CODE_SUCCESS.equals(infoRsp.getRET_CODE())){
	                TransRet ret = (TransRet) rsp.trxObj();
	                //这里特殊处理不加字段改动小一些
	                ret.setSETTLE_DAY(infoRsp.getREQ_SN());
	                result = JSONArray.toJSONString(ret);
	            }
	        }catch(AIPGException e){
	            e.printStackTrace();
	        }
		    break;
		default:
		    break;
		}
		logger.info("result = "+result);
		return result;
    }
}