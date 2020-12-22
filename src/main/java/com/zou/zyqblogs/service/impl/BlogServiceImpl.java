package com.zou.zyqblogs.service.impl;


import com.github.pagehelper.PageHelper;
import com.zou.zyqblogs.mapper.BlogMapper;
import com.zou.zyqblogs.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BlogServiceImpl extends GeneralServiceImpl implements BlogService {
    @Autowired
    private BlogMapper bm;

    public BlogServiceImpl() {
        super("tb_blog");
    }

    //后端控制台统计报表需求
    //查询博客访问量top10
    public List<Map<String,Object>> blogViewsTop10(){
        return bm.blogViewsTop10();
    }
    //查询网站近一个月内，每天的访问量，转为每周的访问量
    @Override
    public List<Map<String, Object>> blogTimeInAMonth() {
        int i=0;//标记循环次数，到了第7次就将七天的游览量的总和存入map
        int j=0;//标记周数，到了第7次就给j+1
        int count=0;//标记7天游览量的总和数
        //将每周的的访问量，存入listMap中，用来规定格式
        List<Map<String, Object>> nums=new ArrayList<>();
        //查询到一个月内网站，每天的访问量
        List<Map<String, Object>> maps = bm.blogTimeInAMonth();
        for (Map<String, Object> map : maps) {//遍历查询粗来的maps
            i+=1;//每遍历一次，i标记则+1
            //每遍历一次，将遍历出来的某天访问量+=，存到count标记中
            count+=Integer.parseInt(map.get("day_visits").toString());
            if(i==7){//判断i=7时，一周
                j+=1;//将j标记+1，代表第1周
                i=0;//并将i清0

                //需要重新new一个map，否则会在nums中存入一样的数据
                Map<String,Object> num=new HashMap<>();//存每周总和的map

                num.put("weeks","第"+j+"周");//将周次存入num中
                num.put("count",count);//将count存入num中
                //将j周的num，存入nums中
                nums.add(num);
                count=0;//并将count清0
            }
        }
        return nums;//返回num
    }
    //查询每个类别的博文数量
    public List<Map<String,Object>> blogCategroyOfArticleCount(){
        return bm.blogCategroyOfArticleCount();
    }



    //根据博文的标题查询
    @Override
    public List<Map<String,Object>> queryBlogByTitle(String blog_title,int page,int limit) {
        PageHelper.startPage(page,limit,"blog_id desc");//通过PageHelper实现分页操作
        return bm.queryBlogByTitle(blog_title);
    }

    //修改博文信息
    @Override
    public int updBlog(Map<String, Object> map) {
        return bm.updBlog(map);
    }

    //删除分类时，修改与之级联的博文表的信息
    @Override
    public int updCategory(Set<Integer> ids) {
        return bm.updCategory(ids);
    }
}
