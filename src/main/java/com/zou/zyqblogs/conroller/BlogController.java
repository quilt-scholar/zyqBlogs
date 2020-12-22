package com.zou.zyqblogs.conroller;

import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.classfile.Code;
import com.zou.zyqblogs.service.*;
import com.zou.zyqblogs.utils.CodeEnum;
import com.zou.zyqblogs.utils.ParamsUtil;
import com.zou.zyqblogs.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;


    //查询所有数据
    @PostMapping("/finAll")
    @ResponseBody
    public ResultUtils finall(@RequestParam Map<String,Object> map){
        map.put("orderBy","blog_id desc");//设置根据blog_id倒序排列
        //查询博客表中所有数据
        List<Map<String, Object>> maps = blogService.finallObjects(map);
        //将查询粗来的数据放入PageInfo中
        PageInfo<Map<String, Object>> list = new PageInfo<Map<String, Object>>(maps);
        return new ResultUtils(CodeEnum.SUCCED,list);
    }

    //根据id数组批量删除
    @PostMapping("/dels")
    @ResponseBody
    public ResultUtils dels(int[] ids){
        //创建一个map，将id数组与表的id名存入该map
        Map<String, Object> map = new HashMap<>();
        map.put("ids",ids);
        map.put("tableId","blog_id");
        //执行批量删除操作，将map传进去
        int i = blogService.delObjects(map);
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }

    //根据ID删除数据
    @PostMapping("/del")
    @ResponseBody
    public ResultUtils del(@RequestParam Map<String,Object> map){
        int i = blogService.delObject(map);
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }

    //根据标题查询数据
    @PostMapping("/queryByTitle")
    @ResponseBody
    public ResultUtils queryByTitle(@RequestParam Map<String,Object> map){
        map.put("orderBy","create_time desc");
        //创建一个map,判断需要模糊查询的key是否存在
        Map<String, Object> like = new HashMap<>();
        if(map.get("blog_title")!=null) {//存在则将它存入like
            like.put("blog_title", map.get("blog_title"));
            map.remove("blog_title");//将参数map中的blog_title删除
        }
        map.put("like",like);//价格模糊查询map存入参数map

        List<Map<String,Object>> maps = blogService.finallObjects(map);

        PageInfo<Map<String,Object>> pageInfo=new PageInfo<Map<String,Object>>(maps);
        return new ResultUtils(CodeEnum.SUCCED,pageInfo);
    }

    //博客修改页面跳转
    @GetMapping("/blogEdit.html")
    public String edit(Model m,@RequestParam Map<String,Object> map){
        //查询分类、标签所有数据，存入model中
        m.addAttribute("categoryList",categoryService.finallObjects(new HashMap<>()));
        m.addAttribute("tagList",tagService.finallObjects(new HashMap<>()));
        //根据id查询博文数据，存入model中
        m.addAttribute("selected",blogService.finallObjectById(map));
        return "/afterEnd/children/blogEdit";
    }

    //通过id修改博文数据
    @PostMapping("/edit")
    @ResponseBody
    public ResultUtils edit(@RequestParam Map<String,Object> map){
        map.remove("editor-html-code");//删除富文本传递的多余的数据
        //将传递的分类名称字符串，使用split根据‘，’分割，将分割的数据分别存入map中
        String[] split = map.get("blog_category_name").toString().split(",");
        //获取需要存入map数据的key，val,并执行存入map操作
        String[] keys={"blog_category_id","blog_category_name"};
        ParamsUtil.saveMap(map,keys,split[0],split[1]);
        int i = blogService.updBlog(map);//执行修改博文
        if(i>0){
            return new ResultUtils(CodeEnum.SUCCED,null);
        }
        return new ResultUtils(CodeEnum.BEDEFEATED,null);
    }

}
