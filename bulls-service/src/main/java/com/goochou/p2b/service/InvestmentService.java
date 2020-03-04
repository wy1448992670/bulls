package com.goochou.p2b.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.goochou.p2b.dao.InvestmentMapper;
import com.goochou.p2b.model.Investment;
import com.goochou.p2b.model.InvestmentExample;
import com.goochou.p2b.model.InvestmentView;
import com.goochou.p2b.model.Project;
import com.goochou.p2b.model.vo.InvestementCountGroupLimitDay;
import com.goochou.p2b.model.vo.InvestmentOrderVO;
import com.goochou.p2b.model.vo.InvestmentSearchCondition;

public interface InvestmentService {


    InvestmentMapper getMapper();

    public Investment get(Integer id);

    public void update(Investment investment);

    /**
     * 根据关键词分页查询
     *
     * @return 记录总数 count，结果list
     */
    public List<Map<String, Object>> query(InvestmentSearchCondition searchCondition);

    public Map<String, Object> queryCount(InvestmentSearchCondition searchCondition);

    /**
     * 查询项目投资总金额
     */
    Double getAmountCount(Integer userId);

    public List<Investment> findByUserId(Integer userId, int start, int limit);

    /**
     * 投资记录查询
     *
     * @param userId    当前用户ID
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param status    项目状态
     * @param order     排序方式
     * @param start
     * @param limit
     * @return
     */
    public List<Map<String, Object>> findByUserId(int userId, Date startDate, Date endDate, Integer status, Integer order, Integer start, Integer limit) throws Exception;

    public Integer findByUserIdCount(int userId, Date startDate, Date endDate, Integer status) throws Exception;

    public Map<String, Object> investmentDetail(Integer investmentId);
    
    int getInvestmentCountByExample(InvestmentExample example);

	public List<Investment> getExceedTimeLimitInvestment() throws Exception;
	/**
	 *
	 *
	 * 关闭投资
	 * 2019-05-15 张琼麒
	 * @return
	 * @throws Exception
	 */
	public void cancelInvestment(Investment investment) throws Exception;

	Investment findByOrderNo(String orderNo);

	/**
	 * 关闭过期投资方法
	 * 2019-05-15 张琼麒
	 * @return
	 * @throws Exception
	 */
	//public void cancelExceedTimeLimitInvestment() throws Exception;


    List<InvestmentOrderVO> queryBullsOrderList(String keyword, String orderNo, Integer temp, Integer orderStatus, Integer payStatus, Date startDate, Date endDate, Integer curPage, Integer limit, Integer userId,Integer yueLing);

    Integer queryBullsOrderCount(String keyword, String orderNo, Integer temp, Integer orderStatus, Integer payStatus, Date startDate, Date endDate, Integer userId,Integer yueLing);

    /**
     * @description 派息记录（牛只订单详情）
     * @author shuys
     * @date 2019/5/29
     * @param id
     * @return com.goochou.p2b.model.vo.InvestmentOrderVO
    */
    InvestmentOrderVO queryOrderDetail(Integer id);

    /**
     * 传入project,预生成投资账单interest并注入到project中
     * @author 张琼麒
     * @version 创建时间：2019年5月27日 下午2:07:15
     * @param project
     * @throws Exception
     */
    public void pregeneratedInterest(Project project) throws Exception;
    
	/**
	 * 传入project,预生成简单的(不计算加息等活动)投资账单interest并注入到project中
	 *  Created on 2019-05-17
	 * <p>Title:[预生账单]</p>
	 * @author:[张琼麒]
	 * @update:[日期2019-05-17] [张琼麒]
	 * @param project
	 * @return
	 * @throws Exception
	 */
	public void pregeneratedSimpleInterest(Project project) throws Exception;
	
	/**
	 * 传入investment,回购该投资
	 *  Created on 2019-05-17 
	 * <p>Title:[预生账单]</p>
	 * @author:[张琼麒]
	 * @update:[日期2019-05-17] [张琼麒]
	 * @param investment
	 * @throws Exception
	 */
	public void doInvestmentBuyBack(Investment investment) throws Exception;

	/**
	 * 获得到期,可自动回购的投资
	 * 2019-05-15 张琼麒
	 * @return
	 * @throws Exception
	 */
	public List<Investment> getDueInvestmentCouldAutoBuyBack() throws Exception;
	
	/**
	 * 获得今日到期的投资订单
	 * 2019-06-19 张琼麒
	 * @return
	 * @throws Exception
	 */
	public List<Investment> getInvestmentDueInToday() throws Exception;

	/**
	 * 预支回报账单列表
	 * @author sxy
	 * @param userId
	 * @param limitStart
	 * @param limitEnd
	 * @return
	 */
	public List<Map<String,Object>> listPrepaidBill(Integer userId, Integer limitStart, Integer limitEnd);
	
	/**
	 * 预支回报账单数量
	 * @author sxy
	 * @param userId
	 * @return
	 */
	Integer countPrepaidBill(Integer userId);

    /**
     * 支付结果修改订单支付中或未支付状态 
     * @author ydp
     * @param investment
     * @param needStatus
     * @return
     */
	int update(Investment investment, int orderStatus, int payStatus);
    
    /**
     * 根据用户ID查询数量
     * @author ydp
     * @param userId
     * @return
     */
    public Integer queryCountByUserId(Integer userId);
    
    List<Investment> findByIds(List<Integer> ids);

    /**
     * @return
     */
    List<Map<String, Object>> getInvestAmountByMonthDay(Integer adminDepartmentId, Integer departmentId);

    /**
     * @param investId
     * @return
     */
    Map<String, Object> getProjectOrderInfo(Integer investId);
    
    /**
	 * 投资到期
	 * 2019-06-19 张琼麒
	 * @return
	 * @throws Exception
	 */
    public void doDue(Investment investment) throws Exception;

    /**
     * @return
     */
    Double getAmountAll(Integer adminId,Integer departmentId);
	
	/**
	 * @description 物权订单列表（最新版）
	 * @author shuys
	 * @date 2019/8/21
	 * @param keyword
	 * @param orderNo
	 * @param orderStatusArr
	 * @param payStatus
	 * @param startDate
	 * @param endDate
	 * @param curPage
	 * @param limit
	 * @param userId
	 * @param yueLing
	 * @return java.util.List<com.goochou.p2b.model.vo.InvestmentOrderVO>
	*/
	List<InvestmentOrderVO> listInvestment(String keyword, String orderNo, List<Integer> orderStatusArr, Integer payStatus, Date startDate, Date endDate,
			Date startDueTime, Date endDueTime,Date startBuyBackTime, Date endBuyBackTime,
			Integer curPage, Integer limit, Integer userId,Integer yueLing, Integer adminId,Integer departmentId);

	/**
	 * @description 物权订单列表 count（最新版）
	 * @author shuys
	 * @date 2019/8/21
	 * @param keyword
	 * @param orderNo
	 * @param orderStatusArr
	 * @param payStatus
	 * @param startDate
	 * @param endDate
	 * @param userId
	 * @param yueLing
	 * @return long
	*/
	long countInvestment(String keyword, String orderNo, List<Integer> orderStatusArr, Integer payStatus, Date startDate, Date endDate,
			Date startDueTime, Date endDueTime,Date startBuyBackTime, Date endBuyBackTime,Integer userId,Integer yueLing, Integer adminId,Integer departmentId);

	/**
	 * @description 统计不同期限的牛只数量 （默认查询交易成功  饲养期，已卖出状态）
	 * @author shuys
	 * @date 2019/8/21
	 * @param startDate
	 * @param endDate
	 * @return java.util.Map
	*/
	Map sumInvestment(Date startDate, Date endDate, List<Integer> orderStatusArr);

	List<InvestmentView> listInvestmentViewByProject(Integer userId, Integer projectId, List<Integer> orderStatusArr);

	List<InvestmentView> listInvestmentViewByProjectUnuserId(Integer unuserId, Integer projectId,
			List<Integer> orderStatusArr);
}
