package com.goochou.p2b.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.model.TrackDevice;
import com.goochou.p2b.service.TrackDeviceService;

/**
 * 行动轨迹相关
 * 
 * @ClassName: TrackDeviceController
 * @author zj
 * @date 2019-07-05 11:01
 */
@Controller
@RequestMapping(value = "track")
@Api(value = "行动轨迹")
public class TrackDeviceController extends BaseController {

	private static final Logger logger = Logger.getLogger(TrackDeviceController.class);

	@Autowired
	TrackDeviceService trackDeviceService;

	/**
	 * 项目详细信息
	 *
	 * @param request
	 * @param gpsNumber gps设备编号
	 * @return
	 * @author: zj
	 */
	@RequestMapping(value = "/id/{gpsNumber}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "通过GPS编号获取行动轨迹ID")
	public AppResult id(HttpServletRequest request, @PathVariable String gpsNumber) {
		logger.info("==============查询gps设备id======================");
		try {
			TrackDevice trackdevice = trackDeviceService.getTrackdevice(gpsNumber);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("trackId", trackdevice.getId());
			return new AppResult(SUCCESS,SUCCESS_MSG, map);
		} catch (Exception e) {
			logger.error("获取行动轨迹ID出错===========>" + e.getMessage(), e);
			return new AppResult(FAILED, MESSAGE_EXCEPTION);
		}
	}

}
