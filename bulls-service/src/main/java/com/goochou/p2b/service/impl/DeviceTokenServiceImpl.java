package com.goochou.p2b.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.DeviceTokenMapper;
import com.goochou.p2b.model.DeviceToken;
import com.goochou.p2b.model.DeviceTokenExample;
import com.goochou.p2b.service.DeviceTokenService;

@Service
public class DeviceTokenServiceImpl implements DeviceTokenService {
	private static final Logger logger = Logger.getLogger(DeviceTokenServiceImpl.class);
	@Resource
	private DeviceTokenMapper deviceTokenMapper;

	@Override
	public List<DeviceToken> selectBytoken(String devicetoken) {
		DeviceTokenExample tokenExample = new DeviceTokenExample();
		tokenExample.createCriteria().andTokenEqualTo(devicetoken);
		return deviceTokenMapper.selectByExample(tokenExample);
	}

	@Override
	public DeviceToken queryByToken(Integer userId, String token) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("token", token);
		return deviceTokenMapper.queryByToken(map);
	}

	@Override
	public void saveRecord(DeviceToken deviceToken) {
		deviceToken.setUpdateDate(new Date());
		if (deviceToken.getId() != null) {
			deviceTokenMapper.updateByPrimaryKey(deviceToken);
		} else {
			deviceToken.setCreateDate(new Date());
			deviceTokenMapper.insertSelective(deviceToken);
		}
	}

	@Override
	public DeviceToken queryByUserId(Integer userId) {
		return deviceTokenMapper.queryByUserId(userId);
	}

	@Override
	public List<Map<String, Object>> queryByUserList(Integer[] ids) {
		Map<String, Object> map = new HashMap<>();
		map.put("ids", ids);
		return deviceTokenMapper.queryByUserList(map);
	}
	
	/**
	 * 将本次唤醒的deviceToken数据保存到数据库中.
	 * 本次唤醒的deviceToken数据通过接口传入,称为theDeviceToken
	 * 通过theDeviceToken.uuid,查询对应的deviceToken记录,称为uuidDT(uuid唯一)
	 * 如果有uuidDT,此记录将用于更新
	 * 通过theDeviceToken.device_token,查询install的[有效的]deviceTokenDT记录,
	 * 		称为installTokenDT(installTokenDT唯一,可以存在多个相同的uninstall的[失效的]deviceToken记录,uuid不同)
	 * 如果有installTokenDT,此记录将用于更新
	 * uuidDT和installTokenDT同时存在,注销installTokenDT(删除或使之失效)
	 * 可能获得一个将用于更新的记录,称为waitUpdateDT
	 * 通过theDeviceToken.userId,查询对应的deviceToken记录,称为userDT(userId唯一)
	 * 如果有waitUpdateDT
	 * 		如果有userDT且userDT不是waitUpdateDT(记录的id不同),注销userDT(删除或使之失效)
	 * 		然后把theDeviceToken的数据给waitUpdateDT,保留id和创建时间.保存waitUpdateDT.
	 * 如果没有waitUpdateDT
	 * 		如果有userDT,注销userDT(删除或使之失效)
	 * 		把theDeviceToken插入数据库
	 */
	@Override
	public void saveToken(DeviceToken theDeviceToken) throws Exception {
		logger.info("saveToken:"+theDeviceToken.getUuid()+" "+theDeviceToken.getToken()+" "+theDeviceToken.getUserId());
		DeviceToken waitUpdateDT=null;//将要更新的数据记录
		if(!StringUtils.isEmpty(theDeviceToken.getUuid())) {
			waitUpdateDT = this.selectByUUID(theDeviceToken.getUuid());
		}
		
		if(!StringUtils.isEmpty(theDeviceToken.getToken())) {
			DeviceToken installTokenDT=this.selectInstallDTByToken(theDeviceToken.getToken());
			if(installTokenDT != null) {
				if(waitUpdateDT == null) {
					waitUpdateDT = installTokenDT;
				}else {
					if(!waitUpdateDT.getId().equals(installTokenDT.getId())) {
						logger.info("待更新设备信息冲突,注销installTokenDT:"+installTokenDT.getUuid()+" "+installTokenDT.getToken()+" "+installTokenDT.getUserId());
						this.destroyToken(installTokenDT);
					}
				}
			}
		}
		
		if(waitUpdateDT != null) {
			logger.info("待更新设备信息:"+waitUpdateDT.getUuid()+" "+waitUpdateDT.getToken()+" "+waitUpdateDT.getUserId());
		}
		
		DeviceToken userDT=null;
		if (theDeviceToken.getUserId() != null) {
			userDT=this.queryByUserId(theDeviceToken.getUserId());
		}
		
		if(waitUpdateDT != null) {//有可更新DT
			if(!waitUpdateDT.equals(theDeviceToken)) {
				if(userDT != null && !userDT.getId().equals(waitUpdateDT.getId())) {
					logger.info("更新设备信息,该用户已存在其他设备信息,先注销该信息:"+userDT.getUuid()+" "+userDT.getToken()+" "+userDT.getUserId());
					this.destroyToken(userDT);
				}
				logger.info("更新设备信息");
				theDeviceToken.setId(waitUpdateDT.getId());
				theDeviceToken.setCreateDate(waitUpdateDT.getCreateDate());
				this.saveRecord(theDeviceToken);//更新
			}else {
				logger.info("设备信息无更改,不必更新");
			}
		}else {//无可更新DT
			if(userDT != null) {
				logger.info("新增设备信息,该用户已存在设备信息,先注销该信息:"+userDT.getUuid()+" "+userDT.getToken()+" "+userDT.getUserId());
				this.destroyToken(userDT);
			}
			logger.info("新增设备信息");
			this.saveRecord(theDeviceToken);//插入
		}
		
	}

	@Override
	public void destroyToken(DeviceToken deviceToken) throws Exception {
		if(!StringUtils.isEmpty(deviceToken.getUuid())) {
			deviceToken.setUserId(null);
			deviceToken.setIsUninstall(true);
			this.saveRecord(deviceToken);
		}else {
			deviceTokenMapper.deleteByPrimaryKey(deviceToken.getId());
		}
	}
	
	@Override
    public DeviceToken selectByUUID(String uuid) throws Exception {
        DeviceTokenExample example = new DeviceTokenExample();
        example.createCriteria().andUuidEqualTo(uuid);
        List<DeviceToken> deviceTokenList=deviceTokenMapper.selectByExample(example);
        if(deviceTokenList.size()==1) {
			return deviceTokenList.get(0);
		}else if(deviceTokenList.size()== 0) {
			return null;
		}else {
			throw new Exception("deviceToken.uuid重复");
		}
    }
	
	@Override
	public DeviceToken selectInstallDTByToken(String devicetoken) throws Exception {
		DeviceTokenExample example = new DeviceTokenExample();
		example.createCriteria().andTokenEqualTo(devicetoken).andIsUninstallEqualTo(false);
		List<DeviceToken> deviceTokenList= deviceTokenMapper.selectByExample(example);
		if(deviceTokenList.size() == 1) {
			return deviceTokenList.get(0);
		}else if(deviceTokenList.size() == 0) {
			return null;
		}else {
			throw new Exception("install中的deviceToken.token重复");
		}
	}
}
