package com.goochou.p2b.service;

import com.goochou.p2b.model.AppVersion;
import com.goochou.p2b.model.AppVersionContent;
import com.goochou.p2b.model.AppVersionContentWithBLOBs;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AppVersionService {

    AppVersion getAppVersion();
    /**
     * 通过渠道获取版本号
     * @author ydp
     * @param client 终端来源 IOS,Android,PC,WAP
     * @return
     */
    AppVersionContent getAppVersionContent(String client);
    /**
     * 通过渠道获取版本号（包括大字段）
     * @author ydp
     * @param client 终端来源 IOS,Android,PC,WAP
     * @return
     */
    AppVersionContentWithBLOBs getAppVersionContentWithBLOBs(@Param("client")String client);
    
    /**
     * app版本控制管理list页面
     * @author sxy
     * @param keyword
     * @param start
     * @param limit
     * @return
     */
    public List<AppVersionContent> queryAppVersionContentList(String keyword, Integer start, Integer limit);
    
    /**
     * app版本控制管理list页面数量
     * @author sxy
     * @param keyword
     * @return
     */
    public Integer queryAppVersionContentCount(String keyword);
    public void saveAppVersionContent(AppVersionContentWithBLOBs group);
    public AppVersionContent selectAppVersionContentKey(Integer id);
    public void deleteAppVersionContentKey(Integer id);

}
