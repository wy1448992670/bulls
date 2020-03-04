package com.goochou.p2b;

import java.util.Date;
import java.util.List;

import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.openapi.pay.AllinPayBindCardRequest;
import com.goochou.p2b.hessian.openapi.pay.AllinPayBindCardResponse;
import com.goochou.p2b.hessian.openapi.pay.AllinPayRequest;
import com.goochou.p2b.hessian.openapi.pay.AllinPayResponse;
import com.goochou.p2b.hessian.openapi.withdraw.WithdrawRequest;
import com.goochou.p2b.model.pay.allinpay.AIPGException;
import com.goochou.p2b.model.pay.allinpay.HttpUtil;
import com.goochou.p2b.model.pay.allinpay.QpayUtil;
import com.goochou.p2b.model.pay.allinpay.xml.XmlParser;
import com.goochou.p2b.model.pay.allinpay.xstruct.common.AipgReq;
import com.goochou.p2b.model.pay.allinpay.xstruct.common.AipgRsp;
import com.goochou.p2b.model.pay.allinpay.xstruct.common.InfoReq;
import com.goochou.p2b.model.pay.allinpay.xstruct.common.InfoRsp;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.qry.QTDetail;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.qry.QTransRsp;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.qry.TransQueryReq;
import com.goochou.p2b.utils.DateUtil;

public class AllinPayTest {
	public static void main(String[] args) {
//	    bindCard();
	    //{"agreeId":"201906251603237784","bankCode":"03080000","bankName":"招商银行","success":true}
//	    bindCardConfirm();
//	    createPay();
//	    pay();
//	    query("N2019345244455622");
//	    refund("N2019345244455622");
	    withdraw();
//	    withdrawquery();
	}
	private static void withdrawquery() {
    	InfoReq infoReq = QpayUtil.makeReq("200004");
        TransQueryReq queryReq = new TransQueryReq();
        queryReq.setMERCHANT_ID(PayConstants.ALLINPAY_PROVIDED_MCHNT_CD);
        queryReq.setQUERY_SN("200604000007045-0001562120638436");///查询交易的文件名
        /*queryReq.setSTART_DAY("");
        queryReq.setEND_DAY("");*/
        AipgReq req = new AipgReq();
        req.setINFO(infoReq);
        req.addTrx(queryReq);
        
        try{
            //step1 对象转xml
            String xml = XmlParser.toXml(req);
            //step2 加签
            String signedXml = QpayUtil.buildSignedXml(xml);
            //step3 发往通联
            String url = PayConstants.ALLINPAY_PROVIDED_API+"?MERCHANT_ID="+PayConstants.ALLINPAY_PROVIDED_MCHNT_CD+"&REQ_SN="+infoReq.getREQ_SN();
            System.out.println("============================请求报文============================");
            System.out.println(signedXml);
            String respText = HttpUtil.post(signedXml, url);
            System.out.println("============================响应报文============================");
            System.out.println(respText);
            //step4 验签
            if(!QpayUtil.verifyXml(respText)){
                System.out.println("====================================================>验签失败");
                return;
            }
            System.out.println("====================================================>验签成功");
            //step5 xml转对象
            AipgRsp rsp = XmlParser.parseRsp(respText);
            InfoRsp infoRsp = rsp.getINFO();
            System.out.println(infoRsp.getRET_CODE());
            System.out.println(infoRsp.getERR_MSG());
            if("0000".equals(infoRsp.getRET_CODE())){
                QTransRsp ret = (QTransRsp) rsp.trxObj();
                @SuppressWarnings("unchecked")
                List<QTDetail> list = ret.getDetails();
                for(QTDetail dt : list){
                    System.out.println(dt.getRET_CODE());
                    System.out.println(dt.getERR_MSG());
                }
            }
        }catch(AIPGException e){
            e.printStackTrace();
        }
	}
	
	private static void withdraw() {
	    WithdrawRequest request = new WithdrawRequest();
        request.setOrderNo("T19070510400022204");
        request.setAmount(1D);
        request.setBankNo("0308");
        request.setCardNo("6214832182322391");
        request.setTrueName("叶东平");
        request.setDate(DateUtil.yyyyMMdd.format(new Date()));
//	    WithdrawRequest request = new WithdrawRequest();
//        request.setOrderNo("T"+System.currentTimeMillis());
//        request.setAmount(100D);
//        request.setBankNo("0308");
//        request.setCardNo("6217881111111111111");
//        request.setTrueName("张三000090");
        ServiceMessage msg = new ServiceMessage("allinpay.withdraw", request);
        AllinPayResponse result = (AllinPayResponse) OpenApiClient.getInstance()
                .setServiceMessage(msg).send();
        System.out.println(result);
    }
	
	private static void refund(String orderNo) {
        AllinPayRequest pay = new AllinPayRequest();
        pay.setOrderNo(orderNo);
        pay.setAmount(10001L);
        ServiceMessage msg = new ServiceMessage("allinpay.order.refund", pay);
        AllinPayResponse result = (AllinPayResponse) OpenApiClient.getInstance()
                .setServiceMessage(msg).send();
        System.out.println(result);
    }
	
	private static void query(String orderNo) {
	    AllinPayRequest pay = new AllinPayRequest();
        pay.setOrderNo(orderNo);
        ServiceMessage msg = new ServiceMessage("allinpay.order.query", pay);
        AllinPayResponse result = (AllinPayResponse) OpenApiClient.getInstance()
                .setServiceMessage(msg).send();
        System.out.println(result);
	}
	
	private static void createPay() {
	    AllinPayRequest pay = new AllinPayRequest();
	    pay.setAgreeId("201906251603237784");
	    pay.setAmount(10000L);
	    pay.setOrderNo("N2019345244455621");
	    pay.setSubject("牛只购买");
        ServiceMessage msg = new ServiceMessage("allinpay.create.pay", pay);
        AllinPayResponse result = (AllinPayResponse) OpenApiClient.getInstance()
                .setServiceMessage(msg).send();
        System.out.println(result);
	}
	
	private static void pay() {
	    AllinPayRequest pay = new AllinPayRequest();
        pay.setAgreeId("201906251603237784");
        pay.setCode("111111");
        pay.setOrderNo("N2019345244455621");
        pay.setThpInfo("{\"sign\":\"\",\"tphtrxcrtime\":\"\",\"tphtrxid\":0,\"trxflag\":\"trx\",\"trxsn\":\"\"}");
        ServiceMessage msg = new ServiceMessage("allinpay.pay", pay);
        AllinPayResponse result = (AllinPayResponse) OpenApiClient.getInstance()
                .setServiceMessage(msg).send();
        System.out.println(result);
    }
	
	private static void bindCard() {
	    AllinPayBindCardRequest pay = new AllinPayBindCardRequest();
        pay.setBankCard("6214850210049661");
        pay.setIdentityCard("350781198408154015");
        pay.setPhoneNo("15001824049");
        pay.setTrueName("叶东平");
        pay.setUserId("4018");
        ServiceMessage msg = new ServiceMessage("allinpay.bind.card", pay);
        AllinPayBindCardResponse result = (AllinPayBindCardResponse) OpenApiClient.getInstance()
                .setServiceMessage(msg).send();
        System.out.println(result);
	}
	
	private static void bindCardConfirm() {
	    AllinPayBindCardRequest pay = new AllinPayBindCardRequest();
        pay.setBankCard("6214850210049661");
        pay.setIdentityCard("350781198408154015");
        pay.setPhoneNo("15001824049");
        pay.setTrueName("叶东平");
        pay.setUserId("4018");
        pay.setCode("111111");
        ServiceMessage msg = new ServiceMessage("allinpay.bind.card.confirm", pay);
        AllinPayBindCardResponse result = (AllinPayBindCardResponse) OpenApiClient.getInstance()
                .setServiceMessage(msg).send();
        System.out.println(result);
	}
	
	private static void bindCardMessage() {
	    AllinPayBindCardRequest pay = new AllinPayBindCardRequest();
        pay.setBankCard("6214850210049661");
        pay.setIdentityCard("350781198408154015");
        pay.setPhoneNo("15001824049");
        pay.setTrueName("叶东平");
        pay.setUserId("4018");
        pay.setThpInfo("");
        ServiceMessage msg = new ServiceMessage("allinpay.bind.card.message", pay);
        AllinPayBindCardResponse result = (AllinPayBindCardResponse) OpenApiClient.getInstance()
                .setServiceMessage(msg).send();
        System.out.println(result);
    }
}
