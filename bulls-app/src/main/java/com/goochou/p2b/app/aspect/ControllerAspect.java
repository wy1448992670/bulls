package com.goochou.p2b.app.aspect;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONArray;
import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.utils.JSONAlibabaUtil;

/**
 * @author ydp
 * @date 2019/06/04
 */
@Aspect
public class ControllerAspect {
    
    private static final Logger logger = Logger.getLogger(ControllerAspect.class);
    
    // 所有方法的执行作为切入点
    @Before("execution(* com.goochou.p2b.app.controller.*.*(..))")
    public void beforeMethod(JoinPoint jp) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request  = attributes.getRequest();
        long reqId = System.currentTimeMillis();
        request.setAttribute("reqId", reqId);
        logger.info("---before["+reqId+"]----");
        logger.info(request.getRequestURI());
        Map<String, Object> res = new HashMap<String, Object>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                //如果字段的值为空，判断若值为空，则删除这个字段>
                if (null == res.get(en) || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        logger.info(JSONAlibabaUtil.parseMap(res));
    }
    @AfterReturning(returning = "rvt", pointcut = "execution(* com.goochou.p2b.app.controller.*.*(..))")
    // 声明rvt时指定的类型会限制目标方法必须返回指定类型的值或没有返回值
    // 此处将rvt的类型声明为Object，意味着对目标方法的返回值不加限制
    public void log(JoinPoint jp, Object rvt) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request  = attributes.getRequest();
        logger.info("---after["+request.getAttribute("reqId")+"]---");
        if(rvt instanceof AppResult) {
            AppResult result = (AppResult)rvt;
            try {
                logger.info(JSONArray.toJSON(result));
            } catch (Exception e) {
                 e.printStackTrace();
            }
        }
        logger.info("---end["+request.getAttribute("reqId")+"]["+(System.currentTimeMillis()-Long.valueOf(request.getAttribute("reqId")+""))+"ms]---");
    }
}
