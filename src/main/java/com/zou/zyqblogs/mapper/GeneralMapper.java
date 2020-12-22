package com.zou.zyqblogs.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GeneralMapper {
    //查询所表中的数据数
    public int finallObjectNum(@Param("maps") Map<String,Object> map);
    //查询表中的所有数据
    public List<Map<String,Object>> finallObjects(@Param("maps") Map<String,Object> map);
    //查询表中指定条件的数据
    public Map<String,Object> finallObjectById(@Param("maps") Map<String,Object> map);
    //为表添加数据
    public int addObjects(Map<String,Object> map);
    //批量删除
    public int delObjects(Map<String,Object> map);
    //删除
    public int delObject(@Param("maps") Map<String,Object> map);
    //根据字段批量查询表中数据
    public List<Map<String,Object>> finallObjectByFields(Map<String,Object> map);

}
