package com.goochou.p2b.service.impl;

import com.goochou.p2b.dao.ShareMapper;
import com.goochou.p2b.model.Share;
import com.goochou.p2b.model.ShareExample;
import com.goochou.p2b.service.ShareService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShareServiceImpl implements ShareService {

    @Resource
    private ShareMapper shareMapper;


    @Override
    public ShareMapper getMapper() {
        return this.shareMapper;
    }

    @Override
    public List<Share> queryAll(Integer start, Integer limit) {
        ShareExample example = new ShareExample();
        ShareExample.Criteria c = example.createCriteria();
        example.setLimitStart(start);
        example.setLimitEnd(limit);
        return shareMapper.selectByExample(example);
    }

    @Override
    public Integer queryCount() {
        ShareExample example = new ShareExample();
        ShareExample.Criteria c = example.createCriteria();
        return (int) shareMapper.countByExample(example);
    }


    public Share queryByKey(Integer id) {
        return shareMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(Share share) {
        share.setUpdateDate(new Date());
        if (share.getId() != null) {
            shareMapper.updateByPrimaryKeySelective(share);
        } else {
            share.setCreateDate(new Date());
            share.setUploadId(1);// 默认图片
            shareMapper.insertSelective(share);
        }
    }

    public void delShare(Integer id, Integer status) throws Exception {
        if (status != null && status != 1) { // 状态不为 不启用是
            throw new Exception("不能删除状态为启用的分享");
        }
        ShareExample example = new ShareExample();
        example.createCriteria().andIdEqualTo(id).andStatusEqualTo(status);
    }

    public List<Map<String, Object>> queryShareList(Integer start, Integer limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("end", limit);
        return shareMapper.queryShareList(params);
    }
    
    @Override
    public Share queryByClickWord(String clickWord) {
        ShareExample example = new ShareExample();
        example.createCriteria().andClickWordEqualTo(clickWord);
        
        List<Share> list = shareMapper.selectByExample(example);
        if(list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
