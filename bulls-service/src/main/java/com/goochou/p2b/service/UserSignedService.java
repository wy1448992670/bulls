package com.goochou.p2b.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserSignedService {

    /**
     * 用户签到
     *
     * @param userId
     * @return
     */
    public int saveSigned(Integer userId);

    /**
     * 新版签到功能
     *
     * @param userId
     * @return
     */
    public int saveSignedNew(Integer userId);

    public int saveSignedAgain(Integer userId);

    /**
     * @return
     * @Title: UserSignedService.java
     * @Package com.goochou.p2b.service
     * @Description(描述):查询昨日和今日所有的签到用户以及昨日的amount 平均收益
     * @author 王信
     * @date 2016年3月15日 下午7:05:32
     * @version V1.0
     */
    public Map<String, Object> selectSignedAgainYesterday(Integer userId);

    /**
     * @param userId
     * @param date
     * @return
     * @Title: UserSignedService.java
     * @Package com.goochou.p2b.service
     * @Description(描述):两个重载方法 都是验证用户是否用签到
     * @author 王信
     * @date 2016年3月16日 下午1:39:59
     * @version V1.0
     */
    public boolean checkSigned(Integer userId, Date date);

    /**
     * 验证是否第一次签到
     *
     * @param userId
     * @return
     */
    public boolean checkIfFirst(Integer userId);

    /**
     * 查询当月签到次数
     *
     * @return
     */
    public Integer getMonthSignedCount(Integer userId);

    /**
     * 查询每月签到情况
     *
     * @param userId
     * @param date
     * @return
     */
    public List<String> getMonthSigned(Integer userId, Date date);

    /**
     * 查询每日签到人数
     *
     * @return
     */
    public Integer getDailySignedCount(Date date);

    /**
     * @return
     * @Title: UserSignedService.java
     * @Package com.goochou.p2b.service
     * @Description(描述):查询用户累计签到和累计收益 3.0.0版本
     * @author 王信
     * @date 2016年3月16日 下午2:43:06
     * @version V1.0
     */
    public Map<String, Object> selectSignedAwardDetail(Integer userId);

    /**
     * @param userId
     * @param start
     * @param limit
     * @return
     * @Title: UserSignedService.java
     * @Package com.goochou.p2b.service
     * @Description(描述):查询用户签到的所有记录
     * @author 王信
     * @date 2016年3月16日 下午3:03:41
     * @version V1.0
     */
    public List<Map<String, Object>> selectSignedAwardDetailList(Integer userId, Integer start, Integer limit);


    /**
     * @Description(描述):查看所有用户的记录
     * @author 王信
     * @param adminId 
     * @date 2016/5/11
     * @params
     **/
    public List<Map<String, Object>> selectAllUserSigned(Integer page, Integer limit, String keyword, Date startDate, Date endDate, Integer adminId);

    public Integer selectAllUserSignedCount(String keyword, Date startDate, Date endDate, Integer adminId);

    Integer getCountByTime(Integer userId, Date startTime, Date endTime);

    /**
     * @Description: 查询用户所有的签到次数  (可能存在没有派发的次数)
     * @date 2016/10/24
     * @author 王信
     */
    Integer getAllSignedCount(Integer userId);
    
    /**
     * 用户签到
     *
     * @param userId
     * @return
     */
    public int saveSignedLog(Integer userId);
    
}
