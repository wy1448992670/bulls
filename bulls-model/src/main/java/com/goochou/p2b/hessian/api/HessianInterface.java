package com.goochou.p2b.hessian.api;

import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;

/**
 * Created on 2014-8-18
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [Hessian接口, 给正真实现类来实现]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public interface HessianInterface {
	Response execute(ServiceMessage msg);
	void before(ServiceMessage msg);
	void after(ServiceMessage msg);
}
