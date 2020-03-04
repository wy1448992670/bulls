package com.goochou.p2b.service;

import com.goochou.p2b.dao.GoodsOrderInvestRelationMapper;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.goods.GoodsOrder;
import com.goochou.p2b.model.goods.GoodsOrderInvestRelation;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: huangsj
 * @Date: 2019/5/10 16:48
 * @Description:
 */
public interface GoodsOrderInvestRelationService {

	GoodsOrderInvestRelationMapper getMapper();

    int insert(Integer goodsOrderId, Integer investOrderId, BigDecimal money, boolean isUseSuccess, Integer tableType);

    List<GoodsOrderInvestRelation> selectByExample(Integer goodsOrderId);


    /**
     * 使用授信金额时纪录金额与订单的关系 按当前时间离到期时间的天数降序排列，最晚的放到最前面
     *
     * @param userId       用户编号
     * @param goodsOrderId 商城订单编号
     * @param money        使用的授信金额
     * @param isUseSuccess 授信金额是否使用成功
     */
    void addRelationWhenUseCreditBalance(Integer userId, Integer goodsOrderId, BigDecimal money, boolean isUseSuccess, Integer tabelType) throws Exception;

	/**
	 * 因订单退款，订单取消，恢复之前使用的账单信用额度
	 * 退回investmentBlance.userAmount(creditAmount)
	 * @author 张琼麒
	 * @version 创建时间：2019年6月5日 下午3:25:43
	 * @param goodsOrder
	 * @param userAccount
	 * @throws Exception
	 */
	void recoverCreditBalance(GoodsOrder goodsOrder, Assets userAccount) throws Exception;

	/**
	 * 使用信用额度消费时,扣减账单信用额度
	 * 扣除investmentBlance.userAmount(creditAmount)
	 * @author 张琼麒
	 * @version 创建时间：2019年6月5日 下午3:25:43
	 * @param goodsOrder
	 * @param userAccount
	 * @throws Exception
	 */
	void creditBalanceUseSuccess(GoodsOrder goodsOrder);

}
