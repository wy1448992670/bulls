package com.goochou.p2b.admin.web.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.goochou.p2b.model.*;
import com.goochou.p2b.service.*;
import com.goochou.p2b.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.constant.ConstantsAdmin;
import com.goochou.p2b.constant.project.ProjectRiskEnum;
import com.goochou.p2b.model.vo.DataSourceSumVo;
import com.goochou.p2b.service.AdminLogService;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.BankCardService;
import com.goochou.p2b.service.BankService;
import com.goochou.p2b.service.BannerService;
import com.goochou.p2b.service.CustomerListService;
import com.goochou.p2b.service.DepartmentService;
import com.goochou.p2b.service.EmployService;
import com.goochou.p2b.service.InvestmentService;
import com.goochou.p2b.service.LoginRecordService;
import com.goochou.p2b.service.MessagePushService;
import com.goochou.p2b.service.MigrationInvestmentBillService;
import com.goochou.p2b.service.MigrationInvestmentService;
import com.goochou.p2b.service.PushTaskService;
import com.goochou.p2b.service.TradeRecordService;
import com.goochou.p2b.service.UploadService;
import com.goochou.p2b.service.UserAddressService;
import com.goochou.p2b.service.UserAdminService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.service.UserSignedService;
import com.goochou.p2b.utils.AjaxUtil;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.CommonUtil;
import com.goochou.p2b.utils.DateFormatTools;
import com.goochou.p2b.utils.DateUtil;
import com.google.gson.JsonSyntaxException;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

	private static final Logger logger = Logger.getLogger(UserController.class);

	@Resource
	private UserService userService;
	@Resource
	private AssetsService assetsService;
	@Resource
	private BannerService bannerService;
	@Resource
	private UploadService uploadService;
	/*
	 * @Resource private MQSendAdminLog mqSendMessage;
	 */
	@Resource
	private LoginRecordService loginRecordService;
	@Resource
	private UserSignedService userSignedService;
	@Resource
	private UserAddressService userAddressService;

	@Resource
	private AdminLogService adminLogService;
	@Resource
	private UserAdminService userAdminService;
	@Resource
	private TradeRecordService tradeRecordService;
	@Resource
	private CustomerListService customerListService;
	@Resource
	private BankCardService bankCardService;
	@Resource
	private BankService bankService;
    @Resource
    private InvestmentService investmentService;
    @Resource
    private MessagePushService messagePushService ;
    @Resource
    private PushTaskService pushTaskService;
    @Resource
    private MigrationInvestmentService migrationInvestmentService;
    @Resource
    private MigrationInvestmentBillService migrationInvestmentBillService;
    @Resource
    private EmployService employService;
	@Resource
	private ExportApplyService exportApplyService;
	@Resource
	private AdvertisementChannelService advertisementChannelService;

    @Resource
	private DepartmentService departmentService;

	@RequestMapping(value = "/checkName/app", method = RequestMethod.GET)
	public void checkName(@RequestParam String username, Integer id, HttpServletResponse response) {
		Boolean flag = true;
		try {
			flag = userService.checkNameExists(username, id);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		AjaxUtil.str2front(response, JSON.toJSONString(flag));
	}

	@RequestMapping(value = "/freeze", method = RequestMethod.GET)
	public String add(Integer id, Integer type, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			userService.updateUserStatus(id, type);
			map.put("status", "1");
			AjaxUtil.str2front(response, JSON.toJSONString(map));
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			map.put("status", "0");
			AjaxUtil.str2front(response, JSON.toJSONString(map));
		}
		return "redirect:/user/list/app";
	}

	@RequestMapping(value = "/add/app", method = RequestMethod.GET)
	public String add(@ModelAttribute User user) {
		return "/user/add-app";
	}

	@RequestMapping(value = "/add/app", method = RequestMethod.POST)
	public String add(@ModelAttribute User user, HttpServletRequest request, HttpSession session) {
		try {
			// 这是测试的帐号
			user.setStatus(0); // 0为有效状态
			user.setCreateDate(new Date());
			user.setRegisterIp(CommonUtil.getIpAddr(request));
			userService.save(user, null, ((UserAdmin) session.getAttribute("user")).getId());
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return "redirect:/user/list/app";
	}

	@RequestMapping(value = "/edit/app", method = RequestMethod.GET)
	public String edit(Integer id, Model model) {
		try {
		    Subject subject = SecurityUtils.getSubject();
            try {
                subject.checkPermission("user:edit:app");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限查看");
                return "error";
            }
		    
			User user = userService.get(id);
			model.addAttribute("user", user);
			model.addAttribute("departments", departmentService.getAllDepartment());
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return "/user/edit-app";
	}

	@RequestMapping(value = "/edit/app", method = RequestMethod.POST)
	public String edit(Integer id, Double amount, Integer departmentId) {
		try {
//			// 这是测试的帐号
//			User user = userService.get(id);
//			Assets a = user.getAssets();
//			if (user.getStatus() != 3) {
//				return null;
//			}
//            a.setAvailableBalance(a.getAvailableBalance() + amount);
//			assetsService.updateByPrimaryKeyAndVersionSelective(a);
		    
		    //修改用户部门
		    User user = userService.get(id);
		    user.setDepartmentId(departmentId);
		    userService.update(user);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return "redirect:/user/list/app";
	}

	@RequestMapping(value = "/list/app")
	public String query(Model model, String keyword, Integer status, Integer type, Integer level, Integer page,Integer isBankCard, Integer isMigration,
			Double startAmount, Double endAmount, Date startTime, Date endTime, Date investStartTime, Date investndTime,Integer departmentId,
			String codes, String customerName,String inviteKeyword,HttpSession session) {
		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("user:list:app");
			}catch (Exception e) {
				model.addAttribute("error", "您没有权限查看");
				return "error";
			}
			long allstart = System.currentTimeMillis();
			int limit = 20;
			if (page == null) {
				page = 1;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("keyword", StringUtils.trim(keyword));
			map.put("status", status);
			map.put("start", (page - 1) * limit);
			map.put("limit", limit);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			map.put("type", type);
			map.put("level", level);
			map.put("isBankCard", isBankCard);
			map.put("inviteKeyword", inviteKeyword);
			map.put("isMigration", isMigration);
			map.put("departmentId", departmentId);
//			Subject subject = SecurityUtils.getSubject();
			UserAdmin admin = (UserAdmin) session.getAttribute("user");
			try {
				subject.checkPermission("user:seealluser");
			} catch (Exception e) {
				map.put("adminId", admin.getId());
			}

			//List<Integer> userIdList = userService.queryDistriblackList();
			long start = System.currentTimeMillis();
			List<Map<String, Object>> list = userService.query1(map);
			long end = System.currentTimeMillis();
			/*
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> userMap = list.get(i);
				//int userId = (int) userMap.get("id");
				//if (userIdList.contains(userId)) {
					userMap.put("nodistri", "1");
				//}
			}*/
			logger.info("select" + (end - start) + "ms");
			start = System.currentTimeMillis();
			Integer count = userService.queryCount1(map);
			end = System.currentTimeMillis();
			logger.info("count" + (end - start) + "ms");
			int pages = calcPage(count, limit);
			
			model.addAttribute("pages", pages);
			model.addAttribute("users", list);
			model.addAttribute("page", page);
			model.addAttribute("keyword", keyword);
			model.addAttribute("status", status);
			model.addAttribute("startTime", startTime);
			model.addAttribute("endTime", endTime);
			model.addAttribute("level", level);
			model.addAttribute("investStartTime", investStartTime);
			model.addAttribute("investndTime", investndTime);
			model.addAttribute("type", type);
			model.addAttribute("isBankCard", isBankCard);
			model.addAttribute("startAmount", startAmount);
			model.addAttribute("endAmount", endAmount);
			model.addAttribute("codes", "".equals(StringUtils.trim(codes)) ? null : codes);
			model.addAttribute("customerName", customerName);
			model.addAttribute("inviteKeyword", inviteKeyword);
			model.addAttribute("isMigration", isMigration);
			model.addAttribute("departmentId", departmentId);
			model.addAttribute("departments", departmentService.getAllDepartment());
			long allend = System.currentTimeMillis();
			logger.info("all" + (allend - allstart) + "ms");
			return "/user/list-app";
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}


	@RequestMapping(value = "/export/list/app", method = RequestMethod.GET)
	public void exportListApp(Model model, Integer applyId, HttpServletResponse response, HttpSession session) {
		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("exportApply:download");
			}catch (Exception e) {
				model.addAttribute("error", "您没有权限操作");
				throw new Exception("您没有权限操作");
			}
			Date now = new Date();

			ExportApply exportApply = exportApplyService.getMapper().selectByPrimaryKey(applyId);
			if (exportApply == null) {
				// 申请单不存在
				model.addAttribute("error", "申请单不存在");
				throw new Exception("申请单不存在");
			}
			if (exportApply.getExpireTime() == null) {
				model.addAttribute("error", "数据异常");
				throw new Exception("数据异常");
			}
			if (now.after(exportApply.getExpireTime())) {
				// 导出操作已过期
				model.addAttribute("error", "导出操作已过期");
				throw new Exception("导出操作已过期");
			}
			List<ExportApplyCondition> exportApplyConditions = exportApplyService.listApplyCondition(exportApply.getId());
			List<ExportApplyColumns> exportApplyColumns = exportApplyService.listApplyColumns(exportApply.getId());
			if (exportApplyColumns.isEmpty()) {
				// 导出数据列为空
				model.addAttribute("error", "导出数据列为空");
				throw new Exception("导出数据列为空");
			}

			Map<String, Object> params = exportApplyService.formatSearchParams(exportApplyConditions);
			List<Map<String, Object>> list = userService.query1(params);
			List<Map<String, Object>> result = new ArrayList<>();

			if (!list.isEmpty()) {
				for (Map<String, Object> map : list) {
					if (exportApplyColumns.isEmpty()) {
						continue;
					}
					Map<String, Object> tmp = new HashMap<>();
					formatExportData(map, exportApplyColumns, tmp);
					result.add(tmp);
				}
			}
			LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<>();
			if (!exportApplyColumns.isEmpty()) {
				for (ExportApplyColumns column : exportApplyColumns) {
					propertyHeaderMap.put(column.getColCode(), column.getColName());
				}
			}
			String title = "网站用户列表导出";
			HSSFExcelUtils.ExpExs(title, propertyHeaderMap, result, response);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}finally{
			DownloadUtils.closeResponseOutput(response);
		}
	}

	private void formatExportData(Map<String, Object> userInfo, List<ExportApplyColumns> columns, Map<String, Object> result) {
		if (columns == null || columns.isEmpty()) {
			return;
		}
		for (String key : userInfo.keySet()) {
//			if (!Arrays.asList(columns).contains(key)) {
//				continue;
//			}
			// 网站用户列表中的某一列的值
			Object value = userInfo.get(key);
			for (ExportApplyColumns column : columns) {
				// 如果匹配到导出申请设定列
				if (column.getColCode().equalsIgnoreCase(key)) {
					// 是否配置加密
					if (column.getIsEncrypt()!= null && column.getIsEncrypt()) {
						String tmpValue = String.valueOf(value) ;
						if (StringUtils.isBlank(tmpValue)) {
							continue;
						}
						// 真实姓名加密
						if ("true_name,trueName".contains(key)) {
							value = tmpValue.trim().substring(0, 1) + "**";
						}
						// 电话号码加密
						if ("phone".contains(key)) {
							value = tmpValue.trim().substring(0, 3) + "****" + tmpValue.trim().substring(tmpValue.length() - 4, tmpValue.length());
						}
					}
					break;
				}
			}
			value = this.formatMsg(key, value);
			// 回填处理后的数据
			result.put(key, value);
		}
	}

	private Object formatMsg(String key, Object value) {
		if (StringUtils.isBlank(key) || value == null) {
			return value;
		}
		String message = value.toString();
		if ("status".equalsIgnoreCase(key)) {
			int tempValue = Integer.parseInt(value.toString());
			if (tempValue == 0) {
				message = "可用";
			} else if (tempValue == 1) {
				message = "不可用";
			} else {
				message = "已删除";
			}
		} else if ("LEVEL".equalsIgnoreCase(key)) {
			int tempValue = Integer.parseInt(value.toString());
			if (tempValue == 0) {
				message = "普通用户";
			} else if (tempValue == 1) {
				message = "会员用户";
			} else {
				message = "vip用户";
			}
		} else if ("isBankCard".equalsIgnoreCase(key) || "ct".equalsIgnoreCase(key)) {
			int tempValue = Integer.parseInt(value.toString());
			if (tempValue == 0) {
				message = "未绑卡";
			} else {
				message = "已绑卡";
			}
		} else if ("sex".equalsIgnoreCase(key)) {
			int tempValue = Integer.parseInt(value.toString());
			if (tempValue == 0) {
				message = "女";
			} else if (tempValue == 1) {
				message = "男";
			} else if (tempValue == 2) {
				message = "保密";
			} else {
				message = "未知";
			}
		} else if ("status".equalsIgnoreCase(key)) {
			int tempValue = Integer.parseInt(value.toString());
			if (tempValue == 0) {
				message = "可用";
			} else if (tempValue == 1) {
				message = "已冻结";
			} else {
				message = "已删除";
			}
		}
		return message;
	}


	/**
	 * 最近一周需要分配的用户
	 *
	 * @param model
	 * @param keyword
	 * @param status
	 * @param type
	 * @param level
	 * @param page
	 * @param startAmount
	 * @param endAmount
	 * @param startTime
	 * @param endTime
	 * @param investStartTime
	 * @param investndTime
	 * @param codes
	 * @param customerName
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/list/app/distribute")
	public String distriquery(Model model, String keyword, Integer status, Integer type, Integer level, Integer page,
			Double startAmount, Double endAmount, Date startTime, Date endTime, Date investStartTime, Date investndTime,
			String codes, String customerName, HttpSession session) {
		try {
			long allstart = System.currentTimeMillis();
			int limit = 20;
			if (page == null) {
				page = 1;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("keyword", StringUtils.trim(keyword));
			map.put("status", status);
			map.put("start", (page - 1) * limit);
			map.put("limit", limit);

			if (null == startTime || endTime == null) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, 1);
				Date nowTime = cal.getTime();
				cal.add(Calendar.DATE, -7);
				Date date = cal.getTime();
				String startDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String endDate = new SimpleDateFormat("yyyy-MM-dd").format(nowTime);
				startTime = DateUtil.parse(startDate, "yyyy-MM-dd");
				endTime = DateUtil.parse(endDate, "yyyy-MM-dd");
			}
			map.put("startTime", startTime);
			map.put("endTime", endTime);

			map.put("type", type);
			map.put("investStartTime", investStartTime);
			map.put("investndTime", investndTime);
			map.put("startAmount", startAmount);
			map.put("endAmount", endAmount);
			map.put("level", level);
			map.put("code", "".equals(StringUtils.trim(codes)) ? null : codes);
			map.put("customerName", customerName);

			List<Integer> userIdList = userService.queryDistriblackList();
			long start = System.currentTimeMillis();
			List<Map<String, Object>> list = userService.query1(map);
			long end = System.currentTimeMillis();
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> userMap = list.get(i);
				int userId = (int) userMap.get("id");
				if (userIdList.contains(userId)) {
					userMap.put("nodistri", "1");
				}
			}
			logger.info("select" + (end - start) + "ms");
			start = System.currentTimeMillis();
			Integer count = userService.queryCount1(map);
			end = System.currentTimeMillis();
			logger.info("count" + (end - start) + "ms");
			int pages = calcPage(count, limit);

			model.addAttribute("pages", pages);
			model.addAttribute("users", list);
			model.addAttribute("page", page);
			model.addAttribute("keyword", keyword);
			model.addAttribute("status", status);
			model.addAttribute("startTime", startTime);
			model.addAttribute("endTime", endTime);
			model.addAttribute("level", level);
			model.addAttribute("investStartTime", investStartTime);
			model.addAttribute("investndTime", investndTime);
			model.addAttribute("type", type);
			model.addAttribute("startAmount", startAmount);
			model.addAttribute("endAmount", endAmount);
			model.addAttribute("codes", "".equals(StringUtils.trim(codes)) ? null : codes);
			model.addAttribute("customerName", customerName);

			long allend = System.currentTimeMillis();
			logger.info("all" + (allend - allstart) + "ms");
			return "/user/list-app-distribution";
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/list/app/customer")
	public String queryAppCustomer(Model model, Integer id, HttpSession session) {
		try {
			if (null != id && !"".equals(id)) {
				UserAdmin admin = (UserAdmin) session.getAttribute("user");
				int count = customerListService.queryMyCustomer(id, admin.getId());
				if (count > 0) {
					User user = userService.get(id);
					model.addAttribute("userCustomer", user);
					model.addAttribute("id", id);
				} else {
					model.addAttribute("error", "账号不存在或不属于你的客户");
				}
			}
			return "/user/list-app-customer";
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/detail/app", method = RequestMethod.GET)
	public String detail(int id, Model model, HttpSession session) {
		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("user:detail:app");
			}catch (Exception e) {
				model.addAttribute("error", "您没有权限查看");
				return "error";
			}
			UserAdmin admin = (UserAdmin) session.getAttribute("user");
			String roleName = userAdminService.getRoleNameByUserId(admin.getId());
			int count = 1;
			if (roleName.equalsIgnoreCase("normalCustomer")) {
				count = customerListService.queryMyCustomer(id, admin.getId());
			}
			if (count > 0) {
				User user = userService.detail(id);
				List<Map<String, Object>> list = userService.detailDing(id);
				List<Map<String, Object>> list2 = userService.countDing(id);
				if (list.size() > 0 && list.get(0) != null) {
					model.addAttribute("map", list.get(0));
				}
				if (list2.size() > 0 && list2.get(0) != null) {
					model.addAttribute("map2", list2.get(0));
				}
				model.addAttribute("user", user);
				ProjectRiskEnum em = ProjectRiskEnum.getProjectRiskByScore(user.getRiskEvaluateScore());
				model.addAttribute("userRisk", em == null ? null : em.getFeatureName());

				/*// 智投相关数据
				// 智投在投本金
				  Double yyyCapital = assetsService.selectMyYyyAssets(user.getId());
				  model.addAttribute("yyyCapital", yyyCapital); //智投待收收益
				  Double yyyIncome = assetsService.selectMyYyyInterest(user.getId());
				  model.addAttribute("yyyIncome", yyyIncome);

				  //智投投资总笔数
				  Map<String,Object> searchProjectMap = new HashMap<>();
				  searchProjectMap.put("userId", user.getId());
				  searchProjectMap.put("type", InvestmentTypeEnum.YUEYUEYING.getFeatureType());

				  // 用户的总收益
//				  Map<String, Object> map = tradeRecordService.selectAccumulatedIncome(user.getId());
				  Map<String, Object> map = assetsService.selectMyAssets(user.getId());
				  double regularAmount = (Double)map.get("regularAmount");
				  double yyyIncomeAmount = (Double)map.get("yyyIncome");
				  double signAmount = (Double)map.get("signAmount");
				  double hongbaoAmount = (Double)map.get("hongbaoAmount"); double rateCouponAmount =
				  (Double)map.get("rateCouponAmount");
				  Double sumIncome =  BigDecimalUtil.add(regularAmount,yyyIncomeAmount,signAmount,hongbaoAmount, rateCouponAmount);
				  model.addAttribute("sumIncome", sumIncome);*/

				  Assets asset = assetsService.findByuserId(user.getId());
				  Assets assets = assetsService.findByuserId(user.getId());
		          double totalAmount = investmentService.getAmountCount(user.getId());
		          totalAmount  = BigDecimalUtil.add(totalAmount, assets.getBalanceAmount(), assets.getFrozenAmount(), assets.getCreditAmount(), assets.getFreozenCreditAmount());


		          List<Investment> invests = new ArrayList<Investment>();
		          InvestmentExample example = new InvestmentExample();
		          example.createCriteria().andUserIdEqualTo(user.getId()).andOrderStatusNotEqualTo(3);//订单取消的不统计
		          invests = investmentService.getMapper().selectByExample(example);
		          double interestAmount = 0.00;
		           for(Investment i : invests) {
		              //已卖牛
		              if(i.getOrderStatus() == 2) {
		                  interestAmount = BigDecimalUtil.add(interestAmount, i.getInterestAmount());
		              }
		          }
		          model.addAttribute("totalAmount", totalAmount);//总资产
		          model.addAttribute("interestAmount", interestAmount);
				  model.addAttribute("asset", asset);
				  if(user.getChannelId() !=null) {
					  AdvertisementChannel channel = advertisementChannelService.getMapper().selectByPrimaryKey(Integer.parseInt(user.getChannelId()));
					  model.addAttribute("channel", channel);
				  }
				  
			} else {
				model.addAttribute("error", "账号不存在或不属于你的客户");
				return "/error";
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return "/user/detail-app";
	}

	/**
	 * @Description: 查询普通用户
	 * @date 2016/10/10
	 * @author 王信
	 */
	@RequestMapping(value = "/list/app/usable", method = RequestMethod.GET)
	public void detail(String username, HttpServletResponse response) {
		try {
			List<Map<String, Object>> list = userService.query(username, null, null, 0, null, 0, 5);
			AjaxUtil.str2front(response, JSON.toJSONString(list));
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/list/app/queryCustomer", method = RequestMethod.GET)
	public void detailCus(String username, HttpServletResponse response) {
		try {
			List<Map<String, Object>> list = userService.queryCustomer(username, 0, 5);
			AjaxUtil.str2front(response, JSON.toJSONString(list));
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * 进入解绑银行卡
	 *
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/unbund", method = RequestMethod.GET)
	public String unbundCard(Integer userId, Model model) {
		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("user:unbund");
			}catch (Exception e) {
				model.addAttribute("error", "您没有权限操作");
				return "error";
			}
			User user = userService.detail(userId);
//            BankCard bankCard = bankCardService.getByUserId(userId);
//            if (bankCard != null) {
//                model.addAttribute("bankCard", bankCard);
//            } else {
//                model.addAttribute("bankCard", null);
//            }
			List<BankCard> bankCards = new ArrayList<>();
			bankCards = bankCardService.getBankNoByUserId(userId);

			model.addAttribute("user", user);
			model.addAttribute("bankCards", bankCards);
			return "/user/unbund";
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解绑银行卡
	 */
	@RequestMapping(value = "/unbund", method = RequestMethod.POST)
	@ResponseBody
	public String unbund(Integer userId,Integer bankCardId, Model model) {
		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("user:unbund");
			}catch (Exception e) {
				model.addAttribute("error", "您没有权限操作");
				return "error";
			}
			if(bankCardService.get(bankCardId) == null) {
				return "银行卡不存在！";
			}
			bankCardService.updateCardStatusByUserId(userId, bankCardId);
			return "解绑成功！";
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return "系统异常";
		}
	}

	@RequestMapping(value = "/editpayPassword", method = RequestMethod.GET)
	public void editpayPassword(Integer id, HttpSession session, HttpServletResponse response,
			HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			User user = userService.get(id);
			UserAdmin ua = (UserAdmin) session.getAttribute("user");
			userService.updateResetpaypassword(user, "123456");
			AdminLog alog = new AdminLog();
			alog.setAdminId(ua.getId());
			alog.setAdminIp(getIpAddr(request));
			alog.setOperateTime(new Date());
			alog.setLvl(0);
			alog.setRemark("为Id为" + user.getId() + "姓名为:" + user.getTrueName() + "的用户修改了密码");
			adminLogService.save(alog);
			map.put("status", "1");
			AjaxUtil.str2front(response, JSON.toJSONString(map));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", "0");
			AjaxUtil.str2front(response, JSON.toJSONString(map));
		}
	}

	/**
	 * 查询银行卡
	 *
	 * @return
	 */
	@RequestMapping(value = "/bank/card", method = RequestMethod.GET)
	@ResponseBody
	public List<BankCard> getBankCard(Integer userId) {
		try {
			return bankCardService.getByUserId(userId);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询所有提现被锁的用户
	 *
	 * @return
	 * @author 王信
	 * @Create Date: 2015年12月23日上午9:11:31
	 */
	@RequestMapping(value = "withdrawLock", method = RequestMethod.GET)
	public String withdrawLock(Model model, Integer page, String likeSearch) {
		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("user:bank:card");
			}catch (Exception e) {
				model.addAttribute("error", "您没有权限查看");
				return "error";
			}
			int limit = 20;
			if (page == null || page == 0) {
				page = 1;
			}
			List<Map<String, Object>> list = userService.selectLockwithdraw(likeSearch, (page - 1) * limit, limit);
			Integer count = userService.selectLockwithdrawCount(likeSearch);
			int pages = calcPage(count, limit);
			model.addAttribute("page", page);
			model.addAttribute("pages", pages);
			model.addAttribute("likeSearch", likeSearch);
			model.addAttribute("list", list);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return "/user/withdrawLock";
	}

	/**
	 * 删除提现被锁用户
	 *
	 * @return
	 * @author 王信
	 * @Create Date: 2015年12月23日上午9:11:31
	 */
	@RequestMapping(value = "withdrawLockDelete", method = RequestMethod.GET)
	public String withdrawLockDelete(Integer id, Model model) {
		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("user:withdrawLock");
			}catch (Exception e) {
				model.addAttribute("error", "您没有权限操作");
				return "error";
			}
			userService.deleteLockWithdraw(id);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return "redirect:/user/withdrawLock?page=1";
	}

	/**
	 * @Description(描述):登录设备记录查看
	 * @author 王信
	 * @date 2016/5/6
	 * @params
	 **/
	@RequestMapping(value = "loginRecordList", method = RequestMethod.GET)
	public String loginRecordList(Model model, Integer page, String keyword, Date startDate, Date endDate) {
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.checkPermission("user:loginRecordList");
		}catch (Exception e) {
			model.addAttribute("error", "您没有权限查看");
			return "error";
		}
		int limit = 20;
		if (page == null || page == 0) {
			page = 1;
		}
		List<Map<String, Object>> list = loginRecordService.selectAllLoginRecord((page - 1) * limit, limit, keyword,
				startDate, endDate);
		Integer count = loginRecordService.selectAllLoginRecordCount(keyword, startDate, endDate);
		int pages = calcPage(count, limit);
		model.addAttribute("page", page);
		model.addAttribute("pages", pages);
		model.addAttribute("keyword", keyword);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("list", list);
		return "/user/loginRecordList";
	}

	/**
	 * @Description(描述):签到查询
	 * @author 王信
	 * @date 2016/5/11
	 * @params
	 **/
	@RequestMapping(value = "userSignedList", method = RequestMethod.GET)
	public String userSignedList(Model model, Integer page, String keyword, Date startDate, Date endDate,
			HttpSession session) {
		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("user:userSignedList");
			}catch (Exception e) {
				model.addAttribute("error", "您没有权限查看");
				return "error";
			}
			int limit = 20;
			if (page == null || page == 0) {
				page = 1;
			}
			UserAdmin admin = (UserAdmin) session.getAttribute("user");
			Integer adminId = null;
//			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("user:seealluser");
			} catch (Exception e) {
				adminId = admin.getId();
			}
			List<Map<String, Object>> list = userSignedService.selectAllUserSigned((page - 1) * limit, limit, keyword,
					startDate, endDate, adminId);
			Integer count = userSignedService.selectAllUserSignedCount(keyword, startDate, endDate, adminId);
			int pages = calcPage(count, limit);
			model.addAttribute("page", page);
			model.addAttribute("pages", pages);
			model.addAttribute("keyword", keyword);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("list", list);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return "/user/userSignedList";
	}

	/**
	 * @Description(描述):用户地址管理
	 * @author 王信
	 * @date 2016/5/17
	 * @params
	 **/
	@RequestMapping(value = "userAddressList", method = RequestMethod.GET)
	public String userAddressList(Model model, Integer page, String keyword) {
		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("user:userAddressList");
			}catch (Exception e) {
				model.addAttribute("error", "您没有权限查看");
				return "error";
			}
			int limit = 20;
			if (page == null || page == 0) {
				page = 1;
			}
			List<UserAddress> list = userAddressService.selectAllUserAddress((page - 1) * limit, limit, keyword);
			Integer count = userAddressService.selectAllUserAddressCount(keyword);
			int pages = calcPage(count, limit);
			model.addAttribute("page", page);
			model.addAttribute("pages", pages);
			model.addAttribute("keyword", keyword);
			model.addAttribute("list", list);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return "/user/userAddressList";
	}

	/**
	 * @Description(描述):用户地址删除
	 * @author 王信
	 * @date 2016/5/17
	 * @params
	 **/
	@RequestMapping(value = "deleteUserAddress", method = RequestMethod.GET)
	public String deleteUserAddress(Model model, Integer id) {
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.checkPermission("user:userAddressDelete");
		}catch (Exception e) {
			model.addAttribute("error", "您没有权限操作");
			return "error";
		}
		userAddressService.deleteAddress(id);
		return "redirect:/user/userAddressList";
	}

	/**
	 * @Description(描述):新增用户地址
	 * @author 王信
	 * @date 2016/5/17
	 * @params
	 **/
	@RequestMapping(value = "userAddressAdd", method = RequestMethod.POST)
	public String userAddressAdd(Model model, UserAddress userAddress, String keyword) {
		try {
			Subject subject = SecurityUtils.getSubject();
			String addressId = userAddress.getcId();
			if (addressId == null) {
				try {
					subject.checkPermission("user:userAddressAdd");
				}catch (Exception e) {
					model.addAttribute("error", "您没有权限操作");
					return "error";
				}
			} else{
				try {
					subject.checkPermission("user:userAddressEdit");
				}catch (Exception e) {
					model.addAttribute("error", "您没有权限操作");
					return "error";
				}
			}
			User user = userService.getByKeyword(keyword);
			if (user == null) {
				model.addAttribute("flag", 1);
				return "/user/userAddressAdd";
			}
			userAddress.setUserId(user.getId());
			userAddressService.saveUserAddress(userAddress);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return "redirect:/user/userAddressList";
	}

	/**
	 * @Description(描述):查询一个要编辑的地址
	 * @author 王信
	 * @date 2016/5/18
	 * @params
	 **/
	@RequestMapping(value = "userAddressAdds", method = RequestMethod.GET)
	public String userAddressAdds(Model model, Integer id) {
		if (id != null) {
			UserAddress address = userAddressService.getAddressesById(id);
			String username = userService.get(address.getUserId()).getTrueName();
			model.addAttribute("address", address);
			model.addAttribute("username", username);
		}
		return "/user/userAddressAdd";
	}

	@RequestMapping(value = "/messagePush", method = RequestMethod.GET)
	public String messagePush(Model model, Integer page, String keyword, Integer status) {
		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("user:userPush");
			}catch (Exception e) {
				model.addAttribute("error", "您没有权限查看");
				return "error";
			}
			int limit = 20;
			if (page == null || page == 0) {
				page = 1;
			}
			List<MessagePush> list = messagePushService.queryList(keyword, status, (page - 1) * limit, limit);
			Integer count = messagePushService.queryCount(keyword, null);
			int pages = calcPage(count, limit);
			model.addAttribute("page", page);
			model.addAttribute("pages", pages);
			model.addAttribute("keyword", keyword);
			model.addAttribute("status", status);
			model.addAttribute("list", list);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return "/user/messagePush";
	}

	/**
	 * @date 2019年8月15日
	 * @author wangyun
	 * @time 下午2:00:52
	 * @Description 跳转推送消息页面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toEditMessagePush", method = RequestMethod.GET)
	public String toEditMessagePush(Model model, Integer id) {
		if(id != null) {
			try {
				MessagePush messagePush = messagePushService.getMessagePushMapper().selectByPrimaryKey(id);
				if(messagePush == null) {
					throw new Exception("推送消息不存在");
				}
				model.addAttribute("messagePush", messagePush);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
		return "/user/editMessagePush";
	}

	/**
	 * @date 2019年8月15日
	 * @author wangyun
	 * @time 下午1:49:45
	 * @Description 新增/编辑推送消息
	 *
	 * @param request
	 * @param response
	 * @param file
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/editMessagePush", method = RequestMethod.POST)
	public String editMessagePush(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(required = false) MultipartFile file, 
            @RequestParam(required = false) MultipartFile deviceFile,
            Model model, 
            Integer uploadType,
            MessagePush messagePush,String sendTime) {
		 try {
	            Subject subject = SecurityUtils.getSubject();
	            try {
	                subject.checkPermission("user:editMessagePush");
	            }catch (Exception e) {
	                model.addAttribute("error", "您没有权限操作");
	                return "error";
	            }
	            List<String> list = new LinkedList<>();
	            if (!file.isEmpty() || !deviceFile.isEmpty()) {
	            	InputStreamReader isr = null;
	            	if(!file.isEmpty()) {
	            		isr = new InputStreamReader(file.getInputStream(),"UTF-8");
	            	} else if(!deviceFile.isEmpty()) {
	            		isr = new InputStreamReader(deviceFile.getInputStream(),"UTF-8");
	            	}
	                BufferedReader reader = new BufferedReader(isr);
	                String str = new String();
	                int i = 0;
	                while (i < 10) {
	                    str = reader.readLine();
	                    if (StringUtils.isEmpty(str)) {
	                        i++;
	                        continue;
	                    }
	                    //BufferedReader.readLine(),读取第一行会出现bug,首行第一个字符会是一个空字符
	                    //文件第一行的首个字符,而是一个空字符(不是空字符串),但读取第二行就不会出现这问题。
	                    //把第一行的第一个字符去掉。
	                    char s =str.trim().charAt(0);
	                    if(s == 65279){
	                    	if(str.length() > 1){
	                    		str=str.substring(1);
	                    	}
	                    }
	                    list.add(str);
	                }
	                reader.close();
	            }
	            messagePush.setSendTime(StringUtils.isEmpty(sendTime) ? null : DateUtil.parse(sendTime, "yyyy-MM-dd HH:mm:ss"));// 如果不设置定时发送,则即时发送
	            messagePushService.addMessagePush(list, messagePush,uploadType);

	        } catch (Exception e) {
	            logger.error(e);
	            e.printStackTrace();
	        }
		return "redirect:/user/messagePush";
	}

	@RequestMapping(value = "/bankManagement", method = RequestMethod.GET)
	public String bankManagement(Model model, Integer page, String keyword) {
		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("user:bankManagement");
			}catch (Exception e) {
				model.addAttribute("error", "您没有权限查看");
				return "error";
			}
			int limit = 20;
			if (page == null || page == 0) {
				page = 1;
			}
			List<Bank> list = bankService.query(keyword, (page - 1) * limit, limit);
			Integer count = bankService.queryCount(keyword);
			int pages = calcPage(count, limit);
			model.addAttribute("page", page);
			model.addAttribute("pages", pages);
			model.addAttribute("keyword", keyword);
			model.addAttribute("list", list);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return "/user/bankManagement";
	}

	@RequestMapping(value = "/editBankManagement", method = RequestMethod.GET)
	public String editBankManagement(Model model, Integer id) {
		try {
			Bank bank = bankService.get(id);
			model.addAttribute("bank", bank);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return "/user/editBankManagement";
	}

	@RequestMapping(value = "/editBankManagement", method = RequestMethod.POST)
	public String editBankManagement(Model model, Integer id, String announcement, String name, String note,
			Double bindDailyMaxAmount, Double bindSingleMaxAmount, Double bindSingleMinAmount,
			Double firstBindMaxAmount) {
		try {
			Subject subject = SecurityUtils.getSubject();
			Bank bank = new Bank();
			if (id == null) {
				try {
					subject.checkPermission("user:bankManagement:add");
				}catch (Exception e) {
					model.addAttribute("error", "您没有权限操作");
					return "error";
				}
				bank.setId(id);
				bank.setAnnouncement(announcement);
				bank.setName(name);
				bank.setNote(note);
				bank.setBindDailyMaxAmount(bindDailyMaxAmount);
				bank.setBindSingleMaxAmount(bindSingleMaxAmount);
				bank.setBindSingleMinAmount(bindSingleMinAmount);
				bank.setFirstBindMaxAmount(firstBindMaxAmount);
			} else {
				try {
					subject.checkPermission("user:bankManagement:edit");
				}catch (Exception e) {
					model.addAttribute("error", "您没有权限操作");
					return "error";
				}
				bank = bankService.get(id);
				bank.setAnnouncement(announcement);
				bank.setName(name);
				bank.setNote(note);
				bank.setBindDailyMaxAmount(bindDailyMaxAmount);
				bank.setBindSingleMaxAmount(bindSingleMaxAmount);
				bank.setBindSingleMinAmount(bindSingleMinAmount);
				bank.setFirstBindMaxAmount(firstBindMaxAmount);
			}
			bankService.save(bank);

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return "redirect:/user/editBankManagement?id=" + id;
	}

	/**
	 * 用户详情-银行卡信息
	 *
	 * @author sxy
	 * @param response
	 * @param page
	 * @param userId
	 */
	@RequestMapping(value = "/listBankAjax", method = RequestMethod.GET)
	public void listBankAjax(HttpServletResponse response, Integer page, Integer userId) {
		try {
			int limit = 5;
			page = page == null ? 1 : page;

			List<Map<String, Object>> userBankList = bankCardService.listUserBank((page - 1) * limit, limit, userId);
			int count = bankCardService.getBankCardCountByUserId(userId);

			int pages = calcPage(count, limit);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("page", page);
			map.put("pages", pages);
			map.put("list", userBankList);
			AjaxUtil.str2front(response, JSON.toJSONString(map));
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * 禁言用户发言
	 *
	 * @Title: bannedUserAjax
	 * @param response
	 * @param userId
	 * @param isForbidComment
	 * @return Object
	 * @author zj
	 * @date 2019-06-05 17:57
	 */
	@RequestMapping(value = "/bannedUserAjax")
	@ResponseBody
	public Object bannedUserAjax(HttpServletResponse response, Integer userId, Integer isForbidComment) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			userService.doBannedUser(userId, isForbidComment);
			map.put("code", 1);
			map.put("msg", "禁言成功");
			return map;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			map.put("code", -1);
			map.put("msg", "禁言失败");
			return map;
		}
	}


	/**
	 * 设置会员，以及派息日期和派息比例 <br/>
	 * <>
	 * @author shuys
	 * @date 2019/10/9
	 * @param response
	 * @param model
	 * @param userId
	 * @param giveOutDay  每月利息发放日期(月份的天数)
	 * @param giveScale 利息发放比例
	 * @param level 会员等级
	 * @return java.lang.Object
	*/
	@RequestMapping(value = "/setVipUserAjax", method = RequestMethod.POST)
	@ResponseBody
	public Object setVip(HttpServletResponse response, Model model, Integer userId, Integer giveOutDay, BigDecimal giveScale, Integer level) {
		Map<String, Object> map = new HashMap<>(16);
		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("user:setVip:app");
			}catch (Exception e) {
				model.addAttribute("error", "您没有权限操作");
				return "error";
			}
			if (level != 0 && level != 1 && level != 2) {
				map.put("code", -1);
				map.put("msg", "错误的会员等级");
				return map;
			}
			if (level == 2) {
				if (giveOutDay < 1 || giveOutDay > 28) {
					map.put("code", -1);
					map.put("msg", "错误的每月利息发放日期");
					return map;
				}
				if (giveScale.compareTo(BigDecimal.ZERO) <= 0 || giveScale.compareTo(BigDecimal.ZERO) > 100) {
					map.put("code", -1);
					map.put("msg", "错误的利息发放比例");
					return map;
				}
			} else {
				giveOutDay = null;
				giveScale = null;
			}
			userService.doSetVipUser(userId, giveOutDay, giveScale, level);
			map.put("code", 1);
			map.put("msg", "操作成功");
			return map;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			map.put("code", -1);
			map.put("msg", e.getMessage());
			return map;
		}
	}
	
	/**
	 * 迁移用户投资列表
	 * @author sxy
	 * @param id
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/listMigrationInvestment", method = RequestMethod.GET)
    public String listMigrationInvestment(Integer id, Model model, HttpSession session) {
        try {
            List<MigrationInvestmentView> listMigrationInvestment = migrationInvestmentService.listMigrationInvestment(id, null, null, null, null);
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for(MigrationInvestmentView migrationInvestment : listMigrationInvestment) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", migrationInvestment.getId());
                map.put("bidTitle", migrationInvestment.getBidTitle());
//                if(migrationInvestment.getRepaymentTypeId() == 1) {
//                    map.put("repaymentType", "按月还款、等额本息");
//                } else if(migrationInvestment.getRepaymentTypeId() == 2) {
//                    map.put("repaymentType", "按月付息、到期还本");
//                } else if(migrationInvestment.getRepaymentTypeId() == 3) {
//                    map.put("repaymentType", "一次性还款");
//                }
                map.put("repaymentType", migrationInvestment.getRepaymentTypeId());
                if(migrationInvestment.getPeriodUnit() == -1) {
                    map.put("period", migrationInvestment.getPeriod() + "年");
                } else if(migrationInvestment.getPeriodUnit() == 0) {
                    map.put("period", migrationInvestment.getPeriod() + "月");
                } else if(migrationInvestment.getPeriodUnit() == 1) {
                    map.put("period", migrationInvestment.getPeriod() + "日");
                }
                map.put("createTime", migrationInvestment.getCreateTime() != null ? DateFormatTools.dateToStr2(migrationInvestment.getCreateTime()) : "");
                map.put("loanTime", migrationInvestment.getLoanTime() != null ? DateFormatTools.dateToStr2(migrationInvestment.getLoanTime()) : "");
                map.put("apr", migrationInvestment.getApr() != null ? migrationInvestment.getApr() + "%" : "");
                map.put("interest", migrationInvestment.getInterest());
                map.put("increaseRate", migrationInvestment.getIncreaseRate() != null ? migrationInvestment.getIncreaseRate() + "%" : "");
                map.put("increaseRateInterest", migrationInvestment.getIncreaseInterest());
                map.put("amount", migrationInvestment.getAmount());
                map.put("redAmount", migrationInvestment.getRedAmount());
                map.put("finishTime", migrationInvestment.getFinishedTime() != null ? DateFormatTools.dateToStr2(migrationInvestment.getFinishedTime()) : "");
                
                list.add(map);
            }
            
            model.addAttribute("list", list);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }

        return "user/listMigrationInvestment";
    }
	
	/**
	 * 迁移用户投资回款账单
	 * @author sxy
	 * @param response
	 * @param migrationInvestmentId
	 */
	@RequestMapping(value = "/listMigrationInvestmentBillAjax", method = RequestMethod.GET)
    public void listMigrationInvestmentBillAjax(HttpServletResponse response, Integer migrationInvestmentId) {
        try {
            List<MigrationInvestmentBill> listBill = migrationInvestmentBillService.listMigrationInvestmentBill(migrationInvestmentId);
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for(MigrationInvestmentBill bill : listBill) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", bill.getId());
                map.put("periods", bill.getPeriods());
                map.put("receiveCorpus", bill.getReceiveCorpus());
                map.put("receiveInterest", bill.getReceiveInterest());
                map.put("increaseInterest", bill.getReceiveIncreaseInterest());
                map.put("receiveTime", bill.getReceiveTime() != null ? DateFormatTools.dateToStr2(bill.getReceiveTime()) : "");
                map.put("realReceiveTime", bill.getRealReceiveTime() != null ? DateFormatTools.dateToStr2(bill.getRealReceiveTime()) : "");
                if(bill.getIsReceive()) {
                    map.put("status", "已回款");
                } else {
                    map.put("status", "未回款");
                }
                
                list.add(map);
            }
            
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("list", list);
            
            AjaxUtil.str2front(response, JSON.toJSONString(resultMap));
        } catch (Exception e) {
            logger.error(e);
        }
    }
	
	
	@RequestMapping(value = "/migrationImport", method = RequestMethod.POST)
	public void migrationImport(@RequestParam MultipartFile file, HttpServletResponse response,HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("==========进入 com.goochou.p2b.admin.web.controller.UserController.migrationImport  ======== ");
		
		try {
			Subject subject = SecurityUtils.getSubject();
			subject.checkPermission("user:migrationImport");
			
			String fileType="";
			if(file.getOriginalFilename().lastIndexOf(".")!=-1) {
				fileType=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
			}
			if(!fileType.equals("mujson")) {
				map.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
				map.put(ConstantsAdmin.MESSAGE, fileType+"请上传mujson文件");
			}else {
				try {
					userService.doMigrationImport(file);
					logger.info("==========结束 com.goochou.p2b.admin.web.controller.UserController.migrationImport  ======== ");
					map.put(ConstantsAdmin.STATUS, ConstantsAdmin.SUCCESS);
					map.put(ConstantsAdmin.MESSAGE, "迁移成功");
				}catch(JsonSyntaxException e) {
					map.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
					map.put(ConstantsAdmin.MESSAGE, "数据格式有误");
					logger.error("ccom.goochou.p2b.admin.web.controller.UserController.migrationImport 导入迁移用户错误========>"+e.getMessage());
				}catch (Exception e) {
					map.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
					map.put(ConstantsAdmin.MESSAGE, e.getMessage());
					logger.error("ccom.goochou.p2b.admin.web.controller.UserController.migrationImport 导入迁移用户错误========>"+e.getMessage(),e);
				}
			}
			
		}catch (Exception e) {
			map.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
			map.put(ConstantsAdmin.MESSAGE, "你没权限");
		}
		
		AjaxUtil.str2front(response, JSON.toJSONString(map));
	}


	@RequestMapping(value = "/addSalesman", method = RequestMethod.POST)
	@ResponseBody
	public void addSalesman(Model model,Employ employ, HttpSession session, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String mobile = employ.getMobile();
			boolean isMobile = userService.checkPhone(mobile);
			if(!isMobile) {
				map.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
				map.put(ConstantsAdmin.MESSAGE, "手机号格式错误");
				AjaxUtil.str2front(response, JSON.toJSONString(map));
				return;
			}

			//校验手机号是否已经添加
			boolean flag = employService.checkMobileExsits(mobile.trim());
			if(flag) {
				map.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
				map.put(ConstantsAdmin.MESSAGE, "手机号已存在");
				AjaxUtil.str2front(response, JSON.toJSONString(map));
				return;
			}

			if(employService.addEmploy(employ) != 1) {
				map.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
				map.put(ConstantsAdmin.MESSAGE, "保存信息异常");

			} else {
				map.put(ConstantsAdmin.STATUS, ConstantsAdmin.SUCCESS);
				map.put(ConstantsAdmin.MESSAGE, "添加成功");
			}

		} catch (Exception e) {
			map.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
			map.put(ConstantsAdmin.MESSAGE, e.getMessage());
		}
		AjaxUtil.str2front(response, JSON.toJSONString(map));
	}
	
//list-app-relation
	
	@RequestMapping(value = "/list/addrelation")
	@ResponseBody
	public JSONObject userListForAdd(Model model,Employ employ,String keyword, HttpSession session, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		
		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("user:list:app");
			}catch (Exception e) {
				json.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
				json.put(ConstantsAdmin.MESSAGE, "您没有权限查看");
				return json;
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("keyword", StringUtils.trim(keyword)); 
			
			UserAdmin admin = (UserAdmin) session.getAttribute("user");
			try {
				subject.checkPermission("user:seealluser");
			} catch (Exception e) {
				map.put("adminId", admin.getId());
			}
			
			List<Map<String, Object>> list = userService.query1(map);
			json.put("users", list);
			json.put("keyword", keyword);
			json.put(ConstantsAdmin.STATUS, ConstantsAdmin.SUCCESS);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			json.put(ConstantsAdmin.MESSAGE, "搜索用户异常");
		}
		return json;
	}
	
	/**
	 * @desc 分配用户
	 * @author wangyun
	 * @param model
	 * @param userIds
	 * @param empId
	 * @param page
	 * @param session
	 * @param response
	 */
	@RequestMapping(value = "/addRelationUser", method = RequestMethod.POST)
	@ResponseBody
	public void addRelationUser(Model model, Integer userIds[],Integer empId, 
			Integer page, HttpSession session, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Subject subject = SecurityUtils.getSubject();
			
			try {
				subject.checkPermission("user:addRelationUser");
			}catch (Exception e) {
				map.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
				map.put(ConstantsAdmin.MESSAGE, "您没有权限分配");
				return;
			}
			
			if(userIds == null || userIds.length == 0) {
				map.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
				map.put(ConstantsAdmin.MESSAGE, "用户ID不能为空");
				AjaxUtil.str2front(response, JSON.toJSONString(map));
				return;
			}
			
			List<Integer> userIdList = Arrays.asList(userIds);
			employService.addRelationEmployUser(userIdList, empId);
			map.put(ConstantsAdmin.STATUS, ConstantsAdmin.SUCCESS);
			map.put(ConstantsAdmin.MESSAGE, "分配成功");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			map.put(ConstantsAdmin.STATUS, ConstantsAdmin.ERROR);
			map.put(ConstantsAdmin.MESSAGE, e.getMessage());
		}
		AjaxUtil.str2front(response, JSON.toJSONString(map)); 
	}



	@RequestMapping(value = "/dataSourceSum", method = RequestMethod.GET)
	public String dataSourceSum(Model model,HttpSession session,
			Date userCreateDateStart, Date userCreateDateEnd,Integer departmentId) {
		logger.info("==========进入 com.goochou.p2b.admin.web.controller.UserController.dataSourceSum  ======== ");

		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("user:dataSourceSum");
            }catch (Exception e) {
                model.addAttribute("error", "您没有权限操作");
                return "error";
            }
			UserAdmin admin = (UserAdmin) session.getAttribute("user");
            Integer adminId=admin.getId();
            Integer adminDepartmentId=admin.getDepartmentId();
			try {
				subject.checkPermission("user:seealluser");
				adminId=null;
				adminDepartmentId=0;
			} catch (Exception e) {
			}

    		List<DataSourceSumVo> list=userService.dataSourceSum(userCreateDateStart, userCreateDateEnd, departmentId,adminId);

    		model.addAttribute("list", list);
    		model.addAttribute("userCreateDateStart", userCreateDateStart!= null ? DateFormatTools.dateToStr2(userCreateDateStart) : null);
			model.addAttribute("userCreateDateEnd", userCreateDateEnd!= null ? DateFormatTools.dateToStr2(userCreateDateEnd) : null);
    		model.addAttribute("departmentId", departmentId);
    		model.addAttribute("departments", departmentService.getShowDepartments(adminDepartmentId));
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
            e.printStackTrace();
		}

		return "/user/dataSourceSum";
	}
}
