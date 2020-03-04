package com.goochou.p2b.invocation.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.openapi.pay.YeePayRequest;
import com.goochou.p2b.hessian.openapi.pay.YeePayResponse;
import com.goochou.p2b.invocation.BaseInvocation;
import com.goochou.p2b.invocation.IInvocation;

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
public class YeePayCreatePayInvocation extends BaseInvocation implements IInvocation {

	/**
	 *  Created on 2017-6-8
	 * <p>Discription:[主方法]</p>
	 * @author:[叶东平]
	 * @update:[日期2017-6-8] [叶东平]
	 */
	@Override
	public YeePayResponse executeService(Request request) {
	    YeePayResponse res = new YeePayResponse();
        String rspJson = "";
        try {
            rspJson = getRspXML(request, PayConstants.METHOD_CREATE_PAY, OutPayEnum.YEEPAY.getFeatureName());
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
	private void setResult(YeePayResponse res, String rspJson) {
	    if(null!=rspJson && !"".equals(rspJson)) {
    	    res.setSuccess(true);
            res.setUrl(rspJson);
	    }else {
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
	    YeePayRequest fsmr = (YeePayRequest) request;
        String reqJSON = JSONArray.toJSONString(fsmr);
        return reqJSON;
    }

}
