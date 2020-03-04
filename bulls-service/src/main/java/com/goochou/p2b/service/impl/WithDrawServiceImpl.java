package com.goochou.p2b.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.goochou.p2b.constant.WithdrawStatusEnum;
import com.goochou.p2b.model.*;
import com.goochou.p2b.model.vo.WithdrawRecordVO;
import com.goochou.p2b.model.vo.WithdrawVO;
import com.goochou.p2b.utils.DateUtil;
import com.goochou.p2b.utils.StringUtil;
import com.goochou.p2b.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.BusinessTableEnum;
import com.goochou.p2b.constant.assets.AccountOperateEnum;
import com.goochou.p2b.dao.AssetsMapper;
import com.goochou.p2b.dao.BankBranchMapper;
import com.goochou.p2b.dao.BankMapper;
import com.goochou.p2b.dao.TradeRecordMapper;
import com.goochou.p2b.dao.UserAdminMapper;
import com.goochou.p2b.dao.UserMapper;
import com.goochou.p2b.dao.WithdrawMapper;
import com.goochou.p2b.dao.WithdrawTempMapper;
import com.goochou.p2b.model.WithdrawExample.Criteria;
import com.goochou.p2b.service.BankCardService;
import com.goochou.p2b.service.UserAccountService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.service.WithdrawService;
import com.goochou.p2b.service.exceptions.LockFailureException;
import com.goochou.p2b.utils.BigDecimalUtil;

@Service
public class WithDrawServiceImpl implements WithdrawService {
	private static final Logger logger = Logger.getLogger(WithDrawServiceImpl.class);
    @Resource
    private WithdrawMapper withdrawMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private AssetsMapper assetsMapper;
    @Resource
    private UserAdminMapper userAdminMapper;
    @Resource
    private BankMapper bankMapper;
    @Resource
    private BankBranchMapper bankBranchMapper;
    @Resource
    private TradeRecordMapper tradeRecordMapper;
    @Resource
    private UserService userService;
    @Resource
    private BankCardService bankCardService;
    @Resource
    private WithdrawTempMapper withdrawTempMapper;
    @Resource
    private UserAccountService userAccountService;

    @Override
    public List<Map<String, Object>> query(String keyword, String payChannel, Date startTime, Date endTime, Integer status, Integer type, Integer start, Integer limit, Integer adminId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", keyword);
        map.put("start", start);
        map.put("payChannel", StringUtils.isBlank(payChannel) ? null : payChannel);
        map.put("limit", limit);
        map.put("status", status);
        map.put("type", type);
        map.put("adminId", adminId);
        map.put("startTime", startTime);
        if (endTime != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(endTime);
            c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
            map.put("endTime", c1.getTime());
        } else {
            map.put("endTime", null);
        }
        return withdrawMapper.query(map);
    }

    @Override
    public Map<String, Object> queryCount(String keyword, String payChannel, Date startTime, Date endTime, Integer status, Integer type, Integer adminId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", keyword);
        map.put("payChannel", payChannel);
        map.put("status", status);
        map.put("type", type);
        map.put("adminId", adminId);
        map.put("startTime", startTime);
//        if (endTime != null) {
//            Calendar c1 = Calendar.getInstance();
//            c1.setTime(endTime);
//            c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
//            map.put("endTime", c1.getTime());
//        } else {
//            map.put("endTime", null);
//        }
        map.put("endTime", endTime);
        return withdrawMapper.queryCount(map);
    }

    @Override
    public Map<String, Object> queryCount1(String keyword, String payChannel, Date createStartTime, Date createEndTime, Date startTime, Date endTime, Integer status, Integer type, Integer adminId, Integer departmentId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", keyword);
        map.put("payChannel", payChannel);
        map.put("status", status);
        map.put("type", type);
        map.put("adminId", adminId);
        map.put("startTime", DateUtil.getDayMinTime(startTime));
        map.put("endTime", DateUtil.getNextDayMinTime(endTime));
        map.put("createStartTime", DateUtil.getDayMinTime(createStartTime));
        map.put("createEndTime", DateUtil.getNextDayMinTime(createEndTime));
        map.put("departmentId", departmentId);
        return withdrawMapper.queryCount(map);
    }

    @Override
    public List<Map<String, Object>> query1(String keyword, String payChannel, Date createStartTime, Date createEndTime, Date startTime, Date endTime, Integer status, Integer type, Integer start, Integer limit, Integer adminId, Integer departmentId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if (type == null) {
//            type = 1;
        }
        map.put("keyword", keyword);
        map.put("start", start);
        map.put("payChannel", payChannel);
        map.put("limit", limit);
        map.put("status", status);
        map.put("type", type);
        map.put("adminId", adminId);
        map.put("startTime", DateUtil.getDayMinTime(startTime));
        map.put("endTime", DateUtil.getNextDayMinTime(endTime));
        map.put("createStartTime", DateUtil.getDayMinTime(createStartTime));
        map.put("createEndTime", DateUtil.getNextDayMinTime(createEndTime));
        map.put("departmentId", departmentId);
        return withdrawMapper.query(map);
    }

    @Override
    public Withdraw detail(Integer id) {
        Withdraw draw = withdrawMapper.selectByPrimaryKey(id);
        return draw;
    }

    // 更新提现单号
    @Override
    public void update(Withdraw withdraw) {
        withdrawMapper.updateByPrimaryKeySelective(withdraw);
        // 更新临时表
        String orderNo = withdraw.getOrderNo();
        WithdrawTemp withdrawTemp = new WithdrawTemp();
        Date now = new Date();
        withdrawTemp.setPredictSendDate(now);
        withdrawTemp.setUpdateDate(now);
        WithdrawTempExample example = new WithdrawTempExample();
        example.createCriteria().andOrderNoEqualTo(orderNo);
        withdrawTempMapper.updateByExampleSelective(withdrawTemp, example);

    }

    @Override
    public void saveForAppOne510(Withdraw w, Assets a, WithdrawTemp wt) throws Exception {
        withdrawMapper.insertSelective(w);

        int ret3 = userAccountService.modifyAccount(a,BigDecimal.valueOf(w.getAmount())
        		,w.getId(),BusinessTableEnum.withdraw,AccountOperateEnum.WITHDRAW_BALANCE_FREEZE);
        if (ret3 != 1) {
            throw new LockFailureException();
        }

        int ret = assetsMapper.updateByPrimaryKeyAndVersionSelective(a);
        if (ret != 1) {
            throw new LockFailureException();
        }
        int ret2 = withdrawTempMapper.insert(wt);
        if (ret2 != 1) {
            throw new LockFailureException();
        }

    }

    @Override
    public List<Withdraw> findRecordByUserId(int userId, Date startDate, Date endDate, Integer status, Integer
            start, Integer limit) {
        WithdrawExample example = new WithdrawExample();
        Criteria c = example.createCriteria();
        c.andUserIdEqualTo(userId);
        if (startDate != null && endDate != null) {
            c.andCreateDateBetween(startDate, endDate);
        }
        if (status != null) {
            c.andStatusEqualTo(status);
        } else {
            List<Integer> list = new ArrayList<>();
            list.add(1);
            list.add(2);
            list.add(4);
            c.andStatusIn(list);
        }
        example.setOrderByClause("create_time desc");
        example.setLimitStart(start);
        example.setLimitEnd(limit);
        return withdrawMapper.selectByExample(example);
    }

    @Override
    public List<Withdraw> findRecordByUserIdAndApp(Integer userId, Integer start, Integer limit) {
        return withdrawMapper.findRecordByUserIdAndApp(userId, start, limit);
    }

    @Override
    public Integer findRecordByUserIdCount(int userId, Date startDate, Date endDate, Integer status) {
        WithdrawExample example = new WithdrawExample();
        Criteria c = example.createCriteria();
        c.andUserIdEqualTo(userId);
        if (startDate != null && endDate != null) {
            c.andCreateDateBetween(startDate, endDate);
        }
        if (status != null) {
            c.andStatusEqualTo(status);
        }
        return withdrawMapper.countByExample(example);
    }

    @Override
    public Integer findRecordByUserIdCountApp(int userId) {
        WithdrawExample example = new WithdrawExample();
        Criteria c = example.createCriteria();
        c.andUserIdEqualTo(userId);
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(4);
        c.andStatusIn(list);
        return withdrawMapper.countByExample(example);
    }

    @Override
    public synchronized void audit(Withdraw withdraw, Integer source, boolean needSendMsg) throws
            Exception {
        withdrawMapper.updateByPrimaryKeySelective(withdraw);
        withdraw = withdrawMapper.selectByPrimaryKey(withdraw.getId());

        Assets assets = assetsMapper.selectByPrimaryKey(withdraw.getUserId());
        if (null != assets ) {

            if (withdraw.getStatus() == 1) {

            	if(userAccountService.modifyAccount(assets, BigDecimal.valueOf(withdraw.getAmount()), withdraw.getId(),
                        BusinessTableEnum.withdraw, AccountOperateEnum.WITHDRAW_BALANCE_FROZEN_SUBTRACT) == 0) {
            		throw new LockFailureException();
                }
            	if(userAccountService.modifyAccount(assets, BigDecimal.valueOf(withdraw.getAmount()), withdraw.getId(),
                    BusinessTableEnum.withdraw, AccountOperateEnum.WITHDRAW_CASH_ADD) == 0) {
            	    throw new LockFailureException();
            	}
            } else if (withdraw.getStatus() == 2 || withdraw.getStatus() == 3 || withdraw.getStatus() == 6) {
                // 提现失败或者取消或拒绝
                if(withdraw.getAmount()>assets.getFrozenAmount()) {
                	throw new Exception("提现失败,返回余额时冻结余额不足,id:"+withdraw.getId());
                }

                if(userAccountService.modifyAccount(assets, BigDecimal.valueOf(withdraw.getAmount()), withdraw.getId(),
                        BusinessTableEnum.withdraw, AccountOperateEnum.WITHDRAW_BALANCE_UNFREEZE) == 0) {
            		throw new LockFailureException();
                }
                // ------------失败发送短信
                if (needSendMsg) {
                    User u = userService.get(withdraw.getUserId());
                }
            }
            int ret = assetsMapper.updateByPrimaryKeyAndVersionSelective(assets);
            if (ret != 1) {
                throw new LockFailureException();
            }
        }
    }

    @Override
    public Boolean chargeWithdrawLimit(Integer userId, Double amount, Double limitAmount) {
        Map<String, Object> map = withdrawMapper.chargeWithdrawLimit(userId);
        limitAmount = limitAmount == null ? 1000000 : limitAmount;
        if (map != null) {
            Double all = (Double) map.get("amount");
            if (all == null) {
                all = 0d;
            }
            if (all + amount > limitAmount) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Map<String, Object> chargeWithdrawLimit(Integer userId) {
        return withdrawMapper.chargeWithdrawLimit(userId);
    }

    @Override
    public Integer getWithdrawApplyCount() {
        WithdrawExample example = new WithdrawExample();
        Criteria c = example.createCriteria();
        c.andStatusEqualTo(0);
        return withdrawMapper.countByExample(example);
    }

    @Override
    public int getWithdrawApplyCountByRole(Integer type, String roleName) {
        WithdrawExample example = new WithdrawExample();
        Criteria c = example.createCriteria();
        c.andTypeEqualTo(0);
        if (type == 0) {
            // 待审核的提现申请
            if ("technology".equals(roleName)) {
                c.andTechOperateUserIdIsNull();// 技术id是空
                c.andStatusEqualTo(0);
            } else if ("finance2".equals(roleName)) {
                c.andTechOperateUserIdIsNotNull();// 技术id不是空
                c.andFinanceOperateUserIdIsNull();
                c.andStatusEqualTo(0);
            } else if ("ceo".equals(roleName)) {
                c.andTechOperateUserIdIsNotNull();// 技术id不是空
                c.andFinanceOperateUserIdIsNotNull();
//                c.andCeoOperateUserIdIsNull();// ceoid是空
                c.andStatusEqualTo(4);
            } else if ("admin".equals(roleName)) {
                c.andStatusEqualTo(0);
            } else {
                return 0;
            }
        } else if (type == 1) {
            // 待打款的提现申请
            if ("finance".equals(roleName) || "admin".equals(roleName)) {
                c.andTechOperateUserIdIsNotNull();// 技术id不是空
                c.andFinanceOperateUserIdIsNotNull();
//                c.andCeoOperateUserIdIsNotNull();
                c.andStatusEqualTo(4);
            } else {
                return 0;
            }
        }
        return withdrawMapper.countByExample(example);

    }

    @Override
    public List<Withdraw> getWithdrawForExport(Integer status, String userName) {
        List<Withdraw> list = withdrawMapper.queryWithdrawForExport(status, userName);
        return list;
    }

    @Override
    public Withdraw getByOrderNo(String orderNo) {
        WithdrawExample example = new WithdrawExample();
        example.createCriteria().andOrderNoEqualTo(orderNo);
        List<Withdraw> list = withdrawMapper.selectByExample(example);
        if (null != list && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Withdraw> listByStatusAndMethod(List<Integer> status, String payChannel) {
        WithdrawExample example = new WithdrawExample();
        example.createCriteria().andStatusIn(status).andPayChannelEqualTo(payChannel);
        return withdrawMapper.selectByExample(example);
    }

    @Override
    public List<Map<String, Object>> getWithdrawMonthDay(Integer adminId, Integer departmentId) {
        return withdrawMapper.getWithdrawMonthDay(adminId, departmentId);
    }

    @Override
    public void save(Withdraw w) {
        withdrawMapper.insertSelective(w);
    }

    @Override
    public boolean checkIfSinaSuccess(Integer userId) {
        WithdrawExample example = new WithdrawExample();
        example.createCriteria().andStatusEqualTo(1).andUserIdEqualTo(userId).andPayChannelEqualTo("2"); // TODO sq 取枚举
        int count = withdrawMapper.countByExample(example);
        return count > 0 ? true : false;
    }

    @Override
    public Withdraw get(Integer id) {
        return withdrawMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Withdraw> getByMethodAndStatus(String payChannel, Integer status) {
        WithdrawExample example = new WithdrawExample();
        Criteria c = example.createCriteria();
        c.andPayChannelEqualTo(payChannel).andStatusEqualTo(status);
        return withdrawMapper.selectByExample(example);
    }

    @Override
    public Integer getCurMonthFreeCount(Integer userId) {
        return withdrawMapper.getCurMonthFreeCount(userId);
    }

	@Override
	public Integer queryWithdrawOfInProcessNeedAlert() {

		return withdrawMapper.queryWithdrawOfInProcessNeedAlert();
	}

	@Override
	public Integer queryTotalWithdrawToday() {

		return withdrawMapper.queryTotalWithdrawToday();
	}

	@Override
	public List<Withdraw> getWithdrawByDate(Date date, Date endDate) {

		WithdrawExample example = new WithdrawExample();
		example.createCriteria().andCreateDateGreaterThanOrEqualTo(date).andCreateDateLessThanOrEqualTo(endDate);
		return withdrawMapper.selectByExample(example);
	}

	@Override
	public List<Map<String, Object>> queryWithdrawByDate(String startTime, String endTime) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		return withdrawMapper.queryWithdrawByDate(params);
	}


    @Override
    public WithdrawVO getWithdrawDetailById(Integer id) {
        return withdrawMapper.getWithdrawDetailById(id);
    }

    private String getWithdrawStatus() {
        List<Integer> statusList = new ArrayList<>();
        statusList.add(WithdrawStatusEnum.AUDIT.getCode());
        statusList.add(WithdrawStatusEnum.SUCCESS.getCode());
        statusList.add(WithdrawStatusEnum.FAILURE.getCode());
//        statusList.add(WithdrawStatusEnum.CANCEL.getCode());
        statusList.add(WithdrawStatusEnum.BANK_PROCESS.getCode());
//        statusList.add(WithdrawStatusEnum.HANG_UP.getCode());
        return org.apache.commons.lang.StringUtils.join(statusList, ',');
    }
    
    private String getProcessWithdrawStatus() {
        List<Integer> statusList = new ArrayList<>();
        statusList.add(WithdrawStatusEnum.AUDIT.getCode());
        statusList.add(WithdrawStatusEnum.SUCCESS.getCode()); 
        statusList.add(WithdrawStatusEnum.BANK_PROCESS.getCode());
        return org.apache.commons.lang.StringUtils.join(statusList, ',');
    }
    public List<WithdrawRecordVO> queryWithdrawRecordByUser(Integer userId, Date startDate, Date endDate, Integer start, Integer end) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("startDate", DateUtil.getMinInDay(startDate));
        params.put("endDate", DateUtil.getMaxInDay(endDate));
        params.put("start", start);
        params.put("end", end);
        params.put("status", this.getWithdrawStatus());
        return this.withdrawMapper.queryUserWithdrawRecord(params);
    }

    public int queryWithdrawRecordCountByUser(Integer userId, Date startDate, Date endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("startDate", DateUtil.getMinInDay(startDate));
        params.put("endDate", DateUtil.getMaxInDay(endDate));
        params.put("status", this.getWithdrawStatus());
        return this.withdrawMapper.queryUserWithdrawRecordCount(params);
    }

    /**
     * @desc 查询成功或者处理中的提现记录
     * @author wangyun
     * @param userId
     * @param startDate
     * @param endDate
     * @param start
     * @param end
     * @return
     */
    @Override
    public List<WithdrawRecordVO> queryProcessWithdrawRecordByUser(Integer userId, Date startDate, Date endDate, Integer start, Integer end) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("startDate", DateUtil.getMinInDay(startDate));
        params.put("endDate", DateUtil.getMaxInDay(endDate));
        params.put("start", start);
        params.put("end", end);
        params.put("status", this.getProcessWithdrawStatus());
        return this.withdrawMapper.queryUserWithdrawRecord(params);
    }
    
    public Double queryWithdrawTotalAmount(Integer userId, Date startDate, Date endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("startDate", DateUtil.getMinInDay(startDate));
        params.put("endDate", DateUtil.getMaxInDay(endDate));
        return this.withdrawMapper.queryUserWithdrawSum(params);
    }

    @Override
    public WithdrawTemp getBySn(String reqSn) {
        WithdrawTempExample example = new WithdrawTempExample();
        example.createCriteria().andReqSnEqualTo(reqSn);
        List<WithdrawTemp> list = withdrawTempMapper.selectByExample(example);
        if (null != list && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
}
