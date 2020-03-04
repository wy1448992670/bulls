 package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.dao.GoodsClickMapper;
import com.goochou.p2b.model.goods.GoodsClick;

/**
 * @author sxy
 * @date 2019/12/12
 */
public interface GoodsClickService {

    GoodsClickMapper getMapper();
    
    GoodsClick getByUserId(Integer userId);
    
    GoodsClick getByGoodsId(Integer goodsId);
    
    GoodsClick getByUserAndGoods(Integer userId, Integer goodsId);

    /**
     * @author sxy
     * @param userId
     * @param goodsId
     */
    void addOrUpdateClick(Integer userId, Integer goodsId);
    
}
