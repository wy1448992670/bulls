package com.goochou.p2b.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.dao.UserInviteDetailMapper;
import com.goochou.p2b.dao.UserInviteMapper;
import com.goochou.p2b.model.CommisionRewardRule;
import com.goochou.p2b.model.Investment;
import com.goochou.p2b.model.Product;
import com.goochou.p2b.model.Project;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.UserInvite;
import com.goochou.p2b.model.UserInviteDetail;
import com.goochou.p2b.model.UserInviteExample;
import com.goochou.p2b.model.vo.InvitedUserDetailVO;
import com.goochou.p2b.service.HongbaoService;
import com.goochou.p2b.service.MessageService;
import com.goochou.p2b.service.RateCouponService;
import com.goochou.p2b.service.UserInviteService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.service.memcached.MemcachedManager;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.DateFormatTools;
import com.goochou.p2b.utils.DateUtil;

@Service
public class UserInviteServiceImpl implements UserInviteService {
	
	private final static Logger logger = Logger.getLogger(UserInviteServiceImpl.class);
    @Resource
    private UserInviteMapper userInviteMapper;
    @Resource
    private HongbaoService hongbaoService;
    @Resource
    private UserService userService;
    @Resource
    private RateCouponService rateCouponService;
    @Resource
    private UserInviteDetailMapper userInviteDetailMapper;
    @Resource
    private MemcachedManager memcachedManager;
    @Resource
    private MessageService messageService;

    @Override
    public List<Map<String, Object>> getInviteList(Integer userId) {
        return userInviteMapper.getInviteList(userId);
    }

    @Override
    public Integer getInviteListCount(Integer userId) {
        return userInviteMapper.getInviteListCount(userId);
    }

    @Override
    public void save(Integer userId, Integer inviteUserId) {
        UserInvite ui = new UserInvite();
        ui.setUserId(userId);
        ui.setInviteUserId(inviteUserId);
        userInviteMapper.insertSelective(ui);
    }

    @Override
    public void updateStatus(Integer userId, Integer inviteUserId, Investment investment, Project project) {
        UserInviteExample example = new UserInviteExample();
        example.createCriteria().andUserIdEqualTo(userId).andInviteUserIdEqualTo(inviteUserId);

        List<UserInvite> list = userInviteMapper.selectByExample(example);
        if (null != list && !list.isEmpty()) {
            UserInvite ui = list.get(0);
            if(!ui.getStatus().equals(1)){
                ui.setStatus(1);
                userInviteMapper.updateByPrimaryKeySelective(ui);
            }

            //插入邀请投资详细记录,
            User inviteUser=userService.get(inviteUserId);
            Long days= DateFormatTools.dayToDaySubtract(inviteUser.getCreateDate(),new Date());
            if(days<=365){
            	
            	//判断该投资的标为30天以上定期,才能给与提成奖励
            	if(project.getProjectType()!=0||project.getParentId()!=null||project.getLimitDays()<30){
            		return;
            	}
            	
                UserInviteDetail detail = new UserInviteDetail();
                detail.setStatus(0);
                detail.setInvestmentAmount(investment.getAmount());
//                detail.setInvestmentTime(investment.getTime());
                detail.setInvestmentId(investment.getId());
                detail.setUiId(ui.getId());
                detail.setProjectId(project.getId());
                detail.setLimitDays(project.getLimitDays());
                
                // 年化额度
                Double annualAnvestment = investment.getAmount() * project.getLimitDays() / 365;
                
                Double awardRate = getAwardRate(annualAnvestment);
                if(awardRate!=null){
                	Double award = BigDecimalUtil.fixed2(investment.getAmount() * project.getLimitDays() / 365 * awardRate);//奖励金额
                	// 如果计算后的奖励金额为0,则不发放奖励
                	if(award>0){
                		detail.setAwardAmount(award);
                        userInviteDetailMapper.insert(detail);
                	}
                }else{
                	logger.info("=====================获取佣金(提成)梯度奖励规则为NULL,不发放奖励========================");
                }
            }
        }
    }

    @Override
    public void updateStatus(Integer userId, Integer inviteUserId, Investment investment, Product product) {
        UserInviteExample example = new UserInviteExample();
        example.createCriteria().andUserIdEqualTo(userId).andInviteUserIdEqualTo(inviteUserId);

        List<UserInvite> list = userInviteMapper.selectByExample(example);
        if (null != list && !list.isEmpty()) {
            UserInvite ui = list.get(0);
            if (!ui.getStatus().equals(1)) {
                ui.setStatus(1);
                userInviteMapper.updateByPrimaryKeySelective(ui);
            }

            //插入邀请投资详细记录,
            User inviteUser = userService.get(inviteUserId);
            Long days = DateFormatTools.dayToDaySubtract(inviteUser.getCreateDate(), new Date());
            if (days <= 180) {
                UserInviteDetail detail = new UserInviteDetail();
                detail.setStatus(0);
                detail.setInvestmentAmount(investment.getAmount());
//                detail.setInvestmentTime(investment.getTime());
                detail.setInvestmentId(investment.getId());
                detail.setUiId(ui.getId());
                detail.setProjectId(product.getId());
                detail.setLimitDays(product.getOutDays());
                Double award = BigDecimalUtil.fixed2(investment.getAmount() * product.getOutDays() / 365 * 0.01);//奖励金额
                detail.setAwardAmount(award);
                userInviteDetailMapper.insert(detail);
            }
        }
    }

    @Override
    public void updateInvestUserStatus(Integer userId) {
        // 查询当前投资用户的邀请人是否满足要求
        User u = userService.get(userId);
        UserInviteExample example2 = new UserInviteExample();
        example2.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(1);
        int count = userInviteMapper.countByExample(example2);
        // 活期投资大于等于2000并且邀请投资人大于等于6人
        //Double investAmount = u.getAssets().getHuoInvestmentAmount();
        //if (investAmount >= 2000 && count >= 6) {
            // 查询当月全民理财师奖励是否已经发放
            if (rateCouponService.getMonthSend(userId, 1) != null) {
                logger.info("=================用户" + u.getTrueName() + "当月全民理财师奖励已经发放，无需再次发送===============");
            } else {
                //logger.info("=================用户" + u.getTrueName() + "当月投资金额【" + investAmount + "】,邀请人数【" + count + "】人，发放奖励===============");
                rateCouponService.save(1, 1, 0.01, userId, 0);
            }
        //}

    }

    @Override
    public Integer getCountByUser(Integer userId) {
        UserInviteExample example = new UserInviteExample();
        example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(1);
        return userInviteMapper.countByExample(example);
    }

    @Override
    public List<Map<String, Object>> inviteReport(String keyword, Integer start, Integer limit, Integer adminId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", keyword);
        map.put("start", start);
        map.put("limit", limit);
        map.put("adminId", adminId);
        return userInviteMapper.inviteReport(map);
    }

    @Override
    public Integer inviteReportCount(String keyword, Integer adminId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", keyword);
        map.put("adminId", adminId);
        return userInviteMapper.inviteReportCount(map);
    }

    @Override
    public List<Map<String, Object>> getInviteDetail(Integer userId) {
        return userInviteMapper.getInviteDetail(userId);
    }
    @Override
    public Integer getInviteDetailCount(Integer userId) {
        return userInviteMapper.getInviteDetailCount(userId);
    }

    @Override
    public UserInvite get(Integer userId, Integer inviteUserId) {
        UserInviteExample example = new UserInviteExample();
        example.createCriteria().andUserIdEqualTo(userId).andInviteUserIdEqualTo(inviteUserId);
        List<UserInvite> list = userInviteMapper.selectByExample(example);
        if (null != list && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Integer getInviteCount(Integer userId) {
        return userInviteMapper.getInviteCount(userId);
    }

    @Override
    public List<Map<String, Object>> userInviteDetail(String keywords) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keywords", keywords);
        return userInviteMapper.userInviteDetail(map);
    }

    @Override
    public Double getMyCommission(Integer userId, Integer status, Date month) {
        return userInviteMapper.getMyCommission(userId, status, month);
    }

    @Override
    public Integer getInviteCountDetail(Integer userId, Integer status) {
        UserInviteExample example = new UserInviteExample();
        UserInviteExample.Criteria c = example.createCriteria();
        c.andUserIdEqualTo(userId);
        if (status != null) {
            c.andStatusEqualTo(status);
        }
        return userInviteMapper.countByExample(example);
    }

    @Override
    public List<Map<String, Object>> listDetail(Integer userId, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("start", start);
        map.put("limit", limit);
        return userInviteMapper.listDetail(map);
    }

    @Override
    public Integer listDetailCount(Integer userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        return userInviteMapper.listDetailCount(userId);
    }

    @Override
    public void saveInviteDetail(UserInviteDetail detail) {
        userInviteDetailMapper.insertSelective(detail);
    }

    @Override
    public List<UserInviteDetail> getUnsettlement() {
        return userInviteMapper.getUnsettlement();
    }

    @Override
    public List<Map<String, Object>> getUnsettlementGroupByUser() {
        return userInviteMapper.getUnsettlementGroupByUser();
    }



    @Override
    public List<Map<String, Object>> selectInvestmentAward(String keyword,Date startTime, Date endTime , Integer page, Integer limit) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("start",page);
        map.put("limit",limit);
        map.put("keyword",keyword);
        return userInviteMapper.selectInvestmentAward(map);
    }
    @Override
    public Integer countInvestmentAward(String keyword, Date startTime, Date endTime){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("keyword",keyword);
        return userInviteMapper.countInvestmentAward(map);
    }

    @Override
    public List<Map<String, Object>> selectInviteAmountList(Integer start, Integer limit) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("start",start);
        map.put("limit",limit);
        return userInviteMapper.selectInviteAmountList(map);
    }

	@Override
	public Map<String, Object> selectInvitedCountTopOne() {
		
		Map<String,Object> map = new HashMap<String,Object>();
		return userInviteMapper.selectInvitedCountTopOne(map);
	}

	@Override
	public Map<String, Object> selectInvitedAmountTopOne() {
		
		Map<String,Object> map = new HashMap<String,Object>();
		return userInviteMapper.selectInvitedAmountTopOne(map);
	}

	@Override
	public List<InvitedUserDetailVO> getInvitedUserDetail(Integer userId, Date beginTime, Date endTime) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId",userId);
		map.put("beginTime", DateUtil.dateFullTimeFormat.format(beginTime));
		map.put("endTime", DateUtil.dateFullTimeFormat.format(endTime));
		return userInviteMapper.getInvitedUserDetail(map);
	}
	
	/***
	 * 根据单次年化投资额，查询出提成比例规则
	 * @param annualAnvestment
	 * @return
	 */
	@Override
	public Double getAwardRate(Double annualAnvestment){
		Double awardRate = null;
		// 先设置奖励的序列号为null;
    	Integer awardIndex = null;
    	try{
    		//json转换为list
        	List<CommisionRewardRule> awardList = new ArrayList<CommisionRewardRule>();
    		// 获取缓存中的设定奖励利率
        	String awardListJsonStr = memcachedManager.getCacheKeyValue(Constants.COMMISION_REWARD_RULE);
        	
        	logger.info("=====================获取佣金(提成)梯度奖励规则=="+awardListJsonStr);
        	
        	//对获取的缓存字符串去除空白空格干扰，进行转换
        	awardList = JSONObject.parseArray(awardListJsonStr.replace(" ", ""), CommisionRewardRule.class);  
        	
        	// 对列表进行排序，按照投资金额从小到大排序
        	Collections.sort(awardList, new Comparator<CommisionRewardRule>() {
                @Override  
                public int compare(CommisionRewardRule rule1, CommisionRewardRule rule2) {  
                	if(rule1.getAmount()>rule2.getAmount()){
                		return 1;
                	}else if(rule1.getAmount().equals(rule2.getAmount())){
                		return 0;
                	}else{
                		return -1;
                	}
                }  
            }); 
        	
        	//进行规则筛选
        	if(awardList!=null&&awardList.size()>0){
        		for(int i=0;i<awardList.size();i++){
        			if(annualAnvestment>=awardList.get(i).getAmount()){
        				awardIndex = i;
        			}else{
        				break;
        			}
        		}
        	}
        	
        	//获取符合条件的奖励规则
        	if(awardIndex!=null){
        		CommisionRewardRule rule = awardList.get(awardIndex);
            	awardRate = rule.getRate();
        	}
        	
        	
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.info("=====================获取佣金(提成)梯度奖励规则失败，出现异常========================"+e);
    	}
		
		return awardRate;
	}
	
	@Override
	public Double getAwardRate(Double annualAnvestment, String key){
		Double awardRate = null;
		// 先设置奖励的序列号为null;
    	Integer awardIndex = null;
    	try{
    		//json转换为list
        	List<CommisionRewardRule> awardList = new ArrayList<CommisionRewardRule>();
    		// 获取缓存中的设定奖励利率
        	String awardListJsonStr = memcachedManager.getCacheKeyValue(key);
        	
        	logger.info("=====================获取梯度奖励规则=="+awardListJsonStr);
        	
        	//对获取的缓存字符串去除空白空格干扰，进行转换
        	awardList = JSONObject.parseArray(awardListJsonStr.replace(" ", ""), CommisionRewardRule.class);  
        	
        	// 对列表进行排序，按照投资金额从小到大排序
        	Collections.sort(awardList, new Comparator<CommisionRewardRule>() {
                @Override  
                public int compare(CommisionRewardRule rule1, CommisionRewardRule rule2) {  
                	if(rule1.getAmount()>rule2.getAmount()){
                		return 1;
                	}else if(rule1.getAmount().equals(rule2.getAmount())){
                		return 0;
                	}else{
                		return -1;
                	}
                }  
            }); 
        	
        	//进行规则筛选
        	if(awardList!=null&&awardList.size()>0){
        		for(int i=0;i<awardList.size();i++){
        			if(annualAnvestment>=awardList.get(i).getAmount()){
        				awardIndex = i;
        			}else{
        				break;
        			}
        		}
        	}
        	
        	//获取符合条件的奖励规则
        	if(awardIndex!=null){
        		CommisionRewardRule rule = awardList.get(awardIndex);
            	awardRate = rule.getRate();
        	}
        	
        	
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.info("=====================获取梯度奖励规则失败，出现异常========================"+e);
    	}
		
		return awardRate;
	}

	@Override
	public Map<String, Object> getUserInviteSumAndMoney(Integer userId) {
		//  查询邀请活动的人数数据
    	Map<String, Object> m = null;
    	m = userInviteMapper.getInviteUserCount(userId);
        m.put("inviteAmount", userInviteMapper.getInviteUserAmount(userId));
        return m;
	}

}
