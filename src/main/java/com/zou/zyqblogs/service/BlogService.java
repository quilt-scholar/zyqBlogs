package com.zou.zyqblogs.service;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BlogService extends GeneralService {
    //后端控制台统计报表需求
    //查询博客访问量top10
    public List<Map<String,Object>> blogViewsTop10();
    //查询网站近一个月内，每天的访问量，转为每周的访问量
    public List<Map<String, Object>> blogTimeInAMonth();
    //查询每个类别的博文数量
    public List<Map<String,Object>> blogCategroyOfArticleCount();

    //根据博文的标题查询
    List<Map<String,Object>> queryBlogByTitle(String blog_title, int page, int limit);

    //修改博文信息
    int updBlog(Map<String,Object> map);

    //删除分类时，修改与之级联的博文表的信息
    int updCategory(Set<Integer> ids);
}
