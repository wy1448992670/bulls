package com.goochou.p2b.task.doing.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.goochou.p2b.model.Investment;
import com.goochou.p2b.service.InvestmentService;

/**
 * 投资订单到期
 * Created on 2019-05-20
 * <p>Title:       [牛只投资订单自动回购]</p>
 * @author         [张琼麒]
 * @update:[日期2019-05-17] [张琼麒]
 * @version        1.0
 */
public class InvestmentDueTask extends BaseTask {

	private static final long serialVersionUID = 2553116224855470049L;
	private static final Logger logger = Logger.getLogger(InvestmentDueTask.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    	logger.info("投资订单到期 start------");
    	InvestmentService investmentService=(InvestmentService) applicationContext.getBean("investmentServiceImpl");
    	List<Investment> dueInvestmentList;
    	try {
        	dueInvestmentList=investmentService.getInvestmentDueInToday();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            dueInvestmentList=new ArrayList<Investment>();
        }
        
        for(Investment dueInvestment:dueInvestmentList) {
        	try {
        		logger.info(dueInvestment.getOrderNo()+"投资订单到期");
        		investmentService.doDue(dueInvestment);
        		logger.info(dueInvestment.getOrderNo()+"投资订单到期开始");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
		}
        
        logger.info("投资订单到期 end------");
    }
}
