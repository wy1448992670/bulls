package com.goochou.p2b.module.druidtech;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.common.BaseAO;
import com.goochou.p2b.constant.farmcloud.Constants;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.openapi.farmcloud.CommonResponse;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceRequest;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.LoginResponse;
import com.goochou.p2b.invocation.impl.druidtech.GetDeviceInvocation;
import com.goochou.p2b.invocation.impl.druidtech.GetTokenInvocation;
import com.goochou.p2b.invocation.impl.druidtech.RefreshTokenInvocation;
import com.goochou.p2b.model.TrackDevice;
import com.goochou.p2b.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: huangsj
 * @Date: 2019/6/28 14:55
 * @Description:
 */
@Service
public class GetDevices extends BaseAO implements HessianInterface {

    @Autowired
    private GetTokenInvocation getTokenInvocation;
    @Autowired
    private RefreshTokenInvocation refreshTokenInvocation;
    @Autowired
    private GetDeviceInvocation deviceInvocation;

    @Override
    public CommonResponse<List<TrackDevice>> execute(ServiceMessage msg) {
        DeviceRequest req = (DeviceRequest) msg.getReq();
        CommonResponse<List<TrackDevice>> response = new CommonResponse<List<TrackDevice>>();
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

            response = deviceInvocation.executeService(req);

            if (response.getStatusCode() == 401) {
                CommonResponse<LoginResponse> res = refreshTokenInvocation.executeService(null);
                if (res.isSuccess()) {
                    Constants.DRUIDTECH_TOKEN = res.getData().getAuthentication();
                    req.setAuthentication(Constants.DRUIDTECH_TOKEN);

                    response = deviceInvocation.executeService(req);
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