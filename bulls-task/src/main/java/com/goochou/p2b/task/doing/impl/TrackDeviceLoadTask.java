package com.goochou.p2b.task.doing.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSON;
import com.goochou.p2b.constant.farmcloud.Constants;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.openapi.farmcloud.CommonResponse;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceRequest;
import com.goochou.p2b.model.TrackDevice;
import com.goochou.p2b.service.HongbaoService;
import com.goochou.p2b.service.InvestmentService;
import com.goochou.p2b.service.TrackDeviceService;

/**
 * Created on 2019-07-05
 * <p>Title:       [GPS设备装载数据]</p>
 * <p>Department:  研发中心</p>
 * @author         [张琼麒] [259392141@qq.com]
 * @version        1.0
 */
public class TrackDeviceLoadTask extends BaseTask {

	private static final long serialVersionUID = 2026304358917957666L;
	private static final Logger logger = Logger.getLogger(TrackDeviceLoadTask.class);
	
	final Integer OPERATOR_PAGE_SIZE=1000;
	
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    	logger.info("------GPS设备load start------");
    	TrackDeviceService trackDeviceService=(TrackDeviceService) applicationContext.getBean("trackDeviceServiceImpl");
        Integer currentPage=0;
        
        while(true) {
        	List<TrackDevice> deviceResponeList;
        	DeviceRequest req = new DeviceRequest();
            req.setAuthentication(Constants.DRUIDTECH_TOKEN);
            req.setResultLimit(OPERATOR_PAGE_SIZE.toString());
            req.setResultOffset(currentPage*OPERATOR_PAGE_SIZE+"");
            //req.setResultSort("id");
            req.setResultSort("mark");
            try {
            	ServiceMessage msg = new ServiceMessage("druidtech.devices", req);
                CommonResponse<List<TrackDevice>> result = (CommonResponse<List<TrackDevice>>) OpenApiClient.getInstance().setServiceMessage(msg).send();
                if (result!=null && result.isSuccess()) {
                	
                	deviceResponeList =result.getData();
                } else {
                	logger.info("GPS设备load定时任务 druidtech.devices调用失败 end------");
                	break;
                }
    		} catch (Exception e) {
    			logger.error(e.getMessage(),e);
    			break;
    		}
            
            if(deviceResponeList!=null) {
            	System.out.println("deviceResponeList:"+deviceResponeList.size()+" "+JSON.toJSONString(deviceResponeList));
            	trackDeviceService.doLoadTrackDevice(deviceResponeList);
            	if(deviceResponeList.size()==OPERATOR_PAGE_SIZE) {
            		currentPage++;
            	}else {
            		break;
            	}
            }
        }
        
        trackDeviceService.doCompileVirtualTrackDevice();
        logger.info("------GPS设备load end------");
    }
}
