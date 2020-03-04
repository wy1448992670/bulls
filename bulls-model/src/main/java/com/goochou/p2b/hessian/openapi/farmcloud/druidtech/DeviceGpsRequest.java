package com.goochou.p2b.hessian.openapi.farmcloud.druidtech;

import com.goochou.p2b.hessian.Request;

/**
 * @Auther: huangsj
 * @Date: 2019/6/28 17:22
 * @Description:
 */
public class DeviceGpsRequest extends Request {

    private String authentication;
    private String id;
    private String day; //最近多少天数据，范围[1-30]

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
