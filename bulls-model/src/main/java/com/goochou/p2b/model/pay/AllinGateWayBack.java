package com.goochou.p2b.model.pay;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.pay.BaseOutPay;
import com.goochou.p2b.constant.pay.BasePayBack;
import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.model.pay.allinpay.AllinGateWayUtil;

/**
 * 通联网关支付回调
 * @author ydp
 * @date 2019/06/25
 */
public class AllinGateWayBack extends BasePayBack
{
    
    public AllinGateWayBack(){}

    @Override
    public String getOutPayId()
    {
        return "trxid";
    }

    @Override
    public String getAmount()
    {
        return "trxamt";
    }

    @Override
    public String getBackInfo()
    {
        return "trxreserved";
    }

    @Override
    public String getStatus()
    {
        return "0000";
    }

    @Override
    public String getSign()
    {
        return "sign";
    }

    @Override
    public String getStatusName()
    {
        return "trxstatus";
    }
    
    @Override
    public String getInfoCiphertext(String outPayId, String amount,
            String backInfo, String status, String signKey, String resultDesc, String additionalInfo, String succTime, 
            String batchNo, String sn, String currency, String accountNo, String mobile, String resCode, String settAmount, String orderNo)
    {
        return null;
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
		return "outtrxid";
	}

	@Override
	public String getDataContent() {
		return null;
	}

	@Override
	public Boolean checkSign(HttpServletRequest request, BaseOutPay baseOutPay) {
		boolean signFlag = false;
		try {
		    TreeMap<String, String> map = new TreeMap<String, String>();
	        Map reqMap = request.getParameterMap();
	        for(Object key:reqMap.keySet()){
	            map.put(key.toString(), ((String[])reqMap.get(key))[0]);
	        }
	        signFlag = AllinGateWayUtil.validSign(map, PayConstants.ALLINPAY_MCHNT_KEY);
        }catch(Exception e){
        	e.printStackTrace();
        }
        return signFlag;
	}
}
