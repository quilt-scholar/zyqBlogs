package com.zou.zyqblogs.service;


import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public interface GeneralService {
    //查询所表中的数据数
    public int finallObjectNum(Map<String,Object> map);

    //查询表中的所有数据
    public List<Map<String,Object>> finallObjects(Map<String,Object> map);

    //查询表中指定条件的数据
    public Map<String,Object> finallObjectById(Map<String,Object> map);

    //为表添加数据
    public int addObjects(Map<String,Object> map);

    //批量删除
    public int delObjects(Map<String,Object> map);

    //删除
    public int delObject(Map<String,Object> map);

    //根据字段批量查询表中数据
    public List<Map<String,Object>> finallObjectByFields(Map<String,Object> map);


//    public int updObject(Map<String,Object> map);
}
