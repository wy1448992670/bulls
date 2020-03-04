package com.goochou.p2b.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.goochou.p2b.dao.GoodsOrderMapper;
import com.goochou.p2b.hessian.transaction.goods.GoodsOrderListRequest;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.Hongbao;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.goods.Goods;
import com.goochou.p2b.model.goods.GoodsOrder;
import com.goochou.p2b.model.goods.GoodsOrderDetail;

/**
 * @Auther: huangsj
 * @Date: 2019/5/9 11:29
 * @Description:
 */
public interface GoodsOrderService {

	GoodsOrderMapper getMapper();

	GoodsOrder findByOrderNum(String orderNum);

	/**
	 * 下单
	 *
	 * @param user
	 * @param goodsOrder
	 * @param goodss
	 * @param nums
	 * @param hongbao
	 * @return
	 * @throws Exception
	 */
	boolean addOrder(User user, GoodsOrder goodsOrder, List<Goods> goodss, List<Integer> nums, Hongbao hongbao)
			throws Exception;

	/**
	 * 提交订单
	 *
	 * @param goodsOrder
	 * @param userAccount
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	boolean submitOrder(GoodsOrder goodsOrder, Assets userAccount) throws Exception;
	
	boolean addSubmitOrder(User user, GoodsOrder goodsOrder, List<GoodsOrderDetail> goodsOrderDetails, Hongbao hongbao, Assets userAccount)
			throws Exception;
	/**
	 * 订单支付成功处理
	 *
	 * @param goodsOrder
	 * @param userAccount
	 * @return
	 * @throws Exception
	 */
	boolean doPaySuccess(GoodsOrder goodsOrder, Assets userAccount) throws Exception;

	/**
	 * 取消订单
	 *
	 * @param goodsOrder
	 */
	boolean cancelOrder(GoodsOrder goodsOrder, Assets assets) throws Exception;

	/**
	 * 订单退款
	 *
	 * @param goodsOrder
	 */
	boolean doRefundOrder(GoodsOrder goodsOrder, Assets assets) throws Exception;

	List<GoodsOrder> findByIds(List<Integer> ids);

	/**
	 * 获取商品订单列表
	 *
	 * @param start
	 * @param limit
	 * @return
	 * @author sxy
	 */
	List<Map<String, Object>> listGoodsOrder(String trueName, Integer goodsId, String keyword, List<Integer> status,
			Date startTime, Date endTime, Integer limitStart, Integer limitEnd, Integer userId, Integer expressWay,
			Date payStartTime,Date payEndTime, Date refundFinishStartTime, Date refundFinishEndTime, Integer adminId,
			Integer departmentId, String payChannel);

	/**
	 * 统计订单数量
	 *
	 * @return
	 * @author sxy
	 */
	Integer countOrder(String trueName, Integer goodsId, String keyword, List<Integer> status, Date startTime,
			Date endTime, Integer userId, Integer expressWay,
			Date payStartTime,Date payEndTime, Date refundFinishStartTime, Date refundFinishEndTime, Integer adminId,
			Integer departmentId, String payChannel);

	/**
	 * @param request
	 * @return
	 * @date 2019年5月20日
	 * @author wangyun
	 * @time 下午5:40:28
	 * @Description
	 */
	List<GoodsOrder> listGoodsOrder(GoodsOrderListRequest request);

	/**
	 * @param request
	 * @return
	 * @date 2019年5月20日
	 * @author wangyun
	 * @time 下午5:40:28
	 * @Description
	 */
	Integer countOrder(GoodsOrderListRequest request);

	Double getAmountCount(Integer adminId,Integer departmentId);

	GoodsOrder queryGoodsOrderDetail(String orderNo);

	/**
	 * @param otherId
	 * @param goodsOrder
	 * @param i
	 * @return
	 */
	int update(GoodsOrder goodsOrder, int needStatus);

	/**
	 * 获取商品购买记录
	 *
	 * @Title: listBuyGoodsRecord
	 * @param goodsId
	 * @param limitStart
	 * @param limitEnd
	 * @return List<Map<String,Object>>
	 * @author zj
	 * @date 2019-05-31 10:09
	 */
	List<Map<String, Object>> listBuyGoodsRecord(Integer goodsId, Integer limitStart, Integer limitEnd);

	/**
	 * 统计商品购买记录
	 *
	 * @Title: countBuyGoodsRecord
	 * @param goodsId
	 * @param limitStart
	 * @param limitEnd
	 * @return Integer
	 * @author zj
	 * @date 2019-05-31 10:09
	 */
	Integer countBuyGoodsRecord(Integer goodsId, Integer limitStart, Integer limitEnd);

	/**
	 * @description 功能描述
	 * @author shuys
	 * @date 2019/6/4
	 * @param orderNo
	 * @return com.goochou.p2b.model.goods.GoodsOrder
	*/
	GoodsOrder queryGoodsOrderByNumber(String orderNo);

	/**
	 * @description 修改订单收货地址
	 * @author shuys
	 * @date 2019/6/3
	 * @param id
	 * @param addressDetail
	 * @return int
	*/
	int updateAddressDetail(Integer id, String addressDetail) ;

	/**
	 * @description 修改订单状态
	 * @author shuys
	 * @date 2019/6/4
	 * @param id
	 * @param state
	 * @return int
	*/
	int updateOrderState(Integer id, Integer state);

	/**
	 * @description 修改订单为隐藏状态(删除订单)
	 * @author shuys
	 * @date 2019/6/4
	 * @param id
	 * @return int
	*/
	int updateIsHiddenById(Integer id);
	
    /**
	 * 获得过期订单
	 * 2019-05-24 张琼麒
	 * @return
	 * @throws Exception
	 */
	public List<GoodsOrder> getExceedTimeLimitOrder() throws Exception;

	/**
	 * 获取商品评价
	* @Title: listGoodsAssessPage 
	* @param goodsId
	* @param limitStart
	* @param limitEnd
	* @return List<Map<String,Object>>
	* @author zj
	* @date 2019-06-06 11:04
	*/ 
	List<Map<String, Object>> listGoodsAssessPage(Integer goodsId, Integer limitStart, Integer limitEnd);

	/** 
	* 统计商品评价数量
	* @Title: countGoodsAssess 
	* @param goodsId
	* @return int
	* @author zj
	* @date 2019-06-06 11:04
	*/ 
	int countGoodsAssess(Integer goodsId);

	/**
	 * 发货更新物流信息
	 * @author sxy
	 * @param goodsOrder
	 */
	void updateOrderExpress(GoodsOrder goodsOrder) throws Exception;

	List<GoodsOrder> selectSecondKillOrder(Integer activityDetailId,Integer userId);

    /**
     * @author sxy
     * @throws Exception
     */
    void doFlashOrder() throws Exception;
	
    /**
     * 订单运费列表
     * @author sxy
     * @param orderNo
     * @param skuCode
     * @param orderStatus
     * @param payStartTime
     * @param payEndTime
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<Map<String, Object>> listCarriage(String orderNo, String skuCode, Integer orderStatus, Date payStartTime, Date payEndTime, Integer limitStart, Integer limitEnd);
    
    /**
     * 订单运费数量
     * @author sxy
     * @param orderNo
     * @param skuCode
     * @param orderStatus
     * @param payStartTime
     * @param payEndTime
     * @return
     */
    int countCarriage(String orderNo, String skuCode, Integer orderStatus, Date payStartTime, Date payEndTime);
 
}
