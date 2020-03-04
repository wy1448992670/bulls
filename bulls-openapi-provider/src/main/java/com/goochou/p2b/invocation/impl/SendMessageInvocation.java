package com.goochou.p2b.invocation.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.openapi.message.SendMessageRequest;
import com.goochou.p2b.hessian.openapi.message.SendMessageResponse;
import com.goochou.p2b.invocation.BaseInvocation;
import com.goochou.p2b.invocation.IInvocation;
import com.goochou.p2b.model.message.ChanzorBack;
import com.goochou.p2b.model.message.ChanzorData;
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
public class SendMessageInvocation extends BaseInvocation implements IInvocation {

	/**
	 *  Created on 2017-6-8
	 * <p>Discription:[主方法]</p>
	 * @author:[叶东平]
	 * @update:[日期2017-6-8] [叶东平]
	 */
	@Override
	public SendMessageResponse executeService(Request request) {
		SendMessageRequest req = (SendMessageRequest) request;
		SendMessageResponse res = new SendMessageResponse();
        String rspJson = "";
        try {
            rspJson = getRspXML(request, null, req.getChannel());
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
	private void setResult(SendMessageResponse res, String rspJson) {
//		List<ChanzorBack> result = new ArrayList<ChanzorBack>();
//        List<ChanzorData> list = JSONArray.parseArray(rspJson, ChanzorData.class);
//        for(ChanzorData cd : list) {
//        	ChanzorBack cb = new ChanzorBack();
//        	cb.setPhone(String.valueOf(cd.getMobileCount()));
//        	cb.setFlag(cd.getStatus()==0?true:false);
//        	result.add(cb);
//        }
//        res.setResult(result);
//        if(list.size()>0) {
//        	res.setSuccess(true);
//        }
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
		SendMessageRequest fsmr = (SendMessageRequest) request;
        String reqJSON = JSONArray.toJSONString(fsmr);
        return reqJSON;
    }

}
