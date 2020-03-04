package com.goochou.p2b.service.impl;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.hessian.transaction.PayChannelResponse;
import com.goochou.p2b.model.PayTunnel;
import com.goochou.p2b.service.PayTunnelService;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.StringUtils;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.AdminLogMapper;
import com.goochou.p2b.dao.BankCardMapper;
import com.goochou.p2b.model.Bank;
import com.goochou.p2b.model.BankCard;
import com.goochou.p2b.model.BankCardExample;
import com.goochou.p2b.service.BankCardService;
import com.goochou.p2b.service.UploadService;
import com.goochou.p2b.utils.CommonUtils;

import static com.goochou.p2b.constant.pay.ChannelConstants.*;

@Service
public class BankCardServiceImpl implements BankCardService {
    @Resource
    private BankCardMapper bankCardMapper;
    @Resource
    private AdminLogMapper adminLogMapper;
    @Resource
    private UploadService uploadService;
    @Resource
    private PayTunnelService payTunnelService;


    @Override
    public int saveNewBankCard(BankCard bankCard) {
        //保存新卡
        bankCardMapper.insertSelective(bankCard);
        return 0;
    }

    @Override
    public int updateCardStatusByUserId(Integer userId, Integer bankCardId) {
        return bankCardMapper.updateCardByUserId(userId, bankCardId);
    }

    @Override
    public BankCard get(Integer id) {
        return bankCardMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer getBankCardCountByUserId(Integer userId) {
        BankCardExample example = new BankCardExample();
        example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(0);
        return bankCardMapper.countByExample(example);
    }

    @Override
    public List<BankCard> getByUserId(Integer userId) {
//        BankCardExample example = new BankCardExample();
//        example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(0);
        List<BankCard> list = bankCardMapper.selectByUserId(userId);
        if (list != null && !list.isEmpty()) {
            return list;
        }
        return null;
    }

    @Override
    public BankCard getByUserIdMask(Integer userId) {
        BankCardExample example = new BankCardExample();
        example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(0);
        List<BankCard> list = bankCardMapper.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            BankCard bankCard = list.get(0);
            bankCard.setCardNumber(CommonUtils.getCardNoSixFour(bankCard.getCardNumber()));
            return bankCard;
        }
        return null;
    }

    @Override
    public Bank getBankByLlBankCode(String bankCode) {
        return bankCardMapper.getBankByLlBankCode(bankCode);
    }

    @Override
    public List<BankCard> list() {
        return bankCardMapper.selectByExample(new BankCardExample());
    }


    @Override
    public void update(BankCard bankCard) {
        bankCardMapper.updateByPrimaryKeySelective(bankCard);
    }

    @Override
    public List<BankCard> getBankNoByUserId(Integer userId) {
        return bankCardMapper.selectByUserId(userId);
    }

    @Override
    public List<BankCard> getByCardnumber(String cardnumber) {
        BankCardExample example = new BankCardExample();
        example.createCriteria().andCardNumberEqualTo(cardnumber).andStatusEqualTo(0); //0可用并且卡号存在的不能重复绑定
        return bankCardMapper.selectByExample(example);
    }

    @Override
    public List<BankCard> getCardByUserId(Integer userId, Integer status) {
        BankCardExample example = new BankCardExample();
        if (status != null) {
            example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(status);
        } else {
            example.createCriteria().andUserIdEqualTo(userId);
        }
        return bankCardMapper.selectByExample(example);
    }

    @Override
    public int queryUnBindCountByDate(Integer userId, Date date) {
        BankCardExample example = new BankCardExample();
        BankCardExample.Criteria c = example.createCriteria();
        c.andUserIdEqualTo(userId);
        c.andUpdateDateEqualTo(date);
        c.andStatusEqualTo(1);
        return bankCardMapper.countByExample(example);
    }

    // 获取用户可用支付的银行卡
    @Override
    public List<Map<String, String>> getUserBankCard(Integer userId) {
        List<Map<String, String>> result = new ArrayList<>();
        List<Map> userBankCard = bankCardMapper.queryByUserId(userId);
        List<Map> qians = new ArrayList<>();
        if (userBankCard != null && !userBankCard.isEmpty()) {
            for (Map map: userBankCard) {
                Map<String, String> temp = new HashMap<>();
                String id = String.valueOf(map.get("id"));
                String bankId = String.valueOf(map.get("bank_id"));
                String cardNumber = String.valueOf(map.get("card_number"));
                String bankName = String.valueOf(map.get("bank_name"));
                
                String month_max_amount = String.valueOf(map.get("month_max_amount"));
                String daily_max_amount = String.valueOf(map.get("daily_max_amount"));
                String single_max_amount = String.valueOf(map.get("single_max_amount"));
                String min_amount = String.valueOf(map.get("min_amount"));
                String today_use_amount = String.valueOf(map.get("today_use_amount"));
                String bankPayChannel = String.valueOf(map.get("bankPayChannel"));
                String bindPayChannel = String.valueOf(map.get("bindPayChannel"));
                String phone = String.valueOf(map.get("phone"));
                
                String name = this.getBankNameAndLastFourBankCards(cardNumber, bankName);
                String url = this.getBankIconUrl(bankId);

                temp.put(CHANNEL_KEY, id);
                temp.put(CHANNEL_NAME, name);
                temp.put(CHANNEL_URL, url);
                temp.put(CHANNEL_CHOOSE, "0");
                if(bankPayChannel.equals(bindPayChannel)) {
                    temp.put(CHANNEL_ALLOW_CHOOSE, "1");
                    qians.add(temp);
                }else {
                    temp.put(CHANNEL_ALLOW_CHOOSE, "0");
                    temp.put(CHANNEL_JUMP_TEXT, "签约申请");
                    temp.put(CHANNEL_JUMP_KEY, "addCard#cardNo="+cardNumber+"&phone="+phone+"&bindCardId="+id);
                    temp.put(CHANNEL_JUMP_URL, "");
                    temp.put(CHANNEL_JUMP_TEXT_COLOR, "#576b95");
                }
                temp.put("month_max_amount", month_max_amount);
                temp.put("daily_max_amount", daily_max_amount);
                temp.put("single_max_amount", single_max_amount);
                temp.put("min_amount", min_amount);
                temp.put("today_use_amount", today_use_amount);
                temp.put("left_amount", new BigDecimal(daily_max_amount).subtract(new BigDecimal(today_use_amount))
                		.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
                result.add(temp);
            }
        }
        List<Map<String, String>> tempResult = result;
        //过滤相同银行卡保留签约成功的
        for(int i=0;i<qians.size();i++) {
            for(int j =0;j<tempResult.size();j++) {
                if(tempResult.get(j).get("name").equals(qians.get(i).get("name")) && tempResult.get(j).get("allow").equals("0")) {
                    result.remove(j);
                    continue;
                }
            }
        }
        //添加收银台
        Map<String, String> last = new HashMap<>();
        if (userBankCard == null || userBankCard.size() < 5) {
            last = new HashMap<>();
            last.put(CHANNEL_URL, ClientConstants.ALIBABA_PATH + "images/add.png");
            last.put(CHANNEL_KEY, "key");
            last.put(CHANNEL_NAME, "添加银行卡");
            result.add(last);
        }

        return result;
    }


    @Override
    public PayChannelResponse getUserPayChannel (Integer userId) {
        PayChannelResponse payChannelResponse = new PayChannelResponse();
//        PayTunnel payTunnel = payTunnelService.getMajorPayTunnel(); // 主要支付渠道
//        if (payTunnel != null) {
//            if(payTunnel.getName().equals("fuiou")) {
//                payChannelResponse.setPayChannel(OutPayEnum.FUIOU_QUICK.getFeatureName());
//            }
//        }
        List<Map<String, String>> channel = this.getUserBankCard(userId);
        payChannelResponse.setChannel(channel);
        return payChannelResponse;
    }

    // 获取银行卡名称，和卡号后四位
    private String getBankNameAndLastFourBankCards(String sourceCard, String sourceName) {
        if (StringUtils.isBlank(sourceCard) || StringUtils.isBlank(sourceName)) {
            return "";
        }
        String result = "";
        if (sourceCard.length() >= 4) {
            result = sourceName + "(*" + sourceCard.substring(sourceCard.length() - 4) + ")";
        }
        return result;
    }

    // 银行卡图标地址
    private String getBankIconUrl(String bankId) {
        if (StringUtils.isBlank(bankId)) return "";
        return ClientConstants.ALIBABA_PATH + "images/bank-icon/bank" + bankId + "@2x.png";
    }
    
    @Override
    public List<Map<String, Object>> listUserBank(Integer limitStart, Integer limitEnd, Integer userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("limitStart", limitStart);
        map.put("limitEnd", limitEnd);
        map.put("userId",userId);
        
        return bankCardMapper.listUserBank(map);
    }
    
    
    /**
     * @date 2019年8月28日
     * @author wangyun
     * @time 下午2:47:20
     * @Description 查询用户是否解绑该银行卡
     * 
     * @param cardNo
     * @param userId
     * @return
     */
    @Override
    public List<BankCard> queryUnBindByCardNo(String cardNo, Integer userId) {
        BankCardExample example = new BankCardExample();
        BankCardExample.Criteria c = example.createCriteria();
        c.andUserIdEqualTo(userId);
        c.andCardNumberEqualTo(cardNo);
        c.andStatusEqualTo(1);
        return bankCardMapper.selectByExample(example);
    }
}
