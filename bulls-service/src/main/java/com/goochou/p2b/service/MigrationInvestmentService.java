package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.dao.MigrationInvestmentMapper;
import com.goochou.p2b.model.MigrationInvestment;
import com.goochou.p2b.model.MigrationInvestmentView;

/**
 * <p>
 * 注释
 * </p>
 *
 * @author shuys
 * @since 2019年10月22日 13:33:00
 */
public interface MigrationInvestmentService {

    MigrationInvestmentMapper getMapper();

    int updateForVersion(MigrationInvestment migrationInvestment) throws Exception;
    
    List<MigrationInvestmentView> listMigrationInvestment(Integer userId, Integer status, Integer orderType, Integer limitStart, Integer limitEnd);
    
    int countMigrationInvestment(Integer userId, Integer status, Integer orderType);
    
}
