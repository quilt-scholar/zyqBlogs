package com.zou.zyqblogs.service;


import java.util.Map;

public interface CommentService extends GeneralService {
    //评论回复
    public int auditComments(Map<String, Object> map);
}
