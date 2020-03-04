package com.goochou.p2b.hessian.transaction;

import static com.goochou.p2b.constant.pay.ChannelConstants.CHANNEL_KEY;
import static com.goochou.p2b.constant.pay.ChannelConstants.CHANNEL_NAME;
import static com.goochou.p2b.constant.pay.ChannelConstants.CHANNEL_URL;
import static com.goochou.p2b.constant.pay.ChannelConstants.CHANNEL_ALLOW_CHOOSE;
import static com.goochou.p2b.constant.pay.ChannelConstants.CHANNEL_CHOOSE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.constant.pay.OutPayEnum;

/**
 * @author shuys
 * @since 2019/5/21 18:19
 */
public class PayChannelResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8085961511123163143L;

    @FieldMeta(description = "支付方式", have = true)
    private List<Map<String, String>> channel;

    @FieldMeta(description = "需要支付金额", have = true)
    private Double needPayMoney;

    @FieldMeta(description = "账户余额", have = true)
    private Double availableMoney;

    @FieldMeta(description = "授信余额", have = true)
    private Double availableCreditMoney;

    private Double creditPayMoney;

    private Double balancePayMoney;

    private Double bankPayMoney;

    private boolean isAutoUse;

    private String needPayMoneyLabel;

    private String availableMoneyLabel;
    private String availableCreditMoneyLabel;

    private String balancePayMoneyLabel;
    private String creditPayMoneyLabel;

    private String unusableBalanceTip;

    private String balancePayMoneyString;
    private String creditPayMoneyString;

    private String bankPayMoneyString;

    private String needPayMoneyString;

    public String getNeedPayMoneyLabel() {
        return "需支付金额";
    }

    public String getAvailableMoneyLabel() {
        return "当前可用：¥ " + String.format("%.2f",availableMoney);
    }

    public String getAvailableCreditMoneyLabel() {
        return "当前可用：¥ " + String.format("%.2f",availableCreditMoney);
    }

    public String getBalancePayMoneyLabel() {
        return "余额支付";
    }

    public String getCreditPayMoneyLabel() {
        return "冻结利润支付";
    }

    public String getUnusableBalanceTip() {
        return "";
    }

    public String getBalancePayMoneyString() {
        return (balancePayMoney != null && balancePayMoney > 0) ? "- ¥ " + String.format("%.2f",balancePayMoney) : "";
    }

    public String getCreditPayMoneyString() {
        return (creditPayMoney != null && creditPayMoney > 0) ? "- ¥ " + String.format("%.2f",creditPayMoney) : "";
    }

    public String getBankPayMoneyString() {
        return (bankPayMoney != null && bankPayMoney > 0) ? "¥ " + String.format("%.2f",bankPayMoney) : "¥ 0.00";
    }

    public String getNeedPayMoneyString() {
        return (needPayMoney != null && needPayMoney > 0) ? "¥ " + String.format("%.2f",needPayMoney) : "";
    }


    public boolean isAutoUse() {
        return isAutoUse;
    }

    public void setAutoUse(boolean autoUse) {
        isAutoUse = autoUse;
    }

    public Double getCreditPayMoney() {
        return creditPayMoney;
    }

    public Double getBankPayMoney() {
        return bankPayMoney;
    }

    public void setBankPayMoney(Double bankPayMoney) {
        this.bankPayMoney = bankPayMoney;
    }

    public void setCreditPayMoney(Double creditPayMoney) {
        this.creditPayMoney = creditPayMoney;
    }

    public Double getBalancePayMoney() {
        return balancePayMoney;
    }

    public void setBalancePayMoney(Double balancePayMoney) {
        this.balancePayMoney = balancePayMoney;
    }

    public Double getAvailableCreditMoney() {
        return availableCreditMoney;
    }

    public void setAvailableCreditMoney(Double availableCreditMoney) {
        this.availableCreditMoney = availableCreditMoney;
    }

    public Double getNeedPayMoney() {
        return needPayMoney;
    }

    public void setNeedPayMoney(Double needPayMoney) {
        this.needPayMoney = needPayMoney;
    }

    public Double getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(Double availableMoney) {
        this.availableMoney = availableMoney;
    }

    public List<Map<String, String>> getChannel() {
        return channel;
    }

    public void setChannel(List<Map<String, String>> channel) {
        List<Map<String, String>> result = new ArrayList<>();
        //result.add(this.getPaychannelByEnum(OutPayEnum.WEIXIN));
        //result.add(this.getPaychannelByEnum(OutPayEnum.ALIPAY));
        if (channel == null) {
            channel = new ArrayList<>();
        }
        result.addAll(channel);
        this.channel = result;
    }

    public Map<String, String> getPaychannelByEnum(OutPayEnum outPayEnum, boolean choose) {
        Map<String, String> result = new HashMap<>();
        result.put(CHANNEL_URL, ClientConstants.ALIBABA_PATH + "images/" + outPayEnum.getFeatureName() + ".png");
        result.put(CHANNEL_KEY, outPayEnum.getFeatureName());
        result.put(CHANNEL_NAME, outPayEnum.getDescription());
        if(choose) {
            result.put(CHANNEL_CHOOSE, "1");
        }else {
            result.put(CHANNEL_CHOOSE, "0");
        }
        result.put(CHANNEL_ALLOW_CHOOSE, "1");
        return result;
    }
}
