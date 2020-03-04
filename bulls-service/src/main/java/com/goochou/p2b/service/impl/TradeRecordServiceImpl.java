package com.goochou.p2b.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.goochou.p2b.dao.*;
import com.goochou.p2b.model.*;
import com.goochou.p2b.model.goods.GoodsOrder;
import com.goochou.p2b.model.vo.TransactionRecordDetailVO;
import com.goochou.p2b.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.BusinessTableEnum;
import com.goochou.p2b.constant.assets.AccountOperateEnum;
import com.goochou.p2b.constant.assets.AccountOperateTypeEnum;
import com.goochou.p2b.constant.assets.AccountTypeEnum;
import com.goochou.p2b.model.TradeRecordExample.Criteria;
import com.goochou.p2b.model.vo.ActivityDataVO;
import com.goochou.p2b.model.vo.TradeRecordVO;
import com.goochou.p2b.model.vo.TransactionRecordVO;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.DateUtil;

import static com.goochou.p2b.constant.trade.TradeRecodeConstant.*;
import static com.goochou.p2b.constant.trade.TradeRecodeConstant.TABLE_RECHARGE;

@Service
public class TradeRecordServiceImpl implements TradeRecordService {

    private static final Logger logger = Logger.getLogger(TradeRecordServiceImpl.class);

    @Resource
    private TradeRecordMapper tradeRecordMapper;
    @Resource
    private InvestmentMapper investmentMapper;
    @Resource
    private RechargeMapper rechargeMapper;
    @Resource
    private WithdrawMapper withdrawMapper;
    @Resource
    private InterestMapper interestMapper;
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private CapitalMapper capitalMapper;
    @Resource
    private HongbaoMapper hongbaoMapper;
    @Resource
    private BankCardMapper bankCardMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private InvestmentDetailMapper investmentDetailMapper;
    @Resource
    private WithdrawCouponMapper withdrawCouponMapper;

    @Resource
    private HongbaoService hongbaoService;
    @Resource
    private GoodsOrderService goodsOrderService;
    @Resource
    private InvestmentService investmentService;
    @Resource
    private WithdrawService withdrawService;
    @Resource
    private RechargeService rechargeService;
    @Resource
    private JobService jobService;


    @Override
    public TradeRecordMapper getMapper(){
        return tradeRecordMapper;
    }


    @Override
    public int addRecord(Assets assets, BigDecimal money, Integer businessId, BusinessTableEnum businessTableEnum, AccountOperateEnum accountOperateEnum) throws Exception{
        //添加资金纪录
        TradeRecord record = new TradeRecord();
        record.setUserId(assets.getUserId());

        record.setOtherId(businessId);
        record.setTableName(businessTableEnum.name());

        record.setAmount(money.abs().doubleValue());
        record.setBalanceAmount(assets.getBalanceAmount());
        record.setFrozenAmount(assets.getFrozenAmount());
        record.setCreditAmount(assets.getCreditAmount());
        record.setFrozenCreditAmount(assets.getFreozenCreditAmount());

        record.setAoeType(accountOperateEnum.getFeatureName());

        record.setCreateDate(new Date());
        record.setUpdateDate(new Date());
        record.setAccountTypeId(accountOperateEnum.getAccountType().getFeatureType());
        record.setAccountOperateTypeId(accountOperateEnum.getAccountOperateType().getFeatureType());
        if(tradeRecordMapper.insertSelective(record)==0) {
        	throw new Exception("用户资金操作失败");
        }
        return 1;
    }

    @Override
    public List<Map<String, Object>> findByUserId(int userId, Date startDate, Date endDate, Integer type, Integer source, Integer start, Integer limit) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("startDate", DateUtil.getMinInDay(startDate));
        map.put("endDate", DateUtil.getMaxInDay(endDate));
//        map.put("type", type);
        map.put("start", start);
        map.put("limit", limit);
        map.put("source", source);
        return tradeRecordMapper.findByUserId(map);
    }

    @Override
    public Integer findByUserIdCount(int userId, Date startDate, Date endDate, Integer type, Integer source) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("startDate", DateUtil.getMinInDay(startDate));
        map.put("endDate", DateUtil.getMaxInDay(endDate));
        map.put("type", type);
        map.put("source", source);
        return tradeRecordMapper.findByUserIdCount(map);
    }

    @Override
    public Object detail(Integer id) throws Exception {
        if (id != null) {
            TradeRecord t = tradeRecordMapper.selectByPrimaryKey(id);
            if (t != null) {
                Integer otherId = t.getOtherId();
                Object o = null;
//                switch (t.getType()) {
//                    case 0:
//                        Investment i = investmentMapper.selectByPrimaryKey(otherId);// 投资
//                        if (i != null) {
//                            Project p = projectMapper.selectByPrimaryKey(i.getProjectId());
//                            if (i.getHongbaoId() != null) {
//                                i.setHbAmount((hongbaoMapper.selectByPrimaryKey(i.getHongbaoId())).getAmount());
//                            }
//                            i.setProject(p);
//                        }
//                        o = i;
//                        break;
//                    case 1:
//                        o = rechargeMapper.selectByPrimaryKey(otherId);// 充值
//                        break;
//                    case 2:
//                        o = withdrawMapper.selectByPrimaryKey(otherId);// 提现
//                        break;
//                    case 3:
//                        if (t.getTableName().equals("investment")) {//购买债转   转让人的收益
//                            Investment i2 = investmentMapper.selectByPrimaryKey(t.getOtherId());
//                            i2.setAmount(t.getAmount());
////                            Project p2 = projectMapper.selectByPrimaryKey(i2.getProjectId()).getProject();
////                            i2.setProject(p2);
//                            o = i2;
//                        } else {// interest
//                            Interest interest = interestMapper.selectByPrimaryKey(otherId);// 收益利息
//                            Investment i2 = investmentMapper.selectByPrimaryKey(interest.getInvestmentId());
//                            Project p2 = projectMapper.selectByPrimaryKey(i2.getProjectId());
//                            i2.setProject(p2);
//                            interest.setInvestment(i2);
//                            interest.setDate(t.getTime());
//                            o = interest;
//                        }
//                        break;
//                    case 4:
//
//                    case 5:
//                        Capital capital = capitalMapper.selectByPrimaryKey(otherId);// 回收本金
//                        Investment i3 = investmentMapper.selectByPrimaryKey(capital.getInvestmentId());
//                        Project p3 = projectMapper.selectByPrimaryKey(i3.getProjectId());
//                        if (p3.getParentId() != null) {
////                            p3 = p3.getProject();
//                        }
//                        i3.setProject(p3);
//                        capital.setInvestment(i3);
//                        o = capital;
//                        break;
//                    case 6:
//                        Investment i4 = investmentMapper.selectByPrimaryKey(otherId);// 投资
//                        Project p4 = projectMapper.selectByPrimaryKey(i4.getProjectId());
//                        i4.setProject(p4);
//                        o = i4;
//                        break;
//                    default:
//                        break;
//                }
                return o;
            }
        }
        return null;
    }

    @Override
    public List<TradeRecord> findExcelTradeRecord(Date startDate, Date endDate, String aoeType, Integer start,
                                                  Integer limit) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", DateUtil.getMinInDay(startDate));
        map.put("endDate", DateUtil.getMaxInDay(endDate));
        map.put("aoeType", aoeType);
        map.put("start", start);
        map.put("limit", limit);
        return tradeRecordMapper.findExcelTradeRecord(map);
    }

    @Override
    public TradeRecord get(Integer id) {
        return tradeRecordMapper.selectByPrimaryKey(id);
    }

    // 交易详情
    @Override
    public Map<String, Object> detailApp(Integer id) throws Exception {
        if (id != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            TradeRecord t = tradeRecordMapper.selectByPrimaryKey(id);
            map.put("id", t.getId());
//            map.put("time", t.getTime());
            map.put("amount", t.getAmount());
//            map.put("balance", t.getBalance());
            map.put("userId", t.getUserId());
            map.put("username", t.getUsername());
//            map.put("type", t.getType());
            Map<String, Object> params = new HashMap<String, Object>();
            if (t != null) {
                String tableName = t.getTableName();
//                if (t.getSource().equals(5)) {
//                    Product product = null;
//                    if ("investment".equals(tableName)) {
//                        product = productMapper.selectByInvestmentIdProduct(t.getOtherId());
//                        map.put("projectName", product.getName());//新产品
//                    } else if ("investment_detail".equals(tableName)) {
//                        InvestmentDetail detail = investmentDetailMapper.selectByPrimaryKey(t.getOtherId());
//                        product = productMapper.selectByInvestmentIdProduct(detail.getInvestmentId());
//                        map.put("projectName", product.getName());//新产品
//                    } else if ("capital".equals(tableName)) {
//                        Capital capital = capitalMapper.selectByPrimaryKey(t.getOtherId());
//                        product = productMapper.selectByInvestmentIdProduct(capital.getInvestmentId());
//                        map.put("projectName", product.getName());//新产品
//                    } else {
//                        map.put("projectName", "安鑫赚");//新产品
//                    }
//                    return map;
//                }
//                if (null != t.getOtherId()) {
//	                switch (t.getType()) {
//	                    case 0:
//	                    	// 是否是月月盈项目
//	                    	if(t.getSource().equals(9)){
//	                    		// 根据otherId查询月月盈项目名称
//	                    		Investment i = investmentMapper.selectByPrimaryKey(t.getOtherId());
//	                    		MonthlyGainProjectPackage mgpp  = monthlyGainProjectPackageMapper.selectByPrimaryKey(i.getPackageId());
//	                    		map.put("projectName", mgpp.getTitle());
//	                    		break;
//	                    	}
//
//	                        Investment i = investmentMapper.selectByPrimaryKey(t.getOtherId());
//	                        Project p = projectMapper.selectByPrimaryKey(i.getProjectId());
//	                        map.put("projectName", p.getTitle());
//	                        // 投资
//	                        break;
//	                    case 1:
//	                        // 充值
//	                        Recharge recharge = rechargeMapper.selectByPrimaryKey(t.getOtherId());
//	                        map.put("orderno", recharge.getOrderNo());
//	                        if (recharge.getCardNo() != null) {
//	                            map.put("tailNum", recharge.getCardNo().substring(recharge.getCardNo().length() - 4, recharge.getCardNo().length()));
//	                            BankCard bc = bankCardMapper.getBankbyCardNo(recharge.getCardNo());
//	                            map.put("bankName", bc.getBank().getName());
//	                        }
//	                        break;
//	                    case 2:
//	                        // 提现
//	                        Withdraw withdraw = withdrawMapper.selectByPrimaryKey(t.getOtherId());
//	                        map.put("tailNum", withdraw.getCardNo().substring(withdraw.getCardNo().length() - 4, withdraw.getCardNo().length()));
//	                        map.put("cardNo", withdraw.getCardNo());
//	                        map.put("withdrawType", withdraw.getType() == 0 ? "快速提现" : "普通提现");
//	                        //String bankCode = LianLianUtil.getBankCard(withdraw.getCardNo());
//	                        //Bank bank = bankService.getByCode(bankCode, 1);
////	                        Bank bank = bankService.get(withdraw.getBankId());
////	                        map.put("bankName", bank.getName());
//	                        WithdrawCoupon wc = withdrawCouponMapper.getByWithdrawId(withdraw.getId());
//	                        String haveWithdrawCoupon = wc == null ? "未使用" : "已使用"; //0是未使用 1已使用
//	                        map.put("haveWithdrawCoupon", haveWithdrawCoupon);
//	                        Double realAmount = withdraw.getRealAmount();
//	                        String commission = "0.00";
//	                        if (realAmount != 0.0) {
//	                            commission = new DecimalFormat(" 0.00").format(BigDecimalUtil.sub(withdraw.getAmount(), realAmount));
//	                        }
//	                        map.put("commission", commission);
//
//	                        break;
//	                    case 3://收益
//
//	                    	// 判断是否是月月盈项目
//	                    	if(t.getSource().equals(9)){
//	                    		MonthlyGainInterest mgi =  monthlyGainInterestMapper.selectByPrimaryKey(t.getOtherId());
//	                    		MonthlyGainProjectPackage mgpp  = monthlyGainProjectPackageMapper.selectByPrimaryKey(mgi.getPackageId());
//	                    		map.put("projectName",mgpp.getTitle());
//	                    		break;
//	                    	}
//
//	                        //活期收益项目名 显示
//	                        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
//	                        params.put("otherId", t.getOtherId());
//	                        params.put("tableName", tableName);
//	                        Map<String, Object> pro = null;
//	                        if (t.getSource() == 6 || t.getSource() == 7) {
//	                            map.put("projectName", projectMapper.selectByExpMoneyIdOrRateCouponId(params));
//	                        } else {
//	                            pro = projectMapper.selectByPrimaryInterest(params);
//	                            if (pro != null) {
//	                                if (t.getSource() == 0) {
//	                                    if (pro.get("parent_id") != null) {
//	                                        Integer pid = Integer.valueOf(pro.get("parent_id").toString());
//	                                        Map<String, Object> proj = projectMapper.selectByOtherIdTitle4(pid);
//	                                        String title = proj.get("title").toString();
//	                                        map.put("projectName", title);
//	                                    } else {
//	                                        map.put("projectName", pro.get("title"));
//	                                    }
//	                                } else {
//	                                    map.put("projectName", pro.get("title"));
//	                                }
//	                            } else {
//	                                map.put("projectName", "灵活宝");
//	                            }
//	                        }
//	                        map.put("time", f.format(t.getTime()));
//	                        break;
//	                    case 4:
//	                    	// 判断是否是月月盈项目
//	                    	if(t.getSource().equals(9)){
//	                    		Capital capital = capitalMapper.selectByPrimaryKey(t.getOtherId());
//	                    		MonthlyGainInvestmentDetail mgid = monthlyGainInvestmentDetailMapper.selectByPrimaryKey(capital.getInvestmentId());
//	                    		MonthlyGainProjectPackage mgpp  = monthlyGainProjectPackageMapper.selectByPrimaryKey(mgid.getPackageId());
//	                    		map.put("projectName", mgpp.getTitle());
//	                    		break;
//	                    	}
//
//	                        if (!StringUtils.isEmpty(tableName)) {
//	                            params.put("otherId", t.getOtherId());
//	                            params.put("tableName", tableName);
//	                            Map<String, Object> project = projectMapper.selectByOtherIdTitle2(params);
//	                            if (project != null) {
//	                                if (project.get("parent_id") != null) {
//	                                    Integer pid = Integer.valueOf(project.get("parent_id").toString());
//	                                    Map<String, Object> proj = projectMapper.selectByOtherIdTitle4(pid);
//	                                    map.put("projectName", proj.get("title"));
//	                                } else {
//	                                    map.put("projectName", project.get("title"));
//	                                }
//	                            } else {
//	                                map.put("projectName", "");
//	                            }
//	                        } else {
//	                            map.put("projectName", "");
//	                        }
//	                        break;
//	                    case 5:
//	                        if (!StringUtils.isEmpty(tableName)) {
//	                            params.put("otherId", t.getOtherId());
//	                            params.put("tableName", tableName);
//	                            Map<String, Object> project = projectMapper.selectByOtherIdTitle(params);
//	                            if (project != null) {
//	                                map.put("projectName", project.get("title"));
//	                            } else {
//	                                map.put("projectName", "");
//	                            }
//	                        } else {
//	                            map.put("projectName", "");
//	                        }
//	                        break;
//	                    case 6:
//	                    case 10:
//	                        if (!StringUtils.isEmpty(tableName)) {
//	                            params.put("otherId", t.getOtherId());
//	                            params.put("tableName", tableName);
//	                            Map<String, Object> project = projectMapper.selectByOtherIdTitle3(params);
//	                            if (project != null) {
//	                                map.put("projectName", project.get("title"));
//	                            } else {
//	                                map.put("projectName", "");
//	                            }
//	                        } else {
//	                            map.put("projectName", "");
//	                        }
//	                        break;
//	                    case 12:
//	                        map.put("type", 3);
//	                        map.put("projectName", t.getReamk()==null?"":t.getReamk());
//	                        break;
//	                    default:
//	                        break;
//	                }
//                }
                return map;
            }
        }
        return null;
    }

    @Override
    public List<TradeRecord> findByUserIdApp(int userId,
                                             Date startDate, Date endDate, String aoeType, Integer start,
                                             Integer limit) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("startDate", DateUtil.getMinInDay(startDate));
        map.put("endDate", DateUtil.getMaxInDay(endDate));
        map.put("type", aoeType);
        map.put("start", start);
        map.put("limit", limit);
        return tradeRecordMapper.findByUserIdApp(map);
    }

    @Override
    public Integer findByUserIdAppCount(int userId, Date startDate, Date endDate, String aoeType) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("startDate", DateUtil.getMinInDay(startDate));
        map.put("endDate", DateUtil.getMaxInDay(endDate));
        map.put("aoeType", aoeType);
        return tradeRecordMapper.findByUserIdAppCount(map);
    }

    @Override
    public TradeRecord getTradeRecordByorderIdAndTableName(Integer id, String tableName) {
        TradeRecordExample example = new TradeRecordExample();
        Criteria c = example.createCriteria();
        c.andOtherIdEqualTo(id);
        c.andTableNameEqualTo(tableName);
        return tradeRecordMapper.selectByExample(example).get(0);
    }

    @Override
    public List<Map<String, Object>> getWithRate(Integer userId, List<String> aoeTypes, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("aoeTypes", StringUtils.join(aoeTypes, "','"));
        map.put("start", start);
        map.put("limit", limit);
        return tradeRecordMapper.getWithRate(map);
    }

    @Override
    public int getWithRateCount(Integer userId, List<String> aoeTypes) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("aoeTypes", StringUtils.join(aoeTypes, "','"));
        return tradeRecordMapper.getWithRateCount(map);
    }

    @Override
    public Double selectAllAmountByType(int type) {
        return tradeRecordMapper.selectAllAmountByType(type);
    }

    @Override
    public Double getNewInvesting(Date date, Integer id) {
        List<Double> list = tradeRecordMapper.getNewInvesting(date, id);
        if (null != list && list.size() != 0) {
            if (list.get(0) == null) {
                return 0d;
            } else if (list.size() == 1 || list.get(1) == null) {
                return list.get(0);
            }
            return list.get(0) - list.get(1);
        }
        return 0d;
    }

    @Override
    public List<Map<String, Object>> query(String keyword, String aoeType, Date startDate, Date endDate, Integer start, Integer limit, Integer adminId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", DateUtil.getMinInDay(startDate));
        map.put("endDate", DateUtil.getMaxInDay(endDate));
        map.put("keyword", keyword);
        map.put("aoeType", aoeType);
        map.put("start", start);
        map.put("limit", limit);
//        map.put("adminId", adminId);
        return tradeRecordMapper.query(map);
    }

    @Override
    public Integer queryCount(String keyword, String aoeType, Date startDate, Date endDate, Integer adminId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", DateUtil.getMinInDay(startDate));
        map.put("endDate", DateUtil.getMaxInDay(endDate));
        map.put("keyword", keyword);
        map.put("aoeType", aoeType);
//        map.put("adminId", adminId);
        return tradeRecordMapper.queryCount(map);
    }

    /**
     * @return
     * @author 王信
     * @date 2015年11月5日 下午1:57:55
     */
    @Override
    public List<Map<String, Object>> tradeAdd() {

        return tradeRecordMapper.tradeAdd();
    }

    @Override
    public List<Map<String, Object>> continuousExtractionInTimeSlot(int timeSlot, double extractAmount, int days) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("timeSlot", timeSlot);
        map.put("extractAmount", extractAmount);
        map.put("days", days);
        return tradeRecordMapper.continuousExtractionInTimeSlot(map);
    }

    @Override
    public List<Map<String, Object>> rechargeAndReflectInSameDay(Date date) {
        return tradeRecordMapper.rechargeAndReflectInSameDay(date);
    }

    @Override
    public List<Map<String, Object>> immediatelyAfterInvestmentOfAdjacent(int limitdays) {
        if (limitdays < 0) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("late", limitdays);
        map.put("morning", limitdays + 1);
        return tradeRecordMapper.immediatelyAfterInvestmentOfAdjacent(map);
    }

    @Override
    public List<Map<String, Object>> selectShakeRecord(String keyword, Integer type, Date startTime, Date endTime, Integer start, Integer limit, Integer adminId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", DateUtil.getMinInDay(startTime));
        map.put("endTime", DateUtil.getMaxInDay(endTime));
        map.put("keyword", keyword);
        map.put("type", type);
        map.put("start", start);
        map.put("limit", limit);
        map.put("adminId", adminId);
        return tradeRecordMapper.selectShakeRecord(map);
    }

    @Override
    public Integer selectShakeRecordCount(String keyword, Integer type, Date startTime, Date endTime, Integer adminId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        if (endTime != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(endTime);
            c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
            map.put("endTime", c1.getTime());
        } else {
            map.put("endTime", null);
        }
        map.put("keyword", keyword);
        map.put("type", type);
        map.put("adminId", adminId);
        return tradeRecordMapper.selectShakeRecordCount(map);
    }

    @Override
    public Map<String, Object> selectShakeCount(String phone) {
        return tradeRecordMapper.selectShakeCount(phone);
    }

    @Override
    public double selectRegularIncome(Integer userId) {

        return tradeRecordMapper.selectRegularIncome(userId);
    }

    @Override
    public Double getTransactionAmount(List<Integer> list) {
        return tradeRecordMapper.getTransactionAmount(list);
    }

    @Override
    public List<Map<String, Object>> noOperationAfterBackPay() {
        return tradeRecordMapper.noOperationAfterBackPay();
    }

    @Override
    public Double getCountByTime(Integer userId, Date startTime, Date endTime) {
        TradeRecordExample example = new TradeRecordExample();
        example.createCriteria().andUserIdEqualTo(userId)//.andTypeEqualTo(3).andTimeBetween(startTime, endTime)
        ;
        List<TradeRecord> list = tradeRecordMapper.selectByExample(example);
        Double sum = 0d;
        if (null != list && !list.isEmpty()) {
            for (TradeRecord t : list) {
                sum = BigDecimalUtil.add(sum, t.getAmount());
            }
        }
        return sum;
    }


    @Override
    public TradeRecord getFirstTrade(Integer userId) {
        TradeRecordExample example = new TradeRecordExample();
        example.createCriteria().andUserIdEqualTo(userId)//.andTypeEqualTo(0)
        ;
        example.setOrderByClause("time");
        List<TradeRecord> list = tradeRecordMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> selectUserInvestmentWeeklyReport(String aoeType, Date startDate, Date endDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("aoeType", aoeType);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        return tradeRecordMapper.selectUserInvestmentWeeklyReport(map);
    }

    @Override
    public List<Map<String, Object>> selectNewUserHuoTotalAmount(String aoeType, Date startDate, Date endDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("aoeType", aoeType);
        return tradeRecordMapper.selectNewUserHuoTotalAmount(map);
    }


    @Override
    public List<Map<String, Object>> hourReport(Integer adminId,Integer departmentId,String aoeType) {
        return tradeRecordMapper.hourReport(adminId,departmentId,aoeType);
    }

    @Override
    public Double getTodayRedeemAmount(Integer userId) {
        Double amount = tradeRecordMapper.getTodayRedeemAmount(userId);
        if (amount != null) {
            return BigDecimalUtil.fixed2(amount);
        }
        return 0d;
    }

    @Override
    public List<TradeRecord> getHuoTradeList(Integer userId, String aoeType, Date startTime, Date endTime, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("aoeType", aoeType);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("start", start);
        map.put("limit", limit);
        return tradeRecordMapper.getHuoTradeList(map);
    }

    @Override
    public Integer getHuoTradeListCount(Integer userId, String aoeType, Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("aoeType", aoeType);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return tradeRecordMapper.getHuoTradeListCount(map);
    }

    @Override
    public Map<String, Object> selectAccumulatedIncome(Integer userId) {
        return tradeRecordMapper.selectAccumulatedIncome(userId);
    }

    public int findByUserIdandAmount(Integer userId) {
        return tradeRecordMapper.findByUserIdandAmount(userId);
    }

    @Override
    public Double getHongbaoAmountByMonth(Date date) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", date);
        return tradeRecordMapper.getHongbaoAmountByMonth(map);
    }

    @Override
    public List<TradeRecord> getFirstTrade(Integer userId, String aoeType) {
        TradeRecordExample example = new TradeRecordExample();
        example.createCriteria().andUserIdEqualTo(userId)//.andTypeEqualTo(type)
        ;
        example.setOrderByClause("create_date");
        List<TradeRecord> list = tradeRecordMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public void saveTraderecord(TradeRecord tradeRecord) {
        tradeRecordMapper.insertSelective(tradeRecord);

    }

    @Override
    public Double selectAllAmountSource(Integer userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        return tradeRecordMapper.selectAllAmountSource(map);
    }

	@Override
	public List<TradeRecordVO> queryInvestRecodeByDate(Double amount,
			String date) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("amount", amount);
        map.put("date", date);
        return tradeRecordMapper.queryInvestRecodeByDate(map);
	}

	@Override
	public List<TradeRecord> queryTodayInvestment(){

		TradeRecordExample example = new TradeRecordExample();

		//查询定期,新手标,新产品 的 投资,活转定
		Integer []types = new Integer[]{0, 10};
		List<Integer> typeList = Arrays.asList(types);

		Integer []sources = new Integer[]{0, 2, 5};
		List<Integer> sourceList = Arrays.asList(sources);

		Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        example.createCriteria().andCreateDateGreaterThanOrEqualTo(c.getTime())//.andTypeIn(typeList).andSourceIn(sourceList)
        ;
        return tradeRecordMapper.selectByExample(example);
	}

	@Override
	public List<TradeRecord> queryTodayInvestmentHuo(){

		TradeRecordExample example = new TradeRecordExample();

		//查询当天活期投资
		Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
//        example.createCriteria().andTypeEqualTo(0).andSourceEqualTo(1).andTimeGreaterThanOrEqualTo(c.getTime());
        return tradeRecordMapper.selectByExample(example);
	}

	@Override
	public List<TradeRecord> queryInvestmentByDate(Date date, Date endDate, Integer []source, Integer []types, List<Integer> registUsers, Boolean isIn) {

		TradeRecordExample example = new TradeRecordExample();
		Criteria c = example.createCriteria()//.andTimeGreaterThanOrEqualTo(date).andTimeLessThanOrEqualTo(endDate).andSourceIn(Arrays.asList(source)).andTypeIn(Arrays.asList(types))
         ;
		if(registUsers != null && isIn != null){
			if(isIn){
				c.andUserIdIn(registUsers);
			}else{
				c.andUserIdNotIn(registUsers);
			}
		}
		return tradeRecordMapper.selectByExample(example);
	}

	@Override
	public List<ActivityDataVO> queryTradeRecordInfoByHour(Date date, Date endDate,
			Integer[] source, Integer[] types, String inStr) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> map = new HashMap<>();
		map.put("source", Arrays.asList(source));
		map.put("types", Arrays.asList(types));
		map.put("inStr", inStr);
		map.put("date", format.format(date));
		map.put("endDate", format.format(endDate));
		return tradeRecordMapper.queryTradeRecordInfoByHour(map);
	}

	@Override
	public List<ActivityDataVO> queryTradeRecordInfoByAge(Date date, Date endDate,
			Integer[] source, Integer[] types, String inStr) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> map = new HashMap<>();
		map.put("source", Arrays.asList(source));
		map.put("types", Arrays.asList(types));
		map.put("inStr", inStr);
		map.put("date", format.format(date));
		map.put("endDate", format.format(endDate));
		return tradeRecordMapper.queryTradeRecordInfoByAge(map);
	}

	@Override
	public List<ActivityDataVO> queryTradeRecordInfoByArea(Date date, Date endDate,
			Integer[] source, Integer[] types, String inStr) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> map = new HashMap<>();
		map.put("source", Arrays.asList(source));
		map.put("types", Arrays.asList(types));
		map.put("inStr", inStr);
		map.put("date", format.format(date));
		map.put("endDate", format.format(endDate));
		return tradeRecordMapper.queryTradeRecordInfoByArea(map);
	}

	@Override
	public List<TradeRecord> find(int userId, Integer[] type,
			Integer[] source) throws Exception {

		TradeRecordExample example = new TradeRecordExample();
		example.createCriteria().andUserIdEqualTo(userId);

//		if(source != null && source.length > 0){
//			example.createCriteria().andSourceIn(Arrays.asList(source));
//		}
//
//		if(type != null && type.length > 0){
//			example.createCriteria().andTypeIn(Arrays.asList(type));
//		}

		return tradeRecordMapper.selectByExample(example);
	}

	@Override
	public List<TradeRecord> queryMonthlyGainRepayMentRecord(Map<String,Object> searchMap) {
		return tradeRecordMapper.queryMonthlyGainRepayMentRecord(searchMap);
	}

	@Override
	public Double queryMonthlyGainRepayMentAmount(Map<String,Object> searchMap) {
		return tradeRecordMapper.queryMonthlyGainRepayMentAmount(searchMap);
	}

	@Override
	public int queryMonthlyGainRepayMentRecordCount(Map<String, Object> searchMap) {
		return tradeRecordMapper.queryMonthlyGainRepayMentRecordCount(searchMap);
	}

	@Override
	public List<Map<String, Object>> queryTradeRecordInfo(String trueName, Date startTime, Date endTime,Integer page,Integer limit) {
		 Map<String,Object> map=new HashMap<String,Object>();
         map.put("trueName",trueName);
         map.put("startTime",DateUtil.format(startTime, "yyyy-MM-dd ")+"00:00:00");
         map.put("endTime",DateUtil.format(endTime, "yyyy-MM-dd ")+"23:59:59");
         map.put("start", page);
         map.put("limit", limit);
         List<Map<String, Object>> tradeRecodList=tradeRecordMapper.queryTradeRecord(map);
		return tradeRecodList;
	}
	@Override
	public int getTradeRecordInfoCount(String trueName, Date startTime, Date endTime) {
		 Map<String,Object> map=new HashMap<String,Object>();
		 map.put("trueName",trueName);
		 map.put("startTime",DateUtil.format(startTime, "yyyy-MM-dd ")+"00:00:00");
         map.put("endTime",DateUtil.format(endTime, "yyyy-MM-dd ")+"23:59:59");
         int count=tradeRecordMapper.queryTradeRecordCount(map);
		return count;
	}

    // 交易记录查询
	private List<TransactionRecordVO> queryUserTradeRecord(String keyword, String aoeType, Date startTime, Date endTime, Integer userId, Integer id,
                                                           List<Integer> accountTypeList, List<Integer> accountOperateTypeList, List<String> aoeTypeList, Integer start, Integer end, Integer adminId, Integer departmentId) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("keyword", keyword);
        params.put("id", id);
        params.put("aoeType", aoeType);
        params.put("startTime", DateUtil.getMinInDay(startTime));
        params.put("endTime", DateUtil.getMaxInDay(endTime));
        params.put("userId", userId);
        params.put("accountTypeIds", StringUtils.join(accountTypeList,","));
        params.put("accountOperateTypeIds", StringUtils.join(accountOperateTypeList,","));
        params.put("aoeTypes", StringUtils.join(aoeTypeList,"','"));
        params.put("start", start);
        params.put("end", end);
        params.put("adminId", adminId);
        params.put("departmentId", departmentId);
        
        return this.getMapper().queryUserTradeRecord(params);
    }

    // 交易记录查询 count
    private int queryUserTradeRecordCount(String keyword, String aoeType, Date startTime, Date endTime, Integer userId,
                                                           List<Integer> accountTypeList, List<Integer> accountOperateTypeList, List<String> aoeTypeList, Integer adminId, Integer departmentId) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("keyword", keyword);
        params.put("aoeType", aoeType);
        params.put("startTime", DateUtil.getMinInDay(startTime));
        params.put("endTime", DateUtil.getMaxInDay(endTime));
        params.put("userId", userId);
        params.put("accountTypeIds", StringUtils.join(accountTypeList,","));
        params.put("accountOperateTypeIds", StringUtils.join(accountOperateTypeList,","));
        params.put("aoeTypes", StringUtils.join(aoeTypeList,"','"));
        params.put("adminId", adminId);
        params.put("departmentId", departmentId);
        return this.getMapper().queryUserTradeRecordCount(params);
    }

    // 后台管理交易记录查询
    @Override
    public List<TransactionRecordVO> queryTradeRecord(String keyword, String aoeType, Integer accountType, Date startTime, Date endTime, Integer userId, Integer start, Integer end, Integer adminId, Integer departmentId) {
        List<Integer> accountTypeList = null;
        if (accountType != null) {
            accountTypeList = new ArrayList<>();
            accountTypeList.add(accountType);
        }
	    return this.queryUserTradeRecord(keyword, aoeType, startTime, endTime, userId, null, accountTypeList, null, null, start, end, adminId, departmentId);
    }

    // 后台管理交易记录查询 Count
    @Override
    public int queryTradeRecordCount(String keyword, String aoeType, Integer accountType, Date startTime, Date endTime, Integer userId, Integer adminId,  Integer departmentId) {
        List<Integer> accountTypeList = null;
        if (accountType != null) {
            accountTypeList = new ArrayList<>();
            accountTypeList.add(accountType);
        }
        return this.queryUserTradeRecordCount(keyword, aoeType, startTime, endTime, userId, accountTypeList, null, null, adminId, departmentId);
    }

    private List<Integer> getAPPAccountType(Integer accountType) {
        List<Integer> accountTypeList = new ArrayList<>(); // 用户账户类型
        if (accountType == null || accountType.equals(AccountTypeEnum.BALANCE.getFeatureType())) {
            accountTypeList.add(AccountTypeEnum.BALANCE.getFeatureType()); // 余额
        }
        if (accountType == null || accountType.equals(AccountTypeEnum.CASH.getFeatureType())) {
            accountTypeList.add(AccountTypeEnum.CASH.getFeatureType()); // 现金
        }
        return accountTypeList;
    }

    private List<Integer> getAPPAccountOperateType() {
        List<Integer> accountOperateType = new ArrayList<>(); // 对用户资金账户的操作类型
        accountOperateType.add(AccountOperateTypeEnum.ADD.getFeatureType());  // 余额增加
        accountOperateType.add(AccountOperateTypeEnum.SUBTRACT.getFeatureType());  // 余额减少
        accountOperateType.add(AccountOperateTypeEnum.UNFREEZE.getFeatureType()); // 解冻
        accountOperateType.add(AccountOperateTypeEnum.FREEZE.getFeatureType()); // 冻结
        return accountOperateType;
    }

    // 移动端交易记录查询  app
    @Override
    public List<TransactionRecordVO> queryTradeRecordFromApp(Integer userId, Integer accountType, Date startDate, Date endDate, Integer start, Integer end) {
        List<Integer> accountTypeList = this.getAPPAccountType(accountType);
        List<Integer> accountOperateTypeList = this.getAPPAccountOperateType();
        return this.queryUserTradeRecord(null, null, startDate, endDate, userId, null, accountTypeList, accountOperateTypeList, null, start, end, null,null);
    }

    // 移动端交易记录查询 Count  app
    @Override
    public int queryTradeRecordCountFromApp(Integer userId, Integer accountType, Date startDate, Date endDate) {
        List<Integer> accountTypeList = this.getAPPAccountType(accountType);
        List<Integer> accountOperateTypeList = this.getAPPAccountOperateType();
        return this.queryUserTradeRecordCount(null, null, startDate, endDate, userId, accountTypeList, accountOperateTypeList, null, null, null);
    }

    // 详情页交易记录查询
    @Override
    public List<TransactionRecordVO> queryTradeRecordByUserIdAndAoeType(Integer userId, List<String> aoeTypes, Integer start, Integer end, Integer adminId) {
        return this.queryUserTradeRecord(null, null, null, null, userId, null, null, null, aoeTypes, start, end, adminId, null);
    }

    // 详情页交易记录查询 Count
    @Override
    public int queryTradeRecordCountByUserIdAndAoeType(Integer userId, List<String> aoeTypes, Integer adminId) {
        return this.queryUserTradeRecordCount(null, null, null, null, userId, null, null, aoeTypes, adminId, null);
    }

    // 查询收入支出  isIncome: true 收入  false 支出
    @Override
    public double queryUserTradeSumAmount(Integer userId, Integer accountType, Date startDate, Date endDate, boolean isIncome) {
        List<Integer> accountTypeList = this.getAPPAccountType(accountType);
        Map<String, Object> params = new HashMap<>(16);
        params.put("userId", userId);
        params.put("startTime", startDate);
        params.put("endTime", endDate);
        params.put("accountTypeId", StringUtils.join(accountTypeList,","));
        List<Integer> accountOperateTypeList = new ArrayList<>();
        if (isIncome) {
            accountOperateTypeList.add(AccountOperateTypeEnum.ADD.getFeatureType());  // 余额增加
            accountOperateTypeList.add(AccountOperateTypeEnum.UNFREEZE.getFeatureType()); // 解冻
        } else {
            accountOperateTypeList.add(AccountOperateTypeEnum.SUBTRACT.getFeatureType());  // 余额减少
            accountOperateTypeList.add(AccountOperateTypeEnum.FREEZE.getFeatureType()); // 冻结
        }
        params.put("accountOperateTypeId", StringUtils.join(accountOperateTypeList,","));
        return this.getMapper().queryUserTradeSumAmount(params);
    }

	@Override
	public List<Map<String, Object>> listReturnDetailByPage(String orderNo, Integer limitStart, Integer limitEnd) {
		List<Map<String, Object>> list = this.getMapper().listReturnDetailByPage(orderNo, limitStart, limitEnd);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			int accountOperateTypeId = Integer.parseInt((String)map.get("account_operate_type_id"));
			String aoeType = (String)map.get("aoe_type");
			AccountOperateTypeEnum enum1 = AccountOperateTypeEnum.getValueByType(accountOperateTypeId);
			AccountOperateEnum accountOperateEnum = AccountOperateEnum.getValueByName(aoeType);
			map.put("description", enum1.getDescription());
			map.put("signSymbolStr", enum1.getSignSymbolStr());
			map.put("app_description", accountOperateEnum.getAppDescription());
		}
		return list;
	}

	@Override
	public int countReturnDetail(String orderNo) {
		return this.getMapper().countReturnDetail(orderNo);
	}

	public TransactionRecordVO queryTradeRecordById(Integer id) {
        TransactionRecordVO transactionRecordVO = null;
        List<TransactionRecordVO> list = this.queryUserTradeRecord(null, null, null, null, null, id, null, null, null, null, null, null, null);
        if (list != null && !list.isEmpty()) {
            transactionRecordVO = list.get(0);
        }
        return transactionRecordVO;
    }

    public TransactionRecordDetailVO queryTradeRecordDetailById(Integer id) throws Exception {
        TransactionRecordVO vo = this.queryTradeRecordById(id);
        if (vo == null) {
            throw new Exception("交易记录不存在");
        }
        TransactionRecordDetailVO detail = new TransactionRecordDetailVO();
        detail.setAppShowAmount(vo.getAppShowAmount());
//        detail.setPayV(vo.getAccountTypeMsg());
        detail.setCreateDate(vo.getCreateDate());
        detail.setAccountType(vo.getAccountTypeId());
        detail.setAoeType(vo.getAoeType());
        detail.setAccountOperateType(vo.getAccountOperateTypeId());

        String tableName = vo.getTableName();
        Integer sourceId = vo.getOtherId();
        boolean flag = false;
        switch (tableName) {
            case TABLE_WITHDRAW:
                Withdraw withdraw = withdrawService.get(sourceId);
                if (withdraw != null) {
                    flag = true;
                    detail.setOrderNo(withdraw.getOrderNo());
//                    String bankName = withdraw.getBankName();
//                    String cardNo = withdraw.getCardNo();
//                    String payChannel = bankName + "(" + com.goochou.p2b.utils.StringUtils.parseLastFourCardNo(cardNo) + ")";
//                    detail.setPayChannel(payChannel);
                }
                break;
            case TABLE_HONGBAO:
                Hongbao hongbao = hongbaoService.get(sourceId);
                if (hongbao != null) {
                    flag = true;

                }
                break;
            case TABLE_INVESTMENT:
                Investment investment = investmentService.get(sourceId);
                if (investment != null) {
                    flag = true;
                    detail.setOrderNo(investment.getOrderNo());
                }
                break;
            case TABLE_GOODS_ORDER:
                GoodsOrder goodsOrder = goodsOrderService.getMapper().selectByPrimaryKey(sourceId);
                if (goodsOrder != null) {
                    flag = true;
                    detail.setOrderNo(goodsOrder.getOrderNo());
                }
                break;
            case TABLE_RECHARGE:
                Recharge recharge = rechargeService.get(sourceId);
                if (recharge != null) {
                    flag = true;
                    detail.setOrderNo(recharge.getOrderNo());
                }
                break;
            default:
                break;
        }
        if (!flag) {
            throw new Exception("参数异常");
        }

        return detail;
    }

    public List<Map<String, String>> customDetailOfFunds(Date date,Integer adminId,Integer departmentId) {
        String currentPeriodTable=jobService.getAssetsSnapshotTableName(date);
        if (jobService.countTableName(currentPeriodTable) > 0) {
            return tradeRecordMapper.customDetailOfFunds(currentPeriodTable,adminId,departmentId);
        }
        logger.info("用户资金明细表不存在，tableName" + currentPeriodTable);
        return new ArrayList<>();
    }


    public Map<String, String> customDetailOfFundsSum(Date date,Integer adminId,Integer departmentId) {
        String currentPeriodTable=jobService.getExistAssetsSnapshotTableName(date);
        if (jobService.countTableName(currentPeriodTable) > 0) {
            return tradeRecordMapper.customDetailOfFundsSum(currentPeriodTable,adminId,departmentId);
        }
        logger.info("用户资金明细表不存在，tableName" + currentPeriodTable);
        return null;
    }


}
