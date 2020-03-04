package com.goochou.p2b.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.goochou.p2b.model.Feedback;
import com.goochou.p2b.model.UserAdmin;

/**
 * Created by irving on 2015/8/7.
 */
public interface FeedbackService {
    /**
     * 插入用户反馈记录
     *
     * @param fee
     * @param pictureIds
     *            图片
     */
    public void save(Feedback fee, List<Integer> pictureIds);

    public List<Map<String, Object>> query(String keyword, Date startTime, Date endTime, Integer start, Integer limit, Integer adminId);

    public Integer queryCount(String keyword, Date startTime, Date endTime, Integer adminId);

    public void reply(UserAdmin user, String replyContent, int feedbackId);

    /**
     * 根据ID查询反馈信息对象
     *
     * @param id
     * @return Feedback 刘源 2015-10-27
     */
    public Feedback queryById(Integer id);

    /**
     * 根据ID查询反馈信息对象
     *
     * @param id
     * 刘源 2015-10-27
     */
    public void update(Feedback fee);

    /**
     * 根据ID查询反馈信息详情
     *
     * @param id
     * 刘源 2015-10-27
     */
    public Map<String, Object> queryByIdToUserFeedbackInfo(Integer id);

}
