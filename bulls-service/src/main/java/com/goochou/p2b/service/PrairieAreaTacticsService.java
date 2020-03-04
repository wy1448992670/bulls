package com.goochou.p2b.service;

import java.util.Date;
import java.util.List;

import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceBehaviorResponse;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceGpsResponse;
import com.goochou.p2b.model.PrairieAreaTactics;
import com.goochou.p2b.model.PrairieAreaTacticsCacheAgency;
import com.goochou.p2b.model.Project;
import com.goochou.p2b.model.TrackDevice;
import com.goochou.p2b.model.TrackDeviceView;

public interface PrairieAreaTacticsService {

    
    List<PrairieAreaTactics> listPrairieAreaTacticsByEarNumber(String realEarNumber);

    PrairieAreaTacticsCacheAgency flushPrairieAreaTacticsCache() throws Exception;

    PrairieAreaTacticsCacheAgency doGetCacheAgency() throws Exception;
	
    List<DeviceGpsResponse> calculateGpsList(TrackDeviceView trackDeviceView, Integer days) throws Exception;
	
	Long executedTactics(Date date,List<PrairieAreaTactics> tacticsList);

	TrackDevice calculateVirtualTrackDevice(TrackDeviceView trackDeviceView) throws Exception;

	List<DeviceBehaviorResponse> calculateBehaviorList(TrackDeviceView trackDeviceView, Date startTime, Integer count)
			throws Exception;

}


