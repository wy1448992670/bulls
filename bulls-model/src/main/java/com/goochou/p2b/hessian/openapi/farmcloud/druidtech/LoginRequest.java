package com.goochou.p2b.hessian.openapi.farmcloud.druidtech;

import com.goochou.p2b.hessian.Request;

/**
 * @Auther: huangsj
 * @Date: 2019/6/28 17:05
 * @Description:
 */
public class LoginRequest extends Request {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
