package com.goochou.p2b.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.ActivityConstant;
import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.ProjectActivityStatusEnum;
import com.goochou.p2b.dao.ProjectActivitySettingMapper;
import com.goochou.p2b.model.Project;
import com.goochou.p2b.model.ProjectActivitySetting;
import com.goochou.p2b.model.ProjectActivitySettingExample;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.vo.ProjectActivityRecordVO;
import com.goochou.p2b.model.vo.ProjectActivitySettingVO;
import com.goochou.p2b.service.HongbaoService;
import com.goochou.p2b.service.MessageService;
import com.goochou.p2b.service.ProjectActivitySettingService;
import com.goochou.p2b.service.ProjectService;
import com.goochou.p2b.utils.BigDecimalUtil;

@Service
public class ProjectActivitySettingServiceImpl implements ProjectActivitySettingService {
	
	private final static Logger logger = Logger.getLogger(ProjectActivitySettingServiceImpl.class);

	@Resource
	private ProjectActivitySettingMapper projectActivitySettingMapper;

	@Resource
	private HongbaoService hongbaoService;
	
	@Resource
	private MessageService messageService;
	
	@Resource
	private ProjectService projectService;
	
	
	@Override
	public void saveProjectActivity(Integer projectId, Integer period) {
		
		
		Date now = new Date();
		try {
			//创建配置信息
			ProjectActivitySetting projectActivitySetting = new ProjectActivitySetting();
			projectActivitySetting.setCreateTime(now);
			projectActivitySetting.setUpdateTime(now);
			projectActivitySetting.setProjectId(projectId);
			projectActivitySetting.setPeriod(period);
			if(projectActivitySettingMapper.insertSelective(projectActivitySetting) <= 0){
				logger.info("创建配置信息失败: projectId: " + projectId + ", period: " + period);
				throw new RuntimeException("创建配置信息失败: projectId: " + projectId + ", period: " + period);
			}
			
			//创建抽奖号码表
			Map<String, Object> params = new HashMap<>();
			Integer increment = period * ActivityConstant.CODE_PER_PRICE + 1;
			params.put("increment", increment);
			params.put("period", period);
			
			projectActivitySettingMapper.createProjectActivityRecordTable(params);
			
		} catch (Exception e) {
			logger.error(e);
		}
		
	}

	@Override
	public List<ProjectActivitySetting> queryProjectActivitySettings() {
		
		ProjectActivitySettingExample example = new ProjectActivitySettingExample();
		return projectActivitySettingMapper.selectByExample(example);
	}

	@Override
	public List<Map<String,Object>> queryProjectAndActivity(Integer period) {
		Map<String,Object> map = new HashMap<>();
		map.put("period",period);
		return projectActivitySettingMapper.queryProjectAndActivity(map);
	}

	@Override
	public Map<String, Object> queryProjectActivityRecordX(Integer period) {
		Map<String,Object> map = new HashMap<>();
		map.put("period",period);
		map.put("project_activity_record","project_activity_record_"+period);
		return projectActivitySettingMapper.queryProjectActivityRecordX(map);
	}

	@Override
	public List<Map<String, Object>> queryWinNumberDetailX(Integer period,Integer userId) {
		Map<String,Object> map = new HashMap<>();
		map.put("userId",userId);
		map.put("period",period);
		map.put("project_activity_record","project_activity_record_"+period);
		return projectActivitySettingMapper.queryWinNumberDetailX(map);
	}

	public ProjectActivitySetting queryProjectActivitySettingByProjectId(
			Integer projectId) {
		
		ProjectActivitySettingExample example = new ProjectActivitySettingExample();
		example.createCriteria().andProjectIdEqualTo(projectId);
		List<ProjectActivitySetting> list = projectActivitySettingMapper.selectByExample(example);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public int insertProjectActivityRecordBatch(Map<String, Object> record) {
		
		return projectActivitySettingMapper.insertProjectActivityRecordBatch(record);
	}

	public int delProjectActivitySetting(String period) {
		int i=0;
		try {
			ProjectActivitySettingExample example = new ProjectActivitySettingExample();
			example.createCriteria().andPeriodEqualTo(Integer.parseInt(period));
			i += projectActivitySettingMapper.deleteByExample(example);
			
			Map<String, Object> params = new HashMap<>();
			params.put("period", period);
			i += projectActivitySettingMapper.dropProjectActivityRecordTable(params);
			
		} catch (Exception e) {
			logger.error(e);
		}
		
		return i;
	}

	/**
	 * 根据期数查找幸运号码列表
	 * @param period 期数
	 * @return List
	 */
	@Override
	public List<ProjectActivitySettingVO> queryProjectActivityRecordForAdmin(Integer period,Integer start, Integer limit) {

		List<ProjectActivitySettingVO> pasVOList = projectActivitySettingMapper.findProjectActivitySettingById(period,start,limit);

		for(ProjectActivitySettingVO pasvo:pasVOList){

			if(("3".equals(pasvo.getStatus())||"4".equals(pasvo.getStatus())) && null==pasvo.getUserId()){
				pasvo.setStatusStr(ProjectActivityStatusEnum.WAITING_OPEN.getDescription());
			}else if("2".equals(pasvo.getStatus())){
				pasvo.setStatusStr(ProjectActivityStatusEnum.SELLING.getDescription());
			}else if(("3".equals(pasvo.getStatus())||"4".equals(pasvo.getStatus())) && null!=pasvo.getUserId()){
				pasvo.setStatusStr(ProjectActivityStatusEnum.OPENED.getDescription());
			}
		}
		return pasVOList;
	}

	/**
	 * 根据期数查找幸运列表条数
	 * @param period 期数
	 * @return Integer
	 */
	@Override
	public Integer queryProjectActivityRecordCountForAdmin(Integer period) {
		return projectActivitySettingMapper.queryProjectActivityRecordCountForAdmin(period);
	}

	@Override
	public Map<String, Object> savePrize(Integer settingId, Integer luckyCode, Integer adminId, Integer period) {
		
		Map<String, Object> result = new HashMap<>();
		result.put("msg", "设置中奖号码失败");
		result.put("code", "fail");
			
		//查询中奖用户信息
		Map<String, Object> params = new HashMap<>();
		params.put("period", period);
		params.put("id", luckyCode);
		List<User> userList = projectActivitySettingMapper.selectProjectActivityRecordUserInfo(params);
		
		Date now = new Date();
		
		//查询用户信息
		if(userList == null || userList.size() == 0){
			logger.error(new RuntimeException("savePrize : 未获取到中奖用户"));
			result.put("msg", "设置中奖号码失败, 未获取到中奖用户");
			return result;
		}
		
		ProjectActivitySetting temp = projectActivitySettingMapper.selectByPrimaryKey(settingId);
		if(temp.getUserId() != null && temp.getUserId() > 0){
			logger.error(new RuntimeException("savePrize : 奖品已发放"));
			result.put("msg", "设置中奖号码失败, 奖品已发放");
			return result;
		}
		
		Integer projectId = temp.getProjectId();
		Project project = projectService.get(projectId);
		if(project.getStatus() < 3){
			logger.error(new RuntimeException("savePrize : 尚未满标"));
			result.put("msg", "设置中奖号码失败, 尚未满标");
			return result;
		}
		
		User luckyUser = userList.get(0);
		
		//更新project_activity_setting
		ProjectActivitySetting setting = new ProjectActivitySetting();
		setting.setOpenTime(now);
		setting.setOperateId(adminId);
		setting.setTrueName(luckyUser.getTrueName());
		setting.setUserId(luckyUser.getId());
		setting.setWinNumber(luckyCode);
		setting.setUpdateTime(now);
		setting.setId(settingId);
		
		if(projectActivitySettingMapper.updateByPrimaryKeySelective(setting) <= 0){
			logger.error(new RuntimeException("savePrize : 设置配置表失败"));
			result.put("msg", "设置中奖号码失败, 更新信息失败");
			throw new RuntimeException("savePrize : 设置配置表失败");
		}
		
		//发放奖励 type=1, 有效期15天, application=0, limit_amount=0, amount=根据配置和周期来取
		
		String configStr = ";;;;";
		String []conifg = configStr.split(";");
		
		Integer limitDays = project.getLimitDays();
		Integer index = limitDays / 30 - 1;
		index = index < 0 || index > conifg.length - 1 ? 0 : index;
		String hongbaoAmount = conifg[index];
		
		
		String description = "恭喜您参与第" + period + "期“" + Constants.PROJECT_ACITIVITY_MESSAGE_TITLE + "”活动中奖";
		if(hongbaoService.sendHongBaoToUser(Double.parseDouble(hongbaoAmount), luckyUser.getId(), description, null, 1, 1, 0, null, 15) <= 0){
			logger.error(new RuntimeException("savePrize : 发送红包失败"));
			result.put("msg", "设置中奖号码失败, 发送红包失败");
			throw new RuntimeException("savePrize : 发送红包失败");
		}
		
		result.put("msg", "设置中奖号码成功");
		result.put("code", "ok");
		result.put("trueName", luckyUser.getTrueName());
		
		//发站内信, 短信
		try {
			
			String title = "恭喜您第" + period + "期" + Constants.PROJECT_ACITIVITY_MESSAGE_TITLE + "活动中奖";
			String content = "亲爱的鑫聚财用户，恭喜您参与第" + period + "期“" + Constants.PROJECT_ACITIVITY_MESSAGE_TITLE + "”活动中奖，" + hongbaoAmount + "元现金红包已发送，您可在APP【我的福利】中查看。";
			messageService.save(title, content, luckyUser.getId());
			
			//发短信
//				YunSendMessage.sendSms(content, luckyUser.getPhone());
        	//SendMessageUtils.send(content + "TD退订【鑫聚财】", luckyUser.getPhone());
			
			//集体站内信
			params.clear();
			params.put("period", period);
			userList = projectActivitySettingMapper.selectProjectActivityRecordUserInfo(params);
			List<String> sendUserList = new ArrayList<>();
			for(User u : userList){
				if(!sendUserList.contains(u.getPhone()) && !u.getPhone().equals(luckyUser.getPhone())){
					sendUserList.add(u.getPhone());
					logger.info("u.getPhone() : " + u.getPhone());
				}
			}
			
			//亲爱的鑫粉，您参与5期“投资赢现金”活动中奖结果已发布，您可在鑫聚财APP活动页面查看。
			if(sendUserList.size() > 0){
				title = "第" + period + "期“" + Constants.PROJECT_ACITIVITY_MESSAGE_TITLE + "”活动公告";
				String phone = luckyUser.getPhone();
				content = "亲爱的鑫聚财用户，您参与第" + period + "期“投资赢现金”活动中奖结果已发布，手机尾号" + phone.substring(phone.length() - 4, phone.length()) + "的用户获得" + hongbaoAmount + "元现金红包。投资越多中奖机会越高，离中奖只差一步之遥";
				messageService.saveInternalList(title, content, sendUserList);
			}
		} catch (Exception e) {
			logger.error(e);
		}
			
		return result;
	}


	public List<ProjectActivityRecordVO> queryProjectActivityDetail(
			Integer period, Integer start, Integer limit) {
		
		//取ProjectActivityRecord, 关联user表
		Map<String, Object> params = new HashMap<>();
		params.put("period", period);
		params.put("start", start);
		params.put("limit", limit);
		List<ProjectActivityRecordVO> list = projectActivitySettingMapper.selectProjectActivityRecordWithUserInfo(params);
		
		if(list == null || list.size() == 0){
			logger.info("queryProjectActivityDetail : 未获取到详细信息 : period: " + period);
			return null;
		}
		
		//取project信息
		Integer projectId = list.get(0).getProjectId();
		Project project = projectService.get(projectId);
		Integer limitDays = project.getLimitDays();
		
		for(ProjectActivityRecordVO vo : list){
			
			//年化  BigDecimalUtil.fix2( =  count * 1000 / 365 * project的limit_days)
			Double amount = BigDecimalUtil.div(vo.getCount() * ActivityConstant.CODE_PER_PRICE * limitDays, 365, 2);
			vo.setAmount(amount);
		}
		return list;
	}

	@Override
	public Integer queryProjectActivityDetailCount(Integer period) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("period", period);
		return projectActivitySettingMapper.queryProjectActivityDetailCount(params);
	}
	
	public List<ProjectActivityRecordVO> queryProjectActivityByUser(Integer period, Integer userId){
		
		Map<String, Object> params = new HashMap<>();
		params.put("period", period);
		params.put("userId", userId);
		return projectActivitySettingMapper.queryProjectActivityByUser(params);
	}
	
}
