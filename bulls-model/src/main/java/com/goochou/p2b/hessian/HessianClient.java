package com.goochou.p2b.hessian;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.goochou.p2b.hessian.api.HessianInterface;

/**
 * Created on 2014-8-18
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [Hessian服务客户端]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 *
 * @author [叶东平] [58294114@qq.com]
 * @version 1.0
 */
public abstract class HessianClient {

    private static HessianProxyFactory hessianProxyFactory = null;

    protected ThreadLocal<ServiceMessage> thread = new ThreadLocal<ServiceMessage>();

    public abstract String getIp();

    public abstract String getPORT();

    public abstract String getMODULE();

    protected abstract String getUSER();

    protected abstract String getPWD();

    protected HessianClient() {
        if (null == hessianProxyFactory) {
            hessianProxyFactory = new HessianProxyFactory();
            hessianProxyFactory.setReadTimeout(18000);
        }
    }

    public abstract HessianClient setServiceMessage(ServiceMessage msg);

    protected ServiceMessage getServiceMessage() {

        return thread.get();
    }

    public Response send() {
        Response result = new Response();
        try {
            HessianInterface api = (HessianInterface) hessianProxyFactory
                    .create(HessianInterface.class, "http://"+this.getIp()+":"+this.getPORT()+"/"+this.getMODULE()+"/remote/com.rsc.interface");
//                    .create(HessianInterface.class, "http://localhost:8080/remote/com.rsc.interface");
            result = api.execute(getServiceMessage());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }


}
