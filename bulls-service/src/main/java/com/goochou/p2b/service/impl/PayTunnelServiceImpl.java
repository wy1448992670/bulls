package com.goochou.p2b.service.impl;

import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.NewsMapper;
import com.goochou.p2b.dao.PayTunnelMapper;
import com.goochou.p2b.model.NewsWithBLOBs;
import com.goochou.p2b.model.PayTunnel;
import com.goochou.p2b.model.PayTunnelExample;
import com.goochou.p2b.service.PayTunnelService;

import static com.goochou.p2b.constant.pay.ChannelConstants.PAY_TUNNEL_STATUS_0;
import static com.goochou.p2b.constant.pay.ChannelConstants.PAY_TUNNEL_TYPE_0;

@Service
public class PayTunnelServiceImpl implements PayTunnelService {

    @Resource
    private PayTunnelMapper payTunnelMapper;
    @Resource
    private NewsMapper newsMapper;


    @Override
    public List<PayTunnel> list(Integer start, Integer limit) {
        PayTunnelExample example = new PayTunnelExample();
        example.setLimitStart(start);
        example.setLimitEnd(limit);
        return payTunnelMapper.selectByExample(example);
    }

    @Override
    public void update(PayTunnel tunnel) {
        payTunnelMapper.updateByPrimaryKeySelective(tunnel);
    }

    @Override
    public Integer listCount() {
        return payTunnelMapper.countByExample(new PayTunnelExample());
    }

    @Override
    public void save(PayTunnel tunnel) {
        payTunnelMapper.insert(tunnel);
        //System.out.println(2/0);
        NewsWithBLOBs news = new NewsWithBLOBs();
        news.setTitle("11111111111");
        int i = newsMapper.insert(news);
    }

    @Override
    public void delete(Integer id) {
        payTunnelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PayTunnel get(Integer id) {
        return payTunnelMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PayTunnel> listUsable(Integer type) {
        PayTunnelExample example = new PayTunnelExample();
        example.createCriteria().andTypeEqualTo(type).andStatusEqualTo(0);
        example.setOrderByClause("weight desc");
        return payTunnelMapper.selectByExample(example);
    }

    public static void main(String[] args) {
        int a = 10;
        int b = 100;
        float ret1 = 0;
        float ret2 = 0;
        int len = 100;
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            int c = random.nextInt(a + b);
            System.out.println("结果" + c);
            if (c <= 10) {
                ret1++;
            }
            if (c > 10 && c <= (a + b)) {
                ret2++;
            }
        }
        System.out.println("区间1===" + ret1 + "总概率===" + (float) (ret1 / len));
        System.out.println("区间2===" + ret2 + "总概率===" + (float) (ret2 / len));

        float test = 1 / 3;
        System.out.println(test);
    }

    // 获取主要支付渠道(充值渠道)
    @Override
    public PayTunnel getMajorPayTunnel() {
        PayTunnelExample example = new PayTunnelExample();
        example.setLimitStart(0);
        example.setLimitEnd(1);
        example.createCriteria().andTypeEqualTo(PAY_TUNNEL_TYPE_0).andStatusEqualTo(PAY_TUNNEL_STATUS_0);
        example.setOrderByClause("weight desc");
        List<PayTunnel> payTunnels = payTunnelMapper.selectByExample(example);
        return payTunnels != null && !payTunnels.isEmpty() ? payTunnels.get(0) : null;
    }
}
