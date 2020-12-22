package com.zou.zyqblogs.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MenuMapper extends GeneralMapper {
    int uploadMenu(@Param("maps") Map<String,Object> map);
}
