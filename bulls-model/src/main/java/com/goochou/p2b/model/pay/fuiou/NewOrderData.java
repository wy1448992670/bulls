package com.goochou.p2b.model.pay.fuiou;

import java.util.ArrayList;
import java.util.List;

public class NewOrderData {
    private String order_valid_time;
    private String bank_cd;
    private String goods_price;
    private String goods_num;
    private String goods_des;
    private String goods_display_url;
    private String md5;
    private String rtMd5;
    private String order_opr;
    private String iss_ins_cd;
    private String RSA;
    private String user_id;
    private String card_no;
    private String cert_type;
    private String cert_no;
    private String cardholder_name;

    public static String xmlHeader = "<?xml version=‘1.0’ encoding=‘UTF-8’?><Content>";
    public static String xmlTail = "</Content>";
    public static String SignedHeader = "<SignedValue>";
    public static String SignedTail = "</SignedValue>";
    public static String orderResultHeader = "<orderResult>";
    public static String orderResultTail = "</orderResult>";

    private String version = "1.0";
    private String txn_dir = "";
    private String mchnt_cd = "";
    private String mchnt_date = "";
    private String mchnt_time = "";
    private String mchnt_ssn = "";
    private String mchnt_key = "";
    private String busi_cd = "";
    private String order_id = "";
    private String order_st = "";
    private String order_date = "";
    private String order_valid_date = "";
    private String order_amt = "";
    private String cur_type = "";
    private String pay_notify_flag = "";
    private String page_notify_url = "";
    private String back_notify_url = "";
    private String goods_id = "";
    private String goods_name = "";
    private String order_pay_type = "";
    private String payer_desc = "";
    private String payer_notify_type = "";
    private String payer_cell_phone = "";
    private String payer_email = "";

    private String fy_ssn = "";
    private String fy_date = "";
    private String fy_time = "";
    private String order_pay_code = "";
    private String order_pay_error = "";
    private String resv1 = "";
    private String resv2 = "";
    private String resv3 = "";
    private String resv4 = "";
    private String resv5 = "";

    public String getMchnt_key() {
        return mchnt_key;
    }

    public void setMchnt_key(String mchnt_key) {
        this.mchnt_key = mchnt_key;
    }

    public String getIss_ins_cd() {
        return iss_ins_cd;
    }

    public void setIss_ins_cd(String iss_ins_cd) {
        this.iss_ins_cd = iss_ins_cd;
    }

    public String getRtMd5() {
        return rtMd5;
    }

    public void setRtMd5(String rtMd5) {
        this.rtMd5 = rtMd5;
    }

    public String getOrder_opr() {
        return order_opr;
    }

    public void setOrder_opr(String order_opr) {
        this.order_opr = order_opr;
    }

    public String getMchnt_cd() {
        return mchnt_cd;
    }

    public void setMchnt_cd(String mchnt_cd) {
        this.mchnt_cd = mchnt_cd;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_amt() {
        return order_amt;
    }

    public void setOrder_amt(String order_amt) {
        this.order_amt = order_amt;
    }

    public String getOrder_pay_type() {
        return order_pay_type;
    }

    public void setOrder_pay_type(String order_pay_type) {
        this.order_pay_type = order_pay_type;
    }

    public String getPage_notify_url() {
        return page_notify_url;
    }

    public void setPage_notify_url(String page_notify_url) {
        this.page_notify_url = page_notify_url;
    }

    public String getBack_notify_url() {
        return back_notify_url;
    }

    public void setBack_notify_url(String back_notify_url) {
        this.back_notify_url = back_notify_url;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getOrder_valid_time() {
        return order_valid_time;
    }

    public void setOrder_valid_time(String order_valid_time) {
        this.order_valid_time = order_valid_time;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getCert_type() {
        return cert_type;
    }

    public void setCert_type(String cert_type) {
        this.cert_type = cert_type;
    }

    public String getCert_no() {
        return cert_no;
    }

    public void setCert_no(String cert_no) {
        this.cert_no = cert_no;
    }

    public String getCardholder_name() {
        return cardholder_name;
    }

    public void setCardholder_name(String cardholder_name) {
        this.cardholder_name = cardholder_name;
    }

    public String getBank_cd() {
        return bank_cd;
    }

    public void setBank_cd(String bank_cd) {
        this.bank_cd = bank_cd;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }

    public String getGoods_des() {
        return goods_des;
    }

    public void setGoods_des(String goods_des) {
        this.goods_des = goods_des;
    }

    public String getGoods_display_url() {
        return goods_display_url;
    }

    public void setGoods_display_url(String goods_display_url) {
        this.goods_display_url = goods_display_url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getOrder_st() {
        return order_st;
    }

    public void setOrder_st(String order_st) {
        this.order_st = order_st;
    }

    public static String getXmlHeader() {
        return xmlHeader;
    }

    public static void setXmlHeader(String xmlHeader) {
        NewOrderData.xmlHeader = xmlHeader;
    }

    public static String getXmlTail() {
        return xmlTail;
    }

    public static void setXmlTail(String xmlTail) {
        NewOrderData.xmlTail = xmlTail;
    }

    public static String getSignedHeader() {
        return SignedHeader;
    }

    public static void setSignedHeader(String signedHeader) {
        SignedHeader = signedHeader;
    }

    public static String getSignedTail() {
        return SignedTail;
    }

    public static void setSignedTail(String signedTail) {
        SignedTail = signedTail;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTxn_dir() {
        return txn_dir;
    }

    public void setTxn_dir(String txn_dir) {
        this.txn_dir = txn_dir;
    }

    public String getMchnt_date() {
        return mchnt_date;
    }

    public void setMchnt_date(String mchnt_date) {
        this.mchnt_date = mchnt_date;
    }

    public String getMchnt_time() {
        return mchnt_time;
    }

    public void setMchnt_time(String mchnt_time) {
        this.mchnt_time = mchnt_time;
    }

    public String getMchnt_ssn() {
        return mchnt_ssn;
    }

    public void setMchnt_ssn(String mchnt_ssn) {
        this.mchnt_ssn = mchnt_ssn;
    }

    public String getBusi_cd() {
        return busi_cd;
    }

    public void setBusi_cd(String busi_cd) {
        this.busi_cd = busi_cd;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_valid_date() {
        return order_valid_date;
    }

    public void setOrder_valid_date(String order_valid_date) {
        this.order_valid_date = order_valid_date;
    }

    public String getCur_type() {
        return cur_type;
    }

    public void setCur_type(String cur_type) {
        this.cur_type = cur_type;
    }

    public String getPay_notify_flag() {
        return pay_notify_flag;
    }

    public void setPay_notify_flag(String pay_notify_flag) {
        this.pay_notify_flag = pay_notify_flag;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getPayer_desc() {
        return payer_desc;
    }

    public void setPayer_desc(String payer_desc) {
        this.payer_desc = payer_desc;
    }

    public String getPayer_notify_type() {
        return payer_notify_type;
    }

    public void setPayer_notify_type(String payer_notify_type) {
        this.payer_notify_type = payer_notify_type;
    }

    public String getPayer_cell_phone() {
        return payer_cell_phone;
    }

    public void setPayer_cell_phone(String payer_cell_phone) {
        this.payer_cell_phone = payer_cell_phone;
    }

    public String getPayer_email() {
        return payer_email;
    }

    public void setPayer_email(String payer_email) {
        this.payer_email = payer_email;
    }

    public String getFy_ssn() {
        return fy_ssn;
    }

    public void setFy_ssn(String fy_ssn) {
        this.fy_ssn = fy_ssn;
    }

    public String getFy_date() {
        return fy_date;
    }

    public void setFy_date(String fy_date) {
        this.fy_date = fy_date;
    }

    public String getFy_time() {
        return fy_time;
    }

    public void setFy_time(String fy_time) {
        this.fy_time = fy_time;
    }

    public String getOrder_pay_code() {
        return order_pay_code;
    }

    public void setOrder_pay_code(String order_pay_code) {
        this.order_pay_code = order_pay_code;
    }

    public String getOrder_pay_error() {
        return order_pay_error;
    }

    public void setOrder_pay_error(String order_pay_error) {
        this.order_pay_error = order_pay_error;
    }

    public String getResv1() {
        return resv1;
    }

    public void setResv1(String resv1) {
        this.resv1 = resv1;
    }

    public String getResv2() {
        return resv2;
    }

    public void setResv2(String resv2) {
        this.resv2 = resv2;
    }

    public String getResv3() {
        return resv3;
    }

    public void setResv3(String resv3) {
        this.resv3 = resv3;
    }

    public String getResv4() {
        return resv4;
    }

    public void setResv4(String resv4) {
        this.resv4 = resv4;
    }

    public String getResv5() {
        return resv5;
    }

    public void setResv5(String resv5) {
        this.resv5 = resv5;
    }

    public String getRSA() {
        return RSA;
    }

    public void setRSA(String RSA) {
        this.RSA = RSA;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /*
             * 将来自富友系统的String型字符串转换为供页面显示的OrderData列表
             * xml
             */
    public static List convert2OrderDateList(String sOrderList) {
        List orderList = new ArrayList();
        int index = sOrderList.indexOf(orderResultHeader);
        String xml = "";
        String tmp = sOrderList;
        while (index != -1) {
            xml = tmp.substring(index + orderResultHeader.length(), tmp.indexOf(orderResultTail));
            tmp = tmp.substring(tmp.indexOf(orderResultTail) + orderResultTail.length());
            index = tmp.indexOf(orderResultHeader);
            NewOrderData orderDate = new NewOrderData();

            try {
                orderDate = NewOrderData.convertXml2Bean(xml);
            } catch (Exception e) {
                e.printStackTrace();
            }
            orderList.add(orderDate);
        }
        return orderList;
    }

    /*
     * 取指定xml tag赋给NewOrderData
     */
    public static NewOrderData convertXml2Bean(String xml) throws Exception {
        NewOrderData orderDate = new NewOrderData();
        orderDate.setMchnt_cd(getValueByTagName(xml, (String) "mchnt_cd"));
        orderDate.setOrder_id(getValueByTagName(xml, (String) "order_id"));
        orderDate.setOrder_amt(getValueByTagName(xml, (String) "order_amt"));
        orderDate.setOrder_date(getValueByTagName(xml, (String) "order_date"));
        orderDate.setOrder_st(getValueByTagName(xml, (String) "order_st"));
        orderDate.setOrder_pay_code(getValueByTagName(xml, (String) "order_pay_code"));
        orderDate.setOrder_pay_error(getValueByTagName(xml, (String) "order_pay_error"));
        orderDate.setOrder_opr(getValueByTagName(xml, (String) "order_opr"));
        orderDate.setResv1(getValueByTagName(xml, (String) "resv1"));
        orderDate.setFy_ssn(getValueByTagName(xml, (String) "fy_ssn"));
        return orderDate;
    }

    public static String getValueByTagName(String xml, String tagName) {
        try {
            String beginTag = "<" + tagName + ">";
            String endTag = "</" + tagName + ">";
            int begin = xml.indexOf(beginTag);
            int end = xml.indexOf(endTag);
            return xml.substring(begin + beginTag.length(), end).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
