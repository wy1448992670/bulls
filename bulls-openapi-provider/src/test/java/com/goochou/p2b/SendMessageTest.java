package com.goochou.p2b;

import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.openapi.message.SendMessageRequest;
import com.goochou.p2b.hessian.openapi.message.SendMessageResponse;

public class SendMessageTest {
	public static void main(String[] args) {
		SendMessageRequest smr = new SendMessageRequest();
		smr.setContent("验证码：123456。15分钟有效期，仅用于验证手机，请勿告知他人。");
		smr.setPhone("15001824049");
        ServiceMessage msg = new ServiceMessage("message.send", smr);
        SendMessageResponse result = (SendMessageResponse) OpenApiClient.getInstance()
                .setServiceMessage(msg).send();
        System.out.println(result);
	}
}
