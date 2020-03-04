package com.goochou.p2b.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.goochou.p2b.constant.ActivityTriggerTypeEnum;
import com.goochou.p2b.model.Activity;
import com.goochou.p2b.model.ActivityDetail;
import com.goochou.p2b.model.ActivityQualification;
import com.goochou.p2b.model.AssistanceActivity;
import com.goochou.p2b.model.BaseResult;
import com.goochou.p2b.model.Investment;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.vo.ActivityInvestAndLotteryVO;

public interface ActivityService {

    public List<Activity> list(Integer start, Integer limit, Integer status, Integer type);

    /**
     * 查询活动列表
     * @author sxy
     * @param start
     * @param limit
     * @param status
     * @return
     */
    public List<Map<String, Object>> lists(Integer start, Integer limit, Integer status);

    public Activity getByName(String name);

    public Activity getByNameAndStatus(String name, Integer status);

    /**
     * 活动是否过期
     *
     * @param name
     * @return
     */
    public boolean checkExpired(String name);

    /**
     * @param status 是否启用    1新推出0已结束
     * @return
     * @Title: ActivityService.java
     * @Package com.goochou.p2b.service
     * @Description(描述):
     * @author 王信
     * @date 2016年6月4日 下午7:12:47
     * @version V1.0
     */
    public Long queryCount(Integer status);

    public void save(Activity ac);

    public Activity selectNewActivity();

    public Map<String, Object> selectActivityById(Integer id);

    public void update(Activity ac);

    /**
     * @param keyword
     * @return
     * @Description: 加息封侯 破10+0.5之战
     * @author 王信
     * @date 2016年1月5日 上午11:32:06
     */
    public List<Map<String, Object>> rateBreakList(String keyword, Integer start, Integer limit);

    /**
     * @param keyword
     * @return
     * @Description: 加息封侯 破10+0.5之战
     * @author 王信
     * @date 2016年1月5日 上午11:32:06
     */
    public Integer rateBreakListCount(String keyword);

    /**
     * 根据活动名称查询活动实体
     *
     * @param name
     * @return
     * @author 王信
     * @Create Date: 2015年12月15日下午3:44:38
     */
    public Activity queryByName(String name);

    /**
     * @Description(描述):04-22上线活动 定期加息活动查询list
     * @author 王信
     * @date 2016/4/18
     * @params
     **/
    public List<Map<String, Object>> selectRegularActivityList(Integer userId);

    /**
     * 查询所有活动ID对应的title
     *
     * @author 刘源
     * @date 2016/4/21
     */
    public List<Activity> selectAll();

    /**
     * 更新用户领奖次数
     *
     * @param user
     * @param investment
     * @author 刘源
     * @date 2016/6/28
     */
    void saveAwardGoldCount(User user, Investment investment);

    /**
     * @Description(描述):2016-08-03 冰爽八月
     * @author 王信
     * @date 2016/7/29
     * @params
     **/
    List<Map<String, Object>> selectInvestmentActivity(Date startTime, Date endTime);

    /**
     * 查询投资额和年化额 筛选定期和安鑫赚用户
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> selectInvestmentActivityFilter(Date startTime, Date endTime);

    /**
     * @Description(描述):尊贵之约，如“7”而至！投资即送iPhone7！
     * @author 王信
     * @date 2016/8/12
     * @params
     **/
    Double selectAppleActivity(Integer userId, Integer limitDays, Date startDate, Date endDate);

    Double selectAppleActivity2(Integer userId, Integer limitDays, Date startDate, Date endDate);


    /**
     * @Description(描述):根据活动ID查询活动详细
     * @author 王信
     * @date 2016/8/29
     * @params
     **/
    Activity queryActivityById(Integer activityId);

    /**
     * @Description(描述):
     * @author 王信
     * @date 2016/9/1
     * @params
     **/
    List<Map<String, Object>> selectAppleActivityUserList(String keyword, String keyword1, Date keyword2, Integer activityId, Date startTime, Date endTime, Integer limitDays, Integer page, Integer limit);

    Integer selectAppleActivityUserCount(String keyword, String keyword1, Date keyword2, Integer activityId, Date startTime, Date endTime, Integer limitDays);

    /**
     * @Description(描述):查询活动期间投资情况
     * @author zxx
     * @date 2016/9/8
     * @params
     **/
    public List<Map<String, Object>> selectWeekInvestmentActivity(Date startTime, Date endTime);


    /**
     * @Description(描述):查询活动期间投资奖励情况
     * @author zxx
     * @date 2016/9/9
     * @params
     **/
    public List<Map<String, Object>> selectWeekInvestmentAward(String keyword, String keyword1, Date keyword2, Integer activityId, Date startTime, Date endTime, Integer limitDays, Integer page, Integer limit);


    /**
     * @Description(描述):查询活动期间投资奖励情况
     * @author zxx
     * @date 2016/9/9
     * @params
     **/
    public Integer countWeekInvestmentAward(String keyword, String keyword1, Date keyword2, Integer activityId, Date startTime, Date endTime, Integer limitDays);

    /**
     * @Description(描述):查询活动期间投资情况
     * @author zxx
     * @date 2016/9/8
     * @params
     **/
    public List<Map<String, Object>> selectWeekInvestmentActivity(Date startTime, Date endTime, Integer userid);

    /**
     * @Description(描述):查询3125活动期间投资情况
     * @author zxx
     * @date 2016/9/10
     * @params
     **/
    public List<Map<String, Object>> selectHongbaoInvestmentAward(String keyword, String keyword1, Date keyword2, Integer activityId, Date startTime, Date endTime, Integer limitDays, Integer page, Integer limit);

    public Integer countHongbaoInvestmentAward(String keyword, String keyword1, Date keyword2, Integer activityId, Date startTime, Date endTime, Integer limitDays);


    /**
     * @Description(描述):京东卡活动
     * @author zxx
     * @date 2016/9/18
     * @params
     **/
    public List<Map<String, Object>> selectJingdongInvestmentAward(String keyword, String keyword1, Date keyword2, Integer activityId, Date startTime, Date endTime, Integer limitDays, Integer page, Integer limit);

    public Integer countJingdongInvestmentAward(String keyword, String keyword1, Date keyword2, Integer activityId, Date startTime, Date endTime, Integer limitDays);

    /**
     * @Description(描述): 终结者，金霸主  活动跑马灯
     * @author 王信
     * @date 2016/9/21
     * @params
     **/
    public List<Map<String, Object>> selectProjectTerminator(Map<String, Object> map);

    /**
     * @Description(描述):抢标活动后台列表
     * @author 王信
     * @date 2016/9/21
     * @params
     **/
    List<Map<String, Object>> projectAwardList(String keyword, Date startTime, Date endTime, Integer start, Integer limit);

    Integer projectAwardListCount(String keyword, Date startTime, Date endTime);

    /**
     * @Description(描述):十月 黄金活动
     * @author 王信
     * @date 2016/9/22
     * @params
     **/
    List<Map<String, Object>> selectInvestmentAwardActivity(Date startTime, Date endTime);

    /**
     * @Description(描述):根据活动名称，奖品名称查询，是否有该奖品 有则返回1 没有则返回0
     * @author 王信
     * @date 2016/9/22
     * @params
     **/
    Integer queryActivityByGift(String activityName, Integer giftId);

 /*   *//**
     * @Description(描述):国庆黄金活动 后台统计查询
     * @author 王信
     * @date 2016/9/26
     * @params
     **//*
    List<Map<String, Object>> selectNationalGoldList(String keyword, Integer activityId, Date startTime, Date endTime, Integer start, Integer limit);

    Integer selectNationalGoldCount(String keyword, Integer activityId, Date startTime, Date endTime)*/;

    /**
     * @Description: 双重壕礼    后台统计查询
     * @date 2016/10/21
     * @author 王信
     */
    List<Map<String, Object>> selectGetDoubleGiftList(String keyword, Integer activityId, Date startTime, Date endTime, Integer start, Integer limit);

    Integer selectGetDoubleGiftCount(String keyword, Integer activityId, Date startTime, Date endTime);

    /**
     * @Description: 统计活动期间总投资人数  ,和总投资金额
     * @date 2016/10/21
     * @author 王信
     */
    Map<String, Object> selectGetDoubleGiftMap(Date startTime, Date endTime);

    /**
     * @Description(描述):2016-08-03 万圣大狂欢 聚财又送礼
     * @author zxx
     * @date 2016/10/20
     * @params
     **/
    List<Map<String, Object>> selecthalloweenActivity(Date startTime, Date endTime);


    /**
     * @Description(描述):2016-08-03 双十一活动用户投资情况
     * @author zxx
     * @date 2016/10/27
     * @params
     **/
    public List<Map<String, Object>> selectSinglesDayActivity(Integer userId, Date startTime, Date endTime);


    /**
     * @Description(描述):2016-08-03 查询用户首次投资投资
     * @author zxx
     * @date 2016/10/27
     * @params
     **/
    public List<Map<String, Object>> beforeSinglesDayActivity(Integer userId, Date startTime, Date endTime);

    /**
     * @Description(描述):双十一后台统计查询
     * @author 赵星星
     * @date 2016/10/27
     * @params
     **/
    List<Map<String, Object>> listSelectSinglesDay(String keyword, Integer activityId, Date startTime, Date endTime, Integer start, Integer limit);

    Integer countSelectSinglesDay(String keyword, Integer activityId, Date startTime, Date endTime);

    /**
     * @Description(描述):感恩节后台统计查询
     * @author 赵星星
     * @date 2016/10/27
     * @params
     **/

    public List<Map<String, Object>> thanksgivingDay(String keyword, Date startTime, Date endTime, Integer start, Integer limit);

    /**
     * @Description: 2017-02-14  情人节活动
     * @date 2017/1/16
     * @author 王信
     */
    Map<String, Object> selectValentinesDayActivity(Integer userId, Date startTime, Date endTime);

    /**
     * @Description: 查询活动期间用户的
     * @date 2017/1/16
     * @author 王信
     */
    Double selectActivityTimeInvestmentAmount(Integer userId, Date startTime, Date endTime);

    /**
     * @Description: 查询20170214情人节活动  后台投资统计
     * @date 2017/1/18
     * @author 王信
     */
    Map<String, Object> selectInvestmentTotal(Date startTime, Date endTime);

    /**
     * @Description: 20170214情人节活动  后台投资统计
     * @date 2017/1/18
     * @author 王信
     */
    List<Map<String, Object>> selectValentinesDayActivityList(String keyword, Date startTime, Date endTime, Integer start, Integer limit);

    Integer selectValentinesDayActivityCount(String keyword, Date startTime, Date endTime);

    Map<String, Object> selectInvest(String keyword, Date startTime, Date endTime);

    Map<String, Object> selectExpMobey(String keyword, Date startTime, Date endTime, Integer activityId);

    Map<String, Object> selectHongbao(String keyword, Date startTime, Date endTime);

    List<Map<String, Object>> selectActivityInvestment(Integer userId, Date startTime, Date endTime);

    /**
     * @Description: 二周年新客
     * @date 2017/3/30
     * @author zxx
     */
    public List<Map<String, Object>> newAnniversary(String keyword, Date startTime, Date endTime, Date acstartTime, Date acendTime, Integer start, Integer limit);

    Integer newAnniversaryCount(String keyword, Date startTime, Date endTime, Date acstartTime, Date acendTime);

    /**
     * @Description: 二周年老客
     * @date 2017/3/30
     * @author zxx
     */
    public List<Map<String, Object>> twoYearsThanksActivity(String keyword, Date startTime, Date endTime, Date acstartTime, Date acendTime, Integer start, Integer limit);

    public Integer twoYearsThanksActivityCount(String keyword, Date startTime, Date endTime, Date acstartTime, Date acendTime);


    /**
     * 根据活动名称查询
     *
     * @param activityName
     * @param keyword
     * @param activityId
     * @param startTime
     * @param endTime
     * @param start
     * @param limit
     * @return
     */
    List<Map<String, Object>> selectActivityListByName(String activityName, String keyword, Integer activityId, Date startTime, Date endTime, Integer start, Integer limit);

    Integer selectActivityCountByName(String activityName, String keyword, Integer activityId, Date startTime, Date endTime);

    /**
     * 年化投资排名
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param name      活动名称
     * @return
     */
    Map<String, Object> selectRank(String name, Date startTime, Date endTime, Integer limitDays);

    /**
     * 中奖信息
     *
     * @param map
     * @return
     */
    Map<String, Object> getLotteryInfo(Map<String, Object> map);

    /**
     * 查询抽奖次数和抽奖记录
     *
     * @param userId
     * @param activityId
     * @param startTime
     * @param endTime
     * @param limitDays
     * @return
     */
    ActivityInvestAndLotteryVO getLotteryInfo(Integer userId, Integer activityId, Date startTime, Date endTime, Integer limitDays);

    /****
     * 十一月收益双11活动详情
     * @param activity
     * @param user
     * @return
     */
    Map<String, Object> getNoverberMap(Activity activity, User user);

    Map<String, Object> selectInvestmentAmount2(Integer userId, Date startDate, Date endDate, Integer limitDays);

    /**
     * 抽取奖品返回对应信息
     * @param user 用户对象
     * @param activityId 活动id
     * @return map
     */
    Map<String,Object> doLotteryDraw(User user,int activityId);
    
    /**
     * 根据条件查询用户在2017-01-01到2017-12-31的投资金额
     * @param userId 查询条件
     * @return map
     */
    Map<String,Object> getAvgAmount(int userId);

    /**
     * 根据条件查询跨年抽奖资格信息
     * @param userId 用户id
     * @param activityId 活动id
     * @return 跨年活动抽奖资格信息
     */
    ActivityQualification findByUserIdAndActivityId(int userId,int activityId);

    /**
     * 添加跨年抽奖资格信息
     * @param userId 添加的信息
     * @param activityId 添加的信息
     * @return int
     */
    int insertActivityQualification(int userId,int activityId);

    /**
     * 根据条件修改跨年抽奖机会次数
     * @param activityQualification 修改条件
     * @return int
     */
    int updateByUserIdAndActivityIdAndVersion(ActivityQualification activityQualification);

    /**
     * 查询跨年活动最近30条记录
     * @return list
     */
    List<Map<String,Object>> findThirtyRecord(List<Integer> activityIdList);

    /**
     * 根据用户id查询抽奖次数
     * @param userId 用户id
     * @return int
     */
    int findCountByUserIdAndActivityId(int userId,List<Integer> activityIdList);

    /**
     * 根据用户查找跨年活动抽奖记录
     * @param userId 用户id
     * @return list
     */
    List<Map<String,Object>> findHappyRecord(int userId,List<Integer> activityIdList);

    /**
     * 活动名称找活动id
     * @param nameList 活动名称集合
     * @return 活动集合
     */
    List<Activity> findActivityId(List nameList);
    
    
    /**
     * 获取红包雨活动, 邀请人可以获得的红包金额及佣金金额
     * @param userId
     * @return
     */
    Double queryHongBaoRainInviteUserCashGift(Integer userId, Date beginTime, Date endTime);

    /**
     * 跨年活动查询红包雨中奖条数
     * @param activityId 活动id
     * @return int
     */
   int findLotteryRecordCount(Integer activityId);

    /**
     * 查询跨年活动红包雨记录
     * @param userId 用户id
     * @return list
     */
    List<Map<String, Object>> findRedRainRecord(Integer userId,Integer activityId);

    /***
     * 新邀请活动，获取我的收益信息列表
     * @param searchMap
     * @return
     */
    List<Map<String, Object>> getMyInviteInComePageList(Map<String,Object> searchMap);


    Map<String, Object> getMyInvite(Integer userId);
    /**
     * 查询用户助力列表
     * @param id
     * @return
     */
	public List<AssistanceActivity> assistanceList(Integer userId);
     /**
      * 好友助力
      * @param activityId
      * @param userId
      * @param phone
      * @return
      */
	public BaseResult doAssistance(Integer activityId, Integer userId, String phone);
    /**
     * 查询当前手机号是否已经助力
     * @param phone
     * @return
     */
	public boolean getAssistanceCheck(String phone,Integer activityId);

	/**
	 * 查询,并封装所有正在执行的活动详情
	 * @author 张琼麒
	 * @version 创建时间：2019年6月19日 上午9:30:36
	 * @return
	 */
	public Map<Integer,List<ActivityDetail>> getActionActivityDetailMap();
	
	/**
	 * 刷新缓存
	 * @author 张琼麒
	 * @version 创建时间：2019年6月19日 上午9:30:36
	 * @return
	 * @throws Exception 
	 */
	public Map<Integer,List<ActivityDetail>> doFlushCacheActionActivityDetailMap() throws Exception;

	/**
	 * 从缓存中查询活动详情,key:activityDetail.getTriggerType()|ActivityTriggerTypeEnum.getFeatureType()
	 * @author 张琼麒
	 * @version 创建时间：2019年6月19日 下午1:27:04
	 * @return
	 * @throws Exception 
	 */
	public Map<Integer, List<ActivityDetail>> doGetCacheActionActivityDetailMap() throws Exception;
	
	/**
	 * 从缓存中查询对应触发条件的活动详情
	 * @author 张琼麒
	 * @version 创建时间：2019年6月19日 下午1:27:04
	 * @return
	 * @throws Exception 
	 */
	public List<ActivityDetail> doGetCacheActionActivityDetailList(ActivityTriggerTypeEnum atte) throws Exception;

	/**
	 * 应用ActivityTriggerTypeEnum.INVESTMENT
	 * 并发送红包
	 * @author 张琼麒
	 * @version 创建时间：2019年6月20日 下午1:04:00
	 * @param investment
	 * @throws Exception 
	 */
	public void doActivityInvestment(Investment investment) throws Exception;
	
	/**
	 * 应用ActivityTriggerTypeEnum.REGISTER
	 * 并发送红包
	 * @author 张琼麒
	 * @version 创建时间：2019年6月20日 下午1:04:00
	 * @param investment
	 * @throws Exception 
	 */
	public void doActivityRegister(User user) throws Exception;
	
	/**
	 * 应用ActivityTriggerTypeEnum.LOGIN
	 * 并发送红包
	 * @author 张琼麒
	 * @version 创建时间：2019年7月09日 下午1:04:00
	 * @param investment
	 * @throws Exception 
	 */
	void doActivityLogin(User user) throws Exception;
	
	List<User> getFirstInvestmentWaitInviter() throws Exception;
	
	List<User> getFirstInvestmentWaitInvitee() throws Exception;

	/**
	 * @desc 应用ActivityTriggerTypeEnum.INVEST_BUY_BACK 回购发送红包
	 * @author wangyun
	 * @param investment
	 * @throws Exception
	 */
	void doActivityBuyBack(Investment investment) throws Exception;
}


