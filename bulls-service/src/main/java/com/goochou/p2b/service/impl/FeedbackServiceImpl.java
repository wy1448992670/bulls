package com.goochou.p2b.service.impl;

import com.goochou.p2b.dao.FeePictureMapper;
import com.goochou.p2b.dao.FeedbackMapper;
import com.goochou.p2b.model.FeePicture;
import com.goochou.p2b.model.Feedback;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.service.FeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by irving on 2015/8/7.
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Resource
    private FeedbackMapper feedbackMapper;
    @Resource
    private FeePictureMapper feePictureMapper;

    @Override
    public void save(Feedback fee, List<Integer> pictureIds) {
        feedbackMapper.insert(fee);
        if (null != pictureIds && !pictureIds.isEmpty()) {
            for (Integer pic : pictureIds) {
                FeePicture feePic = new FeePicture();
                feePic.setFeeId(fee.getId());
                feePic.setUploadId(pic);
                feePictureMapper.insert(feePic);
            }
        }
    }

    @Override
    public List<Map<String, Object>> query(String keyword, Date startTime, Date endTime, Integer start, Integer limit, Integer adminId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        if (endTime != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(endTime);
            c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
            map.put("endTime", c1.getTime());
        } else {
            map.put("endTime", null);
        }
        map.put("keyword", keyword);
        map.put("start", start);
        map.put("limit", limit);
        map.put("adminId", adminId);
        return feedbackMapper.query(map);
    }

    @Override
    public Integer queryCount(String keyword, Date startTime, Date endTime, Integer adminId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("keyword", keyword);
        map.put("adminId", adminId);
        return feedbackMapper.queryCount(map);
    }

    @Override
    public void reply(UserAdmin user, String replyContent, int feedbackId) {
        // 保存发送到用户的回复信息
        // Feedback fee = this.queryById(id);
    }

    @Override
    public Feedback queryById(Integer id) {
        return feedbackMapper.queryById(id);
    }

    @Override
    public void update(Feedback fee) {
        feedbackMapper.updateByPrimaryKey(fee);
    }

    @Override
    public Map<String, Object> queryByIdToUserFeedbackInfo(Integer id) {
        return feedbackMapper.queryByIdToUserFeedbackInfo(id);
    }
}
