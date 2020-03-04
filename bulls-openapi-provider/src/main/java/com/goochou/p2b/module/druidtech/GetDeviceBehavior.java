package com.goochou.p2b.module.druidtech;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.common.BaseAO;
import com.goochou.p2b.constant.farmcloud.Constants;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.openapi.farmcloud.CommonResponse;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceBehaviorReqeust;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceBehaviorResponse;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.LoginResponse;
import com.goochou.p2b.invocation.impl.druidtech.GetDeviceBehaviorInvocation;
import com.goochou.p2b.invocation.impl.druidtech.GetTokenInvocation;
import com.goochou.p2b.invocation.impl.druidtech.RefreshTokenInvocation;
import com.goochou.p2b.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: huangsj
 * @Date: 2019/6/28 14:53
 * @Description:
 */
@Service
public class GetDeviceBehavior extends BaseAO implements HessianInterface {

    @Autowired
    private GetTokenInvocation getTokenInvocation;
    @Autowired
    private RefreshTokenInvocation refreshTokenInvocation;
    @Autowired
    private GetDeviceBehaviorInvocation deviceBehaviorInvocation;

    @Override
    public CommonResponse<List<DeviceBehaviorResponse>> execute(ServiceMessage msg) {
        DeviceBehaviorReqeust req = (DeviceBehaviorReqeust) msg.getReq();
        CommonResponse<List<DeviceBehaviorResponse>>  response = new CommonResponse<List<DeviceBehaviorResponse>>();
        try {

            if (StringUtils.isEmpty(req.getAuthentication())) {
                CommonResponse<LoginResponse> res = getTokenInvocation.executeService(null);
                if (res.isSuccess()) {
                    Constants.DRUIDTECH_TOKEN = res.getData().getAuthentication();
                    req.setAuthentication(Constants.DRUIDTECH_TOKEN);
                }else{
                    response.setSuccess(false);
                    return response;
                }
            }

            response = deviceBehaviorInvocation.executeService(req);

            if (response.getStatusCode() == 401) {
                CommonResponse<LoginResponse> res = refreshTokenInvocation.executeService(null);
                if (res.isSuccess()) {
                    Constants.DRUIDTECH_TOKEN = res.getData().getAuthentication();
                    req.setAuthentication(Constants.DRUIDTECH_TOKEN);

                    response = deviceBehaviorInvocation.executeService(req);
                }else{
                    response.setSuccess(false);
                    return response;
                }
            }

        } catch (Exception e) {
            OpenApiApp.EXCEPTION.exception(msg, e);
        }
        return response;
    }


    @Override
    public void before(ServiceMessage msg) {
        System.out.println("before");

    }

    @Override
    public void after(ServiceMessage msg) {
        System.out.println("after");

    }
}