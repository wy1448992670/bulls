package com.goochou.p2b.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@SuppressWarnings("restriction")
public class WaterMarkGenerate {
    private static final String FONT_FAMILY="微软雅黑";//字体
    private static final int FONT_STYLE=Font.BOLD;//字体加粗
    private static final int FONT_SIZE=24;//字体大小
    private static final float ALPHA=0.4F;//水印透明度

    private static final int LOGO_WIDTH=100;//图片水印大小

    //添加文字水印
    /*tarPath:图片保存路径
     *contents:文字水印内容* */
    public static void generateWithTextMark(File srcFile,
            String tarPath,String contents) throws Exception{
        Image srcImage=ImageIO.read(srcFile);
        int width=srcImage.getWidth(null);
        int height=srcImage.getHeight(null);

        BufferedImage tarBuffImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g=tarBuffImage.createGraphics();
        g.drawImage(srcImage, 0, 0, width,height,null);

        //计算
        int strWidth=FONT_SIZE*getTextLength(contents);
        int strHeight=FONT_SIZE;

        //水印位置
//      int x=width-strWidth;
//      int y=height-strHeight;

        int x=0,y=0;

        //设置字体和水印透明度
        g.setFont(new Font(FONT_FAMILY,FONT_STYLE,FONT_SIZE));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,ALPHA));
        g.setColor(new Color(140,209,255));
//      g.drawString(contents, x, y);
        //旋转图片
        g.rotate(Math.toRadians(-30),width/2,height/2);
        while(x < width*1.5){
            y = -height/2;
            while(y < height*1.5){
                g.drawString(contents,x,y);
                y += strHeight + 50;
            }
            x += strWidth + 100; //水印之间的间隔设为100
        }
        g.dispose();

        JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(new FileOutputStream(tarPath));
        en.encode(tarBuffImage);
    }
    public static void generateWithTextMark(InputStream input,
            String tarPath,String contents) throws Exception{
        Image srcImage= ImageIO.read(input);
        int width=srcImage.getWidth(null);
        int height=srcImage.getHeight(null);

        BufferedImage tarBuffImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g=tarBuffImage.createGraphics();
        g.drawImage(srcImage, 0, 0, width,height,null);

        //计算
        int strWidth=FONT_SIZE*getTextLength(contents);
        int strHeight=FONT_SIZE;

        //水印位置
//      int x=width-strWidth;
//      int y=height-strHeight;

        int x=0,y=0;

        //设置字体和水印透明度
        g.setFont(new Font(FONT_FAMILY,FONT_STYLE,FONT_SIZE));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,ALPHA));
        g.setColor(new Color(140,209,255));
//      g.drawString(contents, x, y);
        //旋转图片
        g.rotate(Math.toRadians(-30),width/2,height/2);
        while(x < width*1.5){
            y = -height/2;
            while(y < height*1.5){
                g.drawString(contents,x,y);
                y += strHeight + 50;
            }
            x += strWidth + 100; //水印之间的间隔设为100
        }
        g.dispose();
        FileOutputStream out =new FileOutputStream(tarPath);
        JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(out);
        en.encode(tarBuffImage);
        out.close();
    }
    //添加图片水印
    /*
     * tarPath:图片保存路径
     * logoPath:logo文件路径
     * */
    public static void generateWithImageMark(File srcFile,
            String tarPath,String logoPath) throws Exception{
        Image srcImage=ImageIO.read(srcFile);
        int width=srcImage.getWidth(null);
        int height=srcImage.getHeight(null);

        BufferedImage tarBuffImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g=tarBuffImage.createGraphics();
        g.drawImage(srcImage, 0, 0, width,height,null);

        Image logoImage=ImageIO.read(new File(logoPath));
        int logoWidth=LOGO_WIDTH;
        int logoHeight=(LOGO_WIDTH*logoImage.getHeight(null))/logoImage.getWidth(null);

        int x=width-logoWidth;
        int y=height-logoHeight;

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,ALPHA));
        g.drawImage(logoImage, x, y, logoWidth, logoHeight, null);
        g.dispose();
        FileOutputStream out =new FileOutputStream(tarPath);
        JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(out);
        en.encode(tarBuffImage);
        out.close();
    }
    //文本长度的处理：文字水印的中英文字符的宽度转换
    public static int getTextLength(String text){
        int length = text.length();
        for(int i=0;i<text.length();i++){
            String s = String.valueOf(text.charAt(i));
            if(s.getBytes().length>1){  //中文字符
                length++;
            }
        }
        length = length%2 == 0?length/2:length/2+1;  //中文和英文字符的转换
        return length;
    }

}