package com.goochou.p2b.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.goochou.p2b.dao.GoodsShoppingCartMapper;
import com.goochou.p2b.model.GoodsShoppingCart;
import com.goochou.p2b.model.goods.Goods;
import com.goochou.p2b.model.vo.ShoppingCartPickedVO;
import com.goochou.p2b.model.vo.ShoppingCartVO;

/**
 * @author shuys
 * @since 2019/5/27 16:44
 */
public interface GoodsShoppingCartService {

    /**
     * @description 功能描述
     * @author shuys
     * @date 2019/5/27
     * @param
     * @return com.goochou.p2b.dao.GoodsShoppingCartMapper
    */
    GoodsShoppingCartMapper getMapper();

    /**
     * @description 添加商品到购物车
     * @author shuys
     * @date 2019/5/27
     * @param shoppingCart
     * @return void
    */
    void saveShoppingCard(GoodsShoppingCart shoppingCart, boolean isAdd);

    /**
     * @description 购物车列表
     * @author shuys
     * @date 2019/5/28
     * @param userId
     * @return java.util.List<com.goochou.p2b.model.vo.ShoppingCartVO>
    */
    List<ShoppingCartVO> queryShoppingCartList(Integer userId);

    /**
     * @description 结算 商品列表
     * @author shuys
     * @date 2019/5/28
     * @param userId
     * @param ids
     * @return java.util.List<com.goochou.p2b.model.vo.ShoppingCartVO>
     */
    List<ShoppingCartVO> queryShoppingCartList(Integer userId, List<Integer> ids);

    /**
     * @description 购物车记录数
     * @author shuys
     * @date 2019/5/28
     * @param userId
     * @return int
    */
    int queryShoppingCartCount(Integer userId);

    /**
     * 删除购物车里的商品
     * @Title: delShoppingCartGoods
     * @param userId  用户主键
     * @param goodsId 商品主键
     * @author zj
     * @date 2019-05-30 18:05
     */
    void delShoppingCartGoods(Integer userId,Integer[] goodsId);


    /**
     * @description 获取购物车商品数量
     * @author shuys
     * @date 2019/5/28
     * @param userId
     * @param goodsId
     * @return java.lang.Integer
    */
    int getShoppingCartGoodsCount(Integer userId, Integer goodsId);

    /**
     * @description 功能描述
     * @author shuys
     * @date 2019/5/28
     * @param userId
     * @param goodsId
     * @return com.goochou.p2b.model.GoodsShoppingCart
    */
    GoodsShoppingCart getByUserIdAndGoodsId(Integer userId, Integer goodsId);

    /**
     * @description 修改购物车商品信息
     * @author shuys
     * @date 2019/6/5
     * @param userId
     * @param goodsId
     * @param shoppingCart
     * @return int
    */
    int updateByUserIdAndGoodsId(Integer userId, Integer goodsId, GoodsShoppingCart shoppingCart);

    /**
     * @param userId
     * @return
     */
    Integer getShoppingCartCount(Integer userId);

    /////////////////////////////////////////////////////////////////////////////////////////////
    // 操作缓存

    /**
     * @description 用户购物车缓存KEY
     * @author shuys
     * @date 2019/7/19
     * @param userId
     * @return java.lang.String
    */
    String getShoppingCartCacheKey(Integer userId);

    /**
     * @description 根据用户id获取购物车缓存数据
     * @author shuys
     * @date 2019/7/19
     * @param userId
     * @return java.util.List<com.goochou.p2b.model.vo.ShoppingCartVO>
    */
    List<ShoppingCartVO> getCacheShoppingCartListByUserId(Integer userId);

    /**
     * @description 根据用户id更新购物车缓存数据
     * @author shuys
     * @date 2019/7/19
     * @param userId
     * @param sourceData
     * @return void
    */
    void updateCacheShoppingCartListByUserId(Integer userId, List<ShoppingCartVO> sourceData);

    /**
     * @description 根据用户id和商品id获取购物车缓存数据
     * @author shuys
     * @date 2019/7/19
     * @param userId
     * @param goodsId
     * @return com.goochou.p2b.model.vo.ShoppingCartVO
    */
    ShoppingCartVO getCacheShoppingCartByUserAndGoods(Integer userId, Integer goodsId);

    /**
     * @description 根据用户id和商品id获取购物车缓存数据
     * @author shuys
     * @date 2019/7/25
     * @param userId
     * @param goodsId
     * @param resourcesList 列表缓存
     * @return com.goochou.p2b.model.vo.ShoppingCartVO
     */
    ShoppingCartVO getCacheShoppingCartByUserAndGoods(Integer userId, Integer goodsId, List<ShoppingCartVO> resourcesList);

    /**
     * @description 批量删除用户购物车商品
     * @author shuys
     * @date 2019/7/19
     * @param userId
     * @param goodsIds
     * @return void
    */
    void deleteCacheShoppingCartByUserAndGoods(Integer userId, List<Integer> goodsIds) throws Exception;

    /**
     * @description 删除购物车中的商品
     * @author shuys
     * @date 2019/7/19
     * @return
    */
    void deleteCacheShoppingCartByUserAndGoods(Integer userId, Integer goodsId) throws Exception;

    /**
     * @description 将商品信息同步到购物车中的商品信息
     * @author shuys
     * @date 2019/7/19
     * @param shoppingCart
     * @param goods
     * @return void
    */
    void updateLocalShoppingCartByGoods(ShoppingCartVO shoppingCart, Goods goods, boolean vip) throws Exception;

    /**
     * @description 获取商品种类数量
     * @author shuys
     * @date 2019/7/22
     * @param userId
     * @return int
    */
    int getCacheShoppingCartCount(Integer userId);

    /**
     * @description 获取购物车中单个商品数量
     * @author shuys
     * @date 2019/7/22
     * @param userId
     * @param goodsId
     * @return int
    */
    int getCacheShoppingCartGoodsCount(Integer userId, Integer goodsId);

    /////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 获取购物车选中商品信息
     * @author sxy
     * @param isVIP
     * @param userId
     * @return
     */
    ShoppingCartPickedVO getShoppingCartPickedInfo(boolean isVIP, Integer userId) throws Exception;

    /**
     * 获取购物车商品id集合
     * @author sys
     * @param shoppingCartVOS
     * @return
     */
    List<Integer> getGoodsIdList(List<ShoppingCartVO> shoppingCartVOS);

    /**
     * 根据商品id获取本地商品信息
     * @author sys
     * @param goodsList
     * @param goodsId
     * @return
     * @throws Exception
     */
    Goods findLocalGoodsById(List<Goods> goodsList, Integer goodsId) throws Exception;

}
