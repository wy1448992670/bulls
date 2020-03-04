package com.goochou.p2b.task.doing.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.goochou.p2b.model.User;
import com.goochou.p2b.model.UserExample;
import com.goochou.p2b.model.UserExample.Criteria;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.service.impl.UserServiceImpl;

/**
 *  vip 用户利息发放 
 * @author wangyun
 * @date 2019年10月9日
 * @time 上午11:23:18
 */
public class UserDividendJobQuartzTask extends BaseTask {
    
    private static final long serialVersionUID = -3120709320082315597L;
    private static final Logger logger = Logger.getLogger(UserDividendJobQuartzTask.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("用户利息发放 start------");
    	final UserService userService = (UserService) applicationContext.getBean("userServiceImpl");
    	
    	// 1. 查询需要发放利润的vip用户
    	UserExample example = new UserExample();
    	Criteria c = example.createCriteria(); //0普通用户1会员用户 2vip用户
    	c.andLevelEqualTo(2); 
        List<User> list = userService.getUserMapper().selectByExample(example);

        if(list == null || list.size() == 0) {
        	logger.info("暂无发放利息VIP用户----------");
        	return;
        }

        for (User user : list) {
        	try {
        		userService.doVipUserFreezeProfitJob(user);
        	} catch (Exception e) {
        		e.printStackTrace();
        		logger.error(e.getMessage(), e);
			}
        }
         
        logger.info("用户利息发放 end------");
    }
}
