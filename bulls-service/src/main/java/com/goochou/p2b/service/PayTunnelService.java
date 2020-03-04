package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.model.PayTunnel;

public interface PayTunnelService {
    public List<PayTunnel> list(Integer start, Integer limit);

    public Integer listCount();

    public void update(PayTunnel tunnel);

    public void save(PayTunnel tunnel);

    public void delete(Integer id);

    public PayTunnel get(Integer id);

    /**
     * @param type 0充值1提现
     * @return
     */
    public List<PayTunnel> listUsable(Integer type);

    /**
     * @description 获取主要支付渠道(充值渠道)
     * @author shuys
     * @date 2019/5/22
     * @param
     * @return com.goochou.p2b.model.PayTunnel
    */
    public PayTunnel getMajorPayTunnel();
}
