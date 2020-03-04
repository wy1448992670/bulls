package com.goochou.p2b.app.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.constant.farmcloud.Constants;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.openapi.farmcloud.CommonResponse;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceBehaviorReqeust;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceBehaviorResponse;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceGpsRequest;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceGpsResponse;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceRequest;
import com.goochou.p2b.model.TrackDevice;
import com.goochou.p2b.model.TrackDeviceView;
import com.goochou.p2b.model.TrackDeviceViewExample;
import com.goochou.p2b.service.PrairieAreaTacticsService;
import com.goochou.p2b.service.TrackDeviceService;
import com.goochou.p2b.utils.DateUtil;
import com.goochou.p2b.utils.StringUtils;


/**
 * @Auther: huangsj
 * @Date: 2019/7/2 16:45
 * @Description:
 */
@Controller
@RequestMapping(value = "druidtech")
@Api(value = "Gps定位设备接口")
public class DruidtechGpsController extends BaseController {

    private static final Logger logger = Logger.getLogger(DruidtechGpsController.class);

    @Resource
    TrackDeviceService trackDeviceService;
    
    @Resource
    PrairieAreaTacticsService prairieAreaTacticsService;
    /**
     * 查看所有设备
     */
    @RequestMapping(value = "/device/realTime", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查看所有设备")
    public AppResult getDevicesRealTime(
            @ApiParam("app版本号") @RequestParam(required = true) String appVersion,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam(required = true) String client,
            @ApiParam("每页显示的个数") @RequestParam(required = true) Integer pageSize,
            @ApiParam("当前页数") @RequestParam(required = true) Integer curPage
    ) {

        logger.info("用户通过" + client + "查看所有设备。。。");
        try {

            if (pageSize == 0) {
                pageSize = 100;
            }

            DeviceRequest req = new DeviceRequest();
            req.setAuthentication(Constants.DRUIDTECH_TOKEN);
            req.setResultLimit(pageSize.toString());
            req.setResultOffset(curPage.toString());
            req.setResultSort("mark");

            ServiceMessage msg = new ServiceMessage("druidtech.devices", req);
            CommonResponse<List<TrackDevice>> result = (CommonResponse<List<TrackDevice>>) OpenApiClient.getInstance().setServiceMessage(msg).send();

            Map<String, Object> returnMap = new HashMap<String, Object>(16);
            if (result.isSuccess()) {
                String datas = JSONObject.toJSONString(result.getData(), SerializerFeature.WriteMapNullValue,
                        SerializerFeature.WriteNullNumberAsZero,
                        SerializerFeature.WriteNullStringAsEmpty,
                        SerializerFeature.WriteNullBooleanAsFalse,
                        SerializerFeature.WriteNullListAsEmpty);
                returnMap.put("devices", datas);
                return new AppResult(SUCCESS, returnMap);
            } else {
                return new AppResult(FAILED, MESSAGE_EXCEPTION);
            }
        } catch (Exception e) {
            logger.error("getDevices====>出错" + e.getMessage(), e);
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }
    
    /**
     * 查看所有设备
     */
    @RequestMapping(value = "/device", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查看所有设备")
    public AppResult getDevices(
            @ApiParam("app版本号") @RequestParam(required = true) String appVersion,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam(required = true) String client,
            @ApiParam("牧场代号") @RequestParam(required = false) String prairieValue,
            @ApiParam("真实耳标号") @RequestParam(required = false) String realEarNumber,
            @ApiParam("耳标号") @RequestParam(required = false) String earNumber,
            @ApiParam("每页显示的个数") @RequestParam(required = false) Integer pageSize,
            @ApiParam("当前页数") @RequestParam(required = false) Integer curPage ) {

        logger.info("用户通过" + client + "查看所有设备。。。");
        try {
            if (pageSize != null && pageSize < 1) {
                pageSize = 100;
            }
            if (curPage == null || curPage < 1) {
            	curPage = 1;
            }
            TrackDeviceViewExample example=new TrackDeviceViewExample();
            if (pageSize != null) {
            	example.setLimitStart((curPage-1) * pageSize);
            	example.setLimitEnd(pageSize);
            }
            if(prairieValue==null && realEarNumber==null && earNumber==null) {
            	prairieValue="1";
            }
            
            TrackDeviceViewExample.Criteria criteria=example.createCriteria();
            criteria.andIdIsNotNull();
            if(prairieValue != null) {
            	criteria.andPrairieValueEqualTo(prairieValue);
            }
            if(realEarNumber !=null) {
            	criteria.andRealEarNumberEqualTo(realEarNumber);
            }else if(earNumber !=null) {//兼容老的设备拉取接口
            	criteria.andEarNumberEqualTo(earNumber);
            }
            
            List<TrackDeviceView> trackDeviceViewList=trackDeviceService.getTrackDeviceViewMapper().selectByExample(example);
            Long count = trackDeviceService.getTrackDeviceViewMapper().countByExample(example);
            
            int pageCount = 1;
            if (pageSize != null) {
            	pageCount = calcPage(count.intValue(), pageSize);
            }else {
            	pageSize=count.intValue();
            }
            
            Map<String, Object> returnMap = new HashMap<String, Object>(16);
            returnMap.put("devices", trackDeviceViewList);
            returnMap.put("count", count);
            returnMap.put("curPage", curPage);
            returnMap.put("pageSize", pageSize);
            returnMap.put("pageCount", pageCount);
            
            return new AppResult(SUCCESS, returnMap);
           
        } catch (Exception e) {
            logger.error("getDevices====>出错" + e.getMessage(), e);
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    /**
     * 查看设备gps数据
     */
    @RequestMapping(value = "/device/{realEarNumber}/gps/last/{days}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查看设备的gps")
    public AppResult getDeviceGps(
            @ApiParam("app版本号") @RequestParam(required = true) String appVersion,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam(required = true) String client,
            @ApiParam("真实耳标号") @PathVariable("realEarNumber") String realEarNumber,
            @ApiParam("最近几天的数据") @PathVariable("days") Integer days
    ) {

        logger.info("用户通过" + client + "查看牛只"+realEarNumber+"的gps数据。。。");
        try {
        	
        	if (days==null || days==0) {
                days = 1;
            }
        	days=1;

        	//通过earNumber查找设备信息
        	TrackDeviceViewExample example=new TrackDeviceViewExample();
    		example.createCriteria().andRealEarNumberEqualTo(realEarNumber);
    		List<TrackDeviceView> trackDeviceViewList=trackDeviceService.getTrackDeviceViewMapper().selectByExample(example);
        	if(trackDeviceViewList==null || trackDeviceViewList.size()!=1) {
        		return new AppResult(FAILED, "设备对应出错");//耳标号对应多个设备
        	}
        	
        	//有对应的设备,通过gpsId调用德鲁伊接口,获取GPS路径信息
        	List<DeviceGpsResponse> resultDate=null;
        	
    		TrackDeviceView trackDeviceView=trackDeviceViewList.get(0);
    		if(trackDeviceView!=null &&  !StringUtils.isBlank(trackDeviceView.getId()) ){
    			if( !trackDeviceView.getIsVirtual()) {
            		DeviceGpsRequest req = new DeviceGpsRequest();
                    req.setAuthentication(Constants.DRUIDTECH_TOKEN);
                    req.setId(trackDeviceView.getId());//设备id|gpsId
                    req.setDay(days.toString());
                    ServiceMessage msg = new ServiceMessage("druidtech.device.gps", req);
                    CommonResponse<List<DeviceGpsResponse>> apiGpsResponse = (CommonResponse<List<DeviceGpsResponse>> )OpenApiClient.getInstance().setServiceMessage(msg).send();
                    System.out.println("druidtech.device.gps:"+apiGpsResponse);
                    if(apiGpsResponse!=null && apiGpsResponse.isSuccess()) {
                    	if(apiGpsResponse.getData()!=null && apiGpsResponse.getData().size()>1) {
                    		resultDate=apiGpsResponse.getData();
                    	}
                    }
            	}
    			
    			if(resultDate==null) {
            		List<DeviceGpsResponse> calculateGpsList=prairieAreaTacticsService.calculateGpsList(trackDeviceView,days);
            		if(calculateGpsList!=null || calculateGpsList.size()>1) {
            			resultDate=calculateGpsList;
            		}
            	}
    		}
        	
            Map<String, Object> returnMap = new HashMap<String, Object>(16);
            if (resultDate!=null) {
                String datas = JSONObject.toJSONString(resultDate, SerializerFeature.WriteMapNullValue,
                        SerializerFeature.WriteNullNumberAsZero,
                        SerializerFeature.WriteNullStringAsEmpty,
                        SerializerFeature.WriteNullBooleanAsFalse,
                        SerializerFeature.WriteNullListAsEmpty);
                returnMap.put("gps", datas);
                return new AppResult(SUCCESS, returnMap);
            } else {
                return new AppResult(FAILED, MESSAGE_EXCEPTION);
            }
        } catch (Exception e) {
            logger.error("getDevices====>出错" + e.getMessage(), e);
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }


    /**
     * 查看设备行为数据
     */
    @RequestMapping(value = "/device/{realEarNumber}/behavior", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查看设备行为数据")
    public AppResult getDeviceBehavior(
            @ApiParam("app版本号") @RequestParam(required = true) String appVersion,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam(required = true) String client,
            //@ApiParam("设备编号") @PathVariable("id") String deviceId,
            @ApiParam("真实耳标号") @PathVariable("realEarNumber") String realEarNumber,
            @ApiParam("接口返回的个数不超过1000") @RequestParam(required = true) Integer count,
            @ApiParam("从什么时间开始的") @RequestParam(required = true) Date startTime
    ) {

        logger.info("用户通过" + client + "查看设备"+realEarNumber+"从"+startTime+"开始后的行为数据。。。");
        try {
        	
        	//通过earNumber查找设备信息
        	TrackDeviceViewExample example=new TrackDeviceViewExample();
    		example.createCriteria().andRealEarNumberEqualTo(realEarNumber);
    		List<TrackDeviceView> trackDeviceViewList=trackDeviceService.getTrackDeviceViewMapper().selectByExample(example);
        	if(trackDeviceViewList==null || trackDeviceViewList.size()!=1) {
        		return new AppResult(FAILED, "设备对应出错");//耳标号对应多个设备
        	}
        	//有对应的设备,通过gpsId调用德鲁伊接口,获取设备行为数据
        	List<DeviceBehaviorResponse> resultDate=null;
        	
    		TrackDeviceView trackDeviceView=trackDeviceViewList.get(0);
    		if(trackDeviceView!=null && !StringUtils.isBlank(trackDeviceView.getId()) ) {
    			if(!trackDeviceView.getIsVirtual()) {
    				DeviceBehaviorReqeust req = new DeviceBehaviorReqeust();
    				req.setAuthentication(Constants.DRUIDTECH_TOKEN);
    				req.setResultLimit(count.toString());
    				req.setResultSort("timestamp");
    				req.setId(trackDeviceView.getId());
    				req.setStartTime(DateUtil.getStrRfc3339Time(startTime));

    				ServiceMessage msg = new ServiceMessage("druidtech.device.behavior", req);
    				CommonResponse<List<DeviceBehaviorResponse>> apiGpsResponse = (CommonResponse<List<DeviceBehaviorResponse>>) OpenApiClient
    						.getInstance().setServiceMessage(msg).send();
    				System.out.println("druidtech.device.behavior:" + apiGpsResponse);
    				if (apiGpsResponse != null && apiGpsResponse.isSuccess()) {
    					if (apiGpsResponse.getData() != null && apiGpsResponse.getData().size() > 1) {
    						resultDate = apiGpsResponse.getData();
    					}
    				}
    			}
    			
    			if(resultDate==null) {
            		List<DeviceBehaviorResponse> calculateBehaviorList=prairieAreaTacticsService.calculateBehaviorList(trackDeviceView,startTime,count);
            		if(calculateBehaviorList!=null || calculateBehaviorList.size()>1) {
            			resultDate=calculateBehaviorList;
            		}
            	}
    		}

            Map<String, Object> returnMap = new HashMap<String, Object>(16);
            if (resultDate!=null) {
                String datas = JSONObject.toJSONString(resultDate, SerializerFeature.WriteMapNullValue,
                        SerializerFeature.WriteNullNumberAsZero,
                        SerializerFeature.WriteNullStringAsEmpty,
                        SerializerFeature.WriteNullBooleanAsFalse,
                        SerializerFeature.WriteNullListAsEmpty);
                returnMap.put("behaviors", datas);
                return new AppResult(SUCCESS, returnMap);
            } else {
                return new AppResult(FAILED, MESSAGE_EXCEPTION);
            }
        } catch (Exception e) {
            logger.error("getDevices====>出错" + e.getMessage(), e);
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }
}
