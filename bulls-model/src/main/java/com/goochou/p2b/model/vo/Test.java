package com.goochou.p2b.model.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by irving on 2016/7/7.
 */
public class Test {
    public static void main(String[] args) {
        String json = "{\"check1\":true,\"check12\":true,\"check3\":true,\"check6\":true,\"check9\":true}";
        JSONObject object = JSON.parseObject(json);
        System.out.println(object.get("check1"));

        String s = "1/3/4/";
        System.out.println(s.substring(0, s.lastIndexOf("/")));
    }
}
