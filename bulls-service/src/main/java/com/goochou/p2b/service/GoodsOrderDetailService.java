package com.goochou.p2b.service;

import com.goochou.p2b.dao.GoodsOrderDetailMapper;
import com.goochou.p2b.model.goods.GoodsOrderDetail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: huangsj
 * @Date: 2019/5/27 15:20
 * @Description:
 */
public interface GoodsOrderDetailService {


    GoodsOrderDetailMapper getMapper();


    /**
     * 构造订单详情数据
     * @param goodsId
     * @param count
     * @param salePrice
     * @param buyPrice
     * @return
     */
    GoodsOrderDetail initDetailNoSave(Integer goodsId, Integer count, BigDecimal salePrice, BigDecimal buyPrice);

    /**
     * 通过订单编号查找订单详情
     * @param orderId
     * @return
     */
    List<GoodsOrderDetail> getDetailsByOrderId(Integer orderId);

    /**
     * 商城订单明细列表
     * @author sxy
     * @param trueName
     * @param keyword
     * @param orderStatus
     * @param startTime
     * @param endTime
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<Map<String, Object>> listGoodsOrderDetail(String trueName, String keyword, Integer orderStatus, Integer payStatus, String goodsCategory,
        Date startTime, Date endTime, Integer limitStart, Integer limitEnd, Integer adminId, Integer departmentId);
    
    /**
     * 商城订单明细数量
     * @author sxy
     * @param trueName
     * @param keyword
     * @param orderStatus
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countGoodsOrderDetail(String trueName, String keyword, Integer orderStatus, Integer payStatus, String goodsCategory,
        Date startTime, Date endTime, Integer adminId, Integer departmentId);
}
