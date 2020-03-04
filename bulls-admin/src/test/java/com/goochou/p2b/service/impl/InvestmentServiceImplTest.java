package com.goochou.p2b.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.goochou.p2b.model.*;
import com.goochou.p2b.model.vo.AreaIndexVO;
import com.goochou.p2b.service.*;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.alibaba.fastjson.JSONObject;

@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class InvestmentServiceImplTest extends AbstractJUnit4SpringContextTests {
	@Resource 
	private InvestmentService investmentService;
	@Resource 
	private ProjectService projectService;
	@Resource
	private ActivityBlessingChanceRecordService activityBlessingChanceRecordService;
	@Resource
	private UserService userService;
	@Resource
	private AreaService areaService;
	
	@Test
    public void testSave() {
		Integer id=25;
		Project project=projectService.get(id);
		System.out.println("project object context:"+JSONObject.toJSONString(project));
		try {
			//根据project,查询investment,并生成Interest,将结果封装回project 并更新project和investment的汇总信息
			//projectService.doGeneratedInterest(project);
			
			
			InvestmentExample example = new InvestmentExample();
			InvestmentExample.Criteria c = example.createCriteria();
			c.andProjectIdEqualTo(project.getId());
			List<Investment> investmentList = investmentService.getMapper().selectByExample(example);
			project.setInvestmentList(investmentList);
			//传入project,预生成投资账单interest并注入到project中
			investmentService.pregeneratedInterest(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("project object context:"+JSONObject.toJSONString(project));
	}
	
	//@Test
    public void testClone() throws CloneNotSupportedException {
		Integer id=25;
		Project project1=projectService.get(id);
		
		InvestmentExample example = new InvestmentExample();
		InvestmentExample.Criteria c = example.createCriteria();
		c.andProjectIdEqualTo(project1.getId());
		//c.andPayStatusEqualTo(1);// 支付状态 0：未支付，1：已支付
		//c.andOrderStatusEqualTo(0);// 0：未饲养，1：饲养期，2：已卖牛 3 已取消
		List<Investment> investmentList = investmentService.getMapper().selectByExample(example);
		project1.setInvestmentList(investmentList);
		Project project2=project1.clone();
	
		System.out.println("project1:"+project1.getInvestmentList());
		System.out.println("project2:"+project2.getInvestmentList());
		
		System.out.println("project1:"+project1.getInvestmentList().get(0));
		System.out.println("project2:"+project2.getInvestmentList().get(0));
		try {
			//projectService.doGeneratedInterest(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("project object context:"+JSONObject.toJSONString(project1));
	}

	public static <T extends Serializable> T deepCopy(T src) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(src);

		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);
		@SuppressWarnings("unchecked")
		T dest = (T) in.readObject();
		return dest;
	}

	@Test
	public void doSendOutChanceByInvestment() throws Exception {
		User user = userService.get(579467);
		//activityBlessingChanceRecordService.doSendOutChanceByInvestment(user, 1080);
		activityBlessingChanceRecordService.doSendOutChance(user, 1, null);
	}

	@Test
	public void register() throws Exception {
		User user = new User();
		user.setPhone("17621516960");
		user.setPassword("a1234567");
		user.setUsername("ap" + System.currentTimeMillis());
		user.setCreateDate(new Date());

		Map<String, Object> result = userService.addUserByRegist(user);

		System.out.println("********************");
		System.out.println(result.toString());
		System.out.println(result.get("user").toString());
		
	}


	@Test
	public void getAearAll() {
		long start = System.currentTimeMillis();
		List<AreaIndexVO> areas = areaService.getAllArea();

		System.out.println("=============>" + JSON.toJSON(areas));
		System.out.println("耗时" + (System.currentTimeMillis() - start) + "ms");
	}
	

	
}
