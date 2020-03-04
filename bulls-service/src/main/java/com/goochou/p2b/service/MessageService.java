package com.goochou.p2b.service;

import com.goochou.p2b.model.MessageTemplate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MessageService {

    /**
     * 给用户发送消息
     *
     * @param title      消息标题
     * @param content    消息内容
     * @param receiverId 接受者ID
     * @return
     */
    public Integer save(String title, String content, Integer receiverId);

    public List<Map<String, Object>> list(String keyword, Integer start, Integer limit);

    public Integer listCount(String keyword);


    /**
     * 根据消息ID删除消息内容
     */
    public void deleteByMessageId(Integer id);


    /**
     * 查询数据库中所有的短信模板
     *
     * @return
     * @author 王信
     * @Create Date: 2015年12月30日上午11:54:55
     */
    public List<MessageTemplate> selectList(Integer status);

    /**
     * 新增短信模板
     *
     * @return
     * @author 王信
     * @Create Date: 2015年12月30日下午2:26:35
     */
    public Integer addTemplet(String content, Integer type, Integer status);

    /**
     * 删除短信模板
     *
     * @param id
     * @author 王信
     * @Create Date: 2015年12月30日下午2:46:16
     */
    public void deleteTemplet(Integer id);

    /**
     * 根据id查询单个模板的信息
     *
     * @param id
     * @return
     * @author 王信
     * @Create Date: 2015年12月30日下午3:07:50
     */
    public MessageTemplate selectByIdMessageTemplate(Integer id);

    /**
     * 编辑短信模板
     *
     * @return
     * @author 王信
     * @Create Date: 2015年12月30日下午2:26:35
     */
    public Integer updatedTemplet(String content, Integer type, Integer status, Integer id);

    /**
     * @Description: 批量发送站内信
     * @date 2016/10/21
     * @author 王信
     */
    void saveInternalList(String title, String content, List<String> list);

    public Integer saveMessage(String title, String content);
    public void saveReceiveTime(Integer messagerid ,Integer userd,Date date);
}
