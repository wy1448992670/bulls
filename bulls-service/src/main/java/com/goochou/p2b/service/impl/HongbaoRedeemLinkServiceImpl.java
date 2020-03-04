package com.goochou.p2b.service.impl;

import com.goochou.p2b.dao.HongbaoRedeemLinkMapper;
import com.goochou.p2b.model.HongbaoRedeemLink;
import com.goochou.p2b.service.HongbaoRedeemLinkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * HonghaoRedeemLinkServiceImpl
 *
 * @author 刘源
 * @date 2016/6/23
 */
@Service
public class HongbaoRedeemLinkServiceImpl implements HongbaoRedeemLinkService{

    @Resource
    private HongbaoRedeemLinkMapper hongbaoRedeemLinkMapper;


    @Override
    public List<HongbaoRedeemLink> queryById(Integer redeemId) {

        return hongbaoRedeemLinkMapper.queryById(redeemId);
    }
}
