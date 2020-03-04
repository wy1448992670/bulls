package com.goochou.p2b.invocation.impl.druidtech;

import com.goochou.p2b.constant.farmcloud.CompanyEnum;
import com.goochou.p2b.constant.farmcloud.Constants;
import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.openapi.farmcloud.CommonResponse;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.LoginResponse;
import com.goochou.p2b.invocation.BaseInvocation;
import com.goochou.p2b.invocation.IInvocation;
import org.springframework.stereotype.Service;

/**
 * @Auther: huangsj
 * @Date: 2019/6/28 16:08
 * @Description:
 */
@Service
public class RefreshTokenInvocation extends BaseInvocation implements IInvocation {

    @Override
    public CommonResponse<LoginResponse> executeService(Request request) {
        try {
            CommonResponse<LoginResponse> response = (CommonResponse<LoginResponse>)getRsp(request, Constants.DRUIDTECH_REFRESH_TOKEN, CompanyEnum.DRUIDTECH.getFeatureName());
            return response;
        } catch (Exception e)  {
            logger.error(e, e);
        }
        return null;
    }
}
