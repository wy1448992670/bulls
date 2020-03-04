package com.goochou.p2b.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.goochou.p2b.exception.IException;
import com.goochou.p2b.hessian.exception.ServiceException;

public class ExceptionImpl implements IException  {
	
	private final static Log log = LogFactory.getLog(ExceptionImpl.class);

	@Override
	public void exception(Object o, Exception e) {
		if(e instanceof ServiceException){
			log.info("ServiceException----");
		}else if(e instanceof Exception){
			log.info("Exception----");
		}
		log.error(e.getMessage(), e);
	}

	@Override
	public void exception(Exception e) {
		this.exception(null,e);
	}
}
