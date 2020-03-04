package com.goochou.p2b.service;

import com.goochou.p2b.dao.MallActivityMapper;
import com.goochou.p2b.dao.MallActivitySecondKillMapper;
import com.goochou.p2b.model.MallActivity;
import com.goochou.p2b.model.MallActivitySecondKill;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.model.vo.bulls.MallActivitySecondKillVO;
import com.goochou.p2b.model.vo.bulls.MallActivityVO;
import com.goochou.p2b.model.vo.bulls.SecondKillDetailVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 商城活动 service
 * </p>
 *
 * @author shuys
 * @since 2019年12月12日 15:39:00
 */
public interface MallActivityService {

    MallActivityMapper getMapper();

    MallActivitySecondKillMapper getMallActivitySecondKillMapper();

    List<MallActivityVO> listMallActivity(String name, Date startDateStart, Date startDateEnd, Date stopDateStart, Date stopDateEnd, int limitStart, int limitEnd);

    long countMallActivity(String name, Date startDateStart, Date startDateEnd, Date stopDateStart, Date stopDateEnd);

    MallActivityVO detailMallActivity(Integer id);

    int saveMallActivity(MallActivity mallActivity, UserAdmin admin) throws Exception;

    int updateMallActivityEableStatus(Integer id, Integer eableStatus) throws Exception;
    
    List<MallActivitySecondKillVO> listMallActivitySecondKillByActivityId(Integer mallActivityId) throws Exception;

    /**
     * 功能描述 <br/>
     * <>
     * @author shuys
     * @date 2019/12/18
     * @param mallActivitySecondKill 秒杀商品信息
     * @param file 活动广告图片
     * @return int
    */
    int saveMallActivitySecondKill(MallActivitySecondKill mallActivitySecondKill, MultipartFile file, UserAdmin admin) throws Exception;

    /**
     * 逻辑删除参与秒杀活动商品（标记为不可用） <br/>
     * <>
     * @author shuys
     * @date 2019/12/18
     * @param secondKillId
     * @return int
    */
    int deleteMallActivitySecondKill(Integer secondKillId) throws Exception;

    /**
     * 计算活动在开始时间和结束时间内循环次数(两个日期之间有几个周n，包含两个日期) <br/>
     * <>
     * @author shuys
     * @date 2019/12/17
     * @param startDate 活动开始时间
     * @param stopDate 活动结束时间
     * @param weekDay 周n（活动日期）
     * @return int
     */
    int calculateCycleTimes(Date startDate, Date stopDate, int weekDay) throws Exception;

    MallActivitySecondKillVO getMallActivitySecondKillById(Integer id) throws Exception;


    /**
     * 获取当天的秒杀商品配置数据
     * @param activityDetailId 活动详情编号 为null 则查询当天所有的秒杀商品
     * @return
     */
    List<SecondKillDetailVO> getTheSameDaySecondKillDetails(Integer activityDetailId);

    /**
     * 获取活动已售出的数量
     * @param activityDetailId
     * @return
     */
    int getSaledCountForActivity(Integer activityDetailId);

    /**
     * 获取活动已下单成功的数量
     * @param activityDetailId
     * @return
     */
    int getBuyedCountForUser(Integer activityDetailId,Integer userId);

    /**
     * 活动结束后一天，将活动期间内占用的库存解锁
     * @param mallActivityId
     * @return
     */
    void doUnlockStockWhenActivityEnd(Integer mallActivityId) throws Exception;
    
}
