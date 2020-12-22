package com.zou.zyqblogs.service.impl;


import com.zou.zyqblogs.mapper.BlogMapper;
import com.zou.zyqblogs.mapper.LinkMapper;
import com.zou.zyqblogs.service.BlogService;
import com.zou.zyqblogs.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LinkServiceImpl extends GeneralServiceImpl implements LinkService {
    @Autowired
    private LinkMapper lm;

    public LinkServiceImpl() {
        super("tb_link");
    }

    @Override
    public int updLink(Map<String, Object> map) {
        return lm.updLink(map);
    }
}
