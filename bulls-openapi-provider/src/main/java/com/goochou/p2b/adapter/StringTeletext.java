package com.goochou.p2b.adapter;

import com.goochou.p2b.hessian.Request;

/**
 * Created on 2014-8-28
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [消息体]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class StringTeletext implements IStreamableMessage {

    protected Request req;
    protected String reqStr;
    protected String channelName;
    protected String requestType;


    public StringTeletext(String reqStr, String channelName,String requestType) {
        this.reqStr = reqStr;
        this.channelName = channelName;
        this.requestType = requestType;
    }
    
    public StringTeletext(Request req, String channelName,String requestType) {
        this.req=req;
        this.channelName = channelName;
        this.requestType = requestType;
    }

    @Override
    public Request getReq() {
        return req;
    }

    @Override
    public String getReqStr()
    {
        return reqStr;
    }

    public void setReqStr(String reqStr)
    {
        this.reqStr = reqStr;
    }

    @Override
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
    
    @Override
    public String getRequestType()
    {
        return requestType;
    }

    public void setRequestType(String requestType)
    {
        this.requestType = requestType;
    }
}