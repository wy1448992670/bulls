package com.goochou.p2b.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.goochou.p2b.constant.ActivityTriggerTypeEnum;
import com.goochou.p2b.constant.HongbaoTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.BusinessTableEnum;
import com.goochou.p2b.constant.assets.AccountOperateEnum;
import com.goochou.p2b.dao.AssetsMapper;
import com.goochou.p2b.dao.HongbaoMapper;
import com.goochou.p2b.dao.HongbaoTemplateMapper;
import com.goochou.p2b.dao.ProjectMapper;
import com.goochou.p2b.dao.TradeRecordMapper;
import com.goochou.p2b.dao.UserMapper;
import com.goochou.p2b.dao.UserSignedMapper;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.Hongbao;
import com.goochou.p2b.model.HongbaoExample;
import com.goochou.p2b.model.HongbaoExample.Criteria;
import com.goochou.p2b.model.HongbaoTemplate;
import com.goochou.p2b.model.HongbaoTemplateExample;
import com.goochou.p2b.model.Project;
import com.goochou.p2b.model.TradeRecord;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.vo.HongbaoTemplateModel;
import com.goochou.p2b.service.HongbaoService;
import com.goochou.p2b.service.MessageService;
import com.goochou.p2b.service.UserAccountService;
import com.goochou.p2b.service.exceptions.LockFailureException;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.DateFormatTools;
import com.goochou.p2b.utils.DateUtil;

@Service
public class HongbaoServiceImpl implements HongbaoService {

	private static final Logger logger = Logger.getLogger(HongbaoServiceImpl.class);

    @Resource
    private HongbaoMapper hongbaoMapper;
    @Resource
    private MessageService messageService;
    @Resource
    private TradeRecordMapper tradeRecordMapper;
    @Resource
    private AssetsMapper assetsMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserSignedMapper userSignedMapper;
    @Resource
    private HongbaoTemplateMapper hongbaoTemplateMapper;
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private UserAccountService userAccountService;

    @Override
    public HongbaoMapper getMapper(){
        return hongbaoMapper;
    }

    @Override
    public Hongbao addToUser(Double amount, Integer userId, String descript, Integer source, Integer type, Integer application, Integer limitDays) {
        Hongbao hb = new Hongbao();
        Calendar c = Calendar.getInstance();
        hb.setSendTime(c.getTime());
        hb.setAmount(amount);
        hb.setType(type);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        hb.setExpireTime(c.getTime());
        hb.setUserId(userId);
        hb.setLimitAmount(0);
        hb.setDescript(descript);
        hb.setSource(source);
//        if (limitDays != null) {
//            hb.setLimitDay(limitDays);
//        }
//        hb.setApplication(application);
        int ret = hongbaoMapper.insertSelective(hb);
        if (ret == 1) {
            // 发送消息
            String title = "恭喜您获得" + amount + "元红包奖励";
            messageService.save(title, descript == null ? title : descript, userId);
        }
        return hb;
    }


    @Override
    public Hongbao addToUser(Double amount, Integer userId, String descript, String title, Integer source, Integer type, Integer application, Integer limitDays) {
        Hongbao hb = new Hongbao();
        Calendar c = Calendar.getInstance();
        hb.setSendTime(c.getTime());
        hb.setAmount(amount);
        hb.setType(type);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        hb.setExpireTime(c.getTime());
        hb.setUserId(userId);
        hb.setLimitAmount(0);
        hb.setDescript(descript);
        hb.setSource(source);
//        if (limitDays != null) {
//            hb.setLimitDay(limitDays);
//        }
//        hb.setApplication(application);
        int ret = hongbaoMapper.insertSelective(hb);
        if (ret == 1) {
            // 发送消息
            if (title == null) {
                title = "恭喜您获得" + amount + "元红包奖励";
            }
            messageService.save(title, descript == null ? title : descript, userId);
        }
        return hb;
    }

    public Hongbao addToUser(Double amount, Integer userId, String descript, String title, Integer source, Integer type, Integer application, Integer limitDays, Integer efectiveDays) {
        Hongbao hb = new Hongbao();
        Calendar c = Calendar.getInstance();
        hb.setSendTime(c.getTime());
        hb.setAmount(amount);
        hb.setType(type);
        if (efectiveDays == null) {
            c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        } else {
            c.add(Calendar.DATE, efectiveDays);
        }
        hb.setExpireTime(c.getTime());
        hb.setUserId(userId);
        hb.setLimitAmount(0);
        hb.setDescript(descript);
        hb.setSource(source);
//        if (limitDays != null) {
//            hb.setLimitDay(limitDays);
//        }
//        hb.setApplication(application);
        hongbaoMapper.insertSelective(hb);
        return hb;
    }

    @Override
    public Hongbao get(Integer id) {
        return hongbaoMapper.selectByPrimaryKey(id);
    }

    @Override
    public Map<String, Integer> getByUser(Integer userId) {
        // 未使用的
        Integer noUseCount = (int) hongbaoMapper.countByExample(getSearchConditon(userId, 0, 0, 0));
        // 已使用
        Integer usedCount = (int) hongbaoMapper.countByExample(getSearchConditon(userId, 1, 0, 0));
        // 已过期的
        Integer expiredCount = (int) hongbaoMapper.countByExample(getSearchConditon(userId, 2, 0, 0));
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("noUseCount", noUseCount);
        map.put("usedCount", usedCount);
        map.put("expiredCount", expiredCount);
        return map;
    }

    /**
     * @param userId
     * @param status   0未使用1已使用2已过期
     * @param hbType 红包类型 0 利息1现金
     * @return
     */
    private HongbaoExample getSearchConditon(Integer userId, Integer status, Integer hbType, Integer application) {
        HongbaoExample example = new HongbaoExample();
        Criteria c = example.createCriteria();
        c.andUserIdEqualTo(userId);
        c.andTypeEqualTo(hbType);
        if (status == 0) {
            c.andUseTimeIsNull();
            Calendar c1 = Calendar.getInstance();
            c1.setTime(new Date());
            c1.set(Calendar.DATE, c1.get(Calendar.DATE));
            c1.set(Calendar.HOUR_OF_DAY, 0);
            c1.set(Calendar.MINUTE, 0);
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MILLISECOND, 0);
            c.andExpireTimeGreaterThanOrEqualTo(c1.getTime());
        } else if (status == 1) {
            c.andUseTimeIsNotNull();
        } else if (status == 2) {
            c.andUseTimeIsNull();
            Calendar c1 = Calendar.getInstance();
            c1.setTime(new Date());
            c1.set(Calendar.DATE, c1.get(Calendar.DATE));
            c1.set(Calendar.HOUR_OF_DAY, 0);
            c1.set(Calendar.MINUTE, 0);
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MILLISECOND, 0);
            c.andExpireTimeLessThan(c1.getTime());
        }
        return example;
    }

    @Override
    public List<Hongbao> getByUser(Integer userId, Integer projectLimitDays, Integer status, Integer hbType, Integer application, Integer start, Integer limit) {
        // 只有小于三个月的项目才能使用红包,查询可使用的红包和红包列表页面公用此方法
        if ((projectLimitDays != null && projectLimitDays <= 90) || projectLimitDays == null) {
            HongbaoExample example = getSearchConditon(userId, status, hbType, application);
            example.setLimitStart(start);
            example.setLimitEnd(limit);
            example.setOrderByClause("send_time,id desc");
            return hongbaoMapper.selectByExample(example);
        }
        return null;
    }

    @Override
    public List<Hongbao> getUserInterestHongbaoForApp(Integer userId, Integer status, Integer application, Integer start, Integer limit) {
        HongbaoExample example = new HongbaoExample();
        Criteria c = example.createCriteria();
        c.andUserIdEqualTo(userId);
        c.andTypeEqualTo(0); // 利息红包
        c.andSourceEqualTo(1);
        //c.andApplicationEqualTo(application); // 0定期1活期
        if (status == 0) {
            c.andUseTimeIsNull();
            Calendar c1 = Calendar.getInstance();
            c1.setTime(new Date());
            c1.set(Calendar.DATE, c1.get(Calendar.DATE));
            c1.set(Calendar.HOUR_OF_DAY, 0);
            c1.set(Calendar.MINUTE, 0);
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MILLISECOND, 0);
            c.andExpireTimeGreaterThanOrEqualTo(c1.getTime());
        } else if (status == 1) {
            return hongbaoMapper.getUsingInterestHongbaoForApp(userId, start, limit);
        } else if (status == 2) {
            c.andUseTimeIsNull();
            Calendar c1 = Calendar.getInstance();
            c1.setTime(new Date());
            c1.set(Calendar.DATE, c1.get(Calendar.DATE));
            c1.set(Calendar.HOUR_OF_DAY, 0);
            c1.set(Calendar.MINUTE, 0);
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MILLISECOND, 0);
            c.andExpireTimeLessThan(c1.getTime());
        }
        example.setLimitStart(start);
        example.setLimitEnd(limit);
        example.setOrderByClause("send_time,id desc");
        return hongbaoMapper.selectByExample(example);
    }

    @Override
    public Integer getUserInterestHongbaoCountForApp(Integer userId, Integer status, Integer application) {
        HongbaoExample example = new HongbaoExample();
        Criteria c = example.createCriteria();
        c.andUserIdEqualTo(userId);
        c.andTypeEqualTo(0);
        //c.andApplicationEqualTo(application); // 0定期1活期
        if (status == 0) {
            c.andUseTimeIsNull();
            Calendar c1 = Calendar.getInstance();
            c1.setTime(new Date());
            c1.set(Calendar.DATE, c1.get(Calendar.DATE));
            c1.set(Calendar.HOUR_OF_DAY, 0);
            c1.set(Calendar.MINUTE, 0);
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MILLISECOND, 0);
            c.andExpireTimeGreaterThanOrEqualTo(c1.getTime());
        } else if (status == 1) {
            return hongbaoMapper.getUsingInterestHongbaoCountForApp(userId);
        } else if (status == 2) {
            c.andUseTimeIsNull();
            Calendar c1 = Calendar.getInstance();
            c1.setTime(new Date());
            c1.set(Calendar.DATE, c1.get(Calendar.DATE));
            c1.set(Calendar.HOUR_OF_DAY, 0);
            c1.set(Calendar.MINUTE, 0);
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MILLISECOND, 0);
            c.andExpireTimeLessThan(c1.getTime());
        }
        return (int) hongbaoMapper.countByExample(example);
    }

    @Override
    public Integer getCountByUser(Integer userId, Integer status, Integer hbType, Integer application) {
        return (int) hongbaoMapper.countByExample(getSearchConditon(userId, status, hbType, application));
    }

    @Override
    public boolean getCountBySource(Integer userId, Integer source) {
        HongbaoExample example = new HongbaoExample();
        HongbaoExample.Criteria c = example.createCriteria();
        c.andUserIdEqualTo(userId);
        c.andSourceEqualTo(source);
        Integer count = (int) hongbaoMapper.countByExample(example);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public synchronized Map<String, Integer> useHongbao(Integer userId, Integer id) throws Exception {
        Hongbao hongbao = hongbaoMapper.selectByPrimaryKey(id);
        Map<String, Integer> map = new HashMap<String, Integer>();
        User user = userMapper.selectByPrimaryKey(userId);
        if (hongbao == null || !hongbao.getUserId().equals(userId)) {
            map.put("result", 1);
            return map;
        }
        if (hongbao.getUseTime() != null) {
            map.put("result", 2);
            return map;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DATE, c.get(Calendar.DATE));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(hongbao.getExpireTime());
        c2.set(Calendar.HOUR_OF_DAY, 0);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);
        c2.set(Calendar.MILLISECOND, 0);
        if (c2.getTimeInMillis() < c.getTimeInMillis()) {
            map.put("result", 3);
            return map;
        }
        hongbao.setUseTime(new Date());
        Assets assets = user.getAssets();
        hongbaoMapper.updateByPrimaryKey(hongbao);
        if (hongbao.getType() == 1) {
        	userAccountService.modifyAccount(assets,BigDecimal.valueOf(hongbao.getAmount())
        			,hongbao.getId(),BusinessTableEnum.hongbao,AccountOperateEnum.USE_CASH_HONGBAO_BALANCE_ADD);
        	/*
            TradeRecord tr = new TradeRecord();
            tr.setAmount(hongbao.getAmount());
            tr.setBalanceAmount(BigDecimalUtil.add(assets.getBalanceAmount(), hongbao.getAmount()));
            tr.setFrozenAmount(assets.getFrozenAmount());
            tr.setCreditAmount(assets.getCreditAmount());
            tr.setFrozenCreditAmount(assets.getFreozenCreditAmount());
            tr.setOtherId(hongbao.getId());
            tr.setTableName("hongbao");
            tr.setAoeType(AccountOperateEnum.USEHONGBAO.getFeatureName());
            tr.setUserId(userId);
            tradeRecordMapper.insert(tr);
            // 修改用户资产
            assets.setBalanceAmount(BigDecimalUtil.add(assets.getBalanceAmount(), hongbao.getAmount())); // 账户总资产+红包金额
            */

            int ret = assetsMapper.updateByPrimaryKeyAndVersionSelective(assets);
            if (ret != 1) {
                throw new LockFailureException();
            }
        }else {
            map.put("result", 5);
            return map;
        }
        map.put("result", 4);
        return map;
    }

    @Override
    public Double getTodayHongbao(Integer userId) {
        return hongbaoMapper.getTodayHongbao(userId);
    }

    @Override
    public List<Map<String, Object>> query(String keyword, Integer application, Integer type, Date startSendTime, Date endSendTime, Date startUseTime, Date endUseTime, Integer start, Integer limit, Integer adminId, Integer departmentId) {
        Map<String, Object> map = new HashMap<String, Object>(16);
        map.put("startSendTime", DateUtil.getMinInDay(startSendTime));
        map.put("endSendTime", DateUtil.getMaxInDay(endSendTime));
        map.put("startUseTime", DateUtil.getMinInDay(startUseTime));
        map.put("endUseTime", DateUtil.getMaxInDay(endUseTime));
        map.put("keyword", keyword);
        map.put("application", application);
        map.put("type", type);
        map.put("start", start);
        map.put("limit", limit);
        map.put("adminId", adminId);
        map.put("departmentId", departmentId);
        return hongbaoMapper.query(map);
    }

    @Override
    public Integer queryCount(String keyword, Integer application, Integer type, Date startSendTime, Date endSendTime, Date startUseTime, Date endUseTime, Integer adminId, Integer departmentId) {
        Map<String, Object> map = new HashMap<String, Object>(16);
        map.put("startSendTime", DateUtil.getMinInDay(startSendTime));
        map.put("endSendTime", DateUtil.getMaxInDay(endSendTime));
        map.put("startUseTime", DateUtil.getMinInDay(startUseTime));
        map.put("endUseTime", DateUtil.getMaxInDay(endUseTime));
        map.put("keyword", keyword);
        map.put("application", application);
        map.put("type", type);
        map.put("adminId", adminId);
        map.put("departmentId", departmentId);
        return hongbaoMapper.queryCount(map);
    }

    @Override
    public List<Hongbao> selectEffective(Integer userId, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("start", start);
        map.put("limit", limit);
        return hongbaoMapper.selectEffective(map);
    }

    @Override
    public Integer selectEffectiveCount(Integer userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        return hongbaoMapper.selectEffectiveCount(map);
    }

    @Override
    public Hongbao queryByIdOrUser(Integer userId, Integer Id) {
        Hongbao hongbao = hongbaoMapper.selectByPrimaryKey(Id);
        if (userId.equals(hongbao.getUserId())) {
            return hongbao;
        }
        return null;
    }

    @Override
    public void sendInvestmentHongbaoMessage() throws Exception {
        //查询所需要发送的短信用的用户ID
        List<String> list = hongbaoMapper.selectInvestmentHongbaoMessage();
        for (String phone : list) {
            String content = "尊敬的用户：您的账户中还有投资红包未使用，红包即将过期，快打开APP使用吧！ TD退订【鑫聚财】";
            //SendMessageUtils.send(content, phone);
        }
    }

    @Override
    public Integer getHongbaoInverstmentCount(Integer userId, Integer type, Integer status, Double limitAmount, Integer limitDay) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("status", status);
        map.put("type", type);
        map.put("limitAmount", limitAmount);
        map.put("limitDay", limitDay);
        return hongbaoMapper.getHongbaoInverstmentCount(map);
    }

    @Override
    public List<Hongbao> getHongbaoInverstmentList(Integer userId, Integer type, Integer status, Integer start, Integer limit, Double limitAmount, Integer limitDay) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("status", status);
        map.put("start", start);
        map.put("limit", limit);
        map.put("type", type);
        map.put("limitAmount", limitAmount);
        map.put("limitDay", limitDay);
        return hongbaoMapper.getHongbaoInverstmentList(map);
    }

    @Override
    public List<Map<String, Object>> getInverstmentHongbaoList(Integer userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        return hongbaoMapper.getInverstmentHongbaoList(map);
    }

    @Override
    public int getInverstmentHongbaoCount(Integer userId) {
        return hongbaoMapper.getInverstmentHongbaoCount(userId);
    }

    @Override
    public void saveInverstmentHongbao(Hongbao hongbao) {
        hongbaoMapper.insertSelective(hongbao);
    }

    @Override
    public List<Map<String, Object>> webListInvestHb(Integer userId) {
        return hongbaoMapper.webListInvestHb(userId);
    }

    @Override
    public List<Map<String, Object>> selectUserUseList(String keyword, Integer type, Integer isUse, Date startTime,
                                                       Date endTime, Integer redeemId, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", keyword);
        map.put("type", type);
        map.put("isUse", isUse);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("redeemId", redeemId);
        map.put("start", start);
        map.put("limit", limit);
        return hongbaoMapper.selectUserUseList(map);
    }

    @Override
    public Integer selectUserUseCount(String keyword, Integer type, Integer isUser, Date startTime, Date endTime, Integer redeemId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", keyword);
        map.put("type", type);
        map.put("isUse", isUser);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("redeemId", redeemId);
        return hongbaoMapper.selectUserUseCount(map);
    }

    @Override
    public List<Map<String, Object>> selectReportHongbaoUseList(Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return hongbaoMapper.selectReportHongbaoUseList(map);
    }

    @Override
    public void saveToUserInvestmentHongbao(List<HongbaoTemplateModel> list, List<String> list2, Integer type, String descript, Integer days, Integer adminId) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        for (HongbaoTemplateModel model : list) {
            Date expTime = DateFormatTools.jumpOneDay(new Date(), model.getEffectiveDay());
            HongbaoTemplate template = model.getHongbaoTemplate();
            HongbaoTemplateExample example = new HongbaoTemplateExample();
			example.createCriteria().andAmountEqualTo(template.getAmount()).andLimitAmountEqualTo(template.getLimitAmount()).andTypeEqualTo(
			    type).andNameEqualTo(template.getName()).andEffectiveDaysEqualTo(template.getEffectiveDays());
			if(type == 2 || type == 4) {
			    example.createCriteria().andLimitDayEqualTo(template.getLimitDay());
			}
            List<HongbaoTemplate> templateList = hongbaoTemplateMapper.selectByExample(example);
            HongbaoTemplate templateOld = null;
            if (templateList.size() > 0) {
                templateOld = templateList.get(0);
            }
            if (templateOld == null) {
                if(type == 2 || type == 4) { //2投资红包 4拼牛红包
                    template.setLimitDay(model.getMinInvestDay()); //最低投资天数
                }
            	template.setType(type); //1现金红包 2投资红包 3优惠券 4拼牛红包
            	template.setName(model.getDescript()); //红包标题
            	template.setEffectiveDays(model.getEffectiveDay()); //红包有效天数
                hongbaoTemplateMapper.insertSelective(template);
            } else {
                template = templateOld;
            }

//            for (String userIdStr : list2) {
//                Hongbao hongbao=new Hongbao();
//                hongbao.setTemplateId(template.getId());
//                int userId = Integer.parseInt(userIdStr);
//                hongbao.setUserId(userId);
//                hongbao.setLimitAmount(template.getLimitAmount());
//                hongbao.setSendTime(new Date());
//                hongbao.setAmount(template.getAmount());
//                //hongbao.setApplication(0);
//                hongbao.setDescript(descript);
//                hongbao.setExpireTime(DateFormatTools.strToDate(sdf.format(expTime)));
//                hongbao.setType(2);
//                hongbao.setSource(0);
//                hongbaoMapper.insertSelective(hongbao);
//            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("template_id", template.getId());
            map.put("templateLimitAmount", template.getLimitAmount());
            map.put("templateAmount", template.getAmount());
            map.put("descript", model.getDescript());
            map.put("sendTime", new Date());
            map.put("expireTime", DateFormatTools.strToDate(sdf.format(expTime)));
            map.put("list", list2);
            map.put("adminId", adminId);
            map.put("type", type);
            map.put("limitDay", template.getLimitDay());
            hongbaoMapper.saveHongbaoList(map);
        }
    }

    @Override
    public Hongbao addToUser2(Double amount, Integer userId, String descript, Integer source, Integer type, Integer application,
                              Integer limitDays, Integer limitAmount, String limitMonths) {
        Hongbao hb = new Hongbao();
        Date now = new Date();
        hb.setSendTime(now);
        hb.setAmount(amount);
        hb.setType(type);
        hb.setExpireTime(DateFormatTools.jumpOneDay(now, 3));
        hb.setUserId(userId);
        hb.setLimitAmount(0);
        hb.setDescript(descript);
        hb.setSource(source);
        hb.setLimitAmount(limitAmount);
        //hb.setApplication(application);
        HongbaoTemplate hongbaoTemplate = new HongbaoTemplate();
        hongbaoTemplate.setLimitAmount(limitAmount);
        hongbaoTemplate.setName(descript);
//        if (limitMonths == null) {
//            hongbaoTemplate.setMonthType("30");
//        } else {
//            hongbaoTemplate.setMonthType(limitMonths);
//        }
        hongbaoTemplate.setAmount(amount);
        hongbaoTemplate.setType(2);
        hongbaoTemplate.setStatus(1);
        hongbaoTemplate.setEffectiveDays(limitDays);
        hongbaoTemplateMapper.insertSelective(hongbaoTemplate);
        hb.setTemplateId(hongbaoTemplate.getId());
        int ret = hongbaoMapper.insertSelective(hb);
        if (ret == 1) {
            // 发送消息
            String title = "恭喜您获得" + amount + "元投资红包奖励";
            messageService.save(title, descript, userId);
        }
        return hb;
    }

    @Override
    public Hongbao addToUser3(Double amount, Integer userId, String descript, Integer source, Integer type, Integer application,
                              Integer limitDays, Integer limitAmount, String limitMonths, Integer temType, Integer status, Integer efectiveDays) {
        Hongbao hb = new Hongbao();
        Calendar c = Calendar.getInstance();
        hb.setSendTime(c.getTime());
        hb.setAmount(amount);
        hb.setType(type);
        if (efectiveDays == null) {
            c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        } else {
            c.add(Calendar.DATE, efectiveDays);
        }
        hb.setExpireTime(c.getTime());
        hb.setUserId(userId);
        hb.setDescript(descript);
        hb.setSource(source);
        hb.setLimitAmount(limitAmount);
        //hb.setApplication(application);
        HongbaoTemplate hongbaoTemplate = new HongbaoTemplate();
        hongbaoTemplate.setLimitAmount(limitAmount);
        hongbaoTemplate.setName(descript);
//        if (limitMonths == null) {
//            hongbaoTemplate.setMonthType("30");
//        } else {
//            hongbaoTemplate.setMonthType(limitMonths);
//        }
        hongbaoTemplate.setAmount(amount);
        hongbaoTemplate.setType(temType);
        hongbaoTemplate.setStatus(status);
        hongbaoTemplate.setEffectiveDays(limitDays);
        hongbaoTemplateMapper.insertSelective(hongbaoTemplate);
        hb.setTemplateId(hongbaoTemplate.getId());
        hongbaoMapper.insert(hb);
        return hb;
    }


    @Override
    public Hongbao addToUser4(Double amount, Integer userId, String descript, Integer source, Integer type, Integer application,
                              Integer limitDays, Integer limitAmount, String limitMonths, Integer temType, Integer status) {
        Hongbao hb = new Hongbao();
        Calendar c = Calendar.getInstance();
        hb.setSendTime(c.getTime());
        hb.setAmount(amount);
        hb.setType(type);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        hb.setExpireTime(c.getTime());
        hb.setUserId(userId);
        hb.setDescript(descript);
        hb.setSource(source);
        hb.setLimitAmount(limitAmount);
        //hb.setApplication(application);
        HongbaoTemplate hongbaoTemplate = new HongbaoTemplate();
        hongbaoTemplate.setLimitAmount(limitAmount);
        hongbaoTemplate.setName(descript);
//        if (limitMonths == null) {
//            hongbaoTemplate.setMonthType("30");
//        } else {
//            hongbaoTemplate.setMonthType(limitMonths);
//        }

        hongbaoMapper.insert(hb);
        return hb;
    }


    public boolean findHongbao(Integer userId, Double amount, String descript) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("amount", amount);
        map.put("descript", descript);
        List list = hongbaoMapper.findHongbao(map);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void saveToUserCashHongbao(List<Hongbao> list2, String descript, Integer days, Integer adminId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Date date = new Date();
        map.put("list", list2);
        map.put("limitDay", days);
        map.put("descript", descript);
        map.put("sendTime", date);
        map.put("expireTime", DateFormatTools.jumpOneDay(date, days));
        map.put("adminId", adminId);
        hongbaoMapper.saveToUserCashHongbao(map);
    }

    public List<Map<String, Object>> unUserSum(String keyword, Integer application, Integer type, Date startSendTime, Date endSendTime, Date startUseTime, Date endUseTime, Integer adminId, Integer departmentId) {
        Map<String, Object> map = new HashMap<String, Object>(16);
        map.put("startSendTime", DateUtil.getMinInDay(startSendTime));
        map.put("endSendTime", DateUtil.getMaxInDay(endSendTime));
        map.put("startUseTime", DateUtil.getMinInDay(startUseTime));
        map.put("endUseTime", DateUtil.getMaxInDay(endUseTime));
        map.put("keyword", keyword);
        map.put("application", application);
        map.put("type", type);
        map.put("adminId", adminId);
        map.put("departmentId", departmentId);
        return hongbaoMapper.unUserSum(map);
    }


    @Override
    public Map<String, Object> getHongbaodAmountByMonth(Date date) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", date);
        if (hongbaoMapper.getHongbaodAmountByMonth(map) != null && hongbaoMapper.getHongbaodAmountByMonth(map).size() > 0) {
            return hongbaoMapper.getHongbaodAmountByMonth(map).get(0);
        }
        return null;
    }

    @Override
    public List<Hongbao> queryByRedeemId(Integer redeemId) {
    	List<Hongbao> hbs = new ArrayList<>();
    	if(null != redeemId){
    		hbs = hongbaoMapper.queryByRedeemId(redeemId);
    	}
    	return hbs;
    }


    @Override
    public List<Hongbao> queryHongbaoUseInfoByDate(Date date, Date endDate) {

        HongbaoExample example = new HongbaoExample();
        example.createCriteria().andUseTimeGreaterThanOrEqualTo(date).andUseTimeLessThanOrEqualTo(endDate);
        return hongbaoMapper.selectByExample(example);
    }

    @Override
    public int saveInvestHongBao(Double amount, Integer userId, String descript, Integer source, Integer type, Integer application,
                                 Integer limitDays, Integer limitAmount, String limitMonths, Integer expireDays) {

        int ret = 0;
        try {
            Hongbao hb = new Hongbao();
            Date now = new Date();
            hb.setSendTime(now);
            hb.setAmount(amount);
            hb.setType(type);
            expireDays = expireDays == null ? 3 : expireDays;
            hb.setExpireTime(DateFormatTools.jumpOneDay(now, expireDays));
            hb.setUserId(userId);
            hb.setLimitAmount(0);
            hb.setDescript(descript);
            hb.setSource(source);
            hb.setLimitAmount(limitAmount);
           // hb.setApplication(application);
            HongbaoTemplate hongbaoTemplate = new HongbaoTemplate();
            hongbaoTemplate.setLimitAmount(limitAmount);
            hongbaoTemplate.setName(descript);
//            if (limitMonths == null) {
//                hongbaoTemplate.setMonthType("30");
//            } else {
//                hongbaoTemplate.setMonthType(limitMonths);
//            }
            hongbaoTemplate.setAmount(amount);
            hongbaoTemplate.setType(2);
            hongbaoTemplate.setStatus(1);
            hongbaoTemplate.setEffectiveDays(limitDays);
            hongbaoTemplateMapper.insertSelective(hongbaoTemplate);
            hb.setTemplateId(hongbaoTemplate.getId());
            if (hongbaoMapper.insertSelective(hb) <= 0) {
                logger.info("发送投资红包失败, userId : " + userId);
            }
            ret = 1;
        } catch (Exception e) {
            logger.error("发送投资红包异常, userId : " + userId, e);
        }

        return ret;
    }


    public int saveInvestHongBaoNew(Double amount, Integer userId, String descript, Integer source, Integer type, Integer application,
                                    Integer limitDays, Integer limitAmount, String limitMonths, Integer expireDays, String couponId) {
        int ret = 0;
        try {
            Hongbao hb = new Hongbao();
            Date now = new Date();
            hb.setCouponId(couponId);
            hb.setSendTime(now);
            hb.setAmount(amount);
            hb.setType(type);
            hb.setExpireTime(DateUtil.getDateAfter(now, expireDays-1));
            hb.setUserId(userId);
            hb.setLimitAmount(limitAmount);
            hb.setDescript(descript);
            hb.setSource(source);
            //hb.setLimitDay(limitDays);
            //hb.setApplication(application);
            HongbaoTemplate hongbaoTemplate = new HongbaoTemplate();
            hongbaoTemplate.setLimitAmount(limitAmount);
            hongbaoTemplate.setName(descript);
//            if (limitMonths == null) {
//                hongbaoTemplate.setMonthType("30");
//            } else {
//                hongbaoTemplate.setMonthType(limitMonths);
//            }
            hongbaoTemplate.setAmount(amount);
            hongbaoTemplate.setType(2);
            hongbaoTemplate.setStatus(1);
            hongbaoTemplate.setEffectiveDays(limitDays);
            hongbaoTemplateMapper.insertSelective(hongbaoTemplate);
            hb.setTemplateId(hongbaoTemplate.getId());
            if (hongbaoMapper.insertSelective(hb) <= 0) {
                logger.info("发送投资红包失败, userId : " + userId);
            }
            ret = 1;
        } catch (Exception e) {
            logger.error("发送投资红包异常, userId : " + userId, e);
        }

        return ret;
    }

    @Override
    public int sendHongBaoToUser(Double amount, Integer userId,
                                 String descript, String title, Integer source, Integer type,
                                 Integer application, Integer limitDays, Integer efectiveDays) {

        Hongbao hb = new Hongbao();
        Calendar c = Calendar.getInstance();
        hb.setSendTime(c.getTime());
        hb.setAmount(amount);
        hb.setType(type);
        if (efectiveDays == null) {
            c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        } else {
            c.add(Calendar.DATE, efectiveDays);
        }
        hb.setExpireTime(c.getTime());
        hb.setUserId(userId);
        hb.setLimitAmount(0);
        hb.setDescript(descript);
        hb.setSource(source);
//        if (limitDays != null) {
//            hb.setLimitDay(limitDays);
//        }
//        hb.setApplication(application);
        return hongbaoMapper.insertSelective(hb);
    }

    public Map<String, Object> selectBestHongbao(Integer userId, Double amount, Integer projectId) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> params = new HashMap<String, Object>();
        Project project = projectMapper.selectByPrimaryKey(projectId);
        params.put("amount", amount);
        params.put("userId", userId);
        params.put("limitDays", project.getLimitDays());
        List<Hongbao> list = hongbaoMapper.selectBestHongbao(params);
        int hCount = 0;
        Hongbao hongbao = new Hongbao();
        if (list != null && list.size() > 0) {
            hongbao = hongbaoMapper.selectBestHongbao(params).get(0);
            hCount = 1;
        }
        Integer count = getHongbaoInverstmentCount(userId,2,2,null,null);
        //有红包没有可以使用的红包，提示暂无可用
        if (count > 0 && hCount == 0) {
            hCount = 2;
        }
        result.put("bestHongbao", hongbao);
        result.put("hCount", hCount + "");   //0没有红包    1有红包    2暂无可用
        result.put("hPiece", list == null ? 0 : list.size());  //加息券数量
        return result;
    }


	@Override
	public List<Map<String,Object>> queryExpireHongbaoAfterSomeDay(
			Map<String, Object> map) {

		return hongbaoMapper.queryExpireHongbaoAfterSomeDay(map);
	}

    @Override
    public List<Map<String, Object>> getMyInviteRecord(Integer userId, String descript, Integer page,Integer limit) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("descript", descript);

        if(null!=page){
        	params.put("start", (page-1)*limit);
        }

        params.put("limit", limit);
        return hongbaoMapper.getMyInviteRecord(params);
    }

    public List<Map<String, Object>> getInviteRecord(Integer userId, String descript, Integer limit) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("descript", descript);
        params.put("limit", limit);
        return hongbaoMapper.getInviteRecord(params);
    }

    public int getHongbaoCountByUser(Integer userId, List source, Integer type, String descript) {
        HongbaoExample example = new HongbaoExample();
        HongbaoExample.Criteria c = example.createCriteria();
        c.andUserIdEqualTo(userId);
        if (source != null) {
            c.andSourceIn(source);
        }
        if (type != null) {
            c.andTypeEqualTo(type);
        }
        if (descript != null) {
            c.andDescriptEqualTo(descript);
        }
        return (int) hongbaoMapper.countByExample(example);
    }


	@Override
	public List<Hongbao> queryUserCanUseHongbao(Integer userId, Integer source) {

		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("source", source);

		return hongbaoMapper.queryUserCanUseHongbao(params);
	}

	@Override
	public List<Hongbao> listHongbaoByUserId(Integer userId) {
		HongbaoExample hongbaoExample = new HongbaoExample();
		hongbaoExample.createCriteria().andUserIdEqualTo(userId).andExpireTimeLessThan(new Date());
		return hongbaoMapper.selectByExample(hongbaoExample);
	}


    // 邀请新用户获得的现金红包总额
    public Double getInvitRecordTotalAmount(Integer userId, Boolean isCash) {
        String types;
        if (isCash == null) {
            types = null;
        } else {
            types = getHongbaoTypes(isCash);
        }
        Map<String, Object> params = new HashMap<String, Object>(8);
        params.put("userId", userId);
        params.put("types", types); //  红包类型1现金2投资红包3优惠券
        params.put("triggerTypes", this.getActivityTriggerTypes()); //  1.注册 2.投资 3.消费
        return this.getMapper().queryInviteTotalAmount(params);
    }

    private String getHongbaoTypes(boolean isCash) {
        List<Integer> typeList = new ArrayList<>();
        if (isCash) {
            typeList.add(HongbaoTypeEnum.CASH.getCode());
        } else {
            typeList.add(HongbaoTypeEnum.INVESTMENT.getCode());
            typeList.add(HongbaoTypeEnum.COUPONS.getCode());
        }
        return StringUtils.join(typeList, ",");
    }

    private String getActivityTriggerTypes (){
        List<Integer> list = new ArrayList<>();
        list.add(ActivityTriggerTypeEnum.REGISTER.getFeatureType());
        list.add(ActivityTriggerTypeEnum.INVESTMENT.getFeatureType());
        list.add(ActivityTriggerTypeEnum.BUYGOODS.getFeatureType());
        return StringUtils.join(list, ",");
    }
}



