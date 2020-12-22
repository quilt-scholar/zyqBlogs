package com.zou.zyqblogs.service.impl;


import com.zou.zyqblogs.mapper.BlogMapper;
import com.zou.zyqblogs.mapper.CommentMapper;
import com.zou.zyqblogs.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CommentServiceImpl extends GeneralServiceImpl implements CommentService {
    @Autowired
    private CommentMapper cm;

    public CommentServiceImpl() {
        super("tb_blog_comment");
    }


    //评论回复
    @Override
    public int auditComments(Map<String, Object> map) {
        return cm.auditComments(map);
    }
}
