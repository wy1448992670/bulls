package com.goochou.p2b.service;

import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.Withdraw;
import com.goochou.p2b.model.WithdrawTemp;
import com.goochou.p2b.model.vo.WithdrawRecordVO;
import com.goochou.p2b.model.vo.WithdrawVO;
import com.goochou.p2b.service.exceptions.LockFailureException;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface WithdrawService {

    /**
     * @description 根据关键词分页查询
     * @author
     * @date 2019/5/30
     * @param keyword
     * @param payChannel
     * @param startTime
     * @param endTime
     * @param status
     * @param type
     * @param adminId
     * @return java.util.Map<java.lang.String,java.lang.Object>
    */
    public Map<String, Object> queryCount(String keyword, String payChannel, Date startTime, Date endTime, Integer status, Integer type, Integer adminId);

    public List<Map<String, Object>> query(String keyword, String payChannel, Date startTime, Date endTime, Integer status, Integer type, Integer start, Integer limit, Integer adminId) throws Exception;

    public Map<String, Object> queryCount1(String keyword, String payChannel, Date createStartTime, Date createEndTime, Date startTime, Date endTime, Integer status, Integer type, Integer adminId, Integer departmentId);

    public List<Map<String, Object>> query1(String keyword, String payChannel, Date createStartTime, Date createEndTime, Date startTime, Date endTime, Integer status, Integer type, Integer start, Integer limit, Integer adminId, Integer departmentId) throws Exception;

    public Withdraw detail(Integer id);

    public void update(Withdraw withdraw);

    public void saveForAppOne510(Withdraw w, Assets a, WithdrawTemp wt) throws Exception;

    /**
     * 提现审核
     * @throws Exception 
     */
    public void audit(Withdraw withdraw, Integer source, boolean needSendMsg) throws LockFailureException, Exception;

    /**
     * 查询用户提现记录
     */
    public List<Withdraw> findRecordByUserId(int userId, Date startDate, Date endDate, Integer status, Integer start, Integer limit);

    public Integer findRecordByUserIdCountApp(int userId);

    public Integer findRecordByUserIdCount(int userId, Date startDate, Date endDate, Integer status);

    /**
     * 判断当日总提现金额是否超限 默认100W true :允许提现 false:不允许
     *
     * @param limitAmount 限制金额
     * @return
     */
    public Boolean chargeWithdrawLimit(Integer userId, Double amount, Double limitAmount);

    /**
     * 查询用户当天提现成功次数以及提现总额
     *
     * @param userId
     * @return
     */
    public Map<String, Object> chargeWithdrawLimit(Integer userId);

    /**
     * 获取提现申请中待审核的申请数量
     *
     * @return
     */
    public Integer getWithdrawApplyCount();

    /**
     * 获取提现申请中待审核的申请数量--不同角色 type 0 待审核的提现申请 type 1 待打款的提现申请
     */
    public int getWithdrawApplyCountByRole(Integer type, String roleName);

    /**
     * 根据前台选择的提现状态和提现用户名来查询
     */
    public List<Withdraw> getWithdrawForExport(Integer status, String userName);

    public Withdraw getByOrderNo(String orderNo);

    public List<Withdraw> listByStatusAndMethod(List<Integer> status, String payChannel);

    /**
     * 统计每日提现金额
     *
     * @param
     * @return
     */
    public List<Map<String, Object>> getWithdrawMonthDay(Integer adminDepartmentId, Integer departmentId);

    /**
     * 获取用户活期提现列表
     */
    public List<Withdraw> findRecordByUserIdAndApp(Integer userId, Integer start, Integer limit);

    public void save(Withdraw w);

    /**
     * 判断用户是否从新浪提现成功过
     *
     * @return
     */
    public boolean checkIfSinaSuccess(Integer userId);

    public Withdraw get(Integer id);

    public List<Withdraw> getByMethodAndStatus(String payChannel, Integer status);

    Integer getCurMonthFreeCount(Integer userId);

    /**
     * 查询银行处理中需要告警的数据
     * @return
     */
    Integer queryWithdrawOfInProcessNeedAlert();

    /**
     * 当天提现总条数
     * @return
     */
    Integer queryTotalWithdrawToday();

    List<Withdraw> getWithdrawByDate(Date date, Date endDate);

	public List<Map<String, Object>> queryWithdrawByDate(String startTime, String endTime);

	/**
	 * @description 提现详情
	 * @author shuys
	 * @date 2019/5/30
	 * @param id
	 * @return com.goochou.p2b.model.vo.WithdrawVO
	*/
	WithdrawVO getWithdrawDetailById(Integer id);

	/**
	 * @description 提现记录 app
	 * @author shuys
	 * @date 2019/6/28
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @param start
	 * @param end
	 * @return java.util.List<com.goochou.p2b.model.vo.WithdrawRecordVO>
	*/
    List<WithdrawRecordVO> queryWithdrawRecordByUser(Integer userId, Date startDate, Date endDate, Integer start, Integer end);

    /**
     * @description 提现记录 count app
     * @author shuys
     * @date 2019/6/28
     * @param userId
     * @param startDate
     * @param endDate
     * @return int
    */
    int queryWithdrawRecordCountByUser(Integer userId, Date startDate, Date endDate);
    List<WithdrawRecordVO> queryProcessWithdrawRecordByUser(Integer userId, Date startDate, Date endDate, Integer start, Integer end);
    /**
     * @description 提现总金额
     * @author shuys
     * @date 2019/6/28
     * @param userId
     * @param startDate
     * @param endDate
     * @return java.lang.Double
    */
    Double queryWithdrawTotalAmount(Integer userId, Date startDate, Date endDate);

    /**
     * @param reqSn
     * @return
     */
    public WithdrawTemp getBySn(String reqSn);
}
