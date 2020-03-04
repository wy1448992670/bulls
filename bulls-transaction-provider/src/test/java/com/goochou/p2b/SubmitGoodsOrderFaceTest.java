package com.goochou.p2b;

import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.TransactionClient;
import com.goochou.p2b.hessian.transaction.goods.GoodsOrderRequest;

public class SubmitGoodsOrderFaceTest {
	public static void main(String[] args) {
		GoodsOrderRequest smr = new GoodsOrderRequest();
        ServiceMessage msg = new ServiceMessage("goodsorder.add", smr);
        Response result = (Response) TransactionClient.getInstance()
                .setServiceMessage(msg).send();
        System.out.println(result);
	}
}
