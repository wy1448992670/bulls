package com.goochou.p2b.hessian.openapi.farmcloud.druidtech;

import com.goochou.p2b.hessian.Request;

/**
 * @Auther: huangsj
 * @Date: 2019/6/28 17:13
 * @Description:
 */
public class DeviceRequest extends Request {

    private String authentication;
    private String resultLimit;
    private String resultOffset;
    private String resultSort;

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getResultLimit() {
        return resultLimit;
    }

    public void setResultLimit(String resultLimit) {
        this.resultLimit = resultLimit;
    }

    public String getResultOffset() {
        return resultOffset;
    }

    public void setResultOffset(String resultOffset) {
        this.resultOffset = resultOffset;
    }

    public String getResultSort() {
        return resultSort;
    }

    public void setResultSort(String resultSort) {
        this.resultSort = resultSort;
    }
}
