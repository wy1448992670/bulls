package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.model.ProjectClass;

public interface ProjectClassService {
    List<ProjectClass> selectProjectClassList(String deviceType);

    ProjectClass get(Integer id);

    ProjectClass get(Integer limitDays, Integer noob);

    List<ProjectClass> queryProjectClassList(String keyword, Integer page, Integer limit, String name, Integer noob, Integer limitDays);

    Integer queryProjectClassListCount(String keyword, String name, Integer noob, Integer limitDays);

    Integer save(ProjectClass projectClass);

    Integer updateProjectClass(ProjectClass projectClass);

    List<ProjectClass> queryIndex(int start, int limit,String deviceType);
}
