package com.goochou.p2b.invocation.impl.druidtech;

import com.goochou.p2b.constant.farmcloud.CompanyEnum;
import com.goochou.p2b.constant.farmcloud.Constants;
import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.openapi.farmcloud.CommonResponse;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceGpsResponse;
import com.goochou.p2b.invocation.BaseInvocation;
import com.goochou.p2b.invocation.IInvocation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: huangsj
 * @Date: 2019/7/1 13:34
 * @Description:
 */
@Service
public class GetDeviceGpsInvocation extends BaseInvocation implements IInvocation {

    @Override
    public CommonResponse<List<DeviceGpsResponse>> executeService(Request request) {
        try {
            CommonResponse<List<DeviceGpsResponse>> response = (CommonResponse<List<DeviceGpsResponse>>)getRsp(request, Constants.DRUIDTECH_DEVICE_GPS, CompanyEnum.DRUIDTECH.getFeatureName());
            logger.info("rspJson:" + response);
            return response;
        } catch (Exception e) {
            logger.error(e, e);
        }
        return null;
    }
}