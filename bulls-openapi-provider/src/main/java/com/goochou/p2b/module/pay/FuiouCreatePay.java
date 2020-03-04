package com.goochou.p2b.module.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.common.BaseAO;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.openapi.pay.FuiouCreatePayRequest;
import com.goochou.p2b.hessian.openapi.pay.FuiouDataResponse;
import com.goochou.p2b.invocation.impl.FuiouCreatePayInvocation;


@Service
public class FuiouCreatePay extends BaseAO implements HessianInterface
{
	
	@Autowired
	private FuiouCreatePayInvocation fuiouCreatePayInvocation;
	
	@Override
    public Response execute(ServiceMessage msg)
    {
    	FuiouCreatePayRequest req = (FuiouCreatePayRequest) msg.getReq();
    	FuiouDataResponse response = new FuiouDataResponse();
		try {
			response = fuiouCreatePayInvocation.executeService(req);
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
