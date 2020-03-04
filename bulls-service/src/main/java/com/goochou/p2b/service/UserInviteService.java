package com.goochou.p2b.service;

import com.goochou.p2b.model.*;
import com.goochou.p2b.model.vo.InvitedUserDetailVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserInviteService {
    /**
     * 查询用户的邀请list
     *
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getInviteList(Integer userId);

    public Integer getInviteListCount(Integer userId);

    public void save(Integer userId, Integer inviteUserId);

    /**
     * 当被推荐的用户首次投资时修改状态0->1，并且给邀请人送红包
     *
     * @param userId
     * @param inviteUserId
     */
    public void updateStatus(Integer userId, Integer inviteUserId, Investment investment, Project project);

    public void updateStatus(Integer userId, Integer inviteUserId, Investment investment, Product product);

    public void updateInvestUserStatus(Integer userId);

    public Integer getCountByUser(Integer userId);

    public List<Map<String, Object>> inviteReport(String keyword, Integer start, Integer limit, Integer adminId);

    public Integer inviteReportCount(String keyword, Integer adminId);

    public List<Map<String, Object>> getInviteDetail(Integer userId);
    public Integer getInviteDetailCount(Integer userId);

    /**
     * 查询邀请信息
     *
     * @param userId       邀请人ID
     * @param inviteUserId 被邀请人ID
     * @return
     */
    public UserInvite get(Integer userId, Integer inviteUserId);

    /**
     * 查询邀请的用户在投金额>=200的人数总数
     *
     * @param userId
     * @return
     */
    public Integer getInviteCount(Integer userId);

    /**
     * @param keywords
     * @return
     * @Title: UserInviteService.java
     * @Package com.goochou.p2b.service
     * @Description:
     * @author 王信
     * @date 2016年1月20日 下午2:08:08
     */
    public List<Map<String, Object>> userInviteDetail(String keywords);

    /**
     * 统计总的佣金
     * month:佣金结算月
     */
    public Double getMyCommission(Integer userId, Integer status, Date month);

    /**
     * 统计用户邀请人数
     */
    public Integer getInviteCountDetail(Integer userId, Integer status);

    /**
     * App统计用户邀请详情信息 待结算 已结算 总额 按照用户分组
     */
    public List<Map<String, Object>> listDetail(Integer userId, Integer start, Integer limit);

    /**
     * App统计用户邀请详情信息 待结算 已结算 总额 按照用户分组
     */
    public Integer listDetailCount(Integer userId);

    public void saveInviteDetail(UserInviteDetail detail);

    /**
     * 每月20号结算佣金
     */
    public List<UserInviteDetail> getUnsettlement();

    public List<Map<String, Object>> getUnsettlementGroupByUser();


    public List<Map<String, Object>> selectInvestmentAward(String keyword,Date startTime, Date endTime , Integer page, Integer limit) ;

    public Integer countInvestmentAward(String keyword, Date startTime, Date endTime);
    /**
     * @Description: 邀请人获得奖励排行榜
     * @date  2016/10/8
     * @author 王信
     */
    List<Map<String,Object>> selectInviteAmountList(Integer start,Integer limit);
    
    /**
     * 查询邀请的好友注册最多的用户
     * @return
     */
    Map<String, Object> selectInvitedCountTopOne();
    
    /**
     * 查询邀请的好友获得奖励最多
     * @return
     */
    Map<String, Object> selectInvitedAmountTopOne();
    
    /**
     * 查询用户时间段内邀请的用户信息
     * @param userId
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<InvitedUserDetailVO> getInvitedUserDetail(Integer userId, Date beginTime, Date endTime);


    public Map<String,Object> getUserInviteSumAndMoney(Integer userId);
    
    public Double getAwardRate(Double annualAnvestment);
    
    public Double getAwardRate(Double annualAnvestment, String key);
}
