package com.goochou.p2b.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.HongbaoMapper;
import com.goochou.p2b.dao.RateCouponMapper;
import com.goochou.p2b.dao.WithdrawCouponMapper;
import com.goochou.p2b.dao.YaoRecordMapper;
import com.goochou.p2b.model.Hongbao;
import com.goochou.p2b.model.RateCoupon;
import com.goochou.p2b.model.WithdrawCoupon;
import com.goochou.p2b.model.YaoRecord;
import com.goochou.p2b.model.YaoRecordExample;
import com.goochou.p2b.service.YaoRecordService;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.CommonUtils;

@Service
public class YaoRecordServiceImpl implements YaoRecordService {

    @Resource
    private YaoRecordMapper yaoRecordMapper;
    @Resource
    private HongbaoMapper hongbaoMapper;
    @Resource
    private RateCouponMapper rateCouponMapper;
    @Resource
    private WithdrawCouponMapper withdrawCouponMapper;

    @Override
    public void save(YaoRecord yr) {
        yaoRecordMapper.insert(yr);
    }

    @Override
    public List<Map<String, Object>> getCountByType(Date startTime, Date endTime) {
        return yaoRecordMapper.getCountByType(startTime, endTime);
    }

    @Override
    public Integer getCountByTime(Integer userId, Date startTime, Date endTime) {
        YaoRecordExample example = new YaoRecordExample();
        example.createCriteria().andUserIdEqualTo(userId).andCreateDateBetween(startTime, endTime);
        return yaoRecordMapper.countByExample(example);
    }

    @Override
    public List<Map<String, Object>> listByUser(Integer userId, Integer start, Integer limit) {
        List<Map<String, Object>> list = yaoRecordMapper.listByUser(userId, start, limit);
        List<Map<String, Object>> returnList = new ArrayList<>();
        if (null != list && !list.isEmpty()) {
            for (Map<String, Object> record : list) {
                Map<String, Object> map = new HashMap<>();
                Integer type = (Integer) record.get("type");
                String time = new SimpleDateFormat("yyyy-MM-dd").format((Date) record.get("create_date"));
                String words = (String) record.get("words");
                Integer otherId = (Integer) record.get("other_id");
                map.put("time", time);
                map.put("phone", CommonUtils.getPhone((String) record.get("phone")));
                String text = "摇到";
                if (type.equals(0)) {
                    if (StringUtils.isNotBlank(words)) {
                        text += "“" + words + "”";
                    } else {
                        text += "谢谢参与";
                    }
                } else if (type.equals(1)) {
                    Hongbao hb = hongbaoMapper.selectByPrimaryKey(otherId);
                    if(null != hb){
                    	text += "" + hb.getAmount() + "元现金红包";
                    }
                } else if (type.equals(2)) {
                    //灵活宝加息券
                    RateCoupon coupon = rateCouponMapper.selectByPrimaryKey(otherId);
                    if(null != coupon){
                    	text += "" + BigDecimalUtil.multi(coupon.getRate(), 100) + "%灵活宝加息券";
                    }
                } else if (type.equals(3)) {

                } else if (type.equals(5)) {
                    //散标加息券
                    RateCoupon coupon = rateCouponMapper.selectByPrimaryKey(otherId);
                    if(null != coupon){
                    	text += "" + BigDecimalUtil.multi(coupon.getRate(), 100) + "%散标加息券";
                    }
                } else if (type.equals(6)) {
                    //投资红包
                    Hongbao hb = hongbaoMapper.selectByPrimaryKey(otherId);
                    if(null != hb){
                    	text += "" + hb.getAmount() + "元投资红包";
                    }
                } else if (type.equals(7)) {
                    //提现券
                    WithdrawCoupon wc = withdrawCouponMapper.selectByPrimaryKey(otherId);
                    if(null != wc){
                    	text += "" + wc.getTitle();
                    }
                }
                map.put("text", text);
                returnList.add(map);
            }
        }
        return returnList;
    }

    @Override
    public Integer listByUserCount(Integer userId) {
        return yaoRecordMapper.listByUserCount(userId);
    }

}
