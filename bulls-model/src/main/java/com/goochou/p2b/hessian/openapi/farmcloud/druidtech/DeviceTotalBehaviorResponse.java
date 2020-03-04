package com.goochou.p2b.hessian.openapi.farmcloud.druidtech;

import com.goochou.p2b.hessian.Response;

import java.io.Serializable;

/**
 * @Auther: huangsj
 * @Date: 2019/6/28 17:32
 * @Description:
 */
public class DeviceTotalBehaviorResponse  implements Serializable {

    private String count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
