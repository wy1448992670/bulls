package com.goochou.p2b.module.inface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.common.BaseAO;
import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.TestEnum;
import com.goochou.p2b.hessian.InterFaceRequest;
import com.goochou.p2b.hessian.InterFaceResponse;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.model.vo.InterfaceVO;

@Service
public class InterFace extends BaseAO implements HessianInterface
{

    @SuppressWarnings("unchecked")
	@Override
    public InterFaceResponse execute(ServiceMessage msg)
    {
    	List<InterfaceVO> list = new ArrayList<InterfaceVO>();
    	if (TestEnum.DEBUG.getFeatureName().equals(Constants.TEST_SWITCH)) {
	    	InterFaceRequest req = (InterFaceRequest) msg.getReq();
	    	Map<String, com.goochou.p2b.hessian.Service> map = (Map<String, com.goochou.p2b.hessian.Service>) OpenApiApp.SPRING_CONTEXT.getBean("interfaceMap");
	    	Iterator<Map.Entry<String, com.goochou.p2b.hessian.Service>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, com.goochou.p2b.hessian.Service> entry = it.next();
				if(Constants.INTERFACES.equals(entry.getKey())){
					continue;
				}
				if(null!=req.getName()){
					if(req.getName().equals(entry.getKey())){
						list.add(new InterfaceVO(entry.getKey(), entry.getValue()));
					}
				}else{
					list.add(new InterfaceVO(entry.getKey(), entry.getValue()));
				}
			}
    	}
		InterFaceResponse result = new InterFaceResponse();
		result.setInterfaces(list);
		return result;
    }

    
    @Override
    public void before(ServiceMessage msg)
    {
        System.out.println("before");

    }

    @Override
    public void after(ServiceMessage msg)
    {
        System.out.println("after");

    }
}
