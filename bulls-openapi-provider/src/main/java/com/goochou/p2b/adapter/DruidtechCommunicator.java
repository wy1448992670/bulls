package com.goochou.p2b.adapter;

import com.alibaba.fastjson.JSONArray;
import com.goochou.p2b.common.utils.HttpsClientRequestFactory;
import com.goochou.p2b.constant.farmcloud.Constants;
import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.openapi.farmcloud.CommonResponse;
import com.goochou.p2b.hessian.openapi.farmcloud.druidtech.*;
import com.goochou.p2b.model.TrackDevice;

import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @Auther: huangsj
 * @Date: 2019/6/28 15:05
 * @Description:
 */
public class DruidtechCommunicator implements ICommunicator {

    private static final Logger logger = Logger.getLogger(DruidtechCommunicator.class);


    private static String remoteURL = Constants.DRUIDTECH_API;

    public static Map<String, String> buildBasicMap() {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("appid", PayConstants.ALLINPAY_APP_ID);
        params.put("cusid", PayConstants.ALLINPAY_MCHNT_CD);
        params.put("version", "11");
        params.put("randomstr", System.currentTimeMillis() + "");
        return params;
    }

    @Override
    public Response httSend2(Request request, String method) throws CommunicateException {

        Response result = null;
        Map<String, String> map = new HashMap<String, String>();

        // 发送内容
        switch (method) {
//            case Constants.DRUIDTECH_LOGIN:
//                result = login(request);
//                break;
            case Constants.DRUIDTECH_GET_TOKEN:
                result = getToken();
                break;
            case Constants.DRUIDTECH_REFRESH_TOKEN:
                result = refreshToken();
                break;
            case Constants.DRUIDTECH_DEVICE:
                result = getDevices(request);
                break;
            case Constants.DRUIDTECH_DEVICE_GPS:
                result = getDeviceGps(request);
                break;
            case Constants.DRUIDTECH_DEVICE_BEHAVIOR:
                result = getDeviceBehaviors(request);
                break;
            case Constants.DRUIDTECH_DEVICE_TOTAL_BEHAVIOR:
                result = getDeviceTotalBehavior(request);
                break;
            default:
                break;
        }
        logger.info("result = " + result);
        return result;
    }

    @Override
    public String httSend(String request, String method, String requestType)
            throws CommunicateException {
        return "";
    }

    private Response getToken() {

//        AccessID：5d1979f9415bac40ae3cea99
//        AccessSecret：a580c1fa3ad9a65be914b368633a5baf

        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setAccess_id(Constants.DRUIDTECH_ACCESS_ID);
        tokenRequest.setSecret(Constants.DRUIDTECH_ACCESS_SECRET);

        String url = remoteURL + Constants.DRUIDTECH_GET_TOKEN;

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");

        RestTemplate template = new RestTemplate(new HttpsClientRequestFactory());

        String body = JSONArray.toJSONString(tokenRequest);
        HttpEntity<String> requestEntity = new HttpEntity<String>(body, requestHeaders);
        ResponseEntity<LoginResponse> response = template.exchange(url, HttpMethod.POST, requestEntity, LoginResponse.class);


        CommonResponse<LoginResponse> commonResponse = new CommonResponse();
        commonResponse.setStatusCode(response.getStatusCode().value());

        if (response.getStatusCode().value() == 200) {
            LoginResponse result = response.getBody();

            HttpHeaders headers = response.getHeaders();
            String token = headers.getFirst("X-Druid-Authentication");

            result.setAuthentication(token);

            commonResponse.setSuccess(true);
            commonResponse.setData(result);
        } else {
            commonResponse.setSuccess(false);
        }
        return commonResponse;
    }


    private Response refreshToken() {

        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setAccess_id(Constants.DRUIDTECH_ACCESS_ID);
        tokenRequest.setSecret(Constants.DRUIDTECH_ACCESS_SECRET);

        String url = remoteURL + Constants.DRUIDTECH_REFRESH_TOKEN;

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");

        RestTemplate template = new RestTemplate(new HttpsClientRequestFactory());

        String body = JSONArray.toJSONString(tokenRequest);
        HttpEntity<String> requestEntity = new HttpEntity<String>(body, requestHeaders);
        ResponseEntity<LoginResponse> response = template.exchange(url, HttpMethod.POST, requestEntity, LoginResponse.class);


        CommonResponse<LoginResponse> commonResponse = new CommonResponse();
        commonResponse.setStatusCode(response.getStatusCode().value());

        if (response.getStatusCode().value() == 200) {
            LoginResponse result = response.getBody();

            HttpHeaders headers = response.getHeaders();
            String token = headers.getFirst("X-Druid-Authentication");

            result.setAuthentication(token);

            commonResponse.setSuccess(true);
            commonResponse.setData(result);
        } else {
            commonResponse.setSuccess(false);
        }
        return commonResponse;
    }


    private Response getDevices(Request request) {

        DeviceRequest deviceRequest = (DeviceRequest) request;

        String url = remoteURL + Constants.DRUIDTECH_DEVICE;

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("x-druid-authentication", deviceRequest.getAuthentication());
        requestHeaders.add("x-result-limit", deviceRequest.getResultLimit());
        requestHeaders.add("x-result-offset", deviceRequest.getResultOffset());
        requestHeaders.add("x-result-sort", deviceRequest.getResultSort());

        RestTemplate template = new RestTemplate(new HttpsClientRequestFactory());

        HttpEntity<String> requestEntity = new HttpEntity<String>("", requestHeaders);

        ParameterizedTypeReference pt = new ParameterizedTypeReference<List<TrackDevice>>() {
        };


        ResponseEntity<List<TrackDevice>> response = null;
        CommonResponse<List<TrackDevice>> commonResponse = new CommonResponse();

        try {
            response = template.exchange(url, HttpMethod.GET, requestEntity, pt);
        } catch (HttpClientErrorException e) {
            logger.error("请求设备接口出错："+e.getMessage());
            commonResponse.setSuccess(false);
            commonResponse.setStatusCode(401);
            return commonResponse;
        }

        commonResponse.setStatusCode(response.getStatusCode().value());

        if (response.getStatusCode().value() == 200) {
//            400    :   参数错误
//            500    ：  服务内部错误
//            403    :   越权操作
//            200    ：  正确
//            401    ：  是鉴权失效
//            HttpHeaders headers = response.getHeaders();
//            int statusCode = Integer.parseInt(headers.getFirst("statusCode"));
//            if (statusCode != 200) {
//                logger.error("请求设备接口出错：" + statusCode);
//                commonResponse.setSuccess(false);
//                commonResponse.setStatusCode(statusCode);
//                return commonResponse;
//            }


            List<TrackDevice> result = response.getBody();

            logger.info(result.get(0).toString());

            commonResponse.setSuccess(true);
            commonResponse.setData(result);
        } else {
            commonResponse.setSuccess(false);
        }
        return commonResponse;
    }


    private Response getDeviceGps(Request request) {

        DeviceGpsRequest deviceGpsRequest = (DeviceGpsRequest) request;

        String url = remoteURL + String.format(Constants.DRUIDTECH_DEVICE_GPS, deviceGpsRequest.getId(), deviceGpsRequest.getDay());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("x-druid-authentication", deviceGpsRequest.getAuthentication());

        RestTemplate template = new RestTemplate(new HttpsClientRequestFactory());

        HttpEntity<String> requestEntity = new HttpEntity<String>("", requestHeaders);

        ParameterizedTypeReference pt = new ParameterizedTypeReference<List<DeviceGpsResponse>>() {
        };

        ResponseEntity<List<DeviceGpsResponse>> response = null;
        CommonResponse<List<DeviceGpsResponse>> commonResponse = new CommonResponse();

        try {
            response = template.exchange(url, HttpMethod.GET, requestEntity, pt);
        } catch (HttpClientErrorException e) {
            logger.error("请求设备接口出错："+e.getMessage());
            commonResponse.setSuccess(false);
            commonResponse.setStatusCode(401);
            return commonResponse;
        }


        commonResponse.setStatusCode(response.getStatusCode().value());

        if (response.getStatusCode().value() == 200) {

            List<DeviceGpsResponse> result = response.getBody();

            commonResponse.setSuccess(true);
            commonResponse.setData(result);
        } else {
            commonResponse.setSuccess(false);
        }
        return commonResponse;
    }

    private Response getDeviceBehaviors(Request request) {

        DeviceBehaviorReqeust deviceBehaviorReqeust = (DeviceBehaviorReqeust) request;

        String url = remoteURL + String.format(Constants.DRUIDTECH_DEVICE_BEHAVIOR, deviceBehaviorReqeust.getId(), deviceBehaviorReqeust.getStartTime());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("x-druid-authentication", deviceBehaviorReqeust.getAuthentication());
        requestHeaders.add("x-result-limit", deviceBehaviorReqeust.getResultLimit());
        requestHeaders.add("x-result-sort", deviceBehaviorReqeust.getResultSort());

        RestTemplate template = new RestTemplate(new HttpsClientRequestFactory());

        HttpEntity<String> requestEntity = new HttpEntity<String>("", requestHeaders);

        ParameterizedTypeReference pt = new ParameterizedTypeReference<List<DeviceBehaviorResponse>>() {
        };

        CommonResponse<List<DeviceBehaviorResponse>> commonResponse = new CommonResponse();
        ResponseEntity<List<DeviceBehaviorResponse>> response = null;

        try {
            response = template.exchange(url, HttpMethod.GET, requestEntity, pt);
        } catch (HttpClientErrorException e) {
            logger.error("请求设备接口出错："+e.getMessage());
            commonResponse.setSuccess(false);
            commonResponse.setStatusCode(401);
            return commonResponse;
        }


        commonResponse.setStatusCode(response.getStatusCode().value());

        if (response.getStatusCode().value() == 200) {

            List<DeviceBehaviorResponse> result = response.getBody();
            commonResponse.setSuccess(true);
            commonResponse.setData(result);
        } else {
            commonResponse.setSuccess(false);
        }
        return commonResponse;
    }


    private Response getDeviceTotalBehavior(Request request) {

        DeviceTotalBehaviorRequest deviceBehaviorReqeust = (DeviceTotalBehaviorRequest) request;

        String url = remoteURL + String.format(Constants.DRUIDTECH_DEVICE_TOTAL_BEHAVIOR, deviceBehaviorReqeust.getDeviceId());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("x-druid-authentication", deviceBehaviorReqeust.getAuthentication());

        RestTemplate template = new RestTemplate(new HttpsClientRequestFactory());

        HttpEntity<String> requestEntity = new HttpEntity<String>("", requestHeaders);


        CommonResponse<DeviceTotalBehaviorResponse> commonResponse = new CommonResponse();
        ResponseEntity<String> response = null;

        try {
            response = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            logger.error("请求设备接口出错："+e.getMessage());
            commonResponse.setSuccess(false);
            commonResponse.setStatusCode(401);
            return commonResponse;
        }

        commonResponse.setStatusCode(response.getStatusCode().value());

        if (response.getStatusCode().value() == 200) {
            String responseBody = response.getBody();

            DeviceTotalBehaviorResponse result = new DeviceTotalBehaviorResponse();
            result.setCount(responseBody);

            commonResponse.setSuccess(true);
            commonResponse.setData(result);
        } else {
            commonResponse.setSuccess(false);
        }
        return commonResponse;
    }

}