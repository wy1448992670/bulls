package com.goochou.p2b.admin.web.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.goochou.p2b.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.goochou.p2b.admin.annotatioin.Token;
import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.ConstantsAdmin;
import com.goochou.p2b.constant.ProjectDaysEnum;
import com.goochou.p2b.constant.ProjectTypeEnum;
import com.goochou.p2b.constant.pasture.ProjectStatusEnum;
import com.goochou.p2b.dao.CronTriggersMapper;
import com.goochou.p2b.model.AppVersionContent;
import com.goochou.p2b.model.AppVersionContentWithBLOBs;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.BaseResult;
import com.goochou.p2b.model.Enterprise;
import com.goochou.p2b.model.Product;
import com.goochou.p2b.model.ProductProperty;
import com.goochou.p2b.model.Project;
import com.goochou.p2b.model.ProjectAccount;
import com.goochou.p2b.model.ProjectActivitySetting;
import com.goochou.p2b.model.ProjectClass;
import com.goochou.p2b.model.ProjectContract;
import com.goochou.p2b.model.ProjectContractWithBLOBs;
import com.goochou.p2b.model.ProjectCreditor;
import com.goochou.p2b.model.ProjectLifePicture;
import com.goochou.p2b.model.ProjectPicture;
import com.goochou.p2b.model.Prompt;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.model.VideoAlbum;
import com.goochou.p2b.model.vo.BondPayVO;
import com.goochou.p2b.model.vo.OrderChangeVO;
import com.goochou.p2b.model.vo.ProjectView;
import com.goochou.p2b.model.vo.bulls.BuyBullsDetailMoneyVO;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.DepartmentService;
import com.goochou.p2b.service.EnterpriseService;
import com.goochou.p2b.service.ProductPropertyService;
import com.goochou.p2b.service.ProductService;
import com.goochou.p2b.service.ProjectAccountService;
import com.goochou.p2b.service.ProjectActivitySettingService;
import com.goochou.p2b.service.ProjectClassService;
import com.goochou.p2b.service.ProjectPictureService;
import com.goochou.p2b.service.ProjectService;
import com.goochou.p2b.service.TmDictService;
import com.goochou.p2b.service.UserService;

@Controller
@RequestMapping(value = "/project")
public class ProjectController extends BaseController {
	private static final Logger logger = Logger.getLogger(ProjectController.class);
	@Resource
	private ProjectService projectService;
	@Resource
	private ProjectPictureService projectPictureService;
	@Resource
	private UserService userService;
	@Resource
	private ProjectAccountService projectAccountService;
	@Resource
	private AssetsService assetsService;
	@Resource
	private ProjectClassService projectClassService;
	@Resource
	private ProjectActivitySettingService projectActivitySettingService;

	@Resource
	private EnterpriseService enterpriseService;

	@Resource
	private CronTriggersMapper cronTriggersMapper;

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductPropertyService productPropertyService;
	@Autowired
	private TmDictService tmDictService;
	@Resource
    private DepartmentService departmentService;
	
	@RequestMapping(value = "/list")
	public String query(Model model, HttpServletResponse response,
			@RequestParam(required = false) List<Integer> status,@RequestParam(required = false) Integer noob, 
			@RequestParam(required = false) Integer limitDays, @RequestParam(required = false) String keyword,
			@RequestParam(required = false) Date createTimeStart,	@RequestParam(required = false) Date createTimeEnd,
			@RequestParam(required = false) Date payTimeStart,	@RequestParam(required = false) Date payTimeEnd,
			@RequestParam(required = false) Date buybackTimeStart,	@RequestParam(required = false) Date buybackTimeEnd,
			@RequestParam(required = false) Date dueTimeStart,	@RequestParam(required = false) Date dueTimeEnd,
			@RequestParam(required = false) Date feedTime, @RequestParam(required = false) Integer buyAgain,
			@RequestParam(required = false) Integer projectType, @RequestParam(required = false) Integer page, 
			@RequestParam(required = false) String desc) {
		String result="/project/list";
		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("project:view");
			} catch (Exception e) {
				model.addAttribute("error", "您没有权限查看");
				return "error";
			}
			if(status!=null && status.size()==0) {
				status=null;
			}
			/*
			if(status!=null) {
				System.out.println("status="+StringUtils.join(status, ","));
			}*/
			
			int limit = 10;
			if (page == null) {
				page = 1;
			}
			if (null == desc || "".equals(desc)) {
				desc = "desc";
			}
			
			//2019-08-21 zqq修改
			/*
			List<Project> list = projectService.queryList(keyword, null, null, noob, limitDays, (page - 1) * limit,
					limit, "create_date", desc, "", "");
			*/
			
			this.projectList(false,model,response,
					status,noob,limitDays,keyword,
					createTimeStart,createTimeEnd, payTimeStart,payTimeEnd,
					buybackTimeStart,buybackTimeEnd,dueTimeStart,dueTimeEnd,feedTime, buyAgain, projectType,
					"create_date" ,desc ,page, limit);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return result;
	}

	@RequestMapping(value = "/export")
	public void export(Model model, HttpServletResponse response,
			@RequestParam(required = false) List<Integer> status,@RequestParam(required = false) Integer noob, 
			@RequestParam(required = false) Integer limitDays, @RequestParam(required = false) String keyword,
			@RequestParam(required = false) Date createTimeStart,	@RequestParam(required = false) Date createTimeEnd,
			@RequestParam(required = false) Date payTimeStart,	@RequestParam(required = false) Date payTimeEnd,
			@RequestParam(required = false) Date buybackTimeStart,	@RequestParam(required = false) Date buybackTimeEnd,
			@RequestParam(required = false) Date dueTimeStart,	@RequestParam(required = false) Date dueTimeEnd,
			@RequestParam(required = false) Date feedTime, @RequestParam(required = false) Integer buyAgain,
			@RequestParam(required = false) Integer projectType, @RequestParam(required = false) String desc) {
		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("project:export");
			} catch (Exception e) {
				model.addAttribute("error", "您没有权限查看");
				return;
			}
			if(status!=null && status.size()==0) {
				status=null;
			}
			
			if (null == desc || "".equals(desc)) {
				desc = "asc";
			}
			
			this.projectList(true,model,response,
					status,noob,limitDays,keyword,
					createTimeStart,createTimeEnd, payTimeStart,payTimeEnd,
					buybackTimeStart,buybackTimeEnd,dueTimeStart,dueTimeEnd,feedTime, buyAgain, projectType,
					"create_date" ,desc ,0, 10);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private String projectList(Boolean isExport,Model model, HttpServletResponse response,
			List<Integer> status, Integer noob, Integer limitDays, String keyword,
			Date createTimeStart, Date createTimeEnd, Date payTimeStart, Date payTimeEnd,
			Date buybackTimeStart, Date buybackTimeEnd,Date dueTimeStart, Date dueTimeEnd,
			Date feedTime, Integer buyAgain, Integer projectType,
			String orderBy ,String desc ,Integer page, Integer pageSize) {

		if (buyAgain == null) {
			buyAgain = 0;// 默认查询不是续购的
		}
		
		int limitStart=(page - 1) * pageSize;
		Date createTimeEndCal=createTimeEnd;
		Date payTimeEndCal=payTimeEnd;
		Date buybackTimeEndCal=buybackTimeEnd;
		if(createTimeEndCal!=null) {
			createTimeEndCal=DateUtil.getAbsoluteDate(createTimeEndCal, Calendar.DATE, 1);
		}
		if(payTimeEndCal!=null) {
			payTimeEndCal=DateUtil.getAbsoluteDate(payTimeEndCal, Calendar.DATE, 1);
		}
		if(buybackTimeEndCal!=null) {
			buybackTimeEndCal=DateUtil.getAbsoluteDate(buybackTimeEndCal, Calendar.DATE, 1);
		}
		
		List<ProjectView> list =projectService.listProjectView(status, noob, limitDays, keyword, 
				createTimeStart, createTimeEndCal, payTimeStart, payTimeEndCal, 
				buybackTimeStart, buybackTimeEndCal,dueTimeStart ,dueTimeEnd,feedTime, buyAgain, projectType,
				limitStart, pageSize, orderBy, desc);
		
		if (isExport) {
			try {
				LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<String, String>();
				propertyHeaderMap.put("id", "ID");
				propertyHeaderMap.put("title", "名称");
				propertyHeaderMap.put("noobName", "是否新手");
				propertyHeaderMap.put("annualized", "年化");
				propertyHeaderMap.put("increaseAnnualized", "加息");
				propertyHeaderMap.put("limitDays", "期限/天");
				propertyHeaderMap.put("rateCouponDays", "加息券生息天数");
				propertyHeaderMap.put("totalAmount", "募集金额");
				propertyHeaderMap.put("earNumber", "耳标编号号");
				propertyHeaderMap.put("gpsNumber", "GPS设备编号");
				propertyHeaderMap.put("createDate", "创建时间");
				propertyHeaderMap.put("startTime", "上架时间");
				propertyHeaderMap.put("tradeTime", "支付时间");
				propertyHeaderMap.put("buyBackTime", "回购时间");
				propertyHeaderMap.put("projectTypeName", "项目类型");
				propertyHeaderMap.put("projectStatusName", "状态");
				HSSFExcelUtils.ExpExs(new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "物权资产",
						propertyHeaderMap, list, response);
			} catch (Exception e) {
				logger.error(e);
			} finally {
				DownloadUtils.closeResponseOutput(response);
			}
		}else {
		
			Long count = projectService.countProjectView(status, noob, limitDays, keyword,
					createTimeStart, createTimeEnd, payTimeStart, payTimeEnd, 
					buybackTimeStart, buybackTimeEndCal,dueTimeStart ,dueTimeEnd,feedTime, buyAgain, projectType);
			
			int pages = calcPage(count.intValue(), pageSize);
			model.addAttribute("pages", pages);
			model.addAttribute("list", list);
			model.addAttribute("status", status);
			model.addAttribute("noob", noob);
			model.addAttribute("limitDays", limitDays);
			model.addAttribute("keyword", keyword);
			
			model.addAttribute("createTimeStart", createTimeStart != null ? DateUtil.dateFormat.format(createTimeStart) : null);
			model.addAttribute("createTimeEnd",  createTimeEnd != null ? DateUtil.dateFormat.format(createTimeEnd) : null);
			model.addAttribute("payTimeStart",  payTimeStart != null ? DateUtil.dateFormat.format(payTimeStart) : null);
			model.addAttribute("payTimeEnd",  payTimeEnd != null ? DateUtil.dateFormat.format(payTimeEnd) : null);
			model.addAttribute("buybackTimeStart",  buybackTimeStart != null ? DateUtil.dateFormat.format(buybackTimeStart) : null);
			model.addAttribute("buybackTimeEnd",  buybackTimeEnd != null ? DateUtil.dateFormat.format(buybackTimeEnd) : null);
			model.addAttribute("feedTime",  feedTime != null ? DateUtil.dateFormat.format(feedTime) : null);
            
			model.addAttribute("buyAgain", buyAgain);
			model.addAttribute("projectType", projectType);
			
			model.addAttribute("page", page);
			model.addAttribute("desc", desc);
			String listParams = this.getListParams(status, noob, limitDays, keyword, createTimeStart, createTimeEnd, payTimeStart, payTimeEnd, 
					buybackTimeStart, buybackTimeEnd, feedTime, buyAgain, projectType, page, desc);
			if (StringUtils.isBlank(listParams)) {
				model.addAttribute("listParams", "");
			} else {
				model.addAttribute("listParams", listParams);
			}
			model.addAttribute("days", ProjectDaysEnum.enumParseMap());
			model.addAttribute("statisticsInfo", projectService.statisticsInfo());
			return "/project/list";
		}
		return "";
	}
	
	private String getListParams(List<Integer> status, Integer noob, Integer limitDays, String keyword, Date createTimeStart, Date createTimeEnd, 
								 Date payTimeStart, Date payTimeEnd, Date buybackTimeStart, Date buybackTimeEnd, Date feedTime, Integer buyAgain, 
								 Integer projectType, Integer page, String desc) {
		Map<String, Object> paramsMap = new HashMap<>();
		if (status != null && !status.isEmpty()) {
			paramsMap.put("status", status.get(0));
		}
		if (noob != null) {
			paramsMap.put("noob", noob);
		}
		if (limitDays != null) {
			paramsMap.put("limitDays", limitDays);
		}
		if (StringUtils.isNotBlank(keyword)) {
			paramsMap.put("keyword", keyword);
		}
		if (createTimeStart != null) {
			paramsMap.put("createTimeStart", DateUtil.dateFormat.format(createTimeStart));
		}
		if (createTimeEnd != null) {
			paramsMap.put("createTimeEnd", DateUtil.dateFormat.format(createTimeEnd));
		}
		if (payTimeStart != null) {
			paramsMap.put("payTimeStart", DateUtil.dateFormat.format(payTimeStart));
		}
		if (payTimeEnd != null) {
			paramsMap.put("payTimeEnd", DateUtil.dateFormat.format(payTimeEnd));
		}
		if (buybackTimeStart != null) {
			paramsMap.put("buybackTimeStart", DateUtil.dateFormat.format(buybackTimeStart));
		}
		if (buybackTimeEnd != null) {
			paramsMap.put("buybackTimeEnd", DateUtil.dateFormat.format(buybackTimeEnd));
		}
		if (feedTime != null) {
			paramsMap.put("feedTime", DateUtil.dateFormat.format(feedTime));
		}
		if (buyAgain != null) {
			paramsMap.put("buyAgain", buyAgain);
		}
		if (projectType != null) {
			paramsMap.put("projectType", projectType);
		}
		if (page != null) {
			paramsMap.put("page", page);
		}
		if (StringUtils.isNotBlank(desc)) {
			paramsMap.put("desc", desc);
		}
		String params = "";
		if (!paramsMap.isEmpty()) {
			for(Map.Entry<String, Object> entry : paramsMap.entrySet()){
				String mapKey = entry.getKey();
				Object mapValue = entry.getValue().toString();
				params += "&" + mapKey + "=" + mapValue;
			}
			params = transcoding(params);
		}
		return params;
	}
	
	private String transcoding(String source) {
	    // jsp传输特殊字符 URL中文处理
		return source.replaceAll("=", "%3D")
                .replaceAll("&", "%26")
                .replaceAll("#", "%23")
                .replaceAll(" ", "%20")
                .replaceAll(":", "%3A")
//                .replaceAll(".", "%2E")
				;
	} 
	
	/**
	 * 债券查询
	 *
	 * @param model
	 * @param keyword
	 * @param status
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/list/bond")
	public String queryBond(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(required = false) Integer status, @RequestParam(required = false) Integer page,
			Date startTime, Date endTime, String name) {
		try {
			int limit = 20;
			if (page == null) {
				page = 1;
			}
			List<Project> list = projectService.queryBond(keyword, status, (page - 1) * limit, limit, null, 0,
					startTime, endTime);
			int count = projectService.queryBondCount(keyword, status, startTime, endTime);

			int pages = calcPage(count, limit);
			model.addAttribute("pages", pages);
			model.addAttribute("list", list);
			model.addAttribute("page", page);
			model.addAttribute("keyword", keyword);
			model.addAttribute("name", name);
			model.addAttribute("status", status);
			model.addAttribute("startTime", startTime);
			model.addAttribute("endTime", endTime);
		} catch (Exception e) {
			logger.error(e);
		}
		return "/project/list-bond";
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(int id, Model model) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.checkPermission("project:detail");
		} catch (Exception e) {
			model.addAttribute("error", "您没有权限查看");
			return "error";
		}

		logger.info("==============进入项目详情页===================");
		model.addAttribute("project", projectService.getProjectById(id));
		return "/project/detail";
	}

	@RequestMapping(value = "/investors", method = RequestMethod.GET)
	public void investors(Integer projectId, Integer page, HttpServletResponse response) {
		if (page == null || page <= 0) {
			page = 10;
		}
		Integer limit = 20;
		List<Map<String, Object>> investors = projectService.getInvestors(projectId, 0, (page - 1) * limit, limit);
		int count = projectService.getInvestorsCount(projectId, 0);
		int pages = count % limit == 0 ? count / limit : count / limit + 1;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pages", pages);
		map.put("page", page);
		map.put("investors", investors);
		AjaxUtil.str2front(response, JSON.toJSONString(map));
	}

	@RequestMapping(value = "/detail/bond", method = RequestMethod.GET)
	public String detailBond(int id, Model model) {

		return "/project/detail-bond";
	}

	@Token(save = true)
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, Integer id) {
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.checkPermission("project:add");
		} catch (Exception e) {
			model.addAttribute("error", "您没有权限查看");
			return "error";
		}

		logger.info("========进入添加项目方法====add=====");

		// 如果id不为空，则创建新的标数据默认显示此id的project数据，方便发表人员快速创建同数据标
		if (id!=null) {
			Project project = projectService.detail(id);
			if (project.getStatus() != ProjectStatusEnum.BUYBACK.getCode()) {
				// 只能复制【已回购】项目
				model.addAttribute("error", "只能复制【已回购】项目。");
				return "error";
			}
			if (project.getAnnualized() != null) {
				BigDecimal b = new BigDecimal(String.valueOf(project.getAnnualized()));
				project.setAnnualized(Float.parseFloat(Double.toString(BigDecimalUtil.multi(b.doubleValue(), 100d))));
			}
			if (project.getIncreaseAnnualized() != null) {
				BigDecimal b = new BigDecimal(String.valueOf(project.getIncreaseAnnualized()));
				project.setIncreaseAnnualized(
						Float.parseFloat(Double.toString(BigDecimalUtil.multi(b.doubleValue(), 100d))));
			}
			model.addAttribute("project", project);
			// 新入库的图片对象
			model.addAttribute("newPictures", projectPictureService.doCopyPicture(project.getPictures()));
			model.addAttribute("operate", "copy");
		} else {
			model.addAttribute("operate", "add");
		}

		List<Product> list = productService.selectProductList(1, (1 - 1) * 20, 2000);
		model.addAttribute("productList", list);

		model.addAttribute("prairie",tmDictService.listTmDict("prairie"));
		model.addAttribute("days", ProjectDaysEnum.getEnumList());
		model.addAttribute("types", ProjectTypeEnum.getEnumList());
		return "/project/add";
	}

	/**
	 * @Description: 新增个人借贷项目类型
	 * @date 2016/11/19
	 * @author 王信
	 */
	@Token(save = true)
	@RequestMapping(value = "/addLoan", method = RequestMethod.GET)
	public String addLoan(Model model, Integer copyId) {
		// 复制数据
		if (null != copyId && !"".equals(copyId)) {
			Project project = projectService.detail(copyId);
			Map<String, Object> record = projectService.queryProjectConfig(copyId);
			model.addAttribute("map", record);
			model.addAttribute("project", project);
			model.addAttribute("copyId", copyId);
		}
		// 查询期数
		List<ProjectActivitySetting> list = projectActivitySettingService.queryProjectActivitySettings();
		Integer period = 1;
		if (list != null && list.size() > 0) {
			period += list.size();
		}

		model.addAttribute("period", period);
		return "/project/addLoan";
	}

	@RequestMapping(value = "/add/app", method = RequestMethod.GET)
	public String addapp(Model model) {
		Project project = projectService.getNewProjectByApp();
		String title = "";
		if (project == null) {
			title = "鑫聚财第1期";
		} else {
			title = "鑫聚财第" + (project.getLimitDays() + 1) + "期";
		}
		model.addAttribute("title", title);
		model.addAttribute("rate", ConstantsAdmin.RATE_DEFAULT_VALUE);
		model.addAttribute("num", project.getLimitDays() + 1);
		return "/project/addapp";
	}

	@Token(remove = true)
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(Model model,HttpServletRequest request, Project project, String picture, String picture2, String picture3,
			String new_pic1, String new_pic2, String picture4, Integer detailId, String period, Integer copyId,
			String packageType) {
		try {

			String[] productPropertyIdArray = request.getParameterValues("ids");
			String[] propertyValueArray = request.getParameterValues("category");

//			// 添加的资产为月月盈
//			if (!StringUtils.isEmpty(packageType) && "1".equals(packageType)) {
//				project.setProjectType(ProjectTypeEnum.YUEYUEYING.getFeatureType());
//			}
			
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("project:add");
			} catch (Exception e) {
				model.addAttribute("error", "您没有权限查看");
				return "error";
			}
			
			String keyword = project.getTitle();
			if (!StringUtils.isEmpty(keyword)) {
				keyword = keyword.replaceAll("\\s*", "");
				if (StringUtils.isEmpty(keyword)) {
					keyword = null;
				}
			}
			project.setTitle(keyword);
			if (project.getTag() == null || project.getTag().isEmpty()) {
				project.setTag(null);
			}
			if (project.getTotalAmount() != null) {
				project.setTotalAmount(project.getTotalAmount());
			}
			// 处理金额，缩小100倍入库
			if (project.getAnnualized() != null) {
				BigDecimal b = new BigDecimal(String.valueOf(project.getAnnualized()));
				project.setAnnualized(Float.parseFloat(Double.toString(BigDecimalUtil.div(b.doubleValue(), 100d, 4))));
			}
			if (project.getIncreaseAnnualized() != null) {
				BigDecimal b = new BigDecimal(String.valueOf(project.getIncreaseAnnualized()));
				project.setIncreaseAnnualized(
						Float.parseFloat(Double.toString(BigDecimalUtil.div(b.doubleValue(), 100d, 4))));
			}
			
			if(StringUtils.isBlank(project.getEarNumber()) || StringUtils.isBlank(project.getRealEarNumber())) {
				model.addAttribute("error", "耳标号不能为空");
				return "error";
			}

			// 如果牛只被用户占用，不可上标，如果用户取消订单，牛只状态先标记为删除
			if (projectService.doCheckBuyAgainProject(project.getParentId())) { // 标记删除用户已取消的续购项目
				model.addAttribute("error", "牛只已被用户买走或还在支付中，不可重新上标");
				return "error";
			}
			
			boolean flag = projectService.ifExistProjectByEarnumber(project.getId(), project.getEarNumber());
			if (flag) {
				model.addAttribute("error", "显示耳标号重复");
				return "error";
			}
			
			flag = projectService.ifExistProjectByRealEarnumber(project.getId(), project.getRealEarNumber());
			if (flag) {
				model.addAttribute("error", "真实耳标号重复");
				return "error";
			}
			
			
			List<String> pictures = new ArrayList<>();
			if (!StringUtils.isEmpty(new_pic1)
					|| !org.springframework.util.StringUtils.isEmpty(new_pic2)) {
				pictures.add(new_pic1);
				pictures.add(new_pic2);
			} else {
				pictures.add(picture);
				pictures.add(picture2);
				pictures.add(picture3);
				pictures.add(picture4);
			}

			if (project.getSort() == null) {
				project.setSort(99);
			}
			projectService.saveWithPicture(project, pictures, productPropertyIdArray, propertyValueArray);

			ProjectCreditor record = new ProjectCreditor();
			record.setTitle(project.getTitle() + "债权");
			record.setStatus(1);
			record.setProjectId(project.getId());
			record.setDetailId(detailId);
			if (project.getTransferable() == null) {
				project.setTransferable(0);
			}
			// projectService.saveCreditorDetail(record);
				if (null != copyId && !"".equals(copyId)) {
				Project copyProject = projectService.detail(copyId);
//		List<ProjectPicture> picList = copyProject.getPictures();
//		if (null != picList) {
//		    for (ProjectPicture pic : picList) {
//			pic.setId(null);
//			pic.setProjectId(project.getId());
//			pic.setCreateTime(new Date());
//			projectPictureService.addProjectPicture(pic);
//		    }
//		}
			}
			if (!StringUtils.isEmpty(period) && "on".equals(period)) {
				projectActivitySettingService.saveProjectActivity(project.getId(),
						Integer.parseInt(request.getParameter("periodHidden")));
			}

			// 如果sort不为99, 则跟新排序
			if (project.getSort() != null && project.getSort() != 99) {
				Map<String, Object> map = new HashMap<>();
				map.put("days", project.getLimitDays());
				map.put("sort", project.getSort());
				projectService.updateProjectSortBylimitDays(map);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
			return "error";
		}
		if (project.getProjectType().equals(5)) {
			return "redirect:/project/cycleList";
		}
		return "redirect:/project/list";

	}

	/**
	 * @Description: 新增活期项目
	 * @date 2016/10/11
	 * @author 王信
	 */
	@RequestMapping(value = "/addCurrent", method = RequestMethod.POST)
	public String addCurrent(Project project) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/project/list/app";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(int id, String listParams, Model model) {
		try {
			List<Product> list = productService.selectProductList(1, (1 - 1) * 20, 2000);
			model.addAttribute("productList", list);
			Project project = projectService.detail(id);

			if (project.getStatus() != ProjectStatusEnum.BUILDED.getCode()) {
				model.addAttribute("error", "只能编辑新建的项目");
				return "/error";
			}
//			project.setAnnualized(Float.parseFloat(
//					BigDecimalUtil.sub(project.getAnnualized(), project.getIncreaseAnnualized()).toString()));
//			Map<String, Object> record = projectService.queryProjectConfig(id);
//			if (project.getProjectType() == 3) {
//				User user = userService.get(project.getUserId());
//				// record.put("userId", user.getId());
//				// record.put("username", user.getUsername());
//				// record.put("phone", user.getPhone());
//			}
			// model.addAttribute("map", record);
			// 处理金额，扩大100倍显示
			if (project.getAnnualized() != null) {
				BigDecimal b = new BigDecimal(String.valueOf(project.getAnnualized()));
				project.setAnnualized(Float.parseFloat(Double.toString(BigDecimalUtil.multi(b.doubleValue(), 100d))));
			}
			if (project.getIncreaseAnnualized() != null) {
				BigDecimal b = new BigDecimal(String.valueOf(project.getIncreaseAnnualized()));
				project.setIncreaseAnnualized(
						Float.parseFloat(Double.toString(BigDecimalUtil.multi(b.doubleValue(), 100d))));
			}

			model.addAttribute("project", project);

//			ProjectActivitySetting projectActivitySetting = projectActivitySettingService
//					.queryProjectActivitySettingByProjectId(project.getId());
			Integer period = 1;
			String on = "";
//			if (projectActivitySetting != null) {
//				period = projectActivitySetting.getPeriod();
//				on = "on";
//			} else {
//				List<ProjectActivitySetting> list = projectActivitySettingService.queryProjectActivitySettings();
//				period += (list != null && list.size() > 0 ? list.size() : 0);
//			}

			model.addAttribute("period", period);
			model.addAttribute("on", on);

			if (!project.getProjectType().equals(5)) {// 飞周期标
//		    if (project.getEnterprise().getType().equals(1)) {// 个人
//			return "/project/editLoan";
//		    } else if (project.getEnterprise().getType().equals(0)) {// 企业
//			return "/project/edit";
//		    }
			}

			if (project.getProjectType().equals(5)) {// 周期标
//		    if (project.getEnterprise().getType().equals(1)) {// 个人周期标
//			return "/project/cyclePersonEdit";
//		    } else if (project.getEnterprise().getType().equals(0)) {// 企业周期标
				// return "/project/cycleEdit";
				// }

			}
			
			model.addAttribute("prairie",tmDictService.listTmDict("prairie"));
			model.addAttribute("days", ProjectDaysEnum.getEnumList());
			model.addAttribute("types", ProjectTypeEnum.getEnumList());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
//		model.addAttribute("listParams", transcoding(listParams));
		model.addAttribute("listParams", listParams);

		return "/project/edit";
	}

	/**
	 * 
	 * <p>
	 * 编辑页面
	 * </p>
	 * 
	 * @author: lxfeng
	 * @date: Created on 2018-3-14 下午8:36:37
	 */
	@RequestMapping(value = "/editBatch", method = RequestMethod.GET)
	public String editBatch(int id, Model model) {
		Project project = projectService.detail(id);
		project.setAnnualized(Float
				.parseFloat(BigDecimalUtil.sub(project.getAnnualized(), project.getIncreaseAnnualized()).toString()));
		model.addAttribute("project", project);
		Enterprise enterprise = enterpriseService.selectByPrimaryKey(project.getEnterpriseId());
		if (enterprise != null) {
			model.addAttribute("landlordName", enterprise.getName());
		}

		ProjectActivitySetting projectActivitySetting = projectActivitySettingService
				.queryProjectActivitySettingByProjectId(project.getId());
		Integer period = 1;
		String on = "";
		if (projectActivitySetting != null) {
			period = projectActivitySetting.getPeriod();
			on = "on";
		} else {
			List<ProjectActivitySetting> list = projectActivitySettingService.queryProjectActivitySettings();
			period += (list != null && list.size() > 0 ? list.size() : 0);
		}

		model.addAttribute("period", period);
		model.addAttribute("on", on);

		return "/project/projectBatchEdit";
	}

	@RequestMapping(value = "/lowerShelvesAjax", method = RequestMethod.POST)
	@ResponseBody
	public Object lowerShelves(Model model, Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("code", "-1");
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("project:lowerShelves");
			} catch (Exception e) {
				map.put("msg", "您没有权限操作");
			}
			projectService.doLowerShelves(id);
			map.put("code", "1");
			map.put("msg", "下架成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			map.put("msg", "下架失败：" + e.getMessage());
		}
		return map;
	}

	/**
	 * 编辑项目数据入库
	 * 
	 * @Title: edit
	 * @param request
	 * @param project
	 * @param picture
	 * @param picture2
	 * @param packageType
	 * @return String
	 * @author zj
	 * @date 2019-05-21 14:05
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(HttpServletRequest request, Project project, String picture, String picture2, String packageType, String listParams,
			Model model) {
		try {

			String[] productPropertyIdArray = request.getParameterValues("ids");
			String[] propertyValueArray = request.getParameterValues("category");

			// 普通的标的是否转为月月盈
			// 月月盈
//			if (!StringUtils.isEmpty(packageType) && "1".equals(packageType)) {
//				project.setProjectType(ProjectTypeEnum.YUEYUEYING.getFeatureType());
//			} else {
//				// 如果projectType是其他类型。避免改掉
//				Project p = projectService.detail(project.getId());
//				if (p.getProjectType() != null && p.getProjectType() == 7) {
//					project.setProjectType(ProjectTypeEnum.DINGQI.getFeatureType());
//				}
//			}
//
//			String oldSort = request.getParameter("oldSort");
//			project.setAnnualized(Float.parseFloat(
//					BigDecimalUtil.add(project.getAnnualized(), project.getIncreaseAnnualized()).toString()));
//			project.setDeadline(null);
//			project.setSort(project.getSort() == null ? 99 : project.getSort());

			// 如果sort不为99, 则跟新排序
//			if (oldSort != null && project.getSort() != 99 && project.getSort() != Integer.parseInt(oldSort)) {
//				Map<String, Object> map = new HashMap<>();
//				map.put("days", project.getLimitDays());
//				map.put("sort", project.getSort());
//				projectService.updateProjectSortBylimitDays(map);
//			}
			
			if(StringUtils.isBlank(project.getEarNumber()) || StringUtils.isBlank(project.getRealEarNumber())) {
				model.addAttribute("error", "耳标号重复不能为空");
				return "error";
			}
			
			boolean flag = projectService.ifExistProjectByEarnumber(project.getId(), project.getEarNumber());
			if (flag) {
				model.addAttribute("error", "显示耳标号重复");
				return "error";
			}
			
			flag = projectService.ifExistProjectByRealEarnumber(project.getId(), project.getRealEarNumber());
			if (flag) {
				model.addAttribute("error", "真实耳标号重复");
				return "error";
			}
			
			// 处理金额，缩小100倍入库
			if (project.getAnnualized() != null) {
				BigDecimal b = new BigDecimal(String.valueOf(project.getAnnualized()));
				project.setAnnualized(Float.parseFloat(Double.toString(BigDecimalUtil.div(b.doubleValue(), 100d, 4))));
			}
			if (project.getIncreaseAnnualized() != null) {
				BigDecimal b = new BigDecimal(String.valueOf(project.getIncreaseAnnualized()));
				project.setIncreaseAnnualized(
						Float.parseFloat(Double.toString(BigDecimalUtil.div(b.doubleValue(), 100d, 4))));
			}

			projectService.update(project, picture, picture2, productPropertyIdArray, propertyValueArray);

//			String period = request.getParameter("period");
//
//			if (!StringUtils.isEmpty(request.getParameter("projectStatus"))
//					&& request.getParameter("projectStatus").equals("0")) {
//				if (StringUtils.isEmpty(period)) {
//					projectActivitySettingService.delProjectActivitySetting(request.getParameter("periodHidden"));
//				} else {
//					if (projectActivitySettingService.queryProjectActivitySettingByProjectId(project.getId()) == null) {
//						projectActivitySettingService.saveProjectActivity(project.getId(),
//								Integer.parseInt(request.getParameter("periodHidden")));
//					}
//				}
//			}

		} catch (Exception e) {
			logger.error(e);
			model.addAttribute("error", e.getMessage());
			return "/error";
		}
		if (StringUtils.isNotBlank(listParams)) {
			return "redirect:/project/list?1=1" + listParams;
		}
		return "redirect:/project/list";
	}

	/**
	 * 
	 * <p>
	 * 标的修改-批量创建的标的
	 * </p>
	 * 
	 * @return
	 * @author: lxfeng
	 * @date: Created on 2018-3-14 下午8:08:51
	 */
	@RequestMapping(value = "/batchEdit", method = RequestMethod.POST)
	public String batchEdit(HttpServletRequest request, String period, Project project, String packageType,
			String transferablecheck) {
		try {
			// 状态为投资中，添加开始时间
			if (project.getStatus() == 2) {
				project.setStartTime(new Date());
			}

			if (!StringUtils.isEmpty(request.getParameter("projectStatus"))
					&& request.getParameter("projectStatus").equals("0")) {
				if (StringUtils.isEmpty(period)) {
					projectActivitySettingService.delProjectActivitySetting(request.getParameter("periodHidden"));
				} else {
					if (projectActivitySettingService.queryProjectActivitySettingByProjectId(project.getId()) == null) {
						projectActivitySettingService.saveProjectActivity(project.getId(),
								Integer.parseInt(request.getParameter("periodHidden")));
					}
				}
			}

			// 月月盈
//			if (!StringUtils.isEmpty(packageType) && "on".equals(packageType)) {
//				project.setProjectType(ProjectTypeEnum.YUEYUEYING.getFeatureType());
//	    	if (StringUtils.isEmpty(transferablecheck)) {
//	    		project.setTransferable(1);
//			}
//			} else {
				// 如果projectType是其他类型。避免改掉
				Project p = projectService.detail(project.getId());
				if (p.getProjectType() != null && p.getProjectType() == 7) {
					project.setProjectType(ProjectTypeEnum.YANGNIU.getFeatureType());
//			    if (StringUtils.isEmpty(transferablecheck)) {
//		    		project.setTransferable(0);
//				}
				}
//			}

			project.setAnnualized(
					BigDecimalUtil.add(project.getAnnualized(), project.getIncreaseAnnualized()).floatValue());
			projectService.updateProject(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/project/list";
	}

	/**
	 * @Description(描述):活期项目编辑
	 * @author 王信
	 * @date 2016/9/9
	 * @params
	 **/
	@RequestMapping(value = "/editapp", method = RequestMethod.GET)
	public String editapp(int id, Model model) {
		Project project = projectService.detail(id);
		project.setTotalAmount(project.getTotalAmount() / 10000);
		model.addAttribute("project", project);
		return "/project/editapp";
	}

	@RequestMapping(value = "/checkName", method = RequestMethod.GET)
	public void checkName(@RequestParam String title, Integer id, HttpServletResponse response) {
		Boolean flag = true;
		try {
			flag = projectService.checkNameExists(title, id);
		} catch (Exception e) {
			logger.error(e);
		}
		AjaxUtil.str2front(response, JSON.toJSONString(flag));
	}

	/**
	 * 删除项目图片
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/picture", method = RequestMethod.POST)
	public void deletePicture(Integer id, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			projectPictureService.delete(id);
			map.put(STATUS, SUCCESS);
			AjaxUtil.str2front(response, JSON.toJSONString(map));
		} catch (Exception e) {
			logger.error(e);
			map.put(STATUS, ERROR);
			AjaxUtil.str2front(response, JSON.toJSONString(map));
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void upload(String picName, @RequestParam MultipartFile file, Integer type, HttpServletResponse response,
			HttpSession session) {
		Map<String, Object> map = null;
		logger.info("==========进入 com.goochou.p2b.admin.web.controller.ProjectController.upload  ======== ");
		try {
			ProjectPicture picture = new ProjectPicture();
			picture.setStatus(1); // 当前图片还是不可用的状态
			picture.setCreateDate(new Date());
			picture.setName(picName.trim());
			picture.setType(type); // 1项目图片2协议图片
			map = projectPictureService.save(picture, file, ((UserAdmin) session.getAttribute("user")).getId());
			logger.info("==========结束 com.goochou.p2b.admin.web.controller.ProjectController.upload  ======== ");
		} catch (Exception e) {
			logger.error("com.goochou.p2b.admin.web.controller.ProjectController.upload 上传图片出错========>"+e.getMessage(),e);
		}
		AjaxUtil.str2front(response, JSON.toJSONString(map));
	}

	/**
	 * 批量人保水印
	 * @author 张琼麒
	 * @version 创建时间：2019年10月18日 上午10:31:57
	 * @param id
	 * @param model
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "/batchRenbaoCover", method = RequestMethod.GET)
	public String batchRenbaoCover(Model model, HttpServletResponse response,HttpSession session) {
		logger.info("==========进入 com.goochou.p2b.admin.web.controller.ProjectController.batchRenbaoCover  ======== ");
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.checkPermission("resources:view");
		} catch (Exception e) {
			model.addAttribute("error", "您没有权限查看");
			return "error";
		}
		try {
			projectPictureService.batchRenbaoCover();
		} catch (Exception e) {
			logger.error("com.goochou.p2b.admin.web.controller.ProjectController.batchRenbaoCover========>"+e.getMessage(),e);
		}
		model.addAttribute("error", "OK");
		return "error";
	}
	
	
	@RequestMapping(value = "/invest", method = RequestMethod.GET)
	public String invest(Integer id, Model model) {
		try {
			Project p = projectService.detail(id);
			if (p.getParentId() != null) {// 债权投资
				Project b = projectService.detail(p.getParentId());
				p.setTitle(b.getTitle());
				p.setAnnualized(b.getAnnualized());
				p.setLimitDays(b.getLimitDays());
			}
			model.addAttribute("project", p);
			List<Map<String, Object>> user = userService.query(null, null, null, 3, null, 0, 9999);
			List<Integer> ids = projectService.getAllInvestors(id);
			model.addAttribute("list", user);
			model.addAttribute("ids", ids);
		} catch (Exception e) {
			logger.error(e);
		}
		return "/project/invest";
	}

	/**
	 * 用户投资
	 * <p/>
	 * 项目ID
	 *
	 * @param amount 投资金额
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/invest", method = RequestMethod.POST)
	public String invest(Integer projectId, Integer userId, double amount, Model model) {
		try {
		} catch (Exception e) {
			logger.error("投资异常，草草草草", e);
		}
		return "redirect:/project/invest?id=" + projectId;
	}

	/**
	 * 债权列表页面
	 *
	 * @return
	 * @author 刘源
	 * @date 2016-2-2
	 */
	@RequestMapping(value = "creditorList", method = RequestMethod.GET)
	public String creditorList(Model model, HttpServletResponse response, String keyword,
			@RequestParam(required = false) String status, @RequestParam(required = false) Integer page) {
		int limit = 20;
		if (page == null) {
			page = 1;
		}
		if (StringUtils.isEmpty(status)) {
			status = null;
		}
		List<Map<String, Object>> list = projectService.queryCreditor(keyword, status, (page - 1) * limit, limit);
		int count = projectService.queryCreditorCount(keyword, status);
		int pages = calcPage(count, limit);
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		model.addAttribute("keyword", keyword);
		model.addAttribute("status", status);
		return "project/creditorList";
	}

	@RequestMapping(value = "saveCreditor", method = RequestMethod.GET)
	public String saveCreditor(Model model, HttpServletResponse response, String keyword, String status,
			@RequestParam(required = false) Integer page) {
		int limit = 20;
		if (page == null) {
			page = 1;
		}
		List<Map<String, Object>> list = projectService.queryCreditor(keyword, status, (page - 1) * limit, limit);
		int count = projectService.queryCreditorCount(keyword, status);
		int pages = calcPage(count, limit);
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		model.addAttribute("keyword", keyword);
		model.addAttribute("status", status);
		return "project/creditorList";
	}

	/**
	 * @param model
	 * @param response
	 * @param page
	 * @return
	 * @Title: ProjectController.java
	 * @Package com.goochou.p2b.admin.web.controller
	 * @Description(描述):温馨提示 后台添加，app获取
	 * @author 王信
	 * @date 2016年2月29日 上午9:46:32
	 * @version V1.0
	 */
	@RequestMapping(value = "reminderList", method = RequestMethod.GET)
	public String reminderList(Model model, HttpServletResponse response, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) String title) {
		int limit = 20;
		if (page == null) {
			page = 1;
		}
		List<Map<String, Object>> list = projectService.selectPromptList(title, (page - 1) * limit, limit);
		Integer count = projectService.selectPromptCount(title);
		int pages = calcPage(count, limit);
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		model.addAttribute("pages", pages);
		return "project/reminderList";
	}

	/**
	 * @param model
	 * @param response
	 * @return
	 * @Title: ProjectController.java
	 * @Package com.goochou.p2b.admin.web.controller
	 * @Description(描述):温馨提示新增跳转
	 * @author 王信
	 * @date 2016年2月29日 下午2:16:33
	 * @version V1.0
	 */
	@RequestMapping(value = "reminderAdd", method = RequestMethod.GET)
	public String reminderAdd(Model model, HttpServletResponse response) {
		return "project/reminderAdd";
	}

	/**
	 * @param model
	 * @param response
	 * @return
	 * @Title: ProjectController.java
	 * @Package com.goochou.p2b.admin.web.controller
	 * @Description(描述):保存新增温馨提示
	 * @author 王信
	 * @date 2016年2月29日 下午3:30:25
	 * @version V1.0
	 */
	@RequestMapping(value = "reminderCozyAdd", method = RequestMethod.POST)
	public String reminderCozyAdd(Model model, HttpServletResponse response, String title, String[] context) {
		Integer id = projectService.saveAddPrompt(title, context);
		return "redirect:/project/reminderList";
	}

	/**
	 * @param model
	 * @param response
	 * @return
	 * @Title: ProjectController.java
	 * @Package com.goochou.p2b.admin.web.controller
	 * @Description(描述):温馨提示编辑
	 * @author 王信
	 * @date 2016年2月29日 下午5:02:55
	 * @version V1.0
	 */
	@RequestMapping(value = "reminderEdit", method = RequestMethod.GET)
	public String reminderEdit(Model model, HttpServletResponse response, Integer id, Integer page) {
		Prompt prompt = projectService.selectKeyPrompt(id);
		model.addAttribute("prompt", prompt);
		model.addAttribute("page", page);
		return "/project/reminderEdit";
	}

	/**
	 * @param title   标题 不可修改
	 * @param context 内容
	 * @param id      提示id
	 * @param parent  排序id
	 * @return
	 * @Title: ProjectController.java
	 * @Package com.goochou.p2b.admin.web.controller @Description(描述):
	 * @author 王信
	 * @date 2016年3月16日 下午3:31:33
	 */
	@RequestMapping(value = "reminderCozyEdit", method = RequestMethod.POST)
	public String reminderCozyEdit(Model model, HttpServletResponse response, String title, String context, Integer id,
			Integer parent, Integer page) {
		Prompt prompt = new Prompt();
		prompt.setContext(context);
		prompt.setId(id);
		prompt.setTitle(title);
		prompt.setParentId(parent);
		projectService.updatePrompt(prompt);
		model.addAttribute("page", page);
		return "redirect:/project/reminderList";
	}

	@RequestMapping(value = "reminderCozyDelete", method = RequestMethod.GET)
	public String reminderCozyDelete(Model model, HttpServletResponse response, Integer id) {
		projectService.deletePrompt(id);
		return "redirect:/project/reminderList";
	}

	/**
	 * 进入债权详情或合同内容编辑列表页面
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月8日
	 * @parameter
	 */
	@RequestMapping(value = "contractList", method = RequestMethod.GET)
	public String creditorContractEdit(Model model, HttpServletResponse response, Integer type, String keyword,
			Integer page) {
		Integer limit = 20;
		if (page == null) {
			page = 1;
		}
		List<Map<String, Object>> list = projectService.queryContractList(type, keyword, (page - 1) * limit, limit);
		int count = projectService.queryContractListCount(type, keyword);
		int pages = calcPage(count, limit);
		model.addAttribute("pages", pages);
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		model.addAttribute("keyword", keyword);
		model.addAttribute("type", type);
		return "/project/contractList";
	}

	/**
	 * 今日编辑详情或合同内容编辑页面
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月8日
	 * @parameter
	 */
	@RequestMapping(value = "contractDetail", method = RequestMethod.GET)
	public String contractDetail(Model model, HttpServletResponse response,
			@RequestParam(required = false) Integer id) {
		ProjectContract map = projectService.queryContractDetail(id);
		model.addAttribute("map", map);
		return "/project/contractDetail";
	}

	/**
	 * 编辑债权、项目合同或详情
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月8日
	 * @parameter
	 */
	@RequestMapping(value = "editContractDetail", method = RequestMethod.POST)
	public String editContractDetail(Model model, HttpServletResponse response, ProjectContractWithBLOBs group) {
		projectService.saveContractInfo(group);
		return "redirect:/project/contractList";
	}

	/**
	 * @Description: 债权详情模版 查询
	 * @date 2016/11/1
	 * @author 王信 重写
	 */
	@RequestMapping(value = "getContractTitleList", method = RequestMethod.GET)
	public void getContractTitleList(Model model, HttpServletResponse response, Integer id) {
		try {
			List<ProjectContract> list = new ArrayList<ProjectContract>();
			if (id == null) {
				list = projectService.getContractTitleList();
			} else {
				ProjectContract projectContract = projectService.queryContractDetail(id);
				list.add(projectContract);
			}

			AjaxUtil.str2front(response, JSON.toJSONString(list));
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * 债权详情配置页面
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月8日
	 * @parameter
	 */
	@RequestMapping(value = "creditorDetail", method = RequestMethod.GET)
	public String creditorDetail(Model model, HttpServletResponse response,
			@RequestParam(required = false) Integer id) {
		Map<String, Object> record = projectService.queryProjectCreditorConfig(id);
		model.addAttribute("map", record);
		return "/project/creditorDetail";
	}

	/**
	 * 债权详情配置
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月9日
	 * @parameter
	 */
	@RequestMapping(value = "editCreditorDetail", method = RequestMethod.POST)
	public String editCreditorDetail(Model model, HttpServletResponse response, Integer id, String title,
			String content, Integer projectId, Integer detailId, Integer status) {
		ProjectCreditor record = new ProjectCreditor();
		record.setId(id);
		record.setTitle(title);
		record.setContent(content);
		record.setStatus(status);
		record.setProjectId(projectId);
		record.setDetailId(detailId);
		projectService.saveCreditorDetail(record);
		return "redirect:/project/creditorList";
	}

	/**
	 * 获取可用于配置债权的定期项目项
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月9日
	 * @parameter
	 */
	@RequestMapping(value = "getProjectTitleOption", method = RequestMethod.GET)
	@ResponseBody
	public List<Project> getProjectTitleOption(Model model, HttpServletResponse response, Integer id) {
		try {
			List<Project> list = projectService.getProjectTitleOption(id);
			return list;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	@RequestMapping(value = "getProjectAccount", method = RequestMethod.GET)
	@ResponseBody
	public List<Project> getProjectAccount(Model model, Integer id) {
		try {
			List<Project> list = projectService.getProjectAccount(id);
			return list;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	@RequestMapping(value = "getProjectAccountDetails", method = RequestMethod.GET)
	@ResponseBody
	public ProjectAccount getProjectAccountDetails(Model model, Integer id) {
		try {
			Project project = projectService.get(id);
			ProjectAccount pa = projectAccountService.queryProjectAccountByEnterprise(project.getEnterpriseId());
			return pa;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * 校验项目是否配置债权
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月9日
	 * @parameter
	 */
	@RequestMapping(value = "checkProjectHasCreditor", method = RequestMethod.GET)
	public void checkProjectHasCreditor(Model model, HttpServletResponse response, Integer projectId) {
		try {
			Boolean res = projectService.checkProjectHasCreditor(projectId);
			AjaxUtil.str2front(response, JSON.toJSONString(res));
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * 删除债权配置
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月9日
	 * @parameter
	 */
	@RequestMapping(value = "deleteCreditorConfig", method = RequestMethod.POST)
	public void deleteCreditorConfig(Model model, HttpServletResponse response, Integer id) {
		try {
			projectService.deleteCreditorConfig(id);
			AjaxUtil.str2front(response, JSON.toJSONString(true));
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * 删除债权配置
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月9日
	 * @parameter
	 */
	@RequestMapping(value = "deleteContract", method = RequestMethod.POST)
	public void deleteContract(Model model, HttpServletResponse response, Integer id) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Boolean check = projectService.checkContractDelete(id);
			if (!check) {
				projectService.deleteContract(id);
				map.put("msg", "删除成功");
			} else {
				map.put("msg", "改债权内容模板，已被使用");
			}
			map.put("code", !check);

			AjaxUtil.str2front(response, JSON.toJSONString(map));
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * @Description(描述):app版本控制管理list页面
	 * @author 王信
	 * @date 2016/4/12
	 * @params app版本控制管理list页面
	 **/
	@RequestMapping(value = "versionList", method = RequestMethod.GET)
	public String versionList(Model model, HttpServletResponse response, String keyword, Integer page) {
		Integer limit = 20;
		if (page == null) {
			page = 1;
		}
		List<AppVersionContent> list = projectService.queryAppVersionContentList(keyword, (page - 1) * limit, limit);
		int count = projectService.queryAppVersionContentCount(keyword);
		int pages = calcPage(count, limit);
		model.addAttribute("pages", pages);
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		model.addAttribute("keyword", keyword);
		return "/project/versionList";
	}

	/**
	 * @Description(描述):新增版本管理内容
	 * @author 王信
	 * @date 2016/4/12
	 * @params
	 **/
	@RequestMapping(value = "versionAdd", method = RequestMethod.GET)
	public String versionAdd(Model model, HttpServletResponse response, @RequestParam(required = false) Integer id,
			@RequestParam(required = false) Integer page) {
		AppVersionContent appVersionContent = projectService.selectAppVersionContentKey(id);
		model.addAttribute("map", appVersionContent);
		model.addAttribute("page", page);
		return "project/versionAdd";
	}

	/**
	 * @Description(描述):新增保存
	 * @author 王信
	 * @date 2016/4/12
	 * @params
	 **/
	@RequestMapping(value = "versionAddSave", method = RequestMethod.POST)
	public String versionAddSave(Model model, HttpServletResponse response, AppVersionContentWithBLOBs group,
			@RequestParam(required = false) Integer page) {
		projectService.saveAppVersionContent(group);
		return "redirect:/project/versionList";
	}

	/**
	 * @Description(描述):新增保存
	 * @author 王信
	 * @date 2016/4/12
	 * @params
	 **/
	@RequestMapping(value = "versionDelete", method = RequestMethod.GET)
	public String versionDelete(Model model, HttpServletResponse response, Integer id) {
		projectService.deleteAppVersionContentKey(id);
		return "redirect:/project/versionList";
	}

	/**
	 * @Description(描述):债权列表
	 * @author 王信
	 * @date 2016/5/27
	 * @params
	 **/
	@RequestMapping(value = "/creditorAdminList")
	public String creditorAdminList(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(required = false) Integer status, @RequestParam(required = false) Integer page) {
		try {
			int limit = 20;
			if (page == null) {
				page = 1;
			}
			List<Map<String, Object>> list = projectService.selectCreditorList(keyword, status, (page - 1) * limit,
					limit);
			int count = projectService.selectCreditorCount(keyword, status);
			int pages = calcPage(count, limit);
			model.addAttribute("pages", pages);
			model.addAttribute("list", list);
			model.addAttribute("page", page);
			model.addAttribute("keyword", keyword);
			model.addAttribute("status", status);
		} catch (Exception e) {
			logger.error(e);
		}
		return "/project/creditorAdminList";
	}

	@RequestMapping(value = "/getRegularProject", method = RequestMethod.GET)
	public void getRegularProject(HttpServletResponse response) {
		List<Project> list = projectService.getRegularProject(null);
		AjaxUtil.str2front(response, JSON.toJSONString(list));
	}

	@RequestMapping(value = "/account/list")
	public String accountList(Model model, Integer page) {
		try {
			int limit = 20;
			if (page == null) {
				page = 1;
			}
			List<ProjectAccount> list = projectAccountService.list((page - 1) * limit, limit);
			int count = projectAccountService.listCount();
			int pages = calcPage(count, limit);
			model.addAttribute("pages", pages);
			model.addAttribute("list", list);
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error(e);
		}
		return "/project/accountList";
	}

	@RequestMapping(value = "/account/edit")
	public String accountEdit(Model model, Integer id) {
		try {
			ProjectAccount account = projectAccountService.detail(id);
			model.addAttribute("account", account);
		} catch (Exception e) {
			logger.error(e);
		}
		return "/project/accountDetail";
	}

	@RequestMapping(value = "/account/edit", method = RequestMethod.POST)
	public String accountEditPost(Model model, ProjectAccount account) {
		try {
			projectAccountService.update(account);
		} catch (Exception e) {
			logger.error(e);
		}
		return "redirect:/project/account/list";
	}

	@RequestMapping(value = "/account/add")
	public String addAccount(Model model, Integer id) {
		return "/project/accountAdd";
	}

	@RequestMapping(value = "/account/add", method = RequestMethod.POST)
	public String addAccountPost(Model model, ProjectAccount account) {
		try {
			projectAccountService.save(account);
		} catch (Exception e) {
			logger.error(e);
		}
		return "redirect:/project/account/list";
	}

	/**
	 * @Description(描述):借款项目列表
	 * @author 王信
	 * @date 2016/9/20
	 * @params
	 **/
	@RequestMapping(value = "/loanList", method = RequestMethod.POST)
	public String loanList(Model model, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer status) {
		try {
			int limit = 20;
			if (page == null) {
				page = 1;
			}
			// List<Project> list = projectService.queryLoanList(status,(page -
			// 1) * limit, limit);
			// int count = projectService.queryLoanCount(status);
			// int pages = calcPage(count, limit);
			// model.addAttribute("pages", pages);
			// model.addAttribute("list", list);
			// model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error(e);
		}
		return "redirect:/project/loanList";
	}

	/**
	 * @Description: 查询超级投资人余额是否足够开标
	 * @date 2016/11/3
	 * @author 王信
	 */
	@RequestMapping(value = "/userAssetsBalance", method = RequestMethod.GET)
	public void userAssetsBalance(Integer userId, Double totalAmount, HttpServletResponse response) {
		try {
			Assets assets = assetsService.findByuserId(userId);
			boolean flag = false;
			if (assets != null && totalAmount != null) {
//		if (totalAmount > assets.getAvailableBalance()) {
//		    flag = true;
//		}
			}
			AjaxUtil.str2front(response, JSON.toJSONString(flag));
		} catch (Exception e) {
			logger.error(e);
		}
	}

	@RequestMapping(value = "/userAssetsCreditor", method = RequestMethod.GET)
	public void userAssetsCreditor(HttpServletResponse response, @RequestParam Double totalAmount) {
		Boolean flag = null;
		AjaxUtil.str2front(response, JSON.toJSONString(flag));
	}

	/**
	 * 项目成本
	 *
	 * @param model
	 * @param keyword
	 * @param status
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/list/projectCost")
	public String projectCost(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(required = false) Integer status, @RequestParam(required = false) Integer page,
			Date startTime, Date endTime, Integer type, Integer limitDays) {
		try {
			int limit = 20;
			if (page == null) {
				page = 1;
			}
			List<Map<String, Object>> list = projectService.projectCost((page - 1) * limit, limit, keyword, status,
					startTime, endTime, type, limitDays);
			int count = projectService.countProjectCost(keyword, status, startTime, endTime, type, limitDays);
			int pages = calcPage(count, limit);
			model.addAttribute("pages", pages);
			model.addAttribute("limitDays", limitDays);
			model.addAttribute("type", type);
			model.addAttribute("list", list);
			model.addAttribute("page", page);
			model.addAttribute("keyword", keyword);
			model.addAttribute("status", status);
			model.addAttribute("startTime", startTime);
			model.addAttribute("endTime", endTime);
		} catch (Exception e) {
			logger.error(e);
		}
		return "/project/projectCost";
	}

	/**
	 * @Description: 周期标列表 新定期使用
	 * @date 2017/2/16
	 * @author 王信
	 */
	@RequestMapping(value = "/cycleList")
	public String cycleList(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(required = false) String title, @RequestParam(required = false) Integer status,
			@RequestParam(required = false) Date startTime, @RequestParam(required = false) Date endTime,
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, String title1,
			@RequestParam(required = false) Integer page, Integer noob) {
		try {
			int limit = 20;
			if (page == null) {
				page = 1;
			}
			List<Project> list = projectService.queryCycleList(keyword, title, status, startTime, endTime, startDate,
					endDate, (page - 1) * limit, limit, title1);
			int count = projectService.queryCycleCount(keyword, title, status, startTime, endTime, startDate, endDate,
					title1);
			int pages = calcPage(count, limit);
			model.addAttribute("pages", pages);
			model.addAttribute("list", list);
			model.addAttribute("page", page);
			model.addAttribute("keyword", keyword);
			model.addAttribute("title", title);
			model.addAttribute("title1", title1);
			model.addAttribute("startTime", startTime);
			model.addAttribute("endTime", endTime);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("noob", noob);
			model.addAttribute("status", status);
		} catch (Exception e) {
			logger.error(e);
		}
		return "/project/cycleList";
	}

	@RequestMapping(value = "/cycleAdd", method = RequestMethod.GET)
	public String cycleAdd(Integer type) {
		if (type == 1) {
			return "/project/cyclePsersonAdd";
		}
		return "/project/cycleAdd";
	}

	@RequestMapping(value = "/cycleEdit", method = RequestMethod.POST)
	public String cycleEdit(Project project, String picture, String picture2) {
		try {
			project.setAnnualized(Float.parseFloat(
					BigDecimalUtil.add(project.getAnnualized(), project.getIncreaseAnnualized()).toString()));
			project.setDeadline(null);
			project.setStartTime(new Date());
			// projectService.update(project, picture, picture2);
		} catch (Exception e) {
			logger.error(e);
		}
		return "redirect:/project/cycleList";
	}

	@RequestMapping(value = "/projectClassList", method = RequestMethod.GET)
	public String projectClassList(Model model, @RequestParam(required = false) String keyword, Integer page,
			String name, Integer noob, Integer limitDays) {
		try {
			if (page == null) {
				page = 1;
			}
			Integer limit = ConstantsAdmin.PAGE_LIMIT2;
			List<ProjectClass> list = projectClassService.queryProjectClassList(keyword, (page - 1) * limit, limit,
					name, noob, limitDays);
			Integer count = projectClassService.queryProjectClassListCount(keyword, name, noob, limitDays);
			model.addAttribute("list", list);
			model.addAttribute("page", page);
			model.addAttribute("keyword", keyword);
			model.addAttribute("name", name);
			model.addAttribute("noob", noob);
			model.addAttribute("limitDays", limitDays);
			model.addAttribute("pages", calcPage(count.intValue(), limit));
		} catch (Exception e) {
			logger.error(e);
		}
		return "/project/projectClassList";
	}

	@RequestMapping(value = "/addProjectClass", method = RequestMethod.GET)
	public String addProjectClass(Model model, Integer id) {
		if (id != null) {
			ProjectClass projectClass = projectClassService.get(id);
			model.addAttribute("project", projectClass);
		}
		return "project/addProjectClass";
	}

	@RequestMapping(value = "/addOrEditProjectClass", method = RequestMethod.POST)
	public String addOrEditProjectClass(@ModelAttribute ProjectClass projectClass) {
		if (projectClass.getId() == null) {
			projectClassService.save(projectClass);
		} else {
			projectClassService.updateProjectClass(projectClass);
		}
		return "redirect:/project/projectClassList";
	}

	/**
	 * 
	 * <p>
	 * 批量添加页面
	 * </p>
	 * 
	 * @param id
	 * @author: lxfeng
	 * @date: Created on 2018-3-12 下午5:01:28
	 */
	@RequestMapping(value = "/projectBatchAdd", method = RequestMethod.GET)
	public String projectBatchAdd(Model model, String id) {

		return "/project/projectBatchAdd";
	}

	@RequestMapping(value = "/checkAccountName", method = RequestMethod.GET)
	public void checkAccountName(@RequestParam String name, HttpServletResponse response) {
		Boolean flag = true;
		try {
			flag = projectAccountService.checkAccountNameOrOrderNoNotExists(name, null);
		} catch (Exception e) {
			logger.error(e);
		}
		AjaxUtil.str2front(response, JSON.toJSONString(flag));
	}

	@RequestMapping(value = "/checkAccountOrderNo", method = RequestMethod.GET)
	public void checkAccountOrderNo(@RequestParam String orderNo, HttpServletResponse response) {
		Boolean flag = true;
		try {
			flag = projectAccountService.checkAccountNameOrOrderNoNotExists(null, orderNo);
		} catch (Exception e) {
			logger.error(e);
		}
		AjaxUtil.str2front(response, JSON.toJSONString(flag));
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String projectSearch(Model model, String id) {
		List<Project> list = projectService.queryByContractId(id);
		if (list.size() > 0) {
			// 还款计划
			double interestAmount = 0;
			double fffAmount = 0;
			double capAmount = 0;
			double test = 0;
			List<BondPayVO> bondPays = projectService.queryBondPayList(list.get(0).getId());
			for (int i = 0; i < bondPays.size(); i++) {
				int day = 0;
				if (i == 0) {
					day = DateUtil.dateToDateDay(DateUtil.parseStrToDate(bondPays.get(i).getDeadline()),
							DateUtil.parseStrToDate(bondPays.get(i).getDate()));
				} else {
					day = DateUtil.dateToDateDay(DateUtil.parseStrToDate(bondPays.get(i - 1).getDate()),
							DateUtil.parseStrToDate(bondPays.get(i).getDate()));
				}
				bondPays.get(i).setInterestAmount(BigDecimalUtil
						.fixed2(bondPays.get(i).getTotalAmount() * bondPays.get(i).getAnnualized() * day / 365));
				if (i == (bondPays.size() - 1)) {
					bondPays.get(i).setCapitalAmount(bondPays.get(i).getTotalAmount());
				} else {
					bondPays.get(i).setCapitalAmount(0d);
				}
				bondPays.get(i).setCapInterAmount(BigDecimalUtil.fixed2(bondPays.get(i).getCapitalAmount()
						+ bondPays.get(i).getInterestAmount() + bondPays.get(i).getFff()));
				capAmount = BigDecimalUtil.fixed2(capAmount + bondPays.get(i).getCapitalAmount());
				bondPays.get(i).setFff(BigDecimalUtil.fixed2(bondPays.get(i).getTotalAmount() * 0.0026));
				fffAmount = BigDecimalUtil.fixed2(fffAmount + bondPays.get(i).getFff());
				interestAmount = BigDecimalUtil.fixed2(interestAmount + bondPays.get(i).getInterestAmount());
				test = BigDecimalUtil.fixed2(test + bondPays.get(i).getInterestAmount() + bondPays.get(i).getFff());
				System.out.println(test);
			}
			model.addAttribute("fffAmount", fffAmount);
			model.addAttribute("interestAmount", interestAmount);
			model.addAttribute("totleAmount", BigDecimalUtil.fixed2(capAmount + interestAmount + fffAmount));
			model.addAttribute("interest", bondPays);
			model.addAttribute("capAmount", capAmount);
			// 标的债转情况
			List<OrderChangeVO> orderChanges = projectService.queryOrderChangeList(list.get(0).getId());
			model.addAttribute("orderChanges", orderChanges);
		}
		model.addAttribute("id", id);
		model.addAttribute("list", list);
		return "/project/searchDetail";
	}

	@RequestMapping(value = "/getProjectCreditor ", method = RequestMethod.GET)
	public String getProjectCreditor(Model model, HttpServletResponse response, Date startTime, Date endTime) {

		return "/project/monthCreditorDetail";
	}

	@RequestMapping(value = "/generateProjectCreditor ", method = RequestMethod.POST)
	@ResponseBody
	public int generatingClaims(Model model, HttpServletResponse response, String projectIdsStr) {
		boolean flag = false;
		String[] projectIds = projectIdsStr.split(",");
		flag = projectService.batchUpdateProject(projectIds);
		if (flag) {
			return 1;
		}
		return 0;
	}

	@RequestMapping(value = "/monthGainadd", method = RequestMethod.GET)
	public String monthGainadd(Model model, HttpServletResponse response) {
//	
		return "/project/monthGainDetail";
	}

	@RequestMapping(value = "/getMonthGainInvestment", method = RequestMethod.GET)
	public String getMonthGainInvestment(Model model, HttpServletResponse response, @RequestParam Integer prackgeId,
			Integer page, HttpSession session) {

		return "/project/monthGainInvestmentList";
	}

	@RequestMapping(value = "/saveMonthGain", method = RequestMethod.POST)
	public String saveMonthGain(@RequestParam String tag, HttpServletResponse response, Double totalAmount,
			Integer productId, Integer limitDay, Float annualized, Float annualizedadd, Integer newsUser,
			String startTime, Integer projectRiskGrade, Integer matchingOriginalProject) {
		try {
		} catch (Exception e) {
			logger.error(e);
		}
		return "redirect:/project/monthGainList";
	}

	@RequestMapping(value = "/monthGainList", method = RequestMethod.GET)
	public String monthGainList(Model model, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) String keyword, @RequestParam(required = false) String status,
			Date startDate, Date endDate, Integer limitDays) {
		try {

		} catch (Exception e) {
			logger.error(e);
		}
		return "/project/monthGainList";
	}

	@RequestMapping(value = "/zzlist")
	public String zzlist(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(required = false) Integer status, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer bondDayDiff, @RequestParam(required = false) String amount,
			@RequestParam(required = false) Date startTime, @RequestParam(required = false) Date endTime) {
		try {
			int limit = 20;
			if (page == null) {
				page = 1;
			}
			String startAmount = null;
			String endAmount = null;
			if (!StringUtils.isEmpty(amount)) {
				startAmount = amount.substring(0, amount.indexOf("-"));
				endAmount = amount.substring(amount.indexOf("-") + 1, amount.length());
			}
			System.out.println(startAmount + "======" + endAmount);
			long start = System.currentTimeMillis();
			List<Project> list = projectService.queryzzList(keyword, startAmount, endAmount, bondDayDiff, status,
					startTime, endTime, (page - 1) * limit, limit);
			long end = System.currentTimeMillis();
			logger.info("query" + (end - start));
			start = System.currentTimeMillis();
			int count = projectService.queryzzListCount(keyword, startAmount, endAmount, bondDayDiff, status, startTime,
					endTime);
			end = System.currentTimeMillis();
			logger.info("queryCount" + (end - start));

			int pages = calcPage(count, limit);

			Map<String, Object> map = projectService.queryzzTotalAmount(keyword, startAmount, endAmount, bondDayDiff,
					status, startTime, endTime);
			model.addAttribute("pages", pages);
			model.addAttribute("list", list);
			model.addAttribute("page", page);
			model.addAttribute("keyword", keyword);
			model.addAttribute("bondDayDiff", bondDayDiff);
			model.addAttribute("keyword", keyword);
			model.addAttribute("amount", amount);
			model.addAttribute("status", status);
			model.addAttribute("map", map);
			if (startTime != null) {
				model.addAttribute("startTime", DateUtil.dateFormat.format(startTime));
			}
			if (endTime != null) {
				model.addAttribute("endTime", DateUtil.dateFormat.format(endTime));
			}

		} catch (Exception e) {
			logger.error(e);
		}
		return "/project/zzlist";
	}

	/***
	 * 资产包新增导入债权转让资产
	 * 
	 * @param model
	 * @param response
	 * @param prackgeId
	 * @return
	 */
	@RequestMapping(value = "/importAssetMarks", method = RequestMethod.GET)
	public String importAssetMarks(Model model, HttpServletResponse response, @RequestParam Integer prackgeId,
			@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limitDays,
			@RequestParam(required = false) Integer projectType) {

		return "/project/importAssetMarks";
	}

	/***
	 * 资产包新增导入债权转让资产
	 * 
	 * @param session
	 * @param model
	 * @param prackgeId
	 * @param ids
	 * @param splitStr
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/importAssetMarks", method = RequestMethod.POST)
	public BaseResult importAssetMarks(HttpSession session, Model model, Integer prackgeId, String ids,
			String splitStr) {
		BaseResult result = new BaseResult(false);
		String[] idArray = ids.trim().split(splitStr);

		if (prackgeId == null || idArray == null || idArray.length == 0) {
			result.setMsg("操作数据异常请检查！");
			return result;
		}
		try {
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error(e);
		}
		return result;
	}

	/**
	 * 智投产品信息
	 * 
	 * @param model
	 * @param response
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/investProductList ", method = RequestMethod.GET)
	public String investProductList(Model model, HttpServletResponse response) {

		return "/project/investProductList";
	}

	/**
	 * 根据产品id查询拥有的属性
	 * 
	 * @param productId
	 * @return
	 * @author: zj
	 */
	@ResponseBody
	@RequestMapping(value = "/listProductPropertyAjax")
	public Object listProductPropertyAjax(int productId) {
		return productPropertyService.listProductProperty(productId);
	}

	/**
	 * 查询项目的投资记录
	 * 
	 * @param model
	 * @return
	 * @author: zj
	 */
	@ResponseBody
	@RequestMapping(value = "/listInvestmentAjax")
	public Object listInvestment(Model model, Integer page, Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int limit = 20;
			if (page == null) {
				page = 1;
			}

			long start = System.currentTimeMillis();
			List<Map<String, Object>> list = projectService.listInvestmentPage(id, (page - 1) * limit, limit);
			long end = System.currentTimeMillis();
			logger.info("query" + (end - start));
			start = System.currentTimeMillis();
			int count = projectService.countInvestmentPage(id);
			end = System.currentTimeMillis();
			logger.info("queryCount" + (end - start));

			int pages = calcPage(count, limit);

			map.put("pages", pages);
			map.put("page", page);
			map.put("list", list);
			map.put("code", "1");
		} catch (Exception e) {
			map.put("code", "-1");
			logger.error(e.getMessage(), e);
		}
		return map;
	}

	/**
	 * 判断是否存在相同耳标号的标
	 * 
	 * @Title: ifExistProjectByEarnumber
	 * @param earNumber
	 * @return Object
	 * @author zj
	 * @date 2019-06-19 11:35
	 */
	@ResponseBody
	@RequestMapping(value = "/ifExistProjectByEarnumberAjax")
	public Object ifExistProjectByEarnumberAjax(String earNumber, Integer projectId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			boolean flag = projectService.ifExistProjectByEarnumber(projectId, earNumber);
			if (flag) {
				map.put("code", "1");
				map.put("msg", "该耳标号已存在");
			} else {
				map.put("code", "0");
				map.put("msg", "该耳标号不存在");
			}
			return map;
		} catch (Exception e) {
			logger.error("ifExistProjectByEarnumberAjax  出错===========>" + e.getMessage(), e);
			map.put("code", "-1");
			map.put("msg", "校验失败");
			return map;
		}

	}

	/**
	 * 检查是否存在相同的gps编号
	 * 
	 * @Title: checkGpsNumberAjax
	 * @param gpsNumber
	 * @param projectId
	 * @return Object
	 * @author zj
	 * @date 2019-07-04 14:39
	 */
	@ResponseBody
	@RequestMapping(value = "/checkGpsNumberAjax")
	public Object checkGpsNumberAjax(String gpsNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			boolean flag = projectService.checkGpsNumber(gpsNumber);
			if (flag) {
				map.put("code", "1");
				map.put("msg", "该GPS设备号已存在");
			} else {
				map.put("code", "0");
				map.put("msg", "该GPS设备号不存在");
			}
			return map;
		} catch (Exception e) {
			logger.error("checkGpsNumberAjax  出错===========>" + e.getMessage(), e);
			map.put("code", "-1");
			map.put("msg", "校验失败");
			return map;
		}

	}

	/**
	 * 根据耳标号查询回购记录
	 * 
	 * @param model
	 * @return
	 * @author: zj
	 */
	@ResponseBody
	@RequestMapping(value = "/listBuyBackRecordAjax")
	public Object listBuyBackRecord(Model model, Integer page, String earNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int limit = 20;
			if (page == null) {
				page = 1;
			}

			long start = System.currentTimeMillis();
			List<Map<String, Object>> list = projectService.listBuyBackRecord(earNumber, (page - 1) * limit, limit);
			long end = System.currentTimeMillis();
			logger.info("query" + (end - start));
			start = System.currentTimeMillis();
			int count = projectService.countBuyBackRecord(earNumber);
			end = System.currentTimeMillis();
			logger.info("queryCount" + (end - start));

			int pages = calcPage(count, limit);

			map.put("pages", pages);
			map.put("page", page);
			map.put("list", list);
			map.put("code", "1");
		} catch (Exception e) {
			map.put("code", "-1");
			logger.error(e.getMessage(), e);
		}
		return map;
	}
	
	/**
	 * 牧场管理--回购查询
	* @Title: listBuyBack 
	* @param model
	* @param startAge
	* @param endAge
	* @param startDate
	* @param endDate
	* @param page
	* @return String
	* @author zj
	* @date 2019-08-01 10:05
	 */
	@RequestMapping(value = "/listBuyBack")
	public String listBuyBack(Model model, HttpSession session,
			@RequestParam(required = false) Integer startAge, 
			@RequestParam(required = false) Integer endAge,
			@RequestParam(required = false) String startDate, 
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer departmentId) {

		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("project:buyBack:view");
			} catch (Exception e) {
				model.addAttribute("error", "您没有权限查看");
				return "error";
			}
			UserAdmin admin = (UserAdmin) session.getAttribute("user");
	        Integer adminId=null;
	        try {
				subject.checkPermission("user:seealluser");
			} catch (Exception e) {
				adminId=admin.getId();
			}
	        try {
				subject.checkPermission("project:buyBack:viewall");
				adminId=null;
			} catch (Exception e) {
			}
	        
			int limit = 10;
			if (page == null) {
				page = 1;
			}

			long start = System.currentTimeMillis();
			List<Map<String, Object>> list = projectService.listBuyBack(startDate, endDate, startAge, endAge, (page - 1) * limit, limit,adminId,departmentId);
			System.out.println("===============" + list.size() + "==============");
			long end = System.currentTimeMillis();
			logger.info("query" + (end - start));
			start = System.currentTimeMillis();
			int count = projectService.countBuyBack(startDate, endDate, startAge, endAge,adminId,departmentId);
			end = System.currentTimeMillis();
			logger.info("queryCount" + (end - start));

			int pages = calcPage(count, limit);
			model.addAttribute("pages", pages);
			model.addAttribute("list", list);
			model.addAttribute("page", page);
			model.addAttribute("startAge", startAge);
			model.addAttribute("endAge", endAge);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("statisticsInfo", projectService.statisticsInfo());
			model.addAttribute("departmentId", departmentId);
			if(adminId==null) {
				model.addAttribute("departments", departmentService.getAllDepartment());
			}else {
				model.addAttribute("departments", departmentService.getShowDepartments(admin.getDepartmentId()));
			}
			

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "/project/listBuyBack";
	}
	
	
	@RequestMapping(value = "/lifePicList")
	public String lifePicList(Model model, HttpServletResponse response, String earNumber, Integer page) {
		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("project:lifePic:list");
			} catch (Exception e) {
				model.addAttribute("error", "您没有权限");
				return "error";
			}
			int limit = 8;
			if (page == null) {
				page = 1;
			}
			List<ProjectLifePicture> lifePicList = new ArrayList<>();
			lifePicList = projectService.queryLifePictureByEarNumber(earNumber, (page - 1) * limit, limit);
			int count =  (int)projectService.countLifePictureByEarNumber(earNumber);
			int pages = calcPage(count, limit);
			
			model.addAttribute("pages", pages);
			model.addAttribute("list", lifePicList);
			model.addAttribute("page", page);
			model.addAttribute("earNumber", earNumber);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return "/project/lifepicList";
	}
	
	
	@RequestMapping(value = "/addpic", method = RequestMethod.GET)
	public String addpic(Model model, String earNumber) {
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.checkPermission("project:lifePic:upload");
		} catch (Exception e) {
			model.addAttribute("error", "您没有权限上传");
			return "error";
		}
		model.addAttribute("earNumber", earNumber);
		return "/project/lifepic";
	}
	/**
	 * @date 2019年9月11日
	 * @author wangyun
	 * @time 上午10:38:59
	 * @Description 上传 牛只生活照
	 * 
	 * @param model
	 * @param response
	 * @param files
	 * @param earNumber
	 * @return
	 */
	@RequestMapping(value = "/uploadLifePic")
	@ResponseBody
	public JSONObject uploadLifePic(Model model, HttpServletResponse response,HttpSession session,
			@RequestParam("file") MultipartFile file, 
			@RequestParam("earNumber") String earNumber) {
		try {
			// 根据耳标号查询项目
			List<Project> list = projectService.queryProjectByEarNumber(earNumber);
			if(list == null || list.size() == 0) {
				model.addAttribute("error", "耳标号错误！");
				return null;
			}
			int adminId = ((UserAdmin) session.getAttribute("user")).getId();
			projectService.saveAddLifePicture(file, earNumber, adminId);
			JSONObject json = new JSONObject();
			json.put("code", 200);// 接口插件需要返回上传成功code
			json.put("data", null);
			return json;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}


/**
 * 上传视频页面跳转
 * @param model
 * @param id
 * @return : java.lang.String
 * @author : zj
 * @date : 2019/9/12 10:47
 */
	@Token(save = true)
	@RequestMapping(value = "/addVideoPage", method = RequestMethod.GET)
	public String addVideoPage(Model model, Integer id) {
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.checkPermission("project:addVideoPage");
		} catch (Exception e) {
			model.addAttribute("error", "您没有权限查看");
			return "error";
		}

		logger.info("========进入上传视频的方法====addVideoPage=====");


		return "/project/addVideo";
	}


	
	/**
	 * @date 2019年9月18日
	 * @author wangyun
	 * @time 下午1:20:44
	 * @Description 添加视频入库
	 * 
	 * @param model
	 * @param response
	 * @param videoId
	 * @param title
	 * @param isRecommend
	 * @param picpath
	 */
	@Token(save = true)
	@RequestMapping(value = "/addVideo",method = RequestMethod.POST)
	@ResponseBody
	public void addVideo(Model model, HttpServletResponse response, String videoIds,String title, Integer isRecommend, String picpath, String showTime) {
		Subject subject = SecurityUtils.getSubject();
		JSONObject json = new JSONObject();
		try {
		 	subject.checkPermission("project:addVideo");
		} catch (Exception e) {
			model.addAttribute("error", "您没有权限上传视频"); 
			return;
		}
		
		logger.info("===========上传视频===============");
		try {
			System.err.println(videoIds+"-------------");
			if(videoIds == null) {
				json.put("code", Constants.FAIL);
				AjaxUtil.str2front(response, JSON.toJSONString(json));
			}
			
			String[] videoIdArr = videoIds.split(",");
			if(videoIdArr.length == 0) {
				json.put("code", Constants.FAIL);
				AjaxUtil.str2front(response, JSON.toJSONString(json));
			}
			
			for (String videoId : videoIdArr) {
				VideoAlbum video = projectService.getVideoAlbumById(Integer.parseInt(videoId));
				if(video == null) {
					json.put("code", Constants.FAIL);
					AjaxUtil.str2front(response, JSON.toJSONString(json));
					break;
				}
				video.setUpdateTime(new Date());
				video.setShowTime(DateUtil.parse(showTime, "yyyy-MM-dd HH:mm:ss"));
				video.setTitle(title);
				video.setIsRecommend(isRecommend);
				video.setVideoPageUrl(picpath);
				video.setStatus(1);// 1成功 0失败
				projectService.updateVideoAlbum(video);
				
			}
			json.put("code", Constants.SUCCESS); 
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", Constants.FAIL);
		}
		 
		AjaxUtil.str2front(response, JSON.toJSONString(json));
	}
	
	@RequestMapping(value = "/uploadVideo", method = RequestMethod.POST)
	@ResponseBody
	public void uploadVideo(@RequestParam MultipartFile file, Integer type, HttpServletResponse response,
			HttpSession session) {
		Map<String, Object> map = null;
		logger.info("==========进入uploadVideo ======== ");
		try {
			int adminId = ((UserAdmin) session.getAttribute("user")).getId();
			map = projectService.addUploadVideo(file, adminId);
			logger.info("==========结束 uploadVideo ======== ");
		} catch (Exception e) {
			logger.error("uploadVideo上传视频出错========>"+e.getMessage(),e);
		}
		AjaxUtil.str2front(response, JSON.toJSONString(map));
	}
	
	/**
	 * @date 2019年9月19日
	 * @author wangyun
	 * @time 下午5:50:58
	 * @Description 视频列表
	 * 
	 * @param model
	 * @param response
	 * @param page
	 * @param keyword
	 * @param isRecommend
	 * @param status
	 * @param beginCreateTime
	 * @param endCreateTime
	 * @param beginShowTime
	 * @param endShowTime
	 * @return
	 */
	@RequestMapping(value = "/videoAlbumList", method = RequestMethod.GET)
	public String queryVideoAlbumList(Model model, HttpServletResponse response, Integer page,String keyword,Integer isRecommend,
			Date beginCreateTime, Date endCreateTime,
			Date beginShowTime, Date endShowTime) {
		try {
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.checkPermission("project:video:list");
			} catch (Exception e) {
				model.addAttribute("error", "您没有权限");
				return "error";
			}
			
			int limit = 6;
			if (page == null) {
				page = 1;
			}
			List<VideoAlbum> videoList = new ArrayList<>();
			
			videoList = projectService.queryVideoAlbumList((page - 1) * limit, limit, keyword,isRecommend,beginCreateTime,endCreateTime,beginShowTime,endShowTime);
			int count =   projectService.countVideoAlbumList(keyword,isRecommend,beginCreateTime,endCreateTime,beginShowTime,endShowTime);
			int pages = calcPage(count, limit);
		 
			model.addAttribute("pages", pages);
			model.addAttribute("list", videoList);
			model.addAttribute("page", page); 
			model.addAttribute("keyword", keyword); 
			model.addAttribute("isRecommend", isRecommend); 
			model.addAttribute("beginCreateTime", beginCreateTime); 
			model.addAttribute("endCreateTime", endCreateTime); 
			model.addAttribute("beginShowTime", beginShowTime); 
			model.addAttribute("endShowTime", endShowTime); 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return "/project/videoList";
	}
	
	/**
	 * @date 2019年9月19日
	 * @author wangyun
	 * @time 下午5:51:12
	 * @Description 删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "/videoDelete", method = RequestMethod.POST)
	public void videoDelete(Model model, HttpServletResponse response, Integer id) {
		Map<String, Object> map = new HashMap<>();
		try {
			VideoAlbum video = projectService.getVideoAlbumById(id);
			if(video == null) {
				map.put(STATUS, ERROR);
				AjaxUtil.str2front(response, JSON.toJSONString(map));
			}
			
			video.setStatus(2);//删除
			projectService.updateVideoAlbum(video);
			map.put(STATUS, SUCCESS);
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}
		AjaxUtil.str2front(response, JSON.toJSONString(map));
	}
	
	/**
	 * @date 2019年9月20日
	 * @author wangyun
	 * @time 上午10:52:32
	 * @Description 编辑推荐
	 * 
	 * @param model
	 * @param response
	 * @param id
	 * @param isRecommend
	 */
	@RequestMapping(value = "/videoRecommend", method = RequestMethod.GET)
	public void videoRecommend(Model model, HttpServletResponse response, Integer id, Integer isRecommend) {
		Map<String, Object> map = new HashMap<>();
		try {
			VideoAlbum video = projectService.getVideoAlbumById(id);
			if(video == null) {
				map.put(STATUS, ERROR);
				AjaxUtil.str2front(response, JSON.toJSONString(map));
			}
			
			video.setIsRecommend(isRecommend);//1 是 0 否
			projectService.updateVideoAlbum(video);
			map.put(STATUS, SUCCESS);
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}
		AjaxUtil.str2front(response, JSON.toJSONString(map));
	}
	
	/**
	 * @date 2019年9月20日
	 * @author wangyun
	 * @time 上午10:57:51
	 * @Description 编辑视频
	 * 
	 * @return
	 */
	@RequestMapping(value = "/editVideo", method = RequestMethod.GET)
	public String editVideo(Model model, Integer id) {
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.checkPermission("project:editVideo");
		} catch (Exception e) {
			model.addAttribute("error", "您没有权限");
			return "error";
		}
		VideoAlbum video = projectService.getVideoAlbumById(id);
		if(video == null) {
			model.addAttribute("msg", "视频id错误");
			 return "/project/videoList";
		}
		model.addAttribute("video",video);
		return "project/editVideo";
	}
	
	/**
	 * @date 2019年9月20日
	 * @author wangyun
	 * @time 下午1:38:37
	 * @Description 提交编辑视频
	 * 
	 * @param model
	 * @param session
	 * @param video
	 * @return
	 */
	@RequestMapping(value = "/submitEditVideo", method = RequestMethod.POST)
	public String submitEditVideo(Model model,HttpSession session, VideoAlbum video, String showTime) {
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.checkPermission("project:submitEditVideo");
		} catch (Exception e) {
			model.addAttribute("error", "您没有权限");
			return "error";
		}
		VideoAlbum editVideo = projectService.getVideoAlbumById(video.getId());
		if(editVideo == null) {
			model.addAttribute("msg", "视频id错误");
			 return "/project/videoList";
		}
		
		int adminId = ((UserAdmin) session.getAttribute("user")).getId();
		editVideo.setOperaterId(adminId);
		editVideo.setTitle(video.getTitle());
		editVideo.setIsRecommend(video.getIsRecommend());
		editVideo.setShowTime(DateUtil.parse(showTime, "yyyy-MM-dd HH:mm:ss"));
		editVideo.setVideoPageUrl(video.getVideoPageUrl());
		editVideo.setUpdateTime(new Date());
		
		projectService.updateVideoAlbum(editVideo);
		return "redirect:/project/videoAlbumList";
	}
	
	/**
	 * @date 2019年9月19日
	 * @author wangyun
	 * @time 下午5:51:12
	 * @Description 生活照删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "/lifePictureDelete", method = RequestMethod.GET)
	public void lifePictureDelete(Model model, HttpServletResponse response, Integer id) {
		Map<String, Object> map = new HashMap<>();
		try {
			ProjectLifePicture picture = projectService.queryLifePictureById(id);
			if(picture == null) {
				map.put(STATUS, ERROR);
				AjaxUtil.str2front(response, JSON.toJSONString(map));
			}
			
			picture.setStatus(2);//删除
			projectService.updateProjectLifePicture(picture);
			map.put(STATUS, SUCCESS);
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}
		AjaxUtil.str2front(response, JSON.toJSONString(map));
	}
	
	@RequestMapping(value = "/calculateBuyMoney", method = RequestMethod.POST)
	@ResponseBody
	public void calculate(@RequestParam Integer productId, 
			@RequestParam Integer sex,
			@RequestParam Integer limitDays, 
			@RequestParam BigDecimal weight,
		    @RequestParam(required = false) BigDecimal annualized,
			HttpServletResponse response,
			HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		BuyBullsDetailMoneyVO vo = null; 
		try {
			Product product = productService.selectById(productId);
			if(product == null) {
				logger.info(productId + "未查询到产品信息");
				map.put(STATUS, ERROR);
				AjaxUtil.str2front(response, JSONObject.toJSONString(map));
				return;
			}
			vo = projectService.caculateBuyMoney(String.valueOf(sex), 
					weight, 
					BigDecimal.valueOf(product.getAddWeight()), 
					BigDecimal.valueOf(product.getFeedPrice()) ,
					sex == 1 ? BigDecimal.valueOf(product.getFemalePrice()): BigDecimal.valueOf(product.getMalePrice()),
							ProjectDaysEnum.getValueByType(limitDays), annualized);
			map.put("vo", vo);
			logger.info("计算饲养费信息:" + vo.toString());
			map.put(STATUS, SUCCESS);
		} catch (Exception e) {
			logger.error("计算饲养需要支付的资金出错========>"+e.getMessage(),e);
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}
		AjaxUtil.str2front(response, JSONObject.toJSONString(map));
	}
	
}
