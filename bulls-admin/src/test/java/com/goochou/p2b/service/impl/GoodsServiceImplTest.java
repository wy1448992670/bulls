/**   
* @Title: GoodsServiceImplTest.java 
* @Package com.goochou.p2b.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zj
* @date 2019-05-29 10:00 
* @version V1.0   
*/
package com.goochou.p2b.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.goochou.p2b.dao.GoodsMapper;
import com.goochou.p2b.dao.GoodsPictureMapper;
import com.goochou.p2b.dao.GoodsPropertyValueMapper;
import com.goochou.p2b.dao.ProjectMapper;
import com.goochou.p2b.dao.ProjectPictureMapper;
import com.goochou.p2b.dao.ProjectPropertyValueMapper;
import com.goochou.p2b.model.GoodsPicture;
import com.goochou.p2b.model.Project;
import com.goochou.p2b.model.ProjectPicture;
import com.goochou.p2b.model.ProjectPropertyValue;
import com.goochou.p2b.model.goods.Goods;
import com.goochou.p2b.model.goods.GoodsPropertyValue;

/**
 * @ClassName: GoodsServiceImplTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zj
 * @date 2019-05-29 10:00
 */
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class GoodsServiceImplTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	GoodsMapper goodsMapper;
	@Autowired
	GoodsPropertyValueMapper goodsPropertyValueMapper;

	@Autowired
	GoodsPictureMapper goodsPictureMapper;
	@Autowired
	ProjectMapper projectMapper;
	@Autowired
	ProjectPropertyValueMapper projectPropertyValueMapper;
	@Autowired
	ProjectPictureMapper projectPictureMapper;

	/**
	 * Test method for
	 * {@link com.goochou.p2b.service.impl.GoodsServiceImpl#listGoods(java.lang.Integer, java.lang.Integer, java.lang.String)}.
	 */
	@Test
	public void addGoodsTest() {

		for (int i = 0; i < 1000; i++) {
			Goods goods = new Goods();
			goods.setCategoryId(1);
			goods.setBrandId(1);
			goods.setGoodsName("达达牛肉" + i);
			goods.setGoodsNo("S" + getFixLengthString(10));
			goods.setBuyingPrice(new BigDecimal(3000));
			goods.setSalingPrice(new BigDecimal(6000));
			goods.setMemberSalingPrice(new BigDecimal(5500));
			goods.setStock(800);
			goods.setIntroduction("很美味的草原牛肉");
			goods.setVersion(0);
			goods.setCreateDate(new Date());
			goods.setUpdateDate(new Date());
			goodsMapper.insert(goods);
			GoodsPropertyValue propertyValue = new GoodsPropertyValue();
			// 属性值
			propertyValue.setPropertyId(1);
			propertyValue.setGoodsId(goods.getId());
			propertyValue.setPropertyValue("英国");
			propertyValue.setCreateDate(new Date());
			propertyValue.setUpdateDate(new Date());
			goodsPropertyValueMapper.insert(propertyValue);
			GoodsPropertyValue propertyValue2 = new GoodsPropertyValue();
			propertyValue2.setPropertyId(2);
			propertyValue2.setGoodsId(goods.getId());
			propertyValue2.setPropertyValue("365天");
			propertyValue2.setCreateDate(new Date());
			propertyValue2.setUpdateDate(new Date());
			goodsPropertyValueMapper.insert(propertyValue2);
			GoodsPropertyValue propertyValue3 = new GoodsPropertyValue();
			propertyValue3.setPropertyId(3);
			propertyValue3.setGoodsId(goods.getId());
			propertyValue3.setPropertyValue("59kg");
			propertyValue3.setCreateDate(new Date());
			propertyValue3.setUpdateDate(new Date());
			goodsPropertyValueMapper.insert(propertyValue3);
			// 图片
			GoodsPicture godGoodsPicture = new GoodsPicture();
			godGoodsPicture.setGoodsId(goods.getId());
			godGoodsPicture.setType(12);
			godGoodsPicture.setName("牛肉");
			godGoodsPicture.setUploadId(349);
			godGoodsPicture.setCreateDate(new Date());
			godGoodsPicture.setStatus(1);
			goodsPictureMapper.insert(godGoodsPicture);
			GoodsPicture godGoodsPicture2 = new GoodsPicture();
			godGoodsPicture2.setGoodsId(goods.getId());
			godGoodsPicture2.setType(14);
			godGoodsPicture2.setName("牛肉");
			godGoodsPicture2.setUploadId(348);
			godGoodsPicture2.setCreateDate(new Date());
			godGoodsPicture2.setStatus(1);
			goodsPictureMapper.insert(godGoodsPicture2);
		}
	}

	@Test
	public void addProjectTest() {

		for (int i = 0; i <5000; i++) {
			Project project = new Project();
			project.setProductId(1);
			project.setEnterpriseId(1);
			project.setTitle("牛牛" + i+500);
			project.setAnnualized(0.07F);
			project.setIncreaseAnnualized(0.01F);
			project.setLimitDays(360);
			project.setTotalAmount(20000d);
			project.setCreateDate(new Date());
			project.setUpdateDate(new Date());
			project.setRepaymentMethod(0);
			project.setProjectDescription("很赚钱的养牛项目");
			project.setProjectType(0);
			project.setInvestorsNum(0);
			project.setStatus(0);
			project.setVersion(0);
			project.setTag("新手");
			project.setNoob(1);
			project.setRateCouponDays(0);
			project.setSort(99);
			project.setPlatServiceCharge(0.002500);
			project.setRepayUnit("day");
			project.setSex("1");
			project.setRaiseFee(100d);
			project.setManageFee(160d);
			project.setEarNumber("R" + getFixLengthString(8));
			project.setSafeNumber("B" + getFixLengthString(8));
			project.setInvestedAmount(0.00d);
			projectMapper.insert(project);

			// GoodsPropertyValue propertyValue=new GoodsPropertyValue();
			ProjectPropertyValue projectPropertyValue = new ProjectPropertyValue();
			// 属性值
			projectPropertyValue.setProjectId(project.getId());
			projectPropertyValue.setProductPropertyId(3);
			projectPropertyValue.setPropertyValue("内蒙古");
			projectPropertyValue.setCreateDate(new Date());
			projectPropertyValue.setUpdateDate(new Date());
			projectPropertyValueMapper.insert(projectPropertyValue);
			ProjectPropertyValue projectPropertyValue2 = new ProjectPropertyValue();
			// 属性值
			projectPropertyValue2.setProjectId(project.getId());
			projectPropertyValue2.setProductPropertyId(1);
			projectPropertyValue2.setPropertyValue("安格斯牛");
			projectPropertyValue2.setCreateDate(new Date());
			projectPropertyValue2.setUpdateDate(new Date());
			projectPropertyValueMapper.insert(projectPropertyValue2);
			ProjectPropertyValue projectPropertyValue3 = new ProjectPropertyValue();
			// 属性值
			projectPropertyValue3.setProjectId(project.getId());
			projectPropertyValue3.setProductPropertyId(9);
			projectPropertyValue3.setPropertyValue("健康");
			projectPropertyValue3.setCreateDate(new Date());
			projectPropertyValue3.setUpdateDate(new Date());
			projectPropertyValueMapper.insert(projectPropertyValue3);
			ProjectPropertyValue projectPropertyValue4 = new ProjectPropertyValue();
			// 属性值
			projectPropertyValue4.setProjectId(project.getId());
			projectPropertyValue4.setProductPropertyId(7);
			projectPropertyValue4.setPropertyValue("21kg");
			projectPropertyValue4.setCreateDate(new Date());
			projectPropertyValue4.setUpdateDate(new Date());
			projectPropertyValueMapper.insert(projectPropertyValue4);
			ProjectPropertyValue projectPropertyValue5 = new ProjectPropertyValue();
			// 属性值
			projectPropertyValue5.setProjectId(project.getId());
			projectPropertyValue5.setProductPropertyId(8);
			projectPropertyValue5.setPropertyValue("3年");
			projectPropertyValue5.setCreateDate(new Date());
			projectPropertyValue5.setUpdateDate(new Date());
			projectPropertyValueMapper.insert(projectPropertyValue5);

			// 图片

			ProjectPicture picture = new ProjectPicture();

			picture.setProjectId(project.getId());
			picture.setType(13);
			picture.setName("安格斯牛");
			picture.setUploadId(358);
			picture.setCreateDate(new Date());
			picture.setStatus(0);
			projectPictureMapper.insert(picture);
			ProjectPicture picture2 = new ProjectPicture();

			picture2.setProjectId(project.getId());
			picture2.setType(1);
			picture2.setName("安格斯牛详细图");
			picture2.setUploadId(357);
			picture2.setCreateDate(new Date());
			picture2.setStatus(0);
			projectPictureMapper.insert(picture2);
		}
	}

	public static String getFixLengthString(int strLength) {

		Random rm = new Random();

		// 获得随机数
		double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);

		// 将获得的获得随机数转化为字符串
		String fixLengthString = String.valueOf(pross);

		// 返回固定的长度的随机数
		return fixLengthString.substring(1, strLength + 1);
	}
}
