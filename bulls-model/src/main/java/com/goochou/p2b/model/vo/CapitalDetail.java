package com.goochou.p2b.model.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CapitalDetail {

    public CapitalDetail() {
    }

    public CapitalDetail(Date time) {
        this.time = time;
        this.formatTime = format.format(this.time);
    }

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private Object num_id;
    private Double investment; //总计投资
    private double huoInvest; //活期投资
    private double dingInvest; //定期投资（包含新手标+普通定期标+VIP定期标，直接余额投资）
    private double huoToDingInvest; //活转定（包括活转定投资定期+活转定投资债权）
    private double bondInvest;//债权投资
    private double recharge;
    private double withdraw;
    private double dingEarnings;//定期收益
    private double huoEarnings; //活期收益
    private double earnings; //总收益
    private double receive; //回收本金
    private Date time;
    private String formatTime;
    private double yyyInvest;
	private double yyyEarnings;

	public double getYyyInvest() {
		return yyyInvest;
	}

	public void setYyyInvest(double yyyInvest) {
		this.yyyInvest = yyyInvest;
	}
	public double getYyyEarnings() {
		return yyyEarnings;
	}

	public void setYyyEarnings(double yyyEarnings) {
		this.yyyEarnings = yyyEarnings;
	}

	public String getFormatTime() {
        return formatTime;
    }

    public void setFormatTime(String formatTime) {
        this.formatTime = formatTime;
    }

    public Object getNum_id() {
        if (time != null) {
            return time.getDate();
        } else {
            return "总计:";
        }
    }

    public void setNum_id(Object num_id) {
        this.num_id = num_id;
    }

    public double getEarnings() {
        return earnings;
    }

    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    public Double getInvestment() {
        return investment;
    }

    public void setInvestment(Double investment) {
        this.investment = investment;
    }

    public double getBondInvest() {
        return bondInvest;
    }

    public void setBondInvest(double bondInvest) {
        this.bondInvest = bondInvest;
    }

    public double getDingEarnings() {
        return dingEarnings;
    }

    public void setDingEarnings(double dingEarnings) {
        this.dingEarnings = dingEarnings;
    }

    public double getDingInvest() {
        return dingInvest;
    }

    public void setDingInvest(double dingInvest) {
        this.dingInvest = dingInvest;
    }

    public double getHuoEarnings() {
        return huoEarnings;
    }

    public void setHuoEarnings(double huoEarnings) {
        this.huoEarnings = huoEarnings;
    }

    public double getHuoInvest() {
        return huoInvest;
    }

    public void setHuoInvest(double huoInvest) {
        this.huoInvest = huoInvest;
    }

    public double getHuoToDingInvest() {
        return huoToDingInvest;
    }

    public void setHuoToDingInvest(double huoToDingInvest) {
        this.huoToDingInvest = huoToDingInvest;
    }

    public double getReceive() {
        return receive;
    }

    public void setReceive(double receive) {
        this.receive = receive;
    }

    public double getRecharge() {
        return recharge;
    }

    public void setRecharge(double recharge) {
        this.recharge = recharge;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(double withdraw) {
        this.withdraw = withdraw;
    }
}
