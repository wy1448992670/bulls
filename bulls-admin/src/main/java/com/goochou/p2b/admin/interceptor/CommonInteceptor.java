package com.goochou.p2b.admin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.goochou.p2b.constant.TestEnum;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.utils.StringUtil;

public class CommonInteceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object arg2) throws Exception {
        String path = request.getContextPath();
        String scheme = StringUtil.isNull(request.getHeader("X-Forwarded-Scheme")) ? "http" : request.getHeader("X-Forwarded-Scheme");
        String basePath = "";
        if (TestEnum.RELEASE.getFeatureName().equals(com.goochou.p2b.constant.Constants.TEST_SWITCH)) {
        	basePath = "https://" + request.getServerName() + path + "/";
        }else{
        	basePath = scheme + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        }
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("basePath", basePath);
        request.setAttribute("aPath", ClientConstants.ALIBABA_PATH);
       
        return true;
    }

}
