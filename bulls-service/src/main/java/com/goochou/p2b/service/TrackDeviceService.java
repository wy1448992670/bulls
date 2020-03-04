package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.dao.TrackDeviceMapper;
import com.goochou.p2b.dao.TrackDeviceViewMapper;
import com.goochou.p2b.model.TrackDevice;

public interface TrackDeviceService {

	/**
	 * 通过gps编号获取行动轨迹参数对象
	 * 
	 * @Title: getTrackdevice
	 * @param simNumber
	 * @return Trackdevice
	 * @author zj
	 * @date 2019-07-05 11:12
	 */
	public TrackDevice getTrackdevice(String simNumber);

	TrackDeviceMapper getTrackDeviceMapper();

	TrackDeviceViewMapper getTrackDeviceViewMapper();

	void doLoadTrackDevice(List<TrackDevice> deviceResponeList);

	void doCompileVirtualTrackDevice();
}
