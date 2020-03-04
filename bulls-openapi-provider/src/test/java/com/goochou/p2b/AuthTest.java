package com.goochou.p2b;

import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.openapi.auth.AuthenticationRequest;

public class AuthTest {
	public static void main(String[] args) {
		AuthenticationRequest smr = new AuthenticationRequest();
		smr.setUsername("测试八");
		smr.setIdNo("110000199001081129");
        ServiceMessage msg = new ServiceMessage("auth.pyzx", smr);
        Response result = (Response) OpenApiClient.getInstance()
                .setServiceMessage(msg).send();
        if(result.isSuccess()) {
        	//业务逻辑
        }
//	    FuiouBindCardRequest smr = new FuiouBindCardRequest();
//        smr.setBankCard("6214850210049661");
//        smr.setIdentityCard("350781198408154015");
//        smr.setPhoneNo("15001824049");
//        smr.setTrueName("叶东平");
//        smr.setUserId("1");
//        ServiceMessage msg = new ServiceMessage("fuiou.bind.card", smr);
//        FuiouBindCardResponse result = (FuiouBindCardResponse) OpenApiClient.getInstance()
//                .setServiceMessage(msg).send();
//        if(result.isSuccess()) {
//            //业务逻辑
//        }
	}
}
