package com.goochou.p2b.task.doing.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.goochou.p2b.service.YaoCountService;
import com.goochou.p2b.utils.DateFormatTools;

/**
 * Created on 2018-1-31
 * <p>Title:       [每日摇一摇初始化]</p>
 * <p>Company:     鑫聚财线上交易平台</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * <p>Department:  研发中心</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class YaoCountJobQuartzTask extends BaseTask {

    /**
	 * <p>Discription:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = -2179418022323338535L;

	private static final Logger logger = Logger.getLogger(YaoCountJobQuartzTask.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    	logger.info("摇一摇初始化 start------");
    	final YaoCountService yaoCountService = (YaoCountService) applicationContext.getBean("yaoCountServiceImpl");
        try {
        	logger.info("开始执行时间：" + DateFormatTools.dateToStr1(new Date()));
            List<Integer> list = yaoCountService.selectAllIncomeUser();
            if (null != list && !list.isEmpty()) {
                for (Integer userId : list) {
                    try {
                        yaoCountService.updateCount(userId);
                    } catch (Exception e) {
                    	logger.error(e);
                    	logger.error("=================摇一摇更新中=================" + userId + "ID用户更新失败");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("摇一摇初始化 end------");
    }
}
