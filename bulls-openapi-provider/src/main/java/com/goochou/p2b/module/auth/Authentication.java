package com.goochou.p2b.module.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.common.BaseAO;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.openapi.auth.AuthenticationRequest;
import com.goochou.p2b.invocation.impl.AuthenticationInvocation;


@Service
public class Authentication extends BaseAO implements HessianInterface
{
	
	@Autowired
	private AuthenticationInvocation authenticationInvocation;
	
	@Override
    public Response execute(ServiceMessage msg)
    {
		AuthenticationRequest req = (AuthenticationRequest) msg.getReq();
    	Response response = new Response();
		try {
			response = authenticationInvocation.executeService(req);
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
