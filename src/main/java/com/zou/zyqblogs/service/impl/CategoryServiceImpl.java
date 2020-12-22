package com.zou.zyqblogs.service.impl;


import com.zou.zyqblogs.mapper.BlogMapper;
import com.zou.zyqblogs.mapper.CategoryMapper;
import com.zou.zyqblogs.service.BlogService;
import com.zou.zyqblogs.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class CategoryServiceImpl extends GeneralServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper cm;

    public CategoryServiceImpl() {
        super("tb_blog_category");
    }
    @Override
    public int dels(Integer[] ids,BlogService blogService){
        //与其他controller批量删除方法相同
        Map<String, Object> map = new HashMap<>();
        map.put("ids",ids);
        map.put("tableId","category_id");

        int i = this.delObjects(map);
        //执行完分类的批量删除操作，需要将有级联关系的博文修改为未发布...
        //注意需要将数组转为Set集合去重
        i +=blogService.updCategory(new HashSet<>(Arrays.asList(ids)));

        return i;
    }
    @Override
    public int del(Map<String, Object> map,BlogService blogService){
        int i = this.delObject(map);
        //从参数map中获取分类ID转为int，存入set中
        int category_id = Integer.parseInt(map.get("category_id").toString());
        HashSet<Integer> ids = new HashSet<>();
        ids.add(category_id);
        //单独删除一个分类也需要修改有级联关系的博文
        i +=blogService.updCategory(ids);
        return i;
    }
    //修改分类信息
    @Override
    public int updCategory(Map<String, Object> map) {
        return cm.updCategory(map);
    }
}
