package com.zou.zyqblogs.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface CategoryMapper extends GeneralMapper {
    //修改分类信息
    public int updCategory(@Param("maps") Map<String, Object> map);
}
