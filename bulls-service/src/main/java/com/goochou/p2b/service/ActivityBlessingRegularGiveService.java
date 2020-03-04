package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.model.ActivityBlessingRegularGive;

public interface ActivityBlessingRegularGiveService {

	void updateByExampleForVersion(ActivityBlessingRegularGive activityBlessingRegularGive) throws Exception;

	void updateByExampleSelectiveForVersion(ActivityBlessingRegularGive activityBlessingRegularGive) throws Exception;

	List<ActivityBlessingRegularGive> getNoGivenList() throws Exception;
	
	void doGive(ActivityBlessingRegularGive activityBlessingRegularGive) throws Exception;

}
