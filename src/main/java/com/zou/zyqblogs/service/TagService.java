package com.zou.zyqblogs.service;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TagService extends GeneralService {
    //根据标签名查询
    public List<Map<String,Object>> queryByName(String tag_name,int page,int limit);

    //查询标签并查询出使用该标签的博文数
    public List<Map<String,Object>> queryTagsNum(int page,int limit,String orderBy);

    //修改标签
    public int updTag(Map<String,Object> map);
}
