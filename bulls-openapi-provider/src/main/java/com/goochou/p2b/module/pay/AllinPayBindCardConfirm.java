package com.goochou.p2b.module.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.common.BaseAO;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.openapi.pay.AllinPayBindCardRequest;
import com.goochou.p2b.hessian.openapi.pay.AllinPayBindCardResponse;
import com.goochou.p2b.invocation.impl.AllinPayBindCardConfirmInvocation;


@Service
public class AllinPayBindCardConfirm extends BaseAO implements HessianInterface
{
	
	@Autowired
	private AllinPayBindCardConfirmInvocation allinPayBindCardConfirmInvocation;
	
	@Override
    public AllinPayBindCardResponse execute(ServiceMessage msg)
    {
    	AllinPayBindCardRequest req = (AllinPayBindCardRequest) msg.getReq();
    	AllinPayBindCardResponse response = new AllinPayBindCardResponse();
		try {
			response = allinPayBindCardConfirmInvocation.executeService(req);
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
