package com.goochou.p2b.service;

import java.util.Calendar;
import java.util.Date;

public interface JobService {
	
	boolean doAssetsSnapshot(Calendar currentPeriod, Integer recursionDepth);

	String getAssetsSnapshotTableName(Date date);

	int countTableName(String tableName);

	String getExistAssetsSnapshotTableName(Date date);

	Date getFIRST_RECORD_DATE();

	String getASSETS_NOW_TABLE();
}
