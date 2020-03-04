package com.goochou.p2b.constant.project;

import java.util.Random;

/**
 * 牛只体重范围
 * 
 * @ClassName: WeightEnum
 * @author zj
 * @date 2019-07-16 16:31
 */
public enum ProjectWeightEnum {

	FARM1("20KG-100KG 牛崽", 96, "100KG-300KG 青年", 116, "300KG-500KG 成年", 50, "500KG以上 育肥牛", 146),
	FARM2("20KG-100KG 牛崽", 81, "100KG-300KG 青年", 172, "300KG-500KG 成年", 52, "500KG以上 育肥牛", 106),
	FARM3("20KG-100KG 牛崽", 106, "100KG-300KG 青年", 64, "300KG-500KG 成年", 153, "500KG以上 育肥牛", 26),
	FARM4("20KG-100KG 牛崽", 167, "100KG-300KG 青年", 152, "300KG-500KG 成年", 84, "500KG以上 育肥牛", 145);

	/**
	 * @Title: ProjectWeightEnum
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param desc20to100
	 * @param desc100to300
	 * @param desc300to500
	 * @param desc500
	 * @param count20to100
	 * @param count100to300
	 * @param count30to500
	 * @param count500
	 * @author zj
	 * @date 2019-07-22 10:58
	 */
	private ProjectWeightEnum(String desc20to100, int count20to100, String desc100to300, int count100to300, String desc300to500, int count300to500,
			String desc500, int count500) {
		this.desc20to100 = desc20to100;
		this.desc100to300 = desc100to300;
		this.desc300to500 = desc300to500;
		this.desc500 = desc500;
		this.count20to100 = count20to100;
		this.count100to300 = count100to300;
		this.count300to500 = count300to500;
		this.count500 = count500;
	}

	public String desc20to100;
	public String desc100to300;
	public String desc300to500;
	public String desc500;

	private int count20to100;
	private int count100to300;
	private int count300to500;
	private int count500;

	public String getDesc20to100() {
		return desc20to100;
	}

	public void setDesc20to100(String desc20to100) {
		this.desc20to100 = desc20to100;
	}

	public String getDesc100to300() {
		return desc100to300;
	}

	public void setDesc100to300(String desc100to300) {
		this.desc100to300 = desc100to300;
	}

	public String getDesc300to500() {
		return desc300to500;
	}

	public void setDesc300to500(String desc300to500) {
		this.desc300to500 = desc300to500;
	}

	public String getDesc500() {
		return desc500;
	}

	public void setDesc500(String desc500) {
		this.desc500 = desc500;
	}

	public int getCount20to100() {
		return count20to100;
	}

	public void setCount20to100(int count20to100) {
		this.count20to100 = count20to100;
	}

	public int getCount100to300() {
		return count100to300;
	}

	public void setCount100to300(int count100to300) {
		this.count100to300 = count100to300;
	}

	public int getCount300to500() {
		return count300to500;
	}

	public void setCount30to500(int count300to500) {
		this.count300to500 = count300to500;
	}

	public int getCount500() {
		return count500;
	}

	public void setCount500(int count500) {
		this.count500 = count500;
	}

	public static void main(String[] args) {
		Random random = new Random();
		for (int i = 0; i < 25; i++) {
			int m = random.nextInt(200);
			System.out.println(m + "===============================");
		}

	}
}
