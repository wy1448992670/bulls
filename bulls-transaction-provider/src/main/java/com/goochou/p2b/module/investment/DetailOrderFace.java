package com.goochou.p2b.module.investment;

import com.goochou.p2b.constant.ProjectTypeEnum;
import com.goochou.p2b.constant.pasture.InvestmentStateEnum;
import com.goochou.p2b.dao.ProjectViewMapper;
import com.goochou.p2b.model.Project;
import com.goochou.p2b.model.ProjectExample;
import com.goochou.p2b.model.vo.ProjectView;
import com.goochou.p2b.model.vo.ProjectViewExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.hessian.MapResponse;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.transaction.investment.InvestOrderDetailRequest;
import com.goochou.p2b.hessian.transaction.investment.InvestOrderDetailResponse;
import com.goochou.p2b.model.Investment;
import com.goochou.p2b.model.vo.InvestmentDetailsVO;
import com.goochou.p2b.service.InvestmentService;
import com.goochou.p2b.service.PastureOrderService;
import com.goochou.p2b.service.ProjectService;
import com.goochou.p2b.utils.BigDecimalUtil;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DetailOrderFace  implements HessianInterface {

    @Autowired
    private PastureOrderService orderService;
    @Autowired
    private InvestmentService investmentService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectViewMapper projectViewMapper;
    
    @Override
    public InvestOrderDetailResponse execute(ServiceMessage msg) {

    	InvestOrderDetailRequest orderRequest = (InvestOrderDetailRequest) msg.getReq();
    	InvestOrderDetailResponse response = new InvestOrderDetailResponse();

        try {
        	Integer investId = orderRequest.getInvestId();
        	Investment invest = investmentService.getMapper().selectByPrimaryKey(investId);
            if(invest == null) {
                response.setSuccess(false);
                response.setErrorMsg("该投资订单不存在");
                return response;
            }
        	//获取项目图片
        	String path = projectService.getProjectsmallImagePath("1", invest.getProjectId(), true);
        	InvestmentDetailsVO detail = orderService.queryInvestDetailById(investId);
        	detail.setPath(path);
        	//格式化数据
        	detail.setAnnualizedStr(BigDecimalUtil.multi(Double.valueOf(detail.getAnnualized()), 100)+"%");
            detail.setLimitDaysStr(detail.getLimitDays()+"天");
        	detail.setPin(ProjectTypeEnum.PINNIU.getFeatureType() == invest.getType());
        	
        	if (detail.getPin()) {
        		ProjectViewExample example = new ProjectViewExample();
                example.createCriteria().andIdEqualTo(invest.getProjectId());
                List<ProjectView> projectViewList = projectViewMapper.selectByExample(example);
                ProjectView projectView=null;
                if(projectViewList.size()==1){
                	projectView=projectViewList.get(0);
                }else {
                	response.setSuccess(false);
                    response.setErrorMsg("牛只查询错误");
                    return response;
                }
                
                if (ProjectTypeEnum.PINNIU.getFeatureType() != projectView.getProjectType()) {
                    response.setSuccess(false);
                    response.setErrorMsg("该投资项目类型不是拼牛");
                    return response;
                }
        		
        		
        	    // 计算投资订单所占比例
                BigDecimal pinRateForAmount = projectView.getPinRateForAmount(BigDecimal.valueOf(detail.getAmount()));
                
        	    detail.setPinRate(pinRateForAmount.multiply(BigDecimal.valueOf(100)).setScale(2,BigDecimal.ROUND_FLOOR)+"%");
        	    
        	    // 剩余可投分数
                detail.setPinResiduePoint(projectView.getPinResiduePoint());
                
                // 计算投资订单所占份数的饲养费，管理费
                detail.setRaiseFee(pinRateForAmount.multiply(BigDecimal.valueOf(detail.getRaiseFee())).setScale(2,BigDecimal.ROUND_FLOOR).doubleValue());
                detail.setManageFee(pinRateForAmount.multiply(BigDecimal.valueOf(detail.getManageFee())).setScale(2,BigDecimal.ROUND_FLOOR).doubleValue());

            }
            response.setSuccess(true);
            response.setDetails(detail);
            
        } catch (Exception e) {
        	e.printStackTrace();
            OpenApiApp.EXCEPTION.exception(msg, e);
            response.setSuccess(false);
            response.setErrorMsg("出现异常");
        }
        return response;
    }

    @Override
    public void before(ServiceMessage msg) {

    }

    @Override
    public void after(ServiceMessage msg) {

    }
}
