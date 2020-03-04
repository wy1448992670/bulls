package com.goochou.p2b.module.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.common.BaseAO;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.openapi.pay.AllinPayRequest;
import com.goochou.p2b.hessian.openapi.pay.AllinPayResponse;
import com.goochou.p2b.invocation.impl.AllinPayCreatePayInvocation;


@Service
public class AllinPayCreatePay extends BaseAO implements HessianInterface
{
	
	@Autowired
	private AllinPayCreatePayInvocation allinPayCreatePayInvocation;
	
	@Override
    public Response execute(ServiceMessage msg)
    {
	    AllinPayRequest req = (AllinPayRequest) msg.getReq();
	    AllinPayResponse response = new AllinPayResponse();
		try {
			response = allinPayCreatePayInvocation.executeService(req);
		} catch (Exception e) {
			OpenApiApp.EXCEPTION.exception(msg, e);
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
