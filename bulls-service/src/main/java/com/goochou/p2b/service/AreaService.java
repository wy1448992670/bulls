package com.goochou.p2b.service;

import com.goochou.p2b.dao.AreaMapper;
import com.goochou.p2b.model.Area;
import com.goochou.p2b.model.vo.AreaIndexVO;

import java.util.List;

public interface AreaService {

    AreaMapper getMapper();

    List<AreaIndexVO> getAreaByParentCode(String parentCode);

    Area getAreaByCode(String parengCode) throws Exception;

    List<AreaIndexVO> getAllArea();

}


