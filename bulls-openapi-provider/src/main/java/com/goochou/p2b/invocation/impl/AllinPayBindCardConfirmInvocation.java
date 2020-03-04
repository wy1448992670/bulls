package com.goochou.p2b.invocation.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.openapi.pay.AllinPayBindCardRequest;
import com.goochou.p2b.hessian.openapi.pay.AllinPayBindCardResponse;
import com.goochou.p2b.invocation.BaseInvocation;
import com.goochou.p2b.invocation.IInvocation;
import com.goochou.p2b.utils.JSONAlibabaUtil;

/**
 * @author ydp
 * @date 2019/05/23
 */
@Service
public class AllinPayBindCardConfirmInvocation extends BaseInvocation implements IInvocation {

	/**
	 *  Created on 2017-6-8
	 * <p>Discription:[主方法]</p>
	 * @author:[叶东平]
	 * @update:[日期2017-6-8] [叶东平]
	 */
	@Override
	public AllinPayBindCardResponse executeService(Request request) {
	    AllinPayBindCardResponse res = new AllinPayBindCardResponse();
        String rspJson = "";
        try {
            rspJson = getRspXML(request, PayConstants.METHOD_BIND_CARD_CONFIRM, OutPayEnum.ALLINPAY.getFeatureName());
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
	private void setResult(AllinPayBindCardResponse res, String rspJson) {
        JSONObject obj = parseJSONString(rspJson);
        String code = JSONAlibabaUtil.getJsonStr(obj, "retcode");
        if(PayConstants.SUCCESS.equals(code)){
        	String trxstatus = JSONAlibabaUtil.getJsonStr(obj, "trxstatus");
            if(PayConstants.RESP_CODE_SUCCESS.equals(trxstatus)) {
                res.setSuccess(true);
                String agreeId = JSONAlibabaUtil.getJsonStr(obj, "agreeid");
                String bankCode = JSONAlibabaUtil.getJsonStr(obj, "bankcode");
                String bankName = JSONAlibabaUtil.getJsonStr(obj, "bankname");
                res.setAgreeId(agreeId);
                res.setBankCode(bankCode);
                res.setBankName(bankName);
            }else {
                res.setSuccess(false);
                String errorMsg = JSONAlibabaUtil.getJsonStr(obj, "errmsg");
                res.setErrorMsg(errorMsg);
            }
        }else{
        	res.setSuccess(false);
        	String errorMsg = JSONAlibabaUtil.getJsonStr(obj, "retmsg");
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
	    AllinPayBindCardRequest fsmr = (AllinPayBindCardRequest) request;
        String reqJSON = JSONArray.toJSONString(fsmr);
        return reqJSON;
    }

}
