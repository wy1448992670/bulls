package com.goochou.p2b.task.doing.impl;

import com.goochou.p2b.model.MallActivity;
import com.goochou.p2b.model.MallActivityExample;
import com.goochou.p2b.service.MallActivityService;
import com.goochou.p2b.utils.DateUtil;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Calendar;
import java.util.List;

/**
 * <p>
 * 商城活动task
 * </p>
 */
public class MallActivitySecondKillTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(MallActivitySecondKillTask.class);
    
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        logger.info("商品秒杀活动 start");
        final MallActivityService mallActivityService = (MallActivityService) applicationContext.getBean("mallActivityServiceImpl");
        try {
            // 前一天
            Calendar beforeOneDay = Calendar.getInstance();
            beforeOneDay.set(Calendar.DATE, beforeOneDay.get(Calendar.DATE) - 1);
            beforeOneDay.set(Calendar.HOUR_OF_DAY, 0);
            beforeOneDay.set(Calendar.MINUTE, 0);
            beforeOneDay.set(Calendar.SECOND, 0);

            // 查询当前日期的前一天的结束的秒杀活动，解锁秒杀活动期间内锁定的库存
            MallActivityExample example = new MallActivityExample();
            example.createCriteria().andTypeEqualTo(1).andStopDateEqualTo(beforeOneDay.getTime());
            List<MallActivity> list = mallActivityService.getMapper().selectByExample(example);
            
            if (!list.isEmpty()) {
                list.forEach(item -> {
                    try {
                        mallActivityService.doUnlockStockWhenActivityEnd(item.getId());
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                });
            }
            
        } catch (Exception e) {
            logger.info("商品秒杀活动 error：" + e.getMessage(), e);
            e.printStackTrace();
        }
        logger.info("商品秒杀活动 end");
    }
    
    
}
