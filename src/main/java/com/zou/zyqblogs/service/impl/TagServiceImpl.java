package com.zou.zyqblogs.service.impl;


import com.github.pagehelper.PageHelper;
import com.zou.zyqblogs.mapper.LinkMapper;
import com.zou.zyqblogs.mapper.TagMapper;
import com.zou.zyqblogs.service.LinkService;
import com.zou.zyqblogs.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TagServiceImpl extends GeneralServiceImpl implements TagService {
    @Autowired
    private TagMapper tm;

    public TagServiceImpl() {
        super("tb_blog_tag");
    }

    @Override
    public List<Map<String, Object>> queryByName(String tag_name, int page, int limit) {
        PageHelper.startPage(page,limit,"tag_id desc");
        return tm.queryByName(tag_name);
    }

    @Override
    public List<Map<String, Object>> queryTagsNum(int page,int limit,String orderBy) {
        PageHelper.startPage(page,limit,orderBy);
        return tm.queryTagsNum();
    }

    @Override
    public int updTag(Map<String, Object> map) {
        return tm.updTag(map);
    }
}
