package com.goochou.p2b.task.doing.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.goochou.p2b.model.ActivityBlessingRegularGive;
import com.goochou.p2b.service.ActivityBlessingRegularGiveService;

/**
 * Created on 2019-07-05
 * <p>Title:       [GPS设备装载数据]</p>
 * <p>Department:  研发中心</p>
 * @author         [张琼麒] [259392141@qq.com]
 * @version        1.0
 */
public class ActivityBlessingRegularGiveTask extends BaseTask {

	private static final long serialVersionUID = -3962590249398941978L;

	private static final Logger logger = Logger.getLogger(ActivityBlessingRegularGiveTask.class);
	
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    	logger.info("------定期发送抽副卡次数 start------");
    	ActivityBlessingRegularGiveService activityBlessingRegularGiveService=(ActivityBlessingRegularGiveService) applicationContext.getBean("activityBlessingRegularGiveServiceImpl");
    	List<ActivityBlessingRegularGive> activityBlessingRegularGiveList;
		try {
			activityBlessingRegularGiveList = activityBlessingRegularGiveService.getNoGivenList();
			for(ActivityBlessingRegularGive activityBlessingRegularGive:activityBlessingRegularGiveList) {
	    		try {
	    			activityBlessingRegularGiveService.doGive(activityBlessingRegularGive);
				} catch (Exception e) {
					logger.error(e, e);
				}
	    	}
		} catch (Exception e) {
			logger.error(e, e);
		}
    	
        logger.info("------定期发送抽副卡次数 end------");
    }
}
