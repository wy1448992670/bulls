package com.goochou.p2b.hessian.openapi.farmcloud.druidtech;

import com.goochou.p2b.hessian.Response;

import java.io.Serializable;

/**
 * @Auther: huangsj
 * @Date: 2019/6/28 17:28
 * @Description:
 */
public class DeviceBehaviorResponse  implements Serializable {

    private String id;
    private String device_id;
    private String mark;
    private String uuid;
    private String firmware_version;
    private String timestamp;
    private String updated_at;
    private String odba;//活动量
    private String action_type;//行为参数  1 取食，2 反刍，3 其他
    private String estrus;//发情: 1表示检测到发情
    private String steps;//步数

    private String[] action_votes;//行为具体数值

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirmware_version() {
        return firmware_version;
    }

    public void setFirmware_version(String firmware_version) {
        this.firmware_version = firmware_version;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getOdba() {
        return odba;
    }

    public void setOdba(String odba) {
        this.odba = odba;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public String getEstrus() {
        return estrus;
    }

    public void setEstrus(String estrus) {
        this.estrus = estrus;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String[] getAction_votes() {
        return action_votes;
    }

    public void setAction_votes(String[] action_votes) {
        this.action_votes = action_votes;
    }
}
