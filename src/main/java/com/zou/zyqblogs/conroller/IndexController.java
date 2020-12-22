package com.zou.zyqblogs.conroller;

import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.zou.zyqblogs.service.*;
import com.zou.zyqblogs.utils.CodeEnum;
import com.zou.zyqblogs.utils.ParamsUtil;
import com.zou.zyqblogs.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private TagService tagService;
    @Autowired
    private LinkService linkService;
    @Autowired
    public ConfigService configService;
    @Autowired
    public MenuService menuService;

    //访问前端首页
    @GetMapping({"/","/index.html"})
    public String index(Model model,String blog_title,String blog_tags,String blog_category_name){
        //将搜索条件存入model中，用于前端调用
        model.addAttribute("blog_title",blog_title);
        model.addAttribute("blog_tags",blog_tags);
        model.addAttribute("blog_category_name",blog_category_name);

        //该方法用于查询启动页面需要的公共数据
        Map<String, Object> mpas = ParamsUtil.indexPublicDataExtract(blogService,adminUserService,tagService,configService);

        //将这些公共数据添加到model对应key的attribute
        model.addAttribute("blogsTime",mpas.get("blogsTime"));
        model.addAttribute("blogsViews",mpas.get("blogsViews"));
        model.addAttribute("admin",mpas.get("admin"));
        model.addAttribute("tags",mpas.get("tags"));
        model.addAttribute("set",mpas.get("set"));

        return "/leadingEnd/index";
    }
    //查询博文
    @PostMapping("/blogFilter")
    @ResponseBody
    public ResultUtils queryBlogFilter(@RequestParam Map<String,Object> map){
        map.put("orderBy","blog_id asc");
        map.put("limit","5");
        //创建一个map,判断需要模糊查询的key是否存在
        Map<String, Object> like = new HashMap<>();
        if(map.get("blog_title")!=null) {//存在则将它存入like
            like.put("blog_title", map.get("blog_title"));
            map.remove("blog_title");
        }
        if (map.get("blog_tags")!=null) {
            like.put("blog_tags", map.get("blog_tags"));
            map.remove("blog_tags");
        }
        map.put("like", like);//价格模糊查询map存入参数map

        List<Map<String, Object>> list = blogService.finallObjects(map);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return new ResultUtils(CodeEnum.SUCCED,pageInfo);
    }

    //访问前端友链首页
    @GetMapping("/links.html")
    public String links(Model model){
        //该方法用于查询启动页面需要的公共数据
        Map<String, Object> mpas = ParamsUtil.indexPublicDataExtract(blogService,adminUserService,tagService,configService);

        //将这些公共数据添加到model对应key的attribute
        model.addAttribute("blogsTime",mpas.get("blogsTime"));
        model.addAttribute("blogsViews",mpas.get("blogsViews"));
        model.addAttribute("admin",mpas.get("admin"));
        model.addAttribute("tags",mpas.get("tags"));
        model.addAttribute("set",mpas.get("set"));

        //创建map，循环3次，查询3种不同类型的友链添加到model中
        Map<String,Object> map = new HashMap<>();
        for(int i=0;i<3;i++){
            map.put("link_type",i+"");
            model.addAttribute("link"+(i+1),linkService.finallObjects(map));
        }
//        map.put("link_type","0");
//        model.addAttribute("link1",linkService.finallObjects(map));
//        map.put("link_type","1");
//        model.addAttribute("link2",linkService.finallObjects(map));
//        map.put("link_type","2");
//        model.addAttribute("link3",linkService.finallObjects(map));
        return "/leadingEnd/links";
    }

    //访问博文页面
    @GetMapping("/article/{id}")
    public String article(@PathVariable int id, Model model){
        //该方法用于查询启动页面需要的公共数据
        Map<String, Object> mpas = ParamsUtil.indexPublicDataExtract(blogService,adminUserService,tagService,configService);

        //将这些公共数据添加到model对应key的attribute
        model.addAttribute("blogsTime",mpas.get("blogsTime"));
        model.addAttribute("blogsViews",mpas.get("blogsViews"));
        model.addAttribute("admin",mpas.get("admin"));
        model.addAttribute("tags",mpas.get("tags"));
        model.addAttribute("set",mpas.get("set"));

        //创建一个map，存入查询博文ID，执行查询
        Map<String, Object> map = new HashMap<>();
        map.put("blog_id",id);
        model.addAttribute("blog",blogService.finallObjectById(map));

        //上面map中的博文id，在查询评论时也需要，所以不需要创建新的map
        //获取需要存入新map数据的key，val，执行存入map操作：comment_status是否审核
        String[] keys={"comment_status","page","limit","orderBy"};
        ParamsUtil.saveMap(map,keys,1,"1","5","comment_create_time desc");
        //执行查询评论操作，需要分页
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(commentService.finallObjects(map));
        model.addAttribute("comments",pageInfo);

        return "/leadingEnd/article";
    }
    //访问博文增加阅读量
    @PostMapping("/blogViews")
    @ResponseBody
    public ResultUtils blogViews(@RequestParam Map<String, Object> map){
        int i = blogService.updBlog(map);
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }
    //评论查询
    @PostMapping("/commentfinall")
    @ResponseBody
    public ResultUtils commentFinall(@RequestParam Map<String,Object> map){
        //查询已审核评论数据
        String[] keys={"comment_status","orderBy"};
        ParamsUtil.saveMap(map,keys,1,"comment_create_time desc");
//        map.put("comment_status",1);
//        map.put("orderBy","comment_create_time desc");
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(commentService.finallObjects(map));
        return new ResultUtils(CodeEnum.SUCCED,pageInfo);
    }




    //访问后台添加博客页面
    @GetMapping("/addBlog.html")
    public String getCategorys(Model m){
        Map<String,Object> map=new HashMap<>();
        m.addAttribute("set",configService.finallConfig());
        m.addAttribute("menus",ParamsUtil.fatherAndSonMenuList(menuService.finallObjects(new HashMap<>()),0));
        m.addAttribute("categoryList",categoryService.finallObjects(new HashMap<>()));
        m.addAttribute("tagList",tagService.finallObjects(new HashMap<>()));
        return "/afterEnd/main/writeBlogs";
    }

    //访问后台控制台页面
    @GetMapping("/main.html")
    public String main(Model model){
        model.addAttribute("menus",ParamsUtil.fatherAndSonMenuList(menuService.finallObjects(new HashMap<>()),0));
        model.addAttribute("set",configService.finallConfig());
        return "/afterEnd/main/main";
    }

    //访问后台博客管理页面
    @GetMapping("/blog.html")
    public String blog(Model model){
        model.addAttribute("menus",ParamsUtil.fatherAndSonMenuList(menuService.finallObjects(new HashMap<>()),0));
        model.addAttribute("set",configService.finallConfig());
        return "/afterEnd/admin/blog";
    }

    //访问后台博客评论页面
    @GetMapping("/comment.html")
    public String comment(Model model){
        model.addAttribute("menus",ParamsUtil.fatherAndSonMenuList(menuService.finallObjects(new HashMap<>()),0));
        model.addAttribute("set",configService.finallConfig());
        return "/afterEnd/admin/comment";
    }

    //访问后台博客友链页面
    @GetMapping("/link.html")
    public String link(Model model){
        model.addAttribute("menus",ParamsUtil.fatherAndSonMenuList(menuService.finallObjects(new HashMap<>()),0));
        model.addAttribute("set",configService.finallConfig());
        return "/afterEnd/admin/link";
    }

    //访问后台博客标签页面
    @GetMapping("/tag.html")
    public String tag(Model model){
        model.addAttribute("menus",ParamsUtil.fatherAndSonMenuList(menuService.finallObjects(new HashMap<>()),0));
        model.addAttribute("set",configService.finallConfig());
        return "/afterEnd/admin/tag";
    }

    //访问后台博客分类页面
    @GetMapping("/category.html")
    public String category(Model model){
        model.addAttribute("menus",ParamsUtil.fatherAndSonMenuList(menuService.finallObjects(new HashMap<>()),0));
        model.addAttribute("set",configService.finallConfig());
        return "/afterEnd/admin/category";
    }

    //访问后台退出
    @GetMapping("/outUser")
    public String out(HttpServletRequest req){
        req.getSession().removeAttribute("user");
        return "redirect:/login.html";
    }
}
