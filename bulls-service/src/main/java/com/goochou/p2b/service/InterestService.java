package com.goochou.p2b.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.goochou.p2b.dao.InterestMapper;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.Interest;
import com.goochou.p2b.model.UserDailyInvest;
import com.goochou.p2b.model.UserInvestConfig;
import com.goochou.p2b.model.vo.AnXinZhuanOfflineDataVO;
import com.goochou.p2b.model.vo.InterestVO;

/**
 * 派息
 *
 * @author Administrator
 */
public interface InterestService {

    Double getUncollectInterestAmountByUserId(Integer userId);

    public List<Map<String, Double>> statisticsByYear(int userId);

    public List<Map<String, Object>> statisticsByMonth(int userId);

    public List<Map<String, Object>> findByUserId(Integer userId, int start, int limit);

    public List<Map<String, Object>> findCapitalByUserId(Integer userId, int start, int limit);

    /**
     * @Description(描述):定期派息定时器查询需要派送的利息
     **/
    public List<Interest> getUndividendedByDate(Date date, Integer limit);

    /**
     * @Description(描述):定期派息定时器查询需要发送短信的用户
     * @author 王信
     * @date 2016/5/16
     * @params
     **/
    public void getUndividendedByDateMessage() throws Exception;

    public boolean grantInterestToUser(Interest interest) throws Exception;

    public void grantDynamicInterestToUser(UserDailyInvest u) throws Exception;

    /**
     * 根据用户ID查询已经获取的利息
     *
     * @param userId
     */
    public Double getInterestedAmountByUserId(Integer userId);

    /**
     * @description 牛只订单详情派息明细
     * @author shuys
     * @date 2019/5/29
     * @param investmentId
     * @param start
     * @param limit
     * @return java.util.List<com.goochou.p2b.model.Interest>
    */
    List<Interest> getByInvestmentId(Integer investmentId, Integer start, Integer limit);

    /**
     * @description 牛只订单详情派息明细count
     * @author shuys
     * @date 2019/5/29
     * @param investmentId
     * @return java.util.List<com.goochou.p2b.model.Interest>
    */
    int getCountByInvestmentId(Integer investmentId);

    /**
     * @param userId
     * @param type   0 昨日收益 1近一周收益 2近一月收益 3总收益
     * @return
     */
    public Double getUserDynamicInterest(Integer userId, Integer type);

    public List<Map<String, Object>> query(String keyword, Date startTime, Date endTime, Integer start, Integer end);

    public Long queryCount(String keyword, Date startTime, Date endTime);

    public Map<String, Object> queryTotalAmount(String keyword, Date startTime, Date endTime);

    public List<Interest> detailByUser(Integer userId, Date startTime, Date endTime, Integer start, Integer limit);

    public Integer detailCount(Integer userId, Date startTime, Date endTime);

    /**
     * 某时间段内，连续只提取利息的用户信息详情，即单独提取利息 刘源 2015-11-6
     *
     * @param timeSlot
     * @return
     */
    public List<Map<String, Object>> queryDailyExtractInterest(int timeSlot);

    /**
     * 查询最近7天利息记录
     *
     * @return
     */
    public List<Interest> get7Day(Integer userId);

    /**
     * 查询最近即将回款记录
     * @param userId
     * @param investmentId
     * @param investmentDetailId
     * @return
     */
    public Interest queryLatestPaymentInfo(Integer userId, Integer investmentId, Integer investmentDetailId);


    /**
     * @Description(描述):回款列表 用户回款查看列表
     * @author 王信
     * @date 2016/9/2
     * @params
     **/
    List<Map<String, Object>> selectUserPaymentList(String keyword, Date startTime, Date endTime, Integer start, Integer limit, Integer adminId);

    Integer selectUserPaymentCount(String keyword, Date startTime, Date endTime, Integer adminId);

    List<Map<String, Object>> selectUserPaymentSum(String keyword, Date startTime, Date endTime, Integer adminId);

    /**
     * @Description: 2016-12-01 定期整改  项目满标之后,更新系统时间
     * @date 2016/12/1
     * @author 王信
     */
    List<Interest> selectRegularInterestList(Date now, Integer projectId, Double annualized);

    List<Interest> selectRegularInterestListTwo(Date now, Integer projectId, Double annualized);

    /**
     * 查询当月派发的总利息
     *
     * @param
     */
    public Double getInterestedAmountByMonth(Date date);

    public void update(Interest interest);

    public List<Interest> getPackageUnInterest();

    public List<Interest> getByInvestmentDetailId(Integer investmentDetailId);

    /**
     * 获取当天赎回信息
     * @return
     */
    List<Interest> queryTodayInterestInfo();

    List<Interest> queryInterestInfoByInvestemntInfo(AnXinZhuanOfflineDataVO date);

    Double[] offlineDataOperate(AnXinZhuanOfflineDataVO data, String type);

    List<Interest> getCreditorIncomeByInvestmentId(Integer investmentId);

    /**
     * 活期下架
     * @param data
     * @param type
     * @return
     */
    boolean doOfflineHuo(Assets assets) throws Exception;

    /**
     * 月月盈借款人派息
     * @param interest
     * @return
     */
    boolean grantMonthlyGainInterestToUser(Interest interest);

	List<Interest> selectMonthlyGainInterestList(Date now, Integer projectId,
			double annualized);

	/**
	 *
	 * <p>查询未派息总本金</p>
	 * @param userId
	 * @return
	 * @author: lxfeng
	 * @date: Created on 2018-7-26 下午6:32:36
	 */
	Double getNoDividendPayout(Integer userId);

	List<Map<String, Object>> investIntellectualList(String keyword, Date startTime, Date endTime, Integer start, Integer limit,Integer adminId,String quitable,Integer limitDay);

	Integer investIntellectualListCount(String keyword, Date startTime, Date endTime,Integer adminId,String quitable,Integer limitDay);

	Double investIntellectualListCountTotalAmount(String keyword, Date startTime, Date endTime, Integer adminId,String quitable,Integer limitDay);

	List<Interest> getCapitalUndividendedByDate(Date date, int limit);

	boolean grantInterestToConfigDetails(UserInvestConfig uic, Interest interest) throws Exception;
    /**
     * 计算回款本金
     * @param userId
     * @return
     */
	Double calTotalCapital(Integer userId);

	public void insert(Interest interest);

    List<InterestVO> queryInterestList(String keyword, String orderNo, Date investmentStartDate, Date investmentEndDate,
            Integer hasDividended, Date interestStartDate, Date interestEndDate, Integer start, Integer limit,Integer adminId,Integer departmentId);

	int queryInterestCount(String keyword, String orderNo, Date investmentStartDate, Date investmentEndDate, Integer hasDividended,
	Date interestStartDate, Date interestEndDate,Integer adminId,Integer departmentId);

	public List<Map<String, Object>> getInterestListByInvestId(Integer investId, Integer userId);

	public InterestMapper getInterestMapper();
}
