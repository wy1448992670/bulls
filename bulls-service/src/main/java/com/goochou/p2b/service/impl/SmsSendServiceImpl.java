 package com.goochou.p2b.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.openapi.message.SendMessageRequest;
import com.goochou.p2b.hessian.openapi.message.SendMessageResponse;
import com.goochou.p2b.model.SmsSendExample;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.MessageChannelEnum;
import com.goochou.p2b.constant.TestEnum;
import com.goochou.p2b.dao.SmsSendMapper;
import com.goochou.p2b.model.SmsSend;
import com.goochou.p2b.service.SmsSendService;

/**
 * @author zzy
 * @date 2019/10/18
 */
@Service
public class SmsSendServiceImpl implements SmsSendService {

    private static final Logger logger = Logger.getLogger(SmsSendServiceImpl.class);

    @Resource
    private SmsSendMapper smsSendMapper;
    
    @Override
    public SmsSendMapper getMapper() {
        return smsSendMapper;
    }

    @Override
    public void addSmsSend(String mobile,String content, MessageChannelEnum messageChannelEnum, Date sendTime) throws Exception {
    	if(messageChannelEnum==null) {
        	messageChannelEnum=MessageChannelEnum.DH3T;
        }
        SmsSend smsSend = new SmsSend();
        smsSend.setReserveSendTime(sendTime);
        smsSend.setSendChannelId(messageChannelEnum.getFeatureType());
        smsSend.setSendStatus(0);
        smsSend.setMobile(mobile);
        smsSend.setContent(content);
        smsSend.setCreateTime(new Date());
        if(this.getMapper().insertSelective(smsSend)!=1) {
        	throw new Exception("插入短信表异常");
        }
    }

    @Override
    public void doSendMessage(SmsSend smsSend) {
    	try {
        	smsSend=smsSendMapper.selectByPrimaryKeyForUpdate(smsSend.getId());
        	if(!smsSend.getSendStatus().equals(0)) {
        		logger.info("用户手机号为：" + smsSend.getMobile() + "，短信发送记录已发送");
                return;
        	}
        	
        	if(TestEnum.DEBUG.getFeatureName().equals(Constants.TEST_SWITCH)){
        		smsSend.setSendTime(new Date());
        		smsSend.setSendStatus(1); // 更新为 已发
        		smsSendMapper.updateByPrimaryKeySelective(smsSend);
            }else {
            	smsSend.setSendTime(new Date());
            	smsSend.setSendStatus(-1); // 更新为 失败
            	smsSendMapper.updateByPrimaryKeySelective(smsSend);
            	// 发送短信
                SendMessageRequest smr = new SendMessageRequest();
                smr.setContent(smsSend.getContent());
                smr.setPhone(smsSend.getMobile());
                smr.setMarket(true);
                //smr.setChannel(MessageChannelEnum.getValueByType(smsSend.getSendChannelId()).getFeatureName());
                ServiceMessage msg = new ServiceMessage("message.send", smr);
                SendMessageResponse sendMessageResponse = (SendMessageResponse) OpenApiClient.getInstance()
                        .setServiceMessage(msg).send();
                if (sendMessageResponse.isSuccess()) {
                    smsSend.setSendStatus(1); // 更新为 已发
                    smsSendMapper.updateByPrimaryKeySelective(smsSend);
                } else {
                    logger.error("用户手机号为：" + smsSend.getMobile() + "，短信发送失败！！！");
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }

    }

}
