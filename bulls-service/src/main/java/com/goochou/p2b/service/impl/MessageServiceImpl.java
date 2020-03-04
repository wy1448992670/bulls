package com.goochou.p2b.service.impl;

import com.goochou.p2b.dao.MessageMapper;
import com.goochou.p2b.dao.MessageReceiverMapper;
import com.goochou.p2b.dao.MessageTemplateMapper;
import com.goochou.p2b.model.Message;
import com.goochou.p2b.model.MessageReceiver;
import com.goochou.p2b.model.MessageTemplate;
import com.goochou.p2b.model.MessageTemplateExample;
import com.goochou.p2b.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageMapper messageMapper;
    @Resource
    private MessageReceiverMapper messageReceiverMapper;
    @Resource
    private MessageTemplateMapper messageTemplateMapper;

    @Override
    public Integer save(String title, String content, Integer receiverId) {
        Date d = new Date();
        Message message = new Message();
        message.setTitle(title);
        message.setContent(content);
        message.setCreateTime(d);
        messageMapper.insert(message);

        MessageReceiver r = new MessageReceiver();
        r.setMessageId(message.getId());
        r.setReceiveTime(d);
        r.setReceiverId(receiverId);
        messageReceiverMapper.insertSelective(r);

        return message.getId();
    }
    @Override
    public Integer saveMessage(String title, String content){
        Date d = new Date();
        Message message = new Message();
        message.setTitle(title);
        message.setContent(content);
        message.setCreateTime(d);
        messageMapper.insert(message);
        return message.getId();
    }
    @Override
    public void saveReceiveTime(Integer messagerid ,Integer userd,Date date) {
        MessageReceiver r = new MessageReceiver();
        r.setMessageId(messagerid);
        r.setReceiveTime(date);
        r.setReceiverId(userd);
        messageReceiverMapper.insertSelective(r);
    }

    @Override
    public List<Map<String, Object>> list(String keyword, Integer start, Integer limit) {
        return messageMapper.list(keyword, start, limit);
    }

    @Override
    public Integer listCount(String keyword) {
        return messageMapper.listCount(keyword);
    }

    @Override
    public void deleteByMessageId(Integer id) {
        messageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<MessageTemplate> selectList(Integer status) {
        //0失效  1启用
        if (status != null) {
            return messageTemplateMapper.selectAllMessageTemplate(status);
        }
        MessageTemplateExample m = new MessageTemplateExample();
        m.setOrderByClause("id desc");
        return messageTemplateMapper.selectByExample(m);
    }

    @Override
    public Integer addTemplet(String content, Integer type, Integer status) {
        MessageTemplate messageTemplate = new MessageTemplate();
        messageTemplate.setContent(content);
        messageTemplate.setType(type);
        messageTemplate.setStatus(status);
        return messageTemplateMapper.insert(messageTemplate);
    }

    @Override
    public void deleteTemplet(Integer id) {
        messageTemplateMapper.deleteByPrimaryKey(id);
    }

    @Override
    public MessageTemplate selectByIdMessageTemplate(Integer id) {
        return messageTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer updatedTemplet(String content, Integer type, Integer status, Integer id) {
        MessageTemplate messageTemplate = new MessageTemplate();
        messageTemplate.setContent(content);
        messageTemplate.setType(type);
        messageTemplate.setStatus(status);
        messageTemplate.setId(id);
        return messageTemplateMapper.updateByPrimaryKey(messageTemplate);
    }

    @Override
    public void saveInternalList(String title, String content, List<String> list) {
        Date d = new Date();
        Message message = new Message();
        message.setTitle(title);
        message.setContent(content);
        message.setCreateTime(d);
        messageMapper.insert(message);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("messageId", message.getId());
        map.put("list", list);
        map.put("createTime", d);
        messageReceiverMapper.saveInternalList(map);
    }

}
