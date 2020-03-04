 package com.goochou.p2b.service;

import com.goochou.p2b.dao.RechargeOfflineApplyMapper;
import com.goochou.p2b.model.RechargeOfflineApply;
import com.goochou.p2b.model.UserAdmin;

import java.text.ParseException;
import java.util.Map;

 /**
 * @author sxy
 * @date 2019/11/22
 */
public interface RechargeOfflineApplyService {

    RechargeOfflineApplyMapper getMapper();

    Map<String, Object> getById(Integer id) throws ParseException;

    void audit(Integer id, Integer state, String auditRemark, final String orderNo, UserAdmin userAdmin) throws Exception;
    
    void save(RechargeOfflineApply rechargeOfflineApply);

    void updateApply(Integer id, String bankcardNum, String serialNumber, Integer uploadId, UserAdmin userAdmin) throws Exception;

     void deleteApply(Integer id) throws Exception;
}
