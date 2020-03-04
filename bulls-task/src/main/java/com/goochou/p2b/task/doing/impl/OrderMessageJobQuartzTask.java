package com.goochou.p2b.task.doing.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.goochou.p2b.model.OrderDone;
import com.goochou.p2b.service.MessagePushService;
import com.goochou.p2b.service.OrderDoneService;

/**
 * 各种短信
 * @author ydp
 * @date 2019/06/19
 */
public class OrderMessageJobQuartzTask extends BaseTask {
    
	/**
     *
     */
    private static final long serialVersionUID = -3120709320082315597L;
    private static final Logger logger = Logger.getLogger(OrderMessageJobQuartzTask.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    	logger.info("各种短信 start------");
    	final OrderDoneService orderDoneService = (OrderDoneService) applicationContext.getBean("orderDoneServiceImpl");
        try {
            List<OrderDone> dones = orderDoneService.queryOrderDoneNeedSendMessage();
            while (!dones.isEmpty()) {
                Iterator<OrderDone> it = dones.iterator();
                while (it.hasNext()) {
                    boolean b = orderDoneService.doSend(it.next());
                    if (b) {
                        it.remove();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("各种短信 end------");
        logger.info("app推送 start------");
    	final MessagePushService messagePushService = (MessagePushService) applicationContext.getBean("messagePushServiceImpl");
        try {
        	messagePushService.doMessagePush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("推送异常",e);
        }
        logger.info("app推送 end------");
    }
}
