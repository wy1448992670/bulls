package com.goochou.p2b.service.impl;

import com.goochou.p2b.constant.BusinessTableEnum;
import com.goochou.p2b.constant.assets.AccountOperateEnum;
import com.goochou.p2b.dao.AssetsMapper;
import com.goochou.p2b.dao.MigrationInvestmentBillMapper;
import com.goochou.p2b.dao.UserMapper;
import com.goochou.p2b.model.*;
import com.goochou.p2b.model.MigrationInvestmentBillExample.Criteria;
import com.goochou.p2b.service.MigrationInvestmentBillService;
import com.goochou.p2b.service.MigrationInvestmentService;
import com.goochou.p2b.service.TradeRecordService;
import com.goochou.p2b.service.UserAccountService;
import com.goochou.p2b.service.exceptions.LockFailureException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 注释
 * </p>
 *
 * @author shuys
 * @since 2019年10月22日 13:32:00
 */
@Service
public class MigrationInvestmentBillServiceImpl implements MigrationInvestmentBillService {

    private final static Logger logger = Logger.getLogger(MigrationInvestmentBillServiceImpl.class);
    
    @Resource
    private MigrationInvestmentBillMapper migrationInvestmentBillMapper;
    
    @Resource
    private TradeRecordService tradeRecordService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private AssetsMapper assetsMapper;

    @Resource
    private UserAccountService userAccountService;

    @Resource
    private MigrationInvestmentService migrationInvestmentService;
    
    @Override
    public MigrationInvestmentBillMapper getMapper() {
        return this.migrationInvestmentBillMapper;
    }

    @Override
    public int updateForVersion(MigrationInvestmentBill migrationInvestmentBill) throws Exception {
        if (migrationInvestmentBill.getId() == null) {
            throw new Exception("id不能为空");
        }
        if (migrationInvestmentBill.getVersion() == null) {
            throw new Exception("版本号不能为空");
        }
        MigrationInvestmentBillExample example = new MigrationInvestmentBillExample();
        example.createCriteria()
                .andIdEqualTo(migrationInvestmentBill.getId())
                .andVersionEqualTo(migrationInvestmentBill.getVersion());
        migrationInvestmentBill.setVersion(migrationInvestmentBill.getVersion() + 1);
        if(1 != migrationInvestmentBillMapper.updateByExampleSelective(migrationInvestmentBill, example)) {
            throw new LockFailureException();
        }
        return 1;
    }

    @Override
    public void doDividend(MigrationInvestmentBill bill) {
        bill = migrationInvestmentBillMapper.selectByPrimaryKey(bill.getId());
        if (bill.getIsReceive()) { // 已回款
            logger.error("回款失败，该用户已汇款，" + bill.getUserId());
            return;
        }
        Date now = new Date();
        try {
            // 用户上锁
            User user = userMapper.selectByPrimaryKeyForUpdate(bill.getUserId().intValue());
            // 获取用户的资产
            Assets assets = assetsMapper.selectByPrimaryKeyForUpdate(user.getId());
            boolean flag = true;
            // 迁移用户,回款本金(增加余额)
            if(userAccountService.modifyAccount(assets, bill.getReceiveCorpus(), bill.getId().intValue(),
                    BusinessTableEnum.migration_investment_bill, AccountOperateEnum.MIGRATION_PRINCIPAL_BALANCE_ADD) == 0) {
                flag = false;
                logger.error("更新余额失败，" + assets.getUserId());
            }
            BigDecimal interest = bill.getReceiveInterest().add(bill.getReceiveIncreaseInterest());
            // 迁移用户,回款利息(增加余额)
            if(userAccountService.modifyAccount(assets, interest, bill.getId().intValue(),
                    BusinessTableEnum.migration_investment_bill, AccountOperateEnum.MIGRATION_INTEREST_BALANCE_ADD) == 0) {
                flag = false;
                logger.error("更新余额失败，" + assets.getUserId());
            }
            if (flag) {
                assets.setUpdateDate(now);
                // 更新实际收款时间
                bill.setRealReceiveTime(now);
                // 更新为已还款
                bill.setIsReceive(true);
                if (1 == assetsMapper.updateByPrimaryKeySelective(assets)) {
                    this.updateForVersion(bill);
                    // 查询用户当前投资账单是否存在 未还 账单
                    MigrationInvestmentBillExample example = new MigrationInvestmentBillExample();
                    example.createCriteria()
                            .andUserIdEqualTo(bill.getUserId())
                            .andMigrationInvestmentIdEqualTo(bill.getMigrationInvestmentId())
                            .andIsReceiveEqualTo(false); // 未回款
                    List<MigrationInvestmentBill> list = migrationInvestmentBillMapper.selectByExample(example);
                    if (list.isEmpty()) { // 如果不存在 未还 账单,更新投资账单为 已结清
                        MigrationInvestment migrationInvestment = migrationInvestmentService.getMapper().selectByPrimaryKey(bill.getMigrationInvestmentId());
                        if (!migrationInvestment.getIsFinished()) { // 未结清
                            // 更新投资账单为 已结清
                            migrationInvestment.setIsFinished(true);
                            migrationInvestment.setFinishedTime(now);
                            migrationInvestmentService.updateForVersion(migrationInvestment);
                        }
                    }
                } else {
                    logger.error("更新用户资金失败，" + assets.getUserId());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public List<MigrationInvestmentBill> listMigrationInvestmentBill(Integer migrationInvestmentId) {
        MigrationInvestmentBillExample example = new MigrationInvestmentBillExample();
        Criteria criteria = example.createCriteria();
        if(migrationInvestmentId != null) {
            criteria.andMigrationInvestmentIdEqualTo((long)migrationInvestmentId);
        }
        
        return migrationInvestmentBillMapper.selectByExample(example);
    }
    
}
