 package com.goochou.p2b.model.vo;

import java.io.Serializable;

import com.goochou.p2b.model.MigrationInvestmentView;

/**
 * @author sxy
 * @date 2019/10/23
 */
public class MigrationInvestmentVO extends MigrationInvestmentView implements Serializable {

    private static final long serialVersionUID = 1L;

    private String repaymentTypeStr;
    
    private String periodStr;
    
    private String createTimeStr;
    
    private String loanTimeStr;
    
    private String finishedTimeStr;
    
    private String nextReceiveTimeStr;
    
    private String lastReceiveTimeStr;
    
    private String statusStr;

    public String getRepaymentTypeStr() {
        return repaymentTypeStr;
    }

    public void setRepaymentTypeStr(String repaymentTypeStr) {
        this.repaymentTypeStr = repaymentTypeStr;
    }

    public String getPeriodStr() {
        return periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getLoanTimeStr() {
        return loanTimeStr;
    }

    public void setLoanTimeStr(String loanTimeStr) {
        this.loanTimeStr = loanTimeStr;
    }

    public String getFinishedTimeStr() {
        return finishedTimeStr;
    }

    public void setFinishedTimeStr(String finishedTimeStr) {
        this.finishedTimeStr = finishedTimeStr;
    }

    public String getNextReceiveTimeStr() {
        return nextReceiveTimeStr;
    }

    public void setNextReceiveTimeStr(String nextReceiveTimeStr) {
        this.nextReceiveTimeStr = nextReceiveTimeStr;
    }

    public String getLastReceiveTimeStr() {
        return lastReceiveTimeStr;
    }

    public void setLastReceiveTimeStr(String lastReceiveTimeStr) {
        this.lastReceiveTimeStr = lastReceiveTimeStr;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }
    
}
