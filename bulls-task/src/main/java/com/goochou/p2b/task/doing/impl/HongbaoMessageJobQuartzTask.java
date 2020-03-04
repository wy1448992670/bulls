package com.goochou.p2b.task.doing.impl;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.goochou.p2b.service.HongbaoService;

/**
 * Created on 2018-1-31
 * <p>Title:       [红包到期提醒]</p>
 * <p>Company:     鑫聚财线上交易平台</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * <p>Department:  研发中心</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class HongbaoMessageJobQuartzTask extends BaseTask {

	/**
	 * <p>Discription:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = 5113790577877089507L;
	private static final Logger logger = Logger.getLogger(HongbaoMessageJobQuartzTask.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    	logger.info("红包到期提醒 start------");
    	final HongbaoService hongbaoService = (HongbaoService) applicationContext.getBean("hongbaoServiceImpl");
        try {
        	hongbaoService.sendInvestmentHongbaoMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("红包到期提醒 end------");
    }
}
