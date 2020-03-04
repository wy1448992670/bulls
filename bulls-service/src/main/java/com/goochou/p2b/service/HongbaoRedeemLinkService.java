package com.goochou.p2b.service;

import com.goochou.p2b.model.HongbaoRedeemLink;

import java.util.List;

/**
 * liuyuan
 * 2016/6/23.
 */
public interface HongbaoRedeemLinkService {

    /**
     * @Description(描述):根据兑换码ID  查询对应红包模版组
     * @author 王信
     * @date 2016/6/30
     * @params
     **/
    public List<HongbaoRedeemLink> queryById(Integer redeemId);
}
