package com.goochou.p2b.task.doing.impl;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created on 2017-12-18
 * <p>Title:       [签到]</p>
 * <p>Company:     鑫聚财线上交易平台</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * <p>Department:  研发中心</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class SignedUserJobQuartzTask extends BaseTask {

	/**
	 * <p>Discription:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = 2376113484451411272L;
	private static final Logger logger = Logger.getLogger(SignedUserJobQuartzTask.class);
	
	/**
	 * 每次执行条数
	 */
	final int LIMIT = 5000;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    	logger.info("签到定时任务 start------");
        try {
        	
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("签到定时任务 end------");
    }
}
