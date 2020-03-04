package com.goochou.p2b.task.doing.impl;

import com.goochou.p2b.model.SmsSend;
import com.goochou.p2b.model.SmsSendExample;
import com.goochou.p2b.service.SmsSendService;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 短信发送
 * </p>
 *
 * @author shuys
 * @since 2019年10月18日 15:35:00
 */
public class SmsSendJobQuartzTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(SmsSendJobQuartzTask.class);
    
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        logger.info("短信发送 start");
        final SmsSendService smsSendService = (SmsSendService) applicationContext.getBean("smsSendServiceImpl");
        try {

            SmsSendExample example = new SmsSendExample();
            example.createCriteria()
                    .andReserveSendTimeLessThanOrEqualTo(new Date()) // <= 时间
//                .andSendStatusIn(Arrays.asList(-1, 0)); // 失败 or 未发
                    .andSendStatusEqualTo(0); // 未发
            List<SmsSend> smsSends = smsSendService.getMapper().selectByExample(example);

            if (!smsSends.isEmpty()) {
                for (SmsSend smsSend : smsSends) {
                    smsSendService.doSendMessage(smsSend);
                }
            }
        } catch (Exception e) {
            logger.info("短信发送 error");
            e.printStackTrace();
        }
        logger.info("短信发送 end");
    }
    
    
}
