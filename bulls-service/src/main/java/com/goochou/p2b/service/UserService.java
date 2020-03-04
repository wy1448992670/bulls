package com.goochou.p2b.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.goochou.p2b.dao.EmployMapper;
import com.goochou.p2b.dao.UserMapper;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.model.AndroidTunnel;
import com.goochou.p2b.model.CustomerServiceManagement;
import com.goochou.p2b.model.EmailVerify;
import com.goochou.p2b.model.Employ;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.vo.DataSourceSumVo;
import com.goochou.p2b.model.vo.InvestSpreadOfCountryVO;
import com.goochou.p2b.model.vo.LandlordVO;

public interface UserService {
    User getLoginUser(String keyword);

    /**
     * @param user
     * @return 0 保存成功 1昵称已经存在 2昵称包含敏感词
     */
    int save(User user) throws Exception;

    void save(User user, MultipartFile file, Integer userId)
            throws Exception;

    int update(User user) throws Exception;

    void updateBySelect(User user, MultipartFile file, Integer userId)
            throws Exception;

    void delete(int id) throws Exception;

    List<User> query(String username);

    User get(Integer id);

    /**
     * 根据邀请码查询用户
     *
     * @return
     */
    User getByInviteCode(String inviteCode);

    User getByPhone(String phone);

    void updateLogout(String phone);

    void updateLogoutByToken(String token);

    /**
     * 验证用户名是否存在
     *
     * @param username
     * @return true 不存在, false存在
     * @throws Exception
     */
    boolean checkNameExists(String username, Integer userId)
            throws Exception;

    /**
     * 验证手机号是否存在
     *
     * @param phone
     * @return true 不存在, false存在
     * @throws Exception
     */
    boolean checkPhoneExists(String phone, Integer userId);

    int check(String value, String property);

    /**
     * @param value
     * @param property
     * @param isForget 是否是找回密码的功能
     * @return
     */
    int check(String value, String property, boolean exists);

    /**
     * @param user
     * @return ret 0：成功，1：帐号不存在，2：密码不正确,3:锁定中
     */
    Map<String, Object> login(String account, String password,
                              String ip, String deviceToken, String appVersion, String client);

    List<User> search(String keyword) throws Exception;

    /**
     * 查询所有可用的用户列表
     *
     * @return
     */
    List<User> list() throws Exception;

    List<User> listIncomed() throws Exception;

    /**
     * 用户根据关键词分页查询
     *
     * @param keyword
     * @param start
     * @param limit
     */
    List<Map<String, Object>> query(String keyword, Double startAmount,
                                    Double endAmount, Integer status, Integer type, int start, int limit);

    /**
     * 用户根据关键词分页查询
     *
     * @return
     */
    List<Map<String, Object>> query1(Map<String, Object> map);

    List<Map<String, Object>> query2(String keyword, Double startAmount,
                                     Double endAmount, Integer status, Date startTime, Date endTime,
                                     Integer type, int start, int limit, Date investStartTime, Date investndTime, String customerName);

    Integer queryCount(String keyword, Double startAmount,
                       Double endAmount, Integer status, Integer type);

    /**
     * 查找用户详细信息
     *
     * @param id
     * @return
     */
    User detail(Integer id) throws Exception;

    /**
     * 更改密码
     */
    void updatePassword(Integer id, String password) throws Exception;

    /**
     * 查询所有用户总数
     *
     * @return
     */
    Integer count(Integer status,Integer adminDepartmentId,Integer departmentId) throws Exception;

    Long countHuo();

    /**
     * 用户注册
     *
     * @return
     * @throws Exception
     */
    Map<String, Object> addUserByRegist(User user) throws Exception;

    boolean checkUserRegist(User user);

    boolean checkUsername(String username);

    boolean checkPhone(String phone);

    boolean checkEmail(String email);

    boolean checkPassword(String password);

    String getSinaUid(String code, String appkey, String secret,
                      String callbackurl);

    Map<String, Object> addOrLoginUserByThirdParty(User user, String flag);

    String getQQId(String code, String client_id, String client_secret,
                   String redirectUri);

    /**
     * 统计每个月1-30号每一天的用户注册数量
     *
     * @return
     */
    List<Map<String, Object>> getCountByMonthDay(Integer adminDepartmentId,Integer departmentId);

    List<Map<String, Double>> investAmountRank();

    int updatephone(Integer userId, String phone, String ip);

    int addEmailVerify(EmailVerify emailVerify);

    int check(String value, String property, Integer userId);

    int verifyEmail(Integer userId, String verifyCode);

    int setpassword(User user, String oldpassword, String password);

    int checkoldpassword(Integer userId, String oldpassword);

    int checkpaypassword(Integer userId, String paypassword);

    int setpaypassword(Integer userId, String paypassword, int type);

    void editPersonal(User user);

    /**
     * @param id   当前需要更新头像的用户ID
     * @param file 当前需要更新的头像file
     */
    String updateAvatar(Integer id, MultipartFile file) throws Exception;

    int checknewpaypassword(Integer userId, String paypassword);

    /**
     * 根据用户名 邮箱 手机号 查询用户
     *
     * @return
     */
    User getByEmailOrPhoneOrName(String keyword);

    User getByKeyword(String keyword);

    List<User> getByTrueName(String trueName);

    boolean checkInviteCode(String inviteCode);

    List<User> getAllUser();

    Map<String, Object> updateAvatarApp(Integer id,
                                        Map<String, Object> map);

    User selectUserByPhone(String phone);

    void updateUserStatus(Integer userId, Integer type);

    /**
     * 根据token获取用户
     * @author ydp
     * @param token
     * @return
     */
    User checkLogin(String token);

    void setBackPassword(Integer id, String password);

    User updateCheckLogin(String token, Integer status, String ip);

    boolean getByTrueNameAndIDCard(String idCard);

    //  int sendVerifyCode(String phone, String verifyCode) throws
    // Exception;
    //
    //  int sendWithdrawNotice(String phone, Double amount, Date time, int
    // tunnel, String bankCode) throws Exception;
    //
    //  int sendRepairNotice(String phone, Date time, Integer last) throws
    // Exception;

    /**
     * 查询所有老用户用于绑卡
     *
     * @param tunnel 0连连1
     * @return
     */
    List<User> ListAllOldUser();

    /**
     * 统计使用我邀请码加入的用户
     *
     * @param tunnel 0连连1
     * @return
     */
    Integer getCountByInviteCode(String inviteCode);

    /**
     * 本月邀请的人数和其中投资的人数
     *
     * @param
     * @return
     */
    List<Map<String, Object>> getThisMonthCountByInviteCode(
            String inviteCode);

    /**
     * 本月邀请的人
     *
     * @param
     * @return
     */
    List<Map<String, Object>> getThisMonthUserByInviteCode(
            String inviteCode);

    /**
     * 本月前的三个月邀请的人
     *
     * @param
     * @return
     */
    List<Map<String, Object>> getOldMonthsUserByInviteCode(
            String inviteCode, Date startTime, Date endTime);

    /**
     * 本月前的本月邀请的有效人数
     *
     * @param
     * @return
     */
    Integer getFinancialPlanner(String inviteCode, Date date);

    /**
     * 重置用户支付密码
     *
     * @param
     * @return
     */
    void updateResetpaypassword(User user, String string);

    /**
     * 重置用户昵称
     *
     * @param
     * @return
     */
    void updateUserName(User user);

    Integer trueNameCount();

    Long huoRecharge();

    List<Double> accountChecking(Integer userId);

    /**
     * 每日拉去当天生日用户的名单
     *
     * @return
     * @author 王信
     * @date 2015年11月6日 上午9:55:09
     */
    List<Map<String, Object>> userBirthday();

    /**
     * 微信WEB方式，手机登录
     *
     * @param 刘源 2015-11-10
     * @return ret 0：成功，1：帐号不存在，2：密码不正确,3:锁定中
     */
    Map<String, Object> wapLogin(String account, String password,
                                 String ip);

    /**
     * 查询该死的羊毛
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<User> getByTime(Date startTime, Date endTime);

    Integer queryCount1(Map<String, Object> map);

    /**
     * 查询所有客服名字，方便前台用户新增 公共方法 只要传入权限组就可以返回权限组对应的人员信息
     *
     * @return
     * @author 王信
     * @Create Date: 2015年11月17日下午5:25:12
     */
    List<Map<String, Object>> selectCustomerUser();

    /**
     * 查询当前在投金额>0并且收益>0的用户
     *
     * @return
     */
    List<Map<String, Object>> listInvestedAndIncomed();

    /**
     * 根据id查单个客服信息，回填数据给更新页面
     *
     * @param id
     * @return
     * @author 王信
     * @Create Date: 2015年11月18日上午9:13:44
     */
    List<Map<String, Object>> selectSingleUser(Integer id);

    /**
     * 手动同步客服配置数据到数据库
     *
     * @author 王信
     * @Create Date: 2015年11月18日下午3:14:01
     */
    void batchCustomer();

    /**
     * 查询在投+余额+冻结金额大于0的用户并且不是黑名单中的用户
     *
     * @return
     */
    List<User> getActiveUser();

    /**
     * 获取需要发送短信的用户手机号
     *
     * @return
     */
    List<String> getMessageUserPhone();

    /**
     * 查询所有的提现被锁用户
     *
     * @return
     * @author 王信
     * @Create Date: 2015年12月23日上午9:32:47
     */
    List<Map<String, Object>> selectLockwithdraw(String likeSearch,
                                                 Integer page, Integer limit);

    /**
     * 查询记录条数
     *
     * @param likeSearch
     * @param page
     * @param limit
     * @return
     * @author 王信
     * @Create Date: 2015年12月23日上午10:32:21
     */
    Integer selectLockwithdrawCount(String likeSearch);

    /**
     * 解锁体现用户的账号
     *
     * @param id
     * @author 王信
     * @Create Date: 2015年12月23日上午11:01:44
     */
    void deleteLockWithdraw(Integer id);

    User getByToken(String token);

    /**
     * 根据userid **，**，字符串查询
     *
     * @param userIds
     * @return
     * @author 王信
     * @Create Date: 2015年12月25日下午3:07:00
     */
    List<String> selectUserIds(String userIds);

    /**
     * 查询用户个人资产
     *
     * @return
     * @author 刘源
     * @date 2016年2月17日
     * @parameter
     */
    Map<String, Object> queryUserAssets(Integer userId);

    /**
     * 用户资产差异情况统计
     *
     * @param start
     * @param limit
     * @return
     */
    List<Map<String, Object>> queryUserAssetsDifferenceStatistics(Integer start, Integer limit);

    Map<String, Object> selectUserActivityInvestment(Integer userId, Date startTime, Date endTime);

    boolean isActivityRegist(Integer userId, Date startTime, Date endTime);

    /**
     * 强制更新，使用时注意字段
     *
     * @param user
     * @author 刘源
     * @date 2016/6/7
     */
    void updateWithNull(User user);

    /**
     * 根据ID查询用户信息
     *
     * @param id
     * @author 刘源
     * @date 2016/7/1
     */
    User queryById(Integer id);

    /**
     * @Description(描述):查询所有可以分派给客服的用户
     * @author 赵星星
     * @date 2016/7/20
     * @params
     **/
    List<Map<String, Object>> queryCustomer(String keyword, int start, int limit);


    /**
     * @Description(描述):用户周报表
     * @author 王信
     * @date 2016/8/29
     * @params
     **/
    Map<String, Object> selectUserWeeklyReport(Date startDate, Date endDate);

    /**
     * 根据被邀请码查询用户
     *
     * @param invitedCode
     * @return
     */
    User getByInvitedCode(String invitedCode);

    List<Map<String, Object>> detailDing(Integer id) throws Exception;

    List<Map<String, Object>> countDing(Integer id) throws Exception;

    List<Map<String, Object>> selectByDate(Integer userId);

    /**
     * 查询注册用户数量,去除测试用户
     *
     * @return
     */
    int countUser();

    int selectMaxUserId();

    /**
     * 统计用户投资分布情况
     *
     * @return List<InvestSpreadOfCountryVO>
     * @author xueqi
     */
    List<InvestSpreadOfCountryVO> queryInvestSpreadOfCountry();

    /**
     * 统计当日注册用户
     *
     * @return
     */
    Integer queryTodayRegisterUser(Date date);

    /**
     * 总注册量
     *
     * @return
     */
    Integer queryAllRegisterUser();

    List<User> queryRegistUserByDate(Date date, Date endDate);

    /**
     * 用户注册(房东)
     *
     * @return
     */
    Map<String, Object> addUserByRegistOfLandlord(User user);


    User searchLandlord(String phone);

    List<User> queryUserByStatus(String status);

    boolean updateUserFadadaCustomerId(Map<String, Object> param) ;

    List<LandlordVO> queryLandloardList(Map<String, Object> map);

    Integer queryLandloardListCount(Map<String, Object> map);

	Integer queryTodayExpGoldUser(Date date);

	Integer queryTodaySignedUser(Date date);

	//老的密码校验, 暂时给app用
	boolean checkPasswordOld(String password);

    Integer updateRiskEvaluate(User user, Integer score);

    /**
     * 修改手势密码、指纹密码
     * @param userId
     * @return
     */
    int updatePwdByUserId(Integer userId,int isGesturePassword,String gesturePassword,int isFingerprintPassword);

    /**
     * 获取手势密码、指纹密码
     * @param userId
     * @return
     */
    Map<String,Object> getUserPwdInfo(Integer userId);

    /**
     * 获取用户的优惠券数量
     *
     * @param userId
     * @return
     */
    int getCouponTotalCount(Integer userId);

	Map<String, Object> doAccount(Integer userId);

	Map<String, Object> queryUsercustom(Integer userId);
    /**
     * 查询不能分配的用户Id
     * @return
     */
	List<Integer> queryDistriblackList();

	/**
	 * @date 2019年5月15日
	 * @author wangyun
	 * @time 下午2:19:06
	 * @Description 插入用户认证表
	 *
	 * @param userId
	 * @param realName
	 * @param idNumber
	 * @return
	 */
	int insertUserAuth(Integer userId,String realName, String idNumber);

	/**
	 * @date 2019年5月14日
	 * @author wangyun
	 * @time 下午4:18:14
	 * @Description 用户实名信息
	 *
	 * @param userId
	 * @param user
	 * @return
	 */
	int updateAuth(User user, int authId);

	/**
	 * @date 2019年5月22日
	 * @author wangyun
	 * @time 上午11:54:02
	 * @Description 手机验证码登录
	 *
	 * @return
	 */
	Map<String, Object> loginMobile(String mobile, String ip,String client, String appVersion);

	int insertSelective(User user);

	/**
	* 禁言用户
	* @Title: bannedUser
	* @param userId
	* @param isForbidComment    是否禁止评论（0否 1是）
	* @author zj
	* @date 2019-06-05 18:02
	*/
	void doBannedUser(Integer userId, Integer isForbidComment);

    /**
     * @description 邀请记录
     * @author shuys
     * @date 2019/6/24
     * @param userId
     * @return java.util.Map<java.lang.String,java.lang.Object>
    */
    Map <String, Object> queryInviteRecord(Integer userId);

    /**
     * @description 邀请新用户获得的总额
     * @author shuys
     * @date 2019/6/24
     * @param userId
     * @return java.lang.Double
    */
    Double getInvitRecordTotalAmount(Integer userId);

    UserMapper getUserMapper();
    

    public User queryByUserName(String userName);

    int doSetVipUser(Integer userId, Integer giveOutDay, BigDecimal giveScale, Integer level) throws Exception;
    
    void doVipUserFreezeProfitJob(User user) throws Exception ;
    
    List<Map<String, Object>> listMirgationUserReport(String keyword, Date mirgationTime,Integer start, Integer limit);
    int countMirgationUserReport(String keyword, Date mirgationTime);

	void doMigrationImport(MultipartFile file) throws Exception;

	List<DataSourceSumVo> dataSourceSum(Date userCreateDateStart, Date userCreateDateEnd, Integer departmentId,
			Integer adminId);
}
