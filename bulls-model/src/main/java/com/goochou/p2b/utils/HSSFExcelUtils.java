package com.goochou.p2b.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.google.common.collect.Lists;

/**
 * Excel工具类
 */
public class HSSFExcelUtils {
    public static void main(String[] args) throws Throwable {
        String path = "D:/统计报表.xls";
        //表头
        List<String> headers = Lists.newArrayList();
        for (int i = 1; i < 10; i++) {
            headers.add("表头"+i);
        }
        //数据行
        List<Map> datas = new ArrayList<Map>();
        Map m = null;
        for(int i=1;i<10;i++){
            m = new HashMap(); //一行数据集
            for(int j=0;j<headers.size();j++){
                m.put(j, "第"+i+" 行数据："+j);
            }
            datas.add(m);
        }
        ExpExs(path, "统计报表","统计报表",headers,datas);
    }

    /*
     * 通用的Excel文件创建方法
     *   title:首行标题: 2015年度统计报表
     *  sheets:sheet的tab标签页说明: 15年度报表
     * headers:表头：List存放表头  编号、姓名、备注
     *   datas:数据行：list存放实体数据，map存放具体每一行数据，和headers对应。
     *      rs:HttpServletResponse响应作用域，如果不为null，会直接将文件流输出到客户端，下载文件
     */
    public static <T> void ExpExs(String title,LinkedHashMap<String, String> propertyHeaderMap,Collection<T> dataSet,HttpServletResponse rs){
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(title); //+workbook.getNumberOfSheets()

            HSSFRow row;
            HSSFCell cell;

            // 设置这些样式
            HSSFFont font = workbook.createFont();
            font.setFontName(HSSFFont.FONT_ARIAL);//字体
            font.setFontHeightInPoints((short) 16);//字号
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
            //font.setColor(HSSFColor.BLUE.index);//颜色

            HSSFCellStyle cellStyle= workbook.createCellStyle(); //设置单元格样式
            cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER );
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setFont(font);

            //产生表格标题行
            row = sheet.createRow(0);
            row.setHeightInPoints(20);
            int m = 0;
            for (String key : propertyHeaderMap.keySet()) {
            	HSSFRichTextString text = new HSSFRichTextString(propertyHeaderMap.get(key));
            	cell = row.createCell(m);
                cell.setCellValue(text);
                cell.setCellStyle(cellStyle);
                m++;
            }


            cellStyle= workbook.createCellStyle();
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setDataFormat((short)0x31);//设置显示格式，避免点击后变成科学计数法了
            //cellStyle.setWrapText(true);//设置自动换行
            //遍历集合数据，产生数据行
            int index = 0;
            for (T data : dataSet) {
            	index++;
                row=sheet.createRow((index));
                row.setHeightInPoints(20);
                int j=0;
                for (String property : propertyHeaderMap.keySet()) {
                	cell = row.createCell(j);
                    cell.setCellStyle(cellStyle);

                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);

                    try {
                    	Object value = null;
                    	if(data instanceof Map){
                    		value = ((Map) data).get(property);
                    	}else{
                    		// 拼装getter方法名
                            String getMethodName = "get" + property.substring(0, 1).toUpperCase() + property.substring(1);
	                        // 利用反射机制获取dataSet中的属性值，填进cell中
	                        Class<? extends Object> tCls = data.getClass();
	                        Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
	                        value = getMethod.invoke(data, new Object[]{}); // 调用getter从data中获取数据
                    	}
                        if (property.equals("num_id")) {
                            if (value == null) {
                                cell.setCellValue(index);
                                j++;
                                continue;
                            } else {
                                cell.setCellValue(value.toString());
                                j++;
                                continue;
                            }

                        }
                        if (value == null) {
                            value = "";
                        }
                        // 判断值的类型后进行类型转换
                        String textValue = null;
                        if (value instanceof Date) {
                            Date date = (Date) value;
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            textValue = sdf.format(date);
                        } else {
                            // 其它数据类型都当作字符串简单处理
                            textValue = value.toString();
                        }
                        if (textValue != null) {
                            Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                            Matcher matcher = p.matcher(textValue);
                            if (matcher.matches()) {
                                // 是数字当作double处理
                                cell.setCellValue(Double.parseDouble(textValue));
                            } else {
                            	HSSFRichTextString richString = new HSSFRichTextString(textValue);
                                cell.setCellValue(richString);
                            }
                        }

                        HSSFRichTextString richString = new HSSFRichTextString(textValue);
                        cell.setCellValue(richString);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    j++;
                }
            }

            int k = 0;
            for (String key : propertyHeaderMap.keySet()) {
            	sheet.autoSizeColumn((short)k);
                k++;
            }

            rs.reset();
            rs.setContentType("multipart/form-data"); //自动识别
            rs.setHeader("Content-Disposition","attachment;filename="+new String(title.getBytes("GBK"),"iso-8859-1")+".xls");
            //文件流输出到rs里
            workbook.write(rs.getOutputStream());
            rs.getOutputStream().flush();
            rs.getOutputStream().close();
        } catch (Exception e) {
            System.out.println("#Error ["+e.getMessage()+"] ");
        }
    }


    /*
     * 通用的Excel文件创建方法
     *    path:保存路径: C:/xls/统计报表.xls
     *   title:首行标题: 2015年度统计报表
     *  sheets:sheet的tab标签页说明: 15年度报表
     * headers:表头：List存放表头  编号、姓名、备注
     *   datas:数据行：list存放实体数据，map存放具体每一行数据，和headers对应。
     */
    public static void ExpExs(String path,String title,String sheets,List headers,List<Map> datas){
        try {
            if(sheets== null || "".equals(sheets)){ sheets = "sheet"; }

            boolean isExist = new File(path).exists();
            if(!isExist){
                HSSFWorkbook workbook = new HSSFWorkbook();
                HSSFSheet sheet = workbook.createSheet(sheets);

                FileOutputStream out = new FileOutputStream(new File(path));
                workbook.write(out);
                out.flush();
                out.close();
            }
            FileInputStream file = new FileInputStream(new File(path));
            HSSFWorkbook workbook = new HSSFWorkbook(file);

            HSSFSheet sheet = null;
            if(!isExist){
                sheet = workbook.getSheetAt(0);
            }else{
                if(workbook.getSheet(sheets) == null){
                    sheet = workbook.createSheet(sheets); //+workbook.getNumberOfSheets()
                }else{
                    System.out.println("文件：["+path+"] ["+sheets+"] 已经存在...");
                    System.out.println("");
                    return;
                }
            }
            HSSFRow row;
            HSSFCell cell;

            // 设置这些样式
            HSSFFont font = workbook.createFont();
            font.setFontName(HSSFFont.FONT_ARIAL);//字体
            font.setFontHeightInPoints((short) 16);//字号
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
            //font.setColor(HSSFColor.BLUE.index);//颜色

            HSSFCellStyle cellStyle= workbook.createCellStyle(); //设置单元格样式
            cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER );
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setFont(font);

            //产生表格标题行
            row = sheet.createRow(0);
            row.setHeightInPoints(20);
            for (int i = 0; i < headers.size(); i++) {
                HSSFRichTextString text = new HSSFRichTextString(headers.get(i).toString());
                cell = row.createCell(i);
                cell.setCellValue(text);
                cell.setCellStyle(cellStyle);
            }


            cellStyle= workbook.createCellStyle();
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setDataFormat((short)0x31);//设置显示格式，避免点击后变成科学计数法了
            //cellStyle.setWrapText(true);//设置自动换行
            Map map;
            //遍历集合数据，产生数据行
            for (int i=0; i <datas.size(); i++) {
                row=sheet.createRow((i+1));
                row.setHeightInPoints(20);
                map = datas.get(i);

                for(int j=0;j<map.size();j++) {
                     cell = row.createCell(j);
                     cell.setCellStyle(cellStyle);

                     cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                     if(map.get(j) != null) {
                         cell.setCellValue(new HSSFRichTextString(map.get(j).toString()));
                     }else{
                         cell.setCellValue(new HSSFRichTextString(""));
                    }
                }
            }

            for (int i = 0; i < headers.size(); i++) {
                sheet.autoSizeColumn((short)i);
            }

            FileOutputStream out = new FileOutputStream(new File(path));
            workbook.write(out);
            out.flush();
            out.close();

            /*
            HSSFRow row = sheet.createRow(sheets);
            HSSFCell cell = null;
            cell=row.createCell(sheets);
            cell.setCellValue(new HSSFRichTextString("-["+sheets+"]-"));
            sheets=sheets+2;//中间空一行
            row=sheet.createRow(sheets);
            */

        } catch (Exception e) {
            System.out.println("#Error ["+e.getMessage()+"] ");
        }
        System.out.println("文件：["+path+"] ["+sheets+"] 创建成功...");
        System.out.println("");
    }
}
