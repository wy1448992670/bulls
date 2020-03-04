package com.goochou.p2b.invocation.impl.druidtech;

import com.goochou.p2b.constant.farmcloud.CompanyEnum;
import com.goochou.p2b.constant.farmcloud.Constants;
import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.openapi.farmcloud.CommonResponse;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceTotalBehaviorResponse;
import com.goochou.p2b.invocation.BaseInvocation;
import com.goochou.p2b.invocation.IInvocation;
import org.springframework.stereotype.Service;

/**
 * @Auther: huangsj
 * @Date: 2019/6/28 16:09
 * @Description:
 */
@Service
public class GetDeviceTotalBehaviorInvocation extends BaseInvocation implements IInvocation {

    @Override
    public CommonResponse<DeviceTotalBehaviorResponse> executeService(Request request) {
        try {
            CommonResponse<DeviceTotalBehaviorResponse> response = (CommonResponse<DeviceTotalBehaviorResponse>)getRsp(request, Constants.DRUIDTECH_DEVICE_TOTAL_BEHAVIOR, CompanyEnum.DRUIDTECH.getFeatureName());
            logger.info("rspJson:" + response);
            return response;
        } catch (Exception e) {
            logger.error(e, e);
        }
        return null;
    }
}