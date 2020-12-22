package com.zou.zyqblogs.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TagMapper extends GeneralMapper {
    //根据标签名查询
    public List<Map<String,Object>> queryByName(String tag_name);

    //查询标签并查询出使用该标签的博文数
    public List<Map<String,Object>> queryTagsNum();

    //修改标签
    public int updTag(@Param("maps")Map<String,Object> map);
}
