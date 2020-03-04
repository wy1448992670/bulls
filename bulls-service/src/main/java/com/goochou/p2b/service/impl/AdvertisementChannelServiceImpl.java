package com.goochou.p2b.service.impl;

import javax.annotation.Resource;

import com.goochou.p2b.constant.DictConstants;
import com.goochou.p2b.constant.MessageChannelEnum;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.openapi.message.SendMessageRequest;
import com.goochou.p2b.hessian.openapi.message.SendMessageResponse;
import com.goochou.p2b.model.bo.AdvertisementChannelRegisterUserBO;
import com.goochou.p2b.service.SmsSendService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.AdvertisementChannelMapper;
import com.goochou.p2b.model.AdvertisementChannel;
import com.goochou.p2b.model.AdvertisementChannelExample;
import com.goochou.p2b.model.AdvertisementChannelExample.Criteria;
import com.goochou.p2b.model.vo.AdvertisementChannelSumVo;
import com.goochou.p2b.service.AdvertisementChannelService;
import com.goochou.p2b.service.exceptions.LockFailureException;

import java.util.*;

@Service
public class AdvertisementChannelServiceImpl implements AdvertisementChannelService {
    private final static Logger logger = Logger.getLogger(AdvertisementChannelServiceImpl.class);

    @Resource
    private AdvertisementChannelMapper advertisementChannelMapper;
    
    @Resource
    private SmsSendService smsSendService;

    @Override
    public AdvertisementChannelMapper getAdvertisementChannelMapper() {
		return advertisementChannelMapper;
	}

	@Override
    public int updateForVersion(AdvertisementChannel advertisementChannel) throws Exception {
        if (advertisementChannel.getId() == null) {
            throw new Exception("id不能为空");
        }
        if (advertisementChannel.getVersion() == null) {
            throw new Exception("版本号不能为空");
        }
        AdvertisementChannelExample example = new AdvertisementChannelExample();
        example.createCriteria()
                .andIdEqualTo(advertisementChannel.getId())
                .andVersionEqualTo(advertisementChannel.getVersion());
        advertisementChannel.setVersion(advertisementChannel.getVersion() + 1);
        if(1 != advertisementChannelMapper.updateByExampleSelective(advertisementChannel, example)) {
        	throw new LockFailureException();
        }
        return 1;
    }

    @Override
    public AdvertisementChannelMapper getMapper() {
        return this.advertisementChannelMapper;
    }

    @Override
    public int save(AdvertisementChannel advertisementChannel) throws Exception {
        Date now = new Date();
        advertisementChannel.setUpdateTime(now);
        String guizeText = advertisementChannel.getGuizeText();
        guizeText = guizeText.replaceAll("\n", "");
        guizeText = guizeText.replaceAll("\r", "");
        advertisementChannel.setGuizeText(guizeText);
        if (advertisementChannel.getId() == null) { // 新增
            advertisementChannel.setCreateTime(now);
            // 默认打开
            advertisementChannel.setStatus(1);
            return advertisementChannelMapper.insertSelective(advertisementChannel);
        } else { // 修改
            return this.updateForVersion(advertisementChannel);
        }
    }

    @Override
    public boolean validChannelNo (String channelNo) {
        AdvertisementChannelExample example = new AdvertisementChannelExample();
        example.createCriteria().andChannelNoEqualTo(channelNo);
        List<AdvertisementChannel> list = advertisementChannelMapper.selectByExample(example);
        if (list.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

	@Override
	public List<AdvertisementChannel> list(String channelNo, String channelName, Integer channelType, Integer status,
			Date startTime, Date endTime,Integer limitStart, Integer limit) {
		AdvertisementChannelExample example = listCondition(channelNo, channelName, channelType, status, startTime, endTime,limitStart,limit,"id desc");
		return advertisementChannelMapper.selectByExample(example);
	}

	@Override
	public int countList(String channelNo, String channelName, Integer channelType, Integer status, Date startTime, Date endTime) {
		AdvertisementChannelExample example = listCondition(channelNo, channelName, channelType, status, startTime, endTime,null ,null,null);
		return (int) advertisementChannelMapper.countByExample(example);
	}
	
	@Override
	public List<AdvertisementChannelSumVo>  selectSum(String channelNo, String channelName, Integer channelType,Integer status,
			Date userCreateDateStart, Date userCreateDateEnd,Integer limitStart, Integer limit) {
		Map<String,Object> prarm=new HashMap<String,Object>();
		if(userCreateDateStart!=null) {
			prarm.put("userCreateDateStart", userCreateDateStart);
		}
		if(userCreateDateEnd!=null) {
			prarm.put("userCreateDateEnd", userCreateDateEnd);
		}
		AdvertisementChannelExample example = listCondition(channelNo, channelName, channelType, status,null, null,limitStart,limit,"id");
		return advertisementChannelMapper.selectSum(prarm,example);
	}

	private static AdvertisementChannelExample listCondition(String channelNo, String channelName, Integer channelType, Integer status,
			Date startTime, Date endTime,Integer limitStart, Integer limit,String orderByClause) {
		AdvertisementChannelExample  example = new AdvertisementChannelExample();
		Criteria cri = example.createCriteria();
		if(StringUtils.isNotBlank(channelName)){
			channelName = "%" + channelName + "%";
			cri.andChannelNameLike(channelName);
		}
		if(StringUtils.isNotBlank(channelNo)) {
			channelNo = "%" + channelNo + "%";
			cri.andChannelNoLike(channelNo);
		}
		if(channelType != null) {
			cri.andChannelTypeEqualTo(channelType);
		}
		if(status != null) {
		    cri.andStatusEqualTo(status);
		}
		if(startTime != null) {
			cri.andCreateTimeGreaterThanOrEqualTo(startTime);
		}
		if(endTime != null) {
			cri.andCreateTimeLessThanOrEqualTo(endTime);
		}

		if(limitStart != null && limit != null) {
			example.setLimitStart(limitStart);
		    example.setLimitEnd(limit);
		    
		}
		if(orderByClause!=null) {
			example.setOrderByClause(orderByClause);//"id desc"
		}
		return example;
	}

    @Override
    public void doSendMessage(AdvertisementChannelRegisterUserBO bo) {
        String messageContent = DictConstants.SEND_ADVERTISEMENT_CHANNEL_CODE;
        String password = "Bf" + bo.getPhone().substring(bo.getPhone().length() -6); // 默认密码为 Bf + 用户手机号后六位
        messageContent = messageContent
                .replace("{channelName}", bo.getChannelName())
                .replace("{channelUrl}", bo.getChannelUrl())
                .replace("{password}", password);
        Date now = new Date();
        // 设置发送时间（每天下午八点）
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.set(Calendar.HOUR_OF_DAY, 20);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        System.out.println(DateUtil.fomartDate(c.getTime()));
        try {
            // TODO 存在并发问题
            smsSendService.addSmsSend(bo.getPhone(), messageContent, MessageChannelEnum.DH3T, c.getTime());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

}
