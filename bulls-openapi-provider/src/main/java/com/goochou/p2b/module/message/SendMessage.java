package com.goochou.p2b.module.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.common.BaseAO;
import com.goochou.p2b.constant.MessageChannelEnum;
import com.goochou.p2b.constant.TestEnum;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.openapi.message.SendMessageRequest;
import com.goochou.p2b.hessian.openapi.message.SendMessageResponse;
import com.goochou.p2b.invocation.impl.SendMessageInvocation;


@Service
public class SendMessage extends BaseAO implements HessianInterface
{
	
	@Autowired
	private SendMessageInvocation sendMessageInvocation;
	
	@Override
    public Response execute(ServiceMessage msg)
    {
    	SendMessageRequest req = (SendMessageRequest) msg.getReq();
    	if(null == req.getChannel() || "".equals(req.getChannel())) {
    		req.setChannel(MessageChannelEnum.DH3T.getFeatureName());
    	}
    	SendMessageResponse response = new SendMessageResponse();
		if (TestEnum.RELEASE.getFeatureName().equals(com.goochou.p2b.constant.Constants.TEST_SWITCH)) {
			try {
				response = sendMessageInvocation.executeService(req);
			} catch (Exception e) {
				OpenApiApp.EXCEPTION.exception(msg, e);
			}
		} else {
			response.setSuccess(true);
		}
		return response;
    }

    
    @Override
    public void before(ServiceMessage msg)
    {
        System.out.println("before");

    }

    @Override
    public void after(ServiceMessage msg)
    {
        System.out.println("after");

    }
}
