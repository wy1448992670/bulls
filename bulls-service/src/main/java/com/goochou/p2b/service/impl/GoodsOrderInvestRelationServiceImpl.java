package com.goochou.p2b.service.impl;
 
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.goochou.p2b.constant.BusinessTableEnum;
import com.goochou.p2b.constant.assets.AccountOperateEnum;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.InvestmentBlance;
import com.goochou.p2b.model.goods.GoodsOrder;
import com.goochou.p2b.service.*;
import com.goochou.p2b.service.exceptions.LockFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.goods.GoodsOrderInvestRelationEnum;
import com.goochou.p2b.dao.GoodsOrderInvestRelationMapper;
import com.goochou.p2b.model.goods.GoodsOrderInvestRelation;
import com.goochou.p2b.model.goods.GoodsOrderInvestRelationExample;

/**
 * @Auther: huangsj
 * @Date: 2019/5/10 16:48
 * @Description:
 */
@Service
public class GoodsOrderInvestRelationServiceImpl implements GoodsOrderInvestRelationService {


    @Autowired
    private GoodsOrderInvestRelationMapper goodsOrderInvestRelationMapper;

    @Autowired
    private AssetsService assetsService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private InvestmentService investmentService;
    @Autowired
    private InvestmentBlanceService investmentBlanceService;

	@Override
    public GoodsOrderInvestRelationMapper getMapper(){
    	return goodsOrderInvestRelationMapper;
	}


    @Override
    public int insert(Integer goodsOrderId, Integer investOrderId, BigDecimal money, boolean isUseSuccess, Integer tableType) {

        GoodsOrderInvestRelationEnum goodsOrderInvestRelationEnum;
        if(isUseSuccess){
            goodsOrderInvestRelationEnum = GoodsOrderInvestRelationEnum.USING;
        }
        else{
            goodsOrderInvestRelationEnum = GoodsOrderInvestRelationEnum.REFUND;
        }

        GoodsOrderInvestRelation relation=new GoodsOrderInvestRelation();
        relation.setAmount(money);
        relation.setInvestId(investOrderId);
        relation.setGoodsOrderId(goodsOrderId);
        relation.setCreateDate(new Date());
        relation.setState(goodsOrderInvestRelationEnum.getCode());
        relation.setTableType(tableType);
        return goodsOrderInvestRelationMapper.insertSelective(relation);
    }


    @Override
    public List<GoodsOrderInvestRelation> selectByExample(Integer goodsOrderId) {

        GoodsOrderInvestRelationExample example = new GoodsOrderInvestRelationExample();
        GoodsOrderInvestRelationExample.Criteria c = example.createCriteria();
        c.andGoodsOrderIdEqualTo(goodsOrderId);

        return goodsOrderInvestRelationMapper.selectByExample(example);
    }



    @Override
    public void creditBalanceUseSuccess(GoodsOrder goodsOrder) {
        //查找此商城订单使用了哪些购牛订单的多少授信额度
        List<GoodsOrderInvestRelation> relations = selectByExample(goodsOrder.getId());
        for (GoodsOrderInvestRelation relation : relations) {
            relation.setState(GoodsOrderInvestRelationEnum.SUCCESS.getCode());
            relation.setUpdateDate(new Date());
            goodsOrderInvestRelationMapper.updateByPrimaryKeySelective(relation);
        }
    }

    /**
	 * 因订单退款，订单取消，恢复之前使用的账单信用额度
	 * 退回investmentBlance.userAmount(creditAmount)
	 * @author 张琼麒
	 * @version 创建时间：2019年6月5日 下午3:25:43
	 * @param goodsOrder
	 * @param userAccount
	 * @throws Exception
	 */
	@Override
	public void recoverCreditBalance(GoodsOrder goodsOrder, Assets userAccount) throws Exception {
		// 查找此商城订单使用了哪些购牛订单的多少授信额度
		// GoodsOrderInvestRelation添加时按照investmentBlance.due_time desc,id desc 添加
		List<GoodsOrderInvestRelation> relations = selectByExample(goodsOrder.getId());
		for (GoodsOrderInvestRelation relation : relations) {
			relation.setState(GoodsOrderInvestRelationEnum.REFUND.getCode());
			relation.setUpdateDate(new Date());

			int affectedRows = goodsOrderInvestRelationMapper.updateByPrimaryKeySelective(relation);
			if (affectedRows == 0) {
				throw new LockFailureException();
			}

			// 恢复购牛订单的授信额度
			InvestmentBlance investmentBlance = investmentBlanceService.findByInvestId(relation.getInvestId());
			investmentBlance=investmentBlanceService.selectByPrimaryKeyForUpdate(investmentBlance.getId());
			if (investmentBlance.getState() == 1) {// 0未兑付,1已兑付
				// 如果购牛订单已结算，则之前使用的授信金额返还到余额
				//CREDIT,SUBTRACT
				userAccountService.modifyAccount(userAccount, relation.getAmount(),
						investmentBlance.getInvestmentId(), BusinessTableEnum.investment, AccountOperateEnum.INVEST_BUYBACK_INTEREST_CREDIT_SUBTRACT);
				//BLANCE,ADD
				userAccountService.modifyAccount(userAccount, relation.getAmount(), 
						investmentBlance.getInvestmentId(),	BusinessTableEnum.investment, AccountOperateEnum.INVEST_BUYBACK_INTEREST_BALANCE_ADD);
			} else {
				investmentBlance.setUseAmount(investmentBlance.getUseAmount().subtract(relation.getAmount()));
				investmentBlance.setUpdateDate(new Date());
				// 和添加时一个上锁顺序
				affectedRows = investmentBlanceService.getMapper().updateByPrimaryKeySelective(investmentBlance);
				if (affectedRows == 0) {
					throw new LockFailureException();
				}
			}
		}
	}

	/**
	 * 使用信用额度消费时,扣减账单信用额度
	 * 扣除investmentBlance.userAmount(creditAmount)
	 * @author 张琼麒
	 * @version 创建时间：2019年6月5日 下午3:25:43
	 * @param goodsOrder
	 * @param userAccount
	 * @throws Exception
	 */
	@Override
	public void addRelationWhenUseCreditBalance(Integer userId, Integer goodsOrderId, BigDecimal money,
			boolean isUseSuccess, Integer tabelType) throws Exception {
		// 查找当前还有授信额度的认养订单 优先用最后赎回的订单的授信资金
		// investmentBlance.due_time desc,id desc
		List<InvestmentBlance> investmentBlanceList = investmentBlanceService.findAvailableCreditBalanceInvestList(userId);

		BigDecimal needPayMoney = money;
		int affectedRows = 0;
		for (InvestmentBlance investmentBlance : investmentBlanceList) {
			// due_time desc,id desc 开排它锁
			investmentBlance = investmentBlanceService.selectByPrimaryKeyForUpdate(investmentBlance.getId());
			BigDecimal availableMoney = investmentBlance.getAmount().subtract(investmentBlance.getUseAmount());

			if (availableMoney.compareTo(needPayMoney) >= 0) {
				// 此订单的金额够支付
				investmentBlance.setUseAmount(investmentBlance.getUseAmount().add(needPayMoney));
				investmentBlance.setUpdateDate(new Date());
				affectedRows = investmentBlanceService.getMapper().updateByPrimaryKeySelective(investmentBlance);
				if (affectedRows == 0) {
					throw new LockFailureException();
				}

				// 添加使用纪录，使用金额为needPayMoney
				affectedRows = insert(goodsOrderId, investmentBlance.getInvestmentId(), needPayMoney, isUseSuccess,tabelType);
				if (affectedRows == 0) {
					throw new LockFailureException();
				}

				needPayMoney = BigDecimal.ZERO;
				break;
			} else {
				// 此订单的金额不够支付
				needPayMoney = needPayMoney.subtract(availableMoney);

				investmentBlance.setUseAmount(investmentBlance.getUseAmount().add(availableMoney));
				investmentBlance.setUpdateDate(new Date());
				affectedRows = investmentBlanceService.getMapper().updateByPrimaryKeySelective(investmentBlance);
				if (affectedRows == 0) {
					throw new LockFailureException();
				}

				// 添加使用纪录，使用金额为此订单可用的金额 availableMoney
				affectedRows = insert(goodsOrderId, investmentBlance.getInvestmentId(), availableMoney, isUseSuccess,tabelType);
				if (affectedRows == 0) {
					throw new LockFailureException();
				}
			}
		}
	}
}
