package com.goochou.p2b.service.impl;

import com.goochou.p2b.dao.GoodsOrderDetailMapper;
import com.goochou.p2b.model.goods.GoodsOrder;
import com.goochou.p2b.model.goods.GoodsOrderDetail;
import com.goochou.p2b.model.goods.GoodsOrderDetailExample;
import com.goochou.p2b.model.goods.GoodsOrderExample;
import com.goochou.p2b.service.GoodsOrderDetailService;
import com.goochou.p2b.utils.BigDecimalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: huangsj
 * @Date: 2019/5/27 15:20
 * @Description:
 */
@Service
public class GoodsOrderDetailServiceImpl implements GoodsOrderDetailService {


    @Autowired
    private GoodsOrderDetailMapper goodsOrderDetailMapper;


    @Override
    public GoodsOrderDetailMapper getMapper() {
        return goodsOrderDetailMapper;
    }


    @Override
    public List<GoodsOrderDetail> getDetailsByOrderId(Integer orderId) {

        GoodsOrderDetailExample example = new GoodsOrderDetailExample();
        GoodsOrderDetailExample.Criteria c = example.createCriteria();
        c.andOrderIdEqualTo(orderId);

        return goodsOrderDetailMapper.selectByExample(example);
    }

    public GoodsOrderDetail initDetailNoSave(Integer goodsId, Integer count, BigDecimal salePrice, BigDecimal buyPrice){

        GoodsOrderDetail detail = new GoodsOrderDetail();
        detail.setGoodsId(goodsId);
        detail.setCount(count);
        detail.setSalePrice(salePrice);
        detail.setCreateDate(new Date());
        detail.setBuyPrice(buyPrice);

        return detail;
    }
    
    
    public List<Map<String, Object>> listGoodsOrderDetail(String trueName, String keyword, Integer orderStatus, Integer payStatus, String goodsCategory,
        Date startTime, Date endTime, Integer limitStart, Integer limitEnd, Integer adminId, Integer departmentId) {
        
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("trueName", trueName);
        map.put("keyword", keyword);
        map.put("orderStatus", orderStatus);
        map.put("payStatus", payStatus);
        map.put("goodsCategory", goodsCategory);
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
        map.put("departmentId", departmentId);
        
        return goodsOrderDetailMapper.listGoodsOrderDetail(map);
    }
    
    public Integer countGoodsOrderDetail(String trueName, String keyword, Integer orderStatus, Integer payStatus, String goodsCategory, Date startTime,
        Date endTime,Integer adminId, Integer departmentId) {
        
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("trueName", trueName);
        map.put("keyword", keyword);
        map.put("orderStatus", orderStatus);
        map.put("payStatus", payStatus);
        map.put("goodsCategory", goodsCategory);
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
        map.put("departmentId", departmentId);
        return goodsOrderDetailMapper.countGoodsOrderDetail(map);
    }
}
