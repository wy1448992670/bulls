package com.goochou.p2b.module.druidtech;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.common.BaseAO;
import com.goochou.p2b.constant.farmcloud.Constants;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.openapi.farmcloud.CommonResponse;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceTotalBehaviorRequest;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceTotalBehaviorResponse;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.LoginResponse;
import com.goochou.p2b.invocation.impl.druidtech.GetDeviceTotalBehaviorInvocation;
import com.goochou.p2b.invocation.impl.druidtech.GetTokenInvocation;
import com.goochou.p2b.invocation.impl.druidtech.RefreshTokenInvocation;
import com.goochou.p2b.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: huangsj
 * @Date: 2019/6/28 14:56
 * @Description:
 */
@Service
public class GetDeviceTotalMsg  extends BaseAO implements HessianInterface {

    @Autowired
    private GetTokenInvocation getTokenInvocation;
    @Autowired
    private RefreshTokenInvocation refreshTokenInvocation;
    @Autowired
    private GetDeviceTotalBehaviorInvocation totalBehaviorInvocation;

    @Override
    public CommonResponse<DeviceTotalBehaviorResponse> execute(ServiceMessage msg) {
        DeviceTotalBehaviorRequest req = (DeviceTotalBehaviorRequest) msg.getReq();
        CommonResponse<DeviceTotalBehaviorResponse> response = null;
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

            response = totalBehaviorInvocation.executeService(req);

            if (response.getStatusCode() == 401) {
                CommonResponse<LoginResponse> res = refreshTokenInvocation.executeService(null);
                if (res.isSuccess()) {
                    Constants.DRUIDTECH_TOKEN = res.getData().getAuthentication();
                    req.setAuthentication(Constants.DRUIDTECH_TOKEN);

                    response = totalBehaviorInvocation.executeService(req);
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