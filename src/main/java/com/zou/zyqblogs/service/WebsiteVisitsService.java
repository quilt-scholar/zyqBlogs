package com.zou.zyqblogs.service;


import java.util.List;
import java.util.Map;
import java.util.Set;

public interface WebsiteVisitsService extends GeneralService {
    //网站访问需求
    //查询当天时间到当前时间，中有没有数据
    public Map<String,Object> websiteDayVisits();
    //修改当天时间的网站访问量+1
    public int updDayVisits(Map<String,Object> map);

}
