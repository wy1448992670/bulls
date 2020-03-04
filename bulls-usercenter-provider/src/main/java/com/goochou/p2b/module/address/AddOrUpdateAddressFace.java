package com.goochou.p2b.module.address;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.goochou.p2b.model.*;
import com.goochou.p2b.service.AreaService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.ProvinceCityMapper;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.user.UserAddressRequest;
import com.goochou.p2b.hessian.user.UserAddressResponse;
import com.goochou.p2b.service.UserAddressService;
import com.goochou.p2b.service.UserService;

@Service
public class AddOrUpdateAddressFace  implements HessianInterface{
    private static final Logger logger = Logger.getLogger(AddOrUpdateAddressFace.class);
	@Resource
	private UserAddressService userAddressService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private ProvinceCityMapper provinceCityMapper;

    @Resource
    private AreaService areaService;
    
	/**
	 * Code:400  	用户ID不能为空
	 * Code:404  	用户不存在
	 * Code:410  	手机号格式不正确
	 * Code:411 	地址ID有误
	 * Code:412 	地址归属有误
	 * Code:500 	地址保存异常
	 */
	@Override
	public Response execute(ServiceMessage msg) {
		UserAddressRequest req =  (UserAddressRequest) msg.getReq();
		UserAddressResponse  response = new UserAddressResponse();
		
		if(req.getUserId() == null) {
			response.setSuccess(false);
			response.setErrorCode("400");
            response.setErrorMsg("您还没有填写手机号");
            return response;
		}
        if(StringUtils.isBlank(req.getCityCode())) {
            response.setSuccess(false);
            response.setErrorCode("401");
            response.setErrorMsg("请选择所在城市");
            return response;
        }
        if(StringUtils.isBlank(req.getProvinceCode())) {
            response.setSuccess(false);
            response.setErrorCode("402");
            response.setErrorMsg("请选择所在省份");
            return response;
        }
		
		User user = userService.get(req.getUserId());
		if(user == null) {
			 response.setSuccess(false);
			 response.setErrorCode("404");
             response.setErrorMsg("用户不存在");
             return response;
		}
		
		boolean isPhone = userService.checkPhone(req.getPhone());
		if(!isPhone) {
			 response.setSuccess(false);
			 response.setErrorCode("410");
             response.setErrorMsg("请输入正确的联系电话");
             return response;
		}
 
	    UserAddress userAddress = new UserAddress();
        userAddress.setUserId(user.getId());
        userAddress.setDetail(req.getDetailAddress());
        userAddress.setPhone(req.getPhone());
        userAddress.setName(req.getName());
//        if (req.getProvinceId() != null && req.getCityId() != null) {
//            // 2.0版本后弃用
//            userAddress.setpId(req.getProvinceId().toString());
//            userAddress.setcId(req.getCityId().toString());
//        }
        if (StringUtils.isNotBlank(req.getProvinceCode()) && StringUtils.isNotBlank(req.getCityCode())) {
            userAddress.setpId(req.getProvinceCode());
            userAddress.setcId(req.getCityCode());
            userAddress.setaId(req.getAreaCode());
        }
        userAddress.setRemarks(req.getRemarks());
        userAddress.setPostcode(req.getPostcode());
      
        Integer idDefault = req.getIsDefault();
        
        if (idDefault == null) {//用户不选时，新增地址默认为默认地址
        	idDefault = 1;
        }
        userAddress.setReserve(idDefault);//是否是默认地址   0否1是
        
        //查询联合索引已删除的地址
        //user_id, name, phone, detail, p_id, c_id
//        UserAddressExample example = new UserAddressExample();
//        UserAddressExample.Criteria c =  example.createCriteria();
//        if (req.getProvinceId() != null && req.getCityId() != null) {
//            c.andCIdEqualTo(req.getCityId().toString());
//            c.andPIdEqualTo(req.getProvinceId().toString());
//        }
//        if (req.getProvinceCode() != null && req.getCityCode() != null) {
//            c.andPIdEqualTo(req.getProvinceCode());
//            c.andCIdEqualTo(req.getCityCode());
//            if (req.getAreaCode() != null) {
//                c.andAIdEqualTo(req.getAreaCode());
//            }
//        }
//        c.andUserIdEqualTo(user.getId());
//        c.andNameEqualTo(req.getName());
//        c.andPhoneEqualTo(req.getPhone());
//        c.andDetailEqualTo(req.getDetailAddress());
//        c.andReserveEqualTo(2);//已删除的地址
//        List<UserAddress> list = userAddressService.getUserAddressMapper().selectByExample(example);
        
        try {
        	if (req.getAddressId() == null) { //新增
        		if (idDefault == 1) {//设置当前保存，或者新增地址为默认地址，其他地址全部更新为不是默认地址
                    userAddressService.updateAllReserve(user.getId());
                }
        		
//                if(list != null && list.size() > 0) { //如果已存在地址,将地址设置为有效
//                	logger.info("已存在地址,且已逻辑删除.=====" + req.getDetailAddress() +"====修改为有效");
//                	UserAddress existAddress = list.get(0);
//                	if(idDefault == 1) { //是否默认地址 0否 1是 
//                		existAddress.setReserve(1);
//                	} else {
//                		existAddress.setReserve(0);
//                	}
//                	
//                	userAddressService.updateAddress(existAddress);
//                } else {
//                	logger.info("新增地址======" + req.getDetailAddress());
//                	userAddress.setCreateDate(new Date());
//                    userAddressService.saveAddress(userAddress);
//                }
                logger.info("新增地址======" + req.getDetailAddress());
                userAddress.setCreateDate(new Date());
                userAddressService.saveAddress(userAddress);
                
                response.setSuccess(true);
                response.setAddress(userAddress);
                response.setMsg("保存成功");
                
            } else { //修改地址
                UserAddress ua = userAddressService.selectKeyUserAddress(req.getAddressId());
                if (ua == null) {
                    response.setSuccess(false);
                    response.setErrorCode("411");
                    response.setErrorMsg("你还没有填写收货地址");
                    return response;
                }
                if(ua.getUserId().intValue() != req.getUserId()) {
                	 response.setSuccess(false);
                     response.setErrorCode("412");
                     response.setErrorMsg("地址归属有误");
                     return response;
                }
                if (idDefault == 1) {//设置当前保存,或者新增地址为默认地址,其他地址全部更新为不是默认地址
                    userAddressService.updateAllReserve(user.getId());
                }
                userAddress.setId(req.getAddressId());
                userAddress.setUpdateDate(new Date());
                userAddressService.updateAddress(userAddress);
                
                response.setSuccess(true);
                response.setMsg("更新成功");
                response.setAddress(userAddress);
            }

            //添加省市名称 (用于客户端显示，不参与更新)
            Area province = areaService.getAreaByCode(req.getProvinceCode());
            if (province == null) {
                response.setSuccess(false);
                response.setErrorMsg("省不存在");
                return response;
            }
            Area city = areaService.getAreaByCode(req.getCityCode());
            if (city == null) {
                response.setSuccess(false);
                response.setErrorMsg("市不存在");
                return response;
            }
            userAddress.setProvinceName(province.getName());
            userAddress.setCityName(city.getName());
            if (StringUtils.isNotBlank(req.getAreaCode())) {
                Area area = areaService.getAreaByCode(req.getAreaCode());
                userAddress.setAreaName(area == null ? null : area.getName());
            }
            
            return response;
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorCode("500");
            response.setErrorMsg("保存地址异常");
            return response;
		}
        
	}

	@Override
	public void before(ServiceMessage msg) {
		
	}

	@Override
	public void after(ServiceMessage msg) {
		
	}

}
