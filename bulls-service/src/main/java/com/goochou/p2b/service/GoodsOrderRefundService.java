package com.goochou.p2b.service;

import com.goochou.p2b.dao.GoodsOrderRefundMapper;
import com.goochou.p2b.model.goods.GoodsOrderRefund;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author shuys
 * @since 2019/6/4 16:14
 */
public interface GoodsOrderRefundService {

    GoodsOrderRefundMapper getMapper();

    /**
     * @description 申请退款
     * @author shuys
     * @date 2019/6/6
     * @param userId
     * @param orderNo
     * @param reason
     * @return int
    */
    int insertApplyRefund(Integer userId, String orderNo, String reason) throws Exception;

    /**
     * @description 修改申请
     * @author shuys
     * @date 2019/6/6
     * @param userId
     * @param orderNo
     * @param reason
     * @return int
    */
    int updateApplyRefund(Integer userId, String orderNo, String reason) throws Exception;

    /**
     * @description 审核申请
     * @author shuys
     * @date 2019/6/4
     * @param id
     * @param status
     * @param remark
     * @return int
    */
    int updateAuditRefund(Integer id, Integer status, String remark, Integer auditUserId);

    /**
     * @description 审核申请 不通过
     * @author shuys
     * @date 2019/6/25
     * @param id
     * @param auditRemark
     * @param auditUserId
     * @param orderId
     * @return int
    */
    int updateGoodsOrderRefundNoPass(Integer id, String auditRemark, Integer auditUserId, Integer orderId) throws Exception;

    /**
     * @description 审核申请 通过
     * @author shuys
     * @date 2019/6/4
     * @param id
     * @param remark
     * @return int
    */
    int updateGoodsOrderRefundPass(Integer id, String remark, Integer auditUserId) throws Exception;

    /**
     * @description 功能描述
     * @author shuys
     * @date 2019/6/5
     * @param userId
     * @param orderId
     * @return com.goochou.p2b.model.goods.GoodsOrderRefund
    */
    GoodsOrderRefund queryGoodsOrderRefund(Integer userId, Integer orderId);

    /**
     * 退款审核列表
     * @author sxy
     * @param trueName
     * @param keyword
     * @param auditStatus
     * @param startTime
     * @param endTime
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<Map<String, Object>> listGoodsOrderRefund(String trueName, String keyword, Integer auditStatus,
        Date startTime, Date endTime, Date auditStartTime, Date auditEndTime, Integer limitStart, Integer limitEnd,
        Integer adminId, Integer departmentId, String payChannel);

    /**
     * 退款申请数量
     * @author sxy
     * @param trueName
     * @param keyword
     * @param auditStatus
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countGoodsOrderRefund(String trueName, String keyword, Integer auditStatus,
        Date startTime, Date endTime, Date auditStartTime, Date auditEndTime, Integer adminId, Integer departmentId, String payChannel);

}
