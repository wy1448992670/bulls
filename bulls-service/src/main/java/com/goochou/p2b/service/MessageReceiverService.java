package com.goochou.p2b.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.goochou.p2b.model.MessageReceiver;

public interface MessageReceiverService {

    // 显示所有信息
    public List<MessageReceiver> list(Integer receiverId, Integer status, Integer start, Integer limit);

    public Integer listCount(Integer receiverId, Integer status);

    /**
     * @param id
     * @param status
     *            0未读1已读2删除
     * @throws Exception
     */
    public boolean update(List<Integer> id, Integer status) throws Exception;

    /**
     * @description app查询用户消息列表
     * @author shuys
     * @date 2019/6/20
     * @param userId
     * @param start
     * @param limit
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public List<Map<String, Object>> listAppMessage(Integer userId, Integer start, Integer limit);

    /**
     * @description app查询用户消息列表 count
     * @author shuys
     * @date 2019/6/20
     * @param userId
     * @param status
     * @return java.lang.Integer
    */
    public Integer listAppMessageCount(Integer userId, Integer status);

    public Integer getUnReadCount(Integer userId);

    public Integer save(Integer id, Integer messageId, Integer userId, Integer status, Date nowDate, Date readDate);

    /**
     * 根据ID删除消息接收信息 如果消息已读，则不能删除
     *
     * @param replyMessageId
     */
    public void deleteByMessageId(Integer messageId);


    /**
     * @Description(描述):一键阅读
     * @author 王信
     * @date 2016/4/22
     * @params
     **/
    public void updateAllMessage(Integer userId);

    /**
     * @Description(描述):查询是否有未阅读的消息
     * @author 王信
     * @date 2016/5/24
     * @params
     **/
    Integer selectReadMessage(Integer id);
}
