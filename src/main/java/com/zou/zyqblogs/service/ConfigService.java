package com.zou.zyqblogs.service;

import com.zou.zyqblogs.mapper.GeneralMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface ConfigService extends GeneralMapper {
    //根据配置名称查询数据
    public Map<String,Object> finallConfig();

    //修改配置信息
    int updWebsite(Map<String,Object> map);
}
