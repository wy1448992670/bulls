package com.goochou.p2b.annotationin.client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2014-8-25
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [验证结果对象]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class ValidatorResult {
	
	private Object validateObject;
	
	private boolean validated = true;
	
	private List<String> invalidateDescList = new ArrayList<String>();

	@Override
	public String toString() {
		if (validateObject == null) {
			return "No Result";
		}
		StringBuilder sb = new StringBuilder();
        //sb.append("对象[").append(validateObject.getClass()).append(" - ").append("]");
        if (validated) {
            sb.append("验证通过");
        } else {
            //sb.append("验证未通过，未通过原因：");
            int rownum = 1;
            for (String detailDesc : invalidateDescList) {
                //sb.append("\t").append(rownum).append(".\t").append(detailDesc);
            	sb.append("\t").append(detailDesc);
                ++rownum;
            }
        }
		
		return sb.toString();
	}

	public Object getValidateObject() {
		return validateObject;
	}

	public void setValidateObject(Object validateObject) {
		this.validateObject = validateObject;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public List<String> getInvalidateDescList() {
		return invalidateDescList;
	}

	public void setInvalidateDescList(List<String> invalidateDescList) {
		this.invalidateDescList = invalidateDescList;
	}
}
