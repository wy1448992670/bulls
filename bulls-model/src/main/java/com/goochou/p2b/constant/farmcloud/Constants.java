package com.goochou.p2b.constant.farmcloud;

import com.goochou.p2b.constant.ConfigHelper;

/**
 * @Auther: huangsj
 * @Date: 2019/7/1 11:48
 * @Description:
 */
public class Constants {

    public static String DRUIDTECH_TOKEN= "";


    public static String DRUIDTECH_API= ConfigHelper.getDefault().getString("druidtech_api");
    public static String DRUIDTECH_ACCESS_ID= ConfigHelper.getDefault().getString("druidtech_access_id");
    public static String DRUIDTECH_ACCESS_SECRET= ConfigHelper.getDefault().getString("druidtech_access_secret");


    public final static String DRUIDTECH_LOGIN="/admin/api/v1/login";
    public final static String DRUIDTECH_GET_TOKEN="/admin/api/v1/secret/login";
    public final static String DRUIDTECH_REFRESH_TOKEN="/admin/api/v1/secret/refresh";
    public final static String DRUIDTECH_DEVICE="/admin/api/v1/device/";
    public final static String DRUIDTECH_DEVICE_GPS="/admin/api/v1/gps/device/%s/last/%s";
    public final static String DRUIDTECH_DEVICE_BEHAVIOR="/admin/api/v1/behavior2/device/%s/page/%s";
    public final static String DRUIDTECH_DEVICE_TOTAL_BEHAVIOR="/admin/api/v1/behavior2/device/%s/count";
}
