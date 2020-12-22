package com.zou.zyqblogs.service.impl;


import com.github.pagehelper.PageHelper;
import com.zou.zyqblogs.mapper.BlogMapper;
import com.zou.zyqblogs.mapper.WebsiteVisitsMapper;
import com.zou.zyqblogs.service.BlogService;
import com.zou.zyqblogs.service.WebsiteVisitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class WebsiteVisitsServiceImpl extends GeneralServiceImpl implements WebsiteVisitsService {
    @Autowired
    private WebsiteVisitsMapper wvm;
    public WebsiteVisitsServiceImpl() {
        super("website_visits");
    }

    //查询当天时间到当前时间，中有没有数据
    @Override
    public Map<String, Object> websiteDayVisits() {
        return wvm.websiteDayVisits();
    }

    //修改为当天时间的网站访问量+1
    @Override
    public int updDayVisits(Map<String, Object> map) {
        return wvm.updDayVisits(map);
    }


}
