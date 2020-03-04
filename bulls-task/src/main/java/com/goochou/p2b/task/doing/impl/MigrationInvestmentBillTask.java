package com.goochou.p2b.task.doing.impl;

import com.goochou.p2b.model.MigrationInvestmentBill;
import com.goochou.p2b.model.MigrationInvestmentBillExample;
import com.goochou.p2b.service.MigrationInvestmentBillService;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 迁移用户派息 （0-2点，每十分钟执行一次）
 * </p>
 *
 * @author shuys
 * @since 2019年10月22日 14:47:00
 */
public class MigrationInvestmentBillTask extends BaseTask {

    private static final Logger logger = Logger.getLogger(MigrationInvestmentBillTask.class);
    
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("迁移用户利息发放 start------");
        try {
            final MigrationInvestmentBillService migrationInvestmentBillService = (MigrationInvestmentBillService) applicationContext.getBean("migrationInvestmentBillServiceImpl");

            Date now = new Date();
            MigrationInvestmentBillExample example = new MigrationInvestmentBillExample();
            example.createCriteria()
                    .andIsReceiveEqualTo(false) // 未回款
                    .andReceiveTimeLessThanOrEqualTo(now); //应派息时间 <= 当前时间
            List<MigrationInvestmentBill> list = migrationInvestmentBillService.getMapper().selectByExample(example);
            if (!list.isEmpty()) {
                for (MigrationInvestmentBill bill : list) {
                    migrationInvestmentBillService.doDividend(bill);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            logger.info("迁移用户利息发放 error------");
        }
        logger.info("迁移用户利息发放 end------");  
    }
}
