package com.goochou.p2b.model.pay;

import javax.servlet.http.HttpServletRequest;

import com.fuiou.util.MD5;
import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.pay.BaseOutPay;
import com.goochou.p2b.constant.pay.BasePayBack;
import com.goochou.p2b.constant.pay.PayConstants;

/**
 * Created on 2014-8-26
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [汇潮回调参数]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class FuiouQuickPayBack extends BasePayBack
{
    
    public FuiouQuickPayBack(){}

    @Override
    public String getOutPayId()
    {
        return "ORDERID";
    }

    @Override
    public String getAmount()
    {
        return "AMT";
    }

    @Override
    public String getBackInfo()
    {
        return "RESPONSEMSG";
    }

    @Override
    public String getStatus()
    {
        return "0000";
    }

    @Override
    public String getSign()
    {
        return "SIGN";
    }

    @Override
    public String getStatusName()
    {
        return "RESPONSECODE";
    }
    
    @Override
    public String getInfoCiphertext(String outPayId, String amount,
            String backInfo, String status, String signKey, String resultDesc, String additionalInfo, String succTime, 
            String batchNo, String sn, String currency, String accountNo, String mobile, String resCode, String settAmount, String orderNo)
    {
        String sign = outPayId+"&"+amount+"&"+status+"&"+signKey;
        return MD5.MD5Encode(sign).toUpperCase();
    }

    @Override
    public Boolean getCent()
    {
        return true;
    }

    @Override
    public String getResultDesc()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getAdditionalInfo()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getSuccTime()
    {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public String getRes() {
		return "success";
	}

	@Override
	public String getRequestType() {
		return Constants.REQUEST_POST;
	}

	@Override
	public String getBatchNo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCurrency() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAccountNo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMobile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getResCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSettAmount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrderNo() {
		return "MCHNTORDERID";
	}

	@Override
	public String getDataContent() {
		return null;
	}

	@Override
	public Boolean checkSign(HttpServletRequest request, BaseOutPay baseOutPay) {
		boolean signFlag = false;
		try {
            String version = request.getParameter("VERSION");
            String type = request.getParameter("TYPE");
            String responseCode = request.getParameter("RESPONSECODE");
            String responseMsg = request.getParameter("RESPONSEMSG");
            String mchntCd = request.getParameter("MCHNTCD");
            String mchntOrderId = request.getParameter("MCHNTORDERID");
            String orderId = request.getParameter("ORDERID");
            String bankCard = request.getParameter("BANKCARD");
            String amt = request.getParameter("AMT");
            String sign = request.getParameter("SIGN");

            // 校验签名
            String signPain = new StringBuffer().append(type).append("|").append(version).append("|").append(responseCode)
                    .append("|").append(mchntCd).append("|").append(mchntOrderId).append("|").append(orderId).append("|")
                    .append(amt).append("|").append(bankCard).append("|").append(PayConstants.FUIOU_QUICK_MCHNT_KEY).toString();
            signFlag = MD5.MD5Encode(signPain).equals(sign);
        }catch(Exception e){
        	e.printStackTrace();
        }
        return signFlag;
	}
}
