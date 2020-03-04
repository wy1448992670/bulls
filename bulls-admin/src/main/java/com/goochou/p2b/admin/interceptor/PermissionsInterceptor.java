package com.goochou.p2b.admin.interceptor;

import java.util.Date;
import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.goochou.p2b.model.AdminLog;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.service.AdminLogService;
import com.goochou.p2b.utils.CommonUtil;

public class PermissionsInterceptor implements HandlerInterceptor {
	
	private static final Logger logger = Logger.getLogger(PermissionsInterceptor.class);
    
    @Resource
    private AdminLogService adminLogService;

    private String[] ignoreUris;

    public void setIgnoreUris(String[] ignoreUris) {
        this.ignoreUris = ignoreUris;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception ex) throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mv) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        boolean b = false;
        String url = request.getRequestURI();
        for (String uri : ignoreUris) {
            if (url.contains(uri)) {
                b = true;
                break;
            }
        }
        if (!b) {
            Subject currentUser = SecurityUtils.getSubject();
            UserAdmin uAdmin = (UserAdmin) currentUser.getSession().getAttribute("user");
            if (uAdmin != null) {
//                if (currentUser.isPermitted("coupon:audit")) {
//                    List<RateCouponAudit> list = rateCouponService.getByStatus(0);
//                    request.setAttribute("msgList", list);
//                    request.setAttribute("msgCount", list.size());
//                }
            	String path = request.getRequestURI();
            	StringBuffer sb = new StringBuffer();
            	Enumeration pNames=request.getParameterNames();
            	while(pNames.hasMoreElements()){
            	    String name=(String)pNames.nextElement();
            	    String value=request.getParameter(name);
            	    sb.append("&"+name + "=" + value);
            	}
            	logger.info("user="+uAdmin.getTrueName()+";path="+path+";type:"+request.getMethod()+";params="+sb.toString());
            	
            	try {
            		AdminLog log = new AdminLog();
                	log.setAdminId(uAdmin.getId());
                	log.setAdminIp(CommonUtil.getIpAddr(request));
                	log.setAdminUserName(uAdmin.getTrueName());
                	log.setLvl(1);
                	log.setOperateTime(new Date());
                	log.setRemark(path);
                	adminLogService.save(log);
				} catch (Exception e) {
					logger.error("璁板綍admin_log鏃ュ織澶辫触", e);
				}
            	
            	
                b = true;
            } else {
                String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
                response.sendRedirect(basePath + "login");
            }
        }
        return true;
    }
}
