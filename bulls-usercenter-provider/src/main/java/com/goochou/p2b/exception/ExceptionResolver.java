package com.goochou.p2b.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.goochou.p2b.common.BaseAction;
import com.goochou.p2b.hessian.Response;

public class ExceptionResolver extends BaseAction implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		if (handler == null || ex == null) {
			return null;
		}
		if (!(handler instanceof HandlerMethod)) {
			return null;
		}
		
		if (ex instanceof ResponseException) {
			Response result = ((ResponseException) ex).getResponse();
			if (result != null) {
				outResult(response, result);
			}
			return null;
		}
		Response e = new Response(false, ex.getMessage());
		e.setErrorCode("9999");
		
		outResult(response, e);
		log.error("未处理相关异常"+ex.getMessage(), ex);
		return null;
	}

}
