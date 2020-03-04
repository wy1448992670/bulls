package com.goochou.p2b.service.impl;

import com.goochou.p2b.dao.FeePictureMapper;
import com.goochou.p2b.dao.FeedbackMapper;
import com.goochou.p2b.model.FeePicture;
import com.goochou.p2b.model.FeePictureExample;
import com.goochou.p2b.service.FeePicetureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by irving on 2015/8/7.
 */
@Service
public class FeePicetureServiceImpl implements FeePicetureService {
    @Resource
    private FeedbackMapper feedbackMapper;
    @Resource
    private FeePictureMapper feePictureMapper;

     public FeePicture selectByFeeId(Integer feeId){
        FeePictureExample example  = new FeePictureExample();
        FeePictureExample.Criteria c = example.createCriteria();
        c.andFeeIdEqualTo(feeId);
         List<FeePicture> list = feePictureMapper.selectByExample(example);
         if(list!=null && list.size()>0){
             return list.get(0);
         }
         return null;

    }


}
