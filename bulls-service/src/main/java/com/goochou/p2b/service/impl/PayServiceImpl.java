package com.goochou.p2b.service.impl;

import com.goochou.p2b.constant.ClientEnum;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.openapi.pay.PayOrderRequest;
import com.goochou.p2b.service.PayService;
import org.springframework.stereotype.Service;

/**
 * @Auther: huangsj
 * @Date: 2019/5/21 17:46
 * @Description:
 */
@Service
public class PayServiceImpl implements PayService {

    public Response addRechargeForOrder(String orderType, String orderNum, Integer userId, String payChannel, Integer bankCardId, ClientEnum clientEnum) {

        PayOrderRequest req = new PayOrderRequest();
        req.setUserId(userId);
        req.setOrderType(orderType);
        req.setOrderNum(orderNum);
        req.setPayChannel(payChannel);
        req.setBankCardId(bankCardId);
        req.setClientEnum(clientEnum);

        ServiceMessage message = new ServiceMessage("payorder.create", req);

        return OpenApiClient.getInstance().setServiceMessage(message).send();
    }
}
