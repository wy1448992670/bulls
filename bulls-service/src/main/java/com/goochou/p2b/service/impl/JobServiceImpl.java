package com.goochou.p2b.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.goochou.p2b.dao.JobMapper;
import com.goochou.p2b.service.JobService;

/**
 * Created by irving on 2016/7/19.
 */
@Service
public class JobServiceImpl implements JobService {
	@Resource
    private JobMapper jobMapper;
	
	@Override
	public boolean doAssetsSnapshot(Calendar currentPeriod,Integer recursionDepth) {
    	if(recursionDepth<0) {
    		return false;
    	}
    	String currentPeriodTable=this.getAssetsSnapshotTableName(currentPeriod.getTime());
    	System.out.println("currentPeriodTable:"+currentPeriodTable);
    	if (this.countTableName(currentPeriodTable) > 0) {
    		return false;
    	}
    	return this.assetsSnapshotRecursion(currentPeriod,recursionDepth);
    }
	
	private boolean assetsSnapshotRecursion(Calendar currentPeriod,Integer recursionDepth) {
    	if(recursionDepth<0) {
    		return false;
    	}
    	Calendar currentPeriodStart=JobServiceImpl.getYesterday(currentPeriod);
    	Calendar currentPeriodEnd=currentPeriod;
    	String priorPeriodTable=this.getAssetsSnapshotTableName(currentPeriodStart.getTime());
    	String currentPeriodTable=this.getAssetsSnapshotTableName(currentPeriodEnd.getTime());
    	System.out.println("priorPeriodTable:"+currentPeriodTable);
    	System.out.println("currentPeriodTable:"+currentPeriodTable);
    	if (this.countTableName(priorPeriodTable) == 0) {
    		if(!this.assetsSnapshotRecursion(currentPeriodStart,recursionDepth-1)) {
    			return false;
    		}
    	}
    	jobMapper.createAssetsSnapshotTable(currentPeriodTable);
    	jobMapper.assetsSnapshot(priorPeriodTable,currentPeriodTable, currentPeriodStart.getTime(), currentPeriodEnd.getTime());
    	return true;
    }
    
    private static Calendar getYesterday(Calendar today) {
    	Calendar yesterday=(Calendar)today.clone();
    	yesterday.add(Calendar.DATE, -1);
    	return yesterday;
    }
    
    
    private static final DateFormat ASSETS_SNAPSHOT_DATE_FORMAT =new SimpleDateFormat("YYYYMMdd");
    
    private static Date FIRST_RECORD_DATE=null;

	private static String ASSETS_SNAPSHOT_TABLE_PREFIX="trade_record_";
	
	private static String ASSETS_NOW_TABLE="assets";
	
    static {
    	FIRST_RECORD_DATE =new GregorianCalendar(2019, 6, 9).getTime();
    }
    
    @Override
    public Date getFIRST_RECORD_DATE() {
		return FIRST_RECORD_DATE;
	}
    
    @Override
    public String getASSETS_NOW_TABLE() {
		return ASSETS_NOW_TABLE;
	}
    
    @Override
    public String getAssetsSnapshotTableName(Date date) {
    	return ASSETS_SNAPSHOT_TABLE_PREFIX+ASSETS_SNAPSHOT_DATE_FORMAT.format(date);
    }
    
    @Override
    public int countTableName(String tableName) {
    	return jobMapper.countTableName(tableName);
    }
    
    @Override
    public String getExistAssetsSnapshotTableName(Date date) {
    	if(date==null) {
    		return ASSETS_NOW_TABLE;
    	}
    	String tableName=getAssetsSnapshotTableName(date);
    	if(this.countTableName(tableName)==0) {
    		if(date.before(FIRST_RECORD_DATE)) {
    			tableName=getAssetsSnapshotTableName(FIRST_RECORD_DATE);
    		}else {
    			tableName=ASSETS_NOW_TABLE;
    		}
    	}
    	return tableName;
    }
}
