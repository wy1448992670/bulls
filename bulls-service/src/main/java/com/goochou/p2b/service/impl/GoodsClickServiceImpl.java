 package com.goochou.p2b.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.GoodsClickMapper;
import com.goochou.p2b.model.goods.GoodsClick;
import com.goochou.p2b.model.goods.GoodsClickExample;
import com.goochou.p2b.model.goods.GoodsClickExample.Criteria;
import com.goochou.p2b.service.GoodsClickService;

/**
 * @author sxy
 * @date 2019/12/12
 */
@Service
public class GoodsClickServiceImpl implements GoodsClickService {

    @Resource
    private GoodsClickMapper goodsClickMapper;
    
    @Override
    public GoodsClickMapper getMapper() {
        return goodsClickMapper;
    }

    @Override
    public GoodsClick getByUserId(Integer userId) {
        GoodsClickExample example = new GoodsClickExample();
        example.createCriteria().andUserIdEqualTo(userId);
        
        List<GoodsClick> list = goodsClickMapper.selectByExample(example);
        if(list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public GoodsClick getByGoodsId(Integer goodsId) {
        GoodsClickExample example = new GoodsClickExample();
        example.createCriteria().andGoodsIdEqualTo(goodsId);
        
        List<GoodsClick> list = goodsClickMapper.selectByExample(example);
        if(list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public GoodsClick getByUserAndGoods(Integer userId, Integer goodsId) {
        GoodsClickExample example = new GoodsClickExample();
        Criteria criteria = example.createCriteria();
        if(userId != null) {
            criteria.andUserIdEqualTo(userId);
        } else {
            criteria.andUserIdIsNull();
        }
        if(goodsId != null) {
            criteria.andGoodsIdEqualTo(goodsId);
        }
        
        List<GoodsClick> list = goodsClickMapper.selectByExample(example);
        if(list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void addOrUpdateClick(Integer userId, Integer goodsId) {
        GoodsClick getByUserAndGoods = this.getByUserAndGoods(userId, goodsId);
        if(getByUserAndGoods == null) {
            GoodsClick click = new GoodsClick();
            click.setUserId(userId);
            click.setGoodsId(goodsId);
            click.setClick(1);
            click.setCreateDate(new Date());
            goodsClickMapper.insertSelective(click);
        } else {
            getByUserAndGoods.setClick(getByUserAndGoods.getClick() + 1);
            getByUserAndGoods.setUpdateDate(new Date());
            goodsClickMapper.updateByPrimaryKeySelective(getByUserAndGoods);
        }
    }

}
