package com.goochou.p2b.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class CommonUtil {

	public static final String CHARSET = "UTF-8";
	
	/**
	 * 数据格式化
	 */
	private static DecimalFormat df = new DecimalFormat("#.##");

	/**
	 * 获取客户端真是IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		// ipAddress = this.getRequest().getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	/**
	 * SQL注入检测
	 * 
	 * @param str
	 * @return
	 */
	public static boolean sqlValidate(String str) {
		str = str.toLowerCase();// 统一转为小写
		String reg = "^[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}$";
        if(!str.matches(reg)){
    		String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|chr|mid|master|truncate|"
    				+ "char|declare|sitename|net user|xp_cmdshell|or|like'|and|exec|execute|insert|create|drop|"
    				+ "table|from|grant|use|group_concat|column_name|"
    				+ "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|"
    				+ "chr|mid|master|truncate|char|declare|or|like";// 过滤掉的sql关键字，可以手动添加
    		String[] badStrs = badStr.split("\\|");
    		for (int i = 0; i < badStrs.length; i++) {
    			if (str.equals(badStrs[i])) {
    				return true;
    			}
    		}
    	}
        return false;
	}

	/**
	 * 
	 * 获得保留小数点后两位有效数字的字符串
	 * 
	 * @param doubleValue
	 * @return xx.xx
	 */
	public static String getFormatDouble(String doubleValue) {
		double b = Double.valueOf(doubleValue);
		String result = df.format(b);
		return result;
	}

	public static boolean compareDate(String date1, String date2) {
		boolean flag = false;
		try {
			SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
			java.util.Calendar calen1 = java.util.Calendar.getInstance();
			java.util.Calendar calen2 = java.util.Calendar.getInstance();
			calen1.setTime(sd.parse(date1));
			calen2.setTime(sd.parse(date2));
			int result = calen1.compareTo(calen2);
			if (result > 0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 标题中含有特殊字符替换 如:●▲@◎※ 主要在标题中使用
	 * 
	 * @param Content
	 * @return
	 */
	public static String replaceSign(String content) {
		String strContent = "";
		strContent = content.replaceAll("\\*", "");
		strContent = strContent.replaceAll("\\$", "");
		strContent = strContent.replaceAll("\\+", "");
		String arrStr[] = { ":", "：", "●", "▲", "■", "@", "＠", "◎", "★", "※",
				"＃", "〓", "＼", "§", "☆", "○", "◇", "◆", "□", "△", "＆", "＾",
				"￣", "＿", "♂", "♀", "Ю", "┭", "①", "「", "」", "≮", "§", "￡",
				"∑", "『", "』", "⊙", "∷", "Θ", "の", "↓", "↑", "Ф", "~", "Ⅱ",
				"∈", "┣", "┫", "╋", "┇", "┋", "→", "←", "!", "Ж", "#", "<", ">" };
		for (int i = 0; i < arrStr.length; i++) {
			if ((strContent.indexOf(arrStr[i])) >= 0) {
				strContent = strContent.replaceAll(arrStr[i], "");
			}
		}

		return strContent;
	}

	public static String StringFilter(String str) throws PatternSyntaxException {
		// 清除掉所有特殊字符
		String result = "";
		String regEx = "[|&;$%@<>()+,]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		result = m.replaceAll("").trim();
		result = result.replaceAll("CR", "");
		result = result.replaceAll("LF", "");
		result = result.replaceAll("'", "");
		result = result.replaceAll("\"", "");
		result = result.replaceAll("\'", "");
		result = result.replaceAll("\\\"", "");
		result = result.replaceAll("\\\\", "");
		return result;
	}

	/**
	 * 验证是否含有特殊字符
	 * 
	 * @param str
	 *            验证字符串
	 * @return
	 */
	public static boolean checkTS(String str) {
		// 过滤的特殊字符
		String tsStr = "<>＞＜‘“＼％";
		int num = 0;
		str = str.trim();
		if (str != null && str.length() > 0) {
			for (int i = 0; i < str.length(); i++) {
				num = tsStr.indexOf(str.charAt(i));
				if (num != -1) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 判断特殊字符
	 * 
	 * @param input
	 * @return
	 */
	public static boolean hasSpecialChars(String inputStr) {
		boolean flag = false;
		if ((inputStr != null) && (inputStr.length() > 0)) {
			char c;
			for (int i = 0; i <= inputStr.length() - 1; i++) {
				c = inputStr.charAt(i);
				switch (c) {
				case '>':
					flag = true;
					break;
				case '<':
					flag = true;
					break;
				case '"':
					flag = true;
					break;
				case '&':
					flag = true;
					break;
				case '|':
					flag = true;
					break;
				case ';':
					flag = true;
					break;
				// case ',': flag = true; break;
				case '$':
					flag = true;
					break;
				case '%':
					flag = true;
					break;
				case '\\':
					flag = true;
					break;
				case '(':
					flag = true;
					break;
				case ')':
					flag = true;
					break;
				case '+':
					flag = true;
					break;
				case '?':
					flag = true;
					break;
				case 0x0d:
					flag = true;
					break;
				case 0x0a:
					flag = true;
					break;
				}
			}
		}
		return flag;
	}


	/**
	 *  Created on 2014年12月23日 
	 * <p>Discription:[区分Windows还是linux操作系统]</p>
	 * @author:[刘春伟]
	 * @update:[日期2014年12月23日] [刘春伟]
	 * @return boolean .
	 */
	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}

	/**
	 *  Created on 2014年12月23日 
	 * <p>Discription:[获取本机ip地址，并自动区分Windows还是linux操作系统]</p>
	 * @author:[刘春伟]
	 * @update:[日期2014年12月23日] [刘春伟]
	 * @return String .
	 */
	public static String getLocalIP() {
		String sIP = "";
		InetAddress ip = null;
		try {
			// 如果是Windows操作系统
			if (isWindowsOS()) {
				ip = InetAddress.getLocalHost();
			}
			// 如果是Linux操作系统
			else {
				boolean bFindIP = false;
				Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
						.getNetworkInterfaces();
				while (netInterfaces.hasMoreElements()) {
					if (bFindIP) {
						break;
					}
					NetworkInterface ni = (NetworkInterface) netInterfaces
							.nextElement();
					// ----------特定情况，可以考虑用ni.getName判断
					// 遍历所有ip
					Enumeration<InetAddress> ips = ni.getInetAddresses();
					while (ips.hasMoreElements()) {
						ip = (InetAddress) ips.nextElement();
						if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
								&& ip.getHostAddress().indexOf(":") == -1) {
							bFindIP = true;
							break;
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null != ip) {
			sIP = ip.getHostAddress();
		}
		return sIP;
	}
	
	/**
	 * 
	 *  Created on 2014年12月11日 
	 * <p>Discription:[判断list是否有值]</p>
	 * @author:[杨龙平]
	 * @update:[2014年12月11日] [杨龙平]
	 * @return boolean .
	 */
	@SuppressWarnings("rawtypes")
	public final static boolean checkListIsValid(List list) {
		if (list == null)
			return false;
		if (list.size() == 0 || list.size() < 0)
			return false;
		return true;
	}
	/**
	 *  Created on 2015年1月6日 
	 * <p>Discription:[将文件以流的形式变成字符串 ]</p>
	 * @author:[刘春伟]
	 * @update:[日期2015年1月6日] [刘春伟]
	 * @return String .
	 */
	public static  String readFile(String path) throws IOException {
		File file = new File(path);
		StringBuilder sb = null;
		if (file.isFile()) {
			BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			String content = "";
			sb = new StringBuilder();
			while (content != null) {
				content = bf.readLine();
				if (content == null) {
					break;
				}
				sb.append(content+"\n");
			}
			bf.close();
		}
		return sb.toString();
	}	
	/**
	 *  Created on 2015年1月6日 
	 * Discription:[创建文件夹]
	 * @author:[刘春伟]
	 * @update:[日期2015年1月6日] [刘春伟]
	 * @return boolean .
	 */
	public static boolean createDirs(String path,String fileName) {
		File aFile = new File(path);
		boolean success = true;
		if (!aFile.exists()) {
			success = aFile.mkdirs();
		}
		if(StringUtils.isNotEmpty(fileName) && success){
			//如果文件存在，先删除文件
			 aFile = new File(aFile,fileName);
			 if(aFile.exists()){
				 aFile.delete();
			 }
			try {
				success = aFile.createNewFile();
			} catch (IOException e) {
				success = false;
			}
		}
		return success;
	}
	/**
	 *  Created on 2015年1月12日 
	 * <p>Discription:[删除单独某个文件]</p>
	 * @author:[刘春伟]
	 * @update:[日期2015年1月12日] [刘春伟]
	 * @return boolean .
	 */
	public static boolean deleteFiles(String filePathName){
		boolean success = false;
		File file = new File(filePathName);
		if(file.isFile() && file.exists()){
			try {
				success = file.delete();
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		return success;
	}

   /**
     * @param dir 将要删除的文件目录
     * 
    *  Created on 2015年1月13日 
    * <p>Discription:[递归删除目录下的所有文件及子目录下所有文件]</p>
    * @author:[刘春伟]
    * @update:[日期2015年1月13日] [刘春伟]
    * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
    */
	  private static boolean deleteDir(File dir) {
	        if (dir.isDirectory()) {
	        	 String[] children = dir.list();
	        	//递归删除目录中的子目录下
	        	for (int i=0; i<children.length; i++) {
	                boolean success = deleteDir(new File(dir, children[i]));
	                if (!success) {
	                    return false;
	                }
	            }
	        }
	        // 目录此时为空，可以删除
	        return dir.delete();
	    }
	  
		/**
		 * 
		 *  Created on 2015-3-4 
		 * <p>Discription:[判断字符是否为数字的非空字符串]</p>
		 * @author:[杨龙平]
		 * @update:[日期yyyy-MM-dd] [杨龙平]
		 * @return boolean .
		 */
		public static boolean isNotNullAndNotNum(String str){
			if(StringUtils.isNotEmpty(str)){
				if(StringUtils.isNumeric(str)){
					return true;
				}
			}
			return false;
		}
		
		/**
		 * 
		 *  Created on 2015-12-9 
		 * <p>Discription:[根据list对象随机获取该list对象里面的一个对象或值]</p>
		 * @author:[杨龙平]
		 * @update:[日期2015-12-9] [杨龙平]
		 * @return Object .
		 */
	    public static Object randOneObjByList(List list) {
	    	Object obj = null;
	    	if(checkListIsValid(list)){
	    		Random rand = new Random();
	    		Integer i = Math.abs(rand.nextInt())%list.size();
	    		obj = list.get(i);
	    	}
	    	return obj;
		}
	    
	    public static <T> List<List<T>> splitList(List<T> list, int pageSize) {

			int listSize = list.size();
			int page = (listSize + (pageSize - 1)) / pageSize;

			List<List<T>> listArray = new ArrayList<List<T>>();
			for (int i = 0; i < page; i++) {
				List<T> subList = new ArrayList<T>();
				for (int j = 0; j < listSize; j++) {
					int pageIndex = ((j + 1) + (pageSize - 1)) / pageSize;
					if (pageIndex == (i + 1)) {
						subList.add(list.get(j));
					}
					if ((j + 1) == ((j + 1) * pageSize)) {
						break;
					}
				}
				listArray.add(subList);
			}
			return listArray;
		}
	    
	    
	    /**
		 * 
		 *  Created on 2017-12-22 
		 * <p>Discription:[将20以内的阿拉伯数字转成中文大写数字]</p>
		 * @author:[罗娜]
		 * @update:[日期2017-12-22] [罗娜]
		 * @return String .
		 */
	    public static String ChineseNumber(int num) {
	    	String[] numeric = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
	        StringBuilder builder = new StringBuilder();
	        builder.append(numeric[num / 1000] + "千").
	                append(numeric[num / 100 % 10] + "百").
	                append(numeric[num / 10 % 10] + "十").
	                append(numeric[num % 10]);
	        //去掉了零千....
	        int index = -1;
	        while ((index = builder.indexOf(numeric[0], index + 1)) != -1) {
	            if (index < builder.length() - 1) {
	                builder.deleteCharAt(index + 1);
	            }
	        }
	        //去掉双零
	        index = 0;
	        while ((index = builder.indexOf("零零", index)) != -1) {
	            builder.deleteCharAt(index);
	        }

	        if (builder.length() > 1) {
	            //去掉开头的零
	            if (builder.indexOf(numeric[0]) == 0) {
	                builder.deleteCharAt(0);
	            }
	            //去掉末尾的零
	            if (builder.indexOf(numeric[0]) == builder.length() - 1) {
	                builder.deleteCharAt(builder.length() - 1);
	            }

	        }
	        //把开头一十换成十
	        if (builder.indexOf("一十") == 0) {
	            builder.deleteCharAt(0);
	        }
	        return builder.toString();
	    
		}
	    
	    /**
	     * double金额改大写
	     * @author sxy
	     * @param amount
	     * @return
	     */
	    public static String moneyToChinese(double amount) {
	        String[] yuan = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖",
	                "拾", "佰", "仟", "万", "亿", "圆", "角", "分", "负" };
	 
	        NumberFormat format = NumberFormat.getInstance(Locale.CHINA);
	        format.setMaximumFractionDigits(2);
	        format.setMinimumFractionDigits(2);
	        format.setGroupingUsed(false);
	 
	        String money = format.format(amount);
	        
	        // 记录符号
	        String flag = null;
	        if (money.indexOf('-') == 0) {
	            flag = yuan[18];
	            money = money.substring(1);
	        }
	        
	        // 最大小数点前24位
	        if(money.length() > 27){
	            return "超出有效范围";
	        }
	 
	        StringBuilder strb = new StringBuilder();
	 
	        char[] chars = money.toCharArray();
	        int len = chars.length;
	        for (int i = len - 1; i >= 0; i--) {
	            String temp = null;
	            switch (chars[i]) {
	            case '0':
	                temp = yuan[0];
	                break;
	            case '1':
	                temp = yuan[1];
	                break;
	            case '2':
	                temp = yuan[2];
	                break;
	            case '3':
	                temp = yuan[3];
	                break;
	            case '4':
	                temp = yuan[4];
	                break;
	            case '5':
	                temp = yuan[5];
	                break;
	            case '6':
	                temp = yuan[6];
	                break;
	            case '7':
	                temp = yuan[7];
	                break;
	            case '8':
	                temp = yuan[8];
	                break;
	            case '9':
	                temp = yuan[9];
	                break;
	            case '.':
	                temp = yuan[15];
	                break;
	            }
	 
	            switch (len - 1 - i) {
	            case 0:
	                temp += yuan[17];
	                break;
	            case 1:
	                temp += yuan[16];
	                break;
	            case 4:
	            case 8:
	            case 12:
	            case 16:
	            case 20:
	            case 24:
	                temp += yuan[10];
	                break;
	            case 5:
	            case 9:
	            case 13:
	            case 17:
	            case 21:
	            case 25:
	                temp += yuan[11];
	                break;
	            case 6:
	            case 10:
	            case 14:
	            case 18:
	            case 22:
	            case 26:
	                temp += yuan[12];
	                break;
	            case 7:
	            case 15:
	            case 23:
	                temp += yuan[13];
	                break;
	            case 11:
	            case 19:
	                temp += yuan[14];
	                break;
	            }
	 
	            strb.insert(0, temp);
	        }
	 
	        // 插入符号位
	        if (null != flag) {
	            strb.insert(0, flag);
	        }
	 
	        String result = strb.toString();
	        result = result.replaceAll("零拾", "零");
	        result = result.replaceAll("零佰", "零");
	        result = result.replaceAll("零仟", "零");
	        result = result.replaceAll("零零零", "零");
	        result = result.replaceAll("零零", "零");
	        result = result.replaceAll("零角零分", "整");
	        result = result.replaceAll("零分", "整");
	        result = result.replaceAll("零角", "零");
	        result = result.replaceAll("零亿零万零圆", "亿圆");
	        result = result.replaceAll("亿零万零圆", "亿圆");
	        result = result.replaceAll("零亿零万", "亿");
	        result = result.replaceAll("零万零圆", "万圆");
	        result = result.replaceAll("零亿", "亿");
	        result = result.replaceAll("零万", "万");
	        result = result.replaceAll("零圆", "圆");
	        result = result.replaceAll("零零", "零");
	 
	        return result;
	    }
	    
	    /**
	     * 请求url并返回数据
	     * @author sxy
	     * @param urlStr
	     * @return
	     */
	    public static String getURLContent(String urlStr) {               
	        //请求的url 
	        URL url = null;      
	        //请求的输入流
	        BufferedReader in = null;   
	        //输入流的缓冲
	        StringBuffer sb = new StringBuffer(); 
	        try{
	            url = new URL(urlStr);     
	            in = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8") ); 
	            String str = null;  
	            //一行一行进行读入
	            while((str = in.readLine()) != null) {
	                sb.append( str );     
	            }     
	        } catch (Exception ex) {   
	                        
	        } finally{    
	            try{
	                if(in!=null) {
	                    in.close(); //关闭流    
	                }     
	            }catch(IOException ex) {      
	                    
	            }     
	        }     
	       String result =sb.toString();     
	       return result;    
	    }
	    
	public static void main(String args[]) {
		/*System.out.println(CommonUtil.hasSpecialChars("的身份的身份是发的fdfdfd"));
		System.out.println(sqlValidate("likes"));
		System.out.println(CommonUtil.getLocalIP());*/
		
		//测试随机获取list对象
		/*List<String> list = new ArrayList<String>();
    	list.add("abcd1");
    	list.add("12342");
    	list.add("sdfg3");
    	list.add("cvbn4");
    	String str = (String) randOneObjByList(list);
    	System.out.println(str);*/
		
//		System.out.println(ChineseNumber(25));
//	    System.out.println(moneyToChinese(1111111101.11));
	}
}
