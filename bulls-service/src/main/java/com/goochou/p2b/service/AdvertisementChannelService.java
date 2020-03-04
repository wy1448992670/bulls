package com.goochou.p2b.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.goochou.p2b.dao.AdvertisementChannelMapper;
import com.goochou.p2b.model.AdvertisementChannel;
import com.goochou.p2b.model.bo.AdvertisementChannelRegisterUserBO;
import com.goochou.p2b.model.vo.AdvertisementChannelSumVo;

public interface AdvertisementChannelService {
	AdvertisementChannelMapper getAdvertisementChannelMapper();
	
	int updateForVersion(AdvertisementChannel advertisementChannel) throws Exception;
	

	AdvertisementChannelMapper getMapper();

	int save (AdvertisementChannel advertisementChannel) throws Exception;

	/**
	 * 验证渠道号是否存在  true 存在  false 不存在 <br/>
	 * <>
	 * @author shuys
	 * @date 2019/10/12
	 * @param channelNo 渠道编号
	 * @return boolean
	*/
	boolean validChannelNo (String channelNo);
	
	List<AdvertisementChannel> list(String channelNo, String channelName, Integer channelType, Integer status, Date startTime, Date endTime, Integer limitStart, Integer limit);
	
	int countList(String channelNo, String channelName, Integer channelType, Integer status, Date startTime, Date endTime);

	void doSendMessage(AdvertisementChannelRegisterUserBO bo);

	List<AdvertisementChannelSumVo> selectSum(String channelNo, String channelName, Integer channelType,Integer status,
			Date userCreateDateStart, Date userCreateDateEnd, Integer limitStart, Integer limit);
}
