package com.goochou.p2b.hessian.openapi.farmcloud.druidtech;

import com.goochou.p2b.hessian.Request;

/**
 * @Auther: huangsj
 * @Date: 2019/6/28 17:19
 * @Description:
 */
public class DeviceBehaviorReqeust extends Request {
    private String authentication;
    private String resultSort; //timestamp 顺序 -timestamp 倒序
    private String resultLimit; //每页条数，考虑服务器压力，建议不超过1000

    private String id;
    private String startTime; //从什么时间开始 时间参数，rfc3339格式，为空表示获取第一页数据，需结合header中x-result-sort参数，请看备注

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getResultSort() {
        return resultSort;
    }

    public void setResultSort(String resultSort) {
        this.resultSort = resultSort;
    }

    public String getResultLimit() {
        return resultLimit;
    }

    public void setResultLimit(String resultLimit) {
        this.resultLimit = resultLimit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
