package com.zou.zyqblogs.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface WebsiteVisitsMapper extends GeneralMapper {
    //网站访问需求
    //查询当天时间到当前时间，中有没有数据
    public Map<String,Object> websiteDayVisits();
    //修改当天时间的网站访问量+1
    public int updDayVisits(Map<String,Object> map);

    //删除近一个月内以外的访问量数据
    public int delExceptNear1MonthWithinVisitor();

}
