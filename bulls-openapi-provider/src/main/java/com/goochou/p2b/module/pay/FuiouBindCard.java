package com.goochou.p2b.module.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.common.BaseAO;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.openapi.pay.FuiouBindCardRequest;
import com.goochou.p2b.hessian.openapi.pay.FuiouBindCardResponse;
import com.goochou.p2b.invocation.impl.FuiouBindCardInvocation;


@Service
public class FuiouBindCard extends BaseAO implements HessianInterface
{
	
	@Autowired
	private FuiouBindCardInvocation fuiouBankCardInvocation;
	
	@Override
    public FuiouBindCardResponse execute(ServiceMessage msg)
    {
    	FuiouBindCardRequest req = (FuiouBindCardRequest) msg.getReq();
    	FuiouBindCardResponse response = new FuiouBindCardResponse();
		try {
			response = fuiouBankCardInvocation.executeService(req);
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
