package com.goochou.p2b.constant.redis;

/**
 * @author: huangsj
 * @Date: 2019/12/17 18:09
 * @Description:
 */
public class RedisConstants {


    //秒杀活动商品配置数据
    public final static String SECONDKILL_PRODUCT_DETAIL="sk_ad_%d";
    //秒杀活动单个商品已销售数量
    public final static String SECONDKILL_ACTIVITY_SALED_COUNT ="sk_ad_%d_saled";
    //已卖完
    public final static String SECONDKILL_ACTIVITY_SALE_OVER="sk_ad_%d_sale_over";
    //秒杀用户已抢购的商品是否未支付
    public final static String SECONDKILL_ACTIVITY_PERSON_UNPAY="sk_ad_%d_uid_%d_un_pay";
    //秒杀用户已购买的商品数量
    public final static String SECONDKILL_ACTIVITY_PERSON_BUYED_COUNT ="sk_ad_%d_uid_%d_buyed";
    //秒杀用户是否不能购买 0 可以购买  1不能购买
    public final static String SECONDKILL_ACTIVITY_PERSON_CANNOT_BUY ="sk_ad_%d_uid_%d_cannot_buy";


}
