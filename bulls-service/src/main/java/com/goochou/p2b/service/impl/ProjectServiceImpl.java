package com.goochou.p2b.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.constant.*;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.constant.pasture.InvestPayStateEnum;
import com.goochou.p2b.constant.pasture.InvestmentStateEnum;
import com.goochou.p2b.constant.pasture.ProjectStatusEnum;
import com.goochou.p2b.dao.*;
import com.goochou.p2b.exception.ResponseException;
import com.goochou.p2b.model.*;
import com.goochou.p2b.model.ProjectExample.Criteria;
import com.goochou.p2b.model.vo.*;
import com.goochou.p2b.model.vo.bulls.BuyBullsDetailMoneyVO;
import com.goochou.p2b.model.vo.bulls.EnableKeepPeriodVO;
import com.goochou.p2b.model.vo.bulls.KeepPeriodVO;
import com.goochou.p2b.model.vo.p2peye.P2pEyeInvestData;
import com.goochou.p2b.model.vo.p2peye.P2pEyeLoansData;
import com.goochou.p2b.service.*;
import com.goochou.p2b.service.exceptions.LockFailureException;
import com.goochou.p2b.service.memcached.MemcachedManager;
import com.goochou.p2b.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
	private final static Logger logger = Logger.getLogger(ProjectServiceImpl.class);
	@Resource
	private ProjectMapper projectMapper;
	@Resource
	private ProjectPictureMapper projectPictureMapper;
	@Resource
	private EnterpriseMapper enterpriseMapper;
	@Resource
	public UploadMapper uploadMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private InvestmentMapper investmentMapper;
	@Resource
	private ProjectCreditorMapper projectCreditorMapper;
	@Resource
	private PromptMapper promptMapper;
	@Resource
	private ProjectContractMapper projectContractMapper;
	@Resource
	private AppVersionContentMapper appVersionContentMapper;
	@Resource
	private ProjectLinkProjectMapper projectLinkProjectMapper;
	@Resource
	private ProductMapper productMapper;
	@Resource
	private RateCouponService rateCouponService;
	@Resource
	private HongbaoService hongbaoService;

	@Resource
	private EnterpriseService enterpriseService;
	@Autowired
	private ProjectPropertyValueService projectPropertyValueService;
	@Autowired
	private ProductPropertyService productPropertyService;
	@Resource
	private MemcachedManager memcachedManager;


	@Autowired
	private OrderDoneService orderDoneService;

	@Resource
	private InvestmentService investmentService;
	@Resource
	private InterestService interestService;
	@Resource
	private ProjectViewMapper projectViewMapper;
	@Resource
	private ProjectPropertyViewMapper projectPropertyViewMapper;
	@Resource
	private UploadService uploadService;
	@Resource
	private ProjectLifePictureMapper projectLifePictureMapper;
	@Resource
	private VideoAlbumMapper videoAlbumMapper;
	@Resource
	private RechargeService rechargeService;
	@Autowired
    private RedisService redisService;

	public ProjectMapper getMapper() {
		return projectMapper;
	}


	@Override
	public boolean isShowBulls(User user, String appName, String dataSource, String client) {

		//是否屏蔽养牛信息
		boolean isShowBulls=true;
		if (client.equals(ClientEnum.IOS.getFeatureName())) {
			if (Constants.NO.equals(memcachedManager.getCacheKeyValue(DictConstants.BULLS_SHOW_IOS))) {
				isShowBulls = false;
			}
		} else if (client.equals(ClientEnum.ANDROID.getFeatureName())) {
			if (Constants.NO.equals(memcachedManager.getCacheKeyValue(DictConstants.BULLS_SHOW_ANDROID))) {
				isShowBulls = false;
			}
		} else if (client.equals(ClientEnum.WAP.getFeatureName())) {
			if (Constants.NO.equals(memcachedManager.getCacheKeyValue(DictConstants.BULLS_SHOW_WAP))) {
				isShowBulls = false;
			}
		}

		if("不显示牛".equals(appName)){
			isShowBulls=false;
		}

		//华为市场上线时屏蔽  注意：上线后要关闭
		if(isHidden(user, dataSource)){
			isShowBulls=false;
		}

		//会员用户可查看
		if(isShowBulls==false){
			if(user!=null && user.getLevel()>0){
				isShowBulls = true;
			}
		}
		return isShowBulls;
	}

	@Override
	public boolean isHidden(User user, String appChannel) {
		return isHidden(user,appChannel,false);
	}

	@Override
	public boolean isHidden(User user, String appChannel,boolean isbalance) {

		boolean isHidden =false;

		String appChannels = memcachedManager.getCacheKeyValue(DictConstants.APP_CHANNEL);

		if(isbalance) {
			appChannels = memcachedManager.getCacheKeyValue(DictConstants.APP_CHANNEL_BALANCE);
		}

		if(StringUtils.isNotEmpty(appChannels)) {
			String[] channels = appChannels.split(",");

			for (String channel : channels) {
				//华为市场
				if (channel.equals(appChannel)) {
					isHidden = true;
					break;
				}
			}
		}

		return isHidden;
	}

	@Override
	public List<Map<String, Object>> queryBond2(String keyword, Integer status, int start, int limit, Date startTime,
			Date endTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("start", start);
		params.put("limit", limit);
		params.put("status", status);

		return projectMapper.selectDebts(params);
	}

	@Override
	public List<Project> query(String keyword, Integer status, int start, int limit, String orderBy, int sort)
			throws Exception {
		return this.query(keyword, status, null, start, limit, orderBy, sort);
	}

	@Override
	public List<Project> query(String keyword, Integer status, Integer projectType, int start, int limit,
			String orderBy, int sort) {
		ProjectExample example = new ProjectExample();
		Criteria c = example.createCriteria();
		if (StringUtils.isNotBlank(keyword)) {
			c.andTitleLike("%" + keyword + "%");
		}
		if (status != null) {
			c.andStatusEqualTo(status);
		}
		if (projectType != null) {
			// 根据传入进来的projectType进行查询(一般用户查询活期理财项目)
			c.andProjectTypeEqualTo(projectType);
		} else {
			// 非活期理财项目
			c.andProjectTypeNotEqualTo(2);
		}
		c.andParentIdIsNull();
		example.setLimitStart(start);
		example.setLimitEnd(limit);
		if (StringUtils.isNotBlank(orderBy)) {
			if (sort == 0) {
				example.setOrderByClause(orderBy);
			} else {
				example.setOrderByClause(orderBy + " desc");
			}
		}
		return projectMapper.selectByExample(example);
	}

	@Override
	public List<Project> queryList(String keyword, Integer status, Integer projectType, Integer start, Integer limit,
			String orderBy, String sort) throws Exception {
		return queryList(keyword, status, projectType, null, null, start, limit, orderBy, sort, null, null);
	}


	@Override
	public Integer countProjectList(String keyword, Integer status, Integer projectType, Integer noob,Integer limitDays,String lendBeginTimeStartTime,
			String lendBeginTimeEndTime) {
		return projectMapper.countProjectList(keyword, status, projectType, noob, limitDays, lendBeginTimeStartTime,
				 lendBeginTimeEndTime);
	}
	public Integer queryListCount(String keyword, Integer status, Integer projectType, Integer noob) {
		return projectMapper.queryListCount(keyword, status, projectType, noob);
	}

	@Override
	public Integer queryCount(String keyword, Integer status) {
		return this.queryListCount(keyword, status, null, null);
	}

	@Override
	public List<Project> queryList(String keyword, Integer status, Integer projectType, Integer noob, Integer limitDays,
			Integer start, Integer limit, String orderBy, String sort, String lendBeginTimeStartTime,
			String lendBeginTimeEndTime) throws Exception {
		List<Project> list = projectMapper.queryList(keyword, status, projectType, noob, limitDays, start, limit,
				orderBy, sort, lendBeginTimeStartTime, lendBeginTimeEndTime);
//		for (Project project : list) {
//			Double a = BigDecimalUtil.sub(project.getAnnualized(), project.getIncreaseAnnualized());
//			project.setAnnualized(Float.parseFloat(a.toString()));
//		}
		return list;
	}

	@Override
	public List<Project> queryIndex(Integer status, int start, int limit, Integer noob) {
		ProjectExample example = new ProjectExample();
		Criteria c = example.createCriteria();
		c.andNoobEqualTo(noob);
		c.andStatusEqualTo(status);
		example.setOrderByClause("total_amount asc,status,create_date desc");
		example.setLimitStart(start);
		example.setLimitEnd(limit);

		return projectMapper.selectByExample(example);
	}

	@Override
	public List<Project> queryBondIndex(int start, int limit) {
		ProjectExample example = new ProjectExample();
		Criteria c = example.createCriteria();
		c.andStatusNotEqualTo(2); // 取消的债券不查询
		c.andParentIdIsNotNull();
		c.andProjectTypeEqualTo(1);
		c.andParentIdGreaterThanOrEqualTo(506);
		example.setLimitStart(start);
		example.setLimitEnd(limit);
		example.setOrderByClause("STATUS ,create_time desc");
		return projectMapper.selectByExample(example);
	}

	@Override
	public Integer queryIndexCount() {
		ProjectExample example = new ProjectExample();
		Criteria c = example.createCriteria();
		c.andStatusGreaterThanOrEqualTo(2);
		c.andParentIdIsNull();
		c.andIdGreaterThanOrEqualTo(506);
		c.andProjectTypeEqualTo(0);
		return projectMapper.countByExample(example);
	}

	@Override
	public Integer queryBondIndexCount() {
		ProjectExample example = new ProjectExample();
		Criteria c = example.createCriteria();
		c.andStatusNotEqualTo(2); // 取消的债券不查询
		c.andParentIdIsNotNull();
		c.andProjectTypeEqualTo(1);
		c.andParentIdGreaterThanOrEqualTo(506);
		return projectMapper.countByExample(example);
	}

	@Override
	public Integer queryCount(String keyword, Integer status, Integer projectType) {
		return queryListCount(keyword, status, projectType, null);
	}

	@Override
	public Project detail(Integer id) {
		Project project = projectMapper.selectByPrimaryKey(id);
//		if (project != null) {
//			if (project.getProjectType() == 0 || project.getProjectType() == 1 || project.getProjectType() == 2
//					|| project.getProjectType() == 3) {
//				Double trueAmount = investmentMapper.selectTrueAmount(project.getId());
////		project.setTrueAmount(trueAmount);
//			}
//		}
		return project;
	}

	@Override
	public Map<String, Object> detailSafe(Integer id, Integer projectType) {
		if (projectType.equals(0)) {
			return projectMapper.detailSafe(id);
		} else if (projectType.equals(1)) {
			return projectMapper.detailSafeBond(id);
		}
		return null;
	}

	@Override
	public Project detailNew(Integer id, Integer projectType) {
		Project project = null;
		if (projectType.equals(0)) {
			project = projectMapper.detailNew(id);
		} else if (projectType.equals(1)) {
			project = projectMapper.detailNewBond(id);
		}

		if (project.getEnterpriseId() != null) {
			if (enterpriseMapper.selectByPrimaryKey(project.getEnterpriseId()) != null) {
//		project.setEnterprise(enterpriseMapper.selectByPrimaryKey(project.getEnterpriseId()));
			}
		}

		// 判断是否是线上合规注册的借款人
//	Integer userId = project.getEnterprise().getUserId();
		// 获取借款人
//	User user = userMapper.selectByPrimaryKey(userId);
		// 合规的

		List<ProjectPicture> list = null;

		if (projectType.equals(0)) {
//		list = project.getPictures();
		} else if (projectType.equals(1)) {
//		list = project.getProject().getPictures();
		}
		if (list != null & !list.isEmpty()) {

			List<ProjectPicture> l1 = new ArrayList<>();
			List<ProjectPicture> l2 = new ArrayList<>();
			if (null != list && !list.isEmpty()) {
				for (ProjectPicture p : list) {
					try {
						p.getUpload().setPath(ClientConstants.ALIBABA_PATH + "upload/" + p.getUpload().getPath());
						if (p.getType() == 1) {
							l1.add(p);
						} else if (p.getType() == 2) {
							l2.add(p);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (projectType.equals(0)) {
//		    project.setPictures(null);
			} else if (projectType.equals(1)) {
//		    project.getProject().setPictures(null);
			}
//		project.setProjectPictures(l1);
//		project.setBorrowPictures(l2);
		}

		return project;
	}

	@Override
	public Project detailNewPC(Integer id, Integer projectType) {
		Project project = null;
		if (projectType.equals(0)) {
			project = projectMapper.detailNew(id);
		} else if (projectType.equals(1)) {
			project = projectMapper.detailNewBondPC(id);
		}

		if (project.getEnterpriseId() != null) {
			if (enterpriseMapper.selectByPrimaryKey(project.getEnterpriseId()) != null) {
//		project.setEnterprise(enterpriseMapper.selectByPrimaryKey(project.getEnterpriseId()));
			}
		}
		List<ProjectPicture> list = null;

		if (projectType.equals(0)) {
//	    list = project.getPictures();
		} else if (projectType.equals(1)) {
//	    list = project.getProject().getPictures();
		}
		if (list != null & !list.isEmpty()) {

			List<ProjectPicture> l1 = new ArrayList<>();
			List<ProjectPicture> l2 = new ArrayList<>();
			if (null != list && !list.isEmpty()) {
				for (ProjectPicture p : list) {
					try {
						p.getUpload().setPath(ClientConstants.ALIBABA_PATH + "upload/" + p.getUpload().getPath());
						if (p.getType() == 1) {
							l1.add(p);
						} else if (p.getType() == 2) {
							l2.add(p);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (projectType.equals(0)) {
//		project.setPictures(null);
			} else if (projectType.equals(1)) {
//		project.getProject().setPictures(null);
			}
//	    project.setProjectPictures(l1);
//	    project.setBorrowPictures(l2);
		}

		return project;
	}

	@Override
	public Project showDetail(Integer id) {
		return null;
	}

	@Override
	public void update(Project project) throws Exception {
		projectMapper.updateByPrimaryKeySelective(project);
	}

	@Override
	public void updateAndLock(Project project) throws Exception {
		this.updateByPrimaryKeySelectiveForVersion(project);
	}

	@Override
	public void updateByPrimaryKeyForVersion(Project project) throws Exception {
		if (project.getId() == null) {
			throw new Exception("id不能为空");
		}
		if (project.getVersion() == null) {
			throw new Exception("版本号不能为空");
		}
		ProjectExample example = new ProjectExample();
		example.createCriteria().andIdEqualTo(project.getId())
				.andVersionEqualTo(project.getVersion());
		project.setVersion(project.getVersion() + 1);
		if (1 != projectMapper.updateByExample(project, example)) {
			throw new LockFailureException();
		}
	}
	@Override
	public void updateByPrimaryKeySelectiveForVersion(Project project) throws Exception {
		if (project.getId() == null) {
			throw new Exception("id不能为空");
		}
		if (project.getVersion() == null) {
			throw new Exception("版本号不能为空");
		}
		ProjectExample example = new ProjectExample();
		example.createCriteria().andIdEqualTo(project.getId())
				.andVersionEqualTo(project.getVersion());
		project.setVersion(project.getVersion() + 1);
		if (1 != projectMapper.updateByExampleSelective(project, example)) {
			throw new LockFailureException();
		}
	}

	@Override
	public boolean checkNameExists(String title, Integer id) throws Exception {
		if (id != null) {
			Project project = projectMapper.selectByPrimaryKey(id);
			if (project.getTitle().equals(title)) { // 无更改则不需要验证
				return true;
			}
		}
		ProjectExample example = new ProjectExample();
		Criteria c = example.createCriteria();
		c.andTitleEqualTo(StringUtils.trim(title));
		List<Project> list = projectMapper.selectByExample(example);
		return list.size() > 0 ? false : true;
	}

	@Override
	public void saveWithPicture(Project project, List<String> pictures, String[] productPropertyIdArray,
			String[] propertyValueArray) throws Exception {

		try {

			project.setStatus(ProjectStatusEnum.BUILDED.getCode());
			Date d = new Date();
			// if (project.getDeadline() != null) {
			// Calendar c = Calendar.getInstance();
			// c.setTime(project.getDeadline());
			// c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
			// c.get(Calendar.DATE), 23, 59, 59);
			// project.setDeadline(c.getTime());// 时间是每天的23:59:59
			// }
			//	project.setCreateTime(d);
			project.setDeadline(null);
			// 取整百
			double yu = project.getTotalAmount() % 100;
			project.setTotalAmount(BigDecimalUtil.sub(project.getTotalAmount(), yu));
			projectMapper.insertSelective(project);
			projectPropertyValueService.saveProjectPropertyValue(productPropertyIdArray, propertyValueArray,
					project.getId());
	
			if (pictures != null && pictures.size() > 0) {
				for (String s : pictures) {
					updatePicture(s, project.getId());
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}


	public void updatePicture(String picture, Integer projectId) {
		if (StringUtils.isNotBlank(picture)) {
			String[] ps = picture.split(",");
			if (ps != null && ps.length > 0) {
				for (int i = 0; i < ps.length; i++) {
					ProjectPicture p = projectPictureMapper.selectByPrimaryKey(Integer.parseInt(ps[i]));
					if (!StringUtil.isNull(p.getProjectId())) {
						p.setId(null);
						p.setProjectId(projectId);
						p.setStatus(0);
						projectPictureMapper.insertSelective(p);
					} else {
						p.setProjectId(projectId);
						p.setStatus(0);
						projectPictureMapper.updateByPrimaryKeySelective(p);
					}
				}
			}
		}
	}

	@Override
	public void update(Project project, String picture, String picture2, String[] productPropertyIdArray,
			String[] propertyValueArray) throws Exception {
		Project oldProject = this.getMapper().selectByPrimaryKey(project.getId());
		if (oldProject.getStatus() != ProjectStatusEnum.BUILDED.getCode()) {
			throw new Exception("只能编辑新建的项目");
		}
		project.setVersion(oldProject.getVersion());
		if (project.getStatus() != ProjectStatusEnum.BUILDED.getCode()
				&& project.getStatus() != ProjectStatusEnum.ENABLE_SALE.getCode()&&project.getStatus()!=ProjectStatusEnum.DEAD.getCode()) {
			throw new Exception("编辑项目状态只能改为新建或上架或者死亡");
		}
		if (project.getStatus() == ProjectStatusEnum.ENABLE_SALE.getCode()) {// 已发布
			project.setStartTime(new Date());
		}
//		if (project.getProjectType().equals(5) && project.getStatus().equals(1)) {
//			Product product = productMapper.selectByPrimaryKey(project.getProductId());
//			// product.setOpenAmount(BigDecimalUtil.add(product.getOpenAmount(),
//			// project.getTotalAmount()));
//			productMapper.updateByPrimaryKeySelectiveAndVersion(product);
//		}
		// 取整百
		double yu = project.getTotalAmount() % 100;
		project.setTotalAmount(BigDecimalUtil.sub(project.getTotalAmount(), yu));
		this.updateByPrimaryKeySelectiveForVersion(project);
		projectPropertyValueService.updateProjectPropertyValue(productPropertyIdArray, propertyValueArray,
				project.getId());
		updatePicture(picture, project.getId());
		updatePicture(picture2, project.getId());
	}

	@Override
	public List<Project> queryBond(String keyword, Integer status, int start, int limit, String orderBy, int sort,
			Date startTime, Date endTime) throws Exception {
		ProjectExample example = new ProjectExample();
		Criteria c = example.createCriteria();
		c.andIdGreaterThanOrEqualTo(506);
		if (!StringUtils.isEmpty(keyword)) {
			c.andTitleLike("%" + keyword + "%");
		}
		if (startTime != null) {
//	    c.andCreateTimeGreaterThan(startTime);
		}
		if (endTime != null) {
//	    c.andCreateTimeLessThanOrEqualTo(endTime);
		}
		if (status != null) {
			c.andStatusEqualTo(status);
		} else {
			c.andStatusNotEqualTo(2);// 取消的不查
		}
		c.andParentIdIsNotNull();
		example.setLimitStart(start);
		example.setLimitEnd(limit);
		if (StringUtils.isNotBlank(orderBy)) {
			if (sort == 0) {
				example.setOrderByClause(orderBy);
			} else {
				example.setOrderByClause(orderBy + " desc");
			}
		}
		List<Project> list = projectMapper.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			for (Project p : list) {
//		p.setProject(projectMapper.selectByPrimaryKey(p.getParentId())); //
				// 查询转让的用户
//		p.setUser(userMapper.selectByPrimaryKey(p.getUserId()));
			}
		}
		return list;
	}

	@Override
	public Integer queryBondCount(String keyword, Integer status, Date startTime, Date endTime) {
		ProjectExample example = new ProjectExample();
		Criteria c = example.createCriteria();
		c.andIdGreaterThanOrEqualTo(506);
		if (!StringUtils.isEmpty(keyword)) {
			c.andTitleLike("%" + keyword + "%");
		}
		if (startTime != null) {
//	    c.andCreateTimeGreaterThan(startTime);
		}
		if (endTime != null) {
//	    c.andCreateTimeLessThanOrEqualTo(endTime);
		}
		if (status != null) {
			c.andStatusEqualTo(status);
		} else {
			c.andStatusNotEqualTo(2);// 取消的不查
		}
		c.andParentIdIsNotNull();
		return projectMapper.countByExample(example);
	}

	@Override
	public Project detailBond(Integer id) {
		Project p = projectMapper.selectByPrimaryKey(id);
		if (p != null) {
//	    p.setProject(projectMapper.selectByPrimaryKey(p.getParentId())); // 查询债券转让的项目
			// 查询转让的用户
//	    p.setUser(userMapper.selectByPrimaryKey(p.getUserId()));
		}
		return p;
	}

	@Override
	public Integer count() {
		return projectMapper.countByExample(new ProjectExample());
	}

	@Override
	public List<Map<String, Object>> getInvestors(Integer projectId, Integer status, Integer start, Integer limit) {
		Project p = projectMapper.selectByPrimaryKey(projectId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", projectId);
		params.put("start", start);
		params.put("limit", limit);
		params.put("status", status);
		if (p != null && (p.getProjectType() == 5 || p.getProjectType() == 6)) {
			return projectMapper.getInvestors1(params);
		}
		return projectMapper.getInvestors(params);
	}

	@Override
	public List<Map<String, Object>> getMonthlyGainInvestors(Integer projectId, Integer status, Integer start,
			Integer limit) {
		Project p = projectMapper.selectByPrimaryKey(projectId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", projectId);
		params.put("start", start);
		params.put("limit", limit);
		params.put("status", status);
		return projectMapper.getMonthlyGainInvestors(params);
	}

	@Override
	public int getMonthlyGainInvestorsCount(Integer projectId) {
		return projectMapper.getMonthlyGainInvestorsCount(projectId);
	}

	@Override
	public int getInvestorsCount(Integer projectId, Integer status) {
		Project p = projectMapper.selectByPrimaryKey(projectId);
		if (p != null && (p.getProjectType() == 5 || p.getProjectType() == 6)) {
			return projectMapper.getInvestorsCount1(projectId, status);
		}
		return projectMapper.getInvestorsCount(projectId, status);
	}

	@Override
	public Project get(Integer id) {
		return projectMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Map<String, Object>> getUsableBondList(Integer userId, Integer start, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("start", start);
		map.put("limit", limit);
		return projectMapper.getUsableBondList(map);
	}

	@Override
	public Integer getUsableBondListCount(Integer userId) {
		return projectMapper.getUsableBondListCount(userId);
	}

	@Override
	public Map<String, Object> getTransferedProjectByUserId(Integer userId) {
		return projectMapper.getTransferedProjectByUserId(userId);
	}

	@Override
	public List<Map<String, Object>> getAssignBondList(Integer userId, Integer status, Integer start, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("status", status);
		map.put("start", start);
		map.put("limit", limit);
		return projectMapper.getAssignBondList(map);
	}

	@Override
	public int getAssignBondCount(Integer userId, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("status", status);
		return projectMapper.getAssignBondCount(map);
	}

	@Override
	public List<Map<String, Object>> getPurchasedBondList(Integer userId, Boolean hasDividended, Integer start,
			Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("hasDividended", hasDividended);
		map.put("start", start);
		map.put("limit", limit);
		return projectMapper.getPurchasedBondList(map);
	}

	@Override
	public int getPurchasedBondCount(Integer userId) {
		return projectMapper.getPurchasedBondCount(userId);
	}

	@Override
	public void updateDeadlinedProjects() {
		ProjectExample example = new ProjectExample();
		Criteria c = example.createCriteria();
		c.andDeadlineLessThanOrEqualTo(new Date());
		c.andStatusEqualTo(2); // 项目状态是投资中并且截至时间小于等于当前时间的
		c.andParentIdIsNull();
		c.andProjectTypeEqualTo(0);
		List<Project> list = projectMapper.selectByExample(example);
		if (null != list && !list.isEmpty()) {
			for (Project p : list) {
				p.setStatus(3); // 投资完成
				projectMapper.updateByPrimaryKeySelective(p);
			}
		}
	}

	@Override
	public void updateInvestCompletedProjects() {
		ProjectExample example = new ProjectExample();
		Criteria c = example.createCriteria();
		c.andStatusEqualTo(3); // 项目状态是投资完成的
		c.andParentIdIsNull();
		c.andProjectTypeEqualTo(0);
		List<Project> list = projectMapper.selectByExample(example);
		if (null != list && !list.isEmpty()) {
			for (Project p : list) {
				p.setStatus(4); // 还款中
				projectMapper.updateByPrimaryKeySelective(p);
			}
		}
	}

	@Override
	public void updateOverProjects() {
		Calendar calendar = Calendar.getInstance();
		ProjectExample example = new ProjectExample();
		Criteria c = example.createCriteria();
		c.andStatusEqualTo(4); // 项目状态是还款中
		c.andParentIdIsNull();
		c.andProjectTypeEqualTo(0);
		List<Project> list = projectMapper.selectByExample(example);
		if (null != list && !list.isEmpty()) {
			for (Project p : list) {
				calendar.setTime(p.getDeadline());
				calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + p.getLimitDays());
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 0);
				long cTime = calendar.getTimeInMillis();
				long currentTime = new Date().getTime();
				logger.info("项目ID=" + p.getId() + "当前还款截至时间=" + cTime + ",当前时间=" + currentTime);
				if (cTime <= currentTime) {
					// 如果截至时间+限制还款天数小于等于当前时间的毫秒
					logger.info("项目ID=" + p.getId() + "执行修改状态从4->5");
					p.setStatus(5); // 还款成功
					projectMapper.updateByPrimaryKeySelective(p);
				}
			}
		}
	}

	@Override
	public void updateDeadlinedBonds() throws Exception {
		ProjectExample example = new ProjectExample();
		Criteria c = example.createCriteria();
		c.andDeadlineLessThanOrEqualTo(new Date());
		c.andStatusEqualTo(0); // 项目状态是转让中并且截至时间小于当前时间的
		c.andParentIdIsNotNull();
		c.andProjectTypeEqualTo(1);
		List<Project> list = projectMapper.selectByExample(example);
		if (null != list && !list.isEmpty()) {
			for (Project p : list) {
				Investment investment = investmentMapper.selectByPrimaryKey(p.getInvestmentId());
				logger.info(
						"zhaizhuan cannel investmentId:" + investment.getId() + ";version:" + investment.getVersion());
				p.setStatus(2); // 取消状态
				this.updateByPrimaryKeySelectiveForVersion(p);

				// remain_amount还原
//		logger.info("remainmaount--" + investment.getRemainAmount() + "---project total amount"
//			+ p.getTotalAmount() + "---invested amount---" + p.getInvestedAmount());
//		investment.setRemainAmount(BigDecimalUtil.sub(
//			BigDecimalUtil.add(investment.getRemainAmount(), p.getTotalAmount()), p.getInvestedAmount()));

//		if (investment.getRemainAmount() > investment.getAmount()) {
//		    investment.setRemainAmount(investment.getAmount());
//		}
//		if (investment.getRemainAmount() < 0) {
//		    investment.setRemainAmount(0d);
//		}
				int lock = investmentMapper.updateByPrimaryKeySelectiveAndVersion(investment);
				logger.info("updateByPrimaryKeySelectiveAndVersion:" + lock);
				if (lock == 0) {
					throw new LockFailureException();
				}
			}
		}
	}

	@Override
	public List<Integer> getAllInvestors(Integer projectId) {
		return projectMapper.getAllInvestors(projectId);
	}

	@Override
	public List<Project> queryIndexByApp(int start, int limit) {
		ProjectExample example = new ProjectExample();
		Criteria c = example.createCriteria();
		c.andStatusGreaterThanOrEqualTo(1);
		c.andProjectTypeEqualTo(2);
		example.setLimitStart(start);
		example.setLimitEnd(limit);
		example.setOrderByClause("create_time desc");
		return projectMapper.selectByExample(example);
	}

	@Override
	public int saveByApp() {
		Project project = new Project();
		Project oldproject = getNewProjectByApp();
		if (oldproject == null) {
			project.setTitle("全民理财第1期");
		} else {
			project.setTitle("全民理财第" + (oldproject.getLimitDays() + 1) + "期");
			project.setAnnualized(ConstantsAdmin.RATE_DEFAULT_VALUE_F);
			project.setProjectType(2);
			project.setStatus(2); // 创建状态
//	    project.setCreateTime(new Date());
			project.setTotalAmount(1000000d);
			project.setLimitDays(oldproject.getLimitDays() + 1); // limitdays暂时用来表示期数
		}
		return projectMapper.insertSelective(project);
	}

	@Override
	public Project getNewProjectByApp() {
		return projectMapper.getNewProjectByApp();
	}

	@Override
	public void updateStartInvestmentProjects() {
		ProjectExample example = new ProjectExample();
		Criteria c = example.createCriteria();
		c.andStartTimeLessThan(new Date());
		c.andStatusEqualTo(1); // 项目状态是投资中并且截至时间小于等于当前时间的
		c.andProjectTypeEqualTo(2);
		List<Project> list = projectMapper.selectByExample(example);
		if (null != list && !list.isEmpty()) {
			for (Project p : list) {
				p.setStatus(2); // 投资中
				projectMapper.updateByPrimaryKeySelective(p);
			}
		}
	}

	@Override
	public Integer getAllProjectCountByApp() {
		return projectMapper.getAllProjectCountByApp();
	}

	@Override
	public void saveByApp(Project project) {
		project.setTotalAmount(project.getTotalAmount() * 10000);
		project.setStatus(2);
//	project.setCreateTime(new Date());
		projectMapper.insertSelective(project);
	}

	@Override
	public void saveByApp(Project project, String picture2) {
		project.setTotalAmount(project.getTotalAmount() * 10000);
//	project.setCreateTime(new Date());
		if (project.getId() == null) {
			projectMapper.insertSelective(project);
		} else {
			projectMapper.updateByPrimaryKeySelective(project);
		}
		updatePicture(picture2, project.getId());
	}

	@Override
	public List<Map<String, Object>> queryCreditor(String keyword, String status, Integer start, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("status", status);
		map.put("start", start);
		map.put("limit", limit);
		return projectMapper.queryCreditor(map);
	}

	@Override
	public Integer queryCreditorCount(String keyword, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("status", status);
		return projectMapper.queryCreditorCount(map);
	}

	public Project selectNoobProject() {// 查询新手标 按照id排序 获取最近有效的一个。
		return projectMapper.selectNoobProject();
	}

	@Override
	public Project selectRegularProject(int level) {// 查询定期标的，，年化收益最高的
		return projectMapper.selectRegularProject(level);
	}

	@Override
	public Integer selectInvestment(Integer project_id) {
		return projectMapper.selectInvestment(project_id);
	}

	@Override
	public Integer queryAllCreditorListCount() {
		Map<String, Object> map = new HashMap<String, Object>();
		return projectCreditorMapper.queryAllCreditorListCount(map);
	}

	@Override
	public Integer queryNewAllCreditorListCount(Map<String, Object> searchMap) {
		return projectCreditorMapper.queryNewAllCreditorListCount(searchMap);
	}

	@Override
	public List<Map<String, Object>> queryAllCreditorList(Integer userId, Integer flag, Integer start, Integer limit,
			Integer projectId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (userId != null) {
			map.put("userId", userId);
		}
		// map.put("flag", flag);
		map.put("start", start);
		map.put("limit", limit);
		map.put("projectId", projectId);
		return projectCreditorMapper.queryNewAllCreditorList(map);
	}

	@Override
	public Map<String, Object> queryCreditorDetail(Integer userId, Integer projectId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("projectId", projectId);
		return projectCreditorMapper.queryCreditorDetail(map);
	}

	@Override
	public List<Project> selectRegularList() {
		return projectMapper.selectRegularList();
	}

	@Override
	public Integer selectRegularCount() {
		return projectMapper.selectRegularCount();
	}

	@Override
	public Map<String, Object> queryProjectCreditorDetail(Integer userId, Integer projectId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("investmentId", projectId);
		return projectCreditorMapper.queryProjectCreditorDetail(map);
	}

	@Override
	public Integer saveAddPrompt(String title, String[] context) {
		Integer id = null;
		for (int i = 0; i < context.length; i++) {
			Prompt record = new Prompt();
			record.setContext(context[i]);
			record.setTitle(title);
			record.setParentId(i + 1);
			id = promptMapper.insertSelective(record);
		}
		return id;
	}

	@Override
	public List<Map<String, Object>> selectPromptList(String keyword, Integer start, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("keyword", keyword);
		return promptMapper.selectPromptList(map);
	}

	@Override
	public Integer selectPromptCount(String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		return promptMapper.selectPromptCount(map);
	}

	@Override
	public Prompt selectKeyPrompt(Integer id) {

		return promptMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updatePrompt(Prompt prompt) {
		promptMapper.updateByPrimaryKey(prompt);
	}

	@Override
	public void deletePrompt(Integer id) {
		promptMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<String> selectTitlePrompt(String title) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		return promptMapper.selectTitlePrompt(map);
	}

	@Override
	public List<Project> selectRegularStatusList() {
		return promptMapper.selectRegularStatusList();
	}

	@Override
	public List<Map<String, Object>> queryContractList(Integer type, String keyword, Integer start, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("keyword", keyword);
		map.put("start", start);
		map.put("limit", limit);
		return projectContractMapper.queryContractList(map);
	}

	@Override
	public int queryContractListCount(Integer type, String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("keyword", keyword);
		return projectContractMapper.queryContractListCount(map);
	}

	@Override
	public ProjectContract queryContractDetail(Integer id) {
		return projectContractMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ProjectContract> getContractTitleList() {
		return projectContractMapper.queryAllContract();
	}

	@Override
	public void saveContractInfo(ProjectContractWithBLOBs group) {
		if (group.getId() == null) {
			projectContractMapper.insertSelective(group);
		} else {
			projectContractMapper.updateByPrimaryKeySelective(group);
		}
	}

	@Override
	public List<Map<String, Object>> queryCreditorAssignmentAssets(Integer userId, Integer start, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("start", start);
		map.put("limit", limit);
		return projectMapper.queryCreditorAssignmentAssets(map);
	}

	@Override
	public Integer queryCreditorAssignmentAssetsCount(Integer userId) {
		return projectMapper.queryCreditorAssignmentAssetsCount(userId);
	}

	@Override
	public Map<String, Object> queryCreditorAssignmentAssetsDetail(Integer investID, Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("investID", investID);
		map.put("userId", userId);
		Map<String, Object> listMap = projectMapper.queryCreditorAssignmentAssetsDetail(map);
		map.put("amount", listMap.get("amount"));
		map.put("totalAmount", listMap.get("total_amount"));
		map.put("detailId", listMap.get("detailId"));
		if (listMap.get("parent_id") == null) {
			map.put("time", DateFormatTools.dateToStr2((Date) listMap.get("time")));
			map.put("title", listMap.get("projectTitle"));
			String name = projectMapper.queryEnterprise(Integer.valueOf(listMap.get("id").toString()));
			map.put("name", name);
			map.put("projectId", listMap.get("id"));
		} else {
			Map<String, Object> name = projectMapper
					.queryEnterprise2(Integer.valueOf(listMap.get("parent_id").toString()));
			map.put("title", name.get("title"));
			map.put("time", DateFormatTools.dateToStr2((Date) name.get("time")));
			map.put("name", name.get("name"));
			map.put("projectId", listMap.get("parent_id"));
		}
		return map;
	}

	public ProjectContractWithBLOBs queryCreditorContentDetail(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return projectContractMapper.queryCreditorContentDetail(map);
	}

	@Override
	public Map<String, Object> queryCreditorContractData(Integer userId, Integer investmentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("investmentId", investmentId);
		return projectMapper.queryCreditorContractData(map);
	}

	@Override
	public List<Project> getProjectTitleOption(Integer id) {
		return projectMapper.getProjectTitleOption(id);
	}

	@Override
	public List<Project> getProjectAccount(Integer id) {
		ProjectExample example = new ProjectExample();
		if (id == null) {
			List<Integer> list = new ArrayList<>();
			list.add(0);
			list.add(3);
			list.add(5);
			example.createCriteria().andProjectTypeIn(list).andParentIdIsNull();
			example.setOrderByClause("create_time desc");
			example.setLimitStart(0);
			example.setLimitEnd(20);
		} else {
			example.createCriteria().andIdEqualTo(id);
		}
		return projectMapper.selectByExample(example);
	}

	@Override
	public void saveCreditorDetail(ProjectCreditor record) {
		if (record.getId() == null) {
			projectCreditorMapper.insertSelective(record);
		} else {
			projectCreditorMapper.updateByPrimaryKeySelective(record);
		}
	}

	@Override
	public Boolean checkProjectHasCreditor(Integer projectId) {
		if (projectId != null) {
			return projectCreditorMapper.checkProjectHasCreditor(projectId);
		}
		return true;
	}

	@Override
	public Map<String, Object> queryProjectCreditorConfig(Integer id) {
		return projectCreditorMapper.queryProjectCreditorConfig(id);
	}

	@Override
	public Map<String, Object> queryProjectConfig(Integer id) {
		return projectCreditorMapper.queryProjectConfig(id);
	}

	@Override
	public void deleteCreditorConfig(Integer id) {
		projectCreditorMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteContract(Integer id) {
		projectContractMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Boolean checkContractDelete(Integer id) {
		return projectCreditorMapper.checkContractDelete(id);
	}

	@Override
	public ProjectContractWithBLOBs queryByTitle(String title) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		return projectContractMapper.queryByTitle(map);
	}

	@Override
	public ProjectContractWithBLOBs queryProjectContractWithBLOBs(Integer id) {
		return projectContractMapper.selectByPrimaryKey(id);
	}

	@Override
	public Integer selectUserInvestment(Integer userId, Integer projectId) {

		return projectMapper.selectUserInvestment(userId, projectId);
	}

	@Override
	public ProjectCreditor queryCreditorDeailByProjectId(Integer projectId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("projectId", projectId);
		return projectCreditorMapper.queryCreditorDeailByProjectId(map);
	}

	@Override
	public List<Prompt> queryByTitlePrompt(String title) {
		return promptMapper.queryByTitle(title);
	}

	@Override
	public List<AppVersionContent> queryAppVersionContentList(String keyword, Integer start, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("start", start);
		map.put("limit", limit);
		return appVersionContentMapper.queryAppVersionContentList(map);
	}

	@Override
	public Integer queryAppVersionContentCount(String keyword) {
		return appVersionContentMapper.queryAppVersionContentCount(keyword);
	}

	@Override
	public void saveAppVersionContent(AppVersionContentWithBLOBs group) {
		System.out.println(group);
		if (group.getId() == null) {
			appVersionContentMapper.insertSelective(group);
		} else {
			appVersionContentMapper.updateByPrimaryKeySelective(group);
		}
	}

	@Override
	public AppVersionContent selectAppVersionContentKey(Integer id) {
		return appVersionContentMapper.selectByPrimaryKey(id);
	}

	@Override
	public void deleteAppVersionContentKey(Integer id) {
		appVersionContentMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Project> selectRegularProjectList(Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", page);
		map.put("limit", limit);
		return projectMapper.selectRegularProjectList(map);
	}

	@Override
	public Integer selectRegularProjectCount() {
		return projectMapper.selectRegularProjectCount();
	}

	@Override
	public List<Project> selectRegularProjectTunnelList() {
		return projectMapper.selectRegularProjectTunnelList();
	}

	@Override
	public List<Project> selectRegularProjectListold(Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", page);
		map.put("limit", limit);
		return projectMapper.selectRegularProjectListold(map);
	}

	@Override
	public Integer selectRegularProjectCountold() {
		return projectMapper.selectRegularProjectCountold();
	}

	@Override
	public List<Map<String, Object>> selectCreditorList(String keyword, Integer status, Integer start, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("status", status);
		map.put("start", start);
		map.put("limit", limit);
		return projectMapper.selectCreditorList(map);
	}

	@Override
	public Integer selectCreditorCount(String keyword, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("status", status);
		return projectMapper.selectCreditorCount(map);
	}

	@Override
	public List<Project> getRegularProject(Map<String, Object> params) {
		return projectMapper.getRegularProject(params);
	}

	@Override
	public List<Map<String, Object>> selectReportList(String keyword, Date startTime, Date endTime) {

		return null;
	}

	@Override
	public List<Map<String, Object>> queryCurrentCreditorList(Integer userId, Integer start, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("start", start);
		map.put("limit", limit);
		return projectMapper.queryCurrentCreditorList(map);
	}

	@Override
	public List<Map<String, Object>> queryCurrentCreditorList(Integer userId, Integer source, Integer start,
			Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("source", source);
		map.put("start", start);
		map.put("limit", limit);
		return projectMapper.queryNewCurrentCreditorList(map);
	}

	@Override
	public Integer queryCurrentCreditorCount(Integer userId) {
		return projectMapper.queryCurrentCreditorCount(userId);
	}

	@Override
	public Integer queryCurrentCreditorCount(Integer userId, Integer source) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("source", source);
		return projectMapper.queryNewCurrentCreditorCount(map);
	}

	@Override
	public Project selectHuoBase(Integer id) {
		Project project = projectMapper.selectByPrimaryKey(id);
//	List<ProjectPicture> list = project.getPictures();
//	if (list != null & !list.isEmpty()) {
//	    List<ProjectPicture> l1 = new ArrayList<>();
//	    List<ProjectPicture> l2 = new ArrayList<>();
//	    if (null != list && !list.isEmpty()) {
//		for (ProjectPicture p : list) {
//		    try {
//			p.getUpload().setPath(ClientConstants.ALIBABA_PATH + "upload/" + p.getUpload().getPath());
//			if (p.getType() == 1) {
//			    l1.add(p);
//			} else if (p.getType() == 2) {
//			    l2.add(p);
//			}
//		    } catch (Exception e) {
//			e.printStackTrace();
//		    }
//		}
//	    }
//	    project.setPictures(null);
//	    project.setProjectPictures(l1);
//	    project.setBorrowPictures(l2);
//	}

		return project;
	}

	@Override
	public synchronized void saveCurrentProject(Project project) throws Exception {
		ProjectExample example = new ProjectExample();
		example.createCriteria().andTitleEqualTo(project.getTitle());
		List<Project> tList = projectMapper.selectByExample(example);
		if (tList.size() > 0) {
			return;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("start", 0);
		map.put("limit", 1);
		List<Map<String, Object>> list = userMapper.selectSuperUserList(map);
		if (list.size() > 0) {
			Integer userId = Integer.valueOf(list.get(0).get("id").toString());
			Double creditorAmount = Double.valueOf(list.get(0).get("creditorSurplusAmount").toString());
			String trueName = list.get(0).get("trueName").toString();
			// 查询用户 可以用债权 从大到小
			List<Project> projectList = projectMapper.selectSuperProjectList(userId);
			Double totalAmount = project.getTotalAmount().doubleValue();
//	    project.setCreateTime(new Date());
			if (creditorAmount < totalAmount * 2 && creditorAmount >= totalAmount) {
				// YunSendMessage.sendSms("【鑫聚财】活期开标预警,超级投资人债转不足以开标,请尽快新建超级标.", "15000230763");
				// YunSendMessage.sendSms("【鑫聚财】活期开标预警,超级投资人债转不足以开标,请尽快新建超级标.",
				// "15021338050,15000230763,18918378783,17749760213,18501740523,15618934406");
			} else if (creditorAmount < totalAmount) {
				// YunSendMessage.sendSms("【鑫聚财】活期开标失败,超级投资人债转不足以开标,请尽快新建超级标", "15000230763");
				// YunSendMessage.sendSms("【鑫聚财】活期开标失败,超级投资人债转不足以开标,请尽快新建超级标.",
				// "15021338050,15000230763,18918378783,17749760213,18501740523,15618934406");
				return;
			}
			projectMapper.insertSelective(project);
			for (Project pobj : projectList) {
				// 债权剩余份额
				Double left = BigDecimalUtil.sub(totalAmount,
						BigDecimalUtil.sub(pobj.getTotalAmount(), pobj.getInvestedAmount()));
				if (left > 0) {// 项目可以用债权不够
					// 插入 项目关联项目标记录
					ProjectLinkProject link = new ProjectLinkProject();
					link.setSubcalssId(project.getId());
					link.setParentId(pobj.getId());
					link.setParentAmount(BigDecimalUtil.sub(totalAmount, left));
					projectLinkProjectMapper.insertSelective(link);
					totalAmount = left;
				} else {// 项目可以债权足够
					// 插入 项目关联项目标记录 跳出循环
					ProjectLinkProject link = new ProjectLinkProject();
					link.setSubcalssId(project.getId());
					link.setParentId(pobj.getId());
					link.setParentAmount(totalAmount);
					projectLinkProjectMapper.insertSelective(link);
					break;
				}
			}
		}

	}
	
	// 下架牛只
	@Override
	public void doLowerShelves(Integer id) throws Exception {
		Project project = projectMapper.selectByPrimaryKeyForUpdate(id);
		if (project == null) {
			throw new Exception("项目不存在");
		}
		if (project.getStatus() != ProjectStatusEnum.ENABLE_SALE.getCode()) {
			throw new Exception("只能下架状态为【上架】状态的项目");
		}
		if (project.getProjectType() == ProjectTypeEnum.PINNIU.getFeatureType() && project.getInvestedAmount() != 0) {
			throw new Exception("该拼牛项目已有用户参与，不允许下架");
		}
		project.setStatus(ProjectStatusEnum.BUILDED.getCode());
		projectMapper.updateByPrimaryKeySelective(project);
	}

	public Integer listMyCurrentCount(Integer userId, Integer status, Integer orderBy, Date startDate, Date endDate) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("status", status);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("orderBy", orderBy);
		return projectMapper.listMyCurrentCount(map);
	}

	@Override
	public Double getTransferAmountByInvestmentId(Integer investmentId) {
		return projectMapper.getTransferAmountByInvestmentId(investmentId);
	}

	@Override
	public List<Map<String, Object>> listMyCurrent(Integer userId, Integer status, Integer orderBy, Date startDate,
			Date endDate, Integer start, Integer limit) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("status", status);
		map.put("orderBy", orderBy);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("start", start);
		map.put("limit", limit);
		return projectMapper.listMyCurrent(map);
	}

	public Project getProjectById(Integer id) {
		return projectMapper.getProjectById(id);
	}

	@Override
	public Integer selectTodayHuoProject() {

		return projectMapper.selectTodayHuoProject();
	}

	public List<Map<String, Object>> projectCost(Integer start, Integer limit, String keyword, Integer status,
			Date startTime, Date endTime, Integer type, Integer limitDays) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", StringUtils.trim(keyword));
		map.put("start", start);
		map.put("limit", limit);
		map.put("type", type);
		map.put("status", status);
		map.put("limitDays", limitDays);
		map.put("startTime", startTime);
		if (endTime != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(endTime);
			c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
			map.put("endTime", c1.getTime());
		} else {
			map.put("endTime", null);
		}

		return projectMapper.projectCost(map);
	}

	public List<Map<String, Object>> projectCost1(Integer start, Integer limit, String keyword, Integer status,
			Date startTime, Date endTime, Integer type, Integer limitDays) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", StringUtils.trim(keyword));
		map.put("start", start);
		map.put("limit", limit);
		map.put("type", type);
		map.put("status", status);
		map.put("limitDays", limitDays);
		map.put("startTime", startTime);
		if (endTime != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(endTime);
			c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
			map.put("endTime", c1.getTime());
		} else {
			map.put("endTime", null);
		}

		return projectMapper.projectCost1(map);
	}

	@Override
	public List<Project> selectRegularReport(Integer status, String keyword, Integer noob) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", status);
		map.put("keyword", keyword);
		map.put("noob", noob);
		return projectMapper.selectRegularReport(map);
	}

	@Override
	public List<Project> selectPackageList(Integer productId, Integer start, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		if (start != null) {
			map.put("start", start);
		}
		if (limit != null) {
			map.put("limit", limit);
		}
		List<Project> list = null;
		if (productId == null) {
			Product product = productMapper.selectMinOutDaysProduct();
			map.put("productId", product.getId());
			list = projectMapper.selectPackageList(map);
		} else {
			list = projectMapper.selectPackageList(map);
		}
		return list;
	}

	@Override
	public List<Project> getProjectListByProduct(Integer productId) {
		return projectMapper.getProjectListByProduct(productId);
	}

	@Override
	public Map<String, Object> selectProjectLimit() {
		return projectMapper.selectProjectLimit();
	}

	@Override
	public Integer selectByStatusAndProductIdCount(Integer projectType, Integer status, Integer productId) {
		ProjectExample example = new ProjectExample();
		example.createCriteria().andProductIdEqualTo(productId).andProjectTypeEqualTo(projectType)
				.andStatusGreaterThanOrEqualTo(status);
		return projectMapper.countByExample(example);
	}

	/**
	 * 根据投资金额大小排序取前三条
	 *
	 * @param projectId 标id
	 * @return List
	 */
	@Override
	public List<Map<String, Object>> getThreeInvestors(Integer projectId) {
		Map<String, Object> params = new HashMap<>();
		params.put("projectId", projectId);
		return projectMapper.getThreeInvestors(params);
	}

	/**
	 * 根据投资金额大小排序取前三条（月月盈）
	 *
	 * @param projectId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getMonthlyGainThreeInvestors(Integer projectId) {
		Map<String, Object> params = new HashMap<>();
		params.put("projectId", projectId);
		return projectMapper.getMonthlyGainThreeInvestors(params);
	}

	@Override
	public List<Project> selectByPackageList(Integer packageId) {
		ProjectExample example = new ProjectExample();
		if (packageId != null) {
//	    example.createCriteria().andPackageIdEqualTo(packageId);
		}
		return projectMapper.selectByExample(example);
	}

	@Override
	public List<Project> queryCycleList(String keyword, String title, Integer status, Date startTime, Date endTime,
			Date startDate, Date endDate, Integer start, Integer limit, String title1) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", StringUtils.isNotBlank(keyword) ? keyword : null);
		map.put("title", StringUtils.isNotBlank(title) ? title : null);
		map.put("title1", title1);
		map.put("status", status);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("start", start);
		map.put("limit", limit);
		return projectMapper.queryCycleList(map);
	}

	@Override
	public int queryCycleCount(String keyword, String title, Integer status, Date startTime, Date endTime,
			Date startDate, Date endDate, String title1) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", StringUtils.isNotBlank(keyword) ? keyword : null);
		map.put("title", StringUtils.isNotBlank(title) ? title : null);
		map.put("status", status);
		map.put("title1", title1);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return projectMapper.queryCycleCount(map);
	}

	public Integer countProjectCost(String keyword, Integer status, Date startTime, Date endTime, Integer type,
			Integer limitDays) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", StringUtils.trim(keyword));
		map.put("type", type);
		map.put("status", status);
		map.put("limitDays", limitDays);
		map.put("startTime", startTime);
		if (endTime != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(endTime);
			c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
			map.put("endTime", c1.getTime());
		} else {
			map.put("endTime", null);
		}

		return projectMapper.countProjectCost(map);
	}

	@Override
	public Project getProjectByProductId(Integer productId) {
		ProjectExample example = new ProjectExample();
		Criteria criteria = example.createCriteria();
		criteria.andProductIdEqualTo(productId);
		List<Project> list = projectMapper.selectByExample(example);
		return null == list ? null : list.get(0);
	}

	@Override
	public List<Project> queryCanAutoReleaseProjects(Map<String, Object> map) {

		return projectMapper.queryCanAutoReleaseProjects(map);
	}

	@Override
	public List<Integer> querySellOutProjectCountByLimitDays(Map<String, Object> map) {

		return projectMapper.querySellOutProjectCountByLimitDays(map);
	}

	public int updateProjectByProjectIds(Project project, Integer status) {

		ProjectExample projectExample = new ProjectExample();
		projectExample.createCriteria().andIdEqualTo(project.getId());
		project.setStatus(status);
		project.setSort(99);
		return projectMapper.updateByExampleSelective(project, projectExample);
	}

	@Override
	public int updateProjectSortBylimitDays(Map<String, Object> map) {

		return projectMapper.updateProjectSortBylimitDays(map);
	}

	public Map<String, Object> getBestCoupon(Integer userId, Integer projectId, double defaultAmount) {
		Map<String, Object> result = new HashMap<>();
		// Map<String, Object> bestRateCoupon =
		// rateCouponService.selectBestRateCoupon(userId, projectId, defaultAmount);
		Map<String, Object> bestHongbao = hongbaoService.selectBestHongbao(userId, defaultAmount, projectId);
		// result.putAll(bestRateCoupon);
		result.putAll(bestHongbao);
		return result;
	}

	public List<Project> selectHotProjectList(String param1, String param2, String param3) {
		Map<String, Object> map = new HashMap<>();
		map.put("param1", param1);
		map.put("param2", param2);
		map.put("param3", param3);
		return projectMapper.selectHotProjectList(map);
	}

	@Override
	public List<Map<String, Object>> selectRegularProjectLimitList() {
		return projectMapper.selectRegularProjectLimitList();
	}

	public List<Project> selectStatus2ProjectList(Integer page, Integer limit, List<Integer> list, String sort,
			Integer limitDays, Integer noob) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", page);
		map.put("limit", limit);
		map.put("list", list);
		map.put("sort", sort);
		map.put("limitDays", limitDays);
		map.put("noob", noob);
		return projectMapper.selectStatus2ProjectList(map);
	}

	@Override
	public Integer selectStatus2ProjectCount(List<Integer> list, Integer limitDays, Integer noob) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("limitDays", limitDays);
		map.put("noob", noob);
		return projectMapper.selectStatus2ProjectCount(map);
	}

	@Override
	public Double getProjectClassTotalAmount(Integer limitDays, Integer noob) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limitDays", limitDays);
		map.put("noob", noob);
		return projectMapper.getProjectClassTotalAmount(map);
	}

	@Override
	public Integer queryProjectListCount(String isCreatedProject, Date startDate, Date endDate, String orderno) {
		Map<String, Object> map = new HashMap<>();
		map.put("isCreatedProject", isCreatedProject);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("orderno", orderno);
		return projectMapper.queryProjectListCount(map);
	}

	/**
	 * <p>
	 * 计算时间
	 * </p>
	 *
	 * @param lendBeginTime
	 * @param lendMonth
	 * @param index
	 * @return
	 * @author: lxfeng
	 * @date: Created on 2018-3-14 下午5:01:23
	 */
	private Date getLendBeginTime(Date lendBeginTime, int lendMonth, int index) {
		Calendar c = Calendar.getInstance();
		c.setTime(lendBeginTime);
		c.add(Calendar.MONTH, lendMonth * index);
		return c.getTime();

	}

//	public static void main(String[] args) {
//		Calendar c = Calendar.getInstance();
//		c.set(Calendar.MONTH, 0);
//		c.set(Calendar.DATE, 31);
//		System.out.println(DateUtil.dateFullTimeFormat.format(c.getTime()));
//		Date lendBeginTime = new ProjectServiceImpl().getLendBeginTime(c.getTime(), 1, 1);
//		String format = DateUtil.dateFullTimeFormat.format(lendBeginTime);
//		System.out.println(format);
//	}

	@Override
	public void updateProject(Project project) {
		projectMapper.updateByPrimaryKeySelective(project);
	}

	@Override
	public Integer selectRegularlyProjectCount(Map<String, Object> searchMap) {
		return projectMapper.selectRegularlyProjectCount(searchMap);
	}

	@Override
	public Integer selectAssignmentOfDebtCount(Map<String, Object> searchMap) {
		return projectMapper.selectAssignmentOfDebtNewCount(searchMap);
	}

	@Override
	public List<Project> selectRegularlyProjectListPage(Map<String, Object> searchMap) {
		return projectMapper.selectRegularlyProjectListPage(searchMap);
	}

	@Override
	public List<Map<String, Object>> selectAssignmentOfDebtListPage(Map<String, Object> searchMap) {
		return projectMapper.selectAssignmentOfDebtListPage(searchMap);
	}

	/**
	 * @param income  预期收益
	 * @param amount  购买价格
	 * @param dayDiff 剩余期限
	 * @return
	 */
	public Double getProjectAnnualizedMix(Object income, Object amount, Long dayDiff) {
		if (dayDiff == 0 || (0 == (int) (double) amount)) {
			return 0d;
		}
		Double annualizedMix = BigDecimalUtil.multi(income, 365);
		annualizedMix = BigDecimalUtil.div(annualizedMix, (Double) amount, 8);
		annualizedMix = BigDecimalUtil.div(annualizedMix, dayDiff, 8);
		annualizedMix = BigDecimalUtil.multi(annualizedMix, 100);
		annualizedMix = BigDecimalUtil.fixedDecimal(annualizedMix, 1); // 保留一位小数不做4舍5入
		return annualizedMix;
	}

	public List<Map<String, Object>> queryCreditorList(Map<String, Object> searchMap, String tagSplit) {
		List<Map<String, Object>> list;
		list = projectMapper.selectAssignmentOfDebtNewListPage(searchMap);
		if (list.size() > 0) {
			for (Map<String, Object> map : list) {
				// 标签
				if (StringUtils.isNotBlank(map.get("tag") == null ? null : map.get("tag").toString())) {
					map.put("taglist", map.get("tag").toString().split(tagSplit));
				}

				// 项目总额格式化
				String totalAmountStr = String.valueOf(BigDecimalUtil.fixedDecimal(
						BigDecimalUtil.div(Double.valueOf(map.get("total_amount").toString()), 10000, 4), 2));
				String amountArry[] = totalAmountStr.split("\\.");
				map.put("integralAmount", amountArry[0]);
				map.put("decimalAmount", amountArry[1]);

				// 项目可投金额格式化
				Object voteAmountObj = map.get("voteAmount");
				String voteAmountStr = String.valueOf(BigDecimalUtil
						.fixedDecimal(BigDecimalUtil.div(Double.valueOf(voteAmountObj.toString()), 10000, 4), 2));
				String voteAmountArry[] = voteAmountStr.split("\\.");
				map.put("vateIntegralAmount", voteAmountArry[0]);
				map.put("vateDecimalAmount", voteAmountArry[1]);

				// 剩余百分比
				Double completed = BigDecimalUtil
						.multi(BigDecimalUtil.div(Double.valueOf(map.get("invested_amount").toString()),
								Double.valueOf(map.get("total_amount").toString()), 4), 100);
				map.put("completed", completed);

				// 预期收益=预计收到的利息+营销折扣
				// 转让活动让利费率cache配置获取
				Double voteAmount = voteAmountObj == null ? 0d : (Double) voteAmountObj;
				if (voteAmount <= 0) {
					voteAmount = (Double) map.get("total_amount");
				}
				Object bondManagementRateObj = map.get("bondManagementRate");
				Double bondManagementRate = bondManagementRateObj == null ? 0 : (Double) bondManagementRateObj;
				Object expectedProfitObj = map.get("expectedProfit");
				Double expectedProfit = expectedProfitObj == null ? 0d : (Double) expectedProfitObj;
				Double trueExpectedProfit = BigDecimalUtil.add(expectedProfit,
						BigDecimalUtil.multi(voteAmount, bondManagementRate));

				// 年化率: 预期年化率=预期收益/购买价格/剩余期限*365
				Integer dayDiff = Integer.parseInt(map.get("dayDiff").toString());
				Double trueExceptRate = 0d;
				map.put("trueExceptRate", trueExceptRate);
				if (dayDiff != 0) {
					trueExceptRate = BigDecimalUtil.div(trueExpectedProfit, voteAmount, 8);
					trueExceptRate = BigDecimalUtil.div(trueExceptRate, dayDiff, 8);
					trueExceptRate = BigDecimalUtil.multi(trueExceptRate, 365);
					trueExceptRate = BigDecimalUtil.cfixed2UpDecimal(BigDecimalUtil.multi(trueExceptRate, 100), 1);
					map.put("trueExceptRate", trueExceptRate);
				}

				map.put("trueExceptRateRight", BigDecimalUtil.sub(trueExceptRate, map.get("trueAnnualized")));

				map.put("increaseAmount", (int) Constants.FEE_PIECE_AMOUNT + "");// 递增金额（每份的金额）
				map.put("leftPiece", (int) BigDecimalUtil.div(voteAmount, Constants.FEE_PIECE_AMOUNT, 0) + "");// 剩余份数

				// 额外加息利息计算
				Double addRate = Double.parseDouble(map.get("addRate").toString());
				Double addExceptInterest = BigDecimalUtil.multi(voteAmount, BigDecimalUtil.div(addRate, 100, 8));
				addExceptInterest = BigDecimalUtil.multi(addExceptInterest, dayDiff);
				addExceptInterest = BigDecimalUtil.div(addExceptInterest, 365, 8);
				map.put("trueExpectedProfit",
						BigDecimalUtil.fixedDecimal(BigDecimalUtil.add(trueExpectedProfit, addExceptInterest), 2));
			}
		}
		return list;
	}

	@Override
	public Map<String, Integer> selectProjectCount() {
		Map<String, Integer> resultMap = new HashMap<>();
		// 新手产品
		Map<String, Object> searchNewHand = new HashMap<>();
		searchNewHand.put("status", 1);// 投资中,sql中进行了特定处理
		searchNewHand.put("onlyNewHand", 1);// 新手产品
		resultMap.put("newHandCount", projectMapper.selectRegularlyProjectCount(searchNewHand));
		// 定期产品
		Map<String, Object> searchRegularly = new HashMap<>();
		searchRegularly.put("noNewHand", 0);// 定期标
		searchRegularly.put("status", 1);// 投资中,sql中进行了特定处理
		resultMap.put("regularlyCount", projectMapper.selectRegularlyProjectCount(searchRegularly));
		// 债权转让
		Map<String, Object> searchDebt = new HashMap<>();
		searchDebt.put("status", 0);// 转让中,sql中进行了特定处理
		resultMap.put("debtCount", projectMapper.selectAssignmentOfDebtCount(searchDebt));
		return resultMap;
	}

	@Override
	public Project queryByLimitDays(int limitDays) {
		return projectMapper.queryByLimitDays(limitDays);
	}

	@Override
	public List<Project> queryByContractId(String contractId) {
		return projectMapper.queryByContractId(contractId);
	}

	@Override
	public List<P2pEyeLoansData> queryP2pEyeLoansData(Map<String, Object> params) {
		return projectMapper.queryP2pEyeLoansData(params);
	}

	@Override
	public List<P2pEyeInvestData> queryP2pEyeInvestData(Map<String, Object> params) {
		return projectMapper.queryP2pEyeInvestData(params);
	}

	@Override
	public List<BondPayVO> queryBondPayList(int projectId) {
		return projectMapper.queryBondPayList(projectId);
	}

	@Override
	public Map<String, Object> getProjectPicture(Integer id) {
		return projectMapper.getProjectPicture(id);
	}

	@Override
	public List<OrderChangeVO> queryOrderChangeList(int projectId) {
		return projectMapper.queryOrderChangeList(projectId);
	}

	@Override
	public List<Project> queryMonthlyGainProjectList(Map<String, Object> params) {
		return projectMapper.queryMonthlyGainProjectList(params);
	}

	@Override
	public int updateProjectOfMonthlyGain(List<Integer> projectIdList, Integer packageId, Integer productId) {

		ProjectExample projectExample = new ProjectExample();
		projectExample.createCriteria().andIdIn(projectIdList);
		Project record = new Project();
		record.setProductId(productId);
//	record.setPackageId(packageId);
		return projectMapper.updateByExampleSelective(record, projectExample);
	}

	@Override
	public boolean updateWithReturn(Project project) throws Exception {
		int count = projectMapper.updateByPrimaryKeySelective(project);
		return count > 0 ? true : false;
	}

	@Override
	public boolean monthlyGainBondChangeToNormalBond(Project bondProject) {

//		bondProject.setProjectType(ProjectTypeEnum.ZHAIZHUAN.getFeatureType());
//	bondProject.setBondManagementRate(0d);
		// TODO app6.4 user_bond_management_rate(转让人转让管理费)
		try {
			return this.updateWithReturn(bondProject);

		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	@Override
	public List<Project> getProjectByPackageId(Integer packageId) {
		Map<String, Object> params = new HashMap<>();
		params.put("packageId", packageId);
		List<Project> list = projectMapper.getProjectByPackageId(params);
		return list;
	}

	@Override
	public boolean batchUpdateProject(String[] projectId) {
		boolean updateFlag = false;
		for (String string : projectId) {
			if (StringUtils.isNotBlank(string)) {
				Project record = new Project();
				record.setId(Integer.parseInt(string));
//				record.setProjectType(ProjectTypeEnum.YUEYUEYING.getFeatureType());
				int flag = projectMapper.updateByPrimaryKeySelective(record);
				if (flag == 0) {
					throw new RuntimeException("批量生成债权失败");
				}
				updateFlag = true;
			}
		}
		return updateFlag;
	}

	@Override
	public List<Project> selectByExample(ProjectExample example) {
		return projectMapper.selectByExample(example);
	}

	@Override
	public synchronized boolean saveCannel(Project p) throws Exception {
		// 已处理过
		if (p.getStatus() == 2) {
			return true;
		}
		logger.info("债转取消定时任务处理的");
		boolean flag = false;
		Investment investment = investmentMapper.selectByPrimaryKey(p.getInvestmentId());
		logger.info("zhaizhuan cannel investmentId:" + investment.getId() + ";version:" + investment.getVersion());
		p.setStatus(2); // 取消状态
		this.updateByPrimaryKeySelectiveForVersion(p);
		// remain_amount还原
//	logger.info("remainmaount--" + investment.getRemainAmount() + "---project total amount"
//		+ p.getTotalAmount() + "---invested amount---" + p.getInvestedAmount());
//	investment.setRemainAmount(BigDecimalUtil
//		.sub(BigDecimalUtil.add(investment.getRemainAmount(), p.getTotalAmount()), p.getInvestedAmount()));
//
//	if (investment.getRemainAmount() > investment.getAmount()) {
//	    investment.setRemainAmount(investment.getAmount());
//	}
//	if (investment.getRemainAmount() < 0) {
//	    investment.setRemainAmount(0d);
//	}
		int lock = investmentMapper.updateByPrimaryKeySelectiveAndVersion(investment);
		logger.info("updateByPrimaryKeySelectiveAndVersion:" + lock);
		if (lock == 0) {
			throw new LockFailureException();
		}
		flag = true;
		return flag;
	}

	public List<Project> queryzzList(String keyword, String startAmount, String endAmount, Integer bondDayDiff,
			Integer status, Date startTime, Date endTime, Integer start, Integer limit) {
		List<Project> list = projectMapper.queryzzList(keyword, startAmount, endAmount, bondDayDiff, status, startTime,
				endTime, start, limit);
		return list;
	}

	public Map<String, Object> queryzzTotalAmount(String keyword, String startAmount, String endAmount,
			Integer bondDayDiff, Integer status, Date startTime, Date endTime) {
		return projectMapper.queryzzTotalAmount(keyword, startAmount, endAmount, bondDayDiff, status, startTime,
				endTime);
	}

	public Integer queryzzListCount(String keyword, String startAmount, String endAmount, Integer bondDayDiff,
			Integer status, Date startTime, Date endTime) {
		return projectMapper.queryzzListCount(keyword, startAmount, endAmount, bondDayDiff, status, startTime, endTime);
	}

	@Override
	public List<Map<String, Object>> queryZtDebtProjectList(Map<String, Object> searchMap) {
		return projectMapper.queryZtDebtProjectList(searchMap);
	}

	@Override
	public int queryZtDebtProjectCount(Map<String, Object> searchMap) {
		return projectMapper.queryZtDebtProjectCount(searchMap);
	}

	/**
	 * 根据到期的投资订单，判断用户是否可以再续养
	 * @param investment
	 * @return true 可以续养 false 不可以
	 */
	@Override
	public boolean enableBuyAgain(Investment investment) {

		Project project = getMapper().selectByPrimaryKey(investment.getProjectId());

		ProjectPropertyViewExample example = new ProjectPropertyViewExample();
		ProjectPropertyViewExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(project.getId());
		ProjectPropertyView projectPropertyView = projectPropertyViewMapper.selectByExample(example).get(0);

		List<KeepPeriodVO> keepPeriodVOList = caculateBuyAgainCase(project.getSex(),
				Integer.parseInt(projectPropertyView.getYueLing()) * 30,
				project.getLimitDays());
		if(keepPeriodVOList==null || keepPeriodVOList.size()==0) {
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 续养前牛只数据生成
	 * @param project
	 * @param projectDaysEnum
	 * @return 续养牛只的标的编号
	 */
	@Override
	public Integer doNewProjectWhenReFeed(Project project, ProjectDaysEnum projectDaysEnum) throws Exception{

		//查询原牛只的月龄
		int monthCountPropertyId= 0 ;

		ProductPropertyExample productPropertyExample = new ProductPropertyExample();
		ProductPropertyExample.Criteria criteria22 = productPropertyExample.createCriteria();
		criteria22.andProductIdEqualTo(project.getProductId());

		List<ProductProperty> productProperties = productPropertyService.getMapper().selectByExample(productPropertyExample);
		for (ProductProperty productProperty : productProperties){
			if(productProperty.getPropertyName().equals("月龄")){
				monthCountPropertyId= productProperty.getId();
				break;
			}
		}

		//查询之前标的属性
		ProjectPropertyValueExample valueExample = new ProjectPropertyValueExample();
		ProjectPropertyValueExample.Criteria old_criteria = valueExample.createCriteria();
		old_criteria.andProjectIdEqualTo(project.getId());
		old_criteria.andProductPropertyIdEqualTo(monthCountPropertyId);
		ProjectPropertyValue oldPropertyValue = projectPropertyValueService.getMapper().selectByExample(valueExample).get(0);

		//计算续养后的牛只月龄有没有超标  母牛可饲养总期限：1800天 公牛可饲养总期限：720天
		int maxLifeDays = project.getSex().equals("0") ? 720 : 1800;
		int tempDays = Integer.parseInt(oldPropertyValue.getPropertyValue()) * 30 + project.getLimitDays() + projectDaysEnum.getFeatureType();
		if (tempDays > maxLifeDays) {
			//提示续养日期参数有误
			throw  new ResponseException("-1","续养日期参数有误");
		}

		ProjectExample example = new ProjectExample();
		ProjectExample.Criteria criteria = example.createCriteria();
//		criteria.andUserIdEqualTo(project.getUserId());
		criteria.andParentIdEqualTo(project.getId());

		//判断此标是否可以再续  自己没续成功，后台没有发布新的
		List<Project> projects = getMapper().selectByExample(example);
		Project newProject=null;
		if(projects!=null && projects.size()>0){
			if(projects.size()==2){
				throw  new ResponseException("1","牛只已上标，无法续养。");
			}else {
				newProject = projects.get(0);
				if(!newProject.getBuyAgain()){
					throw  new ResponseException("1","牛只已上标，无法续养。");
				}
				if (newProject.getBuyAgain() &&
						(newProject.getStatus() == ProjectStatusEnum.SALED.getCode()
								|| newProject.getStatus() == ProjectStatusEnum.BUYBACK.getCode()
								|| newProject.getStatus() == ProjectStatusEnum.DEAD.getCode())) {
					throw  new ResponseException("2","此牛只您已续养。");
				}
				if(newProject.getBuyAgain() && (newProject.getStatus()==ProjectStatusEnum.PAYING.getCode())){
					//自己有没有正在续购 投资订单还未关闭  （续购正在付款中则提示或者跳转到支付页面）
					throw  new ResponseException("3","续养订单正在支付中。");
				}
			}
		}


		//计算之前认养后的牛只重量  最佳饲养期：公牛700KG;母牛600KG；到达最佳饲养期则用最大公斤数计算
		BigDecimal maxWeight = BigDecimalUtil.parse(project.getSex().equals("0") ? 700 : 600);
		BigDecimal tempWeight = BigDecimalUtil.parse(project.getWeight()).add(BigDecimalUtil.parse(project.getLimitDays()));
		BigDecimal newWeight = tempWeight.compareTo(maxWeight) > 0 ? maxWeight : tempWeight;

		//计算牛只价格
		BuyBullsDetailMoneyVO buyMoneyDetail = caculateBuyMoney(project, projectDaysEnum);


		//如果没有则直接添加，有则更新标的
		if (newProject == null) {

			newProject = new Project();
			BeanUtils.copyProperties(project, newProject, "id");
			newProject.setParentId(project.getId());
			newProject.setInvestmentId(0);

			newProject.setIncreaseAnnualized(0F);

			newProject.setCreateDate(new Date());
			newProject.setUpdateDate(new Date());

			newProject.setInvestedAmount(0d);
			newProject.setInvestorsNum(0);

			newProject.setNoob(0);
			newProject.setContractId("");
			newProject.setAutoEnableSale(false);
			newProject.setBuyAgain(true);
			newProject.setVersion(0);

			newProject.setStatus(0);

			newProject.setWeight(newWeight.doubleValue());

			newProject.setLimitDays(projectDaysEnum.getFeatureType());
			newProject.setAnnualized(projectDaysEnum.getRate().divide(BigDecimalUtil.parse(100)).floatValue());
			newProject.setRaiseFee(buyMoneyDetail.getFeedMoney().doubleValue());
			newProject.setManageFee(buyMoneyDetail.getManageMoney().doubleValue());
			newProject.setInterestAmount(buyMoneyDetail.getTotalInterest().doubleValue());
			newProject.setUnitFeedPrice(buyMoneyDetail.getUnitFeedMoney().doubleValue());
			newProject.setUnitManagePrice(buyMoneyDetail.getUnitManageMoney().doubleValue());

			//保留百位
			newProject.setTotalAmount(buyMoneyDetail.getTotalMoney()
					.subtract(buyMoneyDetail.getTotalMoney().divideAndRemainder(BigDecimalUtil.parse(100))[1])
					.doubleValue());

			getMapper().insertSelective(newProject);


			//查询之前标的属性
			ProjectPropertyValueExample ppExample = new ProjectPropertyValueExample();
			ProjectPropertyValueExample.Criteria criteria1 = ppExample.createCriteria();
			criteria1.andProjectIdEqualTo(project.getId());

			List<ProjectPropertyValue> propertyValues = projectPropertyValueService.getMapper().selectByExample(ppExample);
			ProjectPropertyValue newProperty = null;
			for (ProjectPropertyValue pp : propertyValues) {
				newProperty = new ProjectPropertyValue();
				newProperty.setProjectId(newProject.getId());
				newProperty.setProductPropertyId(pp.getProductPropertyId());
				newProperty.setCreateDate(new Date());
				//月龄要增加
				if (pp.getProductPropertyId().intValue() == monthCountPropertyId) {
					newProperty.setPropertyValue((Integer.parseInt(pp.getPropertyValue()) + project.getLimitDays() / 30) + "");
				} else {
					newProperty.setPropertyValue(pp.getPropertyValue());
				}

				projectPropertyValueService.getMapper().insertSelective(newProperty);
			}


			//查询之前上传图片信息
			ProjectPictureExample pictureExample = new ProjectPictureExample();
			ProjectPictureExample.Criteria criteria2 = pictureExample.createCriteria();
			criteria2.andProjectIdEqualTo(project.getId());

			List<ProjectPicture> pictures = projectPictureMapper.selectByExample(pictureExample);

			ProjectPicture projectPicture = null;
			//添加原标的图片及属性
			for (ProjectPicture picture : pictures) {
				projectPicture = new ProjectPicture();
				projectPicture.setProjectId(newProject.getId());
				projectPicture.setType(picture.getType());
				projectPicture.setName(picture.getName());
				projectPicture.setUploadId(picture.getUploadId());
				projectPicture.setCreateDate(picture.getCreateDate());
				projectPicture.setStatus(picture.getStatus());

				projectPictureMapper.insertSelective(projectPicture);
			}


			//增加节点
			OrderDone orderDone = new OrderDone();
			orderDone.setOrderNo(newProject.getId().toString());
			orderDone.setOrderType(OrderTypeEnum.PROJECT.getFeatureName());
			orderDone.setOrderStatus(OrderDoneEnum.CREATEPROJECT.getFeatureName());
			orderDone.setCreateDate(new Date());
			orderDone.setUpdateDate(new Date());

			if (orderDoneService.insert(orderDone) != 1) {
				throw new LockFailureException();
			}
		} else {

			//不同的选择期限，更新续养牛只数据

			newProject.setWeight(newWeight.doubleValue());

			newProject.setLimitDays(projectDaysEnum.getFeatureType());
			newProject.setAnnualized(projectDaysEnum.getRate().divide(BigDecimalUtil.parse(100)).floatValue());
			newProject.setRaiseFee(buyMoneyDetail.getFeedMoney().doubleValue());
			newProject.setManageFee(buyMoneyDetail.getManageMoney().doubleValue());
			newProject.setInterestAmount(buyMoneyDetail.getTotalInterest().doubleValue());

			//保留百位
			newProject.setTotalAmount(buyMoneyDetail.getTotalMoney()
					.subtract(buyMoneyDetail.getTotalMoney().divideAndRemainder(BigDecimalUtil.parse(100))[1])
					.doubleValue());

			getMapper().updateByPrimaryKeySelective(newProject);


			//增加节点
			OrderDone orderDone = new OrderDone();
			orderDone.setOrderNo(newProject.getId().toString());
			orderDone.setOrderType(OrderTypeEnum.PROJECT.getFeatureName());
			orderDone.setOrderStatus(OrderDoneEnum.UPDATEPROJECT.getFeatureName());
			orderDone.setCreateDate(new Date());
			orderDone.setUpdateDate(new Date());

			if (orderDoneService.insert(orderDone) != 1) {
				throw new LockFailureException();
			}



//				//查询之前标的属性
//				ProjectPropertyValueExample valueExample = new ProjectPropertyValueExample();
//				ProjectPropertyValueExample.Criteria criteria1 = valueExample.createCriteria();
//				criteria1.andProjectIdEqualTo(p.getId());
//				criteria1.andProductPropertyIdEqualTo(monthCountPropertyId);
//				ProjectPropertyValue oldPropertyValue = projectPropertyValueService.getMapper().selectByExample(valueExample).get(0);
//
//				//修改月龄
//				ProjectPropertyValueExample example2 = new ProjectPropertyValueExample();
//				ProjectPropertyValueExample.Criteria criteria2 = example2.createCriteria();
//				criteria2.andProjectIdEqualTo(newProject.getId());
//				criteria2.andProductPropertyIdEqualTo(monthCountPropertyId);
//
//				ProjectPropertyValue newPropertyValue = projectPropertyValueService.getMapper().selectByExample(example2).get(0);
//				newPropertyValue.setPropertyValue((Integer.parseInt(oldPropertyValue.getPropertyValue())+p.getLimitDays()/30)+"");
//
//				projectPropertyValueService.getMapper().updateByPrimaryKey(newPropertyValue);
		}

		return newProject.getId();
	}

	/**
	 * 根据前一次饲养牛的情况计算牛只可续养的周期
	 *      母牛可饲养总期限：1800天
	 *      公牛可饲养总期限：720天
	 *      最佳饲养期：公牛700KG;母牛600KG；到达最佳饲养期则用最大公斤数计算
	 * @param sex
	 * @param age
	 * @param feedDays
	 * @return
	 */
	@Override
    public List<KeepPeriodVO> caculateBuyAgainCase(String sex,Integer age,Integer feedDays){

		int totalDays = sex.equals("0")?720:1800;

		int  remainDays = totalDays - age - feedDays;
        EnableKeepPeriodVO enableKeepPeriodVO = new EnableKeepPeriodVO();
        List<KeepPeriodVO> list = new ArrayList<>();
        if(remainDays>=90){
            ProjectDaysEnum projectDaysEnum = ProjectDaysEnum.PROJECT_DAYS_90;
            KeepPeriodVO kp=new KeepPeriodVO();
            kp.setDays(projectDaysEnum.getFeatureType());
            kp.setDaysName(projectDaysEnum.getFeatureName());
            list.add(kp);
        }
        if(remainDays>=180){
            ProjectDaysEnum projectDaysEnum = ProjectDaysEnum.PROJECT_DAYS_180;
            KeepPeriodVO kp=new KeepPeriodVO();
            kp.setDays(projectDaysEnum.getFeatureType());
            kp.setDaysName(projectDaysEnum.getFeatureName());
            list.add(kp);
        }
        if(remainDays>=360){
            ProjectDaysEnum projectDaysEnum = ProjectDaysEnum.PROJECT_DAYS_360;
            KeepPeriodVO kp=new KeepPeriodVO();
            kp.setDays(projectDaysEnum.getFeatureType());
            kp.setDaysName(projectDaysEnum.getFeatureName());
            list.add(kp);
        }

        return list;
    }


	/**
	 * 根据续养周期，计算饲养需要支付的资金
	 *
	 *
	 * @param project
	 * @param daysEnum
	 */
	@Override
	public BuyBullsDetailMoneyVO caculateBuyMoney(Project project, ProjectDaysEnum daysEnum){
		if(daysEnum==null){
			return  new BuyBullsDetailMoneyVO();
		}

		ProductExample productExample = new ProductExample();
		ProductExample.Criteria criteria = productExample.createCriteria();
		criteria.andIdEqualTo(project.getProductId());
		Product product = productMapper.selectByExample(productExample).get(0);


		//计算牛只价格,根据此前饲养结束时的重量(初始重量+饲养预期增重)  最佳饲养期：公牛700KG;母牛600KG；到达最佳饲养期则用最大公斤数计算
		BigDecimal bull_weight = BigDecimalUtil.parse(project.getWeight())
				.add(BigDecimalUtil.parse(product.getAddWeight()).multiply(BigDecimalUtil.parse(project.getLimitDays())));

		BigDecimal maxWeight = project.getSex().equals("0")?BigDecimalUtil.parse(700):BigDecimalUtil.parse(600);
		bull_weight = BigDecimalUtil.parse(Math.min(bull_weight.doubleValue(),maxWeight.doubleValue()));

		BigDecimal unit_price = project.getSex().equals("0")?BigDecimalUtil.parse(product.getMalePrice()):BigDecimalUtil.parse(product.getFemalePrice());

		return caculateBuyMoney(project.getSex(),bull_weight,
						BigDecimalUtil.parse(product.getAddWeight()),
						BigDecimalUtil.parse(product.getFeedPrice()),unit_price
						,daysEnum, BigDecimal.valueOf(project.getAnnualized() * 100));

	}


	/**
	 * 根据续养周期，计算饲养需要支付的资金
	 * @param sex
	 * @param weight
	 * @param unitAddWeighMoney
	 * @param unitFeedMoney
	 * @param unitBullPrice
	 * @param daysEnum
	 * @return
	 */
	@Override
	public BuyBullsDetailMoneyVO caculateBuyMoney(String sex, BigDecimal weight,
												   BigDecimal unitAddWeighMoney,
												   BigDecimal unitFeedMoney,
												   BigDecimal unitBullPrice,
												   ProjectDaysEnum daysEnum,
												   BigDecimal annualized){
		if(daysEnum==null){
			return  new BuyBullsDetailMoneyVO();
		}

		BuyBullsDetailMoneyVO detailMoneyVO  = new BuyBullsDetailMoneyVO();

		detailMoneyVO.setUnitFeedMoney(unitFeedMoney);
		detailMoneyVO.setUnitAddWeightMoney(unitAddWeighMoney);

		//计算牛只价格,根据此前饲养结束时的重量(初始重量+饲养预期增重)
		// 最佳饲养期：公牛700KG;母牛600KG；到达最佳饲养期则用最大公斤数计算
//		BigDecimal bull_weight = BigDecimalUtil.parse(weight)
//				.add(BigDecimalUtil.parse(product.getAddWeight()).multiply(BigDecimalUtil.parse(project.getLimitDays())));

		BigDecimal maxWeight = sex.equals("0")?BigDecimalUtil.parse(700):BigDecimalUtil.parse(600);
		BigDecimal bull_weight = BigDecimalUtil.parse(Math.min(weight.doubleValue(), maxWeight.doubleValue()));

		detailMoneyVO.setUnitBullPrice(unitBullPrice);

		//计算牛只价格
		detailMoneyVO.setBullMoney(bull_weight.multiply(unitBullPrice));

		//计算饲料费用
		detailMoneyVO.setFeedMoney(
				detailMoneyVO.getUnitFeedMoney()
						.multiply(BigDecimalUtil.parse(daysEnum.getFeatureType()))
		);

		//根据饲养周期的相应年华率，计算管理费   (12*增价值 — 牛初始价值*a)/(a+12) = 总费用
		// 公牛公式 ( 购牛款+总费用  饲养期间按年化率获取得的利息    =      预期增重牛肉价值 - 总费用 )
		// 母牛公式 ( 购牛款+总费用  饲养期间按年化率获取得的利息    =      按母牛一年增重价值8000 - 总费用 )

		BigDecimal growupValue= sex.equals("0")
				? detailMoneyVO.getUnitAddWeightMoney().multiply(BigDecimalUtil.parse(daysEnum.getFeatureType()))
				.multiply(detailMoneyVO.getUnitBullPrice())
				: BigDecimalUtil.parse(8000).multiply(BigDecimalUtil.parse(daysEnum.getFeatureType()))
				.divide(BigDecimalUtil.parse(360),2, RoundingMode.DOWN);
		// 年化利率
		BigDecimal rate = daysEnum.getRate();
		if (annualized != null && annualized.compareTo(BigDecimal.ZERO) > 0 && annualized.compareTo(rate) != 0) {
			rate = annualized;
		}
		BigDecimal a = rate.multiply(BigDecimalUtil.parse(daysEnum.getFeatureType())).divide(BigDecimalUtil.parse(3000),8,RoundingMode.DOWN);
		BigDecimal totalFee =	BigDecimalUtil.parse(12).multiply(growupValue).subtract(detailMoneyVO.getBullMoney().multiply(a))
				.divide(a.add(BigDecimalUtil.parse(12)),2,RoundingMode.DOWN);

		detailMoneyVO.setUnitManageMoney(totalFee.divide(BigDecimalUtil.parse(daysEnum.getFeatureType()),4, RoundingMode.DOWN)
				.subtract(detailMoneyVO.getUnitFeedMoney()));

//		detailMoneyVO.setManageMoney(detailMoneyVO.getUnitManageMoney().multiply(BigDecimalUtil.parse(daysEnum.getFeatureType())));
		detailMoneyVO.setManageMoney(totalFee.subtract(detailMoneyVO.getFeedMoney()));

		detailMoneyVO.setTotalMoney(detailMoneyVO.getBullMoney().add(detailMoneyVO.getFeedMoney().add(detailMoneyVO.getManageMoney())));

		detailMoneyVO.setTotalInterest(growupValue.subtract(totalFee));

		return  detailMoneyVO;
	}

	@Override
	public List<Project> queryMonthlyGainProjectListWithoutOriginalProject(Map<String, Object> params) {
		return projectMapper.queryMonthlyGainProjectListWithoutOriginalProject(params);
	}

	@Override
	public Project getProjectById(int projectId) {
		return projectMapper.selectByPrimaryKey(projectId);
	}

	@Override
	public List<Map<String, Object>> listInvestmentPage(Integer id, Integer start, Integer limit) {
		return projectMapper.listInvestmentByProjectId(id, start, limit);
	}

	@Override
	public int countInvestmentPage(Integer id) {
		return projectMapper.countInvestmentByProjectId(id);
	}

	@Override
	public String getProjectsmallImagePath(String type, Integer projectId, boolean retainWatermark) {
		List<String> list = projectMapper.getProjectImagePath(type, projectId);
		String prefix = retainWatermark ? "projectrenbao" : "project";
		if (list != null && list.size() != 0) {
			if(type.equals("1")) {
				InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("upload.properties");
				Properties p = new Properties();
				try {
					p.load(inputStream);
				} catch (IOException e) {
					logger.error(e);
				}
				System.out.println("littleImagePath:"+ClientConstants.ALIBABA_PATH + "upload/" + list.get(0));
				return getProjectsmallImagePathResult(p, list.get(0), retainWatermark);
//				return (ClientConstants.ALIBABA_PATH + "upload/" + list.get(0)).replaceAll("/"+p.getProperty(ConstantsAdmin.PROJECT_PICTURE_PATH,"project")+"/"
//						, "/"+p.getProperty(ConstantsAdmin.PROJECT_PICTURE_RENBAO_PATH, prefix)+"/");
			}else {
				return ClientConstants.ALIBABA_PATH + "upload/" + list.get(0);
			}
		} else {
			return null;
		}
	}



	/**
	 * 生成账单 根据project和单个investment 生成interest账单并插入数据库
	 * 汇总investment和project的利息,将投资的状态从no_buy改为buyed,并保存 将结果封装回investment和project
	 *
	 * @author:[张琼麒]
	 * @update:[日期2019-05-17] [张琼麒]
	 * @param project
	 * @return 总利息
	 * @throws Exception
	 */
	@Override
	public void doGeneratedInterestForOneInvestment(Project project, Investment investment) throws Exception {

		project.setInvestmentList(new ArrayList<Investment>());
		project.getInvestmentList().add(investment);

		this.doGeneratedInterest(project);

	}

	/**
	 * 预成账单 根据project和单个investment 生成interest账单并插入数据库
	 * 汇总investment和project的利息,将投资的状态从no_buy改为buyed,并保存 将结果封装回investment和project
	 *
	 * @author:[张琼麒]
	 * @update:[日期2019-05-17] [张琼麒]
	 * @param project
	 * @return 总利息
	 * @throws Exception
	 */
	@Override
	public void tryGeneratedInterestForOneInvestment(Project project, Investment investment) throws Exception {

		project.setInvestmentList(new ArrayList<Investment>());
		project.getInvestmentList().add(investment);

		investmentService.pregeneratedInterest(project);
		;

	}

	/**
	 * 生成账单 根据project,以及其封装的investmentList 生成interest账单并插入数据库,
	 * 汇总investment和project的利息,将投资的状态从no_buy改为buyed,并保存 将结果封装回investment和project
	 *
	 * @author:[张琼麒]
	 * @update:[日期2019-05-17] [张琼麒]
	 * @param project
	 * @return 总利息
	 * @throws Exception
	 */
	@Override
	public void doGeneratedInterest(Project project) throws Exception {
		if (project.getStatus() != ProjectStatusEnum.PAYING.getCode()) {
			throw new Exception("项目状态不能创建状态");
		}
		for (Investment investment : project.getInvestmentList()) {
			if (investment.getPayStatus() != InvestPayStateEnum.PAYED.getCode()) {
				throw new Exception("投资未支付成功");// 支付状态 0：未支付，1：支付中 2：已支付
			}

			//0：未饲养，1：饲养期，2：已卖牛 3 已取消, 4 待成交
			if(investment.getType().equals(ProjectTypeEnum.YANGNIU.getFeatureType())) {
				if(investment.getOrderStatus() != InvestmentStateEnum.no_buy.getCode()) {
					throw new Exception("投资状态不能创建账单");
				}
			}else if(investment.getType().equals(ProjectTypeEnum.PINNIU.getFeatureType())){
				if(investment.getOrderStatus() != InvestmentStateEnum.waiting.getCode()) {
					throw new Exception("投资状态不能创建账单");
				}
			}else {
				throw new Exception("投资状态不能创建账单");
			}
		}
		// 当前产品,结算按自然日结算
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		project.setDeadline(calendar.getTime());

		investmentService.pregeneratedInterest(project);

		this.projectMapper.updateByPrimaryKey(project);
		for (Investment investment : project.getInvestmentList()) {
			investment.setOrderStatus(InvestmentStateEnum.buyed.getCode());// 0：未饲养，1：饲养期，2：已卖牛 3 已取消
			investmentService.getMapper().updateByPrimaryKey(investment);
			for (Interest interest : investment.getInterestList()) {
				interestService.insert(interest);
			}
		}
	}

	/**
	 * 根据project的investment是否"已卖牛",决定是否修改project的状态到"已回购"
	 * 由investmentService.doInvestmentBuyBack调用
	 * <p>
	 * Title:[project完成buyBack]
	 * </p>
	 *
	 * @author:[张琼麒]
	 * @update:[日期2019-05-21] [张琼麒]
	 * @param project
	 * @return
	 * @throws Exception
	 */
	@Override
	public void doBuyBack(Project project) throws Exception {
		// 锁项目
		project = this.getMapper().selectByPrimaryKeyForUpdate(project.getId());
		if (!project.getStatus().equals(ProjectStatusEnum.SALED.getCode())) {
			throw new Exception("项目未售出,不能回购");
		}
		// 当前时间
		Calendar now = Calendar.getInstance();
		// 最后结算时间
		Calendar dueCalendar = Calendar.getInstance();
		// 起息时间
		dueCalendar.setTime(project.getDeadline());
		// [计息期]类型 0日,1月
		Integer interestPeriodsType = 0;
		// 总[计息期]数
		Integer interestTotalPeriods = project.getLimitDays();
		if (interestPeriodsType == 0) {// [计息期]类型 0日,1月
			dueCalendar.add(dueCalendar.DATE, interestTotalPeriods.intValue());
		} else if (interestPeriodsType == 1) {
			dueCalendar.add(dueCalendar.MONTH, interestTotalPeriods.intValue());
		}
		if (dueCalendar.after(now)) {
			throw new Exception("项目当前未到期");
		}

		InvestmentExample example = new InvestmentExample();
		InvestmentExample.Criteria c = example.createCriteria();
		c.andProjectIdEqualTo(project.getId());
		c.andPayStatusEqualTo(2);// 支付状态 0：未支付，1：已支付  -->   0：未支付，1：支付中 2：已支付
		c.andOrderStatusEqualTo(1);// 0：未饲养，1：饲养期，2：已卖牛 3 已取消
		List<Investment> investmentList = investmentService.getMapper().selectByExample(example);

		if (investmentList.size() == 0) {
			project.setStatus(ProjectStatusEnum.BUYBACK.getCode());
			project.setBuyBackTime(new Date());
			project.setUpdateDate(new Date());
			this.updateByPrimaryKeySelectiveForVersion(project);
		}
	}

	@Override
	public Map<String, Object> getProjectDetail(Integer projectId) {
		Map<String, Object> infoMap = this.getMapper().getProjectDetailInfo(projectId);
		// 项目产品属性及值
		List<Map<String, Object>> proList = projectMapper.listProjectProductPropertyInfoById(projectId);
		String littleImagePath = getProjectsmallImagePath("1", projectId, true);
		// 拼装体重
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("property_name", "体重（约）");
		map.put("property_value", Double.valueOf(infoMap.get("weight") + "") + "kg");
		proList.add(map);
		map = new HashMap<String, Object>();
		map.put("property_name", "性别");
		if (infoMap.get("sex").equals("0")) {
			map.put("property_value", "公");
		} else {
			map.put("property_value", "母");
		}
		proList.add(map);
		infoMap.put("proList", proList);
		infoMap.put("littleImagePath", littleImagePath);
		infoMap.put("bigImagePath", littleImagePath);
		infoMap.put("totalAmountStr", BigDecimal.valueOf(Double.valueOf(infoMap.get("total_amount").toString())).setScale(0, BigDecimal.ROUND_DOWN)+""+"元认领");
		return infoMap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> listProjectInfo(Map<String, Object> parMap) {
		List<Map<String, Object>> list = projectMapper.listProjectDetailInfoByPage(parMap);

		String littleImagePath = "";
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			Integer projectId = (Integer) map.get("id");
//			// 项目产品属性及值
//			List<Map<String, Object>> proList = projectMapper.listProjectProductPropertyInfoById(projectId);
//			for (Iterator iterator2 = proList.iterator(); iterator2.hasNext();) {
//				Map<String, Object> map2 = (Map<String, Object>) iterator2.next();
//				if("性别".equals(map2.get("property_name"))) {
//					map.put("sex", map2.get("property_value"));
//				}
//			}
			// 项目小图
			littleImagePath = getProjectsmallImagePath("1", projectId, true);
			map.put("littleImagePath", littleImagePath);
			map.put("nhxx", "饲养预计利润");
		}
		return list;
	}

	@Override
	public boolean ifExistProjectByEarnumber(Integer projectId, String earNumber) {
		ProjectExample example = new ProjectExample();
		Criteria criteria = example.createCriteria();
		criteria.andEarNumberEqualTo(earNumber).andStatusNotIn(Arrays.asList(
				ProjectStatusEnum.BUILDED.getCode(),
				ProjectStatusEnum.BUYBACK.getCode(),
				ProjectStatusEnum.DELETE.getCode()
		));
		if (org.springframework.util.StringUtils.isEmpty(projectId)) {
//			example.createCriteria().andEarNumberEqualTo(earNumber)
//					.andStatusNotEqualTo(ProjectStatusEnum.BUILDED.getCode()).andStatusNotEqualTo(ProjectStatusEnum.BUYBACK.getCode());
		} else {
//			example.createCriteria().andEarNumberEqualTo(earNumber).andIdNotEqualTo(projectId)
//					.andStatusNotEqualTo(ProjectStatusEnum.BUILDED.getCode()).andStatusNotEqualTo(ProjectStatusEnum.BUYBACK.getCode());
			criteria.andIdNotEqualTo(projectId);
		}
		List<Project> list = getMapper().selectByExample(example);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean ifExistProjectByRealEarnumber(Integer projectId, String realEarnumber) {
		ProjectExample example = new ProjectExample();
		Criteria criteria = example.createCriteria();
		criteria.andRealEarNumberEqualTo(realEarnumber).andStatusNotIn(Arrays.asList(
				ProjectStatusEnum.BUILDED.getCode(),
				ProjectStatusEnum.BUYBACK.getCode(),
				ProjectStatusEnum.DELETE.getCode()
		));
		if (org.springframework.util.StringUtils.isEmpty(projectId)) {
//			example.createCriteria().andRealEarNumberEqualTo(realEarnumber)
//					.andStatusNotEqualTo(ProjectStatusEnum.BUILDED.getCode()).andStatusNotEqualTo(ProjectStatusEnum.BUYBACK.getCode());
		} else {
//			example.createCriteria().andRealEarNumberEqualTo(realEarnumber).andIdNotEqualTo(projectId)
//					.andStatusNotEqualTo(ProjectStatusEnum.BUILDED.getCode()).andStatusNotEqualTo(ProjectStatusEnum.BUYBACK.getCode());
			criteria.andIdNotEqualTo(projectId);
		}
		List<Project> list = getMapper().selectByExample(example);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean ifExistProjectByBuyAgain(Integer projectId) {
		if (projectId == null) {
			return false;
		}
		ProjectExample example = new ProjectExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(projectId).andBuyAgainEqualTo(true).andStatusNotIn(Arrays.asList(
				ProjectStatusEnum.BUILDED.getCode(),
				ProjectStatusEnum.BUYBACK.getCode(),
				ProjectStatusEnum.DELETE.getCode()
		));
		List<Project> list = getMapper().selectByExample(example);
		if (list.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Map<String, Object> getProjectDetailByEarNumber(String gpsNumber) {
		Map<String, Object> infoMap = this.getMapper().getProjectDetailInfoByGpsNumber(gpsNumber);
		Integer projectId = (Integer) infoMap.get("id");
		// 项目产品属性及值
		List<Map<String, Object>> proList = projectMapper.listProjectProductPropertyInfoById(projectId);
		String littleImagePath = getProjectsmallImagePath("1", projectId, true);
		// 拼装体重
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("property_name", "体重（约）");
		map.put("property_value", Double.valueOf(infoMap.get("weight") + "") + "kg");
		proList.add(map);
		map.clear();
		map.put("property_name", "性别");
		if (infoMap.get("sex").equals("0")) {
			map.put("property_value", "公");
		} else {
			map.put("property_value", "母");
		}
		proList.add(map);
		infoMap.put("proList", proList);
		infoMap.put("littleImagePath", littleImagePath);
		return infoMap;
	}

	@Override
	public boolean checkGpsNumber(String gpsNumber) {
		int count = this.getMapper().getProjectByGpsNumber(gpsNumber);
		if(count>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<ProjectGpsListVo> projectGpsList(Map<String, Object> parMap) {
		List<ProjectGpsListVo> list = projectMapper.listGpsProjectByPage(parMap);

		String littleImagePath = "";
		for (ProjectGpsListVo project :list ) {
			Integer projectId = project.getId();
			// 项目小图
			littleImagePath = getProjectsmallImagePath("1", projectId, true);
			project.setLittleImagePath(littleImagePath);
		}
		return list;
	}

	@Override
	public void doEnableSale(Project oldProject) throws Exception {
		Project project = this.getMapper().selectByPrimaryKey(oldProject.getId());
		if (project.getStatus() != ProjectStatusEnum.BUILDED.getCode()) {
			throw new Exception("只能编辑新建的项目");
		}
		project.setAutoEnableSale(oldProject.getAutoEnableSale());
		project.setStatus(ProjectStatusEnum.ENABLE_SALE.getCode());
		project.setStartTime(new Date());

		// 取整百
		double yu = project.getTotalAmount() % 100;
		project.setTotalAmount(BigDecimalUtil.sub(project.getTotalAmount(), yu));
		this.updateByPrimaryKeySelectiveForVersion(project);
	}

	final static List<Integer> SALE_STATUS_LIST= new ArrayList<Integer>(){{add(ProjectStatusEnum.ENABLE_SALE.getCode());add(ProjectStatusEnum.PAYING.getCode());}};

	@Override
	public void doEnableSaleByLimitDays(Integer limitDays,Integer projectType,Integer saleStatusProjectMiniCount) throws Exception {
		if(limitDays==null || limitDays<=0) {
			return;
		}
		if(saleStatusProjectMiniCount==null || saleStatusProjectMiniCount<=0) {
			return;
		}
		ProjectExample saleStatusExample=new ProjectExample();
		Criteria saleStatusCriteria=saleStatusExample.createCriteria();
		saleStatusCriteria.andStatusIn(SALE_STATUS_LIST);
		saleStatusCriteria.andLimitDaysEqualTo(limitDays).andProjectTypeEqualTo(projectType);
		Integer saleStatusProjectCount=this.getMapper().countByExample(saleStatusExample);

		if(saleStatusProjectCount>=saleStatusProjectMiniCount) {
			return;
		}

		ProjectExample buildedExample=new ProjectExample();
		Criteria buildedCriteria=buildedExample.createCriteria();
		buildedExample.setOrderByClause("id");
		buildedCriteria.andStatusEqualTo(ProjectStatusEnum.BUILDED.getCode());
		buildedCriteria.andLimitDaysEqualTo(limitDays).andProjectTypeEqualTo(projectType).andBuyAgainEqualTo(false);
		List<Project> buildedProjectList=this.getMapper().selectByExample(buildedExample);

		for(Project buildedProject:buildedProjectList) {
			try {
				buildedProject.setAutoEnableSale(true);
				this.doEnableSale(buildedProject);
				if(++saleStatusProjectCount>=saleStatusProjectMiniCount) {
					return;
				}
			} catch (Exception e) {
				logger.info("自动上架 doEnableSaleByLimitDays():"+e.getMessage(),e);
			}
		}
	}
	@Override
	public List<Map<String, Object>> listBuyBackRecord(String earNumber, Integer start, Integer limit) {
		return projectMapper.listBuyBackRecord(earNumber, start, limit);
	}

	@Override
	public int countBuyBackRecord(String earNumber) {
		return projectMapper.countBuyBackRecord(earNumber);
	}

	@Override
	public List<Integer> statisticsInfo(){
		return projectMapper.statisticsInfo();
	}

	@Override
	public boolean isNoob(Integer userId) {
		ProjectExample example = new ProjectExample();
		example.createCriteria().andUserIdEqualTo(userId).andNoobEqualTo(1).andStatusGreaterThan(2);
		int count = getMapper().countByExample(example);
		if (count != 0) {
			return false;// 不是新手
		}
		return true;//是新手
	}

	@Override
	public List<Map<String, Object>> listBuyBack(String startDate, String endDate, Integer startAge, Integer endAge,Integer start, Integer limit,Integer adminId,Integer departmentId) {
		return this.getMapper().listBuyBack(startDate, endDate, startAge, endAge, start,  limit,adminId,departmentId);
	}

	@Override
	public Integer countBuyBack(String startDate, String endDate, Integer startAge, Integer endAge,Integer adminId,Integer departmentId) {
		return this.getMapper().countBuyBack(startDate, endDate, startAge, endAge,adminId,departmentId);
	}

	@Override
	public List<Map<String, Object>> listBuyBackTJ(String startDate, String endDate){
		List<Map<String, Object>> list = this.getMapper().listBuyBackTJ(startDate, endDate);

		Map<String, Object> bMap = new HashMap<String, Object>();// 大牛
		Map<String, Object> sMap = new HashMap<String, Object>();// 小牛

		Map<String, Object> newMap1 = new HashMap<String, Object>();
		Map<String, Object> newMap2 = new HashMap<String, Object>();
		Map<String, Object> newMap3 = new HashMap<String, Object>();
		Map<String, Object> newMap4 = new HashMap<String, Object>();

		List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();

		if (CollectionUtils.isEmpty(list)) {
//			newMap1.put("name", "回购数量（只）");
//			newMap1.put("b_cow", 0);
//			newMap1.put("s_cow", 0);
//			newMap1.put("hj",  0);
//
//			newMap2.put("name", "应付本金");
//			newMap2.put("b_cow", 0);
//			newMap2.put("s_cow", 0);
//			newMap2.put("hj",  0);
//
//			newMap3.put("name", "应付授信");
//			newMap3.put("b_cow", 0);
//			newMap3.put("s_cow", 0);
//			newMap3.put("hj",  0);
//
//			newMap4.put("name", "应付合计");
//			newMap4.put("b_cow", 0);
//			newMap4.put("s_cow", 0);
//			newMap4.put("hj",0);
//
//			newList.add(newMap1);
//			newList.add(newMap2);
//			newList.add(newMap3);
//			newList.add(newMap4);
			return list;
		}

		if (list.size() == 1) {
			Map<String, Object> map = (Map<String, Object>) list.get(0);
			if ("基础牛群".equals(map.get("type"))) {
				newMap1.put("name", "回购数量（只）");
				newMap1.put("b_cow", (Long) map.get("count"));
				newMap1.put("s_cow", 0);
				newMap1.put("hj", (Long) map.get("count") + 0);

				newMap2.put("name", "应付本金");
				newMap2.put("b_cow", (Double) map.get("amount"));
				newMap2.put("s_cow", 0);
				newMap2.put("hj", BigDecimalUtil.add(map.get("amount"), 0));

				newMap3.put("name", "应付授信");
				newMap3.put("b_cow", (Double) map.get("interest_amount"));
				newMap3.put("s_cow", 0);
				newMap3.put("hj", BigDecimalUtil.add(map.get("interest_amount"), 0));

				newMap4.put("name", "应付合计");
				newMap4.put("b_cow", BigDecimalUtil.add(newMap2.get("b_cow"), newMap3.get("b_cow")));
				newMap4.put("s_cow", BigDecimalUtil.add(newMap2.get("s_cow"), newMap3.get("s_cow")));
				newMap4.put("hj", (BigDecimalUtil.add(newMap2.get("hj"), newMap3.get("hj"))));

				newList.add(newMap1);
				newList.add(newMap2);
				newList.add(newMap3);
				newList.add(newMap4);

				return newList;
			} else {
				newMap1.put("name", "回购数量（只）");
				newMap1.put("b_cow", 0);
				newMap1.put("s_cow", (Long) map.get("count"));
				newMap1.put("hj", 0 + (Long) map.get("count"));

				newMap2.put("name", "应付本金");
				newMap2.put("b_cow", 0);
				newMap2.put("s_cow", (Double) map.get("amount"));
				newMap2.put("hj", BigDecimalUtil.add(0, map.get("amount")));

				newMap3.put("name", "应付授信");
				newMap3.put("b_cow", 0);
				newMap3.put("s_cow", (Double) map.get("interest_amount"));
				newMap3.put("hj", BigDecimalUtil.add(0, map.get("interest_amount")));

				newMap4.put("name", "应付合计");
				newMap4.put("b_cow", BigDecimalUtil.add(newMap2.get("b_cow"), newMap3.get("b_cow")));
				newMap4.put("s_cow", BigDecimalUtil.add(newMap2.get("s_cow"), newMap3.get("s_cow")));
				newMap4.put("hj", (BigDecimalUtil.add(newMap2.get("hj"), newMap3.get("hj"))));

				newList.add(newMap1);
				newList.add(newMap2);
				newList.add(newMap3);
				newList.add(newMap4);

				return newList;
			}
		} else {// 两条记录都存在

			bMap = (Map<String, Object>) list.get(0);// 大牛
			sMap = (Map<String, Object>) list.get(1);// 小牛

			newMap1.put("name", "回购数量（只）");
			newMap1.put("b_cow", (Long) bMap.get("count"));
			newMap1.put("s_cow", (Long) sMap.get("count"));
			newMap1.put("hj", (Long) bMap.get("count") + (Long) sMap.get("count"));

			newMap2.put("name", "应付本金");
			newMap2.put("b_cow", (Double) bMap.get("amount"));
			newMap2.put("s_cow", (Double) sMap.get("amount"));
			newMap2.put("hj", BigDecimalUtil.add(bMap.get("amount"), sMap.get("amount")));

			newMap3.put("name", "应付授信");
			newMap3.put("b_cow", (Double) bMap.get("interest_amount"));
			newMap3.put("s_cow", (Double) sMap.get("interest_amount"));
			newMap3.put("hj", BigDecimalUtil.add(bMap.get("interest_amount"), sMap.get("interest_amount")));

			newMap4.put("name", "应付合计");
			newMap4.put("b_cow", BigDecimalUtil.add(newMap2.get("b_cow"), newMap3.get("b_cow")));
			newMap4.put("s_cow", BigDecimalUtil.add(newMap2.get("s_cow"), newMap3.get("s_cow")));
			newMap4.put("hj", (BigDecimalUtil.add(newMap2.get("hj"), newMap3.get("hj"))));

			newList.add(newMap1);
			newList.add(newMap2);
			newList.add(newMap3);
			newList.add(newMap4);

			return newList;
		}
	}

	@Override
	public List<Map<String, Object>> listCreditFunds(Integer typeId,String startDate, String endDate,Integer start, Integer limit) {
		return this.getMapper().listCreditFunds(typeId,startDate, endDate, start,  limit);
	}

	@Override
	public int countCreditFunds(Integer typeId,String startDate, String endDate) {
		return this.getMapper().countCreditFunds(typeId,startDate, endDate);
	}

	@Override
	public List<InvestStatementVO> investStatement(String startDate, String endDate) {
		List<InvestStatementVO> investStatementList = projectMapper.investStatement(startDate, endDate);
	/*
	    if(investStatementList != null && investStatementList.size() > 0) {
	    	for (InvestStatementVO vo : investStatementList) {

			}
	    }*/

	    return investStatementList;
	}

	/**
	 * 后台,物权资产查询
	 * @author 张琼麒
	 * @version 创建时间：2019年8月19日 下午3:18:28
	 * @param status
	 * @param noob
	 * @param limitDays
	 * @param keyword
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @param payTimeStart
	 * @param payTimeEnd
	 * @param buybackTimeStart
	 * @param buybackTimeEnd
	 * @param limitStart
	 * @param limit
	 * @param orderBy
	 * @param sort
	 * @return
	 */
	@Override
	public List<ProjectView> listProjectView(List<Integer> status, Integer noob,Integer limitDays,String keyword,
		Date createTimeStart, Date createTimeEnd,Date payTimeStart, Date payTimeEnd,
		Date buybackTimeStart, Date buybackTimeEnd,	Date dueTimeStart, Date dueTimeEnd,
		Date feedTime, Integer buyAgain, Integer projectType,
		Integer limitStart, Integer limit, String orderBy, String sort){

		ProjectViewExample example=this.assemblyConditions(status, noob,limitDays,keyword,
				createTimeStart, createTimeEnd,payTimeStart, payTimeEnd,
				buybackTimeStart, buybackTimeEnd,dueTimeStart,dueTimeEnd,feedTime, buyAgain, projectType,
				limitStart, limit, orderBy, sort);

		return projectViewMapper.selectByExample(example);
	}

	@Override
	public long countProjectView(List<Integer> status, Integer noob,Integer limitDays,String keyword,
		Date createTimeStart, Date createTimeEnd,Date payTimeStart, Date payTimeEnd,
		Date buybackTimeStart, Date buybackTimeEnd,	Date dueTimeStart, Date dueTimeEnd,
		Date feedTime, Integer buyAgain, Integer projectType){

		ProjectViewExample example=this.assemblyConditions(status, noob,limitDays,keyword,
				createTimeStart, createTimeEnd,payTimeStart, payTimeEnd,
				buybackTimeStart, buybackTimeEnd,dueTimeStart,dueTimeEnd,feedTime, buyAgain, projectType,
				null, null, null, null);

		return projectViewMapper.countByExample(example);
	}
	/**
	 * 为上两个查询组装Example
	 * @see #listProjectView
	 * @see #countProjectView
	 * @author 张琼麒
	 * @version 创建时间：2019年8月19日 下午4:02:52
	 * @param status
	 * @param noob
	 * @param limitDays
	 * @param keyword
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @param payTimeStart
	 * @param payTimeEnd
	 * @param buybackTimeStart
	 * @param buybackTimeEnd
	 * @param limitStart
	 * @param limit
	 * @param orderBy
	 * @param sort
	 * @return
	 */
	private ProjectViewExample assemblyConditions(List<Integer> status, Integer noob,Integer limitDays,String keyword,
			Date createTimeStart, Date createTimeEnd,Date payTimeStart, Date payTimeEnd,
			Date buybackTimeStart, Date buybackTimeEnd,	Date dueTimeStart, Date dueTimeEnd,	Date feedTime, Integer buyAgain, Integer projectType,
			Integer limitStart, Integer limit, String orderBy, String sort) {
		ProjectViewExample example=new ProjectViewExample();
		ProjectViewExample.Criteria criteria=example.createCriteria();

		if(status!=null) {
			criteria.andStatusIn(status);
		}
		if(noob!=null) {
			criteria.andNoobEqualTo(noob);
		}
		if(limitDays!=null) {
			criteria.andLimitDaysEqualTo(limitDays);
		}
		if(createTimeStart!=null) {
			criteria.andCreateDateGreaterThanOrEqualTo(createTimeStart);
		}
		if(createTimeEnd!=null) {
			criteria.andCreateDateLessThan(createTimeEnd);
		}
		if(payTimeStart!=null) {
			criteria.andTradeTimeGreaterThanOrEqualTo(payTimeStart);
		}
		if(payTimeEnd!=null) {
			criteria.andTradeTimeLessThan(payTimeEnd);
		}
		if(buybackTimeStart!=null) {
			criteria.andBuyBackTimeGreaterThanOrEqualTo(buybackTimeStart);
		}
		if(buybackTimeEnd!=null) {
			criteria.andBuyBackTimeLessThan(buybackTimeEnd);
		}
		if(feedTime!=null) {
			List<ProjectViewExample.Criteria> tempList=new ArrayList<ProjectViewExample.Criteria>();
			//payTime<=feedTime and (buyBackTime>feedTime or buyBackTime is null)
			for(ProjectViewExample.Criteria criteriaS:example.getOredCriteria()) {
				criteriaS.andTradeTimeLessThanOrEqualTo(feedTime);

				ProjectViewExample.Criteria criteriaS2=example.createCriteria();
				criteriaS2.getAllCriteria().addAll(criteriaS.getAllCriteria());//copy Criteria to or

				criteriaS.andBuyBackTimeGreaterThan(feedTime);
				criteriaS2.andBuyBackTimeIsNull();
				tempList.add(criteriaS2);
			}
			example.getOredCriteria().addAll(tempList);
		}

		List<Integer> statusList = new ArrayList<>();
		statusList.add(ProjectStatusEnum.DELETE.getCode());
		if (buyAgain == null || buyAgain == 0) {
			criteria.andBuyAgainEqualTo(false).andStatusNotIn(statusList);
		} else if (buyAgain == 1) {
			statusList.add(ProjectStatusEnum.BUILDED.getCode());
			// statusList.add(ProjectStatusEnum.PAYING.getCode());
			criteria.andBuyAgainEqualTo(true).andStatusNotIn(statusList);
		}
		if (projectType != null) {
			criteria.andProjectTypeEqualTo(projectType);
		}

		//// 注意：其他查询条件必须写在 keyword 条件之前，否则查询结果不准确
		//项目名或耳标
		if(!StringUtils.isBlank(keyword)) {
			List<ProjectViewExample.Criteria> tempList=new ArrayList<ProjectViewExample.Criteria>();
			for(ProjectViewExample.Criteria criteriaS:example.getOredCriteria()) {

				ProjectViewExample.Criteria criteriaS2=example.createCriteria();
				criteriaS2.getAllCriteria().addAll(criteriaS.getAllCriteria());//copy Criteria to or

				criteriaS.andTitleLike("%"+keyword+"%");
				criteriaS2.andEarNumberLike("%"+keyword+"%");
				tempList.add(criteriaS2);
			}
			example.getOredCriteria().addAll(tempList);
		}

		//分页
		if(limitStart!=null && limitStart>=0) {
			example.setLimitStart(limitStart);
			if(limit==null || limit<0) {
				limit=10;
			}
			example.setLimitEnd(limit);
		}
		//排序
		if(!StringUtils.isBlank(orderBy)) {
			example.setOrderByClause(orderBy+(sort==null?"":" "+sort));
		}
		return example;
	}

	@Override
	public List<Project> queryProjectByEarNumber(String earNumber) {
		List<Project> projectList = new ArrayList<>();
		ProjectExample example=new ProjectExample();
		ProjectExample.Criteria criteria=example.createCriteria();
		criteria.andEarNumberEqualTo(earNumber);
		projectList = this.getMapper().selectByExample(example);
		return projectList;
	}

	@Override
	public void saveAddLifePicture(MultipartFile file, String earNumber, int adminId) {
		Map<String, Object> map = uploadService.save(file, 17, adminId);
		if (map.get(ConstantsAdmin.STATUS).equals(ConstantsAdmin.SUCCESS)) {
			ProjectLifePicture lifePic = new ProjectLifePicture();
			lifePic.setCreateDate(new Date());
			lifePic.setUpdateDate(new Date());
			lifePic.setCreateUser(adminId);
			lifePic.setEarNumber(earNumber);
			lifePic.setStatus(1);// 1可用 0不可用
			lifePic.setUploadId(Integer.parseInt(map.get(ConstantsAdmin.ID)+""));
			lifePic.setPicturePath((String) map.get(ConstantsAdmin.PATH));
			lifePic.setIsRead(0); //0未读 1已读
			projectLifePictureMapper.insert(lifePic);
		}
	}

	@Override
    public List<ProjectLifePicture> queryLifePictureByEarNumber(String earNumber,Integer limitStart, Integer limit) {
        List<ProjectLifePicture> pictureList = new ArrayList<>();
        ProjectLifePictureExample example = lifePicCondition(earNumber,limitStart,limit);
        pictureList = projectLifePictureMapper.selectByExample(example);
        return pictureList;
    }

    @Override
    public long countLifePictureByEarNumber(String earNumber) {
        ProjectLifePictureExample example = lifePicCondition(earNumber,null,null);
        return projectLifePictureMapper.countByExample(example);
    }

    public ProjectLifePictureExample lifePicCondition(String earNumber,Integer limitStart, Integer limit) {
        ProjectLifePictureExample example = new ProjectLifePictureExample();
        ProjectLifePictureExample.Criteria criteria=example.createCriteria();
        criteria.andEarNumberEqualTo(earNumber);
        //分页
        if(limitStart!=null && limitStart>=0) {
            example.setLimitStart(limitStart);
            if(limit==null || limit<0) {
                limit=10;
            }
            example.setLimitEnd(limit);
        }
        // 只查询可用的
        criteria.andStatusEqualTo(1);//1可用 0不可用 2已删除
        //排序
        example.setOrderByClause("create_date desc");
        return example;
    }

	@Override
	public List<Map<String, Object>> listProjectPicture(Integer userId, Integer limitStart, Integer limitEnd) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("userId", userId);
	    map.put("limitStart", limitStart);
        map.put("limitEnd", limitEnd);

	    return projectMapper.listProjectPicture(map);
	}

	@Override
	public Integer countProjectPicture(Integer userId) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("userId", userId);

	    return projectMapper.countProjectPicture(map);
	}

    @Override
    public List<Map<String, Object>> getProjectPictureDetail(Integer userId, String earNum, Integer limitStart,
        Integer limitEnd) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("earNum", earNum);
        map.put("limitStart", limitStart);
        map.put("limitEnd", limitEnd);

        return projectMapper.getProjectPictureDetail(map);
    }

    @Override
    public Integer countProjectPictureDetail(Integer userId, String earNum) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("earNum", earNum);

        return projectMapper.countProjectPictureDetail(map);
    }

    @Override
    public Map<String, Object> addUploadVideo(MultipartFile file, int adminId) {
        Map<String, Object> map = uploadService.save(file, 18, adminId);
        VideoAlbum video = new VideoAlbum();
        if (map.get(ConstantsAdmin.STATUS).equals(ConstantsAdmin.SUCCESS)) {
            video.setCreateTime(new Date());
            video.setOperaterId(adminId);
            video.setVideoUrl(ClientConstants.ALIBABA_PATH + "upload/"+ map.get(ConstantsAdmin.PATH));
            video.setStatus(0);//1成功 0失败
            videoAlbumMapper.insert(video);
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("video", video);
        returnMap.put("upload", map);
        return returnMap;
    }

    @Override
    public List<VideoAlbum> listVideoAlbum(Integer isRecommend,Integer start, Integer end){
        VideoAlbumExample example = new VideoAlbumExample();
        VideoAlbumExample.Criteria criteria = example.createCriteria();
        if (start != null) {
            example.setLimitStart(start);
        }
        if (end != null) {
            example.setLimitEnd(end);
        }
        if (isRecommend != null) {
            criteria.andIsRecommendEqualTo(isRecommend);
        }
		criteria.andStatusEqualTo(1);
        example.setOrderByClause(" is_recommend desc, show_time desc ");
        return videoAlbumMapper.selectByExample(example);
    }

    @Override
    public long countVideoAlbum(Integer isRecommend){
        VideoAlbumExample example = new VideoAlbumExample();
        VideoAlbumExample.Criteria criteria = example.createCriteria();
        if (isRecommend != null) {
            criteria.andIsRecommendEqualTo(isRecommend);
        }
		criteria.andStatusEqualTo(1);
        return videoAlbumMapper.countByExample(example);
    }

    @Override
    public VideoAlbum getVideoAlbumById(Integer videoId) {
        return videoAlbumMapper.selectByPrimaryKey(videoId);
    }

    @Override
    public void updateVideoAlbum(VideoAlbum video) {
        videoAlbumMapper.updateByPrimaryKey(video);
    }

    @Override
    public ProjectLifePicture queryLifePictureById(Integer pictureId) {
        return projectLifePictureMapper.selectByPrimaryKey(pictureId);
    }

    @Override
    public void updateProjectLifePicture(ProjectLifePicture projectLifePicture) {
        projectLifePictureMapper.updateByPrimaryKey(projectLifePicture);
    }

    @Override
    public List<VideoAlbum> queryVideoAlbumList(Integer start, Integer end,String keyword,Integer isRecommend,
    	Date beginCreateTime, Date endCreateTime,Date beginShowTime, Date endShowTime){
		VideoAlbumExample example = videoCondition(keyword,isRecommend,beginCreateTime, endCreateTime, beginShowTime, endShowTime);
	    if (start != null) {
	    	example.setLimitStart(start);
	    }
	    if (end != null) {
	    	example.setLimitEnd(end);
	    }
	    return videoAlbumMapper.selectByExample(example);
    }

	@Override
    public int countVideoAlbumList(String keyword,Integer isRecommend,Date beginCreateTime, Date endCreateTime,Date beginShowTime, Date endShowTime){
    	VideoAlbumExample example = videoCondition(keyword, isRecommend,beginCreateTime, endCreateTime, beginShowTime, endShowTime);
    	return (int)videoAlbumMapper.countByExample(example);
    }


	public VideoAlbumExample videoCondition(String keyword,Integer isRecommend, Date beginCreateTime, Date endCreateTime,Date beginShowTime, Date endShowTime) {
		VideoAlbumExample example = new VideoAlbumExample();
        VideoAlbumExample.Criteria criteria = example.createCriteria();
    	if(!StringUtils.isBlank(keyword)) {
    		criteria.andTitleLike("%"+keyword+"%");
		}
    	if(isRecommend != null) {
    		criteria.andIsRecommendEqualTo(isRecommend);
    	}
        if(beginCreateTime != null) {
        	criteria.andCreateTimeGreaterThan(beginCreateTime);
        }
        if(endCreateTime != null) {
        	criteria.andCreateTimeLessThan(DateUtil.parse(DateUtil.dateAdd("day", 1, endCreateTime)+"", "yyyy-MM-dd"));
        }

        if(beginShowTime != null) {
        	criteria.andShowTimeGreaterThan(beginShowTime);
        }
        if(endShowTime != null) {
        	criteria.andShowTimeLessThan(DateUtil.parse(DateUtil.dateAdd("day", 1, endShowTime)+"", "yyyy-MM-dd"));
        }

        criteria.andStatusEqualTo(1);//只查询有效状态
		example.setOrderByClause(" is_recommend desc, create_time desc, show_time desc ");

		return example;
	}

	@Override
	public List<FilialeSellVO> listFilialeSell(Integer start, Integer end,String keyword, Date startTime, Date endTime) {
		return this.getMapper().listFilialeSell(start, end, keyword, startTime, endTime);
	}

	@Override
	public int countFilialeSell(String keyword, Date startTime, Date endTime) {
		return this.getMapper().countFilialeSell(keyword, startTime, endTime);
	}

	@Override
	public List<FilialeSellDetailVO> detailFilialeSell(Integer empId,String keyword,String recommendKeyword, Integer start, Integer end, Date startTime,
			Date endTime) {

		return this.getMapper().detailFilialeSell(empId,keyword, recommendKeyword, start, end, startTime, endTime);
	}

	@Override
	public int countDetailFilialeSell(Integer empId,String keyword,String recommendKeyword, Date startTime, Date endTime) {
		return this.getMapper().countDetailFilialeSell(empId,keyword, recommendKeyword, startTime, endTime);
	}

	@Override
	public int reshelfStatus(Integer parentId, Integer userId) throws Exception {
		// 0.未上架，1.后台上架（管理员手动上标，系统自动上标），2.用户上架，支付中（续购），3.用户上架，已支付（续购）
		int status = 0;
		ProjectExample example = new ProjectExample();
		example.createCriteria()
				.andParentIdEqualTo(parentId);
		List<Project> projects = projectMapper.selectByExample(example);
		if (!projects.isEmpty()) {
			if (projects.size() == 2) {
				// 后台上架
				status = 1;
			}
			Project project = projects.get(0);
			if (!project.getBuyAgain()) { // 后台重新生成新的项目（0待上架1上架2待付款3已出售）
				status = 1;
			} else { // 用户操作续购（2待付款（支付中）3已出售（已支付））
				InvestmentExample investmentExample = new InvestmentExample();
				investmentExample.setOrderByClause(" id desc ");
				investmentExample.setLimitStart(0);
				investmentExample.setLimitEnd(1);
				investmentExample.createCriteria()
						.andProjectIdEqualTo(project.getId())
						.andUserIdEqualTo(userId);
				// 查询 未饲养（待支付）投资订单
				List<Investment> investments = investmentMapper.selectByExample(investmentExample);
				if (!investments.isEmpty()) {
					if (investments.get(0).getPayStatus() == InvestPayStateEnum.PAYING.getCode()) {
						status = 2; // 支付中
					} else if (investments.get(0).getPayStatus() == InvestPayStateEnum.PAYED.getCode()) {
						status = 3; // 支付中
					}
				}
			}
		}
		return status;
	}

	@Override
	public Recharge getBuyAgainRecharge(Integer projectId, Integer userId) {
		InvestmentExample investmentExample = new InvestmentExample();
		investmentExample.setOrderByClause(" id desc ");
		investmentExample.setLimitStart(0);
		investmentExample.setLimitEnd(1);
		investmentExample.createCriteria()
				.andProjectIdEqualTo(projectId)
				.andUserIdEqualTo(userId);
		// 查询 未饲养（待支付）投资订单
		List<Investment> investments = investmentMapper.selectByExample(investmentExample);
		if (!investments.isEmpty()) {
			return this.getBuyAgainInvestmentRecharge(investments.get(0).getId(), investments.get(0).getUserId());
		}
		return null;
	}

	private Recharge getBuyAgainInvestmentRecharge(Integer investId, Integer userId) {
		RechargeExample rechargeExample = new RechargeExample();
		rechargeExample.setOrderByClause(" id desc ");
		rechargeExample.setLimitStart(0);
		rechargeExample.setLimitEnd(1);
		rechargeExample.createCriteria()
				.andOtherIdEqualTo(investId)
				.andOrderTypeEqualTo(BusinessTableEnum.investment.name())
				.andUserIdEqualTo(userId)
				.andStatusIn(Arrays.asList(0, 1)); // 支付中 or 已支付
		// 查询 支付中 的支付单
		List<Recharge> recharges = rechargeService.getMapper().selectByExample(rechargeExample);
		if (!recharges.isEmpty()) { // 存在支付订单
			return recharges.get(0);
		}
		return null;
	}

	/**
	 * 检查是否存在 已取消/未饲养 的投资项目，并标记删除
	*/
	@Override
	public boolean doCheckBuyAgainProject(Integer parentId) throws Exception {
		if (parentId == null) {
			return false;
		}
		boolean flag = false;
		ProjectExample example = new ProjectExample();
		example.createCriteria()
				.andParentIdEqualTo(parentId)
//				.andStatusIn(Arrays.asList(ProjectStatusEnum.PAYING.getCode(), ProjectStatusEnum.SALED.getCode()))
				.andBuyAgainEqualTo(true)
				.andStatusNotEqualTo(ProjectStatusEnum.DELETE.getCode());
		// 查询 非标记删除 的续购项目
		List<Project> projects = projectMapper.selectByExample(example);
		if (!projects.isEmpty()) {
			if (projects.size() != 1) {
				throw new Exception("数据异常");
			}
			Project buyAgainProject = projects.get(0);
			InvestmentExample investmentExample = new InvestmentExample();
			investmentExample.setOrderByClause(" id desc ");
			investmentExample.setLimitStart(0);
			investmentExample.setLimitEnd(1);
			investmentExample.createCriteria()
					.andProjectIdEqualTo(buyAgainProject.getId());
//					.andOrderStatusIn(Arrays.asList(InvestmentStateEnum.no_buy.getCode(), InvestmentStateEnum.buyed.getCode()))
//					.andPayStatusIn(Arrays.asList(InvestPayStateEnum.PAYING.getCode(), InvestPayStateEnum.PAYED.getCode()));
			// 查询 支付中/已支付 投资订单
			// 查询此项目最新的投资订单
			List<Investment> investments = investmentMapper.selectByExample(investmentExample);
			if (!investments.isEmpty()) {
				Investment investment = investments.get(0);
				if (investment.getPayStatus() == InvestPayStateEnum.PAYING.getCode() || investment.getPayStatus() == InvestPayStateEnum.PAYED.getCode()) {
//					throw new Exception("用户正在续购支付中或已支付，不允许重新上标");
					flag = true;
				} else {
					// 标记删除续购项目
					buyAgainProject.setStatus(ProjectStatusEnum.DELETE.getCode());
					buyAgainProject.setUpdateDate(new Date());
					this.updateAndLock(buyAgainProject);
					flag = false;
				}
			}
		}
		return flag;
	}

	/**
	 * 查询是否可被续购
	 * @param earNumber
	 * @return
	 */
	@Override
	public Boolean queryExistSalingProjectByEarNumber(String earNumber) {
		Boolean flag = true;
		List<Project> projectList = new ArrayList<>();
		ProjectExample example = new ProjectExample();
		ProjectExample.Criteria criteria = example.createCriteria();
		criteria.andEarNumberEqualTo(earNumber);
		projectList = this.getMapper().selectByExample(example);

		// 耳标号有支付中或者上架中的牛只,牛只被后台管理员上架
		//最后一笔项目信息被续养的订单或者被续养在待付款状态不能被续养
		if(projectList != null && projectList.size() > 0) {
			//牛只存在支付中和上架状态
			Project project = projectList.get(projectList.size()-1);//取最后一笔项目信息
			if(project.getStatus() == ProjectStatusEnum.PAYING.getCode()
					|| project.getStatus() == ProjectStatusEnum.ENABLE_SALE.getCode()) {
				flag = false;
			} else if (project.getBuyAgain() && project.getStatus() == ProjectStatusEnum.SALED.getCode()){ //已续购
				flag = false;
			}
		}

		return flag;
	}

	@Override
	public int sumTotalLimitDayByEarNumber(String earNumber) {
		return projectMapper.sumTotalLimitDayByEarNumber(earNumber);
	}

	@Override
	public BigDecimal statisticsFilialeSell(Integer empId, String keyword, String recommendKeyword, Date startTime,
			Date endTime) {
		return projectMapper.statisticsFilialeSell(empId,keyword, recommendKeyword, startTime, endTime);
	}


	private List<ProjectIndexVO> listGroupByLimitDaysBase(int limit, String orderByClause, boolean isHome) {
		ProjectExample example = new ProjectExample();
		example.setLimitEnd(limit);
		if (limit <= 0) {
			example.setLimitEnd(4);
		}
		if (StringUtils.isBlank(orderByClause)) {
			example.setOrderByClause(" total_amount asc, create_date desc ");
		}
		example.createCriteria()
				.andStatusEqualTo(ProjectStatusEnum.ENABLE_SALE.getCode())
				.andProjectTypeEqualTo(ProjectTypeEnum.YANGNIU.getFeatureType());
		List<ProjectIndexVO> result = new ArrayList<>();
		List<Project> projects = projectMapper.listGroupByLimitDays(example);
		for (Project project : projects) {
			result.add(this.convertProjectIndexVO(project, isHome));
		}
		return result;
	}

	@Override
	public ProjectIndexVO convertProjectIndexVO(Project project, boolean isHome) {
		ProjectIndexVO vo = new ProjectIndexVO();
		BigDecimal interestAmount = PayUtil.getInterestAmount(project, true);
		vo.setAnnualizedStr(interestAmount + "元");
		vo.setIncreaseAnnualizedStr(BigDecimalUtil.multi(project.getIncreaseAnnualized(), 100) + "%");
		vo.setLimitDay(project.getLimitDays());
		if (project.getRepayUnit().equals(RepayUnitEnum.MONTH.getFeatureName())) {
			vo.setLimitDayStr(project.getLimitDays() + RepayUnitEnum.MONTH.getDescription());
		} else if (project.getRepayUnit().equals(RepayUnitEnum.DAY.getFeatureName())) {
			vo.setLimitDayStr(project.getLimitDays() + RepayUnitEnum.DAY.getDescription());
		}
		vo.setProductType(project.getProductId());
		vo.setId(project.getId());
		vo.setNoob(project.getNoob());
		vo.setSex(project.getSex());
		vo.setTitle(project.getTitle());
		vo.setTotalAmount(project.getTotalAmount());
		String littleImagePath = "";
		if (StringUtils.isBlank(project.getLittleImage())) {
			littleImagePath = this.getProjectsmallImagePath("1", vo.getId(), false);
		} else {
			littleImagePath = this.getProjectsmallImagePath(project.getLittleImage(), false);
		}
		vo.setLittleImagePath(littleImagePath);
		vo.setEarNumber(project.getEarNumber());
		vo.setProjectType(project.getProjectType());
		vo.setInsuranceImagePath(ClientConstants.ALIBABA_PATH + "images/newyear/insurance.png");
		if (isHome) {
			if (vo.getNoob() == 1) {
				vo.setNoobImagePath(ClientConstants.ALIBABA_PATH + "images/newyear/first_buy_sm.png");
			}
			if ("1".equals(project.getSex())) { // 母
				vo.setSexImagePath(ClientConstants.ALIBABA_PATH + "images/newyear/she_sm.png");
			} else {
				vo.setSexImagePath(ClientConstants.ALIBABA_PATH + "images/newyear/he_sm.png");
			}
		} else {
			if (vo.getNoob() == 1) {
				vo.setNoobImagePath(ClientConstants.ALIBABA_PATH + "images/newyear/first_buy_bg.png");
			}
			if ("1".equals(project.getSex())) { // 母
				vo.setSexImagePath(ClientConstants.ALIBABA_PATH + "images/newyear/she_bg.png");
			} else {
				vo.setSexImagePath(ClientConstants.ALIBABA_PATH + "images/newyear/he_bg.png");
			}
		}
		return vo;
	}

	public ProjectIndexVO convertProjectIndexVO(ProjectView projectView, boolean isHome) {
		Project project = new Project();
		BeanUtils.copyProperties(projectView, project);
		ProjectIndexVO vo = this.convertProjectIndexVO(project, isHome);
		if (projectView.getProjectType() == ProjectTypeEnum.PINNIU.getFeatureType()) { // 拼牛
			BigDecimal interestAmount = PayUtil.getPinniuInterestAmount(project, projectView.getPinTotalPoint(), true);
			vo.setAnnualizedStr(interestAmount + "元");
		}
		vo.setInvestedPoint(projectView.getPinInvestedPoint());
		vo.setTotalPoint(projectView.getPinTotalPoint());
		return vo;
	}

	@Override
	public List<ProjectIndexVO> listGroupByLimitDays(int limit) {
		return this.listGroupByLimitDaysBase(limit, null, true);
	}

	@Override
	public List<Map<String, Object>> listGroupByLimitDays(int limit, String orderByClause) {
		List<Map<String, Object>> result = new ArrayList<>();
		Map<String, Object> limitNoob = new HashMap<>();
		limitNoob.put("title", "新手牛专区");
		limitNoob.put("key", "noob");
		JSONArray noobData = new JSONArray();
		Map<String, Object> limit30 = new HashMap<>();
		limit30.put("title", "30天牛只专区");
		limit30.put("key", "30");
		JSONArray limit30Data = new JSONArray();
		Map<String, Object> limit60 = new HashMap<>();
		limit60.put("title", "60天牛只专区");
		limit60.put("key", "60");
		JSONArray limit60Data = new JSONArray();
		Map<String, Object> limit90 = new HashMap<>();
		limit90.put("title", "90天牛只专区");
		limit90.put("key", "90");
		JSONArray limit90Data = new JSONArray();
		Map<String, Object> limit180 = new HashMap<>();
		limit180.put("title", "180天牛只专区");
		limit180.put("key", "180");
		JSONArray limit180Data = new JSONArray();
		Map<String, Object> limit360 = new HashMap<>();
		limit360.put("title", "360天牛只专区");
		limit360.put("key", "360");
		JSONArray limit360Data = new JSONArray();

		List<ProjectIndexVO> list = this.listGroupByLimitDaysBase(limit, orderByClause, false);
		list.forEach(vo -> {
			if (vo.getNoob() == 1) {
				noobData.add(vo);
			} else {
				switch (vo.getLimitDay()) {
					case 30:
						limit30Data.add(vo);
						break;
					case 60:
						limit60Data.add(vo);
						break;
					case 90:
						limit90Data.add(vo);
						break;
					case 180:
						limit180Data.add(vo);
						break;
					case 360:
						limit360Data.add(vo);
						break;
					default:
						break;
				}
			}
		});
		if (!noobData.isEmpty()) {
			limitNoob.put("data", noobData);
			result.add(limitNoob);
		}
		if (!limit30Data.isEmpty()) {
			limit30.put("data", limit30Data);
			result.add(limit30);
		}
		if (!limit60Data.isEmpty()) {
			limit60.put("data", limit60Data);
			result.add(limit60);
		}
		if (!limit90Data.isEmpty()) {
			limit90.put("data", limit90Data);
			result.add(limit90);
		}
		if (!limit180Data.isEmpty()) {
			limit180.put("data", limit180Data);
			result.add(limit180);
		}
		if (!limit360Data.isEmpty()) {
			limit360.put("data", limit360Data);
			result.add(limit360);
		}
		return result;
	}

	private String getProjectsmallImagePath(String littleImage, boolean retainWatermark) {
		if (StringUtils.isBlank(littleImage)) {
			return null;
		}
		String prefix = retainWatermark ? "projectrenbao" : "project";
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("upload.properties");
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e) {
			logger.error(e);
		}
		return getProjectsmallImagePathResult(p, littleImage, retainWatermark);
	}
	
	private String getProjectsmallImagePathResult(Properties p, String littleImage, boolean retainWatermark) {
		String path = "";
		if (retainWatermark) {
			path = (ClientConstants.ALIBABA_PATH + "upload/" + littleImage)
					.replaceAll("/"+p.getProperty(ConstantsAdmin.PROJECT_PICTURE_PATH,"project")+"/",
							"/"+p.getProperty(ConstantsAdmin.PROJECT_PICTURE_RENBAO_PATH, "projectrenbao")+"/");
		} else {
			path = (ClientConstants.ALIBABA_PATH + "upload/" + littleImage);
		}
		return path;
	}

	@Override
	public List<ProjectIndexVO> listPingniu(int limit) {
		List<ProjectIndexVO> result = new ArrayList<>();
		List<ProjectView> projects = listPinniuBase(limit);
		
		for (ProjectView projectView : projects) {
			result.add(this.convertProjectIndexVO(projectView, true));
		}
		/*
		// 过滤id
		List<Integer> filterIds = projects.stream().map(item -> item.getId()).collect(Collectors.toList());
		for (ProjectView projectView : projects) {
			if (projectView.getInvestedAmount() >= projectView.getTotalAmount()) {
				// 十分已经全被占用，并且状态为代付款，最新拼牛信息
				List<ProjectView> newProjects = listPinniuBase(1, filterIds);
				if (!newProjects.isEmpty()) {
					result.add(this.convertProjectIndexVO(newProjects.get(0), true));
				}
			} else {
				result.add(this.convertProjectIndexVO(projectView, true));
			}
		}*/
		return result;
	}
	
	private List<ProjectView> listPinniuBase(int limit) {
		ProjectViewExample example = new ProjectViewExample();
		example.setLimitStart(0);
		example.setLimitEnd(limit);
		example.setOrderByClause(" (total_amount-invested_amount)/total_amount asc, create_date asc ");

		return projectViewMapper.selectPinNiuListForHome(example);
	}

    @Override
    public WeatherVO getWeatherInfo() {
        WeatherVO weatherVO = null;
        Object cache = redisService.get("weatherInfo");
        if(cache != null) {
            weatherVO = JSON.parseObject((String)cache, WeatherVO.class);
        } else {
            //获取天气数据
            String weatherContent = CommonUtil.getURLContent(Constants.WEATHER_URL);
            weatherContent = weatherContent.substring(weatherContent.indexOf("(") + 1, weatherContent.lastIndexOf(")"));
            JSONObject jsonWeather = (JSONObject) JSONObject.parse(weatherContent);
            jsonWeather = (JSONObject)jsonWeather.getJSONObject("data").getJSONObject("forecast_1h").getJSONObject("1");
            //空气质量
            String airContent = CommonUtil.getURLContent(Constants.AIR_QUALITY_URL);
            airContent = airContent.substring(airContent.indexOf("(") + 1, airContent.lastIndexOf(")"));
            JSONObject jsonAir = (JSONObject) JSONObject.parse(airContent);
            jsonAir = (JSONObject)jsonAir.getJSONObject("data").getJSONObject("air");

            String degree = (String)jsonWeather.get("degree"); //温度
            String weatherCode = (String)jsonWeather.get("weather_code"); //天气code
            String weather = (String)jsonWeather.get("weather"); //天气
            String weatherPicUrl = String.format(Constants.WEATHER_PICTURE_URL, weatherCode); //天气小图
            int aqiLevel = (int)jsonAir.get("aqi_level"); //空气等级
            String aqiName = (String)jsonAir.get("aqi_name"); //空气质量

            weatherVO = new WeatherVO();
            weatherVO.setDegree(degree);
            weatherVO.setWeatherCode(weatherCode);
            weatherVO.setWeather(weather);
            weatherVO.setWeatherPicUrl(weatherPicUrl);
            weatherVO.setAqiLevel(aqiLevel);
            weatherVO.setAqiName(aqiName);

            redisService.set("weatherInfo", JSON.toJSONString(weatherVO), 60*60*1);
        }

        return weatherVO;
    }

}
