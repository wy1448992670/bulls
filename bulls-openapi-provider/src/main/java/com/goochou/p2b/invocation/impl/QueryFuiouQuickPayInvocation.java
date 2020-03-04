package com.goochou.p2b.invocation.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.openapi.pay.QueryFuiouQuickPayRequest;
import com.goochou.p2b.hessian.openapi.pay.QueryFuiouQuickPayResponse;
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
@Service("queryFuiouQuickPayInvocation")
public class QueryFuiouQuickPayInvocation extends BaseInvocation implements IInvocation {

	/**
	 *  Created on 2017-6-8
	 * <p>Discription:[主方法]</p>
	 * @author:[叶东平]
	 * @update:[日期2017-6-8] [叶东平]
	 */
	@Override
	public QueryFuiouQuickPayResponse executeService(Request request) {
		QueryFuiouQuickPayResponse res = new QueryFuiouQuickPayResponse();
        String rspJson = "";
        try {
            rspJson = getRspXML(request, PayConstants.METHOD_QUICK_QUERY_ORDER, OutPayEnum.FUIOU.getFeatureName());
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
	private void setResult(QueryFuiouQuickPayResponse res, String rspJson) {
        JSONObject obj = parseJSONString(rspJson);
        //{"rcd":"5185","rdesc":"订单已支付","sign":"1ec54e1d7ea932e9cf9574761b70ad4e"}
        String rcd = JSONAlibabaUtil.getJsonStr(obj, "rcd");
        if("5185".equals(rcd)){
        	res.setStatus("1");
        }else if("5077".equals(rcd)){
        	res.setStatus("0");
        }else if("51B3".equals(rcd)){
        	res.setStatus("3");
        }else{
        	res.setStatus("2");
        	String rdesc = JSONAlibabaUtil.getJsonStr(obj, "rdesc");
        	res.setErrorMsg(rdesc);
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
		QueryFuiouQuickPayRequest fsmr = (QueryFuiouQuickPayRequest) request;
        String reqJSON = JSONArray.toJSONString(fsmr);
        return reqJSON;
    }

}
