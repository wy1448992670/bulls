package com.goochou.p2b.service;


import com.goochou.p2b.model.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 抽奖服务类
 *
 * @author 王信
 * @Create Date: 2015年12月3日上午11:55:50
 */
public interface LotteryCountService {
    /**
     * 根据用户id查询用户的抽奖次数
     *
     * @param id
     * @return
     * @throws ParseException
     * @author 王信
     * @Create Date: 2015年12月3日下午4:25:15
     */
    public int saveOrUpdateUserLottery(Integer userId, String phone) throws ParseException;

    /**
     * 查询抽奖记录表条数 传id代表查询是否是新用户，如果什么都不传 或者传0 那么就是查所有用户    lottery_count 表的查询
     *
     * @return
     * @author 刘源
     * @date 2015年12月3日
     */

    public Integer queryCount(Integer userId);

    /**
     * 查询lottery_record表中的中奖记录 用户判断用户是否是第一次抽奖
     *
     * @param id
     * @return
     * @author 王信
     * @Create Date: 2015年12月7日下午2:27:05
     */
    public Integer queryRecordCount(Integer id, Integer activityId);

    /**
     * 返回奖品的集合  返回奖品的集合  index输入1不显示奖品抽奖概率   输入0  显示中奖概率
     *
     * @return
     * @author 王信
     * @Create Date: 2015年12月3日下午7:47:09
     */
    public List<LotteryGift> listLotteryGift(String ids);

    /**
     * 初始化用户抽奖记录
     *
     * @author 刘源
     * @date 2015年12月3日
     */
    public void addLotteryCountInit();

    /**
     * 根据日期查询数据条数
     *
     * @param now
     * @return
     * @author 刘源
     * @date 2015年12月3日
     */
    public Integer queryCountByDate(Date now);

    /**
     * 系统更新用户抽奖次数
     *
     * @author 刘源
     * @date 2015年12月3日
     */
    public void updateLotteryCount();

    /**
     * 新增用户记录   抽奖记录表
     *
     * @param record
     * @return
     * @author 王信
     * @Create Date: 2015年12月4日上午9:42:00
     */
    public int insertSelective(LotteryCount record);

    /**
     * 根据主键查询实体
     *
     * @param id
     * @return
     */
    public LotteryCount queryByPrimaryKey(Integer userId);

    /**
     * 根据ID将用户资金信息和抽奖信息新增到库中，并返回对象
     *
     * @param id
     * @return
     */
    public void insertFromAssetsById(Integer userId);

    /**
     * 抽奖记录表的更新
     *
     * @param reord
     * @return
     * @author 王信
     * @Create Date: 2015年12月4日上午9:59:14
     */
    public int updateByPrimaryKeySelective(LotteryCount record);

    /**
     * 用户抽奖
     *
     * @param user
     * @return
     */
    public String setLotteryCount(User user);

    /**
     * 保存用户抽奖的记录
     *
     * @param record
     * @return
     * @author 王信
     * @Create Date: 2015年12月4日下午3:02:26
     */
    public int saveLotteryRecord(LotteryRecord record);

    /**
     * 抽奖成功,减少抽奖次数
     *
     * @author 王信
     * @Create Date: 2015年12月7日上午9:53:00
     */
    public int updateCount(Integer userId);

    /**
     * 此方法用户查询用户是否中了该实物
     *
     * @param type   奖品类型
     * @param giftId 奖品id
     * @param userId 用户id
     * @return
     * @author 王信
     * @Create Date: 2015年12月10日下午4:19:48
     */
    public int onlyKind(Integer type, Integer giftId, Integer userId, Integer activityId);

    /**
     * 抽奖完成之后修改剩余 数量
     *
     * @param id
     * @author 王信
     * @Create Date: 2015年12月10日下午7:22:15
     */
    public int updateLotteryNum(LotteryGift gift);

    /**
     * 查询某一天开始到今日的充值-提前差额   累积投资
     *
     * @param activity
     * @return
     * @author 王信
     * @Create Date: 2015年12月12日下午12:00:26
     */
    public double selectRechargeWithdraw(Activity activity, Integer userId);

    /**
     * 根据userid查询抽奖次数
     *
     * @param userId
     * @return
     * @author 王信
     * @Create Date: 2015年12月14日下午12:22:15
     */
    public LotteryCount queryCountByUserId(Integer userId);

    /**
     * 根据电话号码查询抽奖次数
     *
     * @param phone
     * @return
     * @author 王信
     * @Create Date: 2015年12月14日下午12:23:09
     */
    public LotteryCount queryCountByPhone(String phone);

    public LotteryCount insertSysCount(String phone);

    /**
     * 未注册用户,根据电话插入内容,存入系统赠送的一次抽奖
     *
     * @param ct
     * @param user
     * @author 王信
     * @Create Date: 2015年12月14日下午2:33:20
     */
    public void insertSysCount(LotteryCount ct, User user);

    /**
     * 更新系统赠送机会
     *
     * @param ct
     * @author 王信
     * @Create Date: 2015年12月14日下午2:49:21
     */
    public void updateLotteryCount(LotteryCount ct);

    /**
     * 根据电话查询抽奖次数
     *
     * @param phone
     * @return
     * @author 王信
     * @Create Date: 2015年12月14日下午3:15:03
     */
    public LotteryCount selectLotteryCountByPhone(String phone);

    /**
     * 查询未注册用户是否第一次中奖
     *
     * @param phone
     * @return
     * @author 王信
     * @Create Date: 2015年12月14日下午3:34:04
     */
    public int queryRecordCountByPhone(String phone);

    /**
     * 根据奖品的id查询奖品的详细信息
     *
     * @param id
     * @return
     * @author 王信
     * @Create Date: 2015年12月10日下午7:26:18
     */
    public LotteryGift selectLotteryGift(Integer id);


    /**
     * 后台使用   用来查询单个用户的次数
     *
     * @param phone
     * @return
     * @author 王信
     * @Create Date: 2015年12月24日下午4:40:28
     */
    public Map<String, Object> selectChrismasLotteryCount(String phone);

    /**
     * 获取抽奖次数
     *
     * @param ct
     * @param user
     * @author 刘源
     * @date 2016/4/19
     */
    public int doGetLotteryCount(LotteryCount ct, User user);

    /**
     * 保存抽奖次数
     *
     * @param record
     * @author 刘源
     * @date 2016/6/30
     */
    void save(LotteryCount record);

    /**
     * @Description(描述):根据用户的ID和活动ID 查询用户的活动次数
     * @author 王信
     * @date 2016/9/26
     * @params
     **/
    LotteryCount saveOrQueryUserAndActivityCount(Integer userId, Integer activityId);

    /**
     * @Description: 根据活动ID, 和用户ID 查询用户当前活动能够抽奖的次数
     * @date 2017/1/16
     * @author 王信
     */
    Integer saveOrselectActivityCount(Integer activityId, Integer userId);


    LotteryCount selectLotteryCounts(Integer activityId, Integer userId);

    /****
     *保存或者查询系统中奖次数
     * @param activityId
     * @param userId
     * @return
     */
    Integer saveOrselectActivitySysCount(Integer activityId, Integer userId);
}
