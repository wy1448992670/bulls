package com.goochou.p2b.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.goochou.p2b.constant.BusinessTableEnum;
import com.goochou.p2b.constant.assets.AccountOperateEnum;
import com.goochou.p2b.dao.TradeRecordMapper;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.TradeRecord;
import com.goochou.p2b.model.vo.ActivityDataVO;
import com.goochou.p2b.model.vo.TradeRecordVO;
import com.goochou.p2b.model.vo.TransactionRecordDetailVO;
import com.goochou.p2b.model.vo.TransactionRecordVO;

public interface TradeRecordService {

    TradeRecordMapper getMapper();

    int addRecord(Assets assets, BigDecimal money, Integer businessId, BusinessTableEnum businessTableEnum, AccountOperateEnum accountOperateEnum) throws Exception;

    /**
     * 查询交易记录同时显示每日利率
     *
     * @param userId
     * @param start
     * @param limit
     * @return
     */
    public List<Map<String, Object>> getWithRate(Integer userId, List<String> aoeTypes, Integer start, Integer limit);

    public int getWithRateCount(Integer userId, List<String> aoeTypes);

    /**
     * 交易记录查询
     *
     * @param userId    当前用户ID
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param type      交易类型
     * @param start
     * @param limit
     * @return
     */
    public List<Map<String, Object>> findByUserId(int userId, Date startDate, Date endDate, Integer type, Integer source, Integer start, Integer limit) throws Exception;

    public Integer findByUserIdCount(int userId, Date startDate, Date endDate, Integer type, Integer source) throws Exception;

    /**
     * 通过投资记录的ID查询具体的投资详情 根据type区分
     *
     * @param id
     * @return
     */
    public Object detail(Integer id) throws Exception;

    public TradeRecord get(Integer id);

    public List<TradeRecord> findExcelTradeRecord(Date startDate, Date endDate, String aoeType, Integer start, Integer limit) throws Exception;

    public List<TradeRecord> findByUserIdApp(int userId, Date startDate, Date endDate, String aoeType, Integer start, Integer limit) throws Exception;
    public Integer findByUserIdAppCount(int userId, Date startDate, Date endDate, String aoeType) throws Exception;

    public Map<String, Object> detailApp(Integer id) throws Exception;

    public TradeRecord getTradeRecordByorderIdAndTableName(Integer id, String string);

    public Double selectAllAmountByType(int type);
    public Double selectAllAmountSource(Integer userId);

    public Double getNewInvesting(Date date, Integer id);

    public List<Map<String, Object>> query(String keyword, String aoeType, Date startTime, Date endTime, Integer start, Integer limit, Integer adminId);

    public Integer queryCount(String keyword, String aoeType, Date startTime, Date endTime, Integer adminId);

    /**
     * 王信，每日新增投资用户（指当日时间晚于用户注册日期）  类型是0
     *
     * @return
     */
    public List<Map<String, Object>> tradeAdd();

    /**
     * 在timeSlot时间段内，连续days天提现最少extractAmount的用户详情
     * 刘源 2015-11-6
     *
     * @param timeSlot      时间段 基本单位天
     * @param extractAmount 提现金额
     * @param days          连续天数
     * @return
     */
    public List<Map<String, Object>> continuousExtractionInTimeSlot(int timeSlot, double extractAmount, int days);

    /**
     * 某天刚充值则提现的用户（充值提现时间间隔小于1天）
     * * 刘源 2015-11-6
     *
     * @param date
     * @return
     */
    public List<Map<String, Object>> rechargeAndReflectInSameDay(Date date);

    /**
     * 前天投资时间在晚上21:00后，提现时间第二天在9::00前，以及用户数据
     * 刘源 2015-11-11
     *
     * @param limitdays 距统计日期相差天数 只能是>=0
     * @return
     */
    public List<Map<String, Object>> immediatelyAfterInvestmentOfAdjacent(int limitdays);

    /**
     * cha
     *
     * @param keyword
     * @param type
     * @param startTime
     * @param endTime
     * @return
     * @author 王信
     * @param adminId
     * @Create Date: 2015年12月28日下午3:43:36
     */
    public List<Map<String, Object>> selectShakeRecord(String keyword, Integer type, Date startTime, Date endTime, Integer start, Integer limit, Integer adminId);

    /**
     * @return
     * @author 王信
     * @param adminId
     * @Create Date: 2015年12月28日下午3:48:50
     */
    public Integer selectShakeRecordCount(String keyword, Integer type, Date startTime, Date endTime, Integer adminId);

    /**
     * 查询单个用户的摇一摇摇奖次数
     *
     * @param phone
     * @return
     * @author 王信
     * @Create Date: 2015年12月28日下午4:56:50
     */
    public Map<String, Object> selectShakeCount(String phone);

    /**
     * Description(描述):查询定期收益，前一天的回款。
     * DATE:2016/4/30 10:17
     *
     * @author：王信
     */

    public double selectRegularIncome(Integer userId);

    public Double getCountByTime(Integer userId, Date startTime, Date endTime);


    /**
     * 定期回款到用户余额后，用户未对进行投资、提现的用户名单
     * 回款日期后，用户未进行投资或提现的用户，充值未记录
     *
     * @param
     * @author 刘源
     * @date 2016/5/27
     */
    List<Map<String, Object>> noOperationAfterBackPay();

    /**
     * 根据类型获取金额信息
     */
    public Double getTransactionAmount(List<Integer> type);


    int findByUserIdandAmount(Integer userId);


    /**
     * @Description(描述):查询用户首次充值记录
     * @author zhaoxingxing
     * @date 2016/8/9
     * @params
     **/
    public TradeRecord getFirstTrade(Integer userId);


    /**
     * @Description(描述):运营周报表 新老客户投资情况
     * @author 王信
     * @date 2016/8/29
     * @params type  0新老客户     1新客
     **/
    List<Map<String, Object>> selectUserInvestmentWeeklyReport(String aoeType, Date startDate, Date endDate);

    /**
     * @Description(描述):运营周报表 新客活期投资多少
     * @author 王信
     * @date 2016/8/29
     * @params
     **/
    List<Map<String, Object>> selectNewUserHuoTotalAmount(String aoeType,Date startDate, Date endDate);

    //24小时报表统计投资 充值 提现等数据
    List<Map<String, Object>> hourReport(Integer adminId,Integer departmentId,String aoeType);

    public Double getTodayRedeemAmount(Integer userId);

    public List<TradeRecord> getHuoTradeList(Integer userId, String aoeType, Date startTime, Date endTime, Integer start, Integer limit);

    public Integer getHuoTradeListCount(Integer userId, String aoeType, Date startTime, Date endTime);

    /**
     * @Description: 累计收益
     * @date  2016/11/29
     * @author 王信
     */
    Map<String,Object> selectAccumulatedIncome(Integer userId);

    /**
     * @Description: 利息红包
     * @date  2016/11/30
     * @author zxx
     */
    public Double getHongbaoAmountByMonth(Date  date );

    /**
     * @Description(描述):查询用户首次记录
     * @author zhaoxingxing
     * @date 2016/8/9
     * @params
     **/
    public List<TradeRecord> getFirstTrade(Integer userId,String aoeType);

    public void saveTraderecord(TradeRecord tradeRecord);

    /**
     * @Description: 根据投资金额，时间获取各个客服下用户投资信息
     * @author xueqi
     * @param amount
     * @param date
     * @return List<TradeRecordVO>
     */
    public List<TradeRecordVO> queryInvestRecodeByDate(Double amount, String date);

    /**
     * 查询定期,新手标,新产品 的 投资,活转定
     * @return
     */
    List<TradeRecord> queryTodayInvestment();

    /**
     * 查询当天活期投资
     * @return
     */
    List<TradeRecord> queryTodayInvestmentHuo();

    List<TradeRecord> queryInvestmentByDate(Date date, Date endDate, Integer []source, Integer []types, List<Integer> registUsers, Boolean isIn);

    List<ActivityDataVO> queryTradeRecordInfoByHour(Date date, Date endDate, Integer []source, Integer []types, String inStr);

    List<ActivityDataVO> queryTradeRecordInfoByAge(Date date, Date endDate, Integer []source, Integer []types, String inStr);

    List<ActivityDataVO> queryTradeRecordInfoByArea(Date date, Date endDate, Integer []source, Integer []types, String inStr);

    /**
     * 交易记录查询
     *
     * @param userId    当前用户ID
     * @param type      交易类型
     * @return
     */
    public List<TradeRecord> find(int userId, Integer []type, Integer []source) throws Exception;

    /***
     * 根据月月盈投资记录ID,查询其回款记录
     * @param
     * @return
     */
    public List<TradeRecord> queryMonthlyGainRepayMentRecord(Map<String,Object> searchMap);

    /***
     * 根据月月盈投资记录ID,查询其回款记录条数
     * @param searchMap
     * @return
     */
    public int queryMonthlyGainRepayMentRecordCount(Map<String,Object> searchMap);

    /***
     * 根据月月盈投资记录ID,查询其回款金额（利息加本金）
     * @param
     * @return
     */
    public Double queryMonthlyGainRepayMentAmount(Map<String,Object> searchMap);

    public List<Map<String, Object>> queryTradeRecordInfo(String trueName, Date startTime, Date endTime,Integer page,Integer limit);

	public int getTradeRecordInfoCount(String trueName, Date startTime, Date endTime);

    /**
     * @description 后台管理交易记录查询
     * @author shuys
     * @date 2019/6/13
     * @param keyword
     * @param aoeType
     * @param startTime
     * @param endTime
     * @param start
     * @param end
     * @return java.util.List<com.goochou.p2b.model.vo.TransactionRecordVO>
     */
    List<TransactionRecordVO> queryTradeRecord(String keyword, String aoeType, Integer accountType, Date startTime, Date endTime, Integer userId, Integer start, Integer end, Integer adminId, Integer departmentId);


    /**
     * @description 后台管理交易记录查询 Count
     * @author shuys
     * @date 2019/6/13
     * @param keyword
     * @param aoeType
     * @param startTime
     * @param endTime
     * @return java.util.List<com.goochou.p2b.model.vo.TransactionRecordVO>
    */
    int queryTradeRecordCount(String keyword, String aoeType, Integer accountType, Date startTime, Date endTime, Integer userId, Integer adminId,  Integer departmentId);

    /**
     * @description 移动端交易记录查询
     * @author shuys
     * @date 2019/6/13
     * @param userId
     * @param start
     * @param end
     * @return java.util.List<com.goochou.p2b.model.vo.TransactionRecordVO>
     */
    List<TransactionRecordVO> queryTradeRecordFromApp(Integer userId, Integer accountType, Date startDate, Date endDate, Integer start, Integer end);

    /**
     * @description 移动端交易记录查询 Count
     * @author shuys
     * @date 2019/6/13
     * @param userId
     * @return java.util.List<com.goochou.p2b.model.vo.TransactionRecordVO>
    */
    int queryTradeRecordCountFromApp(Integer userId, Integer accountType, Date startDate, Date endDate);

    /**
     * @description 详情页交易记录
     * @author shuys
     * @date 2019/6/13
     * @param userId
     * @param start
     * @param end
     * @return java.util.List<com.goochou.p2b.model.vo.TransactionRecordVO>
     */
    List<TransactionRecordVO> queryTradeRecordByUserIdAndAoeType(Integer userId, List<String> aoeTypes, Integer start, Integer end, Integer adminId);

    /**
     * @description 详情页交易记录 Count
     * @author shuys
     * @date 2019/6/13
     * @param userId
     * @return java.util.List<com.goochou.p2b.model.vo.TransactionRecordVO>
     */
    int queryTradeRecordCountByUserIdAndAoeType(Integer userId, List<String> aoeTypes, Integer adminId);

    /**
     * @description 收入/支出
     * @author shuys
     * @date 2019/6/17
     * @param userId
     * @param accountType
     * @param startDate
     * @param endDate
     * @param isIncome   true 收入  false  支出
     * @return double
    */
    double queryUserTradeSumAmount(Integer userId, Integer accountType, Date startDate, Date endDate, boolean isIncome);

	/**
	* 预支回报明细
	* @Title: listReturnDetailByPage
	* @param userId
	* @param limitStart
	* @param limitEnd
	* @return List<Map<String,Object>>
	* @author zj
	* @date 2019-06-18 13:59
	*/
	List<Map<String, Object>> listReturnDetailByPage(String orderNo, Integer limitStart, Integer limitEnd);

	/**
	*统计预支回报明细的数量
	* @Title: countReturnDetail
	* @param userId
	* @return int
	* @author zj
	* @date 2019-06-18 14:05
	*/
	int countReturnDetail(String orderNo);

	/**
	 * @description 交易记录
	 * @author shuys
	 * @date 2019/6/28
	 * @param id
	 * @return com.goochou.p2b.model.vo.TransactionRecordVO
	*/
    TransactionRecordVO queryTradeRecordById(Integer id);

    /**
     * @description 交易记录详情
     * @author shuys
     * @date 2019/6/28
     * @param id
     * @return com.goochou.p2b.model.vo.TransactionRecordDetailVO
    */
    TransactionRecordDetailVO queryTradeRecordDetailById(Integer id) throws Exception;

    /**
     * @description 某个时间点客户资金明细
     * @author shuys
     * @date 2019/7/31
     * @param date
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    List<Map<String, String>> customDetailOfFunds(Date date,Integer adminId,Integer departmentId);

    /**
     * @description 某个时间点客户资金明细总和
     * @author shuys
     * @date 2019/7/31
     * @param date
     * @return java.util.Map<java.lang.String,java.lang.Object>
    */
    Map<String, String> customDetailOfFundsSum(Date date,Integer adminId,Integer departmentId);
}
