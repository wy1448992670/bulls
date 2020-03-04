package com.goochou.p2b.service;

import com.goochou.p2b.constant.ClientEnum;
import com.goochou.p2b.hessian.Response;

/**
 * @Auther: huangsj
 * @Date: 2019/5/21 17:45
 * @Description:
 */
public interface PayService {

    Response addRechargeForOrder(String orderType, String orderNum, Integer userId, String payChannel, Integer bankCardId, ClientEnum clientEnum);

}
