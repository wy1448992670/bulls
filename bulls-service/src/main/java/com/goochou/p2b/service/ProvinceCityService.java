package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.model.ProvinceCity;

public interface ProvinceCityService {
    /**
     * 查询省市
     * @author ydp
     * @return
     * @throws Exception
     */
    public List<ProvinceCity> query() throws Exception;
}
