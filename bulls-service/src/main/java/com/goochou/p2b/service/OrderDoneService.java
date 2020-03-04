package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.model.OrderDone;

public interface OrderDoneService {
    public int insert(OrderDone ordeDoner);
    
    public List<OrderDone> queryOrderDoneListByOrderNo(String orderNo);

    /**
     * @return
     */
    public List<OrderDone> queryOrderDoneNeedSendMessage();

    /**
     * @param next
     * @return
     */
    public boolean doSend(OrderDone next);
}
