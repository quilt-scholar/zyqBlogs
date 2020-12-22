package com.zou.zyqblogs.utils;

import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ImageVerify {
    //验证码的宽高
    private int width=100;
    private int heith=30;
    private static String verify;//保存正确的验证码
    private Random random=new Random();
    private String codes = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

    //随机获取验证码
    public String getRandom(){
        String str="";
        for (int i = 0; i <5 ; i++) {
            str+=codes.charAt(random.nextInt(codes.length()));
        }
        System.out.println(str);
        this.verify=str;
        return str;
    }
    //获取验证码的的值
    public static String getVerify(){
        return verify;
    }

    //随机颜色
    private Color randomColor() {
        int r = this.random.nextInt(225);  //这里为什么是225，因为当r，g，b都为255时，即为白色，为了好辨认，需要颜色深一点。
        int g = this.random.nextInt(225);
        int b = this.random.nextInt(225);
        return new Color(r, g, b);            //返回一个随机颜色
    }
    //画干扰线，验证码干扰线用来防止计算机解析图片
    private void drawLine(BufferedImage image) {
        int num = random.nextInt(3); //定义干扰线的数量
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setStroke(new BasicStroke(2));//设置干扰线的宽度
        for (int i = 0; i < num; i++) {
            //随机两个点的位置
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(heith);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(heith);
            g.setColor(randomColor());//设置干扰线的颜色
            g.drawLine(x1, y1, x2, y2);//设置干扰线
        }
    }

    public BufferedImage getImg(HttpSession session){
        //在内存中创建一张空图片 参数列表（宽，高，颜色）
        BufferedImage img = new BufferedImage(width,heith,BufferedImage.TYPE_INT_RGB);
        //通过笔得到图片
        Graphics2D bi = (Graphics2D) img.getGraphics();
        //设置图片的背景颜色
        bi.setColor(Color.white);//将画笔设为白色
        bi.fillRect(0,0,width,heith);//填充一个矩形，并且背景为白色
        //给图片设置数据
        bi.setColor(randomColor());//设置画笔为蓝色
        //因为要写字符数据，所以设置画笔的字体样式
        bi.setFont(new Font(null,Font.BOLD,20));
        bi.drawString(getRandom(),0,20);//将指定数据写到图片的指定坐标上
        session.setAttribute("verify",verify);

        drawLine(img);//实战干扰线

        return img;
    }
}
