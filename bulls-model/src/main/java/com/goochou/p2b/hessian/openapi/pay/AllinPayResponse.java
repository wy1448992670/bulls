package com.goochou.p2b.hessian.openapi.pay;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Response;


public class AllinPayResponse extends Response {
	
	/**
     *
     */
    private static final long serialVersionUID = -7762893720141566905L;
    @FieldMeta(description = "交易单号", have=true)
	private String trxId;
	@FieldMeta(description = "渠道平台交易单号", have=true)
    private String chnltrxId;
	@FieldMeta(description = "交易完成时间", have=true)
    private String finTime;
	@FieldMeta(description = "交易透传信息", have=true)
    private String thpInfo;
	@FieldMeta(description = "交易状态", have=true)
    private String trxStatus;
    public String getTrxStatus() {
        return trxStatus;
    }
    public void setTrxStatus(String trxStatus) {
        this.trxStatus = trxStatus;
    }
    public String getTrxId() {
        return trxId;
    }
    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }
    public String getChnltrxId() {
        return chnltrxId;
    }
    public void setChnltrxId(String chnltrxId) {
        this.chnltrxId = chnltrxId;
    }
    public String getFinTime() {
        return finTime;
    }
    public void setFinTime(String finTime) {
        this.finTime = finTime;
    }
    public String getThpInfo() {
        return thpInfo;
    }
    public void setThpInfo(String thpInfo) {
        this.thpInfo = thpInfo;
    }
    @Override
    public String toString() {
        return "AllinPayResponse [trxId=" + trxId + ", chnltrxId=" + chnltrxId + ", finTime=" + finTime + ", thpInfo="
            + thpInfo + ", trxStatus=" + trxStatus + ", success=" + success + ", errorCode=" + errorCode + ", errorMsg="
            + errorMsg + ", msg=" + msg + "]";
    }
}
