 package com.goochou.p2b.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import com.goochou.p2b.constant.ClientEnum;
import com.goochou.p2b.constant.OrderTypeEnum;
import com.goochou.p2b.constant.RechargeOfflineApplyStateEnum;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.dao.AdminLogMapper;
import com.goochou.p2b.dao.AssetsMapper;
import com.goochou.p2b.model.*;
import com.goochou.p2b.service.RechargeService;
import com.goochou.p2b.service.UploadService;
import com.goochou.p2b.utils.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.RechargeOfflineApplyMapper;
import com.goochou.p2b.model.RechargeOfflineApply;
import com.goochou.p2b.service.RechargeOfflineApplyService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

 /**
 * @author sxy
 * @date 2019/11/22
 */
@Service
public class RechargeOfflineApplyServiceImpl implements RechargeOfflineApplyService {

    private static final Logger logger = Logger.getLogger(RechargeOfflineApplyServiceImpl.class);
    
    @Resource
    private RechargeOfflineApplyMapper rechargeOfflineApplyMapper;
    @Resource
    private RechargeService rechargeService;
    @Resource
    private AssetsMapper assetsMapper;
    @Resource
    private AdminLogMapper adminLogMapper;
    @Resource
    private UploadService uploadService;
    
    @Override
    public RechargeOfflineApplyMapper getMapper() {
        return rechargeOfflineApplyMapper;
    }

    @Override
    public void save(RechargeOfflineApply rechargeOfflineApply) {
        if(rechargeOfflineApply.getId() == null) {
            rechargeOfflineApply.setCreateTime(new Date());
            this.getMapper().insertSelective(rechargeOfflineApply);
        } else {
            this.getMapper().updateByPrimaryKeySelective(rechargeOfflineApply);
        }
    }
    

    @Override
    public Map<String, Object> getById(Integer id) throws ParseException {
        Map<String, Object> result = rechargeOfflineApplyMapper.queryById(id);
        result.put("create_time", DateUtil.dateFullTimeFormat.format(result.get("create_time")));
        Object auditTime = result.get("audit_time");
        if (auditTime != null) {
            result.put("audit_time", DateUtil.dateFullTimeFormat.format(auditTime));
        }
        Object lastUpdateTime = result.get("last_update_time");
        if (lastUpdateTime != null) {
            result.put("last_update_time", DateUtil.dateFullTimeFormat.format(lastUpdateTime));
        }
        RechargeOfflineApplyStateEnum state = RechargeOfflineApplyStateEnum.getByCode((Integer) result.get("state"));
        if (state != null) {
            result.put("stateMsg", state.getDescript());
        }
        return result;
    }

    @Override
    public void audit(Integer id, Integer state, String auditRemark, final String orderNo, UserAdmin userAdmin) throws Exception {
        RechargeOfflineApply rechargeOfflineApply = rechargeOfflineApplyMapper.selectByPrimaryKey(id);
        if (rechargeOfflineApply == null) {
            throw new Exception("线下充值申请单不存在");
        }
        // 上锁
        rechargeOfflineApplyMapper.updateByPrimaryKey(rechargeOfflineApply);
        if (rechargeOfflineApply.getState() != RechargeOfflineApplyStateEnum.APPLING.getCode()) {
            throw new Exception("不能重复审核");
        }
        if (rechargeOfflineApply.getMoney().compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("充值金额不能为负数");
        }
        if (state == null || (state != RechargeOfflineApplyStateEnum.NO_PASS.getCode() && state != RechargeOfflineApplyStateEnum.PASS.getCode())) {
            throw new Exception("参数错误");
        }
        Date now = new Date();
        if (state == RechargeOfflineApplyStateEnum.PASS.getCode()) { // 通过
            rechargeOfflineApply.setState(RechargeOfflineApplyStateEnum.PASS.getCode());
            
            Recharge recharge = new Recharge();
            recharge.setUserId(rechargeOfflineApply.getUserId());

//            final String orderno = rechargeOrderIdGenerator.next(); //生成订单号
            recharge.setOrderNo(orderNo);
            recharge.setOutOrderNo(rechargeOfflineApply.getSerialNumber());
            recharge.setOrderType(OrderTypeEnum.RECHARGE.getFeatureName());
            recharge.setPayChannel(OutPayEnum.OFFLINE.getFeatureName());
            recharge.setClient(ClientEnum.PC.getFeatureName());
            recharge.setAmount(rechargeOfflineApply.getMoney().doubleValue());
            recharge.setCardNo(rechargeOfflineApply.getBankcardNum());
            recharge.setCreateDate(now);
            recharge.setUpdateDate(now);
            recharge.setRemark("线下充值申请单");
            recharge.setStatus(1); // 处理中
            if (rechargeService.getMapper().insertSelective(recharge) > 0) {
                recharge.setStatus(0); // 成功
                Assets assets = assetsMapper.selectByPrimaryKey(rechargeOfflineApply.getUserId()); // 获取用户的资产，取得可用余额
                // 更新用户资金并添加交易记录
                if (rechargeService.updateRecord(recharge, assets, null)) {
                    // 记录管理员操作日志
                    AdminLog adminLog = new AdminLog();
                    adminLog.setAdminId(userAdmin.getId());
                    adminLog.setAdminUserName(userAdmin.getTrueName());
                    adminLog.setOperateTime(now);
                    adminLog.setRemark("线下充值申请单");
                    adminLogMapper.insertSelective(adminLog);
                    // 更新 申请单 关联的 充值单
                    rechargeOfflineApply.setRechargeId(recharge.getId());
                } else {
                    throw new Exception("充值失败");
                }
            } else {
                throw new Exception("充值失败");
            }
        } else { // 不通过
            rechargeOfflineApply.setState(RechargeOfflineApplyStateEnum.NO_PASS.getCode());
        }
        rechargeOfflineApply.setAuditorId(userAdmin.getId());
        rechargeOfflineApply.setAuditTime(now);
        rechargeOfflineApply.setAuditRemark(auditRemark);
        rechargeOfflineApplyMapper.updateByPrimaryKey(rechargeOfflineApply);
    }


     @Override
     public void updateApply(Integer id, String bankcardNum, String serialNumber, Integer uploadId, UserAdmin userAdmin) throws Exception {
         RechargeOfflineApply rechargeOfflineApply = rechargeOfflineApplyMapper.selectByPrimaryKey(id);
         if (rechargeOfflineApply == null) {
             throw new Exception("线下充值申请单不存在");
         }
         // 上锁
         rechargeOfflineApplyMapper.updateByPrimaryKey(rechargeOfflineApply);
         if (rechargeOfflineApply.getState() == RechargeOfflineApplyStateEnum.NO_PASS.getCode()) {
             throw new Exception("不能编辑审核不通过的申请单");
         }
         if (uploadId != null) {
             rechargeOfflineApply.setVoucherPicId(uploadId);
         }
         rechargeOfflineApply.setLastUpdateBy(userAdmin.getId());
         rechargeOfflineApply.setLastUpdateTime(new Date());
         rechargeOfflineApply.setBankcardNum(bankcardNum);
         rechargeOfflineApply.setSerialNumber(serialNumber);
         rechargeOfflineApplyMapper.updateByPrimaryKey(rechargeOfflineApply);
     }


     @Override
     public void deleteApply(Integer id) throws Exception {
         RechargeOfflineApply rechargeOfflineApply = rechargeOfflineApplyMapper.selectByPrimaryKey(id);
         if (rechargeOfflineApply == null) {
             throw new Exception("线下充值申请单不存在");
         }
         // 上锁
         rechargeOfflineApplyMapper.updateByPrimaryKey(rechargeOfflineApply);
         if (rechargeOfflineApply.getState() != RechargeOfflineApplyStateEnum.APPLING.getCode()) {
             throw new Exception("状态不是申请中");
         }
         rechargeOfflineApplyMapper.deleteByPrimaryKey(id);
     }
}
