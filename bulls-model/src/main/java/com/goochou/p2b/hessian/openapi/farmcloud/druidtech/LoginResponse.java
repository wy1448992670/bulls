package com.goochou.p2b.hessian.openapi.farmcloud.druidtech;

import com.goochou.p2b.hessian.Response;

import java.io.Serializable;

/**
 * @Auther: huangsj
 * @Date: 2019/6/28 17:06
 * @Description:
 */
public class LoginResponse implements Serializable {

    private String id;
    private String username;
    private String address;
    private String phone;

    private String company_id;
    private String company_name;
    private String total_device;//用户拥有的设备数量
    private String total_area;//用户拥有的围栏数量
    private String total_room;//分组

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    private String authentication;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getTotal_device() {
        return total_device;
    }

    public void setTotal_device(String total_device) {
        this.total_device = total_device;
    }

    public String getTotal_area() {
        return total_area;
    }

    public void setTotal_area(String total_area) {
        this.total_area = total_area;
    }

    public String getTotal_room() {
        return total_room;
    }

    public void setTotal_room(String total_room) {
        this.total_room = total_room;
    }
}
