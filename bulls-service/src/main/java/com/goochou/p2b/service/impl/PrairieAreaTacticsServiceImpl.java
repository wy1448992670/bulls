package com.goochou.p2b.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.dao.PrairieAreaTacticsMapper;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceBehaviorResponse;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceGpsResponse;
import com.goochou.p2b.model.PrairieArea;
import com.goochou.p2b.model.PrairieAreaPoint;
import com.goochou.p2b.model.PrairieAreaTactics;
import com.goochou.p2b.model.PrairieAreaTacticsCacheAgency;
import com.goochou.p2b.model.PrairieAreaTacticsExample;
import com.goochou.p2b.model.TrackDevice;
import com.goochou.p2b.model.TrackDeviceView;
import com.goochou.p2b.service.PrairieAreaService;
import com.goochou.p2b.service.PrairieAreaTacticsService;
import com.goochou.p2b.service.memcached.MemcachedManager;
import com.goochou.p2b.utils.DateUtil;

@Service
public class PrairieAreaTacticsServiceImpl implements PrairieAreaTacticsService {
	private final static Logger logger = Logger.getLogger(PrairieAreaTacticsServiceImpl.class);

	@Resource
	PrairieAreaTacticsMapper prairieAreaTacticsMapper;
	
	@Resource
	PrairieAreaService prairieAreaService;
	
	@Resource
	MemcachedManager memcachedManager;

	@Override
	public List<PrairieAreaTactics> listPrairieAreaTacticsByEarNumber(String realEarNumber) {
		PrairieAreaTacticsExample example = new PrairieAreaTacticsExample();
		example.createCriteria().andEarNumberEqualTo(realEarNumber);
		example.setOrderByClause("sequence asc");
		return prairieAreaTacticsMapper.selectByExample(example);
	}
	
	/**
	 * 刷新memcache缓存
	 */
    @Override
	public PrairieAreaTacticsCacheAgency flushPrairieAreaTacticsCache() throws Exception {
    	Map<Long,List<PrairieArea>> prairieIdKPrairieAreaListVMap=prairieAreaService.getPrairieIdKPrairieAreaListVMap();
    	Map<Long,PrairieArea> prairieAreaIdKPrairieAreaVMap=new HashMap<Long,PrairieArea>(1 << 8);
    	Map<Long, List<PrairieAreaTactics>> prairieIdKPrairieAreaTacticsListVMap = this.getPrairieIdKPrairieAreaTacticsListVMap();
    	
    	for(Long prairieId:prairieIdKPrairieAreaListVMap.keySet()) {
    		List<PrairieArea> byPrairieIdList=prairieIdKPrairieAreaListVMap.get(prairieId);
    		for(PrairieArea prairieArea:byPrairieIdList) {
    			prairieAreaIdKPrairieAreaVMap.put(prairieArea.getId(), prairieArea);
    		}
    	}
    
    	PrairieAreaTacticsCacheAgency cacheAgency=new PrairieAreaTacticsCacheAgency();
		cacheAgency.setPrairieIdKPrairieAreaListVMap(prairieIdKPrairieAreaListVMap);
		cacheAgency.setPrairieAreaIdKPrairieAreaVMap(prairieAreaIdKPrairieAreaVMap);
		cacheAgency.setPrairieIdKPrairieAreaTacticsListVMap(prairieIdKPrairieAreaTacticsListVMap);
		
		if(!memcachedManager.addOrReplace(Constants.PRAIRIE_AREA_TACTICS_CACHE, cacheAgency)) {
			throw new Exception("刷新memcached.server1.host "+Constants.PRAIRIE_AREA_TACTICS_CACHE+" 失败");
		}
		
		return cacheAgency;
	}
    
    /**
     * 获得缓存代理
     * @author 张琼麒
     * @version 创建时间：2019年9月9日 下午3:27:05
     * @return
     * @throws Exception
     */
    @Override
	public PrairieAreaTacticsCacheAgency doGetCacheAgency() throws Exception {
		Object theObject=memcachedManager.get(Constants.PRAIRIE_AREA_TACTICS_CACHE);
		if(theObject==null) {
			synchronized (PrairieAreaTacticsServiceImpl.class) {
				prairieAreaTacticsMapper.lockTable();
				theObject=memcachedManager.get(Constants.PRAIRIE_AREA_TACTICS_CACHE);
				if(theObject==null) {
					return this.flushPrairieAreaTacticsCache();
				}
			}
		}
		PrairieAreaTacticsCacheAgency cacheAgency=(PrairieAreaTacticsCacheAgency)theObject;
		return cacheAgency;
	}
	

	
	/**
	 * 按PrairieId组装 有序的策略组map
	 * 
	 * @author 张琼麒
	 * @version 创建时间：2019年8月28日 下午3:17:09
	 * @return
	 */
	private Map<Long, List<PrairieAreaTactics>> getPrairieIdKPrairieAreaTacticsListVMap() {

		Map<Long, List<PrairieAreaTactics>> prairieIdKPrairieAreaTacticsListVMap = new HashMap<Long, List<PrairieAreaTactics>>(1 << 8);
		PrairieAreaTacticsExample example = new PrairieAreaTacticsExample();
		example.createCriteria().andEarNumberIsNull().andPrairieIdIsNotNull();
		example.setOrderByClause("sequence asc");
		for (PrairieAreaTactics prairieAreaTactics : prairieAreaTacticsMapper.selectByExample(example)) {
			List<PrairieAreaTactics> prairieAreaTacticsList = prairieIdKPrairieAreaTacticsListVMap.get(prairieAreaTactics.getPrairieId());
			if (prairieAreaTacticsList == null) {
				prairieAreaTacticsList = new ArrayList<PrairieAreaTactics>();
				prairieIdKPrairieAreaTacticsListVMap.put(prairieAreaTactics.getPrairieId(), prairieAreaTacticsList);
			}
			prairieAreaTacticsList.add(prairieAreaTactics);
		}
		return prairieIdKPrairieAreaTacticsListVMap;
	}

	/**
	 * 策略计算,通过牛的耳标|牧场id,获得策略序列,匹配生成数据的时间,返回击中得区域id
	 * 
	 * @author 张琼麒
	 * @version 创建时间：2019年8月28日 下午6:09:14
	 * @param project
	 * @param date
	 * @return PrairieArea.id
	 * @throws Exception 
	 */
	@Override
	public List<DeviceGpsResponse> calculateGpsList(TrackDeviceView trackDeviceView, Integer days) throws Exception {
		List<DeviceGpsResponse> result=new ArrayList<DeviceGpsResponse>();
		
		//今日0点
		Date today = DateUtil.getDayStartDate(new Date());
		//日期循环体
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DATE, -days);
		
		if(StringUtils.isBlank(trackDeviceView.getRealEarNumber())){
			throw new Exception("耳标号未指定");
		}

		//耳标号的hashCode
		Integer realEarNumberHashCode=trackDeviceView.getRealEarNumber().hashCode();
		//牧场id
		Long prairieId=null;
		if(!StringUtils.isBlank(trackDeviceView.getPrairieValue()) && StringUtils.isNumeric(trackDeviceView.getPrairieValue())) {
			prairieId = Long.parseLong(trackDeviceView.getPrairieValue());
		}
		
		PrairieAreaTacticsCacheAgency cacheAgency=this.doGetCacheAgency();
		//牧场默认区域
		PrairieArea defultPrairieArea=null;
		if(prairieId!=null) {
			List<PrairieArea> innerPrairieAreaList=cacheAgency.listPrairieAreaByPrairieId(prairieId);
			if(innerPrairieAreaList!=null && innerPrairieAreaList.size()>0) {
				defultPrairieArea=innerPrairieAreaList.get(0);
			}
		}
		
		//组装策略
		List<PrairieAreaTactics> tacticsList = new ArrayList<PrairieAreaTactics>();
		//指定牛策略
		List<PrairieAreaTactics> byEarNumberList = this.listPrairieAreaTacticsByEarNumber(trackDeviceView.getRealEarNumber());
		if(byEarNumberList!=null) {
			tacticsList.addAll(byEarNumberList);
		}
		//牧场则略
		if (prairieId != null) {
			// 牧场id
			List<PrairieAreaTactics> byPrairieIdList = cacheAgency.listPrairieAreaTacticsByPrairieId(prairieId);
			if(byPrairieIdList!=null) {
				tacticsList.addAll(byPrairieIdList);
			}
		}
		cal.add(Calendar.HOUR, -3);
		while(today.after(cal.getTime())) {
			/*
			入循环体先加1小时
			获得的数据为1点到后一天0点的数据,模拟德鲁伊接口数据形式
			 */
			cal.add(Calendar.HOUR, 4);
			
			//随机种子,以耳标号.hashCode+生成数据的时间为随机种子
			Long seed=realEarNumberHashCode + cal.getTime().getTime();
			//随机数生成器
			Random random = new Random(seed);
			
			//策略计算,获得区域id
			Long prairieAreaId=this.executedTactics(cal.getTime(),  tacticsList);
			
			//对应区域
			PrairieArea prairieArea=null;
			if(prairieAreaId!=null) {
				prairieArea=cacheAgency.getPrairieAreaById(prairieAreaId);
			}
			if(prairieArea==null) {
				if(defultPrairieArea==null) {
					continue;
				}
				prairieArea=defultPrairieArea;
			}
			if(!prairieArea.isEffectiveArea()) {
				continue;
			}
			
			PrairieAreaPoint testPoint=null;
			
			//while最大执行次数,防止死循环
			int insurance=20;
			do{
				testPoint=getPointInRectangleByRandomSeed(prairieArea,random);
				logger.debug("testPoint:"+JSON.toJSONString(testPoint));
			}while(--insurance>0 && !pointInPolygon(testPoint,prairieArea) );
			
			if(insurance>0) {
				DeviceGpsResponse deviceGpsResponse=this.packageDeviceGpsResponse(testPoint.getLongitude().doubleValue()
						 ,testPoint.getLatitude().doubleValue(), cal, random, seed, trackDeviceView);
				
				result.add(deviceGpsResponse);
			}
		}
		
		return result;
	}
	
	/**
	 * 策略计算,通过牛的耳标|牧场id,获得策略序列,匹配生成数据的时间,返回击中得区域id
	 * 
	 * @author 张琼麒
	 * @version 创建时间：2019年8月28日 下午6:09:14
	 * @param trackDeviceView
	 * @param date
	 * @return PrairieArea.id
	 * @throws Exception 
	 */
	@Override
	public List<DeviceBehaviorResponse> calculateBehaviorList(TrackDeviceView trackDeviceView, Date startTime , Integer count) throws Exception {
		List<DeviceBehaviorResponse> result=new ArrayList<DeviceBehaviorResponse>();
		
		//今日0点
		Date today = DateUtil.getDayStartDate(new Date());
		//日期循环体
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE)/10*10);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		if(StringUtils.isBlank(trackDeviceView.getRealEarNumber())){
			throw new Exception("耳标号未指定");
		}
		
		//耳标号的hashCode
		Integer realEarNumberHashCode=trackDeviceView.getRealEarNumber().hashCode();
		
		cal.add(Calendar.MINUTE, -10);
		while(today.after(cal.getTime()) && count-->0) {
			DeviceBehaviorResponse deviceBehaviorResponse=new DeviceBehaviorResponse();
			/*
			获得的数据为startTime开始到现在或者count最大值的数据,10分钟一条,模拟德鲁伊接口数据形式
			 */
			cal.add(Calendar.MINUTE, 10);
			
			//随机种子,以耳标号.hashCode+生成数据的时间为随机种子
			Long seed=realEarNumberHashCode + cal.getTime().getTime();
			//随机数生成器
			Random random = new Random(seed);
			
			double randomNumber=(random.nextDouble()*1000)+1;
			double logarithm=Math.log10(randomNumber);//0~4 对数越大,概率越大
			Calendar moning=Calendar.getInstance();
			Calendar evening=Calendar.getInstance();
			moning.setTime(cal.getTime());
			evening.setTime(cal.getTime());
			moning.set(Calendar.HOUR_OF_DAY, 6);
			evening.set(Calendar.HOUR_OF_DAY, 20);
			
			if(cal.before(moning) || cal.after(evening)) {
				logarithm+=1;//晚上对数+1
			}
			
			if(logarithm>2.4) {//~75%
				deviceBehaviorResponse.setOdba("80");
			}else if(logarithm>2) {//~90%
				deviceBehaviorResponse.setOdba("90");
			}else {//0~2
				deviceBehaviorResponse.setOdba((80+((int)(4/logarithm))*10)+"");
			}
			
			deviceBehaviorResponse.setAction_type("0");
			deviceBehaviorResponse.setDevice_id(trackDeviceView.getId());
			deviceBehaviorResponse.setEstrus("0");
			deviceBehaviorResponse.setFirmware_version(trackDeviceView.getFirmwareVersion());
			deviceBehaviorResponse.setId(UUID.nameUUIDFromBytes((seed+63+"").getBytes()).toString().replace("-", "").substring(0,24));
			deviceBehaviorResponse.setMark(trackDeviceView.getMark());
			deviceBehaviorResponse.setUuid(trackDeviceView.getUuid());
			if(StringUtils.isNumeric(deviceBehaviorResponse.getOdba()) && Integer.valueOf(deviceBehaviorResponse.getOdba()) > 81) {
			    double steps = Math.floor(Math.sqrt((Integer.valueOf(deviceBehaviorResponse.getOdba())-80)));
			    deviceBehaviorResponse.setSteps(steps + ""); 
			}
			
			Calendar simulateDate=Calendar.getInstance();
			simulateDate.setTime(cal.getTime());
			simulateDate.add(Calendar.SECOND, random.nextInt(360));
			deviceBehaviorResponse.setTimestamp(TrackDeviceView.DATE_PARSE.format(simulateDate.getTime()));//时间戳yyyy-MM-dd'T'HH:mm:ss'Z'
			
			simulateDate.add(Calendar.SECOND,random.nextInt(60));
			simulateDate.add(Calendar.MILLISECOND,random.nextInt(1000));
			deviceBehaviorResponse.setUpdated_at(TrackDeviceView.DATE_PARSE_MILLISECOND.format(simulateDate.getTime()));//更新时间yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
			
			result.add(deviceBehaviorResponse);
		}
		
		return result;
	}
	
	private DeviceGpsResponse packageDeviceGpsResponse(Double longitude,Double latitude,Calendar cal,Random random,Long seed,TrackDeviceView trackDeviceView) {
		
		DeviceGpsResponse deviceGpsResponse=new DeviceGpsResponse();
		deviceGpsResponse.setLongitude(longitude+"");
		deviceGpsResponse.setLatitude(latitude+"");
		deviceGpsResponse.setAltitude((800+random.nextDouble()*350)+"");//海拔
		deviceGpsResponse.setUsed_star((5+random.nextInt(5))+"");//用于定位的卫星数
		deviceGpsResponse.setView_star((10+random.nextInt(15))+"");//发现的卫星数
		deviceGpsResponse.setId(UUID.nameUUIDFromBytes((seed+31+"").getBytes()).toString().replace("-", "").substring(0,24));//gps数据id
		deviceGpsResponse.setDevice_id( trackDeviceView.getId() );//t_track_device.id,gps设备id
		Integer speedR=random.nextInt(150);
		Integer speed=0;
		if(speedR>100) {
			speed=random.nextInt(20);
		}else if(speedR>80) {
			speed=3;
		}else if(speedR>55) {
			speed=2;
		}else if(speedR>30) {
			speed=1;
		}else {
			speed=0;
		}
		deviceGpsResponse.setSpeed(speed/10.0+"");//速度
		deviceGpsResponse.setFix_time((5+random.nextInt(95))+"");//定位耗时
		deviceGpsResponse.setHdop((80+random.nextInt(120))/100.0+"");//水平精度因子
		deviceGpsResponse.setVdop((80+random.nextInt(120))/100.0+"");//垂直精度因子
		deviceGpsResponse.setPdop("0");//位置精确因子
		Calendar simulateDate=Calendar.getInstance();
		simulateDate.setTime(cal.getTime());
		
		simulateDate.add(Calendar.SECOND, 240+random.nextInt(120));
		deviceGpsResponse.setTimestamp(TrackDeviceView.DATE_PARSE.format(simulateDate.getTime()));//时间戳yyyy-MM-dd'T'HH:mm:ss'Z'
		
		simulateDate.add(Calendar.SECOND,random.nextInt(60));
		simulateDate.add(Calendar.MILLISECOND,random.nextInt(1000));
		deviceGpsResponse.setUpdated_at(TrackDeviceView.DATE_PARSE_MILLISECOND.format(simulateDate.getTime()));//更新时间yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
		
		deviceGpsResponse.setSms("");
		deviceGpsResponse.setOwner("");
		deviceGpsResponse.setRelative_altitude("0");
		deviceGpsResponse.setGround_altitude("0");
		deviceGpsResponse.setTemperature("0");
		deviceGpsResponse.setHumidity("0");
		deviceGpsResponse.setLight("0");
		deviceGpsResponse.setPressure("0");
		deviceGpsResponse.setHorizontal("0");
		deviceGpsResponse.setVertical("0");
		deviceGpsResponse.setBattery_voltage("0");
		deviceGpsResponse.setSignal_strength("0");
		deviceGpsResponse.setPoint_location("1");
		deviceGpsResponse.setQuality("1");
		return deviceGpsResponse;
	}
	
	/**
	 * 得到区域外切矩形内的随机点
	 * @author 张琼麒
	 * @version 创建时间：2019年8月29日 下午1:52:46
	 * @param prairieArea
	 * @param random
	 * @return
	 */
	private PrairieAreaPoint getPointInRectangleByRandomSeed(PrairieArea prairieArea,Random random) {
		if(prairieArea.getMaxLongitude()==null) {
			return null;
		}
		PrairieAreaPoint prairieAreaPoint=new PrairieAreaPoint();
		
		prairieAreaPoint.setLongitude(new BigDecimal(prairieArea.getMinLongitude()+(random.nextDouble()*(prairieArea.getMaxLongitude()-prairieArea.getMinLongitude()))));
		prairieAreaPoint.setLatitude(new BigDecimal(prairieArea.getMinLatitude()+(random.nextDouble()*(prairieArea.getMaxLatitude()-prairieArea.getMinLatitude()))));
		
		return prairieAreaPoint;
	}
	

	/**
	判断点是否在多边形区域内
	W. Randolph Franklin 提出的PNPoly算法:
	以测试点为端点,画一条射线,此穿越多边形的边侧次数为奇数,则测试点在多边形内.
	具体实现时,射线平行于X轴或Y轴计算.
	例:
	测试点P[testX,testY]
	当testY在[Y[i],Y[j])中,
	经点P平行于X轴的直线与线段ij的交点在点A[assumeX,testY]上
	则有点段上的点的比例等式:
	(assumeX-X[j])/(testY-Y[j])=(X[i]-X[j])/(Y[i]-Y[j])
	assumeX=(X[i]-X[j])(testY-Y[j])/(Y[i]-Y[j])+X[j]
	当testX<assumeX时,点P在线段ij的左边,
	统计点P在多边形所有线段左边的次数,即为点P的右射线穿越多边形的次数
	为奇数则点P在多边形内
	
	网上给出的优化后的C算法:
	int pnpoly(int nvert, float *vertx, float *verty, float testx, float testy){
	  int i, j, c = 0;
	  for (i = 0, j = nvert-1; i < nvert; j = i++) {
	    if ( ((verty[i]>testy) != (verty[j]>testy)) &&
	     (testx < (vertx[j]-vertx[i]) * (testy-verty[i]) / (verty[j]-verty[i]) + vertx[i]) )
	       c = !c;
	  }
	  return c;
	}
	 * @author 张琼麒
	 * @version 创建时间：2019年8月29日 下午1:54:01
	 * @param testPoint
	 * @param prairieArea
	 * @return
	 */
	private Boolean pointInPolygon(PrairieAreaPoint testPoint,PrairieArea prairieArea) {
		Boolean result=false;
		if(!prairieArea.isEffectiveArea()) {
			return false;
		}
		List<PrairieAreaPoint> pointList=prairieArea.getPrairieAreaPointList();
		double testLongitude=testPoint.getLongitude().doubleValue();
		double testLatitude=testPoint.getLatitude().doubleValue();
		
		for(int i=0,j=pointList.size()-1;i<pointList.size();j=i++) {
			if( 
					(
							pointList.get(i).getLatitude().doubleValue()>testLatitude
							!= 
							pointList.get(j).getLatitude().doubleValue()>testLatitude
					) 
					&& 
					(
							testLongitude <
							( pointList.get(j).getLongitude().doubleValue() - pointList.get(i).getLongitude().doubleValue() )
							* ( testLatitude - pointList.get(i).getLatitude().doubleValue() )
							/ ( pointList.get(j).getLatitude().doubleValue() - pointList.get(i).getLatitude().doubleValue() )
							+ pointList.get(i).getLongitude().doubleValue()
					)
			) {
				result=!result;
			}
		}
		
		return result;
	}

	
	/**
	 * 策略计算,通过策略序列,匹配生成数据的时间,返回击中得区域id
	 * 
	 * @author 张琼麒
	 * @version 创建时间：2019年8月28日 下午6:09:14
	 * @param project
	 * @param date
	 * @return PrairieArea.id
	 */
	@Override
	public Long executedTactics(Date date, List<PrairieAreaTactics> tacticsList) {

		for (PrairieAreaTactics prairieAreaTactics : tacticsList) {
			CronExpression cronExpression = null;
			try {
				cronExpression = new CronExpression(prairieAreaTactics.getCronExpression());
			} catch (ParseException e) {
				e.printStackTrace();
				logger.info("PrairieAreaTacticsServiceImpl.executed():" + e.getMessage(), e);
				continue;
			}
			if (cronExpression.isSatisfiedBy(date)) {
				return prairieAreaTactics.getPrairieAreaId();
			}
		}
		return null;
	}
	
	@Override
	public TrackDevice calculateVirtualTrackDevice(TrackDeviceView trackDeviceView) throws Exception{
		TrackDevice trackDevice=new TrackDevice();
		Date now=new Date();
		//现在的整点
		Calendar nowHour = Calendar.getInstance();
		nowHour.set(Calendar.MINUTE, 0);
		nowHour.set(Calendar.SECOND, 0);
		nowHour.set(Calendar.MILLISECOND, 0);
		if(StringUtils.isBlank(trackDeviceView.getRealEarNumber())){
			throw new Exception("耳标号未指定");
		}
		
		//耳标号的hashCode
		Integer realEarNumberHashCode=trackDeviceView.getRealEarNumber().hashCode();
		//牧场id
		Long prairieId=null;
		if(!StringUtils.isBlank(trackDeviceView.getPrairieValue()) && StringUtils.isNumeric(trackDeviceView.getPrairieValue())) {
			prairieId = Long.parseLong(trackDeviceView.getPrairieValue());
		}
		
		PrairieAreaTacticsCacheAgency cacheAgency=this.doGetCacheAgency();
		//牧场默认区域
		PrairieArea defultPrairieArea=null;
		if(prairieId != null) {
			List<PrairieArea> innerPrairieAreaList=cacheAgency.listPrairieAreaByPrairieId(prairieId);
			if(innerPrairieAreaList!=null && innerPrairieAreaList.size()>0) {
				defultPrairieArea=innerPrairieAreaList.get(0);
			}
		}
		
		//组装策略
		List<PrairieAreaTactics> tacticsList = new ArrayList<PrairieAreaTactics>();
		//指定牛策略
		List<PrairieAreaTactics> byEarNumberList = PrairieAreaTacticsServiceImpl.this.listPrairieAreaTacticsByEarNumber(trackDeviceView.getRealEarNumber());
		if(byEarNumberList!=null) {
			tacticsList.addAll(byEarNumberList);
		}
		//牧场则略
		if (prairieId != null) {
			// 牧场id
			List<PrairieAreaTactics> byPrairieIdList = cacheAgency.listPrairieAreaTacticsByPrairieId(prairieId);
			if(byPrairieIdList!=null) {
				tacticsList.addAll(byPrairieIdList);
			}
		}
		Boolean doneFlag=false;
		int doneInsurance=20;
		nowHour.add(Calendar.HOUR, -1);
		PrairieAreaPoint testPoint=null;
		Random random=null;
		while(!doneFlag && --doneInsurance>0) {
			nowHour.add(Calendar.HOUR, 1);
			//随机种子,以耳标号.hashCode+生成数据的时间为随机种子
			Long seed=realEarNumberHashCode + nowHour.getTime().getTime();
			//随机数生成器
			random = new Random(seed);
			//策略计算,获得区域id
			Long prairieAreaId=this.executedTactics(nowHour.getTime(),  tacticsList);
			//对应区域
			PrairieArea prairieArea=null;
			if(prairieAreaId!=null) {
				prairieArea=cacheAgency.getPrairieAreaById(prairieAreaId);
			}
			if(prairieArea==null) {
				if(defultPrairieArea==null) {
					continue;
				}
				prairieArea=defultPrairieArea;
			}
			if(!prairieArea.isEffectiveArea()) {
				continue;
			}
			
			//while最大执行次数,防止死循环
			int insurance=20;
			do{
				testPoint=getPointInRectangleByRandomSeed(prairieArea,random);
			}while(--insurance>0 && !pointInPolygon(testPoint,prairieArea) );
			
			if(insurance<=0) {
				throw new Exception("计算错误");
			}
		}
		if(testPoint==null) {
			throw new Exception("计算错误");
		}
		
		String realEarNumberUUID=UUID.nameUUIDFromBytes(trackDeviceView.getRealEarNumber().getBytes()).toString().replace("-", "");
		trackDevice.setId(realEarNumberUUID.substring(0, 24) );
		trackDevice.setUuid(realEarNumberUUID.substring(24) + Integer.toHexString(realEarNumberUUID.hashCode()).substring(0,4));
		trackDevice.setDevice_type("10032");
		trackDevice.setHardware_version("2");
		trackDevice.setFirmware_version("200");
		trackDevice.setCompany_id("5d107f78879cb586135d5a04");
		trackDevice.setCompany_name("中亿云");
		trackDevice.setImsi("");
		trackDevice.setIccid("");
		trackDevice.setSim_number("");
		trackDevice.setMac("");
		trackDevice.setMark(trackDeviceView.getRealEarNumber());
		trackDevice.setStock_time("2019-06-24T09:39:14Z");
		trackDevice.setStatus("0");
		trackDevice.setLongitude(testPoint.getLongitude()+"");
		trackDevice.setLatitude(testPoint.getLatitude()+"");
		
		
		Calendar simulateDate=Calendar.getInstance();
		simulateDate.setTime(nowHour.getTime());
		simulateDate.add(Calendar.HOUR,-1);
		simulateDate.add(Calendar.SECOND, 240+random.nextInt(120));
		
		trackDevice.setBeh_timestamp(TrackDeviceView.DATE_PARSE.format(simulateDate.getTime()));
		simulateDate.add(Calendar.SECOND,random.nextInt(60));
		simulateDate.add(Calendar.MILLISECOND,random.nextInt(1000));
		
		trackDevice.setUpdated_at(TrackDeviceView.DATE_PARSE_MILLISECOND.format(simulateDate.getTime()));
		trackDevice.setSync_at(now);
		
		trackDevice.setSn(realEarNumberUUID);
		trackDevice.setNickname(trackDeviceView.getRealEarNumber());
		
		trackDevice.setIs_virtual(true);
		
		return trackDevice;
		
	}
}
