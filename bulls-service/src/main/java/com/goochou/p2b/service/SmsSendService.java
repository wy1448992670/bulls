 package com.goochou.p2b.service;

import java.util.Date;

import com.goochou.p2b.constant.MessageChannelEnum;
import com.goochou.p2b.dao.SmsSendMapper;
import com.goochou.p2b.model.SmsSend;

/**
 * @author sxy
 * @date 2019/10/18
 */
public interface SmsSendService {

    SmsSendMapper getMapper();
    
    void addSmsSend(String mobile, String content, MessageChannelEnum messageChannelEnum, Date sendTime) throws Exception;
    
    void doSendMessage(SmsSend smsSend);
    
}
