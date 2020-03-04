package com.goochou.p2b.hessian.openapi.farmcloud.druidtech;

import java.io.Serializable;

/**
 * @Auther: huangsj
 * @Date: 2019/8/1 16:17
 * @Description:
 */
public class GpsInfo implements Serializable {

    private String longitude;
    private String latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


    @Override
    public String toString() {
        return "GpsInfo{" +
                "longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
