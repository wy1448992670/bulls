package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.dao.MigrationInvestmentBillMapper;
import com.goochou.p2b.model.MigrationInvestmentBill;

/**
 * <p>
 * 注释
 * </p>
 *
 * @author shuys
 * @since 2019年10月22日 13:32:00
 */
public interface MigrationInvestmentBillService {

    MigrationInvestmentBillMapper getMapper();

    int updateForVersion(MigrationInvestmentBill migrationInvestmentBill) throws Exception;

    void doDividend(MigrationInvestmentBill bill);
    
    List<MigrationInvestmentBill> listMigrationInvestmentBill(Integer migrationInvestmentId);
    
}
