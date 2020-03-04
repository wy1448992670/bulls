package com.goochou.p2b;

import com.goochou.p2b.constant.ClientEnum;
import com.goochou.p2b.constant.OrderTypeEnum;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.client.TransactionClient;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceBehaviorReqeust;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceGpsRequest;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceRequest;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.DeviceTotalBehaviorRequest;
import com.goochou.p2b.hessian.openapi.pay.PayOrderRequest;
import com.goochou.p2b.utils.DateUtil;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

/**
 * @Auther: huangsj
 * @Date: 2019/7/1 15:32
 * @Description:
 */

@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class DruidtechTest {

    private String id = "5ce64465415bac48a34b55ec";


    @Test
    public void getDevices() {
        Response result = new Response();
        DeviceRequest req = new DeviceRequest();
        req.setAuthentication("");
        req.setResultLimit("100");
        req.setResultOffset("0");
        req.setResultSort("-mark");

        ServiceMessage msg = new ServiceMessage("druidtech.devices", req);
        result = OpenApiClient.getInstance().setServiceMessage(msg).send();
        System.out.println(result.toString());

    }


    @Test
    public void getDeviceGps() {
        Response result = new Response();
        DeviceGpsRequest req = new DeviceGpsRequest();
        req.setAuthentication("");
        req.setId(id);
        req.setDay("7");

        ServiceMessage msg = new ServiceMessage("druidtech.device.gps", req);
        result = OpenApiClient.getInstance().setServiceMessage(msg).send();
        System.out.println(result.toString());

    }



    @Test
    public void GetDeviceBehavior() {
        Response result = new Response();
        DeviceBehaviorReqeust req = new DeviceBehaviorReqeust();

        req.setAuthentication("dsfsadf");
        req.setResultLimit("100");
        req.setResultSort("-timestamp");

        req.setId(id);
        req.setStartTime(DateUtil.getStrRfc3339Time(new Date()));

        ServiceMessage msg = new ServiceMessage("druidtech.device.behavior", req);
        result = OpenApiClient.getInstance().setServiceMessage(msg).send();
        System.out.println(result.toString());

    }

    @Test
    public void GetDeviceTotalMsg() {
        Response result = new Response();
        DeviceTotalBehaviorRequest req = new DeviceTotalBehaviorRequest();
        req.setAuthentication("");
        req.setDeviceId(id);

        ServiceMessage msg = new ServiceMessage("druidtech.device.total", req);
        result = OpenApiClient.getInstance().setServiceMessage(msg).send();
        System.out.println(result.toString());

    }
}
