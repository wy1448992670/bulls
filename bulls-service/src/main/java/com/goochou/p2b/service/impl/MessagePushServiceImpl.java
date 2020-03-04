package com.goochou.p2b.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.bag.TreeBag;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.ClientEnum;
import com.goochou.p2b.constant.PushUrlTypeEnum;
import com.goochou.p2b.dao.DeviceTokenMapper;
import com.goochou.p2b.dao.MessagePushMapper;
import com.goochou.p2b.dao.PushTaskMapper;
import com.goochou.p2b.model.BaseResult;
import com.goochou.p2b.model.DeviceToken;
import com.goochou.p2b.model.MessagePush;
import com.goochou.p2b.model.MessagePushExample;
import com.goochou.p2b.model.MessagePushExample.Criteria;
import com.goochou.p2b.model.PushTask;
import com.goochou.p2b.model.PushTaskExample;
import com.goochou.p2b.model.User;
import com.goochou.p2b.service.MessagePushService;
import com.goochou.p2b.service.PushTaskService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.utils.DateUtil;
import com.goochou.p2b.utils.StringUtil;
import com.goochou.p2b.utils.umeng.UMengUtil;

@Service("messagePushServiceImpl")
public class MessagePushServiceImpl implements MessagePushService{
	private static final Logger logger = Logger.getLogger(MessagePushServiceImpl.class);
	@Autowired
	private DeviceTokenMapper deviceTokenMapper;
	
	@Autowired
	private MessagePushMapper messagePushMapper;
	@Autowired
	private PushTaskService pushTaskService;
	@Autowired
	private PushTaskMapper pushTaskMapper;
	
	@Autowired
	private UserService userService;
	 
	@Override
	public BaseResult messagePush(MessagePush messagePush) {
		logger.info("开始推送消息-------------------->" + messagePush);
		BaseResult result = new BaseResult();
		Integer androidSuccess = 0;
		Integer iosSuccess = 0;
		try {
			
			if (messagePush == null || StringUtil.isNull(messagePush.getTitle()) || StringUtil.isNull(messagePush.getContent()) ) {
				result.setSuccess(false);
				result.setErrorMsg("参数不完整");
				return result;
			}
			
			// 查询task的原生推送参数
			JSONObject params = new JSONObject();
			if(!StringUtils.isBlank(messagePush.getParams())) {
				params = JSONObject.fromObject(messagePush.getParams());
			}
			
			//List<DeviceToken> deviceTokens = deviceTokenMapper.queryByUserIdList(list);
			List<DeviceToken> deviceTokens = deviceTokenMapper.queryByMessagePush(messagePush.getId());
			
			List<String> iosTokenList = new ArrayList<>();
			List<String> androidTokenList = new ArrayList<>();
			
			StringBuffer iosStr = new StringBuffer();
			StringBuffer androidStr = new StringBuffer();
			for (DeviceToken deviceToken : deviceTokens) {
				if (deviceToken.getClient().equals(ClientEnum.IOS.getFeatureName())) {// IOS
					iosStr.append(deviceToken.getToken()).append("\n");
					iosTokenList.add(deviceToken.getToken());
					iosSuccess++;
				} else if (deviceToken.getClient().equals(ClientEnum.ANDROID.getFeatureName())) {// Android
					androidStr.append(deviceToken.getToken()).append("\n");
					androidTokenList.add(deviceToken.getToken());
					androidSuccess++;
				}
			}
			
			// 设置推送地址类型
			String urlType = "";
			if(messagePush.getType() == 1) {// H5
				urlType = PushUrlTypeEnum.URL.getFeatureName();
			} else { // 原生
				urlType = PushUrlTypeEnum.NATIVE.getFeatureName();
			}
			UMengUtil umeng = null;
			String taskId = "";
			
			//安卓推送
			umeng = new UMengUtil(UMengUtil.ANDROID_APP_KEY,
					UMengUtil.ANDROID_APP_SECRET);
			
			if (androidStr.length() > 0) {
				logger.info("安卓推送开始----------------------->");
				taskId = umeng.sendAndroidFilecast(messagePush.getTitle(),
						messagePush.getContent(), null ,messagePush.getUrl(), // 定时推送按照当前定时任务时间发送友盟(友盟即时推送)
						androidStr.toString(), urlType, params);
				messagePush.setAndroidTaskId(taskId);
				logger.info("taskId: " + taskId);
				//更新push_task表
				PushTask pushTask = new PushTask();
				pushTask.setTaskId(taskId);
				pushTask.setSendTime(new Date());
				PushTaskExample example = new PushTaskExample();
				example.createCriteria().andDeviceTokenIn(androidTokenList).andPushIdEqualTo(messagePush.getId());
				pushTaskMapper.updateByExampleSelective(pushTask, example);
				logger.info("安卓推送结束----------------------->");
			}
			
			//IOS推送
			if (iosStr.length() > 0) {
				logger.info("IOS推送开始----------------------->");
				umeng = new UMengUtil(UMengUtil.IOS_APP_KEY, UMengUtil.IOS_APP_SECRET);
				taskId = umeng.sendIOSFilecast(messagePush.getTitle(),
						messagePush.getContent(), null ,messagePush.getUrl(), // 定时推送按照当前定时任务时间发送友盟(友盟即时推送)
						iosStr.toString(), urlType, params);
				messagePush.setIosTaskId(taskId);
				logger.info("taskId: " + taskId);
				//更新push_task表
				PushTask pushTask = new PushTask(); 
				pushTask.setTaskId(taskId);
				pushTask.setSendTime(new Date());
				PushTaskExample example = new PushTaskExample();
				example.createCriteria().andDeviceTokenIn(iosTokenList).andPushIdEqualTo(messagePush.getId());
				pushTaskMapper.updateByExampleSelective(pushTask, example);
				logger.info("IOS推送结束----------------------->");
			}
			
			messagePush.setAndroidSuccess(androidSuccess);
			messagePush.setIosSuccess(iosSuccess);
			//如果已保存，则修改
			if (messagePush.getStatus() != null && messagePush.getStatus() == 0) {
				messagePush.setStatus(1);//状态  已推送
				if(updateMessagePush(messagePush)){
					result.setSuccess(true);
				}
			}else{
				messagePush.setStatus(1);//状态  已推送
				messagePush.setCreateDate(new Date());
				if(addMessagePush(messagePush)){
					result.setSuccess(true);
				}
			}
			
			
		} catch (Exception e) {
			result.setSuccess(false);
			logger.error("推送异常",e);
			e.printStackTrace();
			logger.error("推送异常",e);
		}
		logger.info("推送结束-------------------->" + result);
		return result;
	}

	@Override
	public List<MessagePush> queryList(String keyword, Integer status,Integer start, Integer limit) {
		Map<String, Object> map = new HashMap<>();
        map.put("keyword",keyword);
        map.put("status",status);
        map.put("start",start);
        map.put("limit",limit);
        return messagePushMapper.queryList(map);
	}

	@Override
	public Integer queryCount(String keyword,Integer status) {
		Map<String, Object> map = new HashMap<>();
        map.put("keyword",keyword);
        map.put("status",status);
        return messagePushMapper.queryCount(map);
	}

	@Override
	public MessagePush queryMessagePush(Integer id) {
		return messagePushMapper.selectByPrimaryKey(id);
	}

	@Override
	public BaseResult searchMessagePush(MessagePush messagePush) {
		BaseResult result = new BaseResult();
		Map<String,Object> map = new HashMap<>();
		
		if (messagePush == null || messagePush.getAndroidTaskId() == null && messagePush.getIosTaskId() == null) {
			result.setSuccess(false);
			return result;
		}
		
		String android = UMengUtil.queryStatus(messagePush.getAndroidTaskId(), UMengUtil.ANDROID_APP_KEY, UMengUtil.ANDROID_APP_SECRET);
		String ios = UMengUtil.queryStatus(messagePush.getIosTaskId(), UMengUtil.IOS_APP_KEY, UMengUtil.IOS_APP_SECRET);
		
		if (android != null) {
			JSONObject fromObject = JSONObject.fromObject(android);
			map.put("android", fromObject);
		}
		if (ios != null) {
			JSONObject fromObject = JSONObject.fromObject(ios);
			map.put("ios", fromObject);
		}
		
		result.setSuccess(true);
		result.setMap(map);
		return result;
	}

	@Override
	public boolean addMessagePush(MessagePush messagePush) {
		if(messagePushMapper.insert(messagePush)>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean updateMessagePush(MessagePush messagePush) {
		if(messagePushMapper.updateByPrimaryKeySelective(messagePush)>0){
			return true;
		}
		return false;
	}
	

	@Override
	public MessagePushMapper getMessagePushMapper() {
		return messagePushMapper;
	}

	@Override
	public void addMessagePush(List<String> list, MessagePush messagePush ,Integer uploadType) throws Exception {
		Date date = new Date();
        // 插入推送消息
        messagePush.setCreateDate(date);
        messagePush.setStatus(0);	// 未推送
        if(this.addMessagePush(messagePush)) {
	        // 插入推送定时任务表
	        PushTask task =  new PushTask();
	    	task.setTitle(messagePush.getTitle());
	        task.setContent(messagePush.getContent());
	        task.setUrl(messagePush.getUrl());
	        task.setCreateTime(date); 
	        if(list!= null && list.size() > 0) { // 模板指定用户
        		for (String key : list) {
	                task.setPushId(messagePush.getId());
	             	if(uploadType == 1) {//用户id
	             		task.setUserId(Integer.parseInt(key));
	             		
	             	} else if(uploadType == 2){// token
	             		task.setDeviceToken(key);
	             	}
	                pushTaskService.addPushTask(task);
	     		}
	        	
	        } else { // 没有模板,推送全部有DeviceToken的用户
	        	List<DeviceToken> deviceUser = deviceTokenMapper.queryAllUsersHasDeviceToken();
	            for (DeviceToken device : deviceUser) {
	            	task.setUserId(device.getUserId());
	            	task.setDeviceToken(device.getToken());
	                task.setPushId(messagePush.getId());
	                pushTaskService.addPushTask(task);
	            }
	        	
	        }
	        
	        // -------------------------SendTime 为空则即时发送
	        if(null == messagePush.getSendTime()) {
	        	logger.info("即时推送消息开始---------------->" + messagePush);
	        	messagePush(messagePush);
	        }
        }
	}
	
	@Override
	public void doMessagePush() {
		try {
			// 查询未推送的消息
			List<MessagePush> messagePush = new ArrayList<>();
			MessagePushExample example = new MessagePushExample();
			Criteria c = example.createCriteria();
			c.andStatusEqualTo(0);// 未推送
			c.andSendTimeLessThan(new Date());// 定时任务跑批时间.
			messagePush = messagePushMapper.selectByExample(example);
			
			// 推送消息
			if(messagePush != null && messagePush.size() > 0) {
				for (MessagePush push : messagePush) {
					messagePush(push);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("定时任务推送异常",e);
		}
	}
	
	
	/**
	 * @date 2019年8月19日
	 * @author wangyun
	 * @time 下午3:20:45
	 * @Description 查询需要推送的userId
	 * 
	 * @param pushId
	 * @return
	 */
	public List<String> getUserIdsByPushId(Integer pushId){
		List<String> userIds = new ArrayList<>();
		List<PushTask> pushTasks = new ArrayList<>();
		PushTaskExample taskExample = new PushTaskExample();
		com.goochou.p2b.model.PushTaskExample.Criteria cre = taskExample.createCriteria(); 
		cre.andPushIdEqualTo(pushId);
		pushTasks = pushTaskMapper.selectByExample(taskExample);
		
		if(pushTasks != null && pushTasks.size() > 0) {
			for (PushTask pushTask : pushTasks) {
				userIds.add(pushTask.getUserId().toString());
			}
			return userIds;
		}
		return null;
	}
}
