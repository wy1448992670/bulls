package com.goochou.p2b.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.goochou.p2b.dao.RechargeMapper;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.Recharge;
import com.goochou.p2b.model.TradeMessageLog;
import com.goochou.p2b.model.UserAdmin;

public interface RechargeService {

    public Map<String, Object> detail(Integer id);

    /**
     * 根据关键词分页查询
     *
     * @param keyword
     * @param start
     * @param limit
     * @return 记录总数 count，结果list
     */
    public List<Map<String, Object>> query(String keyword, Integer status, String payChannel, Date startTime, Date endTime, Integer start, Integer limit, String client,Integer adminId, String code,Integer departmentId) throws Exception;

    public Map<String, Object> queryCount(String keyword, Integer status, String payChannel, Date startTime, Date endTime, String client,Integer adminId, String code,Integer departmentId);

    public Integer querySum(String keyword, Integer status, String payChannel, Date startTime, Date endTime, String client,Integer adminId, String code,Integer departmentId);

    public List<Recharge> findByUserId(Integer userId, int start, int limit);

    public void save(Recharge recharge) throws Exception;

    public void update(Recharge recharge);

	//充值掉单补单
	public void updateFixed(Recharge recharge, UserAdmin admin) throws Exception;

    public Recharge getByOrder(String orderno);

    public List<Map<Integer, String>> getAIRechargePie();

    /**
     * 导出excel数据
     *
     * @param status 当前充值状态
     * @return
     */
    List<Map<String, Object>> getExportByStatus(Integer status);

    public Date getFirstHuoRechargeTime(Integer userId);

    /**
     * 充值月增长曲线图
     *
     * @param type 1活期 0 定期
     * @return
     */
    public List<Map<String, Object>> getRechargeAmountByMonthDay(Integer adminDepartmentId, Integer departmentId);

    /**
     * 检查订单号是否是该用户的
     *
     * @return
     */
    public Boolean checkOrderNo(Integer id, String orderno);

    /**
     * 判断是否首次通过新浪支付充值
     *
     * @param userId
     * @return
     */
    public Boolean checkIfFirstSinaPay(Integer userId);

    /**
     * 查询每日留存  按照输入月份查询
     *
     * @param month
     * @return
     * @author 王信
     * @Create Date: 2015年12月17日上午11:31:40
     */
    public List<Map<String, Object>> selectRechargeWithdrawDay(String month);

    /**
     * 查询每月留存  按照输入年份查询月份
     *
     * @param month
     * @return
     * @author 王信
     * @Create Date: 2015年12月17日上午11:31:40
     */
    public List<Map<String, Object>> selectRechargeWithdrawMonth(String year);

    /**
     * 折线图显示  返回  count  type
     *
     * @param month
     * @return
     * @author 王信
     * @Create Date: 2015年12月17日上午11:31:40
     */
    public List<Map<Integer, String>> selectRechargeWithdrawYear(String year);

    public List<Map<String, Object>> selectUsersAssetsDetailsDay(String days);

    public Recharge get(Integer id);

    /**
     * 当天充值信息（成功，失败）
     * @return
     */
    List<Recharge> queryAllChargeInfoToday();

    public List<Recharge> queryChargeAmountByDate(Date date, Date endDate);

    double getRechargeAmountByExpress(Integer userId, String dateExpress,String cardNo);

    List<Map<String, Object>> queryAllChargeInfo(Integer userId, Date startTime, Date endTime, int start, int limit);

    Integer queryAllChargeInfoCount(Integer id, Date startTime, Date endTime);

	public boolean updateRecord(Recharge recharge, Assets assets, TradeMessageLog tradeMessageLog) throws Exception;

	List<Recharge> selectRechargeException(String payChannel, Integer status, Integer limit, Date startDate);

	public int rechargeCancel(Date startDate);

	/**
	 * 通过订单类型和订单号查询支付记录
	 * 2019-05-15 张琼麒
	 * @param orderType
	 * @param orderNo
	 * @return
	 */
	public List<Recharge> getByOrderTypeAndNo(String orderType, String orderNo);
	/**
	 * 通过订单类型和订单号查询订单的支付状态
	 * 充值状态:0成功，1处理中，2失败
	 * 2019-05-15 张琼麒
	 * @param orderType
	 * @param orderNo
	 * @return
	 */
	public int getRechargeStatusByOrderTypeAndNo(String orderType, String orderNo);

	/**
	 * 调用接口,查询充值是否成功
	 *
	 * @param orderType
	 * @param orderNo
	 * @return
	 */
	public int getRechargeStatusConnectorAPI(Recharge recharge);

	/**
	 * 按orderType,otherId获得支付中的支付单
	 * @author 张琼麒
	 * @version 创建时间：2019年5月23日 下午7:20:48
	 * @param orderType
	 * @param orderNo
	 * @return
	 */
	public List<Recharge> getPayingRechargeByOrderTypeAndNo(String orderType, Integer otherId);

	/**
	 * 完成支付
	 * 确认支付单状态后,传入支付单,完成支付中的支付单状态更新
	 * 请先确认支付单状态:
	 * @see RechargeService#doTryCompletePayingRecharge(Recharge)
	 *
	 * @author 张琼麒
	 * @version 创建时间：2019年5月23日 下午6:14:55
	 * @param recharge
	 * @param remark
	 * @throws Exception
	 */
	//public void doCompletePayingRecharge(Recharge recharge, String remark) throws Exception;

	/**
	 * 尝试完成支付
	 * 传入支付中的支付单,查询支付单状态,完成支付中的支付单状态更新
	 * @author 张琼麒
	 * @version 创建时间：2019年5月23日 下午6:14:55
	 * @param recharge
	 * @param remark
	 * @throws Exception
	 */
	public void doTryCompletePayingRecharge(Recharge recharge) throws Exception;

	/**
	 * CAS更新
	 * @author 张琼麒
	 * @version 创建时间：2019年5月30日 下午6:04:42
	 * @param recharge
	 * @param status
	 * @return
	 */
	public int updateCAS(Recharge recharge, int status);

	void update(Recharge recharge, int status);

    /**
     * @param featureName
     * @param id
     * @return
     */
    public Recharge getPaySuccessRechargeByOrderTypeAndId(String orderType, Integer otherId);

	//充值掉单补单
	void updateFixedByOnLine(Integer id, UserAdmin admin) throws Exception;


	RechargeMapper getMapper();

	/**
     * @param recharge
     * @param assets
     * @throws Exception
     */
    public boolean updateBalance(Recharge recharge, Assets assets) throws Exception;

	/**
	 * @description 充值成功总额
	 * @author shuys
	 * @date 2019/8/6
	 * @param keyword
	 * @param payChannel
	 * @param startTime
	 * @param endTime
	 * @param client
	 * @param adminId
	 * @param code
	 * @return double
	 */
	double querySumAmount(String keyword, String payChannel, Date startTime, Date endTime, String client,Integer adminId, String code,Integer departmentId);
}
