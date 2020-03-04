package com.goochou.p2b.invocation.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.openapi.pay.QueryYeePayResponse;
import com.goochou.p2b.hessian.openapi.pay.YeePayRequest;
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
@Service
public class QueryYeePayInvocation extends BaseInvocation implements IInvocation {

	/**
	 *  Created on 2017-6-8
	 * <p>Discription:[主方法]</p>
	 * @author:[叶东平]
	 * @update:[日期2017-6-8] [叶东平]
	 */
	@Override
	public QueryYeePayResponse executeService(Request request) {
	    QueryYeePayResponse res = new QueryYeePayResponse();
        String rspJson = "";
        try {
            rspJson = getRspXML(request, PayConstants.METHOD_QUICK_QUERY_ORDER, OutPayEnum.YEEPAY.getFeatureName());
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
	private void setResult(QueryYeePayResponse res, String rspJson) {
        JSONObject obj = parseJSONString(rspJson);
        String code = JSONAlibabaUtil.getJsonStr(obj, "code");
        if("OPR00000".equals(code)){
            String paymentStatus = JSONAlibabaUtil.getJsonStr(obj, "paymentStatus");
            if(null == paymentStatus) {
                paymentStatus = "FAILED";
            }
            res.setSuccess(true);
            res.setStatus(paymentStatus);
        }else{
        	res.setSuccess(false);
        	String errorMsg = JSONAlibabaUtil.getJsonStr(obj, "message");
        	res.setErrorMsg(errorMsg);
        }
    }

	/**
	 *  Created on 2017-6-8
	 * <p>Discription:[入参包装]</p>
	 * @author:[叶东平]
	 * @update:[日期2017-6-8] [叶东平]
	 */
	@Override
    protected String toJSONString(Request request) {
	    YeePayRequest fsmr = (YeePayRequest) request;
        String reqJSON = JSONArray.toJSONString(fsmr);
        return reqJSON;
    }
	
	public static void main(String[] args) {
	    String rspJson = "{\"code\":\"OPR00000\",\"fundProcessType\":\"REAL_TIME\",\"goodsParamExt\":\"{\\\"goodsName\\\":\\\"商城牛肉品种\\\",\\\"goodsDesc\\\":\\\"商城牛肉品种\\\"}\",\"haveAccounted\":\"false\",\"merchantName\":\"易宝支付\",\"merchantNo\":\"10000466938\",\"message\":\"成功\",\"orderAmount\":\"0.01\",\"orderId\":\"P19082915200027216\",\"parentMerchantName\":\"易宝支付\",\"parentMerchantNo\":\"10000466938\",\"requestDate\":\"2019-08-29 15:21:27\",\"residualDivideAmount\":\"0\",\"status\":\"PROCESSING\",\"token\":\"ECEEFBE1968452CC6EECFA3BE8DAD330189E36A589E4A888DC951AB59BF235D6\",\"uniqueOrderNo\":\"1001201908290000001071128452\",\"ypSettleAmount\":\"0.01\"}";
	    JSONObject obj = JSONAlibabaUtil.parseString(rspJson);
	    String paymentStatus = JSONAlibabaUtil.getJsonStr(obj, "paymentStatus");
	    System.out.println(paymentStatus);
    }

}
