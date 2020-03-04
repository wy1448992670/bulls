package com.goochou.p2b.hessian;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.common.BaseAO;
import com.goochou.p2b.exception.ParamsException;
import com.goochou.p2b.hessian.api.HessianInterface;

public class HessianImpl extends BaseAO implements HessianInterface {

	static Map<String, Service> interfaceMap = new HashMap<String, Service>();

	public void setInterfaceMap(Map<String, Service> interfaceMap) {
		HessianImpl.interfaceMap = interfaceMap;
	}

	@Override
	public Response execute(final ServiceMessage msg) {
		long start = System.currentTimeMillis();
		log.info("REQUEST===>" + JSONArray.toJSONString(msg));
		Response result = new Response();
		try {
			Service classs = interfaceMap.get(msg.getMethod());
			if(null == classs){
				log.error("方法["+msg.getMethod()+"]未定义");
				result = new ErrorResponse("100", "方法["+msg.getMethod()+"]未定义", "100", "MethodException");
				return result;
			}
			Class<?> impl = Class.forName(classs.getServiceImpl());
			HessianInterface interFace = (HessianInterface) getBean(impl);
			if (null == interFace) {
				log.error("接口["+msg.getMethod()+"]未实现");
				result = new ErrorResponse("200", "接口["+msg.getMethod()+"]未实现", "200", "ServiceImplException");
				return result;
			}
			interFace.before(msg);
			result = interFace.execute(msg);
			interFace.after(msg);
		} catch (Exception e) {
			if(e instanceof ParamsException){
				result = new ErrorResponse("300", "非法参数", "300", "ParamsException");
			}else{
				result = new ErrorResponse("500", "服务异常", "500", "ServiceException");
			}
			log.error("系统异常", e);
		} catch (Error e) {
			log.error("系统异常", e);
			result = new ErrorResponse("400", "系统异常", "400", "SystemException");
		} finally {
			log.info("RESPONSE===>" + JSONArray.toJSONString(result));
		}
		long end = System.currentTimeMillis();
		log.info((end-start)+"ms");
		return result;
	}

	private Object getBean(Class<?> impl) {
		return (Object) OpenApiApp.SPRING_CONTEXT.getBean(impl);
	}
	
	@Override
	public void before(ServiceMessage msg) {
	}

	@Override
	public void after(ServiceMessage msg) {
	}
}
