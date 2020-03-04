package com.goochou.p2b.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;

import com.goochou.p2b.constant.*;
import com.goochou.p2b.constant.assets.AccountOperateEnum;
import com.goochou.p2b.dao.*;
import com.goochou.p2b.model.*;
import com.goochou.p2b.service.*;
import com.goochou.p2b.service.exceptions.LockFailureException;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.CommonUtils;
import com.goochou.p2b.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.fuiou.util.MD5;
import com.goochou.p2b.dao.AdminLogMapper;
import com.goochou.p2b.dao.AdvertisementChannelMapper;
import com.goochou.p2b.dao.AssetsMapper;
import com.goochou.p2b.dao.EmailVerifyMapper;
import com.goochou.p2b.dao.IndustryMapper;
import com.goochou.p2b.dao.UploadMapper;
import com.goochou.p2b.dao.UserAdminMapper;
import com.goochou.p2b.dao.UserAuthenticationMapper;
import com.goochou.p2b.dao.UserMapper;
import com.goochou.p2b.dao.VipDividendMapper;
import com.goochou.p2b.dao.YaoCountMapper;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.openapi.auth.AuthenticationRequest;
import com.goochou.p2b.model.UserExample.Criteria;
import com.goochou.p2b.model.vo.DataSourceSumVo;
import com.goochou.p2b.model.vo.InvestSpreadOfCountryVO;
import com.goochou.p2b.model.vo.LandlordVO;
import com.goochou.p2b.model.vo.MigrationUser;
import com.goochou.p2b.service.ActivityService;
import com.goochou.p2b.service.AdvertisementChannelService;
import com.goochou.p2b.service.HongbaoService;
import com.goochou.p2b.service.LoginRecordService;
import com.goochou.p2b.service.UploadService;
import com.goochou.p2b.service.UserInviteService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.utils.Md5Util;
import com.goochou.p2b.utils.StringEncrypt;
import com.google.gson.Gson;

@Service
public class UserServiceImpl implements UserService {

	private final static Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Resource
	private EmployMapper employMapper;
	@Resource
	private EmployUserMapper employUserMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserAdminMapper userAdminMapper;
    @Resource
    private UploadMapper uploadMapper;
    @Resource
    private UploadService uploadService;
    @Resource
    private IndustryMapper industryMapper;
    @Resource
    private AssetsMapper assetsMapper;
    @Resource
    private EmailVerifyMapper emailVerifyMapper;
    @Resource
    private HongbaoService hongbaoService;
    @Resource
    private UserInviteService userInviteService;
    @Resource
    private YaoCountMapper yaoCountMapper;
    @Resource
    private AdminLogMapper adminLogMapper;
    @Resource
    private UserAuthenticationMapper userAuthMapper;
    @Resource
    private ActivityService activityService;
	@Resource
	protected LoginRecordService loginRecordService;
	@Resource
	private AdvertisementChannelService advertisementChannelService;

	@Resource
	private UserAccountService userAccountService;
    @Resource
    protected VipDividendMapper vipDividendMapper;
    @Resource
	private MigrationInvestmentService migrationInvestmentService;
    @Resource
	private MigrationInvestmentBillService migrationInvestmentBillService;
    @Resource
    private SmsSendService smsSendService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private ActivityBlessingChanceRecordService activityBlessingChanceRecordService;
    
    static private CloseableHttpClient httpclient = HttpClients.createDefault();

    static private String shortMsgUrl = "http://sms.chanzor.com:8001/sms.aspx";

    @Override
    public User getLoginUser(String keyword) {
        return userMapper.getLoginUser(keyword);
    }

    @Deprecated
    @Override
    public int insertSelective(User user) {
    	return userMapper.insertSelective(user);
    }

    @Deprecated
    @Override
    public int save(User user) throws Exception {
        // 验证昵称是否存在
        UserExample example = new UserExample();
        Criteria c = example.createCriteria();
        c.andUsernameEqualTo(user.getUsername());
        if (userMapper.countByExample(example) > 0) {
            return 1; // 用户名已存在
        }
        user.setPassword(StringEncrypt.Encrypt(user.getPassword()));
        userMapper.insertSelective(user);
        return 0;
    }

    @Override
    public int update(User user) throws Exception {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void delete(int id) throws Exception {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<User> query(String username) {
        UserExample example = new UserExample();
        Criteria c = example.createCriteria();
        if (!StringUtils.isEmpty(username)) {
            c.andUsernameLike(username);
        }
        return userMapper.selectByExample(example);
    }

    @Override
    public User get(Integer id) {
        if (id == null) {
            return null;
        }
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getByPhone(String phone) {
        UserExample example = new UserExample();
        example.createCriteria().andPhoneEqualTo(phone);
        List<User> list = userMapper.selectByExample(example);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Map<String, Object> login(String account, String password, String ip, String deviceToken, String appVersion, String client) {
        Map<String, Object> map = new HashMap<String, Object>();
        int ret = 0;
        String msg = null;
        User user = getLoginUser(account);
        user=userMapper.selectByPrimaryKeyForUpdate(user.getId());
        try {
        	if (user == null) {
                ret = 1;
                msg = "登录未获取到用户信息";
                logger.info("登录未获取到用户信息");
            } else {
                Date lastLoginTime = user.getLastLoginTime();
                long now = new Date().getTime();

                // 锁定一个小时 输入错误之后再更新,=5已经锁定了
                if (lastLoginTime != null && user.getErrorCount() >= 5 && (now - lastLoginTime.getTime() < 1000 * 60 * 60)) {
                	ret = 3;
                	msg = "用户密码错误超过5次,锁定1小时";
                	logger.info("登录用户锁定一小时");
                	logger.info("登录用户密码错误超过5次");
                } else {
                	// pc端不更新token
                	if(!ClientEnum.PC.getFeatureName().equals(client.toUpperCase())) {
                		user.setToken(StringEncrypt.Encrypt(UUID.randomUUID().toString()));
                	}
    	            user.setLastLoginTime(new Date());
    	            user.setLastLoginIp(ip);
    	            if (!user.getPassword().equals(MD5.MD5Encode(password+Constants.PASSWORD_FEX))) {
    	                ret = 2;
    	                msg = "密码错误";
    	                // 30分钟以内错误次数+1
    	                if (lastLoginTime == null || (now - lastLoginTime.getTime() <= 1000 * 60 * 10)) {
    	                    user.setErrorCount(user.getErrorCount() + 1);
    	                } else {
    	                    user.setErrorCount(1);
    	                }
    	            } else {
    	                user.setErrorCount(0);
    	            }
    	            //获取头像
    	            if (user.getAvatarId() != null && user.getAvatarId() > 0) {
    	                Upload upload = uploadMapper.selectByPrimaryKey(user.getAvatarId());
    	                if (upload != null) {
    	                    user.setAvatar(upload.getPath());
    	                }
    	            }
    	            user.setAppVersion(appVersion);
    	            userMapper.updateByPrimaryKeySelective(user);

    				//----------------------------  新增登录设备信息记录  start
    	            LoginRecord record = new LoginRecord();
    	            record.setIp(ip);
    	            record.setClient(client);
    	            record.setCreateDate(new Date());
    	            record.setUserId(user.getId());
    	            record.setVersion(appVersion);
    	            loginRecordService.saveLoginRecord(record);
    	            //----------------------------  新增登录设备信息记录    end
    	            //活动红包
    	            activityService.doActivityLogin(user);
    	           
                }
                map.put("user", user);
            }
		} catch (Exception e) {
			e.printStackTrace();
	 		ret = 10;
	 		msg ="登录异常";
		}

        map.put("ret", ret);
        map.put("msg", msg);
        return map;
    }

    @Override
    public Map<String, Object> loginMobile(String mobile, String ip, String client, String appVersion){
    	 Map<String, Object> map = new HashMap<String, Object>();
    	 int ret = 0;
    	 String message = "";
         User user = getLoginUser(mobile);
         user=userMapper.selectByPrimaryKeyForUpdate(user.getId());
         try {

	        if (user == null) {
	            ret = 1;
	            message ="登录未获取到用户信息";
	            logger.info("登录未获取到用户信息");
	        } else {
	        	user.setToken(StringEncrypt.Encrypt(UUID.randomUUID().toString()));
	            user.setLastLoginTime(new Date());
	            user.setLastLoginIp(ip);
	            //获取头像
	            if (user.getAvatarId() != null && user.getAvatarId() > 0) {
	                Upload upload = uploadMapper.selectByPrimaryKey(user.getAvatarId());
	                if (upload != null) {
	                    user.setAvatar(upload.getPath());
	                }
	            }
	            user.setAppVersion(appVersion);
	            userMapper.updateByPrimaryKeySelective(user);
	            map.put("user", user);

				//----------------------------  新增登录设备信息记录  start
				LoginRecord record = new LoginRecord();
				record.setIp(ip);
				record.setClient(client);
				record.setCreateDate(new Date());
				record.setUserId(user.getId());
				record.setVersion(appVersion);
				loginRecordService.saveLoginRecord(record);
				//----------------------------  新增登录设备信息记录    end
 	            //活动红包
 	            activityService.doActivityLogin(user);
	         }

	 	} catch (Exception e) {
	 		e.printStackTrace();
	 		ret = 10;
	 		message ="登录异常";
	 	}
        map.put("ret", ret);
        map.put("msg", message);
        return map;
	}



    @Override
    public List<User> search(String keyword) {
        List<User> users = new ArrayList<User>();
        UserExample example = new UserExample();
        example.createCriteria().andPhoneEqualTo(keyword);
        users.addAll(userMapper.selectByExample(example));
        example.clear();
        example.createCriteria().andUsernameLike("%" + keyword + "%");
        users.addAll(userMapper.selectByExample(example));
        return users;
    }

    @Override
    public List<Map<String, Object>> query(String keyword, Double startAmount, Double endAmount, Integer status, Integer type, int start, int limit) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(keyword) && keyword.indexOf("'") > 0) {
                keyword = keyword.replaceAll("'", "");
            }
            map.put("keyword", keyword);
            map.put("status", status);
            map.put("start", start);
            map.put("limit", limit);
            map.put("type", type);
            map.put("startAmount", startAmount);
            map.put("endAmount", endAmount);
            return userMapper.query(map);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }


    @Override
    public List<Map<String, Object>> queryCustomer(String keyword, int start, int limit) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(keyword) && keyword.indexOf("'") > 0) {
                keyword = keyword.replaceAll("'", "");
            }
            map.put("keyword", keyword);
            map.put("start", start);
            map.put("limit", limit);
            List<Map<String, Object>> list = userMapper.queryCustomer(map);
            return userMapper.queryCustomer(map);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    @Override
    public Map<String, Object> selectUserWeeklyReport(Date startDate, Date endDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        return userMapper.selectUserWeeklyReport(map);
    }


    public List<Map<String, Object>> query1(String keyword, Double startAmount, Double endAmount, Integer status, Date startTime, Date endTime, Integer type, int start, int limit, Date investStartTime, Date investndTime) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("keyword", StringUtils.trim(keyword));
            map.put("status", status);
            map.put("start", start);
            map.put("limit", limit);
            map.put("startTime", startTime);
            map.put("endTime", endTime);
            map.put("type", type);
            map.put("investStartTime", investStartTime);
            map.put("investndTime", investndTime);
            map.put("startAmount", startAmount);
            map.put("endAmount", endAmount);
            return userMapper.query1(map);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> query1(Map<String, Object> map) {
        return userMapper.query1(map);
    }

    public List<Map<String, Object>> query2(String keyword, Double startAmount, Double endAmount, Integer status, Date startTime, Date endTime, Integer type, int start, int limit, Date investStartTime, Date investndTime, String customerName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", StringUtils.trim(keyword));
        map.put("status", status);
        map.put("start", start);
        map.put("limit", limit);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("type", type);
        map.put("investStartTime", investStartTime);
        map.put("investndTime", investndTime);
        map.put("startAmount", startAmount);
        map.put("endAmount", endAmount);
        map.put("customerName", customerName);
        return userMapper.query1(map);
    }

    @Override
    public Integer queryCount(String keyword, Double startAmount, Double endAmount, Integer status, Integer type) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", keyword);
        map.put("status", status);
        map.put("type", type);
        map.put("startAmount", startAmount);
        map.put("endAmount", endAmount);
        return userMapper.queryCount(map);
    }

    @Override
    public User detail(Integer id) throws Exception {
        User user = userMapper.selectByPrimaryKey(id);
        if (user != null) {
            if (user.getAvatarId() != null) {
                Upload u = uploadMapper.selectByPrimaryKey(user.getAvatarId());
                if (u != null) {
                    user.setAvatar(u.getPath());
                }
            }
           /* if (user.getIndustryId() != null) {
                Industry industry = industryMapper.selectByPrimaryKey(user.getIndustryId());
                if (industry != null) {
                    user.setIndustryName(industry.getName());
                }
            }*/
        }
        return user;
    }

    @Override
    public List<Map<String, Object>> detailDing(Integer id) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", id);
        return userMapper.detailDing(map);
    }

    @Override
    public List<Map<String, Object>> countDing(Integer id) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", id);
        return userMapper.countDing(map);
    }

    @Override
    public boolean checkNameExists(String username, Integer userId) throws Exception {
        if (userId != null) {
            User u = userMapper.selectByPrimaryKey(userId);
            if (u.getUsername().equals(username)) { // 用户名无更改则不需要验证
                return true;
            }
        }
        UserExample example = new UserExample();
        Criteria c = example.createCriteria();
        c.andUsernameEqualTo(StringUtils.trim(username));
        List<User> list = userMapper.selectByExample(example);
        return list.size() > 0 ? false : true;
    }

    @Override
    public void save(User user, MultipartFile file, Integer userId) throws Exception {
        Integer avatarId = null;
        if (file != null) {
            Map<String, Object> map = uploadService.save(file, 1, userId);
            if (map.get(ConstantsAdmin.STATUS).equals(ConstantsAdmin.SUCCESS)) {
                avatarId = (Integer) map.get(ConstantsAdmin.ID);
                user.setAvatarId(avatarId);
            }
        }
        userMapper.insert(user);
        // 生成资产记录
        Assets assets = new Assets();
        assets.setUserId(user.getId());
        assetsMapper.insertSelective(assets);
    }

    @Override
    public void updateBySelect(User user, MultipartFile file, Integer userId) throws Exception {
        if (file != null) {
            Map<String, Object> map = uploadService.save(file, 1, userId);
            if (map.get(ConstantsAdmin.STATUS).equals(ConstantsAdmin.SUCCESS)) {
                Integer avatarId = (Integer) map.get(ConstantsAdmin.ID);

                // 删除记录同时删除原来的图片
                User u = userMapper.selectByPrimaryKey(user.getId());
                Integer oldAvatar = u.getAvatarId();
                if (oldAvatar != null) {
                    Upload upload = uploadMapper.selectByPrimaryKey(oldAvatar);
                    String picPath = (String) map.get(ConstantsAdmin.PICTURE_PATH);
                    if (upload != null) {
                        File f = new File(picPath + "/" + upload.getPath());
                        if (f.exists()) {
                            f.delete();
                        }
                    }
                    uploadMapper.deleteByPrimaryKey(oldAvatar);
                }
                user.setAvatarId(avatarId);
            }
        }
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public List<User> list() throws Exception {
        UserExample example = new UserExample();
        Criteria c = example.createCriteria();
        c.andStatusEqualTo(0);
        return userMapper.selectByExample(example);
    }

    @Override
    public List<User> listIncomed() throws Exception {
        return userMapper.listIncomed();
    }

    @Override
    public void updatePassword(Integer id, String password) throws Exception {
        User user = userMapper.selectByPrimaryKey(id);
        user.setPassword(StringEncrypt.Encrypt(password));
        userMapper.updateByPrimaryKey(user);
    }

    @Override
	public Integer count(Integer status, Integer adminDepartmentId, Integer departmentId) {
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		if (status != null) {
			criteria.andStatusEqualTo(status);
		}
		if (adminDepartmentId != null) {
			List<Department> departmentList = departmentService.getSubDepartments(adminDepartmentId);
			List<Integer> departmentIdList = new ArrayList<Integer>();
			for (Department department : departmentList) {
				departmentIdList.add(department.getId());
			}
			criteria.andDepartmentIdIn(departmentIdList);
		}
		if (departmentId != null) {
			List<Department> departmentList = departmentService.getSubDepartments(departmentId);
			List<Integer> departmentIdList = new ArrayList<Integer>();
			for (Department department : departmentList) {
				departmentIdList.add(department.getId());
			}
			criteria.andDepartmentIdIn(departmentIdList);
		}
		return userMapper.countByExample(example);
	}

    @Override
    public Map<String, Object> addUserByRegist(User user) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String msg = "";
        int ret = 0;
        int r = 0;
//        r = this.check(user.getUsername(), "username");
//        if (r == 1) {// 用户名不合法
//            ret = 1;
//        } else if (r == 2) {// 用户名已存在
//            ret = 2;
//        } else {

        	boolean flag = false;
        	/*if(user.isApp()){
        		flag = this.checkPasswordOld(user.getPassword());
        	}else{*/
        		flag = this.checkPassword(user.getPassword());
        	//}

            if (!flag) {// 密码不合法
                ret = 3;
                msg= "密码不合法";
            } else {
                r = this.check(user.getPhone(), "phone");
                if (r == 1) {// 电话不合法
                    ret = 4;
                    msg= "请输入正确的身份证号码";
                } else if (r == 2) {// 电话已经存在
                    ret = 5;
                    msg= "手机号已存在";
                }
            }
       // }

        if (ret == 0) {
            String p = user.getPassword();
            user.setPassword(MD5.MD5Encode(p+Constants.PASSWORD_FEX));

            String channelNo = user.getChannelId();
            if(StringUtils.isNotEmpty(channelNo) ) {
            	 AdvertisementChannelExample channelexample = new AdvertisementChannelExample();
                 channelexample.createCriteria().andChannelNoEqualTo(channelNo);
                 AdvertisementChannelMapper channelMapper = advertisementChannelService.getAdvertisementChannelMapper();
                 List<AdvertisementChannel> channelList=channelMapper.selectByExample(channelexample);
                 if(channelList.size() != 1) {
                 	map.put("ret", 9);
                 	map.put("msg", "addUserByRegist渠道编号重复");
                    return map;
                 }
                 AdvertisementChannel advertisementChannel = channelList.get(0);
                 user.setChannelId(advertisementChannel.getId().toString());
            }

            if (userMapper.insertSelective(user) != 1) {
                ret = 9;
                msg= "用户注册失败";
            } else {
                int id = user.getId();
                // 邀请码9位
                String code = "7" + StringUtils.leftPad(String.valueOf(id), 8, "0");
                String inviteCode = Md5Util.toMD5(code);
                user.setInviteCode(inviteCode);// 生成默认的邀请码
                if (StringUtils.isNotBlank(user.getInviteByCode())) {
                    // 如果邀请码不为空的话，说明有邀请人,插入邀请记录表
                    UserExample example = new UserExample();
                    example.createCriteria().andInviteCodeEqualTo(user.getInviteByCode());
                    List<User> listInvite = userMapper.selectByExample(example);
                    if (null != listInvite && !listInvite.isEmpty()) {
                        if(listInvite.size()!=1) {
                         	map.put("ret", 9);
                         	map.put("msg", "addUserByRegist推荐码重复");
                            return map;
            			}
                        User inviter=listInvite.get(0);
                        userInviteService.save(inviter.getId(), user.getId());
                        //如果推荐人是员工,新建员工用户关系表数据
                        EmployExample employExample=new EmployExample();
            			EmployExample.Criteria crteria=employExample.createCriteria();
            			crteria.andMobileEqualTo(inviter.getPhone());
            			List<Employ> employList=employMapper.selectByExample(employExample);
            			if(employList.size()==1) {
            				Employ employ=employList.get(0);
            				if(employUserMapper.insertByUserIds(Arrays.asList(user.getId()), employ.getId()) != 1) {
                             	map.put("ret", 9);
                             	map.put("msg", "addUserByRegist员工推荐关系插入失败");
                                return map;
            				}
            			}else if (employList.size()>1) {
                         	map.put("ret", ret);
                         	map.put("msg", "addUserByRegist员工手机号重复");
                            return map;
            			}
                    }
                }
                userMapper.updateByPrimaryKeySelective(user);
                // 生成资产记录
                Assets assets = new Assets();
                assets.setUserId(user.getId());
                assetsMapper.insertSelective(assets);
                map.put("user", user);

                //执行注册活动
                activityService.doActivityRegister(user);

                try {
                    logger.info("新年活动-注册发放抽奖次数");
                    activityBlessingChanceRecordService.doSendOutChance(user, 1, null);
                } catch (Exception e) {
                    logger.info("新年活动异常：" +e.getMessage(), e);
                }
                
            }
        }
        map.put("ret", ret);
        map.put("msg", msg);
        return map;
    }

    @Deprecated
    @Override
    public Map<String, Object> addUserByRegistOfLandlord(User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        int ret = 0;
        int r = 0;
        r = this.check(user.getUsername(), "username");
        if (r == 1) {// 用户名不合法
            ret = 1;
        } else if (r == 2) {// 用户名已存在
            ret = 2;
        } else {
            if (!this.checkPassword(user.getPassword())) {// 密码不合法
                ret = 3;
            } else {
                r = this.check(user.getPhone(), "phone");
                if (r == 1) {// 电话不合法
                    ret = 4;
                } else if (r == 2) {// 电话已经存在
                    ret = 5;
                }
            }
        }

        if (ret == 0) {
            String p = user.getPassword();
            user.setPassword(StringEncrypt.Encrypt(p));

            if (userMapper.insertSelective(user) != 1) {
                ret = 9;
            } else {
                int id = user.getId();
                // 邀请码9位
                String code = "7" + StringUtils.leftPad(String.valueOf(id), 8, "0");
                String inviteCode = Md5Util.toMD5(code);
                user.setInviteCode(inviteCode);// 生成默认的邀请码
                if (StringUtils.isNotBlank(user.getInviteByCode())) {
                    // 如果邀请码不为空的话，说明有邀请人,插入邀请记录表
                    UserExample example = new UserExample();
                    example.createCriteria().andInviteCodeEqualTo(user.getInviteByCode());
                    List<User> listInvite = userMapper.selectByExample(example);
                    if (null != listInvite && !listInvite.isEmpty()) {
                        userInviteService.save(listInvite.get(0).getId(), id);
                    }
                }
                userMapper.updateByPrimaryKeySelective(user);
                // 生成资产记录
                Assets assets = new Assets();
                assets.setUserId(user.getId());
                assetsMapper.insertSelective(assets);
                // 生成摇一摇次数
                YaoCount yc = new YaoCount();
                yc.setUserId(user.getId());
                yc.setCount(0);
                yc.setTodayCount(1);
                yc.setTime(new Date());
                yaoCountMapper.insert(yc);
                map.put("user", user);

//                // 请求新浪接口激活会员
//                SinaBean bean = SinaUtil.createActivateMember(user.getId(), user.getRegisterIp());
//                if (!bean.getResultCode().equalsIgnoreCase("APPLY_SUCCESS")) {
//                    for (int i = 0; i < 10; i++) {
//                        SinaBean bean2 = SinaUtil.createActivateMember(user.getId(), user.getRegisterIp());
//                        if (!bean2.getResultCode().equalsIgnoreCase("APPLY_SUCCESS")) {
//                            continue;
//                        }
//                        break;
//                    }
//                }

            }
        }
        map.put("ret", ret);
        return map;
    }

    @Override
    public String getSinaUid(String code, String appkey, String secret, String callbackurl) {
        String url = "https://api.weibo.com/oauth2/access_token";
        String uid = null;
        CloseableHttpResponse response = null;
        try {
            HttpPost post = new HttpPost(url);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("client_id", appkey));
            params.add(new BasicNameValuePair("client_secret", secret));
            params.add(new BasicNameValuePair("grant_type", "authorization_code"));
            params.add(new BasicNameValuePair("code", code));
            params.add(new BasicNameValuePair("redirect_uri", callbackurl));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, Consts.UTF_8);
            post.setEntity(formEntity);
            response = httpclient.execute(post);
            HttpEntity entity = response.getEntity();
            String json = entityToString(entity);
            JSONObject jsonObj = (JSONObject) JSONObject.parse(json);
            uid = jsonObj.getString("uid");
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(e);
                }
            }
        }
        return uid;
    }

    @Deprecated
    @Override
    public Map<String, Object> addOrLoginUserByThirdParty(User user, String flag) {
        Map<String, Object> map = new HashMap<String, Object>();
        UserExample example = new UserExample();
       /* if ("sina".equals(flag)) {
            example.createCriteria().andSinaIdEqualTo(user.getSinaId());
        } else if ("qq".equals(flag)) {
            example.createCriteria().andQqIdEqualTo(user.getQqId());
        }*/
        List<User> users = userMapper.selectByExample(example);
        if (users != null && users.size() > 0) {
            map.put("ret", 0);
            map.put("user", users.get(0));
        } else {
            user.setUsername(getAutoGeneralName());
            userMapper.insertSelective(user);
            // 生成资产记录
            Assets assets = new Assets();
            assets.setUserId(user.getId());
            assetsMapper.insertSelective(assets);
            User u = userMapper.selectByPrimaryKey(user.getId());
            map.put("ret", 0);
            map.put("user", u);
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> getCountByMonthDay(Integer adminId,Integer departmentId) {
        return userMapper.getCountByMonthDay(adminId, departmentId);
    }

    @Override
    public String getQQId(String code, String client_id, String client_secret, String redirectUri) {
        CloseableHttpResponse response1 = null, response2 = null;
        String openId = null;
        try {
            URIBuilder builder1 = new URIBuilder("https://graph.qq.com/oauth2.0/token");
            builder1.addParameter("client_id", client_id).addParameter("code", code).addParameter("client_secret", client_secret).addParameter("redirect_uri", redirectUri).addParameter("grant_type", "authorization_code");
            HttpGet get1 = new HttpGet(builder1.build());
            response1 = httpclient.execute(get1);
            String str = entityToString(response1.getEntity());
            Map<String, String> m = parseUrlParams(str);
            String accessToken = m.get("access_token");
            if (accessToken == null) {
                System.out.println("没有获取到access_token");
                return null;
            }
            URIBuilder builder2 = new URIBuilder("https://graph.qq.com/oauth2.0/me");
            builder2.addParameter("access_token", accessToken);
            HttpGet get2 = new HttpGet(builder2.build());
            response2 = httpclient.execute(get2);
            String result = entityToString(response2.getEntity());
            JSONObject jsonObj = getJsonFromCallback(result);
            openId = jsonObj.getString("openid");

        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (response1 != null) {
                try {
                    response1.close();
                } catch (IOException e) {
                    logger.error(e);
                }
            }
            if (response2 != null) {
                try {
                    response2.close();
                } catch (IOException e) {
                    logger.error(e);
                }
            }
        }

        return openId;
    }

    private String entityToString(HttpEntity entity) throws IllegalStateException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), Consts.UTF_8));
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    private Map<String, String> parseUrlParams(String params) {
        Map<String, String> map = new HashMap<String, String>();
        String[] p = params.split("&");
        for (String kv : p) {
            String[] pair = kv.split("=");
            map.put(pair[0], pair[1]);
        }
        return map;
    }

    // fuck callback
    private JSONObject getJsonFromCallback(String callback) {
        String json = callback.substring(callback.indexOf("(") + 1, callback.indexOf(")"));
        return JSONObject.parseObject(json);
    }

    private String getAutoGeneralName() {
        return String.valueOf(new Date().getTime() * 1000 + new Random().nextInt(1000));
    }

    @Override
    public int check(String value, String property, boolean exists) {
        int ret = check(value, property);
        if (ret == 2 && exists) {
            ret = 0;
        }
        return ret;
    }

    @Override
    public int check(String value, String property) {
        int ret = 0;
        UserExample example = new UserExample();
        Criteria c = example.createCriteria();
        if ("username".equals(property)) {
            if (!this.checkUsername(value)) {
                ret = 1;
            } else {
                c.andUsernameEqualTo(value);
            }
        } else if ("phone".equals(property)) {
            if (!this.checkPhone(value)) {
                ret = 1;
            } else {
                c.andPhoneEqualTo(value);
            }
        } else if ("email".equals(property)) {
            if (!this.checkEmail(value)) {
                ret = 1;
            } else {
                c.andEmailEqualTo(value);
            }
        } else {
            ret = 3;
        }
        if (ret == 0) {
            List<User> users = userMapper.selectByExample(example);
            if (users != null && users.size() > 0) {
                ret = 2;
            }
        }
        return ret;
    }

    @Override
    public int check(String value, String property, Integer userId) {
        int ret = 0;
        if (userId != null) {
            User user = userMapper.selectByPrimaryKey(userId);
            if ("username".equals(property) && value.equals(user.getUsername())) {
                return ret;
            } else if ("phone".equals(property) && value.equals(user.getPhone())) {
                return ret;
            } else if ("email".equals(property) && value.equals(user.getEmail())) {
                return ret;
            } else {
                return this.check(value, property);
            }
        } else {
            return this.check(value, property);
        }
    }

    @Override
    public boolean checkUserRegist(User user) {
        return checkUsername(user.getUsername()) && checkPhone(user.getPhone()) && checkPassword(user.getPassword()) && checkEmail(user.getEmail());
    }

    @Override
    public boolean checkUsername(String username) {
        if (username.matches("^[\\d]+$")) {
            return false;
        }
        return StringUtils.isNotBlank(username) && username.matches("^[a-zA-Z0-9_\u4e00-\u9fa5]{2,15}$");
    }

    @Override
    public boolean checkPhone(String phone) {
        return StringUtils.isNotBlank(phone) && phone.matches("\\d{11}");
    }

    @Override
    public boolean checkEmail(String email) {
        return StringUtils.isBlank(email) || email.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    }

    @Override
    public boolean checkPasswordOld(String password) {
        return StringUtils.isNotBlank(password) && password.matches("^.{6,}$");
    }

    public Integer updateRiskEvaluate(User user, Integer score) {
        try {
            if (score == null) {
                score = 10;
                if(user.getRiskEvaluateScore() != null && user.getRiskEvaluateScore() >= 10){
                	score = user.getRiskEvaluateScore();
                }
            }
            user.setRiskEvaluateScore(score);

            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, com.goochou.p2b.constant.Constants.RISK_EVALUATE_DAY);
            user.setRiskReevaluateTime(c.getTime());
            update(user);
        } catch (Exception e) {
            logger.error(e);
            return 0;
        }
        return 1;
    }

    @Override
    public boolean checkPassword(String password) {
        return StringUtils.isNotBlank(password) && password.matches("^(?=.*[0-9].*)(?=.*[a-zA-Z].*).{6,12}$");
    }

    public boolean checkPayPassword(String password) {
        return StringUtils.isNotBlank(password) && password.matches("^\\d{6}$");
    }

    @Override
    public List<Map<String, Double>> investAmountRank() {
        return userMapper.investAmountRank();
    }

    @Override
    public int addEmailVerify(EmailVerify emailVerify) {
        int ret = 0;
        if (emailVerifyMapper.insertSelective(emailVerify) == 0) {
            ret = 1;
        }
        return ret;
    }

    @Override
    public int verifyEmail(Integer userId, String verifyCode) {
        int ret = 0;
        if (userId != null && StringUtils.isNotBlank(verifyCode)) {
            EmailVerify emailVerify = emailVerifyMapper.lastByUserId(userId);
            if (emailVerify == null) {
                ret = 1;
            } else if (emailVerify.getStatus() == 1) {
                ret = 4;
            } else if ((new Date().getTime() - emailVerify.getCreateTime().getTime()) > 1000 * 60 * 60 * 48) {
                ret = 2;
            } else if (!emailVerify.getVerifyCode().equals(verifyCode)) {
                ret = 3;
            } else {
                emailVerify.setStatus(1);
                emailVerifyMapper.updateByPrimaryKeySelective(emailVerify);
                User user = new User();
                user.setId(userId);
                user.setEmail(emailVerify.getEmail());
                userMapper.updateByPrimaryKeySelective(user);
            }
        } else {
            ret = 1;
        }
        return ret;
    }

    @Override
    public int checkoldpassword(Integer userId, String oldpassword) {
    	System.out.println(userId);
        int ret = 0;
        if (this.checkPassword(oldpassword)) {
            User user = userMapper.selectByPrimaryKey(userId);
            if (!user.getPassword().equals(MD5.MD5Encode(oldpassword+Constants.PASSWORD_FEX))) {
                ret = 2;//旧密码输入错误
            }
        } else {
            ret = 1; //密码格式不正确
        }
        return ret;
    }

    @Override
    public int setpassword(User userTemp, String oldpassword, String password) {
        int ret = 0;
        boolean flag = false;
   
    	if(this.checkPassword(password)){
    		flag = true;
    	}

        if (flag) {
            User user = userMapper.selectByPrimaryKey(userTemp.getId());
            if (user.getPassword().equals(MD5.MD5Encode(oldpassword+Constants.PASSWORD_FEX))) {
                if (user.getPassword() != null && user.getPassword().equals(MD5.MD5Encode(password+Constants.PASSWORD_FEX))) {
                    ret = 3;//新密码与旧密码一致
                } else {
                    user.setPassword(MD5.MD5Encode(password+Constants.PASSWORD_FEX));
                    userMapper.updateByPrimaryKeySelective(user);
                }

            } else {
                ret = 2;//旧密码错误
            }
        } else {
            ret = 1; //密码格式不正确
        }
        return ret;
    }

    @Override
    public void setBackPassword(Integer id, String password) {
        userMapper.setBackPassword(id, MD5.MD5Encode(password+Constants.PASSWORD_FEX));
    }

    // public int checkpaypassword(Integer userId, String paypassword) {
    // int ret = 0;
    // if (this.checkPassword(paypassword)) {
    // User user = userMapper.selectByPrimaryKey(userId);
    // if (user != null) {
    // if (user.getPayPassword() != null &&
    // !user.getPayPassword().equals(StringEncrypt.Encrypt(paypassword))) {
    // if (user.getPassword().equals(StringEncrypt.Encrypt(paypassword))) {
    // ret = 2;
    // }
    // }
    // } else {
    // ret = 1;
    // }
    // } else {
    // ret = 1;
    // }
    // return ret;
    // }
    @Override
    public int checkpaypassword(Integer userId, String paypassword) {
        int ret = 0;
        if (this.checkPayPassword(paypassword)) {
            User user = userMapper.selectByPrimaryKey(userId);
            if (user != null) {
                if (user.getPassword().equals(MD5.MD5Encode(paypassword+Constants.PASSWORD_FEX))) {
                    ret = 2;
                } else {
                    ret = 0;
                }
            } else {
                ret = 1;
            }
        } else {
            ret = 1;
        }
        return ret;
    }

    @Override
    public int checknewpaypassword(Integer userId, String paypassword) {
        int ret = 0;
        if (this.checkPayPassword(paypassword)) {
            User user = userMapper.selectByPrimaryKey(userId);
            if (user != null) {
                if (user.getPassword().equals(MD5.MD5Encode(paypassword+Constants.PASSWORD_FEX))) {
                    ret = 2;
                } else if (StringUtils.isNotBlank(user.getPayPassword()) && user.getPayPassword().equals(MD5.MD5Encode(paypassword+Constants.PASSWORD_FEX))) {
                    ret = 3;
                } else {
                    ret = 0;
                }
            } else {
                ret = 1;
            }
        } else {
            ret = 1;
        }
        return ret;
    }

    @Override
    public int setpaypassword(Integer userId, String paypassword, int type) {
        int ret = 0;
        if (type == 1) {// 设置
            ret = this.checkpaypassword(userId, paypassword);
        } else {// 修改
            ret = this.checknewpaypassword(userId, paypassword);
        }

        if (ret == 0) {
            User user = userMapper.selectByPrimaryKey(userId);
            user.setPayPassword(MD5.MD5Encode(paypassword+Constants.PASSWORD_FEX));
            userMapper.updateByPrimaryKeySelective(user);
        }
        return ret;
    }

    @Override
    public void editPersonal(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void updateLogout(String phone) {
        userMapper.logout(phone);
    }

    @Override
    public void updateLogoutByToken(String token) {
        UserExample example = new UserExample();
        example.createCriteria().andTokenEqualTo(token);
        List<User> list = userMapper.selectByExample(example);
        if (null != list && !list.isEmpty()) {
            User u = list.get(0);
            u.setToken(u.getToken() + "1");
            userMapper.updateByPrimaryKeySelective(u);
        }
    }

    @Override
    public User getByEmailOrPhoneOrName(String keyword) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(keyword);
        example.or(example.createCriteria().andEmailEqualTo(keyword));
        example.or(example.createCriteria().andPhoneEqualTo(keyword));
        List<User> list = userMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public User getByKeyword(String keyword) {
    	List<User> users = userMapper.getByKeyword(keyword);
    	if(users != null && users.size() > 0) {
    		 return userMapper.getByKeyword(keyword).get(0);
    	} else {
    		return null;
    	}

    }

    @Override
    public boolean checkPhoneExists(String phone, Integer userId) {
        if (userId != null) {
            User u = userMapper.selectByPrimaryKey(userId);
            if (u.getPhone().equals(phone)) { // phone无更改则不需要验证
                return true;
            }
        }
        UserExample example = new UserExample();
        Criteria c = example.createCriteria();
        c.andPhoneEqualTo(StringUtils.trim(phone));
        List<User> list = userMapper.selectByExample(example);
        return list.size() > 0 ? false : true;
    }

    @Override
    public boolean checkInviteCode(String inviteCode) {
        UserExample example = new UserExample();
        example.createCriteria().andInviteCodeEqualTo(inviteCode);
        List<User> list = userMapper.selectByExample(example);
        return list != null && list.size() > 0;
    }

    @Override
    public List<User> getAllUser() {
        return userMapper.selectByExample(new UserExample());
    }

    @Override
    public User getByInviteCode(String inviteCode) {
        UserExample example = new UserExample();
        example.createCriteria().andInviteCodeEqualTo(inviteCode);
        List<User> list = userMapper.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public String updateAvatar(Integer id, MultipartFile file) throws Exception {
        if (file != null) {
            Map<String, Object> map = uploadService.save(file, 0, id); // 保存图片
            if (map.get(ConstantsAdmin.STATUS).equals(ConstantsAdmin.SUCCESS)) {
                User user = userMapper.selectByPrimaryKey(id);// 保存图片成功后查询用户是不是有老头像
                Integer avatarId = (Integer) map.get(ConstantsAdmin.ID);

                Integer oldAvatar = user.getAvatarId();
                if (oldAvatar != null) {
                    Upload upload = uploadMapper.selectByPrimaryKey(oldAvatar); // 存在老头像，删除记录同时删除原来的图片
                    if (upload != null) {
                        String picPath = (String) map.get(ConstantsAdmin.PICTURE_PATH);
                        File f = new File(picPath + "/" + upload.getPath());
                        if (f.exists()) {
                            f.delete();
                        }
                        uploadMapper.deleteByPrimaryKey(oldAvatar);
                    }
                }
                user.setAvatarId(avatarId);
                userMapper.updateByPrimaryKey(user); // 设置用户新头像ID，更新用户
                return (String) map.get(ConstantsAdmin.PATH);
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> updateAvatarApp(Integer id, Map<String, Object> map) {
        if (map.get(ConstantsAdmin.STATUS).equals(ConstantsAdmin.SUCCESS)) {
            User user = userMapper.selectByPrimaryKey(id);// 保存图片成功后查询用户是不是有老头像
            Integer avatarId = (Integer) map.get(ConstantsAdmin.ID);

            Integer oldAvatar = user.getAvatarId();
            if (oldAvatar != null) {
                Upload upload = uploadMapper.selectByPrimaryKey(oldAvatar); // 存在老头像，删除记录同时删除原来的图片
                if (upload != null) {
                    String picPath = (String) map.get(ConstantsAdmin.PICTURE_PATH);
                    File f = new File(picPath + "/" + upload.getPath());
                    if (f.exists()) {
                        f.delete();
                    }
                    uploadMapper.deleteByPrimaryKey(oldAvatar);
                }
            }
            user.setAvatarId(avatarId);
            userMapper.updateByPrimaryKey(user); // 设置用户新头像ID，更新用户
            return map;
        }
        return null;
    }

    @Override
    public User selectUserByPhone(String phone) {
        return userMapper.selectUserByPhone(phone);
    }

    @Override
    public void updateUserStatus(Integer userId, Integer type) {
        User u = userMapper.selectByPrimaryKey(userId);
        if (type.equals(0)) {
            u.setStatus(1);
            u.setToken("frozen");
        } else if (type.equals(1)) {
            u.setStatus(3);
        } else if (type.equals(2)) {
            u.setStatus(0);
        }
        userMapper.updateByPrimaryKeySelective(u);

    }

    @Override
    public User checkLogin(String token) {
        return userMapper.checkLogin(token);
    }

    @Override
    public User updateCheckLogin(String token, Integer status, String ip) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        User user = userMapper.checkLogin(token);
        if (user != null && status != null) {
            user.setLastLoginTime(new Date());
            user.setLastLoginIp(ip);
            userMapper.updateByPrimaryKeySelective(user);
        }
        return user;
    }

    public boolean getByTrueNameAndIDCard(String idCard) {
        UserExample example = new UserExample();
        Criteria c = example.createCriteria();
        c.andIdentityCardEqualTo(idCard);
        List<User> list = userMapper.selectByExample(example);
        // 已存在
        if (list != null && !list.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public List<User> getByTrueName(String trueName) {
        UserExample example = new UserExample();
        example.createCriteria().andTrueNameEqualTo(trueName);
        return userMapper.selectByExample(example);
    }

    @Override
    public List<User> ListAllOldUser() {
        return userMapper.ListAllOldUser();
    }

    @Override
    public Integer getCountByInviteCode(String inviteCode) {
        UserExample example = new UserExample();
        example.createCriteria().andInviteByCodeEqualTo(inviteCode);
        return userMapper.countByExample(example);
    }

    @Override
    public List<Map<String, Object>> getThisMonthCountByInviteCode(String inviteCode) {
        return userMapper.getThisMonthCountByInviteCode(inviteCode);
    }

    @Override
    public List<Map<String, Object>> getThisMonthUserByInviteCode(String inviteCode) {

        return userMapper.getThisMonthUserByInviteCode(inviteCode);
    }

    @Override
    public List<Map<String, Object>> getOldMonthsUserByInviteCode(
            String inviteCode, Date startTime, Date endTime) {
        return userMapper.getOldMonthsUserByInviteCode(inviteCode, startTime, endTime);
    }

    @Override
    public Integer getFinancialPlanner(String inviteCode, Date date) {
        return userMapper.getFinancialPlanner(inviteCode, date);
    }

    @Override
    public void updateResetpaypassword(User user, String paypassword) {
        user.setPayPassword(StringEncrypt.Encrypt(paypassword));
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Long countHuo() {
        return userMapper.countHuo();
    }

    @Override
    public void updateUserName(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Integer trueNameCount() {
        UserExample example = new UserExample();
        example.createCriteria().andTrueNameIsNotNull();
        return userMapper.countByExample(example);
    }

    @Override
    public Long huoRecharge() {
        return userMapper.huoRecharge();
    }

    @Override
    public List<Double> accountChecking(Integer userId) {
        return userMapper.accountChecking(userId);
    }

    /**
     * 每日拉取当天生日用户名单
     *
     * @return
     * @author 王信
     * @date 2015年11月6日 上午9:57:01
     */
    @Override
    public List<Map<String, Object>> userBirthday() {

        return userMapper.userBirthday();
    }

    @Override
    public Map<String, Object> wapLogin(String account, String password, String ip) {
        Map<String, Object> map = new HashMap<String, Object>();
        int ret = 0;
        UserExample example = new UserExample();
        example.or().andUsernameEqualTo(account).andStatusEqualTo(0);
        example.or().andPhoneEqualTo(account).andStatusEqualTo(0);
        example.or().andEmailEqualTo(account).andStatusEqualTo(0);
        List<User> users = userMapper.selectByExample(example);
        if (users == null || users.size() == 0) {
            ret = 1;
        } else {
            User user = users.get(0);
            Date lastLoginTime = user.getLastLoginTime();
            long now = new Date().getTime();
            // 锁定一个小时
            if (lastLoginTime != null && user.getErrorCount() >= 5 && (now - lastLoginTime.getTime() < 1000 * 60 * 60)) {
                ret = 3;
            } else {
                user.setToken(StringEncrypt.Encrypt(UUID.randomUUID().toString()));
                user.setLastLoginTime(new Date());
                user.setLastLoginIp(ip);
                if (!user.getPassword().equals(StringEncrypt.Encrypt(password))) {
                    ret = 2;
                    // 30分钟以内错误次数+1
                    if (lastLoginTime == null || (now - lastLoginTime.getTime() <= 1000 * 60 * 30)) {
                        user.setErrorCount(user.getErrorCount() + 1);
                    } else {
                        user.setErrorCount(1);
                    }
                } else {
                    user.setErrorCount(0);
                }
                if (user.getAvatarId() != null && user.getAvatarId() > 0) {
                    Upload upload = uploadMapper.selectByPrimaryKey(user.getAvatarId());
                    if (upload != null) {
                        user.setAvatar(upload.getPath());
                    }
                }
                userMapper.updateByPrimaryKeySelective(user);
            }
            map.put("user", user);
        }
        map.put("ret", ret);
        return map;
    }

    @Override
    public List<User> getByTime(Date startTime, Date endTime) {
        return userMapper.getByTime(startTime, endTime);
    }

    @Override
    public Integer queryCount1(Map<String, Object> map) {
        return userMapper.queryCount1(map);
    }

    @Override
    public List<Map<String, Object>> selectCustomerUser() {
        return userMapper.selectCustomerUser();
    }

    @Override
    public List<Map<String, Object>> listInvestedAndIncomed() {
        return userMapper.listInvestedAndIncomed();
    }

    @Override
    public List<Map<String, Object>> selectSingleUser(Integer id) {
        return userMapper.selectSingleUser(id);
    }

    @Override
    public void batchCustomer() {
        userMapper.batchCustomer();
    }

    @Override
    public List<User> getActiveUser() {
        return userMapper.getActiveUser();
    }

    @Override
    public List<String> getMessageUserPhone() {
        return userMapper.getMessageUserPhone();
    }

    @Override
    public List<Map<String, Object>> selectLockwithdraw(String likeSearch, Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("likeSearch", likeSearch);
        map.put("page", page);
        map.put("limit", limit);
        return userMapper.selectLockwithdraw(map);
    }

    @Override
    public Integer selectLockwithdrawCount(String likeSearch) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("likeSearch", likeSearch);
        return userMapper.selectLockwithdrawCount(map);
    }


    @Override
    public User getByToken(String token) {
        UserExample example = new UserExample();
        example.createCriteria().andTokenEqualTo(token);
        List<User> list = userMapper.selectByExample(example);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public void deleteLockWithdraw(Integer id) {
        userMapper.deleteLockWithdraw(id);
    }

    @Override
    public List<String> selectUserIds(String userIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userIds", userIds);
        return userMapper.selectUserIds(map);
    }

    @Override
    public Map<String, Object> queryUserAssets(Integer userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        return userMapper.queryUserAssets(map);
    }

    @Override
    public List<Map<String, Object>> queryUserAssetsDifferenceStatistics(Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", start);
        map.put("limit", limit);
        return userMapper.queryUserAssetsDifferenceStatistics(map);
    }

    @Override
    public Map<String, Object> selectUserActivityInvestment(Integer userId, Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return userMapper.selectUserActivityInvestment(map);
    }

    @Override
    public boolean isActivityRegist(Integer userId, Date startTime, Date endTime) {
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(userId).andCreateDateBetween(startTime, endTime);
        Integer count = userMapper.countByExample(example);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void updateWithNull(User user) {
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public User queryById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getByInvitedCode(String invitedCode) {
        UserExample example = new UserExample();
        example.createCriteria().andInviteByCodeEqualTo(invitedCode);
        List<User> list = userMapper.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> selectByDate(Integer userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        return userMapper.selectByDate(map);
    }

    public int countUser() {
        return userMapper.countUser();
    }

    @Override
    public int selectMaxUserId() {
        return userMapper.selectMaxUserId();
    }

    @Override
    public List<InvestSpreadOfCountryVO> queryInvestSpreadOfCountry() {

        return userMapper.queryInvestSpreadOfCountry(new HashMap<String, Object>());
    }

    @Override
    public Integer queryTodayRegisterUser(Date date) {
        UserExample example = new UserExample();

        example.createCriteria().andCreateDateGreaterThanOrEqualTo(date);
        Integer count = userMapper.countByExample(example);
        return count;
    }

    @Override
    public Integer queryAllRegisterUser() {
        UserExample example = new UserExample();

        Integer count = userMapper.countByExample(example);
        return count;
    }

    @Override
    public List<User> queryRegistUserByDate(Date date, Date endDate) {

        UserExample example = new UserExample();
        example.createCriteria().andCreateDateGreaterThanOrEqualTo(date).andCreateDateLessThanOrEqualTo(endDate);
        return userMapper.selectByExample(example);
    }

	@Override
	public User searchLandlord(String phone) {
        UserExample example = new UserExample();
        example.createCriteria().andPhoneEqualTo(phone);
        List<User> list = userMapper.selectByExample(example);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
	}


	@Override
	public List<User> queryUserByStatus(String status) {

		UserExample example = new UserExample();
        example.createCriteria().andStatusEqualTo(Integer.parseInt(status));
        return userMapper.selectByExample(example);
	}

	@Override
	public boolean updateUserFadadaCustomerId(Map<String, Object> param) {
        return userMapper.updateByUserId(param) > 0 ? true : false;
	}

	@Override
	public List<LandlordVO> queryLandloardList(Map<String, Object> map) {
		return userMapper.queryLandloardList(map);
	}

	@Override
	public Integer queryLandloardListCount(Map<String, Object> map) {
		return userMapper.queryLandloardListCount(map);
	}

	@Override
	public Integer queryTodayExpGoldUser(Date date) {
        return userMapper.queryTodayExpGoldUser(date);
	}

	@Override
	public Integer queryTodaySignedUser(Date date) {
		return userMapper.queryTodaySignedUser(date);
	}

	@Override
	public int updatePwdByUserId(Integer userId,int isGesturePassword,String gesturePassword,int isFingerprintPassword) {
		Map<String,Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("isGesturePassword", isGesturePassword);
		map.put("gesturePassword", gesturePassword);
		map.put("isFingerprintPassword", isFingerprintPassword);
		return userMapper.updatePwdByUserId(map);
	}

	@Override
	public Map<String, Object> getUserPwdInfo(Integer userId) {
		return userMapper.getUserPwdInfo(userId);
	}

    @Override
    public int getCouponTotalCount(Integer userId) {
        return userMapper.getCouponTotalCount(userId);
    }

	@Override
	public Map<String, Object> doAccount(Integer userId) {
		return userMapper.doAccount(userId);
	}

	@Override
	public Map<String, Object> queryUsercustom(Integer userId) {
		// TODO Auto-generated method stub
		return userMapper.queryUsercustom(userId);
	}

	@Override
	public List<Integer> queryDistriblackList() {
		// TODO Auto-generated method stub
		return userMapper.queryDistriblack();
	}

	@Override
	public int updatephone(Integer userId, String phone, String ip) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertUserAuth(Integer userId, String realName, String idNumber) {
		//插入 user_authentication表
		UserAuthentication auth = new UserAuthentication();
		auth.setIdentificationNo(idNumber);
		auth.setTrueName(realName);
		auth.setTime(new Date());
		auth.setType(0);
		auth.setUserId(userId);
		auth.setStatus(2);//认证中
		userAuthMapper.insert(auth);
		return auth.getId();
	}


	@Override
	public int updateAuth(User user, int authId) {
		int code = 0; //认证失败
		UserAuthentication auth  = userAuthMapper.selectByPrimaryKey(authId);
		//第三方认证
		Response result = new Response();
		if(Constants.TEST_SWITCH.equals(TestEnum.RELEASE.getFeatureName())) {
		    AuthenticationRequest smr = new AuthenticationRequest();
	        smr.setUsername(user.getTrueName());
	        smr.setIdNo(user.getIdentityCard());
	        ServiceMessage msg = new ServiceMessage("auth.pyzx", smr);
	        result = (Response) OpenApiClient.getInstance()
	                .setServiceMessage(msg).send();
		}else {
		    result.setSuccess(true);
		}
        if(result.isSuccess()) {
        	//更新user_authentication数据
        	auth.setStatus(1);//1成功
    		//回写更新user表
    		userMapper.updateByPrimaryKeySelective(user);
    		code = 1;
        } else {
        	auth.setStatus(0);//0失败
        	code = 0;
        }
        userAuthMapper.updateByPrimaryKeySelective(auth);

        return code;

	}

	@Override
	public void doBannedUser(Integer userId, Integer isForbidComment) {
		User user = userMapper.selectByPrimaryKey(userId);
		user.setIsForbidComment(isForbidComment);
		user.setUpdateDate(new Date());
		userMapper.updateByPrimaryKeySelective(user);
	}

	public List<Map<String, Object>> queryInviteUsersToResgister (Integer userId) {
        List<Map<String, Object>> result = userMapper.queryInviteFirstInvestmentHongBao(userId);
        if (result != null && !result.isEmpty()) {
            for (Map<String, Object> map : result) {
                this.convertResultShow(map);
            }
        }
        return result;
    }

    public List<Map<String, Object>> queryNotInviteUsersToResgister (Integer userId) {
        List<Map<String, Object>> result = userMapper.queryNotInviteUsersToResgister(userId);
        if (result != null && !result.isEmpty()) {
            for (Map<String, Object> map : result) {
                this.convertResultShow(map);
            }
        }
        return result;
    }

    public List<Map<String, Object>> queryInviteUsersToInvest (Integer userId) {
        List<Map<String, Object>> result = hongbaoService.getMapper().queryInviteUsersToInvest(userId);
        if (result != null && !result.isEmpty()) {
            for (Map<String, Object> map : result) {
                this.convertResultShow(map);
            }
        }
        return result;
    }

    private Map<String, Object> convertResultShow(Map<String, Object> source) {
//        if (source.isEmpty())
        String trueName = (String)source.get("true_name");
        String phone = (String)source.get("phone");
        if (StringUtils.isNotBlank(trueName)) {
            source.put("true_name", com.goochou.p2b.utils.StringUtils.parseFirstName(trueName));
        }
        if (StringUtils.isNotBlank(phone)) {
            source.put("phone", com.goochou.p2b.utils.StringUtils.parseFirstThreeAndLastFour(phone));
        }
        return source;
    }

    // 邀请人数
    public int queryInviteUsersSum (Integer userId) {
        List<Map<String, Object>> result = userMapper.queryInviteUsersToResgister(userId);
        int sum = 0;
        if (result != null && !result.isEmpty()) {
            sum = result.size();
        }
        return sum;
    }

    // 邀请记录查询
    public Map <String, Object> queryInviteRecord (Integer userId) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> inviteUsersToResgister = this.queryNotInviteUsersToResgister(userId); // 邀请已注册，并且还未养牛的用户信息
        int inviteUsersSum = userMapper.queryInviteUsersCount(userId); // 邀请已注册用户数量
        List<Map<String, Object>> inviteUsersToInvest = this.queryInviteUsersToResgister(userId); // 邀请已养牛用户信息

        Double cashHongbao = hongbaoService.getInvitRecordTotalAmount(userId, true);
        Double couponHongbao = hongbaoService.getInvitRecordTotalAmount(userId, false);

//        BigDecimal hongbaoTotalAmount = BigDecimal.ZERO;
        result.put("fromRegister", inviteUsersToResgister);
        result.put("fromInvest", inviteUsersToInvest);
        result.put("inviteUsersSum", inviteUsersSum);
        result.put("cashTotalAmount", cashHongbao);   // 现金红包总额
        result.put("couponTotalAmount", couponHongbao);  // 红包券总额
        return result;
    }



    // 邀请新用户获得的现金红包总额
    public Double getInvitRecordTotalAmount(Integer userId) {
        return hongbaoService.getInvitRecordTotalAmount(userId ,true);
    }

	@Override
	public UserMapper getUserMapper() {
		return userMapper;
	}

    @Override
    public User queryByUserName(String userName) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(userName);
        List<User> list = userMapper.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    /**
     * @date 2019年10月8日
     * @author wangyun
     * @time 下午4:20:16
     * @Description 用户vip派息
     *
     * @throws Exception
     */
    @Override
    public void doVipUserFreezeProfitJob(User user) throws Exception {
    	try {
	    	if(user == null) {
	    		return;
	    	}
	    	user = userMapper.selectByPrimaryKeyForUpdate(user.getId());
	    	if(user.getLevel() != 2) {
	    		logger.info(user.getTrueName()+"不是vip用户");
	    		return;
	    	}
	    	if(user.getGiveScale() == null) {
	    		logger.info("未设置"+user.getTrueName()+"用户发放比例");
	    		return;
	    	}
	    	if(user.getGiveOutDate() == null) {
	    		logger.info("未设置"+user.getTrueName()+"用户发放时间");
	    		return;
	    	}
	    	Calendar calendar = Calendar.getInstance();
	    	// 查询该用户当月预发放记录
	    	VipDividend dividend = vipDividendMapper.selectByDividendDayForUpdate(user.getId(), calendar.getTime());
	    	if(dividend == null) {
	    		logger.info("未查询到用户"+user.getTrueName()+"发放记录");
	    		return;
	    	}

	    	Assets assets = assetsMapper.selectByPrimaryKeyForUpdate(user.getId());
	    	dividend.setCreditAmount(BigDecimal.valueOf(assets.getCreditAmount()));//记录当前授信金额

	    	// 授信金额*派息比例
	    	BigDecimal dividendAmount = user.getGiveScale().multiply(BigDecimal.valueOf(assets.getCreditAmount()).divide(BigDecimal.valueOf(100))).setScale(2,BigDecimal.ROUND_HALF_UP);
	    	logger.info(user.getTrueName()+"发放授信金额"+ dividendAmount+ "元");
	    	boolean flag = true;
	    	 //减少用户授信资金
	        if(userAccountService.modifyAccount(assets, dividendAmount, dividend.getId(),
	                BusinessTableEnum.user, AccountOperateEnum.VIPDIVIDEND_CREDIT_SUBTRACT) == 0) {
	        	flag = false;
	        }
	        //增加用户余额
	        if(userAccountService.modifyAccount(assets, dividendAmount, dividend.getId(),
	                BusinessTableEnum.user, AccountOperateEnum.VIPDIVIDEND_BALANCE_ADD) == 0) {
	        	flag = false;
	        }

	        //更新用户资金
	        if (assetsMapper.updateByPrimaryKeyAndVersionSelective(assets) == 0) {
	        	throw new LockFailureException();
	        }

	        if(!flag) {
	        	throw new Exception("用户资金操作失败");
	        }
	        // 资金更新后更新发放状态
	        dividend.setDividendAmount(dividendAmount);
	    	dividend.setRealDividendDate(calendar.getTime());
	    	dividend.setIsDividend(true);
	    	dividend.setDividendScale(user.getGiveScale());
		    if(vipDividendMapper.updateByPrimaryKey(dividend) == 0) {
		    	throw new Exception("更新发放记录异常！");
		    }
		    String content = DictConstants.SEND_VIP_PROFIT_CODE;
		    content = content.replaceAll("\\{realName}", CommonUtils.getTrueName(user.getTrueName()));
		    content = content.replaceAll("\\{amount}",  dividendAmount+"元");
		    System.err.println("短信内容: "+content);
		    smsSendService.addSmsSend(user.getPhone(), content, MessageChannelEnum.DH3T, new Date());
	        // 生成下个月发放记录
	        VipDividend nextDividend = new VipDividend();
	        nextDividend.setCreateDate(calendar.getTime());
	        
	        calendar.setTime(dividend.getDividendDate());
	        calendar.add(Calendar.MONTH, 1);
	        nextDividend.setDividendDate(calendar.getTime());//设置下个月的发放时间
	        nextDividend.setIsDividend(false);
	        nextDividend.setUserId(user.getId());
	        if(vipDividendMapper.insert(nextDividend) == 0) {
	        	throw new Exception("生成发放记录异常！");
	        }
    	} catch (Exception e) {
    		logger.error("定时派息异常" + e.getMessage());
			e.printStackTrace();
			throw new Exception(e);
		}
    }


    private static Date getNextMonth(Date date, int day) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, day, 0, 0, 0);
    	return calendar.getTime();
    }

	/**
     * 前提:1.一个月一个用户最多只能有一个派息单.
     * 	2.一个用户只能存在一个未派息单.
     * 定义:1.当月已派息,不允许在当月派息.
     *  2.当期应派息日不能设置今天以前.
     */
    @Override
    public int doSetVipUser(Integer userId, Integer giveOutDay, BigDecimal giveScale, Integer level) throws Exception {
        User user = this.userMapper.selectByPrimaryKeyForUpdate(userId);
        if (user == null) {
            throw new Exception("该用户不存在");
        }

        //当前时间
        Date now = new Date();
        //今日初
        Date today =DateUtil.getDayStartDate(now);

        if (level == 2) { // vip用户
        	boolean curMonthHasFinished = true; //当月已派息
            boolean haveUnfinishedDividends = false; //有未派息单
            Date curPeriodDividendDate = null; //当期派息时间

            //当月初
            Date curMonthBegin = DateUtil.getCurrentMonthFirstDate();

            //当期派息时间
            Calendar calCalendar = Calendar.getInstance();
            calCalendar.setTime(curMonthBegin);
            calCalendar.set(Calendar.DAY_OF_MONTH, giveOutDay);
            curPeriodDividendDate=calCalendar.getTime();//当期派息时间

            //已派息单
            VipDividendExample finishedDividendExample = new VipDividendExample();
            finishedDividendExample.createCriteria()
                    .andUserIdEqualTo(userId)
                    .andIsDividendEqualTo(true);
            finishedDividendExample.setOrderByClause("dividend_date DESC");
            finishedDividendExample.setLimitStart(0);
            finishedDividendExample.setLimitEnd(1);

            List<VipDividend> finishedDividendList = vipDividendMapper.selectByExample(finishedDividendExample);

            if (finishedDividendList.isEmpty()) {//无已派息单
            	curMonthHasFinished = false;
            }else {
                // 最后已派息单
            	VipDividend lastFinishedDividend = finishedDividendList.get(0);
            	lastFinishedDividend = vipDividendMapper.selectByPrimaryKeyForUpdate(lastFinishedDividend.getId());

                if (lastFinishedDividend.getDividendDate().before(curMonthBegin)) { //[最后已派息单].[应派息时间]在[当月初]之前
                	curMonthHasFinished= false;//当月未派息
                } else {  //[最后已派息单].[应派息时间]在[当月初]之后

                    curMonthHasFinished = true;//当月已派息
                }
            }

            // 查询用户未派息记录
            VipDividendExample unfinishedExample = new VipDividendExample();
            unfinishedExample.createCriteria()
                    .andUserIdEqualTo(userId)
                    .andIsDividendEqualTo(false);
            List<VipDividend> unfinishedDividendList = vipDividendMapper.selectByExample(unfinishedExample);
            //未派息单
            VipDividend unfinishedDividend=null;
            if (!unfinishedDividendList.isEmpty()) { // 存在未派息单

            	if(unfinishedDividendList.size()>1) { // 未息单超过一条
            		throw new Exception("该用户派息任务异常！");
            	}

                unfinishedDividend = unfinishedDividendList.get(0);
                unfinishedDividend = vipDividendMapper.selectByPrimaryKeyForUpdate(unfinishedDividend.getId());

                if (unfinishedDividend.getIsDividend()) {//查未派息,得已派息,数据被修改
                	throw new LockFailureException();
                }
                if( unfinishedDividend.getDividendDate().before(today) || unfinishedDividend.getDividendDate().equals(today)) {//应派息日在今天之前(包含今天),但未派息,先派息后再更新
                	throw new Exception("该用户今天之前存在未完成的派息任务！");
                }

                haveUnfinishedDividends = true;
            } else { // 不存在未派息记录，标记插入一条新的派息任务
            	haveUnfinishedDividends = false;
            }

            if(!curMonthHasFinished && curPeriodDividendDate.after(today) ) {//当月未派息,且 当期派息日在今天以后

            }else {
            	Calendar calendar = Calendar.getInstance();
                calendar.setTime(curPeriodDividendDate);
                calendar.add(Calendar.MONTH, 1);
            	curPeriodDividendDate=calendar.getTime();
            }
            /*
            if(curMonthHaveFinished || curPeriodDividendDate.before(today) ||  curPeriodDividendDate.equals(today) ) {//当月已派息,或者 当期派息日在今天之前(包含今天)
            	Calendar calendar = Calendar.getInstance();
                calendar.setTime(curPeriodDividendDate);
                calendar.add(Calendar.MONTH, 1);
            	curPeriodDividendDate=calendar.getTime();
            }
            */
            if (haveUnfinishedDividends) { //更新
            	unfinishedDividend.setDividendDate(curPeriodDividendDate);
            	unfinishedDividend.setDividendScale(giveScale);
            	unfinishedDividend.setCreateDate(now);
             	vipDividendMapper.updateByPrimaryKeySelective(unfinishedDividend);
            }else {//插入
            	VipDividend vipDividend=new VipDividend();
            	vipDividend.setDividendDate(curPeriodDividendDate);
                vipDividend.setDividendScale(giveScale);
                vipDividend.setIsDividend(false);
                vipDividend.setUserId(userId);
                vipDividend.setCreateDate(now);
            	vipDividendMapper.insertSelective(vipDividend);
            }

            // 更新 每月利息发放日期 （每月的哪一天发放）
            user.setGiveOutDate(giveOutDay);
            // 更新派息比例
            user.setGiveScale(giveScale);
        } else {
        	// 查询用户未派息记录
            VipDividendExample example3 = new VipDividendExample();
            example3.createCriteria()
                    .andUserIdEqualTo(userId)
                    .andIsDividendEqualTo(false);
            // 删除用户未派息记录
            vipDividendMapper.deleteByExample(example3);
            user.setGiveOutDate(null);
            user.setGiveScale(null);
        }
        user.setLevel(level);
        user.setUpdateDate(now);
        return userMapper.updateByPrimaryKey(user);
    }
    
    @Override
    public List<Map<String, Object>> listMirgationUserReport(String keyword, Date mirgationTime,Integer start, Integer limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("mirgationStartTime", DateUtil.getDayMinTime(mirgationTime));
        params.put("mirgationEndTime", DateUtil.getNextDayMinTime(mirgationTime));
        params.put("start", start);
        params.put("limit", limit);
        return userMapper.listMirgationUserReport(params);
    }
    
    public int countMirgationUserReport(String keyword, Date mirgationTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("mirgationStartTime", DateUtil.getDayMinTime(mirgationTime));
        params.put("mirgationEndTime", DateUtil.getNextDayMinTime(mirgationTime));
        return userMapper.countMirgationUserReport(params);
    }


	@Override
	public void doMigrationImport(MultipartFile file) throws Exception {
		BufferedReader reader = null;
		StringBuffer jsonStringBuffer = new StringBuffer();
		try {
			char[] tempChars = new char[1024];
			int byteread = 0;
			reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
			
			// 显示文件字节数
			UserServiceImpl.showAvailableBytes(file.getInputStream());
			// 读入多个字节到字节数组中，byteread为一次读入的字节数
			while ((byteread = reader.read(tempChars)) != -1) {
				jsonStringBuffer.append(new String(tempChars, 0, byteread));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e2) {
					throw e2;
				}
			}
		}
		String jsonString = jsonStringBuffer.toString();
		MigrationUser migrationUser = new Gson().fromJson(jsonString, MigrationUser.class);
		if (!migrationUser.verify()) {
			logger.info(jsonString.length() + " json:" + jsonString);
			throw new Exception("文件验证失败");
		}

		UserExample example = new UserExample();
		example.createCriteria().andIdentityCardEqualTo(migrationUser.getId_number());

		List<User> userList = userMapper.selectByExample(example);

		if (userList.size() < 1) {
			throw new Exception("未找到对应用户");
		} else if (userList.size() > 1) {// IdentityCard 唯一索引
			throw new Exception("对应用户有多个账户");
		}
		User user = userMapper.selectByPrimaryKeyForUpdate(userList.get(0).getId());

		if (user.getIsMigration() || user.getMigrationTime() != null) {
			throw new Exception("该转移用户数据已导入,不能重复导入");
		}
		// 获取用户账户
		Assets assets = assetsMapper.selectByPrimaryKeyForUpdate(user.getId());
		// 减少用户授信资金
		userAccountService.modifyAccount(assets, BigDecimal.valueOf(migrationUser.getBalance()),
				migrationUser.getId().intValue(), BusinessTableEnum.user, AccountOperateEnum.MIGRATION_BALANCE_ADD);

		// 更新用户资金
		if (assetsMapper.updateByPrimaryKeyAndVersionSelective(assets) == 0) {
			throw new LockFailureException();
		}
		user.setIsMigration(true);
		user.setMigrationTime(migrationUser.getMigration_time());
		userMapper.updateByPrimaryKey(user);

		for (MigrationInvestment migrationInvestment : migrationUser.getInvestmentList()) {
			migrationInvestment.setUserId(user.getId().longValue());
			migrationInvestmentService.getMapper().insert(migrationInvestment);
		}

		for (MigrationInvestmentBill migrationInvestmentBill : migrationUser.getBillList()) {
			migrationInvestmentBill.setUserId(user.getId().longValue());
			migrationInvestmentBillService.getMapper().insert(migrationInvestmentBill);
		}

	}
	
	private static void showAvailableBytes(InputStream in) {
        try {
        	logger.info("当前字节输入流中的字节数为:" + in.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public List<DataSourceSumVo> dataSourceSum(Date userCreateDateStart, Date userCreateDateEnd, Integer departmentId,
			Integer adminId) {
		Map<String,Object> prarm=new HashMap<String,Object>();
		if(userCreateDateStart!=null) {
			prarm.put("userCreateDateStart", userCreateDateStart);
		}
		if(userCreateDateEnd!=null) {
			prarm.put("userCreateDateEnd", userCreateDateEnd);
		}
		if(departmentId!=null) {
			prarm.put("departmentId", departmentId);
		}
		if(adminId!=null) {
			prarm.put("adminId", adminId);
		}
		return userMapper.dataSourceSum(prarm);
	}
}
