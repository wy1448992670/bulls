package com.goochou.p2b.invocation.impl.druidtech;

import com.goochou.p2b.constant.farmcloud.CompanyEnum;
import com.goochou.p2b.constant.farmcloud.Constants;
import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.openapi.farmcloud.CommonResponse;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceBehaviorResponse;
import com.goochou.p2b.invocation.BaseInvocation;
import com.goochou.p2b.invocation.IInvocation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: huangsj
 * @Date: 2019/6/28 16:08
 * @Description:
 */
@Service
public class GetDeviceBehaviorInvocation extends BaseInvocation implements IInvocation {

    @Override
    public CommonResponse<List<DeviceBehaviorResponse>> executeService(Request request) {
        try {
            CommonResponse<List<DeviceBehaviorResponse>> response = (CommonResponse<List<DeviceBehaviorResponse>>)getRsp(request, Constants.DRUIDTECH_DEVICE_BEHAVIOR, CompanyEnum.DRUIDTECH.getFeatureName());
            logger.info("rspJson:" + response);
            return response;
        } catch (Exception e)  {
            logger.error(e, e);
        }
        return null;
    }
}