package com.goochou.p2b.task.doing.impl;

import java.io.Serializable;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.goochou.p2b.hessian.HessianClient;
import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;

public abstract class BaseTask extends QuartzJobBean implements Serializable {
	/**
     * <p>Discription:[字段功能描述]</p>
     */
    private static final long serialVersionUID = 2567514317313869830L;

    protected ApplicationContext applicationContext;

    /**
     * 从SchedulerFactoryBean注入的applicationContext.
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

	public static Response callHession(HessianClient client, String event, Request request){
		ServiceMessage msg = new ServiceMessage(event, request);
        return client.setServiceMessage(msg).send();
	}
}
