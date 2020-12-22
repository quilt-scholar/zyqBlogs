package com.zou.zyqblogs.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface CommentMapper extends GeneralMapper {
    //评论回复
    public int auditComments(@Param("maps") Map<String, Object> map);
}
