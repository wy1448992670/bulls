package com.goochou.p2b.invocation.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.openapi.pay.FuiouBindCardRequest;
import com.goochou.p2b.hessian.openapi.pay.FuiouBindCardResponse;
import com.goochou.p2b.invocation.BaseInvocation;
import com.goochou.p2b.invocation.IInvocation;
import com.goochou.p2b.model.pay.fuiou.BankCardResqData;
import com.goochou.p2b.utils.JSONAlibabaUtil;

/**
 * @author ydp
 * @date 2019/05/23
 */
@Service
public class FuiouBindCardInvocation extends BaseInvocation implements IInvocation {

	/**
	 *  Created on 2017-6-8
	 * <p>Discription:[主方法]</p>
	 * @author:[叶东平]
	 * @update:[日期2017-6-8] [叶东平]
	 */
	@Override
	public FuiouBindCardResponse executeService(Request request) {
	    FuiouBindCardResponse res = new FuiouBindCardResponse();
        String rspJson = "";
        try {
            rspJson = getRspXML(request, PayConstants.METHOD_BIND_CARD, OutPayEnum.FUIOU.getFeatureName());
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
	private void setResult(FuiouBindCardResponse res, String rspJson) {
        JSONObject obj = parseJSONString(rspJson);
        String code = JSONAlibabaUtil.getJsonStr(obj, "success");
        if("true".equals(code)){
        	res.setSuccess(true);
        	String dataJson = JSONAlibabaUtil.getJsonStr(obj, "bankCardResqData");
        	JSONObject dataObj = parseJSONString(dataJson);
        	BankCardResqData bankCardResqData = (BankCardResqData) JSONAlibabaUtil.toBean(dataObj, BankCardResqData.class);
        	res.setBankCardResqData(bankCardResqData);
        }else{
        	res.setSuccess(false);
        	String errorMsg = JSONAlibabaUtil.getJsonStr(obj, "errorMsg");
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
	    FuiouBindCardRequest fsmr = (FuiouBindCardRequest) request;
        String reqJSON = JSONArray.toJSONString(fsmr);
        return reqJSON;
    }

}
