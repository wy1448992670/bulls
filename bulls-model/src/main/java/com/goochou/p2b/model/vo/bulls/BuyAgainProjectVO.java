package com.goochou.p2b.model.vo.bulls;

import java.util.List;

/**
 * @author: huangsj
 * @Date: 2019/10/31 17:36
 * @Description:
 */
public class BuyAgainProjectVO {

    private Integer projectId;

    private String title;

    private String cardName1;
    private String orderStateStyle;

    private String pictureUrl;
    private String orderNum;
    private String projectName;
    private String sex;
    private String earNum;

    private String orderNumLabel;
    private String projectNameLabel;
    private String sexLabel;
    private String earNumLabel;

    private String buyedAgreeLabel;
    private String buyedAgreeStyle;
    private String buyedAgreeUrl;

    private String cardName2;

    private String selectedBackgroundPicUrl;


    private String buyAgreeStyle;
    private String buyAgreeUrl;


    private String orderMoneyLabel;
    private String orderMoneyStyle;

    private String buttonLabel;

    private String bullMoneyLabel;
    private String feedMoneyLabel;
    private String manageMoneyLabel;
    private String totalMoneyLabel;
    private String totalInterestLabel;
    private String cheapMoneyLabel;



    private String allow;

    private List<KeepPeriodVO> keepPeriodVOList;

    public List<KeepPeriodVO> getKeepPeriodVOList() {
        return keepPeriodVOList;
    }

    private String tip;

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public void setKeepPeriodVOList(List<KeepPeriodVO> keepPeriodVOList) {
        this.keepPeriodVOList = keepPeriodVOList;
    }

    public String getAllow() {
        return allow;
    }

    public void setAllow(String allow) {
        this.allow = allow;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getCardName1() {
        return "原始订单：";
    }

    public String getOrderStateStyle() {
        return "<span style='color:#00CC9F;'>已结算</span>";
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEarNum() {
        return earNum;
    }

    public void setEarNum(String earNum) {
        this.earNum = earNum;
    }

    public String getBuyedAgreeLabel() {
        return "原始协议:";
    }

    public String getBuyedAgreeStyle() {
        return "<span style='color:#576B95;'>《认购及托养服务协议》</span>";
    }

    public String getBuyedAgreeUrl() {
        return buyedAgreeUrl;
    }

    public void setBuyedAgreeUrl(String buyedAgreeUrl) {
        this.buyedAgreeUrl = buyedAgreeUrl;
    }

    public String getCardName2() {
        return "增加委托饲养期";
    }

    public String getSelectedBackgroundPicUrl() {
        return selectedBackgroundPicUrl;
    }

    public void setSelectedBackgroundPicUrl(String selectedBackgroundPicUrl) {
        this.selectedBackgroundPicUrl = selectedBackgroundPicUrl;
    }

    public String getBuyAgreeStyle() {
        return "<span style='color:#576B95;'>《认购及托养服务协议》</span>";
    }

    public String getBuyAgreeUrl() {
        return buyAgreeUrl;
    }

    public void setBuyAgreeUrl(String buyAgreeUrl) {
        this.buyAgreeUrl = buyAgreeUrl;
    }

    public String getOrderMoneyLabel() {
        return "订单金额";
    }
    public String getOrderMoneyStyle() {
        return "<span style='color:#EE4035;'>￥?</span>";
    }

    public String getButtonLabel() {
        return "提交订单";
    }



    public String getTitle() {
        return "续养";
    }

    public String getOrderNumLabel() {
        return "订单编号：";
    }

    public String getProjectNameLabel() {
        return "项目名称：";
    }

    public String getSexLabel() {
        return "性别：";
    }

    public String getEarNumLabel() {
        return "耳标号：";
    }



    public String getBullMoneyLabel() {
        return "牛只单价";
    }

    public String getFeedMoneyLabel() {
        return "续养饲料费";
    }

    public String getManageMoneyLabel() {
        return "续养管理费";
    }

    public String getTotalMoneyLabel() {
        return "需支付金额合计";
    }

    public String getTotalInterestLabel() {
        return "续养预计利润：";
    }

    public String getCheapMoneyLabel() {
        return "优惠金额";
    }
}
