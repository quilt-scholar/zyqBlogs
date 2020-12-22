package com.zou.zyqblogs.service;

import com.zou.zyqblogs.mapper.CategoryMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface CategoryService extends GeneralService {
    //修改分类信息
    public int updCategory(Map<String, Object> map);

    //根据多个id删除分类信息
    public int dels(Integer[] ids,BlogService blogService);
    //根据id删除分类信息
    public int del(Map<String, Object> map,BlogService blogService);
}
