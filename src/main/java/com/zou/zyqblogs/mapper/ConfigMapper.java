package com.zou.zyqblogs.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface ConfigMapper extends GeneralMapper {
    //修改配置信息
    int updWebsite(@Param("maps") Map<String,Object> map);
    //根据配置名称查询数据
    Map<String,Object> finallByConfigName(String config_name);
}
