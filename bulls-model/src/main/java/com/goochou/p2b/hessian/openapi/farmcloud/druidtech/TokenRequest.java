package com.goochou.p2b.hessian.openapi.farmcloud.druidtech;

import com.goochou.p2b.hessian.Request;

import java.io.Serializable;

/**
 * @Auther: huangsj
 * @Date: 2019/6/28 17:10
 * @Description:
 */
public class TokenRequest extends Request {

    private String access_id;
    private String secret;

    public String getAccess_id() {
        return access_id;
    }

    public void setAccess_id(String access_id) {
        this.access_id = access_id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
