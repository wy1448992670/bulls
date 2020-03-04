package com.goochou.p2b.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.ParseException;
import org.springframework.web.multipart.MultipartFile;

import com.goochou.p2b.model.BaseResult;
import com.goochou.p2b.model.CouponTemplate;
import com.goochou.p2b.model.RateCoupon;
import com.goochou.p2b.model.RateCouponAudit;

public interface RateCouponService {

    public int useCoupon(Integer id, Integer userId);

    /**
     * @param type   0日加息券1月加息券
     * @param source 来源来源0签到1全民理财师2活动赠送3抗战活动派送4分享二维码送5首次投资赠送6邀请好友赠送 ,9是圣诞节抽奖,999系统赠送,10新手任务,11首次绑卡
     * @param rate   利率
     * @param userId 用户ID
     * @param time   有效期加息时间0一个月1三天2七天3一天
     */
    public RateCoupon save(Integer type, Integer source, Double rate, Integer userId, Integer time);

    /**
     * @param type
     * @param source 来源： 首次绑定银行卡赠送
     * @param rate   利率
     * @param userId 用户ID
     * @param time   到期时间
     */
    public RateCoupon save(Integer type, Integer source, Double rate, Integer userId, Date time, Integer days);

    public RateCoupon save(Integer type, String descript, Double rate, Integer userId, Date time, Integer days, Integer adminId,String rateCouponType);

    /**
     * 发送加息券
     *
     * @param type     2新活期加息券3定期加息券
     * @param source   来源0签到1全民理财师2活动赠送3抗战活 动派送4分享二维码送5首次投资赠送6邀 请好友赠送7 11月福利赠送 71 12月福利赠 送11首次绑卡赠送 13两周年赠送  999系统 赠送
     * @param descript 加息券描述
     * @param rate     加息券利率
     * @param userId   用户ID
     * @param time     到期时间 null代表1个月
     * @param days     加息的天数
     * @return
     */
    public RateCoupon save2(Integer type, Integer source, String descript, Double rate, Integer userId, Date time, Integer days);

    List<RateCoupon> rateCouponList(Integer id);

    public List<RateCoupon> rateCouponNoUseList(Integer id, Integer page);

    public List<RateCoupon> rateCouponUsingList(Integer id, Integer page);

    public List<RateCoupon> rateCouponExpiredList(Integer id, Integer page);

    public List<RateCoupon> rateCouponUsingAndNoUse(Integer id, Integer start, Integer limit);

    public int countRateCouponUsingAndNoUse(Integer id);

    public int countRateCouponNoUse(Integer id);

    public int countRateCouponUsed(Integer id);

    public int countRateCouponExpired(Integer id);

    public RateCoupon getNoUseMonthRateCoupon(Integer id);

    public RateCoupon saveRateCoupon(Date date, Double rate, Integer id);

    /**
     * 查询用户当月获取的全民理财师奖励的加息券
     *
     * @param userId
     * @return
     */
    RateCoupon getMonthSend(Integer userId, Integer source);

    /**
     * 根据加息券来源判断当前是否已经获取过加息券了
     *
     * @param userId
     * @param source
     * @return true已经拿到过，false未领取
     */
    boolean getCountBySource(Integer userId, Integer source);

    int getDailyCountBySource(Integer userId, Integer source);

    public int countUsingRateCoupon(Integer id);

    public List<Map<String, Object>> query(String keyword, Integer source, Integer type, Date startTime, Date endTime, Integer start, Integer limit, Integer days, Integer adminId);

    public Integer queryCount(String keyword, Integer source, Integer type, Date startTime, Date endTime, Integer days, Integer adminId);

    public Integer querySum(String keyword, Integer source, Integer type, Date startTime, Date endTime, Integer days, Integer adminId);

    /**
     * 时间任务方法，为当天生日用户，发放1%日加息券，1个月过期
     *
     * @return 返回
     * @throws URISyntaxException
     * @throws IOException
     * @throws ParseException
     * @author 刘源 2015-11-9
     */
    public List<String> addBirthdayRateCoupon() throws URISyntaxException, ParseException, IOException;

    /**
     * 检测是否存在发放生日加息券
     *
     * @return
     * @author 刘源
     * @date 2015年12月1日
     */
    public Integer checkBirthdayRateCoupons();

    public List<RateCoupon> rateCoupon(Integer id);

    public void addRateCouponAudit(RateCouponAudit rateCouponAudit);

    public RateCouponAudit getByInvestmentId(Integer investmentId);

    public List<RateCouponAudit> getByStatus(Integer status);

    public RateCoupon get(Integer id);

    public RateCouponAudit getAudit(Integer auditId);


    /**
     * @Description: 新版本加息券列表  5.2.0    0使用中 1未使用 2已使用 3已失效
     * @date 2017/3/25
     * @author 王信
     */
    List<RateCoupon> rateCouponNewList(Integer userId, Integer start, Integer limit);

    Integer rateCouponNewCount(Integer userId);

    /**
     * @Description: 根据加息券的投资ID 查询   加息券内容.
     * @date 2017/5/21
     * @author 王信
     */
    RateCoupon selectByIdRateCoupon(Integer couponId);

    /**
     * @Description: 根据加息券类型, 和用户ID 查询用户的 可用  加息券列表
     * @date 2017/3/26
     * @author 王信
     */
    List<RateCoupon> selectByUserIdRateCouponList(Integer type, Integer userId, Integer start, Integer limit);

    Integer selectByUserIdRateCouponCount(Integer type, Integer userId);

    List<RateCoupon> getByUserIdAndSourceAndType(Integer userId, Integer source, Integer type);

    /**
     * @Description: 更新加息券使用情况.
     * @date 2017/3/27
     * @author 王信
     */
    void updateRateCoupon(RateCoupon rateCoupon);


    List<RateCoupon> selectRateCouponList(Integer userId, Integer status, Integer start, Integer limit);

    int selectRateCouponCount(Integer userId, Integer status);

    /**
     * @Description: 定期加息券定时器
     * @date 2017/3/30
     * @author 王信
     */
    void saveCouponInterestTask() throws Exception;

    /**
     * @Description:根据项目ID 查询该项目所用到的所有加息券.
     * @date 2017/3/31
     * @author 王信
     */
    List<RateCoupon> selectUseRateCouponList(Integer projectId);

    /**
     * @Description: 加息券 修改器
     * @date 2017/3/31
     * @author 王信
     */
    void saveCouponStatusTask() throws Exception;

    void saveBatchCoupon(String couponTitle, MultipartFile file, String descript, Integer type, String userId, String template, String title, Integer adminId,String rateCouponType) throws Exception;

    List<RateCoupon> queryRateCouponUseInfoByDate(Date date, Date endDate);

    Integer saveRateCouponToUser(Integer type, String descript, Double rate, Integer userId, Integer expireDays, Integer days, Integer adminId, String rateCouponType);

    /**
     * 发送加息券（new 包含加息券规则）
     *
     * @param userId
     * @param type
     * @param source
     * @param title
     * @param descript
     * @param rate
     * @param expireDays
     * @param couponId
     * @param minDays
     * @param minAmount
     */
    Integer saveNew(Integer userId, Integer type, Integer source, String title, String descript, Double rate, Integer expireDays, String couponId, Integer minDays, Double minAmount);

    /**
	 * 即将到期的加息券
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryExpireRateCouponAfterSomeDay(Map<String,Object> map);
	
	BaseResult couponTemplateList(Integer type, Integer stockBalance,
    		Integer minDays, Long minAmount, String keyword, Integer start, Integer limit);
	
	boolean addCouponTemplate(CouponTemplate couponTemplate);
	
	CouponTemplate getCouponTemplate(String templateId);
	
	boolean updateCouponTemplate(CouponTemplate couponTemplate);
	
	boolean updateCouponTemplate(String templateId);

}
