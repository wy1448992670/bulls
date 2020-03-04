package com.goochou.p2b.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.dao.GoodsPictureMapper;
import com.goochou.p2b.model.goods.Goods;
import com.goochou.p2b.service.memcached.MemcachedManager;
import com.goochou.p2b.utils.BigDecimalUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.GoodsShoppingCartMapper;
import com.goochou.p2b.model.GoodsShoppingCart;
import com.goochou.p2b.model.GoodsShoppingCartExample;
import com.goochou.p2b.model.vo.ShoppingCartPickedVO;
import com.goochou.p2b.model.vo.ShoppingCartVO;
import com.goochou.p2b.service.GoodsService;
import com.goochou.p2b.service.GoodsShoppingCartService;

/**
 * @author shuys
 * @since 2019/5/27 16:45
 */
@Service
public class GoodsShoppingCartServiceImpl implements GoodsShoppingCartService {

	private static final Logger logger = Logger.getLogger(GoodsShoppingCartServiceImpl.class);

	@Resource
	private GoodsShoppingCartMapper goodsShoppingCartMapper;

	@Resource
	private GoodsService goodsService;

	@Resource
	protected MemcachedManager memcachedManager;

	@Resource
	protected GoodsPictureMapper goodsPictureMapper;

	@Override
	public GoodsShoppingCartMapper getMapper() {
		return goodsShoppingCartMapper;

	}

	@Override
	public void saveShoppingCard(GoodsShoppingCart shoppingCart, boolean isAdd) {
		if (!isAdd) { // 修改购物车商品数量
			GoodsShoppingCartExample example = new GoodsShoppingCartExample();
			example.createCriteria()
					.andUserIdEqualTo(shoppingCart.getUserId())
					.andGoodsIdEqualTo(shoppingCart.getGoodsId());
			shoppingCart.setUpdateDate(new Date());
			this.getMapper().updateByExampleSelective(shoppingCart, example);
		} else {
			shoppingCart.setIsSelected(0);
			shoppingCart.setCreateDate(new Date());
			this.getMapper().insert(shoppingCart);
		}
	}

	private List<ShoppingCartVO> queryShoppingCartList(Integer userId, List<Integer> ids, Integer start, Integer end) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		paramMap.put("ids", StringUtils.join(ids, ","));
		paramMap.put("start", start);
		paramMap.put("end", end);
		return this.getMapper().queryShoppingCartList(paramMap);
	}

	// 购物车列表
	@Override
	public List<ShoppingCartVO> queryShoppingCartList(Integer userId) {
		return this.queryShoppingCartList(userId, null, null, null);
	}

	// 结算 商品列表
	@Override
	public List<ShoppingCartVO> queryShoppingCartList(Integer userId, List<Integer> ids) {
		return this.queryShoppingCartList(userId, ids, null, null);
	}

	@Override
	public int queryShoppingCartCount(Integer userId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		return this.getMapper().queryShoppingCartCount(paramMap);
	}

	@Override
	public void delShoppingCartGoods(Integer userId,Integer[] goodsId) {
		this.getMapper().delShoppingCartGoods(userId,StringUtils.join(goodsId, ","));
	}

	@Override
	public int getShoppingCartGoodsCount(Integer userId, Integer goodsId) {
		GoodsShoppingCart goodsShoppingCart = this.getByUserIdAndGoodsId(userId, goodsId);
		if (goodsShoppingCart != null) {
			return goodsShoppingCart.getGoodsCount();
		}
		return 0;
	}

	@Override
	public GoodsShoppingCart getByUserIdAndGoodsId(Integer userId, Integer goodsId) {
		GoodsShoppingCartExample example = new GoodsShoppingCartExample();
		example.setOrderByClause("update_date desc");
		example.createCriteria().andUserIdEqualTo(userId).andGoodsIdEqualTo(goodsId);
		List<GoodsShoppingCart> result = this.getMapper().selectByExample(example);
		return result != null && !result.isEmpty() ? result.get(0) : null;
	}

	// 计算购物车选中商品金额
	public Map<String, Object> calculatedAmount() {
		Map<String, Object> result = new HashMap<>();

		return result;
	}

	public int updateByUserIdAndGoodsId(Integer userId, Integer goodsId, GoodsShoppingCart shoppingCart) {
		GoodsShoppingCartExample example = new GoodsShoppingCartExample();
		example.createCriteria().andUserIdEqualTo(userId).andGoodsIdEqualTo(goodsId);
		shoppingCart.setUpdateDate(new Date());
		return this.getMapper().updateByExampleSelective(shoppingCart, example);
	}

	@Override
	public Integer getShoppingCartCount(Integer userId) {
		return goodsShoppingCartMapper.getShoppingCartCount(userId);
	}


	/////////////////////////////////////////////////////////////////////////////////////////////
	// 操作缓存

	public String getShoppingCartCacheKey(Integer userId) {
		return (Constants.SHOPPING_CART_LIST).replace("{userId}", userId.toString());
	}

	public List<ShoppingCartVO> getCacheShoppingCartListByUserId(Integer userId) {
		String cacheKey = this.getShoppingCartCacheKey(userId);
		List<ShoppingCartVO> list= new ArrayList<>();
        try {
            if(memcachedManager.get(cacheKey) != null) {
                list = (List<ShoppingCartVO>) memcachedManager.get(cacheKey);
            }
			logger.info("获取购物车缓存列表：" + list.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
		return list;
	}

	public void updateCacheShoppingCartListByUserId(Integer userId, List<ShoppingCartVO> sourceData) {
        try {
            String cacheKey = this.getShoppingCartCacheKey(userId);
			memcachedManager.addOrReplace(cacheKey, sourceData, Constants.SHOPPING_CART_EXPIRE_IN_SECONDS);
//			memcachedManager.addOrReplace(cacheKey, sourceData);
			logger.info("修改购物车缓存：" + sourceData.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public ShoppingCartVO getCacheShoppingCartByUserAndGoods(Integer userId, Integer goodsId) {
		logger.info("获取购物车缓存商品信息（调用获取列表缓存）");
		List<ShoppingCartVO> resourcesList = this.getCacheShoppingCartListByUserId(userId);
		if (resourcesList.isEmpty() || userId == null || goodsId == null) {
			return null;
		}
		ShoppingCartVO result = null;
		for (ShoppingCartVO vo : resourcesList) {
			if (userId.equals(vo.getUserId()) && goodsId.equals(vo.getGoodsId())){
				result = vo;
				break;
			}
		}
		return result;
	}
	public ShoppingCartVO getCacheShoppingCartByUserAndGoods(Integer userId, Integer goodsId, List<ShoppingCartVO> resourcesList) {
		logger.info("获取购物车缓存商品信息");
		if (resourcesList.isEmpty() || userId == null || goodsId == null) {
			return null;
		}
		ShoppingCartVO result = null;
		for (ShoppingCartVO vo : resourcesList) {
			if (userId.equals(vo.getUserId()) && goodsId.equals(vo.getGoodsId())){
				result = vo;
				break;
			}
		}
		return result;
	}

	public void deleteCacheShoppingCartByUserAndGoods(Integer userId, List<Integer> goodsIds) throws Exception {
        try {
            List<ShoppingCartVO> list = this.getCacheShoppingCartListByUserId(userId);
            if (list.isEmpty() || userId == null || (goodsIds == null || goodsIds.isEmpty())) {
                return;
            }
            boolean flag; // 为false时表示删除商品不存在
            for (int i = 0, size1 = goodsIds.size(); i < size1; i ++) {
                Integer goodsId = goodsIds.get(i);
                if (goodsId == null) continue;
                flag = false;
                for (int j = 0, size2 = list.size(); j < size2; j ++) {
                    ShoppingCartVO vo = list.get(j);
                    if (goodsId.equals(vo.getGoodsId())){
                        list.remove(j);
                        flag = true;
                        break;
                    }
                }
//			if (!flag) {
//				throw new Exception("删除商品在购物车中不存在");
//			}
            }

            String cacheKey = this.getShoppingCartCacheKey(userId);
            memcachedManager.replace(cacheKey, list, Constants.SHOPPING_CART_EXPIRE_IN_SECONDS); // 更新缓存
//			memcachedManager.replace(cacheKey, list); // 更新缓存
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public void deleteCacheShoppingCartByUserAndGoods(Integer userId, Integer goodsId) throws Exception {
		List<Integer> goodsIds = new ArrayList<>(1);
		goodsIds.add(goodsId);
		this.deleteCacheShoppingCartByUserAndGoods(userId, goodsIds);
	}


	// 更新/同步商品信息
	public void updateLocalShoppingCartByGoods(ShoppingCartVO shoppingCart, Goods goods, boolean vip) throws Exception {
		if (shoppingCart == null || goods == null) {
			return;
		}
		Integer goodsId = goods.getId();
		if (shoppingCart.getGoodsId() != null && !goodsId.equals(shoppingCart.getGoodsId())) {
		    throw new Exception("操作异常，请重试");
        }
		shoppingCart.setGoodsId(goods.getId());
        shoppingCart.setGoodsName(goods.getGoodsName());
        shoppingCart.setGoodsNo(goods.getGoodsNo());
        shoppingCart.setSalingPrice(vip ? goods.getMemberSalingPrice() : goods.getSalingPrice());
        shoppingCart.setMemberSalingPrice(goods.getMemberSalingPrice());
        shoppingCart.setStock(goods.getStock());
        shoppingCart.setStockUnit(goods.getStockUnit());
        String path = goodsPictureMapper.getGoodsPicturePathByGoodsId(goodsId);
        shoppingCart.setPath(path);
	}

	public int getCacheShoppingCartCount(Integer userId) {
		List<ShoppingCartVO> list = this.getCacheShoppingCartListByUserId(userId);
		return list.size();
	}

	public int getCacheShoppingCartGoodsCount(Integer userId, Integer goodsId) {
		ShoppingCartVO shoppingCartVO = this.getCacheShoppingCartByUserAndGoods(userId, goodsId);
		if (shoppingCartVO != null) {
			return shoppingCartVO.getGoodsCount();
		}
		return 0;
	}

	public ShoppingCartPickedVO getShoppingCartPickedInfo(boolean isVIP, Integer userId) throws Exception {
		logger.info("计算购物车中选中商品信息");
        int selectedGoodsCount = 0; // 选中商品数量
        BigDecimal totalAmout = BigDecimal.ZERO; // 选 中商品总金额

	    List<ShoppingCartVO> list = this.getCacheShoppingCartListByUserId(userId);
        List<Integer> goodsIdList = this.getGoodsIdList(list);
        List<Goods> goodsList = goodsService.getGoodsByIds(goodsIdList);
        if (list.size() != goodsList.size()) {
            throw new Exception("购物车中缓存商品数量与实际商品数量不一致");
        }
	    if(!list.isEmpty()) {
            for (ShoppingCartVO vo: list) {
                if (vo.getIsSelected() != 1) continue; // 非选中状态返回
                Goods goods = findLocalGoodsById(goodsList, vo.getGoodsId());
                BigDecimal buyPrice = isVIP ? goods.getMemberSalingPrice() : goods.getSalingPrice();
                int goodsCount = vo.getGoodsCount();
                // 计算商品总金额
                totalAmout = totalAmout.add(buyPrice.multiply(BigDecimalUtil.parse(goodsCount)));

                selectedGoodsCount += 1;
            }
        }

	    ShoppingCartPickedVO shoppingCartPickedVO = new ShoppingCartPickedVO();
	    shoppingCartPickedVO.setPickedGoodsCount(selectedGoodsCount);
	    shoppingCartPickedVO.setTotalAmout(totalAmout);

	    return shoppingCartPickedVO;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////

	public List<Integer> getGoodsIdList(List<ShoppingCartVO> shoppingCartVOS) {
	    List<Integer> goodsIdList = new ArrayList<>();
        if (shoppingCartVOS != null && !shoppingCartVOS.isEmpty()) {
            for (ShoppingCartVO vo : shoppingCartVOS) {
                goodsIdList.add(vo.getGoodsId());
            }
        }
        return goodsIdList;
	}

	public Goods findLocalGoodsById(List<Goods> goodsList, Integer goodsId) throws Exception {
	    if(goodsList == null || goodsList.isEmpty() || goodsId == null) {
            throw new Exception("商品不存在");
        }
        Goods goods = null;
        for (Goods goodsTemp: goodsList) {
            if (goodsTemp.getId().equals(goodsId)) {
                goods = goodsTemp;
                break;
            }
        }
        return goods;
	}

}
