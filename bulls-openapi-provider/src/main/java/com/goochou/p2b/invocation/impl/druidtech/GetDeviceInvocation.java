package com.goochou.p2b.invocation.impl.druidtech;

import com.goochou.p2b.constant.farmcloud.CompanyEnum;
import com.goochou.p2b.constant.farmcloud.Constants;
import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.openapi.farmcloud.CommonResponse;
import com.goochou.p2b.invocation.BaseInvocation;
import com.goochou.p2b.invocation.IInvocation;
import com.goochou.p2b.model.TrackDevice;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: huangsj
 * @Date: 2019/6/28 16:08
 * @Description:
 */
@Service
public class GetDeviceInvocation extends BaseInvocation implements IInvocation {

    @Override
    public CommonResponse<List<TrackDevice>> executeService(Request request) {
        try {
            CommonResponse<List<TrackDevice>> response = (CommonResponse<List<TrackDevice>>)getRsp(request, Constants.DRUIDTECH_DEVICE, CompanyEnum.DRUIDTECH.getFeatureName());
            logger.info("rspJson:"+response);
            return response;
        } catch (Exception e)  {
            logger.error(e, e);
        }
        return null;
    }
}
