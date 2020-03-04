package com.goochou.p2b.utils;

import com.goochou.p2b.model.LotteryGift;

import java.util.*;
 
/**
 * 不同概率抽奖工具包
 *
 * @author Shunli
 */
public class LotteryUtil {
	
   /*public static void main(String[] args) {





	   List<LotteryGift> list=new ArrayList<LotteryGift>();
	   LotteryGift g1=new LotteryGift();
	   g1.setId(11);
	   g1.setName("0.2%月加息券");
	   g1.setRate(0.3);
	   list.add(g1);
	   LotteryGift g2=new LotteryGift();
	   g2.setId(12);
	   g2.setName("0.1%月加息券");
	   g2.setRate(0.3);
	   list.add(g2);
	   LotteryGift g3=new LotteryGift();
	   g3.setId(13);
	   g3.setName("0.5%日加息券");
	   g3.setRate(0.6);
	   list.add(g3);
	   LotteryGift g4=new LotteryGift();
	   g4.setId(14);
	   g4.setName("随机体验金");
	   g4.setRate(0.7);
	   list.add(g4);
	   LotteryGift g5=new LotteryGift();
	   g5.setId(15);
	   g5.setName("全民理财polo衫");
	   g5.setRate(0.015);
	   list.add(g5);
	   LotteryGift g6=new LotteryGift();
	   g6.setId(16);
	   g6.setName("精美遮阳伞");
	   g6.setRate(0.00015);
	   list.add(g6);
	   LotteryGift g7=new LotteryGift();
	   g7.setId(17);
	   g7.setName("运动水杯");
	   g7.setRate(0.015);
	   list.add(g7);
	   LotteryGift g8=new LotteryGift();
	   g8.setId(18);
	   g8.setName("小米充电宝");
	   g8.setRate(0.001);
	   list.add(g8);
	   LotteryGift g9=new LotteryGift();
	   g9.setId(19);
	   g9.setName("亚马逊kindle");
	   g9.setRate(0.0005);
	   list.add(g9);
	   LotteryGift g10=new LotteryGift();
	   g10.setId(20);
	   g10.setName("高档无人机");
	   g10.setRate(0.0001);
	   list.add(g10);
	   int a1=0,a2=0,a3=0,a4=0,a5=0,a6=0,a7=0,a8=0,a9=0,a10=0;
	   for (int i = 0; i < 6000; i++) {
		   LotteryGift g=startLottery(list);
           System.out.println("奖品名称"+g.getName()+"-----概率----"+g.getRate()+"奖品id"+g.getId()+"奖品剩余数量"+g.getLeftNum());
		   if(g.getId()==11){
			   a1++;
		   }else if (g.getId()==12){
			   a2++;
		   }else if (g.getId()==13){
			   a3++;
		   }else if (g.getId()==14){
			   a4++;
		   }else if (g.getId()==15){
			   a5++;
		   }else if (g.getId()==16){
			   a6++;
		   }else if (g.getId()==17){
			   a7++;
		   }else if (g.getId()==18){
			   a8++;
		   }else if (g.getId()==19){
			   a9++;
		   }else if (g.getId()==20){
			   a10++;
		   }
	   }
	   System.out.println(a1+"<数量--------------0.2%月加息券"+"-----概率----"+g1.getRate());
	   System.out.println(a2+"<数量--------------0.1%月加息券"+"-----概率----"+g2.getRate());
	   System.out.println(a3+"<数量--------------0.5%日加息券"+"-----概率----"+g3.getRate());
	   System.out.println(a4+"<数量--------------随机体验金"+"-----概率----"+g4.getRate());
	   System.out.println(a5+"<数量--------------全民理财polo衫"+"-----概率----"+g5.getRate());
	   System.out.println(a6+"<数量--------------精美遮阳伞"+"-----概率----"+g6.getRate());
	   System.out.println(a7+"<数量--------------运动水杯"+"-----概率----"+g7.getRate());
	   System.out.println(a8+"<数量--------------小米充电宝"+"-----概率----"+g8.getRate());
	   System.out.println(a9+"<数量--------------亚马逊kindle"+"-----概率----"+g9.getRate());
	   System.out.println(a10+"<数量--------------高档无人机"+"-----概率----"+g10.getRate());*/

//	   List<String> li=generateRandom(2);
//	   for (String string : li) {
//		System.out.println(string);
//	   }
//		int[] cba={159,138,137,139,158,177,131,132,133,134,135,136,137,150,151,152,157};
//		String[] abc={"华为荣耀手环","小米手环","小米充电宝","马克杯"};
//		String string="";
//		for (int i = 0; i < 100; i++) {
//			if(i%6==0){
//				string="恭喜\""+cba[(int)(Math.random()*6)]+"****"+(int)(Math.random()*9000+1000)+"\"用户"+"抽中\""+abc[(int)(Math.random()*4)]+"\"  一个    ";
//				System.out.println(string);
//			}
//		}
	   //System.out.print(random.nextInt(6)+"\t");
	   //System.out.println((int)(Math.random()*9000+1000));
   //}

    /**
     * 返回一个固定电话号码段 不同随机奖品的字符串
     * @author 王信
     * @Create Date: 2015年12月11日上午11:29:36
     * @return
     */
    public static String lotteryString(List<Map<String,Object>> list){//159,138,137,139,158,177   
        String sb="";
        for (Map<String, Object> map : list) {
        	Object phone = map.get("phone");
        	Object name = map.get("name");
        	if(phone == null || name == null){
        		continue;
        	}
        	String s=phone.toString();
        	String str = s.substring(0, 3)+"****"+s.substring(7, 11);
        	String ss=name.toString();
			sb = sb + "恭喜\"" + str + "\"用户" + "抽中\"" + ss + "\"          ";
			int len = 4;
			List<String> string = generateRandom(len);
			int i = new Random().nextInt(list.size());
			if (i % 9 == 0) {
				sb = sb + string.get((int) (Math.random() * 4));
			}

		}
    	return sb;
    }
    
    public static List<String>  generateRandom(int count){//随机字符串
    	String sb="";
    	List<String> list =new ArrayList<String>();
    	int[] cba={159,138,137,139,158,177,131,132,133,134,135,136,137,150,151,152,157};
		List<String> goods=new ArrayList<String>(){{add("小米充电宝");add("高档无人机");add("运动水杯");add("亚马逊kindle");}};
		for (int i = 0; i < count; i++) {
			int random = (int)(Math.random()*goods.size());
			sb = "恭喜\"" + cba[(int) (Math.random() * 16)] + "****" + (int) (Math.random() * 9000 + 1000) + "\"用户" + "抽中\"" + goods.get(random) + "\"";
			goods.remove(random);
			list.add(sb);
		}
		return list;
    }
    
    /**
     * 返回抽奖的奖品
     * @author 王信
     * @Create Date: 2015年12月4日上午11:28:22
     * @param list
     * @return
     */
    public static LotteryGift startLottery(List<LotteryGift> list){
    	List<Double> orignalRates = new ArrayList<Double>(list.size());
    	for (LotteryGift gift : list) {
			double rate=gift.getRate();
            if (rate < 0) {
            	rate = 0;
            }
            orignalRates.add(rate);
		}
    	return list.get(lottery(orignalRates));
    }
	
    /**
     * 抽奖,返回物品的索引
     *
     * @param orignalRates
     *            原始的概率列表，保证顺序和实际物品对应
     * @return
     *         物品的索引
     */
    public static int lottery(List<Double> orignalRates) {
        if (orignalRates == null || orignalRates.isEmpty()) {
            return -1;
        }
 
        int size = orignalRates.size();
 
        // 计算总概率，这样可以保证不一定总概率是1
        double sumRate = 0d;
        for (double rate : orignalRates) {
            sumRate += rate;
        }
 
        // 计算每个物品在总概率的基础下的概率情况
        List<Double> sortOrignalRates = new ArrayList<Double>(size);
        Double tempSumRate = 0d;
        for (double rate : orignalRates) {
            tempSumRate += rate;
            sortOrignalRates.add(tempSumRate / sumRate);
        }
 
        // 根据区块值来获取抽取到的物品索引
        double nextDouble = Math.random();
        sortOrignalRates.add(nextDouble);
        Collections.sort(sortOrignalRates);
 
        return sortOrignalRates.indexOf(nextDouble);
    }	
   
}