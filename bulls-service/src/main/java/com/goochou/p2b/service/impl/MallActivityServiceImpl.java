package com.goochou.p2b.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.domain.GoodsDetail;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.constant.goods.GoodsOrderStatusEnum;
import com.goochou.p2b.constant.redis.RedisConstants;
import com.goochou.p2b.dao.GoodsMapper;
import com.goochou.p2b.dao.GoodsPictureMapper;
import com.goochou.p2b.dao.MallActivityMapper;
import com.goochou.p2b.dao.MallActivitySecondKillMapper;
import com.goochou.p2b.model.*;
import com.goochou.p2b.model.goods.Goods;
import com.goochou.p2b.model.goods.GoodsOrder;
import com.goochou.p2b.model.goods.GoodsOrderDetail;
import com.goochou.p2b.model.goods.GoodsOrderDetailExample;
import com.goochou.p2b.model.vo.bulls.MallActivitySecondKillVO;
import com.goochou.p2b.model.vo.bulls.MallActivityVO;
import com.goochou.p2b.model.vo.bulls.SecondKillDetailVO;
import com.goochou.p2b.service.*;
import com.goochou.p2b.utils.DateUtil;
import com.goochou.p2b.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 商城活动 service
 * </p>
 *
 * @author shuys
 * @since 2019年12月12日 15:41:00
 */
@Service
public class MallActivityServiceImpl implements MallActivityService {
    private final static Logger logger = Logger.getLogger(MallActivityServiceImpl.class);
    
    @Resource
    private MallActivityMapper mallActivityMapper;
    
    @Resource
    private MallActivitySecondKillMapper mallActivitySecondKillMapper;
    
    @Resource
    private GoodsService goodsService;

    @Resource
    private GoodsPictureMapper goodsPictureMapper;
    
    @Resource
    private UploadService uploadService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private GoodsOrderService goodsOrderService;
    @Autowired
    private GoodsOrderDetailService goodsOrderDetailService;

    @Override
    public MallActivityMapper getMapper() {
        return mallActivityMapper;
    }

    @Override
    public MallActivitySecondKillMapper getMallActivitySecondKillMapper() {
        return mallActivitySecondKillMapper;
    }

    @Override
    public List<MallActivityVO> listMallActivity(String name, Date startDateStart, Date startDateEnd, Date stopDateStart, Date stopDateEnd, int limitStart, int limitEnd) {
        MallActivityExample example = new MallActivityExample();
        example.setOrderByClause(" id DESC ");
        MallActivityExample.Criteria criteria = example.createCriteria();
        if (limitStart >= 0 && limitEnd > 0) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%"+name+"%");
        }
        if (startDateStart != null) {
            criteria.andStartDateGreaterThanOrEqualTo(DateUtil.getDayMinTime(startDateStart));
        }
        if (startDateEnd != null) {
            criteria.andStartDateLessThan(DateUtil.getNextDayMinTime(startDateStart));
        }
        if (stopDateStart != null) {
            criteria.andStopDateGreaterThanOrEqualTo(DateUtil.getDayMinTime(stopDateStart));
        }
        if (stopDateEnd != null) {
            criteria.andStopDateLessThan(DateUtil.getNextDayMinTime(stopDateEnd));
        }
        return mallActivityMapper.listMallActivity(example);
    }

    @Override
    public long countMallActivity(String name, Date startDateStart, Date startDateEnd, Date stopDateStart, Date stopDateEnd) {
        MallActivityExample example = new MallActivityExample();
        MallActivityExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%"+name+"%");
        }
        if (startDateStart != null) {
            criteria.andStartDateGreaterThanOrEqualTo(DateUtil.getDayMinTime(startDateStart));
        }
        if (startDateEnd != null) {
            criteria.andStartDateLessThan(DateUtil.getNextDayMinTime(startDateStart));
        }
        if (stopDateStart != null) {
            criteria.andStopDateGreaterThanOrEqualTo(DateUtil.getDayMinTime(stopDateStart));
        }
        if (stopDateEnd != null) {
            criteria.andStopDateLessThan(DateUtil.getNextDayMinTime(stopDateEnd));
        }
        return mallActivityMapper.countMallActivity(example);
    }

    @Override
    public MallActivityVO detailMallActivity(Integer id) {
        MallActivityExample example = new MallActivityExample();
        example.createCriteria().andIdEqualTo(id);
        List<MallActivityVO> list = mallActivityMapper.listMallActivity(example);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public int saveMallActivity(MallActivity mallActivity, UserAdmin admin) throws Exception {
        mallActivity.setAdminId(admin.getId());
        if (mallActivity.getId() == null) {
            return mallActivityMapper.insertSelective(mallActivity);
        } else {
            MallActivity ma = mallActivityMapper.selectByPrimaryKeyForUpdate(mallActivity.getId());
            mallActivity.setType(ma.getType());
            return mallActivityMapper.updateByPrimaryKeySelective(mallActivity);
        }
    }

    @Override
    public int updateMallActivityEableStatus(Integer id, Integer eableStatus) throws Exception {
        MallActivity mallActivity = mallActivityMapper.selectByPrimaryKeyForUpdate(id);
        boolean flag = false;
        // 启用 -> 锁定库存，禁用 -> 解锁库存
        boolean isEable = true;
        // 启用
        if (eableStatus == 1 && mallActivity.getEnable() == 0) {
            flag = true;
        }
        // 禁用
        if (eableStatus == 0 && mallActivity.getEnable() == 1) {
            isEable = false;
            flag = true;
        }
        if (flag) {
//            List<MallActivitySecondKillVO> mallActivitySecondKillVOS = this.listMallActivitySecondKillByActivityId(mallActivity.getId());
//            for (MallActivitySecondKillVO vo : mallActivitySecondKillVOS) {
//                Goods goods = goodsService.getMapper().selectByPrimaryKey(vo.getGoodId());
//                if (goods == null) {
//                    throw new Exception("商品不存在");
//                }
//                int lockStock = this.getActivityLockStock(vo.getStockCount(), vo.getWeekDay(), mallActivity.getStartDate(), mallActivity.getStopDate());
//                int optStock = lockStock - vo.getSaledCount();
//                // 锁定/解锁库存
//                this.updateActivityStock(goods, optStock, isEable);
//            }

            mallActivity.setEnable(eableStatus);
            return mallActivityMapper.updateByPrimaryKeySelective(mallActivity);
        } else {
            throw new Exception("数据错误");
        }
    }
    private List<MallActivitySecondKillVO> listMallActivitySecondKill(Integer id, Integer mallActivityId, String goodsName,
                                                                                  Integer isOnShelves, Integer limitStart, Integer limitEnd) throws Exception {
        String pathPrefix = ClientConstants.ALIBABA_PATH + "upload/";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("mallActivityId", mallActivityId);
        params.put("goodsName", goodsName);
        params.put("isOnShelves", isOnShelves);
        params.put("limitStart", limitStart);
        params.put("limitEnd", limitEnd);
        List<MallActivitySecondKillVO> result = mallActivitySecondKillMapper.listMallActivitySecondKill(params);
        for (MallActivitySecondKillVO vo : result) {
            if (vo.getLittleImage() != null) {
                vo.setLittleImage(pathPrefix + vo.getLittleImage());
            }
            if (vo.getActivityImage() != null) {
                vo.setActivityImage(pathPrefix + vo.getActivityImage());
            }
            // 计算活动时间内周数
            int weekCount = this.calculateCycleTimes(vo.getStartDate(), vo.getStopDate(), vo.getWeekDay());
            vo.setLockStock(weekCount * vo.getStockCount());
            vo.setWeekCount(weekCount);
            
        }
        return result;
    }

    @Override
    public List<MallActivitySecondKillVO> listMallActivitySecondKillByActivityId(Integer mallActivityId) throws Exception {
        return this.listMallActivitySecondKill(null, mallActivityId, null, null, null, null);
    }

    public int countMallActivitySecondKillByActivityId(Integer mallActivityId) {
        Map<String, Object> params = new HashMap<>();
        params.put("mallActivityId", mallActivityId);
        params.put("goodsName", null);
        params.put("isOnShelves", null);
        params.put("limitStart", null);
        params.put("limitEnd", null);
        return mallActivitySecondKillMapper.countMallActivitySecondKill(params);
    }

    @Override
    public int saveMallActivitySecondKill(MallActivitySecondKill mallActivitySecondKill, MultipartFile file, UserAdmin admin) throws Exception {
        MallActivity mallActivity = mallActivityMapper.selectByPrimaryKeyForUpdate(mallActivitySecondKill.getActivityId());
        if (mallActivity == null) {
            throw new Exception("活动不存在");
        }
        Date todayStart = DateUtil.getDayStartDate(new Date());
        // 判断活动是否在今天之前已经结束
        if (todayStart.after(mallActivity.getStopDate())) {
            throw new Exception("操作失败，该活动在今天之前已经结束");
        }
        Goods goods = goodsService.getMapper().selectByPrimaryKey(mallActivitySecondKill.getGoodId());
        if (goods == null) {
            throw new Exception("商品不存在");
        }
        // 根据活动开始日期、结束日期和活动所在星期n计算占用库存
        int lockStock = mallActivitySecondKill.getStockCount() * this.calculateCycleTimes(mallActivity.getStartDate(), mallActivity.getStopDate(), mallActivitySecondKill.getWeekDay());
        if (mallActivitySecondKill.getId() == null) {
            // 上架
            if (goods.getStock() <= lockStock) {
                throw new Exception("商品库存不足活动使用");
            }
            // 保存活动图片
            this.saveActivityImage(file, admin, goods.getId());   
            if (this.updateActivityStock(goods, lockStock, 0, true)) {
                mallActivitySecondKill.setSaledCount(0);
                mallActivitySecondKill.setEnable(1);
                mallActivitySecondKillMapper.insertSelective(mallActivitySecondKill);
                return 1;
            } else {
                logger.error("添加秒杀活动商品失败");
            }
        } else {
            MallActivitySecondKill mask = mallActivitySecondKillMapper.selectByPrimaryKeyForUpdate(mallActivitySecondKill.getId());
            if (mask == null) {
                throw new Exception("该活动秒杀商品不存在");
            }
            if (mask.getEnable() == 0) {
                throw new Exception("该活动商品已被删除");
            }

            // 如果是下架操作
            if (mask.getIsOnShelves().intValue() == 1 && mallActivitySecondKill.getIsOnShelves() == 0) {
                mallActivitySecondKill.setIsOnShelves(0);
                // 更新原来的活动商品信息
                mallActivitySecondKillMapper.updateByPrimaryKeySelective(mallActivitySecondKill);
                return 1;
            } else {

                // 保存活动图片
                this.saveActivityImage(file, admin, goods.getId());
                // 商品锁定库存 
                int oldLockStock = mask.getStockCount() * this.calculateCycleTimes(mallActivity.getStartDate(), mallActivity.getStopDate(), mallActivitySecondKill.getWeekDay());
                // 可操作性库存 （商品锁定库存 - 已售库存）
                // int optStock = oldLockStock - mask.getSaledCount();
                // 判断活动是否在今天还未开始
                if (todayStart.before(mallActivity.getStartDate())) {
                    // 未开始：直接修改原来的活动商品信息
                    boolean lockStockSuccess = true;
                    // 如果修改了活动库存
                    if (mask.getStockCount().intValue() != mallActivitySecondKill.getStockCount().intValue()) {
                        // 解锁库存（将原来配置的活动库存还原）
                        if (!this.updateActivityStock(goods, oldLockStock, oldLockStock, false)) {
                            lockStockSuccess = false;
                        }
                        // 锁定库存
                        if (!this.updateActivityStock(goods, lockStock, 0, true)) {
                            lockStockSuccess = false;
                        }
                    }
                    if (lockStockSuccess) {
                        mallActivitySecondKill.setSaledCount(0);
                        mallActivitySecondKill.setEnable(1);
                        // 更新原来的活动商品信息
                        mallActivitySecondKillMapper.updateByPrimaryKeySelective(mallActivitySecondKill);
                        return 1;
                    }
                } else {
                    // 这里不需要解锁库存，等待活动结束后统一处理
//                // 解锁库存
//                if (this.updateActivityStock(goods, optStock, false)) {
//                }
                    // （已开始：将原来的活动商品伪删除）
                    mask.setEnable(0);
                    mallActivitySecondKillMapper.updateByPrimaryKeySelective(mask);
                    mallActivitySecondKill.setId(null);
                    // 生成一条新的活动商品
                    this.saveMallActivitySecondKill(mallActivitySecondKill, file, admin);
                    return 1;
                }
            }
        }
        return 0;
    }
    
    private void saveActivityImage(MultipartFile file, UserAdmin admin, Integer goodsId) throws Exception {
        if (file == null || file.isEmpty()) {
            return;
        }
        Map<String, Object> result = uploadService.save(file, 22, admin.getId());
        Integer uploadId = Integer.valueOf(result.get("id").toString());
        if (uploadId == null) {
            throw new Exception("上传活动广告图片出错");
        }

        GoodsPictureExample example = new GoodsPictureExample();
        example.setOrderByClause(" id desc ");
        example.createCriteria().andGoodsIdEqualTo(goodsId).andStatusEqualTo(0).andTypeEqualTo(16);
        List<GoodsPicture> goodsPictures = goodsPictureMapper.selectByExample(example);
        if (goodsPictures.isEmpty()) {
            GoodsPicture goodsPicture = new GoodsPicture();
            goodsPicture.setGoodsId(goodsId);
            goodsPicture.setType(16); // 活动广告图片
            goodsPicture.setUploadId(uploadId);
            goodsPicture.setCreateDate(new Date());
            goodsPicture.setStatus(0);
            goodsPictureMapper.insertSelective(goodsPicture);
        } else {
            GoodsPicture goodsPicture = goodsPictures.get(0);
            goodsPicture.setStatus(0);
            goodsPicture.setUploadId(uploadId);
            goodsPictureMapper.updateByPrimaryKeySelective(goodsPicture);
        }
        
    }

    @Override
    public int deleteMallActivitySecondKill(Integer secondKillId) throws Exception {
        MallActivitySecondKill mallActivitySecondKill = mallActivitySecondKillMapper.selectByPrimaryKeyForUpdate(secondKillId);
        if (mallActivitySecondKill == null) {
            throw new Exception("秒杀商品不存在");
        }
        MallActivity mallActivity = mallActivityMapper.selectByPrimaryKeyForUpdate(mallActivitySecondKill.getActivityId());
        if (mallActivity == null) {
            throw new Exception("该活动不存在");
        }
        Date todayStart = DateUtil.getDayStartDate(new Date());
        // 判断活动是否已经开始并且未结束
        if (!todayStart.before(mallActivity.getStartDate()) && !todayStart.after(mallActivity.getStopDate())) {
            throw new Exception("活动已经开始并且未结束，不能删除商品");
        }
        Goods goods = goodsService.getMapper().selectByPrimaryKey(mallActivitySecondKill.getGoodId());
        if (goods == null) {
            throw new Exception("商品不存在");
        }
        if (mallActivitySecondKill.getIsOnShelves() == 1) {
            throw new Exception("不能删除上架商品");
        }
        if (mallActivitySecondKill.getEnable() == 0) {
            throw new Exception("该活动商品已被删除");
        }
//        int lockStock = this.getActivityLockStock(mallActivitySecondKill.getStockCount(), mallActivitySecondKill.getWeekDay(), mallActivity.getStartDate(), mallActivity.getStopDate());
//        int optStock = lockStock - mallActivitySecondKill.getSaledCount();
//        // 解锁库存
//        if (this.updateActivityStock(goods, optStock, false)) {
//        }
        // 标记活动商品为 禁用
        mallActivitySecondKill.setEnable(0);
        mallActivitySecondKillMapper.updateByPrimaryKeySelective(mallActivitySecondKill);
        return 1;
    }
    
    private int getActivityLockStock(int stockCount, int weekCount, Date startDate, Date stopDate) throws Exception {
        return stockCount * this.calculateCycleTimes(startDate, stopDate, weekCount);
    }
    
    /**
     * 锁定/解锁库存 <br/>
     * <>
     * @date 2019/12/19
     * @param goods 商品
     * @param lockStock  锁定库存 
     * @param sellCount  出售数量
     * @param lock 是否是锁定 true 锁定，false解锁
     * @return void
    */
    private boolean updateActivityStock(Goods goods, int lockStock, int sellCount, boolean lock) throws Exception {
        if (lock) {
            if (goods.getStock() <= lockStock) {
                throw new Exception("商品库存不足活动使用");
            }
            // 锁定库存
            goods.setStock(goods.getStock() - lockStock);
            if (goods.getActvityStock() == null) {
                goods.setActvityStock(lockStock);
            } else {
                goods.setActvityStock(goods.getActvityStock() + lockStock);
            }
        } else {
            // 解锁库存
            goods.setStock(goods.getStock() + lockStock - sellCount);
            goods.setActvityStock(goods.getActvityStock() - lockStock);
        }
        if (goodsService.updateForVersion(goods) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public int calculateCycleTimes(Date startDate, Date endDate, int weekDay) throws Exception {
        if (startDate == null || endDate == null) {
            throw new Exception("日期不能为空");
        }
        if (endDate.before(startDate)) {
            throw new Exception("开始日期不能大于结束日期");
        }
        if (weekDay < 1 || weekDay > 7) {
            throw new Exception("错误的星期日期");
        }
        int differenceDay = 0;
        // 实例化起始和结束Calendar对象
        Calendar startCalendar = new GregorianCalendar();
        Calendar endCalendar = new GregorianCalendar();
        // 分别设置Calendar对象的时间
        startCalendar.setTime(startDate);
        endCalendar.setTime(endDate);

        // 获取当前开始日期所在年内最大周数
        Calendar temp = new GregorianCalendar();
        temp.setFirstDayOfWeek(Calendar.MONDAY);
        temp.setMinimalDaysInFirstWeek(7);
        temp.setTime(startDate);
        temp.set(startCalendar.get(Calendar.YEAR), Calendar.DECEMBER, 31, 23, 59, 59);
        int endWeekOfYear = temp.get(Calendar.WEEK_OF_YEAR);

        // 1=周日，2=周一.... 7=周六，周日为一周开始日
        // 定义起始日期和结束日期分别属于第几周
        int startWeek = startCalendar.get(Calendar.WEEK_OF_YEAR);
        int endWeek = endCalendar.get(Calendar.WEEK_OF_YEAR);
        // 如果开始时间所在周大于结束时间所在周，则为跨年
        if (startWeek > endWeek) {
            // TODO 当前情况只考虑跨一年
            endWeek = endWeek + endWeekOfYear;
        }

        // 拿到起始日期是星期几
        int startDayOfWeek = startCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        if(startDayOfWeek == 0)    {
            startDayOfWeek = 7;
            startWeek--;
        }

        // 拿到结束日期是星期几
        int endDayOfWeek = endCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        if(endDayOfWeek == 0) {
            endDayOfWeek = 7;
            endWeek--;
        }

        // 计算相差的周数
        int differenceWeek = endWeek - startWeek;

        // 开始计算
        if(startDayOfWeek <= weekDay && endDayOfWeek >= weekDay) {
            // 起始日期当前周数 <= 设定周数 && 结束日期当前周数 >= 设定周数
            differenceDay = differenceWeek + 1;
        } else if (startDayOfWeek > weekDay && endDayOfWeek < weekDay) {
            // 起始日期当前周数 > 设定周数 && 结束日期当前周数 < 设定周数
            differenceDay = differenceWeek - 1;
        } else {
            // 起始日期当前周数 <= 设定周数 && 结束日期当前周数 < 设定周数
            // 起始日期当前周数 > 设定周数 && 结束日期当前周数 >= 设定周数
            differenceDay = differenceWeek;
        }
        return differenceDay;
    }

    @Override
    public MallActivitySecondKillVO getMallActivitySecondKillById(Integer id) throws Exception {
        List<MallActivitySecondKillVO> list = this.listMallActivitySecondKill(id ,null,null,null,null,null);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }


    /**
     * 获取当天的秒杀商品配置数据
     * @param activityDetailId 活动详情编号 为null 则查询当天所有的秒杀商品
     * @return
     */
    @Override
    public List<SecondKillDetailVO> getTheSameDaySecondKillDetails(Integer activityDetailId) {
        List<SecondKillDetailVO> list = null;
        Object cache = redisService.get(String.format(RedisConstants.SECONDKILL_PRODUCT_DETAIL, activityDetailId));
        if(cache!=null){
            SecondKillDetailVO secondKillDetailVO = JSON.parseObject((String)cache, SecondKillDetailVO.class);

            if(secondKillDetailVO!=null) {
                list = new ArrayList<>();
                list.add(secondKillDetailVO);
            }
        }else{
            list = mallActivitySecondKillMapper.getTheSameDaySecondKillDetails(new Date(),activityDetailId);
            if(list!=null && list.size()>0){
                redisService.set(String.format(RedisConstants.SECONDKILL_PRODUCT_DETAIL, activityDetailId),
                        JSON.toJSONString(list.get(0)),60*60*24);
            }
        }
        return list;
    }

    @Override
    public int getSaledCountForActivity(Integer activityDetailId) {
        Object cache = redisService.get(String.format(RedisConstants.SECONDKILL_ACTIVITY_SALED_COUNT,activityDetailId));
        if(cache!=null) {
            return Integer.parseInt(cache.toString());
        }

        List<GoodsOrder> orders = goodsOrderService.selectSecondKillOrder(activityDetailId,null);
        if(orders!=null){
            redisService.set(String.format(RedisConstants.SECONDKILL_ACTIVITY_SALED_COUNT,activityDetailId),orders.size()+"",60 * 60 * 24);
            return orders.size();
        }else{
            redisService.set(String.format(RedisConstants.SECONDKILL_ACTIVITY_SALED_COUNT,activityDetailId),"0",60 * 60 * 24);
            return 0;
        }
    }

    @Override
    public int getBuyedCountForUser(Integer activityDetailId, Integer userId) {
        Object cache = redisService.get(String.format(RedisConstants.SECONDKILL_ACTIVITY_PERSON_BUYED_COUNT,activityDetailId,userId));
        if(cache!=null) {
            return Integer.parseInt(cache.toString());
        }
        //查询参与此活动订单列表，再看订单里有没有未支付的
        List<GoodsOrder> orders = goodsOrderService.selectSecondKillOrder(activityDetailId,userId);
        if(orders!=null) {
            for (GoodsOrder goodsOrder : orders) {
                if (goodsOrder.getState() == GoodsOrderStatusEnum.NO_PAY.getCode()) {
                    redisService.set(String.format(RedisConstants.SECONDKILL_ACTIVITY_PERSON_UNPAY, activityDetailId, userId), "1", 60 * 60 * 24);
                    break;
                }
            }

            redisService.set(String.format(RedisConstants.SECONDKILL_ACTIVITY_PERSON_BUYED_COUNT, activityDetailId, userId), orders.size()+"", 60 * 60 * 24);

            return orders.size();
        }else{

            redisService.set(String.format(RedisConstants.SECONDKILL_ACTIVITY_PERSON_BUYED_COUNT, activityDetailId, userId), "0", 60 * 60 * 24);
            return 0;
        }
    }

    /**
     * 活动结束后一天，将活动期间内占用的库存解锁
    */
    public void doUnlockStockWhenActivityEnd(Integer mallActivityId) throws Exception {
        MallActivity mallActivity = mallActivityMapper.selectByPrimaryKey(mallActivityId);
        if (mallActivity == null) {
            throw new Exception("活动不存在");
        }
        MallActivitySecondKillExample example = new MallActivitySecondKillExample();
        example.createCriteria()
                .andActivityIdEqualTo(mallActivity.getId())
                .andEnableEqualTo(1).
                andStockCountGreaterThan(0)
                .andSaledCountIsNull()
        ;
        List<MallActivitySecondKill> list = mallActivitySecondKillMapper.selectByExample(example);
        if (!list.isEmpty()) {
            list.forEach(item -> {
                try {
                    MallActivitySecondKill mask = mallActivitySecondKillMapper.selectByPrimaryKeyForUpdate(item.getId());
                    if (mask == null) {
                        logger.error("秒杀商品不存在，activitySecondKillId:" + item.getId());
                        return;
                    }
                    Goods goods = goodsService.getMapper().selectByPrimaryKey(item.getGoodId());
                    if (goods == null) {
                        logger.error("商品不存在，goodsId:" + item.getGoodId());
                        return;
                    }
                    if (mask.getSaledCount() == null) {
                        // 秒杀商品出售数量总和
                        int sellCount = mallActivitySecondKillMapper.getSaledCountByActivitySecondKillId(mask.getId());
                        // 根据活动开始日期、结束日期和活动所在星期n计算占用库存
                        int lockStock = mask.getStockCount() * this.calculateCycleTimes(mallActivity.getStartDate(), mallActivity.getStopDate(), mask.getWeekDay());
                        if (sellCount > lockStock) {
                            logger.error("秒杀商品锁定库存或出售库存异常");
                            return;
                        }
                        // 解锁库存
//                        int unlockStock = lockStock - saledCount;
                        if (this.updateActivityStock(goods, lockStock, sellCount, false)) {
                            mask.setSaledCount(sellCount);
                            mallActivitySecondKillMapper.updateByPrimaryKeySelective(mask);
                        }
                    }
                } catch (Exception e) {
                    logger.error("解锁库存出现异常：" + e.getMessage(), e);
                }

            });
        }
        
    }

}
