package com.goochou.p2b.module.withdraw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.common.BaseAO;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.openapi.withdraw.WithdrawRequest;
import com.goochou.p2b.invocation.impl.AllinPayWithdrawInvocation;

@Service
public class AllinPayWithdraw extends BaseAO implements HessianInterface
{
	
	@Autowired
	private AllinPayWithdrawInvocation allinPayWithdrawInvocation;
	
	@Override
    public Response execute(ServiceMessage msg)
    {
		WithdrawRequest req = (WithdrawRequest) msg.getReq();
		Response response = new Response();
		try {
			response = allinPayWithdrawInvocation.executeService(req);
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
