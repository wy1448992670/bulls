package com.goochou.p2b.service.impl;

import com.goochou.p2b.dao.MigrationInvestmentMapper;
import com.goochou.p2b.dao.MigrationInvestmentViewMapper;
import com.goochou.p2b.model.MigrationInvestment;
import com.goochou.p2b.model.MigrationInvestmentExample;
import com.goochou.p2b.model.MigrationInvestmentView;
import com.goochou.p2b.model.MigrationInvestmentViewExample;
import com.goochou.p2b.model.MigrationInvestmentViewExample.Criteria;
import com.goochou.p2b.service.MigrationInvestmentService;
import com.goochou.p2b.service.exceptions.LockFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

/**
 * <p>
 * 注释
 * </p>
 *
 * @author shuys
 * @since 2019年10月22日 13:32:00
 */
@Service
public class MigrationInvestmentServiceImpl implements MigrationInvestmentService {

    @Resource
    private MigrationInvestmentMapper migrationInvestmentMapper;
    @Resource
    private MigrationInvestmentViewMapper migrationInvestmentViewMapper;

    @Override
    public MigrationInvestmentMapper getMapper() {
        return this.migrationInvestmentMapper;
    }

    @Override
    public int updateForVersion(MigrationInvestment migrationInvestment) throws Exception {
        if (migrationInvestment.getId() == null) {
            throw new Exception("id不能为空");
        }
        if (migrationInvestment.getVersion() == null) {
            throw new Exception("版本号不能为空");
        }
        MigrationInvestmentExample example = new MigrationInvestmentExample();
        example.createCriteria()
                .andIdEqualTo(migrationInvestment.getId())
                .andVersionEqualTo(migrationInvestment.getVersion());
        migrationInvestment.setVersion(migrationInvestment.getVersion() + 1);
        if(1 != migrationInvestmentMapper.updateByExampleSelective(migrationInvestment, example)) {
            throw new LockFailureException();
        }
        return 1;
    }

    @Override
    public List<MigrationInvestmentView> listMigrationInvestment(Integer userId, Integer status, Integer orderType, Integer limitStart, Integer limitEnd) {
        MigrationInvestmentViewExample example = listCondition(userId, status, orderType, limitStart, limitEnd);
        return migrationInvestmentViewMapper.selectByExample(example);
    }

    @Override
    public int countMigrationInvestment(Integer userId, Integer status, Integer orderType) {
        MigrationInvestmentViewExample example = listCondition(userId, status, orderType, null, null);
        return (int)migrationInvestmentViewMapper.countByExample(example);
    }
    
    private static MigrationInvestmentViewExample listCondition(Integer userId, Integer status, Integer orderType, Integer limitStart, Integer limitEnd) {
        MigrationInvestmentViewExample example = new MigrationInvestmentViewExample();
        if(orderType!=null) {
            if(orderType == 0) {
                example.setOrderByClause("next_receive_time"); //下期还款时间-正序
            } else if(orderType == 1) {
                example.setOrderByClause("next_receive_time desc"); //下期还款时间-倒序
            } else if(orderType == 2) {
                example.setOrderByClause("last_receive_time"); //末期还款时间-正序
            } else if(orderType == 3) {
                example.setOrderByClause("last_receive_time desc"); //末期还款时间-倒序
            } else if(orderType == 4) {
                example.setOrderByClause("not_received_corpus"); //待收金额-正序
            } else if(orderType == 5) {
                example.setOrderByClause("not_received_corpus desc"); //待收金额-倒序
            }
        }
        if(limitStart != null && limitEnd != null) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        
        Criteria criteria = example.createCriteria();
        if(userId != null) {
            criteria.andUserIdEqualTo((long)userId);
        }
        if(status != null) {
            if(status == 0) {
                criteria.andIsFinishedEqualTo(false);
            } else if(status == 1) {
                criteria.andIsFinishedEqualTo(true);
            }
        }
        
        return example;
    }
    
}
