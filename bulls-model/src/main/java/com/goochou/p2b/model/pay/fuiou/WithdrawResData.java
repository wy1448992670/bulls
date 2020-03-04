package com.goochou.p2b.model.pay.fuiou;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by irving on 2017/6/2.
 */
@XmlRootElement(name = "payforrsp")
public class WithdrawResData {
    private String ret;
    private String memo;

    public String getRet() {
        return ret;
    }

    @XmlElement(name = "ret")
    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMemo() {
        return memo;
    }

    @XmlElement(name = "memo")
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "WithdrawResData{" +
                "ret='" + ret + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
