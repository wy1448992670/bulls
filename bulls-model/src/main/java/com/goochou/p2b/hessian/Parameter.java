package com.goochou.p2b.hessian;

import java.io.Serializable;

/**
 * Created on 2014-8-18
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [接口参数实现]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class Parameter implements Serializable
{
	/**
     * <p>Discription:[字段功能描述]</p>
     */
    private static final long serialVersionUID = 6571174572966935258L;

    private String parameterName;
	
	private Object parameterValue;
	
	public Parameter()
	{
		
	}
	
	public Parameter(String name, Object value)
	{
		this.parameterName = name;
		this.parameterValue = value;
	}

	public String getParameterName()
	{
		return parameterName;
	}

	public void setParameterName(String parameterName)
	{
		this.parameterName = parameterName;
	}

	public Object getParameterValue()
	{
		return parameterValue;
	}

	public void setParameterValue(Object parameterValue)
	{
		this.parameterValue = parameterValue;
	}

	@Override
	public String toString() {
		return "Parameter [parameterName=" + parameterName + ", parameterValue=" + parameterValue + "]";
	}
	
}
