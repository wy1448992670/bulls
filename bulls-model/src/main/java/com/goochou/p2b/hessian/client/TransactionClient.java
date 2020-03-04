package com.goochou.p2b.hessian.client;

import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.hessian.HessianClient;
import com.goochou.p2b.hessian.ServiceMessage;

public class TransactionClient extends HessianClient
{
    private static TransactionClient instance = null;
    
    
    public static synchronized TransactionClient getInstance() {
        if (instance == null) {
            instance = new TransactionClient();    
        }
        return instance;
    }
    
    @Override
    public HessianClient setServiceMessage(ServiceMessage msg)
    {
        super.thread.set(msg);
        return instance;
    }

	@Override
	public String getIp() {
		// TODO Auto-generated method stub
		return ClientConstants.TRANSACTION_IP;
	}

	@Override
	public String getPORT() {
		// TODO Auto-generated method stub
		return ClientConstants.TRANSACTION_PORT;
	}

	@Override
	public String getMODULE() {
		// TODO Auto-generated method stub
		return ClientConstants.TRANSACTION_MODULE;
	}

	@Override
	protected String getUSER() {
		// TODO Auto-generated method stub
		return ClientConstants.TRANSACTION_USER;
	}

	@Override
	protected String getPWD() {
		// TODO Auto-generated method stub
		return ClientConstants.TRANSACTION_PWD;
	}

   
}
