package com.goochou.p2b.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.dao.BannerMapper;
import com.goochou.p2b.model.Banner;
import com.goochou.p2b.service.BannerService;

@Service
public class BannerServiceImpl implements BannerService {

    @Resource
    private BannerMapper bannerMapper;

    @Override
    public List<Banner> listByStatus(Integer status,Integer type,Integer source, Integer page) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("status",status);
        map.put("type",type);
        map.put("source",source);
        if (page == null) {
            return bannerMapper.listBanner(map);
        } else {
            map.put("start",(page - 1) * 20);
            map.put("limit", 20);
            return bannerMapper.listBanner(map);
        }

    }

    public Banner getBannerByStatus(Integer status, Integer type, Integer source) {
        Banner banner = null;
        List<Banner> list = listByStatus(status, type, source, null);
        if (list != null && list.size() > 0) {
            banner = list.get(0);
            banner.setPictureUrl(ClientConstants.ALIBABA_PATH + "upload/" + banner.getPictureUrl());
        }
        return banner;
    }

    @Override
    public void save(Banner banner) {
        if (banner.getId()!=null){
            bannerMapper.updateByPrimaryKeySelective(banner);
        }else{
            bannerMapper.insertSelective(banner);
        }
    }

    @Override
    public Banner detail(Integer id) {
        return bannerMapper.detailBanner(id);
    }

    @Override
    public void update(Banner banner) {
        bannerMapper.updateByPrimaryKeySelective(banner);
    }

    @Override
    public Integer getCountBanner(Integer status,Integer type,Integer source) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("status",status);
        map.put("source",source);
        map.put("type",type);
        return bannerMapper.getCountBanner(map);
    }

}
