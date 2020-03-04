package com.goochou.p2b.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.goochou.p2b.dao.ExportApplyColumnsMapper;
import com.goochou.p2b.dao.ExportApplyConditionMapper;
import com.goochou.p2b.dao.ExportApplyMapper;
import com.goochou.p2b.model.ExportApply;
import com.goochou.p2b.model.ExportApplyColumns;
import com.goochou.p2b.model.ExportApplyCondition;

/**
 * <p>
 * 注释
 * </p>
 *
 * @author shuys
 * @since 2019年11月12日 18:10:00
 */
public interface ExportApplyService {

    ExportApplyMapper getMapper();

    ExportApplyColumnsMapper getExportApplyColumnsMapper();

    ExportApplyConditionMapper getExportApplyConditionMapper();

    int updateForVersion(ExportApply exportApply) throws Exception;
    
    /**
     * 导出申请列表
     * @author sxy
     * @param trueName
     * @param auditStatus
     * @param startTime
     * @param endTime
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<Map<String, Object>> listExportApply(String trueName, Integer auditStatus, Date startTime, Date endTime, Integer limitStart, Integer limitEnd, Integer adminId);
    
    /**
     * 导出申请数量
     * @author sxy
     * @param trueName
     * @param auditStatus
     * @param startTime
     * @param endTime
     * @return
     */
    int countExportApply(String trueName, Integer auditStatus, Date startTime, Date endTime, Integer adminId);
    
    /**
     * 审核导出申请
     * @author sxy
     * @param id
     * @param auditRemark
     * @param auditStatus
     * @param auditUserId
     * @return
     * @throws Exception
     */
    int auditExportApply(Integer id, String auditRemark, Integer auditStatus, Integer auditUserId) throws Exception;



    int addExportApply(Integer applyUserId, String reason, Integer targetList, List<ExportApplyCondition> exportApplyConditions, List<ExportApplyColumns> exportApplyColumns);
    
    List<ExportApplyColumns> listApplyColumns(Integer applyId);
    
    List<ExportApplyCondition> listApplyCondition(Integer applyId);

    Map formatSearchParams(List<ExportApplyCondition> conditions);

}
