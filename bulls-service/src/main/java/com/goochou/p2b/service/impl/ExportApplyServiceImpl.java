package com.goochou.p2b.service.impl;

import com.goochou.p2b.constant.ExportApplyStatusEnum;
import com.goochou.p2b.dao.ExportApplyColumnsMapper;
import com.goochou.p2b.dao.ExportApplyConditionMapper;
import com.goochou.p2b.dao.ExportApplyMapper;
import com.goochou.p2b.model.*;
import com.goochou.p2b.service.ExportApplyService;
import com.goochou.p2b.service.exceptions.LockFailureException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * <p>
 * 注释
 * </p>
 *
 * @author shuys
 * @since 2019年11月12日 18:10:00
 */
@Service
public class ExportApplyServiceImpl implements ExportApplyService {

    private static final Logger logger = Logger.getLogger(ExportApplyServiceImpl.class);
    
    @Resource
    ExportApplyMapper exportApplyMapper;

    @Resource
    ExportApplyColumnsMapper exportApplyColumnsMapper;

    @Resource
    ExportApplyConditionMapper exportApplyConditionMapper;

    @Override
    public ExportApplyMapper getMapper() {
        return exportApplyMapper;
    }


    @Override
    public ExportApplyColumnsMapper getExportApplyColumnsMapper() {
        return exportApplyColumnsMapper;
    }


    @Override
    public ExportApplyConditionMapper getExportApplyConditionMapper() {
        return exportApplyConditionMapper;
    }
    
    @Override
    public int updateForVersion(ExportApply exportApply) throws Exception {
        if (exportApply.getId() == null) {
            throw new Exception("id不能为空");
        }
        if (exportApply.getVersion() == null) {
            throw new Exception("版本号不能为空");
        }
        ExportApplyExample example = new ExportApplyExample();
        example.createCriteria()
                .andIdEqualTo(exportApply.getId())
                .andVersionEqualTo(exportApply.getVersion());
        exportApply.setVersion(exportApply.getVersion() + 1);
        if(1 != exportApplyMapper.updateByExampleSelective(exportApply, example)) {
            throw new LockFailureException();
        }
        return 1;
    }

    @Override
    public List<Map<String, Object>> listExportApply(String trueName, Integer auditStatus, Date startTime, Date endTime,
        Integer limitStart, Integer limitEnd, Integer adminId) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("trueName", trueName);
        map.put("auditStatus", auditStatus);
        map.put("startTime", startTime);
        if (endTime != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(endTime);
            c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
            map.put("endTime", c1.getTime());
        } else {
            map.put("endTime", null);
        }
        map.put("limitStart", limitStart);
        map.put("limitEnd", limitEnd);
        map.put("adminId", adminId);
        
        return exportApplyMapper.listExportApply(map);
    }

    @Override
    public int countExportApply(String trueName, Integer auditStatus, Date startTime, Date endTime, Integer adminId) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("trueName", trueName);
        map.put("auditStatus", auditStatus);
        map.put("startTime", startTime);
        if (endTime != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(endTime);
            c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
            map.put("endTime", c1.getTime());
        } else {
            map.put("endTime", null);
        }
        map.put("adminId", adminId);
        
        return exportApplyMapper.countExportApply(map);
    }

    @Override
    public int auditExportApply(Integer id, String auditRemark, Integer auditStatus, Integer auditUserId) throws Exception {
        ExportApply apply = this.getMapper().selectByPrimaryKey(id);
        if(apply == null) {
            throw new Exception("导出申请不存在");
        }
        if(apply.getApplyStatus() != ExportApplyStatusEnum.IN_AUDIT.getCode()) {
            throw new Exception("导出申请不在审核中");
        }
        apply.setAuditRemark(auditRemark);
        apply.setApplyStatus(auditStatus);
        apply.setAuditUserId(auditUserId);
        apply.setAuditTime(new Date());
        // 计算过期时间, 30分钟后过期
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + 30);
        apply.setExpireTime(c.getTime());
        
        return this.updateForVersion(apply);
    }

    public int addExportApply(Integer applyUserId, String reason, Integer targetList,
                              List<ExportApplyCondition> exportApplyConditions,
                              List<ExportApplyColumns> exportApplyColumns) {
        Date now = new Date();
        ExportApply exportApply = new ExportApply();
        exportApply.setApplyUserId(applyUserId);
        exportApply.setApplyReason(reason);
        exportApply.setApplyTime(now);
        exportApply.setTargetList(targetList);
//        // 计算过期时间, 30分钟后过期
//        Calendar c = Calendar.getInstance();
//        c.setTime(now);
//        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + 30);
//        exportApply.setExpireTime(c.getTime());
        if (exportApplyMapper.insertSelective(exportApply) == 1) {
            if (exportApplyConditions != null && !exportApplyConditions.isEmpty()) {
                for (ExportApplyCondition condition : exportApplyConditions) {
                    condition.setApplyId(exportApply.getId());
                    condition.setCreateTime(now);
                    exportApplyConditionMapper.insertSelective(condition);
                }
            }
            if (exportApplyColumns != null && !exportApplyColumns.isEmpty()) {
                for (ExportApplyColumns column : exportApplyColumns) {
                    column.setApplyId(exportApply.getId());
                    column.setCreateTime(now);
                    exportApplyColumnsMapper.insertSelective(column);
                }
            }
            return 1;
        }
        return 0;
    }

    @Override
    public List<ExportApplyColumns> listApplyColumns(Integer applyId) {
        ExportApplyColumnsExample example = new ExportApplyColumnsExample();
        example.createCriteria().andApplyIdEqualTo(applyId);
        
        return exportApplyColumnsMapper.selectByExample(example);
    }

    @Override
    public List<ExportApplyCondition> listApplyCondition(Integer applyId) {
        ExportApplyConditionExample example = new ExportApplyConditionExample();
        example.createCriteria().andApplyIdEqualTo(applyId);
        
        return exportApplyConditionMapper.selectByExample(example);
    }

    @Override
    public Map formatSearchParams(List<ExportApplyCondition> conditions) {
        Map result = new HashMap();
        if (conditions != null && !conditions.isEmpty()) {
            for (ExportApplyCondition condition : conditions) {
                if (condition.getPropertyCode() != null && condition.getValueCode() != null) {
                    result.put(condition.getPropertyCode(), condition.getValueCode());
                }
            }
        }
        return result;
    }
    
}
