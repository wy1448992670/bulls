package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.hessian.transaction.investment.InvestmentOrderListRequest;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.Hongbao;
import com.goochou.p2b.model.Investment;
import com.goochou.p2b.model.Project;
import com.goochou.p2b.model.vo.InvestmentDetailsVO;
import com.goochou.p2b.model.vo.InvestmentOrderListVO;

/**
 * @Auther: huangsj
 * @Date: 2019/5/13 16:21
 * @Description:
 */
public interface PastureOrderService {


    Investment findOrderByNum(String orderNum);

	/**
	 * 添加订单
	 * @param investment
	 * @param project
	 * @param hongbao
	 * @return
	 * @throws Exception
	 */
    @Deprecated
    boolean addOrder(Investment investment, Project project, Hongbao hongbao) throws Exception;

	/**
	 * 提交订单
	 * @param investment
	 * @param userAccount
	 * @param project
	 * @return
	 * @throws Exception
	 */
    @Deprecated
    boolean submitOrder(Investment investment, Assets userAccount, Project project) throws Exception;

	/**
	 * 取消订单
	 * @param order
	 * @return
	 * @throws Exception
	 */
	boolean cancelOrder(Investment order) throws Exception;

	/**
	 * 订单支付成功处理
	 * @param order
	 * @param userAccount
	 * @return
	 * @throws Exception
	 */
    boolean doPaySuccess(Investment order,Assets userAccount) throws Exception;
    /**
     * @date 2019年5月20日
     * @author wangyun
     * @time 下午2:18:05
     * @Description 牛只订单列表查询
     * 
     * @param request
     * @return
     * @throws Exception
     */
    List<InvestmentOrderListVO> queryPayList(InvestmentOrderListRequest request) throws Exception;


    int queryPayListCount(InvestmentOrderListRequest request) throws Exception;
	/**
	 * @date 2019年5月21日
	 * @author wangyun
	 * @time 上午10:40:52
	 * @Description 牛只订单详情
	 * 
	 * @param id
	 * @return
	 */
    InvestmentDetailsVO queryInvestDetailById(Integer id);

	/**
	 * 新增投资订单并提交支付
	 * 合并addOrder及submitOrder
	 * @author 张琼麒
	 * @version 创建时间：2019年6月1日 上午11:50:48
	 * @param investment
	 * @param userAccount
	 * @param project
	 * @param hongbao
	 * @return
	 * @throws Exception
	 */
	void addSubmitOrder(Investment investment, Assets userAccount, Project project, Hongbao hongbao)
			throws Exception;
	
}
