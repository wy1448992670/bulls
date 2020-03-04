/**   
* @Title: TrackDeviceServiceImpl.java 
* @Package com.goochou.p2b.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zj
* @date 2019-07-05 11:14 
* @version V1.0   
*/
package com.goochou.p2b.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.goochou.p2b.dao.TrackDeviceMapper;
import com.goochou.p2b.dao.TrackDeviceViewMapper;
import com.goochou.p2b.model.TrackDevice;
import com.goochou.p2b.model.TrackDeviceExample;
import com.goochou.p2b.model.TrackDeviceView;
import com.goochou.p2b.model.TrackDeviceViewExample;
import com.goochou.p2b.service.PrairieAreaTacticsService;
import com.goochou.p2b.service.TrackDeviceService;
import com.goochou.p2b.utils.StringUtils;

/**
 * @ClassName: TrackDeviceServiceImpl
 * @author zj
 * @date 2019-07-05 11:14
 */
@Service
public class TrackDeviceServiceImpl implements TrackDeviceService {
	private final static Logger logger = Logger.getLogger(TrackDeviceServiceImpl.class);
	
	@Autowired
	TrackDeviceMapper trackDeviceMapper;
	
	@Autowired
	TrackDeviceViewMapper trackDeviceViewMapper;
	
	@Autowired
	PrairieAreaTacticsService prairieAreaTacticsService;
	
	private TrackDeviceMapper getMapper() {
		return trackDeviceMapper;
	}

	@Override
	public TrackDevice getTrackdevice(String simNumber) {
		TrackDeviceExample example = new TrackDeviceExample();
		example.createCriteria().andSim_numberEqualTo(simNumber);
		List<TrackDevice> list = getMapper().selectByExample(example);
		if (!CollectionUtils.isEmpty(list) && list.size() != 0) {
			return list.get(0);
		} else {
			return null;
		}

	}

	@Override
	public TrackDeviceMapper getTrackDeviceMapper() {
		return trackDeviceMapper;
	}

	@Override
	public TrackDeviceViewMapper getTrackDeviceViewMapper() {
		return trackDeviceViewMapper;
	}
	
	@Override
	public void doLoadTrackDevice(List<TrackDevice> deviceResponeList) {
		Date now=new Date();
		for(TrackDevice deviceResponse:deviceResponeList) {
			deviceResponse.setSync_at(now);
			deviceResponse.setIs_virtual(false);
			
    		TrackDevice oldDeviceResponse=this.getTrackDeviceMapper().selectByPrimaryKey(deviceResponse.getId());
    		if(oldDeviceResponse==null) {
    			this.getTrackDeviceMapper().insert(deviceResponse);
    		}else {
    			this.getTrackDeviceMapper().updateByPrimaryKey(deviceResponse);
    		}
    		
    		if( !StringUtils.isBlank(deviceResponse.getNickname()) ) {
    			
    			TrackDeviceExample exampl=new TrackDeviceExample();
        		exampl.createCriteria()
        		.andNicknameEqualTo(deviceResponse.getNickname())//耳标号重复
        		.andIdNotEqualTo(deviceResponse.getId())//且不是本条数据
        		.andIs_virtualEqualTo(true);//且是虚拟数据
        		
        		this.getTrackDeviceMapper().deleteByExample(exampl);
    		}
    	}
	}
	
	@Override
	public void doCompileVirtualTrackDevice() {
		TrackDeviceViewExample example=new TrackDeviceViewExample();
		example.createCriteria().andIdIsNull();
		example.or(example.createCriteria().andIsVirtualEqualTo(true));
		//没有设备或者是虚拟设备的数据
		List<TrackDeviceView> trackDeviceViewList=trackDeviceViewMapper.selectByExample(example);
		for(TrackDeviceView trackDeviceView:trackDeviceViewList) {
			try {
				TrackDevice trackDevice=prairieAreaTacticsService.calculateVirtualTrackDevice(trackDeviceView);
				if(trackDeviceView.getId()==null) {
					this.getTrackDeviceMapper().insert(trackDevice);
				}else {
					TrackDeviceExample tDExample=new TrackDeviceExample();
					tDExample.createCriteria().andIdEqualTo(trackDeviceView.getId());
					this.getTrackDeviceMapper().updateByExample(trackDevice, tDExample);
				}
			} catch (Exception e) {
				logger.error("doCompileVirtualTrackDevice error",e);
			}
		}
		this.getTrackDeviceMapper().updateGpsNumByEarNum();
	}

}
