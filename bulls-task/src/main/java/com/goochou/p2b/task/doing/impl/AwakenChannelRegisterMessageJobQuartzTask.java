package com.goochou.p2b.task.doing.impl;

import com.goochou.p2b.constant.DictConstants;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.model.Activity;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.bo.AdvertisementChannelRegisterUserBO;
import com.goochou.p2b.service.ActivityService;
import com.goochou.p2b.service.AdvertisementChannelService;
import com.goochou.p2b.service.SmsSendService;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 注释
 * </p>
 *
 * @author shuys
 * @since 2019年10月15日 10:54:00
 */
public class AwakenChannelRegisterMessageJobQuartzTask extends BaseTask {

    private static final long serialVersionUID = -7696893612568803486L;

    private static final Logger logger = Logger.getLogger(AwakenChannelRegisterMessageJobQuartzTask.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("发送注册用户唤醒短信 start");
        Date now=new Date();
        try {
            final AdvertisementChannelService advertisementChannelService = (AdvertisementChannelService) applicationContext.getBean("advertisementChannelServiceImpl");
            
            List<AdvertisementChannelRegisterUserBO> list = advertisementChannelService.getMapper().queryChannelRegisterUser();
            if (!list.isEmpty()) {
                for (AdvertisementChannelRegisterUserBO bo : list) {
                    advertisementChannelService.doSendMessage(bo);
                }
            }
        } catch (Exception e) {
            logger.info("发送注册用户唤醒短信 error");
            e.printStackTrace();
        }
        logger.info("发送注册用户唤醒短信 end");


		// 设置发送时间（每天下午八点）
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.set(Calendar.HOUR_OF_DAY, 12);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
        
        logger.info("邀请投资活动短信 start");
		ActivityService activityService = (ActivityService) applicationContext.getBean("activityServiceImpl");
		SmsSendService smsSendService = (SmsSendService) applicationContext.getBean("smsSendServiceImpl");
		//邀请投资活动
		Activity activity=activityService.queryActivityById(111);
		if(activity.getStatus()==1 
				&& (activity.getStartTime()==null || activity.getStartTime().compareTo(now)<=0)
				&& (activity.getEndTime()==null || activity.getEndTime().compareTo(now)>0)
				) {
			logger.info("发送邀请人唤醒短信 start");
			try {
				// 邀请人未领养，被邀请人在活动期内首投
				List<User> awakenInviterList = activityService.getFirstInvestmentWaitInviter();
				for (User inviter : awakenInviterList) {
					String messageContent = DictConstants.INVITE_FIRST_INVESTMENT_WAIT_INVITER_CODE;
					//messageContent = messageContent.replace("{url}", ClientConstants.H5_URL + "#/bulls?qd=1");
					// 发送短信
					smsSendService.addSmsSend(inviter.getPhone(), messageContent, null, c.getTime());
				}
			} catch (Exception e) {
				logger.info("发送邀请人唤醒短信 error");
				e.printStackTrace();
			}
			logger.info("发送邀请人唤醒短信 end");
			logger.info("发送被邀请人唤醒短信 start");
			try {
				// 邀请人已领养，被邀请人一天内未投资
				List<User> awakenInviteeList = activityService.getFirstInvestmentWaitInvitee();
				for (User invitee : awakenInviteeList) {
					String messageContent = DictConstants.INVITE_FIRST_INVESTMENT_WAIT_INVITEE_CODE;
					messageContent = messageContent.replace("{url}",
							ClientConstants.APP_ROOT + "/i?i=" + invitee.getId());
					// 发送短信
					smsSendService.addSmsSend(invitee.getPhone(), messageContent, null, c.getTime());
				}
			} catch (Exception e) {
				logger.info("发送被邀请人唤醒短信 error");
				e.printStackTrace();
			}
			logger.info("发送被邀请人唤醒短信 end");
		}
		logger.info("邀请投资活动短信 end");

	}
}
