package com.goochou.p2b.task.doing.impl;

import java.util.Calendar;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.goochou.p2b.service.JobService;

/**
 * 用户资金快照
 *
 */
public class AssetsSnapshotTask extends BaseTask {

	private static final long serialVersionUID = -7016862663950408734L;
	private static final Logger logger = Logger.getLogger(AssetsSnapshotTask.class);

	private static final Integer RECURSION_DEPTH =100;
	@Resource
    private JobService jobService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    	logger.info("用户资金快照 start------");
    	final JobService jobService = (JobService) applicationContext.getBean("jobServiceImpl");
        try {
        	Calendar today=Calendar.getInstance();
        	today.set(Calendar.HOUR_OF_DAY, 0);
        	today.set(Calendar.MINUTE, 0);
        	today.set(Calendar.SECOND, 0);
        	today.set(Calendar.MILLISECOND, 0);
        	jobService.doAssetsSnapshot(today,RECURSION_DEPTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("用户资金快照 end------");
    }
}
