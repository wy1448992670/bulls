package com.goochou.p2b.hessian.openapi.farmcloud.druidtech;

import com.goochou.p2b.hessian.Request;

/**
 * @Auther: huangsj
 * @Date: 2019/6/28 17:32
 * @Description:
 */
public class DeviceTotalBehaviorRequest extends Request {


    private String authentication;
    private String deviceId;

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
