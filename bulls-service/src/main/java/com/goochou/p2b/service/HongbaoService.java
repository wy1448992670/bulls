package com.goochou.p2b.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.goochou.p2b.dao.HongbaoMapper;
import com.goochou.p2b.model.Hongbao;
import com.goochou.p2b.model.vo.HongbaoTemplateModel;

public interface HongbaoService {

    HongbaoMapper getMapper();

    public Hongbao get(Integer id);

    /**
     * 查询用户的未使用的红包/已使用的红包/已过期的红包个数
     *
     * @param userId
     * @return
     */
    public Map<String, Integer> getByUser(Integer userId);

    /**
     * 根据用户ID以及红包的使用情况查询
     *
     * @param userId
     * @param projectLimitDays 定期限制使用天数
     * @param type             0 未使用1已使用2已过期
     * @param hbType           0利息红包1现金红包
     * @param application      0定期1活期
     * @return
     */
    public List<Hongbao> getByUser(Integer userId, Integer projectLimitDays, Integer type, Integer hbType, Integer application, Integer start, Integer limit);

    public Integer getCountByUser(Integer userId, Integer type, Integer hbType, Integer source);


    /**
     * 根据红包来源判断是否已经领取红包
     *
     * @param userId
     * @param source
     * @return true已经拿到过，false未领取
     */
    boolean getCountBySource(Integer userId, Integer source);

    /**
     * 根据用户ID以查询APP利息红包使用
     *
     * @param userId
     * @param type        0未使用的1在使用的2已过期的
     * @param application 0定期1活期
     * @return
     */
    public List<Hongbao> getUserInterestHongbaoForApp(Integer userId, Integer type, Integer application, Integer start, Integer limit);

    /**
     * @param userId
     * @param type        0未使用的1在使用的2已过期的
     * @param application 0定期1活期
     * @return
     */
    public Integer getUserInterestHongbaoCountForApp(Integer userId, Integer type, Integer application);

    /**
     * @param amount      红包金额
     * @param userId      用户ID
     * @param descript    红包描述
     * @param source      0网页1APP2WAP1001首投奖励, 1002提成奖, 1003人脉奖
     * @param type        0利息1现金
     * @param application 0定期1活期
     * @return
     */
    public Hongbao addToUser(Double amount, Integer userId, String descript, Integer source, Integer type, Integer application, Integer limitDays);

    public Hongbao addToUser(Double amount, Integer userId, String descript, String title, Integer source, Integer type, Integer application, Integer limitDays);

    public Hongbao addToUser(Double amount, Integer userId, String descript, String title, Integer source, Integer type, Integer application, Integer limitDays, Integer efectiveDays);

    public Hongbao addToUser2(Double amount, Integer userId, String descript, Integer source,
                              Integer type, Integer application, Integer limitDays, Integer limitAmount, String limitMonths);

    public Hongbao addToUser3(Double amount, Integer userId, String descript, Integer source, Integer type, Integer application,
                              Integer limitDays, Integer limitAmount, String limitMonths, Integer temType, Integer status, Integer efectiveDays);

    public Map<String, Integer> useHongbao(Integer userId, Integer id) throws Exception;

    public Double getTodayHongbao(Integer userId);

    public List<Map<String, Object>> query(String keyword, Integer application, Integer type, Date startSendTime, Date endSendTime, Date startUseTime, Date endUseTime, Integer start, Integer limit, Integer adminId, Integer departmentId);

    public Integer queryCount(String keyword, Integer application, Integer type, Date startSendTime, Date endSendTime, Date startUseTime, Date endUseTime, Integer adminId, Integer departmentId);

    public List<Hongbao> selectEffective(Integer userId, Integer start, Integer limit);

    public Integer selectEffectiveCount(Integer userId);

    /**
     * @Description(描述):查询查询用户的单个红包信息
     * @author 王信
     * @date 2016/6/30
     * @params
     **/
    public Hongbao queryByIdOrUser(Integer userId, Integer Id);

    /**
     * @Description(描述):投资红包到期提醒
     * @author 王信
     * @date 2016/7/1
     * @params
     **/
    public void sendInvestmentHongbaoMessage() throws Exception;

    /**
     * @Description(描述):查询投资红包列表
     * @author 王信
     * @date 2016/7/4
     * @params
     **/
    public Integer getHongbaoInverstmentCount(Integer userId, Integer type, Integer status, Double limitAmount, Integer limitDay);

    /**
     * 获取投资红包,优惠券列表
     * @author sxy
     * @param userId
     * @param type 2投资红包 3优惠券
     * @param status
     * @param start
     * @param limit
     * @return
     */
    public List<Hongbao> getHongbaoInverstmentList(Integer userId, Integer type, Integer status, Integer start, Integer limit, Double limitAmount, Integer limitDay);

    List<Map<String, Object>> getInverstmentHongbaoList(Integer userId);


    int getInverstmentHongbaoCount(Integer userId);

    void saveInverstmentHongbao(Hongbao hongbao);

    public List<Map<String, Object>> webListInvestHb(Integer userId);

    /**
     * @Description(描述):红包使用详情列表
     * @author 王信
     * @date 2016/7/12
     * @params
     **/
    List<Map<String, Object>> selectUserUseList(String keyword, Integer type, Integer isUser, Date startTime, Date endTime, Integer redeemId, Integer start, Integer limit);

    Integer selectUserUseCount(String keyword, Integer type, Integer isUse, Date startTime, Date endTime, Integer redeemId);

    /**
     * @Description(描述):红包使用情况
     * @author 王信
     * @date 2016/7/13
     * @params
     **/
    List<Map<String, Object>> selectReportHongbaoUseList(Date startTime, Date endTime);

    /**
     * 根据用户ID批量派发投资红包
     * @author sxy
     * @param list
     * @param list2
     * @param type
     * @param descript
     * @param days
     * @param adminId
     * @throws Exception
     */
    public void saveToUserInvestmentHongbao(List<HongbaoTemplateModel> list, List<String> list2, Integer type, String descript, Integer days, Integer adminId) throws Exception;

    /**
     * @Description(描述):现金红包发放
     * @author zxx
     * @date 2016/10/19
     * @params
     **/
    public Hongbao addToUser4(Double amount, Integer userId, String descript, Integer source, Integer type, Integer application,
                              Integer limitDays, Integer limitAmount, String limitMonths, Integer temType, Integer status);

    public boolean findHongbao(Integer userId, Double amount, String descript);


    /**
     * 批量发送现金红包
     * @author sxy
     * @param list2
     * @param descript
     * @param days
     * @param adminId
     */
    public void saveToUserCashHongbao(List<Hongbao> list2, String descript, Integer days, Integer adminId);

    /**
     * @Description: 查询未使用和使用红包总额
     * @date 2016/11/30
     * @author zxx
     * @param adminId
     */
    public List<Map<String, Object>> unUserSum(String keyword, Integer application, Integer type, Date startSendTime, Date endSendTime, Date startUseTime, Date endUseTime, Integer adminId , Integer departmentId);


    /**
     * @Description: 按月查询投资和现金红包使用总额
     * @date 2016/11/30
     * @author zxx
     */
    public Map<String, Object> getHongbaodAmountByMonth(Date date);

    /***
     *根据红包兑换码查询
     * @param redeemId
     * @return
     */

    List<Hongbao> queryByRedeemId(@Param("redeemId") Integer redeemId);

    List<Hongbao> queryHongbaoUseInfoByDate(Date date, Date endDate);

    /**
     * 发投资红包
     *
     * @param amount
     * @param userId
     * @param descript
     * @param source
     * @param type
     * @param application
     * @param limitDays
     * @param limitAmount
     * @param limitMonths
     * @return
     */
    int saveInvestHongBao(Double amount, Integer userId, String descript, Integer source,
                          Integer type, Integer application, Integer limitDays, Integer limitAmount, String limitMonths, Integer expireDays);

    /**
     * 发现金红包
     *
     * @param amount
     * @param userId
     * @param descript
     * @param title
     * @param source
     * @param type
     * @param application
     * @param limitDays
     * @param efectiveDays
     * @return
     */
    int sendHongBaoToUser(Double amount, Integer userId, String descript, String title, Integer source, Integer type, Integer application, Integer limitDays, Integer efectiveDays);

    /**
     * 最优红包选择
     *
     * @param userId    id
     * @param amount    投资金额
     * @param projectId 投资项目id
     * @return
     */
    public Map<String, Object> selectBestHongbao(Integer userId, Double amount, Integer projectId);

    /**
	 * 即将到期的红包
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryExpireHongbaoAfterSomeDay(Map<String,Object> map);

    List<Map<String, Object>> getMyInviteRecord(Integer userId, String descript, Integer page, Integer limit);

    List<Map<String, Object>> getInviteRecord(Integer userId, String descript, Integer limit);

    int getHongbaoCountByUser(Integer userId, List source, Integer type, String descript);

    List<Hongbao> queryUserCanUseHongbao(Integer userId, Integer source);

    /**
     * 领券中心发放红包（新）
     * @param amount
     * @param userId
     * @param descript
     * @param source
     * @param type
     * @param application
     * @param limitDays
     * @param limitAmount
     * @param limitMonths
     * @param expireDays
     * @param templateId
     * @return
     */
    int saveInvestHongBaoNew(Double amount, Integer userId, String descript, Integer source,
                             Integer type, Integer application, Integer limitDays, Integer limitAmount, String limitMonths, Integer expireDays, String templateId);

	/**
	 * 通过用户id查询可用红包
	 *
	 * @Title: listHongbaoByUserId
	 * @param userId
	 * @return List<Hongbao>
	 * @author zj
	 * @date 2019-05-27 18:04
	 */
	List<Hongbao> listHongbaoByUserId(Integer userId);

	/**
	 * @description 查询红包总额
	 * @author shuys
	 * @date 2019/6/25
	 * @param userId
	 * @param isCash 是否是现金
	 * @return java.lang.Double
	*/
    Double getInvitRecordTotalAmount(Integer userId, Boolean isCash);
}
