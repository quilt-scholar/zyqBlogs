package com.zou.zyqblogs.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface LinkMapper extends GeneralMapper {
    public int updLink(@Param("maps") Map<String,Object> map);
}
