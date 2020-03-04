package com.goochou.p2b.invocation.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.openapi.pay.QueryFuiouPayRequest;
import com.goochou.p2b.hessian.openapi.pay.QueryFuiouPayResponse;
import com.goochou.p2b.invocation.BaseInvocation;
import com.goochou.p2b.invocation.IInvocation;
import com.goochou.p2b.utils.JSONAlibabaUtil;

/**
 * 
 * Created on 2017-6-8
 * <p>Title:       DEC集团系统_[OPENAPI]_[模块名]/p>
 * <p>Description: [汇率外部接口入参出参包装]</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * <p>Company:     澳钜（上海）教育科技有限公司</p>
 * <p>Department:  研发中心</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
@Service("queryFuiouPayInvocation")
public class QueryFuiouPayInvocation extends BaseInvocation implements IInvocation {

	/**
	 *  Created on 2017-6-8
	 * <p>Discription:[主方法]</p>
	 * @author:[叶东平]
	 * @update:[日期2017-6-8] [叶东平]
	 */
	@Override
	public QueryFuiouPayResponse executeService(Request request) {
		QueryFuiouPayResponse res = new QueryFuiouPayResponse();
        String rspJson = "";
        try {
            rspJson = getRspXML(request, PayConstants.METHOD_QUERY_ORDER, OutPayEnum.FUIOU.getFeatureName());
            logger.info("rspJson:"+rspJson);
            setResult(res,rspJson);
        } catch (Exception e)  {
            logger.error(e, e);
        }
        return res; 
	}
	/**
	 *  Created on 2017-6-8 
	 * <p>Discription:[出参包装]</p>
	 * @author:[叶东平]
	 * @update:[日期2017-6-8] [叶东平]
	 * @return void .
	 */
	private void setResult(QueryFuiouPayResponse res, String rspJson) {
		//{"md5":"26026dad5090c68bf9464af69acf0d22","plain":{"fySsn":"","orderId":"664411791819305824363","orderPayCode":"0000","orderPayError":"成功","orderSt":"00","resv1":"备注"}}
        JSONObject obj = parseJSONString(rspJson);
        String plainJson = JSONAlibabaUtil.getJsonStr(obj, "plain");
        JSONObject plainObj = parseJSONString(plainJson);
        String orderPayCode = JSONAlibabaUtil.getJsonStr(plainObj, "orderPayCode");
        if(PayConstants.RESP_CODE_SUCCESS.equals(orderPayCode)){
        	String orderSt = JSONAlibabaUtil.getJsonStr(plainObj, "orderSt");
        	if("11".equals(orderSt)){
            	res.setStatus("1");
            	String fySsn = JSONAlibabaUtil.getJsonStr(plainObj, "fySsn");
            	res.setOutOrderId(fySsn);
            }else if("04".equals(orderSt)){
            	res.setStatus("3");
            }else{
            	res.setStatus("2");
            	String orderPayError = JSONAlibabaUtil.getJsonStr(plainObj, "orderPayError");
            	res.setErrorMsg(orderPayError);
            }
        }else{
        	String orderPayError = JSONAlibabaUtil.getJsonStr(obj, "orderPayError");
        	res.setStatus("0");
        	res.setErrorMsg(orderPayError);
        }
        res.setSuccess(true);
    }

	/**
	 *  Created on 2017-6-8
	 * <p>Discription:[入参包装]</p>
	 * @author:[叶东平]
	 * @update:[日期2017-6-8] [叶东平]
	 */
	@Override
    protected String toJSONString(Request request) {
		QueryFuiouPayRequest fsmr = (QueryFuiouPayRequest) request;
        String reqJSON = JSONArray.toJSONString(fsmr);
        return reqJSON;
    }

}
