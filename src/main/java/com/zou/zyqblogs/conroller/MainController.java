package com.zou.zyqblogs.conroller;

import com.zou.zyqblogs.pojo.Blog;
import com.zou.zyqblogs.pojo.Category;
import com.zou.zyqblogs.service.*;
import com.zou.zyqblogs.utils.CodeEnum;
import com.zou.zyqblogs.utils.ParamsUtil;
import com.zou.zyqblogs.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class MainController {
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


    //获取所有管理模块表中数据数量
    @PostMapping("/getNums")
    @ResponseBody
    public ResultUtils getMainCount(){
        //获取需要存入map数据的key
        String[] keys={"blognum","categorynum","commentnum","tagnum","linknum"};
        //获取需要存入map数据的val
        Object[] values={blogService.finallObjectNum(new HashMap<>()),
                categoryService.finallObjectNum(new HashMap<>()),
                commentService.finallObjectNum(new HashMap<>()),
                tagService.finallObjectNum(new HashMap<>()),
                linkService.finallObjectNum(new HashMap<>())};
        //执行存入map操作
        Map<String,Object> map=ParamsUtil.saveMap(keys,values);
        return new ResultUtils(CodeEnum.SUCCED,map);
    }
    //获取访问量top10的文章
    @PostMapping("/top10")
    @ResponseBody
    public ResultUtils top10(){
        return new ResultUtils(CodeEnum.SUCCED,blogService.blogViewsTop10());
    }
    //获取各个分类有的博文数
    @PostMapping("/categoryNum")
    @ResponseBody
    public ResultUtils categoryNum(){
        return new ResultUtils(CodeEnum.SUCCED,blogService.blogCategroyOfArticleCount());
    }
    //查询网站近一个月内，每天的访问量
    @PostMapping("/websiteVisits")
    @ResponseBody
    public ResultUtils websiteVisits(){
        return new ResultUtils(CodeEnum.SUCCED,blogService.blogTimeInAMonth());
    }


    //博客添加
    @PostMapping("/addBlog")
    @ResponseBody
    public ResultUtils addBlog(@RequestParam Map<String,Object> blog){
        //获取富文本上传的第一张图片为封面图
        String image = UploadController.image;
        if(image==null || image==""){
            return new ResultUtils(CodeEnum.COVERICON,null);
        }
        blog.remove("editor-html-code");//删除多余的键值对

        //将分类获取出来，并分开id与name
        String[] split = blog.get("blog_category_name").toString().split(",");
        //获取需要存入map数据的key，val
        String[] keys={"blog_cover_image", "blog_category_id","blog_category_name"};
        ParamsUtil.saveMap(blog,keys,image,split[0],split[1]);//执行存map操作

        //添加博文，将map参数blog分为key，与val集合,并存入新map中
        keys= new String[]{"keys", "values"};
        blog=ParamsUtil.saveMap(keys,blog.keySet(),blog.values());

        int i = blogService.addObjects(blog);
        if(i>0){//判断博文是否添加成功
            //添加成功将图片路径，图片标记清空
            UploadController.image="";
            UploadController.i=1;

            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }
}
