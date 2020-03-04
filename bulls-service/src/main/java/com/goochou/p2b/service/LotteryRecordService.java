package com.goochou.p2b.service;

import com.goochou.p2b.model.Activity;
import com.goochou.p2b.model.LotteryAddress;
import com.goochou.p2b.model.LotteryRecord;
import com.goochou.p2b.model.User;
import com.goochou.p2b.service.exceptions.LockFailureException;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LotteryRecordService {
    public Integer saveLottery();

    /**
     * 新增用户中奖记录  并返回  id  所谓的订单号
     *
     * @param record
     * @return
     * @author 王信
     * @Create Date: 2015年12月4日下午5:41:23
     */
    public Integer saveLottery(LotteryRecord record);

    /**
     * 根据主键查找奖品ID
     *
     * @param id = orderId
     * @return
     * @author 刘源
     * @date 2015年12月7日
     * @parameter
     */
    public LotteryRecord queryByPrimaryKey(Integer id);

    /**
     * 更新奖品数据
     *
     * @param record
     * @param address
     * @return
     * @author 刘源
     * @date 2015年12月7日
     * @parameter
     */
    public void updateLotteryRecord(LotteryRecord record, LotteryAddress address);

    public void updateLotteryRecord(LotteryRecord lottery);

    /**
     * 根据ID查询奖品详情
     *
     * @param orderId
     * @return
     * @author 刘源
     * @date 2015年12月7日
     * @parameter
     */
    public Map<String, Object> queryDetailByPrimaryKey(Integer orderId);

    /**
     * 根据用户ID查询奖品列表
     *
     * @param userId
     * @return
     * @author
     * @date 2015年12月7日
     * @parameter
     */
    public List<Map<String, Object>> queryListByUserId(Integer userId);

    /**
     * 查询所有获奖记录
     *
     * @param status
     * @param date
     * @param orderId
     * @param giftId
     * @param account
     * @param start
     * @param limit   @date 2015年12月7日
     * @return
     * @author
     * @parameter
     */
    public List<Map<String, Object>> queryAll(String status, Date date, Integer orderId, Integer activityId, Integer giftId, String account, Integer start, Integer limit);

    /**
     * 获奖信息总数
     *
     * @param status
     * @param date
     * @param orderId
     * @param account
     * @param giftId
     * @return
     * @author
     * @date 2015年12月7日
     * @parameter
     */
    public Integer queryCount(String status, Date date, Integer orderId, Integer activityId, Integer giftId, String account);

    /**
     * 查询最新的二十条数据修改成字符串给前台
     *
     * @param
     * @return
     * @author 王信
     * @Create Date: 2015年12月11日下午1:42:38
     */
    public List<Map<String, Object>> selectString(String order, Integer start, Integer limit, Integer activityId);

    /**
     * 开始抽奖
     *
     * @param user
     * @return
     * @throws ParseException
     * @author 王信
     * @Create Date: 2015年12月11日下午5:20:25
     */
    public Map<String, Object> saveLotteryRecord(User user) throws Exception;

    public Map<String, Object> saveLotteryRecord(Activity activity, Integer userId, Integer giftId);

    /****
     * 666 0元购
     * @param userId
     * @param giftId
     * @return
     */
    public Map<String, Object> saveLotteryRecord666(Integer userId, Integer giftId, String phone) throws LockFailureException;

    /**
     * 查询未注册用户最后一次中奖信息
     *
     * @param phone
     * @return
     * @author 王信
     * @Create Date: 2015年12月14日下午7:46:58
     */
    public Map<String, Object> selectLotteryRecordByPhone(String phone);

    /**
     * 导出详情
     *
     * @param status
     * @param date
     * @param orderId
     * @param activityId
     * @param giftId
     * @param account    @author 刘源
     * @return
     * @date 2015年12月23日
     * @parameter
     */
    public List<Map<String, Object>> queryExportDetails(String status, Date date, Integer orderId, Integer activityId, Integer giftId, String account);


    /**
     * 参加领奖活动
     *
     * @param token
     * @author 刘源
     * @date 2016/5/6
     */
    public Map<String, Object> saveAwardGift(String token, Integer gitfId, String ip) throws ParseException;

    /**
     * 投资赢壕礼,全民抢黄金
     *
     * @param token
     * @param giftId
     * @param ip
     * @return
     * @author 刘源
     * @date 2016/6/27
     */
    Map<String, Object> saveAwadSoldActivity(String token, Integer giftId, String ip) throws LockFailureException;

    /**
     * 查询用户领奖记录
     *
     * @param map
     * @author 刘源
     * @date 2016/6/27
     */
    Map<String, Object> queryAwardRecord(Map<String, Object> map);


    /**
     * 查询用户领奖记录
     *
     * @param map
     * @author 刘源
     * @date
     */
    Map<String, Object> queryAwardRecordFilter(Map<String, Object> map);

    /**
     * 查询用户领奖记录
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> queryAllAwardRecord(Map<String, Object> map);

    /**
     * @Description(描述):根据用户ID和活动ID 查询用户的记录
     * @author 王信
     * @date 2016/8/30
     * @params 用户ID   活动ID
     **/
    LotteryRecord queryUserIdByActivityId(Integer userId, Integer activityId);


    /**
     * @Description(描述):用户留言方法，
     * @author 王信
     * @date 2016/8/30
     * @params 用户ID   活动ID
     **/
    void updateUserIdByActivityId(Integer userId, Integer activityId, String remark, String ip, Integer giftId);

    /**
     * @Description(描述):
     * @author 王信
     * @date 2016/9/26
     * @params
     **/
    void deleteAllRecord(Integer userId, Integer activityid);

    Map<String, Object> saveLotteryRecordRegist(Activity activity, Integer id, Integer type);

    Map<String, Object> saveAllLotteryRecord(Integer userId, Activity activity);

    Map<String, Object> saveLotteryRecordJuly(Integer userId, Activity activity, String phone) throws LockFailureException;

    Map<String, Object> saveLotteryRecordQixi(Integer id, Activity activity, String phone) throws LockFailureException;

    Map<String, Object> saveLotteryRecordOctober1(Integer id, String phone, Integer activityId) throws LockFailureException;

    Map<String, Object> saveLotteryRecordOctober2(Integer id, String phone, Activity activity, Integer giftId) throws LockFailureException;

    Map<String, Object> saveLotteryDecember(Integer userId, Activity activity, String phone) throws LockFailureException;

    int findCountByUserIdAndActivityId(int userId,List<Integer>activityId);
}
