package com.goochou.p2b.hessian.exception;

/**
 * Created on 2014-8-18
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [服务自定义异常]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class ServiceException extends Exception {

	/**
     * <p>Discription:[字段功能描述]</p>
     */
    private static final long serialVersionUID = -3393524534706982147L;

    public ServiceException() {
	}

	public ServiceException(Exception e) {
		super(e);
	}

	public ServiceException(String s) {
		super(s);
	}
}
