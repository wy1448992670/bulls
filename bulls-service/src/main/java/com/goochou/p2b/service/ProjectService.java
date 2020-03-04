package com.goochou.p2b.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.goochou.p2b.constant.ProjectDaysEnum;
import com.goochou.p2b.model.*;
import com.goochou.p2b.model.vo.*;
import com.goochou.p2b.model.vo.bulls.BuyBullsDetailMoneyVO;
import com.goochou.p2b.model.vo.bulls.EnableKeepPeriodVO;
import com.goochou.p2b.model.vo.bulls.KeepPeriodVO;
import org.springframework.web.multipart.MultipartFile;

import com.goochou.p2b.dao.ProjectMapper;
import com.goochou.p2b.model.AppVersionContent;
import com.goochou.p2b.model.AppVersionContentWithBLOBs;
import com.goochou.p2b.model.Investment;
import com.goochou.p2b.model.Project;
import com.goochou.p2b.model.ProjectContract;
import com.goochou.p2b.model.ProjectContractWithBLOBs;
import com.goochou.p2b.model.ProjectCreditor;
import com.goochou.p2b.model.ProjectExample;
import com.goochou.p2b.model.ProjectLifePicture;
import com.goochou.p2b.model.Prompt;
import com.goochou.p2b.model.vo.p2peye.P2pEyeInvestData;
import com.goochou.p2b.model.vo.p2peye.P2pEyeLoansData;

public interface ProjectService {

	ProjectMapper getMapper();


    boolean isShowBulls(User user, String appName, String appChannel, String client);

	/**
	 *
	 * @param appChannel android市场编号
	 * @return
	 */
	boolean isHidden(User user, String appChannel);

	boolean isHidden(User user, String appChannel,boolean isbalance);

	/**
	 * 项目根据关键词分页查询
	 *
	 * @param keyword
	 * @param start
	 * @param limit
	 * @return 记录总数 count，结果list
	 */
	public List<Project> query(String keyword, Integer status, int start, int limit, String orderBy, int sort)
			throws Exception;

	public List<Project> query(String keyword, Integer status, Integer projectType, int start, int limit,
			String orderBy, int sort);

	public List<Project> queryList(String keyword, Integer status, Integer projectType, Integer start, Integer limit,
			String orderBy, String sort) throws Exception;

	public Integer queryListCount(String keyword, Integer status, Integer projectType, Integer noob);

	public Integer queryCount(String keyword, Integer status);

	public List<Project> queryList(String keyword, Integer status, Integer projectType, Integer noob, Integer limitDays,
			Integer start, Integer limit, String orderBy, String sort, String lendBeginTimeStartTime,
			String lendBeginTimeEndTime) throws Exception;

	/**
	 * 非后台项目查询
	 *
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Project> queryIndex(Integer status, int start, int limit, Integer projectType);

	public Integer queryIndexCount();

	public Integer queryBondIndexCount();

	/**
	 * 根据项目ID查询项目的投资人ID的list
	 *
	 * @param projectId
	 * @return
	 */
	public List<Integer> getAllInvestors(Integer projectId);

	/**
	 * 非后台债券查询
	 *
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Project> queryBondIndex(int start, int limit);

	/**
	 * 查询项目总数
	 *
	 * @return
	 */
	public Integer count();

	public Integer queryCount(String keyword, Integer status, Integer projectType);

	/**
	 * 债券分页查询
	 *
	 * @param keyword
	 * @param status
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<Project> queryBond(String keyword, Integer status, int start, int limit, String orderBy, int sort,
			Date startTime, Date endTime) throws Exception;

	public Integer queryBondCount(String keyword, Integer status, Date startTime, Date endTime);

	public Project detailBond(Integer id);

	public Project detail(Integer id);

	public Map<String, Object> detailSafe(Integer id, Integer projectType);

	public Project detailNew(Integer id, Integer projectType);

	public Project detailNewPC(Integer id, Integer projectType);

	public Project showDetail(Integer id);

	/**
	 * 项目审核
	 *
	 * @param project
	 * @throws Exception
	 */
	public void update(Project project) throws Exception;

	public void updateAndLock(Project project) throws Exception;

	/**
	 * 验证项目名称是否存在
	 *
	 * @param title
	 * @return true 不存在, false存在
	 * @throws Exception
	 */
	public boolean checkNameExists(String title, Integer id) throws Exception;

	/**
	 * 保存项目同时更新图片关联
	 */
	public void saveWithPicture(Project project, List<String> pictures, String[] productPropertyIdArray,
			String[] propertyValueArray) throws Exception;

	/**
	 * 编辑项目信息
	 */
	public void update(Project project, String picture, String picture2, String[] productPropertyIdArray,
			String[] propertyValueArray) throws Exception;

	public List<Map<String, Object>> getInvestors(Integer projectId, Integer status, Integer start, Integer limit);

	public int getInvestorsCount(Integer projectId, Integer status);

	public Project get(Integer id);

	public Project getProjectByProductId(Integer productId);

	/**
	 * 根据用户查询可转让的债权列表
	 *
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> getUsableBondList(Integer userId, Integer start, Integer limit);

	Integer getUsableBondListCount(Integer userId);

	/**
	 * 根据用户ID查询已转让的项目总数量和总金额
	 *
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getTransferedProjectByUserId(Integer userId);

	public List<Map<String, Object>> getAssignBondList(Integer userId, Integer status, Integer start, Integer limit);

	public int getAssignBondCount(Integer userId, Integer status);

	public List<Map<String, Object>> getPurchasedBondList(Integer userId, Boolean hasDividended, Integer start,
			Integer limit);

	public int getPurchasedBondCount(Integer userId);

	/**
	 * 投资截至时间到了的项目修改状态为投资成功 2-->3
	 *
	 * @return
	 */
	public void updateDeadlinedProjects();

	/**
	 * 项目投资完成后一天修改状态为还款中 3-->4
	 *
	 * @return
	 */
	public void updateInvestCompletedProjects();

	/**
	 * 更新还款时间倒了的项目为回款成功4-->5
	 *
	 * @return
	 */
	public void updateOverProjects();

	/**
	 * 债券到期的从0
	 *
	 * @return
	 */
	public void updateDeadlinedBonds() throws Exception;

	public List<Project> queryIndexByApp(int start, int limit);

	public int saveByApp();

	public Project getNewProjectByApp();

	public void updateStartInvestmentProjects();

	public Integer getAllProjectCountByApp();

	public void saveByApp(Project project);

	public void saveByApp(Project project, String picture2);

	/**
	 * @return
	 * @Title: ProjectService.java
	 * @Package com.goochou.p2b.service
	 * @Description(描述):查询最近最新的一个新手标项目。 @author 王信
	 * @date 2016年2月14日 上午11:56:36
	 * @version V1.0
	 */
	public Project selectNoobProject();

	/**
	 * level 0 普通用户 1VIP用户
	 */
	public Project selectRegularProject(int level);

	/**
	 * @param project_id
	 * @return
	 * @Title: ProjectService.java
	 * @Package com.goochou.p2b.service
	 * @Description(描述):根据项目id查询有多少人投资
	 * @author 王信
	 * @date 2016年2月14日 下午4:11:14
	 * @version V1.0
	 */
	public Integer selectInvestment(Integer project_id);

	/**
	 * 根据关键字债权列表
	 *
	 * @param status
	 * @param limit
	 * @param start
	 * @return
	 * @author 刘源
	 * @date 2016-2-2
	 */
	public List<Map<String, Object>> queryCreditor(String keyword, String status, Integer start, Integer limit);

	/**
	 * 查询债权列表总数
	 *
	 * @param keyword
	 * @param status
	 * @return
	 * @author 刘源
	 * @date 2016-2-16
	 */
	public Integer queryCreditorCount(String keyword, String status);

	/**
	 * @return
	 * @Title: ProjectService.java
	 * @Package com.goochou.p2b.service
	 * @Description(描述):查询所有有效的定期投资项目列表regular
	 * @author 王信
	 * @date 2016年2月16日 下午3:28:14
	 * @version V1.0
	 */
	public List<Project> selectRegularList();

	/**
	 * @return
	 * @Title: ProjectService.java
	 * @Package com.goochou.p2b.service
	 * @Description(描述):当定期项目不够添加至三个
	 * @author 王信
	 * @date 2016年3月1日 下午7:17:49
	 * @version V1.0
	 */
	public List<Project> selectRegularStatusList();

	/**
	 * @return
	 * @Title: ProjectMapper.java
	 * @Package com.goochou.p2b.dao
	 * @Description(描述):查询所有有效的定期投资项目列表regular 记录条数
	 * @author 王信
	 * @date 2016年2月16日 下午3:37:25
	 * @version V1.0
	 */
	public Integer selectRegularCount();

	/**
	 * 查询债权列表
	 *
	 * @param start
	 * @param limit
	 * @return
	 * @author 刘源
	 */
	public List<Map<String, Object>> queryAllCreditorList(Integer userId, Integer flag, Integer start, Integer limit,
			Integer projectId);

	/**
	 * 债权列表总数
	 *
	 * @return
	 * @author 刘源
	 * @date 2016-2-16
	 */
	public Integer queryAllCreditorListCount();

	/**
	 * 根据ID获取债权详情
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年2月16日
	 * @parameter
	 */
	public Map<String, Object> queryCreditorDetail(Integer userId, Integer investmentId);

	/**
	 * @return
	 * @author 刘源
	 * @date 2016年2月26日
	 * @parameter
	 */
	public Map<String, Object> queryProjectCreditorDetail(Integer id, Integer projectId);

	/**
	 * @param title
	 * @param context
	 * @return
	 * @Title: ProjectService.java
	 * @Package com.goochou.p2b.service
	 * @Description(描述):新增温馨提示条目
	 * @author 王信
	 * @date 2016年2月29日 下午4:10:02
	 * @version V1.0
	 */
	public Integer saveAddPrompt(String title, String[] context);

	/**
	 * @return
	 * @Title: ProjectService.java
	 * @Package com.goochou.p2b.service
	 * @Description(描述):查询所有的温馨提示详细信息
	 * @author 王信
	 * @date 2016年2月29日 下午4:10:58
	 * @version V1.0
	 */
	public List<Map<String, Object>> selectPromptList(String keyword, Integer start, Integer limit);

	public Integer selectPromptCount(String keyword);

	/**
	 * @param title
	 * @return
	 * @Title: ProjectService.java
	 * @Package com.goochou.p2b.service
	 * @Description(描述):根据标题查询所有的条目内容
	 * @author 王信
	 * @date 2016年2月29日 下午5:52:25
	 * @version V1.0
	 */
	public List<String> selectTitlePrompt(String title);

	/**
	 * @param id
	 * @return
	 * @Title: ProjectService.java
	 * @Package com.goochou.p2b.service
	 * @Description(描述):根据标题查询
	 * @author 王信
	 * @date 2016年2月29日 下午5:05:28
	 * @version V1.0
	 */
	public Prompt selectKeyPrompt(Integer id);

	/**
	 * @param prompt
	 * @Title: ProjectService.java
	 * @Package com.goochou.p2b.service
	 * @Description(描述):更新条目内容
	 * @author 王信
	 * @date 2016年2月29日 下午5:19:48
	 * @version V1.0
	 */
	public void updatePrompt(Prompt prompt);

	/**
	 * @Title: ProjectService.java
	 * @Package com.goochou.p2b.service
	 * @Description(描述):删除条目内容
	 * @author 王信
	 * @date 2016年2月29日 下午5:19:48
	 * @version V1.0
	 */
	public void deletePrompt(Integer id);

	/**
	 * 查询债权详情以及合同列表
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月8日
	 * @parameter
	 */
	public List<Map<String, Object>> queryContractList(Integer type, String keyword, Integer start, Integer limit);

	/**
	 * 查询债权详情以及合同列表总数
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月8日
	 * @parameter
	 */
	public int queryContractListCount(Integer type, String keyword);

	/**
	 * 查询债权或项目详情
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月8日
	 * @parameter
	 */
	public ProjectContract queryContractDetail(Integer id);

	/**
	 * 查询合同组标题列表
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月8日
	 * @parameter
	 */
	public List<ProjectContract> getContractTitleList();

	/**
	 * 保存或插入详情或合同内容
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月8日
	 * @parameter
	 */
	public void saveContractInfo(ProjectContractWithBLOBs group);

	/**
	 * @param userId
	 * @param start
	 * @param limit
	 * @return
	 * @Title: ProjectService.java
	 * @Package com.goochou.p2b.service
	 * @Description(描述):查询所有自己购买的定期项目和债权
	 * @author 王信
	 * @date 2016年3月8日 下午5:39:59
	 * @version V1.0
	 */
	public List<Map<String, Object>> queryCreditorAssignmentAssets(Integer userId, Integer start, Integer limit);

	public Integer queryCreditorAssignmentAssetsCount(Integer userId);

	/**
	 * @param investID
	 * @return
	 * @Title: ProjectService.java
	 * @Package com.goochou.p2b.service
	 * @Description(描述):根据投资id 查询对应的购买的定期项目和债权
	 * @author 王信
	 * @date 2016年3月9日 下午1:51:47
	 * @version V1.0
	 */
	public Map<String, Object> queryCreditorAssignmentAssetsDetail(Integer investID, Integer userId);

	/**
	 * 查询债权详情或合同信息（网页内容）
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月9日
	 * @parameter
	 */
	public ProjectContractWithBLOBs queryCreditorContentDetail(Integer projectId);

	/**
	 * 查询用户债权合同数据详情
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月9日
	 * @parameter
	 */
	public Map<String, Object> queryCreditorContractData(Integer userId, Integer investmentId);

	/**
	 * 获取可用于配置债权的定期项目项
	 *
	 * @param id
	 * @return
	 * @author 刘源
	 * @date 2016年3月9日
	 * @parameter
	 */
	public List<Project> getProjectTitleOption(Integer id);

	/**
	 * 查询配置项目质押信息的项目列表
	 */
	public List<Project> getProjectAccount(Integer id);

	/**
	 * 保存债权配置详情
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月9日
	 * @parameter
	 */
	public void saveCreditorDetail(ProjectCreditor record);

	/**
	 * 校验项目是否配置债权
	 *
	 * @param projectId
	 * @return
	 * @author 刘源
	 * @date 2016年3月9日
	 * @parameter
	 */
	public Boolean checkProjectHasCreditor(Integer projectId);

	/**
	 * @Description: 查看配置好的债权 id 债权的id
	 * @date 2016/11/8
	 * @author 王信
	 */
	public Map<String, Object> queryProjectCreditorConfig(Integer id);

	/**
	 * @Description: 查看配置好的债权 id 项目id
	 * @date 2016/11/8
	 * @author 王信
	 */
	public Map<String, Object> queryProjectConfig(Integer id);

	/**
	 * 删除项目债权配置
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月9日
	 * @parameter
	 */
	public void deleteCreditorConfig(Integer id);

	/**
	 * 删除项目债权内容
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月9日
	 * @parameter
	 */
	public void deleteContract(Integer id);

	/**
	 * 删除债权内容模板配置前检测
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月9日
	 * @parameter
	 */
	public Boolean checkContractDelete(Integer id);

	/**
	 * 根据title查询配置的带格式提示内容
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月11日
	 * @parameter
	 */
	public ProjectContractWithBLOBs queryByTitle(String title);

	/**
	 * 查询带格式的债权保障信息
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月11日
	 * @parameter
	 */
	public ProjectContractWithBLOBs queryProjectContractWithBLOBs(Integer id);

	/**
	 * @param userId
	 * @return
	 * @Title: ProjectService.java
	 * @Package com.goochou.p2b.service
	 * @Description(描述):查询是否是自己的债权，如果是，则不能购买。 @author 王信
	 * @date 2016年3月14日 上午11:38:51
	 * @version V1.0
	 */
	public Integer selectUserInvestment(Integer userId, Integer projectId);

	/**
	 * 查询定期项目配置的债权
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月16日
	 * @parameter
	 */
	public ProjectCreditor queryCreditorDeailByProjectId(Integer projectId);

	/**
	 * 根据title查询提示
	 *
	 * @return
	 * @author 刘源
	 * @date 2016年3月16日
	 * @parameter
	 */
	public List<Prompt> queryByTitlePrompt(String title);

	/**
	 * @Description(描述):版本控制管理集合
	 * @author 王信
	 * @date 2016/4/12
	 * @params 模糊关键字查询
	 **/
	public List<AppVersionContent> queryAppVersionContentList(String keyword, Integer start, Integer limit);

	public Integer queryAppVersionContentCount(String keyword);

	/**
	 * @Description(描述):新增版本管理
	 * @author 王信
	 * @date 2016/4/12
	 * @params
	 **/
	public void saveAppVersionContent(AppVersionContentWithBLOBs group);

	/**
	 * @Description(描述):根据版本管理id查询对应的版本管理内容
	 * @author 王信
	 * @date 2016/4/12
	 * @params
	 **/
	public AppVersionContent selectAppVersionContentKey(Integer id);

	/**
	 * @Description(描述):根据id删除对应的安卓更新版本
	 * @author 王信
	 * @date 2016/4/12
	 * @params
	 **/
	public void deleteAppVersionContentKey(Integer id);

	/**
	 * @Description(描述):3.0.3以上 定期项目列表
	 * @author 王信
	 * @date 2016/5/18
	 * @params
	 **/
	public List<Project> selectRegularProjectList(Integer page, Integer limit) throws Exception;

	public Integer selectRegularProjectCount();

	public List<Project> selectRegularProjectTunnelList();

	public List<Project> selectRegularProjectListold(Integer page, Integer limit) throws Exception;

	public Integer selectRegularProjectCountold();

	/**
	 * @Description(描述):查询债权转让列表
	 * @author 王信
	 * @date 2016/5/27
	 * @params
	 **/
	List<Map<String, Object>> selectCreditorList(String keyword, Integer status, Integer start, Integer limit);

	Integer selectCreditorCount(String keyword, Integer status);

	/**
	 * 获取有效定期项目
	 *
	 * @param params
	 * @author 刘源
	 * @date 2016/6/24
	 */
	List<Project> getRegularProject(Map<String, Object> params);

	/**
	 * @Description(描述):导出回款计划 按时间
	 * @author 王信
	 * @date 2016/8/19
	 * @params
	 **/
	List<Map<String, Object>> selectReportList(String keyword, Date startTime, Date endTime);

	public List<Map<String, Object>> queryBond2(String keyword, Integer status, int start, int limit, Date startTime,
			Date endTime);

	/**
	 * @Description(描述): 我的资产 活期-查看债权
	 * 
	 * @author 王信
	 * @date 2016/9/8
	 * @params
	 **/
	List<Map<String, Object>> queryCurrentCreditorList(Integer userId, Integer start, Integer limit);

	List<Map<String, Object>> queryCurrentCreditorList(Integer userId, Integer source, Integer start, Integer limit);

	Integer queryCurrentCreditorCount(Integer userId);

	Integer queryCurrentCreditorCount(Integer userId, Integer source);

	/**
	 * @Description(描述):活期基本信息
	 * @author 王信
	 * @date 2016/9/8
	 * @params
	 **/
	Project selectHuoBase(Integer id);

	/**
	 * @Description: 10月迭代，，新增活期项目
	 * @date 2016/10/11
	 * @author 王信
	 */
	void saveCurrentProject(Project project) throws Exception;

	void doLowerShelves(Integer id) throws Exception;

	/**
	 * 我的定期列表 status:0回款中 1已结束 orderBy:默认0投资时间倒序排列
	 * 1到期时间排序，如果到期时间一样，再按照投资时间排序，投资时间越早的排在下面 2投资金额排序，如果投资金额一样，再按照投资时间排序，投资时间越早的排在下面
	 * 3下期回款时间排序，如果下期回款时间一样，再按照投资时间排序，再按照投资时间排序，投资时间越早的排在下面
	 */
	public List<Map<String, Object>> listMyCurrent(Integer userId, Integer status, Integer orderBy, Date startDate,
			Date endDate, Integer start, Integer limit);

	public Integer listMyCurrentCount(Integer userId, Integer status, Integer orderBy, Date startDate, Date endDate);

	public Double getTransferAmountByInvestmentId(Integer investmentId);

	/**
	 * @Description(描述):活期基本信息
	 * @author zxx
	 * @date 2016/10/18
	 * @params
	 **/
	Project getProjectById(Integer id);

	/**
	 * @Description: 活期整改 定时器 查询今日 是否有开活期标的
	 * @date 2016/11/2
	 * @author 王信
	 */
	Integer selectTodayHuoProject();

	/**
	 * @Description: 项目成本
	 * @date 2016/11/24
	 * @author zxx
	 */
	public List<Map<String, Object>> projectCost(Integer start, Integer limit, String keyword, Integer status,
			Date startTime, Date endTime, Integer type, Integer limitDays);

	public Integer countProjectCost(String keyword, Integer status, Date startTime, Date endTime, Integer type,
			Integer limitDays);

	public List<Map<String, Object>> projectCost1(Integer start, Integer limit, String keyword, Integer status,
			Date startTime, Date endTime, Integer type, Integer limitDays);

	/**
	 * @Description: 项目列表导出
	 * @date 2017/3/1
	 * @author 王信
	 */
	List<Project> selectRegularReport(Integer status, String keyword, Integer noob);

	/**
	 * @Description: 查询该产品库下的所有可打包资产.
	 * @date 2017/2/17
	 * @author 王信
	 */
	List<Project> selectPackageList(Integer productId, Integer start, Integer limit);

	/**
	 * @Description: 根据资产包ID 查询资产包所属的所有项目.
	 * @date 2017/2/20
	 * @author 王信
	 */
	List<Project> selectByPackageList(Integer packageId);

	List<Project> queryCycleList(String keyword, String title, Integer status, Date startTime, Date endTime,
			Date startDate, Date endDate, Integer start, Integer limit, String title1);

	int queryCycleCount(String keyword, String title, Integer status, Date startTime, Date endTime, Date startDate,
			Date endDate, String title1);

	/**
	 * 投资产品 用户投资后，后台默认优先投资债权转让，无债权转让可投时再投资周期标。 债权转让：按照标的发放时间正序投资，越早上架的标的越先投资。
	 * 周期标：按照创建时间正序投资，越早创建的越先投资。
	 */
	public List<Project> getProjectListByProduct(Integer productId);

	/**
	 * @Description: 查询定期项目 年化区间,锁定区间,起投金额
	 * @date 2017/2/24
	 * @author 王信
	 */
	Map<String, Object> selectProjectLimit();

	/**
	 * @Description: 根据项目类型, 项目状态 ,以及产品ID 查询项目个数
	 * @date 2017/3/13
	 * @author 王信
	 */
	Integer selectByStatusAndProductIdCount(Integer projectType, Integer status, Integer productId);

	/**
	 * 查询前三条标的金额记录
	 *
	 * @param projectId 标id
	 * @return List
	 */
	List<Map<String, Object>> getThreeInvestors(Integer projectId);

	/**
	 * 查询需要自动发布的项目(按照项目周期分组, 查询在售数量为0, 并且已经创建的项目)
	 *
	 * @return
	 */
	List<Project> queryCanAutoReleaseProjects(Map<String, Object> map);

	/**
	 * 捞出哪些周期完了
	 *
	 * @param map
	 * @return
	 */
	List<Integer> querySellOutProjectCountByLimitDays(Map<String, Object> map);

	int updateProjectByProjectIds(Project project, Integer status);

	/**
	 * 根据项目周期更新排序字段
	 *
	 * @param map
	 * @return
	 */
	int updateProjectSortBylimitDays(Map<String, Object> map);

	Map<String, Object> getBestCoupon(Integer userId, Integer projectId, double defaultAmount);

	List<Project> selectHotProjectList(String param1, String param2, String param3);

	List<Map<String, Object>> selectRegularProjectLimitList();

	/**
	 * 定期2级列表
	 *
	 * @param page
	 * @param limit
	 * @param list
	 * @param sort
	 * @param limitDays
	 * @param noob
	 * @return
	 */
	List<Project> selectStatus2ProjectList(Integer page, Integer limit, List<Integer> list, String sort,
			Integer limitDays, Integer noob);

	Integer selectStatus2ProjectCount(List<Integer> list, Integer limitDays, Integer noob);

	Double getProjectClassTotalAmount(Integer limitDays, Integer noob);

	Integer queryProjectListCount(String isCreatedProject, Date startDate, Date endDate, String orderno);

	/**
	 * 
	 * <p>
	 * 修改项目
	 * </p>
	 * 
	 * @param project
	 * @author: lxfeng
	 * @date: Created on 2018-3-14 下午8:17:40
	 */
	public void updateProject(Project project) throws Exception;

	// pc改版

	/***
	 * 获取新手产品，定期产品，债权转让产品数量
	 * 
	 * @return
	 */
	Map<String, Integer> selectProjectCount();

	/***
	 * 查询符合条件的定期项目数量
	 * 
	 * @param searchMap
	 * @return
	 */
	Integer selectRegularlyProjectCount(Map<String, Object> searchMap);

	/***
	 * 查询符合条件的债权转让项目数量
	 * 
	 * @param searchMap
	 * @return
	 */
	Integer selectAssignmentOfDebtCount(Map<String, Object> searchMap);

	/***
	 * 查询符合条件的定期项目分页列表
	 * 
	 * @param searchMap
	 * @return
	 */
	List<Project> selectRegularlyProjectListPage(Map<String, Object> searchMap);

	/***
	 * 查询符合条件的债权转让项目分页列表
	 * 
	 * @param searchMap
	 * @return
	 */
	List<Map<String, Object>> selectAssignmentOfDebtListPage(Map<String, Object> searchMap);

	/**
	 * 获取债转年化利率
	 *
	 * @param income  预期收益
	 * @param amount  购买价格
	 * @param dayDiff 剩余期限
	 * @return
	 */
	Double getProjectAnnualizedMix(Object income, Object amount, Long dayDiff);

	List<Map<String, Object>> queryCreditorList(Map<String, Object> searchMap, String tagSplit);

	/**
	 * 
	 * <p>
	 * 根据投资期限查询剩余投资金额最多的标的
	 * </p>
	 * 
	 * @return
	 * @author: lxfeng
	 * @date: Created on 2018-4-20 下午4:51:39
	 */
	Project queryByLimitDays(int limitDays);

	List<Project> queryByContractId(String contractId);

	List<P2pEyeLoansData> queryP2pEyeLoansData(Map<String, Object> params);

	List<P2pEyeInvestData> queryP2pEyeInvestData(Map<String, Object> params);

	public List<BondPayVO> queryBondPayList(int projectId);

	Map<String, Object> getProjectPicture(Integer id);

	public List<OrderChangeVO> queryOrderChangeList(int projectId);

	List<Project> queryMonthlyGainProjectList(Map<String, Object> params);

	int updateProjectOfMonthlyGain(List<Integer> projectIdList, Integer packageId, Integer productId);

	/**
	 * 月月盈转让转普通转让
	 * 
	 * @param bondProject
	 * @return
	 */
	public boolean monthlyGainBondChangeToNormalBond(Project bondProject);

	public boolean updateWithReturn(Project project) throws Exception;

	public List<Project> getProjectByPackageId(Integer packageId);

	/**
	 * 查询月月盈项目投资的前3名数据
	 * 
	 * @param projectId
	 * @return
	 */
	public List<Map<String, Object>> getMonthlyGainThreeInvestors(Integer projectId);

	/**
	 * 查询月月盈资产标投资详细记录
	 * 
	 * @param projectId
	 * @param status
	 * @param start
	 * @param limit
	 * @return
	 */
	List<Map<String, Object>> getMonthlyGainInvestors(Integer projectId, Integer status, Integer start, Integer limit);

	/**
	 * 查询月月盈资产标投资详细记录总条数
	 * 
	 * @param projectId
	 * @return
	 */
	int getMonthlyGainInvestorsCount(Integer projectId);

	/***
	 * 新债权转让总数查询
	 * 
	 * @param searchMap
	 * @return
	 */
	public Integer queryNewAllCreditorListCount(Map<String, Object> searchMap);

	/**
	 * 批量生成债权
	 * 
	 * @param projectId
	 * @return
	 */
	boolean batchUpdateProject(String[] projectId);

	public List<Project> selectByExample(ProjectExample example);

	public boolean saveCannel(Project project) throws Exception;

	public Integer queryzzListCount(String keyword, String startAmount, String endAmount, Integer bondDayDiff,
			Integer status, Date startTime, Date endTime);

	List<Project> queryzzList(String keyword, String startAmount, String endAmount, Integer bondDayDiff, Integer status,
			Date startTime, Date endTime, Integer start, Integer limit);

	Map<String, Object> queryzzTotalAmount(String keyword, String startAmount, String endAmount, Integer bondDayDiff,
			Integer status, Date startTime, Date endTime);

	/***
	 * 智投可导入债转资产查询列表
	 * 
	 * @param searchMap
	 * @return
	 */
	List<Map<String, Object>> queryZtDebtProjectList(Map<String, Object> searchMap);

	/***
	 * 智投可导入债转资产查询总数
	 * 
	 * @param searchMap
	 * @return
	 */
	int queryZtDebtProjectCount(Map<String, Object> searchMap);


	/**
	 * 根据到期的投资订单，判断用户是否可以再续养
	 * @param investment
	 * @return true 可以续养 false 不可以
	 */
	boolean enableBuyAgain(Investment investment);


	/**
	 * 续养前牛只数据生成
	 * @param projectId
	 * @param projectDaysEnum
	 * @return 续养牛只的标的编号
	 */
	Integer doNewProjectWhenReFeed(Project project, ProjectDaysEnum projectDaysEnum) throws Exception;


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
	List<KeepPeriodVO> caculateBuyAgainCase(String sex, Integer age, Integer feedDays);

	/**
	 * 根据续养周期，计算饲养需要支付的资金
	 * @param project
	 * @param daysEnum
	 */
	BuyBullsDetailMoneyVO caculateBuyMoney(Project project, ProjectDaysEnum daysEnum);


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
	BuyBullsDetailMoneyVO caculateBuyMoney(String sex, BigDecimal weight,
												  BigDecimal unitAddWeighMoney,
												  BigDecimal unitFeedMoney,
												  BigDecimal unitBullPrice,
												  ProjectDaysEnum daysEnum,
										          BigDecimal annualized);


	List<Project> queryMonthlyGainProjectListWithoutOriginalProject(Map<String, Object> params);

	/**
	 * 根据id获取项目对象
	 * 
	 * @param projectId
	 * @return
	 * @author: zj
	 */
	Project getProjectById(int projectId);

	/**
	 * 查询项目投资记录
	 * 
	 * @这里用一句话描述这个方法的作用
	 * @param id
	 * @param start
	 * @param limit
	 * @return
	 * @author: zj
	 */
	List<Map<String, Object>> listInvestmentPage(Integer id, Integer start, Integer limit);

	/**
	 * 查询项目投资记录总数
	 * 
	 * @这里用一句话描述这个方法的作用
	 * @param id
	 * @param start
	 * @param limit
	 * @return
	 * @author: zj
	 */
	int countInvestmentPage(Integer id);

	/**
	 * 根据项目主键和图片类型获取项目的缩略图（支取一张）
	 * 
	 * @param type
	 * @param projectId
	 * @return
	 * @author: zj
	 */
	String getProjectsmallImagePath(String type, Integer projectId, boolean retainWatermark);

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
	public void doGeneratedInterestForOneInvestment(Project project, Investment investment) throws Exception;

	public void tryGeneratedInterestForOneInvestment(Project project, Investment investment) throws Exception;

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
	public void doGeneratedInterest(Project project) throws Exception;

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
	public void doBuyBack(Project project) throws Exception;

	/**
	 * @Title: getProjectDetail
	 * @Description: 获取项目认购前的信息
	 * @param projectId
	 * @return Map<String,Object>
	 * @author zj
	 * @date 2019-05-21 17:52
	 */
	Map<String, Object> getProjectDetail(Integer projectId);

	/**
	 * 
	 * 根据条件 分页获取在售项目列表信息
	 * 
	 * @Title: listProjectInfo
	 * @param parMap
	 * @return List<Map<String,Object>>
	 * @author zj
	 * @date 2019-05-22 14:24
	 */
	List<Map<String, Object>> listProjectInfo(Map<String, Object> parMap);

	/**
	 * 是否存在相同耳标号的标
	 * 
	 * @Title: ifExistProjectByEarnumber
	 * @param projectId
	 * @param earNumber
	 * @return boolean
	 * @author zj
	 * @date 2019-06-19 13:33
	 */
	boolean ifExistProjectByEarnumber(Integer projectId, String earNumber);

	/**
	 * 是否存在相同耳标号的标
	 * 
	 * @Title: ifExistProjectByEarnumber
	 * @param projectId
	 * @param earNumber
	 * @return boolean
	 * @author zj
	 * @date 2019-06-19 13:33
	 */
	boolean ifExistProjectByRealEarnumber(Integer projectId, String realEarnumber);

	/**
	 * 是否被用户续养 <br/>
	 * <>
	 * @author shuys
	 * @date 2019/11/20
	 * @param projectId
	 * @return boolean
	*/
	boolean ifExistProjectByBuyAgain(Integer projectId);
	
	/**
	 * 根据耳标号得到牛只的信息
	 * 
	 * @Title: getProjectDetailByEarNumber
	 * @param earNumber
	 * @return Map<String,Object>
	 * @author zj
	 * @date 2019-07-03 11:13
	 */
	Map<String, Object> getProjectDetailByEarNumber(String earNumber);

	/**
	 * 查看是否存在gpsnumber
	 * 
	 * @Title: checkGpsNumber
	 * @param gpsNumber 设备编码
	 * @return boolean true 存在 false 不存在
	 * @author zj
	 * @date 2019-07-04 14:31
	 */
	boolean checkGpsNumber(String gpsNumber);

	/** 
	* 统计项目个数
	* @Title: countProjectList 
	* @param keyword
	* @param status
	* @param projectType
	* @param noob
	* @param lendBeginTimeStartTime
	* @param lendBeginTimeEndTime
	* @return Integer
	* @author zj
	* @date 2019-07-11 17:10
	*/ 
	Integer countProjectList(String keyword, Integer status, Integer projectType, Integer noob ,Integer limitDays,
			String lendBeginTimeStartTime, String lendBeginTimeEndTime);

	List<ProjectGpsListVo> projectGpsList(Map<String, Object> parMap);

	void doEnableSale(Project oldProject) throws Exception;

	void doEnableSaleByLimitDays(Integer limitDays,Integer projectType,  Integer saleStatusProjectMiniCount) throws Exception;

	/** 
	* 根据耳标号查询回购记录
	* @Title: listBuyBackRecord 
	* @param earNumber
	* @return List<Map<String,Object>>
	* @author zj
	* @date 2019-07-19 15:36
	*/ 
	List<Map<String, Object>> listBuyBackRecord(String earNumber, Integer start, Integer limit );

	/** 
	*统计耳标号查询回购记录总数
	* @Title: countBuyBackRecord 
	* @param earNumber
	* @return int
	* @author zj
	* @date 2019-07-19 15:45
	*/ 
	int countBuyBackRecord(String earNumber);
	
	/**
	 * 列表统计信息
	* @Title: statisticsInfo 
	* @return List<Integer>
	* @author zj
	* @date 2019-07-22 16:03
	 */
	List<Integer> statisticsInfo();

	/**
	 *
	 * 用户是否买过新手标
	 *
	 * @Title: checkNoob
	 * @param userId
	 * @return boolean true: 新手 false 非新手
	 * @author zj
	 * @date 2019-08-12 15:02
	 */
	boolean isNoob(Integer userId);

	/**
	 * 牧场管理-回购查询
	 *
	 * @Title: listBuyBack
	 * @param startDate
	 * @param endDate
	 * @param startAge
	 * @param endAge
	 * @return List<Map<String,Object>>
	 * @author zj
	 * @date 2019-07-30 11:48
	 */
	List<Map<String, Object>> listBuyBack(String startDate, String endDate, Integer startAge, Integer endAge,Integer start, Integer limit,Integer adminId,Integer departmentId);

	/**
	* 牧场管理-回购查询(统计数量)
	* @Title: countBuyBack
	* @param startDate
	* @param endDate
	* @param startAge
	* @param endAge
	* @return Integer
	* @author zj
	* @date 2019-07-30 11:49
	*/
	Integer countBuyBack(String startDate, String endDate, Integer startAge, Integer endAge,Integer adminId,Integer departmentId);

	/**
	 * 回购统计
	 *
	 * @Title: listBuyBackTJ
	 * @param startDate
	 * @param endDate
	 * @return List<Map<String,Object>>
	 * @author zj
	 * @date 2019-07-31 11:29
	 */
	List<Map<String, Object>> listBuyBackTJ(String startDate, String endDate);

	/**
	 * 授信资金查询列表
	 *
	 * @Title: listCreditFunds
	 * @param startDate
	 * @param endDate
	 * @return List<Map<String,Object>>
	 * @author zj
	 * @date 2019-08-01 09:58
	 */
	List<Map<String, Object>> listCreditFunds(Integer typeId,String startDate, String endDate,Integer start, Integer limit);

	/**
	* 统计授信金额记录数量
	* @Title: countCreditFunds
	* @param typeId
	* @param startDate
	* @param endDate
	* @return int
	* @author zj
	* @date 2019-08-01 10:51
	*/
	int countCreditFunds(Integer typeId, String startDate, String endDate);

	List<InvestStatementVO> investStatement(String startDate, String endDate);

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
	 * @param start
	 * @param limit
	 * @param orderBy
	 * @param sort
	 * @return
	 */
	List<ProjectView> listProjectView(List<Integer> status, Integer noob, Integer limitDays, String keyword,
			Date createTimeStart, Date createTimeEnd, Date payTimeStart, Date payTimeEnd, Date buybackTimeStart,
			Date buybackTimeEnd, Date dueTimeStart, Date dueTimeEnd, Date feedTime, Integer buyAgain, Integer projectType, 
		  	Integer limitStart, Integer limit, String orderBy, String sort);
	
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
	 * @param start
	 * @param limit
	 * @param orderBy
	 * @param sort
	 * @return
	 */
	long countProjectView(List<Integer> status, Integer noob, Integer limitDays, String keyword, Date createTimeStart,
			Date createTimeEnd, Date payTimeStart, Date payTimeEnd, Date buybackTimeStart, Date buybackTimeEnd,
			Date dueTimeStart, Date dueTimeEnd, Date feedTime, Integer buyAgain, Integer projectType);

	List<Project> queryProjectByEarNumber(String earNumber);
	
	void saveAddLifePicture(MultipartFile file, String earNumber, int adminId);
	
	public List<ProjectLifePicture> queryLifePictureByEarNumber(String earNumber,Integer limitStart, Integer limit);
    
    public long countLifePictureByEarNumber(String earNumber);
	
	/**
	 * 牛只相册列表
	 * @author sxy
	 * @param userId
	 * @param limitStart
	 * @param limitEnd
	 * @return
	 */
	public List<Map<String, Object>> listProjectPicture(Integer userId,Integer limitStart, Integer limitEnd);
	
	/**
	 * 牛只相册列表数量
	 * @author sxy
	 * @param userId
	 * @return
	 */
	public Integer countProjectPicture(Integer userId);
	
	/**
	 * 牛只相册详情
	 * @author sxy
	 * @param userId
	 * @param projectId
	 * @param limitStart
	 * @param limitEnd
	 * @return
	 */
	public List<Map<String, Object>> getProjectPictureDetail(Integer userId, String earNum, Integer limitStart, Integer limitEnd);
	
	/**
	 * 牛只相册详情数量
	 * @author sxy
	 * @param userId
	 * @param projectId
	 * @return
	 */
	public Integer countProjectPictureDetail(Integer userId, String earNum);
	
	public Map<String, Object> addUploadVideo(MultipartFile file, int adminId);
	
    List<VideoAlbum> listVideoAlbum(Integer isRecommend, Integer start, Integer end);

    long countVideoAlbum(Integer isRecommend);
    
    public VideoAlbum getVideoAlbumById(Integer videoId);
    
    public void updateVideoAlbum(VideoAlbum video);
    
    public ProjectLifePicture queryLifePictureById(Integer pictureId);
    
    public void updateProjectLifePicture(ProjectLifePicture projectLifePicture);
    
    /**
     * @date 2019年9月19日
     * @author wangyun
     * @time 下午2:02:38
     * @Description 管理后台视频列表
     * 
     * @param start
     * @param end
     * @param beginCreateTime
     * @param endCreateTime
     * @param beginShowTime
     * @param endShowTime
     * @return
     */
    List<VideoAlbum> queryVideoAlbumList(Integer start, Integer end, String keyword,Integer isRecommend, Date beginCreateTime, Date endCreateTime,
            Date beginShowTime, Date endShowTime);

    int countVideoAlbumList(String keyword,Integer isRecommend,Date beginCreateTime, Date endCreateTime, Date beginShowTime, Date endShowTime);

	/**
	 * 项目重新上架状态 <br/>
	 * 0.未上架，1.后台上架（管理员，定时任务），2.用户上架，支付中（续购），3.用户上架，已支付（续购）
	 * @author shuys
	 * @date 2019/11/4
	 * @param parentId
	 * @return int
	*/
	int reshelfStatus(Integer parentId, Integer userId) throws Exception;

	/**
	 * 获取 续购订单 的支付订单 <br/>
	 * <>
	 * @author shuys
	 * @date 2019/11/6
	 * @param projectId
	 * @return boolean
	*/
	Recharge getBuyAgainRecharge(Integer projectId, Integer userId);

	Boolean queryExistSalingProjectByEarNumber(String earNumber);


    List<FilialeSellVO> listFilialeSell(Integer start, Integer end, String keyword, Date startTime, Date endTime);

    int countFilialeSell(String keyword, Date startTime, Date endTime);

    List<FilialeSellDetailVO> detailFilialeSell(Integer empId,String keyword,String recommendKeyword, Integer start, Integer end,Date startTime, Date endTime);

    int countDetailFilialeSell(Integer empId,String keyword, String recommendKeyword, Date startTime, Date endTime);
    
    int sumTotalLimitDayByEarNumber(String earNumber);

	boolean doCheckBuyAgainProject(Integer parentId) throws Exception;
	
	/**
	 * @desc 根据销售人员统计总金额
	 * @author wangyun
	 * @param empId
	 * @param keyword
	 * @param recommendKeyword
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	BigDecimal statisticsFilialeSell(Integer empId,String keyword, String recommendKeyword, Date startTime, Date endTime);

	List<ProjectIndexVO> listGroupByLimitDays(int limit);

	List<Map<String, Object>> listGroupByLimitDays(int limit, String orderByClause);
	
	ProjectIndexVO convertProjectIndexVO(Project project, boolean isHome);

	List<ProjectIndexVO> listPingniu(int limit);

	void updateByPrimaryKeyForVersion(Project project) throws Exception;

	void updateByPrimaryKeySelectiveForVersion(Project project) throws Exception;
	
	/**
	 * 牛只详情页天气数据
	 * @author sxy
	 * @return
	 */
	WeatherVO getWeatherInfo();
}
