package com.goochou.p2b.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.AssetsMapper;
import com.goochou.p2b.dao.CapitalMapper;
import com.goochou.p2b.dao.InterestMapper;
import com.goochou.p2b.dao.InterestProductMapper;
import com.goochou.p2b.dao.InvestmentDetailMapper;
import com.goochou.p2b.dao.InvestmentMapper;
import com.goochou.p2b.dao.InvestmentTailDiffMapper;
import com.goochou.p2b.dao.ProductMapper;
import com.goochou.p2b.dao.ProjectPayPlanMapper;
import com.goochou.p2b.dao.TradeRecordMapper;
import com.goochou.p2b.dao.UserMapper;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.Capital;
import com.goochou.p2b.model.Interest;
import com.goochou.p2b.model.Investment;
import com.goochou.p2b.model.Product;
import com.goochou.p2b.model.ProductExample;
import com.goochou.p2b.model.TradeRecord;
import com.goochou.p2b.model.User;
import com.goochou.p2b.service.MessageService;
import com.goochou.p2b.service.ProductService;
import com.goochou.p2b.service.ProjectService;
import com.goochou.p2b.service.RateCouponService;
import com.goochou.p2b.service.exceptions.LockFailureException;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.DateFormatTools;

@Service
public class ProductServiceImpl implements ProductService {
	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);
    @Resource
    private ProductMapper productMapper;
    @Resource
    private InvestmentMapper investmentMapper;
    @Resource
    private InvestmentTailDiffMapper investmentTailDiffMapper;
    @Resource
    private InterestMapper interestMapper;
    @Resource
    private InterestProductMapper interestProductMapper;
    @Resource
    private ProjectService projectService;
    @Resource
    private UserMapper usermapper;
    @Resource
    private TradeRecordMapper tradeRecordMapper;
    @Resource
    private CapitalMapper capitalMapper;
    @Resource
    private ProjectPayPlanMapper projectPayPlanMapper;
    @Resource
    private InvestmentDetailMapper investmentDetailMapper;
    @Resource
    private AssetsMapper assetsMapper;
    @Resource
    private MessageService messageService;
    @Resource
    private  RateCouponService rateCouponService;


    @Override
    public List<Product> selectProductList(Integer status, Integer start, Integer limit) {
        ProductExample example = new ProductExample();
        if (start != null) {
            example.setLimitStart(start);
        }
        if (limit != null) {
            example.setLimitEnd(limit);
        }
        if (status != null) {
            example.createCriteria().andStatusEqualTo(status);
        }
        example.setOrderByClause("id  desc");
        return productMapper.selectByExample(example);
    }

    @Override
    public Integer selectProductCount(Integer status) {
        ProductExample example = new ProductExample();
        if (status != null) {
            example.createCriteria().andStatusEqualTo(status);
        }
        return productMapper.countByExample(example);
    }

    @Override
    public void saveProduct(Product product) {
        if (product.getId() == null) {
            productMapper.insertSelective(product);
        } else {
            productMapper.updateByPrimaryKeySelectiveAndVersion(product);
        }
    }

    @Override
    public Product selectById(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public Product selectMinOutDaysProduct() {

        return productMapper.selectMinOutDaysProduct();
    }

    @Override
    public Product selectMaxAnnualizedProduct() {
        return productMapper.selectMaxAnnualizedProduct();
    }

    @Override
    public Map<String, Object> selectProductLimit() {
        return productMapper.selectProductLimit();
    }

    @Override
    public List<Product> queryProductList(Integer start, Integer limit) {
        ProductExample example = new ProductExample();
        example.createCriteria().andStatusEqualTo(1);
        example.setOrderByClause("out_days,annualized_min,time");
        example.setLimitStart(start);
        example.setLimitEnd(limit);
        return productMapper.selectByExample(example);
    }

    @Override
    public Integer queryProductCount() {
        ProductExample example = new ProductExample();
        example.createCriteria().andStatusEqualTo(1);
        example.setOrderByClause("out_days");
        return productMapper.countByExample(example);
    }

    @Override
    public List<Map<String, Object>> selectByuserIdAndProductStatusList(Integer status, Integer userId, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", status);
        map.put("userId", userId);
        map.put("start", start);
        map.put("limit", limit);
        return investmentMapper.selectByuserIdAndProductStatusList(map);
    }

    @Override
    public Integer selectByuserIdAndProductStatusCount(Integer status, Integer userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", status);
        map.put("userId", userId);
        return investmentMapper.selectByuserIdAndProductStatusCount(map);
    }

    @Override
    public Map<String, Object> selectInvestmentIdByProductDetail(Integer investmentId, Integer userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("investmentId", investmentId);
        map.put("userId", userId);
        return investmentMapper.selectInvestmentIdByProductDetail(map);
    }

    @Override
    public List<Map<String, Object>> selectByInvestmentIdProjectList(Integer investmentId, Integer userId, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("investmentId", investmentId);
        map.put("userId", userId);
        map.put("start", start);
        map.put("limit", limit);
        return productMapper.selectByInvestmentIdProjectList(map);
    }

    @Override
    public Integer selectByInvestmentIdProjectCount(Integer investmentId, Integer userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("investmentId", investmentId);
        map.put("userId", userId);
        return productMapper.selectByInvestmentIdProjectCount(map);
    }

    @Override
    public List<Map<String, Object>> selectInvestDetailIvestList(Integer investmentId, Integer status, Integer userId, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("investmentId", investmentId);
        map.put("status", status);
        map.put("userId", userId);
        map.put("start", start);
        map.put("limit", limit);
        return productMapper.selectInvestDetailIvestList(map);
    }

    @Override
    public Integer selectInvestDetailIvestCount(Integer investmentId, Integer status, Integer userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("investmentId", investmentId);
        map.put("status", status);
        map.put("userId", userId);
        return productMapper.selectInvestDetailIvestCount(map);
    }

    @Override
    public List<Map<String, Object>> projectFormIvestList(Integer packageId, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("packageId", packageId);
        map.put("start", start);
        map.put("limit", limit);
        return productMapper.projectFormIvestList(map);
    }

    @Override
    public Integer projectFormIvestListCount(Integer packageId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("packageId", packageId);
        return productMapper.projectFormIvestListCount(map);
    }

    @Override
    public List<Map<String, Object>> projectFormIvestList1(Integer packageId, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("packageId", packageId);
        map.put("start", start);
        map.put("limit", limit);
        return productMapper.projectFormIvestList1(map);
    }

    @Override
    public Integer projectFormIvestListCount1(Integer packageId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("packageId", packageId);
        return productMapper.projectFormIvestListCount1(map);
    }

    @Override
    public List<Map<String, Object>> list(Integer productId, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", productId);
        map.put("start", start);
        map.put("limit", limit);
        return productMapper.list(map);
    }

    @Override
    public Integer listCount(Integer productId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", productId);
        return productMapper.listCount(map);
    }

    @Override
    public void saveProductInterest() {//新定期产品派息方法

    }


    @Override
    public void saveInterestCompound(Map<String, Object> map) throws Exception {
        Integer userId = (Integer) map.get("userId");
        Integer investmentId = (Integer) map.get("investmentId");
        List<String> detailIds = Arrays.asList(((String) map.get("detailId")).split(","));
        // Double totalInterestAmount = (Double) map.get("totalInterestAmount");
        Double totalCapitalAmount = (Double) map.get("totalCapitalAmount");
        Investment fromInvestment = investmentMapper.selectByPrimaryKey(investmentId);
        Product product = productMapper.selectByPrimaryKey(fromInvestment.getProductId());

        int capitalRet = -1;
//        if (totalCapitalAmount > 0) {
//            //本金大于0复投
//            for (int i = 0; i < detailIds.size(); i++) {
//                Integer detailId = Integer.valueOf(detailIds.get(i));
//                Map<String, Object> params = new HashedMap();
//                params.put("investmentDetailId", detailId);
//                params.put("isInterest", false);
//                Map<String, Object> detailMap = interestMapper.getTodayPayPackageProjectInterestListDetail(params);
//                String[] ids = ((String) detailMap.get("ids")).split(",");
//                Double capitalAmount = (Double) detailMap.get("capitalAmount"); //总的本金
//                //本金如果算出来大于0的话，那么就还要本金复投
//                if (capitalAmount > 0) {
//                    //本金复投开始执行
//                    Investment investment2 = new Investment();
//                    investment2.setTime(new Date());
//                    investment2.setAmount(capitalAmount);
//                    investment2.setUserId(userId);
//                    investment2.setRemainAmount(capitalAmount);
//                    investment2.setInterestUsableAmount(capitalAmount);
//                    investment2.setProductId(fromInvestment.getProductId());
//                    investment2.setType(7); //本金复投
//                    investment2.setParentId(fromInvestment.getId());
//                    capitalRet = investmentService.addInvestmentWithPackageCompound(investment2);
//
//                    //本金复投成功
//                    for (String id : ids) {
//                        Interest interest = interestMapper.selectByPrimaryKey(Integer.valueOf(id));
//                        if (interest.getCapitalAmount() > 0) {
//                            if (capitalRet == 0) {
//                                interest.setHasCastCapital(1); //本金复投成功
//                            } else {
//                                interest.setHasCastCapital(2); //本金复投失败
//                            }
//                            interest.setHasDividended(1);
//                            interestMapper.updateByPrimaryKeySelective(interest);
//                        }
//                    }
//
//                    if (capitalRet == 0) {
//                        List<String> newList = new ArrayList<>();
//                        newList.add(detailIds.get(i));
//                        detailIds.remove(i);
//                        //只有当该笔本金成功复投，才开始复投利息，如果本金都复投失败了，那么利息就不应该做复投了.假设利息复投失败了。那么接下来都不会执行利息复投了。会返还到用户
//                        interestCompound(newList, fromInvestment, product, true);
//
//                        Double leftAmount = BigDecimalUtil.sub(fromInvestment.getInterestUsableAmount(), capitalAmount);
//                        if (leftAmount <= 0) {
//                            //修改资产和原投资信息
//                            fromInvestment.setInterestUsableAmount(0d);
//                            fromInvestment.setRemainAmount(0d);
//                            fromInvestment.setApplyOutTime(new Date());
//                            fromInvestment.setSuccessOutTime(new Date());
//                            fromInvestment.setStatus(3); //已复投
//                            investmentMapper.updateByPrimaryKeySelectiveAndVersion(fromInvestment); //详情全部退出 未推出的
//
//                            List<InvestmentDetail> detailList = investmentDetailService.getByInvestmentId(fromInvestment.getId(), 0);
//                            if (null != detailList && !detailList.isEmpty()) {
//                                for (InvestmentDetail detail : detailList) {
//                                    detail.setInterestUsableAmount(0d);
//                                    detail.setRemainAmount(0d);
//                                    detail.setApplyOutTime(new Date());
//                                    detail.setSuccessOutTime(new Date());
//                                    detail.setStatus(3);//已复投
//                                    investmentDetailMapper.updateByPrimaryKeySelective(detail);
//                                }
//                            }
//                        } else {
//                            //只有当前detail 需要修改
//                            InvestmentDetail detail = investmentDetailMapper.selectByPrimaryKey(detailId);
//                            detail.setInterestUsableAmount(0d);
//                            detail.setRemainAmount(0d);
//                            detail.setApplyOutTime(new Date());
//                            detail.setSuccessOutTime(new Date());
//                            detail.setStatus(3);//已复投
//                            investmentDetailMapper.updateByPrimaryKeySelective(detail);
//                        }
//                        Assets assets = assetsMapper.selectByPrimaryKey(userId);
//                        assets.setUncollectCapital(BigDecimalUtil.sub(assets.getUncollectCapital(), capitalAmount));
//                        assetsMapper.updateByPrimaryKeyAndVersionSelective(assets);
//                    }
//                }
//            }
//        }

        //其他情况利息复投
        interestCompound(detailIds, fromInvestment, product, false);


        //补漏，所有的本金未复投或者失败，但是利息并没有复投的
//        if (detailIds != null && detailIds.size() > 0) {
//            List<Integer> detailIdList = new ArrayList<>();
//            for (String detailId : detailIds) {
//                detailIdList.add(Integer.valueOf(detailId));
//            }
//            InterestExample interestExample = new InterestExample();
//            List<Integer> sList = new ArrayList<>();
//            sList.add(0);
//            sList.add(2);
//            interestExample.createCriteria().andHasCastCapitalIn(sList).andHasCastEqualTo(0).andCapitalAmountGreaterThan(0d).andInvestmentDetailIdIn(detailIdList);
//            List<Interest> interests = interestMapper.selectByExample(interestExample);
//            for (Interest i : interests) {
//                Integer detailId = Integer.valueOf(i.getInvestmentDetailId());
//                Date date = interestMapper.getMaxCastDateByInvestmentDetailId(detailId);
//                Investment fromInvestment2 = investmentMapper.selectByPrimaryKey(i.getInvestmentId());
//                Product product2 = productMapper.selectByPrimaryKey(fromInvestment2.getProductId());
//                InvestmentDetail detail = investmentDetailMapper.selectByPrimaryKey(i.getInvestmentDetailId());
//                if (date == null) {
//                    date = DateFormatTools.jumpOneDay(fromInvestment.getTime(), 1);
//                }
//
//                long days = DateFormatTools.dayToDaySubtractWithoutSeconds(date, new Date()); //日期差
//                Double interestAmount = BigDecimalUtil.fixed2(detail.getInterestUsableAmount() * product2.getAnnualizedMin() * days / 365); //截止到派息日应该计算的收益
//
//                Investment investment = new Investment();
//                investment.setTime(new Date());
//                investment.setAmount(interestAmount);
//                investment.setUserId(i.getUserId());
//                investment.setRemainAmount(interestAmount);
//                investment.setInterestUsableAmount(interestAmount);
//                investment.setProductId(fromInvestment2.getProductId());
//                investment.setType(6);
//                investment.setParentId(i.getInvestmentId());
//                int ret = investmentService.addInvestmentWithPackageCompound(investment);
//                i.setInterestAmount(interestAmount);
//                if (ret == 0) {
//                    i.setHasCast(1);
//                } else {
//                    i.setHasCast(2);
//                }
//                i.setHasDividended(1);
//                interestMapper.updateByPrimaryKeySelective(i);
//            }
//        }

    }

    private void interestCompound(List<String> detailIds, Investment fromInvestment, Product product, boolean force) throws Exception {
        Integer investmentId = fromInvestment.getId();
        Integer userId = fromInvestment.getUserId();
        /**
         *
         * 例子数据
         *    ids               detailAmount     userId    investmentId  payId   detailId    capitalAmount     totalInterest
         * 235,247,248	3000.00,5000.00,5000.00	  6014	     141117	     83	      26,27	   0.00,0.00,5000.00	98.62
         * */
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < detailIds.size(); i++) {
            //判断是否已经有过复投结算的记录，没有按照投资时间开始计算，有的话按照最后一次复投或者复投失败返还的记录时间计算
            Integer detailId = Integer.valueOf(detailIds.get(i));

            Map<String, Object> params = new HashedMap();
            params.put("investmentDetailId", detailId);
            params.put("isInterest", true);
            Map<String, Object> detailMap = interestMapper.getTodayPayPackageProjectInterestListDetail(params);
            if (detailMap != null) {
                //有可能空，因为查出来可能利息已经复投了
                Double detailAmount = (Double) detailMap.get("detailAmount"); //剩余可计算利息的金额
                //判断是否已经有过复投结算的记录，没有按照投资时间开始计算，有的话按照最后一次复投或者复投失败返还的记录时间计算
                String[] ids = ((String) detailMap.get("ids")).split(",");
                Double capitalAmount = (Double) detailMap.get("capitalAmount"); //总的本金

                Date date = interestMapper.getMaxCastDateByInvestmentDetailId(detailId);
                if (date == null) {
//                    date = DateFormatTools.jumpOneDay(fromInvestment.getTime(), 1);
                }
                long days = DateFormatTools.dayToDaySubtractWithoutSeconds(date, new Date()); //日期差
           //     Double interestAmount = BigDecimalUtil.fixed2(detailAmount * product.getAnnualizedMin() * days / 365); //截止到派息日应该计算的收益

            //    if (force || interestAmount >= 50) {
                    //调用复投方法
                    Investment investment = new Investment();
//                    investment.setTime(new Date());
                 //   investment.setAmount(interestAmount);
                    investment.setUserId(userId);
                  //  investment.setRemainAmount(interestAmount);
                  //  investment.setInterestUsableAmount(interestAmount);
                    investment.setProductId(fromInvestment.getProductId());
                    investment.setType(6);
//                    investment.setParentId(investmentId);
                    //int ret = investmentService.addInvestmentWithPackageCompound(investment);
                    int ret = 0;

                    int hasCastCapital = 0;
                    //删除当前查出的利息ID
                    for (String interestId : ids) {
                        Interest interest = interestMapper.selectByPrimaryKey(Integer.valueOf(interestId));
//                        if (interest.getHasCastCapital() != 0) {
//                            hasCastCapital = interest.getHasCastCapital();
//                        }
                        interestMapper.deleteByPrimaryKey(Integer.valueOf(interestId));
                    }


                    //按照detail的结算重新生成
                    Interest newInterest = new Interest();
                //    newInterest.setInterestAmount(interestAmount);
                    newInterest.setDate(new Date());
                    if (ret == 0) {
//                        newInterest.setHasCast(1);
                    } else {
//                        newInterest.setHasCast(2);
                    }
                    newInterest.setHasDividended(1);
                    newInterest.setInvestmentId(investmentId);
//                    newInterest.setInvestmentDetailId(detailId);
                    newInterest.setType(2);
                    newInterest.setUserId(userId);
                    newInterest.setCapitalAmount(capitalAmount);
//                    newInterest.setHasCastCapital(hasCastCapital);
                    interestMapper.insertSelective(newInterest);
            //    }
            }
        }
    }


    @Override
    public void saveProductFailedCompund() throws Exception {
        //复投失败的所有 返还本金或者利息到用户账户余额
        //本金复投成功，但是利息没有复投的也计算在这里，失败返还
        List<Interest> list = interestMapper.getCompoundFailedList();
        if (null != list && !list.isEmpty()) {
            for (Interest interest : list) {
//                boolean isCapitalFailed = interest.getHasCastCapital().equals(2) ? true : false; //是否本金复投失败
//                boolean isInterestFailed = interest.getHasCast().equals(2) || interest.getHasCast().equals(0) ? true : false; //是否利息复投失败

                Double interestAmount = interest.getInterestAmount();
                Double capital = interest.getCapitalAmount();
                User user = usermapper.selectByPrimaryKey(interest.getUserId());
                Integer userId = user.getId();
                Assets assets = user.getAssets();
                // 如果是利息失败 插入交易记录表 -- 收益利息记录
//                if (isInterestFailed) {
////                    interest.setHasCast(3); //改成失败返还状态
////                    assets.setAvailableBalance(BigDecimalUtil.add(assets.getAvailableBalance(), interestAmount));
////                    assets.setTotalIncome(BigDecimalUtil.add(assets.getTotalIncome(), interestAmount)); // 总的投资收益
//
//                    TradeRecord tr = new TradeRecord();
//                    tr.setAmount(interestAmount);
////                    tr.setBalance(assets.getAvailableBalance());
//                    tr.setOtherId(interest.getId());
//                    tr.setTableName("interest");
//                    tr.setCreateDate(new Date());
//                   /* tr.setType(3);
//                    tr.setSource(5);*/
//                    tr.setUserId(userId);
//                    tradeRecordMapper.insertSelective(tr);
//                }
//
//                // 如果是本金失败 插入本金回收交易记录
//                if (isCapitalFailed) {
////                    interest.setHasCastCapital(3);
////                    assets.setAvailableBalance(BigDecimalUtil.add(assets.getAvailableBalance(), capital));
////                    assets.setUncollectCapital(BigDecimalUtil.sub(assets.getUncollectCapital(), capital)); // 待收本金
//
//                    Capital ca = new Capital();
//                    ca.setAmount(capital);
//                    ca.setInvestmentId(interest.getInvestmentId());
//                    ca.setTime(new Date());
//                    capitalMapper.insert(ca);
//
//                    TradeRecord ctr = new TradeRecord();
//                    ctr.setAmount(capital);
////                    ctr.setBalance(assets.getAvailableBalance());
//                    ctr.setOtherId(ca.getId());
//                    ctr.setTableName("capital");
//                    ctr.setCreateDate(new Date());
//                   /* ctr.setType(4);
//                    ctr.setSource(5);*/
//                    ctr.setUserId(userId);
//                    tradeRecordMapper.insertSelective(ctr);
//
//                    Investment fromInvestment = investmentMapper.selectByPrimaryKey(interest.getInvestmentId());
////                    //修改资产和原投资信息
////                    fromInvestment.setInterestUsableAmount(0d);
////                    fromInvestment.setRemainAmount(0d);
////                    fromInvestment.setApplyOutTime(new Date());
////                    fromInvestment.setSuccessOutTime(new Date());
////                    fromInvestment.setStatus(2); //已复投
//                    investmentMapper.updateByPrimaryKeySelectiveAndVersion(fromInvestment);
////                    messageService.save("您的未复投资金已派发！","尊敬的鑫聚财用户，您于"+DateFormatTools.dateToStrCN2(fromInvestment.getTime())
////                            +"投资的安鑫赚名称，借款项目付息后24小时内"+capital
////                            +"元未成功匹配债权，现已派发至您的账户余额，请及时查看！若有疑问，请致电鑫聚财客服：400-168-8082。",user.getId());
//
//                    //详情全部退出 未推出的
////                    List<InvestmentDetail> detailList = investmentDetailService.getByInvestmentId(fromInvestment.getId(), 0);
////                    if (null != detailList && !detailList.isEmpty()) {
////                        for (InvestmentDetail detail : detailList) {
////                            detail.setInterestUsableAmount(0d);
////                            detail.setRemainAmount(0d);
////                            detail.setApplyOutTime(new Date());
////                            detail.setSuccessOutTime(new Date());
////                            detail.setStatus(2);//已复投
////                            investmentDetailMapper.updateByPrimaryKeySelective(detail);
////                        }
////                    }
//                }

                interestMapper.updateByPrimaryKeySelective(interest);

                // 修改用户的assets
                int ret = assetsMapper.updateByPrimaryKeyAndVersionSelective(assets);
                if (ret != 1) {
                	logger.info("=========复投失败返还本金修改资产失败，重试！！！==========");
                    throw new LockFailureException();
                }
            }
        }
    }

    @Override
    public Double selectYesterdayAmount(Integer userId) {

        return productMapper.selectYesterdayAmount(userId);
    }

    @Override
    public Double selectProductTotalAmount(Integer userId) {

        return productMapper.selectProductTotalAmount(userId);
    }

    public static void main(String[] args) {
        System.out.println(10000 * 0.01 * 30 / 365);
    }
}
