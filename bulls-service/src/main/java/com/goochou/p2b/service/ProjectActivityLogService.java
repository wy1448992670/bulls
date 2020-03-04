package com.goochou.p2b.service;

import java.util.List;
import java.util.Map;

public interface ProjectActivityLogService {

    List<Map<String,Object>> queryActivityNumberList(Integer userId);
}
