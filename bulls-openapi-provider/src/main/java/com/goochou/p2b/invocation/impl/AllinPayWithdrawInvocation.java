package com.goochou.p2b.invocation.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.openapi.withdraw.WithdrawRequest;
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
public class AllinPayWithdrawInvocation extends BaseInvocation implements IInvocation {

	/**
	 *  Created on 2017-6-8
	 * <p>Discription:[主方法]</p>
	 * @author:[叶东平]
	 * @update:[日期2017-6-8] [叶东平]
	 */
	@Override
	public Response executeService(Request request) {
		Response res = new Response();
        String rspJson = "";
        try {
            rspJson = getRspXML(request, PayConstants.METHOD_WITHDRAW, OutPayEnum.ALLINPAY.getFeatureName());
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
	private void setResult(Response res, String rspJson) {
        JSONObject obj = parseJSONString(rspJson);
        String ret = JSONAlibabaUtil.getJsonStr(obj, "rET_CODE");
        if("0000".equals(ret)){
        	res.setSuccess(true);
        	String sn = JSONAlibabaUtil.getJsonStr(obj, "sETTLE_DAY");
        	res.setMsg(sn);
        }else{
            res.setSuccess(false);
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
		WithdrawRequest fsmr = (WithdrawRequest) request;
        String reqJSON = JSONArray.toJSONString(fsmr);
        logger.info("WithdrawRequest = "+reqJSON);
        return reqJSON;
    }

}
