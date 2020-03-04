package com.goochou.p2b.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.HongbaoMapper;
import com.goochou.p2b.dao.HongbaoRedeemLinkMapper;
import com.goochou.p2b.dao.HongbaoRedeemMapper;
import com.goochou.p2b.dao.HongbaoTemplateMapper;
import com.goochou.p2b.model.Hongbao;
import com.goochou.p2b.model.HongbaoRedeem;
import com.goochou.p2b.model.HongbaoRedeemLink;
import com.goochou.p2b.model.HongbaoTemplate;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.vo.HongbaoTemplateModel;
import com.goochou.p2b.service.HongbaoRedeemService;
import com.goochou.p2b.service.MessageService;
import com.goochou.p2b.service.exceptions.LockFailureException;
import com.goochou.p2b.utils.DateFormatTools;
import com.goochou.p2b.utils.RandomUtil;

/**
 * HonghaoRedeemServiceImpl
 *
 * @author 刘源
 * @date 2016/6/23
 */
@Service
public class HongbaoRedeemServiceImpl implements HongbaoRedeemService {

    @Resource
    private HongbaoRedeemMapper hongbaoRedeemMapper;
    @Resource
    private HongbaoMapper hongbaoMapper;
    @Resource
    private HongbaoRedeemLinkMapper hongbaoRedeemLinkMapper;
    @Resource
    private HongbaoTemplateMapper hongbaoTemplateMapper;
    @Resource
    private MessageService messageService;


    @Override
    public List<Map<String, Object>> query(String keyword, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("start", start);
        map.put("limit", limit);
        return hongbaoRedeemMapper.query(map);
    }

    @Override
    public Integer queryCount(String keyword) {
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        return hongbaoRedeemMapper.queryCount(map);
    }

    @Override
    public HongbaoRedeem queryById(Integer id) {
        return hongbaoRedeemMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addHongbaoRedeem(Map<String, Object> params) {

    }

    @Override
    public synchronized Map<String, Object> saveHongbao(HongbaoRedeem redeem, User user, String phone) throws LockFailureException {
        List<Hongbao> hbao = new ArrayList<Hongbao>();
        Map<String, Object> map = new HashMap<>();
        Integer ret = 0;
        if (user != null) {
            map.put("redeemId", redeem.getId());
            map.put("userId", user.getId());
            //在红包表查询有没有兑换过红包
            HongbaoRedeem hongbaoRedeem = hongbaoRedeemMapper.queryUserUse(map);
            if (hongbaoRedeem == null) {//用户已经使用过兑换码
                map.put("ret", 1);
                return map;
            }
            //更新数据库中  兑换码信息
            Integer useCount = hongbaoRedeem.getUseCount();
            hongbaoRedeem.setUseCount(useCount - 1);//更新使用次数
            hongbaoRedeem.setUseTime(new Date());
            int lock = hongbaoRedeemMapper.updateByPrimaryKeySelective(hongbaoRedeem);
            if (lock == 0) {
                throw new LockFailureException();
            }
            //查询兑换码对应的红包模版组
            List<Map<String, Object>> list = hongbaoRedeemMapper.queryRedeemCodeHongbaoList(hongbaoRedeem.getId());
            if (list == null) {//兑换码对应的红包模版组为空
                map.put("ret", 2);
                return map;
            }
            //发放红包到用户账户中
            Double totalAmount = 0d;
            for (Map<String, Object> smap : list) {
                Integer count = Integer.valueOf(smap.get("count") == null ? "0" : smap.get("count").toString());
                if (count >= 1) {
                    for (int i = 0; i < count; i++) {
                        Hongbao hongbao = new Hongbao();
                        totalAmount += Double.valueOf(smap.get("amount") == null ? "0" : smap.get("amount").toString());
                        hongbao.setAmount(Double.valueOf(smap.get("amount") == null ? "0" : smap.get("amount").toString()));
                        //hongbao.setApplication(0);
                        hongbao.setDescript(hongbaoRedeem.getTitle());
                        hongbao.setExpireTime(hongbaoRedeem.getExpireTime());
                        hongbao.setSendTime(new Date());
                        hongbao.setLimitAmount(smap.get("limit_amount") == null ? 0 : Integer.valueOf(smap.get("limit_amount").toString()));
                        hongbao.setRedeemId(hongbaoRedeem.getId());
                        hongbao.setTemplateId(Integer.valueOf(smap.get("id").toString()));
                        hongbao.setUserId(user.getId());
                        hongbao.setType(2);//投资红包
                        hongbao.setSource(1);//来源 0网页1APP2wap
                        hongbaoMapper.insertSelective(hongbao);
                        hbao.add(hongbao);
                    }
                }
            }
            messageService.save("投资红包领取成功", "恭喜您成功领取" + totalAmount + "元投资红包，可在【我的福利】—【投资红包】中查看！快去使用此神器吧！", user.getId());
        } else { //没有注册的用户
            //更新兑换码在数据库中的信息
            Integer useCount = redeem.getUseCount();
            redeem.setUseCount(useCount - 1);//更新使用次数
            redeem.setUseTime(new Date());
            int lock = hongbaoRedeemMapper.updateByPrimaryKeySelective(redeem);
            if (lock == 0) {
                throw new LockFailureException();
            }
            //查询兑换码对应的红包模版组
            List<Map<String, Object>> list = hongbaoRedeemMapper.queryRedeemCodeHongbaoList(redeem.getId());
            if (list == null) {//兑换码对应的红包模版组为空
                map.put("ret", 2);
                return map;
            }
            //记录到  ActivityHongbao  ActivityHongbaoRecord 表中
            for (Map<String, Object> smap : list) {
                Integer count = Integer.valueOf(smap.get("count") == null ? "0" : smap.get("count").toString());
                if (count >= 1) {
                    for (int i = 0; i < count; i++) {
                        Double amount = Double.valueOf(smap.get("amount") == null ? "0" : smap.get("amount").toString());
                        //未注册用户存入  ActivityHongbaoRecord表中。同时需要返回给前台
                        Hongbao hongbao = new Hongbao();
                        hongbao.setAmount(amount);
                        hongbao.setLimitAmount(smap.get("limit_amount") == null ? 0 : Integer.valueOf(smap.get("limit_amount").toString()));
                        hongbao.setTemplateId(Integer.valueOf(smap.get("id").toString()));
                        hongbao.setExpireTime(redeem.getExpireTime());
                        hbao.add(hongbao);
                    }
                }
            }
        }
        map.put("list", hbao);
        map.put("ret", ret);
        return map;
    }

    @Override
    public HongbaoRedeem queryRedeemCode(String redeemCode) {

        return hongbaoRedeemMapper.queryRedeemCode(redeemCode);
    }

    @Override
    public Integer queryByRedeemCodeUse(Integer redeemId) {

        return hongbaoRedeemMapper.queryByRedeemCodeUse(redeemId);
    }

    @Override
    public synchronized String saveHongbaoRedeem(HongbaoRedeem hongbaoRedeem, List<HongbaoTemplateModel> list) throws Exception {
        //生成兑换码    前面四位为当前日期，后面八位为随机字符。
        String code = "";
        while (true) {
            code = DateFormatTools.dateToStr4(new Date()) + RandomUtil.getRandomCharAndNumr(8);
            //查询当前兑换码在数据库中是否存在   存在重新生成
            if (queryRedeemCode(code) == null) {
                break;
            }
        }
        hongbaoRedeem.setRedeemCode(code);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        Date expire = DateFormatTools.strToDate(sdf.format(hongbaoRedeem.getExpireTime()));
        hongbaoRedeem.setExpireTime(expire);
        hongbaoRedeem.setCreateTime(new Date());
        if (hongbaoRedeem.getType() == 1) {
            hongbaoRedeem.setUseCount(1);
        }
        hongbaoRedeemMapper.insertSelective(hongbaoRedeem);//保存兑换码信息
        Integer redeemId = hongbaoRedeem.getId();
        //保存兑换码红包组信息
        for (HongbaoTemplateModel hongbaoTemp : list) {
            HongbaoTemplate ht = new HongbaoTemplate();
            ht.setLimitAmount(hongbaoTemp.getLimitAmount());
            ht.setAmount(hongbaoTemp.getAmount());
            //ht.setMonthType(hongbaoTemp.getMonthType());
            hongbaoTemplateMapper.insertSelective(ht);
            HongbaoRedeemLink link = new HongbaoRedeemLink();
            link.setRedeemId(redeemId);
            link.setTemplateId(ht.getId());
            link.setCount(1);
            hongbaoRedeemLinkMapper.insertSelective(link);
        }
        return code;
    }

    @Override
    public Integer updateHongbaoRedeem(HongbaoRedeem hongbaoRedeem, List<HongbaoTemplateModel> list) {
        for (HongbaoTemplateModel hongbaoTemp : list) {
            HongbaoTemplate ht = new HongbaoTemplate();
            ht.setLimitAmount(hongbaoTemp.getLimitAmount());
            ht.setAmount(hongbaoTemp.getAmount());
            //ht.setMonthType(hongbaoTemp.getMonthType());
            ht.setId(hongbaoTemp.getTemplateId());
            hongbaoTemplateMapper.updateByPrimaryKey(ht);
        }
        return hongbaoRedeemMapper.updateByPrimaryKeySelective(hongbaoRedeem);
    }

    @Override
    public void save(HongbaoRedeem hongbaoRedeem, List<HongbaoTemplateModel> list) throws ParseException, LockFailureException {
        //新增时不需要判断，是否存在老的红包记录，直接删除
        if (hongbaoRedeem.getUseCount() == null) {
            hongbaoRedeem.setUseCount(1);
        }
        if (hongbaoRedeem.getId() == null) {
            while (true) {
                String code = DateFormatTools.dateToStr4(new Date()) + RandomUtil.getRandomCharAndNumr(8);
                //查询当前兑换码在数据库中是否存在   存在重新生成
                if (queryRedeemCode(code) == null) {
                    hongbaoRedeem.setRedeemCode(code);
                    break;
                }
            }
            if (hongbaoRedeem.getCreateTime() == null) {
                Date now = new Date();
                hongbaoRedeem.setCreateTime(now);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
            Date expire = DateFormatTools.strToDate(sdf.format(hongbaoRedeem.getExpireTime()));
            hongbaoRedeem.setExpireTime(expire);
            hongbaoRedeemMapper.insertSelective(hongbaoRedeem);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
            Date expire = DateFormatTools.strToDate(sdf.format(hongbaoRedeem.getExpireTime()));
            hongbaoRedeem.setExpireTime(expire);
            int version = hongbaoRedeemMapper.updateByPrimaryKeySelective(hongbaoRedeem);
            if (version == -1) {
                throw new LockFailureException();
            }
            if(null!=hongbaoRedeem.getId()){
            	//更新时必须先删除以前的配置
                List<HongbaoTemplate> temps = hongbaoTemplateMapper.queryByRedeemId(hongbaoRedeem.getId());
                for (HongbaoTemplate template : temps) {
                    hongbaoTemplateMapper.deleteByPrimaryKey(template.getId());
                }
                //删除所有的关联表
                List<HongbaoRedeemLink> links = hongbaoRedeemLinkMapper.queryById(hongbaoRedeem.getId());
                for (HongbaoRedeemLink link : links) {
                    hongbaoRedeemLinkMapper.deleteByPrimaryKey(link.getId());
                }
            }
        }
        for (HongbaoTemplateModel model : list) {
            HongbaoTemplate template = model.getHongbaoTemplate();
            hongbaoTemplateMapper.insertSelective(template);
            HongbaoRedeemLink link = new HongbaoRedeemLink();
            link.setRedeemId(hongbaoRedeem.getId());
            link.setTemplateId(template.getId());
            link.setCount(1);
            hongbaoRedeemLinkMapper.insertSelective(link);
        }


    }
}
