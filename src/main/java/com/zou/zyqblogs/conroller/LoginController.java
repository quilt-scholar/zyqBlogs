package com.zou.zyqblogs.conroller;

import com.zou.zyqblogs.mapper.AdminUserMapper;
import com.zou.zyqblogs.pojo.AdminUser;
import com.zou.zyqblogs.service.AdminUserService;
import com.zou.zyqblogs.service.ConfigService;
import com.zou.zyqblogs.service.MenuService;
import com.zou.zyqblogs.utils.*;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
    //注入用户Service
    @Autowired
    private AdminUserService aus;
    @Autowired
    public ConfigService configService;
    @Autowired
    public MenuService menuService;
    //创建一个标记每个用户距离锁定的次数
    private Map<String,Integer> count=new HashMap<>();

    //获取验证码处理方法
    @GetMapping("/img")
    public void toImg( HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //告诉浏览器，这个请求的图片打开方式
        resp.setContentType("image/jpeg");
        //网站存在缓存，不要让浏览器缓存，禁用缓存
        //Cache-Control指定请求和响应遵循的缓存机制   no-cache：指示请求或响应消息不能缓存
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        //Pragma头域用来包含实现特定的指令   它的含义和Cache- Control:no-cache相同
        resp.setHeader("Pragma","no-cache");
        //Expires控制缓存失效日期 -1位已过期
        resp.setDateHeader("Expires",-1);

        //将画的图片响应个浏览器 参数列表（发送的图片，图片的格式(必须是图片的格式)，通过指定的输出流发送）
        ImageIO.write(new ImageVerify().getImg(req.getSession()),"jpg",resp.getOutputStream());
    }

    //登录处理方法
    @PostMapping("/login")
    @ResponseBody
    public ResultUtils login(AdminUser au, String verify, HttpSession session) throws Exception {
//        判断传递的验证码是否与正确验证码匹配
        if(!session.getAttribute("verify").equals(verify) || verify==null || verify.equals("")){
            return new ResultUtils(CodeEnum.VERIFY,null);
        }
        //判断传递过来参数是否为null
        if(au.getLoginUserName()==null||au.getLoginUserName().equals("")
        && au.getLoginPassword()==null||au.getLoginPassword().equals("")){
            return new ResultUtils(CodeEnum.USERPASS,null);
        }

        //根据用户名查该用户是否存在
        Map<String,Object> map=new HashMap<>();
        map.put("login_user_name",au.getLoginUserName());

        Map<String, Object> admin = aus.finallObjectById(map);
        if(admin==null){//判断用户是否存在
            return new ResultUtils(CodeEnum.USERPASS,null);
        }
        if (admin.get("locked").toString().equals("1")) {//如果用户存在，在判断用户是否被锁定
            return new ResultUtils(CodeEnum.LOCKED,null);
        }
        if(count.get(au.getLoginUserName())==null){//如果用户没有被锁定，就判断以用户为key的标记
            count.put(au.getLoginUserName(),3);
        }

        //将密码加密
        au.setLoginPassword(MD5Util.md5Encode(au.getLoginPassword()));
        //验证账号密码是否错误
        AdminUser adminUser = aus.queryAdminUserByUser(au);
        if(adminUser==null){
            //获取标记用户剩余锁定次数，并--
            Integer i = count.get(au.getLoginUserName());
            i--;
            count.put(au.getLoginUserName(),i);
            System.out.println(count.get(au.getLoginUserName()));
            if(count.get(au.getLoginUserName())==0){//如果=0则将该用户锁定
                map=new HashMap<>();
                map.put("admin_user_id",admin.get("admin_user_id"));
                map.put("locked",1);
                aus.updAdminUser(map);
                count.remove(au.getLoginUserName());//并将删除用户的标记锁定
            }
            return new ResultUtils(CodeEnum.USERPASS,null);
        }
        count.remove(au.getLoginUserName());//登入成功也需要将用户标记锁定删除（重置）
        //正确则将用户存入session中
        session.setAttribute("user",adminUser);
        return new ResultUtils(CodeEnum.SUCCED,null);
    }

    //用户信息修改处理方法
    @PostMapping("/adminEdit")
    @ResponseBody
    public ResultUtils adminEdit(@RequestParam Map<String,Object> map,HttpServletRequest req){
        int i = aus.updAdminUser(map);//将修改的数据传递进修改方法
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }

    //用户信息处理方法
    @PostMapping("/passEdit")
    @ResponseBody
    public ResultUtils passEdit(String oldPass,String newPass,String admin_user_id){
        //创建一个map，将旧密码信息存入该map
        Map<String,Object> map=new HashMap<>();
        map.put("login_password",oldPass);
        //通过通用查询方法，判断旧密码是否匹配，
        // 在执行查询方法时会将通用方法设置的tableName保留下来，需要在mapper中排除掉
        if(aus.finallObjectById(map)==null){
            return new ResultUtils(CodeEnum.OLDPASS,null);
        }
        //将要就该数据的id，和新密码存入map
        map.put("admin_user_id",admin_user_id);
        map.put("login_password",newPass);
        int i = aus.updAdminUser(map);//修改用户信息
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }

    //跳转页面的处理方法
    @GetMapping("/pass.html")
    public String pass(Model model,@RequestParam Map<String,Object> map){
        model.addAttribute("user",aus.finallObjectById(map));
        model.addAttribute("menus", ParamsUtil.fatherAndSonMenuList(menuService.finallObjects(new HashMap<>()),0));
        model.addAttribute("set",configService.finallConfig());
        return "afterEnd/system/pass";
    }
}
