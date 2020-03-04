package com.goochou.p2b.service.impl;

import com.goochou.p2b.constant.ApplyRefundStatusEnum;
import com.goochou.p2b.constant.OrderDoneEnum;
import com.goochou.p2b.constant.OrderTypeEnum;
import com.goochou.p2b.constant.goods.GoodsOrderStatusEnum;
import com.goochou.p2b.dao.GoodsOrderRefundMapper;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.OrderDone;
import com.goochou.p2b.model.goods.GoodsOrder;
import com.goochou.p2b.model.goods.GoodsOrderRefund;
import com.goochou.p2b.model.goods.GoodsOrderRefundExample;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.GoodsOrderRefundService;
import com.goochou.p2b.service.GoodsOrderService;
import com.goochou.p2b.service.OrderDoneService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shuys
 * @since 2019/6/4 16:15
 */
@Service
public class GoodsOrderRefundServiceImpl implements GoodsOrderRefundService {

    @Resource
    private GoodsOrderRefundMapper refundMapper;

    @Resource
    private GoodsOrderService goodsOrderService;

    @Resource
    private AssetsService assetsService;

    @Resource
    private OrderDoneService orderDoneService;

    @Override
    public GoodsOrderRefundMapper getMapper() {
        return refundMapper;
    }

    @Override
    public int insertApplyRefund(Integer userId, String orderNo, String reason) throws Exception {
        GoodsOrder goodsOrder = goodsOrderService.queryGoodsOrderByNumber(orderNo);
        if (goodsOrder == null) {
            throw new Exception("订单不存在");
        }
        // 对订单上锁
        goodsOrder=goodsOrderService.getMapper().selectByPrimaryKeyForUpdate(goodsOrder.getId());
        if (goodsOrder.getState().intValue() != GoodsOrderStatusEnum.PAYED.getCode()) { // 订单为已支付
            throw new Exception("当前订单不能申请退款");
        }

        GoodsOrderRefund goodsOrderRefund = this.queryGoodsOrderRefund(userId, goodsOrder.getId());
        if (goodsOrderRefund != null) {
            throw new Exception("不能重复申请");
        }
        // 更新订单状态为 退款中
        goodsOrderService.updateOrderState(goodsOrder.getId(), GoodsOrderStatusEnum.REFUNDING.getCode());
        // 记录order done
        this.insertOrderDone(goodsOrder.getOrderNo(), OrderDoneEnum.REFUND_APPLY.getFeatureName(), 0);

        GoodsOrderRefund refund = new GoodsOrderRefund();
        refund.setUserId(userId);
        refund.setOrderId(goodsOrder.getId());
        refund.setReason(reason);
        refund.setStatus(ApplyRefundStatusEnum.IN_AUDIT.getCode());
        refund.setCreateDate(new Date());
        return this.getMapper().insertSelective(refund);
    }

    /**
     * @description 记录orderDone
     * @author shuys
     * @date 2019/7/8
     * @param orderNo
     * @param orderStatus
     * @param send 是否发送短信 0 否，1 是
     * @return void
    */
    private void insertOrderDone(String orderNo, String orderStatus, Integer send) {
        OrderDone orderDone = new OrderDone();
        orderDone.setOrderType(OrderTypeEnum.GOODS.getFeatureName());
        orderDone.setOrderNo(orderNo);
        orderDone.setOrderStatus(orderStatus);
        orderDone.setSend(send);
        orderDoneService.insert(orderDone);
    }

    private int updateApplyRefund(Integer userId, Integer orderId, GoodsOrderRefund refund){
        GoodsOrderRefundExample example = new GoodsOrderRefundExample();
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andOrderIdEqualTo(orderId);
        refund.setUpdateDate(new Date());
        return this.getMapper().updateByExampleSelective(refund, example);
    }

    private int updateAuditRefund(GoodsOrderRefund refund){
        refund.setUpdateDate(new Date());
        return this.getMapper().updateByPrimaryKeySelective(refund);
    }

    @Override
    public int updateApplyRefund(Integer userId, String orderNo, String reason) throws Exception{
        GoodsOrder goodsOrder = goodsOrderService.queryGoodsOrderByNumber(orderNo);
        if (goodsOrder == null) {
            throw new Exception("订单不存在");
        }
        // 对订单上锁
        goodsOrder=goodsOrderService.getMapper().selectByPrimaryKeyForUpdate(goodsOrder.getId());
        if (goodsOrder.getState().intValue() != GoodsOrderStatusEnum.PAYED.getCode()) { // 订单为已支付
            throw new Exception("退款申请不在审核中");
        }

        GoodsOrderRefund goodsOrderRefund = this.queryGoodsOrderRefund(userId, goodsOrder.getId());
        if (goodsOrderRefund == null) {
            throw new Exception("申请单不存在");
        }
        // 记录order done TODO 还没有修改退款申请功能
        this.insertOrderDone(goodsOrder.getOrderNo(), OrderDoneEnum.REFUND_APPLY.getFeatureName(), 0);
        GoodsOrderRefund refund = new GoodsOrderRefund();
        refund.setReason(reason);
        return this.updateApplyRefund(userId, goodsOrder.getId(), refund);
    }

    @Override
    public int updateAuditRefund(Integer id, Integer status, String remark, Integer auditUserId) {
        GoodsOrderRefund refund = new GoodsOrderRefund();
        refund.setId(id);
        refund.setStatus(status);
        refund.setAuditRemark(remark);
        refund.setAuditUserId(auditUserId);
        return this.updateAuditRefund(refund);
    }


    public int updateGoodsOrderRefundNoPass(Integer id, String auditRemark, Integer auditUserId, Integer orderId) throws Exception {
        GoodsOrderRefund goodsOrderRefund = this.getMapper().selectByPrimaryKey(id);
        if(goodsOrderRefund==null){
            throw new Exception("退款申请不存在");
        }
        if(goodsOrderRefund.getStatus()!= ApplyRefundStatusEnum.IN_AUDIT.getCode()) {
            throw new Exception("退款申请不在审核中");
        }

        GoodsOrder goodsOrder = goodsOrderService.getMapper().selectByPrimaryKeyForUpdate(orderId); // 订单信息
        if(goodsOrder==null){
            throw new Exception("订单不存在");
        }
        if (goodsOrder.getState()!= GoodsOrderStatusEnum.REFUNDING.getCode()) {
            throw new Exception("订单状态不在退款中");
        }

        goodsOrderService.updateOrderState(goodsOrder.getId(), GoodsOrderStatusEnum.PAYED.getCode()); //修改订单状态
        // 记录order done
        this.insertOrderDone(goodsOrder.getOrderNo(), OrderDoneEnum.REFUND_REJECT.getFeatureName(), 0);

        return this.updateAuditRefund(id, ApplyRefundStatusEnum.NO_PASS.getCode(), auditRemark, auditUserId);
    }


    // 退款申请通过
    @Override
    public int updateGoodsOrderRefundPass(Integer id, String remark, Integer auditUserId) throws Exception {
        GoodsOrderRefund goodsOrderRefund = this.getMapper().selectByPrimaryKey(id);

        if(goodsOrderRefund==null){
        	throw new Exception("退款申请不存在");
        }
        if(goodsOrderRefund.getStatus()!= ApplyRefundStatusEnum.IN_AUDIT.getCode()) {
        	throw new Exception("退款申请不在审核中");
        }
        GoodsOrder goodsOrder = goodsOrderService.getMapper().selectByPrimaryKeyForUpdate(goodsOrderRefund.getOrderId()); // 订单信息

        if(goodsOrder==null){
        	throw new Exception("订单不存在");
        }
        if (goodsOrder.getState()!= GoodsOrderStatusEnum.REFUNDING.getCode()) {
            throw new Exception("订单状态不在退款中");
        }

        Assets assets = assetsService.getMapper().selectByPrimaryKey(goodsOrderRefund.getUserId()); // 获取用户的资产
        if (null == assets) {
            throw new Exception("用户账户异常");
        }
        if (goodsOrderService.doRefundOrder(goodsOrder, assets)) { // 退款
            // 更新申请状态为 通过
            return this.updateAuditRefund(goodsOrderRefund.getId(), ApplyRefundStatusEnum.PASS.getCode(), remark, auditUserId);
        }else {
        	throw new Exception("退款失败,请重试");
        }
    }

    @Override
    public GoodsOrderRefund queryGoodsOrderRefund(Integer userId, Integer orderId) {
        GoodsOrderRefundExample example = new GoodsOrderRefundExample();
        example.setOrderByClause(" create_date desc ");
        List<Integer> statusArr = new ArrayList<>();
        statusArr.add(ApplyRefundStatusEnum.IN_AUDIT.getCode());
        statusArr.add(ApplyRefundStatusEnum.SUBMIT.getCode());
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andOrderIdEqualTo(orderId)
                .andStatusIn(statusArr);
        List<GoodsOrderRefund> list = this.getMapper().selectByExample(example);
        return list != null && !list.isEmpty() ? list.get(0) : null;
    }

    @Override
    public List<Map<String, Object>> listGoodsOrderRefund(String trueName, String keyword, Integer auditStatus,
        Date startTime, Date endTime, Date auditStartTime, Date auditEndTime, Integer limitStart, Integer limitEnd,
        Integer adminId, Integer departmentId, String payChannel) {

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("trueName", trueName);
        map.put("keyword", keyword);
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
        map.put("auditStartTime", auditStartTime);
        if (auditEndTime != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(auditEndTime);
            c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
            map.put("auditEndTime", c1.getTime());
        } else {
            map.put("auditEndTime", null);
        }
        map.put("limitStart", limitStart);
        map.put("limitEnd", limitEnd);
        map.put("adminId", adminId);
        map.put("departmentId", departmentId);
        map.put("payChannel", payChannel);
        
        return refundMapper.listGoodsOrderRefund(map);
    }

    @Override
    public Integer countGoodsOrderRefund(String trueName, String keyword, Integer auditStatus,
                                         Date startTime, Date endTime, Date auditStartTime, Date auditEndTime,
                                         Integer adminId, Integer departmentId, String payChannel) {

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("trueName", trueName);
        map.put("keyword", keyword);
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
        map.put("auditStartTime", auditStartTime);
        if (auditEndTime != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(auditEndTime);
            c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
            map.put("auditEndTime", c1.getTime());
        } else {
            map.put("auditEndTime", null);
        }
        map.put("adminId", adminId);
        map.put("departmentId", departmentId);
        map.put("payChannel", payChannel);

        return refundMapper.countGoodsOrderRefund(map);
    }

}
