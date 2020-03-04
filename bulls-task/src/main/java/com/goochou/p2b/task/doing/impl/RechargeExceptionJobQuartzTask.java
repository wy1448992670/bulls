package com.goochou.p2b.task.doing.impl;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;

/**
 * 充值例外数据查询接口业务处理
 * @author WuYJ
 *
 */
public class RechargeExceptionJobQuartzTask extends BaseTask {


	/**
	 * 
	 */
	private static final long serialVersionUID = 464059093634790138L;
	
	private static final Logger logger = Logger.getLogger(RechargeExceptionJobQuartzTask.class);


    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException
    {
        logger.info("充值例外数据查询接口业务处理定时任务 start------");
        Response response = new Response();
		Request request = new Request();
		ServiceMessage msg = new ServiceMessage("recharge.exception", request);
		response = (Response) OpenApiClient.getInstance().setServiceMessage(msg).send();
		if(response.isSuccess()){
            logger.info("充值例外数据查询接口业务处理成功！");
        }else{
            logger.info("充值例外数据查询接口业务处理失败！");
        }
        logger.info("充值例外数据查询接口业务处理定时任务 end------");
        
    }

}
