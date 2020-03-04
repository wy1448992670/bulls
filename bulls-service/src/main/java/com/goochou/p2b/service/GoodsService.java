package com.goochou.p2b.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.goochou.p2b.dao.GoodsMapper;
import com.goochou.p2b.model.goods.Goods;
import com.goochou.p2b.model.goods.GoodsProperty;
import com.goochou.p2b.model.vo.SecondKillActivityView;

/**
 * @Auther: huangsj
 * @Date: 2019/5/9 09:26
 * @Description:
 */
public interface GoodsService {

	GoodsMapper getMapper();

	/**
	 * 商品列表查询
	 *
	 * @param limitStart
	 * @param limitEnd
	 * @return
	 * @author: zj
	 */
	List<Goods> listGoods(Integer limitStart, Integer limitEnd, String keyword, String skuCode);

	/**
	 * 统计数量
	 *
	 * @return
	 * @author: zj
	 */
	int countGoods(String keyword, String skuCode);

	/**
	 * 新增商品
	 *
	 * @param goods
	 * @author: zj
	 */
	void saveGoods(Goods goods);

	/**
	 * 查询出商品
	 *
	 * @param status
	 * @param start
	 * @param limit
	 * @return
	 * @author: zj
	 */
	List<Goods> selectGoodsList(Integer status, Integer start, Integer limit);

	/**
	 * 获取商品属性
	 *
	 * @param goodsId
	 * @return
	 * @author: zj
	 */
	List<GoodsProperty> listGoodsProperty(int goodsId);

	/**
	 * 新增商品同时更新图片
	 * @param good
	 * @param pictures
	 * @param productPropertyIdArray
	 * @param propertyValueArray
	 * @throws Exception
	 * @author: zj
	 */
	void saveWithPicture(Goods good, List<String> pictures, String[] productPropertyIdArray,
			String[] propertyValueArray) throws Exception;

	/**
	 * 根据主键得到商品
	 * @param id
	 * @return
	 * @author: zj
	 */
	Goods getGood(Integer id);

	/**
	 * 根据商品主键查询下面所有的属性，分类
	 *
	 * @这里用一句话描述这个方法的作用
	 * @param id
	 * @return
	 * @author: zj
	 */
	List<Map<String, Object>> listGoodsProperties(Integer id);

	/**
	 * 更新商品信息
	 * @param goods
	 * @param picture
	 * @param picture2
	 * @param productPropertyIdArray
	 * @param propertyValueArray
	 * @throws Exception
	 * @author: zj
	 */
	void update(Goods goods, String picture, String picture2, String[] productPropertyIdArray,
			String[] propertyValueArray) throws Exception;


	List<Map<String, Object>> getAppGoodsList(Map<String, Object> map);

	int getAppGoodsCount(Map<String, Object> map);

	List<Goods> getGoodsByIds(List<Integer> ids);
 
	/**
   	 * @desc 计算邮费
	 * @author wangyun
	 * @param weight
	 * @param amount
	 * @param cityId
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> calculateExpressFee(Double weight, BigDecimal amount, String cityCode) throws Exception;
	
	int updateForVersion(Goods goods) throws Exception;

	/**
	 * @desc 查询是否是爆款商品
	 * @author wangyun
	 * @return
	 * @throws Exception
	 */
	boolean isHotGoods(Integer goodsId) throws Exception;

	/**
	 * @desc 查询是否是秒杀商品
	 * @author wangyun
	 * @return
	 * @throws Exception
	 */
	public List<SecondKillActivityView> activityKillGoodsByGoodsId(Integer goodsId);

	/**
	 * 根据销量排行
	 * @author sxy
	 * @param limitStart
	 * @param limitEnd
	 * @return
	 */
	List<Goods> listGoodsBySales(Integer limitStart, Integer limitEnd);

	/**
	 * 根据浏览量排行
	 * @author sxy
	 * @param limitStart
	 * @param limitEnd
	 * @return
	 */
	List<Map<String, Object>> listGoodsByClick(Integer limitStart, Integer limitEnd) throws Exception;

	List<SecondKillActivityView> getAllSecondKillActivity(Date date, Integer status);

	Goods queryGoodsDetailById(Integer goodsId);

	/**
	 * 猜你喜欢
	 * @author sxy
	 * @param userId
	 * @param limitStart
	 * @param limitEnd
	 * @return
	 */
	List<Map<String, Object>> listGoodsForLove(Integer userId, Integer limitStart, Integer limitEnd);

	List<SecondKillActivityView> queryAppIndex2WeeksActivity();
}
