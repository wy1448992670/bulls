package com.goochou.p2b.service;

import com.goochou.p2b.model.YaoRecord;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface YaoRecordService {

    void save(YaoRecord yr);

    List<Map<String, Object>> getCountByType(Date startTime, Date endTime);

    Integer getCountByTime(Integer userId, Date startTime, Date endTime);

    List<Map<String, Object>> listByUser(Integer userId, Integer start, Integer limit);

    Integer listByUserCount(Integer userId);
}
