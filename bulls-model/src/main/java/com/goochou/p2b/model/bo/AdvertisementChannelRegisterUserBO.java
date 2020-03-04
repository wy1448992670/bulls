package com.goochou.p2b.model.bo;

import com.goochou.p2b.constant.Constants;

import java.io.Serializable;

/**
 * <p>
 * 渠道注册用户信息
 * </p>
 *
 * @author shuys
 * @since 2019年10月15日 13:17:00
 */
public class AdvertisementChannelRegisterUserBO implements Serializable {

    private static final long serialVersionUID = -4509041522759199683L;

    private Integer userId;

    private String userName;

    private String trueName;

    private String phone;

    private String createDate;

    private Integer level;

    private Integer status;

    private Integer sex;

    private Integer channelId;

    private String channelNo;

    private String channelName;

    private String channelType;

    private Integer channelStatus;

    private String channelUrl;

    public String getChannelUrl() {
        if (channelNo == null) {
            return channelUrl;
        } else {
            return String.format(Constants.ADVERTISEMENT_CHANNEL_URL, channelNo);
        }
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public Integer getChannelStatus() {
        return channelStatus;
    }

    public void setChannelStatus(Integer channelStatus) {
        this.channelStatus = channelStatus;
    }
}
