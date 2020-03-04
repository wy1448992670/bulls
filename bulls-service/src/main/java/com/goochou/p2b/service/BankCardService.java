package com.goochou.p2b.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.goochou.p2b.hessian.transaction.PayChannelResponse;
import org.springframework.web.multipart.MultipartFile;

import com.goochou.p2b.model.Bank;
import com.goochou.p2b.model.BankCard;
import com.goochou.p2b.model.User;

public interface BankCardService {
    public void update(BankCard bankCard);

    /**
     * 保存新的银行卡，如果存在老的银行卡，把status改为1
     */
    public int saveNewBankCard(BankCard bankCard);

    public int updateCardStatusByUserId(Integer userId, Integer bankCardId);

    public BankCard get(Integer id);

    public Integer getBankCardCountByUserId(Integer userId);

    public List<BankCard> getByUserId(Integer userId);

    public BankCard getByUserIdMask(Integer userId);//前六后四卡号的方法

    public Bank getBankByLlBankCode(String bankCode);

    public List<BankCard> list();

    /**
     * 根据用户ID获取用户银行卡信息
     *测试
     * @return
     * @author 刘源
     * @date 2016年2月17日
     * @parameter
     */
    public List<BankCard> getBankNoByUserId(Integer userId);

    public List<BankCard> getByCardnumber(String cardnumber);

    List<BankCard> getCardByUserId(Integer userId, Integer status);

    int queryUnBindCountByDate(Integer userId, Date date);

    /**
     * @description 获取用户可用支付的银行卡
     * @author shuys
     * @date 2019/5/22
     * @param userId
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    public List<Map<String, String>> getUserBankCard(Integer userId);


    /**
     * @description 用户支付方式
     * @author shuys
     * @date 2019/5/23
     * @param userId
     * @return com.goochou.p2b.hessian.transaction.PayChannelResponse
    */
    public PayChannelResponse getUserPayChannel (Integer userId);
    
    /**
     * 根据userId获取银行卡信息
     * @author sxy
     * @param userId
     * @return
     */
    public List<Map<String,Object>> listUserBank(Integer limitStart, Integer limitEnd, Integer userId);
    
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
    public List<BankCard> queryUnBindByCardNo(String cardNo, Integer userId);
    
}
