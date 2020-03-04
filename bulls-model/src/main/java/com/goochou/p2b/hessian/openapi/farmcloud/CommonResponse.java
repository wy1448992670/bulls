package com.goochou.p2b.hessian.openapi.farmcloud;

import com.goochou.p2b.hessian.Response;

/**
 * @Auther: huangsj
 * @Date: 2019/7/1 13:20
 * @Description:
 */
public class CommonResponse<T> extends Response {

    private Integer statusCode;

    private T data;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
