package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.goochou.p2b.model.*;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.alibaba.fastjson.JSON;
import com.goochou.p2b.constant.pasture.InvestmentStateEnum;
import com.goochou.p2b.dao.AssetsMapper;
import com.goochou.p2b.dao.InvestmentViewMapper;
import com.goochou.p2b.dao.JobMapper;
import com.goochou.p2b.dao.MessageMapper;
import com.goochou.p2b.dao.PropertySalesStoreStatusViewMapper;
import com.goochou.p2b.dao.RateCouponMapper;
import com.goochou.p2b.model.vo.PropertySalesStoreStatusView;
import com.goochou.p2b.model.vo.PropertySalesStoreStatusViewExample;
import com.goochou.p2b.service.ActivityBlessingChanceRecordService;
import com.goochou.p2b.service.ActivityBlessingRegularGiveService;
import com.goochou.p2b.service.GoodsOrderService;
import com.goochou.p2b.service.PaymentCheckService;
import com.goochou.p2b.utils.DateUtil;

/**
 * AssignUserToCustome
 * 把新注册的用户分配给客服
 *
 * @author zhaoxingxing
 * @date 2016/7/13
 */
//@Component
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class AppTest extends AbstractJUnit4SpringContextTests {
    @Resource
    private RateCouponMapper rateCouponMapper;
    @Resource
    private MessageMapper messageMapper;
    @Resource
    private GoodsOrderService goodsOrderService;
    @Resource
    private AssetsMapper assetsMapper;
    @Resource
    private JobMapper jobMapper;
    @Resource
    private PropertySalesStoreStatusViewMapper propertySalesStoreStatusViewMapper;
    @Resource
    private PaymentCheckService paymentCheckService;
    @Resource
    private ActivityBlessingChanceRecordService activityBlessingChanceRecordService;
    @Resource
    private ActivityBlessingRegularGiveService activityBlessingRegularGiveService;
    @Resource
    private InvestmentViewMapper investmentViewMapper;

    
    //@Test
    public void testSelect() {
    	InvestmentViewExample example=new InvestmentViewExample();
		com.goochou.p2b.model.InvestmentViewExample.Criteria criteria=example.createCriteria();
		criteria.andOrderStatusEqualTo(InvestmentStateEnum.buyed.getCode());
		criteria.andOrderStatusEqualTo(InvestmentStateEnum.saled.getCode());
		System.out.println(investmentViewMapper.countUserByExample(example));
    }
    
    
    @Test
    public void testPaymentCheck() throws Exception {
    	logger.info("------定期发送抽副卡次数 start------");
    	List<ActivityBlessingRegularGive> activityBlessingRegularGiveList;
		try {
			activityBlessingRegularGiveList = activityBlessingRegularGiveService.getNoGivenList();
			for(ActivityBlessingRegularGive activityBlessingRegularGive:activityBlessingRegularGiveList) {
	    		try {
	    			activityBlessingRegularGiveService.doGive(activityBlessingRegularGive);
				} catch (Exception e) {
					logger.error(e, e);
				}
	    	}
		} catch (Exception e) {
			logger.error(e, e);
		}
    	
        logger.info("------定期发送抽副卡次数 end------");
    	
    	//Date date=DateUtil.getStrToDate("2019-12-02 00:00:00");
    	//paymentCheckService.doLoadFileByDate(date);
    	
    }
    //@Test
    public void testSave() {
        List<RateCoupon> couponList = new LinkedList<>();
        RateCoupon rc = new RateCoupon();
        rc.setType(1);
        rc.setCreateTime(new Date());
        rc.setUserId(2);
        rc.setSource(999);
        rc.setDescript("123");
        rc.setStatus(1);
        rc.setHasDividended(0);
        rc.setExpireTime(new Date());
        couponList.add(rc);
        rateCouponMapper.insertBatch(couponList);

        List<MessageReceiver> msgList = new LinkedList<>();
        MessageReceiver r = new MessageReceiver();
        r.setMessageId(1);
        r.setReceiveTime(new Date());
        r.setReceiverId(2);
        msgList.add(r);
        messageMapper.insertBatch(msgList);
    }
    
    //@Test
    public void myTestSave() {
    	Calendar currentPeriodEnd=Calendar.getInstance();
    	currentPeriodEnd.set(Calendar.HOUR_OF_DAY, 0);
    	currentPeriodEnd.set(Calendar.MINUTE, 0);
    	currentPeriodEnd.set(Calendar.SECOND, 0);
    	currentPeriodEnd.set(Calendar.MILLISECOND, 0);
    	Calendar currentPeriodStar=(Calendar) currentPeriodEnd.clone();
    	currentPeriodStar.add(Calendar.DATE, -1);
    	
    	jobMapper.createAssetsSnapshotTable("trade_record_20190815");
    	jobMapper.assetsSnapshot("trade_record_20190814", "trade_record_20190815", currentPeriodStar.getTime(), currentPeriodEnd.getTime());
    }
    
    //@Test
    public void propertySalesStoreStatusViewTest() {
    	
    	Assets assetsSum=assetsMapper.sumAssetsSnapshoot("assets");
    	Map<String,Double> sumTradeRecord=assetsMapper.sumTradeRecord(new Date(119,6,1),new Date(119,6,20));
    	System.out.println("sumTradeRecord :"+JSON.toJSONString(sumTradeRecord));
    	
    	Calendar datePoint=Calendar.getInstance();
    	datePoint.set(Calendar.MONTH, 6);
    	datePoint.set(Calendar.DAY_OF_MONTH, 1);
    	datePoint.set(Calendar.HOUR_OF_DAY, 0);
    	datePoint.set(Calendar.MINUTE, 0);
    	datePoint.set(Calendar.SECOND, 0);
    	datePoint.set(Calendar.MILLISECOND, 0);
    	Boolean saleStatus =false;
    	Integer yueLing=2;
    	int page=1;
    	int pageSize=10;
		
    	PropertySalesStoreStatusViewExample example=new PropertySalesStoreStatusViewExample();
    	example.setDatePoint(datePoint.getTime());
		example.setLimitStart((page - 1) * pageSize);
		example.setLimitEnd(pageSize);
    	PropertySalesStoreStatusViewExample.Criteria criteria=example.createCriteria();
		
		criteria.andIsRaisedByUsEqualTo(!saleStatus);
		criteria.andCurrentYueLingEqualTo(yueLing.longValue());
		
		
		
		List<PropertySalesStoreStatusView> propertyList=propertySalesStoreStatusViewMapper.selectHistoryByDatePoint(example);
		
    	//List<PropertySalesStoreStatusView> propertyList=propertySalesStoreStatusViewMapper.selectHistoryByDatePoint(example,datePoint.getTime());
    	System.out.println(JSON.toJSONString(propertyList));

    }
    
    //@Test
    public void tese2() throws Exception {
        goodsOrderService.doFlashOrder();
    }


    //@Test
    public void testReadFile() {
        File file = new File("E:\\data\\2019-12-02\\alpay.txt");
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            int i = 1;
            while ((tempStr = reader.readLine()) != null) {
                if (i > 5) {
                    if (tempStr.indexOf("#-") > -1) {
                        break;
                    }
                    sbf.append(tempStr);
                    System.out.println(tempStr);
                }
                i++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
//        System.out.println(sbf.toString());
    }
    
}
