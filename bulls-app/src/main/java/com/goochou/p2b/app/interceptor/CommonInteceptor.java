package com.goochou.p2b.app.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.goochou.p2b.constant.TestEnum;
import com.goochou.p2b.constant.client.ClientConstants;

public class CommonInteceptor implements HandlerInterceptor {
    
    private static final Logger logger = Logger.getLogger(CommonInteceptor.class);

    @Override
    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse response, Object arg2, Exception arg3)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object arg2) throws Exception {
        String path = request.getContextPath();
        String basePath = "";
        if (TestEnum.RELEASE.getFeatureName().equals(com.goochou.p2b.constant.Constants.TEST_SWITCH)) {
            basePath = "https://" + request.getServerName() + path + "/";
        }else{
            basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        }
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("basePath", basePath);
        request.setAttribute("staticPath", ClientConstants.ALIBABA_PATH);
        return true;
    }

}
