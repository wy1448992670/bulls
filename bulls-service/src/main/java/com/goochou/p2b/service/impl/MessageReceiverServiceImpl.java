package com.goochou.p2b.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.goochou.p2b.constant.MessageReceiverStatusEnum;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.MessageMapper;
import com.goochou.p2b.dao.MessageReceiverMapper;
import com.goochou.p2b.model.Message;
import com.goochou.p2b.model.MessageReceiver;
import com.goochou.p2b.model.MessageReceiverExample;
import com.goochou.p2b.model.MessageReceiverExample.Criteria;
import com.goochou.p2b.service.MessageReceiverService;

@Service
public class MessageReceiverServiceImpl implements MessageReceiverService {
    @Resource
    private MessageReceiverMapper messageReceiverMapper;
    @Resource
    private MessageMapper messageMapper;

    // 获取用户信息列表
    @Override
    public List<MessageReceiver> list(Integer receiverId, Integer status, Integer start, Integer limit) {
        MessageReceiverExample example = new MessageReceiverExample();
        Criteria c = example.createCriteria();
        if (receiverId != null && receiverId >= 0) {
            c.andReceiverIdEqualTo(receiverId);
        }
        if (status != null) {
            c.andStatusEqualTo(status);
        }
        example.setOrderByClause("receive_time desc");
        example.setLimitEnd(limit);
        example.setLimitStart(start);
        List<MessageReceiver> messageReceivers = messageReceiverMapper.selectByExample(example);
        if (messageReceivers != null && !messageReceivers.isEmpty()) {
            for (MessageReceiver mr : messageReceivers) {
                Message message = messageMapper.selectByPrimaryKey(mr.getMessageId());
                mr.setMessage(message);
            }
        }
        return messageReceivers;
    }

    @Override
    public Integer listCount(Integer receiverId, Integer status) {
        MessageReceiverExample example = new MessageReceiverExample();
        Criteria c = example.createCriteria();
        if (receiverId != null && receiverId >= 0) {
            c.andReceiverIdEqualTo(receiverId);
        }
        if (status != null) {
            c.andStatusEqualTo(status);
        }
        return messageReceiverMapper.countByExample(example);
    }

    // 删除消息时，如果选中的记录中 状态不是 已读 的禁止删除
    private boolean checkUpdateStatus(List<Integer> id){
        boolean flag = true;
        MessageReceiverExample example = new MessageReceiverExample();
        example.createCriteria().andIdIn(id).andStatusNotEqualTo(MessageReceiverStatusEnum.DELETE.getCode());
        List<MessageReceiver> list = messageReceiverMapper.selectByExample(example);
        for (MessageReceiver messageReceiver: list) {
            int mrStatus = messageReceiver.getStatus();
            // (0.未读，1.已读，2.删除)
            if (mrStatus == MessageReceiverStatusEnum.ALREADY_READ.getCode()
                || mrStatus == MessageReceiverStatusEnum.DELETE.getCode()) { // 如果是已读
                continue;
            }
            flag = false;
            break;
        }
        return flag;
    }

    @Override
    public boolean update(List<Integer> id, Integer status) throws Exception {
//        MessageReceiver m = messageReceiverMapper.selectByPrimaryKey(id);
        boolean flag = true;
        MessageReceiverExample example = new MessageReceiverExample();
        Criteria criteria = example.createCriteria();
        criteria.andIdIn(id);

        MessageReceiver messageReceiver = new MessageReceiver();

        if (status.equals(MessageReceiverStatusEnum.DELETE.getCode())) { // 删除 操作
            messageReceiver.setStatus(status);
            // 检查是否满足删除条件
            flag = this.checkUpdateStatus(id);
            if (flag) {
                criteria.andStatusEqualTo(MessageReceiverStatusEnum.ALREADY_READ.getCode());
                messageReceiverMapper.updateByExampleSelective(messageReceiver, example);
            }
        }
        if (status.equals(MessageReceiverStatusEnum.ALREADY_READ.getCode())) { // 已读 操作
            criteria.andStatusEqualTo(MessageReceiverStatusEnum.UNREAD.getCode());
            messageReceiver.setStatus(status);
            messageReceiver.setReadTime(new Date());
            messageReceiverMapper.updateByExampleSelective(messageReceiver, example);
        }
        return flag;
    }

    @Override
    public List<Map<String, Object>> listAppMessage(Integer userId, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("start", start);
        map.put("limit", limit);
        return messageReceiverMapper.listAppMessage(map);
    }

    @Override
    public Integer listAppMessageCount(Integer userId, Integer status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("status", status);
        return messageReceiverMapper.listAppMessageCount(map);
    }

    @Override
    public Integer getUnReadCount(Integer userId) {
        MessageReceiverExample example = new MessageReceiverExample();
        Criteria c = example.createCriteria();
        c.andReceiverIdEqualTo(userId);
        c.andStatusEqualTo(0);
        return messageReceiverMapper.countByExample(example);
    }

    @Override
    public Integer save(Integer id, Integer messageId, Integer receiverId, Integer status, Date receiveTime, Date readTime) {
        MessageReceiver messageReceiver = new MessageReceiver();
        messageReceiver.setId(id);
        messageReceiver.setMessageId(messageId);
        messageReceiver.setReceiverId(receiverId);
        messageReceiver.setStatus(status);
        messageReceiver.setReceiveTime(receiveTime);
        messageReceiver.setReadTime(readTime);
        return messageReceiverMapper.insertSelective(messageReceiver);
    }

    @Override
    public void deleteByMessageId(Integer messageId) {
        List<Map<String, Object>> deleleList = messageReceiverMapper.queryByMessageId(messageId);
        List<Integer> list = new ArrayList<Integer>();
        for (Map<String, Object> map : deleleList) {
            Integer status = (Integer) map.get("status");
            if (status == 0) {
                messageReceiverMapper.deleteByPrimaryKey((Integer) map.get("id"));
            }
        }
        // if (list.size() > 0) {
        // messageReceiverMapper.deleteByMessageId(list);
        // }
    }

    @Override
    public void updateAllMessage(Integer userId) {
             messageReceiverMapper.updateAllMessage(userId);
    }

    @Override
    public Integer selectReadMessage(Integer id) {

        return messageReceiverMapper.selectReadMessage(id);
    }
}
