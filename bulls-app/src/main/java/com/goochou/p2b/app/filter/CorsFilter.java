    package com.goochou.p2b.app.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.TestEnum;

public class CorsFilter implements Filter {

	private static HashSet<String> domainSet = new HashSet<String>();
	
    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        String originHead = request.getHeader("Origin");

        HttpSession session = null;
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

        String[] ALLOW_DOMAIN = {"https://wap.bfmuchang.com","https://www.bfmuchang.com","http://192.168.1.107:8081","http://192.168.1.199:8081","http://192.168.1.233:8081","http://192.168.1.149:9090","http://192.168.1.33:8081","http://192.168.1.6:3081","http://192.168.1.6:3082","http://192.168.1.6:3083","http://101.80.207.189:3082","http://192.168.1.39:8081"};

        if(TestEnum.DEBUG.getFeatureName().equals(Constants.TEST_SWITCH)){
            if (Arrays.asList(ALLOW_DOMAIN).contains(originHead)) {
                response.setHeader("Access-Control-Allow-Origin", originHead);
            }
        }else{
//            response.setHeader("Access-Control-Allow-Origin", "https://wap.bfmuchang.com");
            response.setHeader("Access-Control-Allow-Origin", originHead);
        }
        if(domainSet.contains(request.getServerName())){
        	if (request.getSession(false) == null) {
        		session = request.getSession();
        		Cookie cookie = new Cookie("JSESSIONID", session.getId());
        		cookie.setDomain("test.com");
        		cookie.setMaxAge(Integer.MAX_VALUE);
        		response.addCookie(cookie);
        	} else {
        		session = request.getSession(false);
        	}
        }
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    	domainSet.add("test.com");
    	domainSet.add("wap.com");
    	domainSet.add("localhost");
    }

}
