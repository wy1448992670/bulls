package com.goochou.p2b.dao;

import com.goochou.p2b.model.ProjectLinkProject;
import com.goochou.p2b.model.ProjectLinkProjectExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ProjectLinkProjectMapper {

    int countByExample(ProjectLinkProjectExample example);

    int deleteByExample(ProjectLinkProjectExample example);


    int deleteByPrimaryKey(Integer id);


    int insert(ProjectLinkProject record);


    int insertSelective(ProjectLinkProject record);


    List<ProjectLinkProject> selectByExample(ProjectLinkProjectExample example);


    ProjectLinkProject selectByPrimaryKey(Integer id);


    int updateByExampleSelective(@Param("record") ProjectLinkProject record, @Param("example") ProjectLinkProjectExample example);


    int updateByExample(@Param("record") ProjectLinkProject record, @Param("example") ProjectLinkProjectExample example);


    int updateByPrimaryKeySelective(ProjectLinkProject record);


    int updateByPrimaryKey(ProjectLinkProject record);


    /**
     * @Description: 查询所有投资剩余份额
     * @date 2016/10/26
     * @author 王信   projectId   活期项目ID
     */
    List<ProjectLinkProject> selectSurplusShareList(@Param("projectId") Integer projectId);

    /**
     * @Description: 查询所有原始债权原有份额
     * @date 2016/10/27
     * @author 王信
     */
    List<ProjectLinkProject> selectOriginalShareList(@Param("projectId") Integer projectId);

}